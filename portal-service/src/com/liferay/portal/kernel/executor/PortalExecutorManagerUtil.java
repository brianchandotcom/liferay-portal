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

package com.liferay.portal.kernel.executor;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Shuyang Zhou
 */
public class PortalExecutorManagerUtil {

	public static <T> Future<T> execute(String name, Callable<T> callable) {
		PortalRuntimePermission.checkThreadPoolExecutor(name);

		return getPortalExecutorManager().execute(name, callable);
	}

	public static <T> T execute(
			String name, Callable<T> callable, long timeout, TimeUnit timeUnit)
		throws ExecutionException, InterruptedException, TimeoutException {

		PortalRuntimePermission.checkThreadPoolExecutor(name);

		return getPortalExecutorManager().execute(
			name, callable, timeout, timeUnit);
	}

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

		getPortalExecutorManager().shutdown();
	}

	public static void shutdown(boolean interrupt) {
		PortalRuntimePermission.checkThreadPoolExecutor(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_THREAD_POOL_ALL_EXECUTORS);

		getPortalExecutorManager().shutdown(interrupt);
	}

	public static void shutdown(String name) {
		PortalRuntimePermission.checkThreadPoolExecutor(name);

		getPortalExecutorManager().shutdown(name);
	}

	public static void shutdown(String name, boolean interrupt) {
		PortalRuntimePermission.checkThreadPoolExecutor(name);

		getPortalExecutorManager().shutdown(name, interrupt);
	}

	public void setPortalExecutorManager(
		PortalExecutorManager portalExecutorManager) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portalExecutorManager = portalExecutorManager;
	}

	private static PortalExecutorManager _portalExecutorManager;

}