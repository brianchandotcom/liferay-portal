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

package com.liferay.portal.kernel.concurrent;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.SystemProperties;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class SystemExecutorServiceUtil {

	public static ExecutorService getExecutorService() {
		return _executorService;
	}

	public static <T> Callable<T> renameThread(
		Callable<T> callable, String taskName) {

		return () -> {
			Thread currentThread = Thread.currentThread();

			String name = currentThread.getName();

			currentThread.setName(
				StringBundler.concat(name, StringPool.MINUS, taskName));

			try {
				return callable.call();
			}
			finally {
				currentThread.setName(name);
			}
		};
	}

	public static Runnable renameThread(Runnable runnable, String taskName) {
		return () -> {
			Thread currentThread = Thread.currentThread();

			String name = currentThread.getName();

			currentThread.setName(
				StringBundler.concat(name, StringPool.MINUS, taskName));

			try {
				runnable.run();
			}
			finally {
				currentThread.setName(name);
			}
		};
	}

	public static void shutdown() throws InterruptedException {
		_executorService.shutdownNow();

		long shutdownTimeout = GetterUtil.getLong(
			SystemProperties.get("system.executor.service.shutdown.timeout"),
			60);

		_executorService.awaitTermination(shutdownTimeout, TimeUnit.SECONDS);
	}

	private static final ExecutorService _executorService;

	static {
		Runtime runtime = Runtime.getRuntime();

		int maxPoolSize = GetterUtil.getInteger(
			SystemProperties.get("system.executor.service.maxpoolsize"),
			runtime.availableProcessors());

		long keepAliveTime = GetterUtil.getLong(
			SystemProperties.get("system.executor.service.keepalivetime"), 60);

		_executorService = new ThreadPoolExecutor(
			0, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
			new SynchronousQueue<>(),
			new NamedThreadFactory(
				SystemExecutorServiceUtil.class.getSimpleName(),
				Thread.NORM_PRIORITY, null),
			new ThreadPoolExecutor.CallerRunsPolicy());
	}

}