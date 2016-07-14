package obj;

import java.security.NoSuchAlgorithmException;

import util.SHA256;

/**
 * A BlockHeader consists of the hash of previous block's header, the root of the 
 * Merkle tree containing the transactions, and the nonce value that results in the
 * hash value of the concatenation of the three elements of the BlockHeader falling 
 * below the proof-of-work difficulty threshold.
 */
public class BlockHeader {
	
	private String previousPoW;
	private String merkleRoot;
	private int nonce;

	public BlockHeader(String previousPoW, String merkleRoot) {		
		this.previousPoW = previousPoW;
		this.merkleRoot = merkleRoot;
	}
	
	/**
	 * Tries nonce values in increasing order from zero until it obtains a hash value of the concatenation
	 * of the previous block header, the root of the Merkle tree containing the transactions, and the nonce,
	 * which is smaller than the required proof-of-work difficulty.
	 * @return String of 64 hex digits that represents the successful hash of the header
	 */
	public String hash(String difficulty) {
		
		String result = "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";
		nonce = -1;
		
		while (result.compareTo(difficulty) > 0) {
			
			try {
				
				System.out.print(++nonce + "\t");
				result = tryNonce(nonce);
				System.out.println(result);
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
	public String previousPoW() {
		return previousPoW;
	}
	
	public int nonce() {
		return nonce;
	}
	
	public String merkleRoot() {
		return merkleRoot;
	}
	
	private String tryNonce(int nonce) throws NoSuchAlgorithmException {
		
		SHA256 sha256 = new SHA256();
		String hash = sha256.hashString(previousPoW + merkleRoot + String.valueOf(nonce));
		return hash;
	}

}