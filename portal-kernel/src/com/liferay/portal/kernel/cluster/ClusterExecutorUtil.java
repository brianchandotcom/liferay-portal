/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;

/**
 * @author Tina Tian
 * @author Raymond Augé
 */
public class ClusterExecutorUtil {

	public static FutureClusterResponses execute(
		ClusterRequest clusterRequest) {

		ClusterExecutor clusterExecutor = _clusterExecutorSnapshot.get();

		return clusterExecutor.execute(clusterRequest);
	}

	public static List<ClusterNode> getClusterNodes() {
		ClusterExecutor clusterExecutor = _clusterExecutorSnapshot.get();

		return clusterExecutor.getClusterNodes();
	}

	public static ClusterNode getLocalClusterNode() {
		ClusterExecutor clusterExecutor = _clusterExecutorSnapshot.get();

		return clusterExecutor.getLocalClusterNode();
	}

	public static boolean isClusterNodeAlive(String clusterNodeId) {
		ClusterExecutor clusterExecutor = _clusterExecutorSnapshot.get();

		return clusterExecutor.isClusterNodeAlive(clusterNodeId);
	}

	public static boolean isEnabled() {
		ClusterExecutor clusterExecutor = _clusterExecutorSnapshot.get();

		return clusterExecutor.isEnabled();
	}

	private static final Snapshot<ClusterExecutor> _clusterExecutorSnapshot =
		new Snapshot<>(ClusterExecutorUtil.class, ClusterExecutor.class);

}