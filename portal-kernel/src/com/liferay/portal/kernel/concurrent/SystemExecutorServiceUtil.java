/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.concurrent;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.SystemProperties;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
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

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			maxPoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
			new LinkedBlockingQueue<>(),
			new NamedThreadFactory(
				SystemExecutorServiceUtil.class.getSimpleName(),
				Thread.NORM_PRIORITY, null),
			new ThreadPoolExecutor.CallerRunsPolicy());

		threadPoolExecutor.allowCoreThreadTimeOut(true);

		_executorService = threadPoolExecutor;
	}

}