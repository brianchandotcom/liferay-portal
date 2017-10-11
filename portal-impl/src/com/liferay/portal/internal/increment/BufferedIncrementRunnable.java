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

package com.liferay.portal.internal.increment;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.increment.Increment;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.io.Serializable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 */
public class BufferedIncrementRunnable implements Runnable {

	public BufferedIncrementRunnable(
		BufferedIncrementConfiguration bufferedIncrementConfiguration,
		BatchablePipe<Serializable, Increment<?>> batchablePipe,
		AtomicInteger queueLengthTracker, Thread dispatchThread) {

		_bufferedIncrementConfiguration = bufferedIncrementConfiguration;
		_batchablePipe = batchablePipe;
		_queueLengthTracker = queueLengthTracker;
		_dispatchThread = dispatchThread;

		if (_bufferedIncrementConfiguration.isStandbyEnabled()) {
			_queueLengthTracker.incrementAndGet();
		}

		_companyId = CompanyThreadLocal.getCompanyId();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void run() {
		CompanyThreadLocal.setCompanyId(_companyId);

		while (true) {
			BufferedIncreasableEntry bufferedIncreasableEntry =
				(BufferedIncreasableEntry)_batchablePipe.take();

			if (bufferedIncreasableEntry == null) {
				break;
			}

			try {
				bufferedIncreasableEntry.proceed();
			}
			catch (Throwable t) {
				_log.error(
					"Unable to persist buffered increment value: " +
						bufferedIncreasableEntry,
					t);
			}

			if (_bufferedIncrementConfiguration.isStandbyEnabled()) {
				int queueLength = _queueLengthTracker.decrementAndGet();

				long standbyTime =
					_bufferedIncrementConfiguration.calculateStandbyTime(
						queueLength);

				try {
					Thread.sleep(standbyTime);
				}
				catch (InterruptedException ie) {
					break;
				}
			}
		}

		if (_dispatchThread != Thread.currentThread()) {
			ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);

			CentralizedThreadLocal.clearShortLivedThreadLocals();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BufferedIncrementRunnable.class);

	private final BatchablePipe<Serializable, Increment<?>> _batchablePipe;
	private final BufferedIncrementConfiguration
		_bufferedIncrementConfiguration;
	private final long _companyId;
	private final Thread _dispatchThread;
	private final AtomicInteger _queueLengthTracker;

}