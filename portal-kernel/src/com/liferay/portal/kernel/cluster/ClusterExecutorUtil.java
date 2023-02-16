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

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;

/**
 * @author Tina Tian
 * @author Raymond Augé
 */
public class ClusterExecutorUtil {

	public static FutureClusterResponses execute(
		ClusterRequest clusterRequest) {

		return _clusterExecutor.execute(clusterRequest);
	}

	public static List<ClusterNode> getClusterNodes() {
		return _clusterExecutor.getClusterNodes();
	}

	public static ClusterNode getLocalClusterNode() {
		return _clusterExecutor.getLocalClusterNode();
	}

	public static boolean isClusterNodeAlive(String clusterNodeId) {
		return _clusterExecutor.isClusterNodeAlive(clusterNodeId);
	}

	public static boolean isEnabled() {
		return _clusterExecutor.isEnabled();
	}

	private static volatile ClusterExecutor _clusterExecutor =
		ServiceProxyFactory.newServiceTrackedInstance(
			ClusterExecutor.class, ClusterExecutorUtil.class,
			"_clusterExecutor", false);

}