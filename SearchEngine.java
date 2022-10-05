import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String[] num = new String[300];
    int size = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Add stuff to the search bar using /add?=s or find out what's in the list using /contents");
        } else if (url.getPath().equals("/empty")) {
            num = new String[300];
            size = 0;
            return String.format("Search bar has been emptied");
        } else if (url.getPath().equals("/contents")) {
            String list = "";
            if (size == 0) {
                return String.format(list);
            }
            for (int i=0; i < size; i++) {
                list += num[i] + " ";
            }
            return String.format(list);
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String list = "";
                    for (int i = 0; i < size; i++) {
                        if (num[i].contains(parameters[1])) {
                            list += num[i] + " ";
                        }
                        
                    }
                    return String.format("Search results: " + list);
                }
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    num[size] = (parameters[1]);
                    size += 1;
                    return String.format(parameters[1] + " has been added to the search");
                }
            }
        }
        return "404 Not Found!";
    }
}



public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    
    }
}
