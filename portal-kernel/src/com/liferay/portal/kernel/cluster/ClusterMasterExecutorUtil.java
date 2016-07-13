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
import com.liferay.portal.kernel.util.ProxyFactory;

import java.util.concurrent.Future;

/**
 * @author Michael C. Han
 */
@ProviderType
public class ClusterMasterExecutorUtil {

	public static void addClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		getClusterMasterExecutor().addClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);
	}

	public static <T> Future<T> executeOnMaster(MethodHandler methodHandler) {
		return getClusterMasterExecutor().executeOnMaster(methodHandler);
	}

	public static ClusterMasterExecutor getClusterMasterExecutor() {
		return _clusterMasterExecutor;
	}

	public static boolean isEnabled() {
		return getClusterMasterExecutor().isEnabled();
	}

	public static boolean isMaster() {
		return getClusterMasterExecutor().isMaster();
	}

	public static void removeClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		getClusterMasterExecutor().removeClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);
	}

	private static volatile ClusterMasterExecutor _clusterMasterExecutor =
		ProxyFactory.newServiceTrackedInstance(
			ClusterMasterExecutor.class, ClusterMasterExecutorUtil.class,
			"_clusterMasterExecutor");

}