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

package com.liferay.wiki.engine.creole.internal.antlrwiki.translator;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.TreeNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.HeadingNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.WikiPageNode;
import com.liferay.wiki.engine.creole.internal.parser.visitor.BaseASTVisitor;

import java.util.List;

/**
 * @author Miguel Pastor
 */
public class TableOfContentsVisitor extends BaseASTVisitor {

	public TreeNode<HeadingNode> compose(WikiPageNode wikiPageNode) {
		_headingNode = new TreeNode<>(new HeadingNode(Integer.MIN_VALUE));

		visit(wikiPageNode);

		return _headingNode;
	}

	@Override
	public void visit(HeadingNode headingNode) {
		addHeadingNode(_headingNode, headingNode);
	}

	protected boolean addHeadingNode(
		TreeNode<HeadingNode> treeNode, HeadingNode headingNode) {

		if (!isLastHeadingNode(treeNode, headingNode)) {
			HeadingNode treeNodeHeadingNode = treeNode.getValue();

			if (headingNode.getLevel() <= treeNodeHeadingNode.getLevel()) {
				TreeNode<HeadingNode> parentTreeNode = treeNode.getParentNode();

				parentTreeNode.addChildNode(headingNode);
			}
			else {
				treeNode.addChildNode(headingNode);
			}

			return false;
		}

		List<TreeNode<HeadingNode>> treeNodes = treeNode.getChildNodes();

		for (int i = treeNodes.size() - 1; i >= 0; --i) {
			return addHeadingNode(treeNodes.get(i), headingNode);
		}

		return true;
	}

	protected boolean isLastHeadingNode(
		TreeNode<HeadingNode> treeNode, HeadingNode headingNode) {

		HeadingNode treeNodeHeadingNode = treeNode.getValue();

		List<TreeNode<HeadingNode>> treeNodes = treeNode.getChildNodes();

		if ((headingNode.getLevel() > treeNodeHeadingNode.getLevel()) &&
			ListUtil.isNotEmpty(treeNodes)) {

			return true;
		}

		return false;
	}

	private TreeNode<HeadingNode> _headingNode;

}