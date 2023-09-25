/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.tree;

import com.liferay.object.definition.tree.constants.TreeConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
		return iterator(TreeConstants.ITERATOR_TYPE_BREADTH_FIRST);
	}

	public Iterator<Node> iterator(long objectDefinitionId) {
		return new BreadthFirstIterator(getNode(objectDefinitionId));
	}

	public Iterator<Node> iterator(String iteratorType) {
		if (Objects.equals(
				iteratorType, TreeConstants.ITERATOR_TYPE_BREADTH_FIRST)) {

			return new BreadthFirstIterator(rootNode);
		}
		else if (Objects.equals(
					iteratorType, TreeConstants.ITERATOR_TYPE_POST_ORDER)) {

			return new PostOrderIterator(rootNode);
		}

		return null;
	}

	protected final Node rootNode;

}