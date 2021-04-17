import java.util.*;
import java.io.*;

public class Main {
  
	
	
	
	
  public static void main(String args[]) throws IOException {
    
    //write your code here
	  Scanner sc=new Scanner(System.in);
	   
	  System.out.println("Enter the starting characters Or a complete word to search");
	  String input=sc.next();
	  boolean flag=false;
	  if(input.length()==0 || input.length()>=2147483647) {   // corner case
		  flag=true;
		  System.out.println("Entered word is wrong, please try another word");
	  }
	  for(int i=0;i<input.length();i++) {
		  if(input.charAt(i)-'a'<0 || input.charAt(i)-'a'>25) {   // corner case
			  flag=true;
			  System.out.println("Entered word contains invalid characters, input word having characters in between a-z");
			  break;
		  }
	  }
	  Dictionary d=new Dictionary();
	  if(!flag) {
		  d.addPredefinedWords(); // TC-> big O(num of words in dictionary * max. length of word), SC->big O(num of words in dictionary * max. length of word)
		  d.searchWord(input);   //TC-> big O(num of words in dictionary * max. length of word) , SC->constant
	  }
	  
	     sc.close(); 
	    }
	    
  
  
  
}
class Dictionary{
	class Node{
		char val;
		Map<Character,Node> nextChars;
		boolean isTerminal;
		Node(char currChar,boolean isTerminal){
			this.val=currChar;
			this.isTerminal=isTerminal;
			nextChars=new HashMap<>();
		}
	}
	
	private Node root=new Node('\0',false);
	
	List<String> result;
	boolean found;
	
	public void addPredefinedWords() {
		try { 
		      File myObj = new File("list.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {  // TC->big O(num of words in dictionary * max. length of word),  SC->big O(num of words in dictionary * max. length of word)
		        String data = myReader.nextLine();
		        //System.out.println(data);
		        if(data.length()>0)
		        addWord(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		
	}
	
	public void addWord(String word) {
		addWord(word,root);  //  TC-> big O(length of word) , SC->big O(length of word)
	}
	private void addWord(String word,Node prevNode) {
		
		if(word.length()==0) {
			prevNode.isTerminal=true;
			return;
		}
		
		char currChar=word.charAt(0);
		Node newNode=prevNode.nextChars.get(currChar);
		if(newNode==null) {
			newNode=new Node(currChar,false);
			prevNode.nextChars.put(currChar,newNode);
		}
		addWord(word.substring(1,word.length()),newNode);
	}
	
	public void searchWord(String word) {
		result=new ArrayList<>();
		found=false;
		searchWord(word,root,"");  //  TC-> big O(num of words in dictionary * max. length of word) , SC->constant
		if(found) {
			System.out.println("Entered word is in dictionary");
		}
		else {
			System.out.println("Entered word is misspelled, below are the suggested words");
			
		}
		display();    // TC-> big O(length of result list) , SC-> big O(length of result list * max. length of a word)
	}


	private void searchWord(String word, Node prevNode,String wordSoFar) {
		
		if(word.length()==0) {
			if(prevNode.isTerminal) {
				found=true;
				result.add(wordSoFar);
			}
			else {
				printSuggestedWords(prevNode,wordSoFar);
			}
			return;
		}
		
		char currChar=word.charAt(0);
		Node currNode=prevNode.nextChars.get(currChar);
		if(currNode==null) {
			printSuggestedWords(prevNode,wordSoFar);
			return;
		}
		
		searchWord(word.substring(1,word.length()),currNode,wordSoFar+currChar);
		
	}

	private void printSuggestedWords(Node prevNode, String wordSoFar) {
	if(prevNode.isTerminal) {
		result.add(wordSoFar);
	}
	
	for(char c:prevNode.nextChars.keySet()) {
		
		printSuggestedWords(prevNode.nextChars.get(c),wordSoFar+prevNode.val);
	}
	
		
	}
	
private void display() {  
		
		for(String s:result) {
			System.out.println(s);
		}
		
	}
}
	 
