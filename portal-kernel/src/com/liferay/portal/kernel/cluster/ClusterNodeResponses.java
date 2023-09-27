/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Michael C. Han
 */
public class ClusterNodeResponses implements Serializable {

	public ClusterNodeResponses(Set<String> expectedReplyNodeIds) {
		_expectedReplyNodeIds = Collections.newSetFromMap(
			new ConcurrentHashMap<>());

		_expectedReplyNodeIds.addAll(expectedReplyNodeIds);
	}

	public boolean addClusterResponse(ClusterNodeResponse clusterNodeResponse) {
		ClusterNode clusterNode = clusterNodeResponse.getClusterNode();

		String clusterNodeId = clusterNode.getClusterNodeId();

		if (_expectedReplyNodeIds.remove(clusterNodeId)) {
			_clusterResponsesByClusterNode.put(
				clusterNodeId, clusterNodeResponse);

			_clusterResponsesQueue.offer(clusterNodeResponse);

			return true;
		}

		return false;
	}

	public ClusterNodeResponse getClusterResponse(String clusterNodeId) {
		return _clusterResponsesByClusterNode.get(clusterNodeId);
	}

	public BlockingQueue<ClusterNodeResponse> getClusterResponses() {
		return _clusterResponsesQueue;
	}

	public int size() {
		return _clusterResponsesByClusterNode.size();
	}

	private final Map<String, ClusterNodeResponse>
		_clusterResponsesByClusterNode = new ConcurrentHashMap<>();
	private final BlockingQueue<ClusterNodeResponse> _clusterResponsesQueue =
		new LinkedBlockingQueue<>();
	private final Set<String> _expectedReplyNodeIds;

}