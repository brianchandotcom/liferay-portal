/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.tree;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * @author Feliphe Marinho
 */
public class PostOrderIterator implements Iterator<Node> {

	public PostOrderIterator(Node node) {
		_fillStack(_stack.push(node));
	}

	@Override
	public boolean hasNext() {
		return !_stack.isEmpty();
	}

	@Override
	public Node next() {
		return _stack.pop();
	}

	private void _fillStack(Node node) {
		List<Node> childNodes = node.getChildNodes();

		if (childNodes == null) {
			return;
		}

		for (int i = childNodes.size() - 1; i >= 0; i--) {
			_fillStack(_stack.push(childNodes.get(i)));
		}
	}

	private final Stack<Node> _stack = new Stack<>();

}