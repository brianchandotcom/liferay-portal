/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.tree;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Feliphe Marinho
 */
public class BreadthFirstIterator implements Iterator<Node> {

	public BreadthFirstIterator(Node node) {
		_queue.add(node);
	}

	@Override
	public boolean hasNext() {
		return !_queue.isEmpty();
	}

	@Override
	public Node next() {
		Node node = _queue.poll();

		List<Node> nodes = node.getChildNodes();

		if (ListUtil.isNotEmpty(nodes)) {
			_queue.addAll(nodes);
		}

		return node;
	}

	private final Queue<Node> _queue = new LinkedList<>();

}