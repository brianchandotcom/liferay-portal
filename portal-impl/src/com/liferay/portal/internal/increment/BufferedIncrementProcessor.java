/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.internal.increment;

import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.increment.BufferedIncrementThreadLocal;
import com.liferay.portal.kernel.increment.Increment;
import com.liferay.portal.kernel.util.NamedThreadFactory;

import java.io.Serializable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 */
public class BufferedIncrementProcessor {

	public BufferedIncrementProcessor(
		BufferedIncrementConfiguration bufferedIncrementConfiguration,
		String configuration) {

		_bufferedIncrementConfiguration = bufferedIncrementConfiguration;

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			0, bufferedIncrementConfiguration.getThreadpoolMaxSize(),
			bufferedIncrementConfiguration.getThreadpoolKeepAliveTime(),
			TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		threadPoolExecutor.setRejectedExecutionHandler(
			new ThreadPoolExecutor.DiscardPolicy());

		Thread currentThread = Thread.currentThread();

		threadPoolExecutor.setThreadFactory(
			new NamedThreadFactory(
				"BufferedIncrement-".concat(configuration),
				Thread.NORM_PRIORITY, currentThread.getContextClassLoader()));

		_executorService = threadPoolExecutor;
	}

	public void destroy() {
		_executorService.shutdown();
	}

	@SuppressWarnings("rawtypes")
	public void process(BufferedIncreasableEntry bufferedIncreasableEntry) {
		if (_batchablePipe.put(bufferedIncreasableEntry)) {
			Runnable runnable = new BufferedIncrementRunnable(
				_bufferedIncrementConfiguration, _batchablePipe,
				_queueLengthTracker, Thread.currentThread());

			if (!CTCollectionThreadLocal.isProductionMode() ||
				BufferedIncrementThreadLocal.isForceSync()) {

				runnable.run();
			}
			else {
				_executorService.execute(runnable);
			}
		}
	}

	private final BatchablePipe<Serializable, Increment<?>> _batchablePipe =
		new BatchablePipe<>();
	private final BufferedIncrementConfiguration
		_bufferedIncrementConfiguration;
	private final ExecutorService _executorService;
	private final AtomicInteger _queueLengthTracker = new AtomicInteger();

}