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

package com.liferay.portal.upgrade.internal.graph;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.upgrade.internal.UpgradeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
public class ReleaseGraphManager {

	public ReleaseGraphManager(final List<UpgradeInfo> upgradeInfos) {
		_directedGraph = new DefaultDirectedGraph<>(
			new UpgradeProcessEdgeFactory(upgradeInfos));

		for (UpgradeInfo upgradeInfo : upgradeInfos) {
			_directedGraph.addVertex(upgradeInfo.getFromVersionString());
			_directedGraph.addVertex(upgradeInfo.getToVersionString());

			_directedGraph.addEdge(
				upgradeInfo.getFromVersionString(),
				upgradeInfo.getToVersionString(),
				new UpgradeProcessEdge(upgradeInfo));
		}
	}

	public List<UpgradeInfo> findUpgradePath(String from) {
		List<String> sinkNodes = getSinkNodes();

		sinkNodes.remove(from);

		if (sinkNodes.size() == 1) {
			return findUpgradePath(from, sinkNodes.get(0));
		}

		if (sinkNodes.size() > 1) {
			throw new IllegalStateException(
				"There are more that one possible end nodes " + sinkNodes);
		}

		throw new IllegalStateException("No end nodes!");
	}

	public List<UpgradeInfo> findUpgradePath(String from, String to) {
		if (!_directedGraph.containsVertex(from)) {
			throw new IllegalArgumentException(
				"There is no path starting in " + from);
		}

		if (!_directedGraph.containsVertex(to)) {
			throw new IllegalArgumentException(
				"There is no path starting in " + to);
		}

		DijkstraShortestPath<String, UpgradeProcessEdge> dijkstraShortestPath =
			new DijkstraShortestPath<>(_directedGraph, from, to);

		List<UpgradeProcessEdge> pathEdgeList =
			dijkstraShortestPath.getPathEdgeList();

		if (pathEdgeList == null) {
			throw new IllegalArgumentException(
				"There is no path between " + from + " and " + to);
		}

		return ListUtil.toList(
			pathEdgeList,
			new Accessor<UpgradeProcessEdge, UpgradeInfo>() {

				@Override
				public UpgradeInfo get(UpgradeProcessEdge upgradeProcessEdge) {
					return upgradeProcessEdge._upgradeInfo;
				}

				@Override
				public Class<UpgradeInfo> getAttributeClass() {
					return UpgradeInfo.class;
				}

				@Override
				public Class<UpgradeProcessEdge> getTypeClass() {
					return UpgradeProcessEdge.class;
				}
			}
		);
	}

	protected List<String> getSinkNodes() {
		final List<String> endVertices = new ArrayList<>();

		Set<String> vertices = _directedGraph.vertexSet();

		for (String vertex : vertices) {
			Set<UpgradeProcessEdge> upgradeProcessEdges =
				_directedGraph.outgoingEdgesOf(vertex);

			if (upgradeProcessEdges.isEmpty()) {
				endVertices.add(vertex);
			}
		}

		return endVertices;
	}

	private final DirectedGraph<String, UpgradeProcessEdge> _directedGraph;

	private static class UpgradeProcessEdge extends DefaultEdge {

		public UpgradeProcessEdge(UpgradeInfo upgradeInfo) {
			_upgradeInfo = upgradeInfo;
		}

		public UpgradeInfo getUpgradeInfo() {
			return _upgradeInfo;
		}

		private final UpgradeInfo _upgradeInfo;

	}

	private static class UpgradeProcessEdgeFactory
		implements EdgeFactory<String, UpgradeProcessEdge> {

		public UpgradeProcessEdgeFactory(List<UpgradeInfo> upgradeInfos) {
			_upgradeInfos = upgradeInfos;
		}

		@Override
		public UpgradeProcessEdge createEdge(
			String sourceVertex, String targetVertex) {

			for (UpgradeInfo upgradeInfo : _upgradeInfos) {
				String from = upgradeInfo.getFromVersionString();
				String to = upgradeInfo.getToVersionString();

				if (from.equals(sourceVertex) && to.equals(targetVertex)) {
					return new UpgradeProcessEdge(upgradeInfo);
				}
			}

			return null;
		}

		private final List<UpgradeInfo> _upgradeInfos;

	}

}