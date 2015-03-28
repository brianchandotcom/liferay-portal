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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.concurrent.Future;

/**
 * @author Michael C. Han
 */
@ProviderType
public class ClusterMasterExecutorUtil {

	public static void addClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		ClusterMasterExecutor clusterMasterExecutor =
			getClusterMasterExecutor();

		if (clusterMasterExecutor == null) {
			return;
		}

		clusterMasterExecutor.addClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);
	}

	public static <T> Future<T> executeOnMaster(MethodHandler methodHandler) {
		ClusterMasterExecutor clusterMasterExecutor =
			getClusterMasterExecutor();

		if (clusterMasterExecutor == null) {
			return null;
		}

		return clusterMasterExecutor.executeOnMaster(methodHandler);
	}

	public static ClusterMasterExecutor getClusterMasterExecutor() {
		return _instance._serviceTracker.getService();
	}

	public static boolean isEnabled() {
		ClusterMasterExecutor clusterMasterExecutor =
			getClusterMasterExecutor();

		if (clusterMasterExecutor == null) {
			return false;
		}

		return clusterMasterExecutor.isEnabled();
	}

	public static boolean isMaster() {
		ClusterMasterExecutor clusterMasterExecutor =
			getClusterMasterExecutor();

		if (clusterMasterExecutor == null) {
			return false;
		}

		return clusterMasterExecutor.isMaster();
	}

	public static void removeClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		ClusterMasterExecutor clusterMasterExecutor =
			getClusterMasterExecutor();

		if (clusterMasterExecutor == null) {
			return;
		}

		clusterMasterExecutor.removeClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);
	}

	private ClusterMasterExecutorUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(ClusterMasterExecutor.class);

		_serviceTracker.open();
	}

	private static final ClusterMasterExecutorUtil _instance =
		new ClusterMasterExecutorUtil();

	private final ServiceTracker<ClusterMasterExecutor, ClusterMasterExecutor>
		_serviceTracker;

}