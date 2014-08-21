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

import java.util.Arrays;
import java.util.List;

/**
 * @author Tina Tian
 */
public class ClusterEvent implements Serializable {

	public static ClusterEvent depart(ClusterNode... clusterNodes) {
		return new ClusterEvent(
			ClusterEventType.DEPART, Arrays.asList(clusterNodes));
	}

	public static ClusterEvent depart(List<ClusterNode> clusterNodes) {
		return new ClusterEvent(ClusterEventType.DEPART, clusterNodes);
	}

	public static ClusterEvent join(ClusterNode... clusterNodes) {
		return new ClusterEvent(
			ClusterEventType.JOIN, Arrays.asList(clusterNodes));
	}

	public static ClusterEvent join(List<ClusterNode> clusterNodes) {
		return new ClusterEvent(ClusterEventType.JOIN, clusterNodes);
	}

	public ClusterEvent(ClusterEventType clusterEventType) {
		_clusterEventType = clusterEventType;
	}

	public ClusterEvent(
		ClusterEventType clusterEventType, List<ClusterNode> clusterNodes) {

		_clusterEventType = clusterEventType;
		_clusterNodes = clusterNodes;
	}

	public ClusterEventType getClusterEventType() {
		return _clusterEventType;
	}

	public List<ClusterNode> getClusterNodes() {
		return _clusterNodes;
	}

	public void setClusterNodes(List<ClusterNode> clusterNodes) {
		_clusterNodes = clusterNodes;
	}

	private final ClusterEventType _clusterEventType;
	private List<ClusterNode> _clusterNodes;

}