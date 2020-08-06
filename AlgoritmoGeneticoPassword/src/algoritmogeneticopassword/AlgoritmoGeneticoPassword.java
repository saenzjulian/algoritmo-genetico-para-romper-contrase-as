/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmogeneticopassword;

import static java.lang.System.exit;
import java.util.Random;

/**
 *
 * @author JAGS 
 * Desarrollado por Julián Andrés García Sáenz
 * saenzjulian80@gmail.com 
 * Colombia +57 317 579 2354
 *
 */
public class AlgoritmoGeneticoPassword {

    /**
     * @param args the command line arguments
     */
    static int filas = 6, columnas = 4, numeroDeGanadores = 3, Ngenes = 5, sumatoria = 0;

    static String[][] poblacion = new String[filas][columnas];
    static String[][] PoblacionTem = new String[filas][columnas];
    static String[] parejas = new String[filas];
    static String[] ganadoresDelTorneo = new String[numeroDeGanadores]; 
    static String letras = " ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz012345679";
    static String cadenaMeta = "aqui escribo cualquier cosa";

    public static void iniciarPoblacion(String[][] poblacion, String letras) {
        System.out.println("-----------------------------------------------------------");
        System.out.println("--------------------INICIANDO POBLACIÓN--------------------");
        String individuo = "";
        Random random = new Random();
        for (int i = 0; i < parejas.length; i++) {
            individuo = "";
            for (int g = 0; g < cadenaMeta.length(); g++) {
                individuo += letras.charAt(random.nextInt(letras.length()));
            }
            poblacion[i][0] = "" + (i + 1);
            poblacion[i][1] = individuo;
            poblacion[i][2] = "" + 0;
        }
        System.out.println("-----------------------------------------------------------");
    }

    public static void convertirIndividuo(String[][] Poblacion, String target) {
        int contador = 0;
        for (int i = 0; i < parejas.length; i++) {
            String individuo = Poblacion[i][1];
            contador = 0;
            //Cada valor se multiplica por potencias de 2 
            for (int k = 0; k < target.length(); k++) {
                //Comparando letra a letra para ver cuales son las correctas
                if (target.charAt(k) == individuo.charAt(k)) {
                    contador++;
                }
            }
            Poblacion[i][2] = "" + contador;
            sumatoria += contador;
        }
        System.out.println("Convirtiendo individuo...");
    }

//    public static double calidadIndividuo(String[][] Poblacion) {
//        //columa que tiene el valor del individuo  
//        double mayor = Double.parseDouble(Poblacion[0][2]);
//        double valor = 0;
//        for (int i = 0; i < parejas.length; i++) {
//            valor = Double.parseDouble(Poblacion[i][2]);
//            if (mayor > valor) {
//                mayor = valor;
//            }
//        }
//        System.out.println("------------------------MEJOR ADAPTADO-----------------------");
//        System.out.println("---------------------------"+mayor+"-------------------------");
//        return (mayor);
//    }
    
    public static String mutacion(String individuo, String target) {
        Random random = new Random();//aleatorio para el punto de combinacion
        String resultado = "";
        String[] vectora = individuo.split("");
        String mutables = "", genI;

        String posicionesC = "0-";
        for (int k = 0; k < target.length(); k++) {
            if (target.charAt(k) == individuo.charAt(k)) {
            } else {
                posicionesC += "" + k + "-"; //Me indica que en esa posición está mal la letra
            }
        }
        posicionesC += "0";
        System.out.println("posicionamiento incorrecto " + posicionesC);
        if (posicionesC.length() >= 0) {
            String[] pociciones = posicionesC.split("-");
            int posicion1 = random.nextInt(pociciones.length);
            int posicion2 = random.nextInt(pociciones.length);

            int gen1 = Integer.parseInt("" + pociciones[posicion1]);
            int gen2 = Integer.parseInt("" + pociciones[posicion2]);

            vectora[gen1] = "" + letras.charAt(random.nextInt(letras.length()));
            vectora[gen2] = "" + letras.charAt(random.nextInt(letras.length()));

            resultado = "";
            for (int i = 0; i < vectora.length; i++) {
                resultado += vectora[i];
            }
            return resultado;
        } else {
            return individuo;
        }
    }

    public static String combinar(String individuoA, String individuoB) {
        Random random = new Random();
        String[] vectorA = individuoA.split("");
        String[] vectorB = individuoB.split("");
        String resultado = "";
        int gen;
        for (int i = 0; i < (individuoA.length() / 2); i++) {
            resultado += vectorA[i];
        }
        for (int i = (individuoA.length() / 2); i < individuoB.length(); i++) {
            resultado += vectorB[i];
        }
        return resultado;
    }

    public static void combinacionMutacion(String[][] poblacion, String[][] poblacionTemporal) {
        System.out.println("--------------------COMBINACIÓN Y MUTACIÓN-------------------");
        Random random = new Random();//aleatorio para el punto de combinacion
        String individuoA, individuoB;
        String ParejaA = "";
        //se hace sólo hasta la mitad porque cada uno tiene una pareja
        for (int i = 0; i < (parejas.length / 2); i++) {
            individuoA = poblacion[i][1];
            ParejaA = parejas[i]; //se obtiene la pareja del vector de parejas
            String cadenaADN = "";
            individuoB = poblacion[Integer.parseInt(ParejaA) - 1][1];
            //puntocruce=ri.nextInt(4);//punto de cruce aleatorio 
            cadenaADN = combinar(individuoA, individuoB);
            System.out.println("[" + poblacion[i][0] + "]"
                    + "[" + poblacion[i][1] + "] Cruzado con [" + poblacion[Integer.parseInt(ParejaA) - 1][0] + "]"
                    + "[" + poblacion[Integer.parseInt(ParejaA) - 1][1] + "]");
            System.out.println(">>>>> Nuevo individuo [" + cadenaADN + "]");
            System.out.println();

            poblacionTemporal[i][0] = "" + (i + 1);
            poblacionTemporal[i][1] = cadenaADN;
        }//for de parejas        
        for (int i = 0; i < parejas.length; i++) {
            poblacion[i][0] = poblacionTemporal[i][0];
            poblacion[i][1] = poblacionTemporal[i][1];
        }

        System.out.println("---------------------------MUTANDO--------------------------");
        System.out.println("--------------INDIVIDUO--------------RESULTADO--------------");
        for (int k = 0; k < 2; k++) {
            int mutado = random.nextInt(6);
            individuoA = poblacion[mutado][1];
            String cadenaADN = mutacion(individuoA, cadenaMeta);
            poblacion[mutado][1] = cadenaADN;
            System.out.println("[" + poblacion[mutado][0] + "]" + "[" + poblacion[mutado][1] + "]" + " Gen mutado" + "[" + mutado + "] Resultado >>> [" + poblacion[mutado][0] + "]" + "[" + cadenaADN + "]");
        }
        System.out.println("------------------------------------------------------------");

    }

    public static void copiarse(String[][] poblacion, String[][] poblacionTemporal) {
        System.out.println("---------------------REPLICANDO GENÉTICA--------------------");
        int indice = 0;
        int t = 0;
        //se saca del vector de ganadores  
        for (int i = 0; i < ganadoresDelTorneo.length; i++) {
            int ganador = Integer.parseInt(ganadoresDelTorneo[i]);
            poblacionTemporal[indice][0] = "" + (i + t + 1); //Nombre del Individuo
            poblacionTemporal[indice + 1][0] = "" + (i + 2 + t);//Nombre del Individuo Copiado

            //se copian cada una de las columnas, de poblacion a poblacion temporal
            for (int f = 1; f < columnas; f++) {
                poblacionTemporal[indice][f] = poblacion[indice][f];
                poblacionTemporal[indice + 1][f] = poblacion[indice + 1][f];
            }
            indice += 2;
            t++;
        }
        //paso toda la info de poblacionTemporal a poblacion
        for (int i = 0; i < parejas.length; i++) {
            poblacion[i][0] = poblacionTemporal[i][0];
            poblacion[i][1] = poblacionTemporal[i][1];
        }
        System.out.println("------------------------------------------------------------");

    }

    public static void verGanadores(String[] ganadores) {
        System.out.println("--------------------------GANADORES-------------------------");
        int individuoGanador = 0;
        //for par ver los ganadoers 
        for (int i = 0; i < ganadores.length; i++) {
            individuoGanador = Integer.parseInt(ganadores[i]);
            System.out.println("[" + poblacion[individuoGanador - 1][0] + "][" + poblacion[individuoGanador - 1][1] + "][" + poblacion[individuoGanador - 1][2] + "][" + poblacion[individuoGanador - 1][3] + "]");
        }
        System.out.println("-----------------------------------------------------------");
    }

    public static void torneo(String[][] poblacion) {
        String rendimientoA = "", rendimientoB = "";
        String parejaA = "";
        int iterador = 0;
        System.out.println("---------------------------TORNEO---------------------------");
        //Torneo entre las parejas, se realiza hasta la mitad porque cada uno tiene su pareja
        for (int i = 0; i < (parejas.length / 2); i++) {
            //Desempeño de los dos individuos, de acuerdo a la funcion actual  
            rendimientoA = poblacion[i][2];
            parejaA = parejas[i];
            rendimientoB = poblacion[Integer.parseInt(parejaA) - 1][2];
            System.out.println("[" + poblacion[i][0] + "][" + poblacion[i][1] + "][" + poblacion[i][2] + "][" + rendimientoA + "] VS. [" + poblacion[Integer.parseInt(parejaA) - 1][0] + "][" + poblacion[Integer.parseInt(parejaA) - 1][1] + "][" + poblacion[Integer.parseInt(parejaA) - 1][2] + "][" + rendimientoB + "]");
            //Se comparan para competir 
            if (Double.parseDouble(rendimientoA) >= Double.parseDouble(rendimientoB)) {
                ganadoresDelTorneo[iterador] = poblacion[i][0];
            } else {
                ganadoresDelTorneo[iterador] = parejaA;
            }
            iterador++;
        }
        System.out.println("-----------------------------------------------------------");
    }

    public static void seleccionDeParejas(String[][] Poblacion) {
        System.out.println("Seleccionando parejas...");
//        String aux = Poblacion[1][0];
        for (int i = 0; i < parejas.length; i++) {
            parejas[(parejas.length - 1) - i] = Poblacion[i][0];
        }
    }

    public static void adaptabilidad(String[][] Poblacion, double sumatoria) {
        System.out.println("Calculando adaptabilidad...");
        for (int i = 0; i < parejas.length; i++) {
            if (sumatoria != 0) {
                /**
                 * ADAPTABILIDAD se define como la sumatoria de letras
                 * correctamente ubicadas de todos los individuos dividido la
                 * cantidad de letras correctas de cada individuo
                 */
                Poblacion[i][3] = "" + (Double.parseDouble(Poblacion[i][2]) / sumatoria);
            }
        }
    }

    public static void verPoblacion(String[][] poblacion, boolean emparejado) {
        System.out.println("-------------------------POBLACIÓN-------------------------");
        String cadena = "";
        //Hasta el numero de individuos,filas
        for (int i = 0; i < filas; i++) {
            for (int k = 0; k < columnas; k++) {
                if (!(poblacion[i][k] == null)) {
                    cadena += "[" + poblacion[i][k] + "]";
                }
            }
            //si se visualiza con parejas o no 
            if (emparejado && parejas[i] != null) {
                cadena += "  Emparejado con " + parejas[i] + "\n";
            } else {
                cadena += "" + "\n";
            }
            //A ver si ya se decifró
            if (Integer.parseInt(poblacion[i][2]) >= cadenaMeta.length()) {
                System.out.println("\n>>> Superado [" + poblacion[i][1] + "]"
                        + "\n>>> Idoneidad [" + poblacion[i][2] + "]"
                        + "\n>>>Genética [" + poblacion[i][3] + "]");
                exit(0);
            }
        }
        System.out.print(cadena); //Muestro toda la población
        System.out.println("-----------------------------------------------------------");
    }

    public static void main(String[] args) {
        iniciarPoblacion(poblacion, letras);
        verPoblacion(poblacion, false);
        int iterar = 0;
        double adaptados = 1;
        /**
         * *
         * iterar vaya hasta 18999 para que por seguridad no se vaya a ir a un
         * ciclo infinito, pero cuando encuentre la solución el va a salir del
         * ciclo
         */
        while (adaptados < 19000) {
            convertirIndividuo(poblacion, cadenaMeta);
            adaptabilidad(poblacion, sumatoria);
            verPoblacion(poblacion, true);
            seleccionDeParejas(poblacion);
            torneo(poblacion);
            verGanadores(ganadoresDelTorneo);
            copiarse(poblacion, PoblacionTem);
            verPoblacion(PoblacionTem, true);
            combinacionMutacion(poblacion, PoblacionTem);
            iterar++;
        }
    }
}
