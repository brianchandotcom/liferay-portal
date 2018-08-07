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
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class PortalExecutorManagerUtil {

	public static ThreadPoolExecutor getPortalExecutor(String name) {
		return _portalExecutorManager.getPortalExecutor(name);
	}

	public static ThreadPoolExecutor getPortalExecutor(
		String name, boolean createIfAbsent) {

		return _portalExecutorManager.getPortalExecutor(name, createIfAbsent);
	}

	public static PortalExecutorManager getPortalExecutorManager() {
		return _portalExecutorManager;
	}

	public static ThreadPoolExecutor registerPortalExecutor(
		String name, ThreadPoolExecutor threadPoolExecutor) {

		return _portalExecutorManager.registerPortalExecutor(
			name, threadPoolExecutor);
	}

	public static void shutdown() {
		_portalExecutorManager.shutdown();
	}

	public static void shutdown(boolean interrupt) {
		_portalExecutorManager.shutdown(interrupt);
	}

	private PortalExecutorManagerUtil() {
	}

	private static volatile PortalExecutorManager _portalExecutorManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			PortalExecutorManager.class, PortalExecutorManagerUtil.class,
			"_portalExecutorManager", true);

}