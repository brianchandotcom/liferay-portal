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

package com.liferay.portal.parsers.creole.visitor.impl;

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

/**
 * @author Miguel Pastor
 */
public abstract class BaseASTVisitor implements ASTVisitor {

	public void visit(BoldTextNode boldTextNode) {
		if (boldTextNode.getChildASTNodesCount() > 0) {
			traverse(boldTextNode.getChildASTNodes());
		}
	}

	public void visit(CollectionNode collectionNode) {
		for (ASTNode curNode : collectionNode.getASTNodes()) {
			curNode.accept(this);
		}
	}

	public void visit(ForcedEndOfLineNode forcedEndOfLineNode) {
	}

	public void visit(FormattedTextNode formattedTextNode) {
		if (formattedTextNode.getChildASTNodesCount() > 0) {
			traverse(formattedTextNode.getChildASTNodes());
		}
	}

	public void visit(HeadingNode headingNode) {
		traverse(headingNode.getChildASTNodes());
	}

	public void visit(HorizontalNode horizontalNode) {
	}

	public void visit(ImageNode imageNode) {
		if (imageNode.hasAltCollectionNode()) {
			traverse(imageNode.getAltNode().getASTNodes());
		}
	}

	public void visit(ItalicTextNode italicTextNode) {
		if (italicTextNode.getChildASTNodesCount() > 0) {
			traverse(italicTextNode.getChildASTNodes());
		}
	}

	public void visit(LineNode lineNode) {
		traverse(lineNode.getChildASTNodes());
	}

	public void visit(LinkNode linkNode) {
		if (linkNode.hasAltCollectionNode()) {
			traverse(linkNode.getAltCollectionNode().getASTNodes());
		}
	}

	public void visit(ListNode listNode) {
		traverse(listNode.getChildASTNodes());
	}

	public void visit(NoWikiSectionNode noWikiSectionNode) {
	}

	public void visit(OrderedListItemNode orderedListItemNode) {
		traverse(orderedListItemNode.getChildASTNodes());
	}

	public void visit(OrderedListNode orderedListNode) {
		traverse(orderedListNode.getChildASTNodes());
	}

	public void visit(ParagraphNode paragraphNode) {
		traverse(paragraphNode.getChildASTNodes());
	}

	public void visit(ScapedNode scapedNode) {
	}

	public void visit(TableDataNode tableDataNode) {
		traverse(tableDataNode.getChildASTNodes());
	}

	public void visit(TableHeaderNode tableHeaderNode) {
		traverse(tableHeaderNode.getChildASTNodes());
	}

	public void visit(TableNode tableNode) {
		traverse(tableNode.getChildASTNodes());
	}

	public void visit(TableOfContentsNode tableOfContentsNode) {
	}

	public void visit(UnformattedTextNode unformattedTextNode) {
		if (unformattedTextNode.getChildASTNodesCount() > 0) {
			traverse(unformattedTextNode.getChildASTNodes());
		}
	}

	public void visit(UnorderedListItemNode unorderedListItemNode) {
		traverse(unorderedListItemNode.getChildASTNodes());
	}

	public void visit(UnorderedListNode unorderedListNode) {
		traverse(unorderedListNode.getChildASTNodes());
	}

	public void visit(WikiPageNode wikiPageNode) {
		traverse(wikiPageNode.getChildASTNodes());
	}

	protected void traverse(List<ASTNode> astNodes) {
		if (astNodes != null) {
			for (ASTNode node : astNodes) {
				node.accept(this);
			}
		}
	}

}