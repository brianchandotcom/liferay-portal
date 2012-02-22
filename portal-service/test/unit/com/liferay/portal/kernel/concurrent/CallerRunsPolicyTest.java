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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.TestCase;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class CallerRunsPolicyTest extends TestCase {

	public void testCallerRunsPolicy1() {
		MarkerThreadPoolHandler markerThreadPoolHandler =
			new MarkerThreadPoolHandler();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3,
			new CallerRunsPolicy(), Executors.defaultThreadFactory(),
			markerThreadPoolHandler);

		threadPoolExecutor.shutdown();

		MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

		threadPoolExecutor.execute(markerBlockingJob);

		assertFalse(markerBlockingJob.isStarted());
		assertFalse(markerThreadPoolHandler.isBeforeExecuteRan());
		assertFalse(markerThreadPoolHandler.isAfterExecuteRan());
	}

	public void testCallerRunsPolicy2() {
		MarkerThreadPoolHandler markerThreadPoolHandler =
			new MarkerThreadPoolHandler();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
			new CallerRunsPolicy(), Executors.defaultThreadFactory(),
			markerThreadPoolHandler);

		try {
			threadPoolExecutor.execute(new MarkerBlockingJob(true));
			threadPoolExecutor.execute(new MarkerBlockingJob(true));

			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

			threadPoolExecutor.execute(markerBlockingJob);

			assertTrue(markerBlockingJob.isEnded());
			assertTrue(markerThreadPoolHandler.isBeforeExecuteRan());
			assertTrue(markerThreadPoolHandler.isAfterExecuteRan());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	public void testCallerRunsPolicy3() {
		MarkerThreadPoolHandler markerThreadPoolHandler =
			new MarkerThreadPoolHandler();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
			new CallerRunsPolicy(), Executors.defaultThreadFactory(),
			markerThreadPoolHandler);

		try {
			threadPoolExecutor.execute(new MarkerBlockingJob(true));
			threadPoolExecutor.execute(new MarkerBlockingJob(true));

			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(
				false, true);

			try {
				threadPoolExecutor.execute(markerBlockingJob);

				fail();
			}
			catch (RuntimeException re) {
			}

			assertTrue(markerBlockingJob.isStarted());
			assertFalse(markerBlockingJob.isEnded());
			assertTrue(markerThreadPoolHandler.isBeforeExecuteRan());
			assertTrue(markerThreadPoolHandler.isAfterExecuteRan());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

}