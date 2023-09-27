/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class ListTree<T extends Comparable<T>> {

	public ListTree() {
		this(null);
	}

	public ListTree(T value) {
		_rootNode = new TreeNode<>(value);
	}

	public List<TreeNode<T>> getChildNodes(TreeNode<T> node) {
		List<TreeNode<T>> nodes = new ArrayList<>();

		getChildNodes(node, nodes);

		return nodes;
	}

	public TreeNode<T> getRootNode() {
		return _rootNode;
	}

	protected void getChildNodes(TreeNode<T> node, List<TreeNode<T>> nodes) {
		List<TreeNode<T>> childNodes = node.getChildNodes();

		nodes.addAll(childNodes);

		for (TreeNode<T> childNode : childNodes) {
			getChildNodes(childNode, nodes);
		}
	}

	private final TreeNode<T> _rootNode;

}