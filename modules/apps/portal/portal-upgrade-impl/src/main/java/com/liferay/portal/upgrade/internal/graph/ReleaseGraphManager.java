/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.internal.graph;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.upgrade.internal.registry.UpgradeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
public class ReleaseGraphManager {

	public ReleaseGraphManager(List<UpgradeInfo> upgradeInfos) {
		if (upgradeInfos == null) {
			upgradeInfos = new ArrayList<>();
		}

		_directedGraph = new DefaultDirectedGraph<>(
			new UpgradeProcessEdgeFactory(upgradeInfos));

		for (UpgradeInfo upgradeInfo : upgradeInfos) {
			_directedGraph.addVertex(upgradeInfo.getFromSchemaVersionString());
			_directedGraph.addVertex(upgradeInfo.getToSchemaVersionString());

			_directedGraph.addEdge(
				upgradeInfo.getFromSchemaVersionString(),
				upgradeInfo.getToSchemaVersionString(),
				new UpgradeProcessEdge(upgradeInfo));
		}
	}

	public List<UpgradeInfo> getUpgradeInfos(
		String fromVersionString, String toVersionString) {

		if (!_directedGraph.containsVertex(fromVersionString) ||
			!_directedGraph.containsVertex(toVersionString)) {

			return Collections.emptyList();
		}

		DijkstraShortestPath<String, UpgradeProcessEdge> dijkstraShortestPath =
			new DijkstraShortestPath<>(
				_directedGraph, fromVersionString, toVersionString);

		List<UpgradeProcessEdge> upgradeProcessEdges =
			dijkstraShortestPath.getPathEdgeList();

		if (upgradeProcessEdges == null) {
			return Collections.emptyList();
		}

		return ListUtil.toList(
			upgradeProcessEdges,
			new Function<UpgradeProcessEdge, UpgradeInfo>() {

				@Override
				public UpgradeInfo apply(
					UpgradeProcessEdge upgradeProcessEdge) {

					return upgradeProcessEdge.getUpgradeInfo();
				}

			});
	}

	public List<List<UpgradeInfo>> getUpgradeInfosList(
		String fromVersionString) {

		List<String> endVertices = getEndVertices();

		endVertices.remove(fromVersionString);

		List<List<UpgradeInfo>> upgradeInfosList = new ArrayList<>();

		for (String endVertex : endVertices) {
			List<UpgradeInfo> upgradeInfos = getUpgradeInfos(
				fromVersionString, endVertex);

			if (upgradeInfos.isEmpty()) {
				continue;
			}

			upgradeInfosList.add(upgradeInfos);
		}

		return upgradeInfosList;
	}

	protected List<String> getEndVertices() {
		List<String> endVertices = new ArrayList<>();

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

}