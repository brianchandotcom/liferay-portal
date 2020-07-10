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

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class GraphUtil {

	public static Map<Node, Collection<Node>> getNodeMap(
		Set<Node> nodes, Map<Node, Collection<Edge>> edgeMap) {

		Deque<Edge> backtraceEdges = new LinkedList<>();
		Set<Edge> cyclingEdges = new HashSet<>();
		Set<Edge> resolvedEdges = new HashSet<>();

		for (Collection<Edge> edges : edgeMap.values()) {
			for (Edge edge : edges) {
				_filterCyclingEdges(
					edge, edgeMap, backtraceEdges, cyclingEdges, resolvedEdges);
			}
		}

		Map<Node, Collection<Node>> nodeMap = new HashMap<>();

		for (Edge edge : resolvedEdges) {
			Collection<Node> children = nodeMap.computeIfAbsent(
				edge.getFromNode(), node -> new HashSet<>());

			Node toNode = edge.getToNode();

			children.add(toNode);

			nodes.remove(toNode);
		}

		nodeMap.put(Node.ROOT_NODE, nodes);

		return nodeMap;
	}

	private static void _filterCyclingEdges(
		Edge edge, Map<Node, Collection<Edge>> edgeMap,
		Deque<Edge> backtraceEdges, Set<Edge> cyclingEdges,
		Set<Edge> resolvedEdges) {

		if (backtraceEdges.contains(edge)) {
			cyclingEdges.add(edge);

			return;
		}

		if (resolvedEdges.contains(edge) || cyclingEdges.contains(edge)) {
			return;
		}

		Collection<Edge> nextEdges = edgeMap.get(edge.getToNode());

		if (nextEdges == null) {
			resolvedEdges.add(edge);

			return;
		}

		backtraceEdges.push(edge);

		for (Edge nextEdge : nextEdges) {
			_filterCyclingEdges(
				nextEdge, edgeMap, backtraceEdges, cyclingEdges, resolvedEdges);
		}

		backtraceEdges.pop();

		if (!cyclingEdges.contains(edge)) {
			resolvedEdges.add(edge);
		}
	}

}