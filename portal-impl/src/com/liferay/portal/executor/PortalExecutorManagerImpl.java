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

package com.liferay.portal.executor;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorFactory;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.security.Permission;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Shuyang Zhou
 */
@DoPrivileged
public class PortalExecutorManagerImpl implements PortalExecutorManager {

	public void afterPropertiesSet() {
		if (_portalExecutorFactory == null) {
			throw new IllegalArgumentException(
				"Portal executor factory is null");
		}
	}

	public <T> Future<T> execute(String name, Callable<T> callable) {
		ThreadPoolExecutor threadPoolExecutor = getPortalExecutor(name);

		return threadPoolExecutor.submit(callable);
	}

	public <T> T execute(
			String name, Callable<T> callable, long timeout, TimeUnit timeUnit)
		throws ExecutionException, InterruptedException, TimeoutException {

		ThreadPoolExecutor threadPoolExecutor = getPortalExecutor(name);

		Future<T> future = threadPoolExecutor.submit(callable);

		return future.get(timeout, timeUnit);
	}

	public ThreadPoolExecutor getPortalExecutor(String name) {
		return getPortalExecutor(name, true);
	}

	public ThreadPoolExecutor getPortalExecutor(
		String name, boolean createIfAbsent) {

		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager != null) {
			Permission permission = new PortalRuntimePermission(
				PACLConstants.PORTAL_RUNTIME_PERMISSION_THREAD_POOL_EXECUTOR,
				name);

			securityManager.checkPermission(permission);
		}

		ThreadPoolExecutor threadPoolExecutor = _threadPoolExecutors.get(name);

		if ((threadPoolExecutor == null) && createIfAbsent) {
			synchronized (_threadPoolExecutors) {
				threadPoolExecutor = _threadPoolExecutors.get(name);

				if (threadPoolExecutor == null) {
					threadPoolExecutor =
						_portalExecutorFactory.createPortalExecutor(name);

					_threadPoolExecutors.put(name, threadPoolExecutor);
				}
			}
		}

		return threadPoolExecutor;
	}

	public ThreadPoolExecutor registerPortalExecutor(
		String name, ThreadPoolExecutor threadPoolExecutor) {

		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager != null) {
			Permission permission = new PortalRuntimePermission(
				PACLConstants.PORTAL_RUNTIME_PERMISSION_THREAD_POOL_EXECUTOR,
				name);

			securityManager.checkPermission(permission);
		}

		ThreadPoolExecutor oldThreadPoolExecutor = _threadPoolExecutors.get(
			name);

		if (oldThreadPoolExecutor == null) {
			synchronized (_threadPoolExecutors) {
				oldThreadPoolExecutor = _threadPoolExecutors.get(name);

				if (oldThreadPoolExecutor == null) {
					oldThreadPoolExecutor = _threadPoolExecutors.put(
						name, threadPoolExecutor);
				}
			}
		}

		return oldThreadPoolExecutor;
	}

	public void setPortalExecutorFactory(
		PortalExecutorFactory portalExecutorFactory) {

		_portalExecutorFactory = portalExecutorFactory;
	}

	public void setPortalExecutors(
		Map<String, ThreadPoolExecutor> threadPoolExecutors) {

		if (threadPoolExecutors != null) {
			_threadPoolExecutors =
				new ConcurrentHashMap<String, ThreadPoolExecutor>(
					threadPoolExecutors);
		}
	}

	public void shutdown() {
		shutdown(false);
	}

	public void shutdown(boolean interrupt) {
		for (Map.Entry<String, ThreadPoolExecutor> entry :
				_threadPoolExecutors.entrySet()) {

			try {
				SecurityManager securityManager = System.getSecurityManager();

				if (securityManager != null) {
					String name = entry.getKey();

					Permission permission = new PortalRuntimePermission(
						PACLConstants.
							PORTAL_RUNTIME_PERMISSION_THREAD_POOL_EXECUTOR,
						name);

					securityManager.checkPermission(permission);
				}
			}
			catch (SecurityException se) {
				_log.error(se, se);

				continue;
			}

			ThreadPoolExecutor threadPoolExecutor = entry.getValue();

			if (interrupt) {
				threadPoolExecutor.shutdownNow();
			}
			else {
				threadPoolExecutor.shutdown();
			}
		}

		_threadPoolExecutors.clear();
	}

	public void shutdown(String name) {
		shutdown(name, false);
	}

	public void shutdown(String name, boolean interrupt) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager != null) {
			Permission permission = new PortalRuntimePermission(
				PACLConstants.PORTAL_RUNTIME_PERMISSION_THREAD_POOL_EXECUTOR,
				name);

			securityManager.checkPermission(permission);
		}

		ThreadPoolExecutor threadPoolExecutor = _threadPoolExecutors.remove(
			name);

		if (threadPoolExecutor == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No portal executor found for name " + name);
			}

			return;
		}

		if (interrupt) {
			threadPoolExecutor.shutdownNow();
		}
		else {
			threadPoolExecutor.shutdown();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalExecutorManagerImpl.class);

	private PortalExecutorFactory _portalExecutorFactory;
	private Map<String, ThreadPoolExecutor> _threadPoolExecutors =
		new ConcurrentHashMap<String, ThreadPoolExecutor>();

}