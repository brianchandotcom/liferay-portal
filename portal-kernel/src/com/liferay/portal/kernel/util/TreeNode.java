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
 * @author Dennis Ju
 * @author Brian Wing Shun Chan
 */
public class TreeNode<T extends Comparable<T>> {

	public TreeNode(T value) {
		this(value, null);
	}

	public TreeNode(T value, TreeNode<T> parentNode) {
		this(value, parentNode, new ArrayList<TreeNode<T>>());
	}

	public TreeNode(
		T value, TreeNode<T> parentNode, List<TreeNode<T>> childNodes) {

		_value = value;
		_parentNode = parentNode;
		_childNodes = childNodes;
	}

	public TreeNode<T> addChildNode(T value) {
		TreeNode<T> childNode = new TreeNode<>(value, this);

		_childNodes.add(childNode);

		return childNode;
	}

	public List<TreeNode<T>> getChildNodes() {
		return _childNodes;
	}

	public List<T> getChildValues() {
		List<T> values = new ArrayList<>(_childNodes.size());

		for (TreeNode<T> childNode : _childNodes) {
			values.add(childNode.getValue());
		}

		return values;
	}

	public TreeNode<T> getParentNode() {
		return _parentNode;
	}

	public T getValue() {
		return _value;
	}

	public boolean isRootNode() {
		if (_parentNode == null) {
			return true;
		}

		return false;
	}

	private final List<TreeNode<T>> _childNodes;
	private final TreeNode<T> _parentNode;
	private final T _value;

}