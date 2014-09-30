grammar Creole10;

options {
	language=Java;
}

tokens {
  FORCED_END_OF_LINE;
  HEADING_SECTION;
  HORIZONTAL_SECTION;
  LIST_ITEM;
  LIST_ITEM_PART;
  NOWIKI_SECTION;
  SCAPE_NODE;
  TEXT_NODE;
  UNORDERED_LIST;
  UNFORMATTED_TEXT;
  WIKI;
}

scope CountLevel {
  int level;
  String currentMarkup;
  String groups;
}

@header {
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.parsers.creole.ast.link.interwiki.C2InterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.DokuWikiInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.FlickrInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.GoogleInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.InterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.JSPWikiInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.MeatballInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.MediaWikiInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.MoinMoinInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.OddmuseInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.OhanaInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.PmWikiInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.PukiWikiInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.PurpleWikiInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.RadeoxInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.SnipSnapInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.TWikiInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.TiddlyWikiInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.UsemodInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.WikipediaInterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.interwiki.XWikiInterwikiLinkNode;
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
}

@lexer::header {
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
}

@members{
	public void displayRecognitionError(String[] tokenNames,RecognitionException e) {
		String header = getErrorHeader(e);
		String message = getErrorMessage(e, tokenNames);

		_errors.add(header + " " + message);
	}

	public List<String> getErrors() {
		return _errors;
	}

	public WikiPageNode getWikiPageNode() {
		if (_wikipage == null)
			throw new IllegalStateException("No successful parsing process");

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

	private List<String> _errors = new ArrayList<String>();
	private WikiPageNode _wikipage;

}

wikipage
	:	( whitespaces )?  p=paragraphs  { _wikipage = new WikiPageNode($p.sections); } EOF
	;
paragraphs returns [CollectionNode sections = new CollectionNode()]
	:	(p= paragraph {
			if ($p.node != null){ // at this moment we ignore paragraps with blanks
				$sections.add($p.node);
			}
			} )*
	;
paragraph returns [ASTNode node = null]
	:	n=nowiki_block { $node = $n.nowikiNode; }
	|	blanks  paragraph_separator
	|	( blanks )?
			(	tof = table_of_contents {$node = $tof.tableOfContents;}
			|	h =  heading { $node = $h.header;}
			|	{ input.LA(1) == DASH && input.LA(2) == DASH &&
				input.LA(3) == DASH && input.LA(4) == DASH }?
				hn = horizontalrule {$node = $hn.horizontal;}
			|	l = list {$node = $l.listNode;}
			|	t = table { $node = $t.table; }
			|	tp = text_paragraph  {$node = $tp.paragraph; }
			)  ( paragraph_separator )?
	;



///////////////////////   T E X T  P A R A G R A P H   ////////////////////////

text_paragraph returns [ ParagraphNode paragraph = new ParagraphNode() ]
	:	(	tl = text_line {  $paragraph.addChildASTNode($tl.line);  }
		|	( NOWIKI_OPEN  ~( NEWLINE ) )  =>
			nw = nowiki_inline  {$paragraph.addChildASTNode($nw.nowiki);} ( te = text_element  {$paragraph.addChildASTNode($te.item);} )*  text_lineseparator
		)+
	;
text_line returns [LineNode line = new LineNode()]
	:	first = text_firstelement  {
										if ($first.item != null) { // recovering from errors
											$line.addChildASTNode($first.item);
										}
									}
								( element = text_element  {
								if ($element.item != null) // recovering from errors
									$line.addChildASTNode($element.item);
							}
					)*  text_lineseparator
	;
text_firstelement returns [ASTNode item = null]
	:	{ input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) }?
		tf = text_formattedelement { $item = $tf.item; }
	|	tu = text_first_unformattedelement { $item = $tu.item; }
	;
text_formattedelement returns [FormattedTextNode item = null]
	:	ital_markup  ic =text_italcontent  { $item = new ItalicTextNode($ic.text); } ( ( NEWLINE )?  ital_markup )?
	|	bold_markup  bc = text_boldcontent  {$item = new BoldTextNode($bc.text); } ( ( NEWLINE )?  bold_markup )?
	;
text_boldcontent returns [ CollectionNode text = new CollectionNode() ]
	:	( NEWLINE )?  ( p = text_boldcontentpart  { $text.add($p.node); } )*
	|	EOF
	;
text_italcontent returns [ CollectionNode text = new CollectionNode() ]
	:	( NEWLINE )?  ( p = text_italcontentpart  { $text.add($p.node); } )*
	|	EOF
	;
text_element returns [ASTNode item = null]
	:	onestar  tu1 = text_unformattedelement { $item = $tu1.contents; }
	|	tu2 = text_unformattedelement  onestar { $item = $tu2.contents; }
	|	tf = text_formattedelement { $item = $tf.item; }
	;

text_boldcontentpart returns [FormattedTextNode node = null]
	:	ital_markup  t = text_bolditalcontent  {$node = new ItalicTextNode($t.items); } ( ital_markup )?
	|	tf = text_formattedcontent {$node = new FormattedTextNode($tf.items); }
	;
text_italcontentpart returns [FormattedTextNode node = null]
	:	bold_markup  t = text_bolditalcontent { $node = new BoldTextNode($t.items); } ( bold_markup )?
	|	tf =text_formattedcontent {$node = new FormattedTextNode($tf.items); }
	;
text_bolditalcontent returns [ASTNode items = null]
	:	( NEWLINE )?  ( tf =text_formattedcontent {$items = $tf.items; } )?
	|	EOF
	;
text_formattedcontent returns [CollectionNode items = new CollectionNode ()]
	:	onestar  ( t = text_unformattedelement  {$items.add($t.contents); } onestar  ( text_linebreak )? )+
	;
text_linebreak
	:	{ input.LA(2) != DASH && input.LA(2) != POUND &&
		input.LA(2) != EQUAL && input.LA(2) != NEWLINE }?
		text_lineseparator
	;
text_inlineelement returns [ASTNode element = null ]
	:	tf = text_first_inlineelement {$element = $tf.element; }
	|	nwi = nowiki_inline {$element = $nwi.nowiki; }
	;
text_first_inlineelement  returns [ASTNode element = null]
	:
		l=link {$element = $l.link;}
	|	i = image {$element = $i.image;}
	|	e= extension {$element = $e.node;}
	;
text_first_unformattedelement returns [ASTNode item = null]
	:	tfu = text_first_unformatted {$item = new UnformattedTextNode($tfu.items);}
	|	tfi = text_first_inlineelement { $item = $tfi.element; }
	;
text_first_unformatted returns [CollectionNode items = new CollectionNode()]
	:	 	t = text_first_unformmatted_text {$items.add(new UnformattedTextNode($t.text.toString()));}
		|	(forced_linebreak { $items.add(new ForcedEndOfLineNode()); }
		|	e = escaped {$items.add($e.scaped);} )+
	;

text_first_unformmatted_text returns [StringBundler text = new StringBundler()]
	:
	 (c =  ~(	POUND
			|	STAR
			|	EQUAL
			|	PIPE
			|	ITAL
			|	LINK_OPEN
			|	IMAGE_OPEN
			|	NOWIKI_OPEN
			|	EXTENSION
			|	FORCED_LINEBREAK
			|	ESCAPE
			|	NEWLINE
			|	EOF ) {$text.append($c.text); } )  +
	;
text_unformattedelement returns [ASTNode contents = null]
	:	text = text_unformatted { $contents = $text.items; }
	|	ti = text_inlineelement { $contents = $ti.element; }
	;

text_unformatted returns [CollectionNode items = new CollectionNode()]
	:		contents = text_unformated_text {$items.add(new UnformattedTextNode($contents.text.toString())); }
		|	(forced_linebreak { $items.add(new ForcedEndOfLineNode()); }
		|	e = escaped {$items.add($e.scaped);} )+
	;

text_unformated_text returns [StringBundler text = new StringBundler()]
:
	(c = ~(	ITAL
			|	STAR
			|	LINK_OPEN
			|	IMAGE_OPEN
			|	NOWIKI_OPEN
			|	EXTENSION
			|	FORCED_LINEBREAK
			|	ESCAPE
			|	NEWLINE
			|	EOF ) { $text.append($c.text);} ) +
	;

//////////////////////////////   H E A D I N G   //////////////////////////////

heading returns [ASTNode header]
	scope {
	       CollectionNode items;
	       int nestedLevel;
	       String text;
	}
	@init {
		$heading::items = new CollectionNode();
		$heading::text = new String();
	}
	:	heading_markup  {$heading::nestedLevel++;} heading_content { $header = new HeadingNode($heading::items,$heading::nestedLevel); }  ( heading_markup )?  ( blanks )?
		paragraph_separator
	;
heading_content
	:	heading_markup {$heading::nestedLevel++;}  heading_content  ( heading_markup )?
	|	ht = heading_text {$heading::items= $ht.items;}
	;

heading_text returns [CollectionNode items = null]
	:	te = heading_cellcontent {$items = $te.items;}
	;

heading_cellcontent returns [CollectionNode items = new CollectionNode()]
	:	onestar  ( tcp = heading_cellcontentpart  {

							if ($tcp.node != null) { // some AST Node could be NULL if bad CREOLE syntax is wrotten
								$items.add($tcp.node);
							}

							}
						onestar )*
	;
heading_cellcontentpart returns [ASTNode node = null]
	:	tf = heading_formattedelement {$node=$tf.content;}
	|	tu = heading_unformattedelement {$node=$tu.content;}
	;
heading_formattedelement returns [ASTNode content = null]
	:	ital_markup   ( tic = heading_italcontent { $content = new ItalicTextNode($tic.items); })?  ( ital_markup )?
	|	bold_markup ( tbc= heading_boldcontent  {$content = new BoldTextNode($tbc.items);})?  ( bold_markup )?
	;
heading_boldcontent returns [CollectionNode items = new CollectionNode()]
	:	onestar  ( tb = heading_boldcontentpart  { $items.add($tb.node); } onestar )+
	|	EOF
	;
heading_italcontent returns [CollectionNode items = new CollectionNode()]
	:	onestar  ( ti = heading_italcontentpart  { $items.add($ti.node); } onestar )+
	|	EOF
	;
heading_boldcontentpart returns [ASTNode node = null]
	:	tf = heading_formattedcontent {$node = $tf.elements; }
	|	ital_markup  tb = heading_bolditalcontent  { $node = new ItalicTextNode($tb.elements);  } ( ital_markup )?
	;
heading_italcontentpart returns [ASTNode node = null]
	:	bold_markup  tb = heading_bolditalcontent  {$node = new BoldTextNode($tb.elements); } ( bold_markup )?
	|	tf = heading_formattedcontent { $node = $tf.elements; }
	;
heading_bolditalcontent returns [CollectionNode elements = null]
	:	onestar  ( tfc = heading_formattedcontent  { $elements = $tfc.elements; } onestar )?
	|	EOF
	;
heading_formattedcontent returns [CollectionNode elements = new CollectionNode()]
	:	( tu = heading_unformattedelement { $elements.add($tu.content); } )+
	;
heading_unformattedelement returns [ASTNode content = null]
	:	tu = heading_unformatted_text  {$content = new UnformattedTextNode($tu.text.toString());}
	|	ti = heading_inlineelement {$content = $ti.element;}
	;
heading_inlineelement returns [ASTNode element = null]
	:	l = link {$element = $l.link; }
	|	i =image {$element = $i.image; }
	|	nwi = nowiki_inline {$element = $nwi.nowiki; }
	;

heading_unformatted_text returns [StringBundler text = new StringBundler()]
	:	( c = ~(LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN |EQUAL | ESCAPE | NEWLINE | EOF  )  {$text.append($c.text);} )+
	;


/////////////////////////////////   L I S T   /////////////////////////////////

list returns [ListNode listNode = null]
	scope {
		BaseListNode currentParent;
		ListNode root;
		Stack<ItemNode> parents;
		int lastLevel = 1;
	}
	@init{
		$list::root = new ListNode();

		if (input.LA(1) == POUND) {
			$list::currentParent = new OrderedListNode($list::root);
		}
		else {
			$list::currentParent = new UnorderedListNode($list::root);
		}

		$list::root.addChildASTNode($list::currentParent);

		$list::parents = new Stack<ItemNode>();
	}
	@after {
		$listNode = $list::root;
	}
	:	( elem = list_elems )+  ( end_of_list )?
	;

list_elems
	scope  CountLevel;
	@init{
		$CountLevel::level = 0;
	}
	:	om = list_ordelem_markup {++$CountLevel::level;$CountLevel::currentMarkup = $om.text;$CountLevel::groups += $om.text;}  
				elem=list_elem {  

					Stack<ItemNode> parents = $list::parents;

					ItemNode top = parents.isEmpty()?null:parents.peek();

					BaseParentableNode baseParentableNode = $list::currentParent;

					if (top == null) {
						OrderedListItemNode node = new OrderedListItemNode($CountLevel::level, baseParentableNode, $elem.items);
						baseParentableNode.addChildASTNode(node);

						parents.push(node);

					}
					else if ($CountLevel::level > $list::lastLevel) {		
						OrderedListNode orderedListNode = new OrderedListNode(top);

						OrderedListItemNode node = new OrderedListItemNode($CountLevel::level, orderedListNode, $elem.items);
						orderedListNode.addChildASTNode(node);

						top.addChildASTNode(orderedListNode);

						parents.push(node);
					} 
					else if ($CountLevel::level < $list::lastLevel) {
						ItemNode in = parents.peek();

						while (in.getLevel() > $CountLevel::level) {
							in = parents.pop();
							--$list::lastLevel;
						}

						top = in;

						baseParentableNode = top.getBaseParentableNode();

						OrderedListItemNode node = new OrderedListItemNode($CountLevel::level, baseParentableNode, $elem.items);

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

						OrderedListItemNode node = new OrderedListItemNode($CountLevel::level, baseParentableNode, $elem.items);

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

					$list::lastLevel = $CountLevel::level;
				}
	|	um = list_unordelem_markup {++$CountLevel::level; $CountLevel::currentMarkup = $um.text;$CountLevel::groups += $um.text;}  
				elem=list_elem {

					Stack<ItemNode> parents = $list::parents;

					ItemNode top = parents.isEmpty()?null:parents.peek();

					BaseParentableNode baseParentableNode = $list::currentParent;

					if (top == null) {
						UnorderedListItemNode node = new UnorderedListItemNode($CountLevel::level, baseParentableNode, $elem.items);
						baseParentableNode.addChildASTNode(node);

						parents.push(node);

					} 
					else if ($CountLevel::level > $list::lastLevel) {
						UnorderedListNode unorderedListNode = new UnorderedListNode(top);

						UnorderedListItemNode node = new UnorderedListItemNode($CountLevel::level, unorderedListNode, $elem.items);
						unorderedListNode.addChildASTNode(node);

						top.addChildASTNode(unorderedListNode);

						parents.push(node);

					} 
					else if ($CountLevel::level < $list::lastLevel) {
						ItemNode in = parents.peek();

						while (in.getLevel() > $CountLevel::level) {
							in = parents.pop();
							--$list::lastLevel;
						}

						top = in;

						baseParentableNode = top.getBaseParentableNode();

						UnorderedListItemNode node = new UnorderedListItemNode($CountLevel::level, baseParentableNode, $elem.items);

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

						UnorderedListItemNode node = new UnorderedListItemNode($CountLevel::level, baseParentableNode, $elem.items);

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

					$list::lastLevel = $CountLevel::level;
				}
	;
list_elem  returns [CollectionNode  items = null]
	:	( m = list_elem_markup {
			             ++$CountLevel::level;
			             if (!$m.text.equals($CountLevel::currentMarkup)) {
							$CountLevel::groups+= GROUPING_SEPARATOR;
			             }
			             $CountLevel::groups+= $m.text;
			             $CountLevel::currentMarkup = $m.text;
			          } )*  c =list_elemcontent  {$items = $c.items; } list_elemseparator
	;
list_elem_markup
	:	list_ordelem_markup
	|	list_unordelem_markup
	;
list_elemcontent returns [CollectionNode items = new CollectionNode()]
	:	onestar  ( part = list_elemcontentpart  { $items.add($part.node); } onestar )*
	;
list_elemcontentpart returns [ASTNode node = null]
	:	tuf = text_unformattedelement {
				if ($tuf.contents instanceof CollectionNode)
					$node = new UnformattedTextNode($tuf.contents);
				else
					$node = $tuf.contents;
				}
	|	tf = list_formatted_elem { $node = new FormattedTextNode($tf.contents);}
	;
list_formatted_elem returns [CollectionNode contents = new CollectionNode()]
	:	bold_markup  onestar  ( boldContents = list_boldcontentpart  {
						BoldTextNode add = null;
						if ($boldContents.contents instanceof CollectionNode){
						     add = new BoldTextNode($boldContents.contents);
						}
						else {
						    CollectionNode c = new CollectionNode();
						    c.add($boldContents.contents);
						    add = new BoldTextNode(c);
						}
						$contents.add(add);
						}
				onestar )*
		( bold_markup )?
	|	ital_markup    onestar  ( italContents = list_italcontentpart      {
						ItalicTextNode add = null;
						if ($italContents.contents instanceof CollectionNode){
						    add = new ItalicTextNode($italContents.contents);
						}
						else {
						      CollectionNode c = new CollectionNode();
						      c.add($italContents.contents);
						      add = new ItalicTextNode(c);
						}
						$contents.add(add);
						} onestar )*
		( ital_markup )?
	;

list_boldcontentpart returns [ASTNode  contents = null]
scope {
	List<ASTNode> elements;
}
@init{
	$list_boldcontentpart::elements = new ArrayList<ASTNode>();
}
	:	ital_markup  c = list_bolditalcontent  {$contents = new ItalicTextNode($c.text);} ( ital_markup )?
	|	( t = text_unformattedelement { $list_boldcontentpart::elements.add($t.contents); } )+ {$contents = new CollectionNode($list_boldcontentpart::elements); }
	/*|	would be equivalent ??? ( t = text_unformattedelement { $contents.add($t.contents); } )*/
	;

list_bolditalcontent returns [ASTNode  text = null]
	:	( t = text_unformattedelement { $text = $t.contents; } )+
	;

list_italcontentpart returns [ASTNode  contents  = null]
scope {
	List<ASTNode> elements;
}
@init{
	$list_italcontentpart::elements = new ArrayList<ASTNode>();
}
	:	bold_markup  c = list_bolditalcontent  { $contents = new BoldTextNode($c.text); } ( bold_markup )?
	|	( t = text_unformattedelement { $list_italcontentpart::elements.add($t.contents); })+ { $contents = new CollectionNode($list_italcontentpart::elements); }
	;

////////////////////////////////   T A B L E   ////////////////////////////////
table returns [TableNode table = new TableNode()]
	:	( tr = table_row {$table.addChildASTNode($tr.row);} )+
	;
table_row  returns [CollectionNode row = new CollectionNode()]
	:	( tc = table_cell { $row.add($tc.cell); } )+  table_rowseparator
	;
table_cell returns [TableCellNode cell = null]
	:	{ input.LA(2) == EQUAL }?  th = table_headercell {$cell = $th.header;}
	|	tc = table_normalcell {$cell = $tc.cell; }
	;
table_headercell returns [TableHeaderNode header = null]
	:	table_headercell_markup  tc = table_cellcontent {$header = new TableHeaderNode($tc.items);}
	;
table_normalcell returns [TableDataNode cell = null]
	:	table_cell_markup  tc = table_cellcontent { $cell = new TableDataNode($tc.items); }
	;
table_cellcontent returns [CollectionNode items = new CollectionNode()]
	:	onestar  ( tcp = table_cellcontentpart  {
			if ($tcp.node != null) {
				$items.add($tcp.node);
			}
		}
		onestar )*
	;
table_cellcontentpart returns [ASTNode node = null]
	:	tf = table_formattedelement {$node=$tf.content;}
	|	tu = table_unformattedelement {$node=$tu.content;}
	;
table_formattedelement returns [ASTNode content = null]
	:	ital_markup   ( tic = table_italcontent { $content = new ItalicTextNode($tic.items); })?  ( ital_markup )?
	|	bold_markup ( tbc= table_boldcontent  {$content = new BoldTextNode($tbc.items);})?  ( bold_markup )?
	;
table_boldcontent returns [CollectionNode items = new CollectionNode()]
	:	onestar  ( tb = table_boldcontentpart  { $items.add($tb.node); } onestar )+
	|	EOF
	;
table_italcontent returns [CollectionNode items = new CollectionNode()]
	:	onestar  ( ti = table_italcontentpart  { $items.add($ti.node); } onestar )+
	|	EOF
	;
table_boldcontentpart returns [ASTNode node = null]
	:	tf = table_formattedcontent {$node = $tf.elements; }
	|	ital_markup  tb = table_bolditalcontent  { $node = new ItalicTextNode($tb.elements);  } ( ital_markup )?
	;
table_italcontentpart returns [ASTNode node = null]
	:	bold_markup  tb = table_bolditalcontent  {$node = new BoldTextNode($tb.elements); } ( bold_markup )?
	|	tf = table_formattedcontent { $node = $tf.elements; }
	;
table_bolditalcontent returns [CollectionNode elements = null]
	:	onestar  ( tfc = table_formattedcontent  { $elements = $tfc.elements; } onestar )?
	|	EOF
	;
table_formattedcontent returns [CollectionNode elements = new CollectionNode()]
	:	( tu = table_unformattedelement { $elements.add($tu.content); } )+
	;
table_unformattedelement returns [ASTNode content = null]
	:	tu = table_unformatted  {$content = new UnformattedTextNode($tu.text);}
	|	ti = table_inlineelement {$content = $ti.element;}
	;
table_inlineelement returns [ASTNode element = null]
	:	l = link {$element = $l.link; }
	|	i =image {$element = $i.image; }
	|	e = extension {$element = $e.node; }
	|	nw =nowiki_inline {$element = $nw.nowiki; }
	;
table_unformatted returns [CollectionNode text = new CollectionNode()]
	:		t = table_unformatted_text { $text.add(new UnformattedTextNode($t.text.toString()));}
		|	(forced_linebreak {$text.add(new ForcedEndOfLineNode());}
		|	e = escaped {$text.add($e.scaped);} )+
	;

table_unformatted_text returns [StringBundler text = new StringBundler()]
	:	( c = ~(	PIPE
			|	ITAL
			|	STAR
			|	LINK_OPEN
			|	IMAGE_OPEN
			|	NOWIKI_OPEN
			|	EXTENSION
			|	FORCED_LINEBREAK
			|	ESCAPE
			|	NEWLINE
			|	EOF )  {$text.append($c.text);} )+
	;
///////////////////////////////   N O W I K I   ///////////////////////////////

nowiki_block returns [NoWikiSectionNode nowikiNode]
	:	nowikiblock_open_markup  contents = nowiki_block_contents {$nowikiNode = new NoWikiSectionNode($contents.text.toString());}
		nowikiblock_close_markup  paragraph_separator
	;

nowikiblock_open_markup
	:	nowiki_open_markup  newline
	;

nowikiblock_close_markup
	:	NOWIKI_BLOCK_CLOSE
	;

nowiki_inline returns [NoWikiSectionNode nowiki = null]
	:	nowiki_open_markup  t = nowiki_inline_contents
		nowiki_close_markup {$nowiki = new NoWikiSectionNode($t.text.toString());}
	;
nowiki_block_contents returns [StringBundler contents = new StringBundler()]
	:(c=~( NOWIKI_BLOCK_CLOSE | EOF ) {$contents.append($c.text);})*
	;

nowiki_inline_contents returns [StringBundler text = new StringBundler()]
	:	(c = ~( NOWIKI_CLOSE| NEWLINE | EOF )  { $text.append($c.text); })*
	;



//////////////////////   H O R I Z O N T A L   R U L E   //////////////////////

horizontalrule returns [ASTNode horizontal = null]
	:	horizontalrule_markup  ( blanks )?  paragraph_separator {$horizontal = new HorizontalNode();}
	;



////////////////////////////////   L I N K   /////////////////////////////////

link returns [LinkNode link = null]
	:	link_open_markup  a =link_address  {$link = $a.link; } (link_description_markup
		d = link_description {
			if ($link == null) { // recover from possible errors
			    $link = new LinkNode();
			}
			$link.setAltCollectionNode($d.node);

			} )?  link_close_markup
	;

link_address returns [LinkNode link =null]
	:	li = link_interwiki_uri  ':'  p = link_interwiki_pagename {
						$li.interwiki.setTitle($p.text.toString());
						$link = $li.interwiki;
					}
	|	lu = link_uri {$link = new LinkNode($lu.text.toString()); }
	;
link_interwiki_uri returns [InterwikiLinkNode interwiki = null]
	:	'C' '2' { $interwiki = new C2InterwikiLinkNode(); }
	|	'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' { $interwiki = new DokuWikiInterwikiLinkNode(); }
	|	'F' 'l' 'i' 'c' 'k' 'r'  { $interwiki = new FlickrInterwikiLinkNode(); }
	|	'G' 'o' 'o' 'g' 'l' 'e' { $interwiki = new GoogleInterwikiLinkNode(); }
	|	'J' 'S' 'P' 'W' 'i' 'k' 'i' { $interwiki = new JSPWikiInterwikiLinkNode(); }
	|	'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' { $interwiki = new MeatballInterwikiLinkNode(); }
	|	'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' { $interwiki = new MediaWikiInterwikiLinkNode(); }
	|	'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n'  { $interwiki = new MoinMoinInterwikiLinkNode(); }
	|	'O' 'd' 'd' 'm' 'u' 's' 'e'  { $interwiki = new OddmuseInterwikiLinkNode(); }
	|	'O' 'h' 'a' 'n' 'a' { $interwiki = new OhanaInterwikiLinkNode(); }
	|	'P' 'm' 'W' 'i' 'k' 'i'  { $interwiki = new PmWikiInterwikiLinkNode(); }
	|	'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i'  { $interwiki = new PukiWikiInterwikiLinkNode(); }
	|	'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' { $interwiki = new PurpleWikiInterwikiLinkNode(); }
	|	'R' 'a' 'd' 'e' 'o' 'x' { $interwiki = new RadeoxInterwikiLinkNode(); }
	|	'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' { $interwiki = new SnipSnapInterwikiLinkNode(); }
	|	'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' { $interwiki = new TiddlyWikiInterwikiLinkNode(); }
	|	'T' 'W' 'i' 'k' 'i' { $interwiki = new TWikiInterwikiLinkNode(); }
	|	'U' 's' 'e' 'm' 'o' 'd' { $interwiki = new UsemodInterwikiLinkNode(); }
	|	'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' { $interwiki = new WikipediaInterwikiLinkNode(); }
	|	'X' 'W' 'i' 'k' 'i' { $interwiki = new XWikiInterwikiLinkNode(); }
	;
link_interwiki_pagename returns [StringBundler text = new StringBundler()]
	:	( c = ~( PIPE | LINK_CLOSE | NEWLINE | EOF ) { $text.append($c.text); } ) +
	;
link_description returns [CollectionNode node = new CollectionNode()]
	:	( l = link_descriptionpart {
					// Recover code: some bad syntax could include null elements in the collection
					if ($l.text != null) {
						$node.add($l.text);
					}
				}
		| i = image {$node.add($i.image);})+
	;
link_descriptionpart returns [ASTNode text = null]
scope{
	CollectionNode element;
}
@init{
	$link_descriptionpart::element = new CollectionNode();
}
	:	bold_markup  onestar ( lb = link_bold_descriptionpart  {$link_descriptionpart::element.add($lb.text);} onestar )+  {$text = new BoldTextNode($link_descriptionpart::element);}
		bold_markup
	|	ital_markup  onestar  ( li = link_ital_descriptionpart {$link_descriptionpart::element.add($li.text);}  onestar )+ {$text = new ItalicTextNode($link_descriptionpart::element);}
		ital_markup
	|	onestar  ( t = link_descriptiontext  onestar {$link_descriptionpart::element.add($t.text);})+ {$text = new UnformattedTextNode($link_descriptionpart::element);}
	;
link_bold_descriptionpart returns [ASTNode text = null]
	:	ital_markup  t = link_boldital_description {$text = new ItalicTextNode($t.text);}  ital_markup
	|	ld = link_descriptiontext {$text =$ld.text;}
	;
link_ital_descriptionpart returns [ASTNode text = null]
	:	bold_markup  td = link_boldital_description  bold_markup {$text = new BoldTextNode($td.text);}
	|	t = link_descriptiontext {$text = $t.text; }
	;
link_boldital_description returns [CollectionNode text = new CollectionNode()]
	:	onestar  ( t = link_descriptiontext  onestar {
					for (ASTNode item:$t.text.getASTNodes()) {
						$text.add(item);
					}
				})+
	;
link_descriptiontext returns [CollectionNode text = new CollectionNode()]
	:		t = link_descriptiontext_simple { $text.add(new UnformattedTextNode($t.text.toString()));}
		|	( forced_linebreak {$text.add(new ForcedEndOfLineNode());}
		|	e = escaped {$text.add($e.scaped);} )+
	;
link_descriptiontext_simple returns [StringBundler text = new StringBundler()]
	:	( c = ~(LINK_CLOSE
			|	ITAL
			|	STAR
			|	LINK_OPEN
			|	IMAGE_OPEN
			|	NOWIKI_OPEN
			|	EXTENSION
			|	FORCED_LINEBREAK
			|	ESCAPE
			|	NEWLINE
			|	EOF ) { $text.append($c.text); } )+
	;
link_uri returns [StringBundler text = new StringBundler()]
	:	( c = ~( PIPE | LINK_CLOSE | NEWLINE | EOF ) {$text.append($c.text); } )+
	;



////////////////////////////////   I M A G E   ////////////////////////////////

image returns [ImageNode image = new ImageNode()]
	:	image_open_markup uri = image_uri {$image.setLink($uri.link.toString());}  ( alt =image_alternative {$image.setAltCollectionNode($alt.alternative);} )?
		image_close_markup
	;
image_uri returns [StringBundler link = new StringBundler()]
	:	(c = ~( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) {$link.append($c.text); })+
	;
image_alternative returns [CollectionNode alternative = new CollectionNode()]
	:	image_alternative_markup  ( p = image_alternativepart {$alternative.add($p.item); } )+
	;
image_alternativepart returns [ASTNode item = null]
scope{
    CollectionNode elements;
}
@init{
   $image_alternativepart::elements = new CollectionNode();
}
	:	bold_markup  onestar  ( t1 = image_bold_alternativepart  {$image_alternativepart::elements.add($t1.text);} onestar )+
		bold_markup {$item = new BoldTextNode($image_alternativepart::elements);}
	|	ital_markup  onestar  (  t2 = image_ital_alternativepart  {$image_alternativepart::elements.add($t2.text);} onestar )+
		ital_markup {$item = new ItalicTextNode($image_alternativepart::elements);}
	|	onestar  ( t3=image_alternativetext  {
					for (ASTNode n: $t3.items.getASTNodes()) {
					   $image_alternativepart::elements.add(n);
					 }
				              } onestar )+ {$item =new UnformattedTextNode($image_alternativepart::elements);}
	;
image_bold_alternativepart returns [ASTNode text = null]
scope{
    CollectionNode elements;
}
@init{
   $image_bold_alternativepart::elements = new CollectionNode();
}
	:	ital_markup  t = link_boldital_description  {$text = new ItalicTextNode($t.text); } ital_markup
	|	onestar  ( i = image_alternativetext  onestar{
					for (ASTNode item:$i.items.getASTNodes()) {
					    $image_ital_alternativepart::elements.add(item);
					}
					} )+ {$text = new UnformattedTextNode($image_bold_alternativepart::elements);}
	;

image_ital_alternativepart returns [ASTNode text = null]
scope{
    CollectionNode elements;
}
@init{
   $image_ital_alternativepart::elements = new CollectionNode();
}
	:	bold_markup  t = link_boldital_description  {$text = new BoldTextNode($t.text); } bold_markup
	|	onestar  (i =  image_alternativetext  onestar {
					for (ASTNode item:$i.items.getASTNodes()) {
					    $image_ital_alternativepart::elements.add(item);
					}
					} )+ {$text = new UnformattedTextNode($image_ital_alternativepart::elements);}
	;
image_boldital_alternative returns [CollectionNode text = new CollectionNode()]
	:	onestar  ( i = image_alternativetext  onestar {
					for (ASTNode item:$i.items.getASTNodes()) {
					    $text.add(item);
					}
					})+
	;
image_alternativetext returns [CollectionNode items = new CollectionNode()]
	:	contents = image_alternative_simple_text {$items.add(new UnformattedTextNode($contents.text.toString())); }
	|	(forced_linebreak {$items.add(new ForcedEndOfLineNode());})+
	;

image_alternative_simple_text returns [StringBundler text = new StringBundler()]
	:
	( c = ~( 	IMAGE_CLOSE
			|	ITAL
			|	STAR
			|	LINK_OPEN
			|	IMAGE_OPEN
			|	NOWIKI_OPEN
			|	EXTENSION
			|	FORCED_LINEBREAK
			|	NEWLINE
			|	EOF ) {$text.append($c.text); } ) +
	;

/////////////////////////////  E X T E N S I O N  /////////////////////////////

extension returns [ASTNode node = null]
	:	extension_markup  extension_handler  blanks  extension_statement
		extension_markup
	;

extension_handler
	:	(~( EXTENSION  |  BLANKS  |  ESCAPE  |  NEWLINE  |  EOF ) | escaped )+
	;

extension_statement
	:	(~( EXTENSION  |  ESCAPE  |  EOF ) | escaped )*
	;


/////////////////////////////  TABLE OF CONTENTS EXTENSION  /////////////////////////////

table_of_contents returns [TableOfContentsNode tableOfContents = new TableOfContentsNode()]
	:
		(
			'<<TableOfContents>>'
			|
			'<<TableOfContents title='
			'\"'
			t = table_of_contents_title_text { tableOfContents.setTitle($t.text.toString()); }
			'\"'
			'>>'
		)
	;


table_of_contents_title_text returns [StringBundler text = new StringBundler()]
	:	( c = ~(LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN |EQUAL | ESCAPE | NEWLINE | EOF | '>>' )  {$text.append($c.text);} )+
	;

onestar
	:	( { input.LA(2) != STAR }?  ( STAR )?)
	|
	;
escaped returns [ScapedNode scaped = new ScapedNode()]
	:	ESCAPE   c =. { $scaped.setContent($c.text) ; }
		// '.' in a parser rule means arbitrary token, not character
	;
paragraph_separator
	:	( newline )+
	|	EOF
	;
whitespaces
	:	( blanks | newline )+
	;
blanks
	:	BLANKS
	;
text_lineseparator
	:	newline  ( blanks )?
	|	EOF
	;
newline
	:	NEWLINE
	;
bold_markup
	:	STAR  STAR
	;
ital_markup
	:	ITAL
	;
heading_markup
	:	EQUAL
	;
list_ordelem_markup
	:	POUND
	;
list_unordelem_markup
	:	STAR
	;
list_elemseparator
	:	newline  ( blanks )?
	|	EOF
	;
end_of_list
	:	newline
	|	EOF
	;
table_cell_markup
	:	PIPE
	;
table_headercell_markup
	:	PIPE  EQUAL
	;
table_rowseparator
	:	newline
	|	EOF
	;
nowiki_open_markup
	:	NOWIKI_OPEN
	;
nowiki_close_markup
	:	NOWIKI_CLOSE
	;
horizontalrule_markup
	:	DASH  DASH  DASH  DASH
	;
link_open_markup
	:	LINK_OPEN
	;
link_close_markup
	:	LINK_CLOSE
	;
link_description_markup
	:	PIPE
	;
image_open_markup
	:	IMAGE_OPEN
	;
image_close_markup
	:	IMAGE_CLOSE
	;
image_alternative_markup
	:	PIPE
	;
extension_markup
	:	EXTENSION
	;
forced_linebreak
	:	FORCED_LINEBREAK
	;



///////////////////////////////////////////////////////////////////////////////
///////////////////////// S C A N N E R    R U L E S //////////////////////////
///////////////////////////////////////////////////////////////////////////////

ESCAPE					: '~';
NOWIKI_BLOCK_CLOSE		: NEWLINE  '}}}';
NEWLINE					: ( CR )?  LF
						| CR;
fragment CR				: '\r';
fragment LF				: '\n';

BLANKS					: ( SPACE | TABULATOR )+;
fragment SPACE			: ' ';
fragment TABULATOR		: '\t';

BRACE_CLOSE				: NEWLINE '}';
COLON_SLASH				: ':'  '/';
ITAL					: '//';
NOWIKI_OPEN				: '{{{';
NOWIKI_CLOSE			: '}}}';
LINK_OPEN				: '[[';
LINK_CLOSE				: ']]';
IMAGE_OPEN				: '{{';
IMAGE_CLOSE				: '}}';
FORCED_LINEBREAK		: '\\\\';
EQUAL					: '=';
PIPE					: '|';
POUND					: '#';
DASH					: '-';
STAR					: '*';
SLASH					: '/';
EXTENSION				: '@@';

INSIGNIFICANT_CHAR		: .;