/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		_addHeadingNode(_headingNode, headingNode);
	}

	private boolean _addHeadingNode(
		TreeNode<HeadingNode> treeNode, HeadingNode headingNode) {

		if (!_isLastHeadingNode(treeNode, headingNode)) {
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
			return _addHeadingNode(treeNodes.get(i), headingNode);
		}

		return true;
	}

	private boolean _isLastHeadingNode(
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