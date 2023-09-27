/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
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
		this(clusterEventType, Collections.<ClusterNode>emptyList());
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

	private final ClusterEventType _clusterEventType;
	private final List<ClusterNode> _clusterNodes;

}