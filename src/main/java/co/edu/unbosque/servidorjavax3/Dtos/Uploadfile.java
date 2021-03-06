package co.edu.unbosque.servidorjavax3.Dtos;

import co.edu.unbosque.servidorjavax3.services.ImageServices;

import java.io.*;


import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "uploadFile", value = "/uploadFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class Uploadfile extends HttpServlet {
    private String UPLOAD_DIRECTORY = "uploads";
    private ImageServices imageServices;
    public Uploadfile(){
        this.imageServices=new ImageServices();
    }
    public void init() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Extracting parameters other than uploaded file
        System.out.println("Name: " + request.getParameter("name"));
        System.out.println("esta es la linea 24 ");

        // Getting an instance of the upload path
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

        System.out.println(uploadPath+ " esta es la ruta ");
        System.out.println("esta es el nombre "+request.getParameter("titulo"));
        System.out.println("esta es el precio "+request.getParameter("fcoins"));
        System.out.println("esta es el user "+request.getParameter("artist"));

        File uploadDir = new File(uploadPath);

        // If path doesn`t exist, create it
        if (!uploadDir.exists()) uploadDir.mkdir();

        try {
            // Getting each part from the request
            String fileName = crear()+".jpg";
            System.out.println("este es el servelet context "+getServletContext().getRealPath("")+File.separator);
            String path=getServletContext().getRealPath("")+File.separator;
            for (Part part : request.getParts()) {
                // Storing the file using the same name
                part.write(uploadPath + File.separator + fileName);
                System.out.println("Este es el alfa numerico"+fileName);
                System.out.println("Este es nombre original"+part.getSubmittedFileName());


            }
            imageServices.create_peace(request.getParameter("titulo"),request.getParameter("fcoins"),request.getParameter("artist"),fileName,getServletContext().getRealPath("") + File.separator);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Redirecting
        response.sendRedirect("./resultado.html");
    }

    public void destroy() {}

    public String crear(){

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return generatedString;
    }
}