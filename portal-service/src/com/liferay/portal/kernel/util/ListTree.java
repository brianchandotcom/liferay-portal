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