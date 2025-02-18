import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Creates or uses an existing file, clears it, and then writes bits to the file
 * @author Manvir Hansra
 */
public class BitOutputStream {
    private OutputStream output;
    private int bitCount = 0;
    private byte currentByte = 0;

    public BitOutputStream(File file, boolean append) throws FileNotFoundException {
        this.output = new FileOutputStream(file, append);
    }

    /**
     * Writes a singular bit to the output stream
     * @param bit the bit to be written
     * @throws IOException
     */
    public void writeBit(char bit) throws IOException {
        currentByte <<= 1;
        currentByte |= (bit - '0');

        bitCount++;

        if (bitCount == 8) {
            bitCount = 0;
            output.write(currentByte);
        }
    }

    /**
     * Writes multiple bits to the output stream
     * @param bitString bits to be written
     * @throws IOException
     */
    public void writeBits(String bitString) throws IOException{
        for (char c : bitString.toCharArray()) {
            writeBit(c);
        }
    }

    /**
     * Closes the file and fills in 0s for the remaining bits in the current byte if needed
     * @throws IOException
     */
    public void close() throws IOException {
        if (bitCount != 8 && bitCount != 0) {
            while (bitCount < 8) {
            currentByte <<= 1;
            bitCount++;
            }
            output.write(currentByte);
        }
        output.close();
    }
}
