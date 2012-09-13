// $ANTLR 3.0.1 /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g 2012-09-12 08:31:36

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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.parsers.creole.ast.ASTNode;
import com.liferay.portal.parsers.creole.ast.BaseListNode;
import com.liferay.portal.parsers.creole.ast.BaseParentableNode;
import com.liferay.portal.parsers.creole.ast.BoldTextNode;
import com.liferay.portal.parsers.creole.ast.CollectionNode;
import com.liferay.portal.parsers.creole.ast.extension.TableOfContentsNode;
import com.liferay.portal.parsers.creole.ast.ForcedEndOfLineNode;
import com.liferay.portal.parsers.creole.ast.FormattedTextNode;
import com.liferay.portal.parsers.creole.ast.HeadingNode;
import com.liferay.portal.parsers.creole.ast.HorizontalNode;
import com.liferay.portal.parsers.creole.ast.ImageNode;
import com.liferay.portal.parsers.creole.ast.ItalicTextNode;
import com.liferay.portal.parsers.creole.ast.ItemNode;
import com.liferay.portal.parsers.creole.ast.LineNode;
import com.liferay.portal.parsers.creole.ast.ListNode;
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

import java.util.Stack;

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
	"<invalid>", "<EOR>", "<DOWN>", "<UP>", "FORCED_END_OF_LINE", "HEADING_SECTION", "HORIZONTAL_SECTION", "LIST_ITEM", "LIST_ITEM_PART", "NOWIKI_SECTION", "SCAPE_NODE", "TEXT_NODE", "UNORDERED_LIST", "UNFORMATTED_TEXT", "WIKI", "NEWLINE", "POUND", "STAR", "EQUAL", "PIPE", "ITAL", "LINK_OPEN", "IMAGE_OPEN", "NOWIKI_OPEN", "EXTENSION", "FORCED_LINEBREAK", "ESCAPE", "NOWIKI_BLOCK_CLOSE", "NOWIKI_CLOSE", "LINK_CLOSE", "IMAGE_CLOSE", "BLANKS", "TABLE_OF_CONTENTS_TEXT", "DASH", "CR", "LF", "SPACE", "TABULATOR", "BRACE_CLOSE", "COLON_SLASH", "SLASH", "TABLE_OF_CONTENTS_OPEN_MARKUP", "TABLE_OF_CONTENTS_CLOSE_MARKUP", "INSIGNIFICANT_CHAR", "':'", "'C'", "'2'", "'D'", "'o'", "'k'", "'u'", "'W'", "'i'", "'F'", "'l'", "'c'", "'r'", "'G'", "'g'", "'e'", "'J'", "'S'", "'P'", "'M'", "'a'", "'t'", "'b'", "'d'", "'n'", "'O'", "'m'", "'s'", "'h'", "'p'", "'R'", "'x'", "'T'", "'y'", "'U'", "'X'"
    };
    public static final int INSIGNIFICANT_CHAR=43;
    public static final int STAR=17;
    public static final int FORCED_END_OF_LINE=4;
    public static final int TABLE_OF_CONTENTS_TEXT=32;
    public static final int NOWIKI_BLOCK_CLOSE=27;
    public static final int EOF=-1;
    public static final int NOWIKI_CLOSE=28;
    public static final int SPACE=36;
    public static final int LIST_ITEM=7;
    public static final int TEXT_NODE=11;
    public static final int WIKI=14;
    public static final int SLASH=40;
    public static final int ESCAPE=26;
    public static final int BRACE_CLOSE=38;
    public static final int EQUAL=18;
    public static final int TABULATOR=37;
    public static final int PIPE=19;
    public static final int LIST_ITEM_PART=8;
    public static final int LINK_OPEN=21;
    public static final int TABLE_OF_CONTENTS_OPEN_MARKUP=41;
    public static final int BLANKS=31;
    public static final int FORCED_LINEBREAK=25;
    public static final int UNORDERED_LIST=12;
    public static final int POUND=16;
    public static final int DASH=33;
    public static final int HEADING_SECTION=5;
    public static final int NOWIKI_OPEN=23;
    public static final int HORIZONTAL_SECTION=6;
    public static final int UNFORMATTED_TEXT=13;
    public static final int NOWIKI_SECTION=9;
    public static final int ITAL=20;
    public static final int IMAGE_OPEN=22;
    public static final int COLON_SLASH=39;
    public static final int NEWLINE=15;
    public static final int SCAPE_NODE=10;
    public static final int IMAGE_CLOSE=30;
    public static final int TABLE_OF_CONTENTS_CLOSE_MARKUP=42;
    public static final int LINK_CLOSE=29;
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
	    ruleMemo = new HashMap[127+1];
	 }
	

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "/home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g"; }


	public WikiPageNode getWikiPageNode() {
		if (_wikipage == null)
			throw new IllegalStateException("No succesful parsing process");

		return _wikipage;
	}

	protected static final String GROUPING_SEPARATOR = "-";

	protected BaseParentableNode buildAndComposeListNode(BaseParentableNode baseParentableNode, ItemNode itemNode, boolean ordered) {
		BaseParentableNode listNode = null;
		
		if (ordered) {
			listNode = new OrderedListNode(baseParentableNode);
		} 
		else {
			listNode = new UnorderedListNode(baseParentableNode);
		}

		itemNode.setBaseParentableNode(listNode);
		listNode.addChildASTNode(itemNode);

		baseParentableNode.addChildASTNode(listNode);

		return listNode;
	}

	private WikiPageNode _wikipage = null;




    // $ANTLR start wikipage
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:135:1: wikipage : ( whitespaces )? p= paragraphs EOF ;
    public final void wikipage() throws RecognitionException {
	CollectionNode p = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:136:2: ( ( whitespaces )? p= paragraphs EOF )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:136:4: ( whitespaces )? p= paragraphs EOF
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:136:4: ( whitespaces )?
	    int alt1=2;
	    int LA1_0 = input.LA(1);

	    if ( (LA1_0==NEWLINE||LA1_0==BLANKS) ) {
		alt1=1;
	    }
	    switch (alt1) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:136:6: whitespaces
		    {
		    pushFollow(FOLLOW_whitespaces_in_wikipage111);
		    whitespaces();
		    _fsp--;
		    if (failed) return ;

		    }
		    break;

	    }

	    pushFollow(FOLLOW_paragraphs_in_wikipage119);
	    p=paragraphs();
	    _fsp--;
	    if (failed) return ;
	    if ( backtracking==0 ) {
	       _wikipage = new WikiPageNode(p); 
	    }
	    match(input,EOF,FOLLOW_EOF_in_wikipage124); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:138:1: paragraphs returns [CollectionNode sections = new CollectionNode()] : (p= paragraph )* ;
    public final CollectionNode paragraphs() throws RecognitionException {
	CollectionNode sections =  new CollectionNode();

	ASTNode p = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:139:2: ( (p= paragraph )* )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:139:4: (p= paragraph )*
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:139:4: (p= paragraph )*
	    loop2:
	    do {
		int alt2=2;
		int LA2_0 = input.LA(1);

		if ( ((LA2_0>=FORCED_END_OF_LINE && LA2_0<=WIKI)||(LA2_0>=POUND && LA2_0<=79)) ) {
		    alt2=1;
		}


		switch (alt2) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:139:5: p= paragraph
		    {
		    pushFollow(FOLLOW_paragraph_in_paragraphs142);
		    p=paragraph();
		    _fsp--;
		    if (failed) return sections;
		    if ( backtracking==0 ) {

					if (p != null){ // at this moment we ignore paragraps with blanks
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:145:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | l= list | t= table | tp= text_paragraph ) ( paragraph_separator )? );
    public final ASTNode paragraph() throws RecognitionException {
	ASTNode node =	null;

	NoWikiSectionNode n = null;

	ASTNode tof = null;

	ASTNode h = null;

	ASTNode hn = null;

	ListNode l = null;

	TableNode t = null;

	ParagraphNode tp = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:146:2: (n= nowiki_block | blanks paragraph_separator | ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | l= list | t= table | tp= text_paragraph ) ( paragraph_separator )? )
	    int alt6=3;
	    switch ( input.LA(1) ) {
	    case NOWIKI_OPEN:
		{
		int LA6_1 = input.LA(2);

		if ( ((LA6_1>=FORCED_END_OF_LINE && LA6_1<=WIKI)||(LA6_1>=POUND && LA6_1<=79)) ) {
		    alt6=3;
		}
		else if ( (LA6_1==NEWLINE) ) {
		    alt6=1;
		}
		else {
		    if (backtracking>0) {failed=true; return node;}
		    NoViableAltException nvae =
			new NoViableAltException("145:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | l= list | t= table | tp= text_paragraph ) ( paragraph_separator )? );", 6, 1, input);

		    throw nvae;
		}
		}
		break;
	    case BLANKS:
		{
		switch ( input.LA(2) ) {
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
		case BRACE_CLOSE:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
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
		case 79:
		    {
		    alt6=3;
		    }
		    break;
		default:
		    if (backtracking>0) {failed=true; return node;}
		    NoViableAltException nvae =
			new NoViableAltException("145:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | l= list | t= table | tp= text_paragraph ) ( paragraph_separator )? );", 6, 2, input);

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
	    case BRACE_CLOSE:
	    case COLON_SLASH:
	    case SLASH:
	    case TABLE_OF_CONTENTS_OPEN_MARKUP:
	    case TABLE_OF_CONTENTS_CLOSE_MARKUP:
	    case INSIGNIFICANT_CHAR:
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
	    case 79:
		{
		alt6=3;
		}
		break;
	    default:
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
		    new NoViableAltException("145:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | l= list | t= table | tp= text_paragraph ) ( paragraph_separator )? );", 6, 0, input);

		throw nvae;
	    }

	    switch (alt6) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:146:4: n= nowiki_block
		    {
		    pushFollow(FOLLOW_nowiki_block_in_paragraph163);
		    n=nowiki_block();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		       node = n; 
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:147:4: blanks paragraph_separator
		    {
		    pushFollow(FOLLOW_blanks_in_paragraph170);
		    blanks();
		    _fsp--;
		    if (failed) return node;
		    pushFollow(FOLLOW_paragraph_separator_in_paragraph173);
		    paragraph_separator();
		    _fsp--;
		    if (failed) return node;

		    }
		    break;
		case 3 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:148:4: ( blanks )? (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | l= list | t= table | tp= text_paragraph ) ( paragraph_separator )?
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:148:4: ( blanks )?
		    int alt3=2;
		    int LA3_0 = input.LA(1);

		    if ( (LA3_0==BLANKS) ) {
			alt3=1;
		    }
		    switch (alt3) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:148:6: blanks
			    {
			    pushFollow(FOLLOW_blanks_in_paragraph180);
			    blanks();
			    _fsp--;
			    if (failed) return node;

			    }
			    break;

		    }

		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:149:4: (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | l= list | t= table | tp= text_paragraph )
		    int alt4=6;
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
			    alt4=6;
			}
			else {
			    if (backtracking>0) {failed=true; return node;}
			    NoViableAltException nvae =
				new NoViableAltException("149:4: (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | l= list | t= table | tp= text_paragraph )", 4, 3, input);

			    throw nvae;
			}
			}
			break;
		    case POUND:
			{
			alt4=4;
			}
			break;
		    case STAR:
			{
			int LA4_5 = input.LA(2);

			if ( (!( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) )) ) {
			    alt4=4;
			}
			else if ( ( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ) ) {
			    alt4=6;
			}
			else {
			    if (backtracking>0) {failed=true; return node;}
			    NoViableAltException nvae =
				new NoViableAltException("149:4: (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | l= list | t= table | tp= text_paragraph )", 4, 5, input);

			    throw nvae;
			}
			}
			break;
		    case PIPE:
			{
			alt4=5;
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
		    case BRACE_CLOSE:
		    case COLON_SLASH:
		    case SLASH:
		    case TABLE_OF_CONTENTS_OPEN_MARKUP:
		    case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		    case INSIGNIFICANT_CHAR:
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
		    case 79:
			{
			alt4=6;
			}
			break;
		    default:
			if (backtracking>0) {failed=true; return node;}
			NoViableAltException nvae =
			    new NoViableAltException("149:4: (tof= table_of_contents | h= heading | {...}?hn= horizontalrule | l= list | t= table | tp= text_paragraph )", 4, 0, input);

			throw nvae;
		    }

		    switch (alt4) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:149:6: tof= table_of_contents
			    {
			    pushFollow(FOLLOW_table_of_contents_in_paragraph194);
			    tof=table_of_contents();
			    _fsp--;
			    if (failed) return node;
			    if ( backtracking==0 ) {
			      node = tof;
			    }

			    }
			    break;
			case 2 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:150:6: h= heading
			    {
			    pushFollow(FOLLOW_heading_in_paragraph208);
			    h=heading();
			    _fsp--;
			    if (failed) return node;
			    if ( backtracking==0 ) {
			       node = h;
			    }

			    }
			    break;
			case 3 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:151:6: {...}?hn= horizontalrule
			    {
			    if ( !( input.LA(1) == DASH && input.LA(2) == DASH &&
							input.LA(3) == DASH && input.LA(4) == DASH ) ) {
				if (backtracking>0) {failed=true; return node;}
				throw new FailedPredicateException(input, "paragraph", " input.LA(1) == DASH && input.LA(2) == DASH &&\n\t\t\t\tinput.LA(3) == DASH && input.LA(4) == DASH ");
			    }
			    pushFollow(FOLLOW_horizontalrule_in_paragraph227);
			    hn=horizontalrule();
			    _fsp--;
			    if (failed) return node;
			    if ( backtracking==0 ) {
			      node = hn;
			    }

			    }
			    break;
			case 4 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:154:6: l= list
			    {
			    pushFollow(FOLLOW_list_in_paragraph240);
			    l=list();
			    _fsp--;
			    if (failed) return node;
			    if ( backtracking==0 ) {
			      node = l;
			    }

			    }
			    break;
			case 5 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:155:6: t= table
			    {
			    pushFollow(FOLLOW_table_in_paragraph253);
			    t=table();
			    _fsp--;
			    if (failed) return node;
			    if ( backtracking==0 ) {
			       node = t; 
			    }

			    }
			    break;
			case 6 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:156:6: tp= text_paragraph
			    {
			    pushFollow(FOLLOW_text_paragraph_in_paragraph266);
			    tp=text_paragraph();
			    _fsp--;
			    if (failed) return node;
			    if ( backtracking==0 ) {
			      node = tp; 
			    }

			    }
			    break;

		    }

		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:157:7: ( paragraph_separator )?
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
			else if ( ((LA5_2>=FORCED_END_OF_LINE && LA5_2<=WIKI)||(LA5_2>=POUND && LA5_2<=79)) ) {
			    alt5=1;
			}
		    }
		    switch (alt5) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:157:9: paragraph_separator
			    {
			    pushFollow(FOLLOW_paragraph_separator_in_paragraph279);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:164:1: text_paragraph returns [ ParagraphNode paragraph = new ParagraphNode() ] : (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+ ;
    public final ParagraphNode text_paragraph() throws RecognitionException {
	ParagraphNode paragraph =  new ParagraphNode();

	LineNode tl = null;

	NoWikiSectionNode nw = null;

	ASTNode te = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:165:2: ( (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:165:4: (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:165:4: (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+
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
		case BRACE_CLOSE:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
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
		case 79:
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
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:165:6: tl= text_line
		    {
		    pushFollow(FOLLOW_text_line_in_text_paragraph307);
		    tl=text_line();
		    _fsp--;
		    if (failed) return paragraph;
		    if ( backtracking==0 ) {
			paragraph.addChildASTNode(tl);	
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:166:5: ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator
		    {
		    pushFollow(FOLLOW_nowiki_inline_in_text_paragraph339);
		    nw=nowiki_inline();
		    _fsp--;
		    if (failed) return paragraph;
		    if ( backtracking==0 ) {
		      paragraph.addChildASTNode(nw);
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:167:66: (te= text_element )*
		    loop7:
		    do {
			int alt7=2;
			int LA7_0 = input.LA(1);

			if ( ((LA7_0>=FORCED_END_OF_LINE && LA7_0<=WIKI)||(LA7_0>=POUND && LA7_0<=79)) ) {
			    alt7=1;
			}


			switch (alt7) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:167:68: te= text_element
			    {
			    pushFollow(FOLLOW_text_element_in_text_paragraph350);
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

		    pushFollow(FOLLOW_text_lineseparator_in_text_paragraph359);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:170:1: text_line returns [LineNode line = new LineNode()] : first= text_firstelement (element= text_element )* text_lineseparator ;
    public final LineNode text_line() throws RecognitionException {
	LineNode line =  new LineNode();

	ASTNode first = null;

	ASTNode element = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:171:2: (first= text_firstelement (element= text_element )* text_lineseparator )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:171:4: first= text_firstelement (element= text_element )* text_lineseparator
	    {
	    pushFollow(FOLLOW_text_firstelement_in_text_line382);
	    first=text_firstelement();
	    _fsp--;
	    if (failed) return line;
	    if ( backtracking==0 ) {

											if (first != null) { // recovering from errors
												line.addChildASTNode(first);
											}
										
	    }
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:176:9: (element= text_element )*
	    loop9:
	    do {
		int alt9=2;
		int LA9_0 = input.LA(1);

		if ( ((LA9_0>=FORCED_END_OF_LINE && LA9_0<=WIKI)||(LA9_0>=POUND && LA9_0<=79)) ) {
		    alt9=1;
		}


		switch (alt9) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:176:11: element= text_element
		    {
		    pushFollow(FOLLOW_text_element_in_text_line401);
		    element=text_element();
		    _fsp--;
		    if (failed) return line;
		    if ( backtracking==0 ) {

										if (element != null) // recovering from errors
											line.addChildASTNode(element);
									
		    }

		    }
		    break;

		default :
		    break loop9;
		}
	    } while (true);

	    pushFollow(FOLLOW_text_lineseparator_in_text_line415);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:182:1: text_firstelement returns [ASTNode item = null] : ({...}?tf= text_formattedelement | tu= text_first_unformattedelement );
    public final ASTNode text_firstelement() throws RecognitionException {
	ASTNode item =	null;

	FormattedTextNode tf = null;

	ASTNode tu = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:183:2: ({...}?tf= text_formattedelement | tu= text_first_unformattedelement )
	    int alt10=2;
	    int LA10_0 = input.LA(1);

	    if ( (LA10_0==STAR||LA10_0==ITAL) ) {
		alt10=1;
	    }
	    else if ( ((LA10_0>=FORCED_END_OF_LINE && LA10_0<=WIKI)||(LA10_0>=LINK_OPEN && LA10_0<=IMAGE_OPEN)||(LA10_0>=EXTENSION && LA10_0<=79)) ) {
		alt10=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return item;}
		NoViableAltException nvae =
		    new NoViableAltException("182:1: text_firstelement returns [ASTNode item = null] : ({...}?tf= text_formattedelement | tu= text_first_unformattedelement );", 10, 0, input);

		throw nvae;
	    }
	    switch (alt10) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:183:4: {...}?tf= text_formattedelement
		    {
		    if ( !( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ) ) {
			if (backtracking>0) {failed=true; return item;}
			throw new FailedPredicateException(input, "text_firstelement", " input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ");
		    }
		    pushFollow(FOLLOW_text_formattedelement_in_text_firstelement437);
		    tf=text_formattedelement();
		    _fsp--;
		    if (failed) return item;
		    if ( backtracking==0 ) {
		       item = tf; 
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:185:4: tu= text_first_unformattedelement
		    {
		    pushFollow(FOLLOW_text_first_unformattedelement_in_text_firstelement448);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:187:1: text_formattedelement returns [FormattedTextNode item = null] : ( ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )? | bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )? );
    public final FormattedTextNode text_formattedelement() throws RecognitionException {
	FormattedTextNode item =  null;

	CollectionNode ic = null;

	CollectionNode bc = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:188:2: ( ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )? | bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )? )
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
		    new NoViableAltException("187:1: text_formattedelement returns [FormattedTextNode item = null] : ( ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )? | bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )? );", 15, 0, input);

		throw nvae;
	    }
	    switch (alt15) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:188:4: ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )?
		    {
		    pushFollow(FOLLOW_ital_markup_in_text_formattedelement464);
		    ital_markup();
		    _fsp--;
		    if (failed) return item;
		    pushFollow(FOLLOW_text_italcontent_in_text_formattedelement470);
		    ic=text_italcontent();
		    _fsp--;
		    if (failed) return item;
		    if ( backtracking==0 ) {
		       item = new ItalicTextNode(ic); 
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:188:81: ( ( NEWLINE )? ital_markup )?
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:188:83: ( NEWLINE )? ital_markup
			    {
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:188:83: ( NEWLINE )?
			    int alt11=2;
			    int LA11_0 = input.LA(1);

			    if ( (LA11_0==NEWLINE) ) {
				alt11=1;
			    }
			    switch (alt11) {
				case 1 :
				    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:188:85: NEWLINE
				    {
				    match(input,NEWLINE,FOLLOW_NEWLINE_in_text_formattedelement479); if (failed) return item;

				    }
				    break;

			    }

			    pushFollow(FOLLOW_ital_markup_in_text_formattedelement485);
			    ital_markup();
			    _fsp--;
			    if (failed) return item;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:189:4: bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )?
		    {
		    pushFollow(FOLLOW_bold_markup_in_text_formattedelement493);
		    bold_markup();
		    _fsp--;
		    if (failed) return item;
		    pushFollow(FOLLOW_text_boldcontent_in_text_formattedelement500);
		    bc=text_boldcontent();
		    _fsp--;
		    if (failed) return item;
		    if ( backtracking==0 ) {
		      item = new BoldTextNode(bc); 
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:189:79: ( ( NEWLINE )? bold_markup )?
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:189:81: ( NEWLINE )? bold_markup
			    {
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:189:81: ( NEWLINE )?
			    int alt13=2;
			    int LA13_0 = input.LA(1);

			    if ( (LA13_0==NEWLINE) ) {
				alt13=1;
			    }
			    switch (alt13) {
				case 1 :
				    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:189:83: NEWLINE
				    {
				    match(input,NEWLINE,FOLLOW_NEWLINE_in_text_formattedelement509); if (failed) return item;

				    }
				    break;

			    }

			    pushFollow(FOLLOW_bold_markup_in_text_formattedelement515);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:191:1: text_boldcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_boldcontentpart )* | EOF );
    public final CollectionNode text_boldcontent() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	FormattedTextNode p = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:192:2: ( ( NEWLINE )? (p= text_boldcontentpart )* | EOF )
	    int alt18=2;
	    int LA18_0 = input.LA(1);

	    if ( ((LA18_0>=FORCED_END_OF_LINE && LA18_0<=79)) ) {
		alt18=1;
	    }
	    else if ( (LA18_0==EOF) ) {
		alt18=1;
	    }
	    else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
		    new NoViableAltException("191:1: text_boldcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_boldcontentpart )* | EOF );", 18, 0, input);

		throw nvae;
	    }
	    switch (alt18) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:192:4: ( NEWLINE )? (p= text_boldcontentpart )*
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:192:4: ( NEWLINE )?
		    int alt16=2;
		    int LA16_0 = input.LA(1);

		    if ( (LA16_0==NEWLINE) ) {
			alt16=1;
		    }
		    switch (alt16) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:192:6: NEWLINE
			    {
			    match(input,NEWLINE,FOLLOW_NEWLINE_in_text_boldcontent534); if (failed) return text;

			    }
			    break;

		    }

		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:192:18: (p= text_boldcontentpart )*
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:192:20: p= text_boldcontentpart
			    {
			    pushFollow(FOLLOW_text_boldcontentpart_in_text_boldcontent546);
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
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:193:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_text_boldcontent557); if (failed) return text;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:195:1: text_italcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_italcontentpart )* | EOF );
    public final CollectionNode text_italcontent() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	FormattedTextNode p = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:196:2: ( ( NEWLINE )? (p= text_italcontentpart )* | EOF )
	    int alt21=2;
	    int LA21_0 = input.LA(1);

	    if ( ((LA21_0>=FORCED_END_OF_LINE && LA21_0<=79)) ) {
		alt21=1;
	    }
	    else if ( (LA21_0==EOF) ) {
		alt21=1;
	    }
	    else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
		    new NoViableAltException("195:1: text_italcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_italcontentpart )* | EOF );", 21, 0, input);

		throw nvae;
	    }
	    switch (alt21) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:196:4: ( NEWLINE )? (p= text_italcontentpart )*
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:196:4: ( NEWLINE )?
		    int alt19=2;
		    int LA19_0 = input.LA(1);

		    if ( (LA19_0==NEWLINE) ) {
			alt19=1;
		    }
		    switch (alt19) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:196:6: NEWLINE
			    {
			    match(input,NEWLINE,FOLLOW_NEWLINE_in_text_italcontent573); if (failed) return text;

			    }
			    break;

		    }

		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:196:18: (p= text_italcontentpart )*
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:196:20: p= text_italcontentpart
			    {
			    pushFollow(FOLLOW_text_italcontentpart_in_text_italcontent585);
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
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:197:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_text_italcontent596); if (failed) return text;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:199:1: text_element returns [ASTNode item = null] : ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement );
    public final ASTNode text_element() throws RecognitionException {
	ASTNode item =	null;

	ASTNode tu1 = null;

	ASTNode tu2 = null;

	FormattedTextNode tf = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:200:2: ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement )
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
			new NoViableAltException("199:1: text_element returns [ASTNode item = null] : ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement );", 22, 1, input);

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
	    case BRACE_CLOSE:
	    case COLON_SLASH:
	    case SLASH:
	    case TABLE_OF_CONTENTS_OPEN_MARKUP:
	    case TABLE_OF_CONTENTS_CLOSE_MARKUP:
	    case INSIGNIFICANT_CHAR:
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
	    case 79:
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
		    new NoViableAltException("199:1: text_element returns [ASTNode item = null] : ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement );", 22, 0, input);

		throw nvae;
	    }

	    switch (alt22) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:200:4: onestar tu1= text_unformattedelement
		    {
		    pushFollow(FOLLOW_onestar_in_text_element610);
		    onestar();
		    _fsp--;
		    if (failed) return item;
		    pushFollow(FOLLOW_text_unformattedelement_in_text_element617);
		    tu1=text_unformattedelement();
		    _fsp--;
		    if (failed) return item;
		    if ( backtracking==0 ) {
		       item = tu1; 
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:201:4: tu2= text_unformattedelement onestar
		    {
		    pushFollow(FOLLOW_text_unformattedelement_in_text_element628);
		    tu2=text_unformattedelement();
		    _fsp--;
		    if (failed) return item;
		    pushFollow(FOLLOW_onestar_in_text_element631);
		    onestar();
		    _fsp--;
		    if (failed) return item;
		    if ( backtracking==0 ) {
		       item = tu2; 
		    }

		    }
		    break;
		case 3 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:202:4: tf= text_formattedelement
		    {
		    pushFollow(FOLLOW_text_formattedelement_in_text_element642);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:205:1: text_boldcontentpart returns [FormattedTextNode node = null] : ( ital_markup t= text_bolditalcontent ( ital_markup )? | tf= text_formattedcontent );
    public final FormattedTextNode text_boldcontentpart() throws RecognitionException {
	FormattedTextNode node =  null;

	ASTNode t = null;

	CollectionNode tf = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:206:2: ( ital_markup t= text_bolditalcontent ( ital_markup )? | tf= text_formattedcontent )
	    int alt24=2;
	    int LA24_0 = input.LA(1);

	    if ( (LA24_0==ITAL) ) {
		alt24=1;
	    }
	    else if ( ((LA24_0>=FORCED_END_OF_LINE && LA24_0<=WIKI)||(LA24_0>=POUND && LA24_0<=PIPE)||(LA24_0>=LINK_OPEN && LA24_0<=79)) ) {
		alt24=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
		    new NoViableAltException("205:1: text_boldcontentpart returns [FormattedTextNode node = null] : ( ital_markup t= text_bolditalcontent ( ital_markup )? | tf= text_formattedcontent );", 24, 0, input);

		throw nvae;
	    }
	    switch (alt24) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:206:4: ital_markup t= text_bolditalcontent ( ital_markup )?
		    {
		    pushFollow(FOLLOW_ital_markup_in_text_boldcontentpart659);
		    ital_markup();
		    _fsp--;
		    if (failed) return node;
		    pushFollow(FOLLOW_text_bolditalcontent_in_text_boldcontentpart666);
		    t=text_bolditalcontent();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		      node = new ItalicTextNode(t); 
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:206:84: ( ital_markup )?
		    int alt23=2;
		    int LA23_0 = input.LA(1);

		    if ( (LA23_0==ITAL) ) {
			alt23=1;
		    }
		    switch (alt23) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:206:86: ital_markup
			    {
			    pushFollow(FOLLOW_ital_markup_in_text_boldcontentpart673);
			    ital_markup();
			    _fsp--;
			    if (failed) return node;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:207:4: tf= text_formattedcontent
		    {
		    pushFollow(FOLLOW_text_formattedcontent_in_text_boldcontentpart685);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:209:1: text_italcontentpart returns [FormattedTextNode node = null] : ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent );
    public final FormattedTextNode text_italcontentpart() throws RecognitionException {
	FormattedTextNode node =  null;

	ASTNode t = null;

	CollectionNode tf = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:210:2: ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent )
	    int alt26=2;
	    int LA26_0 = input.LA(1);

	    if ( (LA26_0==STAR) ) {
		int LA26_1 = input.LA(2);

		if ( (LA26_1==STAR) ) {
		    alt26=1;
		}
		else if ( ((LA26_1>=FORCED_END_OF_LINE && LA26_1<=WIKI)||LA26_1==POUND||(LA26_1>=EQUAL && LA26_1<=PIPE)||(LA26_1>=LINK_OPEN && LA26_1<=79)) ) {
		    alt26=2;
		}
		else {
		    if (backtracking>0) {failed=true; return node;}
		    NoViableAltException nvae =
			new NoViableAltException("209:1: text_italcontentpart returns [FormattedTextNode node = null] : ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent );", 26, 1, input);

		    throw nvae;
		}
	    }
	    else if ( ((LA26_0>=FORCED_END_OF_LINE && LA26_0<=WIKI)||LA26_0==POUND||(LA26_0>=EQUAL && LA26_0<=PIPE)||(LA26_0>=LINK_OPEN && LA26_0<=79)) ) {
		alt26=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
		    new NoViableAltException("209:1: text_italcontentpart returns [FormattedTextNode node = null] : ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent );", 26, 0, input);

		throw nvae;
	    }
	    switch (alt26) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:210:4: bold_markup t= text_bolditalcontent ( bold_markup )?
		    {
		    pushFollow(FOLLOW_bold_markup_in_text_italcontentpart701);
		    bold_markup();
		    _fsp--;
		    if (failed) return node;
		    pushFollow(FOLLOW_text_bolditalcontent_in_text_italcontentpart708);
		    t=text_bolditalcontent();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		       node = new BoldTextNode(t); 
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:210:82: ( bold_markup )?
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:210:84: bold_markup
			    {
			    pushFollow(FOLLOW_bold_markup_in_text_italcontentpart714);
			    bold_markup();
			    _fsp--;
			    if (failed) return node;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:211:4: tf= text_formattedcontent
		    {
		    pushFollow(FOLLOW_text_formattedcontent_in_text_italcontentpart725);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:213:1: text_bolditalcontent returns [ASTNode items = null] : ( ( NEWLINE )? (tf= text_formattedcontent )? | EOF );
    public final ASTNode text_bolditalcontent() throws RecognitionException {
	ASTNode items =  null;

	CollectionNode tf = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:214:2: ( ( NEWLINE )? (tf= text_formattedcontent )? | EOF )
	    int alt29=2;
	    int LA29_0 = input.LA(1);

	    if ( ((LA29_0>=FORCED_END_OF_LINE && LA29_0<=79)) ) {
		alt29=1;
	    }
	    else if ( (LA29_0==EOF) ) {
		alt29=1;
	    }
	    else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
		    new NoViableAltException("213:1: text_bolditalcontent returns [ASTNode items = null] : ( ( NEWLINE )? (tf= text_formattedcontent )? | EOF );", 29, 0, input);

		throw nvae;
	    }
	    switch (alt29) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:214:4: ( NEWLINE )? (tf= text_formattedcontent )?
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:214:4: ( NEWLINE )?
		    int alt27=2;
		    int LA27_0 = input.LA(1);

		    if ( (LA27_0==NEWLINE) ) {
			alt27=1;
		    }
		    switch (alt27) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:214:6: NEWLINE
			    {
			    match(input,NEWLINE,FOLLOW_NEWLINE_in_text_bolditalcontent743); if (failed) return items;

			    }
			    break;

		    }

		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:214:18: (tf= text_formattedcontent )?
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:214:20: tf= text_formattedcontent
			    {
			    pushFollow(FOLLOW_text_formattedcontent_in_text_bolditalcontent754);
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
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:215:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_text_bolditalcontent764); if (failed) return items;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:217:1: text_formattedcontent returns [CollectionNode items = new CollectionNode ()] : onestar (t= text_unformattedelement onestar ( text_linebreak )? )+ ;
    public final CollectionNode text_formattedcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode ();

	ASTNode t = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:218:2: ( onestar (t= text_unformattedelement onestar ( text_linebreak )? )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:218:4: onestar (t= text_unformattedelement onestar ( text_linebreak )? )+
	    {
	    pushFollow(FOLLOW_onestar_in_text_formattedcontent778);
	    onestar();
	    _fsp--;
	    if (failed) return items;
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:218:13: (t= text_unformattedelement onestar ( text_linebreak )? )+
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
		case BRACE_CLOSE:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
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
		case 79:
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
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:218:15: t= text_unformattedelement onestar ( text_linebreak )?
		    {
		    pushFollow(FOLLOW_text_unformattedelement_in_text_formattedcontent787);
		    t=text_unformattedelement();
		    _fsp--;
		    if (failed) return items;
		    if ( backtracking==0 ) {
		      items.add(t); 
		    }
		    pushFollow(FOLLOW_onestar_in_text_formattedcontent792);
		    onestar();
		    _fsp--;
		    if (failed) return items;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:218:81: ( text_linebreak )?
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:218:83: text_linebreak
			    {
			    pushFollow(FOLLOW_text_linebreak_in_text_formattedcontent797);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:220:1: text_linebreak : {...}? text_lineseparator ;
    public final void text_linebreak() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:221:2: ({...}? text_lineseparator )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:221:4: {...}? text_lineseparator
	    {
	    if ( !( input.LA(2) != DASH && input.LA(2) != POUND &&
			input.LA(2) != EQUAL && input.LA(2) != NEWLINE ) ) {
		if (backtracking>0) {failed=true; return ;}
		throw new FailedPredicateException(input, "text_linebreak", " input.LA(2) != DASH && input.LA(2) != POUND &&\n\t\tinput.LA(2) != EQUAL && input.LA(2) != NEWLINE ");
	    }
	    pushFollow(FOLLOW_text_lineseparator_in_text_linebreak817);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:225:1: text_inlineelement returns [ASTNode element = null ] : (tf= text_first_inlineelement | nwi= nowiki_inline );
    public final ASTNode text_inlineelement() throws RecognitionException {
	ASTNode element =  null;

	ASTNode tf = null;

	NoWikiSectionNode nwi = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:226:2: (tf= text_first_inlineelement | nwi= nowiki_inline )
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
		    new NoViableAltException("225:1: text_inlineelement returns [ASTNode element = null ] : (tf= text_first_inlineelement | nwi= nowiki_inline );", 32, 0, input);

		throw nvae;
	    }
	    switch (alt32) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:226:4: tf= text_first_inlineelement
		    {
		    pushFollow(FOLLOW_text_first_inlineelement_in_text_inlineelement835);
		    tf=text_first_inlineelement();
		    _fsp--;
		    if (failed) return element;
		    if ( backtracking==0 ) {
		      element = tf; 
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:227:4: nwi= nowiki_inline
		    {
		    pushFollow(FOLLOW_nowiki_inline_in_text_inlineelement846);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:229:1: text_first_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension );
    public final ASTNode text_first_inlineelement() throws RecognitionException {
	ASTNode element =  null;

	LinkNode l = null;

	ImageNode i = null;

	ASTNode e = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:230:2: (l= link | i= image | e= extension )
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
		    new NoViableAltException("229:1: text_first_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension );", 33, 0, input);

		throw nvae;
	    }

	    switch (alt33) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:231:3: l= link
		    {
		    pushFollow(FOLLOW_link_in_text_first_inlineelement867);
		    l=link();
		    _fsp--;
		    if (failed) return element;
		    if ( backtracking==0 ) {
		      element = l;
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:232:4: i= image
		    {
		    pushFollow(FOLLOW_image_in_text_first_inlineelement878);
		    i=image();
		    _fsp--;
		    if (failed) return element;
		    if ( backtracking==0 ) {
		      element = i;
		    }

		    }
		    break;
		case 3 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:233:4: e= extension
		    {
		    pushFollow(FOLLOW_extension_in_text_first_inlineelement888);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:235:1: text_first_unformattedelement returns [ASTNode item = null] : (tfu= text_first_unformatted | tfi= text_first_inlineelement );
    public final ASTNode text_first_unformattedelement() throws RecognitionException {
	ASTNode item =	null;

	CollectionNode tfu = null;

	ASTNode tfi = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:236:2: (tfu= text_first_unformatted | tfi= text_first_inlineelement )
	    int alt34=2;
	    int LA34_0 = input.LA(1);

	    if ( ((LA34_0>=FORCED_END_OF_LINE && LA34_0<=WIKI)||(LA34_0>=FORCED_LINEBREAK && LA34_0<=79)) ) {
		alt34=1;
	    }
	    else if ( ((LA34_0>=LINK_OPEN && LA34_0<=IMAGE_OPEN)||LA34_0==EXTENSION) ) {
		alt34=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return item;}
		NoViableAltException nvae =
		    new NoViableAltException("235:1: text_first_unformattedelement returns [ASTNode item = null] : (tfu= text_first_unformatted | tfi= text_first_inlineelement );", 34, 0, input);

		throw nvae;
	    }
	    switch (alt34) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:236:4: tfu= text_first_unformatted
		    {
		    pushFollow(FOLLOW_text_first_unformatted_in_text_first_unformattedelement908);
		    tfu=text_first_unformatted();
		    _fsp--;
		    if (failed) return item;
		    if ( backtracking==0 ) {
		      item = new UnformattedTextNode(tfu);
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:237:4: tfi= text_first_inlineelement
		    {
		    pushFollow(FOLLOW_text_first_inlineelement_in_text_first_unformattedelement919);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:239:1: text_first_unformatted returns [CollectionNode items = new CollectionNode()] : (t= text_first_unformmatted_text | ( forced_linebreak | e= escaped )+ );
    public final CollectionNode text_first_unformatted() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	StringBundler t = null;

	ScapedNode e = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:240:2: (t= text_first_unformmatted_text | ( forced_linebreak | e= escaped )+ )
	    int alt36=2;
	    int LA36_0 = input.LA(1);

	    if ( ((LA36_0>=FORCED_END_OF_LINE && LA36_0<=WIKI)||(LA36_0>=NOWIKI_BLOCK_CLOSE && LA36_0<=79)) ) {
		alt36=1;
	    }
	    else if ( ((LA36_0>=FORCED_LINEBREAK && LA36_0<=ESCAPE)) ) {
		alt36=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
		    new NoViableAltException("239:1: text_first_unformatted returns [CollectionNode items = new CollectionNode()] : (t= text_first_unformmatted_text | ( forced_linebreak | e= escaped )+ );", 36, 0, input);

		throw nvae;
	    }
	    switch (alt36) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:240:6: t= text_first_unformmatted_text
		    {
		    pushFollow(FOLLOW_text_first_unformmatted_text_in_text_first_unformatted941);
		    t=text_first_unformmatted_text();
		    _fsp--;
		    if (failed) return items;
		    if ( backtracking==0 ) {
		      items.add(new UnformattedTextNode(t.toString()));
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:241:5: ( forced_linebreak | e= escaped )+
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:241:5: ( forced_linebreak | e= escaped )+
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

			    if ( ((LA35_3>=FORCED_END_OF_LINE && LA35_3<=79)) ) {
				alt35=2;
			    }


			}


			switch (alt35) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:241:6: forced_linebreak
			    {
			    pushFollow(FOLLOW_forced_linebreak_in_text_first_unformatted950);
			    forced_linebreak();
			    _fsp--;
			    if (failed) return items;
			    if ( backtracking==0 ) {
			       items.add(new ForcedEndOfLineNode()); 
			    }

			    }
			    break;
			case 2 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:242:5: e= escaped
			    {
			    pushFollow(FOLLOW_escaped_in_text_first_unformatted962);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:245:1: text_first_unformmatted_text returns [StringBundler text = new StringBundler()] : (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
    public final StringBundler text_first_unformmatted_text() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:246:2: ( (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:247:3: (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:247:3: (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
	    int cnt37=0;
	    loop37:
	    do {
		int alt37=2;
		int LA37_0 = input.LA(1);

		if ( ((LA37_0>=FORCED_END_OF_LINE && LA37_0<=WIKI)||(LA37_0>=NOWIKI_BLOCK_CLOSE && LA37_0<=79)) ) {
		    alt37=1;
		}


		switch (alt37) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:247:4: c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
		    {
		    c=(Token)input.LT(1);
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_text_first_unformmatted_text990);    throw mse;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:261:1: text_unformattedelement returns [ASTNode contents = null] : (text= text_unformatted | ti= text_inlineelement );
    public final ASTNode text_unformattedelement() throws RecognitionException {
	ASTNode contents =  null;

	CollectionNode text = null;

	ASTNode ti = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:262:2: (text= text_unformatted | ti= text_inlineelement )
	    int alt38=2;
	    int LA38_0 = input.LA(1);

	    if ( ((LA38_0>=FORCED_END_OF_LINE && LA38_0<=WIKI)||LA38_0==POUND||(LA38_0>=EQUAL && LA38_0<=PIPE)||(LA38_0>=FORCED_LINEBREAK && LA38_0<=79)) ) {
		alt38=1;
	    }
	    else if ( ((LA38_0>=LINK_OPEN && LA38_0<=EXTENSION)) ) {
		alt38=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return contents;}
		NoViableAltException nvae =
		    new NoViableAltException("261:1: text_unformattedelement returns [ASTNode contents = null] : (text= text_unformatted | ti= text_inlineelement );", 38, 0, input);

		throw nvae;
	    }
	    switch (alt38) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:262:4: text= text_unformatted
		    {
		    pushFollow(FOLLOW_text_unformatted_in_text_unformattedelement1104);
		    text=text_unformatted();
		    _fsp--;
		    if (failed) return contents;
		    if ( backtracking==0 ) {
		       contents = text; 
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:263:4: ti= text_inlineelement
		    {
		    pushFollow(FOLLOW_text_inlineelement_in_text_unformattedelement1115);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:266:1: text_unformatted returns [CollectionNode items = new CollectionNode()] : (contents= text_unformated_text | ( forced_linebreak | e= escaped )+ );
    public final CollectionNode text_unformatted() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	StringBundler contents = null;

	ScapedNode e = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:267:2: (contents= text_unformated_text | ( forced_linebreak | e= escaped )+ )
	    int alt40=2;
	    int LA40_0 = input.LA(1);

	    if ( ((LA40_0>=FORCED_END_OF_LINE && LA40_0<=WIKI)||LA40_0==POUND||(LA40_0>=EQUAL && LA40_0<=PIPE)||(LA40_0>=NOWIKI_BLOCK_CLOSE && LA40_0<=79)) ) {
		alt40=1;
	    }
	    else if ( ((LA40_0>=FORCED_LINEBREAK && LA40_0<=ESCAPE)) ) {
		alt40=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
		    new NoViableAltException("266:1: text_unformatted returns [CollectionNode items = new CollectionNode()] : (contents= text_unformated_text | ( forced_linebreak | e= escaped )+ );", 40, 0, input);

		throw nvae;
	    }
	    switch (alt40) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:267:5: contents= text_unformated_text
		    {
		    pushFollow(FOLLOW_text_unformated_text_in_text_unformatted1137);
		    contents=text_unformated_text();
		    _fsp--;
		    if (failed) return items;
		    if ( backtracking==0 ) {
		      items.add(new UnformattedTextNode(contents.toString())); 
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:268:5: ( forced_linebreak | e= escaped )+
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:268:5: ( forced_linebreak | e= escaped )+
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:268:6: forced_linebreak
			    {
			    pushFollow(FOLLOW_forced_linebreak_in_text_unformatted1146);
			    forced_linebreak();
			    _fsp--;
			    if (failed) return items;
			    if ( backtracking==0 ) {
			       items.add(new ForcedEndOfLineNode()); 
			    }

			    }
			    break;
			case 2 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:269:5: e= escaped
			    {
			    pushFollow(FOLLOW_escaped_in_text_unformatted1158);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:272:1: text_unformated_text returns [StringBundler text = new StringBundler()] : (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
    public final StringBundler text_unformated_text() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:273:1: ( (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:274:2: (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:274:2: (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
	    int cnt41=0;
	    loop41:
	    do {
		int alt41=2;
		int LA41_0 = input.LA(1);

		if ( ((LA41_0>=FORCED_END_OF_LINE && LA41_0<=WIKI)||LA41_0==POUND||(LA41_0>=EQUAL && LA41_0<=PIPE)||(LA41_0>=NOWIKI_BLOCK_CLOSE && LA41_0<=79)) ) {
		    alt41=1;
		}


		switch (alt41) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:274:3: c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
		    {
		    c=(Token)input.LT(1);
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||(input.LA(1)>=EQUAL && input.LA(1)<=PIPE)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_text_unformated_text1183);    throw mse;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:288:1: heading returns [ASTNode header] : heading_markup heading_content ( heading_markup )? ( blanks )? paragraph_separator ;
    public final ASTNode heading() throws RecognitionException {
	heading_stack.push(new heading_scope());
	ASTNode header = null;


			((heading_scope)heading_stack.peek()).items = new CollectionNode();
			((heading_scope)heading_stack.peek()).text = new String();
		
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:298:2: ( heading_markup heading_content ( heading_markup )? ( blanks )? paragraph_separator )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:298:4: heading_markup heading_content ( heading_markup )? ( blanks )? paragraph_separator
	    {
	    pushFollow(FOLLOW_heading_markup_in_heading1285);
	    heading_markup();
	    _fsp--;
	    if (failed) return header;
	    if ( backtracking==0 ) {
	      ((heading_scope)heading_stack.peek()).nestedLevel++;
	    }
	    pushFollow(FOLLOW_heading_content_in_heading1290);
	    heading_content();
	    _fsp--;
	    if (failed) return header;
	    if ( backtracking==0 ) {
	       header = new HeadingNode(((heading_scope)heading_stack.peek()).items,((heading_scope)heading_stack.peek()).nestedLevel); 
	    }
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:298:134: ( heading_markup )?
	    int alt42=2;
	    int LA42_0 = input.LA(1);

	    if ( (LA42_0==EQUAL) ) {
		alt42=1;
	    }
	    switch (alt42) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:298:136: heading_markup
		    {
		    pushFollow(FOLLOW_heading_markup_in_heading1297);
		    heading_markup();
		    _fsp--;
		    if (failed) return header;

		    }
		    break;

	    }

	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:298:155: ( blanks )?
	    int alt43=2;
	    int LA43_0 = input.LA(1);

	    if ( (LA43_0==BLANKS) ) {
		alt43=1;
	    }
	    switch (alt43) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:298:157: blanks
		    {
		    pushFollow(FOLLOW_blanks_in_heading1305);
		    blanks();
		    _fsp--;
		    if (failed) return header;

		    }
		    break;

	    }

	    pushFollow(FOLLOW_paragraph_separator_in_heading1312);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:301:1: heading_content : ( heading_markup heading_content ( heading_markup )? | ht= heading_text );
    public final void heading_content() throws RecognitionException {
	CollectionNode ht = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:302:2: ( heading_markup heading_content ( heading_markup )? | ht= heading_text )
	    int alt45=2;
	    int LA45_0 = input.LA(1);

	    if ( (LA45_0==EQUAL) ) {
		alt45=1;
	    }
	    else if ( (LA45_0==EOF||(LA45_0>=FORCED_END_OF_LINE && LA45_0<=STAR)||(LA45_0>=PIPE && LA45_0<=FORCED_LINEBREAK)||(LA45_0>=NOWIKI_BLOCK_CLOSE && LA45_0<=79)) ) {
		alt45=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
		    new NoViableAltException("301:1: heading_content : ( heading_markup heading_content ( heading_markup )? | ht= heading_text );", 45, 0, input);

		throw nvae;
	    }
	    switch (alt45) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:302:4: heading_markup heading_content ( heading_markup )?
		    {
		    pushFollow(FOLLOW_heading_markup_in_heading_content1322);
		    heading_markup();
		    _fsp--;
		    if (failed) return ;
		    if ( backtracking==0 ) {
		      ((heading_scope)heading_stack.peek()).nestedLevel++;
		    }
		    pushFollow(FOLLOW_heading_content_in_heading_content1327);
		    heading_content();
		    _fsp--;
		    if (failed) return ;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:302:64: ( heading_markup )?
		    int alt44=2;
		    int LA44_0 = input.LA(1);

		    if ( (LA44_0==EQUAL) ) {
			alt44=1;
		    }
		    switch (alt44) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:302:66: heading_markup
			    {
			    pushFollow(FOLLOW_heading_markup_in_heading_content1332);
			    heading_markup();
			    _fsp--;
			    if (failed) return ;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:303:4: ht= heading_text
		    {
		    pushFollow(FOLLOW_heading_text_in_heading_content1344);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:306:1: heading_text returns [CollectionNode items = null] : te= heading_cellcontent ;
    public final CollectionNode heading_text() throws RecognitionException {
	CollectionNode items =	null;

	CollectionNode te = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:307:2: (te= heading_cellcontent )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:307:4: te= heading_cellcontent
	    {
	    pushFollow(FOLLOW_heading_cellcontent_in_heading_text1365);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:310:1: heading_cellcontent returns [CollectionNode items = new CollectionNode()] : onestar (tcp= heading_cellcontentpart onestar )* ;
    public final CollectionNode heading_cellcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode tcp = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:311:2: ( onestar (tcp= heading_cellcontentpart onestar )* )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:311:4: onestar (tcp= heading_cellcontentpart onestar )*
	    {
	    pushFollow(FOLLOW_onestar_in_heading_cellcontent1382);
	    onestar();
	    _fsp--;
	    if (failed) return items;
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:311:13: (tcp= heading_cellcontentpart onestar )*
	    loop46:
	    do {
		int alt46=2;
		int LA46_0 = input.LA(1);

		if ( ((LA46_0>=FORCED_END_OF_LINE && LA46_0<=WIKI)||(LA46_0>=POUND && LA46_0<=STAR)||(LA46_0>=PIPE && LA46_0<=FORCED_LINEBREAK)||(LA46_0>=NOWIKI_BLOCK_CLOSE && LA46_0<=79)) ) {
		    alt46=1;
		}


		switch (alt46) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:311:15: tcp= heading_cellcontentpart onestar
		    {
		    pushFollow(FOLLOW_heading_cellcontentpart_in_heading_cellcontent1391);
		    tcp=heading_cellcontentpart();
		    _fsp--;
		    if (failed) return items;
		    if ( backtracking==0 ) {


									if (tcp != null) { // some AST Node could be NULL if bad CREOLE syntax is wrotten
										items.add(tcp);
									}

									
		    }
		    pushFollow(FOLLOW_onestar_in_heading_cellcontent1402);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:320:1: heading_cellcontentpart returns [ASTNode node = null] : (tf= heading_formattedelement | tu= heading_unformattedelement );
    public final ASTNode heading_cellcontentpart() throws RecognitionException {
	ASTNode node =	null;

	ASTNode tf = null;

	ASTNode tu = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:321:2: (tf= heading_formattedelement | tu= heading_unformattedelement )
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
		else if ( (LA47_2==EOF||(LA47_2>=FORCED_END_OF_LINE && LA47_2<=POUND)||(LA47_2>=EQUAL && LA47_2<=FORCED_LINEBREAK)||(LA47_2>=NOWIKI_BLOCK_CLOSE && LA47_2<=79)) ) {
		    alt47=2;
		}
		else {
		    if (backtracking>0) {failed=true; return node;}
		    NoViableAltException nvae =
			new NoViableAltException("320:1: heading_cellcontentpart returns [ASTNode node = null] : (tf= heading_formattedelement | tu= heading_unformattedelement );", 47, 2, input);

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
	    case BRACE_CLOSE:
	    case COLON_SLASH:
	    case SLASH:
	    case TABLE_OF_CONTENTS_OPEN_MARKUP:
	    case TABLE_OF_CONTENTS_CLOSE_MARKUP:
	    case INSIGNIFICANT_CHAR:
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
	    case 79:
		{
		alt47=2;
		}
		break;
	    default:
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
		    new NoViableAltException("320:1: heading_cellcontentpart returns [ASTNode node = null] : (tf= heading_formattedelement | tu= heading_unformattedelement );", 47, 0, input);

		throw nvae;
	    }

	    switch (alt47) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:321:4: tf= heading_formattedelement
		    {
		    pushFollow(FOLLOW_heading_formattedelement_in_heading_cellcontentpart1423);
		    tf=heading_formattedelement();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		      node =tf;
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:322:4: tu= heading_unformattedelement
		    {
		    pushFollow(FOLLOW_heading_unformattedelement_in_heading_cellcontentpart1434);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:324:1: heading_formattedelement returns [ASTNode content = null] : ( ital_markup (tic= heading_italcontent )? ( ital_markup )? | bold_markup (tbc= heading_boldcontent )? ( bold_markup )? );
    public final ASTNode heading_formattedelement() throws RecognitionException {
	ASTNode content =  null;

	CollectionNode tic = null;

	CollectionNode tbc = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:325:2: ( ital_markup (tic= heading_italcontent )? ( ital_markup )? | bold_markup (tbc= heading_boldcontent )? ( bold_markup )? )
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
		    new NoViableAltException("324:1: heading_formattedelement returns [ASTNode content = null] : ( ital_markup (tic= heading_italcontent )? ( ital_markup )? | bold_markup (tbc= heading_boldcontent )? ( bold_markup )? );", 52, 0, input);

		throw nvae;
	    }
	    switch (alt52) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:325:4: ital_markup (tic= heading_italcontent )? ( ital_markup )?
		    {
		    pushFollow(FOLLOW_ital_markup_in_heading_formattedelement1450);
		    ital_markup();
		    _fsp--;
		    if (failed) return content;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:325:18: (tic= heading_italcontent )?
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt48=1;
			    }
			    break;
		    }

		    switch (alt48) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:325:20: tic= heading_italcontent
			    {
			    pushFollow(FOLLOW_heading_italcontent_in_heading_formattedelement1460);
			    tic=heading_italcontent();
			    _fsp--;
			    if (failed) return content;
			    if ( backtracking==0 ) {
			       content = new ItalicTextNode(tic); 
			    }

			    }
			    break;

		    }

		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:325:96: ( ital_markup )?
		    int alt49=2;
		    int LA49_0 = input.LA(1);

		    if ( (LA49_0==ITAL) ) {
			alt49=1;
		    }
		    switch (alt49) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:325:98: ital_markup
			    {
			    pushFollow(FOLLOW_ital_markup_in_heading_formattedelement1469);
			    ital_markup();
			    _fsp--;
			    if (failed) return content;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:326:4: bold_markup (tbc= heading_boldcontent )? ( bold_markup )?
		    {
		    pushFollow(FOLLOW_bold_markup_in_heading_formattedelement1477);
		    bold_markup();
		    _fsp--;
		    if (failed) return content;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:326:16: (tbc= heading_boldcontent )?
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt50=1;
			    }
			    break;
		    }

		    switch (alt50) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:326:18: tbc= heading_boldcontent
			    {
			    pushFollow(FOLLOW_heading_boldcontent_in_heading_formattedelement1484);
			    tbc=heading_boldcontent();
			    _fsp--;
			    if (failed) return content;
			    if ( backtracking==0 ) {
			      content = new BoldTextNode(tbc);
			    }

			    }
			    break;

		    }

		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:326:90: ( bold_markup )?
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:326:92: bold_markup
			    {
			    pushFollow(FOLLOW_bold_markup_in_heading_formattedelement1494);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:328:1: heading_boldcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (tb= heading_boldcontentpart onestar )+ | EOF );
    public final CollectionNode heading_boldcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode tb = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:329:2: ( onestar (tb= heading_boldcontentpart onestar )+ | EOF )
	    int alt54=2;
	    int LA54_0 = input.LA(1);

	    if ( ((LA54_0>=FORCED_END_OF_LINE && LA54_0<=WIKI)||(LA54_0>=POUND && LA54_0<=STAR)||(LA54_0>=PIPE && LA54_0<=FORCED_LINEBREAK)||(LA54_0>=NOWIKI_BLOCK_CLOSE && LA54_0<=79)) ) {
		alt54=1;
	    }
	    else if ( (LA54_0==EOF) ) {
		alt54=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
		    new NoViableAltException("328:1: heading_boldcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (tb= heading_boldcontentpart onestar )+ | EOF );", 54, 0, input);

		throw nvae;
	    }
	    switch (alt54) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:329:4: onestar (tb= heading_boldcontentpart onestar )+
		    {
		    pushFollow(FOLLOW_onestar_in_heading_boldcontent1511);
		    onestar();
		    _fsp--;
		    if (failed) return items;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:329:13: (tb= heading_boldcontentpart onestar )+
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:329:15: tb= heading_boldcontentpart onestar
			    {
			    pushFollow(FOLLOW_heading_boldcontentpart_in_heading_boldcontent1520);
			    tb=heading_boldcontentpart();
			    _fsp--;
			    if (failed) return items;
			    if ( backtracking==0 ) {
			       items.add(tb); 
			    }
			    pushFollow(FOLLOW_onestar_in_heading_boldcontent1525);
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
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:330:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_heading_boldcontent1533); if (failed) return items;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:332:1: heading_italcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (ti= heading_italcontentpart onestar )+ | EOF );
    public final CollectionNode heading_italcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode ti = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:333:2: ( onestar (ti= heading_italcontentpart onestar )+ | EOF )
	    int alt56=2;
	    int LA56_0 = input.LA(1);

	    if ( ((LA56_0>=FORCED_END_OF_LINE && LA56_0<=WIKI)||(LA56_0>=POUND && LA56_0<=STAR)||(LA56_0>=PIPE && LA56_0<=FORCED_LINEBREAK)||(LA56_0>=NOWIKI_BLOCK_CLOSE && LA56_0<=79)) ) {
		alt56=1;
	    }
	    else if ( (LA56_0==EOF) ) {
		alt56=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
		    new NoViableAltException("332:1: heading_italcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (ti= heading_italcontentpart onestar )+ | EOF );", 56, 0, input);

		throw nvae;
	    }
	    switch (alt56) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:333:4: onestar (ti= heading_italcontentpart onestar )+
		    {
		    pushFollow(FOLLOW_onestar_in_heading_italcontent1547);
		    onestar();
		    _fsp--;
		    if (failed) return items;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:333:13: (ti= heading_italcontentpart onestar )+
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:333:15: ti= heading_italcontentpart onestar
			    {
			    pushFollow(FOLLOW_heading_italcontentpart_in_heading_italcontent1556);
			    ti=heading_italcontentpart();
			    _fsp--;
			    if (failed) return items;
			    if ( backtracking==0 ) {
			       items.add(ti); 
			    }
			    pushFollow(FOLLOW_onestar_in_heading_italcontent1561);
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
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:334:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_heading_italcontent1569); if (failed) return items;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:336:1: heading_boldcontentpart returns [ASTNode node = null] : (tf= heading_formattedcontent | ital_markup tb= heading_bolditalcontent ( ital_markup )? );
    public final ASTNode heading_boldcontentpart() throws RecognitionException {
	ASTNode node =	null;

	CollectionNode tf = null;

	CollectionNode tb = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:337:2: (tf= heading_formattedcontent | ital_markup tb= heading_bolditalcontent ( ital_markup )? )
	    int alt58=2;
	    int LA58_0 = input.LA(1);

	    if ( ((LA58_0>=FORCED_END_OF_LINE && LA58_0<=WIKI)||(LA58_0>=POUND && LA58_0<=STAR)||(LA58_0>=PIPE && LA58_0<=FORCED_LINEBREAK)||(LA58_0>=NOWIKI_BLOCK_CLOSE && LA58_0<=79)) ) {
		alt58=1;
	    }
	    else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
		    new NoViableAltException("336:1: heading_boldcontentpart returns [ASTNode node = null] : (tf= heading_formattedcontent | ital_markup tb= heading_bolditalcontent ( ital_markup )? );", 58, 0, input);

		throw nvae;
	    }
	    switch (alt58) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:337:4: tf= heading_formattedcontent
		    {
		    pushFollow(FOLLOW_heading_formattedcontent_in_heading_boldcontentpart1587);
		    tf=heading_formattedcontent();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		      node = tf; 
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:338:4: ital_markup tb= heading_bolditalcontent ( ital_markup )?
		    {
		    pushFollow(FOLLOW_ital_markup_in_heading_boldcontentpart1594);
		    ital_markup();
		    _fsp--;
		    if (failed) return node;
		    pushFollow(FOLLOW_heading_bolditalcontent_in_heading_boldcontentpart1601);
		    tb=heading_bolditalcontent();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		       node = new ItalicTextNode(tb);  
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:338:94: ( ital_markup )?
		    int alt57=2;
		    int LA57_0 = input.LA(1);

		    if ( (LA57_0==ITAL) ) {
			alt57=1;
		    }
		    switch (alt57) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:338:96: ital_markup
			    {
			    pushFollow(FOLLOW_ital_markup_in_heading_boldcontentpart1608);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:340:1: heading_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= heading_bolditalcontent ( bold_markup )? | tf= heading_formattedcontent );
    public final ASTNode heading_italcontentpart() throws RecognitionException {
	ASTNode node =	null;

	CollectionNode tb = null;

	CollectionNode tf = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:341:2: ( bold_markup tb= heading_bolditalcontent ( bold_markup )? | tf= heading_formattedcontent )
	    int alt60=2;
	    int LA60_0 = input.LA(1);

	    if ( (LA60_0==STAR) ) {
		int LA60_1 = input.LA(2);

		if ( (LA60_1==STAR) ) {
		    alt60=1;
		}
		else if ( (LA60_1==EOF||(LA60_1>=FORCED_END_OF_LINE && LA60_1<=POUND)||(LA60_1>=EQUAL && LA60_1<=FORCED_LINEBREAK)||(LA60_1>=NOWIKI_BLOCK_CLOSE && LA60_1<=79)) ) {
		    alt60=2;
		}
		else {
		    if (backtracking>0) {failed=true; return node;}
		    NoViableAltException nvae =
			new NoViableAltException("340:1: heading_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= heading_bolditalcontent ( bold_markup )? | tf= heading_formattedcontent );", 60, 1, input);

		    throw nvae;
		}
	    }
	    else if ( ((LA60_0>=FORCED_END_OF_LINE && LA60_0<=WIKI)||LA60_0==POUND||(LA60_0>=PIPE && LA60_0<=FORCED_LINEBREAK)||(LA60_0>=NOWIKI_BLOCK_CLOSE && LA60_0<=79)) ) {
		alt60=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
		    new NoViableAltException("340:1: heading_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= heading_bolditalcontent ( bold_markup )? | tf= heading_formattedcontent );", 60, 0, input);

		throw nvae;
	    }
	    switch (alt60) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:341:4: bold_markup tb= heading_bolditalcontent ( bold_markup )?
		    {
		    pushFollow(FOLLOW_bold_markup_in_heading_italcontentpart1625);
		    bold_markup();
		    _fsp--;
		    if (failed) return node;
		    pushFollow(FOLLOW_heading_bolditalcontent_in_heading_italcontentpart1632);
		    tb=heading_bolditalcontent();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		      node = new BoldTextNode(tb); 
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:341:90: ( bold_markup )?
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
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:341:92: bold_markup
			    {
			    pushFollow(FOLLOW_bold_markup_in_heading_italcontentpart1639);
			    bold_markup();
			    _fsp--;
			    if (failed) return node;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:342:4: tf= heading_formattedcontent
		    {
		    pushFollow(FOLLOW_heading_formattedcontent_in_heading_italcontentpart1651);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:344:1: heading_bolditalcontent returns [CollectionNode elements = null] : ( onestar (tfc= heading_formattedcontent onestar )? | EOF );
    public final CollectionNode heading_bolditalcontent() throws RecognitionException {
	CollectionNode elements =  null;

	CollectionNode tfc = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:345:2: ( onestar (tfc= heading_formattedcontent onestar )? | EOF )
	    int alt62=2;
	    int LA62_0 = input.LA(1);

	    if ( ((LA62_0>=FORCED_END_OF_LINE && LA62_0<=FORCED_LINEBREAK)||(LA62_0>=NOWIKI_BLOCK_CLOSE && LA62_0<=79)) ) {
		alt62=1;
	    }
	    else if ( (LA62_0==EOF) ) {
		alt62=1;
	    }
	    else {
		if (backtracking>0) {failed=true; return elements;}
		NoViableAltException nvae =
		    new NoViableAltException("344:1: heading_bolditalcontent returns [CollectionNode elements = null] : ( onestar (tfc= heading_formattedcontent onestar )? | EOF );", 62, 0, input);

		throw nvae;
	    }
	    switch (alt62) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:345:4: onestar (tfc= heading_formattedcontent onestar )?
		    {
		    pushFollow(FOLLOW_onestar_in_heading_bolditalcontent1667);
		    onestar();
		    _fsp--;
		    if (failed) return elements;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:345:13: (tfc= heading_formattedcontent onestar )?
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt61=1;
			    }
			    break;
		    }

		    switch (alt61) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:345:15: tfc= heading_formattedcontent onestar
			    {
			    pushFollow(FOLLOW_heading_formattedcontent_in_heading_bolditalcontent1676);
			    tfc=heading_formattedcontent();
			    _fsp--;
			    if (failed) return elements;
			    if ( backtracking==0 ) {
			       elements = tfc; 
			    }
			    pushFollow(FOLLOW_onestar_in_heading_bolditalcontent1681);
			    onestar();
			    _fsp--;
			    if (failed) return elements;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:346:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_heading_bolditalcontent1689); if (failed) return elements;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:348:1: heading_formattedcontent returns [CollectionNode elements = new CollectionNode()] : (tu= heading_unformattedelement )+ ;
    public final CollectionNode heading_formattedcontent() throws RecognitionException {
	CollectionNode elements =  new CollectionNode();

	ASTNode tu = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:349:2: ( (tu= heading_unformattedelement )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:349:4: (tu= heading_unformattedelement )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:349:4: (tu= heading_unformattedelement )+
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
		case BRACE_CLOSE:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
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
		case 79:
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
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:349:6: tu= heading_unformattedelement
		    {
		    pushFollow(FOLLOW_heading_unformattedelement_in_heading_formattedcontent1709);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:351:1: heading_unformattedelement returns [ASTNode content = null] : (tu= heading_unformatted_text | ti= heading_inlineelement );
    public final ASTNode heading_unformattedelement() throws RecognitionException {
	ASTNode content =  null;

	StringBundler tu = null;

	ASTNode ti = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:352:2: (tu= heading_unformatted_text | ti= heading_inlineelement )
	    int alt64=2;
	    int LA64_0 = input.LA(1);

	    if ( ((LA64_0>=FORCED_END_OF_LINE && LA64_0<=WIKI)||(LA64_0>=POUND && LA64_0<=STAR)||(LA64_0>=PIPE && LA64_0<=ITAL)||(LA64_0>=EXTENSION && LA64_0<=FORCED_LINEBREAK)||(LA64_0>=NOWIKI_BLOCK_CLOSE && LA64_0<=79)) ) {
		alt64=1;
	    }
	    else if ( ((LA64_0>=LINK_OPEN && LA64_0<=NOWIKI_OPEN)) ) {
		alt64=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return content;}
		NoViableAltException nvae =
		    new NoViableAltException("351:1: heading_unformattedelement returns [ASTNode content = null] : (tu= heading_unformatted_text | ti= heading_inlineelement );", 64, 0, input);

		throw nvae;
	    }
	    switch (alt64) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:352:4: tu= heading_unformatted_text
		    {
		    pushFollow(FOLLOW_heading_unformatted_text_in_heading_unformattedelement1732);
		    tu=heading_unformatted_text();
		    _fsp--;
		    if (failed) return content;
		    if ( backtracking==0 ) {
		      content = new UnformattedTextNode(tu.toString());
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:353:4: ti= heading_inlineelement
		    {
		    pushFollow(FOLLOW_heading_inlineelement_in_heading_unformattedelement1744);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:355:1: heading_inlineelement returns [ASTNode element = null] : (l= link | i= image | nwi= nowiki_inline );
    public final ASTNode heading_inlineelement() throws RecognitionException {
	ASTNode element =  null;

	LinkNode l = null;

	ImageNode i = null;

	NoWikiSectionNode nwi = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:356:2: (l= link | i= image | nwi= nowiki_inline )
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
		    new NoViableAltException("355:1: heading_inlineelement returns [ASTNode element = null] : (l= link | i= image | nwi= nowiki_inline );", 65, 0, input);

		throw nvae;
	    }

	    switch (alt65) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:356:4: l= link
		    {
		    pushFollow(FOLLOW_link_in_heading_inlineelement1764);
		    l=link();
		    _fsp--;
		    if (failed) return element;
		    if ( backtracking==0 ) {
		      element = l; 
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:357:4: i= image
		    {
		    pushFollow(FOLLOW_image_in_heading_inlineelement1774);
		    i=image();
		    _fsp--;
		    if (failed) return element;
		    if ( backtracking==0 ) {
		      element = i; 
		    }

		    }
		    break;
		case 3 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:358:4: nwi= nowiki_inline
		    {
		    pushFollow(FOLLOW_nowiki_inline_in_heading_inlineelement1785);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:361:1: heading_unformatted_text returns [StringBundler text = new StringBundler()] : (c=~ ( LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EQUAL | ESCAPE | NEWLINE | EOF ) )+ ;
    public final StringBundler heading_unformatted_text() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:362:2: ( (c=~ ( LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EQUAL | ESCAPE | NEWLINE | EOF ) )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:362:4: (c=~ ( LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EQUAL | ESCAPE | NEWLINE | EOF ) )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:362:4: (c=~ ( LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EQUAL | ESCAPE | NEWLINE | EOF ) )+
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
		case BRACE_CLOSE:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
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
		case 79:
		    {
		    alt66=1;
		    }
		    break;

		}

		switch (alt66) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:362:6: c=~ ( LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EQUAL | ESCAPE | NEWLINE | EOF )
		    {
		    c=(Token)input.LT(1);
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=STAR)||(input.LA(1)>=PIPE && input.LA(1)<=ITAL)||(input.LA(1)>=EXTENSION && input.LA(1)<=FORCED_LINEBREAK)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_heading_unformatted_text1808);    throw mse;
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

    protected static class list_scope {
	BaseListNode currentParent;
	ListNode root;
	Stack<ItemNode> parents;
	int lastLevel = 1;
    }
    protected Stack list_stack = new Stack();


    // $ANTLR start list
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:368:1: list returns [ListNode listNode = null] : (elem= list_elems )+ ( end_of_list )? ;
    public final ListNode list() throws RecognitionException {
	list_stack.push(new list_scope());
	ListNode listNode =  null;


			((list_scope)list_stack.peek()).root = new ListNode();

			if (input.LA(1) == POUND) {
				((list_scope)list_stack.peek()).currentParent = new OrderedListNode(((list_scope)list_stack.peek()).root);
			}
			else {
				((list_scope)list_stack.peek()).currentParent = new UnorderedListNode(((list_scope)list_stack.peek()).root);
			}

			((list_scope)list_stack.peek()).root.addChildASTNode(((list_scope)list_stack.peek()).currentParent);

			((list_scope)list_stack.peek()).parents = new Stack<ItemNode>();
		
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:392:2: ( (elem= list_elems )+ ( end_of_list )? )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:392:4: (elem= list_elems )+ ( end_of_list )?
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:392:4: (elem= list_elems )+
	    int cnt67=0;
	    loop67:
	    do {
		int alt67=2;
		int LA67_0 = input.LA(1);

		if ( (LA67_0==POUND) ) {
		    alt67=1;
		}
		else if ( (LA67_0==STAR) ) {
		    alt67=1;
		}


		switch (alt67) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:392:6: elem= list_elems
		    {
		    pushFollow(FOLLOW_list_elems_in_list1882);
		    list_elems();
		    _fsp--;
		    if (failed) return listNode;

		    }
		    break;

		default :
		    if ( cnt67 >= 1 ) break loop67;
		    if (backtracking>0) {failed=true; return listNode;}
			EarlyExitException eee =
			    new EarlyExitException(67, input);
			throw eee;
		}
		cnt67++;
	    } while (true);

	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:392:28: ( end_of_list )?
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
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:392:30: end_of_list
		    {
		    pushFollow(FOLLOW_end_of_list_in_list1890);
		    end_of_list();
		    _fsp--;
		    if (failed) return listNode;

		    }
		    break;

	    }


	    }

	    if ( backtracking==0 ) {

			listNode = ((list_scope)list_stack.peek()).root;
		
	    }
	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	    list_stack.pop();
	}
	return listNode;
    }
    // $ANTLR end list


    // $ANTLR start list_elems
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:395:1: list_elems : (om= list_ordelem_markup elem= list_elem | um= list_unordelem_markup elem= list_elem );
    public final void list_elems() throws RecognitionException {
	CountLevel_stack.push(new CountLevel_scope());

	list_ordelem_markup_return om = null;

	CollectionNode elem = null;

	list_unordelem_markup_return um = null;



			((CountLevel_scope)CountLevel_stack.peek()).level = 0;
		
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:400:2: (om= list_ordelem_markup elem= list_elem | um= list_unordelem_markup elem= list_elem )
	    int alt69=2;
	    int LA69_0 = input.LA(1);

	    if ( (LA69_0==POUND) ) {
		alt69=1;
	    }
	    else if ( (LA69_0==STAR) ) {
		alt69=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
		    new NoViableAltException("395:1: list_elems : (om= list_ordelem_markup elem= list_elem | um= list_unordelem_markup elem= list_elem );", 69, 0, input);

		throw nvae;
	    }
	    switch (alt69) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:400:4: om= list_ordelem_markup elem= list_elem
		    {
		    pushFollow(FOLLOW_list_ordelem_markup_in_list_elems1920);
		    om=list_ordelem_markup();
		    _fsp--;
		    if (failed) return ;
		    if ( backtracking==0 ) {
		      ++((CountLevel_scope)CountLevel_stack.peek()).level;((CountLevel_scope)CountLevel_stack.peek()).currentMarkup = input.toString(om.start,om.stop);((CountLevel_scope)CountLevel_stack.peek()).groups += input.toString(om.start,om.stop);
		    }
		    pushFollow(FOLLOW_list_elem_in_list_elems1932);
		    elem=list_elem();
		    _fsp--;
		    if (failed) return ;
		    if ( backtracking==0 ) {
			

							Stack<ItemNode> parents = ((list_scope)list_stack.peek()).parents;

							ItemNode top = parents.isEmpty()?null:parents.peek();

							BaseParentableNode baseParentableNode = ((list_scope)list_stack.peek()).currentParent;

							if (top == null) {
								OrderedListItemNode node = new OrderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, baseParentableNode, elem);
								baseParentableNode.addChildASTNode(node);

								parents.push(node);

							}
							else if (((CountLevel_scope)CountLevel_stack.peek()).level > ((list_scope)list_stack.peek()).lastLevel) {		
								OrderedListNode orderedListNode = new OrderedListNode(top);

								OrderedListItemNode node = new OrderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, orderedListNode, elem);
								orderedListNode.addChildASTNode(node);

								top.addChildASTNode(orderedListNode);

								parents.push(node);
							} 
							else if (((CountLevel_scope)CountLevel_stack.peek()).level < ((list_scope)list_stack.peek()).lastLevel) {
								ItemNode in = parents.peek();

								while (in.getLevel() > ((CountLevel_scope)CountLevel_stack.peek()).level) {
									in = parents.pop();
									--((list_scope)list_stack.peek()).lastLevel;
								}

								top = in;

								baseParentableNode = top.getBaseParentableNode();

								OrderedListItemNode node = new OrderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, baseParentableNode, elem);

								if (baseParentableNode instanceof UnorderedListItemNode) {
									buildAndComposeListNode(baseParentableNode, node, true);
								}
								else if (baseParentableNode instanceof UnorderedListNode) {
									baseParentableNode = ((UnorderedListNode)baseParentableNode).getBaseParentableNode();

									buildAndComposeListNode(baseParentableNode, node, true);
								}
								else if (baseParentableNode instanceof OrderedListNode && top instanceof UnorderedListItemNode) {
									baseParentableNode = ((OrderedListNode)baseParentableNode).getBaseParentableNode();

									buildAndComposeListNode(baseParentableNode, node, true);
								}
								else {
									baseParentableNode.addChildASTNode(node);
								}

								parents.push(node);

							}
							else {
								baseParentableNode = top.getBaseParentableNode();

								OrderedListItemNode node = new OrderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, baseParentableNode, elem);

								if (baseParentableNode instanceof UnorderedListItemNode) {
									buildAndComposeListNode(baseParentableNode, node, true);
								} 
								else if (baseParentableNode instanceof UnorderedListNode) {
									baseParentableNode = ((UnorderedListNode)baseParentableNode).getBaseParentableNode();

									buildAndComposeListNode(baseParentableNode, node, true);
								} 
								else if (baseParentableNode instanceof OrderedListNode && top instanceof UnorderedListItemNode) {
									baseParentableNode = ((OrderedListNode)baseParentableNode).getBaseParentableNode();

									buildAndComposeListNode(baseParentableNode, node, true);
								} 
								else {
									baseParentableNode.addChildASTNode(node);
								}

								parents.pop();
								parents.push(node);
							}

							((list_scope)list_stack.peek()).lastLevel = ((CountLevel_scope)CountLevel_stack.peek()).level;
						
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:488:4: um= list_unordelem_markup elem= list_elem
		    {
		    pushFollow(FOLLOW_list_unordelem_markup_in_list_elems1943);
		    um=list_unordelem_markup();
		    _fsp--;
		    if (failed) return ;
		    if ( backtracking==0 ) {
		      ++((CountLevel_scope)CountLevel_stack.peek()).level; ((CountLevel_scope)CountLevel_stack.peek()).currentMarkup = input.toString(um.start,um.stop);((CountLevel_scope)CountLevel_stack.peek()).groups += input.toString(um.start,um.stop);
		    }
		    pushFollow(FOLLOW_list_elem_in_list_elems1955);
		    elem=list_elem();
		    _fsp--;
		    if (failed) return ;
		    if ( backtracking==0 ) {


							Stack<ItemNode> parents = ((list_scope)list_stack.peek()).parents;

							ItemNode top = parents.isEmpty()?null:parents.peek();

							BaseParentableNode baseParentableNode = ((list_scope)list_stack.peek()).currentParent;

							if (top == null) {
								UnorderedListItemNode node = new UnorderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, baseParentableNode, elem);
								baseParentableNode.addChildASTNode(node);

								parents.push(node);

							} 
							else if (((CountLevel_scope)CountLevel_stack.peek()).level > ((list_scope)list_stack.peek()).lastLevel) {
								UnorderedListNode unorderedListNode = new UnorderedListNode(top);

								UnorderedListItemNode node = new UnorderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, unorderedListNode, elem);
								unorderedListNode.addChildASTNode(node);

								top.addChildASTNode(unorderedListNode);

								parents.push(node);

							} 
							else if (((CountLevel_scope)CountLevel_stack.peek()).level < ((list_scope)list_stack.peek()).lastLevel) {
								ItemNode in = parents.peek();

								while (in.getLevel() > ((CountLevel_scope)CountLevel_stack.peek()).level) {
									in = parents.pop();
									--((list_scope)list_stack.peek()).lastLevel;
								}

								top = in;

								baseParentableNode = top.getBaseParentableNode();

								UnorderedListItemNode node = new UnorderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, baseParentableNode, elem);

								if (baseParentableNode instanceof OrderedListItemNode) {
									buildAndComposeListNode(baseParentableNode, node, false);
								} 
								else if (baseParentableNode instanceof OrderedListNode) {
									baseParentableNode = ((OrderedListNode)baseParentableNode).getBaseParentableNode();

									buildAndComposeListNode(baseParentableNode, node, false);
								}
								else if (baseParentableNode instanceof UnorderedListNode && top instanceof OrderedListItemNode) {
									baseParentableNode = ((UnorderedListNode)baseParentableNode).getBaseParentableNode();

									buildAndComposeListNode(baseParentableNode, node, false);
								} 
								else {
									baseParentableNode.addChildASTNode(node);
								}

								parents.push(node);

							} 
							else {
								baseParentableNode = top.getBaseParentableNode();

								UnorderedListItemNode node = new UnorderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, baseParentableNode, elem);

								if (baseParentableNode instanceof OrderedListItemNode) {
									buildAndComposeListNode(baseParentableNode, node, false);
								} 
								else if (baseParentableNode instanceof OrderedListNode ) {
									baseParentableNode = ((OrderedListNode)baseParentableNode).getBaseParentableNode();

									buildAndComposeListNode(baseParentableNode, node, false);
								} 
								else if (baseParentableNode instanceof UnorderedListNode && top instanceof OrderedListItemNode) {
									baseParentableNode = ((UnorderedListNode)baseParentableNode).getBaseParentableNode();

									buildAndComposeListNode(baseParentableNode, node, false);
								} 
								else {
									baseParentableNode.addChildASTNode(node);
								}

								parents.pop();
								parents.push(node);
							}

							((list_scope)list_stack.peek()).lastLevel = ((CountLevel_scope)CountLevel_stack.peek()).level;
						
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
	    CountLevel_stack.pop();

	}
	return ;
    }
    // $ANTLR end list_elems


    // $ANTLR start list_elem
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:578:1: list_elem returns [CollectionNode items = null] : (m= list_elem_markup )* c= list_elemcontent list_elemseparator ;
    public final CollectionNode list_elem() throws RecognitionException {
	CollectionNode items =	null;

	list_elem_markup_return m = null;

	CollectionNode c = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:579:2: ( (m= list_elem_markup )* c= list_elemcontent list_elemseparator )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:579:4: (m= list_elem_markup )* c= list_elemcontent list_elemseparator
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:579:4: (m= list_elem_markup )*
	    loop70:
	    do {
		int alt70=2;
		int LA70_0 = input.LA(1);

		if ( (LA70_0==STAR) ) {
		    alt70=1;
		}
		else if ( (LA70_0==POUND) ) {
		    alt70=1;
		}


		switch (alt70) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:579:6: m= list_elem_markup
		    {
		    pushFollow(FOLLOW_list_elem_markup_in_list_elem1978);
		    m=list_elem_markup();
		    _fsp--;
		    if (failed) return items;
		    if ( backtracking==0 ) {

						     ++((CountLevel_scope)CountLevel_stack.peek()).level;
						     if (!input.toString(m.start,m.stop).equals(((CountLevel_scope)CountLevel_stack.peek()).currentMarkup)) {
									((CountLevel_scope)CountLevel_stack.peek()).groups+= GROUPING_SEPARATOR;
						     }
						     ((CountLevel_scope)CountLevel_stack.peek()).groups+= input.toString(m.start,m.stop);
						     ((CountLevel_scope)CountLevel_stack.peek()).currentMarkup = input.toString(m.start,m.stop);
						  
		    }

		    }
		    break;

		default :
		    break loop70;
		}
	    } while (true);

	    pushFollow(FOLLOW_list_elemcontent_in_list_elem1989);
	    c=list_elemcontent();
	    _fsp--;
	    if (failed) return items;
	    if ( backtracking==0 ) {
	      items = c; 
	    }
	    pushFollow(FOLLOW_list_elemseparator_in_list_elem1994);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:588:1: list_elem_markup : ( list_ordelem_markup | list_unordelem_markup );
    public final list_elem_markup_return list_elem_markup() throws RecognitionException {
	list_elem_markup_return retval = new list_elem_markup_return();
	retval.start = input.LT(1);

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:589:2: ( list_ordelem_markup | list_unordelem_markup )
	    int alt71=2;
	    int LA71_0 = input.LA(1);

	    if ( (LA71_0==POUND) ) {
		alt71=1;
	    }
	    else if ( (LA71_0==STAR) ) {
		alt71=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return retval;}
		NoViableAltException nvae =
		    new NoViableAltException("588:1: list_elem_markup : ( list_ordelem_markup | list_unordelem_markup );", 71, 0, input);

		throw nvae;
	    }
	    switch (alt71) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:589:4: list_ordelem_markup
		    {
		    pushFollow(FOLLOW_list_ordelem_markup_in_list_elem_markup2004);
		    list_ordelem_markup();
		    _fsp--;
		    if (failed) return retval;

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:590:4: list_unordelem_markup
		    {
		    pushFollow(FOLLOW_list_unordelem_markup_in_list_elem_markup2009);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:592:1: list_elemcontent returns [CollectionNode items = new CollectionNode()] : onestar (part= list_elemcontentpart onestar )* ;
    public final CollectionNode list_elemcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode part = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:593:2: ( onestar (part= list_elemcontentpart onestar )* )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:593:4: onestar (part= list_elemcontentpart onestar )*
	    {
	    pushFollow(FOLLOW_onestar_in_list_elemcontent2023);
	    onestar();
	    _fsp--;
	    if (failed) return items;
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:593:13: (part= list_elemcontentpart onestar )*
	    loop72:
	    do {
		int alt72=2;
		int LA72_0 = input.LA(1);

		if ( ((LA72_0>=FORCED_END_OF_LINE && LA72_0<=WIKI)||(LA72_0>=POUND && LA72_0<=79)) ) {
		    alt72=1;
		}


		switch (alt72) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:593:15: part= list_elemcontentpart onestar
		    {
		    pushFollow(FOLLOW_list_elemcontentpart_in_list_elemcontent2032);
		    part=list_elemcontentpart();
		    _fsp--;
		    if (failed) return items;
		    if ( backtracking==0 ) {
		       items.add(part); 
		    }
		    pushFollow(FOLLOW_onestar_in_list_elemcontent2037);
		    onestar();
		    _fsp--;
		    if (failed) return items;

		    }
		    break;

		default :
		    break loop72;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:595:1: list_elemcontentpart returns [ASTNode node = null] : (tuf= text_unformattedelement | tf= list_formatted_elem );
    public final ASTNode list_elemcontentpart() throws RecognitionException {
	ASTNode node =	null;

	ASTNode tuf = null;

	CollectionNode tf = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:596:2: (tuf= text_unformattedelement | tf= list_formatted_elem )
	    int alt73=2;
	    int LA73_0 = input.LA(1);

	    if ( ((LA73_0>=FORCED_END_OF_LINE && LA73_0<=WIKI)||LA73_0==POUND||(LA73_0>=EQUAL && LA73_0<=PIPE)||(LA73_0>=LINK_OPEN && LA73_0<=79)) ) {
		alt73=1;
	    }
	    else if ( (LA73_0==STAR||LA73_0==ITAL) ) {
		alt73=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
		    new NoViableAltException("595:1: list_elemcontentpart returns [ASTNode node = null] : (tuf= text_unformattedelement | tf= list_formatted_elem );", 73, 0, input);

		throw nvae;
	    }
	    switch (alt73) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:596:4: tuf= text_unformattedelement
		    {
		    pushFollow(FOLLOW_text_unformattedelement_in_list_elemcontentpart2058);
		    tuf=text_unformattedelement();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {

						if (tuf instanceof CollectionNode)
							node = new UnformattedTextNode(tuf);
						else
							node = tuf;
						
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:602:4: tf= list_formatted_elem
		    {
		    pushFollow(FOLLOW_list_formatted_elem_in_list_elemcontentpart2069);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:604:1: list_formatted_elem returns [CollectionNode contents = new CollectionNode()] : ( bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )? | ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )? );
    public final CollectionNode list_formatted_elem() throws RecognitionException {
	CollectionNode contents =  new CollectionNode();

	ASTNode boldContents = null;

	ASTNode italContents = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:605:2: ( bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )? | ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )? )
	    int alt78=2;
	    int LA78_0 = input.LA(1);

	    if ( (LA78_0==STAR) ) {
		alt78=1;
	    }
	    else if ( (LA78_0==ITAL) ) {
		alt78=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return contents;}
		NoViableAltException nvae =
		    new NoViableAltException("604:1: list_formatted_elem returns [CollectionNode contents = new CollectionNode()] : ( bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )? | ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )? );", 78, 0, input);

		throw nvae;
	    }
	    switch (alt78) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:605:4: bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )?
		    {
		    pushFollow(FOLLOW_bold_markup_in_list_formatted_elem2085);
		    bold_markup();
		    _fsp--;
		    if (failed) return contents;
		    pushFollow(FOLLOW_onestar_in_list_formatted_elem2088);
		    onestar();
		    _fsp--;
		    if (failed) return contents;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:605:26: (boldContents= list_boldcontentpart onestar )*
		    loop74:
		    do {
			int alt74=2;
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt74=1;
			    }
			    break;
			case FORCED_LINEBREAK:
			    {
			    alt74=1;
			    }
			    break;
			case ESCAPE:
			    {
			    alt74=1;
			    }
			    break;
			case LINK_OPEN:
			    {
			    alt74=1;
			    }
			    break;
			case IMAGE_OPEN:
			    {
			    alt74=1;
			    }
			    break;
			case EXTENSION:
			    {
			    alt74=1;
			    }
			    break;
			case NOWIKI_OPEN:
			    {
			    alt74=1;
			    }
			    break;
			case ITAL:
			    {
			    alt74=1;
			    }
			    break;

			}

			switch (alt74) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:605:28: boldContents= list_boldcontentpart onestar
			    {
			    pushFollow(FOLLOW_list_boldcontentpart_in_list_formatted_elem2097);
			    boldContents=list_boldcontentpart();
			    _fsp--;
			    if (failed) return contents;
			    if ( backtracking==0 ) {

									BoldTextNode add = null;
									if (boldContents instanceof CollectionNode){
									     add = new BoldTextNode(boldContents);
									}
									else {
									    CollectionNode c = new CollectionNode();
									    c.add(boldContents);
									    add = new BoldTextNode(c);
									}
									contents.add(add);
									
			    }
			    pushFollow(FOLLOW_onestar_in_list_formatted_elem2106);
			    onestar();
			    _fsp--;
			    if (failed) return contents;

			    }
			    break;

			default :
			    break loop74;
			}
		    } while (true);

		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:618:3: ( bold_markup )?
		    int alt75=2;
		    int LA75_0 = input.LA(1);

		    if ( (LA75_0==STAR) ) {
			int LA75_1 = input.LA(2);

			if ( (LA75_1==STAR) ) {
			    alt75=1;
			}
		    }
		    switch (alt75) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:618:5: bold_markup
			    {
			    pushFollow(FOLLOW_bold_markup_in_list_formatted_elem2115);
			    bold_markup();
			    _fsp--;
			    if (failed) return contents;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:619:4: ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )?
		    {
		    pushFollow(FOLLOW_ital_markup_in_list_formatted_elem2123);
		    ital_markup();
		    _fsp--;
		    if (failed) return contents;
		    pushFollow(FOLLOW_onestar_in_list_formatted_elem2128);
		    onestar();
		    _fsp--;
		    if (failed) return contents;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:619:28: (italContents= list_italcontentpart onestar )*
		    loop76:
		    do {
			int alt76=2;
			switch ( input.LA(1) ) {
			case STAR:
			    {
			    alt76=1;
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt76=1;
			    }
			    break;
			case FORCED_LINEBREAK:
			    {
			    alt76=1;
			    }
			    break;
			case ESCAPE:
			    {
			    alt76=1;
			    }
			    break;
			case LINK_OPEN:
			    {
			    alt76=1;
			    }
			    break;
			case IMAGE_OPEN:
			    {
			    alt76=1;
			    }
			    break;
			case EXTENSION:
			    {
			    alt76=1;
			    }
			    break;
			case NOWIKI_OPEN:
			    {
			    alt76=1;
			    }
			    break;

			}

			switch (alt76) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:619:30: italContents= list_italcontentpart onestar
			    {
			    pushFollow(FOLLOW_list_italcontentpart_in_list_formatted_elem2137);
			    italContents=list_italcontentpart();
			    _fsp--;
			    if (failed) return contents;
			    if ( backtracking==0 ) {

									ItalicTextNode add = null;
									if (italContents instanceof CollectionNode){
									    add = new ItalicTextNode(italContents);
									}
									else {
									      CollectionNode c = new CollectionNode();
									      c.add(italContents);
									      add = new ItalicTextNode(c);
									}
									contents.add(add);
									
			    }
			    pushFollow(FOLLOW_onestar_in_list_formatted_elem2146);
			    onestar();
			    _fsp--;
			    if (failed) return contents;

			    }
			    break;

			default :
			    break loop76;
			}
		    } while (true);

		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:631:3: ( ital_markup )?
		    int alt77=2;
		    int LA77_0 = input.LA(1);

		    if ( (LA77_0==ITAL) ) {
			alt77=1;
		    }
		    switch (alt77) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:631:5: ital_markup
			    {
			    pushFollow(FOLLOW_ital_markup_in_list_formatted_elem2155);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:634:1: list_boldcontentpart returns [ASTNode contents = null] : ( ital_markup c= list_bolditalcontent ( ital_markup )? | (t= text_unformattedelement )+ );
    public final ASTNode list_boldcontentpart() throws RecognitionException {
	list_boldcontentpart_stack.push(new list_boldcontentpart_scope());
	ASTNode contents =  null;

	ASTNode c = null;

	ASTNode t = null;



		((list_boldcontentpart_scope)list_boldcontentpart_stack.peek()).elements = new ArrayList<ASTNode>();

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:641:2: ( ital_markup c= list_bolditalcontent ( ital_markup )? | (t= text_unformattedelement )+ )
	    int alt81=2;
	    int LA81_0 = input.LA(1);

	    if ( (LA81_0==ITAL) ) {
		alt81=1;
	    }
	    else if ( ((LA81_0>=FORCED_END_OF_LINE && LA81_0<=WIKI)||LA81_0==POUND||(LA81_0>=EQUAL && LA81_0<=PIPE)||(LA81_0>=LINK_OPEN && LA81_0<=79)) ) {
		alt81=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return contents;}
		NoViableAltException nvae =
		    new NoViableAltException("634:1: list_boldcontentpart returns [ASTNode contents = null] : ( ital_markup c= list_bolditalcontent ( ital_markup )? | (t= text_unformattedelement )+ );", 81, 0, input);

		throw nvae;
	    }
	    switch (alt81) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:641:4: ital_markup c= list_bolditalcontent ( ital_markup )?
		    {
		    pushFollow(FOLLOW_ital_markup_in_list_boldcontentpart2181);
		    ital_markup();
		    _fsp--;
		    if (failed) return contents;
		    pushFollow(FOLLOW_list_bolditalcontent_in_list_boldcontentpart2188);
		    c=list_bolditalcontent();
		    _fsp--;
		    if (failed) return contents;
		    if ( backtracking==0 ) {
		      contents = new ItalicTextNode(c);
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:641:86: ( ital_markup )?
		    int alt79=2;
		    int LA79_0 = input.LA(1);

		    if ( (LA79_0==ITAL) ) {
			alt79=1;
		    }
		    switch (alt79) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:641:88: ital_markup
			    {
			    pushFollow(FOLLOW_ital_markup_in_list_boldcontentpart2195);
			    ital_markup();
			    _fsp--;
			    if (failed) return contents;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:642:4: (t= text_unformattedelement )+
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:642:4: (t= text_unformattedelement )+
		    int cnt80=0;
		    loop80:
		    do {
			int alt80=2;
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt80=1;
			    }
			    break;
			case FORCED_LINEBREAK:
			    {
			    alt80=1;
			    }
			    break;
			case ESCAPE:
			    {
			    alt80=1;
			    }
			    break;
			case LINK_OPEN:
			    {
			    alt80=1;
			    }
			    break;
			case IMAGE_OPEN:
			    {
			    alt80=1;
			    }
			    break;
			case EXTENSION:
			    {
			    alt80=1;
			    }
			    break;
			case NOWIKI_OPEN:
			    {
			    alt80=1;
			    }
			    break;

			}

			switch (alt80) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:642:6: t= text_unformattedelement
			    {
			    pushFollow(FOLLOW_text_unformattedelement_in_list_boldcontentpart2209);
			    t=text_unformattedelement();
			    _fsp--;
			    if (failed) return contents;
			    if ( backtracking==0 ) {
			       ((list_boldcontentpart_scope)list_boldcontentpart_stack.peek()).elements.add(t); 
			    }

			    }
			    break;

			default :
			    if ( cnt80 >= 1 ) break loop80;
			    if (backtracking>0) {failed=true; return contents;}
				EarlyExitException eee =
				    new EarlyExitException(80, input);
				throw eee;
			}
			cnt80++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:646:1: list_bolditalcontent returns [ASTNode text = null] : (t= text_unformattedelement )+ ;
    public final ASTNode list_bolditalcontent() throws RecognitionException {
	ASTNode text =	null;

	ASTNode t = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:647:2: ( (t= text_unformattedelement )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:647:4: (t= text_unformattedelement )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:647:4: (t= text_unformattedelement )+
	    int cnt82=0;
	    loop82:
	    do {
		int alt82=2;
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
		case BRACE_CLOSE:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
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
		case 79:
		    {
		    alt82=1;
		    }
		    break;
		case FORCED_LINEBREAK:
		    {
		    alt82=1;
		    }
		    break;
		case ESCAPE:
		    {
		    alt82=1;
		    }
		    break;
		case LINK_OPEN:
		    {
		    alt82=1;
		    }
		    break;
		case IMAGE_OPEN:
		    {
		    alt82=1;
		    }
		    break;
		case EXTENSION:
		    {
		    alt82=1;
		    }
		    break;
		case NOWIKI_OPEN:
		    {
		    alt82=1;
		    }
		    break;

		}

		switch (alt82) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:647:6: t= text_unformattedelement
		    {
		    pushFollow(FOLLOW_text_unformattedelement_in_list_bolditalcontent2240);
		    t=text_unformattedelement();
		    _fsp--;
		    if (failed) return text;
		    if ( backtracking==0 ) {
		       text = t; 
		    }

		    }
		    break;

		default :
		    if ( cnt82 >= 1 ) break loop82;
		    if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
			    new EarlyExitException(82, input);
			throw eee;
		}
		cnt82++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:650:1: list_italcontentpart returns [ASTNode contents = null] : ( bold_markup c= list_bolditalcontent ( bold_markup )? | (t= text_unformattedelement )+ );
    public final ASTNode list_italcontentpart() throws RecognitionException {
	list_italcontentpart_stack.push(new list_italcontentpart_scope());
	ASTNode contents =  null;

	ASTNode c = null;

	ASTNode t = null;



		((list_italcontentpart_scope)list_italcontentpart_stack.peek()).elements = new ArrayList<ASTNode>();

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:657:2: ( bold_markup c= list_bolditalcontent ( bold_markup )? | (t= text_unformattedelement )+ )
	    int alt85=2;
	    int LA85_0 = input.LA(1);

	    if ( (LA85_0==STAR) ) {
		alt85=1;
	    }
	    else if ( ((LA85_0>=FORCED_END_OF_LINE && LA85_0<=WIKI)||LA85_0==POUND||(LA85_0>=EQUAL && LA85_0<=PIPE)||(LA85_0>=LINK_OPEN && LA85_0<=79)) ) {
		alt85=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return contents;}
		NoViableAltException nvae =
		    new NoViableAltException("650:1: list_italcontentpart returns [ASTNode contents = null] : ( bold_markup c= list_bolditalcontent ( bold_markup )? | (t= text_unformattedelement )+ );", 85, 0, input);

		throw nvae;
	    }
	    switch (alt85) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:657:4: bold_markup c= list_bolditalcontent ( bold_markup )?
		    {
		    pushFollow(FOLLOW_bold_markup_in_list_italcontentpart2268);
		    bold_markup();
		    _fsp--;
		    if (failed) return contents;
		    pushFollow(FOLLOW_list_bolditalcontent_in_list_italcontentpart2275);
		    c=list_bolditalcontent();
		    _fsp--;
		    if (failed) return contents;
		    if ( backtracking==0 ) {
		       contents = new BoldTextNode(c); 
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:657:86: ( bold_markup )?
		    int alt83=2;
		    int LA83_0 = input.LA(1);

		    if ( (LA83_0==STAR) ) {
			int LA83_1 = input.LA(2);

			if ( (LA83_1==STAR) ) {
			    alt83=1;
			}
		    }
		    switch (alt83) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:657:88: bold_markup
			    {
			    pushFollow(FOLLOW_bold_markup_in_list_italcontentpart2282);
			    bold_markup();
			    _fsp--;
			    if (failed) return contents;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:658:4: (t= text_unformattedelement )+
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:658:4: (t= text_unformattedelement )+
		    int cnt84=0;
		    loop84:
		    do {
			int alt84=2;
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt84=1;
			    }
			    break;
			case FORCED_LINEBREAK:
			    {
			    alt84=1;
			    }
			    break;
			case ESCAPE:
			    {
			    alt84=1;
			    }
			    break;
			case LINK_OPEN:
			    {
			    alt84=1;
			    }
			    break;
			case IMAGE_OPEN:
			    {
			    alt84=1;
			    }
			    break;
			case EXTENSION:
			    {
			    alt84=1;
			    }
			    break;
			case NOWIKI_OPEN:
			    {
			    alt84=1;
			    }
			    break;

			}

			switch (alt84) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:658:6: t= text_unformattedelement
			    {
			    pushFollow(FOLLOW_text_unformattedelement_in_list_italcontentpart2296);
			    t=text_unformattedelement();
			    _fsp--;
			    if (failed) return contents;
			    if ( backtracking==0 ) {
			       ((list_italcontentpart_scope)list_italcontentpart_stack.peek()).elements.add(t); 
			    }

			    }
			    break;

			default :
			    if ( cnt84 >= 1 ) break loop84;
			    if (backtracking>0) {failed=true; return contents;}
				EarlyExitException eee =
				    new EarlyExitException(84, input);
				throw eee;
			}
			cnt84++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:662:1: table returns [TableNode table = new TableNode()] : (tr= table_row )+ ;
    public final TableNode table() throws RecognitionException {
	TableNode table =  new TableNode();

	CollectionNode tr = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:663:2: ( (tr= table_row )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:663:4: (tr= table_row )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:663:4: (tr= table_row )+
	    int cnt86=0;
	    loop86:
	    do {
		int alt86=2;
		int LA86_0 = input.LA(1);

		if ( (LA86_0==PIPE) ) {
		    alt86=1;
		}


		switch (alt86) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:663:6: tr= table_row
		    {
		    pushFollow(FOLLOW_table_row_in_table2324);
		    tr=table_row();
		    _fsp--;
		    if (failed) return table;
		    if ( backtracking==0 ) {
		      table.addChildASTNode(tr);
		    }

		    }
		    break;

		default :
		    if ( cnt86 >= 1 ) break loop86;
		    if (backtracking>0) {failed=true; return table;}
			EarlyExitException eee =
			    new EarlyExitException(86, input);
			throw eee;
		}
		cnt86++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:665:1: table_row returns [CollectionNode row = new CollectionNode()] : (tc= table_cell )+ table_rowseparator ;
    public final CollectionNode table_row() throws RecognitionException {
	CollectionNode row =  new CollectionNode();

	TableCellNode tc = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:666:2: ( (tc= table_cell )+ table_rowseparator )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:666:4: (tc= table_cell )+ table_rowseparator
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:666:4: (tc= table_cell )+
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
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:666:6: tc= table_cell
		    {
		    pushFollow(FOLLOW_table_cell_in_table_row2350);
		    tc=table_cell();
		    _fsp--;
		    if (failed) return row;
		    if ( backtracking==0 ) {
		       row.add(tc); 
		    }

		    }
		    break;

		default :
		    if ( cnt87 >= 1 ) break loop87;
		    if (backtracking>0) {failed=true; return row;}
			EarlyExitException eee =
			    new EarlyExitException(87, input);
			throw eee;
		}
		cnt87++;
	    } while (true);

	    pushFollow(FOLLOW_table_rowseparator_in_table_row2358);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:668:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );
    public final TableCellNode table_cell() throws RecognitionException {
	TableCellNode cell =  null;

	TableHeaderNode th = null;

	TableDataNode tc = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:669:2: ({...}?th= table_headercell | tc= table_normalcell )
	    int alt88=2;
	    int LA88_0 = input.LA(1);

	    if ( (LA88_0==PIPE) ) {
		int LA88_1 = input.LA(2);

		if ( (LA88_1==EQUAL) ) {
		    int LA88_2 = input.LA(3);

		    if ( ( input.LA(2) == EQUAL ) ) {
			alt88=1;
		    }
		    else if ( (true) ) {
			alt88=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return cell;}
			NoViableAltException nvae =
			    new NoViableAltException("668:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );", 88, 2, input);

			throw nvae;
		    }
		}
		else if ( (LA88_1==EOF||(LA88_1>=FORCED_END_OF_LINE && LA88_1<=STAR)||(LA88_1>=PIPE && LA88_1<=79)) ) {
		    alt88=2;
		}
		else {
		    if (backtracking>0) {failed=true; return cell;}
		    NoViableAltException nvae =
			new NoViableAltException("668:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );", 88, 1, input);

		    throw nvae;
		}
	    }
	    else {
		if (backtracking>0) {failed=true; return cell;}
		NoViableAltException nvae =
		    new NoViableAltException("668:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );", 88, 0, input);

		throw nvae;
	    }
	    switch (alt88) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:669:4: {...}?th= table_headercell
		    {
		    if ( !( input.LA(2) == EQUAL ) ) {
			if (backtracking>0) {failed=true; return cell;}
			throw new FailedPredicateException(input, "table_cell", " input.LA(2) == EQUAL ");
		    }
		    pushFollow(FOLLOW_table_headercell_in_table_cell2379);
		    th=table_headercell();
		    _fsp--;
		    if (failed) return cell;
		    if ( backtracking==0 ) {
		      cell = th;
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:670:4: tc= table_normalcell
		    {
		    pushFollow(FOLLOW_table_normalcell_in_table_cell2390);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:672:1: table_headercell returns [TableHeaderNode header = null] : table_headercell_markup tc= table_cellcontent ;
    public final TableHeaderNode table_headercell() throws RecognitionException {
	TableHeaderNode header =  null;

	CollectionNode tc = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:673:2: ( table_headercell_markup tc= table_cellcontent )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:673:4: table_headercell_markup tc= table_cellcontent
	    {
	    pushFollow(FOLLOW_table_headercell_markup_in_table_headercell2406);
	    table_headercell_markup();
	    _fsp--;
	    if (failed) return header;
	    pushFollow(FOLLOW_table_cellcontent_in_table_headercell2413);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:675:1: table_normalcell returns [TableDataNode cell = null] : table_cell_markup tc= table_cellcontent ;
    public final TableDataNode table_normalcell() throws RecognitionException {
	TableDataNode cell =  null;

	CollectionNode tc = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:676:2: ( table_cell_markup tc= table_cellcontent )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:676:4: table_cell_markup tc= table_cellcontent
	    {
	    pushFollow(FOLLOW_table_cell_markup_in_table_normalcell2429);
	    table_cell_markup();
	    _fsp--;
	    if (failed) return cell;
	    pushFollow(FOLLOW_table_cellcontent_in_table_normalcell2436);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:678:1: table_cellcontent returns [CollectionNode items = new CollectionNode()] : onestar (tcp= table_cellcontentpart onestar )* ;
    public final CollectionNode table_cellcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode tcp = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:679:2: ( onestar (tcp= table_cellcontentpart onestar )* )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:679:4: onestar (tcp= table_cellcontentpart onestar )*
	    {
	    pushFollow(FOLLOW_onestar_in_table_cellcontent2452);
	    onestar();
	    _fsp--;
	    if (failed) return items;
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:679:13: (tcp= table_cellcontentpart onestar )*
	    loop89:
	    do {
		int alt89=2;
		int LA89_0 = input.LA(1);

		if ( ((LA89_0>=FORCED_END_OF_LINE && LA89_0<=WIKI)||(LA89_0>=POUND && LA89_0<=EQUAL)||(LA89_0>=ITAL && LA89_0<=79)) ) {
		    alt89=1;
		}


		switch (alt89) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:679:15: tcp= table_cellcontentpart onestar
		    {
		    pushFollow(FOLLOW_table_cellcontentpart_in_table_cellcontent2461);
		    tcp=table_cellcontentpart();
		    _fsp--;
		    if (failed) return items;
		    if ( backtracking==0 ) {

					if (tcp != null) {
						items.add(tcp);
					}
				
		    }
		    pushFollow(FOLLOW_onestar_in_table_cellcontent2468);
		    onestar();
		    _fsp--;
		    if (failed) return items;

		    }
		    break;

		default :
		    break loop89;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:686:1: table_cellcontentpart returns [ASTNode node = null] : (tf= table_formattedelement | tu= table_unformattedelement );
    public final ASTNode table_cellcontentpart() throws RecognitionException {
	ASTNode node =	null;

	ASTNode tf = null;

	ASTNode tu = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:687:2: (tf= table_formattedelement | tu= table_unformattedelement )
	    int alt90=2;
	    int LA90_0 = input.LA(1);

	    if ( (LA90_0==STAR||LA90_0==ITAL) ) {
		alt90=1;
	    }
	    else if ( ((LA90_0>=FORCED_END_OF_LINE && LA90_0<=WIKI)||LA90_0==POUND||LA90_0==EQUAL||(LA90_0>=LINK_OPEN && LA90_0<=79)) ) {
		alt90=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
		    new NoViableAltException("686:1: table_cellcontentpart returns [ASTNode node = null] : (tf= table_formattedelement | tu= table_unformattedelement );", 90, 0, input);

		throw nvae;
	    }
	    switch (alt90) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:687:4: tf= table_formattedelement
		    {
		    pushFollow(FOLLOW_table_formattedelement_in_table_cellcontentpart2489);
		    tf=table_formattedelement();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		      node =tf;
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:688:4: tu= table_unformattedelement
		    {
		    pushFollow(FOLLOW_table_unformattedelement_in_table_cellcontentpart2500);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:690:1: table_formattedelement returns [ASTNode content = null] : ( ital_markup (tic= table_italcontent )? ( ital_markup )? | bold_markup (tbc= table_boldcontent )? ( bold_markup )? );
    public final ASTNode table_formattedelement() throws RecognitionException {
	ASTNode content =  null;

	CollectionNode tic = null;

	CollectionNode tbc = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:691:2: ( ital_markup (tic= table_italcontent )? ( ital_markup )? | bold_markup (tbc= table_boldcontent )? ( bold_markup )? )
	    int alt95=2;
	    int LA95_0 = input.LA(1);

	    if ( (LA95_0==ITAL) ) {
		alt95=1;
	    }
	    else if ( (LA95_0==STAR) ) {
		alt95=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return content;}
		NoViableAltException nvae =
		    new NoViableAltException("690:1: table_formattedelement returns [ASTNode content = null] : ( ital_markup (tic= table_italcontent )? ( ital_markup )? | bold_markup (tbc= table_boldcontent )? ( bold_markup )? );", 95, 0, input);

		throw nvae;
	    }
	    switch (alt95) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:691:4: ital_markup (tic= table_italcontent )? ( ital_markup )?
		    {
		    pushFollow(FOLLOW_ital_markup_in_table_formattedelement2516);
		    ital_markup();
		    _fsp--;
		    if (failed) return content;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:691:18: (tic= table_italcontent )?
		    int alt91=2;
		    switch ( input.LA(1) ) {
			case STAR:
			    {
			    alt91=1;
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt91=1;
			    }
			    break;
			case FORCED_LINEBREAK:
			    {
			    alt91=1;
			    }
			    break;
			case ESCAPE:
			    {
			    alt91=1;
			    }
			    break;
			case LINK_OPEN:
			    {
			    alt91=1;
			    }
			    break;
			case IMAGE_OPEN:
			    {
			    alt91=1;
			    }
			    break;
			case EXTENSION:
			    {
			    alt91=1;
			    }
			    break;
			case NOWIKI_OPEN:
			    {
			    alt91=1;
			    }
			    break;
			case EOF:
			    {
			    alt91=1;
			    }
			    break;
		    }

		    switch (alt91) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:691:20: tic= table_italcontent
			    {
			    pushFollow(FOLLOW_table_italcontent_in_table_formattedelement2526);
			    tic=table_italcontent();
			    _fsp--;
			    if (failed) return content;
			    if ( backtracking==0 ) {
			       content = new ItalicTextNode(tic); 
			    }

			    }
			    break;

		    }

		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:691:94: ( ital_markup )?
		    int alt92=2;
		    int LA92_0 = input.LA(1);

		    if ( (LA92_0==ITAL) ) {
			alt92=1;
		    }
		    switch (alt92) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:691:96: ital_markup
			    {
			    pushFollow(FOLLOW_ital_markup_in_table_formattedelement2535);
			    ital_markup();
			    _fsp--;
			    if (failed) return content;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:692:4: bold_markup (tbc= table_boldcontent )? ( bold_markup )?
		    {
		    pushFollow(FOLLOW_bold_markup_in_table_formattedelement2543);
		    bold_markup();
		    _fsp--;
		    if (failed) return content;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:692:16: (tbc= table_boldcontent )?
		    int alt93=2;
		    switch ( input.LA(1) ) {
			case STAR:
			    {
			    int LA93_1 = input.LA(2);

			    if ( ( input.LA(2) != STAR ) ) {
				alt93=1;
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt93=1;
			    }
			    break;
			case FORCED_LINEBREAK:
			    {
			    alt93=1;
			    }
			    break;
			case ESCAPE:
			    {
			    alt93=1;
			    }
			    break;
			case LINK_OPEN:
			    {
			    alt93=1;
			    }
			    break;
			case IMAGE_OPEN:
			    {
			    alt93=1;
			    }
			    break;
			case EXTENSION:
			    {
			    alt93=1;
			    }
			    break;
			case NOWIKI_OPEN:
			    {
			    alt93=1;
			    }
			    break;
			case ITAL:
			    {
			    alt93=1;
			    }
			    break;
			case EOF:
			    {
			    alt93=1;
			    }
			    break;
		    }

		    switch (alt93) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:692:18: tbc= table_boldcontent
			    {
			    pushFollow(FOLLOW_table_boldcontent_in_table_formattedelement2550);
			    tbc=table_boldcontent();
			    _fsp--;
			    if (failed) return content;
			    if ( backtracking==0 ) {
			      content = new BoldTextNode(tbc);
			    }

			    }
			    break;

		    }

		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:692:88: ( bold_markup )?
		    int alt94=2;
		    int LA94_0 = input.LA(1);

		    if ( (LA94_0==STAR) ) {
			int LA94_1 = input.LA(2);

			if ( (LA94_1==STAR) ) {
			    alt94=1;
			}
		    }
		    switch (alt94) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:692:90: bold_markup
			    {
			    pushFollow(FOLLOW_bold_markup_in_table_formattedelement2560);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:694:1: table_boldcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (tb= table_boldcontentpart onestar )+ | EOF );
    public final CollectionNode table_boldcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode tb = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:695:2: ( onestar (tb= table_boldcontentpart onestar )+ | EOF )
	    int alt97=2;
	    int LA97_0 = input.LA(1);

	    if ( ((LA97_0>=FORCED_END_OF_LINE && LA97_0<=WIKI)||(LA97_0>=POUND && LA97_0<=EQUAL)||(LA97_0>=ITAL && LA97_0<=79)) ) {
		alt97=1;
	    }
	    else if ( (LA97_0==EOF) ) {
		alt97=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
		    new NoViableAltException("694:1: table_boldcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (tb= table_boldcontentpart onestar )+ | EOF );", 97, 0, input);

		throw nvae;
	    }
	    switch (alt97) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:695:4: onestar (tb= table_boldcontentpart onestar )+
		    {
		    pushFollow(FOLLOW_onestar_in_table_boldcontent2577);
		    onestar();
		    _fsp--;
		    if (failed) return items;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:695:13: (tb= table_boldcontentpart onestar )+
		    int cnt96=0;
		    loop96:
		    do {
			int alt96=2;
			switch ( input.LA(1) ) {
			case ITAL:
			    {
			    alt96=1;
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt96=1;
			    }
			    break;
			case FORCED_LINEBREAK:
			    {
			    alt96=1;
			    }
			    break;
			case ESCAPE:
			    {
			    alt96=1;
			    }
			    break;
			case LINK_OPEN:
			    {
			    alt96=1;
			    }
			    break;
			case IMAGE_OPEN:
			    {
			    alt96=1;
			    }
			    break;
			case EXTENSION:
			    {
			    alt96=1;
			    }
			    break;
			case NOWIKI_OPEN:
			    {
			    alt96=1;
			    }
			    break;

			}

			switch (alt96) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:695:15: tb= table_boldcontentpart onestar
			    {
			    pushFollow(FOLLOW_table_boldcontentpart_in_table_boldcontent2586);
			    tb=table_boldcontentpart();
			    _fsp--;
			    if (failed) return items;
			    if ( backtracking==0 ) {
			       items.add(tb); 
			    }
			    pushFollow(FOLLOW_onestar_in_table_boldcontent2591);
			    onestar();
			    _fsp--;
			    if (failed) return items;

			    }
			    break;

			default :
			    if ( cnt96 >= 1 ) break loop96;
			    if (backtracking>0) {failed=true; return items;}
				EarlyExitException eee =
				    new EarlyExitException(96, input);
				throw eee;
			}
			cnt96++;
		    } while (true);


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:696:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_table_boldcontent2599); if (failed) return items;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:698:1: table_italcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (ti= table_italcontentpart onestar )+ | EOF );
    public final CollectionNode table_italcontent() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	ASTNode ti = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:699:2: ( onestar (ti= table_italcontentpart onestar )+ | EOF )
	    int alt99=2;
	    int LA99_0 = input.LA(1);

	    if ( ((LA99_0>=FORCED_END_OF_LINE && LA99_0<=WIKI)||(LA99_0>=POUND && LA99_0<=EQUAL)||(LA99_0>=LINK_OPEN && LA99_0<=79)) ) {
		alt99=1;
	    }
	    else if ( (LA99_0==EOF) ) {
		alt99=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
		    new NoViableAltException("698:1: table_italcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (ti= table_italcontentpart onestar )+ | EOF );", 99, 0, input);

		throw nvae;
	    }
	    switch (alt99) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:699:4: onestar (ti= table_italcontentpart onestar )+
		    {
		    pushFollow(FOLLOW_onestar_in_table_italcontent2613);
		    onestar();
		    _fsp--;
		    if (failed) return items;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:699:13: (ti= table_italcontentpart onestar )+
		    int cnt98=0;
		    loop98:
		    do {
			int alt98=2;
			switch ( input.LA(1) ) {
			case STAR:
			    {
			    alt98=1;
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt98=1;
			    }
			    break;
			case FORCED_LINEBREAK:
			    {
			    alt98=1;
			    }
			    break;
			case ESCAPE:
			    {
			    alt98=1;
			    }
			    break;
			case LINK_OPEN:
			    {
			    alt98=1;
			    }
			    break;
			case IMAGE_OPEN:
			    {
			    alt98=1;
			    }
			    break;
			case EXTENSION:
			    {
			    alt98=1;
			    }
			    break;
			case NOWIKI_OPEN:
			    {
			    alt98=1;
			    }
			    break;

			}

			switch (alt98) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:699:15: ti= table_italcontentpart onestar
			    {
			    pushFollow(FOLLOW_table_italcontentpart_in_table_italcontent2622);
			    ti=table_italcontentpart();
			    _fsp--;
			    if (failed) return items;
			    if ( backtracking==0 ) {
			       items.add(ti); 
			    }
			    pushFollow(FOLLOW_onestar_in_table_italcontent2627);
			    onestar();
			    _fsp--;
			    if (failed) return items;

			    }
			    break;

			default :
			    if ( cnt98 >= 1 ) break loop98;
			    if (backtracking>0) {failed=true; return items;}
				EarlyExitException eee =
				    new EarlyExitException(98, input);
				throw eee;
			}
			cnt98++;
		    } while (true);


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:700:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_table_italcontent2635); if (failed) return items;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:702:1: table_boldcontentpart returns [ASTNode node = null] : (tf= table_formattedcontent | ital_markup tb= table_bolditalcontent ( ital_markup )? );
    public final ASTNode table_boldcontentpart() throws RecognitionException {
	ASTNode node =	null;

	CollectionNode tf = null;

	CollectionNode tb = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:703:2: (tf= table_formattedcontent | ital_markup tb= table_bolditalcontent ( ital_markup )? )
	    int alt101=2;
	    int LA101_0 = input.LA(1);

	    if ( ((LA101_0>=FORCED_END_OF_LINE && LA101_0<=WIKI)||LA101_0==POUND||LA101_0==EQUAL||(LA101_0>=LINK_OPEN && LA101_0<=79)) ) {
		alt101=1;
	    }
	    else if ( (LA101_0==ITAL) ) {
		alt101=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
		    new NoViableAltException("702:1: table_boldcontentpart returns [ASTNode node = null] : (tf= table_formattedcontent | ital_markup tb= table_bolditalcontent ( ital_markup )? );", 101, 0, input);

		throw nvae;
	    }
	    switch (alt101) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:703:4: tf= table_formattedcontent
		    {
		    pushFollow(FOLLOW_table_formattedcontent_in_table_boldcontentpart2653);
		    tf=table_formattedcontent();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		      node = tf; 
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:704:4: ital_markup tb= table_bolditalcontent ( ital_markup )?
		    {
		    pushFollow(FOLLOW_ital_markup_in_table_boldcontentpart2660);
		    ital_markup();
		    _fsp--;
		    if (failed) return node;
		    pushFollow(FOLLOW_table_bolditalcontent_in_table_boldcontentpart2667);
		    tb=table_bolditalcontent();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		       node = new ItalicTextNode(tb);  
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:704:92: ( ital_markup )?
		    int alt100=2;
		    int LA100_0 = input.LA(1);

		    if ( (LA100_0==ITAL) ) {
			alt100=1;
		    }
		    switch (alt100) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:704:94: ital_markup
			    {
			    pushFollow(FOLLOW_ital_markup_in_table_boldcontentpart2674);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:706:1: table_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= table_bolditalcontent ( bold_markup )? | tf= table_formattedcontent );
    public final ASTNode table_italcontentpart() throws RecognitionException {
	ASTNode node =	null;

	CollectionNode tb = null;

	CollectionNode tf = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:707:2: ( bold_markup tb= table_bolditalcontent ( bold_markup )? | tf= table_formattedcontent )
	    int alt103=2;
	    int LA103_0 = input.LA(1);

	    if ( (LA103_0==STAR) ) {
		alt103=1;
	    }
	    else if ( ((LA103_0>=FORCED_END_OF_LINE && LA103_0<=WIKI)||LA103_0==POUND||LA103_0==EQUAL||(LA103_0>=LINK_OPEN && LA103_0<=79)) ) {
		alt103=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return node;}
		NoViableAltException nvae =
		    new NoViableAltException("706:1: table_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= table_bolditalcontent ( bold_markup )? | tf= table_formattedcontent );", 103, 0, input);

		throw nvae;
	    }
	    switch (alt103) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:707:4: bold_markup tb= table_bolditalcontent ( bold_markup )?
		    {
		    pushFollow(FOLLOW_bold_markup_in_table_italcontentpart2691);
		    bold_markup();
		    _fsp--;
		    if (failed) return node;
		    pushFollow(FOLLOW_table_bolditalcontent_in_table_italcontentpart2698);
		    tb=table_bolditalcontent();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		      node = new BoldTextNode(tb); 
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:707:88: ( bold_markup )?
		    int alt102=2;
		    int LA102_0 = input.LA(1);

		    if ( (LA102_0==STAR) ) {
			int LA102_1 = input.LA(2);

			if ( (LA102_1==STAR) ) {
			    alt102=1;
			}
		    }
		    switch (alt102) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:707:90: bold_markup
			    {
			    pushFollow(FOLLOW_bold_markup_in_table_italcontentpart2705);
			    bold_markup();
			    _fsp--;
			    if (failed) return node;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:708:4: tf= table_formattedcontent
		    {
		    pushFollow(FOLLOW_table_formattedcontent_in_table_italcontentpart2717);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:710:1: table_bolditalcontent returns [CollectionNode elements = null] : ( onestar (tfc= table_formattedcontent onestar )? | EOF );
    public final CollectionNode table_bolditalcontent() throws RecognitionException {
	CollectionNode elements =  null;

	CollectionNode tfc = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:711:2: ( onestar (tfc= table_formattedcontent onestar )? | EOF )
	    int alt105=2;
	    int LA105_0 = input.LA(1);

	    if ( ((LA105_0>=FORCED_END_OF_LINE && LA105_0<=EQUAL)||(LA105_0>=ITAL && LA105_0<=79)) ) {
		alt105=1;
	    }
	    else if ( (LA105_0==EOF||LA105_0==PIPE) ) {
		alt105=1;
	    }
	    else {
		if (backtracking>0) {failed=true; return elements;}
		NoViableAltException nvae =
		    new NoViableAltException("710:1: table_bolditalcontent returns [CollectionNode elements = null] : ( onestar (tfc= table_formattedcontent onestar )? | EOF );", 105, 0, input);

		throw nvae;
	    }
	    switch (alt105) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:711:4: onestar (tfc= table_formattedcontent onestar )?
		    {
		    pushFollow(FOLLOW_onestar_in_table_bolditalcontent2733);
		    onestar();
		    _fsp--;
		    if (failed) return elements;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:711:13: (tfc= table_formattedcontent onestar )?
		    int alt104=2;
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt104=1;
			    }
			    break;
			case FORCED_LINEBREAK:
			    {
			    alt104=1;
			    }
			    break;
			case ESCAPE:
			    {
			    alt104=1;
			    }
			    break;
			case LINK_OPEN:
			    {
			    alt104=1;
			    }
			    break;
			case IMAGE_OPEN:
			    {
			    alt104=1;
			    }
			    break;
			case EXTENSION:
			    {
			    alt104=1;
			    }
			    break;
			case NOWIKI_OPEN:
			    {
			    alt104=1;
			    }
			    break;
		    }

		    switch (alt104) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:711:15: tfc= table_formattedcontent onestar
			    {
			    pushFollow(FOLLOW_table_formattedcontent_in_table_bolditalcontent2742);
			    tfc=table_formattedcontent();
			    _fsp--;
			    if (failed) return elements;
			    if ( backtracking==0 ) {
			       elements = tfc; 
			    }
			    pushFollow(FOLLOW_onestar_in_table_bolditalcontent2747);
			    onestar();
			    _fsp--;
			    if (failed) return elements;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:712:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_table_bolditalcontent2755); if (failed) return elements;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:714:1: table_formattedcontent returns [CollectionNode elements = new CollectionNode()] : (tu= table_unformattedelement )+ ;
    public final CollectionNode table_formattedcontent() throws RecognitionException {
	CollectionNode elements =  new CollectionNode();

	ASTNode tu = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:715:2: ( (tu= table_unformattedelement )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:715:4: (tu= table_unformattedelement )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:715:4: (tu= table_unformattedelement )+
	    int cnt106=0;
	    loop106:
	    do {
		int alt106=2;
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
		case BRACE_CLOSE:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
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
		case 79:
		    {
		    alt106=1;
		    }
		    break;
		case FORCED_LINEBREAK:
		    {
		    alt106=1;
		    }
		    break;
		case ESCAPE:
		    {
		    alt106=1;
		    }
		    break;
		case LINK_OPEN:
		    {
		    alt106=1;
		    }
		    break;
		case IMAGE_OPEN:
		    {
		    alt106=1;
		    }
		    break;
		case EXTENSION:
		    {
		    alt106=1;
		    }
		    break;
		case NOWIKI_OPEN:
		    {
		    alt106=1;
		    }
		    break;

		}

		switch (alt106) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:715:6: tu= table_unformattedelement
		    {
		    pushFollow(FOLLOW_table_unformattedelement_in_table_formattedcontent2775);
		    tu=table_unformattedelement();
		    _fsp--;
		    if (failed) return elements;
		    if ( backtracking==0 ) {
		       elements.add(tu); 
		    }

		    }
		    break;

		default :
		    if ( cnt106 >= 1 ) break loop106;
		    if (backtracking>0) {failed=true; return elements;}
			EarlyExitException eee =
			    new EarlyExitException(106, input);
			throw eee;
		}
		cnt106++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:717:1: table_unformattedelement returns [ASTNode content = null] : (tu= table_unformatted | ti= table_inlineelement );
    public final ASTNode table_unformattedelement() throws RecognitionException {
	ASTNode content =  null;

	CollectionNode tu = null;

	ASTNode ti = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:718:2: (tu= table_unformatted | ti= table_inlineelement )
	    int alt107=2;
	    int LA107_0 = input.LA(1);

	    if ( ((LA107_0>=FORCED_END_OF_LINE && LA107_0<=WIKI)||LA107_0==POUND||LA107_0==EQUAL||(LA107_0>=FORCED_LINEBREAK && LA107_0<=79)) ) {
		alt107=1;
	    }
	    else if ( ((LA107_0>=LINK_OPEN && LA107_0<=EXTENSION)) ) {
		alt107=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return content;}
		NoViableAltException nvae =
		    new NoViableAltException("717:1: table_unformattedelement returns [ASTNode content = null] : (tu= table_unformatted | ti= table_inlineelement );", 107, 0, input);

		throw nvae;
	    }
	    switch (alt107) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:718:4: tu= table_unformatted
		    {
		    pushFollow(FOLLOW_table_unformatted_in_table_unformattedelement2798);
		    tu=table_unformatted();
		    _fsp--;
		    if (failed) return content;
		    if ( backtracking==0 ) {
		      content = new UnformattedTextNode(tu);
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:719:4: ti= table_inlineelement
		    {
		    pushFollow(FOLLOW_table_inlineelement_in_table_unformattedelement2810);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:721:1: table_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension | nw= nowiki_inline );
    public final ASTNode table_inlineelement() throws RecognitionException {
	ASTNode element =  null;

	LinkNode l = null;

	ImageNode i = null;

	ASTNode e = null;

	NoWikiSectionNode nw = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:722:2: (l= link | i= image | e= extension | nw= nowiki_inline )
	    int alt108=4;
	    switch ( input.LA(1) ) {
	    case LINK_OPEN:
		{
		alt108=1;
		}
		break;
	    case IMAGE_OPEN:
		{
		alt108=2;
		}
		break;
	    case EXTENSION:
		{
		alt108=3;
		}
		break;
	    case NOWIKI_OPEN:
		{
		alt108=4;
		}
		break;
	    default:
		if (backtracking>0) {failed=true; return element;}
		NoViableAltException nvae =
		    new NoViableAltException("721:1: table_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension | nw= nowiki_inline );", 108, 0, input);

		throw nvae;
	    }

	    switch (alt108) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:722:4: l= link
		    {
		    pushFollow(FOLLOW_link_in_table_inlineelement2830);
		    l=link();
		    _fsp--;
		    if (failed) return element;
		    if ( backtracking==0 ) {
		      element = l; 
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:723:4: i= image
		    {
		    pushFollow(FOLLOW_image_in_table_inlineelement2840);
		    i=image();
		    _fsp--;
		    if (failed) return element;
		    if ( backtracking==0 ) {
		      element = i; 
		    }

		    }
		    break;
		case 3 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:724:4: e= extension
		    {
		    pushFollow(FOLLOW_extension_in_table_inlineelement2851);
		    e=extension();
		    _fsp--;
		    if (failed) return element;
		    if ( backtracking==0 ) {
		      element = e; 
		    }

		    }
		    break;
		case 4 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:725:4: nw= nowiki_inline
		    {
		    pushFollow(FOLLOW_nowiki_inline_in_table_inlineelement2861);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:727:1: table_unformatted returns [CollectionNode text = new CollectionNode()] : (t= table_unformatted_text | ( forced_linebreak | e= escaped )+ );
    public final CollectionNode table_unformatted() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	StringBundler t = null;

	ScapedNode e = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:728:2: (t= table_unformatted_text | ( forced_linebreak | e= escaped )+ )
	    int alt110=2;
	    int LA110_0 = input.LA(1);

	    if ( ((LA110_0>=FORCED_END_OF_LINE && LA110_0<=WIKI)||LA110_0==POUND||LA110_0==EQUAL||(LA110_0>=NOWIKI_BLOCK_CLOSE && LA110_0<=79)) ) {
		alt110=1;
	    }
	    else if ( ((LA110_0>=FORCED_LINEBREAK && LA110_0<=ESCAPE)) ) {
		alt110=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
		    new NoViableAltException("727:1: table_unformatted returns [CollectionNode text = new CollectionNode()] : (t= table_unformatted_text | ( forced_linebreak | e= escaped )+ );", 110, 0, input);

		throw nvae;
	    }
	    switch (alt110) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:728:5: t= table_unformatted_text
		    {
		    pushFollow(FOLLOW_table_unformatted_text_in_table_unformatted2882);
		    t=table_unformatted_text();
		    _fsp--;
		    if (failed) return text;
		    if ( backtracking==0 ) {
		       text.add(new UnformattedTextNode(t.toString()));
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:729:5: ( forced_linebreak | e= escaped )+
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:729:5: ( forced_linebreak | e= escaped )+
		    int cnt109=0;
		    loop109:
		    do {
			int alt109=3;
			int LA109_0 = input.LA(1);

			if ( (LA109_0==FORCED_LINEBREAK) ) {
			    alt109=1;
			}
			else if ( (LA109_0==ESCAPE) ) {
			    alt109=2;
			}


			switch (alt109) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:729:6: forced_linebreak
			    {
			    pushFollow(FOLLOW_forced_linebreak_in_table_unformatted2891);
			    forced_linebreak();
			    _fsp--;
			    if (failed) return text;
			    if ( backtracking==0 ) {
			      text.add(new ForcedEndOfLineNode());
			    }

			    }
			    break;
			case 2 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:730:5: e= escaped
			    {
			    pushFollow(FOLLOW_escaped_in_table_unformatted2903);
			    e=escaped();
			    _fsp--;
			    if (failed) return text;
			    if ( backtracking==0 ) {
			      text.add(e);
			    }

			    }
			    break;

			default :
			    if ( cnt109 >= 1 ) break loop109;
			    if (backtracking>0) {failed=true; return text;}
				EarlyExitException eee =
				    new EarlyExitException(109, input);
				throw eee;
			}
			cnt109++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:733:1: table_unformatted_text returns [StringBundler text = new StringBundler()] : (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
    public final StringBundler table_unformatted_text() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:734:2: ( (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:734:4: (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:734:4: (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
	    int cnt111=0;
	    loop111:
	    do {
		int alt111=2;
		int LA111_0 = input.LA(1);

		if ( ((LA111_0>=FORCED_END_OF_LINE && LA111_0<=WIKI)||LA111_0==POUND||LA111_0==EQUAL||(LA111_0>=NOWIKI_BLOCK_CLOSE && LA111_0<=79)) ) {
		    alt111=1;
		}


		switch (alt111) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:734:6: c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
		    {
		    c=(Token)input.LT(1);
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||input.LA(1)==EQUAL||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_table_unformatted_text2929);	 throw mse;
		    }

		    if ( backtracking==0 ) {
		      text.append(c.getText());
		    }

		    }
		    break;

		default :
		    if ( cnt111 >= 1 ) break loop111;
		    if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
			    new EarlyExitException(111, input);
			throw eee;
		}
		cnt111++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:748:1: nowiki_block returns [NoWikiSectionNode nowikiNode] : nowikiblock_open_markup contents= nowiki_block_contents nowikiblock_close_markup paragraph_separator ;
    public final NoWikiSectionNode nowiki_block() throws RecognitionException {
	NoWikiSectionNode nowikiNode = null;

	nowiki_block_contents_return contents = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:749:2: ( nowikiblock_open_markup contents= nowiki_block_contents nowikiblock_close_markup paragraph_separator )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:749:4: nowikiblock_open_markup contents= nowiki_block_contents nowikiblock_close_markup paragraph_separator
	    {
	    pushFollow(FOLLOW_nowikiblock_open_markup_in_nowiki_block3026);
	    nowikiblock_open_markup();
	    _fsp--;
	    if (failed) return nowikiNode;
	    pushFollow(FOLLOW_nowiki_block_contents_in_nowiki_block3033);
	    contents=nowiki_block_contents();
	    _fsp--;
	    if (failed) return nowikiNode;
	    if ( backtracking==0 ) {
	      nowikiNode = new NoWikiSectionNode(input.toString(contents.start,contents.stop).toString());
	    }
	    pushFollow(FOLLOW_nowikiblock_close_markup_in_nowiki_block3039);
	    nowikiblock_close_markup();
	    _fsp--;
	    if (failed) return nowikiNode;
	    pushFollow(FOLLOW_paragraph_separator_in_nowiki_block3042);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:753:1: nowikiblock_open_markup : nowiki_open_markup newline ;
    public final void nowikiblock_open_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:754:2: ( nowiki_open_markup newline )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:754:4: nowiki_open_markup newline
	    {
	    pushFollow(FOLLOW_nowiki_open_markup_in_nowikiblock_open_markup3053);
	    nowiki_open_markup();
	    _fsp--;
	    if (failed) return ;
	    pushFollow(FOLLOW_newline_in_nowikiblock_open_markup3056);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:757:1: nowikiblock_close_markup : NOWIKI_BLOCK_CLOSE ;
    public final void nowikiblock_close_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:758:2: ( NOWIKI_BLOCK_CLOSE )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:758:4: NOWIKI_BLOCK_CLOSE
	    {
	    match(input,NOWIKI_BLOCK_CLOSE,FOLLOW_NOWIKI_BLOCK_CLOSE_in_nowikiblock_close_markup3067); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:761:1: nowiki_inline returns [NoWikiSectionNode nowiki = null] : nowiki_open_markup t= nowiki_inline_contents nowiki_close_markup ;
    public final NoWikiSectionNode nowiki_inline() throws RecognitionException {
	NoWikiSectionNode nowiki =  null;

	StringBundler t = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:762:2: ( nowiki_open_markup t= nowiki_inline_contents nowiki_close_markup )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:762:4: nowiki_open_markup t= nowiki_inline_contents nowiki_close_markup
	    {
	    pushFollow(FOLLOW_nowiki_open_markup_in_nowiki_inline3082);
	    nowiki_open_markup();
	    _fsp--;
	    if (failed) return nowiki;
	    pushFollow(FOLLOW_nowiki_inline_contents_in_nowiki_inline3089);
	    t=nowiki_inline_contents();
	    _fsp--;
	    if (failed) return nowiki;
	    pushFollow(FOLLOW_nowiki_close_markup_in_nowiki_inline3093);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:765:1: nowiki_block_contents returns [StringBundler contents = new StringBundler()] : (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )* ;
    public final nowiki_block_contents_return nowiki_block_contents() throws RecognitionException {
	nowiki_block_contents_return retval = new nowiki_block_contents_return();
	retval.start = input.LT(1);

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:766:2: ( (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )* )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:766:3: (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )*
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:766:3: (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )*
	    loop112:
	    do {
		int alt112=2;
		int LA112_0 = input.LA(1);

		if ( ((LA112_0>=FORCED_END_OF_LINE && LA112_0<=ESCAPE)||(LA112_0>=NOWIKI_CLOSE && LA112_0<=79)) ) {
		    alt112=1;
		}


		switch (alt112) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:766:4: c=~ ( NOWIKI_BLOCK_CLOSE | EOF )
		    {
		    c=(Token)input.LT(1);
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=ESCAPE)||(input.LA(1)>=NOWIKI_CLOSE && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return retval;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_nowiki_block_contents3111);	throw mse;
		    }

		    if ( backtracking==0 ) {
		      retval.contents.append(c.getText());
		    }

		    }
		    break;

		default :
		    break loop112;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:769:1: nowiki_inline_contents returns [StringBundler text = new StringBundler()] : (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )* ;
    public final StringBundler nowiki_inline_contents() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:770:2: ( (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )* )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:770:4: (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )*
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:770:4: (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )*
	    loop113:
	    do {
		int alt113=2;
		int LA113_0 = input.LA(1);

		if ( ((LA113_0>=FORCED_END_OF_LINE && LA113_0<=WIKI)||(LA113_0>=POUND && LA113_0<=NOWIKI_BLOCK_CLOSE)||(LA113_0>=LINK_CLOSE && LA113_0<=79)) ) {
		    alt113=1;
		}


		switch (alt113) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:770:5: c=~ ( NOWIKI_CLOSE | NEWLINE | EOF )
		    {
		    c=(Token)input.LT(1);
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=NOWIKI_BLOCK_CLOSE)||(input.LA(1)>=LINK_CLOSE && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_nowiki_inline_contents3144);	 throw mse;
		    }

		    if ( backtracking==0 ) {
		       text.append(c.getText()); 
		    }

		    }
		    break;

		default :
		    break loop113;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:777:1: horizontalrule returns [ASTNode horizontal = null] : horizontalrule_markup ( blanks )? paragraph_separator ;
    public final ASTNode horizontalrule() throws RecognitionException {
	ASTNode horizontal =  null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:778:2: ( horizontalrule_markup ( blanks )? paragraph_separator )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:778:4: horizontalrule_markup ( blanks )? paragraph_separator
	    {
	    pushFollow(FOLLOW_horizontalrule_markup_in_horizontalrule3180);
	    horizontalrule_markup();
	    _fsp--;
	    if (failed) return horizontal;
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:778:27: ( blanks )?
	    int alt114=2;
	    int LA114_0 = input.LA(1);

	    if ( (LA114_0==BLANKS) ) {
		alt114=1;
	    }
	    switch (alt114) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:778:29: blanks
		    {
		    pushFollow(FOLLOW_blanks_in_horizontalrule3185);
		    blanks();
		    _fsp--;
		    if (failed) return horizontal;

		    }
		    break;

	    }

	    pushFollow(FOLLOW_paragraph_separator_in_horizontalrule3191);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:785:1: link returns [LinkNode link = null] : link_open_markup a= link_address ( link_description_markup d= link_description )? link_close_markup ;
    public final LinkNode link() throws RecognitionException {
	LinkNode link =  null;

	LinkNode a = null;

	CollectionNode d = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:786:2: ( link_open_markup a= link_address ( link_description_markup d= link_description )? link_close_markup )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:786:4: link_open_markup a= link_address ( link_description_markup d= link_description )? link_close_markup
	    {
	    pushFollow(FOLLOW_link_open_markup_in_link3212);
	    link_open_markup();
	    _fsp--;
	    if (failed) return link;
	    pushFollow(FOLLOW_link_address_in_link3218);
	    a=link_address();
	    _fsp--;
	    if (failed) return link;
	    if ( backtracking==0 ) {
	      link = a; 
	    }
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:786:59: ( link_description_markup d= link_description )?
	    int alt115=2;
	    int LA115_0 = input.LA(1);

	    if ( (LA115_0==PIPE) ) {
		alt115=1;
	    }
	    switch (alt115) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:786:60: link_description_markup d= link_description
		    {
		    pushFollow(FOLLOW_link_description_markup_in_link3224);
		    link_description_markup();
		    _fsp--;
		    if (failed) return link;
		    pushFollow(FOLLOW_link_description_in_link3232);
		    d=link_description();
		    _fsp--;
		    if (failed) return link;
		    if ( backtracking==0 ) {

					if (link == null) { // recover from possible errors
					    link = new LinkNode();
					}
					link.setAltCollectionNode(d);

					
		    }

		    }
		    break;

	    }

	    pushFollow(FOLLOW_link_close_markup_in_link3240);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );
    public final LinkNode link_address() throws RecognitionException {
	LinkNode link = null;

	InterwikiLinkNode li = null;

	StringBundler p = null;

	StringBundler lu = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:797:2: (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri )
	    int alt116=2;
	    switch ( input.LA(1) ) {
	    case 45:
		{
		int LA116_1 = input.LA(2);

		if ( (LA116_1==46) ) {
		    int LA116_16 = input.LA(3);

		    if ( ((LA116_16>=FORCED_END_OF_LINE && LA116_16<=WIKI)||(LA116_16>=POUND && LA116_16<=INSIGNIFICANT_CHAR)||(LA116_16>=45 && LA116_16<=79)) ) {
			alt116=2;
		    }
		    else if ( (LA116_16==44) ) {
			int LA116_34 = input.LA(4);

			if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
			    alt116=1;
			}
			else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

			    throw nvae;
			}
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 16, input);

			throw nvae;
		    }
		}
		else if ( ((LA116_1>=FORCED_END_OF_LINE && LA116_1<=WIKI)||(LA116_1>=POUND && LA116_1<=45)||(LA116_1>=47 && LA116_1<=79)) ) {
		    alt116=2;
		}
		else {
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 1, input);

		    throw nvae;
		}
		}
		break;
	    case 47:
		{
		int LA116_2 = input.LA(2);

		if ( (LA116_2==48) ) {
		    int LA116_17 = input.LA(3);

		    if ( (LA116_17==49) ) {
			int LA116_35 = input.LA(4);

			if ( (LA116_35==50) ) {
			    int LA116_55 = input.LA(5);

			    if ( (LA116_55==51) ) {
				int LA116_74 = input.LA(6);

				if ( (LA116_74==52) ) {
				    int LA116_93 = input.LA(7);

				    if ( (LA116_93==49) ) {
					int LA116_109 = input.LA(8);

					if ( (LA116_109==52) ) {
					    int LA116_120 = input.LA(9);

					    if ( ((LA116_120>=FORCED_END_OF_LINE && LA116_120<=WIKI)||(LA116_120>=POUND && LA116_120<=INSIGNIFICANT_CHAR)||(LA116_120>=45 && LA116_120<=79)) ) {
						alt116=2;
					    }
					    else if ( (LA116_120==44) ) {
						int LA116_34 = input.LA(10);

						if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
						    alt116=1;
						}
						else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
						    alt116=2;
						}
						else {
						    if (backtracking>0) {failed=true; return link;}
						    NoViableAltException nvae =
							new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

						    throw nvae;
						}
					    }
					    else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 120, input);

						throw nvae;
					    }
					}
					else if ( ((LA116_109>=FORCED_END_OF_LINE && LA116_109<=WIKI)||(LA116_109>=POUND && LA116_109<=51)||(LA116_109>=53 && LA116_109<=79)) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 109, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_93>=FORCED_END_OF_LINE && LA116_93<=WIKI)||(LA116_93>=POUND && LA116_93<=48)||(LA116_93>=50 && LA116_93<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 93, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_74>=FORCED_END_OF_LINE && LA116_74<=WIKI)||(LA116_74>=POUND && LA116_74<=51)||(LA116_74>=53 && LA116_74<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 74, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_55>=FORCED_END_OF_LINE && LA116_55<=WIKI)||(LA116_55>=POUND && LA116_55<=50)||(LA116_55>=52 && LA116_55<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 55, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_35>=FORCED_END_OF_LINE && LA116_35<=WIKI)||(LA116_35>=POUND && LA116_35<=49)||(LA116_35>=51 && LA116_35<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 35, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_17>=FORCED_END_OF_LINE && LA116_17<=WIKI)||(LA116_17>=POUND && LA116_17<=48)||(LA116_17>=50 && LA116_17<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 17, input);

			throw nvae;
		    }
		}
		else if ( ((LA116_2>=FORCED_END_OF_LINE && LA116_2<=WIKI)||(LA116_2>=POUND && LA116_2<=47)||(LA116_2>=49 && LA116_2<=79)) ) {
		    alt116=2;
		}
		else {
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 2, input);

		    throw nvae;
		}
		}
		break;
	    case 53:
		{
		int LA116_3 = input.LA(2);

		if ( (LA116_3==54) ) {
		    int LA116_18 = input.LA(3);

		    if ( (LA116_18==52) ) {
			int LA116_36 = input.LA(4);

			if ( (LA116_36==55) ) {
			    int LA116_56 = input.LA(5);

			    if ( (LA116_56==49) ) {
				int LA116_75 = input.LA(6);

				if ( (LA116_75==56) ) {
				    int LA116_94 = input.LA(7);

				    if ( ((LA116_94>=FORCED_END_OF_LINE && LA116_94<=WIKI)||(LA116_94>=POUND && LA116_94<=INSIGNIFICANT_CHAR)||(LA116_94>=45 && LA116_94<=79)) ) {
					alt116=2;
				    }
				    else if ( (LA116_94==44) ) {
					int LA116_34 = input.LA(8);

					if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
					    alt116=1;
					}
					else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

					    throw nvae;
					}
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 94, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_75>=FORCED_END_OF_LINE && LA116_75<=WIKI)||(LA116_75>=POUND && LA116_75<=55)||(LA116_75>=57 && LA116_75<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 75, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_56>=FORCED_END_OF_LINE && LA116_56<=WIKI)||(LA116_56>=POUND && LA116_56<=48)||(LA116_56>=50 && LA116_56<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 56, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_36>=FORCED_END_OF_LINE && LA116_36<=WIKI)||(LA116_36>=POUND && LA116_36<=54)||(LA116_36>=56 && LA116_36<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 36, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_18>=FORCED_END_OF_LINE && LA116_18<=WIKI)||(LA116_18>=POUND && LA116_18<=51)||(LA116_18>=53 && LA116_18<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 18, input);

			throw nvae;
		    }
		}
		else if ( ((LA116_3>=FORCED_END_OF_LINE && LA116_3<=WIKI)||(LA116_3>=POUND && LA116_3<=53)||(LA116_3>=55 && LA116_3<=79)) ) {
		    alt116=2;
		}
		else {
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 3, input);

		    throw nvae;
		}
		}
		break;
	    case 57:
		{
		int LA116_4 = input.LA(2);

		if ( (LA116_4==48) ) {
		    int LA116_19 = input.LA(3);

		    if ( (LA116_19==48) ) {
			int LA116_37 = input.LA(4);

			if ( (LA116_37==58) ) {
			    int LA116_57 = input.LA(5);

			    if ( (LA116_57==54) ) {
				int LA116_76 = input.LA(6);

				if ( (LA116_76==59) ) {
				    int LA116_95 = input.LA(7);

				    if ( (LA116_95==44) ) {
					int LA116_34 = input.LA(8);

					if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
					    alt116=1;
					}
					else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_95>=FORCED_END_OF_LINE && LA116_95<=WIKI)||(LA116_95>=POUND && LA116_95<=INSIGNIFICANT_CHAR)||(LA116_95>=45 && LA116_95<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 95, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_76>=FORCED_END_OF_LINE && LA116_76<=WIKI)||(LA116_76>=POUND && LA116_76<=58)||(LA116_76>=60 && LA116_76<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 76, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_57>=FORCED_END_OF_LINE && LA116_57<=WIKI)||(LA116_57>=POUND && LA116_57<=53)||(LA116_57>=55 && LA116_57<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 57, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_37>=FORCED_END_OF_LINE && LA116_37<=WIKI)||(LA116_37>=POUND && LA116_37<=57)||(LA116_37>=59 && LA116_37<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 37, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_19>=FORCED_END_OF_LINE && LA116_19<=WIKI)||(LA116_19>=POUND && LA116_19<=47)||(LA116_19>=49 && LA116_19<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 19, input);

			throw nvae;
		    }
		}
		else if ( ((LA116_4>=FORCED_END_OF_LINE && LA116_4<=WIKI)||(LA116_4>=POUND && LA116_4<=47)||(LA116_4>=49 && LA116_4<=79)) ) {
		    alt116=2;
		}
		else {
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 4, input);

		    throw nvae;
		}
		}
		break;
	    case 60:
		{
		int LA116_5 = input.LA(2);

		if ( (LA116_5==61) ) {
		    int LA116_20 = input.LA(3);

		    if ( (LA116_20==62) ) {
			int LA116_38 = input.LA(4);

			if ( (LA116_38==51) ) {
			    int LA116_58 = input.LA(5);

			    if ( (LA116_58==52) ) {
				int LA116_77 = input.LA(6);

				if ( (LA116_77==49) ) {
				    int LA116_96 = input.LA(7);

				    if ( (LA116_96==52) ) {
					int LA116_110 = input.LA(8);

					if ( ((LA116_110>=FORCED_END_OF_LINE && LA116_110<=WIKI)||(LA116_110>=POUND && LA116_110<=INSIGNIFICANT_CHAR)||(LA116_110>=45 && LA116_110<=79)) ) {
					    alt116=2;
					}
					else if ( (LA116_110==44) ) {
					    int LA116_34 = input.LA(9);

					    if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
						alt116=1;
					    }
					    else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
						alt116=2;
					    }
					    else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

						throw nvae;
					    }
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 110, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_96>=FORCED_END_OF_LINE && LA116_96<=WIKI)||(LA116_96>=POUND && LA116_96<=51)||(LA116_96>=53 && LA116_96<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 96, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_77>=FORCED_END_OF_LINE && LA116_77<=WIKI)||(LA116_77>=POUND && LA116_77<=48)||(LA116_77>=50 && LA116_77<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 77, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_58>=FORCED_END_OF_LINE && LA116_58<=WIKI)||(LA116_58>=POUND && LA116_58<=51)||(LA116_58>=53 && LA116_58<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 58, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_38>=FORCED_END_OF_LINE && LA116_38<=WIKI)||(LA116_38>=POUND && LA116_38<=50)||(LA116_38>=52 && LA116_38<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 38, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_20>=FORCED_END_OF_LINE && LA116_20<=WIKI)||(LA116_20>=POUND && LA116_20<=61)||(LA116_20>=63 && LA116_20<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 20, input);

			throw nvae;
		    }
		}
		else if ( ((LA116_5>=FORCED_END_OF_LINE && LA116_5<=WIKI)||(LA116_5>=POUND && LA116_5<=60)||(LA116_5>=62 && LA116_5<=79)) ) {
		    alt116=2;
		}
		else {
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 5, input);

		    throw nvae;
		}
		}
		break;
	    case 63:
		{
		switch ( input.LA(2) ) {
		case 48:
		    {
		    int LA116_21 = input.LA(3);

		    if ( (LA116_21==52) ) {
			int LA116_39 = input.LA(4);

			if ( (LA116_39==68) ) {
			    int LA116_59 = input.LA(5);

			    if ( (LA116_59==63) ) {
				int LA116_78 = input.LA(6);

				if ( (LA116_78==48) ) {
				    int LA116_97 = input.LA(7);

				    if ( (LA116_97==52) ) {
					int LA116_111 = input.LA(8);

					if ( (LA116_111==68) ) {
					    int LA116_121 = input.LA(9);

					    if ( ((LA116_121>=FORCED_END_OF_LINE && LA116_121<=WIKI)||(LA116_121>=POUND && LA116_121<=INSIGNIFICANT_CHAR)||(LA116_121>=45 && LA116_121<=79)) ) {
						alt116=2;
					    }
					    else if ( (LA116_121==44) ) {
						int LA116_34 = input.LA(10);

						if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
						    alt116=1;
						}
						else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
						    alt116=2;
						}
						else {
						    if (backtracking>0) {failed=true; return link;}
						    NoViableAltException nvae =
							new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

						    throw nvae;
						}
					    }
					    else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 121, input);

						throw nvae;
					    }
					}
					else if ( ((LA116_111>=FORCED_END_OF_LINE && LA116_111<=WIKI)||(LA116_111>=POUND && LA116_111<=67)||(LA116_111>=69 && LA116_111<=79)) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 111, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_97>=FORCED_END_OF_LINE && LA116_97<=WIKI)||(LA116_97>=POUND && LA116_97<=51)||(LA116_97>=53 && LA116_97<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 97, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_78>=FORCED_END_OF_LINE && LA116_78<=WIKI)||(LA116_78>=POUND && LA116_78<=47)||(LA116_78>=49 && LA116_78<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 78, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_59>=FORCED_END_OF_LINE && LA116_59<=WIKI)||(LA116_59>=POUND && LA116_59<=62)||(LA116_59>=64 && LA116_59<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 59, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_39>=FORCED_END_OF_LINE && LA116_39<=WIKI)||(LA116_39>=POUND && LA116_39<=67)||(LA116_39>=69 && LA116_39<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 39, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_21>=FORCED_END_OF_LINE && LA116_21<=WIKI)||(LA116_21>=POUND && LA116_21<=51)||(LA116_21>=53 && LA116_21<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 21, input);

			throw nvae;
		    }
		    }
		    break;
		case 59:
		    {
		    switch ( input.LA(3) ) {
		    case 67:
			{
			int LA116_40 = input.LA(4);

			if ( (LA116_40==52) ) {
			    int LA116_60 = input.LA(5);

			    if ( (LA116_60==64) ) {
				int LA116_79 = input.LA(6);

				if ( (LA116_79==51) ) {
				    int LA116_98 = input.LA(7);

				    if ( (LA116_98==52) ) {
					int LA116_112 = input.LA(8);

					if ( (LA116_112==49) ) {
					    int LA116_122 = input.LA(9);

					    if ( (LA116_122==52) ) {
						int LA116_129 = input.LA(10);

						if ( ((LA116_129>=FORCED_END_OF_LINE && LA116_129<=WIKI)||(LA116_129>=POUND && LA116_129<=INSIGNIFICANT_CHAR)||(LA116_129>=45 && LA116_129<=79)) ) {
						    alt116=2;
						}
						else if ( (LA116_129==44) ) {
						    int LA116_34 = input.LA(11);

						    if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
							alt116=1;
						    }
						    else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
							alt116=2;
						    }
						    else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

							throw nvae;
						    }
						}
						else {
						    if (backtracking>0) {failed=true; return link;}
						    NoViableAltException nvae =
							new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 129, input);

						    throw nvae;
						}
					    }
					    else if ( ((LA116_122>=FORCED_END_OF_LINE && LA116_122<=WIKI)||(LA116_122>=POUND && LA116_122<=51)||(LA116_122>=53 && LA116_122<=79)) ) {
						alt116=2;
					    }
					    else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 122, input);

						throw nvae;
					    }
					}
					else if ( ((LA116_112>=FORCED_END_OF_LINE && LA116_112<=WIKI)||(LA116_112>=POUND && LA116_112<=48)||(LA116_112>=50 && LA116_112<=79)) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 112, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_98>=FORCED_END_OF_LINE && LA116_98<=WIKI)||(LA116_98>=POUND && LA116_98<=51)||(LA116_98>=53 && LA116_98<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 98, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_79>=FORCED_END_OF_LINE && LA116_79<=WIKI)||(LA116_79>=POUND && LA116_79<=50)||(LA116_79>=52 && LA116_79<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 79, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_60>=FORCED_END_OF_LINE && LA116_60<=WIKI)||(LA116_60>=POUND && LA116_60<=63)||(LA116_60>=65 && LA116_60<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 60, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_40>=FORCED_END_OF_LINE && LA116_40<=WIKI)||(LA116_40>=POUND && LA116_40<=51)||(LA116_40>=53 && LA116_40<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 40, input);

			    throw nvae;
			}
			}
			break;
		    case 64:
			{
			int LA116_41 = input.LA(4);

			if ( (LA116_41==65) ) {
			    int LA116_61 = input.LA(5);

			    if ( (LA116_61==66) ) {
				int LA116_80 = input.LA(6);

				if ( (LA116_80==64) ) {
				    int LA116_99 = input.LA(7);

				    if ( (LA116_99==54) ) {
					int LA116_113 = input.LA(8);

					if ( (LA116_113==54) ) {
					    int LA116_123 = input.LA(9);

					    if ( ((LA116_123>=FORCED_END_OF_LINE && LA116_123<=WIKI)||(LA116_123>=POUND && LA116_123<=INSIGNIFICANT_CHAR)||(LA116_123>=45 && LA116_123<=79)) ) {
						alt116=2;
					    }
					    else if ( (LA116_123==44) ) {
						int LA116_34 = input.LA(10);

						if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
						    alt116=1;
						}
						else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
						    alt116=2;
						}
						else {
						    if (backtracking>0) {failed=true; return link;}
						    NoViableAltException nvae =
							new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

						    throw nvae;
						}
					    }
					    else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 123, input);

						throw nvae;
					    }
					}
					else if ( ((LA116_113>=FORCED_END_OF_LINE && LA116_113<=WIKI)||(LA116_113>=POUND && LA116_113<=53)||(LA116_113>=55 && LA116_113<=79)) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 113, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_99>=FORCED_END_OF_LINE && LA116_99<=WIKI)||(LA116_99>=POUND && LA116_99<=53)||(LA116_99>=55 && LA116_99<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 99, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_80>=FORCED_END_OF_LINE && LA116_80<=WIKI)||(LA116_80>=POUND && LA116_80<=63)||(LA116_80>=65 && LA116_80<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 80, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_61>=FORCED_END_OF_LINE && LA116_61<=WIKI)||(LA116_61>=POUND && LA116_61<=65)||(LA116_61>=67 && LA116_61<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 61, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_41>=FORCED_END_OF_LINE && LA116_41<=WIKI)||(LA116_41>=POUND && LA116_41<=64)||(LA116_41>=66 && LA116_41<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 41, input);

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
		    case BRACE_CLOSE:
		    case COLON_SLASH:
		    case SLASH:
		    case TABLE_OF_CONTENTS_OPEN_MARKUP:
		    case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		    case INSIGNIFICANT_CHAR:
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
		    case 65:
		    case 66:
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
		    case 79:
			{
			alt116=2;
			}
			break;
		    default:
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 22, input);

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
		case BRACE_CLOSE:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
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
		case 55:
		case 56:
		case 57:
		case 58:
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
		case 79:
		    {
		    alt116=2;
		    }
		    break;
		default:
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 6, input);

		    throw nvae;
		}

		}
		break;
	    case 69:
		{
		switch ( input.LA(2) ) {
		case 67:
		    {
		    int LA116_23 = input.LA(3);

		    if ( (LA116_23==67) ) {
			int LA116_42 = input.LA(4);

			if ( (LA116_42==70) ) {
			    int LA116_62 = input.LA(5);

			    if ( (LA116_62==50) ) {
				int LA116_81 = input.LA(6);

				if ( (LA116_81==71) ) {
				    int LA116_100 = input.LA(7);

				    if ( (LA116_100==59) ) {
					int LA116_114 = input.LA(8);

					if ( ((LA116_114>=FORCED_END_OF_LINE && LA116_114<=WIKI)||(LA116_114>=POUND && LA116_114<=INSIGNIFICANT_CHAR)||(LA116_114>=45 && LA116_114<=79)) ) {
					    alt116=2;
					}
					else if ( (LA116_114==44) ) {
					    int LA116_34 = input.LA(9);

					    if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
						alt116=1;
					    }
					    else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
						alt116=2;
					    }
					    else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

						throw nvae;
					    }
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 114, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_100>=FORCED_END_OF_LINE && LA116_100<=WIKI)||(LA116_100>=POUND && LA116_100<=58)||(LA116_100>=60 && LA116_100<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 100, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_81>=FORCED_END_OF_LINE && LA116_81<=WIKI)||(LA116_81>=POUND && LA116_81<=70)||(LA116_81>=72 && LA116_81<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 81, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_62>=FORCED_END_OF_LINE && LA116_62<=WIKI)||(LA116_62>=POUND && LA116_62<=49)||(LA116_62>=51 && LA116_62<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 62, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_42>=FORCED_END_OF_LINE && LA116_42<=WIKI)||(LA116_42>=POUND && LA116_42<=69)||(LA116_42>=71 && LA116_42<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 42, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_23>=FORCED_END_OF_LINE && LA116_23<=WIKI)||(LA116_23>=POUND && LA116_23<=66)||(LA116_23>=68 && LA116_23<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 23, input);

			throw nvae;
		    }
		    }
		    break;
		case 72:
		    {
		    int LA116_24 = input.LA(3);

		    if ( (LA116_24==64) ) {
			int LA116_43 = input.LA(4);

			if ( (LA116_43==68) ) {
			    int LA116_63 = input.LA(5);

			    if ( (LA116_63==64) ) {
				int LA116_82 = input.LA(6);

				if ( ((LA116_82>=FORCED_END_OF_LINE && LA116_82<=WIKI)||(LA116_82>=POUND && LA116_82<=INSIGNIFICANT_CHAR)||(LA116_82>=45 && LA116_82<=79)) ) {
				    alt116=2;
				}
				else if ( (LA116_82==44) ) {
				    int LA116_34 = input.LA(7);

				    if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
					alt116=1;
				    }
				    else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

					throw nvae;
				    }
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 82, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_63>=FORCED_END_OF_LINE && LA116_63<=WIKI)||(LA116_63>=POUND && LA116_63<=63)||(LA116_63>=65 && LA116_63<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 63, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_43>=FORCED_END_OF_LINE && LA116_43<=WIKI)||(LA116_43>=POUND && LA116_43<=67)||(LA116_43>=69 && LA116_43<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 43, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_24>=FORCED_END_OF_LINE && LA116_24<=WIKI)||(LA116_24>=POUND && LA116_24<=63)||(LA116_24>=65 && LA116_24<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 24, input);

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
		case BRACE_CLOSE:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
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
		case 68:
		case 69:
		case 70:
		case 71:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
		case 79:
		    {
		    alt116=2;
		    }
		    break;
		default:
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 7, input);

		    throw nvae;
		}

		}
		break;
	    case 62:
		{
		switch ( input.LA(2) ) {
		case 50:
		    {
		    switch ( input.LA(3) ) {
		    case 56:
			{
			int LA116_44 = input.LA(4);

			if ( (LA116_44==73) ) {
			    int LA116_64 = input.LA(5);

			    if ( (LA116_64==54) ) {
				int LA116_83 = input.LA(6);

				if ( (LA116_83==59) ) {
				    int LA116_101 = input.LA(7);

				    if ( (LA116_101==51) ) {
					int LA116_115 = input.LA(8);

					if ( (LA116_115==52) ) {
					    int LA116_124 = input.LA(9);

					    if ( (LA116_124==49) ) {
						int LA116_130 = input.LA(10);

						if ( (LA116_130==52) ) {
						    int LA116_133 = input.LA(11);

						    if ( ((LA116_133>=FORCED_END_OF_LINE && LA116_133<=WIKI)||(LA116_133>=POUND && LA116_133<=INSIGNIFICANT_CHAR)||(LA116_133>=45 && LA116_133<=79)) ) {
							alt116=2;
						    }
						    else if ( (LA116_133==44) ) {
							int LA116_34 = input.LA(12);

							if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
							    alt116=1;
							}
							else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
							    alt116=2;
							}
							else {
							    if (backtracking>0) {failed=true; return link;}
							    NoViableAltException nvae =
								new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

							    throw nvae;
							}
						    }
						    else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 133, input);

							throw nvae;
						    }
						}
						else if ( ((LA116_130>=FORCED_END_OF_LINE && LA116_130<=WIKI)||(LA116_130>=POUND && LA116_130<=51)||(LA116_130>=53 && LA116_130<=79)) ) {
						    alt116=2;
						}
						else {
						    if (backtracking>0) {failed=true; return link;}
						    NoViableAltException nvae =
							new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 130, input);

						    throw nvae;
						}
					    }
					    else if ( ((LA116_124>=FORCED_END_OF_LINE && LA116_124<=WIKI)||(LA116_124>=POUND && LA116_124<=48)||(LA116_124>=50 && LA116_124<=79)) ) {
						alt116=2;
					    }
					    else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 124, input);

						throw nvae;
					    }
					}
					else if ( ((LA116_115>=FORCED_END_OF_LINE && LA116_115<=WIKI)||(LA116_115>=POUND && LA116_115<=51)||(LA116_115>=53 && LA116_115<=79)) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 115, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_101>=FORCED_END_OF_LINE && LA116_101<=WIKI)||(LA116_101>=POUND && LA116_101<=50)||(LA116_101>=52 && LA116_101<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 101, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_83>=FORCED_END_OF_LINE && LA116_83<=WIKI)||(LA116_83>=POUND && LA116_83<=58)||(LA116_83>=60 && LA116_83<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 83, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_64>=FORCED_END_OF_LINE && LA116_64<=WIKI)||(LA116_64>=POUND && LA116_64<=53)||(LA116_64>=55 && LA116_64<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 64, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_44>=FORCED_END_OF_LINE && LA116_44<=WIKI)||(LA116_44>=POUND && LA116_44<=72)||(LA116_44>=74 && LA116_44<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 44, input);

			    throw nvae;
			}
			}
			break;
		    case 49:
			{
			int LA116_45 = input.LA(4);

			if ( (LA116_45==52) ) {
			    int LA116_65 = input.LA(5);

			    if ( (LA116_65==51) ) {
				int LA116_84 = input.LA(6);

				if ( (LA116_84==52) ) {
				    int LA116_102 = input.LA(7);

				    if ( (LA116_102==49) ) {
					int LA116_116 = input.LA(8);

					if ( (LA116_116==52) ) {
					    int LA116_125 = input.LA(9);

					    if ( ((LA116_125>=FORCED_END_OF_LINE && LA116_125<=WIKI)||(LA116_125>=POUND && LA116_125<=INSIGNIFICANT_CHAR)||(LA116_125>=45 && LA116_125<=79)) ) {
						alt116=2;
					    }
					    else if ( (LA116_125==44) ) {
						int LA116_34 = input.LA(10);

						if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
						    alt116=1;
						}
						else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
						    alt116=2;
						}
						else {
						    if (backtracking>0) {failed=true; return link;}
						    NoViableAltException nvae =
							new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

						    throw nvae;
						}
					    }
					    else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 125, input);

						throw nvae;
					    }
					}
					else if ( ((LA116_116>=FORCED_END_OF_LINE && LA116_116<=WIKI)||(LA116_116>=POUND && LA116_116<=51)||(LA116_116>=53 && LA116_116<=79)) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 116, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_102>=FORCED_END_OF_LINE && LA116_102<=WIKI)||(LA116_102>=POUND && LA116_102<=48)||(LA116_102>=50 && LA116_102<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 102, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_84>=FORCED_END_OF_LINE && LA116_84<=WIKI)||(LA116_84>=POUND && LA116_84<=51)||(LA116_84>=53 && LA116_84<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 84, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_65>=FORCED_END_OF_LINE && LA116_65<=WIKI)||(LA116_65>=POUND && LA116_65<=50)||(LA116_65>=52 && LA116_65<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 65, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_45>=FORCED_END_OF_LINE && LA116_45<=WIKI)||(LA116_45>=POUND && LA116_45<=51)||(LA116_45>=53 && LA116_45<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 45, input);

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
		    case BRACE_CLOSE:
		    case COLON_SLASH:
		    case SLASH:
		    case TABLE_OF_CONTENTS_OPEN_MARKUP:
		    case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		    case INSIGNIFICANT_CHAR:
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
		    case 79:
			{
			alt116=2;
			}
			break;
		    default:
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 25, input);

			throw nvae;
		    }

		    }
		    break;
		case 70:
		    {
		    int LA116_26 = input.LA(3);

		    if ( (LA116_26==51) ) {
			int LA116_46 = input.LA(4);

			if ( (LA116_46==52) ) {
			    int LA116_66 = input.LA(5);

			    if ( (LA116_66==49) ) {
				int LA116_85 = input.LA(6);

				if ( (LA116_85==52) ) {
				    int LA116_103 = input.LA(7);

				    if ( ((LA116_103>=FORCED_END_OF_LINE && LA116_103<=WIKI)||(LA116_103>=POUND && LA116_103<=INSIGNIFICANT_CHAR)||(LA116_103>=45 && LA116_103<=79)) ) {
					alt116=2;
				    }
				    else if ( (LA116_103==44) ) {
					int LA116_34 = input.LA(8);

					if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
					    alt116=1;
					}
					else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

					    throw nvae;
					}
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 103, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_85>=FORCED_END_OF_LINE && LA116_85<=WIKI)||(LA116_85>=POUND && LA116_85<=51)||(LA116_85>=53 && LA116_85<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 85, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_66>=FORCED_END_OF_LINE && LA116_66<=WIKI)||(LA116_66>=POUND && LA116_66<=48)||(LA116_66>=50 && LA116_66<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 66, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_46>=FORCED_END_OF_LINE && LA116_46<=WIKI)||(LA116_46>=POUND && LA116_46<=51)||(LA116_46>=53 && LA116_46<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 46, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_26>=FORCED_END_OF_LINE && LA116_26<=WIKI)||(LA116_26>=POUND && LA116_26<=50)||(LA116_26>=52 && LA116_26<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 26, input);

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
		case BRACE_CLOSE:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
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
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
		case 79:
		    {
		    alt116=2;
		    }
		    break;
		default:
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 8, input);

		    throw nvae;
		}

		}
		break;
	    case 74:
		{
		int LA116_9 = input.LA(2);

		if ( (LA116_9==64) ) {
		    int LA116_27 = input.LA(3);

		    if ( (LA116_27==67) ) {
			int LA116_47 = input.LA(4);

			if ( (LA116_47==59) ) {
			    int LA116_67 = input.LA(5);

			    if ( (LA116_67==48) ) {
				int LA116_86 = input.LA(6);

				if ( (LA116_86==75) ) {
				    int LA116_104 = input.LA(7);

				    if ( ((LA116_104>=FORCED_END_OF_LINE && LA116_104<=WIKI)||(LA116_104>=POUND && LA116_104<=INSIGNIFICANT_CHAR)||(LA116_104>=45 && LA116_104<=79)) ) {
					alt116=2;
				    }
				    else if ( (LA116_104==44) ) {
					int LA116_34 = input.LA(8);

					if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
					    alt116=1;
					}
					else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

					    throw nvae;
					}
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 104, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_86>=FORCED_END_OF_LINE && LA116_86<=WIKI)||(LA116_86>=POUND && LA116_86<=74)||(LA116_86>=76 && LA116_86<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 86, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_67>=FORCED_END_OF_LINE && LA116_67<=WIKI)||(LA116_67>=POUND && LA116_67<=47)||(LA116_67>=49 && LA116_67<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 67, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_47>=FORCED_END_OF_LINE && LA116_47<=WIKI)||(LA116_47>=POUND && LA116_47<=58)||(LA116_47>=60 && LA116_47<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 47, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_27>=FORCED_END_OF_LINE && LA116_27<=WIKI)||(LA116_27>=POUND && LA116_27<=66)||(LA116_27>=68 && LA116_27<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 27, input);

			throw nvae;
		    }
		}
		else if ( ((LA116_9>=FORCED_END_OF_LINE && LA116_9<=WIKI)||(LA116_9>=POUND && LA116_9<=63)||(LA116_9>=65 && LA116_9<=79)) ) {
		    alt116=2;
		}
		else {
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 9, input);

		    throw nvae;
		}
		}
		break;
	    case 61:
		{
		int LA116_10 = input.LA(2);

		if ( (LA116_10==68) ) {
		    int LA116_28 = input.LA(3);

		    if ( (LA116_28==52) ) {
			int LA116_48 = input.LA(4);

			if ( (LA116_48==73) ) {
			    int LA116_68 = input.LA(5);

			    if ( (LA116_68==61) ) {
				int LA116_87 = input.LA(6);

				if ( (LA116_87==68) ) {
				    int LA116_105 = input.LA(7);

				    if ( (LA116_105==64) ) {
					int LA116_117 = input.LA(8);

					if ( (LA116_117==73) ) {
					    int LA116_126 = input.LA(9);

					    if ( ((LA116_126>=FORCED_END_OF_LINE && LA116_126<=WIKI)||(LA116_126>=POUND && LA116_126<=INSIGNIFICANT_CHAR)||(LA116_126>=45 && LA116_126<=79)) ) {
						alt116=2;
					    }
					    else if ( (LA116_126==44) ) {
						int LA116_34 = input.LA(10);

						if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
						    alt116=1;
						}
						else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
						    alt116=2;
						}
						else {
						    if (backtracking>0) {failed=true; return link;}
						    NoViableAltException nvae =
							new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

						    throw nvae;
						}
					    }
					    else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 126, input);

						throw nvae;
					    }
					}
					else if ( ((LA116_117>=FORCED_END_OF_LINE && LA116_117<=WIKI)||(LA116_117>=POUND && LA116_117<=72)||(LA116_117>=74 && LA116_117<=79)) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 117, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_105>=FORCED_END_OF_LINE && LA116_105<=WIKI)||(LA116_105>=POUND && LA116_105<=63)||(LA116_105>=65 && LA116_105<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 105, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_87>=FORCED_END_OF_LINE && LA116_87<=WIKI)||(LA116_87>=POUND && LA116_87<=67)||(LA116_87>=69 && LA116_87<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 87, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_68>=FORCED_END_OF_LINE && LA116_68<=WIKI)||(LA116_68>=POUND && LA116_68<=60)||(LA116_68>=62 && LA116_68<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 68, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_48>=FORCED_END_OF_LINE && LA116_48<=WIKI)||(LA116_48>=POUND && LA116_48<=72)||(LA116_48>=74 && LA116_48<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 48, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_28>=FORCED_END_OF_LINE && LA116_28<=WIKI)||(LA116_28>=POUND && LA116_28<=51)||(LA116_28>=53 && LA116_28<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 28, input);

			throw nvae;
		    }
		}
		else if ( ((LA116_10>=FORCED_END_OF_LINE && LA116_10<=WIKI)||(LA116_10>=POUND && LA116_10<=67)||(LA116_10>=69 && LA116_10<=79)) ) {
		    alt116=2;
		}
		else {
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 10, input);

		    throw nvae;
		}
		}
		break;
	    case 76:
		{
		switch ( input.LA(2) ) {
		case 51:
		    {
		    int LA116_29 = input.LA(3);

		    if ( (LA116_29==52) ) {
			int LA116_49 = input.LA(4);

			if ( (LA116_49==49) ) {
			    int LA116_69 = input.LA(5);

			    if ( (LA116_69==52) ) {
				int LA116_88 = input.LA(6);

				if ( (LA116_88==44) ) {
				    int LA116_34 = input.LA(7);

				    if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
					alt116=1;
				    }
				    else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_88>=FORCED_END_OF_LINE && LA116_88<=WIKI)||(LA116_88>=POUND && LA116_88<=INSIGNIFICANT_CHAR)||(LA116_88>=45 && LA116_88<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 88, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_69>=FORCED_END_OF_LINE && LA116_69<=WIKI)||(LA116_69>=POUND && LA116_69<=51)||(LA116_69>=53 && LA116_69<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 69, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_49>=FORCED_END_OF_LINE && LA116_49<=WIKI)||(LA116_49>=POUND && LA116_49<=48)||(LA116_49>=50 && LA116_49<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 49, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_29>=FORCED_END_OF_LINE && LA116_29<=WIKI)||(LA116_29>=POUND && LA116_29<=51)||(LA116_29>=53 && LA116_29<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 29, input);

			throw nvae;
		    }
		    }
		    break;
		case 52:
		    {
		    int LA116_30 = input.LA(3);

		    if ( (LA116_30==67) ) {
			int LA116_50 = input.LA(4);

			if ( (LA116_50==67) ) {
			    int LA116_70 = input.LA(5);

			    if ( (LA116_70==54) ) {
				int LA116_89 = input.LA(6);

				if ( (LA116_89==77) ) {
				    int LA116_106 = input.LA(7);

				    if ( (LA116_106==51) ) {
					int LA116_118 = input.LA(8);

					if ( (LA116_118==52) ) {
					    int LA116_127 = input.LA(9);

					    if ( (LA116_127==49) ) {
						int LA116_131 = input.LA(10);

						if ( (LA116_131==52) ) {
						    int LA116_134 = input.LA(11);

						    if ( ((LA116_134>=FORCED_END_OF_LINE && LA116_134<=WIKI)||(LA116_134>=POUND && LA116_134<=INSIGNIFICANT_CHAR)||(LA116_134>=45 && LA116_134<=79)) ) {
							alt116=2;
						    }
						    else if ( (LA116_134==44) ) {
							int LA116_34 = input.LA(12);

							if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
							    alt116=1;
							}
							else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
							    alt116=2;
							}
							else {
							    if (backtracking>0) {failed=true; return link;}
							    NoViableAltException nvae =
								new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

							    throw nvae;
							}
						    }
						    else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 134, input);

							throw nvae;
						    }
						}
						else if ( ((LA116_131>=FORCED_END_OF_LINE && LA116_131<=WIKI)||(LA116_131>=POUND && LA116_131<=51)||(LA116_131>=53 && LA116_131<=79)) ) {
						    alt116=2;
						}
						else {
						    if (backtracking>0) {failed=true; return link;}
						    NoViableAltException nvae =
							new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 131, input);

						    throw nvae;
						}
					    }
					    else if ( ((LA116_127>=FORCED_END_OF_LINE && LA116_127<=WIKI)||(LA116_127>=POUND && LA116_127<=48)||(LA116_127>=50 && LA116_127<=79)) ) {
						alt116=2;
					    }
					    else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 127, input);

						throw nvae;
					    }
					}
					else if ( ((LA116_118>=FORCED_END_OF_LINE && LA116_118<=WIKI)||(LA116_118>=POUND && LA116_118<=51)||(LA116_118>=53 && LA116_118<=79)) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 118, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_106>=FORCED_END_OF_LINE && LA116_106<=WIKI)||(LA116_106>=POUND && LA116_106<=50)||(LA116_106>=52 && LA116_106<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 106, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_89>=FORCED_END_OF_LINE && LA116_89<=WIKI)||(LA116_89>=POUND && LA116_89<=76)||(LA116_89>=78 && LA116_89<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 89, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_70>=FORCED_END_OF_LINE && LA116_70<=WIKI)||(LA116_70>=POUND && LA116_70<=53)||(LA116_70>=55 && LA116_70<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 70, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_50>=FORCED_END_OF_LINE && LA116_50<=WIKI)||(LA116_50>=POUND && LA116_50<=66)||(LA116_50>=68 && LA116_50<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 50, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_30>=FORCED_END_OF_LINE && LA116_30<=WIKI)||(LA116_30>=POUND && LA116_30<=66)||(LA116_30>=68 && LA116_30<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 30, input);

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
		case BRACE_CLOSE:
		case COLON_SLASH:
		case SLASH:
		case TABLE_OF_CONTENTS_OPEN_MARKUP:
		case TABLE_OF_CONTENTS_CLOSE_MARKUP:
		case INSIGNIFICANT_CHAR:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
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
		case 79:
		    {
		    alt116=2;
		    }
		    break;
		default:
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 11, input);

		    throw nvae;
		}

		}
		break;
	    case 78:
		{
		int LA116_12 = input.LA(2);

		if ( (LA116_12==71) ) {
		    int LA116_31 = input.LA(3);

		    if ( (LA116_31==59) ) {
			int LA116_51 = input.LA(4);

			if ( (LA116_51==70) ) {
			    int LA116_71 = input.LA(5);

			    if ( (LA116_71==48) ) {
				int LA116_90 = input.LA(6);

				if ( (LA116_90==67) ) {
				    int LA116_107 = input.LA(7);

				    if ( ((LA116_107>=FORCED_END_OF_LINE && LA116_107<=WIKI)||(LA116_107>=POUND && LA116_107<=INSIGNIFICANT_CHAR)||(LA116_107>=45 && LA116_107<=79)) ) {
					alt116=2;
				    }
				    else if ( (LA116_107==44) ) {
					int LA116_34 = input.LA(8);

					if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
					    alt116=1;
					}
					else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

					    throw nvae;
					}
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 107, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_90>=FORCED_END_OF_LINE && LA116_90<=WIKI)||(LA116_90>=POUND && LA116_90<=66)||(LA116_90>=68 && LA116_90<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 90, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_71>=FORCED_END_OF_LINE && LA116_71<=WIKI)||(LA116_71>=POUND && LA116_71<=47)||(LA116_71>=49 && LA116_71<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 71, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_51>=FORCED_END_OF_LINE && LA116_51<=WIKI)||(LA116_51>=POUND && LA116_51<=69)||(LA116_51>=71 && LA116_51<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 51, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_31>=FORCED_END_OF_LINE && LA116_31<=WIKI)||(LA116_31>=POUND && LA116_31<=58)||(LA116_31>=60 && LA116_31<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 31, input);

			throw nvae;
		    }
		}
		else if ( ((LA116_12>=FORCED_END_OF_LINE && LA116_12<=WIKI)||(LA116_12>=POUND && LA116_12<=70)||(LA116_12>=72 && LA116_12<=79)) ) {
		    alt116=2;
		}
		else {
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 12, input);

		    throw nvae;
		}
		}
		break;
	    case 51:
		{
		int LA116_13 = input.LA(2);

		if ( (LA116_13==52) ) {
		    int LA116_32 = input.LA(3);

		    if ( (LA116_32==49) ) {
			int LA116_52 = input.LA(4);

			if ( (LA116_52==52) ) {
			    int LA116_72 = input.LA(5);

			    if ( (LA116_72==73) ) {
				int LA116_91 = input.LA(6);

				if ( (LA116_91==59) ) {
				    int LA116_108 = input.LA(7);

				    if ( (LA116_108==67) ) {
					int LA116_119 = input.LA(8);

					if ( (LA116_119==52) ) {
					    int LA116_128 = input.LA(9);

					    if ( (LA116_128==64) ) {
						int LA116_132 = input.LA(10);

						if ( ((LA116_132>=FORCED_END_OF_LINE && LA116_132<=WIKI)||(LA116_132>=POUND && LA116_132<=INSIGNIFICANT_CHAR)||(LA116_132>=45 && LA116_132<=79)) ) {
						    alt116=2;
						}
						else if ( (LA116_132==44) ) {
						    int LA116_34 = input.LA(11);

						    if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
							alt116=1;
						    }
						    else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
							alt116=2;
						    }
						    else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
							    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

							throw nvae;
						    }
						}
						else {
						    if (backtracking>0) {failed=true; return link;}
						    NoViableAltException nvae =
							new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 132, input);

						    throw nvae;
						}
					    }
					    else if ( ((LA116_128>=FORCED_END_OF_LINE && LA116_128<=WIKI)||(LA116_128>=POUND && LA116_128<=63)||(LA116_128>=65 && LA116_128<=79)) ) {
						alt116=2;
					    }
					    else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
						    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 128, input);

						throw nvae;
					    }
					}
					else if ( ((LA116_119>=FORCED_END_OF_LINE && LA116_119<=WIKI)||(LA116_119>=POUND && LA116_119<=51)||(LA116_119>=53 && LA116_119<=79)) ) {
					    alt116=2;
					}
					else {
					    if (backtracking>0) {failed=true; return link;}
					    NoViableAltException nvae =
						new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 119, input);

					    throw nvae;
					}
				    }
				    else if ( ((LA116_108>=FORCED_END_OF_LINE && LA116_108<=WIKI)||(LA116_108>=POUND && LA116_108<=66)||(LA116_108>=68 && LA116_108<=79)) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 108, input);

					throw nvae;
				    }
				}
				else if ( ((LA116_91>=FORCED_END_OF_LINE && LA116_91<=WIKI)||(LA116_91>=POUND && LA116_91<=58)||(LA116_91>=60 && LA116_91<=79)) ) {
				    alt116=2;
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 91, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_72>=FORCED_END_OF_LINE && LA116_72<=WIKI)||(LA116_72>=POUND && LA116_72<=72)||(LA116_72>=74 && LA116_72<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 72, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_52>=FORCED_END_OF_LINE && LA116_52<=WIKI)||(LA116_52>=POUND && LA116_52<=51)||(LA116_52>=53 && LA116_52<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 52, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_32>=FORCED_END_OF_LINE && LA116_32<=WIKI)||(LA116_32>=POUND && LA116_32<=48)||(LA116_32>=50 && LA116_32<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 32, input);

			throw nvae;
		    }
		}
		else if ( ((LA116_13>=FORCED_END_OF_LINE && LA116_13<=WIKI)||(LA116_13>=POUND && LA116_13<=51)||(LA116_13>=53 && LA116_13<=79)) ) {
		    alt116=2;
		}
		else {
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 13, input);

		    throw nvae;
		}
		}
		break;
	    case 79:
		{
		int LA116_14 = input.LA(2);

		if ( (LA116_14==51) ) {
		    int LA116_33 = input.LA(3);

		    if ( (LA116_33==52) ) {
			int LA116_53 = input.LA(4);

			if ( (LA116_53==49) ) {
			    int LA116_73 = input.LA(5);

			    if ( (LA116_73==52) ) {
				int LA116_92 = input.LA(6);

				if ( ((LA116_92>=FORCED_END_OF_LINE && LA116_92<=WIKI)||(LA116_92>=POUND && LA116_92<=INSIGNIFICANT_CHAR)||(LA116_92>=45 && LA116_92<=79)) ) {
				    alt116=2;
				}
				else if ( (LA116_92==44) ) {
				    int LA116_34 = input.LA(7);

				    if ( ((LA116_34>=FORCED_END_OF_LINE && LA116_34<=WIKI)||(LA116_34>=POUND && LA116_34<=EQUAL)||(LA116_34>=ITAL && LA116_34<=NOWIKI_CLOSE)||(LA116_34>=IMAGE_CLOSE && LA116_34<=79)) ) {
					alt116=1;
				    }
				    else if ( (LA116_34==PIPE||LA116_34==LINK_CLOSE) ) {
					alt116=2;
				    }
				    else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
					    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 34, input);

					throw nvae;
				    }
				}
				else {
				    if (backtracking>0) {failed=true; return link;}
				    NoViableAltException nvae =
					new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 92, input);

				    throw nvae;
				}
			    }
			    else if ( ((LA116_73>=FORCED_END_OF_LINE && LA116_73<=WIKI)||(LA116_73>=POUND && LA116_73<=51)||(LA116_73>=53 && LA116_73<=79)) ) {
				alt116=2;
			    }
			    else {
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
				    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 73, input);

				throw nvae;
			    }
			}
			else if ( ((LA116_53>=FORCED_END_OF_LINE && LA116_53<=WIKI)||(LA116_53>=POUND && LA116_53<=48)||(LA116_53>=50 && LA116_53<=79)) ) {
			    alt116=2;
			}
			else {
			    if (backtracking>0) {failed=true; return link;}
			    NoViableAltException nvae =
				new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 53, input);

			    throw nvae;
			}
		    }
		    else if ( ((LA116_33>=FORCED_END_OF_LINE && LA116_33<=WIKI)||(LA116_33>=POUND && LA116_33<=51)||(LA116_33>=53 && LA116_33<=79)) ) {
			alt116=2;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			NoViableAltException nvae =
			    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 33, input);

			throw nvae;
		    }
		}
		else if ( ((LA116_14>=FORCED_END_OF_LINE && LA116_14<=WIKI)||(LA116_14>=POUND && LA116_14<=50)||(LA116_14>=52 && LA116_14<=79)) ) {
		    alt116=2;
		}
		else {
		    if (backtracking>0) {failed=true; return link;}
		    NoViableAltException nvae =
			new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 14, input);

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
	    case BRACE_CLOSE:
	    case COLON_SLASH:
	    case SLASH:
	    case TABLE_OF_CONTENTS_OPEN_MARKUP:
	    case TABLE_OF_CONTENTS_CLOSE_MARKUP:
	    case INSIGNIFICANT_CHAR:
	    case 44:
	    case 46:
	    case 48:
	    case 49:
	    case 50:
	    case 52:
	    case 54:
	    case 55:
	    case 56:
	    case 58:
	    case 59:
	    case 64:
	    case 65:
	    case 66:
	    case 67:
	    case 68:
	    case 70:
	    case 71:
	    case 72:
	    case 73:
	    case 75:
	    case 77:
		{
		alt116=2;
		}
		break;
	    default:
		if (backtracking>0) {failed=true; return link;}
		NoViableAltException nvae =
		    new NoViableAltException("796:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 116, 0, input);

		throw nvae;
	    }

	    switch (alt116) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:797:4: li= link_interwiki_uri ':' p= link_interwiki_pagename
		    {
		    pushFollow(FOLLOW_link_interwiki_uri_in_link_address3259);
		    li=link_interwiki_uri();
		    _fsp--;
		    if (failed) return link;
		    match(input,44,FOLLOW_44_in_link_address3262); if (failed) return link;
		    pushFollow(FOLLOW_link_interwiki_pagename_in_link_address3269);
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
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:801:4: lu= link_uri
		    {
		    pushFollow(FOLLOW_link_uri_in_link_address3280);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:803:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );
    public final InterwikiLinkNode link_interwiki_uri() throws RecognitionException {
	InterwikiLinkNode interwiki =  null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:804:2: ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' )
	    int alt117=20;
	    switch ( input.LA(1) ) {
	    case 45:
		{
		alt117=1;
		}
		break;
	    case 47:
		{
		alt117=2;
		}
		break;
	    case 53:
		{
		alt117=3;
		}
		break;
	    case 57:
		{
		alt117=4;
		}
		break;
	    case 60:
		{
		alt117=5;
		}
		break;
	    case 63:
		{
		int LA117_6 = input.LA(2);

		if ( (LA117_6==59) ) {
		    int LA117_15 = input.LA(3);

		    if ( (LA117_15==64) ) {
			alt117=6;
		    }
		    else if ( (LA117_15==67) ) {
			alt117=7;
		    }
		    else {
			if (backtracking>0) {failed=true; return interwiki;}
			NoViableAltException nvae =
			    new NoViableAltException("803:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 117, 15, input);

			throw nvae;
		    }
		}
		else if ( (LA117_6==48) ) {
		    alt117=8;
		}
		else {
		    if (backtracking>0) {failed=true; return interwiki;}
		    NoViableAltException nvae =
			new NoViableAltException("803:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 117, 6, input);

		    throw nvae;
		}
		}
		break;
	    case 69:
		{
		int LA117_7 = input.LA(2);

		if ( (LA117_7==72) ) {
		    alt117=10;
		}
		else if ( (LA117_7==67) ) {
		    alt117=9;
		}
		else {
		    if (backtracking>0) {failed=true; return interwiki;}
		    NoViableAltException nvae =
			new NoViableAltException("803:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 117, 7, input);

		    throw nvae;
		}
		}
		break;
	    case 62:
		{
		int LA117_8 = input.LA(2);

		if ( (LA117_8==70) ) {
		    alt117=11;
		}
		else if ( (LA117_8==50) ) {
		    int LA117_20 = input.LA(3);

		    if ( (LA117_20==49) ) {
			alt117=12;
		    }
		    else if ( (LA117_20==56) ) {
			alt117=13;
		    }
		    else {
			if (backtracking>0) {failed=true; return interwiki;}
			NoViableAltException nvae =
			    new NoViableAltException("803:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 117, 20, input);

			throw nvae;
		    }
		}
		else {
		    if (backtracking>0) {failed=true; return interwiki;}
		    NoViableAltException nvae =
			new NoViableAltException("803:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 117, 8, input);

		    throw nvae;
		}
		}
		break;
	    case 74:
		{
		alt117=14;
		}
		break;
	    case 61:
		{
		alt117=15;
		}
		break;
	    case 76:
		{
		int LA117_11 = input.LA(2);

		if ( (LA117_11==51) ) {
		    alt117=17;
		}
		else if ( (LA117_11==52) ) {
		    alt117=16;
		}
		else {
		    if (backtracking>0) {failed=true; return interwiki;}
		    NoViableAltException nvae =
			new NoViableAltException("803:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 117, 11, input);

		    throw nvae;
		}
		}
		break;
	    case 78:
		{
		alt117=18;
		}
		break;
	    case 51:
		{
		alt117=19;
		}
		break;
	    case 79:
		{
		alt117=20;
		}
		break;
	    default:
		if (backtracking>0) {failed=true; return interwiki;}
		NoViableAltException nvae =
		    new NoViableAltException("803:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 117, 0, input);

		throw nvae;
	    }

	    switch (alt117) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:804:4: 'C' '2'
		    {
		    match(input,45,FOLLOW_45_in_link_interwiki_uri3296); if (failed) return interwiki;
		    match(input,46,FOLLOW_46_in_link_interwiki_uri3298); if (failed) return interwiki;

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:805:4: 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i'
		    {
		    match(input,47,FOLLOW_47_in_link_interwiki_uri3303); if (failed) return interwiki;
		    match(input,48,FOLLOW_48_in_link_interwiki_uri3305); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3307); if (failed) return interwiki;
		    match(input,50,FOLLOW_50_in_link_interwiki_uri3309); if (failed) return interwiki;
		    match(input,51,FOLLOW_51_in_link_interwiki_uri3311); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3313); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3315); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3317); if (failed) return interwiki;

		    }
		    break;
		case 3 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:806:4: 'F' 'l' 'i' 'c' 'k' 'r'
		    {
		    match(input,53,FOLLOW_53_in_link_interwiki_uri3322); if (failed) return interwiki;
		    match(input,54,FOLLOW_54_in_link_interwiki_uri3324); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3326); if (failed) return interwiki;
		    match(input,55,FOLLOW_55_in_link_interwiki_uri3328); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3330); if (failed) return interwiki;
		    match(input,56,FOLLOW_56_in_link_interwiki_uri3332); if (failed) return interwiki;

		    }
		    break;
		case 4 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:807:4: 'G' 'o' 'o' 'g' 'l' 'e'
		    {
		    match(input,57,FOLLOW_57_in_link_interwiki_uri3337); if (failed) return interwiki;
		    match(input,48,FOLLOW_48_in_link_interwiki_uri3339); if (failed) return interwiki;
		    match(input,48,FOLLOW_48_in_link_interwiki_uri3341); if (failed) return interwiki;
		    match(input,58,FOLLOW_58_in_link_interwiki_uri3343); if (failed) return interwiki;
		    match(input,54,FOLLOW_54_in_link_interwiki_uri3345); if (failed) return interwiki;
		    match(input,59,FOLLOW_59_in_link_interwiki_uri3347); if (failed) return interwiki;

		    }
		    break;
		case 5 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:808:4: 'J' 'S' 'P' 'W' 'i' 'k' 'i'
		    {
		    match(input,60,FOLLOW_60_in_link_interwiki_uri3352); if (failed) return interwiki;
		    match(input,61,FOLLOW_61_in_link_interwiki_uri3354); if (failed) return interwiki;
		    match(input,62,FOLLOW_62_in_link_interwiki_uri3356); if (failed) return interwiki;
		    match(input,51,FOLLOW_51_in_link_interwiki_uri3358); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3360); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3362); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3364); if (failed) return interwiki;

		    }
		    break;
		case 6 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:809:4: 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l'
		    {
		    match(input,63,FOLLOW_63_in_link_interwiki_uri3369); if (failed) return interwiki;
		    match(input,59,FOLLOW_59_in_link_interwiki_uri3371); if (failed) return interwiki;
		    match(input,64,FOLLOW_64_in_link_interwiki_uri3373); if (failed) return interwiki;
		    match(input,65,FOLLOW_65_in_link_interwiki_uri3375); if (failed) return interwiki;
		    match(input,66,FOLLOW_66_in_link_interwiki_uri3377); if (failed) return interwiki;
		    match(input,64,FOLLOW_64_in_link_interwiki_uri3379); if (failed) return interwiki;
		    match(input,54,FOLLOW_54_in_link_interwiki_uri3381); if (failed) return interwiki;
		    match(input,54,FOLLOW_54_in_link_interwiki_uri3383); if (failed) return interwiki;

		    }
		    break;
		case 7 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:810:4: 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i'
		    {
		    match(input,63,FOLLOW_63_in_link_interwiki_uri3388); if (failed) return interwiki;
		    match(input,59,FOLLOW_59_in_link_interwiki_uri3390); if (failed) return interwiki;
		    match(input,67,FOLLOW_67_in_link_interwiki_uri3392); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3394); if (failed) return interwiki;
		    match(input,64,FOLLOW_64_in_link_interwiki_uri3396); if (failed) return interwiki;
		    match(input,51,FOLLOW_51_in_link_interwiki_uri3398); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3400); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3402); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3404); if (failed) return interwiki;

		    }
		    break;
		case 8 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:811:4: 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n'
		    {
		    match(input,63,FOLLOW_63_in_link_interwiki_uri3409); if (failed) return interwiki;
		    match(input,48,FOLLOW_48_in_link_interwiki_uri3411); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3413); if (failed) return interwiki;
		    match(input,68,FOLLOW_68_in_link_interwiki_uri3415); if (failed) return interwiki;
		    match(input,63,FOLLOW_63_in_link_interwiki_uri3417); if (failed) return interwiki;
		    match(input,48,FOLLOW_48_in_link_interwiki_uri3419); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3421); if (failed) return interwiki;
		    match(input,68,FOLLOW_68_in_link_interwiki_uri3423); if (failed) return interwiki;

		    }
		    break;
		case 9 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:812:4: 'O' 'd' 'd' 'm' 'u' 's' 'e'
		    {
		    match(input,69,FOLLOW_69_in_link_interwiki_uri3428); if (failed) return interwiki;
		    match(input,67,FOLLOW_67_in_link_interwiki_uri3430); if (failed) return interwiki;
		    match(input,67,FOLLOW_67_in_link_interwiki_uri3432); if (failed) return interwiki;
		    match(input,70,FOLLOW_70_in_link_interwiki_uri3434); if (failed) return interwiki;
		    match(input,50,FOLLOW_50_in_link_interwiki_uri3436); if (failed) return interwiki;
		    match(input,71,FOLLOW_71_in_link_interwiki_uri3438); if (failed) return interwiki;
		    match(input,59,FOLLOW_59_in_link_interwiki_uri3440); if (failed) return interwiki;

		    }
		    break;
		case 10 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:813:4: 'O' 'h' 'a' 'n' 'a'
		    {
		    match(input,69,FOLLOW_69_in_link_interwiki_uri3445); if (failed) return interwiki;
		    match(input,72,FOLLOW_72_in_link_interwiki_uri3447); if (failed) return interwiki;
		    match(input,64,FOLLOW_64_in_link_interwiki_uri3449); if (failed) return interwiki;
		    match(input,68,FOLLOW_68_in_link_interwiki_uri3451); if (failed) return interwiki;
		    match(input,64,FOLLOW_64_in_link_interwiki_uri3453); if (failed) return interwiki;

		    }
		    break;
		case 11 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:814:4: 'P' 'm' 'W' 'i' 'k' 'i'
		    {
		    match(input,62,FOLLOW_62_in_link_interwiki_uri3458); if (failed) return interwiki;
		    match(input,70,FOLLOW_70_in_link_interwiki_uri3460); if (failed) return interwiki;
		    match(input,51,FOLLOW_51_in_link_interwiki_uri3462); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3464); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3466); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3468); if (failed) return interwiki;

		    }
		    break;
		case 12 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:815:4: 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i'
		    {
		    match(input,62,FOLLOW_62_in_link_interwiki_uri3473); if (failed) return interwiki;
		    match(input,50,FOLLOW_50_in_link_interwiki_uri3475); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3477); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3479); if (failed) return interwiki;
		    match(input,51,FOLLOW_51_in_link_interwiki_uri3481); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3483); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3485); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3487); if (failed) return interwiki;

		    }
		    break;
		case 13 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:816:4: 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i'
		    {
		    match(input,62,FOLLOW_62_in_link_interwiki_uri3492); if (failed) return interwiki;
		    match(input,50,FOLLOW_50_in_link_interwiki_uri3494); if (failed) return interwiki;
		    match(input,56,FOLLOW_56_in_link_interwiki_uri3496); if (failed) return interwiki;
		    match(input,73,FOLLOW_73_in_link_interwiki_uri3498); if (failed) return interwiki;
		    match(input,54,FOLLOW_54_in_link_interwiki_uri3500); if (failed) return interwiki;
		    match(input,59,FOLLOW_59_in_link_interwiki_uri3502); if (failed) return interwiki;
		    match(input,51,FOLLOW_51_in_link_interwiki_uri3504); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3506); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3508); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3510); if (failed) return interwiki;

		    }
		    break;
		case 14 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:817:4: 'R' 'a' 'd' 'e' 'o' 'x'
		    {
		    match(input,74,FOLLOW_74_in_link_interwiki_uri3515); if (failed) return interwiki;
		    match(input,64,FOLLOW_64_in_link_interwiki_uri3517); if (failed) return interwiki;
		    match(input,67,FOLLOW_67_in_link_interwiki_uri3519); if (failed) return interwiki;
		    match(input,59,FOLLOW_59_in_link_interwiki_uri3521); if (failed) return interwiki;
		    match(input,48,FOLLOW_48_in_link_interwiki_uri3523); if (failed) return interwiki;
		    match(input,75,FOLLOW_75_in_link_interwiki_uri3525); if (failed) return interwiki;

		    }
		    break;
		case 15 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:818:4: 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p'
		    {
		    match(input,61,FOLLOW_61_in_link_interwiki_uri3530); if (failed) return interwiki;
		    match(input,68,FOLLOW_68_in_link_interwiki_uri3532); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3534); if (failed) return interwiki;
		    match(input,73,FOLLOW_73_in_link_interwiki_uri3536); if (failed) return interwiki;
		    match(input,61,FOLLOW_61_in_link_interwiki_uri3538); if (failed) return interwiki;
		    match(input,68,FOLLOW_68_in_link_interwiki_uri3540); if (failed) return interwiki;
		    match(input,64,FOLLOW_64_in_link_interwiki_uri3542); if (failed) return interwiki;
		    match(input,73,FOLLOW_73_in_link_interwiki_uri3544); if (failed) return interwiki;

		    }
		    break;
		case 16 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:819:4: 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i'
		    {
		    match(input,76,FOLLOW_76_in_link_interwiki_uri3549); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3551); if (failed) return interwiki;
		    match(input,67,FOLLOW_67_in_link_interwiki_uri3553); if (failed) return interwiki;
		    match(input,67,FOLLOW_67_in_link_interwiki_uri3555); if (failed) return interwiki;
		    match(input,54,FOLLOW_54_in_link_interwiki_uri3557); if (failed) return interwiki;
		    match(input,77,FOLLOW_77_in_link_interwiki_uri3559); if (failed) return interwiki;
		    match(input,51,FOLLOW_51_in_link_interwiki_uri3561); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3563); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3565); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3567); if (failed) return interwiki;

		    }
		    break;
		case 17 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:820:4: 'T' 'W' 'i' 'k' 'i'
		    {
		    match(input,76,FOLLOW_76_in_link_interwiki_uri3572); if (failed) return interwiki;
		    match(input,51,FOLLOW_51_in_link_interwiki_uri3574); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3576); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3578); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3580); if (failed) return interwiki;

		    }
		    break;
		case 18 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:821:4: 'U' 's' 'e' 'm' 'o' 'd'
		    {
		    match(input,78,FOLLOW_78_in_link_interwiki_uri3585); if (failed) return interwiki;
		    match(input,71,FOLLOW_71_in_link_interwiki_uri3587); if (failed) return interwiki;
		    match(input,59,FOLLOW_59_in_link_interwiki_uri3589); if (failed) return interwiki;
		    match(input,70,FOLLOW_70_in_link_interwiki_uri3591); if (failed) return interwiki;
		    match(input,48,FOLLOW_48_in_link_interwiki_uri3593); if (failed) return interwiki;
		    match(input,67,FOLLOW_67_in_link_interwiki_uri3595); if (failed) return interwiki;

		    }
		    break;
		case 19 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:822:4: 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a'
		    {
		    match(input,51,FOLLOW_51_in_link_interwiki_uri3600); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3602); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3604); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3606); if (failed) return interwiki;
		    match(input,73,FOLLOW_73_in_link_interwiki_uri3608); if (failed) return interwiki;
		    match(input,59,FOLLOW_59_in_link_interwiki_uri3610); if (failed) return interwiki;
		    match(input,67,FOLLOW_67_in_link_interwiki_uri3612); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3614); if (failed) return interwiki;
		    match(input,64,FOLLOW_64_in_link_interwiki_uri3616); if (failed) return interwiki;

		    }
		    break;
		case 20 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:823:4: 'X' 'W' 'i' 'k' 'i'
		    {
		    match(input,79,FOLLOW_79_in_link_interwiki_uri3621); if (failed) return interwiki;
		    match(input,51,FOLLOW_51_in_link_interwiki_uri3623); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3625); if (failed) return interwiki;
		    match(input,49,FOLLOW_49_in_link_interwiki_uri3627); if (failed) return interwiki;
		    match(input,52,FOLLOW_52_in_link_interwiki_uri3629); if (failed) return interwiki;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:825:1: link_interwiki_pagename returns [StringBundler text = new StringBundler()] : (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ ;
    public final StringBundler link_interwiki_pagename() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:826:2: ( (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:826:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:826:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
	    int cnt118=0;
	    loop118:
	    do {
		int alt118=2;
		int LA118_0 = input.LA(1);

		if ( ((LA118_0>=FORCED_END_OF_LINE && LA118_0<=WIKI)||(LA118_0>=POUND && LA118_0<=EQUAL)||(LA118_0>=ITAL && LA118_0<=NOWIKI_CLOSE)||(LA118_0>=IMAGE_CLOSE && LA118_0<=79)) ) {
		    alt118=1;
		}


		switch (alt118) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:826:6: c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF )
		    {
		    c=(Token)input.LT(1);
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=EQUAL)||(input.LA(1)>=ITAL && input.LA(1)<=NOWIKI_CLOSE)||(input.LA(1)>=IMAGE_CLOSE && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_link_interwiki_pagename3649);	  throw mse;
		    }

		    if ( backtracking==0 ) {
		       text.append(c.getText()); 
		    }

		    }
		    break;

		default :
		    if ( cnt118 >= 1 ) break loop118;
		    if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
			    new EarlyExitException(118, input);
			throw eee;
		}
		cnt118++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:828:1: link_description returns [CollectionNode node = new CollectionNode()] : (l= link_descriptionpart | i= image )+ ;
    public final CollectionNode link_description() throws RecognitionException {
	CollectionNode node =  new CollectionNode();

	ASTNode l = null;

	ImageNode i = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:829:2: ( (l= link_descriptionpart | i= image )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:829:4: (l= link_descriptionpart | i= image )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:829:4: (l= link_descriptionpart | i= image )+
	    int cnt119=0;
	    loop119:
	    do {
		int alt119=3;
		int LA119_0 = input.LA(1);

		if ( ((LA119_0>=FORCED_END_OF_LINE && LA119_0<=WIKI)||(LA119_0>=POUND && LA119_0<=ITAL)||(LA119_0>=FORCED_LINEBREAK && LA119_0<=NOWIKI_CLOSE)||(LA119_0>=IMAGE_CLOSE && LA119_0<=79)) ) {
		    alt119=1;
		}
		else if ( (LA119_0==IMAGE_OPEN) ) {
		    alt119=2;
		}


		switch (alt119) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:829:6: l= link_descriptionpart
		    {
		    pushFollow(FOLLOW_link_descriptionpart_in_link_description3692);
		    l=link_descriptionpart();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {

							// Recover code: some bad syntax could include null elements in the collection
							if (l != null) {
								node.add(l);
							}
						
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:835:5: i= image
		    {
		    pushFollow(FOLLOW_image_in_link_description3704);
		    i=image();
		    _fsp--;
		    if (failed) return node;
		    if ( backtracking==0 ) {
		      node.add(i);
		    }

		    }
		    break;

		default :
		    if ( cnt119 >= 1 ) break loop119;
		    if (backtracking>0) {failed=true; return node;}
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
	return node;
    }
    // $ANTLR end link_description

    protected static class link_descriptionpart_scope {
	CollectionNode element;
    }
    protected Stack link_descriptionpart_stack = new Stack();


    // $ANTLR start link_descriptionpart
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:837:1: link_descriptionpart returns [ASTNode text = null] : ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ );
    public final ASTNode link_descriptionpart() throws RecognitionException {
	link_descriptionpart_stack.push(new link_descriptionpart_scope());
	ASTNode text =	null;

	ASTNode lb = null;

	ASTNode li = null;

	CollectionNode t = null;



		((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element = new CollectionNode();

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:844:2: ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ )
	    int alt123=3;
	    switch ( input.LA(1) ) {
	    case STAR:
		{
		int LA123_1 = input.LA(2);

		if ( (LA123_1==STAR) ) {
		    alt123=1;
		}
		else if ( ((LA123_1>=FORCED_END_OF_LINE && LA123_1<=WIKI)||LA123_1==POUND||(LA123_1>=EQUAL && LA123_1<=PIPE)||(LA123_1>=FORCED_LINEBREAK && LA123_1<=NOWIKI_CLOSE)||(LA123_1>=IMAGE_CLOSE && LA123_1<=79)) ) {
		    alt123=3;
		}
		else {
		    if (backtracking>0) {failed=true; return text;}
		    NoViableAltException nvae =
			new NoViableAltException("837:1: link_descriptionpart returns [ASTNode text = null] : ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ );", 123, 1, input);

		    throw nvae;
		}
		}
		break;
	    case ITAL:
		{
		alt123=2;
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
	    case BRACE_CLOSE:
	    case COLON_SLASH:
	    case SLASH:
	    case TABLE_OF_CONTENTS_OPEN_MARKUP:
	    case TABLE_OF_CONTENTS_CLOSE_MARKUP:
	    case INSIGNIFICANT_CHAR:
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
	    case 79:
		{
		alt123=3;
		}
		break;
	    default:
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
		    new NoViableAltException("837:1: link_descriptionpart returns [ASTNode text = null] : ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ );", 123, 0, input);

		throw nvae;
	    }

	    switch (alt123) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:844:4: bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup
		    {
		    pushFollow(FOLLOW_bold_markup_in_link_descriptionpart3729);
		    bold_markup();
		    _fsp--;
		    if (failed) return text;
		    pushFollow(FOLLOW_onestar_in_link_descriptionpart3732);
		    onestar();
		    _fsp--;
		    if (failed) return text;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:844:25: (lb= link_bold_descriptionpart onestar )+
		    int cnt120=0;
		    loop120:
		    do {
			int alt120=2;
			int LA120_0 = input.LA(1);

			if ( ((LA120_0>=FORCED_END_OF_LINE && LA120_0<=WIKI)||LA120_0==POUND||(LA120_0>=EQUAL && LA120_0<=ITAL)||(LA120_0>=FORCED_LINEBREAK && LA120_0<=NOWIKI_CLOSE)||(LA120_0>=IMAGE_CLOSE && LA120_0<=79)) ) {
			    alt120=1;
			}


			switch (alt120) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:844:27: lb= link_bold_descriptionpart onestar
			    {
			    pushFollow(FOLLOW_link_bold_descriptionpart_in_link_descriptionpart3740);
			    lb=link_bold_descriptionpart();
			    _fsp--;
			    if (failed) return text;
			    if ( backtracking==0 ) {
			      ((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element.add(lb);
			    }
			    pushFollow(FOLLOW_onestar_in_link_descriptionpart3745);
			    onestar();
			    _fsp--;
			    if (failed) return text;

			    }
			    break;

			default :
			    if ( cnt120 >= 1 ) break loop120;
			    if (backtracking>0) {failed=true; return text;}
				EarlyExitException eee =
				    new EarlyExitException(120, input);
				throw eee;
			}
			cnt120++;
		    } while (true);

		    if ( backtracking==0 ) {
		      text = new BoldTextNode(((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element);
		    }
		    pushFollow(FOLLOW_bold_markup_in_link_descriptionpart3755);
		    bold_markup();
		    _fsp--;
		    if (failed) return text;

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:846:4: ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup
		    {
		    pushFollow(FOLLOW_ital_markup_in_link_descriptionpart3760);
		    ital_markup();
		    _fsp--;
		    if (failed) return text;
		    pushFollow(FOLLOW_onestar_in_link_descriptionpart3763);
		    onestar();
		    _fsp--;
		    if (failed) return text;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:846:26: (li= link_ital_descriptionpart onestar )+
		    int cnt121=0;
		    loop121:
		    do {
			int alt121=2;
			int LA121_0 = input.LA(1);

			if ( ((LA121_0>=FORCED_END_OF_LINE && LA121_0<=WIKI)||(LA121_0>=POUND && LA121_0<=PIPE)||(LA121_0>=FORCED_LINEBREAK && LA121_0<=NOWIKI_CLOSE)||(LA121_0>=IMAGE_CLOSE && LA121_0<=79)) ) {
			    alt121=1;
			}


			switch (alt121) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:846:28: li= link_ital_descriptionpart onestar
			    {
			    pushFollow(FOLLOW_link_ital_descriptionpart_in_link_descriptionpart3772);
			    li=link_ital_descriptionpart();
			    _fsp--;
			    if (failed) return text;
			    if ( backtracking==0 ) {
			      ((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element.add(li);
			    }
			    pushFollow(FOLLOW_onestar_in_link_descriptionpart3777);
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
		      text = new ItalicTextNode(((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element);
		    }
		    pushFollow(FOLLOW_ital_markup_in_link_descriptionpart3786);
		    ital_markup();
		    _fsp--;
		    if (failed) return text;

		    }
		    break;
		case 3 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:848:4: onestar (t= link_descriptiontext onestar )+
		    {
		    pushFollow(FOLLOW_onestar_in_link_descriptionpart3791);
		    onestar();
		    _fsp--;
		    if (failed) return text;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:848:13: (t= link_descriptiontext onestar )+
		    int cnt122=0;
		    loop122:
		    do {
			int alt122=2;
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
			case BRACE_CLOSE:
			case COLON_SLASH:
			case SLASH:
			case TABLE_OF_CONTENTS_OPEN_MARKUP:
			case TABLE_OF_CONTENTS_CLOSE_MARKUP:
			case INSIGNIFICANT_CHAR:
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
			case 79:
			    {
			    alt122=1;
			    }
			    break;
			case FORCED_LINEBREAK:
			    {
			    alt122=1;
			    }
			    break;
			case ESCAPE:
			    {
			    alt122=1;
			    }
			    break;

			}

			switch (alt122) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:848:15: t= link_descriptiontext onestar
			    {
			    pushFollow(FOLLOW_link_descriptiontext_in_link_descriptionpart3800);
			    t=link_descriptiontext();
			    _fsp--;
			    if (failed) return text;
			    pushFollow(FOLLOW_onestar_in_link_descriptionpart3803);
			    onestar();
			    _fsp--;
			    if (failed) return text;
			    if ( backtracking==0 ) {
			      ((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element.add(t);
			    }

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:850:1: link_bold_descriptionpart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | ld= link_descriptiontext );
    public final ASTNode link_bold_descriptionpart() throws RecognitionException {
	ASTNode text =	null;

	CollectionNode t = null;

	CollectionNode ld = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:851:2: ( ital_markup t= link_boldital_description ital_markup | ld= link_descriptiontext )
	    int alt124=2;
	    int LA124_0 = input.LA(1);

	    if ( (LA124_0==ITAL) ) {
		alt124=1;
	    }
	    else if ( ((LA124_0>=FORCED_END_OF_LINE && LA124_0<=WIKI)||LA124_0==POUND||(LA124_0>=EQUAL && LA124_0<=PIPE)||(LA124_0>=FORCED_LINEBREAK && LA124_0<=NOWIKI_CLOSE)||(LA124_0>=IMAGE_CLOSE && LA124_0<=79)) ) {
		alt124=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
		    new NoViableAltException("850:1: link_bold_descriptionpart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | ld= link_descriptiontext );", 124, 0, input);

		throw nvae;
	    }
	    switch (alt124) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:851:4: ital_markup t= link_boldital_description ital_markup
		    {
		    pushFollow(FOLLOW_ital_markup_in_link_bold_descriptionpart3823);
		    ital_markup();
		    _fsp--;
		    if (failed) return text;
		    pushFollow(FOLLOW_link_boldital_description_in_link_bold_descriptionpart3830);
		    t=link_boldital_description();
		    _fsp--;
		    if (failed) return text;
		    if ( backtracking==0 ) {
		      text = new ItalicTextNode(t);
		    }
		    pushFollow(FOLLOW_ital_markup_in_link_bold_descriptionpart3835);
		    ital_markup();
		    _fsp--;
		    if (failed) return text;

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:852:4: ld= link_descriptiontext
		    {
		    pushFollow(FOLLOW_link_descriptiontext_in_link_bold_descriptionpart3844);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:854:1: link_ital_descriptionpart returns [ASTNode text = null] : ( bold_markup td= link_boldital_description bold_markup | t= link_descriptiontext );
    public final ASTNode link_ital_descriptionpart() throws RecognitionException {
	ASTNode text =	null;

	CollectionNode td = null;

	CollectionNode t = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:855:2: ( bold_markup td= link_boldital_description bold_markup | t= link_descriptiontext )
	    int alt125=2;
	    int LA125_0 = input.LA(1);

	    if ( (LA125_0==STAR) ) {
		alt125=1;
	    }
	    else if ( ((LA125_0>=FORCED_END_OF_LINE && LA125_0<=WIKI)||LA125_0==POUND||(LA125_0>=EQUAL && LA125_0<=PIPE)||(LA125_0>=FORCED_LINEBREAK && LA125_0<=NOWIKI_CLOSE)||(LA125_0>=IMAGE_CLOSE && LA125_0<=79)) ) {
		alt125=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
		    new NoViableAltException("854:1: link_ital_descriptionpart returns [ASTNode text = null] : ( bold_markup td= link_boldital_description bold_markup | t= link_descriptiontext );", 125, 0, input);

		throw nvae;
	    }
	    switch (alt125) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:855:4: bold_markup td= link_boldital_description bold_markup
		    {
		    pushFollow(FOLLOW_bold_markup_in_link_ital_descriptionpart3860);
		    bold_markup();
		    _fsp--;
		    if (failed) return text;
		    pushFollow(FOLLOW_link_boldital_description_in_link_ital_descriptionpart3867);
		    td=link_boldital_description();
		    _fsp--;
		    if (failed) return text;
		    pushFollow(FOLLOW_bold_markup_in_link_ital_descriptionpart3870);
		    bold_markup();
		    _fsp--;
		    if (failed) return text;
		    if ( backtracking==0 ) {
		      text = new BoldTextNode(td);
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:856:4: t= link_descriptiontext
		    {
		    pushFollow(FOLLOW_link_descriptiontext_in_link_ital_descriptionpart3881);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:858:1: link_boldital_description returns [CollectionNode text = new CollectionNode()] : onestar (t= link_descriptiontext onestar )+ ;
    public final CollectionNode link_boldital_description() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	CollectionNode t = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:859:2: ( onestar (t= link_descriptiontext onestar )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:859:4: onestar (t= link_descriptiontext onestar )+
	    {
	    pushFollow(FOLLOW_onestar_in_link_boldital_description3897);
	    onestar();
	    _fsp--;
	    if (failed) return text;
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:859:13: (t= link_descriptiontext onestar )+
	    int cnt126=0;
	    loop126:
	    do {
		int alt126=2;
		int LA126_0 = input.LA(1);

		if ( ((LA126_0>=FORCED_END_OF_LINE && LA126_0<=WIKI)||LA126_0==POUND||(LA126_0>=EQUAL && LA126_0<=PIPE)||(LA126_0>=FORCED_LINEBREAK && LA126_0<=NOWIKI_CLOSE)||(LA126_0>=IMAGE_CLOSE && LA126_0<=79)) ) {
		    alt126=1;
		}


		switch (alt126) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:859:15: t= link_descriptiontext onestar
		    {
		    pushFollow(FOLLOW_link_descriptiontext_in_link_boldital_description3906);
		    t=link_descriptiontext();
		    _fsp--;
		    if (failed) return text;
		    pushFollow(FOLLOW_onestar_in_link_boldital_description3909);
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
		    if ( cnt126 >= 1 ) break loop126;
		    if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
			    new EarlyExitException(126, input);
			throw eee;
		}
		cnt126++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:865:1: link_descriptiontext returns [CollectionNode text = new CollectionNode()] : (t= link_descriptiontext_simple | ( forced_linebreak | e= escaped )+ );
    public final CollectionNode link_descriptiontext() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	StringBundler t = null;

	ScapedNode e = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:866:2: (t= link_descriptiontext_simple | ( forced_linebreak | e= escaped )+ )
	    int alt128=2;
	    int LA128_0 = input.LA(1);

	    if ( ((LA128_0>=FORCED_END_OF_LINE && LA128_0<=WIKI)||LA128_0==POUND||(LA128_0>=EQUAL && LA128_0<=PIPE)||(LA128_0>=NOWIKI_BLOCK_CLOSE && LA128_0<=NOWIKI_CLOSE)||(LA128_0>=IMAGE_CLOSE && LA128_0<=79)) ) {
		alt128=1;
	    }
	    else if ( ((LA128_0>=FORCED_LINEBREAK && LA128_0<=ESCAPE)) ) {
		alt128=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
		    new NoViableAltException("865:1: link_descriptiontext returns [CollectionNode text = new CollectionNode()] : (t= link_descriptiontext_simple | ( forced_linebreak | e= escaped )+ );", 128, 0, input);

		throw nvae;
	    }
	    switch (alt128) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:866:5: t= link_descriptiontext_simple
		    {
		    pushFollow(FOLLOW_link_descriptiontext_simple_in_link_descriptiontext3932);
		    t=link_descriptiontext_simple();
		    _fsp--;
		    if (failed) return text;
		    if ( backtracking==0 ) {
		       text.add(new UnformattedTextNode(t.toString()));
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:867:5: ( forced_linebreak | e= escaped )+
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:867:5: ( forced_linebreak | e= escaped )+
		    int cnt127=0;
		    loop127:
		    do {
			int alt127=3;
			int LA127_0 = input.LA(1);

			if ( (LA127_0==FORCED_LINEBREAK) ) {
			    alt127=1;
			}
			else if ( (LA127_0==ESCAPE) ) {
			    alt127=2;
			}


			switch (alt127) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:867:7: forced_linebreak
			    {
			    pushFollow(FOLLOW_forced_linebreak_in_link_descriptiontext3942);
			    forced_linebreak();
			    _fsp--;
			    if (failed) return text;
			    if ( backtracking==0 ) {
			      text.add(new ForcedEndOfLineNode());
			    }

			    }
			    break;
			case 2 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:868:5: e= escaped
			    {
			    pushFollow(FOLLOW_escaped_in_link_descriptiontext3954);
			    e=escaped();
			    _fsp--;
			    if (failed) return text;
			    if ( backtracking==0 ) {
			      text.add(e);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:870:1: link_descriptiontext_simple returns [StringBundler text = new StringBundler()] : (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
    public final StringBundler link_descriptiontext_simple() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:871:2: ( (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:871:4: (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:871:4: (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
	    int cnt129=0;
	    loop129:
	    do {
		int alt129=2;
		int LA129_0 = input.LA(1);

		if ( ((LA129_0>=FORCED_END_OF_LINE && LA129_0<=WIKI)||LA129_0==POUND||(LA129_0>=EQUAL && LA129_0<=PIPE)||(LA129_0>=NOWIKI_BLOCK_CLOSE && LA129_0<=NOWIKI_CLOSE)||(LA129_0>=IMAGE_CLOSE && LA129_0<=79)) ) {
		    alt129=1;
		}


		switch (alt129) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:871:6: c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
		    {
		    c=(Token)input.LT(1);
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||(input.LA(1)>=EQUAL && input.LA(1)<=PIPE)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=NOWIKI_CLOSE)||(input.LA(1)>=IMAGE_CLOSE && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_link_descriptiontext_simple3979);    throw mse;
		    }

		    if ( backtracking==0 ) {
		       text.append(c.getText()); 
		    }

		    }
		    break;

		default :
		    if ( cnt129 >= 1 ) break loop129;
		    if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
			    new EarlyExitException(129, input);
			throw eee;
		}
		cnt129++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:883:1: link_uri returns [StringBundler text = new StringBundler()] : (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ ;
    public final StringBundler link_uri() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:884:2: ( (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:884:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:884:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
	    int cnt130=0;
	    loop130:
	    do {
		int alt130=2;
		int LA130_0 = input.LA(1);

		if ( ((LA130_0>=FORCED_END_OF_LINE && LA130_0<=WIKI)||(LA130_0>=POUND && LA130_0<=EQUAL)||(LA130_0>=ITAL && LA130_0<=NOWIKI_CLOSE)||(LA130_0>=IMAGE_CLOSE && LA130_0<=79)) ) {
		    alt130=1;
		}


		switch (alt130) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:884:6: c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF )
		    {
		    c=(Token)input.LT(1);
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=EQUAL)||(input.LA(1)>=ITAL && input.LA(1)<=NOWIKI_CLOSE)||(input.LA(1)>=IMAGE_CLOSE && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_link_uri4078);    throw mse;
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
    // $ANTLR end link_uri


    // $ANTLR start image
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:891:1: image returns [ImageNode image = new ImageNode()] : image_open_markup uri= image_uri (alt= image_alternative )? image_close_markup ;
    public final ImageNode image() throws RecognitionException {
	ImageNode image =  new ImageNode();

	StringBundler uri = null;

	CollectionNode alt = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:892:2: ( image_open_markup uri= image_uri (alt= image_alternative )? image_close_markup )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:892:4: image_open_markup uri= image_uri (alt= image_alternative )? image_close_markup
	    {
	    pushFollow(FOLLOW_image_open_markup_in_image4119);
	    image_open_markup();
	    _fsp--;
	    if (failed) return image;
	    pushFollow(FOLLOW_image_uri_in_image4125);
	    uri=image_uri();
	    _fsp--;
	    if (failed) return image;
	    if ( backtracking==0 ) {
	      image.setLink(uri.toString());
	    }
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:892:79: (alt= image_alternative )?
	    int alt131=2;
	    int LA131_0 = input.LA(1);

	    if ( (LA131_0==PIPE) ) {
		alt131=1;
	    }
	    switch (alt131) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:892:81: alt= image_alternative
		    {
		    pushFollow(FOLLOW_image_alternative_in_image4135);
		    alt=image_alternative();
		    _fsp--;
		    if (failed) return image;
		    if ( backtracking==0 ) {
		      image.setAltCollectionNode(alt);
		    }

		    }
		    break;

	    }

	    pushFollow(FOLLOW_image_close_markup_in_image4144);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:895:1: image_uri returns [StringBundler link = new StringBundler()] : (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+ ;
    public final StringBundler image_uri() throws RecognitionException {
	StringBundler link =  new StringBundler();

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:896:2: ( (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:896:4: (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:896:4: (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+
	    int cnt132=0;
	    loop132:
	    do {
		int alt132=2;
		int LA132_0 = input.LA(1);

		if ( ((LA132_0>=FORCED_END_OF_LINE && LA132_0<=WIKI)||(LA132_0>=POUND && LA132_0<=EQUAL)||(LA132_0>=ITAL && LA132_0<=LINK_CLOSE)||(LA132_0>=BLANKS && LA132_0<=79)) ) {
		    alt132=1;
		}


		switch (alt132) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:896:5: c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF )
		    {
		    c=(Token)input.LT(1);
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=EQUAL)||(input.LA(1)>=ITAL && input.LA(1)<=LINK_CLOSE)||(input.LA(1)>=BLANKS && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return link;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_image_uri4163);    throw mse;
		    }

		    if ( backtracking==0 ) {
		      link.append(c.getText()); 
		    }

		    }
		    break;

		default :
		    if ( cnt132 >= 1 ) break loop132;
		    if (backtracking>0) {failed=true; return link;}
			EarlyExitException eee =
			    new EarlyExitException(132, input);
			throw eee;
		}
		cnt132++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:898:1: image_alternative returns [CollectionNode alternative = new CollectionNode()] : image_alternative_markup (p= image_alternativepart )+ ;
    public final CollectionNode image_alternative() throws RecognitionException {
	CollectionNode alternative =  new CollectionNode();

	ASTNode p = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:899:2: ( image_alternative_markup (p= image_alternativepart )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:899:4: image_alternative_markup (p= image_alternativepart )+
	    {
	    pushFollow(FOLLOW_image_alternative_markup_in_image_alternative4198);
	    image_alternative_markup();
	    _fsp--;
	    if (failed) return alternative;
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:899:30: (p= image_alternativepart )+
	    int cnt133=0;
	    loop133:
	    do {
		int alt133=2;
		int LA133_0 = input.LA(1);

		if ( ((LA133_0>=FORCED_END_OF_LINE && LA133_0<=WIKI)||(LA133_0>=POUND && LA133_0<=ITAL)||(LA133_0>=FORCED_LINEBREAK && LA133_0<=LINK_CLOSE)||(LA133_0>=BLANKS && LA133_0<=79)) ) {
		    alt133=1;
		}


		switch (alt133) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:899:32: p= image_alternativepart
		    {
		    pushFollow(FOLLOW_image_alternativepart_in_image_alternative4207);
		    p=image_alternativepart();
		    _fsp--;
		    if (failed) return alternative;
		    if ( backtracking==0 ) {
		      alternative.add(p); 
		    }

		    }
		    break;

		default :
		    if ( cnt133 >= 1 ) break loop133;
		    if (backtracking>0) {failed=true; return alternative;}
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
	return alternative;
    }
    // $ANTLR end image_alternative

    protected static class image_alternativepart_scope {
	CollectionNode elements;
    }
    protected Stack image_alternativepart_stack = new Stack();


    // $ANTLR start image_alternativepart
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:901:1: image_alternativepart returns [ASTNode item = null] : ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ );
    public final ASTNode image_alternativepart() throws RecognitionException {
	image_alternativepart_stack.push(new image_alternativepart_scope());
	ASTNode item =	null;

	ASTNode t1 = null;

	ASTNode t2 = null;

	CollectionNode t3 = null;



	   ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements = new CollectionNode();

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:908:2: ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ )
	    int alt137=3;
	    switch ( input.LA(1) ) {
	    case STAR:
		{
		int LA137_1 = input.LA(2);

		if ( (LA137_1==STAR) ) {
		    alt137=1;
		}
		else if ( ((LA137_1>=FORCED_END_OF_LINE && LA137_1<=WIKI)||LA137_1==POUND||(LA137_1>=EQUAL && LA137_1<=PIPE)||(LA137_1>=FORCED_LINEBREAK && LA137_1<=LINK_CLOSE)||(LA137_1>=BLANKS && LA137_1<=79)) ) {
		    alt137=3;
		}
		else {
		    if (backtracking>0) {failed=true; return item;}
		    NoViableAltException nvae =
			new NoViableAltException("901:1: image_alternativepart returns [ASTNode item = null] : ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ );", 137, 1, input);

		    throw nvae;
		}
		}
		break;
	    case ITAL:
		{
		alt137=2;
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
	    case BRACE_CLOSE:
	    case COLON_SLASH:
	    case SLASH:
	    case TABLE_OF_CONTENTS_OPEN_MARKUP:
	    case TABLE_OF_CONTENTS_CLOSE_MARKUP:
	    case INSIGNIFICANT_CHAR:
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
	    case 79:
		{
		alt137=3;
		}
		break;
	    default:
		if (backtracking>0) {failed=true; return item;}
		NoViableAltException nvae =
		    new NoViableAltException("901:1: image_alternativepart returns [ASTNode item = null] : ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ );", 137, 0, input);

		throw nvae;
	    }

	    switch (alt137) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:908:4: bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup
		    {
		    pushFollow(FOLLOW_bold_markup_in_image_alternativepart4233);
		    bold_markup();
		    _fsp--;
		    if (failed) return item;
		    pushFollow(FOLLOW_onestar_in_image_alternativepart4236);
		    onestar();
		    _fsp--;
		    if (failed) return item;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:908:26: (t1= image_bold_alternativepart onestar )+
		    int cnt134=0;
		    loop134:
		    do {
			int alt134=2;
			int LA134_0 = input.LA(1);

			if ( (LA134_0==STAR) ) {
			    int LA134_1 = input.LA(2);

			    if ( ((LA134_1>=FORCED_END_OF_LINE && LA134_1<=WIKI)||LA134_1==POUND||(LA134_1>=EQUAL && LA134_1<=PIPE)||(LA134_1>=FORCED_LINEBREAK && LA134_1<=LINK_CLOSE)||(LA134_1>=BLANKS && LA134_1<=79)) ) {
				alt134=1;
			    }


			}
			else if ( ((LA134_0>=FORCED_END_OF_LINE && LA134_0<=WIKI)||LA134_0==POUND||(LA134_0>=EQUAL && LA134_0<=ITAL)||(LA134_0>=FORCED_LINEBREAK && LA134_0<=LINK_CLOSE)||(LA134_0>=BLANKS && LA134_0<=79)) ) {
			    alt134=1;
			}


			switch (alt134) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:908:28: t1= image_bold_alternativepart onestar
			    {
			    pushFollow(FOLLOW_image_bold_alternativepart_in_image_alternativepart4245);
			    t1=image_bold_alternativepart();
			    _fsp--;
			    if (failed) return item;
			    if ( backtracking==0 ) {
			      ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements.add(t1);
			    }
			    pushFollow(FOLLOW_onestar_in_image_alternativepart4250);
			    onestar();
			    _fsp--;
			    if (failed) return item;

			    }
			    break;

			default :
			    if ( cnt134 >= 1 ) break loop134;
			    if (backtracking>0) {failed=true; return item;}
				EarlyExitException eee =
				    new EarlyExitException(134, input);
				throw eee;
			}
			cnt134++;
		    } while (true);

		    pushFollow(FOLLOW_bold_markup_in_image_alternativepart4257);
		    bold_markup();
		    _fsp--;
		    if (failed) return item;
		    if ( backtracking==0 ) {
		      item = new BoldTextNode(((image_alternativepart_scope)image_alternativepart_stack.peek()).elements);
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:910:4: ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup
		    {
		    pushFollow(FOLLOW_ital_markup_in_image_alternativepart4264);
		    ital_markup();
		    _fsp--;
		    if (failed) return item;
		    pushFollow(FOLLOW_onestar_in_image_alternativepart4267);
		    onestar();
		    _fsp--;
		    if (failed) return item;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:910:26: (t2= image_ital_alternativepart onestar )+
		    int cnt135=0;
		    loop135:
		    do {
			int alt135=2;
			int LA135_0 = input.LA(1);

			if ( ((LA135_0>=FORCED_END_OF_LINE && LA135_0<=WIKI)||(LA135_0>=POUND && LA135_0<=PIPE)||(LA135_0>=FORCED_LINEBREAK && LA135_0<=LINK_CLOSE)||(LA135_0>=BLANKS && LA135_0<=79)) ) {
			    alt135=1;
			}


			switch (alt135) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:910:29: t2= image_ital_alternativepart onestar
			    {
			    pushFollow(FOLLOW_image_ital_alternativepart_in_image_alternativepart4277);
			    t2=image_ital_alternativepart();
			    _fsp--;
			    if (failed) return item;
			    if ( backtracking==0 ) {
			      ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements.add(t2);
			    }
			    pushFollow(FOLLOW_onestar_in_image_alternativepart4282);
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

		    pushFollow(FOLLOW_ital_markup_in_image_alternativepart4289);
		    ital_markup();
		    _fsp--;
		    if (failed) return item;
		    if ( backtracking==0 ) {
		      item = new ItalicTextNode(((image_alternativepart_scope)image_alternativepart_stack.peek()).elements);
		    }

		    }
		    break;
		case 3 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:912:4: onestar (t3= image_alternativetext onestar )+
		    {
		    pushFollow(FOLLOW_onestar_in_image_alternativepart4296);
		    onestar();
		    _fsp--;
		    if (failed) return item;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:912:13: (t3= image_alternativetext onestar )+
		    int cnt136=0;
		    loop136:
		    do {
			int alt136=2;
			int LA136_0 = input.LA(1);

			if ( ((LA136_0>=FORCED_END_OF_LINE && LA136_0<=WIKI)||LA136_0==POUND||(LA136_0>=EQUAL && LA136_0<=PIPE)||(LA136_0>=ESCAPE && LA136_0<=LINK_CLOSE)||(LA136_0>=BLANKS && LA136_0<=79)) ) {
			    alt136=1;
			}
			else if ( (LA136_0==FORCED_LINEBREAK) ) {
			    alt136=1;
			}


			switch (alt136) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:912:15: t3= image_alternativetext onestar
			    {
			    pushFollow(FOLLOW_image_alternativetext_in_image_alternativepart4303);
			    t3=image_alternativetext();
			    _fsp--;
			    if (failed) return item;
			    if ( backtracking==0 ) {

								for (ASTNode n: t3.getASTNodes()) {
								   ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements.add(n);
								 }
								      
			    }
			    pushFollow(FOLLOW_onestar_in_image_alternativepart4308);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:918:1: image_bold_alternativepart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | onestar (i= image_alternativetext onestar )+ );
    public final ASTNode image_bold_alternativepart() throws RecognitionException {
	image_bold_alternativepart_stack.push(new image_bold_alternativepart_scope());
	ASTNode text =	null;

	CollectionNode t = null;

	CollectionNode i = null;



	   ((image_bold_alternativepart_scope)image_bold_alternativepart_stack.peek()).elements = new CollectionNode();

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:925:2: ( ital_markup t= link_boldital_description ital_markup | onestar (i= image_alternativetext onestar )+ )
	    int alt139=2;
	    int LA139_0 = input.LA(1);

	    if ( (LA139_0==ITAL) ) {
		alt139=1;
	    }
	    else if ( ((LA139_0>=FORCED_END_OF_LINE && LA139_0<=WIKI)||(LA139_0>=POUND && LA139_0<=PIPE)||(LA139_0>=FORCED_LINEBREAK && LA139_0<=LINK_CLOSE)||(LA139_0>=BLANKS && LA139_0<=79)) ) {
		alt139=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
		    new NoViableAltException("918:1: image_bold_alternativepart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | onestar (i= image_alternativetext onestar )+ );", 139, 0, input);

		throw nvae;
	    }
	    switch (alt139) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:925:4: ital_markup t= link_boldital_description ital_markup
		    {
		    pushFollow(FOLLOW_ital_markup_in_image_bold_alternativepart4334);
		    ital_markup();
		    _fsp--;
		    if (failed) return text;
		    pushFollow(FOLLOW_link_boldital_description_in_image_bold_alternativepart4341);
		    t=link_boldital_description();
		    _fsp--;
		    if (failed) return text;
		    if ( backtracking==0 ) {
		      text = new ItalicTextNode(t); 
		    }
		    pushFollow(FOLLOW_ital_markup_in_image_bold_alternativepart4346);
		    ital_markup();
		    _fsp--;
		    if (failed) return text;

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:926:4: onestar (i= image_alternativetext onestar )+
		    {
		    pushFollow(FOLLOW_onestar_in_image_bold_alternativepart4351);
		    onestar();
		    _fsp--;
		    if (failed) return text;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:926:13: (i= image_alternativetext onestar )+
		    int cnt138=0;
		    loop138:
		    do {
			int alt138=2;
			int LA138_0 = input.LA(1);

			if ( ((LA138_0>=FORCED_END_OF_LINE && LA138_0<=WIKI)||LA138_0==POUND||(LA138_0>=EQUAL && LA138_0<=PIPE)||(LA138_0>=ESCAPE && LA138_0<=LINK_CLOSE)||(LA138_0>=BLANKS && LA138_0<=79)) ) {
			    alt138=1;
			}
			else if ( (LA138_0==FORCED_LINEBREAK) ) {
			    alt138=1;
			}


			switch (alt138) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:926:15: i= image_alternativetext onestar
			    {
			    pushFollow(FOLLOW_image_alternativetext_in_image_bold_alternativepart4360);
			    i=image_alternativetext();
			    _fsp--;
			    if (failed) return text;
			    pushFollow(FOLLOW_onestar_in_image_bold_alternativepart4363);
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
			    if ( cnt138 >= 1 ) break loop138;
			    if (backtracking>0) {failed=true; return text;}
				EarlyExitException eee =
				    new EarlyExitException(138, input);
				throw eee;
			}
			cnt138++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:933:1: image_ital_alternativepart returns [ASTNode text = null] : ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ );
    public final ASTNode image_ital_alternativepart() throws RecognitionException {
	image_ital_alternativepart_stack.push(new image_ital_alternativepart_scope());
	ASTNode text =	null;

	CollectionNode t = null;

	CollectionNode i = null;



	   ((image_ital_alternativepart_scope)image_ital_alternativepart_stack.peek()).elements = new CollectionNode();

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:940:2: ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ )
	    int alt141=2;
	    int LA141_0 = input.LA(1);

	    if ( (LA141_0==STAR) ) {
		int LA141_1 = input.LA(2);

		if ( (LA141_1==STAR) ) {
		    alt141=1;
		}
		else if ( ((LA141_1>=FORCED_END_OF_LINE && LA141_1<=WIKI)||LA141_1==POUND||(LA141_1>=EQUAL && LA141_1<=PIPE)||(LA141_1>=FORCED_LINEBREAK && LA141_1<=LINK_CLOSE)||(LA141_1>=BLANKS && LA141_1<=79)) ) {
		    alt141=2;
		}
		else {
		    if (backtracking>0) {failed=true; return text;}
		    NoViableAltException nvae =
			new NoViableAltException("933:1: image_ital_alternativepart returns [ASTNode text = null] : ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ );", 141, 1, input);

		    throw nvae;
		}
	    }
	    else if ( ((LA141_0>=FORCED_END_OF_LINE && LA141_0<=WIKI)||LA141_0==POUND||(LA141_0>=EQUAL && LA141_0<=PIPE)||(LA141_0>=FORCED_LINEBREAK && LA141_0<=LINK_CLOSE)||(LA141_0>=BLANKS && LA141_0<=79)) ) {
		alt141=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return text;}
		NoViableAltException nvae =
		    new NoViableAltException("933:1: image_ital_alternativepart returns [ASTNode text = null] : ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ );", 141, 0, input);

		throw nvae;
	    }
	    switch (alt141) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:940:4: bold_markup t= link_boldital_description bold_markup
		    {
		    pushFollow(FOLLOW_bold_markup_in_image_ital_alternativepart4391);
		    bold_markup();
		    _fsp--;
		    if (failed) return text;
		    pushFollow(FOLLOW_link_boldital_description_in_image_ital_alternativepart4398);
		    t=link_boldital_description();
		    _fsp--;
		    if (failed) return text;
		    if ( backtracking==0 ) {
		      text = new BoldTextNode(t); 
		    }
		    pushFollow(FOLLOW_bold_markup_in_image_ital_alternativepart4403);
		    bold_markup();
		    _fsp--;
		    if (failed) return text;

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:941:4: onestar (i= image_alternativetext onestar )+
		    {
		    pushFollow(FOLLOW_onestar_in_image_ital_alternativepart4408);
		    onestar();
		    _fsp--;
		    if (failed) return text;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:941:13: (i= image_alternativetext onestar )+
		    int cnt140=0;
		    loop140:
		    do {
			int alt140=2;
			int LA140_0 = input.LA(1);

			if ( ((LA140_0>=FORCED_END_OF_LINE && LA140_0<=WIKI)||LA140_0==POUND||(LA140_0>=EQUAL && LA140_0<=PIPE)||(LA140_0>=ESCAPE && LA140_0<=LINK_CLOSE)||(LA140_0>=BLANKS && LA140_0<=79)) ) {
			    alt140=1;
			}
			else if ( (LA140_0==FORCED_LINEBREAK) ) {
			    alt140=1;
			}


			switch (alt140) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:941:14: i= image_alternativetext onestar
			    {
			    pushFollow(FOLLOW_image_alternativetext_in_image_ital_alternativepart4417);
			    i=image_alternativetext();
			    _fsp--;
			    if (failed) return text;
			    pushFollow(FOLLOW_onestar_in_image_ital_alternativepart4420);
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
			    if ( cnt140 >= 1 ) break loop140;
			    if (backtracking>0) {failed=true; return text;}
				EarlyExitException eee =
				    new EarlyExitException(140, input);
				throw eee;
			}
			cnt140++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:947:1: image_boldital_alternative returns [CollectionNode text = new CollectionNode()] : onestar (i= image_alternativetext onestar )+ ;
    public final CollectionNode image_boldital_alternative() throws RecognitionException {
	CollectionNode text =  new CollectionNode();

	CollectionNode i = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:948:2: ( onestar (i= image_alternativetext onestar )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:948:4: onestar (i= image_alternativetext onestar )+
	    {
	    pushFollow(FOLLOW_onestar_in_image_boldital_alternative4441);
	    onestar();
	    _fsp--;
	    if (failed) return text;
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:948:13: (i= image_alternativetext onestar )+
	    int cnt142=0;
	    loop142:
	    do {
		int alt142=2;
		int LA142_0 = input.LA(1);

		if ( ((LA142_0>=FORCED_END_OF_LINE && LA142_0<=WIKI)||LA142_0==POUND||(LA142_0>=EQUAL && LA142_0<=PIPE)||(LA142_0>=FORCED_LINEBREAK && LA142_0<=LINK_CLOSE)||(LA142_0>=BLANKS && LA142_0<=79)) ) {
		    alt142=1;
		}


		switch (alt142) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:948:15: i= image_alternativetext onestar
		    {
		    pushFollow(FOLLOW_image_alternativetext_in_image_boldital_alternative4450);
		    i=image_alternativetext();
		    _fsp--;
		    if (failed) return text;
		    pushFollow(FOLLOW_onestar_in_image_boldital_alternative4453);
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
		    if ( cnt142 >= 1 ) break loop142;
		    if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
			    new EarlyExitException(142, input);
			throw eee;
		}
		cnt142++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:954:1: image_alternativetext returns [CollectionNode items = new CollectionNode()] : (contents= image_alternative_simple_text | ( forced_linebreak )+ );
    public final CollectionNode image_alternativetext() throws RecognitionException {
	CollectionNode items =	new CollectionNode();

	StringBundler contents = null;


	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:955:2: (contents= image_alternative_simple_text | ( forced_linebreak )+ )
	    int alt144=2;
	    int LA144_0 = input.LA(1);

	    if ( ((LA144_0>=FORCED_END_OF_LINE && LA144_0<=WIKI)||LA144_0==POUND||(LA144_0>=EQUAL && LA144_0<=PIPE)||(LA144_0>=ESCAPE && LA144_0<=LINK_CLOSE)||(LA144_0>=BLANKS && LA144_0<=79)) ) {
		alt144=1;
	    }
	    else if ( (LA144_0==FORCED_LINEBREAK) ) {
		alt144=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return items;}
		NoViableAltException nvae =
		    new NoViableAltException("954:1: image_alternativetext returns [CollectionNode items = new CollectionNode()] : (contents= image_alternative_simple_text | ( forced_linebreak )+ );", 144, 0, input);

		throw nvae;
	    }
	    switch (alt144) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:955:4: contents= image_alternative_simple_text
		    {
		    pushFollow(FOLLOW_image_alternative_simple_text_in_image_alternativetext4475);
		    contents=image_alternative_simple_text();
		    _fsp--;
		    if (failed) return items;
		    if ( backtracking==0 ) {
		      items.add(new UnformattedTextNode(contents.toString())); 
		    }

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:956:4: ( forced_linebreak )+
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:956:4: ( forced_linebreak )+
		    int cnt143=0;
		    loop143:
		    do {
			int alt143=2;
			int LA143_0 = input.LA(1);

			if ( (LA143_0==FORCED_LINEBREAK) ) {
			    alt143=1;
			}


			switch (alt143) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:956:5: forced_linebreak
			    {
			    pushFollow(FOLLOW_forced_linebreak_in_image_alternativetext4483);
			    forced_linebreak();
			    _fsp--;
			    if (failed) return items;
			    if ( backtracking==0 ) {
			      items.add(new ForcedEndOfLineNode());
			    }

			    }
			    break;

			default :
			    if ( cnt143 >= 1 ) break loop143;
			    if (backtracking>0) {failed=true; return items;}
				EarlyExitException eee =
				    new EarlyExitException(143, input);
				throw eee;
			}
			cnt143++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:959:1: image_alternative_simple_text returns [StringBundler text = new StringBundler()] : (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+ ;
    public final StringBundler image_alternative_simple_text() throws RecognitionException {
	StringBundler text =  new StringBundler();

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:960:2: ( (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:961:2: (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:961:2: (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+
	    int cnt145=0;
	    loop145:
	    do {
		int alt145=2;
		int LA145_0 = input.LA(1);

		if ( ((LA145_0>=FORCED_END_OF_LINE && LA145_0<=WIKI)||LA145_0==POUND||(LA145_0>=EQUAL && LA145_0<=PIPE)||(LA145_0>=ESCAPE && LA145_0<=LINK_CLOSE)||(LA145_0>=BLANKS && LA145_0<=79)) ) {
		    alt145=1;
		}


		switch (alt145) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:961:4: c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF )
		    {
		    c=(Token)input.LT(1);
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||(input.LA(1)>=EQUAL && input.LA(1)<=PIPE)||(input.LA(1)>=ESCAPE && input.LA(1)<=LINK_CLOSE)||(input.LA(1)>=BLANKS && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return text;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_image_alternative_simple_text4509);	throw mse;
		    }

		    if ( backtracking==0 ) {
		      text.append(c.getText()); 
		    }

		    }
		    break;

		default :
		    if ( cnt145 >= 1 ) break loop145;
		    if (backtracking>0) {failed=true; return text;}
			EarlyExitException eee =
			    new EarlyExitException(145, input);
			throw eee;
		}
		cnt145++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:975:1: extension returns [ASTNode node = null] : extension_markup extension_handler blanks extension_statement extension_markup ;
    public final ASTNode extension() throws RecognitionException {
	ASTNode node =	null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:976:2: ( extension_markup extension_handler blanks extension_statement extension_markup )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:976:4: extension_markup extension_handler blanks extension_statement extension_markup
	    {
	    pushFollow(FOLLOW_extension_markup_in_extension4601);
	    extension_markup();
	    _fsp--;
	    if (failed) return node;
	    pushFollow(FOLLOW_extension_handler_in_extension4604);
	    extension_handler();
	    _fsp--;
	    if (failed) return node;
	    pushFollow(FOLLOW_blanks_in_extension4607);
	    blanks();
	    _fsp--;
	    if (failed) return node;
	    pushFollow(FOLLOW_extension_statement_in_extension4610);
	    extension_statement();
	    _fsp--;
	    if (failed) return node;
	    pushFollow(FOLLOW_extension_markup_in_extension4614);
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:979:1: extension_handler : (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+ ;
    public final void extension_handler() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:980:2: ( (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:980:4: (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:980:4: (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+
	    int cnt146=0;
	    loop146:
	    do {
		int alt146=3;
		int LA146_0 = input.LA(1);

		if ( ((LA146_0>=FORCED_END_OF_LINE && LA146_0<=WIKI)||(LA146_0>=POUND && LA146_0<=NOWIKI_OPEN)||LA146_0==FORCED_LINEBREAK||(LA146_0>=NOWIKI_BLOCK_CLOSE && LA146_0<=IMAGE_CLOSE)||(LA146_0>=TABLE_OF_CONTENTS_TEXT && LA146_0<=79)) ) {
		    alt146=1;
		}
		else if ( (LA146_0==ESCAPE) ) {
		    alt146=2;
		}


		switch (alt146) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:980:5: ~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF )
		    {
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=NOWIKI_OPEN)||input.LA(1)==FORCED_LINEBREAK||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=IMAGE_CLOSE)||(input.LA(1)>=TABLE_OF_CONTENTS_TEXT && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return ;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_extension_handler4625);    throw mse;
		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:980:64: escaped
		    {
		    pushFollow(FOLLOW_escaped_in_extension_handler4658);
		    escaped();
		    _fsp--;
		    if (failed) return ;

		    }
		    break;

		default :
		    if ( cnt146 >= 1 ) break loop146;
		    if (backtracking>0) {failed=true; return ;}
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
	return ;
    }
    // $ANTLR end extension_handler


    // $ANTLR start extension_statement
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:982:1: extension_statement : (~ ( EXTENSION | ESCAPE | EOF ) | escaped )* ;
    public final void extension_statement() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:983:2: ( (~ ( EXTENSION | ESCAPE | EOF ) | escaped )* )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:983:4: (~ ( EXTENSION | ESCAPE | EOF ) | escaped )*
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:983:4: (~ ( EXTENSION | ESCAPE | EOF ) | escaped )*
	    loop147:
	    do {
		int alt147=3;
		int LA147_0 = input.LA(1);

		if ( ((LA147_0>=FORCED_END_OF_LINE && LA147_0<=NOWIKI_OPEN)||LA147_0==FORCED_LINEBREAK||(LA147_0>=NOWIKI_BLOCK_CLOSE && LA147_0<=79)) ) {
		    alt147=1;
		}
		else if ( (LA147_0==ESCAPE) ) {
		    alt147=2;
		}


		switch (alt147) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:983:5: ~ ( EXTENSION | ESCAPE | EOF )
		    {
		    if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=NOWIKI_OPEN)||input.LA(1)==FORCED_LINEBREAK||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=79) ) {
			input.consume();
			errorRecovery=false;failed=false;
		    }
		    else {
			if (backtracking>0) {failed=true; return ;}
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_extension_statement4672);    throw mse;
		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:983:41: escaped
		    {
		    pushFollow(FOLLOW_escaped_in_extension_statement4693);
		    escaped();
		    _fsp--;
		    if (failed) return ;

		    }
		    break;

		default :
		    break loop147;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:989:1: table_of_contents returns [ASTNode tableOfContents = new TableOfContentsNode()] : TABLE_OF_CONTENTS_TEXT ;
    public final ASTNode table_of_contents() throws RecognitionException {
	ASTNode tableOfContents =  new TableOfContentsNode();

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:990:2: ( TABLE_OF_CONTENTS_TEXT )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:990:38: TABLE_OF_CONTENTS_TEXT
	    {
	    match(input,TABLE_OF_CONTENTS_TEXT,FOLLOW_TABLE_OF_CONTENTS_TEXT_in_table_of_contents4716); if (failed) return tableOfContents;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:994:1: onestar : ( ({...}? ( STAR )? ) | );
    public final void onestar() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:995:2: ( ({...}? ( STAR )? ) | )
	    int alt149=2;
	    switch ( input.LA(1) ) {
	    case STAR:
		{
		int LA149_1 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 1, input);

		    throw nvae;
		}
		}
		break;
	    case BLANKS:
		{
		int LA149_2 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 2, input);

		    throw nvae;
		}
		}
		break;
	    case FORCED_LINEBREAK:
		{
		int LA149_3 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 3, input);

		    throw nvae;
		}
		}
		break;
	    case ESCAPE:
		{
		int LA149_4 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 4, input);

		    throw nvae;
		}
		}
		break;
	    case LINK_OPEN:
		{
		int LA149_5 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 5, input);

		    throw nvae;
		}
		}
		break;
	    case IMAGE_OPEN:
		{
		int LA149_6 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 6, input);

		    throw nvae;
		}
		}
		break;
	    case EXTENSION:
		{
		int LA149_7 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 7, input);

		    throw nvae;
		}
		}
		break;
	    case NOWIKI_OPEN:
		{
		int LA149_8 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 8, input);

		    throw nvae;
		}
		}
		break;
	    case NEWLINE:
		{
		int LA149_9 = input.LA(2);

		if ( ((( input.LA(2) != STAR && input.LA(2) != DASH && input.LA(2) != POUND &&
				input.LA(2) != EQUAL && input.LA(2) != NEWLINE )|| input.LA(2) != STAR )) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 9, input);

		    throw nvae;
		}
		}
		break;
	    case EOF:
		{
		int LA149_10 = input.LA(2);

		if ( ((( input.LA(2) != STAR && input.LA(2) != DASH && input.LA(2) != POUND &&
				input.LA(2) != EQUAL && input.LA(2) != NEWLINE )|| input.LA(2) != STAR )) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 10, input);

		    throw nvae;
		}
		}
		break;
	    case ITAL:
		{
		int LA149_11 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 11, input);

		    throw nvae;
		}
		}
		break;
	    case EQUAL:
		{
		int LA149_12 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 12, input);

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
	    case BRACE_CLOSE:
	    case COLON_SLASH:
	    case SLASH:
	    case TABLE_OF_CONTENTS_OPEN_MARKUP:
	    case TABLE_OF_CONTENTS_CLOSE_MARKUP:
	    case INSIGNIFICANT_CHAR:
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
	    case 79:
		{
		int LA149_13 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 13, input);

		    throw nvae;
		}
		}
		break;
	    case PIPE:
		{
		int LA149_14 = input.LA(2);

		if ( (( input.LA(2) != STAR ||( input.LA(2) != STAR && input.LA(2) == EQUAL ))) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 14, input);

		    throw nvae;
		}
		}
		break;
	    case LINK_CLOSE:
		{
		int LA149_15 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 15, input);

		    throw nvae;
		}
		}
		break;
	    case IMAGE_CLOSE:
		{
		int LA149_16 = input.LA(2);

		if ( ( input.LA(2) != STAR ) ) {
		    alt149=1;
		}
		else if ( (true) ) {
		    alt149=2;
		}
		else {
		    if (backtracking>0) {failed=true; return ;}
		    NoViableAltException nvae =
			new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 16, input);

		    throw nvae;
		}
		}
		break;
	    default:
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
		    new NoViableAltException("994:1: onestar : ( ({...}? ( STAR )? ) | );", 149, 0, input);

		throw nvae;
	    }

	    switch (alt149) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:995:4: ({...}? ( STAR )? )
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:995:4: ({...}? ( STAR )? )
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:995:6: {...}? ( STAR )?
		    {
		    if ( !( input.LA(2) != STAR ) ) {
			if (backtracking>0) {failed=true; return ;}
			throw new FailedPredicateException(input, "onestar", " input.LA(2) != STAR ");
		    }
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:995:32: ( STAR )?
		    int alt148=2;
		    int LA148_0 = input.LA(1);

		    if ( (LA148_0==STAR) ) {
			alt148=1;
		    }
		    switch (alt148) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:995:34: STAR
			    {
			    match(input,STAR,FOLLOW_STAR_in_onestar4738); if (failed) return ;

			    }
			    break;

		    }


		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:997:2: 
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:998:1: escaped returns [ScapedNode scaped = new ScapedNode()] : ESCAPE c= . ;
    public final ScapedNode escaped() throws RecognitionException {
	ScapedNode scaped =  new ScapedNode();

	Token c=null;

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:999:2: ( ESCAPE c= . )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:999:4: ESCAPE c= .
	    {
	    match(input,ESCAPE,FOLLOW_ESCAPE_in_escaped4759); if (failed) return scaped;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1002:1: paragraph_separator : ( ( newline )+ | EOF );
    public final void paragraph_separator() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1003:2: ( ( newline )+ | EOF )
	    int alt151=2;
	    int LA151_0 = input.LA(1);

	    if ( (LA151_0==NEWLINE) ) {
		alt151=1;
	    }
	    else if ( (LA151_0==EOF) ) {
		alt151=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
		    new NoViableAltException("1002:1: paragraph_separator : ( ( newline )+ | EOF );", 151, 0, input);

		throw nvae;
	    }
	    switch (alt151) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1003:4: ( newline )+
		    {
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1003:4: ( newline )+
		    int cnt150=0;
		    loop150:
		    do {
			int alt150=2;
			int LA150_0 = input.LA(1);

			if ( (LA150_0==NEWLINE) ) {
			    alt150=1;
			}


			switch (alt150) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1003:6: newline
			    {
			    pushFollow(FOLLOW_newline_in_paragraph_separator4783);
			    newline();
			    _fsp--;
			    if (failed) return ;

			    }
			    break;

			default :
			    if ( cnt150 >= 1 ) break loop150;
			    if (backtracking>0) {failed=true; return ;}
				EarlyExitException eee =
				    new EarlyExitException(150, input);
				throw eee;
			}
			cnt150++;
		    } while (true);


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1004:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_paragraph_separator4791); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1006:1: whitespaces : ( blanks | newline )+ ;
    public final void whitespaces() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1007:2: ( ( blanks | newline )+ )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1007:4: ( blanks | newline )+
	    {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1007:4: ( blanks | newline )+
	    int cnt152=0;
	    loop152:
	    do {
		int alt152=3;
		int LA152_0 = input.LA(1);

		if ( (LA152_0==BLANKS) ) {
		    alt152=1;
		}
		else if ( (LA152_0==NEWLINE) ) {
		    alt152=2;
		}


		switch (alt152) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1007:6: blanks
		    {
		    pushFollow(FOLLOW_blanks_in_whitespaces4803);
		    blanks();
		    _fsp--;
		    if (failed) return ;

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1007:15: newline
		    {
		    pushFollow(FOLLOW_newline_in_whitespaces4807);
		    newline();
		    _fsp--;
		    if (failed) return ;

		    }
		    break;

		default :
		    if ( cnt152 >= 1 ) break loop152;
		    if (backtracking>0) {failed=true; return ;}
			EarlyExitException eee =
			    new EarlyExitException(152, input);
			throw eee;
		}
		cnt152++;
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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1009:1: blanks : BLANKS ;
    public final void blanks() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1010:2: ( BLANKS )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1010:4: BLANKS
	    {
	    match(input,BLANKS,FOLLOW_BLANKS_in_blanks4820); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1012:1: text_lineseparator : ( newline ( blanks )? | EOF );
    public final void text_lineseparator() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1013:2: ( newline ( blanks )? | EOF )
	    int alt154=2;
	    int LA154_0 = input.LA(1);

	    if ( (LA154_0==NEWLINE) ) {
		alt154=1;
	    }
	    else if ( (LA154_0==EOF) ) {
		alt154=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
		    new NoViableAltException("1012:1: text_lineseparator : ( newline ( blanks )? | EOF );", 154, 0, input);

		throw nvae;
	    }
	    switch (alt154) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1013:4: newline ( blanks )?
		    {
		    pushFollow(FOLLOW_newline_in_text_lineseparator4830);
		    newline();
		    _fsp--;
		    if (failed) return ;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1013:13: ( blanks )?
		    int alt153=2;
		    int LA153_0 = input.LA(1);

		    if ( (LA153_0==BLANKS) ) {
			alt153=1;
		    }
		    switch (alt153) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1013:15: blanks
			    {
			    pushFollow(FOLLOW_blanks_in_text_lineseparator4835);
			    blanks();
			    _fsp--;
			    if (failed) return ;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1014:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_text_lineseparator4843); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1016:1: newline : NEWLINE ;
    public final void newline() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1017:2: ( NEWLINE )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1017:4: NEWLINE
	    {
	    match(input,NEWLINE,FOLLOW_NEWLINE_in_newline4853); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1019:1: bold_markup : STAR STAR ;
    public final void bold_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1020:2: ( STAR STAR )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1020:4: STAR STAR
	    {
	    match(input,STAR,FOLLOW_STAR_in_bold_markup4863); if (failed) return ;
	    match(input,STAR,FOLLOW_STAR_in_bold_markup4866); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1022:1: ital_markup : ITAL ;
    public final void ital_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1023:2: ( ITAL )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1023:4: ITAL
	    {
	    match(input,ITAL,FOLLOW_ITAL_in_ital_markup4876); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1025:1: heading_markup : EQUAL ;
    public final void heading_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1026:2: ( EQUAL )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1026:4: EQUAL
	    {
	    match(input,EQUAL,FOLLOW_EQUAL_in_heading_markup4886); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1028:1: list_ordelem_markup : POUND ;
    public final list_ordelem_markup_return list_ordelem_markup() throws RecognitionException {
	list_ordelem_markup_return retval = new list_ordelem_markup_return();
	retval.start = input.LT(1);

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1029:2: ( POUND )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1029:4: POUND
	    {
	    match(input,POUND,FOLLOW_POUND_in_list_ordelem_markup4896); if (failed) return retval;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1031:1: list_unordelem_markup : STAR ;
    public final list_unordelem_markup_return list_unordelem_markup() throws RecognitionException {
	list_unordelem_markup_return retval = new list_unordelem_markup_return();
	retval.start = input.LT(1);

	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1032:2: ( STAR )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1032:4: STAR
	    {
	    match(input,STAR,FOLLOW_STAR_in_list_unordelem_markup4906); if (failed) return retval;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1034:1: list_elemseparator : ( newline ( blanks )? | EOF );
    public final void list_elemseparator() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1035:2: ( newline ( blanks )? | EOF )
	    int alt156=2;
	    int LA156_0 = input.LA(1);

	    if ( (LA156_0==NEWLINE) ) {
		alt156=1;
	    }
	    else if ( (LA156_0==EOF) ) {
		alt156=2;
	    }
	    else {
		if (backtracking>0) {failed=true; return ;}
		NoViableAltException nvae =
		    new NoViableAltException("1034:1: list_elemseparator : ( newline ( blanks )? | EOF );", 156, 0, input);

		throw nvae;
	    }
	    switch (alt156) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1035:4: newline ( blanks )?
		    {
		    pushFollow(FOLLOW_newline_in_list_elemseparator4916);
		    newline();
		    _fsp--;
		    if (failed) return ;
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1035:13: ( blanks )?
		    int alt155=2;
		    int LA155_0 = input.LA(1);

		    if ( (LA155_0==BLANKS) ) {
			alt155=1;
		    }
		    switch (alt155) {
			case 1 :
			    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1035:15: blanks
			    {
			    pushFollow(FOLLOW_blanks_in_list_elemseparator4921);
			    blanks();
			    _fsp--;
			    if (failed) return ;

			    }
			    break;

		    }


		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1036:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_list_elemseparator4929); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1038:1: end_of_list : ( newline | EOF );
    public final void end_of_list() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1039:2: ( newline | EOF )
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
		    new NoViableAltException("1038:1: end_of_list : ( newline | EOF );", 157, 0, input);

		throw nvae;
	    }
	    switch (alt157) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1039:4: newline
		    {
		    pushFollow(FOLLOW_newline_in_end_of_list4939);
		    newline();
		    _fsp--;
		    if (failed) return ;

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1040:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_end_of_list4944); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1042:1: table_cell_markup : PIPE ;
    public final void table_cell_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1043:2: ( PIPE )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1043:4: PIPE
	    {
	    match(input,PIPE,FOLLOW_PIPE_in_table_cell_markup4954); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1045:1: table_headercell_markup : PIPE EQUAL ;
    public final void table_headercell_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1046:2: ( PIPE EQUAL )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1046:4: PIPE EQUAL
	    {
	    match(input,PIPE,FOLLOW_PIPE_in_table_headercell_markup4964); if (failed) return ;
	    match(input,EQUAL,FOLLOW_EQUAL_in_table_headercell_markup4967); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1048:1: table_rowseparator : ( newline | EOF );
    public final void table_rowseparator() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1049:2: ( newline | EOF )
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
		    new NoViableAltException("1048:1: table_rowseparator : ( newline | EOF );", 158, 0, input);

		throw nvae;
	    }
	    switch (alt158) {
		case 1 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1049:4: newline
		    {
		    pushFollow(FOLLOW_newline_in_table_rowseparator4977);
		    newline();
		    _fsp--;
		    if (failed) return ;

		    }
		    break;
		case 2 :
		    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1050:4: EOF
		    {
		    match(input,EOF,FOLLOW_EOF_in_table_rowseparator4982); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1052:1: nowiki_open_markup : NOWIKI_OPEN ;
    public final void nowiki_open_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1053:2: ( NOWIKI_OPEN )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1053:4: NOWIKI_OPEN
	    {
	    match(input,NOWIKI_OPEN,FOLLOW_NOWIKI_OPEN_in_nowiki_open_markup4992); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1055:1: nowiki_close_markup : NOWIKI_CLOSE ;
    public final void nowiki_close_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1056:2: ( NOWIKI_CLOSE )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1056:4: NOWIKI_CLOSE
	    {
	    match(input,NOWIKI_CLOSE,FOLLOW_NOWIKI_CLOSE_in_nowiki_close_markup5002); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1058:1: horizontalrule_markup : DASH DASH DASH DASH ;
    public final void horizontalrule_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1059:2: ( DASH DASH DASH DASH )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1059:4: DASH DASH DASH DASH
	    {
	    match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup5012); if (failed) return ;
	    match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup5015); if (failed) return ;
	    match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup5018); if (failed) return ;
	    match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup5021); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1061:1: link_open_markup : LINK_OPEN ;
    public final void link_open_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1062:2: ( LINK_OPEN )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1062:4: LINK_OPEN
	    {
	    match(input,LINK_OPEN,FOLLOW_LINK_OPEN_in_link_open_markup5031); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1064:1: link_close_markup : LINK_CLOSE ;
    public final void link_close_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1065:2: ( LINK_CLOSE )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1065:4: LINK_CLOSE
	    {
	    match(input,LINK_CLOSE,FOLLOW_LINK_CLOSE_in_link_close_markup5041); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1067:1: link_description_markup : PIPE ;
    public final void link_description_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1068:2: ( PIPE )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1068:4: PIPE
	    {
	    match(input,PIPE,FOLLOW_PIPE_in_link_description_markup5051); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1070:1: image_open_markup : IMAGE_OPEN ;
    public final void image_open_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1071:2: ( IMAGE_OPEN )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1071:4: IMAGE_OPEN
	    {
	    match(input,IMAGE_OPEN,FOLLOW_IMAGE_OPEN_in_image_open_markup5061); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1073:1: image_close_markup : IMAGE_CLOSE ;
    public final void image_close_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1074:2: ( IMAGE_CLOSE )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1074:4: IMAGE_CLOSE
	    {
	    match(input,IMAGE_CLOSE,FOLLOW_IMAGE_CLOSE_in_image_close_markup5071); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1076:1: image_alternative_markup : PIPE ;
    public final void image_alternative_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1077:2: ( PIPE )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1077:4: PIPE
	    {
	    match(input,PIPE,FOLLOW_PIPE_in_image_alternative_markup5081); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1079:1: extension_markup : EXTENSION ;
    public final void extension_markup() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1080:2: ( EXTENSION )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1080:4: EXTENSION
	    {
	    match(input,EXTENSION,FOLLOW_EXTENSION_in_extension_markup5091); if (failed) return ;

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
    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1082:1: forced_linebreak : FORCED_LINEBREAK ;
    public final void forced_linebreak() throws RecognitionException {
	try {
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1083:2: ( FORCED_LINEBREAK )
	    // /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:1083:4: FORCED_LINEBREAK
	    {
	    match(input,FORCED_LINEBREAK,FOLLOW_FORCED_LINEBREAK_in_forced_linebreak5101); if (failed) return ;

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
	// /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:166:5: ( NOWIKI_OPEN ~ ( NEWLINE ) )
	// /home/migue/development/sourcecode/liferay/liferay-portal/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:166:7: NOWIKI_OPEN ~ ( NEWLINE )
	{
	match(input,NOWIKI_OPEN,FOLLOW_NOWIKI_OPEN_in_synpred1317); if (failed) return ;
	if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=79) ) {
	    input.consume();
	    errorRecovery=false;failed=false;
	}
	else {
	    if (backtracking>0) {failed=true; return ;}
	    MismatchedSetException mse =
		new MismatchedSetException(null,input);
	    recoverFromMismatchedSet(input,mse,FOLLOW_set_in_synpred1320);    throw mse;
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


 

    public static final BitSet FOLLOW_whitespaces_in_wikipage111 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_paragraphs_in_wikipage119 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_wikipage124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_paragraph_in_paragraphs142 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_nowiki_block_in_paragraph163 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_blanks_in_paragraph170 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_paragraph_separator_in_paragraph173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_blanks_in_paragraph180 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_of_contents_in_paragraph194 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_heading_in_paragraph208 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_horizontalrule_in_paragraph227 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_list_in_paragraph240 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_table_in_paragraph253 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_text_paragraph_in_paragraph266 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_paragraph_separator_in_paragraph279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_line_in_text_paragraph307 = new BitSet(new long[]{0xFFFFFFFFFFF27FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_nowiki_inline_in_text_paragraph339 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_element_in_text_paragraph350 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_lineseparator_in_text_paragraph359 = new BitSet(new long[]{0xFFFFFFFFFFF27FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_firstelement_in_text_line382 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_element_in_text_line401 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_lineseparator_in_text_line415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_formattedelement_in_text_firstelement437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_first_unformattedelement_in_text_firstelement448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ital_markup_in_text_formattedelement464 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_italcontent_in_text_formattedelement470 = new BitSet(new long[]{0x0000000000108002L});
    public static final BitSet FOLLOW_NEWLINE_in_text_formattedelement479 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_ital_markup_in_text_formattedelement485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bold_markup_in_text_formattedelement493 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_boldcontent_in_text_formattedelement500 = new BitSet(new long[]{0x0000000000028002L});
    public static final BitSet FOLLOW_NEWLINE_in_text_formattedelement509 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_bold_markup_in_text_formattedelement515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_text_boldcontent534 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_boldcontentpart_in_text_boldcontent546 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_EOF_in_text_boldcontent557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_text_italcontent573 = new BitSet(new long[]{0xFFFFFFFFFFEF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_italcontentpart_in_text_italcontent585 = new BitSet(new long[]{0xFFFFFFFFFFEF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_EOF_in_text_italcontent596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_text_element610 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_unformattedelement_in_text_element617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_unformattedelement_in_text_element628 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_onestar_in_text_element631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_formattedelement_in_text_element642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ital_markup_in_text_boldcontentpart659 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_bolditalcontent_in_text_boldcontentpart666 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_ital_markup_in_text_boldcontentpart673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_formattedcontent_in_text_boldcontentpart685 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bold_markup_in_text_italcontentpart701 = new BitSet(new long[]{0xFFFFFFFFFFEFFFF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_bolditalcontent_in_text_italcontentpart708 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_bold_markup_in_text_italcontentpart714 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_formattedcontent_in_text_italcontentpart725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_text_bolditalcontent743 = new BitSet(new long[]{0xFFFFFFFFFFEF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_formattedcontent_in_text_bolditalcontent754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_text_bolditalcontent764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_text_formattedcontent778 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_unformattedelement_in_text_formattedcontent787 = new BitSet(new long[]{0xFFFFFFFFFFEFFFF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_text_formattedcontent792 = new BitSet(new long[]{0xFFFFFFFFFFEDFFF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_linebreak_in_text_formattedcontent797 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_lineseparator_in_text_linebreak817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_first_inlineelement_in_text_inlineelement835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nowiki_inline_in_text_inlineelement846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_link_in_text_first_inlineelement867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_image_in_text_first_inlineelement878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_extension_in_text_first_inlineelement888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_first_unformatted_in_text_first_unformattedelement908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_first_inlineelement_in_text_first_unformattedelement919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_first_unformmatted_text_in_text_first_unformatted941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forced_linebreak_in_text_first_unformatted950 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_escaped_in_text_first_unformatted962 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_set_in_text_first_unformmatted_text990 = new BitSet(new long[]{0xFFFFFFFFF8007FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_unformatted_in_text_unformattedelement1104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_inlineelement_in_text_unformattedelement1115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_unformated_text_in_text_unformatted1137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forced_linebreak_in_text_unformatted1146 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_escaped_in_text_unformatted1158 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_set_in_text_unformated_text1183 = new BitSet(new long[]{0xFFFFFFFFF80D7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_markup_in_heading1285 = new BitSet(new long[]{0xFFFFFFFFFBFFFFF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_content_in_heading1290 = new BitSet(new long[]{0x0000000080048000L});
    public static final BitSet FOLLOW_heading_markup_in_heading1297 = new BitSet(new long[]{0x0000000080008000L});
    public static final BitSet FOLLOW_blanks_in_heading1305 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_paragraph_separator_in_heading1312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_heading_markup_in_heading_content1322 = new BitSet(new long[]{0xFFFFFFFFFBFF7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_content_in_heading_content1327 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_heading_markup_in_heading_content1332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_heading_text_in_heading_content1344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_heading_cellcontent_in_heading_text1365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_heading_cellcontent1382 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_cellcontentpart_in_heading_cellcontent1391 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_heading_cellcontent1402 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_formattedelement_in_heading_cellcontentpart1423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_heading_unformattedelement_in_heading_cellcontentpart1434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ital_markup_in_heading_formattedelement1450 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_italcontent_in_heading_formattedelement1460 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_ital_markup_in_heading_formattedelement1469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bold_markup_in_heading_formattedelement1477 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_boldcontent_in_heading_formattedelement1484 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_bold_markup_in_heading_formattedelement1494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_heading_boldcontent1511 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_boldcontentpart_in_heading_boldcontent1520 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_heading_boldcontent1525 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_EOF_in_heading_boldcontent1533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_heading_italcontent1547 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_italcontentpart_in_heading_italcontent1556 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_heading_italcontent1561 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_EOF_in_heading_italcontent1569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_heading_formattedcontent_in_heading_boldcontentpart1587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ital_markup_in_heading_boldcontentpart1594 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_bolditalcontent_in_heading_boldcontentpart1601 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_ital_markup_in_heading_boldcontentpart1608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bold_markup_in_heading_italcontentpart1625 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_bolditalcontent_in_heading_italcontentpart1632 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_bold_markup_in_heading_italcontentpart1639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_heading_formattedcontent_in_heading_italcontentpart1651 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_heading_bolditalcontent1667 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_formattedcontent_in_heading_bolditalcontent1676 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_onestar_in_heading_bolditalcontent1681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_heading_bolditalcontent1689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_heading_unformattedelement_in_heading_formattedcontent1709 = new BitSet(new long[]{0xFFFFFFFFFBFB7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_heading_unformatted_text_in_heading_unformattedelement1732 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_heading_inlineelement_in_heading_unformattedelement1744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_link_in_heading_inlineelement1764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_image_in_heading_inlineelement1774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nowiki_inline_in_heading_inlineelement1785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_heading_unformatted_text1808 = new BitSet(new long[]{0xFFFFFFFFFB1B7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_list_elems_in_list1882 = new BitSet(new long[]{0x0000000000038002L});
    public static final BitSet FOLLOW_end_of_list_in_list1890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_list_ordelem_markup_in_list_elems1920 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_list_elem_in_list_elems1932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_list_unordelem_markup_in_list_elems1943 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_list_elem_in_list_elems1955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_list_elem_markup_in_list_elem1978 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_list_elemcontent_in_list_elem1989 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_list_elemseparator_in_list_elem1994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_list_ordelem_markup_in_list_elem_markup2004 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_list_unordelem_markup_in_list_elem_markup2009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_list_elemcontent2023 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_list_elemcontentpart_in_list_elemcontent2032 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_list_elemcontent2037 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_unformattedelement_in_list_elemcontentpart2058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_list_formatted_elem_in_list_elemcontentpart2069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bold_markup_in_list_formatted_elem2085 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_list_formatted_elem2088 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_list_boldcontentpart_in_list_formatted_elem2097 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_list_formatted_elem2106 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_bold_markup_in_list_formatted_elem2115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ital_markup_in_list_formatted_elem2123 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_list_formatted_elem2128 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_list_italcontentpart_in_list_formatted_elem2137 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_list_formatted_elem2146 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_ital_markup_in_list_formatted_elem2155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ital_markup_in_list_boldcontentpart2181 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_list_bolditalcontent_in_list_boldcontentpart2188 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_ital_markup_in_list_boldcontentpart2195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_unformattedelement_in_list_boldcontentpart2209 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_text_unformattedelement_in_list_bolditalcontent2240 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_bold_markup_in_list_italcontentpart2268 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_list_bolditalcontent_in_list_italcontentpart2275 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_bold_markup_in_list_italcontentpart2282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_unformattedelement_in_list_italcontentpart2296 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_row_in_table2324 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_table_cell_in_table_row2350 = new BitSet(new long[]{0x0000000000088000L});
    public static final BitSet FOLLOW_table_rowseparator_in_table_row2358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_headercell_in_table_cell2379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_normalcell_in_table_cell2390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_headercell_markup_in_table_headercell2406 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_cellcontent_in_table_headercell2413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_cell_markup_in_table_normalcell2429 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_cellcontent_in_table_normalcell2436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_table_cellcontent2452 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_cellcontentpart_in_table_cellcontent2461 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_table_cellcontent2468 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_formattedelement_in_table_cellcontentpart2489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_unformattedelement_in_table_cellcontentpart2500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ital_markup_in_table_formattedelement2516 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_italcontent_in_table_formattedelement2526 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_ital_markup_in_table_formattedelement2535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bold_markup_in_table_formattedelement2543 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_boldcontent_in_table_formattedelement2550 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_bold_markup_in_table_formattedelement2560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_table_boldcontent2577 = new BitSet(new long[]{0xFFFFFFFFFFF57FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_boldcontentpart_in_table_boldcontent2586 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_table_boldcontent2591 = new BitSet(new long[]{0xFFFFFFFFFFF57FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_EOF_in_table_boldcontent2599 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_table_italcontent2613 = new BitSet(new long[]{0xFFFFFFFFFFE77FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_italcontentpart_in_table_italcontent2622 = new BitSet(new long[]{0xFFFFFFFFFFE77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_table_italcontent2627 = new BitSet(new long[]{0xFFFFFFFFFFE77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_EOF_in_table_italcontent2635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_formattedcontent_in_table_boldcontentpart2653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ital_markup_in_table_boldcontentpart2660 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_bolditalcontent_in_table_boldcontentpart2667 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_ital_markup_in_table_boldcontentpart2674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bold_markup_in_table_italcontentpart2691 = new BitSet(new long[]{0xFFFFFFFFFFE77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_bolditalcontent_in_table_italcontentpart2698 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_bold_markup_in_table_italcontentpart2705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_formattedcontent_in_table_italcontentpart2717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_table_bolditalcontent2733 = new BitSet(new long[]{0xFFFFFFFFFFE57FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_formattedcontent_in_table_bolditalcontent2742 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_onestar_in_table_bolditalcontent2747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_table_bolditalcontent2755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_unformattedelement_in_table_formattedcontent2775 = new BitSet(new long[]{0xFFFFFFFFFFE57FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_table_unformatted_in_table_unformattedelement2798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_inlineelement_in_table_unformattedelement2810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_link_in_table_inlineelement2830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_image_in_table_inlineelement2840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_extension_in_table_inlineelement2851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nowiki_inline_in_table_inlineelement2861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_unformatted_text_in_table_unformatted2882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forced_linebreak_in_table_unformatted2891 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_escaped_in_table_unformatted2903 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_set_in_table_unformatted_text2929 = new BitSet(new long[]{0xFFFFFFFFF8057FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_nowikiblock_open_markup_in_nowiki_block3026 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_nowiki_block_contents_in_nowiki_block3033 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_nowikiblock_close_markup_in_nowiki_block3039 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_paragraph_separator_in_nowiki_block3042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nowiki_open_markup_in_nowikiblock_open_markup3053 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_newline_in_nowikiblock_open_markup3056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOWIKI_BLOCK_CLOSE_in_nowikiblock_close_markup3067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nowiki_open_markup_in_nowiki_inline3082 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_nowiki_inline_contents_in_nowiki_inline3089 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_nowiki_close_markup_in_nowiki_inline3093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_nowiki_block_contents3111 = new BitSet(new long[]{0xFFFFFFFFF7FFFFF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_set_in_nowiki_inline_contents3144 = new BitSet(new long[]{0xFFFFFFFFEFFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_horizontalrule_markup_in_horizontalrule3180 = new BitSet(new long[]{0x0000000080008000L});
    public static final BitSet FOLLOW_blanks_in_horizontalrule3185 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_paragraph_separator_in_horizontalrule3191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_link_open_markup_in_link3212 = new BitSet(new long[]{0xFFFFFFFFDFF77FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_address_in_link3218 = new BitSet(new long[]{0x0000000020080000L});
    public static final BitSet FOLLOW_link_description_markup_in_link3224 = new BitSet(new long[]{0xFFFFFFFFDE5F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_description_in_link3232 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_link_close_markup_in_link3240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_link_interwiki_uri_in_link_address3259 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_link_address3262 = new BitSet(new long[]{0xFFFFFFFFDFF77FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_interwiki_pagename_in_link_address3269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_link_uri_in_link_address3280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_link_interwiki_uri3296 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_link_interwiki_uri3298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_link_interwiki_uri3303 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_link_interwiki_uri3305 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3307 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_link_interwiki_uri3309 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_link_interwiki_uri3311 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3313 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3315 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_link_interwiki_uri3322 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_link_interwiki_uri3324 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3326 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_link_interwiki_uri3328 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3330 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_link_interwiki_uri3332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_link_interwiki_uri3337 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_link_interwiki_uri3339 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_link_interwiki_uri3341 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_link_interwiki_uri3343 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_link_interwiki_uri3345 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_link_interwiki_uri3347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_link_interwiki_uri3352 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_link_interwiki_uri3354 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_link_interwiki_uri3356 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_link_interwiki_uri3358 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3360 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3362 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_link_interwiki_uri3369 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_link_interwiki_uri3371 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_link_interwiki_uri3373 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_link_interwiki_uri3375 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_link_interwiki_uri3377 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_link_interwiki_uri3379 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_link_interwiki_uri3381 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_link_interwiki_uri3383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_link_interwiki_uri3388 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_link_interwiki_uri3390 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_link_interwiki_uri3392 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3394 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_link_interwiki_uri3396 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_link_interwiki_uri3398 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3400 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3402 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_link_interwiki_uri3409 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_link_interwiki_uri3411 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3413 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_link_interwiki_uri3415 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_link_interwiki_uri3417 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_link_interwiki_uri3419 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3421 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_link_interwiki_uri3423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_link_interwiki_uri3428 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_link_interwiki_uri3430 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_link_interwiki_uri3432 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_link_interwiki_uri3434 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_link_interwiki_uri3436 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_link_interwiki_uri3438 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_link_interwiki_uri3440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_link_interwiki_uri3445 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_link_interwiki_uri3447 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_link_interwiki_uri3449 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_link_interwiki_uri3451 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_link_interwiki_uri3453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_link_interwiki_uri3458 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_link_interwiki_uri3460 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_link_interwiki_uri3462 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3464 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3466 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_link_interwiki_uri3473 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_link_interwiki_uri3475 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3477 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3479 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_link_interwiki_uri3481 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3483 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3485 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_link_interwiki_uri3492 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_link_interwiki_uri3494 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_link_interwiki_uri3496 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_link_interwiki_uri3498 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_link_interwiki_uri3500 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_link_interwiki_uri3502 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_link_interwiki_uri3504 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3506 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3508 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_link_interwiki_uri3515 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_link_interwiki_uri3517 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_link_interwiki_uri3519 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_link_interwiki_uri3521 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_link_interwiki_uri3523 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_link_interwiki_uri3525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_link_interwiki_uri3530 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_link_interwiki_uri3532 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3534 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_link_interwiki_uri3536 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_link_interwiki_uri3538 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_link_interwiki_uri3540 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_link_interwiki_uri3542 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_link_interwiki_uri3544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_link_interwiki_uri3549 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3551 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_link_interwiki_uri3553 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_link_interwiki_uri3555 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_link_interwiki_uri3557 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_link_interwiki_uri3559 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_link_interwiki_uri3561 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3563 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3565 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_link_interwiki_uri3572 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_link_interwiki_uri3574 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3576 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3578 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_link_interwiki_uri3585 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_link_interwiki_uri3587 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_link_interwiki_uri3589 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_link_interwiki_uri3591 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_link_interwiki_uri3593 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_link_interwiki_uri3595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_link_interwiki_uri3600 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3602 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3604 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3606 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_link_interwiki_uri3608 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_link_interwiki_uri3610 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_link_interwiki_uri3612 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3614 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_link_interwiki_uri3616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_link_interwiki_uri3621 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_link_interwiki_uri3623 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3625 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_link_interwiki_uri3627 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_link_interwiki_uri3629 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_link_interwiki_pagename3649 = new BitSet(new long[]{0xFFFFFFFFDFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_descriptionpart_in_link_description3692 = new BitSet(new long[]{0xFFFFFFFFDE5F7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_in_link_description3704 = new BitSet(new long[]{0xFFFFFFFFDE5F7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_bold_markup_in_link_descriptionpart3729 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_link_descriptionpart3732 = new BitSet(new long[]{0xFFFFFFFFDE1D7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_bold_descriptionpart_in_link_descriptionpart3740 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_link_descriptionpart3745 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_bold_markup_in_link_descriptionpart3755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ital_markup_in_link_descriptionpart3760 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_link_descriptionpart3763 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_ital_descriptionpart_in_link_descriptionpart3772 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_link_descriptionpart3777 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_ital_markup_in_link_descriptionpart3786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_link_descriptionpart3791 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_descriptiontext_in_link_descriptionpart3800 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_link_descriptionpart3803 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_ital_markup_in_link_bold_descriptionpart3823 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_boldital_description_in_link_bold_descriptionpart3830 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_ital_markup_in_link_bold_descriptionpart3835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_link_descriptiontext_in_link_bold_descriptionpart3844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bold_markup_in_link_ital_descriptionpart3860 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_boldital_description_in_link_ital_descriptionpart3867 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_bold_markup_in_link_ital_descriptionpart3870 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_link_descriptiontext_in_link_ital_descriptionpart3881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_link_boldital_description3897 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_descriptiontext_in_link_boldital_description3906 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_link_boldital_description3909 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_descriptiontext_simple_in_link_descriptiontext3932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forced_linebreak_in_link_descriptiontext3942 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_escaped_in_link_descriptiontext3954 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_set_in_link_descriptiontext_simple3979 = new BitSet(new long[]{0xFFFFFFFFD80D7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_set_in_link_uri4078 = new BitSet(new long[]{0xFFFFFFFFDFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_open_markup_in_image4119 = new BitSet(new long[]{0xFFFFFFFFBFF77FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_uri_in_image4125 = new BitSet(new long[]{0x0000000040080000L});
    public static final BitSet FOLLOW_image_alternative_in_image4135 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_image_close_markup_in_image4144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_image_uri4163 = new BitSet(new long[]{0xFFFFFFFFBFF77FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_alternative_markup_in_image_alternative4198 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_alternativepart_in_image_alternative4207 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_bold_markup_in_image_alternativepart4233 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_onestar_in_image_alternativepart4236 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_bold_alternativepart_in_image_alternativepart4245 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_onestar_in_image_alternativepart4250 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_bold_markup_in_image_alternativepart4257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ital_markup_in_image_alternativepart4264 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_onestar_in_image_alternativepart4267 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_ital_alternativepart_in_image_alternativepart4277 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_onestar_in_image_alternativepart4282 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_ital_markup_in_image_alternativepart4289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_image_alternativepart4296 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_alternativetext_in_image_alternativepart4303 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_image_alternativepart4308 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_ital_markup_in_image_bold_alternativepart4334 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_boldital_description_in_image_bold_alternativepart4341 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_ital_markup_in_image_bold_alternativepart4346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_image_bold_alternativepart4351 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_alternativetext_in_image_bold_alternativepart4360 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_image_bold_alternativepart4363 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_bold_markup_in_image_ital_alternativepart4391 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_link_boldital_description_in_image_ital_alternativepart4398 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_bold_markup_in_image_ital_alternativepart4403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onestar_in_image_ital_alternativepart4408 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_alternativetext_in_image_ital_alternativepart4417 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_image_ital_alternativepart4420 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_image_boldital_alternative4441 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_alternativetext_in_image_boldital_alternative4450 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_onestar_in_image_boldital_alternative4453 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_image_alternative_simple_text_in_image_alternativetext4475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forced_linebreak_in_image_alternativetext4483 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_set_in_image_alternative_simple_text4509 = new BitSet(new long[]{0xFFFFFFFFBC0D7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_extension_markup_in_extension4601 = new BitSet(new long[]{0xFFFFFFFF7EFF7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_extension_handler_in_extension4604 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_blanks_in_extension4607 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_extension_statement_in_extension4610 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_extension_markup_in_extension4614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_extension_handler4625 = new BitSet(new long[]{0xFFFFFFFF7EFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_escaped_in_extension_handler4658 = new BitSet(new long[]{0xFFFFFFFF7EFF7FF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_set_in_extension_statement4672 = new BitSet(new long[]{0xFFFFFFFFFEFFFFF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_escaped_in_extension_statement4693 = new BitSet(new long[]{0xFFFFFFFFFEFFFFF2L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_TABLE_OF_CONTENTS_TEXT_in_table_of_contents4716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_onestar4738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ESCAPE_in_escaped4759 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_newline_in_paragraph_separator4783 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_EOF_in_paragraph_separator4791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_blanks_in_whitespaces4803 = new BitSet(new long[]{0x0000000080008002L});
    public static final BitSet FOLLOW_newline_in_whitespaces4807 = new BitSet(new long[]{0x0000000080008002L});
    public static final BitSet FOLLOW_BLANKS_in_blanks4820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_newline_in_text_lineseparator4830 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_blanks_in_text_lineseparator4835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_text_lineseparator4843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_newline4853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_bold_markup4863 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_STAR_in_bold_markup4866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ITAL_in_ital_markup4876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQUAL_in_heading_markup4886 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_POUND_in_list_ordelem_markup4896 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_list_unordelem_markup4906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_newline_in_list_elemseparator4916 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_blanks_in_list_elemseparator4921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_list_elemseparator4929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_newline_in_end_of_list4939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_end_of_list4944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PIPE_in_table_cell_markup4954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PIPE_in_table_headercell_markup4964 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_EQUAL_in_table_headercell_markup4967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_newline_in_table_rowseparator4977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_table_rowseparator4982 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOWIKI_OPEN_in_nowiki_open_markup4992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOWIKI_CLOSE_in_nowiki_close_markup5002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DASH_in_horizontalrule_markup5012 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_DASH_in_horizontalrule_markup5015 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_DASH_in_horizontalrule_markup5018 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_DASH_in_horizontalrule_markup5021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LINK_OPEN_in_link_open_markup5031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LINK_CLOSE_in_link_close_markup5041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PIPE_in_link_description_markup5051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IMAGE_OPEN_in_image_open_markup5061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IMAGE_CLOSE_in_image_close_markup5071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PIPE_in_image_alternative_markup5081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EXTENSION_in_extension_markup5091 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FORCED_LINEBREAK_in_forced_linebreak5101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOWIKI_OPEN_in_synpred1317 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x000000000000FFFFL});
    public static final BitSet FOLLOW_set_in_synpred1320 = new BitSet(new long[]{0x0000000000000002L});

}
