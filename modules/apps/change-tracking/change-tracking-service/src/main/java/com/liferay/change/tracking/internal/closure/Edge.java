/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.closure;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class Edge {

	public Edge(Node fromNode, Node toNode) {
		_fromNode = fromNode;
		_toNode = toNode;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Edge)) {
			return false;
		}

		Edge edge = (Edge)object;

		if (Objects.equals(_fromNode, edge._fromNode) &&
			Objects.equals(_toNode, edge._toNode)) {

			return true;
		}

		return false;
	}

	public Node getFromNode() {
		return _fromNode;
	}

	public Node getToNode() {
		return _toNode;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _fromNode);

		return HashUtil.hash(hash, _toNode);
	}

	@Override
	public String toString() {
		return StringBundler.concat(_fromNode, " -> ", _toNode);
	}

	private final Node _fromNode;
	private final Node _toNode;

}