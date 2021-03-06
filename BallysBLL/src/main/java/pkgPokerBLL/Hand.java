package pkgPokerBLL;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import pkgPokerEnum.eCardNo;
import pkgPokerEnum.eHandStrength;



public class Hand {

	private UUID HandID;
	private boolean bIsScored;
	private HandScore HS;
	private ArrayList<Card> CardsInHand = new ArrayList<Card>();
	
	public Hand()
	{
		
	}
	
	public void AddCardToHand(Card c)
	{
		CardsInHand.add(c);
	}

	public ArrayList<Card> getCardsInHand() {
		return CardsInHand;
	}
	
	public HandScore getHandScore()
	{
		return HS;
	}
	
	public Hand EvaluateHand()
	{
		Hand h = Hand.EvaluateHand(this);
		return h;
	}
	
	private static Hand EvaluateHand(Hand h)  {

		Collections.sort(h.getCardsInHand());
		
		//	Another way to sort
		//	Collections.sort(h.getCardsInHand(), Card.CardRank);
		
		HandScore hs = new HandScore();
		try {
			Class<?> c = Class.forName("pkgPokerBLL.Hand");

			for (eHandStrength hstr : eHandStrength.values()) {
				Class[] cArg = new Class[2];
				cArg[0] = pkgPokerBLL.Hand.class;
				cArg[1] = pkgPokerBLL.HandScore.class;

				Method meth = c.getMethod(hstr.getEvalMethod(), cArg);
				Object o = meth.invoke(null, new Object[] { h, hs });

				// If o = true, that means the hand evaluated- skip the rest of
				// the evaluations
				if ((Boolean) o) {
					break;
				}
			}

			h.bIsScored = true;
			h.HS = hs;

		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return h;
	}
	
	
	
	
	
	
	
	
	
	
	
	//TODO: Implement This Method
	public static boolean isHandRoyalFlush(Hand h, HandScore hs)
	{
		return false;
	}
	
	//TODO: Implement This Method
	public static boolean isHandStraightFlush(Hand h, HandScore hs)
	{
		boolean isStraightFlush = false;
		
		ArrayList<Card> kickers = new ArrayList<Card>();
		
		if ((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank().getiRankNbr() != 14)
			&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()==h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank().getiRankNbr()-1)
			&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()==h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()-1)
			&& (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()==h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()-1)
			&& (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr()==h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()-1))
			{
			isStraightFlush = true;
			hs.setHandStrength(eHandStrength.StraightFlush);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank());
			hs.setKickers(kickers);
			}
		
		return isStraightFlush;
	}	
	//TODO: Implement This Method
	public static boolean isHandFourOfAKind(Hand h, HandScore hs)
	{
		return false;
	}	
	
	//TODO: Implement This Method
	public static boolean isHandFlush(Hand h, HandScore hs)
	{
		boolean isHandFlush = false;
		
		ArrayList<Card> kickers = new ArrayList<Card>();
		
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit() == h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteSuit())
			&& (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit() == h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteSuit())
			&& (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit() == h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteSuit())
			&& (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit() == h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteSuit()))
		{
		isHandFlush = true;
		hs.setHandStrength(eHandStrength.Flush);
		hs.setHiHand(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank());
		}
		return isHandFlush;
	}		
	
	//TODO: Implement This Method
	public static boolean isHandStraight(Hand h, HandScore hs)
	{
		return false;
	}	
	
	//TODO: Implement This Method
	public static boolean isHandThreeOfAKind(Hand h, HandScore hs)
	{
		boolean isHandThreeOfAKind = false;
		
		ArrayList<Card> kickers = new ArrayList<Card>();
		
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank()))
				{
					isHandThreeOfAKind = true;
					hs.setHandStrength(eHandStrength.Flush);
					hs.setHiHand(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank());
				}
		else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()))
				{
					isHandThreeOfAKind = true;
					hs.setHandStrength(eHandStrength.Flush);
					hs.setHiHand(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank());
				}
		else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()))
				{
					isHandThreeOfAKind = true;
					hs.setHandStrength(eHandStrength.Flush);
					hs.setHiHand(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank());
				}
				
		return isHandThreeOfAKind;
	}		
	
	//TODO: Implement This Method
	public static boolean isHandTwoPair(Hand h, HandScore hs)
	{
		return false;
	}	
	
	//TODO: Implement This Method
	public static boolean isHandPair(Hand h, HandScore hs)
	{
		boolean isHandPair = false;
		
		ArrayList<Card> kickers = new ArrayList<Card>();
		
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()))
				{
					isHandPair = true;
					hs.setHandStrength(eHandStrength.FullHouse);
					hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
					hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
				}
		else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()))
				{
					isHandPair = true;
					hs.setHandStrength(eHandStrength.FullHouse);
					hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
					hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
				}
		else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()))
				{
					isHandPair = true;
					hs.setHandStrength(eHandStrength.FullHouse);
					hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
					hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
				}
		else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() != h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()))
				{
					isHandPair = true;
					hs.setHandStrength(eHandStrength.FullHouse);
					hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
					hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
				}

		return isHandPair;
	}	
	
	//TODO: Implement This Method
	public static boolean isHandHighCard(Hand h, HandScore hs)
	{
		return false;
	}	
	
	//TODO: Implement This Method
	public static boolean isAcesAndEights(Hand h, HandScore hs)
	{
		return false;
	}	
	
	
	public static boolean isHandFullHouse(Hand h, HandScore hs) {

		boolean isFullHouse = false;
		
		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}

		return isFullHouse;

	}
}
