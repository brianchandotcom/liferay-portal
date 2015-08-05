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

	public List<UpgradeInfo> getUpgradeInfos(String fromVersionString) {
		List<String> endVertices = getEndVertices();

		endVertices.remove(fromVersionString);

		if (endVertices.size() == 1) {
			return getUpgradeInfos(fromVersionString, endVertices.get(0));
		}

		if (endVertices.size() > 1) {
			throw new IllegalStateException(
				"There is more than one possible end node " + endVertices);
		}

		throw new IllegalStateException("There are no end nodes");
	}

	public List<UpgradeInfo> getUpgradeInfos(
		String fromVersionString, String toVersionString) {

		if (!_directedGraph.containsVertex(fromVersionString)) {
			throw new IllegalArgumentException(
				"There is no path for " + fromVersionString);
		}

		if (!_directedGraph.containsVertex(toVersionString)) {
			throw new IllegalArgumentException(
				"There is no path for " + toVersionString);
		}

		DijkstraShortestPath<String, UpgradeProcessEdge> dijkstraShortestPath =
			new DijkstraShortestPath<>(
				_directedGraph, fromVersionString, toVersionString);

		List<UpgradeProcessEdge> upgradeProcessEdges =
			dijkstraShortestPath.getPathEdgeList();

		if (upgradeProcessEdges == null) {
			throw new IllegalArgumentException(
				"There is no path between " + fromVersionString + " and " +
					toVersionString);
		}

		return ListUtil.toList(
			upgradeProcessEdges,
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

			});
	}

	protected List<String> getEndVertices() {
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
				String fromVersionString = upgradeInfo.getFromVersionString();
				String toVersionString = upgradeInfo.getToVersionString();

				if (fromVersionString.equals(sourceVertex) &&
					toVersionString.equals(targetVertex)) {

					return new UpgradeProcessEdge(upgradeInfo);
				}
			}

			return null;
		}

		private final List<UpgradeInfo> _upgradeInfos;

	}

}