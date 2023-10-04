/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.tree;

import java.util.List;

/**
 * @author Feliphe Marinho
 */
public class Node {

	public Node(Edge edge, long primaryKey, Node parentNode) {
		_edge = edge;
		_primaryKey = primaryKey;
		_parentNode = parentNode;

		_depth = (parentNode == null) ? 0 : parentNode._depth + 1;
	}

	public List<Node> getChildNodes() {
		return _childNodes;
	}

	public int getDepth() {
		return _depth;
	}

	public Edge getEdge() {
		return _edge;
	}

	public Node getParentNode() {
		return _parentNode;
	}

	public long getPrimaryKey() {
		return _primaryKey;
	}

	public boolean isRoot() {
		if (_parentNode == null) {
			return true;
		}

		return false;
	}

	public void setChildNodes(List<Node> childNodes) {
		_childNodes = childNodes;
	}

	private List<Node> _childNodes;
	private final int _depth;
	private final Edge _edge;
	private final Node _parentNode;
	private final long _primaryKey;

}