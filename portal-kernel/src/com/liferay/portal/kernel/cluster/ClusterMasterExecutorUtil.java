/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.MethodHandler;

import java.util.concurrent.Future;

/**
 * @author Michael C. Han
 */
public class ClusterMasterExecutorUtil {

	public static void addClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		ClusterMasterExecutor clusterMasterExecutor =
			_clusterMasterExecutorSnapshot.get();

		clusterMasterExecutor.addClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);
	}

	public static <T> Future<T> executeOnMaster(MethodHandler methodHandler) {
		ClusterMasterExecutor clusterMasterExecutor =
			_clusterMasterExecutorSnapshot.get();

		return clusterMasterExecutor.executeOnMaster(methodHandler);
	}

	public static boolean isEnabled() {
		ClusterMasterExecutor clusterMasterExecutor =
			_clusterMasterExecutorSnapshot.get();

		return clusterMasterExecutor.isEnabled();
	}

	public static boolean isMaster() {
		ClusterMasterExecutor clusterMasterExecutor =
			_clusterMasterExecutorSnapshot.get();

		return clusterMasterExecutor.isMaster();
	}

	public static void removeClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		ClusterMasterExecutor clusterMasterExecutor =
			_clusterMasterExecutorSnapshot.get();

		clusterMasterExecutor.removeClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);
	}

	private static final Snapshot<ClusterMasterExecutor>
		_clusterMasterExecutorSnapshot = new Snapshot<>(
			ClusterMasterExecutorUtil.class, ClusterMasterExecutor.class);

}