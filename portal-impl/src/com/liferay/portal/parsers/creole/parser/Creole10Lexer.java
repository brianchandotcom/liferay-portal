// $ANTLR 3.0.1 Creole10.g 2013-05-21 18:55:14

/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

 package com.liferay.portal.parsers.creole.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class Creole10Lexer extends Lexer {
    public static final int T75=75;
    public static final int T76=76;
    public static final int T73=73;
    public static final int INSIGNIFICANT_CHAR=40;
    public static final int T74=74;
    public static final int T79=79;
    public static final int STAR=17;
    public static final int T77=77;
    public static final int T78=78;
    public static final int FORCED_END_OF_LINE=4;
    public static final int NOWIKI_BLOCK_CLOSE=27;
    public static final int NOWIKI_CLOSE=28;
    public static final int SPACE=35;
    public static final int EOF=-1;
    public static final int T72=72;
    public static final int T71=71;
    public static final int LIST_ITEM=7;
    public static final int T70=70;
    public static final int T62=62;
    public static final int TEXT_NODE=11;
    public static final int T63=63;
    public static final int WIKI=14;
    public static final int T64=64;
    public static final int T65=65;
    public static final int T66=66;
    public static final int SLASH=39;
    public static final int T67=67;
    public static final int T68=68;
    public static final int ESCAPE=26;
    public static final int T69=69;
    public static final int BRACE_CLOSE=37;
    public static final int EQUAL=18;
    public static final int TABULATOR=36;
    public static final int LIST_ITEM_PART=8;
    public static final int PIPE=19;
    public static final int LINK_OPEN=21;
    public static final int T61=61;
    public static final int T60=60;
    public static final int BLANKS=31;
    public static final int FORCED_LINEBREAK=25;
    public static final int UNORDERED_LIST=12;
    public static final int POUND=16;
    public static final int DASH=32;
    public static final int HEADING_SECTION=5;
    public static final int NOWIKI_OPEN=23;
    public static final int HORIZONTAL_SECTION=6;
    public static final int T49=49;
    public static final int T48=48;
    public static final int UNFORMATTED_TEXT=13;
    public static final int NOWIKI_SECTION=9;
    public static final int T43=43;
    public static final int Tokens=81;
    public static final int IMAGE_OPEN=22;
    public static final int ITAL=20;
    public static final int T42=42;
    public static final int COLON_SLASH=38;
    public static final int T41=41;
    public static final int T47=47;
    public static final int T46=46;
    public static final int T45=45;
    public static final int T44=44;
    public static final int NEWLINE=15;
    public static final int T50=50;
    public static final int SCAPE_NODE=10;
    public static final int IMAGE_CLOSE=30;
    public static final int T59=59;
    public static final int LINK_CLOSE=29;
    public static final int T52=52;
    public static final int T80=80;
    public static final int T51=51;
    public static final int CR=33;
    public static final int T54=54;
    public static final int T53=53;
    public static final int EXTENSION=24;
    public static final int T56=56;
    public static final int T55=55;
    public static final int T58=58;
    public static final int T57=57;
    public static final int LF=34;
    public Creole10Lexer() {;} 
    public Creole10Lexer(CharStream input) {
	super(input);
    }
    public String getGrammarFileName() { return "Creole10.g"; }

    // $ANTLR start T41
    public final void mT41() throws RecognitionException {
	try {
	    int _type = T41;
	    // Creole10.g:24:5: ( ':' )
	    // Creole10.g:24:7: ':'
	    {
	    match(':'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T41

    // $ANTLR start T42
    public final void mT42() throws RecognitionException {
	try {
	    int _type = T42;
	    // Creole10.g:25:5: ( 'C' )
	    // Creole10.g:25:7: 'C'
	    {
	    match('C'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T42

    // $ANTLR start T43
    public final void mT43() throws RecognitionException {
	try {
	    int _type = T43;
	    // Creole10.g:26:5: ( '2' )
	    // Creole10.g:26:7: '2'
	    {
	    match('2'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T43

    // $ANTLR start T44
    public final void mT44() throws RecognitionException {
	try {
	    int _type = T44;
	    // Creole10.g:27:5: ( 'D' )
	    // Creole10.g:27:7: 'D'
	    {
	    match('D'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T44

    // $ANTLR start T45
    public final void mT45() throws RecognitionException {
	try {
	    int _type = T45;
	    // Creole10.g:28:5: ( 'o' )
	    // Creole10.g:28:7: 'o'
	    {
	    match('o'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T45

    // $ANTLR start T46
    public final void mT46() throws RecognitionException {
	try {
	    int _type = T46;
	    // Creole10.g:29:5: ( 'k' )
	    // Creole10.g:29:7: 'k'
	    {
	    match('k'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T46

    // $ANTLR start T47
    public final void mT47() throws RecognitionException {
	try {
	    int _type = T47;
	    // Creole10.g:30:5: ( 'u' )
	    // Creole10.g:30:7: 'u'
	    {
	    match('u'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T47

    // $ANTLR start T48
    public final void mT48() throws RecognitionException {
	try {
	    int _type = T48;
	    // Creole10.g:31:5: ( 'W' )
	    // Creole10.g:31:7: 'W'
	    {
	    match('W'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T48

    // $ANTLR start T49
    public final void mT49() throws RecognitionException {
	try {
	    int _type = T49;
	    // Creole10.g:32:5: ( 'i' )
	    // Creole10.g:32:7: 'i'
	    {
	    match('i'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T49

    // $ANTLR start T50
    public final void mT50() throws RecognitionException {
	try {
	    int _type = T50;
	    // Creole10.g:33:5: ( 'F' )
	    // Creole10.g:33:7: 'F'
	    {
	    match('F'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T50

    // $ANTLR start T51
    public final void mT51() throws RecognitionException {
	try {
	    int _type = T51;
	    // Creole10.g:34:5: ( 'l' )
	    // Creole10.g:34:7: 'l'
	    {
	    match('l'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T51

    // $ANTLR start T52
    public final void mT52() throws RecognitionException {
	try {
	    int _type = T52;
	    // Creole10.g:35:5: ( 'c' )
	    // Creole10.g:35:7: 'c'
	    {
	    match('c'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T52

    // $ANTLR start T53
    public final void mT53() throws RecognitionException {
	try {
	    int _type = T53;
	    // Creole10.g:36:5: ( 'r' )
	    // Creole10.g:36:7: 'r'
	    {
	    match('r'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T53

    // $ANTLR start T54
    public final void mT54() throws RecognitionException {
	try {
	    int _type = T54;
	    // Creole10.g:37:5: ( 'G' )
	    // Creole10.g:37:7: 'G'
	    {
	    match('G'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T54

    // $ANTLR start T55
    public final void mT55() throws RecognitionException {
	try {
	    int _type = T55;
	    // Creole10.g:38:5: ( 'g' )
	    // Creole10.g:38:7: 'g'
	    {
	    match('g'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T55

    // $ANTLR start T56
    public final void mT56() throws RecognitionException {
	try {
	    int _type = T56;
	    // Creole10.g:39:5: ( 'e' )
	    // Creole10.g:39:7: 'e'
	    {
	    match('e'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T56

    // $ANTLR start T57
    public final void mT57() throws RecognitionException {
	try {
	    int _type = T57;
	    // Creole10.g:40:5: ( 'J' )
	    // Creole10.g:40:7: 'J'
	    {
	    match('J'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T57

    // $ANTLR start T58
    public final void mT58() throws RecognitionException {
	try {
	    int _type = T58;
	    // Creole10.g:41:5: ( 'S' )
	    // Creole10.g:41:7: 'S'
	    {
	    match('S'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T58

    // $ANTLR start T59
    public final void mT59() throws RecognitionException {
	try {
	    int _type = T59;
	    // Creole10.g:42:5: ( 'P' )
	    // Creole10.g:42:7: 'P'
	    {
	    match('P'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T59

    // $ANTLR start T60
    public final void mT60() throws RecognitionException {
	try {
	    int _type = T60;
	    // Creole10.g:43:5: ( 'M' )
	    // Creole10.g:43:7: 'M'
	    {
	    match('M'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T60

    // $ANTLR start T61
    public final void mT61() throws RecognitionException {
	try {
	    int _type = T61;
	    // Creole10.g:44:5: ( 'a' )
	    // Creole10.g:44:7: 'a'
	    {
	    match('a'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T61

    // $ANTLR start T62
    public final void mT62() throws RecognitionException {
	try {
	    int _type = T62;
	    // Creole10.g:45:5: ( 't' )
	    // Creole10.g:45:7: 't'
	    {
	    match('t'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T62

    // $ANTLR start T63
    public final void mT63() throws RecognitionException {
	try {
	    int _type = T63;
	    // Creole10.g:46:5: ( 'b' )
	    // Creole10.g:46:7: 'b'
	    {
	    match('b'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T63

    // $ANTLR start T64
    public final void mT64() throws RecognitionException {
	try {
	    int _type = T64;
	    // Creole10.g:47:5: ( 'd' )
	    // Creole10.g:47:7: 'd'
	    {
	    match('d'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T64

    // $ANTLR start T65
    public final void mT65() throws RecognitionException {
	try {
	    int _type = T65;
	    // Creole10.g:48:5: ( 'n' )
	    // Creole10.g:48:7: 'n'
	    {
	    match('n'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T65

    // $ANTLR start T66
    public final void mT66() throws RecognitionException {
	try {
	    int _type = T66;
	    // Creole10.g:49:5: ( 'O' )
	    // Creole10.g:49:7: 'O'
	    {
	    match('O'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T66

    // $ANTLR start T67
    public final void mT67() throws RecognitionException {
	try {
	    int _type = T67;
	    // Creole10.g:50:5: ( 'm' )
	    // Creole10.g:50:7: 'm'
	    {
	    match('m'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T67

    // $ANTLR start T68
    public final void mT68() throws RecognitionException {
	try {
	    int _type = T68;
	    // Creole10.g:51:5: ( 's' )
	    // Creole10.g:51:7: 's'
	    {
	    match('s'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T68

    // $ANTLR start T69
    public final void mT69() throws RecognitionException {
	try {
	    int _type = T69;
	    // Creole10.g:52:5: ( 'h' )
	    // Creole10.g:52:7: 'h'
	    {
	    match('h'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T69

    // $ANTLR start T70
    public final void mT70() throws RecognitionException {
	try {
	    int _type = T70;
	    // Creole10.g:53:5: ( 'p' )
	    // Creole10.g:53:7: 'p'
	    {
	    match('p'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T70

    // $ANTLR start T71
    public final void mT71() throws RecognitionException {
	try {
	    int _type = T71;
	    // Creole10.g:54:5: ( 'R' )
	    // Creole10.g:54:7: 'R'
	    {
	    match('R'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T71

    // $ANTLR start T72
    public final void mT72() throws RecognitionException {
	try {
	    int _type = T72;
	    // Creole10.g:55:5: ( 'x' )
	    // Creole10.g:55:7: 'x'
	    {
	    match('x'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T72

    // $ANTLR start T73
    public final void mT73() throws RecognitionException {
	try {
	    int _type = T73;
	    // Creole10.g:56:5: ( 'T' )
	    // Creole10.g:56:7: 'T'
	    {
	    match('T'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T73

    // $ANTLR start T74
    public final void mT74() throws RecognitionException {
	try {
	    int _type = T74;
	    // Creole10.g:57:5: ( 'y' )
	    // Creole10.g:57:7: 'y'
	    {
	    match('y'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T74

    // $ANTLR start T75
    public final void mT75() throws RecognitionException {
	try {
	    int _type = T75;
	    // Creole10.g:58:5: ( 'U' )
	    // Creole10.g:58:7: 'U'
	    {
	    match('U'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T75

    // $ANTLR start T76
    public final void mT76() throws RecognitionException {
	try {
	    int _type = T76;
	    // Creole10.g:59:5: ( 'X' )
	    // Creole10.g:59:7: 'X'
	    {
	    match('X'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T76

    // $ANTLR start T77
    public final void mT77() throws RecognitionException {
	try {
	    int _type = T77;
	    // Creole10.g:60:5: ( '<<TableOfContents>>' )
	    // Creole10.g:60:7: '<<TableOfContents>>'
	    {
	    match("<<TableOfContents>>"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T77

    // $ANTLR start T78
    public final void mT78() throws RecognitionException {
	try {
	    int _type = T78;
	    // Creole10.g:61:5: ( '<<TableOfContents title=' )
	    // Creole10.g:61:7: '<<TableOfContents title='
	    {
	    match("<<TableOfContents title="); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T78

    // $ANTLR start T79
    public final void mT79() throws RecognitionException {
	try {
	    int _type = T79;
	    // Creole10.g:62:5: ( '\\\"' )
	    // Creole10.g:62:7: '\\\"'
	    {
	    match('\"'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T79

    // $ANTLR start T80
    public final void mT80() throws RecognitionException {
	try {
	    int _type = T80;
	    // Creole10.g:63:5: ( '>>' )
	    // Creole10.g:63:7: '>>'
	    {
	    match(">>"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T80

    // $ANTLR start ESCAPE
    public final void mESCAPE() throws RecognitionException {
	try {
	    int _type = ESCAPE;
	    // Creole10.g:1119:12: ( '~' )
	    // Creole10.g:1119:14: '~'
	    {
	    match('~'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end ESCAPE

    // $ANTLR start NOWIKI_BLOCK_CLOSE
    public final void mNOWIKI_BLOCK_CLOSE() throws RecognitionException {
	try {
	    int _type = NOWIKI_BLOCK_CLOSE;
	    // Creole10.g:1120:21: ( NEWLINE '}}}' )
	    // Creole10.g:1120:23: NEWLINE '}}}'
	    {
	    mNEWLINE(); 
	    match("}}}"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end NOWIKI_BLOCK_CLOSE

    // $ANTLR start NEWLINE
    public final void mNEWLINE() throws RecognitionException {
	try {
	    int _type = NEWLINE;
	    // Creole10.g:1121:13: ( ( CR )? LF | CR )
	    int alt2=2;
	    int LA2_0 = input.LA(1);

	    if ( (LA2_0=='\r') ) {
		int LA2_1 = input.LA(2);

		if ( (LA2_1=='\n') ) {
		    alt2=1;
		}
		else {
		    alt2=2;}
	    }
	    else if ( (LA2_0=='\n') ) {
		alt2=1;
	    }
	    else {
		NoViableAltException nvae =
		    new NoViableAltException("1121:1: NEWLINE : ( ( CR )? LF | CR );", 2, 0, input);

		throw nvae;
	    }
	    switch (alt2) {
		case 1 :
		    // Creole10.g:1121:15: ( CR )? LF
		    {
		    // Creole10.g:1121:15: ( CR )?
		    int alt1=2;
		    int LA1_0 = input.LA(1);

		    if ( (LA1_0=='\r') ) {
			alt1=1;
		    }
		    switch (alt1) {
			case 1 :
			    // Creole10.g:1121:17: CR
			    {
			    mCR(); 

			    }
			    break;

		    }

		    mLF(); 

		    }
		    break;
		case 2 :
		    // Creole10.g:1122:9: CR
		    {
		    mCR(); 

		    }
		    break;

	    }
	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end NEWLINE

    // $ANTLR start CR
    public final void mCR() throws RecognitionException {
	try {
	    // Creole10.g:1123:16: ( '\\r' )
	    // Creole10.g:1123:18: '\\r'
	    {
	    match('\r'); 

	    }

	}
	finally {
	}
    }
    // $ANTLR end CR

    // $ANTLR start LF
    public final void mLF() throws RecognitionException {
	try {
	    // Creole10.g:1124:16: ( '\\n' )
	    // Creole10.g:1124:18: '\\n'
	    {
	    match('\n'); 

	    }

	}
	finally {
	}
    }
    // $ANTLR end LF

    // $ANTLR start BLANKS
    public final void mBLANKS() throws RecognitionException {
	try {
	    int _type = BLANKS;
	    // Creole10.g:1126:12: ( ( SPACE | TABULATOR )+ )
	    // Creole10.g:1126:14: ( SPACE | TABULATOR )+
	    {
	    // Creole10.g:1126:14: ( SPACE | TABULATOR )+
	    int cnt3=0;
	    loop3:
	    do {
		int alt3=2;
		int LA3_0 = input.LA(1);

		if ( (LA3_0=='\t'||LA3_0==' ') ) {
		    alt3=1;
		}


		switch (alt3) {
		case 1 :
		    // Creole10.g:
		    {
		    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
			input.consume();

		    }
		    else {
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recover(mse);	 throw mse;
		    }


		    }
		    break;

		default :
		    if ( cnt3 >= 1 ) break loop3;
			EarlyExitException eee =
			    new EarlyExitException(3, input);
			throw eee;
		}
		cnt3++;
	    } while (true);


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end BLANKS

    // $ANTLR start SPACE
    public final void mSPACE() throws RecognitionException {
	try {
	    // Creole10.g:1127:18: ( ' ' )
	    // Creole10.g:1127:20: ' '
	    {
	    match(' '); 

	    }

	}
	finally {
	}
    }
    // $ANTLR end SPACE

    // $ANTLR start TABULATOR
    public final void mTABULATOR() throws RecognitionException {
	try {
	    // Creole10.g:1128:21: ( '\\t' )
	    // Creole10.g:1128:23: '\\t'
	    {
	    match('\t'); 

	    }

	}
	finally {
	}
    }
    // $ANTLR end TABULATOR

    // $ANTLR start BRACE_CLOSE
    public final void mBRACE_CLOSE() throws RecognitionException {
	try {
	    int _type = BRACE_CLOSE;
	    // Creole10.g:1130:16: ( NEWLINE '}' )
	    // Creole10.g:1130:18: NEWLINE '}'
	    {
	    mNEWLINE(); 
	    match('}'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end BRACE_CLOSE

    // $ANTLR start COLON_SLASH
    public final void mCOLON_SLASH() throws RecognitionException {
	try {
	    int _type = COLON_SLASH;
	    // Creole10.g:1131:16: ( ':' '/' )
	    // Creole10.g:1131:18: ':' '/'
	    {
	    match(':'); 
	    match('/'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end COLON_SLASH

    // $ANTLR start ITAL
    public final void mITAL() throws RecognitionException {
	try {
	    int _type = ITAL;
	    // Creole10.g:1132:10: ( '//' )
	    // Creole10.g:1132:12: '//'
	    {
	    match("//"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end ITAL

    // $ANTLR start NOWIKI_OPEN
    public final void mNOWIKI_OPEN() throws RecognitionException {
	try {
	    int _type = NOWIKI_OPEN;
	    // Creole10.g:1133:16: ( '{{{' )
	    // Creole10.g:1133:18: '{{{'
	    {
	    match("{{{"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end NOWIKI_OPEN

    // $ANTLR start NOWIKI_CLOSE
    public final void mNOWIKI_CLOSE() throws RecognitionException {
	try {
	    int _type = NOWIKI_CLOSE;
	    // Creole10.g:1134:16: ( '}}}' )
	    // Creole10.g:1134:18: '}}}'
	    {
	    match("}}}"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end NOWIKI_CLOSE

    // $ANTLR start LINK_OPEN
    public final void mLINK_OPEN() throws RecognitionException {
	try {
	    int _type = LINK_OPEN;
	    // Creole10.g:1135:14: ( '[[' )
	    // Creole10.g:1135:16: '[['
	    {
	    match("[["); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end LINK_OPEN

    // $ANTLR start LINK_CLOSE
    public final void mLINK_CLOSE() throws RecognitionException {
	try {
	    int _type = LINK_CLOSE;
	    // Creole10.g:1136:15: ( ']]' )
	    // Creole10.g:1136:17: ']]'
	    {
	    match("]]"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end LINK_CLOSE

    // $ANTLR start IMAGE_OPEN
    public final void mIMAGE_OPEN() throws RecognitionException {
	try {
	    int _type = IMAGE_OPEN;
	    // Creole10.g:1137:15: ( '{{' )
	    // Creole10.g:1137:17: '{{'
	    {
	    match("{{"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end IMAGE_OPEN

    // $ANTLR start IMAGE_CLOSE
    public final void mIMAGE_CLOSE() throws RecognitionException {
	try {
	    int _type = IMAGE_CLOSE;
	    // Creole10.g:1138:16: ( '}}' )
	    // Creole10.g:1138:18: '}}'
	    {
	    match("}}"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end IMAGE_CLOSE

    // $ANTLR start FORCED_LINEBREAK
    public final void mFORCED_LINEBREAK() throws RecognitionException {
	try {
	    int _type = FORCED_LINEBREAK;
	    // Creole10.g:1139:19: ( '\\\\\\\\' )
	    // Creole10.g:1139:21: '\\\\\\\\'
	    {
	    match("\\\\"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end FORCED_LINEBREAK

    // $ANTLR start EQUAL
    public final void mEQUAL() throws RecognitionException {
	try {
	    int _type = EQUAL;
	    // Creole10.g:1140:11: ( '=' )
	    // Creole10.g:1140:13: '='
	    {
	    match('='); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end EQUAL

    // $ANTLR start PIPE
    public final void mPIPE() throws RecognitionException {
	try {
	    int _type = PIPE;
	    // Creole10.g:1141:10: ( '|' )
	    // Creole10.g:1141:12: '|'
	    {
	    match('|'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end PIPE

    // $ANTLR start POUND
    public final void mPOUND() throws RecognitionException {
	try {
	    int _type = POUND;
	    // Creole10.g:1142:11: ( '#' )
	    // Creole10.g:1142:13: '#'
	    {
	    match('#'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end POUND

    // $ANTLR start DASH
    public final void mDASH() throws RecognitionException {
	try {
	    int _type = DASH;
	    // Creole10.g:1143:10: ( '-' )
	    // Creole10.g:1143:12: '-'
	    {
	    match('-'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end DASH

    // $ANTLR start STAR
    public final void mSTAR() throws RecognitionException {
	try {
	    int _type = STAR;
	    // Creole10.g:1144:10: ( '*' )
	    // Creole10.g:1144:12: '*'
	    {
	    match('*'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end STAR

    // $ANTLR start SLASH
    public final void mSLASH() throws RecognitionException {
	try {
	    int _type = SLASH;
	    // Creole10.g:1145:11: ( '/' )
	    // Creole10.g:1145:13: '/'
	    {
	    match('/'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end SLASH

    // $ANTLR start EXTENSION
    public final void mEXTENSION() throws RecognitionException {
	try {
	    int _type = EXTENSION;
	    // Creole10.g:1146:14: ( '@@' )
	    // Creole10.g:1146:16: '@@'
	    {
	    match("@@"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end EXTENSION

    // $ANTLR start INSIGNIFICANT_CHAR
    public final void mINSIGNIFICANT_CHAR() throws RecognitionException {
	try {
	    int _type = INSIGNIFICANT_CHAR;
	    // Creole10.g:1148:21: ( . )
	    // Creole10.g:1148:23: .
	    {
	    matchAny(); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end INSIGNIFICANT_CHAR

    public void mTokens() throws RecognitionException {
	// Creole10.g:1:8: ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR )
	int alt4=62;
	int LA4_0 = input.LA(1);

	if ( (LA4_0==':') ) {
	    int LA4_1 = input.LA(2);

	    if ( (LA4_1=='/') ) {
		alt4=46;
	    }
	    else {
		alt4=1;}
	}
	else if ( (LA4_0=='C') ) {
	    alt4=2;
	}
	else if ( (LA4_0=='2') ) {
	    alt4=3;
	}
	else if ( (LA4_0=='D') ) {
	    alt4=4;
	}
	else if ( (LA4_0=='o') ) {
	    alt4=5;
	}
	else if ( (LA4_0=='k') ) {
	    alt4=6;
	}
	else if ( (LA4_0=='u') ) {
	    alt4=7;
	}
	else if ( (LA4_0=='W') ) {
	    alt4=8;
	}
	else if ( (LA4_0=='i') ) {
	    alt4=9;
	}
	else if ( (LA4_0=='F') ) {
	    alt4=10;
	}
	else if ( (LA4_0=='l') ) {
	    alt4=11;
	}
	else if ( (LA4_0=='c') ) {
	    alt4=12;
	}
	else if ( (LA4_0=='r') ) {
	    alt4=13;
	}
	else if ( (LA4_0=='G') ) {
	    alt4=14;
	}
	else if ( (LA4_0=='g') ) {
	    alt4=15;
	}
	else if ( (LA4_0=='e') ) {
	    alt4=16;
	}
	else if ( (LA4_0=='J') ) {
	    alt4=17;
	}
	else if ( (LA4_0=='S') ) {
	    alt4=18;
	}
	else if ( (LA4_0=='P') ) {
	    alt4=19;
	}
	else if ( (LA4_0=='M') ) {
	    alt4=20;
	}
	else if ( (LA4_0=='a') ) {
	    alt4=21;
	}
	else if ( (LA4_0=='t') ) {
	    alt4=22;
	}
	else if ( (LA4_0=='b') ) {
	    alt4=23;
	}
	else if ( (LA4_0=='d') ) {
	    alt4=24;
	}
	else if ( (LA4_0=='n') ) {
	    alt4=25;
	}
	else if ( (LA4_0=='O') ) {
	    alt4=26;
	}
	else if ( (LA4_0=='m') ) {
	    alt4=27;
	}
	else if ( (LA4_0=='s') ) {
	    alt4=28;
	}
	else if ( (LA4_0=='h') ) {
	    alt4=29;
	}
	else if ( (LA4_0=='p') ) {
	    alt4=30;
	}
	else if ( (LA4_0=='R') ) {
	    alt4=31;
	}
	else if ( (LA4_0=='x') ) {
	    alt4=32;
	}
	else if ( (LA4_0=='T') ) {
	    alt4=33;
	}
	else if ( (LA4_0=='y') ) {
	    alt4=34;
	}
	else if ( (LA4_0=='U') ) {
	    alt4=35;
	}
	else if ( (LA4_0=='X') ) {
	    alt4=36;
	}
	else if ( (LA4_0=='<') ) {
	    int LA4_37 = input.LA(2);

	    if ( (LA4_37=='<') ) {
		int LA4_94 = input.LA(3);

		if ( (LA4_94=='T') ) {
		    int LA4_115 = input.LA(4);

		    if ( (LA4_115=='a') ) {
			int LA4_122 = input.LA(5);

			if ( (LA4_122=='b') ) {
			    int LA4_123 = input.LA(6);

			    if ( (LA4_123=='l') ) {
				int LA4_124 = input.LA(7);

				if ( (LA4_124=='e') ) {
				    int LA4_125 = input.LA(8);

				    if ( (LA4_125=='O') ) {
					int LA4_126 = input.LA(9);

					if ( (LA4_126=='f') ) {
					    int LA4_127 = input.LA(10);

					    if ( (LA4_127=='C') ) {
						int LA4_128 = input.LA(11);

						if ( (LA4_128=='o') ) {
						    int LA4_129 = input.LA(12);

						    if ( (LA4_129=='n') ) {
							int LA4_130 = input.LA(13);

							if ( (LA4_130=='t') ) {
							    int LA4_131 = input.LA(14);

							    if ( (LA4_131=='e') ) {
								int LA4_132 = input.LA(15);

								if ( (LA4_132=='n') ) {
								    int LA4_133 = input.LA(16);

								    if ( (LA4_133=='t') ) {
									int LA4_134 = input.LA(17);

									if ( (LA4_134=='s') ) {
									    int LA4_135 = input.LA(18);

									    if ( (LA4_135=='>') ) {
										alt4=37;
									    }
									    else if ( (LA4_135==' ') ) {
										alt4=38;
									    }
									    else {
										NoViableAltException nvae =
										    new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 135, input);

										throw nvae;
									    }
									}
									else {
									    NoViableAltException nvae =
										new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 134, input);

									    throw nvae;
									}
								    }
								    else {
									NoViableAltException nvae =
									    new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 133, input);

									throw nvae;
								    }
								}
								else {
								    NoViableAltException nvae =
									new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 132, input);

								    throw nvae;
								}
							    }
							    else {
								NoViableAltException nvae =
								    new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 131, input);

								throw nvae;
							    }
							}
							else {
							    NoViableAltException nvae =
								new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 130, input);

							    throw nvae;
							}
						    }
						    else {
							NoViableAltException nvae =
							    new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 129, input);

							throw nvae;
						    }
						}
						else {
						    NoViableAltException nvae =
							new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 128, input);

						    throw nvae;
						}
					    }
					    else {
						NoViableAltException nvae =
						    new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 127, input);

						throw nvae;
					    }
					}
					else {
					    NoViableAltException nvae =
						new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 126, input);

					    throw nvae;
					}
				    }
				    else {
					NoViableAltException nvae =
					    new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 125, input);

					throw nvae;
				    }
				}
				else {
				    NoViableAltException nvae =
					new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 124, input);

				    throw nvae;
				}
			    }
			    else {
				NoViableAltException nvae =
				    new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 123, input);

				throw nvae;
			    }
			}
			else {
			    NoViableAltException nvae =
				new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 122, input);

			    throw nvae;
			}
		    }
		    else {
			NoViableAltException nvae =
			    new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 115, input);

			throw nvae;
		    }
		}
		else {
		    NoViableAltException nvae =
			new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 94, input);

		    throw nvae;
		}
	    }
	    else {
		alt4=62;}
	}
	else if ( (LA4_0=='\"') ) {
	    alt4=39;
	}
	else if ( (LA4_0=='>') ) {
	    int LA4_39 = input.LA(2);

	    if ( (LA4_39=='>') ) {
		alt4=40;
	    }
	    else {
		alt4=62;}
	}
	else if ( (LA4_0=='~') ) {
	    alt4=41;
	}
	else if ( (LA4_0=='\r') ) {
	    switch ( input.LA(2) ) {
	    case '\n':
		{
		int LA4_99 = input.LA(3);

		if ( (LA4_99=='}') ) {
		    int LA4_100 = input.LA(4);

		    if ( (LA4_100=='}') ) {
			alt4=42;
		    }
		    else {
			alt4=45;}
		}
		else {
		    alt4=43;}
		}
		break;
	    case '}':
		{
		int LA4_100 = input.LA(3);

		if ( (LA4_100=='}') ) {
		    alt4=42;
		}
		else {
		    alt4=45;}
		}
		break;
	    default:
		alt4=43;}

	}
	else if ( (LA4_0=='\n') ) {
	    int LA4_42 = input.LA(2);

	    if ( (LA4_42=='}') ) {
		int LA4_100 = input.LA(3);

		if ( (LA4_100=='}') ) {
		    alt4=42;
		}
		else {
		    alt4=45;}
	    }
	    else {
		alt4=43;}
	}
	else if ( (LA4_0=='\t'||LA4_0==' ') ) {
	    alt4=44;
	}
	else if ( (LA4_0=='/') ) {
	    int LA4_44 = input.LA(2);

	    if ( (LA4_44=='/') ) {
		alt4=47;
	    }
	    else {
		alt4=60;}
	}
	else if ( (LA4_0=='{') ) {
	    int LA4_45 = input.LA(2);

	    if ( (LA4_45=='{') ) {
		int LA4_104 = input.LA(3);

		if ( (LA4_104=='{') ) {
		    alt4=48;
		}
		else {
		    alt4=52;}
	    }
	    else {
		alt4=62;}
	}
	else if ( (LA4_0=='}') ) {
	    int LA4_46 = input.LA(2);

	    if ( (LA4_46=='}') ) {
		int LA4_105 = input.LA(3);

		if ( (LA4_105=='}') ) {
		    alt4=49;
		}
		else {
		    alt4=53;}
	    }
	    else {
		alt4=62;}
	}
	else if ( (LA4_0=='[') ) {
	    int LA4_47 = input.LA(2);

	    if ( (LA4_47=='[') ) {
		alt4=50;
	    }
	    else {
		alt4=62;}
	}
	else if ( (LA4_0==']') ) {
	    int LA4_48 = input.LA(2);

	    if ( (LA4_48==']') ) {
		alt4=51;
	    }
	    else {
		alt4=62;}
	}
	else if ( (LA4_0=='\\') ) {
	    int LA4_49 = input.LA(2);

	    if ( (LA4_49=='\\') ) {
		alt4=54;
	    }
	    else {
		alt4=62;}
	}
	else if ( (LA4_0=='=') ) {
	    alt4=55;
	}
	else if ( (LA4_0=='|') ) {
	    alt4=56;
	}
	else if ( (LA4_0=='#') ) {
	    alt4=57;
	}
	else if ( (LA4_0=='-') ) {
	    alt4=58;
	}
	else if ( (LA4_0=='*') ) {
	    alt4=59;
	}
	else if ( (LA4_0=='@') ) {
	    int LA4_55 = input.LA(2);

	    if ( (LA4_55=='@') ) {
		alt4=61;
	    }
	    else {
		alt4=62;}
	}
	else if ( ((LA4_0>='\u0000' && LA4_0<='\b')||(LA4_0>='\u000B' && LA4_0<='\f')||(LA4_0>='\u000E' && LA4_0<='\u001F')||LA4_0=='!'||(LA4_0>='$' && LA4_0<=')')||(LA4_0>='+' && LA4_0<=',')||LA4_0=='.'||(LA4_0>='0' && LA4_0<='1')||(LA4_0>='3' && LA4_0<='9')||LA4_0==';'||LA4_0=='?'||(LA4_0>='A' && LA4_0<='B')||LA4_0=='E'||(LA4_0>='H' && LA4_0<='I')||(LA4_0>='K' && LA4_0<='L')||LA4_0=='N'||LA4_0=='Q'||LA4_0=='V'||(LA4_0>='Y' && LA4_0<='Z')||(LA4_0>='^' && LA4_0<='`')||LA4_0=='f'||LA4_0=='j'||LA4_0=='q'||(LA4_0>='v' && LA4_0<='w')||LA4_0=='z'||(LA4_0>='\u007F' && LA4_0<='\uFFFE')) ) {
	    alt4=62;
	}
	else {
	    NoViableAltException nvae =
		new NoViableAltException("1:1: Tokens : ( T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | T50 | T51 | T52 | T53 | T54 | T55 | T56 | T57 | T58 | T59 | T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | ESCAPE | NOWIKI_BLOCK_CLOSE | NEWLINE | BLANKS | BRACE_CLOSE | COLON_SLASH | ITAL | NOWIKI_OPEN | NOWIKI_CLOSE | LINK_OPEN | LINK_CLOSE | IMAGE_OPEN | IMAGE_CLOSE | FORCED_LINEBREAK | EQUAL | PIPE | POUND | DASH | STAR | SLASH | EXTENSION | INSIGNIFICANT_CHAR );", 4, 0, input);

	    throw nvae;
	}
	switch (alt4) {
	    case 1 :
		// Creole10.g:1:10: T41
		{
		mT41(); 

		}
		break;
	    case 2 :
		// Creole10.g:1:14: T42
		{
		mT42(); 

		}
		break;
	    case 3 :
		// Creole10.g:1:18: T43
		{
		mT43(); 

		}
		break;
	    case 4 :
		// Creole10.g:1:22: T44
		{
		mT44(); 

		}
		break;
	    case 5 :
		// Creole10.g:1:26: T45
		{
		mT45(); 

		}
		break;
	    case 6 :
		// Creole10.g:1:30: T46
		{
		mT46(); 

		}
		break;
	    case 7 :
		// Creole10.g:1:34: T47
		{
		mT47(); 

		}
		break;
	    case 8 :
		// Creole10.g:1:38: T48
		{
		mT48(); 

		}
		break;
	    case 9 :
		// Creole10.g:1:42: T49
		{
		mT49(); 

		}
		break;
	    case 10 :
		// Creole10.g:1:46: T50
		{
		mT50(); 

		}
		break;
	    case 11 :
		// Creole10.g:1:50: T51
		{
		mT51(); 

		}
		break;
	    case 12 :
		// Creole10.g:1:54: T52
		{
		mT52(); 

		}
		break;
	    case 13 :
		// Creole10.g:1:58: T53
		{
		mT53(); 

		}
		break;
	    case 14 :
		// Creole10.g:1:62: T54
		{
		mT54(); 

		}
		break;
	    case 15 :
		// Creole10.g:1:66: T55
		{
		mT55(); 

		}
		break;
	    case 16 :
		// Creole10.g:1:70: T56
		{
		mT56(); 

		}
		break;
	    case 17 :
		// Creole10.g:1:74: T57
		{
		mT57(); 

		}
		break;
	    case 18 :
		// Creole10.g:1:78: T58
		{
		mT58(); 

		}
		break;
	    case 19 :
		// Creole10.g:1:82: T59
		{
		mT59(); 

		}
		break;
	    case 20 :
		// Creole10.g:1:86: T60
		{
		mT60(); 

		}
		break;
	    case 21 :
		// Creole10.g:1:90: T61
		{
		mT61(); 

		}
		break;
	    case 22 :
		// Creole10.g:1:94: T62
		{
		mT62(); 

		}
		break;
	    case 23 :
		// Creole10.g:1:98: T63
		{
		mT63(); 

		}
		break;
	    case 24 :
		// Creole10.g:1:102: T64
		{
		mT64(); 

		}
		break;
	    case 25 :
		// Creole10.g:1:106: T65
		{
		mT65(); 

		}
		break;
	    case 26 :
		// Creole10.g:1:110: T66
		{
		mT66(); 

		}
		break;
	    case 27 :
		// Creole10.g:1:114: T67
		{
		mT67(); 

		}
		break;
	    case 28 :
		// Creole10.g:1:118: T68
		{
		mT68(); 

		}
		break;
	    case 29 :
		// Creole10.g:1:122: T69
		{
		mT69(); 

		}
		break;
	    case 30 :
		// Creole10.g:1:126: T70
		{
		mT70(); 

		}
		break;
	    case 31 :
		// Creole10.g:1:130: T71
		{
		mT71(); 

		}
		break;
	    case 32 :
		// Creole10.g:1:134: T72
		{
		mT72(); 

		}
		break;
	    case 33 :
		// Creole10.g:1:138: T73
		{
		mT73(); 

		}
		break;
	    case 34 :
		// Creole10.g:1:142: T74
		{
		mT74(); 

		}
		break;
	    case 35 :
		// Creole10.g:1:146: T75
		{
		mT75(); 

		}
		break;
	    case 36 :
		// Creole10.g:1:150: T76
		{
		mT76(); 

		}
		break;
	    case 37 :
		// Creole10.g:1:154: T77
		{
		mT77(); 

		}
		break;
	    case 38 :
		// Creole10.g:1:158: T78
		{
		mT78(); 

		}
		break;
	    case 39 :
		// Creole10.g:1:162: T79
		{
		mT79(); 

		}
		break;
	    case 40 :
		// Creole10.g:1:166: T80
		{
		mT80(); 

		}
		break;
	    case 41 :
		// Creole10.g:1:170: ESCAPE
		{
		mESCAPE(); 

		}
		break;
	    case 42 :
		// Creole10.g:1:177: NOWIKI_BLOCK_CLOSE
		{
		mNOWIKI_BLOCK_CLOSE(); 

		}
		break;
	    case 43 :
		// Creole10.g:1:196: NEWLINE
		{
		mNEWLINE(); 

		}
		break;
	    case 44 :
		// Creole10.g:1:204: BLANKS
		{
		mBLANKS(); 

		}
		break;
	    case 45 :
		// Creole10.g:1:211: BRACE_CLOSE
		{
		mBRACE_CLOSE(); 

		}
		break;
	    case 46 :
		// Creole10.g:1:223: COLON_SLASH
		{
		mCOLON_SLASH(); 

		}
		break;
	    case 47 :
		// Creole10.g:1:235: ITAL
		{
		mITAL(); 

		}
		break;
	    case 48 :
		// Creole10.g:1:240: NOWIKI_OPEN
		{
		mNOWIKI_OPEN(); 

		}
		break;
	    case 49 :
		// Creole10.g:1:252: NOWIKI_CLOSE
		{
		mNOWIKI_CLOSE(); 

		}
		break;
	    case 50 :
		// Creole10.g:1:265: LINK_OPEN
		{
		mLINK_OPEN(); 

		}
		break;
	    case 51 :
		// Creole10.g:1:275: LINK_CLOSE
		{
		mLINK_CLOSE(); 

		}
		break;
	    case 52 :
		// Creole10.g:1:286: IMAGE_OPEN
		{
		mIMAGE_OPEN(); 

		}
		break;
	    case 53 :
		// Creole10.g:1:297: IMAGE_CLOSE
		{
		mIMAGE_CLOSE(); 

		}
		break;
	    case 54 :
		// Creole10.g:1:309: FORCED_LINEBREAK
		{
		mFORCED_LINEBREAK(); 

		}
		break;
	    case 55 :
		// Creole10.g:1:326: EQUAL
		{
		mEQUAL(); 

		}
		break;
	    case 56 :
		// Creole10.g:1:332: PIPE
		{
		mPIPE(); 

		}
		break;
	    case 57 :
		// Creole10.g:1:337: POUND
		{
		mPOUND(); 

		}
		break;
	    case 58 :
		// Creole10.g:1:343: DASH
		{
		mDASH(); 

		}
		break;
	    case 59 :
		// Creole10.g:1:348: STAR
		{
		mSTAR(); 

		}
		break;
	    case 60 :
		// Creole10.g:1:353: SLASH
		{
		mSLASH(); 

		}
		break;
	    case 61 :
		// Creole10.g:1:359: EXTENSION
		{
		mEXTENSION(); 

		}
		break;
	    case 62 :
		// Creole10.g:1:369: INSIGNIFICANT_CHAR
		{
		mINSIGNIFICANT_CHAR(); 

		}
		break;

	}

    }


 

}
