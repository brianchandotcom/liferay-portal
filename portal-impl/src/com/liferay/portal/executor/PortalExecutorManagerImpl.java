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
public class PortalExecutorManagerImpl implements PortalExecutorManager {

	public void afterPropertiesSet() {
		if (_portalExecutorFactory == null) {
			throw new IllegalArgumentException(
				"portal executor factory is null");
		}
	}

	public <T> Future<T> execute(String name, Callable<T> callable) {
		ThreadPoolExecutor portalExecutor = getPortalExecutor(name);

		return portalExecutor.submit(callable);
	}

	public <T> T execute(
			String name, Callable<T> callable, long timeout, TimeUnit timeUnit)
		throws ExecutionException, InterruptedException, TimeoutException {

		ThreadPoolExecutor portalExecutor = getPortalExecutor(name);

		Future<T> future = portalExecutor.submit(callable);

		return future.get(timeout, timeUnit);
	}

	public ThreadPoolExecutor getPortalExecutor(String name) {
		return getPortalExecutor(name, true);
	}

	public ThreadPoolExecutor getPortalExecutor(
		String name, boolean createIfAbsent) {

		ThreadPoolExecutor portalExecutor = _portalExecutors.get(name);

		if ((portalExecutor == null) && createIfAbsent) {
			synchronized (_portalExecutors) {
				portalExecutor = _portalExecutors.get(name);

				if (portalExecutor == null) {
					portalExecutor =
						_portalExecutorFactory.createPortalExecutor(name);

					_portalExecutors.put(name, portalExecutor);
				}
			}
		}

		return portalExecutor;
	}

	public ThreadPoolExecutor registerPortalExecutor(
		String name, ThreadPoolExecutor threadPoolExecutor) {

		ThreadPoolExecutor portalExecutor = _portalExecutors.get(name);

		if (portalExecutor == null) {
			synchronized (_portalExecutors) {
				portalExecutor = _portalExecutors.get(name);

				if (portalExecutor == null) {
					portalExecutor = _portalExecutors.put(
						name, threadPoolExecutor);
				}
			}
		}

		return portalExecutor;
	}

	public void setPortalExecutorFactory(
		PortalExecutorFactory portalExecutorFactory) {

		_portalExecutorFactory = portalExecutorFactory;
	}

	public void setPortalExecutors(
		Map<String, ThreadPoolExecutor> portalExecutors) {

		if (portalExecutors != null) {
			_portalExecutors =
				new ConcurrentHashMap<String, ThreadPoolExecutor>(
					portalExecutors);
		}
	}

	public void shutdown() {
		shutdown(false);
	}

	public void shutdown(boolean interrupt) {
		for (ThreadPoolExecutor portalExecutor : _portalExecutors.values()) {
			if (interrupt) {
				portalExecutor.shutdownNow();
			}
			else {
				portalExecutor.shutdown();
			}
		}

		_portalExecutors.clear();
	}

	public void shutdown(String name) {
		shutdown(name, false);
	}

	public void shutdown(String name, boolean interrupt) {
		ThreadPoolExecutor portalExecutor = _portalExecutors.remove(name);

		if (portalExecutor == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No portal executor found for name " + name);
			}

			return;
		}

		if (interrupt) {
			portalExecutor.shutdownNow();
		}
		else {
			portalExecutor.shutdown();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalExecutorManagerImpl.class);

	private PortalExecutorFactory _portalExecutorFactory;
	private Map<String, ThreadPoolExecutor> _portalExecutors =
		new ConcurrentHashMap<String, ThreadPoolExecutor>();

}