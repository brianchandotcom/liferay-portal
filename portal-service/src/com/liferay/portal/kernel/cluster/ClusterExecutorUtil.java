/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Tina Tian
 */
public class ClusterExecutorUtil {

	public static void addClusterEventListener(
		ClusterEventListener clusterEventListener) {

		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return;
		}

		_clusterExecutor.addClusterEventListener(clusterEventListener);
	}

	public static void destroy() {
		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return;
		}

		_clusterExecutor.destroy();
	}

	public static FutureClusterResponses execute(ClusterRequest clusterRequest)
		throws SystemException {

		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return null;
		}

		return _clusterExecutor.execute(clusterRequest);
	}

	public static void execute(
			ClusterRequest clusterRequest,
			ClusterResponseCallback clusterResponseCallback)
		throws SystemException {

		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return;
		}

		_clusterExecutor.execute(clusterRequest, clusterResponseCallback);
	}

	public static void execute(
			ClusterRequest clusterRequest,
			ClusterResponseCallback clusterResponseCallback, long timeout,
			TimeUnit timeUnit)
		throws SystemException {

		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return;
		}

		_clusterExecutor.execute(
			clusterRequest, clusterResponseCallback, timeout, timeUnit);
	}

	public static List<Address> getClusterNodeAddresses() {
		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return Collections.emptyList();
		}

		return _clusterExecutor.getClusterNodeAddresses();
	}

	public static List<ClusterNode> getClusterNodes() {
		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return Collections.emptyList();
		}

		return _clusterExecutor.getClusterNodes();
	}

	public static ClusterNode getLocalClusterNode() throws SystemException {
		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return null;
		}

		return _clusterExecutor.getLocalClusterNode();
	}

	public static Address getLocalClusterNodeAddress() {
		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return null;
		}

		return _clusterExecutor.getLocalClusterNodeAddress();
	}

	public static void initialize() {
		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return;
		}

		_clusterExecutor.initialize();
	}

	public static boolean isClusterNodeAlive(Address address) {
		if ((_clusterExecutor == null) || !_clusterExecutor .isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return false;
		}

		return _clusterExecutor.isClusterNodeAlive(address);
	}

	public static boolean isClusterNodeAlive(String clusterNodeId) {
		if ((_clusterExecutor == null) || !_clusterExecutor .isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return false;
		}

		return _clusterExecutor.isClusterNodeAlive(clusterNodeId);
	}

	public static boolean isEnabled() {
		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return false;
		}

		return true;
	}

	public static void removeClusterEventListener(
		ClusterEventListener clusterEventListener) {

		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return;
		}

		_clusterExecutor.removeClusterEventListener(clusterEventListener);
	}

	public void setClusterExecutor(ClusterExecutor clusterExecutor) {
		_clusterExecutor = clusterExecutor;
	}

	private static Log _log = LogFactoryUtil.getLog(ClusterExecutorUtil.class);

	private static ClusterExecutor _clusterExecutor;

}