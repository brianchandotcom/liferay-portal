/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.parsers.creole.visitor.impl;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.parsers.creole.ast.ASTNode;
import com.liferay.portal.parsers.creole.ast.BoldTextNode;
import com.liferay.portal.parsers.creole.ast.CollectionNode;
import com.liferay.portal.parsers.creole.ast.ForcedEndOfLineNode;
import com.liferay.portal.parsers.creole.ast.FormattedTextNode;
import com.liferay.portal.parsers.creole.ast.HeadingNode;
import com.liferay.portal.parsers.creole.ast.HorizontalNode;
import com.liferay.portal.parsers.creole.ast.ImageNode;
import com.liferay.portal.parsers.creole.ast.ItalicTextNode;
import com.liferay.portal.parsers.creole.ast.LineNode;
import com.liferay.portal.parsers.creole.ast.ListNode;
import com.liferay.portal.parsers.creole.ast.NoWikiSectionNode;
import com.liferay.portal.parsers.creole.ast.OrderedListItemNode;
import com.liferay.portal.parsers.creole.ast.OrderedListNode;
import com.liferay.portal.parsers.creole.ast.ParagraphNode;
import com.liferay.portal.parsers.creole.ast.ScapedNode;
import com.liferay.portal.parsers.creole.ast.UnformattedTextNode;
import com.liferay.portal.parsers.creole.ast.UnorderedListItemNode;
import com.liferay.portal.parsers.creole.ast.UnorderedListNode;
import com.liferay.portal.parsers.creole.ast.WikiPageNode;
import com.liferay.portal.parsers.creole.ast.extension.TableOfContentsNode;
import com.liferay.portal.parsers.creole.ast.link.LinkNode;
import com.liferay.portal.parsers.creole.ast.table.TableDataNode;
import com.liferay.portal.parsers.creole.ast.table.TableHeaderNode;
import com.liferay.portal.parsers.creole.ast.table.TableNode;
import com.liferay.portal.parsers.creole.visitor.ASTVisitor;

import java.util.List;
import java.util.Stack;

/**
 * @author Miguel Pastor
 */
public class XhtmlTranslationVisitor implements ASTVisitor {

	public String translate(WikiPageNode wikiPageNode) {
		_sb.setIndex(0);

		visit(wikiPageNode);

		return _sb.toString();
	}

	public void visit(BoldTextNode boldTextNode) {
		append("<strong>");

		if (boldTextNode.hasContent()) {
			traverse(boldTextNode.getChildASTNodes());
		}

		append("</strong>");
	}

	public void visit(CollectionNode collectionNode) {
		for (ASTNode astNode : collectionNode.getASTNodes()) {
			astNode.accept(this);
		}
	}

	public void visit(ForcedEndOfLineNode forcedEndOfLineNode) {
		append("<br/>");
	}

	public void visit(FormattedTextNode formattedTextNode) {
		if (formattedTextNode.getContent() != null) {
			append(HtmlUtil.escape(formattedTextNode.getContent()));
		}
		else {
			traverse(formattedTextNode.getChildASTNodes());
		}
	}

	public void visit(HeadingNode headingNode) {
		int level = headingNode.getLevel();

		append("<h");
		append(level);
		append(">");

		traverse(headingNode.getChildASTNodes());

		append("</h");
		append(level);
		append(">");
	}

	public void visit(HorizontalNode horizontalNode) {
		append("<hr/>");
	}

	public void visit(ImageNode imageNode) {
		append("<img src=\"");
		append(HtmlUtil.escape(imageNode.getLink()));
		append("\" ");

		if (imageNode.hasAltCollectionNode()) {
			append("alt=\"");

			CollectionNode altCollectionNode = imageNode.getAltNode();

			traverse(altCollectionNode.getASTNodes());

			append("\"");
		}

		append("/>");
	}

	public void visit(ItalicTextNode italicTextNode) {
		append("<em>");

		if (italicTextNode.hasContent()) {
			traverse(italicTextNode.getChildASTNodes());
		}

		append("</em>");
	}

	public void visit(LineNode lineNode) {
		traverse(lineNode.getChildASTNodes(), null, StringPool.SPACE);
	}

	public void visit(LinkNode linkNode) {
		append("<a href=\"");
		append(HtmlUtil.escape(linkNode.getLink()));
		append("\">");

		if (linkNode.hasAltCollectionNode()) {
			CollectionNode altCollectionNode = linkNode.getAltCollectionNode();

			traverse(altCollectionNode.getASTNodes());
		}
		else {
			append(HtmlUtil.escape(linkNode.getLink()));
		}

		append("</a>");
	}

	public void visit(ListNode listNode) {
		traverse(listNode.getChildASTNodes());
	}

	public void visit(NoWikiSectionNode noWikiSectionNode) {
		append("<pre>");
		append(HtmlUtil.escape(noWikiSectionNode.getContent()));
		append("</pre>");
	}

	public void visit(OrderedListItemNode orderedListItemNode) {
		traverse(orderedListItemNode.getChildASTNodes(), "<li>", "</li>");
	}

	public void visit(OrderedListNode orderedListNode) {
		append("<ol>");

		traverse(orderedListNode.getChildASTNodes());

		append("</ol>");
	}

	public void visit(ParagraphNode paragraphNode) {
		traverse(paragraphNode.getChildASTNodes(), "<p>", "</p>");
	}

	public void visit(ScapedNode scapedNode) {
		append(HtmlUtil.escape(scapedNode.getContent()));
	}

	public void visit(TableDataNode tableDataNode) {
		traverse(tableDataNode.getChildASTNodes(), "<td>", "</td>");
	}

	public void visit(TableHeaderNode tableHeaderNode) {
		traverse(tableHeaderNode.getChildASTNodes(), "<th>", "</th>");
	}

	public void visit(TableNode tableNode) {
		append("<table>");

		traverseAndWriteForEach(tableNode.getChildASTNodes(), "<tr>", "</tr>");

		append("</table>");
	}

	public void visit(TableOfContentsNode tableOfContentsNode) {
	}

	public void visit(UnformattedTextNode unformattedTextNode) {
		if (unformattedTextNode.hasContent()) {
			append(HtmlUtil.escape(unformattedTextNode.getContent()));
		}
		else {
			traverse(unformattedTextNode.getChildASTNodes());
		}
	}

	public void visit(UnorderedListItemNode unorderedListItemNode) {
		traverse(unorderedListItemNode.getChildASTNodes(), "<li>", "</li>");
	}

	public void visit(UnorderedListNode unorderedListNode) {
		append("<ul>");

		traverse(unorderedListNode.getChildASTNodes());

		append("</ul>");
	}

	public void visit(WikiPageNode wikiPageNode) {
		traverse(wikiPageNode.getChildASTNodes());
	}

	protected void append(Object object) {
		if (object != null) {
			_sb.append(object);
		}
	}

	protected void appendLevelTags(int nodeLevel, boolean ordered) {
		int diff = nodeLevel - _currentNodeLevel.pop();

		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				if (ordered) {
					append("<ol>");
				}
				else {
					append("<ul>");
				}
			}
		}
		else if (diff < 0) {
			for (int i = 0; i > diff; i--) {
				if (ordered) {
					append("</ol>");
				}
				else {
					append("</ul>");
				}
			}
		}

		_currentNodeLevel.push(nodeLevel);
	}

	protected void traverse(List<ASTNode> astNodes) {
		if (astNodes != null) {
			for (ASTNode astNode : astNodes) {
				if (astNode != null) {
					astNode.accept(this);
				}
			}
		}
	}

	protected void traverse(List<ASTNode> astNodes, String open, String close) {
		append(open);

		traverse(astNodes);

		append(close);
	}

	protected void traverseAndWriteForEach(
		List<ASTNode> astNodes, String open, String close) {

		for (ASTNode curNode : astNodes) {
			append(open);

			if (curNode != null) {
				curNode.accept(this);
			}

			append(close);
		}
	}

	private Stack<Integer> _currentNodeLevel = new Stack<Integer>();
	private StringBundler _sb = new StringBundler();

}