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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Collections;
import java.util.List;

/**
 * @author Tina Tian
 * @author Raymond Augé
 */
public class ClusterExecutorUtil {

	public static void addClusterEventListener(
		ClusterEventListener clusterEventListener) {

		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return;
		}

		clusterExecutor.addClusterEventListener(clusterEventListener);
	}

	public static void destroy() {
		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return;
		}

		clusterExecutor.destroy();
	}

	public static FutureClusterResponses execute(
		ClusterRequest clusterRequest) {

		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return null;
		}

		return clusterExecutor.execute(clusterRequest);
	}

	public static FutureClusterResponses execute(
		ClusterRequest clusterRequest,
		ClusterResponseCallback clusterResponseCallback) {

		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return null;
		}

		return clusterExecutor.execute(clusterRequest, clusterResponseCallback);
	}

	public static ClusterExecutor getClusterExecutor() {
		PortalRuntimePermission.checkGetBeanProperty(ClusterExecutorUtil.class);

		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return null;
		}

		return _clusterExecutor;
	}

	public static List<Address> getClusterNodeAddresses() {
		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return Collections.emptyList();
		}

		return clusterExecutor.getClusterNodeAddresses();
	}

	public static List<ClusterNode> getClusterNodes() {
		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return Collections.emptyList();
		}

		return clusterExecutor.getClusterNodes();
	}

	public static ClusterNode getLocalClusterNode() {
		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return null;
		}

		return clusterExecutor.getLocalClusterNode();
	}

	public static Address getLocalClusterNodeAddress() {
		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return null;
		}

		return clusterExecutor.getLocalClusterNodeAddress();
	}

	public static void initialize() {
		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return;
		}

		clusterExecutor.initialize();
	}

	public static boolean isClusterNodeAlive(Address address) {
		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return false;
		}

		return clusterExecutor.isClusterNodeAlive(address);
	}

	public static boolean isClusterNodeAlive(String clusterNodeId) {
		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return false;
		}

		return clusterExecutor.isClusterNodeAlive(clusterNodeId);
	}

	public static boolean isEnabled() {
		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return false;
		}

		return true;
	}

	public static void removeClusterEventListener(
		ClusterEventListener clusterEventListener) {

		ClusterExecutor clusterExecutor = getClusterExecutor();

		if (clusterExecutor == null) {
			return;
		}

		clusterExecutor.removeClusterEventListener(clusterEventListener);
	}

	public void setClusterExecutor(ClusterExecutor clusterExecutor) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_clusterExecutor = clusterExecutor;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterExecutorUtil.class);

	private static ClusterExecutor _clusterExecutor;

}