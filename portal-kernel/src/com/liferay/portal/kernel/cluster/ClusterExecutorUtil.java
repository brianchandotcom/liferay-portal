/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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