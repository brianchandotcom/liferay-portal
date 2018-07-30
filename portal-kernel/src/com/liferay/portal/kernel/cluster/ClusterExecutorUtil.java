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

	public static void addClusterEventListener(
		ClusterEventListener clusterEventListener) {

		_getClusterExecutor().addClusterEventListener(clusterEventListener);
	}

	public static FutureClusterResponses execute(
		ClusterRequest clusterRequest) {

		return _getClusterExecutor().execute(clusterRequest);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #_getClusterExecutor()}
	 */
	@Deprecated
	public static ClusterExecutor getClusterExecutor() {
		return _getClusterExecutor();
	}

	public static List<ClusterNode> getClusterNodes() {
		return _getClusterExecutor().getClusterNodes();
	}

	public static ClusterNode getLocalClusterNode() {
		return _getClusterExecutor().getLocalClusterNode();
	}

	public static boolean isClusterNodeAlive(String clusterNodeId) {
		return _getClusterExecutor().isClusterNodeAlive(clusterNodeId);
	}

	public static boolean isEnabled() {
		return _getClusterExecutor().isEnabled();
	}

	public static void removeClusterEventListener(
		ClusterEventListener clusterEventListener) {

		_getClusterExecutor().removeClusterEventListener(clusterEventListener);
	}

	private static ClusterExecutor _getClusterExecutor() {
		return _clusterExecutor;
	}

	private static volatile ClusterExecutor _clusterExecutor =
		ServiceProxyFactory.newServiceTrackedInstance(
			ClusterExecutor.class, ClusterExecutorUtil.class,
			"_clusterExecutor", false);

}