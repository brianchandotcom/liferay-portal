// $ANTLR 3.0.1 /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g 2011-08-19 12:28:58

/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.parsers.creole.ast.ASTNode;
import com.liferay.portal.parsers.creole.ast.BoldTextNode;
import com.liferay.portal.parsers.creole.ast.CollectionNode;
import com.liferay.portal.parsers.creole.ast.extension.TableOfContentsNode;
import com.liferay.portal.parsers.creole.ast.ForcedEndOfLineNode;
import com.liferay.portal.parsers.creole.ast.FormattedTextNode;
import com.liferay.portal.parsers.creole.ast.HeadingNode;
import com.liferay.portal.parsers.creole.ast.HorizontalNode;
import com.liferay.portal.parsers.creole.ast.ImageNode;
import com.liferay.portal.parsers.creole.ast.ItalicTextNode;
import com.liferay.portal.parsers.creole.ast.LineNode;
import com.liferay.portal.parsers.creole.ast.link.InterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.LinkNode;
import com.liferay.portal.parsers.creole.ast.NoWikiSectionNode;
import com.liferay.portal.parsers.creole.ast.OrderedListItemNode;
import com.liferay.portal.parsers.creole.ast.OrderedListNode;
import com.liferay.portal.parsers.creole.ast.ParagraphNode;
import com.liferay.portal.parsers.creole.ast.ScapedNode;
import com.liferay.portal.parsers.creole.ast.table.TableCellNode;
import com.liferay.portal.parsers.creole.ast.table.TableDataNode;
import com.liferay.portal.parsers.creole.ast.table.TableHeaderNode;
import com.liferay.portal.parsers.creole.ast.table.TableNode;
import com.liferay.portal.parsers.creole.ast.UnorderedListItemNode;
import com.liferay.portal.parsers.creole.ast.UnorderedListNode;
import com.liferay.portal.parsers.creole.ast.UnformattedTextNode;
import com.liferay.portal.parsers.creole.ast.WikiPageNode;

/**
* This is a generated file from Creole10.g. DO NOT MODIFY THIS FILE MANUALLY!!
* If needed, modify the grammar and rerun the ant generation task 
* (ant build-creole-parser)
*/


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class Creole10Parser extends Parser {
	public static final String[] tokenNames = new String[] {
	"<invalid>", "<EOR>", "<DOWN>", "<UP>", "FORCED_END_OF_LINE", "HEADING_SECTION", "HORIZONTAL_SECTION", "LIST_ITEM", "LIST_ITEM_PART", "NOWIKI_SECTION", "SCAPE_NODE", "TEXT_NODE", "UNORDERED_LIST", "UNFORMATTED_TEXT", "WIKI", "NEWLINE", "POUND", "STAR", "EQUAL", "PIPE", "ITAL", "LINK_OPEN", "IMAGE_OPEN", "NOWIKI_OPEN", "EXTENSION", "FORCED_LINEBREAK", "ESCAPE", "NOWIKI_BLOCK_CLOSE", "NOWIKI_CLOSE", "LINK_CLOSE", "IMAGE_CLOSE", "BLANKS", "TABLE_OF_CONTENTS_TEXT", "DASH", "CR", "LF", "SPACE", "TABULATOR", "COLON_SLASH", "SLASH", "TABLE_OF_CONTENTS_OPEN_MARKUP", "TABLE_OF_CONTENTS_CLOSE_MARKUP", "INSIGNIFICANT_CHAR", "':'", "'C'", "'2'", "'D'", "'o'", "'k'", "'u'", "'W'", "'i'", "'F'", "'l'", "'c'", "'r'", "'G'", "'g'", "'e'", "'J'", "'S'", "'P'", "'M'", "'a'", "'t'", "'b'", "'d'", "'n'", "'O'", "'m'", "'s'", "'h'", "'p'", "'R'", "'x'", "'T'", "'y'", "'U'", "'X'"
	};
	public static final int BLANKS=31;
	public static final int INSIGNIFICANT_CHAR=42;
	public static final int FORCED_LINEBREAK=25;
	public static final int UNORDERED_LIST=12;
	public static final int STAR=17;
	public static final int DASH=33;
	public static final int POUND=16;
	public static final int HEADING_SECTION=5;
	public static final int NOWIKI_OPEN=23;
	public static final int FORCED_END_OF_LINE=4;
	public static final int TABLE_OF_CONTENTS_TEXT=32;
	public static final int HORIZONTAL_SECTION=6;
	public static final int NOWIKI_BLOCK_CLOSE=27;
	public static final int UNFORMATTED_TEXT=13;
	public static final int NOWIKI_SECTION=9;
	public static final int SPACE=36;
	public static final int NOWIKI_CLOSE=28;
	public static final int IMAGE_OPEN=22;
	public static final int ITAL=20;
	public static final int EOF=-1;
	public static final int COLON_SLASH=38;
	public static final int LIST_ITEM=7;
	public static final int TEXT_NODE=11;
	public static final int WIKI=14;
	public static final int SLASH=39;
	public static final int ESCAPE=26;
	public static final int NEWLINE=15;
	public static final int SCAPE_NODE=10;
	public static final int IMAGE_CLOSE=30;
	public static final int EQUAL=18;
	public static final int TABLE_OF_CONTENTS_CLOSE_MARKUP=41;
	public static final int TABULATOR=37;
	public static final int LINK_CLOSE=29;
	public static final int LIST_ITEM_PART=8;
	public static final int PIPE=19;
	public static final int LINK_OPEN=21;
	public static final int TABLE_OF_CONTENTS_OPEN_MARKUP=40;
	public static final int CR=34;
	public static final int EXTENSION=24;
	public static final int LF=35;
	protected static class CountLevel_scope {
	int level;
	String currentMarkup;
	String groups;
	}
	protected Stack CountLevel_stack = new Stack();


	public Creole10Parser(TokenStream input) {
		super(input);
		ruleMemo = new HashMap[129+1];
	 }
	

	public String[] getTokenNames() { return tokenNames; }
	public String getGrammarFileName() { return "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g"; }


	protected static final String GROUPING_SEPARATOR = "-";

	private WikiPageNode _wikipage = null;
	
	public WikiPageNode getWikiPageNode() {
		if(_wikipage == null)
			throw new IllegalStateException("No succesful parsing process");
		
		return _wikipage;
	}



	// $ANTLR start wikipage
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:111:1: wikipage : ( whitespaces )? p= paragraphs EOF ;
	public final void wikipage() throws RecognitionException {
	CollectionNode p = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:112:2: ( ( whitespaces )? p= paragraphs EOF )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:112:4: ( whitespaces )? p= paragraphs EOF
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:112:4: ( whitespaces )?
		int alt1=2;
		int LA1_0 = input.LA(1);

		if ( (LA1_0==NEWLINE||LA1_0==BLANKS) ) {
		alt1=1;
		}
		switch (alt1) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:112:6: whitespaces
			{
			pushFollow(FOLLOW_whitespaces_in_wikipage118);
			whitespaces();
			_fsp--;
			if (failed) return ;

			}
			break;

		}

		pushFollow(FOLLOW_paragraphs_in_wikipage126);
		p=paragraphs();
		_fsp--;
		if (failed) return ;
		if ( backtracking==0 ) {
		   _wikipage = new WikiPageNode(p); 
		}
		match(input,EOF,FOLLOW_EOF_in_wikipage131); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end wikipage


	// $ANTLR start paragraphs
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:114:1: paragraphs returns [CollectionNode sections = new CollectionNode()] : (p= paragraph )* ;
	public final CollectionNode paragraphs() throws RecognitionException {
	CollectionNode sections =  new CollectionNode();

	ASTNode p = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:115:2: ( (p= paragraph )* )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:115:4: (p= paragraph )*
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:115:4: (p= paragraph )*
		loop2:
		do {
		int alt2=2;
		int LA2_0 = input.LA(1);

		if ( ((LA2_0>=FORCED_END_OF_LINE && LA2_0<=WIKI)||(LA2_0>=POUND && LA2_0<=78)) ) {
			alt2=1;
		}


		switch (alt2) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:115:5: p= paragraph
			{
			pushFollow(FOLLOW_paragraph_in_paragraphs149);
			p=paragraph();
			_fsp--;
			if (failed) return sections;
			if ( backtracking==0 ) {

					if(p != null){ // at this moment we ignore paragraps with blanks
						sections.add(p);
					}
					
			}

			}
			break;

		default :
			break loop2;
		}
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return sections;
	}
	// $ANTLR end paragraphs


	// $ANTLR start paragraph
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:121:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )? );
	public final ASTNode paragraph() throws RecognitionException {
	ASTNode node =	null;

	NoWikiSectionNode n = null;

	ASTNode tof = null;

	ASTNode h = null;

	ASTNode hn = null;

	UnorderedListNode lu = null;

	OrderedListNode lo = null;

	TableNode t = null;

	ParagraphNode tp = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:122:2: (n= nowiki_block | blanks paragraph_separator | ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )? )
		int alt6=3;
		switch ( input.LA(1) ) {
		case NOWIKI_OPEN:
		{
		int LA6_1 = input.LA(2);

		if ( (LA6_1==NEWLINE) ) {
			alt6=1;
		}
		else if ( ((LA6_1>=FORCED_END_OF_LINE && LA6_1<=WIKI)||(LA6_1>=POUND && LA6_1<=78)) ) {
			alt6=3;
		}
		else {
			if (backtracking>0) {failed=true; return node;}
			NoViableAltException nvae =
			new NoViableAltException("121:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )? );", 6, 1, input);

			throw nvae;
		}
		}
		break;
		case BLANKS:
		{
		switch ( input.LA(2) ) {
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case STAR:
		case EQUAL:
		case PIPE:
		case ITAL:
		case LINK_OPEN:
		case IMAGE_OPEN:
		case NOWIKI_OPEN:
		case EXTENSION:
		case FORCED_LINEBREAK:
		case ESCAPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
			{
			alt6=3;
			}
			break;
		case NEWLINE:
			{
			alt6=2;
			}
			break;
		case EOF:
			{
			alt6=2;
			}
			break;
		default:
			if (backtracking>0) {failed=true; return node;}
			NoViableAltException nvae =
			new NoViableAltException("121:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )? );", 6, 2, input);

			throw nvae;
		}

		}
		break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case STAR:
		case EQUAL:
		case PIPE:
		case ITAL:
		case LINK_OPEN:
		case IMAGE_OPEN:
		case EXTENSION:
		case FORCED_LINEBREAK:
		case ESCAPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
		{
		alt6=3;
		}
		break;
		default:
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
			new NoViableAltException("121:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )? );", 6, 0, input);

		throw nvae;
		}

		switch (alt6) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:122:4: n= nowiki_block
			{
			pushFollow(FOLLOW_nowiki_block_in_paragraph170);
			n=nowiki_block();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			   node = n; 
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:123:4: blanks paragraph_separator
			{
			pushFollow(FOLLOW_blanks_in_paragraph177);
			blanks();
			_fsp--;
			if (failed) return node;
			pushFollow(FOLLOW_paragraph_separator_in_paragraph180);
			paragraph_separator();
			_fsp--;
			if (failed) return node;

			}
			break;
		case 3 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:124:4: ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )?
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:124:4: ( blanks )?
			int alt3=2;
			int LA3_0 = input.LA(1);

			if ( (LA3_0==BLANKS) ) {
			alt3=1;
			}
			switch (alt3) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:124:6: blanks
				{
				pushFollow(FOLLOW_blanks_in_paragraph187);
				blanks();
				_fsp--;
				if (failed) return node;

				}
				break;

			}

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:125:4: (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph )
			int alt4=7;
			switch ( input.LA(1) ) {
			case TABLE_OF_CONTENTS_TEXT:
			{
			alt4=1;
			}
			break;
			case EQUAL:
			{
			alt4=2;
			}
			break;
			case DASH:
			{
			int LA4_3 = input.LA(2);

			if ( ( input.LA(1) == DASH && input.LA(2) == DASH &&
							input.LA(3) == DASH && input.LA(4) == DASH ) ) {
				alt4=3;
			}
			else if ( (true) ) {
				alt4=7;
			}
			else {
				if (backtracking>0) {failed=true; return node;}
				NoViableAltException nvae =
				new NoViableAltException("125:4: (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph )", 4, 3, input);

				throw nvae;
			}
			}
			break;
			case STAR:
			{
			int LA4_4 = input.LA(2);

			if ( (!( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) )) ) {
				alt4=4;
			}
			else if ( ( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ) ) {
				alt4=7;
			}
			else {
				if (backtracking>0) {failed=true; return node;}
				NoViableAltException nvae =
				new NoViableAltException("125:4: (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph )", 4, 4, input);

				throw nvae;
			}
			}
			break;
			case POUND:
			{
			alt4=5;
			}
			break;
			case PIPE:
			{
			alt4=6;
			}
			break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case ITAL:
			case LINK_OPEN:
			case IMAGE_OPEN:
			case NOWIKI_OPEN:
			case EXTENSION:
			case FORCED_LINEBREAK:
			case ESCAPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
			{
			alt4=7;
			}
			break;
			default:
			if (backtracking>0) {failed=true; return node;}
			NoViableAltException nvae =
				new NoViableAltException("125:4: (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph )", 4, 0, input);

			throw nvae;
			}

			switch (alt4) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:125:6: tof= table_of_contents
				{
				pushFollow(FOLLOW_table_of_contents_in_paragraph201);
				tof=table_of_contents();
				_fsp--;
				if (failed) return node;
				if ( backtracking==0 ) {
				  node = tof;
				}

				}
				break;
			case 2 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:126:6: h= heading
				{
				pushFollow(FOLLOW_heading_in_paragraph218);
				h=heading();
				_fsp--;
				if (failed) return node;
				if ( backtracking==0 ) {
				   node = h;
				}

				}
				break;
			case 3 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:127:6: {...}?hn= horizontalrule
				{
				if ( !( input.LA(1) == DASH && input.LA(2) == DASH &&
							input.LA(3) == DASH && input.LA(4) == DASH ) ) {
				if (backtracking>0) {failed=true; return node;}
				throw new FailedPredicateException(input, "paragraph", " input.LA(1) == DASH && input.LA(2) == DASH &&\n\t\t\t\tinput.LA(3) == DASH && input.LA(4) == DASH ");
				}
				pushFollow(FOLLOW_horizontalrule_in_paragraph237);
				hn=horizontalrule();
				_fsp--;
				if (failed) return node;
				if ( backtracking==0 ) {
				  node = hn;
				}

				}
				break;
			case 4 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:130:6: lu= list_unord
				{
				pushFollow(FOLLOW_list_unord_in_paragraph249);
				lu=list_unord();
				_fsp--;
				if (failed) return node;
				if ( backtracking==0 ) {
				  node = lu;
				}

				}
				break;
			case 5 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:131:6: lo= list_ord
				{
				pushFollow(FOLLOW_list_ord_in_paragraph262);
				lo=list_ord();
				_fsp--;
				if (failed) return node;
				if ( backtracking==0 ) {
				  node = lo;
				}

				}
				break;
			case 6 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:132:6: t= table
				{
				pushFollow(FOLLOW_table_in_paragraph275);
				t=table();
				_fsp--;
				if (failed) return node;
				if ( backtracking==0 ) {
				   node = t; 
				}

				}
				break;
			case 7 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:133:6: tp= text_paragraph
				{
				pushFollow(FOLLOW_text_paragraph_in_paragraph288);
				tp=text_paragraph();
				_fsp--;
				if (failed) return node;
				if ( backtracking==0 ) {
				  node = tp; 
				}

				}
				break;

			}

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:134:7: ( paragraph_separator )?
			int alt5=2;
			int LA5_0 = input.LA(1);

			if ( (LA5_0==NEWLINE) ) {
			alt5=1;
			}
			else if ( (LA5_0==EOF) ) {
			int LA5_2 = input.LA(2);

			if ( (LA5_2==EOF) ) {
				int LA5_4 = input.LA(3);

				if ( (LA5_4==EOF) ) {
				alt5=1;
				}
			}
			else if ( ((LA5_2>=FORCED_END_OF_LINE && LA5_2<=WIKI)||(LA5_2>=POUND && LA5_2<=78)) ) {
				alt5=1;
			}
			}
			switch (alt5) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:134:9: paragraph_separator
				{
				pushFollow(FOLLOW_paragraph_separator_in_paragraph301);
				paragraph_separator();
				_fsp--;
				if (failed) return node;

				}
				break;

			}


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end paragraph


	// $ANTLR start text_paragraph
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:141:1: text_paragraph returns [ ParagraphNode paragraph = new ParagraphNode() ] : (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+ ;
	public final ParagraphNode text_paragraph() throws RecognitionException {
	ParagraphNode paragraph =  new ParagraphNode();

	LineNode tl = null;

	NoWikiSectionNode nw = null;

	ASTNode te = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:142:2: ( (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:142:4: (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:142:4: (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+
		int cnt8=0;
		loop8:
		do {
		int alt8=3;
		switch ( input.LA(1) ) {
		case NOWIKI_OPEN:
			{
			int LA8_2 = input.LA(2);

			if ( (synpred1()) ) {
			alt8=2;
			}


			}
			break;
		case BLANKS:
			{
			alt8=1;
			}
			break;
		case TABLE_OF_CONTENTS_TEXT:
			{
			alt8=1;
			}
			break;
		case DASH:
			{
			alt8=1;
			}
			break;
		case STAR:
			{
			int LA8_6 = input.LA(2);

			if ( ( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ) ) {
			alt8=1;
			}


			}
			break;
		case ITAL:
			{
			int LA8_7 = input.LA(2);

			if ( ( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ) ) {
			alt8=1;
			}


			}
			break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
			{
			alt8=1;
			}
			break;
		case FORCED_LINEBREAK:
			{
			alt8=1;
			}
			break;
		case ESCAPE:
			{
			alt8=1;
			}
			break;
		case LINK_OPEN:
			{
			alt8=1;
			}
			break;
		case IMAGE_OPEN:
			{
			alt8=1;
			}
			break;
		case EXTENSION:
			{
			alt8=1;
			}
			break;

		}

		switch (alt8) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:142:6: tl= text_line
			{
			pushFollow(FOLLOW_text_line_in_text_paragraph329);
			tl=text_line();
			_fsp--;
			if (failed) return paragraph;
			if ( backtracking==0 ) {
			paragraph.addChildASTNode(tl);	
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:143:5: ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator
			{
			pushFollow(FOLLOW_nowiki_inline_in_text_paragraph361);
			nw=nowiki_inline();
			_fsp--;
			if (failed) return paragraph;
			if ( backtracking==0 ) {
			  paragraph.addChildASTNode(nw);
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:144:66: (te= text_element )*
			loop7:
			do {
			int alt7=2;
			int LA7_0 = input.LA(1);

			if ( ((LA7_0>=FORCED_END_OF_LINE && LA7_0<=WIKI)||(LA7_0>=POUND && LA7_0<=78)) ) {
				alt7=1;
			}


			switch (alt7) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:144:68: te= text_element
				{
				pushFollow(FOLLOW_text_element_in_text_paragraph372);
				te=text_element();
				_fsp--;
				if (failed) return paragraph;
				if ( backtracking==0 ) {
				  paragraph.addChildASTNode(te);
				}

				}
				break;

			default :
				break loop7;
			}
			} while (true);

			pushFollow(FOLLOW_text_lineseparator_in_text_paragraph381);
			text_lineseparator();
			_fsp--;
			if (failed) return paragraph;

			}
			break;

		default :
			if ( cnt8 >= 1 ) break loop8;
			if (backtracking>0) {failed=true; return paragraph;}
			EarlyExitException eee =
				new EarlyExitException(8, input);
			throw eee;
		}
		cnt8++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return paragraph;
	}
	// $ANTLR end text_paragraph


	// $ANTLR start text_line
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:147:1: text_line returns [LineNode line = new LineNode()] : first= text_firstelement (element= text_element )* text_lineseparator ;
	public final LineNode text_line() throws RecognitionException {
	LineNode line =  new LineNode();

	ASTNode first = null;

	ASTNode element = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:148:2: (first= text_firstelement (element= text_element )* text_lineseparator )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:148:4: first= text_firstelement (element= text_element )* text_lineseparator
		{
		pushFollow(FOLLOW_text_firstelement_in_text_line404);
		first=text_firstelement();
		_fsp--;
		if (failed) return line;
		if ( backtracking==0 ) {

											if (first != null) { // recovering from errors
												line.addChildASTNode(first);
											}
										
		}
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:153:9: (element= text_element )*
		loop9:
		do {
		int alt9=2;
		int LA9_0 = input.LA(1);

		if ( ((LA9_0>=FORCED_END_OF_LINE && LA9_0<=WIKI)||(LA9_0>=POUND && LA9_0<=78)) ) {
			alt9=1;
		}


		switch (alt9) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:153:11: element= text_element
			{
			pushFollow(FOLLOW_text_element_in_text_line423);
			element=text_element();
			_fsp--;
			if (failed) return line;
			if ( backtracking==0 ) {

										if(element != null) // recovering from errors
											line.addChildASTNode(element);
									
			}

			}
			break;

		default :
			break loop9;
		}
		} while (true);

		pushFollow(FOLLOW_text_lineseparator_in_text_line438);
		text_lineseparator();
		_fsp--;
		if (failed) return line;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return line;
	}
	// $ANTLR end text_line


	// $ANTLR start text_firstelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:159:1: text_firstelement returns [ASTNode item = null] : ({...}?tf= text_formattedelement | tu= text_first_unformattedelement );
	public final ASTNode text_firstelement() throws RecognitionException {
	ASTNode item =	null;

	FormattedTextNode tf = null;

	ASTNode tu = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:160:2: ({...}?tf= text_formattedelement | tu= text_first_unformattedelement )
		int alt10=2;
		int LA10_0 = input.LA(1);

		if ( (LA10_0==STAR||LA10_0==ITAL) ) {
		alt10=1;
		}
		else if ( ((LA10_0>=FORCED_END_OF_LINE && LA10_0<=WIKI)||(LA10_0>=LINK_OPEN && LA10_0<=IMAGE_OPEN)||(LA10_0>=EXTENSION && LA10_0<=78)) ) {
		alt10=2;
		}
		else {
		if (backtracking>0) {failed=true; return item;}
		NoViableAltException nvae =
			new NoViableAltException("159:1: text_firstelement returns [ASTNode item = null] : ({...}?tf= text_formattedelement | tu= text_first_unformattedelement );", 10, 0, input);

		throw nvae;
		}
		switch (alt10) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:160:4: {...}?tf= text_formattedelement
			{
			if ( !( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ) ) {
			if (backtracking>0) {failed=true; return item;}
			throw new FailedPredicateException(input, "text_firstelement", " input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ");
			}
			pushFollow(FOLLOW_text_formattedelement_in_text_firstelement460);
			tf=text_formattedelement();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			   item = tf; 
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:162:4: tu= text_first_unformattedelement
			{
			pushFollow(FOLLOW_text_first_unformattedelement_in_text_firstelement471);
			tu=text_first_unformattedelement();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			   item = tu; 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return item;
	}
	// $ANTLR end text_firstelement


	// $ANTLR start text_formattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:164:1: text_formattedelement returns [FormattedTextNode item = null] : ( ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )? | bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )? );
	public final FormattedTextNode text_formattedelement() throws RecognitionException {
	FormattedTextNode item =  null;

	CollectionNode ic = null;

	CollectionNode bc = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:165:2: ( ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )? | bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )? )
		int alt15=2;
		int LA15_0 = input.LA(1);

		if ( (LA15_0==ITAL) ) {
		alt15=1;
		}
		else if ( (LA15_0==STAR) ) {
		alt15=2;
		}
		else {
		if (backtracking>0) {failed=true; return item;}
		NoViableAltException nvae =
			new NoViableAltException("164:1: text_formattedelement returns [FormattedTextNode item = null] : ( ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )? | bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )? );", 15, 0, input);

		throw nvae;
		}
		switch (alt15) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:165:4: ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )?
			{
			pushFollow(FOLLOW_ital_markup_in_text_formattedelement487);
			ital_markup();
			_fsp--;
			if (failed) return item;
			pushFollow(FOLLOW_text_italcontent_in_text_formattedelement493);
			ic=text_italcontent();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			   item = new ItalicTextNode(ic); 
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:165:81: ( ( NEWLINE )? ital_markup )?
			int alt12=2;
			int LA12_0 = input.LA(1);

			if ( (LA12_0==NEWLINE) ) {
			int LA12_1 = input.LA(2);

			if ( (LA12_1==ITAL) ) {
				alt12=1;
			}
			}
			else if ( (LA12_0==ITAL) ) {
			alt12=1;
			}
			switch (alt12) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:165:83: ( NEWLINE )? ital_markup
				{
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:165:83: ( NEWLINE )?
				int alt11=2;
				int LA11_0 = input.LA(1);

				if ( (LA11_0==NEWLINE) ) {
				alt11=1;
				}
				switch (alt11) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:165:85: NEWLINE
					{
					match(input,NEWLINE,FOLLOW_NEWLINE_in_text_formattedelement502); if (failed) return item;

					}
					break;

				}

				pushFollow(FOLLOW_ital_markup_in_text_formattedelement508);
				ital_markup();
				_fsp--;
				if (failed) return item;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:166:4: bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )?
			{
			pushFollow(FOLLOW_bold_markup_in_text_formattedelement516);
			bold_markup();
			_fsp--;
			if (failed) return item;
			pushFollow(FOLLOW_text_boldcontent_in_text_formattedelement523);
			bc=text_boldcontent();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			  item = new BoldTextNode(bc); 
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:166:79: ( ( NEWLINE )? bold_markup )?
			int alt14=2;
			int LA14_0 = input.LA(1);

			if ( (LA14_0==NEWLINE) ) {
			int LA14_1 = input.LA(2);

			if ( (LA14_1==STAR) ) {
				int LA14_4 = input.LA(3);

				if ( (LA14_4==STAR) ) {
				alt14=1;
				}
			}
			}
			else if ( (LA14_0==STAR) ) {
			int LA14_2 = input.LA(2);

			if ( (LA14_2==STAR) ) {
				alt14=1;
			}
			}
			switch (alt14) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:166:81: ( NEWLINE )? bold_markup
				{
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:166:81: ( NEWLINE )?
				int alt13=2;
				int LA13_0 = input.LA(1);

				if ( (LA13_0==NEWLINE) ) {
				alt13=1;
				}
				switch (alt13) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:166:83: NEWLINE
					{
					match(input,NEWLINE,FOLLOW_NEWLINE_in_text_formattedelement532); if (failed) return item;

					}
					break;

				}

				pushFollow(FOLLOW_bold_markup_in_text_formattedelement538);
				bold_markup();
				_fsp--;
				if (failed) return item;

				}
				break;

			}


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return item;
	}
	// $ANTLR end text_formattedelement


	// $ANTLR start text_boldcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:168:1: text_boldcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_boldcontentpart )* | EOF );
	public final CollectionNode text_boldcontent() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	FormattedTextNode p = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:169:2: ( ( NEWLINE )? (p= text_boldcontentpart )* | EOF )
		int alt18=2;
		int LA18_0 = input.LA(1);

		if ( ((LA18_0>=FORCED_END_OF_LINE && LA18_0<=78)) ) {
		alt18=1;
		}
		else if ( (LA18_0==EOF) ) {
		alt18=1;
		}
		else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
			new NoViableAltException("168:1: text_boldcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_boldcontentpart )* | EOF );", 18, 0, input);

		throw nvae;
		}
		switch (alt18) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:169:4: ( NEWLINE )? (p= text_boldcontentpart )*
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:169:4: ( NEWLINE )?
			int alt16=2;
			int LA16_0 = input.LA(1);

			if ( (LA16_0==NEWLINE) ) {
			alt16=1;
			}
			switch (alt16) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:169:6: NEWLINE
				{
				match(input,NEWLINE,FOLLOW_NEWLINE_in_text_boldcontent557); if (failed) return text;

				}
				break;

			}

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:169:18: (p= text_boldcontentpart )*
			loop17:
			do {
			int alt17=2;
			switch ( input.LA(1) ) {
			case STAR:
				{
				int LA17_2 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
				alt17=1;
				}


				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case PIPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt17=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt17=1;
				}
				break;
			case ESCAPE:
				{
				alt17=1;
				}
				break;
			case LINK_OPEN:
				{
				alt17=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt17=1;
				}
				break;
			case EXTENSION:
				{
				alt17=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt17=1;
				}
				break;
			case ITAL:
				{
				alt17=1;
				}
				break;

			}

			switch (alt17) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:169:20: p= text_boldcontentpart
				{
				pushFollow(FOLLOW_text_boldcontentpart_in_text_boldcontent569);
				p=text_boldcontentpart();
				_fsp--;
				if (failed) return text;
				if ( backtracking==0 ) {
				   text.add(p); 
				}

				}
				break;

			default :
				break loop17;
			}
			} while (true);


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:170:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_text_boldcontent580); if (failed) return text;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end text_boldcontent


	// $ANTLR start text_italcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:172:1: text_italcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_italcontentpart )* | EOF );
	public final CollectionNode text_italcontent() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	FormattedTextNode p = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:173:2: ( ( NEWLINE )? (p= text_italcontentpart )* | EOF )
		int alt21=2;
		int LA21_0 = input.LA(1);

		if ( ((LA21_0>=FORCED_END_OF_LINE && LA21_0<=78)) ) {
		alt21=1;
		}
		else if ( (LA21_0==EOF) ) {
		alt21=1;
		}
		else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
			new NoViableAltException("172:1: text_italcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_italcontentpart )* | EOF );", 21, 0, input);

		throw nvae;
		}
		switch (alt21) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:173:4: ( NEWLINE )? (p= text_italcontentpart )*
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:173:4: ( NEWLINE )?
			int alt19=2;
			int LA19_0 = input.LA(1);

			if ( (LA19_0==NEWLINE) ) {
			alt19=1;
			}
			switch (alt19) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:173:6: NEWLINE
				{
				match(input,NEWLINE,FOLLOW_NEWLINE_in_text_italcontent596); if (failed) return text;

				}
				break;

			}

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:173:18: (p= text_italcontentpart )*
			loop20:
			do {
			int alt20=2;
			switch ( input.LA(1) ) {
			case STAR:
				{
				alt20=1;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case PIPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt20=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt20=1;
				}
				break;
			case ESCAPE:
				{
				alt20=1;
				}
				break;
			case LINK_OPEN:
				{
				alt20=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt20=1;
				}
				break;
			case EXTENSION:
				{
				alt20=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt20=1;
				}
				break;

			}

			switch (alt20) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:173:20: p= text_italcontentpart
				{
				pushFollow(FOLLOW_text_italcontentpart_in_text_italcontent608);
				p=text_italcontentpart();
				_fsp--;
				if (failed) return text;
				if ( backtracking==0 ) {
				   text.add(p); 
				}

				}
				break;

			default :
				break loop20;
			}
			} while (true);


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:174:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_text_italcontent619); if (failed) return text;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end text_italcontent


	// $ANTLR start text_element
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:176:1: text_element returns [ASTNode item = null] : ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement );
	public final ASTNode text_element() throws RecognitionException {
	ASTNode item =	null;

	ASTNode tu1 = null;

	ASTNode tu2 = null;

	FormattedTextNode tf = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:177:2: ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement )
		int alt22=3;
		switch ( input.LA(1) ) {
		case STAR:
		{
		int LA22_1 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt22=1;
		}
		else if ( (true) ) {
			alt22=3;
		}
		else {
			if (backtracking>0) {failed=true; return item;}
			NoViableAltException nvae =
			new NoViableAltException("176:1: text_element returns [ASTNode item = null] : ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement );", 22, 1, input);

			throw nvae;
		}
		}
		break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case EQUAL:
		case PIPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
		{
		alt22=1;
		}
		break;
		case FORCED_LINEBREAK:
		{
		alt22=1;
		}
		break;
		case ESCAPE:
		{
		alt22=1;
		}
		break;
		case LINK_OPEN:
		{
		alt22=1;
		}
		break;
		case IMAGE_OPEN:
		{
		alt22=1;
		}
		break;
		case EXTENSION:
		{
		alt22=1;
		}
		break;
		case NOWIKI_OPEN:
		{
		alt22=1;
		}
		break;
		case ITAL:
		{
		alt22=3;
		}
		break;
		default:
		if (backtracking>0) {failed=true; return item;}
		NoViableAltException nvae =
			new NoViableAltException("176:1: text_element returns [ASTNode item = null] : ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement );", 22, 0, input);

		throw nvae;
		}

		switch (alt22) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:177:4: onestar tu1= text_unformattedelement
			{
			pushFollow(FOLLOW_onestar_in_text_element634);
			onestar();
			_fsp--;
			if (failed) return item;
			pushFollow(FOLLOW_text_unformattedelement_in_text_element641);
			tu1=text_unformattedelement();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			   item = tu1; 
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:178:4: tu2= text_unformattedelement onestar
			{
			pushFollow(FOLLOW_text_unformattedelement_in_text_element652);
			tu2=text_unformattedelement();
			_fsp--;
			if (failed) return item;
			pushFollow(FOLLOW_onestar_in_text_element655);
			onestar();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			   item = tu2; 
			}

			}
			break;
		case 3 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:179:4: tf= text_formattedelement
			{
			pushFollow(FOLLOW_text_formattedelement_in_text_element666);
			tf=text_formattedelement();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			   item = tf; 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return item;
	}
	// $ANTLR end text_element


	// $ANTLR start text_boldcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:182:1: text_boldcontentpart returns [FormattedTextNode node = null] : ( ital_markup t= text_bolditalcontent ( ital_markup )? | tf= text_formattedcontent );
	public final FormattedTextNode text_boldcontentpart() throws RecognitionException {
	FormattedTextNode node =  null;

	ASTNode t = null;

	CollectionNode tf = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:183:2: ( ital_markup t= text_bolditalcontent ( ital_markup )? | tf= text_formattedcontent )
		int alt24=2;
		int LA24_0 = input.LA(1);

		if ( (LA24_0==ITAL) ) {
		alt24=1;
		}
		else if ( ((LA24_0>=FORCED_END_OF_LINE && LA24_0<=WIKI)||(LA24_0>=POUND && LA24_0<=PIPE)||(LA24_0>=LINK_OPEN && LA24_0<=78)) ) {
		alt24=2;
		}
		else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
			new NoViableAltException("182:1: text_boldcontentpart returns [FormattedTextNode node = null] : ( ital_markup t= text_bolditalcontent ( ital_markup )? | tf= text_formattedcontent );", 24, 0, input);

		throw nvae;
		}
		switch (alt24) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:183:4: ital_markup t= text_bolditalcontent ( ital_markup )?
			{
			pushFollow(FOLLOW_ital_markup_in_text_boldcontentpart683);
			ital_markup();
			_fsp--;
			if (failed) return node;
			pushFollow(FOLLOW_text_bolditalcontent_in_text_boldcontentpart690);
			t=text_bolditalcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node = new ItalicTextNode(t); 
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:183:84: ( ital_markup )?
			int alt23=2;
			int LA23_0 = input.LA(1);

			if ( (LA23_0==ITAL) ) {
			alt23=1;
			}
			switch (alt23) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:183:86: ital_markup
				{
				pushFollow(FOLLOW_ital_markup_in_text_boldcontentpart697);
				ital_markup();
				_fsp--;
				if (failed) return node;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:184:4: tf= text_formattedcontent
			{
			pushFollow(FOLLOW_text_formattedcontent_in_text_boldcontentpart709);
			tf=text_formattedcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node = new FormattedTextNode(tf); 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end text_boldcontentpart


	// $ANTLR start text_italcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:186:1: text_italcontentpart returns [FormattedTextNode node = null] : ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent );
	public final FormattedTextNode text_italcontentpart() throws RecognitionException {
	FormattedTextNode node =  null;

	ASTNode t = null;

	CollectionNode tf = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:187:2: ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent )
		int alt26=2;
		int LA26_0 = input.LA(1);

		if ( (LA26_0==STAR) ) {
		int LA26_1 = input.LA(2);

		if ( (LA26_1==STAR) ) {
			alt26=1;
		}
		else if ( ((LA26_1>=FORCED_END_OF_LINE && LA26_1<=WIKI)||LA26_1==POUND||(LA26_1>=EQUAL && LA26_1<=PIPE)||(LA26_1>=LINK_OPEN && LA26_1<=78)) ) {
			alt26=2;
		}
		else {
			if (backtracking>0) {failed=true; return node;}
			NoViableAltException nvae =
			new NoViableAltException("186:1: text_italcontentpart returns [FormattedTextNode node = null] : ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent );", 26, 1, input);

			throw nvae;
		}
		}
		else if ( ((LA26_0>=FORCED_END_OF_LINE && LA26_0<=WIKI)||LA26_0==POUND||(LA26_0>=EQUAL && LA26_0<=PIPE)||(LA26_0>=LINK_OPEN && LA26_0<=78)) ) {
		alt26=2;
		}
		else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
			new NoViableAltException("186:1: text_italcontentpart returns [FormattedTextNode node = null] : ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent );", 26, 0, input);

		throw nvae;
		}
		switch (alt26) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:187:4: bold_markup t= text_bolditalcontent ( bold_markup )?
			{
			pushFollow(FOLLOW_bold_markup_in_text_italcontentpart725);
			bold_markup();
			_fsp--;
			if (failed) return node;
			pushFollow(FOLLOW_text_bolditalcontent_in_text_italcontentpart732);
			t=text_bolditalcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			   node = new BoldTextNode(t); 
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:187:82: ( bold_markup )?
			int alt25=2;
			int LA25_0 = input.LA(1);

			if ( (LA25_0==STAR) ) {
			int LA25_1 = input.LA(2);

			if ( (LA25_1==STAR) ) {
				alt25=1;
			}
			}
			switch (alt25) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:187:84: bold_markup
				{
				pushFollow(FOLLOW_bold_markup_in_text_italcontentpart738);
				bold_markup();
				_fsp--;
				if (failed) return node;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:188:4: tf= text_formattedcontent
			{
			pushFollow(FOLLOW_text_formattedcontent_in_text_italcontentpart750);
			tf=text_formattedcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node = new FormattedTextNode(tf); 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end text_italcontentpart


	// $ANTLR start text_bolditalcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:190:1: text_bolditalcontent returns [ASTNode items = null] : ( ( NEWLINE )? (tf= text_formattedcontent )? | EOF );
	public final ASTNode text_bolditalcontent() throws RecognitionException {
	ASTNode items =  null;

	CollectionNode tf = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:191:2: ( ( NEWLINE )? (tf= text_formattedcontent )? | EOF )
		int alt29=2;
		int LA29_0 = input.LA(1);

		if ( ((LA29_0>=FORCED_END_OF_LINE && LA29_0<=78)) ) {
		alt29=1;
		}
		else if ( (LA29_0==EOF) ) {
		alt29=1;
		}
		else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
			new NoViableAltException("190:1: text_bolditalcontent returns [ASTNode items = null] : ( ( NEWLINE )? (tf= text_formattedcontent )? | EOF );", 29, 0, input);

		throw nvae;
		}
		switch (alt29) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:191:4: ( NEWLINE )? (tf= text_formattedcontent )?
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:191:4: ( NEWLINE )?
			int alt27=2;
			int LA27_0 = input.LA(1);

			if ( (LA27_0==NEWLINE) ) {
			alt27=1;
			}
			switch (alt27) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:191:6: NEWLINE
				{
				match(input,NEWLINE,FOLLOW_NEWLINE_in_text_bolditalcontent768); if (failed) return items;

				}
				break;

			}

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:191:18: (tf= text_formattedcontent )?
			int alt28=2;
			switch ( input.LA(1) ) {
			case STAR:
				{
				int LA28_1 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
				alt28=1;
				}
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case PIPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt28=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt28=1;
				}
				break;
			case ESCAPE:
				{
				alt28=1;
				}
				break;
			case LINK_OPEN:
				{
				alt28=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt28=1;
				}
				break;
			case EXTENSION:
				{
				alt28=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt28=1;
				}
				break;
			}

			switch (alt28) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:191:20: tf= text_formattedcontent
				{
				pushFollow(FOLLOW_text_formattedcontent_in_text_bolditalcontent779);
				tf=text_formattedcontent();
				_fsp--;
				if (failed) return items;
				if ( backtracking==0 ) {
				  items = tf; 
				}

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:192:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_text_bolditalcontent789); if (failed) return items;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end text_bolditalcontent


	// $ANTLR start text_formattedcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:194:1: text_formattedcontent returns [CollectionNode items = new CollectionNode ()] : onestar (t= text_unformattedelement onestar ( text_linebreak )? )+ ;
	public final CollectionNode text_formattedcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode ();

	ASTNode t = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:195:2: ( onestar (t= text_unformattedelement onestar ( text_linebreak )? )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:195:4: onestar (t= text_unformattedelement onestar ( text_linebreak )? )+
		{
		pushFollow(FOLLOW_onestar_in_text_formattedcontent803);
		onestar();
		_fsp--;
		if (failed) return items;
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:195:13: (t= text_unformattedelement onestar ( text_linebreak )? )+
		int cnt31=0;
		loop31:
		do {
		int alt31=2;
		switch ( input.LA(1) ) {
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case EQUAL:
		case PIPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
			{
			alt31=1;
			}
			break;
		case FORCED_LINEBREAK:
			{
			alt31=1;
			}
			break;
		case ESCAPE:
			{
			alt31=1;
			}
			break;
		case LINK_OPEN:
			{
			alt31=1;
			}
			break;
		case IMAGE_OPEN:
			{
			alt31=1;
			}
			break;
		case EXTENSION:
			{
			alt31=1;
			}
			break;
		case NOWIKI_OPEN:
			{
			alt31=1;
			}
			break;

		}

		switch (alt31) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:195:15: t= text_unformattedelement onestar ( text_linebreak )?
			{
			pushFollow(FOLLOW_text_unformattedelement_in_text_formattedcontent812);
			t=text_unformattedelement();
			_fsp--;
			if (failed) return items;
			if ( backtracking==0 ) {
			  items.add(t); 
			}
			pushFollow(FOLLOW_onestar_in_text_formattedcontent817);
			onestar();
			_fsp--;
			if (failed) return items;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:195:81: ( text_linebreak )?
			int alt30=2;
			int LA30_0 = input.LA(1);

			if ( (LA30_0==NEWLINE) ) {
			int LA30_1 = input.LA(2);

			if ( ( input.LA(2) != DASH && input.LA(2) != POUND && 
					input.LA(2) != EQUAL && input.LA(2) != NEWLINE ) ) {
				alt30=1;
			}
			}
			else if ( (LA30_0==EOF) ) {
			int LA30_2 = input.LA(2);

			if ( ( input.LA(2) != DASH && input.LA(2) != POUND && 
					input.LA(2) != EQUAL && input.LA(2) != NEWLINE ) ) {
				alt30=1;
			}
			}
			switch (alt30) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:195:83: text_linebreak
				{
				pushFollow(FOLLOW_text_linebreak_in_text_formattedcontent822);
				text_linebreak();
				_fsp--;
				if (failed) return items;

				}
				break;

			}


			}
			break;

		default :
			if ( cnt31 >= 1 ) break loop31;
			if (backtracking>0) {failed=true; return items;}
			EarlyExitException eee =
				new EarlyExitException(31, input);
			throw eee;
		}
		cnt31++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end text_formattedcontent


	// $ANTLR start text_linebreak
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:197:1: text_linebreak : {...}? text_lineseparator ;
	public final void text_linebreak() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:198:2: ({...}? text_lineseparator )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:198:4: {...}? text_lineseparator
		{
		if ( !( input.LA(2) != DASH && input.LA(2) != POUND && 
			input.LA(2) != EQUAL && input.LA(2) != NEWLINE ) ) {
		if (backtracking>0) {failed=true; return ;}
		throw new FailedPredicateException(input, "text_linebreak", " input.LA(2) != DASH && input.LA(2) != POUND && \n\t\tinput.LA(2) != EQUAL && input.LA(2) != NEWLINE ");
		}
		pushFollow(FOLLOW_text_lineseparator_in_text_linebreak842);
		text_lineseparator();
		_fsp--;
		if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end text_linebreak


	// $ANTLR start text_inlineelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:202:1: text_inlineelement returns [ASTNode element = null ] : (tf= text_first_inlineelement | nwi= nowiki_inline );
	public final ASTNode text_inlineelement() throws RecognitionException {
	ASTNode element =  null;

	ASTNode tf = null;

	NoWikiSectionNode nwi = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:203:2: (tf= text_first_inlineelement | nwi= nowiki_inline )
		int alt32=2;
		int LA32_0 = input.LA(1);

		if ( ((LA32_0>=LINK_OPEN && LA32_0<=IMAGE_OPEN)||LA32_0==EXTENSION) ) {
		alt32=1;
		}
		else if ( (LA32_0==NOWIKI_OPEN) ) {
		alt32=2;
		}
		else {
		if (backtracking>0) {failed=true; return element;}
		NoViableAltException nvae =
			new NoViableAltException("202:1: text_inlineelement returns [ASTNode element = null ] : (tf= text_first_inlineelement | nwi= nowiki_inline );", 32, 0, input);

		throw nvae;
		}
		switch (alt32) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:203:4: tf= text_first_inlineelement
			{
			pushFollow(FOLLOW_text_first_inlineelement_in_text_inlineelement860);
			tf=text_first_inlineelement();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = tf; 
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:204:4: nwi= nowiki_inline
			{
			pushFollow(FOLLOW_nowiki_inline_in_text_inlineelement871);
			nwi=nowiki_inline();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = nwi; 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return element;
	}
	// $ANTLR end text_inlineelement


	// $ANTLR start text_first_inlineelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:206:1: text_first_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension );
	public final ASTNode text_first_inlineelement() throws RecognitionException {
	ASTNode element =  null;

	LinkNode l = null;

	ImageNode i = null;

	ASTNode e = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:207:2: (l= link | i= image | e= extension )
		int alt33=3;
		switch ( input.LA(1) ) {
		case LINK_OPEN:
		{
		alt33=1;
		}
		break;
		case IMAGE_OPEN:
		{
		alt33=2;
		}
		break;
		case EXTENSION:
		{
		alt33=3;
		}
		break;
		default:
		if (backtracking>0) {failed=true; return element;}
		NoViableAltException nvae =
			new NoViableAltException("206:1: text_first_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension );", 33, 0, input);

		throw nvae;
		}

		switch (alt33) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:208:3: l= link
			{
			pushFollow(FOLLOW_link_in_text_first_inlineelement894);
			l=link();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = l;
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:209:4: i= image
			{
			pushFollow(FOLLOW_image_in_text_first_inlineelement905);
			i=image();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = i;
			}

			}
			break;
		case 3 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:210:4: e= extension
			{
			pushFollow(FOLLOW_extension_in_text_first_inlineelement915);
			e=extension();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = e;
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return element;
	}
	// $ANTLR end text_first_inlineelement


	// $ANTLR start text_first_unformattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:212:1: text_first_unformattedelement returns [ASTNode item = null] : (tfu= text_first_unformatted | tfi= text_first_inlineelement );
	public final ASTNode text_first_unformattedelement() throws RecognitionException {
	ASTNode item =	null;

	CollectionNode tfu = null;

	ASTNode tfi = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:213:2: (tfu= text_first_unformatted | tfi= text_first_inlineelement )
		int alt34=2;
		int LA34_0 = input.LA(1);

		if ( ((LA34_0>=FORCED_END_OF_LINE && LA34_0<=WIKI)||(LA34_0>=FORCED_LINEBREAK && LA34_0<=78)) ) {
		alt34=1;
		}
		else if ( ((LA34_0>=LINK_OPEN && LA34_0<=IMAGE_OPEN)||LA34_0==EXTENSION) ) {
		alt34=2;
		}
		else {
		if (backtracking>0) {failed=true; return item;}
		NoViableAltException nvae =
			new NoViableAltException("212:1: text_first_unformattedelement returns [ASTNode item = null] : (tfu= text_first_unformatted | tfi= text_first_inlineelement );", 34, 0, input);

		throw nvae;
		}
		switch (alt34) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:213:4: tfu= text_first_unformatted
			{
			pushFollow(FOLLOW_text_first_unformatted_in_text_first_unformattedelement935);
			tfu=text_first_unformatted();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			  item = new UnformattedTextNode(tfu);
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:214:4: tfi= text_first_inlineelement
			{
			pushFollow(FOLLOW_text_first_inlineelement_in_text_first_unformattedelement946);
			tfi=text_first_inlineelement();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			   item = tfi; 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return item;
	}
	// $ANTLR end text_first_unformattedelement


	// $ANTLR start text_first_unformatted
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:216:1: text_first_unformatted returns [CollectionNode items = new CollectionNode()] : (t= text_first_unformmatted_text | ( forced_linebreak | e= escaped )+ );
	public final CollectionNode text_first_unformatted() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	StringBundler t = null;

	ScapedNode e = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:217:2: (t= text_first_unformmatted_text | ( forced_linebreak | e= escaped )+ )
		int alt36=2;
		int LA36_0 = input.LA(1);

		if ( ((LA36_0>=FORCED_END_OF_LINE && LA36_0<=WIKI)||(LA36_0>=NOWIKI_BLOCK_CLOSE && LA36_0<=78)) ) {
		alt36=1;
		}
		else if ( ((LA36_0>=FORCED_LINEBREAK && LA36_0<=ESCAPE)) ) {
		alt36=2;
		}
		else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
			new NoViableAltException("216:1: text_first_unformatted returns [CollectionNode items = new CollectionNode()] : (t= text_first_unformmatted_text | ( forced_linebreak | e= escaped )+ );", 36, 0, input);

		throw nvae;
		}
		switch (alt36) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:217:6: t= text_first_unformmatted_text
			{
			pushFollow(FOLLOW_text_first_unformmatted_text_in_text_first_unformatted968);
			t=text_first_unformmatted_text();
			_fsp--;
			if (failed) return items;
			if ( backtracking==0 ) {
			  items.add(new UnformattedTextNode(t.toString()));
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:218:5: ( forced_linebreak | e= escaped )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:218:5: ( forced_linebreak | e= escaped )+
			int cnt35=0;
			loop35:
			do {
			int alt35=3;
			int LA35_0 = input.LA(1);

			if ( (LA35_0==FORCED_LINEBREAK) ) {
				alt35=1;
			}
			else if ( (LA35_0==ESCAPE) ) {
				int LA35_3 = input.LA(2);

				if ( ((LA35_3>=FORCED_END_OF_LINE && LA35_3<=78)) ) {
				alt35=2;
				}


			}


			switch (alt35) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:218:6: forced_linebreak
				{
				pushFollow(FOLLOW_forced_linebreak_in_text_first_unformatted977);
				forced_linebreak();
				_fsp--;
				if (failed) return items;
				if ( backtracking==0 ) {
				   items.add(new ForcedEndOfLineNode()); 
				}

				}
				break;
			case 2 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:219:5: e= escaped
				{
				pushFollow(FOLLOW_escaped_in_text_first_unformatted989);
				e=escaped();
				_fsp--;
				if (failed) return items;
				if ( backtracking==0 ) {
				  items.add(e);
				}

				}
				break;

			default :
				if ( cnt35 >= 1 ) break loop35;
				if (backtracking>0) {failed=true; return items;}
				EarlyExitException eee =
					new EarlyExitException(35, input);
				throw eee;
			}
			cnt35++;
			} while (true);


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end text_first_unformatted


	// $ANTLR start text_first_unformmatted_text
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:222:1: text_first_unformmatted_text returns [StringBundler text = new StringBundler()] : (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
	public final StringBundler text_first_unformmatted_text() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:223:2: ( (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:224:3: (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:224:3: (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
		int cnt37=0;
		loop37:
		do {
		int alt37=2;
		int LA37_0 = input.LA(1);

		if ( ((LA37_0>=FORCED_END_OF_LINE && LA37_0<=WIKI)||(LA37_0>=NOWIKI_BLOCK_CLOSE && LA37_0<=78)) ) {
			alt37=1;
		}


		switch (alt37) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:224:4: c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
			{
			c=(Token)input.LT(1);
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_text_first_unformmatted_text1018);	throw mse;
			}

			if ( backtracking==0 ) {
			  text.append(c.getText()); 
			}

			}
			break;

		default :
			if ( cnt37 >= 1 ) break loop37;
			if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
				new EarlyExitException(37, input);
			throw eee;
		}
		cnt37++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end text_first_unformmatted_text


	// $ANTLR start text_unformattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:238:1: text_unformattedelement returns [ASTNode contents = null] : (text= text_unformatted | ti= text_inlineelement );
	public final ASTNode text_unformattedelement() throws RecognitionException {
	ASTNode contents =  null;

	CollectionNode text = null;

	ASTNode ti = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:239:2: (text= text_unformatted | ti= text_inlineelement )
		int alt38=2;
		int LA38_0 = input.LA(1);

		if ( ((LA38_0>=FORCED_END_OF_LINE && LA38_0<=WIKI)||LA38_0==POUND||(LA38_0>=EQUAL && LA38_0<=PIPE)||(LA38_0>=FORCED_LINEBREAK && LA38_0<=78)) ) {
		alt38=1;
		}
		else if ( ((LA38_0>=LINK_OPEN && LA38_0<=EXTENSION)) ) {
		alt38=2;
		}
		else {
		if (backtracking>0) {failed=true; return contents;}
		NoViableAltException nvae =
			new NoViableAltException("238:1: text_unformattedelement returns [ASTNode contents = null] : (text= text_unformatted | ti= text_inlineelement );", 38, 0, input);

		throw nvae;
		}
		switch (alt38) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:239:4: text= text_unformatted
			{
			pushFollow(FOLLOW_text_unformatted_in_text_unformattedelement1133);
			text=text_unformatted();
			_fsp--;
			if (failed) return contents;
			if ( backtracking==0 ) {
			   contents = text; 
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:240:4: ti= text_inlineelement
			{
			pushFollow(FOLLOW_text_inlineelement_in_text_unformattedelement1144);
			ti=text_inlineelement();
			_fsp--;
			if (failed) return contents;
			if ( backtracking==0 ) {
			   contents = ti; 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return contents;
	}
	// $ANTLR end text_unformattedelement


	// $ANTLR start text_unformatted
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:243:1: text_unformatted returns [CollectionNode items = new CollectionNode()] : (contents= text_unformated_text | ( forced_linebreak | e= escaped )+ );
	public final CollectionNode text_unformatted() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	StringBundler contents = null;

	ScapedNode e = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:244:2: (contents= text_unformated_text | ( forced_linebreak | e= escaped )+ )
		int alt40=2;
		int LA40_0 = input.LA(1);

		if ( ((LA40_0>=FORCED_END_OF_LINE && LA40_0<=WIKI)||LA40_0==POUND||(LA40_0>=EQUAL && LA40_0<=PIPE)||(LA40_0>=NOWIKI_BLOCK_CLOSE && LA40_0<=78)) ) {
		alt40=1;
		}
		else if ( ((LA40_0>=FORCED_LINEBREAK && LA40_0<=ESCAPE)) ) {
		alt40=2;
		}
		else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
			new NoViableAltException("243:1: text_unformatted returns [CollectionNode items = new CollectionNode()] : (contents= text_unformated_text | ( forced_linebreak | e= escaped )+ );", 40, 0, input);

		throw nvae;
		}
		switch (alt40) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:244:5: contents= text_unformated_text
			{
			pushFollow(FOLLOW_text_unformated_text_in_text_unformatted1166);
			contents=text_unformated_text();
			_fsp--;
			if (failed) return items;
			if ( backtracking==0 ) {
			  items.add(new UnformattedTextNode(contents.toString())); 
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:245:5: ( forced_linebreak | e= escaped )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:245:5: ( forced_linebreak | e= escaped )+
			int cnt39=0;
			loop39:
			do {
			int alt39=3;
			int LA39_0 = input.LA(1);

			if ( (LA39_0==FORCED_LINEBREAK) ) {
				alt39=1;
			}
			else if ( (LA39_0==ESCAPE) ) {
				alt39=2;
			}


			switch (alt39) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:245:6: forced_linebreak
				{
				pushFollow(FOLLOW_forced_linebreak_in_text_unformatted1175);
				forced_linebreak();
				_fsp--;
				if (failed) return items;
				if ( backtracking==0 ) {
				   items.add(new ForcedEndOfLineNode()); 
				}

				}
				break;
			case 2 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:246:5: e= escaped
				{
				pushFollow(FOLLOW_escaped_in_text_unformatted1187);
				e=escaped();
				_fsp--;
				if (failed) return items;
				if ( backtracking==0 ) {
				  items.add(e);
				}

				}
				break;

			default :
				if ( cnt39 >= 1 ) break loop39;
				if (backtracking>0) {failed=true; return items;}
				EarlyExitException eee =
					new EarlyExitException(39, input);
				throw eee;
			}
			cnt39++;
			} while (true);


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end text_unformatted


	// $ANTLR start text_unformated_text
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:249:1: text_unformated_text returns [StringBundler text = new StringBundler()] : (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
	public final StringBundler text_unformated_text() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:250:1: ( (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:251:2: (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:251:2: (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
		int cnt41=0;
		loop41:
		do {
		int alt41=2;
		int LA41_0 = input.LA(1);

		if ( ((LA41_0>=FORCED_END_OF_LINE && LA41_0<=WIKI)||LA41_0==POUND||(LA41_0>=EQUAL && LA41_0<=PIPE)||(LA41_0>=NOWIKI_BLOCK_CLOSE && LA41_0<=78)) ) {
			alt41=1;
		}


		switch (alt41) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:251:3: c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
			{
			c=(Token)input.LT(1);
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||(input.LA(1)>=EQUAL && input.LA(1)<=PIPE)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_text_unformated_text1214);	throw mse;
			}

			if ( backtracking==0 ) {
			   text.append(c.getText());
			}

			}
			break;

		default :
			if ( cnt41 >= 1 ) break loop41;
			if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
				new EarlyExitException(41, input);
			throw eee;
		}
		cnt41++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end text_unformated_text

	protected static class heading_scope {
	CollectionNode items;
	int nestedLevel;
	String text;
	}
	protected Stack heading_stack = new Stack();


	// $ANTLR start heading
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:265:1: heading returns [ASTNode header] : heading_markup heading_content ( heading_markup )? ( blanks )? paragraph_separator ;
	public final ASTNode heading() throws RecognitionException {
	heading_stack.push(new heading_scope());
	ASTNode header = null;


			((heading_scope)heading_stack.peek()).items = new CollectionNode();	
			((heading_scope)heading_stack.peek()).text = new String();
		
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:2: ( heading_markup heading_content ( heading_markup )? ( blanks )? paragraph_separator )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:4: heading_markup heading_content ( heading_markup )? ( blanks )? paragraph_separator
		{
		pushFollow(FOLLOW_heading_markup_in_heading1318);
		heading_markup();
		_fsp--;
		if (failed) return header;
		if ( backtracking==0 ) {
		  ((heading_scope)heading_stack.peek()).nestedLevel++;
		}
		pushFollow(FOLLOW_heading_content_in_heading1323);
		heading_content();
		_fsp--;
		if (failed) return header;
		if ( backtracking==0 ) {
		   header = new HeadingNode(((heading_scope)heading_stack.peek()).items,((heading_scope)heading_stack.peek()).nestedLevel); 
		}
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:134: ( heading_markup )?
		int alt42=2;
		int LA42_0 = input.LA(1);

		if ( (LA42_0==EQUAL) ) {
		alt42=1;
		}
		switch (alt42) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:136: heading_markup
			{
			pushFollow(FOLLOW_heading_markup_in_heading1330);
			heading_markup();
			_fsp--;
			if (failed) return header;

			}
			break;

		}

		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:155: ( blanks )?
		int alt43=2;
		int LA43_0 = input.LA(1);

		if ( (LA43_0==BLANKS) ) {
		alt43=1;
		}
		switch (alt43) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:157: blanks
			{
			pushFollow(FOLLOW_blanks_in_heading1338);
			blanks();
			_fsp--;
			if (failed) return header;

			}
			break;

		}

		pushFollow(FOLLOW_paragraph_separator_in_heading1345);
		paragraph_separator();
		_fsp--;
		if (failed) return header;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
		heading_stack.pop();
	}
	return header;
	}
	// $ANTLR end heading


	// $ANTLR start heading_content
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:278:1: heading_content : ( heading_markup heading_content ( heading_markup )? | ht= heading_text );
	public final void heading_content() throws RecognitionException {
	CollectionNode ht = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:279:2: ( heading_markup heading_content ( heading_markup )? | ht= heading_text )
		int alt45=2;
		int LA45_0 = input.LA(1);

		if ( (LA45_0==EQUAL) ) {
		alt45=1;
		}
		else if ( (LA45_0==EOF||(LA45_0>=FORCED_END_OF_LINE && LA45_0<=STAR)||(LA45_0>=PIPE && LA45_0<=FORCED_LINEBREAK)||(LA45_0>=NOWIKI_BLOCK_CLOSE && LA45_0<=78)) ) {
		alt45=2;
		}
		else {
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
			new NoViableAltException("278:1: heading_content : ( heading_markup heading_content ( heading_markup )? | ht= heading_text );", 45, 0, input);

		throw nvae;
		}
		switch (alt45) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:279:4: heading_markup heading_content ( heading_markup )?
			{
			pushFollow(FOLLOW_heading_markup_in_heading_content1355);
			heading_markup();
			_fsp--;
			if (failed) return ;
			if ( backtracking==0 ) {
			  ((heading_scope)heading_stack.peek()).nestedLevel++;
			}
			pushFollow(FOLLOW_heading_content_in_heading_content1360);
			heading_content();
			_fsp--;
			if (failed) return ;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:279:64: ( heading_markup )?
			int alt44=2;
			int LA44_0 = input.LA(1);

			if ( (LA44_0==EQUAL) ) {
			alt44=1;
			}
			switch (alt44) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:279:66: heading_markup
				{
				pushFollow(FOLLOW_heading_markup_in_heading_content1365);
				heading_markup();
				_fsp--;
				if (failed) return ;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:280:4: ht= heading_text
			{
			pushFollow(FOLLOW_heading_text_in_heading_content1377);
			ht=heading_text();
			_fsp--;
			if (failed) return ;
			if ( backtracking==0 ) {
			  ((heading_scope)heading_stack.peek()).items = ht;
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end heading_content


	// $ANTLR start heading_text
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:283:1: heading_text returns [CollectionNode items = null] : te= heading_cellcontent ;
	public final CollectionNode heading_text() throws RecognitionException {
	CollectionNode items =	null;

	CollectionNode te = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:284:2: (te= heading_cellcontent )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:284:4: te= heading_cellcontent
		{
		pushFollow(FOLLOW_heading_cellcontent_in_heading_text1398);
		te=heading_cellcontent();
		_fsp--;
		if (failed) return items;
		if ( backtracking==0 ) {
		  items = te;
		}

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end heading_text


	// $ANTLR start heading_cellcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:287:1: heading_cellcontent returns [CollectionNode items = new CollectionNode()] : onestar (tcp= heading_cellcontentpart onestar )* ;
	public final CollectionNode heading_cellcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode tcp = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:288:2: ( onestar (tcp= heading_cellcontentpart onestar )* )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:288:4: onestar (tcp= heading_cellcontentpart onestar )*
		{
		pushFollow(FOLLOW_onestar_in_heading_cellcontent1415);
		onestar();
		_fsp--;
		if (failed) return items;
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:288:13: (tcp= heading_cellcontentpart onestar )*
		loop46:
		do {
		int alt46=2;
		int LA46_0 = input.LA(1);

		if ( ((LA46_0>=FORCED_END_OF_LINE && LA46_0<=WIKI)||(LA46_0>=POUND && LA46_0<=STAR)||(LA46_0>=PIPE && LA46_0<=FORCED_LINEBREAK)||(LA46_0>=NOWIKI_BLOCK_CLOSE && LA46_0<=78)) ) {
			alt46=1;
		}


		switch (alt46) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:288:15: tcp= heading_cellcontentpart onestar
			{
			pushFollow(FOLLOW_heading_cellcontentpart_in_heading_cellcontent1424);
			tcp=heading_cellcontentpart();
			_fsp--;
			if (failed) return items;
			if ( backtracking==0 ) {

									
									if(tcp != null) { // some AST Node could be NULL if bad CREOLE syntax is wrotten
										items.add(tcp); 
									}
									
									
			}
			pushFollow(FOLLOW_onestar_in_heading_cellcontent1436);
			onestar();
			_fsp--;
			if (failed) return items;

			}
			break;

		default :
			break loop46;
		}
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end heading_cellcontent


	// $ANTLR start heading_cellcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:297:1: heading_cellcontentpart returns [ASTNode node = null] : (tf= heading_formattedelement | tu= heading_unformattedelement );
	public final ASTNode heading_cellcontentpart() throws RecognitionException {
	ASTNode node =	null;

	ASTNode tf = null;

	ASTNode tu = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:298:2: (tf= heading_formattedelement | tu= heading_unformattedelement )
		int alt47=2;
		switch ( input.LA(1) ) {
		case ITAL:
		{
		alt47=1;
		}
		break;
		case STAR:
		{
		int LA47_2 = input.LA(2);

		if ( (LA47_2==STAR) ) {
			alt47=1;
		}
		else if ( (LA47_2==EOF||(LA47_2>=FORCED_END_OF_LINE && LA47_2<=POUND)||(LA47_2>=EQUAL && LA47_2<=FORCED_LINEBREAK)||(LA47_2>=NOWIKI_BLOCK_CLOSE && LA47_2<=78)) ) {
			alt47=2;
		}
		else {
			if (backtracking>0) {failed=true; return node;}
			NoViableAltException nvae =
			new NoViableAltException("297:1: heading_cellcontentpart returns [ASTNode node = null] : (tf= heading_formattedelement | tu= heading_unformattedelement );", 47, 2, input);

			throw nvae;
		}
		}
		break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case PIPE:
		case LINK_OPEN:
		case IMAGE_OPEN:
		case NOWIKI_OPEN:
		case EXTENSION:
		case FORCED_LINEBREAK:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
		{
		alt47=2;
		}
		break;
		default:
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
			new NoViableAltException("297:1: heading_cellcontentpart returns [ASTNode node = null] : (tf= heading_formattedelement | tu= heading_unformattedelement );", 47, 0, input);

		throw nvae;
		}

		switch (alt47) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:298:4: tf= heading_formattedelement
			{
			pushFollow(FOLLOW_heading_formattedelement_in_heading_cellcontentpart1457);
			tf=heading_formattedelement();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node =tf;
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:299:4: tu= heading_unformattedelement
			{
			pushFollow(FOLLOW_heading_unformattedelement_in_heading_cellcontentpart1468);
			tu=heading_unformattedelement();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node =tu;
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end heading_cellcontentpart


	// $ANTLR start heading_formattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:301:1: heading_formattedelement returns [ASTNode content = null] : ( ital_markup (tic= heading_italcontent )? ( ital_markup )? | bold_markup (tbc= heading_boldcontent )? ( bold_markup )? );
	public final ASTNode heading_formattedelement() throws RecognitionException {
	ASTNode content =  null;

	CollectionNode tic = null;

	CollectionNode tbc = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:302:2: ( ital_markup (tic= heading_italcontent )? ( ital_markup )? | bold_markup (tbc= heading_boldcontent )? ( bold_markup )? )
		int alt52=2;
		int LA52_0 = input.LA(1);

		if ( (LA52_0==ITAL) ) {
		alt52=1;
		}
		else if ( (LA52_0==STAR) ) {
		alt52=2;
		}
		else {
		if (backtracking>0) {failed=true; return content;}
		NoViableAltException nvae =
			new NoViableAltException("301:1: heading_formattedelement returns [ASTNode content = null] : ( ital_markup (tic= heading_italcontent )? ( ital_markup )? | bold_markup (tbc= heading_boldcontent )? ( bold_markup )? );", 52, 0, input);

		throw nvae;
		}
		switch (alt52) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:302:4: ital_markup (tic= heading_italcontent )? ( ital_markup )?
			{
			pushFollow(FOLLOW_ital_markup_in_heading_formattedelement1484);
			ital_markup();
			_fsp--;
			if (failed) return content;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:302:18: (tic= heading_italcontent )?
			int alt48=2;
			switch ( input.LA(1) ) {
			case STAR:
				{
				alt48=1;
				}
				break;
			case ITAL:
				{
				alt48=1;
				}
				break;
			case LINK_OPEN:
				{
				alt48=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt48=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt48=1;
				}
				break;
			case EOF:
				{
				alt48=1;
				}
				break;
			case BLANKS:
				{
				alt48=1;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case PIPE:
			case EXTENSION:
			case FORCED_LINEBREAK:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt48=1;
				}
				break;
			}

			switch (alt48) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:302:20: tic= heading_italcontent
				{
				pushFollow(FOLLOW_heading_italcontent_in_heading_formattedelement1494);
				tic=heading_italcontent();
				_fsp--;
				if (failed) return content;
				if ( backtracking==0 ) {
				   content = new ItalicTextNode(tic); 
				}

				}
				break;

			}

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:302:96: ( ital_markup )?
			int alt49=2;
			int LA49_0 = input.LA(1);

			if ( (LA49_0==ITAL) ) {
			alt49=1;
			}
			switch (alt49) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:302:98: ital_markup
				{
				pushFollow(FOLLOW_ital_markup_in_heading_formattedelement1503);
				ital_markup();
				_fsp--;
				if (failed) return content;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:303:4: bold_markup (tbc= heading_boldcontent )? ( bold_markup )?
			{
			pushFollow(FOLLOW_bold_markup_in_heading_formattedelement1511);
			bold_markup();
			_fsp--;
			if (failed) return content;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:303:16: (tbc= heading_boldcontent )?
			int alt50=2;
			switch ( input.LA(1) ) {
			case STAR:
				{
				alt50=1;
				}
				break;
			case ITAL:
				{
				alt50=1;
				}
				break;
			case LINK_OPEN:
				{
				alt50=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt50=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt50=1;
				}
				break;
			case BLANKS:
				{
				alt50=1;
				}
				break;
			case EOF:
				{
				alt50=1;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case PIPE:
			case EXTENSION:
			case FORCED_LINEBREAK:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt50=1;
				}
				break;
			}

			switch (alt50) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:303:18: tbc= heading_boldcontent
				{
				pushFollow(FOLLOW_heading_boldcontent_in_heading_formattedelement1518);
				tbc=heading_boldcontent();
				_fsp--;
				if (failed) return content;
				if ( backtracking==0 ) {
				  content = new BoldTextNode(tbc);
				}

				}
				break;

			}

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:303:90: ( bold_markup )?
			int alt51=2;
			int LA51_0 = input.LA(1);

			if ( (LA51_0==STAR) ) {
			int LA51_1 = input.LA(2);

			if ( (LA51_1==STAR) ) {
				alt51=1;
			}
			}
			switch (alt51) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:303:92: bold_markup
				{
				pushFollow(FOLLOW_bold_markup_in_heading_formattedelement1528);
				bold_markup();
				_fsp--;
				if (failed) return content;

				}
				break;

			}


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return content;
	}
	// $ANTLR end heading_formattedelement


	// $ANTLR start heading_boldcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:305:1: heading_boldcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (tb= heading_boldcontentpart onestar )+ | EOF );
	public final CollectionNode heading_boldcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode tb = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:306:2: ( onestar (tb= heading_boldcontentpart onestar )+ | EOF )
		int alt54=2;
		int LA54_0 = input.LA(1);

		if ( ((LA54_0>=FORCED_END_OF_LINE && LA54_0<=WIKI)||(LA54_0>=POUND && LA54_0<=STAR)||(LA54_0>=PIPE && LA54_0<=FORCED_LINEBREAK)||(LA54_0>=NOWIKI_BLOCK_CLOSE && LA54_0<=78)) ) {
		alt54=1;
		}
		else if ( (LA54_0==EOF) ) {
		alt54=2;
		}
		else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
			new NoViableAltException("305:1: heading_boldcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (tb= heading_boldcontentpart onestar )+ | EOF );", 54, 0, input);

		throw nvae;
		}
		switch (alt54) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:306:4: onestar (tb= heading_boldcontentpart onestar )+
			{
			pushFollow(FOLLOW_onestar_in_heading_boldcontent1545);
			onestar();
			_fsp--;
			if (failed) return items;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:306:13: (tb= heading_boldcontentpart onestar )+
			int cnt53=0;
			loop53:
			do {
			int alt53=2;
			switch ( input.LA(1) ) {
			case STAR:
				{
				alt53=1;
				}
				break;
			case BLANKS:
				{
				alt53=1;
				}
				break;
			case ITAL:
				{
				alt53=1;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case PIPE:
			case EXTENSION:
			case FORCED_LINEBREAK:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt53=1;
				}
				break;
			case LINK_OPEN:
				{
				alt53=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt53=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt53=1;
				}
				break;

			}

			switch (alt53) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:306:15: tb= heading_boldcontentpart onestar
				{
				pushFollow(FOLLOW_heading_boldcontentpart_in_heading_boldcontent1554);
				tb=heading_boldcontentpart();
				_fsp--;
				if (failed) return items;
				if ( backtracking==0 ) {
				   items.add(tb); 
				}
				pushFollow(FOLLOW_onestar_in_heading_boldcontent1559);
				onestar();
				_fsp--;
				if (failed) return items;

				}
				break;

			default :
				if ( cnt53 >= 1 ) break loop53;
				if (backtracking>0) {failed=true; return items;}
				EarlyExitException eee =
					new EarlyExitException(53, input);
				throw eee;
			}
			cnt53++;
			} while (true);


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:307:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_heading_boldcontent1567); if (failed) return items;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end heading_boldcontent


	// $ANTLR start heading_italcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:309:1: heading_italcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (ti= heading_italcontentpart onestar )+ | EOF );
	public final CollectionNode heading_italcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode ti = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:310:2: ( onestar (ti= heading_italcontentpart onestar )+ | EOF )
		int alt56=2;
		int LA56_0 = input.LA(1);

		if ( ((LA56_0>=FORCED_END_OF_LINE && LA56_0<=WIKI)||(LA56_0>=POUND && LA56_0<=STAR)||(LA56_0>=PIPE && LA56_0<=FORCED_LINEBREAK)||(LA56_0>=NOWIKI_BLOCK_CLOSE && LA56_0<=78)) ) {
		alt56=1;
		}
		else if ( (LA56_0==EOF) ) {
		alt56=2;
		}
		else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
			new NoViableAltException("309:1: heading_italcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (ti= heading_italcontentpart onestar )+ | EOF );", 56, 0, input);

		throw nvae;
		}
		switch (alt56) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:310:4: onestar (ti= heading_italcontentpart onestar )+
			{
			pushFollow(FOLLOW_onestar_in_heading_italcontent1581);
			onestar();
			_fsp--;
			if (failed) return items;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:310:13: (ti= heading_italcontentpart onestar )+
			int cnt55=0;
			loop55:
			do {
			int alt55=2;
			switch ( input.LA(1) ) {
			case ITAL:
				{
				alt55=1;
				}
				break;
			case STAR:
				{
				alt55=1;
				}
				break;
			case BLANKS:
				{
				alt55=1;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case PIPE:
			case EXTENSION:
			case FORCED_LINEBREAK:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt55=1;
				}
				break;
			case LINK_OPEN:
				{
				alt55=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt55=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt55=1;
				}
				break;

			}

			switch (alt55) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:310:15: ti= heading_italcontentpart onestar
				{
				pushFollow(FOLLOW_heading_italcontentpart_in_heading_italcontent1590);
				ti=heading_italcontentpart();
				_fsp--;
				if (failed) return items;
				if ( backtracking==0 ) {
				   items.add(ti); 
				}
				pushFollow(FOLLOW_onestar_in_heading_italcontent1595);
				onestar();
				_fsp--;
				if (failed) return items;

				}
				break;

			default :
				if ( cnt55 >= 1 ) break loop55;
				if (backtracking>0) {failed=true; return items;}
				EarlyExitException eee =
					new EarlyExitException(55, input);
				throw eee;
			}
			cnt55++;
			} while (true);


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:311:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_heading_italcontent1603); if (failed) return items;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end heading_italcontent


	// $ANTLR start heading_boldcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:313:1: heading_boldcontentpart returns [ASTNode node = null] : (tf= heading_formattedcontent | ital_markup tb= heading_bolditalcontent ( ital_markup )? );
	public final ASTNode heading_boldcontentpart() throws RecognitionException {
	ASTNode node =	null;

	CollectionNode tf = null;

	CollectionNode tb = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:314:2: (tf= heading_formattedcontent | ital_markup tb= heading_bolditalcontent ( ital_markup )? )
		int alt58=2;
		int LA58_0 = input.LA(1);

		if ( ((LA58_0>=FORCED_END_OF_LINE && LA58_0<=WIKI)||(LA58_0>=POUND && LA58_0<=STAR)||(LA58_0>=PIPE && LA58_0<=FORCED_LINEBREAK)||(LA58_0>=NOWIKI_BLOCK_CLOSE && LA58_0<=78)) ) {
		alt58=1;
		}
		else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
			new NoViableAltException("313:1: heading_boldcontentpart returns [ASTNode node = null] : (tf= heading_formattedcontent | ital_markup tb= heading_bolditalcontent ( ital_markup )? );", 58, 0, input);

		throw nvae;
		}
		switch (alt58) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:314:4: tf= heading_formattedcontent
			{
			pushFollow(FOLLOW_heading_formattedcontent_in_heading_boldcontentpart1621);
			tf=heading_formattedcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node = tf; 
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:315:4: ital_markup tb= heading_bolditalcontent ( ital_markup )?
			{
			pushFollow(FOLLOW_ital_markup_in_heading_boldcontentpart1628);
			ital_markup();
			_fsp--;
			if (failed) return node;
			pushFollow(FOLLOW_heading_bolditalcontent_in_heading_boldcontentpart1635);
			tb=heading_bolditalcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			   node = new ItalicTextNode(tb);  
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:315:94: ( ital_markup )?
			int alt57=2;
			int LA57_0 = input.LA(1);

			if ( (LA57_0==ITAL) ) {
			alt57=1;
			}
			switch (alt57) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:315:96: ital_markup
				{
				pushFollow(FOLLOW_ital_markup_in_heading_boldcontentpart1642);
				ital_markup();
				_fsp--;
				if (failed) return node;

				}
				break;

			}


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end heading_boldcontentpart


	// $ANTLR start heading_italcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:317:1: heading_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= heading_bolditalcontent ( bold_markup )? | tf= heading_formattedcontent );
	public final ASTNode heading_italcontentpart() throws RecognitionException {
	ASTNode node =	null;

	CollectionNode tb = null;

	CollectionNode tf = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:318:2: ( bold_markup tb= heading_bolditalcontent ( bold_markup )? | tf= heading_formattedcontent )
		int alt60=2;
		int LA60_0 = input.LA(1);

		if ( (LA60_0==STAR) ) {
		int LA60_1 = input.LA(2);

		if ( (LA60_1==STAR) ) {
			alt60=1;
		}
		else if ( (LA60_1==EOF||(LA60_1>=FORCED_END_OF_LINE && LA60_1<=POUND)||(LA60_1>=EQUAL && LA60_1<=FORCED_LINEBREAK)||(LA60_1>=NOWIKI_BLOCK_CLOSE && LA60_1<=78)) ) {
			alt60=2;
		}
		else {
			if (backtracking>0) {failed=true; return node;}
			NoViableAltException nvae =
			new NoViableAltException("317:1: heading_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= heading_bolditalcontent ( bold_markup )? | tf= heading_formattedcontent );", 60, 1, input);

			throw nvae;
		}
		}
		else if ( ((LA60_0>=FORCED_END_OF_LINE && LA60_0<=WIKI)||LA60_0==POUND||(LA60_0>=PIPE && LA60_0<=FORCED_LINEBREAK)||(LA60_0>=NOWIKI_BLOCK_CLOSE && LA60_0<=78)) ) {
		alt60=2;
		}
		else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
			new NoViableAltException("317:1: heading_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= heading_bolditalcontent ( bold_markup )? | tf= heading_formattedcontent );", 60, 0, input);

		throw nvae;
		}
		switch (alt60) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:318:4: bold_markup tb= heading_bolditalcontent ( bold_markup )?
			{
			pushFollow(FOLLOW_bold_markup_in_heading_italcontentpart1659);
			bold_markup();
			_fsp--;
			if (failed) return node;
			pushFollow(FOLLOW_heading_bolditalcontent_in_heading_italcontentpart1666);
			tb=heading_bolditalcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node = new BoldTextNode(tb); 
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:318:90: ( bold_markup )?
			int alt59=2;
			int LA59_0 = input.LA(1);

			if ( (LA59_0==STAR) ) {
			int LA59_1 = input.LA(2);

			if ( (LA59_1==STAR) ) {
				alt59=1;
			}
			}
			switch (alt59) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:318:92: bold_markup
				{
				pushFollow(FOLLOW_bold_markup_in_heading_italcontentpart1673);
				bold_markup();
				_fsp--;
				if (failed) return node;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:319:4: tf= heading_formattedcontent
			{
			pushFollow(FOLLOW_heading_formattedcontent_in_heading_italcontentpart1685);
			tf=heading_formattedcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			   node = tf; 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end heading_italcontentpart


	// $ANTLR start heading_bolditalcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:321:1: heading_bolditalcontent returns [CollectionNode elements = null] : ( onestar (tfc= heading_formattedcontent onestar )? | EOF );
	public final CollectionNode heading_bolditalcontent() throws RecognitionException {
	CollectionNode elements =  null;

	CollectionNode tfc = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:322:2: ( onestar (tfc= heading_formattedcontent onestar )? | EOF )
		int alt62=2;
		int LA62_0 = input.LA(1);

		if ( ((LA62_0>=FORCED_END_OF_LINE && LA62_0<=FORCED_LINEBREAK)||(LA62_0>=NOWIKI_BLOCK_CLOSE && LA62_0<=78)) ) {
		alt62=1;
		}
		else if ( (LA62_0==EOF) ) {
		alt62=1;
		}
		else {
		if (backtracking>0) {failed=true; return elements;}
		NoViableAltException nvae =
			new NoViableAltException("321:1: heading_bolditalcontent returns [CollectionNode elements = null] : ( onestar (tfc= heading_formattedcontent onestar )? | EOF );", 62, 0, input);

		throw nvae;
		}
		switch (alt62) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:322:4: onestar (tfc= heading_formattedcontent onestar )?
			{
			pushFollow(FOLLOW_onestar_in_heading_bolditalcontent1701);
			onestar();
			_fsp--;
			if (failed) return elements;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:322:13: (tfc= heading_formattedcontent onestar )?
			int alt61=2;
			switch ( input.LA(1) ) {
			case ITAL:
				{
				alt61=1;
				}
				break;
			case LINK_OPEN:
				{
				alt61=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt61=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt61=1;
				}
				break;
			case STAR:
				{
				alt61=1;
				}
				break;
			case BLANKS:
				{
				alt61=1;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case PIPE:
			case EXTENSION:
			case FORCED_LINEBREAK:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt61=1;
				}
				break;
			}

			switch (alt61) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:322:15: tfc= heading_formattedcontent onestar
				{
				pushFollow(FOLLOW_heading_formattedcontent_in_heading_bolditalcontent1710);
				tfc=heading_formattedcontent();
				_fsp--;
				if (failed) return elements;
				if ( backtracking==0 ) {
				   elements = tfc; 
				}
				pushFollow(FOLLOW_onestar_in_heading_bolditalcontent1715);
				onestar();
				_fsp--;
				if (failed) return elements;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:323:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_heading_bolditalcontent1723); if (failed) return elements;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return elements;
	}
	// $ANTLR end heading_bolditalcontent


	// $ANTLR start heading_formattedcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:325:1: heading_formattedcontent returns [CollectionNode elements = new CollectionNode()] : (tu= heading_unformattedelement )+ ;
	public final CollectionNode heading_formattedcontent() throws RecognitionException {
	CollectionNode elements =  new CollectionNode();

	ASTNode tu = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:326:2: ( (tu= heading_unformattedelement )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:326:4: (tu= heading_unformattedelement )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:326:4: (tu= heading_unformattedelement )+
		int cnt63=0;
		loop63:
		do {
		int alt63=2;
		switch ( input.LA(1) ) {
		case STAR:
			{
			alt63=1;
			}
			break;
		case BLANKS:
			{
			alt63=1;
			}
			break;
		case ITAL:
			{
			alt63=1;
			}
			break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case PIPE:
		case EXTENSION:
		case FORCED_LINEBREAK:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
			{
			alt63=1;
			}
			break;
		case LINK_OPEN:
			{
			alt63=1;
			}
			break;
		case IMAGE_OPEN:
			{
			alt63=1;
			}
			break;
		case NOWIKI_OPEN:
			{
			alt63=1;
			}
			break;

		}

		switch (alt63) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:326:6: tu= heading_unformattedelement
			{
			pushFollow(FOLLOW_heading_unformattedelement_in_heading_formattedcontent1743);
			tu=heading_unformattedelement();
			_fsp--;
			if (failed) return elements;
			if ( backtracking==0 ) {
			   elements.add(tu); 
			}

			}
			break;

		default :
			if ( cnt63 >= 1 ) break loop63;
			if (backtracking>0) {failed=true; return elements;}
			EarlyExitException eee =
				new EarlyExitException(63, input);
			throw eee;
		}
		cnt63++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return elements;
	}
	// $ANTLR end heading_formattedcontent


	// $ANTLR start heading_unformattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:328:1: heading_unformattedelement returns [ASTNode content = null] : (tu= heading_unformatted_text | ti= heading_inlineelement );
	public final ASTNode heading_unformattedelement() throws RecognitionException {
	ASTNode content =  null;

	StringBundler tu = null;

	ASTNode ti = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:329:2: (tu= heading_unformatted_text | ti= heading_inlineelement )
		int alt64=2;
		int LA64_0 = input.LA(1);

		if ( ((LA64_0>=FORCED_END_OF_LINE && LA64_0<=WIKI)||(LA64_0>=POUND && LA64_0<=STAR)||(LA64_0>=PIPE && LA64_0<=ITAL)||(LA64_0>=EXTENSION && LA64_0<=FORCED_LINEBREAK)||(LA64_0>=NOWIKI_BLOCK_CLOSE && LA64_0<=78)) ) {
		alt64=1;
		}
		else if ( ((LA64_0>=LINK_OPEN && LA64_0<=NOWIKI_OPEN)) ) {
		alt64=2;
		}
		else {
		if (backtracking>0) {failed=true; return content;}
		NoViableAltException nvae =
			new NoViableAltException("328:1: heading_unformattedelement returns [ASTNode content = null] : (tu= heading_unformatted_text | ti= heading_inlineelement );", 64, 0, input);

		throw nvae;
		}
		switch (alt64) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:329:4: tu= heading_unformatted_text
			{
			pushFollow(FOLLOW_heading_unformatted_text_in_heading_unformattedelement1766);
			tu=heading_unformatted_text();
			_fsp--;
			if (failed) return content;
			if ( backtracking==0 ) {
			  content = new UnformattedTextNode(tu.toString());
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:330:4: ti= heading_inlineelement
			{
			pushFollow(FOLLOW_heading_inlineelement_in_heading_unformattedelement1778);
			ti=heading_inlineelement();
			_fsp--;
			if (failed) return content;
			if ( backtracking==0 ) {
			  content = ti;
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return content;
	}
	// $ANTLR end heading_unformattedelement


	// $ANTLR start heading_inlineelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:332:1: heading_inlineelement returns [ASTNode element = null] : (l= link | i= image | nwi= nowiki_inline );
	public final ASTNode heading_inlineelement() throws RecognitionException {
	ASTNode element =  null;

	LinkNode l = null;

	ImageNode i = null;

	NoWikiSectionNode nwi = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:333:2: (l= link | i= image | nwi= nowiki_inline )
		int alt65=3;
		switch ( input.LA(1) ) {
		case LINK_OPEN:
		{
		alt65=1;
		}
		break;
		case IMAGE_OPEN:
		{
		alt65=2;
		}
		break;
		case NOWIKI_OPEN:
		{
		alt65=3;
		}
		break;
		default:
		if (backtracking>0) {failed=true; return element;}
		NoViableAltException nvae =
			new NoViableAltException("332:1: heading_inlineelement returns [ASTNode element = null] : (l= link | i= image | nwi= nowiki_inline );", 65, 0, input);

		throw nvae;
		}

		switch (alt65) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:333:4: l= link
			{
			pushFollow(FOLLOW_link_in_heading_inlineelement1799);
			l=link();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = l; 
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:334:4: i= image
			{
			pushFollow(FOLLOW_image_in_heading_inlineelement1809);
			i=image();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = i; 
			}

			}
			break;
		case 3 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:335:4: nwi= nowiki_inline
			{
			pushFollow(FOLLOW_nowiki_inline_in_heading_inlineelement1820);
			nwi=nowiki_inline();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = nwi; 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return element;
	}
	// $ANTLR end heading_inlineelement


	// $ANTLR start heading_unformatted_text
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:338:1: heading_unformatted_text returns [StringBundler text = new StringBundler()] : (c=~ ( LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EQUAL | ESCAPE | NEWLINE | EOF ) )+ ;
	public final StringBundler heading_unformatted_text() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:339:2: ( (c=~ ( LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EQUAL | ESCAPE | NEWLINE | EOF ) )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:339:4: (c=~ ( LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EQUAL | ESCAPE | NEWLINE | EOF ) )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:339:4: (c=~ ( LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EQUAL | ESCAPE | NEWLINE | EOF ) )+
		int cnt66=0;
		loop66:
		do {
		int alt66=2;
		switch ( input.LA(1) ) {
		case STAR:
			{
			alt66=1;
			}
			break;
		case BLANKS:
			{
			alt66=1;
			}
			break;
		case ITAL:
			{
			alt66=1;
			}
			break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case PIPE:
		case EXTENSION:
		case FORCED_LINEBREAK:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
			{
			alt66=1;
			}
			break;

		}

		switch (alt66) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:339:6: c=~ ( LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EQUAL | ESCAPE | NEWLINE | EOF )
			{
			c=(Token)input.LT(1);
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=STAR)||(input.LA(1)>=PIPE && input.LA(1)<=ITAL)||(input.LA(1)>=EXTENSION && input.LA(1)<=FORCED_LINEBREAK)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_heading_unformatted_text1845);	throw mse;
			}

			if ( backtracking==0 ) {
			  text.append(c.getText());
			}

			}
			break;

		default :
			if ( cnt66 >= 1 ) break loop66;
			if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
				new EarlyExitException(66, input);
			throw eee;
		}
		cnt66++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end heading_unformatted_text


	// $ANTLR start list_ord
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:345:1: list_ord returns [OrderedListNode orderedList = new OrderedListNode()] : (elem= list_ordelem )+ ( end_of_list )? ;
	public final OrderedListNode list_ord() throws RecognitionException {
	OrderedListNode orderedList =  new OrderedListNode();

	ASTNode elem = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:346:2: ( (elem= list_ordelem )+ ( end_of_list )? )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:346:4: (elem= list_ordelem )+ ( end_of_list )?
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:346:4: (elem= list_ordelem )+
		int cnt67=0;
		loop67:
		do {
		int alt67=2;
		int LA67_0 = input.LA(1);

		if ( (LA67_0==POUND) ) {
			alt67=1;
		}


		switch (alt67) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:346:6: elem= list_ordelem
			{
			pushFollow(FOLLOW_list_ordelem_in_list_ord1904);
			elem=list_ordelem();
			_fsp--;
			if (failed) return orderedList;
			if ( backtracking==0 ) {
			   orderedList.addChildASTNode(elem);  
			}

			}
			break;

		default :
			if ( cnt67 >= 1 ) break loop67;
			if (backtracking>0) {failed=true; return orderedList;}
			EarlyExitException eee =
				new EarlyExitException(67, input);
			throw eee;
		}
		cnt67++;
		} while (true);

		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:346:77: ( end_of_list )?
		int alt68=2;
		int LA68_0 = input.LA(1);

		if ( (LA68_0==NEWLINE) ) {
		alt68=1;
		}
		else if ( (LA68_0==EOF) ) {
		alt68=1;
		}
		switch (alt68) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:346:79: end_of_list
			{
			pushFollow(FOLLOW_end_of_list_in_list_ord1914);
			end_of_list();
			_fsp--;
			if (failed) return orderedList;

			}
			break;

		}


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return orderedList;
	}
	// $ANTLR end list_ord


	// $ANTLR start list_ordelem
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:348:1: list_ordelem returns [ASTNode item = null] : om= list_ordelem_markup elem= list_elem ;
	public final ASTNode list_ordelem() throws RecognitionException {
	CountLevel_stack.push(new CountLevel_scope());

	ASTNode item =	null;

	list_ordelem_markup_return om = null;

	CollectionNode elem = null;


			
			((CountLevel_scope)CountLevel_stack.peek()).level = 0;
			((CountLevel_scope)CountLevel_stack.peek()).groups = new String();
		
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:354:2: (om= list_ordelem_markup elem= list_elem )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:354:4: om= list_ordelem_markup elem= list_elem
		{
		pushFollow(FOLLOW_list_ordelem_markup_in_list_ordelem1947);
		om=list_ordelem_markup();
		_fsp--;
		if (failed) return item;
		if ( backtracking==0 ) {
		  ++((CountLevel_scope)CountLevel_stack.peek()).level; ((CountLevel_scope)CountLevel_stack.peek()).currentMarkup = input.toString(om.start,om.stop); ((CountLevel_scope)CountLevel_stack.peek()).groups += input.toString(om.start,om.stop);
		}
		pushFollow(FOLLOW_list_elem_in_list_ordelem1955);
		elem=list_elem();
		_fsp--;
		if (failed) return item;
		if ( backtracking==0 ) {
		   item = new OrderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, elem);
		}

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
		CountLevel_stack.pop();

	}
	return item;
	}
	// $ANTLR end list_ordelem


	// $ANTLR start list_unord
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:357:1: list_unord returns [UnorderedListNode unorderedList = new UnorderedListNode()] : (elem= list_unordelem )+ ( end_of_list )? ;
	public final UnorderedListNode list_unord() throws RecognitionException {
	UnorderedListNode unorderedList =  new UnorderedListNode();

	UnorderedListItemNode elem = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:358:2: ( (elem= list_unordelem )+ ( end_of_list )? )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:358:4: (elem= list_unordelem )+ ( end_of_list )?
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:358:4: (elem= list_unordelem )+
		int cnt69=0;
		loop69:
		do {
		int alt69=2;
		int LA69_0 = input.LA(1);

		if ( (LA69_0==STAR) ) {
			alt69=1;
		}


		switch (alt69) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:358:6: elem= list_unordelem
			{
			pushFollow(FOLLOW_list_unordelem_in_list_unord1979);
			elem=list_unordelem();
			_fsp--;
			if (failed) return unorderedList;
			if ( backtracking==0 ) {
			   unorderedList.addChildASTNode(elem); 
			}

			}
			break;

		default :
			if ( cnt69 >= 1 ) break loop69;
			if (backtracking>0) {failed=true; return unorderedList;}
			EarlyExitException eee =
				new EarlyExitException(69, input);
			throw eee;
		}
		cnt69++;
		} while (true);

		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:358:80: ( end_of_list )?
		int alt70=2;
		int LA70_0 = input.LA(1);

		if ( (LA70_0==NEWLINE) ) {
		alt70=1;
		}
		else if ( (LA70_0==EOF) ) {
		alt70=1;
		}
		switch (alt70) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:358:82: end_of_list
			{
			pushFollow(FOLLOW_end_of_list_in_list_unord1989);
			end_of_list();
			_fsp--;
			if (failed) return unorderedList;

			}
			break;

		}


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return unorderedList;
	}
	// $ANTLR end list_unord


	// $ANTLR start list_unordelem
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:360:1: list_unordelem returns [UnorderedListItemNode item = null] : um= list_unordelem_markup elem= list_elem ;
	public final UnorderedListItemNode list_unordelem() throws RecognitionException {
	CountLevel_stack.push(new CountLevel_scope());

	UnorderedListItemNode item =  null;

	list_unordelem_markup_return um = null;

	CollectionNode elem = null;



			((CountLevel_scope)CountLevel_stack.peek()).level = 0;
		
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:365:2: (um= list_unordelem_markup elem= list_elem )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:365:4: um= list_unordelem_markup elem= list_elem
		{
		pushFollow(FOLLOW_list_unordelem_markup_in_list_unordelem2022);
		um=list_unordelem_markup();
		_fsp--;
		if (failed) return item;
		if ( backtracking==0 ) {
		  ++((CountLevel_scope)CountLevel_stack.peek()).level; ((CountLevel_scope)CountLevel_stack.peek()).currentMarkup = input.toString(um.start,um.stop);((CountLevel_scope)CountLevel_stack.peek()).groups += input.toString(um.start,um.stop);
		}
		pushFollow(FOLLOW_list_elem_in_list_unordelem2029);
		elem=list_elem();
		_fsp--;
		if (failed) return item;
		if ( backtracking==0 ) {
		   item = new UnorderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, elem);
		}

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
		CountLevel_stack.pop();

	}
	return item;
	}
	// $ANTLR end list_unordelem


	// $ANTLR start list_elem
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:367:1: list_elem returns [CollectionNode items = null] : (m= list_elem_markup )* c= list_elemcontent list_elemseparator ;
	public final CollectionNode list_elem() throws RecognitionException {
	CollectionNode items =	null;

	list_elem_markup_return m = null;

	CollectionNode c = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:368:2: ( (m= list_elem_markup )* c= list_elemcontent list_elemseparator )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:368:4: (m= list_elem_markup )* c= list_elemcontent list_elemseparator
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:368:4: (m= list_elem_markup )*
		loop71:
		do {
		int alt71=2;
		int LA71_0 = input.LA(1);

		if ( (LA71_0==STAR) ) {
			alt71=1;
		}
		else if ( (LA71_0==POUND) ) {
			alt71=1;
		}


		switch (alt71) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:368:6: m= list_elem_markup
			{
			pushFollow(FOLLOW_list_elem_markup_in_list_elem2054);
			m=list_elem_markup();
			_fsp--;
			if (failed) return items;
			if ( backtracking==0 ) {

							 ++((CountLevel_scope)CountLevel_stack.peek()).level;
							 if(!input.toString(m.start,m.stop).equals(((CountLevel_scope)CountLevel_stack.peek()).currentMarkup)) {					
						((CountLevel_scope)CountLevel_stack.peek()).groups+= GROUPING_SEPARATOR;
							 }
							 ((CountLevel_scope)CountLevel_stack.peek()).groups+= input.toString(m.start,m.stop);
							 ((CountLevel_scope)CountLevel_stack.peek()).currentMarkup = input.toString(m.start,m.stop);
						  
			}

			}
			break;

		default :
			break loop71;
		}
		} while (true);

		pushFollow(FOLLOW_list_elemcontent_in_list_elem2065);
		c=list_elemcontent();
		_fsp--;
		if (failed) return items;
		if ( backtracking==0 ) {
		  items = c; 
		}
		pushFollow(FOLLOW_list_elemseparator_in_list_elem2070);
		list_elemseparator();
		_fsp--;
		if (failed) return items;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end list_elem

	public static class list_elem_markup_return extends ParserRuleReturnScope {
	};

	// $ANTLR start list_elem_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:377:1: list_elem_markup : ( list_ordelem_markup | list_unordelem_markup );
	public final list_elem_markup_return list_elem_markup() throws RecognitionException {
	list_elem_markup_return retval = new list_elem_markup_return();
	retval.start = input.LT(1);

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:378:2: ( list_ordelem_markup | list_unordelem_markup )
		int alt72=2;
		int LA72_0 = input.LA(1);

		if ( (LA72_0==POUND) ) {
		alt72=1;
		}
		else if ( (LA72_0==STAR) ) {
		alt72=2;
		}
		else {
		if (backtracking>0) {failed=true; return retval;}
		NoViableAltException nvae =
			new NoViableAltException("377:1: list_elem_markup : ( list_ordelem_markup | list_unordelem_markup );", 72, 0, input);

		throw nvae;
		}
		switch (alt72) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:378:4: list_ordelem_markup
			{
			pushFollow(FOLLOW_list_ordelem_markup_in_list_elem_markup2080);
			list_ordelem_markup();
			_fsp--;
			if (failed) return retval;

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:379:4: list_unordelem_markup
			{
			pushFollow(FOLLOW_list_unordelem_markup_in_list_elem_markup2085);
			list_unordelem_markup();
			_fsp--;
			if (failed) return retval;

			}
			break;

		}
		retval.stop = input.LT(-1);

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return retval;
	}
	// $ANTLR end list_elem_markup


	// $ANTLR start list_elemcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:381:1: list_elemcontent returns [CollectionNode items = new CollectionNode()] : onestar (part= list_elemcontentpart onestar )* ;
	public final CollectionNode list_elemcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode part = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:382:2: ( onestar (part= list_elemcontentpart onestar )* )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:382:4: onestar (part= list_elemcontentpart onestar )*
		{
		pushFollow(FOLLOW_onestar_in_list_elemcontent2099);
		onestar();
		_fsp--;
		if (failed) return items;
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:382:13: (part= list_elemcontentpart onestar )*
		loop73:
		do {
		int alt73=2;
		int LA73_0 = input.LA(1);

		if ( ((LA73_0>=FORCED_END_OF_LINE && LA73_0<=WIKI)||(LA73_0>=POUND && LA73_0<=78)) ) {
			alt73=1;
		}


		switch (alt73) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:382:15: part= list_elemcontentpart onestar
			{
			pushFollow(FOLLOW_list_elemcontentpart_in_list_elemcontent2108);
			part=list_elemcontentpart();
			_fsp--;
			if (failed) return items;
			if ( backtracking==0 ) {
			   items.add(part); 
			}
			pushFollow(FOLLOW_onestar_in_list_elemcontent2113);
			onestar();
			_fsp--;
			if (failed) return items;

			}
			break;

		default :
			break loop73;
		}
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end list_elemcontent


	// $ANTLR start list_elemcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:384:1: list_elemcontentpart returns [ASTNode node = null] : (tuf= text_unformattedelement | tf= list_formatted_elem );
	public final ASTNode list_elemcontentpart() throws RecognitionException {
	ASTNode node =	null;

	ASTNode tuf = null;

	CollectionNode tf = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:385:2: (tuf= text_unformattedelement | tf= list_formatted_elem )
		int alt74=2;
		int LA74_0 = input.LA(1);

		if ( ((LA74_0>=FORCED_END_OF_LINE && LA74_0<=WIKI)||LA74_0==POUND||(LA74_0>=EQUAL && LA74_0<=PIPE)||(LA74_0>=LINK_OPEN && LA74_0<=78)) ) {
		alt74=1;
		}
		else if ( (LA74_0==STAR||LA74_0==ITAL) ) {
		alt74=2;
		}
		else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
			new NoViableAltException("384:1: list_elemcontentpart returns [ASTNode node = null] : (tuf= text_unformattedelement | tf= list_formatted_elem );", 74, 0, input);

		throw nvae;
		}
		switch (alt74) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:385:4: tuf= text_unformattedelement
			{
			pushFollow(FOLLOW_text_unformattedelement_in_list_elemcontentpart2134);
			tuf=text_unformattedelement();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			   
						if(tuf instanceof CollectionNode)
							node = new UnformattedTextNode(tuf);
						else
							node = tuf;
						
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:391:4: tf= list_formatted_elem
			{
			pushFollow(FOLLOW_list_formatted_elem_in_list_elemcontentpart2145);
			tf=list_formatted_elem();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			   node = new FormattedTextNode(tf);
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end list_elemcontentpart


	// $ANTLR start list_formatted_elem
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:393:1: list_formatted_elem returns [CollectionNode contents = new CollectionNode()] : ( bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )? | ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )? );
	public final CollectionNode list_formatted_elem() throws RecognitionException {
	CollectionNode contents =  new CollectionNode();

	ASTNode boldContents = null;

	ASTNode italContents = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:394:2: ( bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )? | ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )? )
		int alt79=2;
		int LA79_0 = input.LA(1);

		if ( (LA79_0==STAR) ) {
		alt79=1;
		}
		else if ( (LA79_0==ITAL) ) {
		alt79=2;
		}
		else {
		if (backtracking>0) {failed=true; return contents;}
		NoViableAltException nvae =
			new NoViableAltException("393:1: list_formatted_elem returns [CollectionNode contents = new CollectionNode()] : ( bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )? | ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )? );", 79, 0, input);

		throw nvae;
		}
		switch (alt79) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:394:4: bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )?
			{
			pushFollow(FOLLOW_bold_markup_in_list_formatted_elem2162);
			bold_markup();
			_fsp--;
			if (failed) return contents;
			pushFollow(FOLLOW_onestar_in_list_formatted_elem2165);
			onestar();
			_fsp--;
			if (failed) return contents;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:394:26: (boldContents= list_boldcontentpart onestar )*
			loop75:
			do {
			int alt75=2;
			switch ( input.LA(1) ) {
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case PIPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt75=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt75=1;
				}
				break;
			case ESCAPE:
				{
				alt75=1;
				}
				break;
			case LINK_OPEN:
				{
				alt75=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt75=1;
				}
				break;
			case EXTENSION:
				{
				alt75=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt75=1;
				}
				break;
			case ITAL:
				{
				alt75=1;
				}
				break;

			}

			switch (alt75) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:394:28: boldContents= list_boldcontentpart onestar
				{
				pushFollow(FOLLOW_list_boldcontentpart_in_list_formatted_elem2174);
				boldContents=list_boldcontentpart();
				_fsp--;
				if (failed) return contents;
				if ( backtracking==0 ) {
				   
									BoldTextNode add = null;
									if(boldContents instanceof CollectionNode){
										 add = new BoldTextNode(boldContents);			 
									}else{						
										CollectionNode c = new CollectionNode();
										c.add(boldContents);
										add = new BoldTextNode(c);			 
									}
									contents.add(add);						
									
				}
				pushFollow(FOLLOW_onestar_in_list_formatted_elem2184);
				onestar();
				_fsp--;
				if (failed) return contents;

				}
				break;

			default :
				break loop75;
			}
			} while (true);

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:406:3: ( bold_markup )?
			int alt76=2;
			int LA76_0 = input.LA(1);

			if ( (LA76_0==STAR) ) {
			int LA76_1 = input.LA(2);

			if ( (LA76_1==STAR) ) {
				alt76=1;
			}
			}
			switch (alt76) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:406:5: bold_markup
				{
				pushFollow(FOLLOW_bold_markup_in_list_formatted_elem2193);
				bold_markup();
				_fsp--;
				if (failed) return contents;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:407:4: ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )?
			{
			pushFollow(FOLLOW_ital_markup_in_list_formatted_elem2201);
			ital_markup();
			_fsp--;
			if (failed) return contents;
			pushFollow(FOLLOW_onestar_in_list_formatted_elem2206);
			onestar();
			_fsp--;
			if (failed) return contents;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:407:28: (italContents= list_italcontentpart onestar )*
			loop77:
			do {
			int alt77=2;
			switch ( input.LA(1) ) {
			case STAR:
				{
				alt77=1;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case PIPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt77=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt77=1;
				}
				break;
			case ESCAPE:
				{
				alt77=1;
				}
				break;
			case LINK_OPEN:
				{
				alt77=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt77=1;
				}
				break;
			case EXTENSION:
				{
				alt77=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt77=1;
				}
				break;

			}

			switch (alt77) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:407:30: italContents= list_italcontentpart onestar
				{
				pushFollow(FOLLOW_list_italcontentpart_in_list_formatted_elem2215);
				italContents=list_italcontentpart();
				_fsp--;
				if (failed) return contents;
				if ( backtracking==0 ) {

									ItalicTextNode add = null;
									if(italContents instanceof CollectionNode){
										add = new ItalicTextNode(italContents);
									}else{
										  CollectionNode c = new CollectionNode();
										  c.add(italContents);
										  add = new ItalicTextNode(c);
									}
									contents.add(add); 
									
				}
				pushFollow(FOLLOW_onestar_in_list_formatted_elem2224);
				onestar();
				_fsp--;
				if (failed) return contents;

				}
				break;

			default :
				break loop77;
			}
			} while (true);

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:418:3: ( ital_markup )?
			int alt78=2;
			int LA78_0 = input.LA(1);

			if ( (LA78_0==ITAL) ) {
			alt78=1;
			}
			switch (alt78) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:418:5: ital_markup
				{
				pushFollow(FOLLOW_ital_markup_in_list_formatted_elem2233);
				ital_markup();
				_fsp--;
				if (failed) return contents;

				}
				break;

			}


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return contents;
	}
	// $ANTLR end list_formatted_elem

	protected static class list_boldcontentpart_scope {
	List<ASTNode> elements;
	}
	protected Stack list_boldcontentpart_stack = new Stack();


	// $ANTLR start list_boldcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:421:1: list_boldcontentpart returns [ASTNode contents = null] : ( ital_markup c= list_bolditalcontent ( ital_markup )? | (t= text_unformattedelement )+ );
	public final ASTNode list_boldcontentpart() throws RecognitionException {
	list_boldcontentpart_stack.push(new list_boldcontentpart_scope());
	ASTNode contents =  null;

	ASTNode c = null;

	ASTNode t = null;



		((list_boldcontentpart_scope)list_boldcontentpart_stack.peek()).elements = new ArrayList<ASTNode>();		

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:428:2: ( ital_markup c= list_bolditalcontent ( ital_markup )? | (t= text_unformattedelement )+ )
		int alt82=2;
		int LA82_0 = input.LA(1);

		if ( (LA82_0==ITAL) ) {
		alt82=1;
		}
		else if ( ((LA82_0>=FORCED_END_OF_LINE && LA82_0<=WIKI)||LA82_0==POUND||(LA82_0>=EQUAL && LA82_0<=PIPE)||(LA82_0>=LINK_OPEN && LA82_0<=78)) ) {
		alt82=2;
		}
		else {
		if (backtracking>0) {failed=true; return contents;}
		NoViableAltException nvae =
			new NoViableAltException("421:1: list_boldcontentpart returns [ASTNode contents = null] : ( ital_markup c= list_bolditalcontent ( ital_markup )? | (t= text_unformattedelement )+ );", 82, 0, input);

		throw nvae;
		}
		switch (alt82) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:428:4: ital_markup c= list_bolditalcontent ( ital_markup )?
			{
			pushFollow(FOLLOW_ital_markup_in_list_boldcontentpart2259);
			ital_markup();
			_fsp--;
			if (failed) return contents;
			pushFollow(FOLLOW_list_bolditalcontent_in_list_boldcontentpart2266);
			c=list_bolditalcontent();
			_fsp--;
			if (failed) return contents;
			if ( backtracking==0 ) {
			  contents = new ItalicTextNode(c);
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:428:86: ( ital_markup )?
			int alt80=2;
			int LA80_0 = input.LA(1);

			if ( (LA80_0==ITAL) ) {
			alt80=1;
			}
			switch (alt80) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:428:88: ital_markup
				{
				pushFollow(FOLLOW_ital_markup_in_list_boldcontentpart2273);
				ital_markup();
				_fsp--;
				if (failed) return contents;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:429:4: (t= text_unformattedelement )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:429:4: (t= text_unformattedelement )+
			int cnt81=0;
			loop81:
			do {
			int alt81=2;
			switch ( input.LA(1) ) {
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case PIPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt81=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt81=1;
				}
				break;
			case ESCAPE:
				{
				alt81=1;
				}
				break;
			case LINK_OPEN:
				{
				alt81=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt81=1;
				}
				break;
			case EXTENSION:
				{
				alt81=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt81=1;
				}
				break;

			}

			switch (alt81) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:429:6: t= text_unformattedelement
				{
				pushFollow(FOLLOW_text_unformattedelement_in_list_boldcontentpart2288);
				t=text_unformattedelement();
				_fsp--;
				if (failed) return contents;
				if ( backtracking==0 ) {
				   ((list_boldcontentpart_scope)list_boldcontentpart_stack.peek()).elements.add(t); 
				}

				}
				break;

			default :
				if ( cnt81 >= 1 ) break loop81;
				if (backtracking>0) {failed=true; return contents;}
				EarlyExitException eee =
					new EarlyExitException(81, input);
				throw eee;
			}
			cnt81++;
			} while (true);

			if ( backtracking==0 ) {
			  contents = new CollectionNode(((list_boldcontentpart_scope)list_boldcontentpart_stack.peek()).elements); 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
		list_boldcontentpart_stack.pop();
	}
	return contents;
	}
	// $ANTLR end list_boldcontentpart


	// $ANTLR start list_bolditalcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:433:1: list_bolditalcontent returns [ASTNode text = null] : (t= text_unformattedelement )+ ;
	public final ASTNode list_bolditalcontent() throws RecognitionException {
	ASTNode text =	null;

	ASTNode t = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:434:2: ( (t= text_unformattedelement )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:434:4: (t= text_unformattedelement )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:434:4: (t= text_unformattedelement )+
		int cnt83=0;
		loop83:
		do {
		int alt83=2;
		switch ( input.LA(1) ) {
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case EQUAL:
		case PIPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
			{
			alt83=1;
			}
			break;
		case FORCED_LINEBREAK:
			{
			alt83=1;
			}
			break;
		case ESCAPE:
			{
			alt83=1;
			}
			break;
		case LINK_OPEN:
			{
			alt83=1;
			}
			break;
		case IMAGE_OPEN:
			{
			alt83=1;
			}
			break;
		case EXTENSION:
			{
			alt83=1;
			}
			break;
		case NOWIKI_OPEN:
			{
			alt83=1;
			}
			break;

		}

		switch (alt83) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:434:6: t= text_unformattedelement
			{
			pushFollow(FOLLOW_text_unformattedelement_in_list_bolditalcontent2320);
			t=text_unformattedelement();
			_fsp--;
			if (failed) return text;
			if ( backtracking==0 ) {
			   text = t; 
			}

			}
			break;

		default :
			if ( cnt83 >= 1 ) break loop83;
			if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
				new EarlyExitException(83, input);
			throw eee;
		}
		cnt83++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end list_bolditalcontent

	protected static class list_italcontentpart_scope {
	List<ASTNode> elements;
	}
	protected Stack list_italcontentpart_stack = new Stack();


	// $ANTLR start list_italcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:437:1: list_italcontentpart returns [ASTNode contents = null] : ( bold_markup c= list_bolditalcontent ( bold_markup )? | (t= text_unformattedelement )+ );
	public final ASTNode list_italcontentpart() throws RecognitionException {
	list_italcontentpart_stack.push(new list_italcontentpart_scope());
	ASTNode contents =  null;

	ASTNode c = null;

	ASTNode t = null;



		((list_italcontentpart_scope)list_italcontentpart_stack.peek()).elements = new ArrayList<ASTNode>();

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:444:2: ( bold_markup c= list_bolditalcontent ( bold_markup )? | (t= text_unformattedelement )+ )
		int alt86=2;
		int LA86_0 = input.LA(1);

		if ( (LA86_0==STAR) ) {
		alt86=1;
		}
		else if ( ((LA86_0>=FORCED_END_OF_LINE && LA86_0<=WIKI)||LA86_0==POUND||(LA86_0>=EQUAL && LA86_0<=PIPE)||(LA86_0>=LINK_OPEN && LA86_0<=78)) ) {
		alt86=2;
		}
		else {
		if (backtracking>0) {failed=true; return contents;}
		NoViableAltException nvae =
			new NoViableAltException("437:1: list_italcontentpart returns [ASTNode contents = null] : ( bold_markup c= list_bolditalcontent ( bold_markup )? | (t= text_unformattedelement )+ );", 86, 0, input);

		throw nvae;
		}
		switch (alt86) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:444:4: bold_markup c= list_bolditalcontent ( bold_markup )?
			{
			pushFollow(FOLLOW_bold_markup_in_list_italcontentpart2350);
			bold_markup();
			_fsp--;
			if (failed) return contents;
			pushFollow(FOLLOW_list_bolditalcontent_in_list_italcontentpart2357);
			c=list_bolditalcontent();
			_fsp--;
			if (failed) return contents;
			if ( backtracking==0 ) {
			   contents = new BoldTextNode(c); 
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:444:86: ( bold_markup )?
			int alt84=2;
			int LA84_0 = input.LA(1);

			if ( (LA84_0==STAR) ) {
			int LA84_1 = input.LA(2);

			if ( (LA84_1==STAR) ) {
				alt84=1;
			}
			}
			switch (alt84) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:444:88: bold_markup
				{
				pushFollow(FOLLOW_bold_markup_in_list_italcontentpart2364);
				bold_markup();
				_fsp--;
				if (failed) return contents;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:445:4: (t= text_unformattedelement )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:445:4: (t= text_unformattedelement )+
			int cnt85=0;
			loop85:
			do {
			int alt85=2;
			switch ( input.LA(1) ) {
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case PIPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt85=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt85=1;
				}
				break;
			case ESCAPE:
				{
				alt85=1;
				}
				break;
			case LINK_OPEN:
				{
				alt85=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt85=1;
				}
				break;
			case EXTENSION:
				{
				alt85=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt85=1;
				}
				break;

			}

			switch (alt85) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:445:6: t= text_unformattedelement
				{
				pushFollow(FOLLOW_text_unformattedelement_in_list_italcontentpart2378);
				t=text_unformattedelement();
				_fsp--;
				if (failed) return contents;
				if ( backtracking==0 ) {
				   ((list_italcontentpart_scope)list_italcontentpart_stack.peek()).elements.add(t); 
				}

				}
				break;

			default :
				if ( cnt85 >= 1 ) break loop85;
				if (backtracking>0) {failed=true; return contents;}
				EarlyExitException eee =
					new EarlyExitException(85, input);
				throw eee;
			}
			cnt85++;
			} while (true);

			if ( backtracking==0 ) {
			   contents = new CollectionNode(((list_italcontentpart_scope)list_italcontentpart_stack.peek()).elements); 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
		list_italcontentpart_stack.pop();
	}
	return contents;
	}
	// $ANTLR end list_italcontentpart


	// $ANTLR start table
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:449:1: table returns [TableNode table = new TableNode()] : (tr= table_row )+ ;
	public final TableNode table() throws RecognitionException {
	TableNode table =  new TableNode();

	CollectionNode tr = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:450:2: ( (tr= table_row )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:450:4: (tr= table_row )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:450:4: (tr= table_row )+
		int cnt87=0;
		loop87:
		do {
		int alt87=2;
		int LA87_0 = input.LA(1);

		if ( (LA87_0==PIPE) ) {
			alt87=1;
		}


		switch (alt87) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:450:6: tr= table_row
			{
			pushFollow(FOLLOW_table_row_in_table2408);
			tr=table_row();
			_fsp--;
			if (failed) return table;
			if ( backtracking==0 ) {
			  table.addChildASTNode(tr);
			}

			}
			break;

		default :
			if ( cnt87 >= 1 ) break loop87;
			if (backtracking>0) {failed=true; return table;}
			EarlyExitException eee =
				new EarlyExitException(87, input);
			throw eee;
		}
		cnt87++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return table;
	}
	// $ANTLR end table


	// $ANTLR start table_row
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:452:1: table_row returns [CollectionNode row = new CollectionNode()] : (tc= table_cell )+ table_rowseparator ;
	public final CollectionNode table_row() throws RecognitionException {
	CollectionNode row =  new CollectionNode();

	TableCellNode tc = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:453:2: ( (tc= table_cell )+ table_rowseparator )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:453:4: (tc= table_cell )+ table_rowseparator
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:453:4: (tc= table_cell )+
		int cnt88=0;
		loop88:
		do {
		int alt88=2;
		int LA88_0 = input.LA(1);

		if ( (LA88_0==PIPE) ) {
			alt88=1;
		}


		switch (alt88) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:453:6: tc= table_cell
			{
			pushFollow(FOLLOW_table_cell_in_table_row2434);
			tc=table_cell();
			_fsp--;
			if (failed) return row;
			if ( backtracking==0 ) {
			   row.add(tc); 
			}

			}
			break;

		default :
			if ( cnt88 >= 1 ) break loop88;
			if (backtracking>0) {failed=true; return row;}
			EarlyExitException eee =
				new EarlyExitException(88, input);
			throw eee;
		}
		cnt88++;
		} while (true);

		pushFollow(FOLLOW_table_rowseparator_in_table_row2442);
		table_rowseparator();
		_fsp--;
		if (failed) return row;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return row;
	}
	// $ANTLR end table_row


	// $ANTLR start table_cell
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:455:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );
	public final TableCellNode table_cell() throws RecognitionException {
	TableCellNode cell =  null;

	TableHeaderNode th = null;

	TableDataNode tc = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:456:2: ({...}?th= table_headercell | tc= table_normalcell )
		int alt89=2;
		int LA89_0 = input.LA(1);

		if ( (LA89_0==PIPE) ) {
		int LA89_1 = input.LA(2);

		if ( (LA89_1==EQUAL) ) {
			int LA89_2 = input.LA(3);

			if ( ( input.LA(2) == EQUAL ) ) {
			alt89=1;
			}
			else if ( (true) ) {
			alt89=2;
			}
			else {
			if (backtracking>0) {failed=true; return cell;}
			NoViableAltException nvae =
				new NoViableAltException("455:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );", 89, 2, input);

			throw nvae;
			}
		}
		else if ( (LA89_1==EOF||(LA89_1>=FORCED_END_OF_LINE && LA89_1<=STAR)||(LA89_1>=PIPE && LA89_1<=78)) ) {
			alt89=2;
		}
		else {
			if (backtracking>0) {failed=true; return cell;}
			NoViableAltException nvae =
			new NoViableAltException("455:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );", 89, 1, input);

			throw nvae;
		}
		}
		else {
		if (backtracking>0) {failed=true; return cell;}
		NoViableAltException nvae =
			new NoViableAltException("455:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );", 89, 0, input);

		throw nvae;
		}
		switch (alt89) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:456:4: {...}?th= table_headercell
			{
			if ( !( input.LA(2) == EQUAL ) ) {
			if (backtracking>0) {failed=true; return cell;}
			throw new FailedPredicateException(input, "table_cell", " input.LA(2) == EQUAL ");
			}
			pushFollow(FOLLOW_table_headercell_in_table_cell2463);
			th=table_headercell();
			_fsp--;
			if (failed) return cell;
			if ( backtracking==0 ) {
			  cell = th;
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:457:4: tc= table_normalcell
			{
			pushFollow(FOLLOW_table_normalcell_in_table_cell2474);
			tc=table_normalcell();
			_fsp--;
			if (failed) return cell;
			if ( backtracking==0 ) {
			  cell = tc; 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return cell;
	}
	// $ANTLR end table_cell


	// $ANTLR start table_headercell
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:459:1: table_headercell returns [TableHeaderNode header = null] : table_headercell_markup tc= table_cellcontent ;
	public final TableHeaderNode table_headercell() throws RecognitionException {
	TableHeaderNode header =  null;

	CollectionNode tc = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:460:2: ( table_headercell_markup tc= table_cellcontent )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:460:4: table_headercell_markup tc= table_cellcontent
		{
		pushFollow(FOLLOW_table_headercell_markup_in_table_headercell2490);
		table_headercell_markup();
		_fsp--;
		if (failed) return header;
		pushFollow(FOLLOW_table_cellcontent_in_table_headercell2497);
		tc=table_cellcontent();
		_fsp--;
		if (failed) return header;
		if ( backtracking==0 ) {
		  header = new TableHeaderNode(tc);
		}

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return header;
	}
	// $ANTLR end table_headercell


	// $ANTLR start table_normalcell
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:462:1: table_normalcell returns [TableDataNode cell = null] : table_cell_markup tc= table_cellcontent ;
	public final TableDataNode table_normalcell() throws RecognitionException {
	TableDataNode cell =  null;

	CollectionNode tc = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:463:2: ( table_cell_markup tc= table_cellcontent )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:463:4: table_cell_markup tc= table_cellcontent
		{
		pushFollow(FOLLOW_table_cell_markup_in_table_normalcell2513);
		table_cell_markup();
		_fsp--;
		if (failed) return cell;
		pushFollow(FOLLOW_table_cellcontent_in_table_normalcell2520);
		tc=table_cellcontent();
		_fsp--;
		if (failed) return cell;
		if ( backtracking==0 ) {
		   cell = new TableDataNode(tc); 
		}

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return cell;
	}
	// $ANTLR end table_normalcell


	// $ANTLR start table_cellcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:465:1: table_cellcontent returns [CollectionNode items = new CollectionNode()] : onestar (tcp= table_cellcontentpart onestar )* ;
	public final CollectionNode table_cellcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode tcp = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:466:2: ( onestar (tcp= table_cellcontentpart onestar )* )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:466:4: onestar (tcp= table_cellcontentpart onestar )*
		{
		pushFollow(FOLLOW_onestar_in_table_cellcontent2536);
		onestar();
		_fsp--;
		if (failed) return items;
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:466:13: (tcp= table_cellcontentpart onestar )*
		loop90:
		do {
		int alt90=2;
		int LA90_0 = input.LA(1);

		if ( ((LA90_0>=FORCED_END_OF_LINE && LA90_0<=WIKI)||(LA90_0>=POUND && LA90_0<=EQUAL)||(LA90_0>=ITAL && LA90_0<=78)) ) {
			alt90=1;
		}


		switch (alt90) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:466:15: tcp= table_cellcontentpart onestar
			{
			pushFollow(FOLLOW_table_cellcontentpart_in_table_cellcontent2545);
			tcp=table_cellcontentpart();
			_fsp--;
			if (failed) return items;
			if ( backtracking==0 ) {
			  items.add(tcp); 
			}
			pushFollow(FOLLOW_onestar_in_table_cellcontent2550);
			onestar();
			_fsp--;
			if (failed) return items;

			}
			break;

		default :
			break loop90;
		}
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end table_cellcontent


	// $ANTLR start table_cellcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:468:1: table_cellcontentpart returns [ASTNode node = null] : (tf= table_formattedelement | tu= table_unformattedelement );
	public final ASTNode table_cellcontentpart() throws RecognitionException {
	ASTNode node =	null;

	ASTNode tf = null;

	ASTNode tu = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:469:2: (tf= table_formattedelement | tu= table_unformattedelement )
		int alt91=2;
		int LA91_0 = input.LA(1);

		if ( (LA91_0==STAR||LA91_0==ITAL) ) {
		alt91=1;
		}
		else if ( ((LA91_0>=FORCED_END_OF_LINE && LA91_0<=WIKI)||LA91_0==POUND||LA91_0==EQUAL||(LA91_0>=LINK_OPEN && LA91_0<=78)) ) {
		alt91=2;
		}
		else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
			new NoViableAltException("468:1: table_cellcontentpart returns [ASTNode node = null] : (tf= table_formattedelement | tu= table_unformattedelement );", 91, 0, input);

		throw nvae;
		}
		switch (alt91) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:469:4: tf= table_formattedelement
			{
			pushFollow(FOLLOW_table_formattedelement_in_table_cellcontentpart2571);
			tf=table_formattedelement();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node =tf;
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:470:4: tu= table_unformattedelement
			{
			pushFollow(FOLLOW_table_unformattedelement_in_table_cellcontentpart2582);
			tu=table_unformattedelement();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node =tu;
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end table_cellcontentpart


	// $ANTLR start table_formattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:472:1: table_formattedelement returns [ASTNode content = null] : ( ital_markup (tic= table_italcontent )? ( ital_markup )? | bold_markup (tbc= table_boldcontent )? ( bold_markup )? );
	public final ASTNode table_formattedelement() throws RecognitionException {
	ASTNode content =  null;

	CollectionNode tic = null;

	CollectionNode tbc = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:473:2: ( ital_markup (tic= table_italcontent )? ( ital_markup )? | bold_markup (tbc= table_boldcontent )? ( bold_markup )? )
		int alt96=2;
		int LA96_0 = input.LA(1);

		if ( (LA96_0==ITAL) ) {
		alt96=1;
		}
		else if ( (LA96_0==STAR) ) {
		alt96=2;
		}
		else {
		if (backtracking>0) {failed=true; return content;}
		NoViableAltException nvae =
			new NoViableAltException("472:1: table_formattedelement returns [ASTNode content = null] : ( ital_markup (tic= table_italcontent )? ( ital_markup )? | bold_markup (tbc= table_boldcontent )? ( bold_markup )? );", 96, 0, input);

		throw nvae;
		}
		switch (alt96) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:473:4: ital_markup (tic= table_italcontent )? ( ital_markup )?
			{
			pushFollow(FOLLOW_ital_markup_in_table_formattedelement2598);
			ital_markup();
			_fsp--;
			if (failed) return content;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:473:18: (tic= table_italcontent )?
			int alt92=2;
			switch ( input.LA(1) ) {
			case STAR:
				{
				alt92=1;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt92=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt92=1;
				}
				break;
			case ESCAPE:
				{
				alt92=1;
				}
				break;
			case LINK_OPEN:
				{
				alt92=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt92=1;
				}
				break;
			case EXTENSION:
				{
				alt92=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt92=1;
				}
				break;
			case EOF:
				{
				alt92=1;
				}
				break;
			}

			switch (alt92) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:473:20: tic= table_italcontent
				{
				pushFollow(FOLLOW_table_italcontent_in_table_formattedelement2608);
				tic=table_italcontent();
				_fsp--;
				if (failed) return content;
				if ( backtracking==0 ) {
				   content = new ItalicTextNode(tic); 
				}

				}
				break;

			}

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:473:94: ( ital_markup )?
			int alt93=2;
			int LA93_0 = input.LA(1);

			if ( (LA93_0==ITAL) ) {
			alt93=1;
			}
			switch (alt93) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:473:96: ital_markup
				{
				pushFollow(FOLLOW_ital_markup_in_table_formattedelement2617);
				ital_markup();
				_fsp--;
				if (failed) return content;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:474:4: bold_markup (tbc= table_boldcontent )? ( bold_markup )?
			{
			pushFollow(FOLLOW_bold_markup_in_table_formattedelement2625);
			bold_markup();
			_fsp--;
			if (failed) return content;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:474:16: (tbc= table_boldcontent )?
			int alt94=2;
			switch ( input.LA(1) ) {
			case STAR:
				{
				int LA94_1 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
				alt94=1;
				}
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt94=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt94=1;
				}
				break;
			case ESCAPE:
				{
				alt94=1;
				}
				break;
			case LINK_OPEN:
				{
				alt94=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt94=1;
				}
				break;
			case EXTENSION:
				{
				alt94=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt94=1;
				}
				break;
			case ITAL:
				{
				alt94=1;
				}
				break;
			case EOF:
				{
				alt94=1;
				}
				break;
			}

			switch (alt94) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:474:18: tbc= table_boldcontent
				{
				pushFollow(FOLLOW_table_boldcontent_in_table_formattedelement2632);
				tbc=table_boldcontent();
				_fsp--;
				if (failed) return content;
				if ( backtracking==0 ) {
				  content = new BoldTextNode(tbc);
				}

				}
				break;

			}

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:474:88: ( bold_markup )?
			int alt95=2;
			int LA95_0 = input.LA(1);

			if ( (LA95_0==STAR) ) {
			int LA95_1 = input.LA(2);

			if ( (LA95_1==STAR) ) {
				alt95=1;
			}
			}
			switch (alt95) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:474:90: bold_markup
				{
				pushFollow(FOLLOW_bold_markup_in_table_formattedelement2642);
				bold_markup();
				_fsp--;
				if (failed) return content;

				}
				break;

			}


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return content;
	}
	// $ANTLR end table_formattedelement


	// $ANTLR start table_boldcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:476:1: table_boldcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (tb= table_boldcontentpart onestar )+ | EOF );
	public final CollectionNode table_boldcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode tb = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:477:2: ( onestar (tb= table_boldcontentpart onestar )+ | EOF )
		int alt98=2;
		int LA98_0 = input.LA(1);

		if ( ((LA98_0>=FORCED_END_OF_LINE && LA98_0<=WIKI)||(LA98_0>=POUND && LA98_0<=EQUAL)||(LA98_0>=ITAL && LA98_0<=78)) ) {
		alt98=1;
		}
		else if ( (LA98_0==EOF) ) {
		alt98=2;
		}
		else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
			new NoViableAltException("476:1: table_boldcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (tb= table_boldcontentpart onestar )+ | EOF );", 98, 0, input);

		throw nvae;
		}
		switch (alt98) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:477:4: onestar (tb= table_boldcontentpart onestar )+
			{
			pushFollow(FOLLOW_onestar_in_table_boldcontent2659);
			onestar();
			_fsp--;
			if (failed) return items;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:477:13: (tb= table_boldcontentpart onestar )+
			int cnt97=0;
			loop97:
			do {
			int alt97=2;
			switch ( input.LA(1) ) {
			case ITAL:
				{
				alt97=1;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt97=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt97=1;
				}
				break;
			case ESCAPE:
				{
				alt97=1;
				}
				break;
			case LINK_OPEN:
				{
				alt97=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt97=1;
				}
				break;
			case EXTENSION:
				{
				alt97=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt97=1;
				}
				break;

			}

			switch (alt97) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:477:15: tb= table_boldcontentpart onestar
				{
				pushFollow(FOLLOW_table_boldcontentpart_in_table_boldcontent2668);
				tb=table_boldcontentpart();
				_fsp--;
				if (failed) return items;
				if ( backtracking==0 ) {
				   items.add(tb); 
				}
				pushFollow(FOLLOW_onestar_in_table_boldcontent2673);
				onestar();
				_fsp--;
				if (failed) return items;

				}
				break;

			default :
				if ( cnt97 >= 1 ) break loop97;
				if (backtracking>0) {failed=true; return items;}
				EarlyExitException eee =
					new EarlyExitException(97, input);
				throw eee;
			}
			cnt97++;
			} while (true);


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:478:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_table_boldcontent2681); if (failed) return items;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end table_boldcontent


	// $ANTLR start table_italcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:480:1: table_italcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (ti= table_italcontentpart onestar )+ | EOF );
	public final CollectionNode table_italcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode ti = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:481:2: ( onestar (ti= table_italcontentpart onestar )+ | EOF )
		int alt100=2;
		int LA100_0 = input.LA(1);

		if ( ((LA100_0>=FORCED_END_OF_LINE && LA100_0<=WIKI)||(LA100_0>=POUND && LA100_0<=EQUAL)||(LA100_0>=LINK_OPEN && LA100_0<=78)) ) {
		alt100=1;
		}
		else if ( (LA100_0==EOF) ) {
		alt100=2;
		}
		else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
			new NoViableAltException("480:1: table_italcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (ti= table_italcontentpart onestar )+ | EOF );", 100, 0, input);

		throw nvae;
		}
		switch (alt100) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:481:4: onestar (ti= table_italcontentpart onestar )+
			{
			pushFollow(FOLLOW_onestar_in_table_italcontent2695);
			onestar();
			_fsp--;
			if (failed) return items;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:481:13: (ti= table_italcontentpart onestar )+
			int cnt99=0;
			loop99:
			do {
			int alt99=2;
			switch ( input.LA(1) ) {
			case STAR:
				{
				alt99=1;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt99=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt99=1;
				}
				break;
			case ESCAPE:
				{
				alt99=1;
				}
				break;
			case LINK_OPEN:
				{
				alt99=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt99=1;
				}
				break;
			case EXTENSION:
				{
				alt99=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt99=1;
				}
				break;

			}

			switch (alt99) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:481:15: ti= table_italcontentpart onestar
				{
				pushFollow(FOLLOW_table_italcontentpart_in_table_italcontent2704);
				ti=table_italcontentpart();
				_fsp--;
				if (failed) return items;
				if ( backtracking==0 ) {
				   items.add(ti); 
				}
				pushFollow(FOLLOW_onestar_in_table_italcontent2709);
				onestar();
				_fsp--;
				if (failed) return items;

				}
				break;

			default :
				if ( cnt99 >= 1 ) break loop99;
				if (backtracking>0) {failed=true; return items;}
				EarlyExitException eee =
					new EarlyExitException(99, input);
				throw eee;
			}
			cnt99++;
			} while (true);


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:482:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_table_italcontent2717); if (failed) return items;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end table_italcontent


	// $ANTLR start table_boldcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:484:1: table_boldcontentpart returns [ASTNode node = null] : (tf= table_formattedcontent | ital_markup tb= table_bolditalcontent ( ital_markup )? );
	public final ASTNode table_boldcontentpart() throws RecognitionException {
	ASTNode node =	null;

	CollectionNode tf = null;

	CollectionNode tb = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:485:2: (tf= table_formattedcontent | ital_markup tb= table_bolditalcontent ( ital_markup )? )
		int alt102=2;
		int LA102_0 = input.LA(1);

		if ( ((LA102_0>=FORCED_END_OF_LINE && LA102_0<=WIKI)||LA102_0==POUND||LA102_0==EQUAL||(LA102_0>=LINK_OPEN && LA102_0<=78)) ) {
		alt102=1;
		}
		else if ( (LA102_0==ITAL) ) {
		alt102=2;
		}
		else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
			new NoViableAltException("484:1: table_boldcontentpart returns [ASTNode node = null] : (tf= table_formattedcontent | ital_markup tb= table_bolditalcontent ( ital_markup )? );", 102, 0, input);

		throw nvae;
		}
		switch (alt102) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:485:4: tf= table_formattedcontent
			{
			pushFollow(FOLLOW_table_formattedcontent_in_table_boldcontentpart2735);
			tf=table_formattedcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node = tf; 
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:486:4: ital_markup tb= table_bolditalcontent ( ital_markup )?
			{
			pushFollow(FOLLOW_ital_markup_in_table_boldcontentpart2742);
			ital_markup();
			_fsp--;
			if (failed) return node;
			pushFollow(FOLLOW_table_bolditalcontent_in_table_boldcontentpart2749);
			tb=table_bolditalcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			   node = new ItalicTextNode(tb);  
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:486:92: ( ital_markup )?
			int alt101=2;
			int LA101_0 = input.LA(1);

			if ( (LA101_0==ITAL) ) {
			alt101=1;
			}
			switch (alt101) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:486:94: ital_markup
				{
				pushFollow(FOLLOW_ital_markup_in_table_boldcontentpart2756);
				ital_markup();
				_fsp--;
				if (failed) return node;

				}
				break;

			}


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end table_boldcontentpart


	// $ANTLR start table_italcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:488:1: table_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= table_bolditalcontent ( bold_markup )? | tf= table_formattedcontent );
	public final ASTNode table_italcontentpart() throws RecognitionException {
	ASTNode node =	null;

	CollectionNode tb = null;

	CollectionNode tf = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:489:2: ( bold_markup tb= table_bolditalcontent ( bold_markup )? | tf= table_formattedcontent )
		int alt104=2;
		int LA104_0 = input.LA(1);

		if ( (LA104_0==STAR) ) {
		alt104=1;
		}
		else if ( ((LA104_0>=FORCED_END_OF_LINE && LA104_0<=WIKI)||LA104_0==POUND||LA104_0==EQUAL||(LA104_0>=LINK_OPEN && LA104_0<=78)) ) {
		alt104=2;
		}
		else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
			new NoViableAltException("488:1: table_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= table_bolditalcontent ( bold_markup )? | tf= table_formattedcontent );", 104, 0, input);

		throw nvae;
		}
		switch (alt104) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:489:4: bold_markup tb= table_bolditalcontent ( bold_markup )?
			{
			pushFollow(FOLLOW_bold_markup_in_table_italcontentpart2773);
			bold_markup();
			_fsp--;
			if (failed) return node;
			pushFollow(FOLLOW_table_bolditalcontent_in_table_italcontentpart2780);
			tb=table_bolditalcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node = new BoldTextNode(tb); 
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:489:88: ( bold_markup )?
			int alt103=2;
			int LA103_0 = input.LA(1);

			if ( (LA103_0==STAR) ) {
			int LA103_1 = input.LA(2);

			if ( (LA103_1==STAR) ) {
				alt103=1;
			}
			}
			switch (alt103) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:489:90: bold_markup
				{
				pushFollow(FOLLOW_bold_markup_in_table_italcontentpart2787);
				bold_markup();
				_fsp--;
				if (failed) return node;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:490:4: tf= table_formattedcontent
			{
			pushFollow(FOLLOW_table_formattedcontent_in_table_italcontentpart2799);
			tf=table_formattedcontent();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			   node = tf; 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end table_italcontentpart


	// $ANTLR start table_bolditalcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:492:1: table_bolditalcontent returns [CollectionNode elements = null] : ( onestar (tfc= table_formattedcontent onestar )? | EOF );
	public final CollectionNode table_bolditalcontent() throws RecognitionException {
	CollectionNode elements =  null;

	CollectionNode tfc = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:493:2: ( onestar (tfc= table_formattedcontent onestar )? | EOF )
		int alt106=2;
		int LA106_0 = input.LA(1);

		if ( ((LA106_0>=FORCED_END_OF_LINE && LA106_0<=EQUAL)||(LA106_0>=ITAL && LA106_0<=78)) ) {
		alt106=1;
		}
		else if ( (LA106_0==EOF||LA106_0==PIPE) ) {
		alt106=1;
		}
		else {
		if (backtracking>0) {failed=true; return elements;}
		NoViableAltException nvae =
			new NoViableAltException("492:1: table_bolditalcontent returns [CollectionNode elements = null] : ( onestar (tfc= table_formattedcontent onestar )? | EOF );", 106, 0, input);

		throw nvae;
		}
		switch (alt106) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:493:4: onestar (tfc= table_formattedcontent onestar )?
			{
			pushFollow(FOLLOW_onestar_in_table_bolditalcontent2815);
			onestar();
			_fsp--;
			if (failed) return elements;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:493:13: (tfc= table_formattedcontent onestar )?
			int alt105=2;
			switch ( input.LA(1) ) {
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt105=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt105=1;
				}
				break;
			case ESCAPE:
				{
				alt105=1;
				}
				break;
			case LINK_OPEN:
				{
				alt105=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt105=1;
				}
				break;
			case EXTENSION:
				{
				alt105=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt105=1;
				}
				break;
			}

			switch (alt105) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:493:15: tfc= table_formattedcontent onestar
				{
				pushFollow(FOLLOW_table_formattedcontent_in_table_bolditalcontent2824);
				tfc=table_formattedcontent();
				_fsp--;
				if (failed) return elements;
				if ( backtracking==0 ) {
				   elements = tfc; 
				}
				pushFollow(FOLLOW_onestar_in_table_bolditalcontent2829);
				onestar();
				_fsp--;
				if (failed) return elements;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:494:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_table_bolditalcontent2837); if (failed) return elements;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return elements;
	}
	// $ANTLR end table_bolditalcontent


	// $ANTLR start table_formattedcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:496:1: table_formattedcontent returns [CollectionNode elements = new CollectionNode()] : (tu= table_unformattedelement )+ ;
	public final CollectionNode table_formattedcontent() throws RecognitionException {
	CollectionNode elements =  new CollectionNode();

	ASTNode tu = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:497:2: ( (tu= table_unformattedelement )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:497:4: (tu= table_unformattedelement )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:497:4: (tu= table_unformattedelement )+
		int cnt107=0;
		loop107:
		do {
		int alt107=2;
		switch ( input.LA(1) ) {
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case EQUAL:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
			{
			alt107=1;
			}
			break;
		case FORCED_LINEBREAK:
			{
			alt107=1;
			}
			break;
		case ESCAPE:
			{
			alt107=1;
			}
			break;
		case LINK_OPEN:
			{
			alt107=1;
			}
			break;
		case IMAGE_OPEN:
			{
			alt107=1;
			}
			break;
		case EXTENSION:
			{
			alt107=1;
			}
			break;
		case NOWIKI_OPEN:
			{
			alt107=1;
			}
			break;

		}

		switch (alt107) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:497:6: tu= table_unformattedelement
			{
			pushFollow(FOLLOW_table_unformattedelement_in_table_formattedcontent2857);
			tu=table_unformattedelement();
			_fsp--;
			if (failed) return elements;
			if ( backtracking==0 ) {
			   elements.add(tu); 
			}

			}
			break;

		default :
			if ( cnt107 >= 1 ) break loop107;
			if (backtracking>0) {failed=true; return elements;}
			EarlyExitException eee =
				new EarlyExitException(107, input);
			throw eee;
		}
		cnt107++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return elements;
	}
	// $ANTLR end table_formattedcontent


	// $ANTLR start table_unformattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:499:1: table_unformattedelement returns [ASTNode content = null] : (tu= table_unformatted | ti= table_inlineelement );
	public final ASTNode table_unformattedelement() throws RecognitionException {
	ASTNode content =  null;

	CollectionNode tu = null;

	ASTNode ti = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:500:2: (tu= table_unformatted | ti= table_inlineelement )
		int alt108=2;
		int LA108_0 = input.LA(1);

		if ( ((LA108_0>=FORCED_END_OF_LINE && LA108_0<=WIKI)||LA108_0==POUND||LA108_0==EQUAL||(LA108_0>=FORCED_LINEBREAK && LA108_0<=78)) ) {
		alt108=1;
		}
		else if ( ((LA108_0>=LINK_OPEN && LA108_0<=EXTENSION)) ) {
		alt108=2;
		}
		else {
		if (backtracking>0) {failed=true; return content;}
		NoViableAltException nvae =
			new NoViableAltException("499:1: table_unformattedelement returns [ASTNode content = null] : (tu= table_unformatted | ti= table_inlineelement );", 108, 0, input);

		throw nvae;
		}
		switch (alt108) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:500:4: tu= table_unformatted
			{
			pushFollow(FOLLOW_table_unformatted_in_table_unformattedelement2880);
			tu=table_unformatted();
			_fsp--;
			if (failed) return content;
			if ( backtracking==0 ) {
			  content = new UnformattedTextNode(tu);
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:501:4: ti= table_inlineelement
			{
			pushFollow(FOLLOW_table_inlineelement_in_table_unformattedelement2892);
			ti=table_inlineelement();
			_fsp--;
			if (failed) return content;
			if ( backtracking==0 ) {
			  content = ti;
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return content;
	}
	// $ANTLR end table_unformattedelement


	// $ANTLR start table_inlineelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:503:1: table_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension | nw= nowiki_inline );
	public final ASTNode table_inlineelement() throws RecognitionException {
	ASTNode element =  null;

	LinkNode l = null;

	ImageNode i = null;

	ASTNode e = null;

	NoWikiSectionNode nw = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:504:2: (l= link | i= image | e= extension | nw= nowiki_inline )
		int alt109=4;
		switch ( input.LA(1) ) {
		case LINK_OPEN:
		{
		alt109=1;
		}
		break;
		case IMAGE_OPEN:
		{
		alt109=2;
		}
		break;
		case EXTENSION:
		{
		alt109=3;
		}
		break;
		case NOWIKI_OPEN:
		{
		alt109=4;
		}
		break;
		default:
		if (backtracking>0) {failed=true; return element;}
		NoViableAltException nvae =
			new NoViableAltException("503:1: table_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension | nw= nowiki_inline );", 109, 0, input);

		throw nvae;
		}

		switch (alt109) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:504:4: l= link
			{
			pushFollow(FOLLOW_link_in_table_inlineelement2913);
			l=link();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = l; 
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:505:4: i= image
			{
			pushFollow(FOLLOW_image_in_table_inlineelement2923);
			i=image();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = i; 
			}

			}
			break;
		case 3 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:506:4: e= extension
			{
			pushFollow(FOLLOW_extension_in_table_inlineelement2934);
			e=extension();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = e; 
			}

			}
			break;
		case 4 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:507:4: nw= nowiki_inline
			{
			pushFollow(FOLLOW_nowiki_inline_in_table_inlineelement2944);
			nw=nowiki_inline();
			_fsp--;
			if (failed) return element;
			if ( backtracking==0 ) {
			  element = nw; 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return element;
	}
	// $ANTLR end table_inlineelement


	// $ANTLR start table_unformatted
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:509:1: table_unformatted returns [CollectionNode text = new CollectionNode()] : (t= table_unformatted_text | ( forced_linebreak | e= escaped )+ );
	public final CollectionNode table_unformatted() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	StringBundler t = null;

	ScapedNode e = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:510:2: (t= table_unformatted_text | ( forced_linebreak | e= escaped )+ )
		int alt111=2;
		int LA111_0 = input.LA(1);

		if ( ((LA111_0>=FORCED_END_OF_LINE && LA111_0<=WIKI)||LA111_0==POUND||LA111_0==EQUAL||(LA111_0>=NOWIKI_BLOCK_CLOSE && LA111_0<=78)) ) {
		alt111=1;
		}
		else if ( ((LA111_0>=FORCED_LINEBREAK && LA111_0<=ESCAPE)) ) {
		alt111=2;
		}
		else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
			new NoViableAltException("509:1: table_unformatted returns [CollectionNode text = new CollectionNode()] : (t= table_unformatted_text | ( forced_linebreak | e= escaped )+ );", 111, 0, input);

		throw nvae;
		}
		switch (alt111) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:510:5: t= table_unformatted_text
			{
			pushFollow(FOLLOW_table_unformatted_text_in_table_unformatted2966);
			t=table_unformatted_text();
			_fsp--;
			if (failed) return text;
			if ( backtracking==0 ) {
			   text.add(new UnformattedTextNode(t.toString()));
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:511:5: ( forced_linebreak | e= escaped )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:511:5: ( forced_linebreak | e= escaped )+
			int cnt110=0;
			loop110:
			do {
			int alt110=3;
			int LA110_0 = input.LA(1);

			if ( (LA110_0==FORCED_LINEBREAK) ) {
				alt110=1;
			}
			else if ( (LA110_0==ESCAPE) ) {
				alt110=2;
			}


			switch (alt110) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:511:6: forced_linebreak
				{
				pushFollow(FOLLOW_forced_linebreak_in_table_unformatted2975);
				forced_linebreak();
				_fsp--;
				if (failed) return text;
				if ( backtracking==0 ) {
				  text.add(new ForcedEndOfLineNode());
				}

				}
				break;
			case 2 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:512:5: e= escaped
				{
				pushFollow(FOLLOW_escaped_in_table_unformatted2988);
				e=escaped();
				_fsp--;
				if (failed) return text;
				if ( backtracking==0 ) {
				  text.add(e);
				}

				}
				break;

			default :
				if ( cnt110 >= 1 ) break loop110;
				if (backtracking>0) {failed=true; return text;}
				EarlyExitException eee =
					new EarlyExitException(110, input);
				throw eee;
			}
			cnt110++;
			} while (true);


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end table_unformatted


	// $ANTLR start table_unformatted_text
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:515:1: table_unformatted_text returns [StringBundler text = new StringBundler()] : (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
	public final StringBundler table_unformatted_text() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:516:2: ( (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:516:4: (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:516:4: (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
		int cnt112=0;
		loop112:
		do {
		int alt112=2;
		int LA112_0 = input.LA(1);

		if ( ((LA112_0>=FORCED_END_OF_LINE && LA112_0<=WIKI)||LA112_0==POUND||LA112_0==EQUAL||(LA112_0>=NOWIKI_BLOCK_CLOSE && LA112_0<=78)) ) {
			alt112=1;
		}


		switch (alt112) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:516:6: c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
			{
			c=(Token)input.LT(1);
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||input.LA(1)==EQUAL||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_table_unformatted_text3014);	 throw mse;
			}

			if ( backtracking==0 ) {
			  text.append(c.getText());
			}

			}
			break;

		default :
			if ( cnt112 >= 1 ) break loop112;
			if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
				new EarlyExitException(112, input);
			throw eee;
		}
		cnt112++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end table_unformatted_text


	// $ANTLR start nowiki_block
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:530:1: nowiki_block returns [NoWikiSectionNode nowikiNode] : nowikiblock_open_markup contents= nowiki_block_contents nowikiblock_close_markup paragraph_separator ;
	public final NoWikiSectionNode nowiki_block() throws RecognitionException {
	NoWikiSectionNode nowikiNode = null;

	nowiki_block_contents_return contents = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:531:2: ( nowikiblock_open_markup contents= nowiki_block_contents nowikiblock_close_markup paragraph_separator )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:531:4: nowikiblock_open_markup contents= nowiki_block_contents nowikiblock_close_markup paragraph_separator
		{
		pushFollow(FOLLOW_nowikiblock_open_markup_in_nowiki_block3111);
		nowikiblock_open_markup();
		_fsp--;
		if (failed) return nowikiNode;
		pushFollow(FOLLOW_nowiki_block_contents_in_nowiki_block3118);
		contents=nowiki_block_contents();
		_fsp--;
		if (failed) return nowikiNode;
		if ( backtracking==0 ) {
		  nowikiNode = new NoWikiSectionNode(input.toString(contents.start,contents.stop).toString());
		}
		pushFollow(FOLLOW_nowikiblock_close_markup_in_nowiki_block3124);
		nowikiblock_close_markup();
		_fsp--;
		if (failed) return nowikiNode;
		pushFollow(FOLLOW_paragraph_separator_in_nowiki_block3127);
		paragraph_separator();
		_fsp--;
		if (failed) return nowikiNode;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return nowikiNode;
	}
	// $ANTLR end nowiki_block


	// $ANTLR start nowikiblock_open_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:535:1: nowikiblock_open_markup : nowiki_open_markup newline ;
	public final void nowikiblock_open_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:536:2: ( nowiki_open_markup newline )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:536:4: nowiki_open_markup newline
		{
		pushFollow(FOLLOW_nowiki_open_markup_in_nowikiblock_open_markup3142);
		nowiki_open_markup();
		_fsp--;
		if (failed) return ;
		pushFollow(FOLLOW_newline_in_nowikiblock_open_markup3145);
		newline();
		_fsp--;
		if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end nowikiblock_open_markup


	// $ANTLR start nowikiblock_close_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:539:1: nowikiblock_close_markup : NOWIKI_BLOCK_CLOSE ;
	public final void nowikiblock_close_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:540:2: ( NOWIKI_BLOCK_CLOSE )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:540:4: NOWIKI_BLOCK_CLOSE
		{
		match(input,NOWIKI_BLOCK_CLOSE,FOLLOW_NOWIKI_BLOCK_CLOSE_in_nowikiblock_close_markup3157); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end nowikiblock_close_markup


	// $ANTLR start nowiki_inline
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:543:1: nowiki_inline returns [NoWikiSectionNode nowiki = null] : nowiki_open_markup t= nowiki_inline_contents nowiki_close_markup ;
	public final NoWikiSectionNode nowiki_inline() throws RecognitionException {
	NoWikiSectionNode nowiki =  null;

	StringBundler t = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:544:2: ( nowiki_open_markup t= nowiki_inline_contents nowiki_close_markup )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:544:4: nowiki_open_markup t= nowiki_inline_contents nowiki_close_markup
		{
		pushFollow(FOLLOW_nowiki_open_markup_in_nowiki_inline3172);
		nowiki_open_markup();
		_fsp--;
		if (failed) return nowiki;
		pushFollow(FOLLOW_nowiki_inline_contents_in_nowiki_inline3179);
		t=nowiki_inline_contents();
		_fsp--;
		if (failed) return nowiki;
		pushFollow(FOLLOW_nowiki_close_markup_in_nowiki_inline3184);
		nowiki_close_markup();
		_fsp--;
		if (failed) return nowiki;
		if ( backtracking==0 ) {
		  nowiki = new NoWikiSectionNode(t.toString());
		}

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return nowiki;
	}
	// $ANTLR end nowiki_inline

	public static class nowiki_block_contents_return extends ParserRuleReturnScope {
	public StringBundler contents = new StringBundler();
	};

	// $ANTLR start nowiki_block_contents
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:547:1: nowiki_block_contents returns [StringBundler contents = new StringBundler()] : (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )* ;
	public final nowiki_block_contents_return nowiki_block_contents() throws RecognitionException {
	nowiki_block_contents_return retval = new nowiki_block_contents_return();
	retval.start = input.LT(1);

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:548:2: ( (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )* )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:548:4: (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )*
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:548:4: (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )*
		loop113:
		do {
		int alt113=2;
		int LA113_0 = input.LA(1);

		if ( ((LA113_0>=FORCED_END_OF_LINE && LA113_0<=ESCAPE)||(LA113_0>=NOWIKI_CLOSE && LA113_0<=78)) ) {
			alt113=1;
		}


		switch (alt113) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:548:5: c=~ ( NOWIKI_BLOCK_CLOSE | EOF )
			{
			c=(Token)input.LT(1);
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=ESCAPE)||(input.LA(1)>=NOWIKI_CLOSE && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return retval;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_nowiki_block_contents3204);	throw mse;
			}

			if ( backtracking==0 ) {
			  retval.contents.append(c.getText());
			}

			}
			break;

		default :
			break loop113;
		}
		} while (true);


		}

		retval.stop = input.LT(-1);

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return retval;
	}
	// $ANTLR end nowiki_block_contents


	// $ANTLR start nowiki_inline_contents
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:551:1: nowiki_inline_contents returns [StringBundler text = new StringBundler()] : (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )* ;
	public final StringBundler nowiki_inline_contents() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:552:2: ( (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )* )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:552:4: (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )*
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:552:4: (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )*
		loop114:
		do {
		int alt114=2;
		int LA114_0 = input.LA(1);

		if ( ((LA114_0>=FORCED_END_OF_LINE && LA114_0<=WIKI)||(LA114_0>=POUND && LA114_0<=NOWIKI_BLOCK_CLOSE)||(LA114_0>=LINK_CLOSE && LA114_0<=78)) ) {
			alt114=1;
		}


		switch (alt114) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:552:5: c=~ ( NOWIKI_CLOSE | NEWLINE | EOF )
			{
			c=(Token)input.LT(1);
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=NOWIKI_BLOCK_CLOSE)||(input.LA(1)>=LINK_CLOSE && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_nowiki_inline_contents3238);	 throw mse;
			}

			if ( backtracking==0 ) {
			   text.append(c.getText()); 
			}

			}
			break;

		default :
			break loop114;
		}
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end nowiki_inline_contents


	// $ANTLR start horizontalrule
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:559:1: horizontalrule returns [ASTNode horizontal = null] : horizontalrule_markup ( blanks )? paragraph_separator ;
	public final ASTNode horizontalrule() throws RecognitionException {
	ASTNode horizontal =  null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:560:2: ( horizontalrule_markup ( blanks )? paragraph_separator )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:560:4: horizontalrule_markup ( blanks )? paragraph_separator
		{
		pushFollow(FOLLOW_horizontalrule_markup_in_horizontalrule3275);
		horizontalrule_markup();
		_fsp--;
		if (failed) return horizontal;
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:560:27: ( blanks )?
		int alt115=2;
		int LA115_0 = input.LA(1);

		if ( (LA115_0==BLANKS) ) {
		alt115=1;
		}
		switch (alt115) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:560:29: blanks
			{
			pushFollow(FOLLOW_blanks_in_horizontalrule3280);
			blanks();
			_fsp--;
			if (failed) return horizontal;

			}
			break;

		}

		pushFollow(FOLLOW_paragraph_separator_in_horizontalrule3286);
		paragraph_separator();
		_fsp--;
		if (failed) return horizontal;
		if ( backtracking==0 ) {
		  horizontal = new HorizontalNode();
		}

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return horizontal;
	}
	// $ANTLR end horizontalrule


	// $ANTLR start link
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:567:1: link returns [LinkNode link = null] : link_open_markup a= link_address ( link_description_markup d= link_description )? link_close_markup ;
	public final LinkNode link() throws RecognitionException {
	LinkNode link =  null;

	LinkNode a = null;

	CollectionNode d = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:568:2: ( link_open_markup a= link_address ( link_description_markup d= link_description )? link_close_markup )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:568:4: link_open_markup a= link_address ( link_description_markup d= link_description )? link_close_markup
		{
		pushFollow(FOLLOW_link_open_markup_in_link3308);
		link_open_markup();
		_fsp--;
		if (failed) return link;
		pushFollow(FOLLOW_link_address_in_link3314);
		a=link_address();
		_fsp--;
		if (failed) return link;
		if ( backtracking==0 ) {
		  link = a; 
		}
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:568:59: ( link_description_markup d= link_description )?
		int alt116=2;
		int LA116_0 = input.LA(1);

		if ( (LA116_0==PIPE) ) {
		alt116=1;
		}
		switch (alt116) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:568:60: link_description_markup d= link_description
			{
			pushFollow(FOLLOW_link_description_markup_in_link3320);
			link_description_markup();
			_fsp--;
			if (failed) return link;
			pushFollow(FOLLOW_link_description_in_link3330);
			d=link_description();
			_fsp--;
			if (failed) return link;
			if ( backtracking==0 ) {

					if(link == null) { // recover from possible errors
						link = new LinkNode();
					}
					link.setAltCollectionNode(d); 
					
					
			}

			}
			break;

		}

		pushFollow(FOLLOW_link_close_markup_in_link3338);
		link_close_markup();
		_fsp--;
		if (failed) return link;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return link;
	}
	// $ANTLR end link


	// $ANTLR start link_address
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );
	public final LinkNode link_address() throws RecognitionException {
	LinkNode link = null;

	InterwikiLinkNode li = null;

	StringBundler p = null;

	StringBundler lu = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:579:2: (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri )
		int alt117=2;
		switch ( input.LA(1) ) {
		case 44:
		{
		int LA117_1 = input.LA(2);

		if ( (LA117_1==45) ) {
			int LA117_16 = input.LA(3);

			if ( (LA117_16==43) ) {
			int LA117_34 = input.LA(4);

			if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
				alt117=2;
			}
			else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
				alt117=1;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

				throw nvae;
			}
			}
			else if ( ((LA117_16>=FORCED_END_OF_LINE && LA117_16<=WIKI)||(LA117_16>=POUND && LA117_16<=INSIGNIFICANT_CHAR)||(LA117_16>=44 && LA117_16<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 16, input);

			throw nvae;
			}
		}
		else if ( ((LA117_1>=FORCED_END_OF_LINE && LA117_1<=WIKI)||(LA117_1>=POUND && LA117_1<=44)||(LA117_1>=46 && LA117_1<=78)) ) {
			alt117=2;
		}
		else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 1, input);

			throw nvae;
		}
		}
		break;
		case 46:
		{
		int LA117_2 = input.LA(2);

		if ( (LA117_2==47) ) {
			int LA117_17 = input.LA(3);

			if ( (LA117_17==48) ) {
			int LA117_35 = input.LA(4);

			if ( (LA117_35==49) ) {
				int LA117_55 = input.LA(5);

				if ( (LA117_55==50) ) {
				int LA117_74 = input.LA(6);

				if ( (LA117_74==51) ) {
					int LA117_93 = input.LA(7);

					if ( (LA117_93==48) ) {
					int LA117_109 = input.LA(8);

					if ( (LA117_109==51) ) {
						int LA117_120 = input.LA(9);

						if ( (LA117_120==43) ) {
						int LA117_34 = input.LA(10);

						if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
							alt117=2;
						}
						else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
							alt117=1;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

							throw nvae;
						}
						}
						else if ( ((LA117_120>=FORCED_END_OF_LINE && LA117_120<=WIKI)||(LA117_120>=POUND && LA117_120<=INSIGNIFICANT_CHAR)||(LA117_120>=44 && LA117_120<=78)) ) {
						alt117=2;
						}
						else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 120, input);

						throw nvae;
						}
					}
					else if ( ((LA117_109>=FORCED_END_OF_LINE && LA117_109<=WIKI)||(LA117_109>=POUND && LA117_109<=50)||(LA117_109>=52 && LA117_109<=78)) ) {
						alt117=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 109, input);

						throw nvae;
					}
					}
					else if ( ((LA117_93>=FORCED_END_OF_LINE && LA117_93<=WIKI)||(LA117_93>=POUND && LA117_93<=47)||(LA117_93>=49 && LA117_93<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 93, input);

					throw nvae;
					}
				}
				else if ( ((LA117_74>=FORCED_END_OF_LINE && LA117_74<=WIKI)||(LA117_74>=POUND && LA117_74<=50)||(LA117_74>=52 && LA117_74<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 74, input);

					throw nvae;
				}
				}
				else if ( ((LA117_55>=FORCED_END_OF_LINE && LA117_55<=WIKI)||(LA117_55>=POUND && LA117_55<=49)||(LA117_55>=51 && LA117_55<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 55, input);

				throw nvae;
				}
			}
			else if ( ((LA117_35>=FORCED_END_OF_LINE && LA117_35<=WIKI)||(LA117_35>=POUND && LA117_35<=48)||(LA117_35>=50 && LA117_35<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 35, input);

				throw nvae;
			}
			}
			else if ( ((LA117_17>=FORCED_END_OF_LINE && LA117_17<=WIKI)||(LA117_17>=POUND && LA117_17<=47)||(LA117_17>=49 && LA117_17<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 17, input);

			throw nvae;
			}
		}
		else if ( ((LA117_2>=FORCED_END_OF_LINE && LA117_2<=WIKI)||(LA117_2>=POUND && LA117_2<=46)||(LA117_2>=48 && LA117_2<=78)) ) {
			alt117=2;
		}
		else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 2, input);

			throw nvae;
		}
		}
		break;
		case 52:
		{
		int LA117_3 = input.LA(2);

		if ( (LA117_3==53) ) {
			int LA117_18 = input.LA(3);

			if ( (LA117_18==51) ) {
			int LA117_36 = input.LA(4);

			if ( (LA117_36==54) ) {
				int LA117_56 = input.LA(5);

				if ( (LA117_56==48) ) {
				int LA117_75 = input.LA(6);

				if ( (LA117_75==55) ) {
					int LA117_94 = input.LA(7);

					if ( (LA117_94==43) ) {
					int LA117_34 = input.LA(8);

					if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
						alt117=2;
					}
					else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
						alt117=1;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

						throw nvae;
					}
					}
					else if ( ((LA117_94>=FORCED_END_OF_LINE && LA117_94<=WIKI)||(LA117_94>=POUND && LA117_94<=INSIGNIFICANT_CHAR)||(LA117_94>=44 && LA117_94<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 94, input);

					throw nvae;
					}
				}
				else if ( ((LA117_75>=FORCED_END_OF_LINE && LA117_75<=WIKI)||(LA117_75>=POUND && LA117_75<=54)||(LA117_75>=56 && LA117_75<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 75, input);

					throw nvae;
				}
				}
				else if ( ((LA117_56>=FORCED_END_OF_LINE && LA117_56<=WIKI)||(LA117_56>=POUND && LA117_56<=47)||(LA117_56>=49 && LA117_56<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 56, input);

				throw nvae;
				}
			}
			else if ( ((LA117_36>=FORCED_END_OF_LINE && LA117_36<=WIKI)||(LA117_36>=POUND && LA117_36<=53)||(LA117_36>=55 && LA117_36<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 36, input);

				throw nvae;
			}
			}
			else if ( ((LA117_18>=FORCED_END_OF_LINE && LA117_18<=WIKI)||(LA117_18>=POUND && LA117_18<=50)||(LA117_18>=52 && LA117_18<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 18, input);

			throw nvae;
			}
		}
		else if ( ((LA117_3>=FORCED_END_OF_LINE && LA117_3<=WIKI)||(LA117_3>=POUND && LA117_3<=52)||(LA117_3>=54 && LA117_3<=78)) ) {
			alt117=2;
		}
		else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 3, input);

			throw nvae;
		}
		}
		break;
		case 56:
		{
		int LA117_4 = input.LA(2);

		if ( (LA117_4==47) ) {
			int LA117_19 = input.LA(3);

			if ( (LA117_19==47) ) {
			int LA117_37 = input.LA(4);

			if ( (LA117_37==57) ) {
				int LA117_57 = input.LA(5);

				if ( (LA117_57==53) ) {
				int LA117_76 = input.LA(6);

				if ( (LA117_76==58) ) {
					int LA117_95 = input.LA(7);

					if ( (LA117_95==43) ) {
					int LA117_34 = input.LA(8);

					if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
						alt117=2;
					}
					else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
						alt117=1;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

						throw nvae;
					}
					}
					else if ( ((LA117_95>=FORCED_END_OF_LINE && LA117_95<=WIKI)||(LA117_95>=POUND && LA117_95<=INSIGNIFICANT_CHAR)||(LA117_95>=44 && LA117_95<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 95, input);

					throw nvae;
					}
				}
				else if ( ((LA117_76>=FORCED_END_OF_LINE && LA117_76<=WIKI)||(LA117_76>=POUND && LA117_76<=57)||(LA117_76>=59 && LA117_76<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 76, input);

					throw nvae;
				}
				}
				else if ( ((LA117_57>=FORCED_END_OF_LINE && LA117_57<=WIKI)||(LA117_57>=POUND && LA117_57<=52)||(LA117_57>=54 && LA117_57<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 57, input);

				throw nvae;
				}
			}
			else if ( ((LA117_37>=FORCED_END_OF_LINE && LA117_37<=WIKI)||(LA117_37>=POUND && LA117_37<=56)||(LA117_37>=58 && LA117_37<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 37, input);

				throw nvae;
			}
			}
			else if ( ((LA117_19>=FORCED_END_OF_LINE && LA117_19<=WIKI)||(LA117_19>=POUND && LA117_19<=46)||(LA117_19>=48 && LA117_19<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 19, input);

			throw nvae;
			}
		}
		else if ( ((LA117_4>=FORCED_END_OF_LINE && LA117_4<=WIKI)||(LA117_4>=POUND && LA117_4<=46)||(LA117_4>=48 && LA117_4<=78)) ) {
			alt117=2;
		}
		else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 4, input);

			throw nvae;
		}
		}
		break;
		case 59:
		{
		int LA117_5 = input.LA(2);

		if ( (LA117_5==60) ) {
			int LA117_20 = input.LA(3);

			if ( (LA117_20==61) ) {
			int LA117_38 = input.LA(4);

			if ( (LA117_38==50) ) {
				int LA117_58 = input.LA(5);

				if ( (LA117_58==51) ) {
				int LA117_77 = input.LA(6);

				if ( (LA117_77==48) ) {
					int LA117_96 = input.LA(7);

					if ( (LA117_96==51) ) {
					int LA117_110 = input.LA(8);

					if ( (LA117_110==43) ) {
						int LA117_34 = input.LA(9);

						if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
						alt117=2;
						}
						else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
						alt117=1;
						}
						else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

						throw nvae;
						}
					}
					else if ( ((LA117_110>=FORCED_END_OF_LINE && LA117_110<=WIKI)||(LA117_110>=POUND && LA117_110<=INSIGNIFICANT_CHAR)||(LA117_110>=44 && LA117_110<=78)) ) {
						alt117=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 110, input);

						throw nvae;
					}
					}
					else if ( ((LA117_96>=FORCED_END_OF_LINE && LA117_96<=WIKI)||(LA117_96>=POUND && LA117_96<=50)||(LA117_96>=52 && LA117_96<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 96, input);

					throw nvae;
					}
				}
				else if ( ((LA117_77>=FORCED_END_OF_LINE && LA117_77<=WIKI)||(LA117_77>=POUND && LA117_77<=47)||(LA117_77>=49 && LA117_77<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 77, input);

					throw nvae;
				}
				}
				else if ( ((LA117_58>=FORCED_END_OF_LINE && LA117_58<=WIKI)||(LA117_58>=POUND && LA117_58<=50)||(LA117_58>=52 && LA117_58<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 58, input);

				throw nvae;
				}
			}
			else if ( ((LA117_38>=FORCED_END_OF_LINE && LA117_38<=WIKI)||(LA117_38>=POUND && LA117_38<=49)||(LA117_38>=51 && LA117_38<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 38, input);

				throw nvae;
			}
			}
			else if ( ((LA117_20>=FORCED_END_OF_LINE && LA117_20<=WIKI)||(LA117_20>=POUND && LA117_20<=60)||(LA117_20>=62 && LA117_20<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 20, input);

			throw nvae;
			}
		}
		else if ( ((LA117_5>=FORCED_END_OF_LINE && LA117_5<=WIKI)||(LA117_5>=POUND && LA117_5<=59)||(LA117_5>=61 && LA117_5<=78)) ) {
			alt117=2;
		}
		else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 5, input);

			throw nvae;
		}
		}
		break;
		case 62:
		{
		switch ( input.LA(2) ) {
		case 58:
			{
			switch ( input.LA(3) ) {
			case 63:
			{
			int LA117_39 = input.LA(4);

			if ( (LA117_39==64) ) {
				int LA117_59 = input.LA(5);

				if ( (LA117_59==65) ) {
				int LA117_78 = input.LA(6);

				if ( (LA117_78==63) ) {
					int LA117_97 = input.LA(7);

					if ( (LA117_97==53) ) {
					int LA117_111 = input.LA(8);

					if ( (LA117_111==53) ) {
						int LA117_121 = input.LA(9);

						if ( (LA117_121==43) ) {
						int LA117_34 = input.LA(10);

						if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
							alt117=2;
						}
						else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
							alt117=1;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

							throw nvae;
						}
						}
						else if ( ((LA117_121>=FORCED_END_OF_LINE && LA117_121<=WIKI)||(LA117_121>=POUND && LA117_121<=INSIGNIFICANT_CHAR)||(LA117_121>=44 && LA117_121<=78)) ) {
						alt117=2;
						}
						else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 121, input);

						throw nvae;
						}
					}
					else if ( ((LA117_111>=FORCED_END_OF_LINE && LA117_111<=WIKI)||(LA117_111>=POUND && LA117_111<=52)||(LA117_111>=54 && LA117_111<=78)) ) {
						alt117=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 111, input);

						throw nvae;
					}
					}
					else if ( ((LA117_97>=FORCED_END_OF_LINE && LA117_97<=WIKI)||(LA117_97>=POUND && LA117_97<=52)||(LA117_97>=54 && LA117_97<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 97, input);

					throw nvae;
					}
				}
				else if ( ((LA117_78>=FORCED_END_OF_LINE && LA117_78<=WIKI)||(LA117_78>=POUND && LA117_78<=62)||(LA117_78>=64 && LA117_78<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 78, input);

					throw nvae;
				}
				}
				else if ( ((LA117_59>=FORCED_END_OF_LINE && LA117_59<=WIKI)||(LA117_59>=POUND && LA117_59<=64)||(LA117_59>=66 && LA117_59<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 59, input);

				throw nvae;
				}
			}
			else if ( ((LA117_39>=FORCED_END_OF_LINE && LA117_39<=WIKI)||(LA117_39>=POUND && LA117_39<=63)||(LA117_39>=65 && LA117_39<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 39, input);

				throw nvae;
			}
			}
			break;
			case 66:
			{
			int LA117_40 = input.LA(4);

			if ( (LA117_40==51) ) {
				int LA117_60 = input.LA(5);

				if ( (LA117_60==63) ) {
				int LA117_79 = input.LA(6);

				if ( (LA117_79==50) ) {
					int LA117_98 = input.LA(7);

					if ( (LA117_98==51) ) {
					int LA117_112 = input.LA(8);

					if ( (LA117_112==48) ) {
						int LA117_122 = input.LA(9);

						if ( (LA117_122==51) ) {
						int LA117_129 = input.LA(10);

						if ( (LA117_129==43) ) {
							int LA117_34 = input.LA(11);

							if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
							alt117=2;
							}
							else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
							alt117=1;
							}
							else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

							throw nvae;
							}
						}
						else if ( ((LA117_129>=FORCED_END_OF_LINE && LA117_129<=WIKI)||(LA117_129>=POUND && LA117_129<=INSIGNIFICANT_CHAR)||(LA117_129>=44 && LA117_129<=78)) ) {
							alt117=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 129, input);

							throw nvae;
						}
						}
						else if ( ((LA117_122>=FORCED_END_OF_LINE && LA117_122<=WIKI)||(LA117_122>=POUND && LA117_122<=50)||(LA117_122>=52 && LA117_122<=78)) ) {
						alt117=2;
						}
						else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 122, input);

						throw nvae;
						}
					}
					else if ( ((LA117_112>=FORCED_END_OF_LINE && LA117_112<=WIKI)||(LA117_112>=POUND && LA117_112<=47)||(LA117_112>=49 && LA117_112<=78)) ) {
						alt117=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 112, input);

						throw nvae;
					}
					}
					else if ( ((LA117_98>=FORCED_END_OF_LINE && LA117_98<=WIKI)||(LA117_98>=POUND && LA117_98<=50)||(LA117_98>=52 && LA117_98<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 98, input);

					throw nvae;
					}
				}
				else if ( ((LA117_79>=FORCED_END_OF_LINE && LA117_79<=WIKI)||(LA117_79>=POUND && LA117_79<=49)||(LA117_79>=51 && LA117_79<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 79, input);

					throw nvae;
				}
				}
				else if ( ((LA117_60>=FORCED_END_OF_LINE && LA117_60<=WIKI)||(LA117_60>=POUND && LA117_60<=62)||(LA117_60>=64 && LA117_60<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 60, input);

				throw nvae;
				}
			}
			else if ( ((LA117_40>=FORCED_END_OF_LINE && LA117_40<=WIKI)||(LA117_40>=POUND && LA117_40<=50)||(LA117_40>=52 && LA117_40<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 40, input);

				throw nvae;
			}
			}
			break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case STAR:
			case EQUAL:
			case PIPE:
			case ITAL:
			case LINK_OPEN:
			case IMAGE_OPEN:
			case NOWIKI_OPEN:
			case EXTENSION:
			case FORCED_LINEBREAK:
			case ESCAPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 64:
			case 65:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
			{
			alt117=2;
			}
			break;
			default:
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 21, input);

			throw nvae;
			}

			}
			break;
		case 47:
			{
			int LA117_22 = input.LA(3);

			if ( (LA117_22==51) ) {
			int LA117_41 = input.LA(4);

			if ( (LA117_41==67) ) {
				int LA117_61 = input.LA(5);

				if ( (LA117_61==62) ) {
				int LA117_80 = input.LA(6);

				if ( (LA117_80==47) ) {
					int LA117_99 = input.LA(7);

					if ( (LA117_99==51) ) {
					int LA117_113 = input.LA(8);

					if ( (LA117_113==67) ) {
						int LA117_123 = input.LA(9);

						if ( (LA117_123==43) ) {
						int LA117_34 = input.LA(10);

						if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
							alt117=2;
						}
						else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
							alt117=1;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

							throw nvae;
						}
						}
						else if ( ((LA117_123>=FORCED_END_OF_LINE && LA117_123<=WIKI)||(LA117_123>=POUND && LA117_123<=INSIGNIFICANT_CHAR)||(LA117_123>=44 && LA117_123<=78)) ) {
						alt117=2;
						}
						else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 123, input);

						throw nvae;
						}
					}
					else if ( ((LA117_113>=FORCED_END_OF_LINE && LA117_113<=WIKI)||(LA117_113>=POUND && LA117_113<=66)||(LA117_113>=68 && LA117_113<=78)) ) {
						alt117=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 113, input);

						throw nvae;
					}
					}
					else if ( ((LA117_99>=FORCED_END_OF_LINE && LA117_99<=WIKI)||(LA117_99>=POUND && LA117_99<=50)||(LA117_99>=52 && LA117_99<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 99, input);

					throw nvae;
					}
				}
				else if ( ((LA117_80>=FORCED_END_OF_LINE && LA117_80<=WIKI)||(LA117_80>=POUND && LA117_80<=46)||(LA117_80>=48 && LA117_80<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 80, input);

					throw nvae;
				}
				}
				else if ( ((LA117_61>=FORCED_END_OF_LINE && LA117_61<=WIKI)||(LA117_61>=POUND && LA117_61<=61)||(LA117_61>=63 && LA117_61<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 61, input);

				throw nvae;
				}
			}
			else if ( ((LA117_41>=FORCED_END_OF_LINE && LA117_41<=WIKI)||(LA117_41>=POUND && LA117_41<=66)||(LA117_41>=68 && LA117_41<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 41, input);

				throw nvae;
			}
			}
			else if ( ((LA117_22>=FORCED_END_OF_LINE && LA117_22<=WIKI)||(LA117_22>=POUND && LA117_22<=50)||(LA117_22>=52 && LA117_22<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 22, input);

			throw nvae;
			}
			}
			break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case STAR:
		case EQUAL:
		case PIPE:
		case ITAL:
		case LINK_OPEN:
		case IMAGE_OPEN:
		case NOWIKI_OPEN:
		case EXTENSION:
		case FORCED_LINEBREAK:
		case ESCAPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
			{
			alt117=2;
			}
			break;
		default:
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 6, input);

			throw nvae;
		}

		}
		break;
		case 68:
		{
		switch ( input.LA(2) ) {
		case 71:
			{
			int LA117_23 = input.LA(3);

			if ( (LA117_23==63) ) {
			int LA117_42 = input.LA(4);

			if ( (LA117_42==67) ) {
				int LA117_62 = input.LA(5);

				if ( (LA117_62==63) ) {
				int LA117_81 = input.LA(6);

				if ( (LA117_81==43) ) {
					int LA117_34 = input.LA(7);

					if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
					alt117=2;
					}
					else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
					alt117=1;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

					throw nvae;
					}
				}
				else if ( ((LA117_81>=FORCED_END_OF_LINE && LA117_81<=WIKI)||(LA117_81>=POUND && LA117_81<=INSIGNIFICANT_CHAR)||(LA117_81>=44 && LA117_81<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 81, input);

					throw nvae;
				}
				}
				else if ( ((LA117_62>=FORCED_END_OF_LINE && LA117_62<=WIKI)||(LA117_62>=POUND && LA117_62<=62)||(LA117_62>=64 && LA117_62<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 62, input);

				throw nvae;
				}
			}
			else if ( ((LA117_42>=FORCED_END_OF_LINE && LA117_42<=WIKI)||(LA117_42>=POUND && LA117_42<=66)||(LA117_42>=68 && LA117_42<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 42, input);

				throw nvae;
			}
			}
			else if ( ((LA117_23>=FORCED_END_OF_LINE && LA117_23<=WIKI)||(LA117_23>=POUND && LA117_23<=62)||(LA117_23>=64 && LA117_23<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 23, input);

			throw nvae;
			}
			}
			break;
		case 66:
			{
			int LA117_24 = input.LA(3);

			if ( (LA117_24==66) ) {
			int LA117_43 = input.LA(4);

			if ( (LA117_43==69) ) {
				int LA117_63 = input.LA(5);

				if ( (LA117_63==49) ) {
				int LA117_82 = input.LA(6);

				if ( (LA117_82==70) ) {
					int LA117_100 = input.LA(7);

					if ( (LA117_100==58) ) {
					int LA117_114 = input.LA(8);

					if ( ((LA117_114>=FORCED_END_OF_LINE && LA117_114<=WIKI)||(LA117_114>=POUND && LA117_114<=INSIGNIFICANT_CHAR)||(LA117_114>=44 && LA117_114<=78)) ) {
						alt117=2;
					}
					else if ( (LA117_114==43) ) {
						int LA117_34 = input.LA(9);

						if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
						alt117=2;
						}
						else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
						alt117=1;
						}
						else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

						throw nvae;
						}
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 114, input);

						throw nvae;
					}
					}
					else if ( ((LA117_100>=FORCED_END_OF_LINE && LA117_100<=WIKI)||(LA117_100>=POUND && LA117_100<=57)||(LA117_100>=59 && LA117_100<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 100, input);

					throw nvae;
					}
				}
				else if ( ((LA117_82>=FORCED_END_OF_LINE && LA117_82<=WIKI)||(LA117_82>=POUND && LA117_82<=69)||(LA117_82>=71 && LA117_82<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 82, input);

					throw nvae;
				}
				}
				else if ( ((LA117_63>=FORCED_END_OF_LINE && LA117_63<=WIKI)||(LA117_63>=POUND && LA117_63<=48)||(LA117_63>=50 && LA117_63<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 63, input);

				throw nvae;
				}
			}
			else if ( ((LA117_43>=FORCED_END_OF_LINE && LA117_43<=WIKI)||(LA117_43>=POUND && LA117_43<=68)||(LA117_43>=70 && LA117_43<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 43, input);

				throw nvae;
			}
			}
			else if ( ((LA117_24>=FORCED_END_OF_LINE && LA117_24<=WIKI)||(LA117_24>=POUND && LA117_24<=65)||(LA117_24>=67 && LA117_24<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 24, input);

			throw nvae;
			}
			}
			break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case STAR:
		case EQUAL:
		case PIPE:
		case ITAL:
		case LINK_OPEN:
		case IMAGE_OPEN:
		case NOWIKI_OPEN:
		case EXTENSION:
		case FORCED_LINEBREAK:
		case ESCAPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 67:
		case 68:
		case 69:
		case 70:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
			{
			alt117=2;
			}
			break;
		default:
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 7, input);

			throw nvae;
		}

		}
		break;
		case 61:
		{
		switch ( input.LA(2) ) {
		case 69:
			{
			int LA117_25 = input.LA(3);

			if ( (LA117_25==50) ) {
			int LA117_44 = input.LA(4);

			if ( (LA117_44==51) ) {
				int LA117_64 = input.LA(5);

				if ( (LA117_64==48) ) {
				int LA117_83 = input.LA(6);

				if ( (LA117_83==51) ) {
					int LA117_101 = input.LA(7);

					if ( (LA117_101==43) ) {
					int LA117_34 = input.LA(8);

					if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
						alt117=2;
					}
					else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
						alt117=1;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

						throw nvae;
					}
					}
					else if ( ((LA117_101>=FORCED_END_OF_LINE && LA117_101<=WIKI)||(LA117_101>=POUND && LA117_101<=INSIGNIFICANT_CHAR)||(LA117_101>=44 && LA117_101<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 101, input);

					throw nvae;
					}
				}
				else if ( ((LA117_83>=FORCED_END_OF_LINE && LA117_83<=WIKI)||(LA117_83>=POUND && LA117_83<=50)||(LA117_83>=52 && LA117_83<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 83, input);

					throw nvae;
				}
				}
				else if ( ((LA117_64>=FORCED_END_OF_LINE && LA117_64<=WIKI)||(LA117_64>=POUND && LA117_64<=47)||(LA117_64>=49 && LA117_64<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 64, input);

				throw nvae;
				}
			}
			else if ( ((LA117_44>=FORCED_END_OF_LINE && LA117_44<=WIKI)||(LA117_44>=POUND && LA117_44<=50)||(LA117_44>=52 && LA117_44<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 44, input);

				throw nvae;
			}
			}
			else if ( ((LA117_25>=FORCED_END_OF_LINE && LA117_25<=WIKI)||(LA117_25>=POUND && LA117_25<=49)||(LA117_25>=51 && LA117_25<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 25, input);

			throw nvae;
			}
			}
			break;
		case 49:
			{
			switch ( input.LA(3) ) {
			case 55:
			{
			int LA117_45 = input.LA(4);

			if ( (LA117_45==72) ) {
				int LA117_65 = input.LA(5);

				if ( (LA117_65==53) ) {
				int LA117_84 = input.LA(6);

				if ( (LA117_84==58) ) {
					int LA117_102 = input.LA(7);

					if ( (LA117_102==50) ) {
					int LA117_115 = input.LA(8);

					if ( (LA117_115==51) ) {
						int LA117_124 = input.LA(9);

						if ( (LA117_124==48) ) {
						int LA117_130 = input.LA(10);

						if ( (LA117_130==51) ) {
							int LA117_133 = input.LA(11);

							if ( (LA117_133==43) ) {
							int LA117_34 = input.LA(12);

							if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
								alt117=2;
							}
							else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
								alt117=1;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
								new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

								throw nvae;
							}
							}
							else if ( ((LA117_133>=FORCED_END_OF_LINE && LA117_133<=WIKI)||(LA117_133>=POUND && LA117_133<=INSIGNIFICANT_CHAR)||(LA117_133>=44 && LA117_133<=78)) ) {
							alt117=2;
							}
							else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 133, input);

							throw nvae;
							}
						}
						else if ( ((LA117_130>=FORCED_END_OF_LINE && LA117_130<=WIKI)||(LA117_130>=POUND && LA117_130<=50)||(LA117_130>=52 && LA117_130<=78)) ) {
							alt117=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 130, input);

							throw nvae;
						}
						}
						else if ( ((LA117_124>=FORCED_END_OF_LINE && LA117_124<=WIKI)||(LA117_124>=POUND && LA117_124<=47)||(LA117_124>=49 && LA117_124<=78)) ) {
						alt117=2;
						}
						else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 124, input);

						throw nvae;
						}
					}
					else if ( ((LA117_115>=FORCED_END_OF_LINE && LA117_115<=WIKI)||(LA117_115>=POUND && LA117_115<=50)||(LA117_115>=52 && LA117_115<=78)) ) {
						alt117=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 115, input);

						throw nvae;
					}
					}
					else if ( ((LA117_102>=FORCED_END_OF_LINE && LA117_102<=WIKI)||(LA117_102>=POUND && LA117_102<=49)||(LA117_102>=51 && LA117_102<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 102, input);

					throw nvae;
					}
				}
				else if ( ((LA117_84>=FORCED_END_OF_LINE && LA117_84<=WIKI)||(LA117_84>=POUND && LA117_84<=57)||(LA117_84>=59 && LA117_84<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 84, input);

					throw nvae;
				}
				}
				else if ( ((LA117_65>=FORCED_END_OF_LINE && LA117_65<=WIKI)||(LA117_65>=POUND && LA117_65<=52)||(LA117_65>=54 && LA117_65<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 65, input);

				throw nvae;
				}
			}
			else if ( ((LA117_45>=FORCED_END_OF_LINE && LA117_45<=WIKI)||(LA117_45>=POUND && LA117_45<=71)||(LA117_45>=73 && LA117_45<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 45, input);

				throw nvae;
			}
			}
			break;
			case 48:
			{
			int LA117_46 = input.LA(4);

			if ( (LA117_46==51) ) {
				int LA117_66 = input.LA(5);

				if ( (LA117_66==50) ) {
				int LA117_85 = input.LA(6);

				if ( (LA117_85==51) ) {
					int LA117_103 = input.LA(7);

					if ( (LA117_103==48) ) {
					int LA117_116 = input.LA(8);

					if ( (LA117_116==51) ) {
						int LA117_125 = input.LA(9);

						if ( (LA117_125==43) ) {
						int LA117_34 = input.LA(10);

						if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
							alt117=2;
						}
						else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
							alt117=1;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

							throw nvae;
						}
						}
						else if ( ((LA117_125>=FORCED_END_OF_LINE && LA117_125<=WIKI)||(LA117_125>=POUND && LA117_125<=INSIGNIFICANT_CHAR)||(LA117_125>=44 && LA117_125<=78)) ) {
						alt117=2;
						}
						else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 125, input);

						throw nvae;
						}
					}
					else if ( ((LA117_116>=FORCED_END_OF_LINE && LA117_116<=WIKI)||(LA117_116>=POUND && LA117_116<=50)||(LA117_116>=52 && LA117_116<=78)) ) {
						alt117=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 116, input);

						throw nvae;
					}
					}
					else if ( ((LA117_103>=FORCED_END_OF_LINE && LA117_103<=WIKI)||(LA117_103>=POUND && LA117_103<=47)||(LA117_103>=49 && LA117_103<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 103, input);

					throw nvae;
					}
				}
				else if ( ((LA117_85>=FORCED_END_OF_LINE && LA117_85<=WIKI)||(LA117_85>=POUND && LA117_85<=50)||(LA117_85>=52 && LA117_85<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 85, input);

					throw nvae;
				}
				}
				else if ( ((LA117_66>=FORCED_END_OF_LINE && LA117_66<=WIKI)||(LA117_66>=POUND && LA117_66<=49)||(LA117_66>=51 && LA117_66<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 66, input);

				throw nvae;
				}
			}
			else if ( ((LA117_46>=FORCED_END_OF_LINE && LA117_46<=WIKI)||(LA117_46>=POUND && LA117_46<=50)||(LA117_46>=52 && LA117_46<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 46, input);

				throw nvae;
			}
			}
			break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case STAR:
			case EQUAL:
			case PIPE:
			case ITAL:
			case LINK_OPEN:
			case IMAGE_OPEN:
			case NOWIKI_OPEN:
			case EXTENSION:
			case FORCED_LINEBREAK:
			case ESCAPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
			{
			alt117=2;
			}
			break;
			default:
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 26, input);

			throw nvae;
			}

			}
			break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case STAR:
		case EQUAL:
		case PIPE:
		case ITAL:
		case LINK_OPEN:
		case IMAGE_OPEN:
		case NOWIKI_OPEN:
		case EXTENSION:
		case FORCED_LINEBREAK:
		case ESCAPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
			{
			alt117=2;
			}
			break;
		default:
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 8, input);

			throw nvae;
		}

		}
		break;
		case 73:
		{
		int LA117_9 = input.LA(2);

		if ( (LA117_9==63) ) {
			int LA117_27 = input.LA(3);

			if ( (LA117_27==66) ) {
			int LA117_47 = input.LA(4);

			if ( (LA117_47==58) ) {
				int LA117_67 = input.LA(5);

				if ( (LA117_67==47) ) {
				int LA117_86 = input.LA(6);

				if ( (LA117_86==74) ) {
					int LA117_104 = input.LA(7);

					if ( (LA117_104==43) ) {
					int LA117_34 = input.LA(8);

					if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
						alt117=2;
					}
					else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
						alt117=1;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

						throw nvae;
					}
					}
					else if ( ((LA117_104>=FORCED_END_OF_LINE && LA117_104<=WIKI)||(LA117_104>=POUND && LA117_104<=INSIGNIFICANT_CHAR)||(LA117_104>=44 && LA117_104<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 104, input);

					throw nvae;
					}
				}
				else if ( ((LA117_86>=FORCED_END_OF_LINE && LA117_86<=WIKI)||(LA117_86>=POUND && LA117_86<=73)||(LA117_86>=75 && LA117_86<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 86, input);

					throw nvae;
				}
				}
				else if ( ((LA117_67>=FORCED_END_OF_LINE && LA117_67<=WIKI)||(LA117_67>=POUND && LA117_67<=46)||(LA117_67>=48 && LA117_67<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 67, input);

				throw nvae;
				}
			}
			else if ( ((LA117_47>=FORCED_END_OF_LINE && LA117_47<=WIKI)||(LA117_47>=POUND && LA117_47<=57)||(LA117_47>=59 && LA117_47<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 47, input);

				throw nvae;
			}
			}
			else if ( ((LA117_27>=FORCED_END_OF_LINE && LA117_27<=WIKI)||(LA117_27>=POUND && LA117_27<=65)||(LA117_27>=67 && LA117_27<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 27, input);

			throw nvae;
			}
		}
		else if ( ((LA117_9>=FORCED_END_OF_LINE && LA117_9<=WIKI)||(LA117_9>=POUND && LA117_9<=62)||(LA117_9>=64 && LA117_9<=78)) ) {
			alt117=2;
		}
		else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 9, input);

			throw nvae;
		}
		}
		break;
		case 60:
		{
		int LA117_10 = input.LA(2);

		if ( (LA117_10==67) ) {
			int LA117_28 = input.LA(3);

			if ( (LA117_28==51) ) {
			int LA117_48 = input.LA(4);

			if ( (LA117_48==72) ) {
				int LA117_68 = input.LA(5);

				if ( (LA117_68==60) ) {
				int LA117_87 = input.LA(6);

				if ( (LA117_87==67) ) {
					int LA117_105 = input.LA(7);

					if ( (LA117_105==63) ) {
					int LA117_117 = input.LA(8);

					if ( (LA117_117==72) ) {
						int LA117_126 = input.LA(9);

						if ( (LA117_126==43) ) {
						int LA117_34 = input.LA(10);

						if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
							alt117=2;
						}
						else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
							alt117=1;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

							throw nvae;
						}
						}
						else if ( ((LA117_126>=FORCED_END_OF_LINE && LA117_126<=WIKI)||(LA117_126>=POUND && LA117_126<=INSIGNIFICANT_CHAR)||(LA117_126>=44 && LA117_126<=78)) ) {
						alt117=2;
						}
						else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 126, input);

						throw nvae;
						}
					}
					else if ( ((LA117_117>=FORCED_END_OF_LINE && LA117_117<=WIKI)||(LA117_117>=POUND && LA117_117<=71)||(LA117_117>=73 && LA117_117<=78)) ) {
						alt117=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 117, input);

						throw nvae;
					}
					}
					else if ( ((LA117_105>=FORCED_END_OF_LINE && LA117_105<=WIKI)||(LA117_105>=POUND && LA117_105<=62)||(LA117_105>=64 && LA117_105<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 105, input);

					throw nvae;
					}
				}
				else if ( ((LA117_87>=FORCED_END_OF_LINE && LA117_87<=WIKI)||(LA117_87>=POUND && LA117_87<=66)||(LA117_87>=68 && LA117_87<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 87, input);

					throw nvae;
				}
				}
				else if ( ((LA117_68>=FORCED_END_OF_LINE && LA117_68<=WIKI)||(LA117_68>=POUND && LA117_68<=59)||(LA117_68>=61 && LA117_68<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 68, input);

				throw nvae;
				}
			}
			else if ( ((LA117_48>=FORCED_END_OF_LINE && LA117_48<=WIKI)||(LA117_48>=POUND && LA117_48<=71)||(LA117_48>=73 && LA117_48<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 48, input);

				throw nvae;
			}
			}
			else if ( ((LA117_28>=FORCED_END_OF_LINE && LA117_28<=WIKI)||(LA117_28>=POUND && LA117_28<=50)||(LA117_28>=52 && LA117_28<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 28, input);

			throw nvae;
			}
		}
		else if ( ((LA117_10>=FORCED_END_OF_LINE && LA117_10<=WIKI)||(LA117_10>=POUND && LA117_10<=66)||(LA117_10>=68 && LA117_10<=78)) ) {
			alt117=2;
		}
		else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 10, input);

			throw nvae;
		}
		}
		break;
		case 75:
		{
		switch ( input.LA(2) ) {
		case 50:
			{
			int LA117_29 = input.LA(3);

			if ( (LA117_29==51) ) {
			int LA117_49 = input.LA(4);

			if ( (LA117_49==48) ) {
				int LA117_69 = input.LA(5);

				if ( (LA117_69==51) ) {
				int LA117_88 = input.LA(6);

				if ( (LA117_88==43) ) {
					int LA117_34 = input.LA(7);

					if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
					alt117=2;
					}
					else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
					alt117=1;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

					throw nvae;
					}
				}
				else if ( ((LA117_88>=FORCED_END_OF_LINE && LA117_88<=WIKI)||(LA117_88>=POUND && LA117_88<=INSIGNIFICANT_CHAR)||(LA117_88>=44 && LA117_88<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 88, input);

					throw nvae;
				}
				}
				else if ( ((LA117_69>=FORCED_END_OF_LINE && LA117_69<=WIKI)||(LA117_69>=POUND && LA117_69<=50)||(LA117_69>=52 && LA117_69<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 69, input);

				throw nvae;
				}
			}
			else if ( ((LA117_49>=FORCED_END_OF_LINE && LA117_49<=WIKI)||(LA117_49>=POUND && LA117_49<=47)||(LA117_49>=49 && LA117_49<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 49, input);

				throw nvae;
			}
			}
			else if ( ((LA117_29>=FORCED_END_OF_LINE && LA117_29<=WIKI)||(LA117_29>=POUND && LA117_29<=50)||(LA117_29>=52 && LA117_29<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 29, input);

			throw nvae;
			}
			}
			break;
		case 51:
			{
			int LA117_30 = input.LA(3);

			if ( (LA117_30==66) ) {
			int LA117_50 = input.LA(4);

			if ( (LA117_50==66) ) {
				int LA117_70 = input.LA(5);

				if ( (LA117_70==53) ) {
				int LA117_89 = input.LA(6);

				if ( (LA117_89==76) ) {
					int LA117_106 = input.LA(7);

					if ( (LA117_106==50) ) {
					int LA117_118 = input.LA(8);

					if ( (LA117_118==51) ) {
						int LA117_127 = input.LA(9);

						if ( (LA117_127==48) ) {
						int LA117_131 = input.LA(10);

						if ( (LA117_131==51) ) {
							int LA117_134 = input.LA(11);

							if ( (LA117_134==43) ) {
							int LA117_34 = input.LA(12);

							if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
								alt117=2;
							}
							else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
								alt117=1;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
								new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

								throw nvae;
							}
							}
							else if ( ((LA117_134>=FORCED_END_OF_LINE && LA117_134<=WIKI)||(LA117_134>=POUND && LA117_134<=INSIGNIFICANT_CHAR)||(LA117_134>=44 && LA117_134<=78)) ) {
							alt117=2;
							}
							else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 134, input);

							throw nvae;
							}
						}
						else if ( ((LA117_131>=FORCED_END_OF_LINE && LA117_131<=WIKI)||(LA117_131>=POUND && LA117_131<=50)||(LA117_131>=52 && LA117_131<=78)) ) {
							alt117=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 131, input);

							throw nvae;
						}
						}
						else if ( ((LA117_127>=FORCED_END_OF_LINE && LA117_127<=WIKI)||(LA117_127>=POUND && LA117_127<=47)||(LA117_127>=49 && LA117_127<=78)) ) {
						alt117=2;
						}
						else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 127, input);

						throw nvae;
						}
					}
					else if ( ((LA117_118>=FORCED_END_OF_LINE && LA117_118<=WIKI)||(LA117_118>=POUND && LA117_118<=50)||(LA117_118>=52 && LA117_118<=78)) ) {
						alt117=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 118, input);

						throw nvae;
					}
					}
					else if ( ((LA117_106>=FORCED_END_OF_LINE && LA117_106<=WIKI)||(LA117_106>=POUND && LA117_106<=49)||(LA117_106>=51 && LA117_106<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 106, input);

					throw nvae;
					}
				}
				else if ( ((LA117_89>=FORCED_END_OF_LINE && LA117_89<=WIKI)||(LA117_89>=POUND && LA117_89<=75)||(LA117_89>=77 && LA117_89<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 89, input);

					throw nvae;
				}
				}
				else if ( ((LA117_70>=FORCED_END_OF_LINE && LA117_70<=WIKI)||(LA117_70>=POUND && LA117_70<=52)||(LA117_70>=54 && LA117_70<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 70, input);

				throw nvae;
				}
			}
			else if ( ((LA117_50>=FORCED_END_OF_LINE && LA117_50<=WIKI)||(LA117_50>=POUND && LA117_50<=65)||(LA117_50>=67 && LA117_50<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 50, input);

				throw nvae;
			}
			}
			else if ( ((LA117_30>=FORCED_END_OF_LINE && LA117_30<=WIKI)||(LA117_30>=POUND && LA117_30<=65)||(LA117_30>=67 && LA117_30<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 30, input);

			throw nvae;
			}
			}
			break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case STAR:
		case EQUAL:
		case PIPE:
		case ITAL:
		case LINK_OPEN:
		case IMAGE_OPEN:
		case NOWIKI_OPEN:
		case EXTENSION:
		case FORCED_LINEBREAK:
		case ESCAPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
			{
			alt117=2;
			}
			break;
		default:
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 11, input);

			throw nvae;
		}

		}
		break;
		case 77:
		{
		int LA117_12 = input.LA(2);

		if ( (LA117_12==70) ) {
			int LA117_31 = input.LA(3);

			if ( (LA117_31==58) ) {
			int LA117_51 = input.LA(4);

			if ( (LA117_51==69) ) {
				int LA117_71 = input.LA(5);

				if ( (LA117_71==47) ) {
				int LA117_90 = input.LA(6);

				if ( (LA117_90==66) ) {
					int LA117_107 = input.LA(7);

					if ( (LA117_107==43) ) {
					int LA117_34 = input.LA(8);

					if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
						alt117=2;
					}
					else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
						alt117=1;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

						throw nvae;
					}
					}
					else if ( ((LA117_107>=FORCED_END_OF_LINE && LA117_107<=WIKI)||(LA117_107>=POUND && LA117_107<=INSIGNIFICANT_CHAR)||(LA117_107>=44 && LA117_107<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 107, input);

					throw nvae;
					}
				}
				else if ( ((LA117_90>=FORCED_END_OF_LINE && LA117_90<=WIKI)||(LA117_90>=POUND && LA117_90<=65)||(LA117_90>=67 && LA117_90<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 90, input);

					throw nvae;
				}
				}
				else if ( ((LA117_71>=FORCED_END_OF_LINE && LA117_71<=WIKI)||(LA117_71>=POUND && LA117_71<=46)||(LA117_71>=48 && LA117_71<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 71, input);

				throw nvae;
				}
			}
			else if ( ((LA117_51>=FORCED_END_OF_LINE && LA117_51<=WIKI)||(LA117_51>=POUND && LA117_51<=68)||(LA117_51>=70 && LA117_51<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 51, input);

				throw nvae;
			}
			}
			else if ( ((LA117_31>=FORCED_END_OF_LINE && LA117_31<=WIKI)||(LA117_31>=POUND && LA117_31<=57)||(LA117_31>=59 && LA117_31<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 31, input);

			throw nvae;
			}
		}
		else if ( ((LA117_12>=FORCED_END_OF_LINE && LA117_12<=WIKI)||(LA117_12>=POUND && LA117_12<=69)||(LA117_12>=71 && LA117_12<=78)) ) {
			alt117=2;
		}
		else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 12, input);

			throw nvae;
		}
		}
		break;
		case 50:
		{
		int LA117_13 = input.LA(2);

		if ( (LA117_13==51) ) {
			int LA117_32 = input.LA(3);

			if ( (LA117_32==48) ) {
			int LA117_52 = input.LA(4);

			if ( (LA117_52==51) ) {
				int LA117_72 = input.LA(5);

				if ( (LA117_72==72) ) {
				int LA117_91 = input.LA(6);

				if ( (LA117_91==58) ) {
					int LA117_108 = input.LA(7);

					if ( (LA117_108==66) ) {
					int LA117_119 = input.LA(8);

					if ( (LA117_119==51) ) {
						int LA117_128 = input.LA(9);

						if ( (LA117_128==63) ) {
						int LA117_132 = input.LA(10);

						if ( (LA117_132==43) ) {
							int LA117_34 = input.LA(11);

							if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
							alt117=2;
							}
							else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
							alt117=1;
							}
							else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

							throw nvae;
							}
						}
						else if ( ((LA117_132>=FORCED_END_OF_LINE && LA117_132<=WIKI)||(LA117_132>=POUND && LA117_132<=INSIGNIFICANT_CHAR)||(LA117_132>=44 && LA117_132<=78)) ) {
							alt117=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 132, input);

							throw nvae;
						}
						}
						else if ( ((LA117_128>=FORCED_END_OF_LINE && LA117_128<=WIKI)||(LA117_128>=POUND && LA117_128<=62)||(LA117_128>=64 && LA117_128<=78)) ) {
						alt117=2;
						}
						else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 128, input);

						throw nvae;
						}
					}
					else if ( ((LA117_119>=FORCED_END_OF_LINE && LA117_119<=WIKI)||(LA117_119>=POUND && LA117_119<=50)||(LA117_119>=52 && LA117_119<=78)) ) {
						alt117=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 119, input);

						throw nvae;
					}
					}
					else if ( ((LA117_108>=FORCED_END_OF_LINE && LA117_108<=WIKI)||(LA117_108>=POUND && LA117_108<=65)||(LA117_108>=67 && LA117_108<=78)) ) {
					alt117=2;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 108, input);

					throw nvae;
					}
				}
				else if ( ((LA117_91>=FORCED_END_OF_LINE && LA117_91<=WIKI)||(LA117_91>=POUND && LA117_91<=57)||(LA117_91>=59 && LA117_91<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 91, input);

					throw nvae;
				}
				}
				else if ( ((LA117_72>=FORCED_END_OF_LINE && LA117_72<=WIKI)||(LA117_72>=POUND && LA117_72<=71)||(LA117_72>=73 && LA117_72<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 72, input);

				throw nvae;
				}
			}
			else if ( ((LA117_52>=FORCED_END_OF_LINE && LA117_52<=WIKI)||(LA117_52>=POUND && LA117_52<=50)||(LA117_52>=52 && LA117_52<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 52, input);

				throw nvae;
			}
			}
			else if ( ((LA117_32>=FORCED_END_OF_LINE && LA117_32<=WIKI)||(LA117_32>=POUND && LA117_32<=47)||(LA117_32>=49 && LA117_32<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 32, input);

			throw nvae;
			}
		}
		else if ( ((LA117_13>=FORCED_END_OF_LINE && LA117_13<=WIKI)||(LA117_13>=POUND && LA117_13<=50)||(LA117_13>=52 && LA117_13<=78)) ) {
			alt117=2;
		}
		else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 13, input);

			throw nvae;
		}
		}
		break;
		case 78:
		{
		int LA117_14 = input.LA(2);

		if ( (LA117_14==50) ) {
			int LA117_33 = input.LA(3);

			if ( (LA117_33==51) ) {
			int LA117_53 = input.LA(4);

			if ( (LA117_53==48) ) {
				int LA117_73 = input.LA(5);

				if ( (LA117_73==51) ) {
				int LA117_92 = input.LA(6);

				if ( (LA117_92==43) ) {
					int LA117_34 = input.LA(7);

					if ( (LA117_34==PIPE||LA117_34==LINK_CLOSE) ) {
					alt117=2;
					}
					else if ( ((LA117_34>=FORCED_END_OF_LINE && LA117_34<=WIKI)||(LA117_34>=POUND && LA117_34<=EQUAL)||(LA117_34>=ITAL && LA117_34<=NOWIKI_CLOSE)||(LA117_34>=IMAGE_CLOSE && LA117_34<=78)) ) {
					alt117=1;
					}
					else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 34, input);

					throw nvae;
					}
				}
				else if ( ((LA117_92>=FORCED_END_OF_LINE && LA117_92<=WIKI)||(LA117_92>=POUND && LA117_92<=INSIGNIFICANT_CHAR)||(LA117_92>=44 && LA117_92<=78)) ) {
					alt117=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 92, input);

					throw nvae;
				}
				}
				else if ( ((LA117_73>=FORCED_END_OF_LINE && LA117_73<=WIKI)||(LA117_73>=POUND && LA117_73<=50)||(LA117_73>=52 && LA117_73<=78)) ) {
				alt117=2;
				}
				else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 73, input);

				throw nvae;
				}
			}
			else if ( ((LA117_53>=FORCED_END_OF_LINE && LA117_53<=WIKI)||(LA117_53>=POUND && LA117_53<=47)||(LA117_53>=49 && LA117_53<=78)) ) {
				alt117=2;
			}
			else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 53, input);

				throw nvae;
			}
			}
			else if ( ((LA117_33>=FORCED_END_OF_LINE && LA117_33<=WIKI)||(LA117_33>=POUND && LA117_33<=50)||(LA117_33>=52 && LA117_33<=78)) ) {
			alt117=2;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
				new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 33, input);

			throw nvae;
			}
		}
		else if ( ((LA117_14>=FORCED_END_OF_LINE && LA117_14<=WIKI)||(LA117_14>=POUND && LA117_14<=49)||(LA117_14>=51 && LA117_14<=78)) ) {
			alt117=2;
		}
		else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 14, input);

			throw nvae;
		}
		}
		break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case STAR:
		case EQUAL:
		case ITAL:
		case LINK_OPEN:
		case IMAGE_OPEN:
		case NOWIKI_OPEN:
		case EXTENSION:
		case FORCED_LINEBREAK:
		case ESCAPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 45:
		case 47:
		case 48:
		case 49:
		case 51:
		case 53:
		case 54:
		case 55:
		case 57:
		case 58:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 69:
		case 70:
		case 71:
		case 72:
		case 74:
		case 76:
		{
		alt117=2;
		}
		break;
		default:
		if (backtracking>0) {failed=true; return link;}
		NoViableAltException nvae =
			new NoViableAltException("578:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 117, 0, input);

		throw nvae;
		}

		switch (alt117) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:579:4: li= link_interwiki_uri ':' p= link_interwiki_pagename
			{
			pushFollow(FOLLOW_link_interwiki_uri_in_link_address3357);
			li=link_interwiki_uri();
			_fsp--;
			if (failed) return link;
			match(input,43,FOLLOW_43_in_link_address3360); if (failed) return link;
			pushFollow(FOLLOW_link_interwiki_pagename_in_link_address3367);
			p=link_interwiki_pagename();
			_fsp--;
			if (failed) return link;
			if ( backtracking==0 ) {
			   
								li.setUri(p.toString());
								link = li;
							
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:583:4: lu= link_uri
			{
			pushFollow(FOLLOW_link_uri_in_link_address3378);
			lu=link_uri();
			_fsp--;
			if (failed) return link;
			if ( backtracking==0 ) {
			  link = new LinkNode(lu.toString()); 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return link;
	}
	// $ANTLR end link_address


	// $ANTLR start link_interwiki_uri
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:585:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );
	public final InterwikiLinkNode link_interwiki_uri() throws RecognitionException {
	InterwikiLinkNode interwiki =  null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:586:2: ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' )
		int alt118=20;
		switch ( input.LA(1) ) {
		case 44:
		{
		alt118=1;
		}
		break;
		case 46:
		{
		alt118=2;
		}
		break;
		case 52:
		{
		alt118=3;
		}
		break;
		case 56:
		{
		alt118=4;
		}
		break;
		case 59:
		{
		alt118=5;
		}
		break;
		case 62:
		{
		int LA118_6 = input.LA(2);

		if ( (LA118_6==58) ) {
			int LA118_15 = input.LA(3);

			if ( (LA118_15==66) ) {
			alt118=7;
			}
			else if ( (LA118_15==63) ) {
			alt118=6;
			}
			else {
			if (backtracking>0) {failed=true; return interwiki;}
			NoViableAltException nvae =
				new NoViableAltException("585:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 118, 15, input);

			throw nvae;
			}
		}
		else if ( (LA118_6==47) ) {
			alt118=8;
		}
		else {
			if (backtracking>0) {failed=true; return interwiki;}
			NoViableAltException nvae =
			new NoViableAltException("585:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 118, 6, input);

			throw nvae;
		}
		}
		break;
		case 68:
		{
		int LA118_7 = input.LA(2);

		if ( (LA118_7==66) ) {
			alt118=9;
		}
		else if ( (LA118_7==71) ) {
			alt118=10;
		}
		else {
			if (backtracking>0) {failed=true; return interwiki;}
			NoViableAltException nvae =
			new NoViableAltException("585:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 118, 7, input);

			throw nvae;
		}
		}
		break;
		case 61:
		{
		int LA118_8 = input.LA(2);

		if ( (LA118_8==49) ) {
			int LA118_19 = input.LA(3);

			if ( (LA118_19==55) ) {
			alt118=13;
			}
			else if ( (LA118_19==48) ) {
			alt118=12;
			}
			else {
			if (backtracking>0) {failed=true; return interwiki;}
			NoViableAltException nvae =
				new NoViableAltException("585:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 118, 19, input);

			throw nvae;
			}
		}
		else if ( (LA118_8==69) ) {
			alt118=11;
		}
		else {
			if (backtracking>0) {failed=true; return interwiki;}
			NoViableAltException nvae =
			new NoViableAltException("585:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 118, 8, input);

			throw nvae;
		}
		}
		break;
		case 73:
		{
		alt118=14;
		}
		break;
		case 60:
		{
		alt118=15;
		}
		break;
		case 75:
		{
		int LA118_11 = input.LA(2);

		if ( (LA118_11==50) ) {
			alt118=17;
		}
		else if ( (LA118_11==51) ) {
			alt118=16;
		}
		else {
			if (backtracking>0) {failed=true; return interwiki;}
			NoViableAltException nvae =
			new NoViableAltException("585:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 118, 11, input);

			throw nvae;
		}
		}
		break;
		case 77:
		{
		alt118=18;
		}
		break;
		case 50:
		{
		alt118=19;
		}
		break;
		case 78:
		{
		alt118=20;
		}
		break;
		default:
		if (backtracking>0) {failed=true; return interwiki;}
		NoViableAltException nvae =
			new NoViableAltException("585:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 118, 0, input);

		throw nvae;
		}

		switch (alt118) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:586:4: 'C' '2'
			{
			match(input,44,FOLLOW_44_in_link_interwiki_uri3394); if (failed) return interwiki;
			match(input,45,FOLLOW_45_in_link_interwiki_uri3396); if (failed) return interwiki;

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:587:4: 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i'
			{
			match(input,46,FOLLOW_46_in_link_interwiki_uri3401); if (failed) return interwiki;
			match(input,47,FOLLOW_47_in_link_interwiki_uri3403); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3405); if (failed) return interwiki;
			match(input,49,FOLLOW_49_in_link_interwiki_uri3407); if (failed) return interwiki;
			match(input,50,FOLLOW_50_in_link_interwiki_uri3409); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3411); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3413); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3415); if (failed) return interwiki;

			}
			break;
		case 3 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:588:4: 'F' 'l' 'i' 'c' 'k' 'r'
			{
			match(input,52,FOLLOW_52_in_link_interwiki_uri3420); if (failed) return interwiki;
			match(input,53,FOLLOW_53_in_link_interwiki_uri3422); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3424); if (failed) return interwiki;
			match(input,54,FOLLOW_54_in_link_interwiki_uri3426); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3428); if (failed) return interwiki;
			match(input,55,FOLLOW_55_in_link_interwiki_uri3430); if (failed) return interwiki;

			}
			break;
		case 4 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:589:4: 'G' 'o' 'o' 'g' 'l' 'e'
			{
			match(input,56,FOLLOW_56_in_link_interwiki_uri3435); if (failed) return interwiki;
			match(input,47,FOLLOW_47_in_link_interwiki_uri3437); if (failed) return interwiki;
			match(input,47,FOLLOW_47_in_link_interwiki_uri3439); if (failed) return interwiki;
			match(input,57,FOLLOW_57_in_link_interwiki_uri3441); if (failed) return interwiki;
			match(input,53,FOLLOW_53_in_link_interwiki_uri3443); if (failed) return interwiki;
			match(input,58,FOLLOW_58_in_link_interwiki_uri3445); if (failed) return interwiki;

			}
			break;
		case 5 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:590:4: 'J' 'S' 'P' 'W' 'i' 'k' 'i'
			{
			match(input,59,FOLLOW_59_in_link_interwiki_uri3450); if (failed) return interwiki;
			match(input,60,FOLLOW_60_in_link_interwiki_uri3452); if (failed) return interwiki;
			match(input,61,FOLLOW_61_in_link_interwiki_uri3454); if (failed) return interwiki;
			match(input,50,FOLLOW_50_in_link_interwiki_uri3456); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3458); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3460); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3462); if (failed) return interwiki;

			}
			break;
		case 6 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:591:4: 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l'
			{
			match(input,62,FOLLOW_62_in_link_interwiki_uri3467); if (failed) return interwiki;
			match(input,58,FOLLOW_58_in_link_interwiki_uri3469); if (failed) return interwiki;
			match(input,63,FOLLOW_63_in_link_interwiki_uri3471); if (failed) return interwiki;
			match(input,64,FOLLOW_64_in_link_interwiki_uri3473); if (failed) return interwiki;
			match(input,65,FOLLOW_65_in_link_interwiki_uri3475); if (failed) return interwiki;
			match(input,63,FOLLOW_63_in_link_interwiki_uri3477); if (failed) return interwiki;
			match(input,53,FOLLOW_53_in_link_interwiki_uri3479); if (failed) return interwiki;
			match(input,53,FOLLOW_53_in_link_interwiki_uri3481); if (failed) return interwiki;

			}
			break;
		case 7 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:592:4: 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i'
			{
			match(input,62,FOLLOW_62_in_link_interwiki_uri3486); if (failed) return interwiki;
			match(input,58,FOLLOW_58_in_link_interwiki_uri3488); if (failed) return interwiki;
			match(input,66,FOLLOW_66_in_link_interwiki_uri3490); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3492); if (failed) return interwiki;
			match(input,63,FOLLOW_63_in_link_interwiki_uri3494); if (failed) return interwiki;
			match(input,50,FOLLOW_50_in_link_interwiki_uri3496); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3498); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3500); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3502); if (failed) return interwiki;

			}
			break;
		case 8 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:593:4: 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n'
			{
			match(input,62,FOLLOW_62_in_link_interwiki_uri3507); if (failed) return interwiki;
			match(input,47,FOLLOW_47_in_link_interwiki_uri3509); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3511); if (failed) return interwiki;
			match(input,67,FOLLOW_67_in_link_interwiki_uri3513); if (failed) return interwiki;
			match(input,62,FOLLOW_62_in_link_interwiki_uri3515); if (failed) return interwiki;
			match(input,47,FOLLOW_47_in_link_interwiki_uri3517); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3519); if (failed) return interwiki;
			match(input,67,FOLLOW_67_in_link_interwiki_uri3521); if (failed) return interwiki;

			}
			break;
		case 9 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:594:4: 'O' 'd' 'd' 'm' 'u' 's' 'e'
			{
			match(input,68,FOLLOW_68_in_link_interwiki_uri3526); if (failed) return interwiki;
			match(input,66,FOLLOW_66_in_link_interwiki_uri3528); if (failed) return interwiki;
			match(input,66,FOLLOW_66_in_link_interwiki_uri3530); if (failed) return interwiki;
			match(input,69,FOLLOW_69_in_link_interwiki_uri3532); if (failed) return interwiki;
			match(input,49,FOLLOW_49_in_link_interwiki_uri3534); if (failed) return interwiki;
			match(input,70,FOLLOW_70_in_link_interwiki_uri3536); if (failed) return interwiki;
			match(input,58,FOLLOW_58_in_link_interwiki_uri3538); if (failed) return interwiki;

			}
			break;
		case 10 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:595:4: 'O' 'h' 'a' 'n' 'a'
			{
			match(input,68,FOLLOW_68_in_link_interwiki_uri3543); if (failed) return interwiki;
			match(input,71,FOLLOW_71_in_link_interwiki_uri3545); if (failed) return interwiki;
			match(input,63,FOLLOW_63_in_link_interwiki_uri3547); if (failed) return interwiki;
			match(input,67,FOLLOW_67_in_link_interwiki_uri3549); if (failed) return interwiki;
			match(input,63,FOLLOW_63_in_link_interwiki_uri3551); if (failed) return interwiki;

			}
			break;
		case 11 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:596:4: 'P' 'm' 'W' 'i' 'k' 'i'
			{
			match(input,61,FOLLOW_61_in_link_interwiki_uri3556); if (failed) return interwiki;
			match(input,69,FOLLOW_69_in_link_interwiki_uri3558); if (failed) return interwiki;
			match(input,50,FOLLOW_50_in_link_interwiki_uri3560); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3562); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3564); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3566); if (failed) return interwiki;

			}
			break;
		case 12 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:597:4: 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i'
			{
			match(input,61,FOLLOW_61_in_link_interwiki_uri3571); if (failed) return interwiki;
			match(input,49,FOLLOW_49_in_link_interwiki_uri3573); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3575); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3577); if (failed) return interwiki;
			match(input,50,FOLLOW_50_in_link_interwiki_uri3579); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3581); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3583); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3585); if (failed) return interwiki;

			}
			break;
		case 13 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:598:4: 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i'
			{
			match(input,61,FOLLOW_61_in_link_interwiki_uri3590); if (failed) return interwiki;
			match(input,49,FOLLOW_49_in_link_interwiki_uri3592); if (failed) return interwiki;
			match(input,55,FOLLOW_55_in_link_interwiki_uri3594); if (failed) return interwiki;
			match(input,72,FOLLOW_72_in_link_interwiki_uri3596); if (failed) return interwiki;
			match(input,53,FOLLOW_53_in_link_interwiki_uri3598); if (failed) return interwiki;
			match(input,58,FOLLOW_58_in_link_interwiki_uri3600); if (failed) return interwiki;
			match(input,50,FOLLOW_50_in_link_interwiki_uri3602); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3604); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3606); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3608); if (failed) return interwiki;

			}
			break;
		case 14 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:599:4: 'R' 'a' 'd' 'e' 'o' 'x'
			{
			match(input,73,FOLLOW_73_in_link_interwiki_uri3613); if (failed) return interwiki;
			match(input,63,FOLLOW_63_in_link_interwiki_uri3615); if (failed) return interwiki;
			match(input,66,FOLLOW_66_in_link_interwiki_uri3617); if (failed) return interwiki;
			match(input,58,FOLLOW_58_in_link_interwiki_uri3619); if (failed) return interwiki;
			match(input,47,FOLLOW_47_in_link_interwiki_uri3621); if (failed) return interwiki;
			match(input,74,FOLLOW_74_in_link_interwiki_uri3623); if (failed) return interwiki;

			}
			break;
		case 15 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:600:4: 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p'
			{
			match(input,60,FOLLOW_60_in_link_interwiki_uri3628); if (failed) return interwiki;
			match(input,67,FOLLOW_67_in_link_interwiki_uri3630); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3632); if (failed) return interwiki;
			match(input,72,FOLLOW_72_in_link_interwiki_uri3634); if (failed) return interwiki;
			match(input,60,FOLLOW_60_in_link_interwiki_uri3636); if (failed) return interwiki;
			match(input,67,FOLLOW_67_in_link_interwiki_uri3638); if (failed) return interwiki;
			match(input,63,FOLLOW_63_in_link_interwiki_uri3640); if (failed) return interwiki;
			match(input,72,FOLLOW_72_in_link_interwiki_uri3642); if (failed) return interwiki;

			}
			break;
		case 16 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:601:4: 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i'
			{
			match(input,75,FOLLOW_75_in_link_interwiki_uri3647); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3649); if (failed) return interwiki;
			match(input,66,FOLLOW_66_in_link_interwiki_uri3651); if (failed) return interwiki;
			match(input,66,FOLLOW_66_in_link_interwiki_uri3653); if (failed) return interwiki;
			match(input,53,FOLLOW_53_in_link_interwiki_uri3655); if (failed) return interwiki;
			match(input,76,FOLLOW_76_in_link_interwiki_uri3657); if (failed) return interwiki;
			match(input,50,FOLLOW_50_in_link_interwiki_uri3659); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3661); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3663); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3665); if (failed) return interwiki;

			}
			break;
		case 17 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:602:4: 'T' 'W' 'i' 'k' 'i'
			{
			match(input,75,FOLLOW_75_in_link_interwiki_uri3670); if (failed) return interwiki;
			match(input,50,FOLLOW_50_in_link_interwiki_uri3672); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3674); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3676); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3678); if (failed) return interwiki;

			}
			break;
		case 18 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:603:4: 'U' 's' 'e' 'm' 'o' 'd'
			{
			match(input,77,FOLLOW_77_in_link_interwiki_uri3683); if (failed) return interwiki;
			match(input,70,FOLLOW_70_in_link_interwiki_uri3685); if (failed) return interwiki;
			match(input,58,FOLLOW_58_in_link_interwiki_uri3687); if (failed) return interwiki;
			match(input,69,FOLLOW_69_in_link_interwiki_uri3689); if (failed) return interwiki;
			match(input,47,FOLLOW_47_in_link_interwiki_uri3691); if (failed) return interwiki;
			match(input,66,FOLLOW_66_in_link_interwiki_uri3693); if (failed) return interwiki;

			}
			break;
		case 19 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:604:4: 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a'
			{
			match(input,50,FOLLOW_50_in_link_interwiki_uri3698); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3700); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3702); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3704); if (failed) return interwiki;
			match(input,72,FOLLOW_72_in_link_interwiki_uri3706); if (failed) return interwiki;
			match(input,58,FOLLOW_58_in_link_interwiki_uri3708); if (failed) return interwiki;
			match(input,66,FOLLOW_66_in_link_interwiki_uri3710); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3712); if (failed) return interwiki;
			match(input,63,FOLLOW_63_in_link_interwiki_uri3714); if (failed) return interwiki;

			}
			break;
		case 20 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:605:4: 'X' 'W' 'i' 'k' 'i'
			{
			match(input,78,FOLLOW_78_in_link_interwiki_uri3719); if (failed) return interwiki;
			match(input,50,FOLLOW_50_in_link_interwiki_uri3721); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3723); if (failed) return interwiki;
			match(input,48,FOLLOW_48_in_link_interwiki_uri3725); if (failed) return interwiki;
			match(input,51,FOLLOW_51_in_link_interwiki_uri3727); if (failed) return interwiki;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return interwiki;
	}
	// $ANTLR end link_interwiki_uri


	// $ANTLR start link_interwiki_pagename
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:607:1: link_interwiki_pagename returns [StringBundler text = new StringBundler()] : (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ ;
	public final StringBundler link_interwiki_pagename() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:608:2: ( (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:608:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:608:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
		int cnt119=0;
		loop119:
		do {
		int alt119=2;
		int LA119_0 = input.LA(1);

		if ( ((LA119_0>=FORCED_END_OF_LINE && LA119_0<=WIKI)||(LA119_0>=POUND && LA119_0<=EQUAL)||(LA119_0>=ITAL && LA119_0<=NOWIKI_CLOSE)||(LA119_0>=IMAGE_CLOSE && LA119_0<=78)) ) {
			alt119=1;
		}


		switch (alt119) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:608:6: c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF )
			{
			c=(Token)input.LT(1);
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=EQUAL)||(input.LA(1)>=ITAL && input.LA(1)<=NOWIKI_CLOSE)||(input.LA(1)>=IMAGE_CLOSE && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_link_interwiki_pagename3747);	  throw mse;
			}

			if ( backtracking==0 ) {
			   text.append(c.getText()); 
			}

			}
			break;

		default :
			if ( cnt119 >= 1 ) break loop119;
			if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
				new EarlyExitException(119, input);
			throw eee;
		}
		cnt119++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end link_interwiki_pagename


	// $ANTLR start link_description
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:610:1: link_description returns [CollectionNode node = new CollectionNode()] : (l= link_descriptionpart | i= image )+ ;
	public final CollectionNode link_description() throws RecognitionException {
	CollectionNode node =  new CollectionNode();

	ASTNode l = null;

	ImageNode i = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:611:2: ( (l= link_descriptionpart | i= image )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:611:4: (l= link_descriptionpart | i= image )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:611:4: (l= link_descriptionpart | i= image )+
		int cnt120=0;
		loop120:
		do {
		int alt120=3;
		int LA120_0 = input.LA(1);

		if ( ((LA120_0>=FORCED_END_OF_LINE && LA120_0<=WIKI)||(LA120_0>=POUND && LA120_0<=ITAL)||(LA120_0>=FORCED_LINEBREAK && LA120_0<=NOWIKI_CLOSE)||(LA120_0>=IMAGE_CLOSE && LA120_0<=78)) ) {
			alt120=1;
		}
		else if ( (LA120_0==IMAGE_OPEN) ) {
			alt120=2;
		}


		switch (alt120) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:611:6: l= link_descriptionpart
			{
			pushFollow(FOLLOW_link_descriptionpart_in_link_description3790);
			l=link_descriptionpart();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {

							// Recover code: some bad syntax could include null elements in the collection		
							if(l != null) {
								node.add(l);
							}
						
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:617:5: i= image
			{
			pushFollow(FOLLOW_image_in_link_description3802);
			i=image();
			_fsp--;
			if (failed) return node;
			if ( backtracking==0 ) {
			  node.add(i);
			}

			}
			break;

		default :
			if ( cnt120 >= 1 ) break loop120;
			if (backtracking>0) {failed=true; return node;}
			EarlyExitException eee =
				new EarlyExitException(120, input);
			throw eee;
		}
		cnt120++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end link_description

	protected static class link_descriptionpart_scope {
	CollectionNode element;
	}
	protected Stack link_descriptionpart_stack = new Stack();


	// $ANTLR start link_descriptionpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:619:1: link_descriptionpart returns [ASTNode text = null] : ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ );
	public final ASTNode link_descriptionpart() throws RecognitionException {
	link_descriptionpart_stack.push(new link_descriptionpart_scope());
	ASTNode text =	null;

	ASTNode lb = null;

	ASTNode li = null;

	CollectionNode t = null;



		((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element = new CollectionNode();

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:626:2: ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ )
		int alt124=3;
		switch ( input.LA(1) ) {
		case STAR:
		{
		int LA124_1 = input.LA(2);

		if ( (LA124_1==STAR) ) {
			alt124=1;
		}
		else if ( ((LA124_1>=FORCED_END_OF_LINE && LA124_1<=WIKI)||LA124_1==POUND||(LA124_1>=EQUAL && LA124_1<=PIPE)||(LA124_1>=FORCED_LINEBREAK && LA124_1<=NOWIKI_CLOSE)||(LA124_1>=IMAGE_CLOSE && LA124_1<=78)) ) {
			alt124=3;
		}
		else {
			if (backtracking>0) {failed=true; return text;}
			NoViableAltException nvae =
			new NoViableAltException("619:1: link_descriptionpart returns [ASTNode text = null] : ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ );", 124, 1, input);

			throw nvae;
		}
		}
		break;
		case ITAL:
		{
		alt124=2;
		}
		break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case EQUAL:
		case PIPE:
		case FORCED_LINEBREAK:
		case ESCAPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case IMAGE_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
		{
		alt124=3;
		}
		break;
		default:
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
			new NoViableAltException("619:1: link_descriptionpart returns [ASTNode text = null] : ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ );", 124, 0, input);

		throw nvae;
		}

		switch (alt124) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:626:4: bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup
			{
			pushFollow(FOLLOW_bold_markup_in_link_descriptionpart3827);
			bold_markup();
			_fsp--;
			if (failed) return text;
			pushFollow(FOLLOW_onestar_in_link_descriptionpart3830);
			onestar();
			_fsp--;
			if (failed) return text;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:626:25: (lb= link_bold_descriptionpart onestar )+
			int cnt121=0;
			loop121:
			do {
			int alt121=2;
			int LA121_0 = input.LA(1);

			if ( ((LA121_0>=FORCED_END_OF_LINE && LA121_0<=WIKI)||LA121_0==POUND||(LA121_0>=EQUAL && LA121_0<=ITAL)||(LA121_0>=FORCED_LINEBREAK && LA121_0<=NOWIKI_CLOSE)||(LA121_0>=IMAGE_CLOSE && LA121_0<=78)) ) {
				alt121=1;
			}


			switch (alt121) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:626:27: lb= link_bold_descriptionpart onestar
				{
				pushFollow(FOLLOW_link_bold_descriptionpart_in_link_descriptionpart3838);
				lb=link_bold_descriptionpart();
				_fsp--;
				if (failed) return text;
				if ( backtracking==0 ) {
				  ((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element.add(lb);
				}
				pushFollow(FOLLOW_onestar_in_link_descriptionpart3843);
				onestar();
				_fsp--;
				if (failed) return text;

				}
				break;

			default :
				if ( cnt121 >= 1 ) break loop121;
				if (backtracking>0) {failed=true; return text;}
				EarlyExitException eee =
					new EarlyExitException(121, input);
				throw eee;
			}
			cnt121++;
			} while (true);

			if ( backtracking==0 ) {
			  text = new BoldTextNode(((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element);
			}
			pushFollow(FOLLOW_bold_markup_in_link_descriptionpart3853);
			bold_markup();
			_fsp--;
			if (failed) return text;

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:628:4: ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup
			{
			pushFollow(FOLLOW_ital_markup_in_link_descriptionpart3858);
			ital_markup();
			_fsp--;
			if (failed) return text;
			pushFollow(FOLLOW_onestar_in_link_descriptionpart3861);
			onestar();
			_fsp--;
			if (failed) return text;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:628:26: (li= link_ital_descriptionpart onestar )+
			int cnt122=0;
			loop122:
			do {
			int alt122=2;
			int LA122_0 = input.LA(1);

			if ( ((LA122_0>=FORCED_END_OF_LINE && LA122_0<=WIKI)||(LA122_0>=POUND && LA122_0<=PIPE)||(LA122_0>=FORCED_LINEBREAK && LA122_0<=NOWIKI_CLOSE)||(LA122_0>=IMAGE_CLOSE && LA122_0<=78)) ) {
				alt122=1;
			}


			switch (alt122) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:628:28: li= link_ital_descriptionpart onestar
				{
				pushFollow(FOLLOW_link_ital_descriptionpart_in_link_descriptionpart3870);
				li=link_ital_descriptionpart();
				_fsp--;
				if (failed) return text;
				if ( backtracking==0 ) {
				  ((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element.add(li);
				}
				pushFollow(FOLLOW_onestar_in_link_descriptionpart3875);
				onestar();
				_fsp--;
				if (failed) return text;

				}
				break;

			default :
				if ( cnt122 >= 1 ) break loop122;
				if (backtracking>0) {failed=true; return text;}
				EarlyExitException eee =
					new EarlyExitException(122, input);
				throw eee;
			}
			cnt122++;
			} while (true);

			if ( backtracking==0 ) {
			  text = new ItalicTextNode(((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element);
			}
			pushFollow(FOLLOW_ital_markup_in_link_descriptionpart3884);
			ital_markup();
			_fsp--;
			if (failed) return text;

			}
			break;
		case 3 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:630:4: onestar (t= link_descriptiontext onestar )+
			{
			pushFollow(FOLLOW_onestar_in_link_descriptionpart3889);
			onestar();
			_fsp--;
			if (failed) return text;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:630:13: (t= link_descriptiontext onestar )+
			int cnt123=0;
			loop123:
			do {
			int alt123=2;
			switch ( input.LA(1) ) {
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case PIPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case TABLE_OF_CONTENTS_TEXT:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
				{
				alt123=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt123=1;
				}
				break;
			case ESCAPE:
				{
				alt123=1;
				}
				break;

			}

			switch (alt123) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:630:15: t= link_descriptiontext onestar
				{
				pushFollow(FOLLOW_link_descriptiontext_in_link_descriptionpart3898);
				t=link_descriptiontext();
				_fsp--;
				if (failed) return text;
				pushFollow(FOLLOW_onestar_in_link_descriptionpart3901);
				onestar();
				_fsp--;
				if (failed) return text;
				if ( backtracking==0 ) {
				  ((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element.add(t);
				}

				}
				break;

			default :
				if ( cnt123 >= 1 ) break loop123;
				if (backtracking>0) {failed=true; return text;}
				EarlyExitException eee =
					new EarlyExitException(123, input);
				throw eee;
			}
			cnt123++;
			} while (true);

			if ( backtracking==0 ) {
			  text = new UnformattedTextNode(((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element);
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
		link_descriptionpart_stack.pop();
	}
	return text;
	}
	// $ANTLR end link_descriptionpart


	// $ANTLR start link_bold_descriptionpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:632:1: link_bold_descriptionpart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | ld= link_descriptiontext );
	public final ASTNode link_bold_descriptionpart() throws RecognitionException {
	ASTNode text =	null;

	CollectionNode t = null;

	CollectionNode ld = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:633:2: ( ital_markup t= link_boldital_description ital_markup | ld= link_descriptiontext )
		int alt125=2;
		int LA125_0 = input.LA(1);

		if ( (LA125_0==ITAL) ) {
		alt125=1;
		}
		else if ( ((LA125_0>=FORCED_END_OF_LINE && LA125_0<=WIKI)||LA125_0==POUND||(LA125_0>=EQUAL && LA125_0<=PIPE)||(LA125_0>=FORCED_LINEBREAK && LA125_0<=NOWIKI_CLOSE)||(LA125_0>=IMAGE_CLOSE && LA125_0<=78)) ) {
		alt125=2;
		}
		else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
			new NoViableAltException("632:1: link_bold_descriptionpart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | ld= link_descriptiontext );", 125, 0, input);

		throw nvae;
		}
		switch (alt125) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:633:4: ital_markup t= link_boldital_description ital_markup
			{
			pushFollow(FOLLOW_ital_markup_in_link_bold_descriptionpart3921);
			ital_markup();
			_fsp--;
			if (failed) return text;
			pushFollow(FOLLOW_link_boldital_description_in_link_bold_descriptionpart3928);
			t=link_boldital_description();
			_fsp--;
			if (failed) return text;
			if ( backtracking==0 ) {
			  text = new ItalicTextNode(t);
			}
			pushFollow(FOLLOW_ital_markup_in_link_bold_descriptionpart3933);
			ital_markup();
			_fsp--;
			if (failed) return text;

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:634:4: ld= link_descriptiontext
			{
			pushFollow(FOLLOW_link_descriptiontext_in_link_bold_descriptionpart3942);
			ld=link_descriptiontext();
			_fsp--;
			if (failed) return text;
			if ( backtracking==0 ) {
			  text =ld;
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end link_bold_descriptionpart


	// $ANTLR start link_ital_descriptionpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:636:1: link_ital_descriptionpart returns [ASTNode text = null] : ( bold_markup td= link_boldital_description bold_markup | t= link_descriptiontext );
	public final ASTNode link_ital_descriptionpart() throws RecognitionException {
	ASTNode text =	null;

	CollectionNode td = null;

	CollectionNode t = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:637:2: ( bold_markup td= link_boldital_description bold_markup | t= link_descriptiontext )
		int alt126=2;
		int LA126_0 = input.LA(1);

		if ( (LA126_0==STAR) ) {
		alt126=1;
		}
		else if ( ((LA126_0>=FORCED_END_OF_LINE && LA126_0<=WIKI)||LA126_0==POUND||(LA126_0>=EQUAL && LA126_0<=PIPE)||(LA126_0>=FORCED_LINEBREAK && LA126_0<=NOWIKI_CLOSE)||(LA126_0>=IMAGE_CLOSE && LA126_0<=78)) ) {
		alt126=2;
		}
		else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
			new NoViableAltException("636:1: link_ital_descriptionpart returns [ASTNode text = null] : ( bold_markup td= link_boldital_description bold_markup | t= link_descriptiontext );", 126, 0, input);

		throw nvae;
		}
		switch (alt126) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:637:4: bold_markup td= link_boldital_description bold_markup
			{
			pushFollow(FOLLOW_bold_markup_in_link_ital_descriptionpart3958);
			bold_markup();
			_fsp--;
			if (failed) return text;
			pushFollow(FOLLOW_link_boldital_description_in_link_ital_descriptionpart3965);
			td=link_boldital_description();
			_fsp--;
			if (failed) return text;
			pushFollow(FOLLOW_bold_markup_in_link_ital_descriptionpart3968);
			bold_markup();
			_fsp--;
			if (failed) return text;
			if ( backtracking==0 ) {
			  text = new BoldTextNode(td);
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:638:4: t= link_descriptiontext
			{
			pushFollow(FOLLOW_link_descriptiontext_in_link_ital_descriptionpart3979);
			t=link_descriptiontext();
			_fsp--;
			if (failed) return text;
			if ( backtracking==0 ) {
			  text = t; 
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end link_ital_descriptionpart


	// $ANTLR start link_boldital_description
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:640:1: link_boldital_description returns [CollectionNode text = new CollectionNode()] : onestar (t= link_descriptiontext onestar )+ ;
	public final CollectionNode link_boldital_description() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	CollectionNode t = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:641:2: ( onestar (t= link_descriptiontext onestar )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:641:4: onestar (t= link_descriptiontext onestar )+
		{
		pushFollow(FOLLOW_onestar_in_link_boldital_description3995);
		onestar();
		_fsp--;
		if (failed) return text;
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:641:13: (t= link_descriptiontext onestar )+
		int cnt127=0;
		loop127:
		do {
		int alt127=2;
		int LA127_0 = input.LA(1);

		if ( ((LA127_0>=FORCED_END_OF_LINE && LA127_0<=WIKI)||LA127_0==POUND||(LA127_0>=EQUAL && LA127_0<=PIPE)||(LA127_0>=FORCED_LINEBREAK && LA127_0<=NOWIKI_CLOSE)||(LA127_0>=IMAGE_CLOSE && LA127_0<=78)) ) {
			alt127=1;
		}


		switch (alt127) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:641:15: t= link_descriptiontext onestar
			{
			pushFollow(FOLLOW_link_descriptiontext_in_link_boldital_description4004);
			t=link_descriptiontext();
			_fsp--;
			if (failed) return text;
			pushFollow(FOLLOW_onestar_in_link_boldital_description4007);
			onestar();
			_fsp--;
			if (failed) return text;
			if ( backtracking==0 ) {

							for (ASTNode item:t.getASTNodes()) {
								text.add(item);
							}
								   
			}

			}
			break;

		default :
			if ( cnt127 >= 1 ) break loop127;
			if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
				new EarlyExitException(127, input);
			throw eee;
		}
		cnt127++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end link_boldital_description


	// $ANTLR start link_descriptiontext
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:647:1: link_descriptiontext returns [CollectionNode text = new CollectionNode()] : (t= link_descriptiontext_simple | ( forced_linebreak | e= escaped )+ );
	public final CollectionNode link_descriptiontext() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	StringBundler t = null;

	ScapedNode e = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:648:2: (t= link_descriptiontext_simple | ( forced_linebreak | e= escaped )+ )
		int alt129=2;
		int LA129_0 = input.LA(1);

		if ( ((LA129_0>=FORCED_END_OF_LINE && LA129_0<=WIKI)||LA129_0==POUND||(LA129_0>=EQUAL && LA129_0<=PIPE)||(LA129_0>=NOWIKI_BLOCK_CLOSE && LA129_0<=NOWIKI_CLOSE)||(LA129_0>=IMAGE_CLOSE && LA129_0<=78)) ) {
		alt129=1;
		}
		else if ( ((LA129_0>=FORCED_LINEBREAK && LA129_0<=ESCAPE)) ) {
		alt129=2;
		}
		else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
			new NoViableAltException("647:1: link_descriptiontext returns [CollectionNode text = new CollectionNode()] : (t= link_descriptiontext_simple | ( forced_linebreak | e= escaped )+ );", 129, 0, input);

		throw nvae;
		}
		switch (alt129) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:648:5: t= link_descriptiontext_simple
			{
			pushFollow(FOLLOW_link_descriptiontext_simple_in_link_descriptiontext4030);
			t=link_descriptiontext_simple();
			_fsp--;
			if (failed) return text;
			if ( backtracking==0 ) {
			   text.add(new UnformattedTextNode(t.toString()));
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:649:5: ( forced_linebreak | e= escaped )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:649:5: ( forced_linebreak | e= escaped )+
			int cnt128=0;
			loop128:
			do {
			int alt128=3;
			int LA128_0 = input.LA(1);

			if ( (LA128_0==FORCED_LINEBREAK) ) {
				alt128=1;
			}
			else if ( (LA128_0==ESCAPE) ) {
				alt128=2;
			}


			switch (alt128) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:649:7: forced_linebreak
				{
				pushFollow(FOLLOW_forced_linebreak_in_link_descriptiontext4040);
				forced_linebreak();
				_fsp--;
				if (failed) return text;
				if ( backtracking==0 ) {
				  text.add(new ForcedEndOfLineNode());
				}

				}
				break;
			case 2 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:650:5: e= escaped
				{
				pushFollow(FOLLOW_escaped_in_link_descriptiontext4052);
				e=escaped();
				_fsp--;
				if (failed) return text;
				if ( backtracking==0 ) {
				  text.add(e);
				}

				}
				break;

			default :
				if ( cnt128 >= 1 ) break loop128;
				if (backtracking>0) {failed=true; return text;}
				EarlyExitException eee =
					new EarlyExitException(128, input);
				throw eee;
			}
			cnt128++;
			} while (true);


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end link_descriptiontext


	// $ANTLR start link_descriptiontext_simple
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:652:1: link_descriptiontext_simple returns [StringBundler text = new StringBundler()] : (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
	public final StringBundler link_descriptiontext_simple() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:653:2: ( (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:653:4: (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:653:4: (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
		int cnt130=0;
		loop130:
		do {
		int alt130=2;
		int LA130_0 = input.LA(1);

		if ( ((LA130_0>=FORCED_END_OF_LINE && LA130_0<=WIKI)||LA130_0==POUND||(LA130_0>=EQUAL && LA130_0<=PIPE)||(LA130_0>=NOWIKI_BLOCK_CLOSE && LA130_0<=NOWIKI_CLOSE)||(LA130_0>=IMAGE_CLOSE && LA130_0<=78)) ) {
			alt130=1;
		}


		switch (alt130) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:653:6: c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
			{
			c=(Token)input.LT(1);
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||(input.LA(1)>=EQUAL && input.LA(1)<=PIPE)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=NOWIKI_CLOSE)||(input.LA(1)>=IMAGE_CLOSE && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_link_descriptiontext_simple4077);	throw mse;
			}

			if ( backtracking==0 ) {
			   text.append(c.getText()); 
			}

			}
			break;

		default :
			if ( cnt130 >= 1 ) break loop130;
			if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
				new EarlyExitException(130, input);
			throw eee;
		}
		cnt130++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end link_descriptiontext_simple


	// $ANTLR start link_uri
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:665:1: link_uri returns [StringBundler text = new StringBundler()] : (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ ;
	public final StringBundler link_uri() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:666:2: ( (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:666:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:666:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
		int cnt131=0;
		loop131:
		do {
		int alt131=2;
		int LA131_0 = input.LA(1);

		if ( ((LA131_0>=FORCED_END_OF_LINE && LA131_0<=WIKI)||(LA131_0>=POUND && LA131_0<=EQUAL)||(LA131_0>=ITAL && LA131_0<=NOWIKI_CLOSE)||(LA131_0>=IMAGE_CLOSE && LA131_0<=78)) ) {
			alt131=1;
		}


		switch (alt131) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:666:6: c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF )
			{
			c=(Token)input.LT(1);
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=EQUAL)||(input.LA(1)>=ITAL && input.LA(1)<=NOWIKI_CLOSE)||(input.LA(1)>=IMAGE_CLOSE && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_link_uri4178);	throw mse;
			}

			if ( backtracking==0 ) {
			  text.append(c.getText()); 
			}

			}
			break;

		default :
			if ( cnt131 >= 1 ) break loop131;
			if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
				new EarlyExitException(131, input);
			throw eee;
		}
		cnt131++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end link_uri


	// $ANTLR start image
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:673:1: image returns [ImageNode image = new ImageNode()] : image_open_markup uri= image_uri (alt= image_alternative )? image_close_markup ;
	public final ImageNode image() throws RecognitionException {
	ImageNode image =  new ImageNode();

	StringBundler uri = null;

	CollectionNode alt = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:674:2: ( image_open_markup uri= image_uri (alt= image_alternative )? image_close_markup )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:674:4: image_open_markup uri= image_uri (alt= image_alternative )? image_close_markup
		{
		pushFollow(FOLLOW_image_open_markup_in_image4219);
		image_open_markup();
		_fsp--;
		if (failed) return image;
		pushFollow(FOLLOW_image_uri_in_image4225);
		uri=image_uri();
		_fsp--;
		if (failed) return image;
		if ( backtracking==0 ) {
		  image.setLink(uri.toString());
		}
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:674:79: (alt= image_alternative )?
		int alt132=2;
		int LA132_0 = input.LA(1);

		if ( (LA132_0==PIPE) ) {
		alt132=1;
		}
		switch (alt132) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:674:81: alt= image_alternative
			{
			pushFollow(FOLLOW_image_alternative_in_image4235);
			alt=image_alternative();
			_fsp--;
			if (failed) return image;
			if ( backtracking==0 ) {
			  image.setAltCollectionNode(alt);
			}

			}
			break;

		}

		pushFollow(FOLLOW_image_close_markup_in_image4244);
		image_close_markup();
		_fsp--;
		if (failed) return image;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return image;
	}
	// $ANTLR end image


	// $ANTLR start image_uri
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:677:1: image_uri returns [StringBundler link = new StringBundler()] : (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+ ;
	public final StringBundler image_uri() throws RecognitionException {
	StringBundler link =  new StringBundler();

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:678:2: ( (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:678:4: (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:678:4: (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+
		int cnt133=0;
		loop133:
		do {
		int alt133=2;
		int LA133_0 = input.LA(1);

		if ( ((LA133_0>=FORCED_END_OF_LINE && LA133_0<=WIKI)||(LA133_0>=POUND && LA133_0<=EQUAL)||(LA133_0>=ITAL && LA133_0<=LINK_CLOSE)||(LA133_0>=BLANKS && LA133_0<=78)) ) {
			alt133=1;
		}


		switch (alt133) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:678:5: c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF )
			{
			c=(Token)input.LT(1);
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=EQUAL)||(input.LA(1)>=ITAL && input.LA(1)<=LINK_CLOSE)||(input.LA(1)>=BLANKS && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return link;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_image_uri4263);	throw mse;
			}

			if ( backtracking==0 ) {
			  link.append(c.getText()); 
			}

			}
			break;

		default :
			if ( cnt133 >= 1 ) break loop133;
			if (backtracking>0) {failed=true; return link;}
			EarlyExitException eee =
				new EarlyExitException(133, input);
			throw eee;
		}
		cnt133++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return link;
	}
	// $ANTLR end image_uri


	// $ANTLR start image_alternative
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:680:1: image_alternative returns [CollectionNode alternative = new CollectionNode()] : image_alternative_markup (p= image_alternativepart )+ ;
	public final CollectionNode image_alternative() throws RecognitionException {
	CollectionNode alternative =  new CollectionNode();

	ASTNode p = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:681:2: ( image_alternative_markup (p= image_alternativepart )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:681:4: image_alternative_markup (p= image_alternativepart )+
		{
		pushFollow(FOLLOW_image_alternative_markup_in_image_alternative4298);
		image_alternative_markup();
		_fsp--;
		if (failed) return alternative;
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:681:30: (p= image_alternativepart )+
		int cnt134=0;
		loop134:
		do {
		int alt134=2;
		int LA134_0 = input.LA(1);

		if ( ((LA134_0>=FORCED_END_OF_LINE && LA134_0<=WIKI)||(LA134_0>=POUND && LA134_0<=ITAL)||(LA134_0>=FORCED_LINEBREAK && LA134_0<=LINK_CLOSE)||(LA134_0>=BLANKS && LA134_0<=78)) ) {
			alt134=1;
		}


		switch (alt134) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:681:32: p= image_alternativepart
			{
			pushFollow(FOLLOW_image_alternativepart_in_image_alternative4307);
			p=image_alternativepart();
			_fsp--;
			if (failed) return alternative;
			if ( backtracking==0 ) {
			  alternative.add(p); 
			}

			}
			break;

		default :
			if ( cnt134 >= 1 ) break loop134;
			if (backtracking>0) {failed=true; return alternative;}
			EarlyExitException eee =
				new EarlyExitException(134, input);
			throw eee;
		}
		cnt134++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return alternative;
	}
	// $ANTLR end image_alternative

	protected static class image_alternativepart_scope {
	CollectionNode elements;
	}
	protected Stack image_alternativepart_stack = new Stack();


	// $ANTLR start image_alternativepart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:683:1: image_alternativepart returns [ASTNode item = null] : ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ );
	public final ASTNode image_alternativepart() throws RecognitionException {
	image_alternativepart_stack.push(new image_alternativepart_scope());
	ASTNode item =	null;

	ASTNode t1 = null;

	ASTNode t2 = null;

	CollectionNode t3 = null;



	   ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements = new CollectionNode();

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:690:2: ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ )
		int alt138=3;
		switch ( input.LA(1) ) {
		case STAR:
		{
		int LA138_1 = input.LA(2);

		if ( (LA138_1==STAR) ) {
			alt138=1;
		}
		else if ( ((LA138_1>=FORCED_END_OF_LINE && LA138_1<=WIKI)||LA138_1==POUND||(LA138_1>=EQUAL && LA138_1<=PIPE)||(LA138_1>=FORCED_LINEBREAK && LA138_1<=LINK_CLOSE)||(LA138_1>=BLANKS && LA138_1<=78)) ) {
			alt138=3;
		}
		else {
			if (backtracking>0) {failed=true; return item;}
			NoViableAltException nvae =
			new NoViableAltException("683:1: image_alternativepart returns [ASTNode item = null] : ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ );", 138, 1, input);

			throw nvae;
		}
		}
		break;
		case ITAL:
		{
		alt138=2;
		}
		break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case EQUAL:
		case PIPE:
		case FORCED_LINEBREAK:
		case ESCAPE:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case LINK_CLOSE:
		case BLANKS:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
		{
		alt138=3;
		}
		break;
		default:
		if (backtracking>0) {failed=true; return item;}
		NoViableAltException nvae =
			new NoViableAltException("683:1: image_alternativepart returns [ASTNode item = null] : ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ );", 138, 0, input);

		throw nvae;
		}

		switch (alt138) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:690:4: bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup
			{
			pushFollow(FOLLOW_bold_markup_in_image_alternativepart4333);
			bold_markup();
			_fsp--;
			if (failed) return item;
			pushFollow(FOLLOW_onestar_in_image_alternativepart4336);
			onestar();
			_fsp--;
			if (failed) return item;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:690:26: (t1= image_bold_alternativepart onestar )+
			int cnt135=0;
			loop135:
			do {
			int alt135=2;
			int LA135_0 = input.LA(1);

			if ( (LA135_0==STAR) ) {
				int LA135_1 = input.LA(2);

				if ( ((LA135_1>=FORCED_END_OF_LINE && LA135_1<=WIKI)||LA135_1==POUND||(LA135_1>=EQUAL && LA135_1<=PIPE)||(LA135_1>=FORCED_LINEBREAK && LA135_1<=LINK_CLOSE)||(LA135_1>=BLANKS && LA135_1<=78)) ) {
				alt135=1;
				}


			}
			else if ( ((LA135_0>=FORCED_END_OF_LINE && LA135_0<=WIKI)||LA135_0==POUND||(LA135_0>=EQUAL && LA135_0<=ITAL)||(LA135_0>=FORCED_LINEBREAK && LA135_0<=LINK_CLOSE)||(LA135_0>=BLANKS && LA135_0<=78)) ) {
				alt135=1;
			}


			switch (alt135) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:690:28: t1= image_bold_alternativepart onestar
				{
				pushFollow(FOLLOW_image_bold_alternativepart_in_image_alternativepart4345);
				t1=image_bold_alternativepart();
				_fsp--;
				if (failed) return item;
				if ( backtracking==0 ) {
				  ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements.add(t1);
				}
				pushFollow(FOLLOW_onestar_in_image_alternativepart4350);
				onestar();
				_fsp--;
				if (failed) return item;

				}
				break;

			default :
				if ( cnt135 >= 1 ) break loop135;
				if (backtracking>0) {failed=true; return item;}
				EarlyExitException eee =
					new EarlyExitException(135, input);
				throw eee;
			}
			cnt135++;
			} while (true);

			pushFollow(FOLLOW_bold_markup_in_image_alternativepart4357);
			bold_markup();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			  item = new BoldTextNode(((image_alternativepart_scope)image_alternativepart_stack.peek()).elements);
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:692:4: ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup
			{
			pushFollow(FOLLOW_ital_markup_in_image_alternativepart4364);
			ital_markup();
			_fsp--;
			if (failed) return item;
			pushFollow(FOLLOW_onestar_in_image_alternativepart4367);
			onestar();
			_fsp--;
			if (failed) return item;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:692:26: (t2= image_ital_alternativepart onestar )+
			int cnt136=0;
			loop136:
			do {
			int alt136=2;
			int LA136_0 = input.LA(1);

			if ( ((LA136_0>=FORCED_END_OF_LINE && LA136_0<=WIKI)||(LA136_0>=POUND && LA136_0<=PIPE)||(LA136_0>=FORCED_LINEBREAK && LA136_0<=LINK_CLOSE)||(LA136_0>=BLANKS && LA136_0<=78)) ) {
				alt136=1;
			}


			switch (alt136) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:692:29: t2= image_ital_alternativepart onestar
				{
				pushFollow(FOLLOW_image_ital_alternativepart_in_image_alternativepart4377);
				t2=image_ital_alternativepart();
				_fsp--;
				if (failed) return item;
				if ( backtracking==0 ) {
				  ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements.add(t2);
				}
				pushFollow(FOLLOW_onestar_in_image_alternativepart4382);
				onestar();
				_fsp--;
				if (failed) return item;

				}
				break;

			default :
				if ( cnt136 >= 1 ) break loop136;
				if (backtracking>0) {failed=true; return item;}
				EarlyExitException eee =
					new EarlyExitException(136, input);
				throw eee;
			}
			cnt136++;
			} while (true);

			pushFollow(FOLLOW_ital_markup_in_image_alternativepart4389);
			ital_markup();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			  item = new ItalicTextNode(((image_alternativepart_scope)image_alternativepart_stack.peek()).elements);
			}

			}
			break;
		case 3 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:694:4: onestar (t3= image_alternativetext onestar )+
			{
			pushFollow(FOLLOW_onestar_in_image_alternativepart4396);
			onestar();
			_fsp--;
			if (failed) return item;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:694:13: (t3= image_alternativetext onestar )+
			int cnt137=0;
			loop137:
			do {
			int alt137=2;
			int LA137_0 = input.LA(1);

			if ( ((LA137_0>=FORCED_END_OF_LINE && LA137_0<=WIKI)||LA137_0==POUND||(LA137_0>=EQUAL && LA137_0<=PIPE)||(LA137_0>=ESCAPE && LA137_0<=LINK_CLOSE)||(LA137_0>=BLANKS && LA137_0<=78)) ) {
				alt137=1;
			}
			else if ( (LA137_0==FORCED_LINEBREAK) ) {
				alt137=1;
			}


			switch (alt137) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:694:15: t3= image_alternativetext onestar
				{
				pushFollow(FOLLOW_image_alternativetext_in_image_alternativepart4403);
				t3=image_alternativetext();
				_fsp--;
				if (failed) return item;
				if ( backtracking==0 ) {

								for (ASTNode n: t3.getASTNodes()) {
								   ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements.add(n);
								 }
									  
				}
				pushFollow(FOLLOW_onestar_in_image_alternativepart4408);
				onestar();
				_fsp--;
				if (failed) return item;

				}
				break;

			default :
				if ( cnt137 >= 1 ) break loop137;
				if (backtracking>0) {failed=true; return item;}
				EarlyExitException eee =
					new EarlyExitException(137, input);
				throw eee;
			}
			cnt137++;
			} while (true);

			if ( backtracking==0 ) {
			  item =new UnformattedTextNode(((image_alternativepart_scope)image_alternativepart_stack.peek()).elements);
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
		image_alternativepart_stack.pop();
	}
	return item;
	}
	// $ANTLR end image_alternativepart

	protected static class image_bold_alternativepart_scope {
	CollectionNode elements;
	}
	protected Stack image_bold_alternativepart_stack = new Stack();


	// $ANTLR start image_bold_alternativepart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:700:1: image_bold_alternativepart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | onestar (i= image_alternativetext onestar )+ );
	public final ASTNode image_bold_alternativepart() throws RecognitionException {
	image_bold_alternativepart_stack.push(new image_bold_alternativepart_scope());
	ASTNode text =	null;

	CollectionNode t = null;

	CollectionNode i = null;



	   ((image_bold_alternativepart_scope)image_bold_alternativepart_stack.peek()).elements = new CollectionNode();

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:707:2: ( ital_markup t= link_boldital_description ital_markup | onestar (i= image_alternativetext onestar )+ )
		int alt140=2;
		int LA140_0 = input.LA(1);

		if ( (LA140_0==ITAL) ) {
		alt140=1;
		}
		else if ( ((LA140_0>=FORCED_END_OF_LINE && LA140_0<=WIKI)||(LA140_0>=POUND && LA140_0<=PIPE)||(LA140_0>=FORCED_LINEBREAK && LA140_0<=LINK_CLOSE)||(LA140_0>=BLANKS && LA140_0<=78)) ) {
		alt140=2;
		}
		else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
			new NoViableAltException("700:1: image_bold_alternativepart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | onestar (i= image_alternativetext onestar )+ );", 140, 0, input);

		throw nvae;
		}
		switch (alt140) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:707:4: ital_markup t= link_boldital_description ital_markup
			{
			pushFollow(FOLLOW_ital_markup_in_image_bold_alternativepart4434);
			ital_markup();
			_fsp--;
			if (failed) return text;
			pushFollow(FOLLOW_link_boldital_description_in_image_bold_alternativepart4441);
			t=link_boldital_description();
			_fsp--;
			if (failed) return text;
			if ( backtracking==0 ) {
			  text = new ItalicTextNode(t); 
			}
			pushFollow(FOLLOW_ital_markup_in_image_bold_alternativepart4446);
			ital_markup();
			_fsp--;
			if (failed) return text;

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:708:4: onestar (i= image_alternativetext onestar )+
			{
			pushFollow(FOLLOW_onestar_in_image_bold_alternativepart4451);
			onestar();
			_fsp--;
			if (failed) return text;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:708:13: (i= image_alternativetext onestar )+
			int cnt139=0;
			loop139:
			do {
			int alt139=2;
			int LA139_0 = input.LA(1);

			if ( ((LA139_0>=FORCED_END_OF_LINE && LA139_0<=WIKI)||LA139_0==POUND||(LA139_0>=EQUAL && LA139_0<=PIPE)||(LA139_0>=ESCAPE && LA139_0<=LINK_CLOSE)||(LA139_0>=BLANKS && LA139_0<=78)) ) {
				alt139=1;
			}
			else if ( (LA139_0==FORCED_LINEBREAK) ) {
				alt139=1;
			}


			switch (alt139) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:708:15: i= image_alternativetext onestar
				{
				pushFollow(FOLLOW_image_alternativetext_in_image_bold_alternativepart4460);
				i=image_alternativetext();
				_fsp--;
				if (failed) return text;
				pushFollow(FOLLOW_onestar_in_image_bold_alternativepart4463);
				onestar();
				_fsp--;
				if (failed) return text;
				if ( backtracking==0 ) {
				   
								for (ASTNode item:i.getASTNodes()) {
									((image_ital_alternativepart_scope)image_ital_alternativepart_stack.peek()).elements.add(item);
								}
								
				}

				}
				break;

			default :
				if ( cnt139 >= 1 ) break loop139;
				if (backtracking>0) {failed=true; return text;}
				EarlyExitException eee =
					new EarlyExitException(139, input);
				throw eee;
			}
			cnt139++;
			} while (true);

			if ( backtracking==0 ) {
			  text = new UnformattedTextNode(((image_bold_alternativepart_scope)image_bold_alternativepart_stack.peek()).elements);
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
		image_bold_alternativepart_stack.pop();
	}
	return text;
	}
	// $ANTLR end image_bold_alternativepart

	protected static class image_ital_alternativepart_scope {
	CollectionNode elements;
	}
	protected Stack image_ital_alternativepart_stack = new Stack();


	// $ANTLR start image_ital_alternativepart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:715:1: image_ital_alternativepart returns [ASTNode text = null] : ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ );
	public final ASTNode image_ital_alternativepart() throws RecognitionException {
	image_ital_alternativepart_stack.push(new image_ital_alternativepart_scope());
	ASTNode text =	null;

	CollectionNode t = null;

	CollectionNode i = null;



	   ((image_ital_alternativepart_scope)image_ital_alternativepart_stack.peek()).elements = new CollectionNode();

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:722:2: ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ )
		int alt142=2;
		int LA142_0 = input.LA(1);

		if ( (LA142_0==STAR) ) {
		int LA142_1 = input.LA(2);

		if ( (LA142_1==STAR) ) {
			alt142=1;
		}
		else if ( ((LA142_1>=FORCED_END_OF_LINE && LA142_1<=WIKI)||LA142_1==POUND||(LA142_1>=EQUAL && LA142_1<=PIPE)||(LA142_1>=FORCED_LINEBREAK && LA142_1<=LINK_CLOSE)||(LA142_1>=BLANKS && LA142_1<=78)) ) {
			alt142=2;
		}
		else {
			if (backtracking>0) {failed=true; return text;}
			NoViableAltException nvae =
			new NoViableAltException("715:1: image_ital_alternativepart returns [ASTNode text = null] : ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ );", 142, 1, input);

			throw nvae;
		}
		}
		else if ( ((LA142_0>=FORCED_END_OF_LINE && LA142_0<=WIKI)||LA142_0==POUND||(LA142_0>=EQUAL && LA142_0<=PIPE)||(LA142_0>=FORCED_LINEBREAK && LA142_0<=LINK_CLOSE)||(LA142_0>=BLANKS && LA142_0<=78)) ) {
		alt142=2;
		}
		else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
			new NoViableAltException("715:1: image_ital_alternativepart returns [ASTNode text = null] : ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ );", 142, 0, input);

		throw nvae;
		}
		switch (alt142) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:722:4: bold_markup t= link_boldital_description bold_markup
			{
			pushFollow(FOLLOW_bold_markup_in_image_ital_alternativepart4492);
			bold_markup();
			_fsp--;
			if (failed) return text;
			pushFollow(FOLLOW_link_boldital_description_in_image_ital_alternativepart4499);
			t=link_boldital_description();
			_fsp--;
			if (failed) return text;
			if ( backtracking==0 ) {
			  text = new BoldTextNode(t); 
			}
			pushFollow(FOLLOW_bold_markup_in_image_ital_alternativepart4504);
			bold_markup();
			_fsp--;
			if (failed) return text;

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:723:4: onestar (i= image_alternativetext onestar )+
			{
			pushFollow(FOLLOW_onestar_in_image_ital_alternativepart4509);
			onestar();
			_fsp--;
			if (failed) return text;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:723:13: (i= image_alternativetext onestar )+
			int cnt141=0;
			loop141:
			do {
			int alt141=2;
			int LA141_0 = input.LA(1);

			if ( ((LA141_0>=FORCED_END_OF_LINE && LA141_0<=WIKI)||LA141_0==POUND||(LA141_0>=EQUAL && LA141_0<=PIPE)||(LA141_0>=ESCAPE && LA141_0<=LINK_CLOSE)||(LA141_0>=BLANKS && LA141_0<=78)) ) {
				alt141=1;
			}
			else if ( (LA141_0==FORCED_LINEBREAK) ) {
				alt141=1;
			}


			switch (alt141) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:723:14: i= image_alternativetext onestar
				{
				pushFollow(FOLLOW_image_alternativetext_in_image_ital_alternativepart4518);
				i=image_alternativetext();
				_fsp--;
				if (failed) return text;
				pushFollow(FOLLOW_onestar_in_image_ital_alternativepart4521);
				onestar();
				_fsp--;
				if (failed) return text;
				if ( backtracking==0 ) {
				   
								for (ASTNode item:i.getASTNodes()) {
									((image_ital_alternativepart_scope)image_ital_alternativepart_stack.peek()).elements.add(item);
								}
								
				}

				}
				break;

			default :
				if ( cnt141 >= 1 ) break loop141;
				if (backtracking>0) {failed=true; return text;}
				EarlyExitException eee =
					new EarlyExitException(141, input);
				throw eee;
			}
			cnt141++;
			} while (true);

			if ( backtracking==0 ) {
			  text = new UnformattedTextNode(((image_ital_alternativepart_scope)image_ital_alternativepart_stack.peek()).elements);
			}

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
		image_ital_alternativepart_stack.pop();
	}
	return text;
	}
	// $ANTLR end image_ital_alternativepart


	// $ANTLR start image_boldital_alternative
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:729:1: image_boldital_alternative returns [CollectionNode text = new CollectionNode()] : onestar (i= image_alternativetext onestar )+ ;
	public final CollectionNode image_boldital_alternative() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	CollectionNode i = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:730:2: ( onestar (i= image_alternativetext onestar )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:730:4: onestar (i= image_alternativetext onestar )+
		{
		pushFollow(FOLLOW_onestar_in_image_boldital_alternative4542);
		onestar();
		_fsp--;
		if (failed) return text;
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:730:13: (i= image_alternativetext onestar )+
		int cnt143=0;
		loop143:
		do {
		int alt143=2;
		int LA143_0 = input.LA(1);

		if ( ((LA143_0>=FORCED_END_OF_LINE && LA143_0<=WIKI)||LA143_0==POUND||(LA143_0>=EQUAL && LA143_0<=PIPE)||(LA143_0>=FORCED_LINEBREAK && LA143_0<=LINK_CLOSE)||(LA143_0>=BLANKS && LA143_0<=78)) ) {
			alt143=1;
		}


		switch (alt143) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:730:15: i= image_alternativetext onestar
			{
			pushFollow(FOLLOW_image_alternativetext_in_image_boldital_alternative4551);
			i=image_alternativetext();
			_fsp--;
			if (failed) return text;
			pushFollow(FOLLOW_onestar_in_image_boldital_alternative4554);
			onestar();
			_fsp--;
			if (failed) return text;
			if ( backtracking==0 ) {

							for (ASTNode item:i.getASTNodes()) {
								text.add(item);
							}
							
			}

			}
			break;

		default :
			if ( cnt143 >= 1 ) break loop143;
			if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
				new EarlyExitException(143, input);
			throw eee;
		}
		cnt143++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end image_boldital_alternative


	// $ANTLR start image_alternativetext
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:736:1: image_alternativetext returns [CollectionNode items = new CollectionNode()] : (contents= image_alternative_simple_text | ( forced_linebreak )+ );
	public final CollectionNode image_alternativetext() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	StringBundler contents = null;


	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:737:2: (contents= image_alternative_simple_text | ( forced_linebreak )+ )
		int alt145=2;
		int LA145_0 = input.LA(1);

		if ( ((LA145_0>=FORCED_END_OF_LINE && LA145_0<=WIKI)||LA145_0==POUND||(LA145_0>=EQUAL && LA145_0<=PIPE)||(LA145_0>=ESCAPE && LA145_0<=LINK_CLOSE)||(LA145_0>=BLANKS && LA145_0<=78)) ) {
		alt145=1;
		}
		else if ( (LA145_0==FORCED_LINEBREAK) ) {
		alt145=2;
		}
		else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
			new NoViableAltException("736:1: image_alternativetext returns [CollectionNode items = new CollectionNode()] : (contents= image_alternative_simple_text | ( forced_linebreak )+ );", 145, 0, input);

		throw nvae;
		}
		switch (alt145) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:737:4: contents= image_alternative_simple_text
			{
			pushFollow(FOLLOW_image_alternative_simple_text_in_image_alternativetext4577);
			contents=image_alternative_simple_text();
			_fsp--;
			if (failed) return items;
			if ( backtracking==0 ) {
			  items.add(new UnformattedTextNode(contents.toString())); 
			}

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:738:4: ( forced_linebreak )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:738:4: ( forced_linebreak )+
			int cnt144=0;
			loop144:
			do {
			int alt144=2;
			int LA144_0 = input.LA(1);

			if ( (LA144_0==FORCED_LINEBREAK) ) {
				alt144=1;
			}


			switch (alt144) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:738:5: forced_linebreak
				{
				pushFollow(FOLLOW_forced_linebreak_in_image_alternativetext4585);
				forced_linebreak();
				_fsp--;
				if (failed) return items;
				if ( backtracking==0 ) {
				  items.add(new ForcedEndOfLineNode());
				}

				}
				break;

			default :
				if ( cnt144 >= 1 ) break loop144;
				if (backtracking>0) {failed=true; return items;}
				EarlyExitException eee =
					new EarlyExitException(144, input);
				throw eee;
			}
			cnt144++;
			} while (true);


			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return items;
	}
	// $ANTLR end image_alternativetext


	// $ANTLR start image_alternative_simple_text
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:741:1: image_alternative_simple_text returns [StringBundler text = new StringBundler()] : (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+ ;
	public final StringBundler image_alternative_simple_text() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:742:2: ( (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:743:2: (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:743:2: (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+
		int cnt146=0;
		loop146:
		do {
		int alt146=2;
		int LA146_0 = input.LA(1);

		if ( ((LA146_0>=FORCED_END_OF_LINE && LA146_0<=WIKI)||LA146_0==POUND||(LA146_0>=EQUAL && LA146_0<=PIPE)||(LA146_0>=ESCAPE && LA146_0<=LINK_CLOSE)||(LA146_0>=BLANKS && LA146_0<=78)) ) {
			alt146=1;
		}


		switch (alt146) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:743:4: c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF )
			{
			c=(Token)input.LT(1);
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||(input.LA(1)>=EQUAL && input.LA(1)<=PIPE)||(input.LA(1)>=ESCAPE && input.LA(1)<=LINK_CLOSE)||(input.LA(1)>=BLANKS && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_image_alternative_simple_text4612);	throw mse;
			}

			if ( backtracking==0 ) {
			  text.append(c.getText()); 
			}

			}
			break;

		default :
			if ( cnt146 >= 1 ) break loop146;
			if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
				new EarlyExitException(146, input);
			throw eee;
		}
		cnt146++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return text;
	}
	// $ANTLR end image_alternative_simple_text


	// $ANTLR start extension
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:757:1: extension returns [ASTNode node = null] : extension_markup extension_handler blanks extension_statement extension_markup ;
	public final ASTNode extension() throws RecognitionException {
	ASTNode node =	null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:758:2: ( extension_markup extension_handler blanks extension_statement extension_markup )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:758:4: extension_markup extension_handler blanks extension_statement extension_markup
		{
		pushFollow(FOLLOW_extension_markup_in_extension4705);
		extension_markup();
		_fsp--;
		if (failed) return node;
		pushFollow(FOLLOW_extension_handler_in_extension4708);
		extension_handler();
		_fsp--;
		if (failed) return node;
		pushFollow(FOLLOW_blanks_in_extension4711);
		blanks();
		_fsp--;
		if (failed) return node;
		pushFollow(FOLLOW_extension_statement_in_extension4714);
		extension_statement();
		_fsp--;
		if (failed) return node;
		pushFollow(FOLLOW_extension_markup_in_extension4719);
		extension_markup();
		_fsp--;
		if (failed) return node;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return node;
	}
	// $ANTLR end extension


	// $ANTLR start extension_handler
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:761:1: extension_handler : (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+ ;
	public final void extension_handler() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:762:2: ( (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:762:4: (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:762:4: (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+
		int cnt147=0;
		loop147:
		do {
		int alt147=3;
		int LA147_0 = input.LA(1);

		if ( ((LA147_0>=FORCED_END_OF_LINE && LA147_0<=WIKI)||(LA147_0>=POUND && LA147_0<=NOWIKI_OPEN)||LA147_0==FORCED_LINEBREAK||(LA147_0>=NOWIKI_BLOCK_CLOSE && LA147_0<=IMAGE_CLOSE)||(LA147_0>=TABLE_OF_CONTENTS_TEXT && LA147_0<=78)) ) {
			alt147=1;
		}
		else if ( (LA147_0==ESCAPE) ) {
			alt147=2;
		}


		switch (alt147) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:762:5: ~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF )
			{
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=NOWIKI_OPEN)||input.LA(1)==FORCED_LINEBREAK||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=IMAGE_CLOSE)||(input.LA(1)>=TABLE_OF_CONTENTS_TEXT && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return ;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_extension_handler4731);	throw mse;
			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:762:64: escaped
			{
			pushFollow(FOLLOW_escaped_in_extension_handler4764);
			escaped();
			_fsp--;
			if (failed) return ;

			}
			break;

		default :
			if ( cnt147 >= 1 ) break loop147;
			if (backtracking>0) {failed=true; return ;}
			EarlyExitException eee =
				new EarlyExitException(147, input);
			throw eee;
		}
		cnt147++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end extension_handler


	// $ANTLR start extension_statement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:764:1: extension_statement : (~ ( EXTENSION | ESCAPE | EOF ) | escaped )* ;
	public final void extension_statement() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:765:2: ( (~ ( EXTENSION | ESCAPE | EOF ) | escaped )* )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:765:4: (~ ( EXTENSION | ESCAPE | EOF ) | escaped )*
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:765:4: (~ ( EXTENSION | ESCAPE | EOF ) | escaped )*
		loop148:
		do {
		int alt148=3;
		int LA148_0 = input.LA(1);

		if ( ((LA148_0>=FORCED_END_OF_LINE && LA148_0<=NOWIKI_OPEN)||LA148_0==FORCED_LINEBREAK||(LA148_0>=NOWIKI_BLOCK_CLOSE && LA148_0<=78)) ) {
			alt148=1;
		}
		else if ( (LA148_0==ESCAPE) ) {
			alt148=2;
		}


		switch (alt148) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:765:5: ~ ( EXTENSION | ESCAPE | EOF )
			{
			if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=NOWIKI_OPEN)||input.LA(1)==FORCED_LINEBREAK||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=78) ) {
			input.consume();
			errorRecovery=false;failed=false;
			}
			else {
			if (backtracking>0) {failed=true; return ;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_extension_statement4778);	throw mse;
			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:765:41: escaped
			{
			pushFollow(FOLLOW_escaped_in_extension_statement4799);
			escaped();
			_fsp--;
			if (failed) return ;

			}
			break;

		default :
			break loop148;
		}
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end extension_statement


	// $ANTLR start table_of_contents
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:771:1: table_of_contents returns [ASTNode tableOfContents = new TableOfContentsNode()] : TABLE_OF_CONTENTS_TEXT ;
	public final ASTNode table_of_contents() throws RecognitionException {
	ASTNode tableOfContents =  new TableOfContentsNode();

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:772:2: ( TABLE_OF_CONTENTS_TEXT )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:772:38: TABLE_OF_CONTENTS_TEXT
		{
		match(input,TABLE_OF_CONTENTS_TEXT,FOLLOW_TABLE_OF_CONTENTS_TEXT_in_table_of_contents4822); if (failed) return tableOfContents;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return tableOfContents;
	}
	// $ANTLR end table_of_contents


	// $ANTLR start onestar
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:776:1: onestar : ( ({...}? ( STAR )? ) | );
	public final void onestar() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:777:2: ( ({...}? ( STAR )? ) | )
		int alt150=2;
		switch ( input.LA(1) ) {
		case STAR:
		{
		int LA150_1 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 1, input);

			throw nvae;
		}
		}
		break;
		case BLANKS:
		{
		int LA150_2 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 2, input);

			throw nvae;
		}
		}
		break;
		case FORCED_LINEBREAK:
		{
		int LA150_3 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 3, input);

			throw nvae;
		}
		}
		break;
		case ESCAPE:
		{
		int LA150_4 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 4, input);

			throw nvae;
		}
		}
		break;
		case LINK_OPEN:
		{
		int LA150_5 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 5, input);

			throw nvae;
		}
		}
		break;
		case IMAGE_OPEN:
		{
		int LA150_6 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 6, input);

			throw nvae;
		}
		}
		break;
		case EXTENSION:
		{
		int LA150_7 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 7, input);

			throw nvae;
		}
		}
		break;
		case NOWIKI_OPEN:
		{
		int LA150_8 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 8, input);

			throw nvae;
		}
		}
		break;
		case NEWLINE:
		{
		int LA150_9 = input.LA(2);

		if ( ((( input.LA(2) != STAR && input.LA(2) != DASH && input.LA(2) != POUND && 
				input.LA(2) != EQUAL && input.LA(2) != NEWLINE )|| input.LA(2) != STAR )) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 9, input);

			throw nvae;
		}
		}
		break;
		case EOF:
		{
		int LA150_10 = input.LA(2);

		if ( ((( input.LA(2) != STAR && input.LA(2) != DASH && input.LA(2) != POUND && 
				input.LA(2) != EQUAL && input.LA(2) != NEWLINE )|| input.LA(2) != STAR )) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 10, input);

			throw nvae;
		}
		}
		break;
		case ITAL:
		{
		int LA150_11 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 11, input);

			throw nvae;
		}
		}
		break;
		case EQUAL:
		{
		int LA150_12 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 12, input);

			throw nvae;
		}
		}
		break;
		case FORCED_END_OF_LINE:
		case HEADING_SECTION:
		case HORIZONTAL_SECTION:
		case LIST_ITEM:
		case LIST_ITEM_PART:
		case NOWIKI_SECTION:
		case SCAPE_NODE:
		case TEXT_NODE:
		case UNORDERED_LIST:
		case UNFORMATTED_TEXT:
		case WIKI:
		case POUND:
		case NOWIKI_BLOCK_CLOSE:
		case NOWIKI_CLOSE:
		case TABLE_OF_CONTENTS_TEXT:
		case DASH:
		case CR:
		case LF:
		case SPACE:
		case TABULATOR:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
		{
		int LA150_13 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 13, input);

			throw nvae;
		}
		}
		break;
		case PIPE:
		{
		int LA150_14 = input.LA(2);

		if ( ((( input.LA(2) != STAR && input.LA(2) == EQUAL )|| input.LA(2) != STAR )) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 14, input);

			throw nvae;
		}
		}
		break;
		case LINK_CLOSE:
		{
		int LA150_15 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 15, input);

			throw nvae;
		}
		}
		break;
		case IMAGE_CLOSE:
		{
		int LA150_16 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
			alt150=1;
		}
		else if ( (true) ) {
			alt150=2;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 16, input);

			throw nvae;
		}
		}
		break;
		default:
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
			new NoViableAltException("776:1: onestar : ( ({...}? ( STAR )? ) | );", 150, 0, input);

		throw nvae;
		}

		switch (alt150) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:777:4: ({...}? ( STAR )? )
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:777:4: ({...}? ( STAR )? )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:777:6: {...}? ( STAR )?
			{
			if ( !( input.LA(2) != STAR ) ) {
			if (backtracking>0) {failed=true; return ;}
			throw new FailedPredicateException(input, "onestar", " input.LA(2) != STAR ");
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:777:32: ( STAR )?
			int alt149=2;
			int LA149_0 = input.LA(1);

			if ( (LA149_0==STAR) ) {
			alt149=1;
			}
			switch (alt149) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:777:34: STAR
				{
				match(input,STAR,FOLLOW_STAR_in_onestar4845); if (failed) return ;

				}
				break;

			}


			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:779:2: 
			{
			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end onestar


	// $ANTLR start escaped
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:780:1: escaped returns [ScapedNode scaped = new ScapedNode()] : ESCAPE c= . ;
	public final ScapedNode escaped() throws RecognitionException {
	ScapedNode scaped =  new ScapedNode();

	Token c=null;

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:781:2: ( ESCAPE c= . )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:781:4: ESCAPE c= .
		{
		match(input,ESCAPE,FOLLOW_ESCAPE_in_escaped4867); if (failed) return scaped;
		c=(Token)input.LT(1);
		matchAny(input); if (failed) return scaped;
		if ( backtracking==0 ) {
		   scaped.setContent(c.getText()) ; 
		}

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return scaped;
	}
	// $ANTLR end escaped


	// $ANTLR start paragraph_separator
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:784:1: paragraph_separator : ( ( newline )+ | EOF );
	public final void paragraph_separator() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:785:2: ( ( newline )+ | EOF )
		int alt152=2;
		int LA152_0 = input.LA(1);

		if ( (LA152_0==NEWLINE) ) {
		alt152=1;
		}
		else if ( (LA152_0==EOF) ) {
		alt152=2;
		}
		else {
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
			new NoViableAltException("784:1: paragraph_separator : ( ( newline )+ | EOF );", 152, 0, input);

		throw nvae;
		}
		switch (alt152) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:785:4: ( newline )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:785:4: ( newline )+
			int cnt151=0;
			loop151:
			do {
			int alt151=2;
			int LA151_0 = input.LA(1);

			if ( (LA151_0==NEWLINE) ) {
				alt151=1;
			}


			switch (alt151) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:785:6: newline
				{
				pushFollow(FOLLOW_newline_in_paragraph_separator4891);
				newline();
				_fsp--;
				if (failed) return ;

				}
				break;

			default :
				if ( cnt151 >= 1 ) break loop151;
				if (backtracking>0) {failed=true; return ;}
				EarlyExitException eee =
					new EarlyExitException(151, input);
				throw eee;
			}
			cnt151++;
			} while (true);


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:786:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_paragraph_separator4899); if (failed) return ;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end paragraph_separator


	// $ANTLR start whitespaces
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:788:1: whitespaces : ( blanks | newline )+ ;
	public final void whitespaces() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:789:2: ( ( blanks | newline )+ )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:789:4: ( blanks | newline )+
		{
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:789:4: ( blanks | newline )+
		int cnt153=0;
		loop153:
		do {
		int alt153=3;
		int LA153_0 = input.LA(1);

		if ( (LA153_0==BLANKS) ) {
			alt153=1;
		}
		else if ( (LA153_0==NEWLINE) ) {
			alt153=2;
		}


		switch (alt153) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:789:6: blanks
			{
			pushFollow(FOLLOW_blanks_in_whitespaces4911);
			blanks();
			_fsp--;
			if (failed) return ;

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:789:15: newline
			{
			pushFollow(FOLLOW_newline_in_whitespaces4915);
			newline();
			_fsp--;
			if (failed) return ;

			}
			break;

		default :
			if ( cnt153 >= 1 ) break loop153;
			if (backtracking>0) {failed=true; return ;}
			EarlyExitException eee =
				new EarlyExitException(153, input);
			throw eee;
		}
		cnt153++;
		} while (true);


		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end whitespaces


	// $ANTLR start blanks
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:791:1: blanks : BLANKS ;
	public final void blanks() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:792:2: ( BLANKS )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:792:4: BLANKS
		{
		match(input,BLANKS,FOLLOW_BLANKS_in_blanks4928); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end blanks


	// $ANTLR start text_lineseparator
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:794:1: text_lineseparator : ( newline ( blanks )? | EOF );
	public final void text_lineseparator() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:795:2: ( newline ( blanks )? | EOF )
		int alt155=2;
		int LA155_0 = input.LA(1);

		if ( (LA155_0==NEWLINE) ) {
		alt155=1;
		}
		else if ( (LA155_0==EOF) ) {
		alt155=2;
		}
		else {
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
			new NoViableAltException("794:1: text_lineseparator : ( newline ( blanks )? | EOF );", 155, 0, input);

		throw nvae;
		}
		switch (alt155) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:795:4: newline ( blanks )?
			{
			pushFollow(FOLLOW_newline_in_text_lineseparator4938);
			newline();
			_fsp--;
			if (failed) return ;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:795:13: ( blanks )?
			int alt154=2;
			int LA154_0 = input.LA(1);

			if ( (LA154_0==BLANKS) ) {
			alt154=1;
			}
			switch (alt154) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:795:15: blanks
				{
				pushFollow(FOLLOW_blanks_in_text_lineseparator4943);
				blanks();
				_fsp--;
				if (failed) return ;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:796:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_text_lineseparator4951); if (failed) return ;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end text_lineseparator


	// $ANTLR start newline
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:798:1: newline : NEWLINE ;
	public final void newline() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:799:2: ( NEWLINE )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:799:4: NEWLINE
		{
		match(input,NEWLINE,FOLLOW_NEWLINE_in_newline4961); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end newline


	// $ANTLR start bold_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:801:1: bold_markup : STAR STAR ;
	public final void bold_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:802:2: ( STAR STAR )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:802:4: STAR STAR
		{
		match(input,STAR,FOLLOW_STAR_in_bold_markup4971); if (failed) return ;
		match(input,STAR,FOLLOW_STAR_in_bold_markup4974); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end bold_markup


	// $ANTLR start ital_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:804:1: ital_markup : ITAL ;
	public final void ital_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:805:2: ( ITAL )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:805:4: ITAL
		{
		match(input,ITAL,FOLLOW_ITAL_in_ital_markup4984); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end ital_markup


	// $ANTLR start heading_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:807:1: heading_markup : EQUAL ;
	public final void heading_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:808:2: ( EQUAL )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:808:4: EQUAL
		{
		match(input,EQUAL,FOLLOW_EQUAL_in_heading_markup4994); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end heading_markup

	public static class list_ordelem_markup_return extends ParserRuleReturnScope {
	};

	// $ANTLR start list_ordelem_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:810:1: list_ordelem_markup : POUND ;
	public final list_ordelem_markup_return list_ordelem_markup() throws RecognitionException {
	list_ordelem_markup_return retval = new list_ordelem_markup_return();
	retval.start = input.LT(1);

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:811:2: ( POUND )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:811:4: POUND
		{
		match(input,POUND,FOLLOW_POUND_in_list_ordelem_markup5004); if (failed) return retval;

		}

		retval.stop = input.LT(-1);

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return retval;
	}
	// $ANTLR end list_ordelem_markup

	public static class list_unordelem_markup_return extends ParserRuleReturnScope {
	};

	// $ANTLR start list_unordelem_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:813:1: list_unordelem_markup : STAR ;
	public final list_unordelem_markup_return list_unordelem_markup() throws RecognitionException {
	list_unordelem_markup_return retval = new list_unordelem_markup_return();
	retval.start = input.LT(1);

	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:814:2: ( STAR )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:814:4: STAR
		{
		match(input,STAR,FOLLOW_STAR_in_list_unordelem_markup5014); if (failed) return retval;

		}

		retval.stop = input.LT(-1);

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return retval;
	}
	// $ANTLR end list_unordelem_markup


	// $ANTLR start list_elemseparator
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:816:1: list_elemseparator : ( newline ( blanks )? | EOF );
	public final void list_elemseparator() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:817:2: ( newline ( blanks )? | EOF )
		int alt157=2;
		int LA157_0 = input.LA(1);

		if ( (LA157_0==NEWLINE) ) {
		alt157=1;
		}
		else if ( (LA157_0==EOF) ) {
		alt157=2;
		}
		else {
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
			new NoViableAltException("816:1: list_elemseparator : ( newline ( blanks )? | EOF );", 157, 0, input);

		throw nvae;
		}
		switch (alt157) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:817:4: newline ( blanks )?
			{
			pushFollow(FOLLOW_newline_in_list_elemseparator5024);
			newline();
			_fsp--;
			if (failed) return ;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:817:13: ( blanks )?
			int alt156=2;
			int LA156_0 = input.LA(1);

			if ( (LA156_0==BLANKS) ) {
			alt156=1;
			}
			switch (alt156) {
			case 1 :
				// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:817:15: blanks
				{
				pushFollow(FOLLOW_blanks_in_list_elemseparator5029);
				blanks();
				_fsp--;
				if (failed) return ;

				}
				break;

			}


			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:818:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_list_elemseparator5037); if (failed) return ;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end list_elemseparator


	// $ANTLR start end_of_list
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:820:1: end_of_list : ( newline | EOF );
	public final void end_of_list() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:821:2: ( newline | EOF )
		int alt158=2;
		int LA158_0 = input.LA(1);

		if ( (LA158_0==NEWLINE) ) {
		alt158=1;
		}
		else if ( (LA158_0==EOF) ) {
		alt158=2;
		}
		else {
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
			new NoViableAltException("820:1: end_of_list : ( newline | EOF );", 158, 0, input);

		throw nvae;
		}
		switch (alt158) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:821:4: newline
			{
			pushFollow(FOLLOW_newline_in_end_of_list5047);
			newline();
			_fsp--;
			if (failed) return ;

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:822:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_end_of_list5052); if (failed) return ;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end end_of_list


	// $ANTLR start table_cell_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:824:1: table_cell_markup : PIPE ;
	public final void table_cell_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:825:2: ( PIPE )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:825:4: PIPE
		{
		match(input,PIPE,FOLLOW_PIPE_in_table_cell_markup5062); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end table_cell_markup


	// $ANTLR start table_headercell_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:827:1: table_headercell_markup : PIPE EQUAL ;
	public final void table_headercell_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:828:2: ( PIPE EQUAL )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:828:4: PIPE EQUAL
		{
		match(input,PIPE,FOLLOW_PIPE_in_table_headercell_markup5072); if (failed) return ;
		match(input,EQUAL,FOLLOW_EQUAL_in_table_headercell_markup5075); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end table_headercell_markup


	// $ANTLR start table_rowseparator
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:830:1: table_rowseparator : ( newline | EOF );
	public final void table_rowseparator() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:831:2: ( newline | EOF )
		int alt159=2;
		int LA159_0 = input.LA(1);

		if ( (LA159_0==NEWLINE) ) {
		alt159=1;
		}
		else if ( (LA159_0==EOF) ) {
		alt159=2;
		}
		else {
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
			new NoViableAltException("830:1: table_rowseparator : ( newline | EOF );", 159, 0, input);

		throw nvae;
		}
		switch (alt159) {
		case 1 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:831:4: newline
			{
			pushFollow(FOLLOW_newline_in_table_rowseparator5085);
			newline();
			_fsp--;
			if (failed) return ;

			}
			break;
		case 2 :
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:832:4: EOF
			{
			match(input,EOF,FOLLOW_EOF_in_table_rowseparator5090); if (failed) return ;

			}
			break;

		}
	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end table_rowseparator


	// $ANTLR start nowiki_open_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:834:1: nowiki_open_markup : NOWIKI_OPEN ;
	public final void nowiki_open_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:835:2: ( NOWIKI_OPEN )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:835:4: NOWIKI_OPEN
		{
		match(input,NOWIKI_OPEN,FOLLOW_NOWIKI_OPEN_in_nowiki_open_markup5100); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end nowiki_open_markup


	// $ANTLR start nowiki_close_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:837:1: nowiki_close_markup : NOWIKI_CLOSE ;
	public final void nowiki_close_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:838:2: ( NOWIKI_CLOSE )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:838:4: NOWIKI_CLOSE
		{
		match(input,NOWIKI_CLOSE,FOLLOW_NOWIKI_CLOSE_in_nowiki_close_markup5110); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end nowiki_close_markup


	// $ANTLR start horizontalrule_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:840:1: horizontalrule_markup : DASH DASH DASH DASH ;
	public final void horizontalrule_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:841:2: ( DASH DASH DASH DASH )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:841:4: DASH DASH DASH DASH
		{
		match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup5120); if (failed) return ;
		match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup5123); if (failed) return ;
		match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup5126); if (failed) return ;
		match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup5129); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end horizontalrule_markup


	// $ANTLR start link_open_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:843:1: link_open_markup : LINK_OPEN ;
	public final void link_open_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:844:2: ( LINK_OPEN )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:844:4: LINK_OPEN
		{
		match(input,LINK_OPEN,FOLLOW_LINK_OPEN_in_link_open_markup5139); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end link_open_markup


	// $ANTLR start link_close_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:846:1: link_close_markup : LINK_CLOSE ;
	public final void link_close_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:847:2: ( LINK_CLOSE )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:847:4: LINK_CLOSE
		{
		match(input,LINK_CLOSE,FOLLOW_LINK_CLOSE_in_link_close_markup5149); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end link_close_markup


	// $ANTLR start link_description_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:849:1: link_description_markup : PIPE ;
	public final void link_description_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:850:2: ( PIPE )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:850:4: PIPE
		{
		match(input,PIPE,FOLLOW_PIPE_in_link_description_markup5159); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end link_description_markup


	// $ANTLR start image_open_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:852:1: image_open_markup : IMAGE_OPEN ;
	public final void image_open_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:853:2: ( IMAGE_OPEN )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:853:4: IMAGE_OPEN
		{
		match(input,IMAGE_OPEN,FOLLOW_IMAGE_OPEN_in_image_open_markup5169); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end image_open_markup


	// $ANTLR start image_close_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:855:1: image_close_markup : IMAGE_CLOSE ;
	public final void image_close_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:856:2: ( IMAGE_CLOSE )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:856:4: IMAGE_CLOSE
		{
		match(input,IMAGE_CLOSE,FOLLOW_IMAGE_CLOSE_in_image_close_markup5179); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end image_close_markup


	// $ANTLR start image_alternative_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:858:1: image_alternative_markup : PIPE ;
	public final void image_alternative_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:859:2: ( PIPE )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:859:4: PIPE
		{
		match(input,PIPE,FOLLOW_PIPE_in_image_alternative_markup5189); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end image_alternative_markup


	// $ANTLR start extension_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:861:1: extension_markup : EXTENSION ;
	public final void extension_markup() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:862:2: ( EXTENSION )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:862:4: EXTENSION
		{
		match(input,EXTENSION,FOLLOW_EXTENSION_in_extension_markup5199); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end extension_markup


	// $ANTLR start forced_linebreak
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:864:1: forced_linebreak : FORCED_LINEBREAK ;
	public final void forced_linebreak() throws RecognitionException {
	try {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:865:2: ( FORCED_LINEBREAK )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:865:4: FORCED_LINEBREAK
		{
		match(input,FORCED_LINEBREAK,FOLLOW_FORCED_LINEBREAK_in_forced_linebreak5209); if (failed) return ;

		}

	}
	catch (RecognitionException re) {
		reportError(re);
		recover(input,re);
	}
	finally {
	}
	return ;
	}
	// $ANTLR end forced_linebreak

	// $ANTLR start synpred1
	public final void synpred1_fragment() throws RecognitionException {   
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:143:5: ( NOWIKI_OPEN ~ ( NEWLINE ) )
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:143:7: NOWIKI_OPEN ~ ( NEWLINE )
	{
	match(input,NOWIKI_OPEN,FOLLOW_NOWIKI_OPEN_in_synpred1339); if (failed) return ;
	if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=78) ) {
		input.consume();
		errorRecovery=false;failed=false;
	}
	else {
		if (backtracking>0) {failed=true; return ;}
		MismatchedSetException mse =
		new MismatchedSetException(null,input);
		recoverFromMismatchedSet(input,mse,FOLLOW_set_in_synpred1342);	throw mse;
	}


	}
	}
	// $ANTLR end synpred1

	public final boolean synpred1() {
	backtracking++;
	int start = input.mark();
	try {
		synpred1_fragment(); // can never throw exception
	} catch (RecognitionException re) {
		System.err.println("impossible: "+re);
	}
	boolean success = !failed;
	input.rewind(start);
	backtracking--;
	failed=false;
	return success;
	}


 

	public static final BitSet FOLLOW_whitespaces_in_wikipage118 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_paragraphs_in_wikipage126 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_wikipage131 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_paragraph_in_paragraphs149 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_nowiki_block_in_paragraph170 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blanks_in_paragraph177 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_paragraph_separator_in_paragraph180 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blanks_in_paragraph187 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_of_contents_in_paragraph201 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_heading_in_paragraph218 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_horizontalrule_in_paragraph237 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_list_unord_in_paragraph249 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_list_ord_in_paragraph262 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_table_in_paragraph275 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_text_paragraph_in_paragraph288 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_paragraph_separator_in_paragraph301 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_line_in_text_paragraph329 = new BitSet(new long[]{0xFFFFFFFFFFF27FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_nowiki_inline_in_text_paragraph361 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_element_in_text_paragraph372 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_lineseparator_in_text_paragraph381 = new BitSet(new long[]{0xFFFFFFFFFFF27FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_firstelement_in_text_line404 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_element_in_text_line423 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_lineseparator_in_text_line438 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_formattedelement_in_text_firstelement460 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_first_unformattedelement_in_text_firstelement471 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_text_formattedelement487 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_italcontent_in_text_formattedelement493 = new BitSet(new long[]{0x0000000000108002L});
	public static final BitSet FOLLOW_NEWLINE_in_text_formattedelement502 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_ital_markup_in_text_formattedelement508 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_text_formattedelement516 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_boldcontent_in_text_formattedelement523 = new BitSet(new long[]{0x0000000000028002L});
	public static final BitSet FOLLOW_NEWLINE_in_text_formattedelement532 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_bold_markup_in_text_formattedelement538 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_text_boldcontent557 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_boldcontentpart_in_text_boldcontent569 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_EOF_in_text_boldcontent580 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_text_italcontent596 = new BitSet(new long[]{0xFFFFFFFFFFEF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_italcontentpart_in_text_italcontent608 = new BitSet(new long[]{0xFFFFFFFFFFEF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_EOF_in_text_italcontent619 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_text_element634 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_unformattedelement_in_text_element641 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_unformattedelement_in_text_element652 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_onestar_in_text_element655 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_formattedelement_in_text_element666 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_text_boldcontentpart683 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_bolditalcontent_in_text_boldcontentpart690 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_ital_markup_in_text_boldcontentpart697 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_formattedcontent_in_text_boldcontentpart709 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_text_italcontentpart725 = new BitSet(new long[]{0xFFFFFFFFFFEFFFF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_bolditalcontent_in_text_italcontentpart732 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_bold_markup_in_text_italcontentpart738 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_formattedcontent_in_text_italcontentpart750 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_text_bolditalcontent768 = new BitSet(new long[]{0xFFFFFFFFFFEF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_formattedcontent_in_text_bolditalcontent779 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_text_bolditalcontent789 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_text_formattedcontent803 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_unformattedelement_in_text_formattedcontent812 = new BitSet(new long[]{0xFFFFFFFFFFEFFFF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_text_formattedcontent817 = new BitSet(new long[]{0xFFFFFFFFFFEDFFF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_linebreak_in_text_formattedcontent822 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_lineseparator_in_text_linebreak842 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_first_inlineelement_in_text_inlineelement860 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_nowiki_inline_in_text_inlineelement871 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_in_text_first_inlineelement894 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_image_in_text_first_inlineelement905 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_extension_in_text_first_inlineelement915 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_first_unformatted_in_text_first_unformattedelement935 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_first_inlineelement_in_text_first_unformattedelement946 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_first_unformmatted_text_in_text_first_unformatted968 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forced_linebreak_in_text_first_unformatted977 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_escaped_in_text_first_unformatted989 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_set_in_text_first_unformmatted_text1018 = new BitSet(new long[]{0xFFFFFFFFF8007FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_unformatted_in_text_unformattedelement1133 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_inlineelement_in_text_unformattedelement1144 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_unformated_text_in_text_unformatted1166 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forced_linebreak_in_text_unformatted1175 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_escaped_in_text_unformatted1187 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_set_in_text_unformated_text1214 = new BitSet(new long[]{0xFFFFFFFFF80D7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_markup_in_heading1318 = new BitSet(new long[]{0xFFFFFFFFFBFFFFF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_content_in_heading1323 = new BitSet(new long[]{0x0000000080048000L});
	public static final BitSet FOLLOW_heading_markup_in_heading1330 = new BitSet(new long[]{0x0000000080008000L});
	public static final BitSet FOLLOW_blanks_in_heading1338 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_paragraph_separator_in_heading1345 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_heading_markup_in_heading_content1355 = new BitSet(new long[]{0xFFFFFFFFFBFF7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_content_in_heading_content1360 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_heading_markup_in_heading_content1365 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_heading_text_in_heading_content1377 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_heading_cellcontent_in_heading_text1398 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_heading_cellcontent1415 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_cellcontentpart_in_heading_cellcontent1424 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_heading_cellcontent1436 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_formattedelement_in_heading_cellcontentpart1457 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_heading_unformattedelement_in_heading_cellcontentpart1468 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_heading_formattedelement1484 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_italcontent_in_heading_formattedelement1494 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_ital_markup_in_heading_formattedelement1503 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_heading_formattedelement1511 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_boldcontent_in_heading_formattedelement1518 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_bold_markup_in_heading_formattedelement1528 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_heading_boldcontent1545 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_boldcontentpart_in_heading_boldcontent1554 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_heading_boldcontent1559 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_EOF_in_heading_boldcontent1567 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_heading_italcontent1581 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_italcontentpart_in_heading_italcontent1590 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_heading_italcontent1595 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_EOF_in_heading_italcontent1603 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_heading_formattedcontent_in_heading_boldcontentpart1621 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_heading_boldcontentpart1628 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_bolditalcontent_in_heading_boldcontentpart1635 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_ital_markup_in_heading_boldcontentpart1642 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_heading_italcontentpart1659 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_bolditalcontent_in_heading_italcontentpart1666 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_bold_markup_in_heading_italcontentpart1673 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_heading_formattedcontent_in_heading_italcontentpart1685 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_heading_bolditalcontent1701 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_formattedcontent_in_heading_bolditalcontent1710 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_onestar_in_heading_bolditalcontent1715 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_heading_bolditalcontent1723 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_heading_unformattedelement_in_heading_formattedcontent1743 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_heading_unformatted_text_in_heading_unformattedelement1766 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_heading_inlineelement_in_heading_unformattedelement1778 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_in_heading_inlineelement1799 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_image_in_heading_inlineelement1809 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_nowiki_inline_in_heading_inlineelement1820 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_heading_unformatted_text1845 = new BitSet(new long[]{0xFFFFFFFFFB1B7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_list_ordelem_in_list_ord1904 = new BitSet(new long[]{0x0000000000018002L});
	public static final BitSet FOLLOW_end_of_list_in_list_ord1914 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_ordelem_markup_in_list_ordelem1947 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_list_elem_in_list_ordelem1955 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_unordelem_in_list_unord1979 = new BitSet(new long[]{0x0000000000028002L});
	public static final BitSet FOLLOW_end_of_list_in_list_unord1989 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_unordelem_markup_in_list_unordelem2022 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_list_elem_in_list_unordelem2029 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_elem_markup_in_list_elem2054 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_list_elemcontent_in_list_elem2065 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_list_elemseparator_in_list_elem2070 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_ordelem_markup_in_list_elem_markup2080 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_unordelem_markup_in_list_elem_markup2085 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_list_elemcontent2099 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_list_elemcontentpart_in_list_elemcontent2108 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_list_elemcontent2113 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_unformattedelement_in_list_elemcontentpart2134 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_formatted_elem_in_list_elemcontentpart2145 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_list_formatted_elem2162 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_list_formatted_elem2165 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_list_boldcontentpart_in_list_formatted_elem2174 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_list_formatted_elem2184 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_bold_markup_in_list_formatted_elem2193 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_list_formatted_elem2201 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_list_formatted_elem2206 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_list_italcontentpart_in_list_formatted_elem2215 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_list_formatted_elem2224 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_ital_markup_in_list_formatted_elem2233 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_list_boldcontentpart2259 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_list_bolditalcontent_in_list_boldcontentpart2266 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_ital_markup_in_list_boldcontentpart2273 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_unformattedelement_in_list_boldcontentpart2288 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_text_unformattedelement_in_list_bolditalcontent2320 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_bold_markup_in_list_italcontentpart2350 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_list_bolditalcontent_in_list_italcontentpart2357 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_bold_markup_in_list_italcontentpart2364 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_unformattedelement_in_list_italcontentpart2378 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_row_in_table2408 = new BitSet(new long[]{0x0000000000080002L});
	public static final BitSet FOLLOW_table_cell_in_table_row2434 = new BitSet(new long[]{0x0000000000088000L});
	public static final BitSet FOLLOW_table_rowseparator_in_table_row2442 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_headercell_in_table_cell2463 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_normalcell_in_table_cell2474 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_headercell_markup_in_table_headercell2490 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_cellcontent_in_table_headercell2497 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_cell_markup_in_table_normalcell2513 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_cellcontent_in_table_normalcell2520 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_table_cellcontent2536 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_cellcontentpart_in_table_cellcontent2545 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_table_cellcontent2550 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_formattedelement_in_table_cellcontentpart2571 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_unformattedelement_in_table_cellcontentpart2582 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_table_formattedelement2598 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_italcontent_in_table_formattedelement2608 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_ital_markup_in_table_formattedelement2617 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_table_formattedelement2625 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_boldcontent_in_table_formattedelement2632 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_bold_markup_in_table_formattedelement2642 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_table_boldcontent2659 = new BitSet(new long[]{0xFFFFFFFFFFF57FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_boldcontentpart_in_table_boldcontent2668 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_table_boldcontent2673 = new BitSet(new long[]{0xFFFFFFFFFFF57FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_EOF_in_table_boldcontent2681 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_table_italcontent2695 = new BitSet(new long[]{0xFFFFFFFFFFE77FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_italcontentpart_in_table_italcontent2704 = new BitSet(new long[]{0xFFFFFFFFFFE77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_table_italcontent2709 = new BitSet(new long[]{0xFFFFFFFFFFE77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_EOF_in_table_italcontent2717 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_formattedcontent_in_table_boldcontentpart2735 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_table_boldcontentpart2742 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_bolditalcontent_in_table_boldcontentpart2749 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_ital_markup_in_table_boldcontentpart2756 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_table_italcontentpart2773 = new BitSet(new long[]{0xFFFFFFFFFFE77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_bolditalcontent_in_table_italcontentpart2780 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_bold_markup_in_table_italcontentpart2787 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_formattedcontent_in_table_italcontentpart2799 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_table_bolditalcontent2815 = new BitSet(new long[]{0xFFFFFFFFFFE57FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_formattedcontent_in_table_bolditalcontent2824 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_onestar_in_table_bolditalcontent2829 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_table_bolditalcontent2837 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_unformattedelement_in_table_formattedcontent2857 = new BitSet(new long[]{0xFFFFFFFFFFE57FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_table_unformatted_in_table_unformattedelement2880 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_inlineelement_in_table_unformattedelement2892 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_in_table_inlineelement2913 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_image_in_table_inlineelement2923 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_extension_in_table_inlineelement2934 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_nowiki_inline_in_table_inlineelement2944 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_unformatted_text_in_table_unformatted2966 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forced_linebreak_in_table_unformatted2975 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_escaped_in_table_unformatted2988 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_set_in_table_unformatted_text3014 = new BitSet(new long[]{0xFFFFFFFFF8057FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_nowikiblock_open_markup_in_nowiki_block3111 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_nowiki_block_contents_in_nowiki_block3118 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_nowikiblock_close_markup_in_nowiki_block3124 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_paragraph_separator_in_nowiki_block3127 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_nowiki_open_markup_in_nowikiblock_open_markup3142 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_newline_in_nowikiblock_open_markup3145 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOWIKI_BLOCK_CLOSE_in_nowikiblock_close_markup3157 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_nowiki_open_markup_in_nowiki_inline3172 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_nowiki_inline_contents_in_nowiki_inline3179 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_nowiki_close_markup_in_nowiki_inline3184 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_nowiki_block_contents3204 = new BitSet(new long[]{0xFFFFFFFFF7FFFFF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_set_in_nowiki_inline_contents3238 = new BitSet(new long[]{0xFFFFFFFFEFFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_horizontalrule_markup_in_horizontalrule3275 = new BitSet(new long[]{0x0000000080008000L});
	public static final BitSet FOLLOW_blanks_in_horizontalrule3280 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_paragraph_separator_in_horizontalrule3286 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_open_markup_in_link3308 = new BitSet(new long[]{0xFFFFFFFFDFF77FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_address_in_link3314 = new BitSet(new long[]{0x0000000020080000L});
	public static final BitSet FOLLOW_link_description_markup_in_link3320 = new BitSet(new long[]{0xFFFFFFFFDE5F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_description_in_link3330 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_link_close_markup_in_link3338 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_interwiki_uri_in_link_address3357 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_43_in_link_address3360 = new BitSet(new long[]{0xFFFFFFFFDFF77FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_interwiki_pagename_in_link_address3367 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_uri_in_link_address3378 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_44_in_link_interwiki_uri3394 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri3396 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_46_in_link_interwiki_uri3401 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3403 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3405 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_link_interwiki_uri3407 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3409 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3411 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3413 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3415 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_52_in_link_interwiki_uri3420 = new BitSet(new long[]{0x0020000000000000L});
	public static final BitSet FOLLOW_53_in_link_interwiki_uri3422 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3424 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_link_interwiki_uri3426 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3428 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_link_interwiki_uri3430 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_56_in_link_interwiki_uri3435 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3437 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3439 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_57_in_link_interwiki_uri3441 = new BitSet(new long[]{0x0020000000000000L});
	public static final BitSet FOLLOW_53_in_link_interwiki_uri3443 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri3445 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_59_in_link_interwiki_uri3450 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_60_in_link_interwiki_uri3452 = new BitSet(new long[]{0x2000000000000000L});
	public static final BitSet FOLLOW_61_in_link_interwiki_uri3454 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3456 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3458 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3460 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3462 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_62_in_link_interwiki_uri3467 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri3469 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3471 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_64_in_link_interwiki_uri3473 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
	public static final BitSet FOLLOW_65_in_link_interwiki_uri3475 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3477 = new BitSet(new long[]{0x0020000000000000L});
	public static final BitSet FOLLOW_53_in_link_interwiki_uri3479 = new BitSet(new long[]{0x0020000000000000L});
	public static final BitSet FOLLOW_53_in_link_interwiki_uri3481 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_62_in_link_interwiki_uri3486 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri3488 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_link_interwiki_uri3490 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3492 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3494 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3496 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3498 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3500 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3502 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_62_in_link_interwiki_uri3507 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3509 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3511 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_67_in_link_interwiki_uri3513 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_62_in_link_interwiki_uri3515 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3517 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3519 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_67_in_link_interwiki_uri3521 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_68_in_link_interwiki_uri3526 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_link_interwiki_uri3528 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_link_interwiki_uri3530 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
	public static final BitSet FOLLOW_69_in_link_interwiki_uri3532 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_link_interwiki_uri3534 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
	public static final BitSet FOLLOW_70_in_link_interwiki_uri3536 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri3538 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_68_in_link_interwiki_uri3543 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
	public static final BitSet FOLLOW_71_in_link_interwiki_uri3545 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3547 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_67_in_link_interwiki_uri3549 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3551 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_61_in_link_interwiki_uri3556 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
	public static final BitSet FOLLOW_69_in_link_interwiki_uri3558 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3560 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3562 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3564 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3566 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_61_in_link_interwiki_uri3571 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_link_interwiki_uri3573 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3575 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3577 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3579 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3581 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3583 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3585 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_61_in_link_interwiki_uri3590 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_link_interwiki_uri3592 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_link_interwiki_uri3594 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_72_in_link_interwiki_uri3596 = new BitSet(new long[]{0x0020000000000000L});
	public static final BitSet FOLLOW_53_in_link_interwiki_uri3598 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri3600 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3602 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3604 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3606 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3608 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_73_in_link_interwiki_uri3613 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3615 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_link_interwiki_uri3617 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri3619 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3621 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
	public static final BitSet FOLLOW_74_in_link_interwiki_uri3623 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_60_in_link_interwiki_uri3628 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_67_in_link_interwiki_uri3630 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3632 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_72_in_link_interwiki_uri3634 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_60_in_link_interwiki_uri3636 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_67_in_link_interwiki_uri3638 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3640 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_72_in_link_interwiki_uri3642 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_75_in_link_interwiki_uri3647 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3649 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_link_interwiki_uri3651 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_link_interwiki_uri3653 = new BitSet(new long[]{0x0020000000000000L});
	public static final BitSet FOLLOW_53_in_link_interwiki_uri3655 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
	public static final BitSet FOLLOW_76_in_link_interwiki_uri3657 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3659 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3661 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3663 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3665 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_75_in_link_interwiki_uri3670 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3672 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3674 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3676 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3678 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_77_in_link_interwiki_uri3683 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
	public static final BitSet FOLLOW_70_in_link_interwiki_uri3685 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri3687 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
	public static final BitSet FOLLOW_69_in_link_interwiki_uri3689 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3691 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_link_interwiki_uri3693 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3698 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3700 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3702 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3704 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_72_in_link_interwiki_uri3706 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri3708 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_link_interwiki_uri3710 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3712 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3714 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_78_in_link_interwiki_uri3719 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3721 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3723 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3725 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri3727 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_link_interwiki_pagename3747 = new BitSet(new long[]{0xFFFFFFFFDFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_descriptionpart_in_link_description3790 = new BitSet(new long[]{0xFFFFFFFFDE5F7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_in_link_description3802 = new BitSet(new long[]{0xFFFFFFFFDE5F7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_bold_markup_in_link_descriptionpart3827 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3830 = new BitSet(new long[]{0xFFFFFFFFDE1D7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_bold_descriptionpart_in_link_descriptionpart3838 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3843 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_bold_markup_in_link_descriptionpart3853 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_link_descriptionpart3858 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3861 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_ital_descriptionpart_in_link_descriptionpart3870 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3875 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_ital_markup_in_link_descriptionpart3884 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3889 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_descriptiontext_in_link_descriptionpart3898 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3901 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_ital_markup_in_link_bold_descriptionpart3921 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_boldital_description_in_link_bold_descriptionpart3928 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_ital_markup_in_link_bold_descriptionpart3933 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_descriptiontext_in_link_bold_descriptionpart3942 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_link_ital_descriptionpart3958 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_boldital_description_in_link_ital_descriptionpart3965 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_bold_markup_in_link_ital_descriptionpart3968 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_descriptiontext_in_link_ital_descriptionpart3979 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_link_boldital_description3995 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_descriptiontext_in_link_boldital_description4004 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_link_boldital_description4007 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_descriptiontext_simple_in_link_descriptiontext4030 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forced_linebreak_in_link_descriptiontext4040 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_escaped_in_link_descriptiontext4052 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_set_in_link_descriptiontext_simple4077 = new BitSet(new long[]{0xFFFFFFFFD80D7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_set_in_link_uri4178 = new BitSet(new long[]{0xFFFFFFFFDFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_open_markup_in_image4219 = new BitSet(new long[]{0xFFFFFFFFBFF77FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_uri_in_image4225 = new BitSet(new long[]{0x0000000040080000L});
	public static final BitSet FOLLOW_image_alternative_in_image4235 = new BitSet(new long[]{0x0000000040000000L});
	public static final BitSet FOLLOW_image_close_markup_in_image4244 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_image_uri4263 = new BitSet(new long[]{0xFFFFFFFFBFF77FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_alternative_markup_in_image_alternative4298 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_alternativepart_in_image_alternative4307 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_bold_markup_in_image_alternativepart4333 = new BitSet(new long[]{0x0000000000120000L});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart4336 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_bold_alternativepart_in_image_alternativepart4345 = new BitSet(new long[]{0x0000000000120000L});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart4350 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_bold_markup_in_image_alternativepart4357 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_image_alternativepart4364 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart4367 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_ital_alternativepart_in_image_alternativepart4377 = new BitSet(new long[]{0x0000000000120000L});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart4382 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_ital_markup_in_image_alternativepart4389 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart4396 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_alternativetext_in_image_alternativepart4403 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart4408 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_ital_markup_in_image_bold_alternativepart4434 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_boldital_description_in_image_bold_alternativepart4441 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_ital_markup_in_image_bold_alternativepart4446 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_image_bold_alternativepart4451 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_alternativetext_in_image_bold_alternativepart4460 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_image_bold_alternativepart4463 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_bold_markup_in_image_ital_alternativepart4492 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_link_boldital_description_in_image_ital_alternativepart4499 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_bold_markup_in_image_ital_alternativepart4504 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_image_ital_alternativepart4509 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_alternativetext_in_image_ital_alternativepart4518 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_image_ital_alternativepart4521 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_image_boldital_alternative4542 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_alternativetext_in_image_boldital_alternative4551 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_onestar_in_image_boldital_alternative4554 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_image_alternative_simple_text_in_image_alternativetext4577 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forced_linebreak_in_image_alternativetext4585 = new BitSet(new long[]{0x0000000002000002L});
	public static final BitSet FOLLOW_set_in_image_alternative_simple_text4612 = new BitSet(new long[]{0xFFFFFFFFBC0D7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_extension_markup_in_extension4705 = new BitSet(new long[]{0xFFFFFFFF7EFF7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_extension_handler_in_extension4708 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_blanks_in_extension4711 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_extension_statement_in_extension4714 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_extension_markup_in_extension4719 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_extension_handler4731 = new BitSet(new long[]{0xFFFFFFFF7EFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_escaped_in_extension_handler4764 = new BitSet(new long[]{0xFFFFFFFF7EFF7FF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_set_in_extension_statement4778 = new BitSet(new long[]{0xFFFFFFFFFEFFFFF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_escaped_in_extension_statement4799 = new BitSet(new long[]{0xFFFFFFFFFEFFFFF2L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_TABLE_OF_CONTENTS_TEXT_in_table_of_contents4822 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STAR_in_onestar4845 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ESCAPE_in_escaped4867 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_newline_in_paragraph_separator4891 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_EOF_in_paragraph_separator4899 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blanks_in_whitespaces4911 = new BitSet(new long[]{0x0000000080008002L});
	public static final BitSet FOLLOW_newline_in_whitespaces4915 = new BitSet(new long[]{0x0000000080008002L});
	public static final BitSet FOLLOW_BLANKS_in_blanks4928 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_newline_in_text_lineseparator4938 = new BitSet(new long[]{0x0000000080000002L});
	public static final BitSet FOLLOW_blanks_in_text_lineseparator4943 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_text_lineseparator4951 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_newline4961 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STAR_in_bold_markup4971 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_STAR_in_bold_markup4974 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ITAL_in_ital_markup4984 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EQUAL_in_heading_markup4994 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_POUND_in_list_ordelem_markup5004 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STAR_in_list_unordelem_markup5014 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_newline_in_list_elemseparator5024 = new BitSet(new long[]{0x0000000080000002L});
	public static final BitSet FOLLOW_blanks_in_list_elemseparator5029 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_list_elemseparator5037 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_newline_in_end_of_list5047 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_end_of_list5052 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PIPE_in_table_cell_markup5062 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PIPE_in_table_headercell_markup5072 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_EQUAL_in_table_headercell_markup5075 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_newline_in_table_rowseparator5085 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_table_rowseparator5090 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOWIKI_OPEN_in_nowiki_open_markup5100 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOWIKI_CLOSE_in_nowiki_close_markup5110 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DASH_in_horizontalrule_markup5120 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_DASH_in_horizontalrule_markup5123 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_DASH_in_horizontalrule_markup5126 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_DASH_in_horizontalrule_markup5129 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LINK_OPEN_in_link_open_markup5139 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LINK_CLOSE_in_link_close_markup5149 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PIPE_in_link_description_markup5159 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IMAGE_OPEN_in_image_open_markup5169 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IMAGE_CLOSE_in_image_close_markup5179 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PIPE_in_image_alternative_markup5189 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EXTENSION_in_extension_markup5199 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FORCED_LINEBREAK_in_forced_linebreak5209 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOWIKI_OPEN_in_synpred1339 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x0000000000007FFFL});
	public static final BitSet FOLLOW_set_in_synpred1342 = new BitSet(new long[]{0x0000000000000002L});

}
