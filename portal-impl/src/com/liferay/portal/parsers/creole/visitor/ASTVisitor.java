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

package com.liferay.portal.parsers.creole.visitor;

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

/**
 * @author Miguel Pastor
 */
public interface ASTVisitor {

	public void visit(BoldTextNode boldTextNode);

	public void visit(CollectionNode collectionNode);

	public void visit(ForcedEndOfLineNode forcedEndOfLineNode);

	public void visit(FormattedTextNode formattedTextNode);

	public void visit(HeadingNode headingNode);

	public void visit(HorizontalNode horizontalNode);

	public void visit(ImageNode imageNode);

	public void visit(ItalicTextNode italicTextNode);

	public void visit(LineNode lineNode);

	public void visit(LinkNode linkNode);

	public void visit(ListNode linkNode);

	public void visit(NoWikiSectionNode noWikiSectionNode);

	public void visit(OrderedListItemNode orderedListItemNode);

	public void visit(OrderedListNode orderedListNode);

	public void visit(ParagraphNode paragraphNode);

	public void visit(ScapedNode scapedNode);

	public void visit(TableDataNode tableDataNode);

	public void visit(TableHeaderNode tableHeaderNode);

	public void visit(TableNode tableNode);

	public void visit(TableOfContentsNode tableOfContentsNode);

	public void visit(UnformattedTextNode unformattedTextNode);

	public void visit(UnorderedListItemNode unorderedListItemNode);

	public void visit(UnorderedListNode unorderedListNode);

	public void visit(WikiPageNode wikiPageNode);

}