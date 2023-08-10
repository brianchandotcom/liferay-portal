/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.tree;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Feliphe Marinho
 */
public class Tree {

	public static final int MAX_HEIGHT = 4;

	public Tree(Node rootNode) {
		this.rootNode = rootNode;
	}

	public List<Edge> getAncestorEdges(long objectDefinitionId) {
		Node node = getNode(objectDefinitionId);

		if (node.isRoot()) {
			return Collections.emptyList();
		}

		List<Edge> edges = new ArrayList<>();

		Node parentNode = node.getParentNode();

		edges.add(node.getEdge());

		while (!parentNode.isRoot()) {
			edges.add(parentNode.getEdge());

			parentNode = parentNode.getParentNode();
		}

		return edges;
	}

	public Node getNode(long objectDefinitionId) {
		Iterator<Node> iterator = iterator();

		Node node = null;

		while (iterator.hasNext()) {
			node = iterator.next();

			if (node.getObjectDefinitionId() == objectDefinitionId) {
				break;
			}
		}

		return node;
	}

	public Iterator<Node> iterator() {
		return new BreadthFirstIterator(rootNode);
	}

	public Iterator<Node> iterator(long objectDefinitionId) {
		return new BreadthFirstIterator(getNode(objectDefinitionId));
	}

	protected final Node rootNode;

	private static class BreadthFirstIterator implements Iterator<Node> {

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

		private Queue<Node> _queue = new LinkedList<>();

	}

}