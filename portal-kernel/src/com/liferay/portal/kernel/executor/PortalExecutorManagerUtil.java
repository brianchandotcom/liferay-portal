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

package com.liferay.portal.kernel.executor;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

/**
 * @author Shuyang Zhou
 */
public class PortalExecutorManagerUtil {

	public static ThreadPoolExecutor getPortalExecutor(String name) {
		PortalRuntimePermission.checkThreadPoolExecutor(name);

		return getPortalExecutorManager().getPortalExecutor(name);
	}

	public static ThreadPoolExecutor getPortalExecutor(
		String name, boolean createIfAbsent) {

		PortalRuntimePermission.checkThreadPoolExecutor(name);

		return getPortalExecutorManager().getPortalExecutor(
			name, createIfAbsent);
	}

	public static PortalExecutorManager getPortalExecutorManager() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortalExecutorManagerUtil.class);

		try {
			while (_portalExecutorManager == null) {
				Registry registry = RegistryUtil.getRegistry();

				_portalExecutorManager = registry.getService(
					PortalExecutorManager.class);

				if (_log.isDebugEnabled()) {
					_log.debug("Waiting for a PortalExecutorManager");
				}

				Thread.sleep(500);
			}
		}
		catch (InterruptedException ie) {
		}

		return _portalExecutorManager;
	}

	public static ThreadPoolExecutor registerPortalExecutor(
		String name, ThreadPoolExecutor threadPoolExecutor) {

		PortalRuntimePermission.checkThreadPoolExecutor(name);

		return getPortalExecutorManager().registerPortalExecutor(
			name, threadPoolExecutor);
	}

	public static void shutdown() {
		PortalRuntimePermission.checkThreadPoolExecutor(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_THREAD_POOL_ALL_EXECUTORS);

		if (_portalExecutorManager == null) {
			return;
		}

		_portalExecutorManager.shutdown();
	}

	public static void shutdown(boolean interrupt) {
		PortalRuntimePermission.checkThreadPoolExecutor(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_THREAD_POOL_ALL_EXECUTORS);

		if (_portalExecutorManager == null) {
			return;
		}

		_portalExecutorManager.shutdown(interrupt);
	}

	private PortalExecutorManagerUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalExecutorManagerUtil.class);

	private static final PortalExecutorManagerUtil _instance =
		new PortalExecutorManagerUtil();

	private static volatile PortalExecutorManager _portalExecutorManager =
		ProxyFactory.newServiceTrackedInstanceWithoutDummyService(
			PortalExecutorManager.class, PortalExecutorManagerUtil.class,
			"_portalExecutorManager");

}