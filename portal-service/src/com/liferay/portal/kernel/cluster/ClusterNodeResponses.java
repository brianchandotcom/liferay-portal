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

package com.liferay.portal.kernel.cluster;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Michael C. Han
 */
public class ClusterNodeResponses implements Serializable {

	public void addClusterResponse(ClusterNodeResponse clusterNodeResponse) {
		ClusterNode clusterNode = clusterNodeResponse.getClusterNode();

		_clusterResponsesByClusterNode.put(
			clusterNode.getClusterNodeId(), clusterNodeResponse);

		_clusterResponsesQueue.offer(clusterNodeResponse);
	}

	public ClusterNodeResponse getClusterResponse(ClusterNode clusterNode) {
		return getClusterResponse(clusterNode.getClusterNodeId());
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
		_clusterResponsesByClusterNode = new HashMap<>();
	private final BlockingQueue<ClusterNodeResponse> _clusterResponsesQueue =
		new LinkedBlockingQueue<>();

}