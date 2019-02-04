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

package com.liferay.wiki.engine.creole.internal.parser.visitor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.wiki.engine.creole.internal.parser.ast.ASTNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.BoldTextNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.CollectionNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.ForcedEndOfLineNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.FormattedTextNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.HeadingNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.HorizontalNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.ImageNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.ItalicTextNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.LineNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.ListNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.NoWikiInlineNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.NoWikiSectionNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.OrderedListItemNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.OrderedListNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.ParagraphNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.ScapedNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.UnformattedTextNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.UnorderedListItemNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.UnorderedListNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.WikiPageNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.extension.TableOfContentsNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.LinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.C2InterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.DokuWikiInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.FlickrInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.GoogleInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.InterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.JSPWikiInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.MeatballInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.MediaWikiInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.MoinMoinInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.OddmuseInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.OhanaInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.PmWikiInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.PukiWikiInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.PurpleWikiInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.RadeoxInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.SnipSnapInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.TWikiInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.TiddlyWikiInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.UsemodInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.WikipediaInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki.XWikiInterwikiLinkNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.table.TableDataNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.table.TableHeaderNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.table.TableNode;

import java.util.List;

/**
 * @author Miguel Pastor
 */
public class XhtmlTranslationVisitor implements ASTVisitor {

	public String translate(WikiPageNode wikiPageNode) {
		_sb.setIndex(0);

		visit(wikiPageNode);

		return _sb.toString();
	}

	@Override
	public void visit(BoldTextNode boldTextNode) {
		append("<strong>");

		if (boldTextNode.hasContent()) {
			traverse(boldTextNode.getChildASTNodes());
		}

		append("</strong>");
	}

	@Override
	public void visit(C2InterwikiLinkNode c2InterwikiLinkNode) {
		appendInterwikiLinkNode(c2InterwikiLinkNode);
	}

	@Override
	public void visit(CollectionNode collectionNode) {
		for (ASTNode astNode : collectionNode.getASTNodes()) {
			astNode.accept(this);
		}
	}

	@Override
	public void visit(DokuWikiInterwikiLinkNode dokuWikiInterwikiLinkNode) {
		appendInterwikiLinkNode(dokuWikiInterwikiLinkNode);
	}

	@Override
	public void visit(FlickrInterwikiLinkNode flickrInterwikiLinkNode) {
		appendInterwikiLinkNode(flickrInterwikiLinkNode);
	}

	@Override
	public void visit(ForcedEndOfLineNode forcedEndOfLineNode) {
		append("<br/>");
	}

	@Override
	public void visit(FormattedTextNode formattedTextNode) {
		if (formattedTextNode.getContent() != null) {
			append(HtmlUtil.escape(formattedTextNode.getContent()));
		}
		else {
			traverse(formattedTextNode.getChildASTNodes());
		}
	}

	@Override
	public void visit(GoogleInterwikiLinkNode googleInterwikiLinkNode) {
		appendInterwikiLinkNode(googleInterwikiLinkNode);
	}

	@Override
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

	@Override
	public void visit(HorizontalNode horizontalNode) {
		append("<hr/>");
	}

	@Override
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

	@Override
	public void visit(ItalicTextNode italicTextNode) {
		append("<em>");

		if (italicTextNode.hasContent()) {
			traverse(italicTextNode.getChildASTNodes());
		}

		append("</em>");
	}

	@Override
	public void visit(JSPWikiInterwikiLinkNode jspWikiInterwikiLinkNode) {
		appendInterwikiLinkNode(jspWikiInterwikiLinkNode);
	}

	@Override
	public void visit(LineNode lineNode) {
		traverse(lineNode.getChildASTNodes(), null, StringPool.SPACE);
	}

	@Override
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

	@Override
	public void visit(ListNode listNode) {
		traverse(listNode.getChildASTNodes());
	}

	@Override
	public void visit(MeatballInterwikiLinkNode meatballInterwikiLinkNode) {
		appendInterwikiLinkNode(meatballInterwikiLinkNode);
	}

	@Override
	public void visit(MediaWikiInterwikiLinkNode mediaWikiInterwikiLinkNode) {
		appendInterwikiLinkNode(mediaWikiInterwikiLinkNode);
	}

	@Override
	public void visit(MoinMoinInterwikiLinkNode moinMoinInterwikiLinkNode) {
		appendInterwikiLinkNode(moinMoinInterwikiLinkNode);
	}

	@Override
	public void visit(NoWikiInlineNode noWikiInlineNode) {
		append("<tt>");
		append(HtmlUtil.escape(noWikiInlineNode.getContent()));
		append("</tt>");
	}

	@Override
	public void visit(NoWikiSectionNode noWikiSectionNode) {
		append("<pre>");
		append(HtmlUtil.escape(noWikiSectionNode.getContent()));
		append("</pre>");
	}

	@Override
	public void visit(OddmuseInterwikiLinkNode oddmuseInterwikiLinkNode) {
		appendInterwikiLinkNode(oddmuseInterwikiLinkNode);
	}

	@Override
	public void visit(OhanaInterwikiLinkNode ohanaInterwikiLinkNode) {
		appendInterwikiLinkNode(ohanaInterwikiLinkNode);
	}

	@Override
	public void visit(OrderedListItemNode orderedListItemNode) {
		traverse(orderedListItemNode.getChildASTNodes(), "<li>", "</li>");
	}

	@Override
	public void visit(OrderedListNode orderedListNode) {
		append("<ol>");

		traverse(orderedListNode.getChildASTNodes());

		append("</ol>");
	}

	@Override
	public void visit(ParagraphNode paragraphNode) {
		traverse(paragraphNode.getChildASTNodes(), "<p>", "</p>");
	}

	@Override
	public void visit(PmWikiInterwikiLinkNode pmWikiInterwikiLinkNode) {
		appendInterwikiLinkNode(pmWikiInterwikiLinkNode);
	}

	@Override
	public void visit(PukiWikiInterwikiLinkNode pukiWikiInterwikiLinkNode) {
		appendInterwikiLinkNode(pukiWikiInterwikiLinkNode);
	}

	@Override
	public void visit(PurpleWikiInterwikiLinkNode purpleWikiInterwikiLinkNode) {
		appendInterwikiLinkNode(purpleWikiInterwikiLinkNode);
	}

	@Override
	public void visit(RadeoxInterwikiLinkNode radeoxInterwikiLinkNode) {
		appendInterwikiLinkNode(radeoxInterwikiLinkNode);
	}

	@Override
	public void visit(ScapedNode scapedNode) {
		append(HtmlUtil.escape(scapedNode.getContent()));
	}

	@Override
	public void visit(SnipSnapInterwikiLinkNode snipSnapInterwikiLinkNode) {
		appendInterwikiLinkNode(snipSnapInterwikiLinkNode);
	}

	@Override
	public void visit(TableDataNode tableDataNode) {
		traverse(tableDataNode.getChildASTNodes(), "<td>", "</td>");
	}

	@Override
	public void visit(TableHeaderNode tableHeaderNode) {
		traverse(tableHeaderNode.getChildASTNodes(), "<th>", "</th>");
	}

	@Override
	public void visit(TableNode tableNode) {
		append("<table>");

		traverseAndWriteForEach(tableNode.getChildASTNodes(), "<tr>", "</tr>");

		append("</table>");
	}

	@Override
	public void visit(TableOfContentsNode tableOfContentsNode) {
	}

	@Override
	public void visit(TiddlyWikiInterwikiLinkNode tiddlyWikiInterwikiLinkNode) {
		appendInterwikiLinkNode(tiddlyWikiInterwikiLinkNode);
	}

	@Override
	public void visit(TWikiInterwikiLinkNode tWikiInterwikiLinkNode) {
		appendInterwikiLinkNode(tWikiInterwikiLinkNode);
	}

	@Override
	public void visit(UnformattedTextNode unformattedTextNode) {
		if (unformattedTextNode.hasContent()) {
			append(HtmlUtil.escape(unformattedTextNode.getContent()));
		}
		else {
			traverse(unformattedTextNode.getChildASTNodes());
		}
	}

	@Override
	public void visit(UnorderedListItemNode unorderedListItemNode) {
		traverse(unorderedListItemNode.getChildASTNodes(), "<li>", "</li>");
	}

	@Override
	public void visit(UnorderedListNode unorderedListNode) {
		append("<ul>");

		traverse(unorderedListNode.getChildASTNodes());

		append("</ul>");
	}

	@Override
	public void visit(UsemodInterwikiLinkNode usemodInterwikiLinkNode) {
		appendInterwikiLinkNode(usemodInterwikiLinkNode);
	}

	@Override
	public void visit(WikiPageNode wikiPageNode) {
		traverse(wikiPageNode.getChildASTNodes());
	}

	@Override
	public void visit(WikipediaInterwikiLinkNode wikipediaInterwikiLinkNode) {
		appendInterwikiLinkNode(wikipediaInterwikiLinkNode);
	}

	@Override
	public void visit(XWikiInterwikiLinkNode xWikiInterwikiLinkNode) {
		appendInterwikiLinkNode(xWikiInterwikiLinkNode);
	}

	protected void append(Object object) {
		if (object != null) {
			_sb.append(object);
		}
	}

	protected void appendInterwikiLinkNode(
		InterwikiLinkNode interwikiLinkNode) {

		append("<a href=\"");
		append(HtmlUtil.escape(interwikiLinkNode.getURL()));
		append("\">");
		append(HtmlUtil.escape(interwikiLinkNode.getTitle()));
		append("</a>");
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
		if ((astNodes == null) || astNodes.isEmpty()) {
			return;
		}

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

	private final StringBundler _sb = new StringBundler();

}