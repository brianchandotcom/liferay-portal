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