/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.MethodHandler;

import java.util.concurrent.Future;

/**
 * @author Michael C. Han
 */
public class ClusterMasterExecutorUtil {

	public static <T> Future<T> executeOnMaster(MethodHandler methodHandler)
		throws SystemException {

		ClusterMasterExecutor clusterMasterExecutor =
			getClusterMasterExecutor();

		if (clusterMasterExecutor == null) {
			return null;
		}

		return _clusterMasterExecutor.executeOnMaster(methodHandler);
	}

	public static ClusterMasterExecutor getClusterMasterExecutor() {
		return _clusterMasterExecutor;
	}

	public static void initialize() {
		ClusterMasterExecutor clusterMasterExecutor =
			getClusterMasterExecutor();

		if (clusterMasterExecutor == null) {
			return;
		}

		_clusterMasterExecutor.initialize();
	}

	public static boolean isEnabled() {
		ClusterMasterExecutor clusterMasterExecutor =
			getClusterMasterExecutor();

		if (clusterMasterExecutor == null) {
			return false;
		}

		return _clusterMasterExecutor.isEnabled();
	}

	public static boolean isMaster() {
		ClusterMasterExecutor clusterMasterExecutor =
			getClusterMasterExecutor();

		if (clusterMasterExecutor == null) {
			return false;
		}

		return _clusterMasterExecutor.isMaster();
	}

	public static void registerClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		ClusterMasterExecutor clusterMasterExecutor =
			getClusterMasterExecutor();

		if (clusterMasterExecutor == null) {
			return;
		}

		_clusterMasterExecutor.registerClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);
	}

	public static void unregisterClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		ClusterMasterExecutor clusterMasterExecutor =
			getClusterMasterExecutor();

		if (clusterMasterExecutor == null) {
			return;
		}

		_clusterMasterExecutor.unregisterClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);
	}

	public void setClusterMasterExecutor(
		ClusterMasterExecutor clusterMasterExecutor) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_clusterMasterExecutor = clusterMasterExecutor;
	}

	private static ClusterMasterExecutor _clusterMasterExecutor;

}