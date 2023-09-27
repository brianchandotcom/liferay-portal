/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.concurrent.Future;

/**
 * @author Michael C. Han
 */
public class ClusterMasterExecutorUtil {

	public static void addClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		_clusterMasterExecutor.addClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);
	}

	public static <T> Future<T> executeOnMaster(MethodHandler methodHandler) {
		return _clusterMasterExecutor.executeOnMaster(methodHandler);
	}

	public static boolean isEnabled() {
		return _clusterMasterExecutor.isEnabled();
	}

	public static boolean isMaster() {
		return _clusterMasterExecutor.isMaster();
	}

	public static void removeClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		_clusterMasterExecutor.removeClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);
	}

	private static volatile ClusterMasterExecutor _clusterMasterExecutor =
		ServiceProxyFactory.newServiceTrackedInstance(
			ClusterMasterExecutor.class, ClusterMasterExecutorUtil.class,
			"_clusterMasterExecutor", false);

}