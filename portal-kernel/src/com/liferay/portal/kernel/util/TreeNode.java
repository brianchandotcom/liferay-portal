/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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