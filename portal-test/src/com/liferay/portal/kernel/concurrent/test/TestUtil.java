/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.concurrent.test;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class TestUtil {

	public static final long KEEPALIVE_TIME = 50;

	public static final long KEEPALIVE_WAIT = KEEPALIVE_TIME * 2;

	public static final long LONG_WAIT = 30 * 1000;

	public static final long SHORT_WAIT = 10;

	public static void closePool(ThreadPoolExecutor threadPoolExecutor) {
		closePool(threadPoolExecutor, false);
	}

	public static void closePool(
		ThreadPoolExecutor threadPoolExecutor, boolean force) {

		try {
			if (force) {
				threadPoolExecutor.shutdownNow();
			}
			else {
				threadPoolExecutor.shutdown();
			}

			if (!threadPoolExecutor.awaitTermination(
					LONG_WAIT, TimeUnit.MILLISECONDS) ||
				!threadPoolExecutor.isTerminated()) {

				throw new IllegalStateException();
			}
		}
		catch (InterruptedException interruptedException) {
			if (_log.isDebugEnabled()) {
				_log.debug(interruptedException);
			}

			throw new RuntimeException();
		}
	}

	public static void unblock(MarkerBlockingJob... markerBlockingJobs) {
		for (MarkerBlockingJob markerBlockingJob : markerBlockingJobs) {
			markerBlockingJob.unBlock();
		}
	}

	public static void waitUntilBlock(MarkerBlockingJob... markerBlockingJobs)
		throws InterruptedException {

		for (MarkerBlockingJob markerBlockingJob : markerBlockingJobs) {
			markerBlockingJob.waitUntilBlock();
		}
	}

	public static void waitUntilEnded(MarkerBlockingJob... markerBlockingJobs)
		throws InterruptedException {

		for (MarkerBlockingJob markerBlockingJob : markerBlockingJobs) {
			markerBlockingJob.waitUntilEnded();
		}

		Thread.sleep(SHORT_WAIT);
	}

	private static final Log _log = LogFactoryUtil.getLog(TestUtil.class);

}