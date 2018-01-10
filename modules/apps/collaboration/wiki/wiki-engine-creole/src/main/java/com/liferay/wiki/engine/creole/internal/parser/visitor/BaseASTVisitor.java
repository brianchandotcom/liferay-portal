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
public abstract class BaseASTVisitor implements ASTVisitor {

	@Override
	public void visit(BoldTextNode boldTextNode) {
		if (boldTextNode.getChildASTNodesCount() > 0) {
			traverse(boldTextNode.getChildASTNodes());
		}
	}

	@Override
	public void visit(C2InterwikiLinkNode c2InterwikiLinkNode) {
	}

	@Override
	public void visit(CollectionNode collectionNode) {
		for (ASTNode curNode : collectionNode.getASTNodes()) {
			curNode.accept(this);
		}
	}

	@Override
	public void visit(DokuWikiInterwikiLinkNode dokuWikiInterwikiLinkNode) {
	}

	@Override
	public void visit(FlickrInterwikiLinkNode flickrInterwikiLinkNode) {
	}

	@Override
	public void visit(ForcedEndOfLineNode forcedEndOfLineNode) {
	}

	@Override
	public void visit(FormattedTextNode formattedTextNode) {
		if (formattedTextNode.getChildASTNodesCount() > 0) {
			traverse(formattedTextNode.getChildASTNodes());
		}
	}

	@Override
	public void visit(GoogleInterwikiLinkNode googleInterwikiLinkNode) {
	}

	@Override
	public void visit(HeadingNode headingNode) {
		traverse(headingNode.getChildASTNodes());
	}

	@Override
	public void visit(HorizontalNode horizontalNode) {
	}

	@Override
	public void visit(ImageNode imageNode) {
		if (imageNode.hasAltCollectionNode()) {
			CollectionNode altNode = imageNode.getAltNode();

			traverse(altNode.getASTNodes());
		}
	}

	@Override
	public void visit(ItalicTextNode italicTextNode) {
		if (italicTextNode.getChildASTNodesCount() > 0) {
			traverse(italicTextNode.getChildASTNodes());
		}
	}

	@Override
	public void visit(JSPWikiInterwikiLinkNode jspWikiInterwikiLinkNode) {
	}

	@Override
	public void visit(LineNode lineNode) {
		traverse(lineNode.getChildASTNodes());
	}

	@Override
	public void visit(LinkNode linkNode) {
		if (linkNode.hasAltCollectionNode()) {
			CollectionNode altCollectionNode = linkNode.getAltCollectionNode();

			traverse(altCollectionNode.getASTNodes());
		}
	}

	@Override
	public void visit(ListNode listNode) {
		traverse(listNode.getChildASTNodes());
	}

	@Override
	public void visit(MeatballInterwikiLinkNode meatballInterwikiLinkNode) {
	}

	@Override
	public void visit(MediaWikiInterwikiLinkNode mediaWikiInterwikiLinkNode) {
	}

	@Override
	public void visit(MoinMoinInterwikiLinkNode moinMoinInterwikiLinkNode) {
	}

	@Override
	public void visit(NoWikiInlineNode noWikiInlineNode) {
	}

	@Override
	public void visit(NoWikiSectionNode noWikiSectionNode) {
	}

	@Override
	public void visit(OddmuseInterwikiLinkNode oddmuseInterwikiLinkNode) {
	}

	@Override
	public void visit(OhanaInterwikiLinkNode ohanaInterwikiLinkNode) {
	}

	@Override
	public void visit(OrderedListItemNode orderedListItemNode) {
		traverse(orderedListItemNode.getChildASTNodes());
	}

	@Override
	public void visit(OrderedListNode orderedListNode) {
		traverse(orderedListNode.getChildASTNodes());
	}

	@Override
	public void visit(ParagraphNode paragraphNode) {
		traverse(paragraphNode.getChildASTNodes());
	}

	@Override
	public void visit(PmWikiInterwikiLinkNode pmWikiInterwikiLinkNode) {
	}

	@Override
	public void visit(PukiWikiInterwikiLinkNode pukiWikiInterwikiLinkNode) {
	}

	@Override
	public void visit(PurpleWikiInterwikiLinkNode purpleWikiInterwikiLinkNode) {
	}

	@Override
	public void visit(RadeoxInterwikiLinkNode radeoxInterwikiLinkNode) {
	}

	@Override
	public void visit(ScapedNode scapedNode) {
	}

	@Override
	public void visit(SnipSnapInterwikiLinkNode snipSnapInterwikiLinkNode) {
	}

	@Override
	public void visit(TableDataNode tableDataNode) {
		traverse(tableDataNode.getChildASTNodes());
	}

	@Override
	public void visit(TableHeaderNode tableHeaderNode) {
		traverse(tableHeaderNode.getChildASTNodes());
	}

	@Override
	public void visit(TableNode tableNode) {
		traverse(tableNode.getChildASTNodes());
	}

	@Override
	public void visit(TableOfContentsNode tableOfContentsNode) {
	}

	@Override
	public void visit(TiddlyWikiInterwikiLinkNode tiddlyWikiInterwikiLinkNode) {
	}

	@Override
	public void visit(TWikiInterwikiLinkNode tWikiInterwikiLinkNode) {
	}

	@Override
	public void visit(UnformattedTextNode unformattedTextNode) {
		if (unformattedTextNode.getChildASTNodesCount() > 0) {
			traverse(unformattedTextNode.getChildASTNodes());
		}
	}

	@Override
	public void visit(UnorderedListItemNode unorderedListItemNode) {
		traverse(unorderedListItemNode.getChildASTNodes());
	}

	@Override
	public void visit(UnorderedListNode unorderedListNode) {
		traverse(unorderedListNode.getChildASTNodes());
	}

	@Override
	public void visit(UsemodInterwikiLinkNode usemodInterwikiLinkNode) {
	}

	@Override
	public void visit(WikiPageNode wikiPageNode) {
		traverse(wikiPageNode.getChildASTNodes());
	}

	@Override
	public void visit(WikipediaInterwikiLinkNode wikipediaInterwikiLinkNode) {
	}

	@Override
	public void visit(XWikiInterwikiLinkNode xWikiInterwikiLinkNode) {
	}

	protected void traverse(List<ASTNode> astNodes) {
		if (astNodes != null) {
			for (ASTNode node : astNodes) {
				node.accept(this);
			}
		}
	}

}