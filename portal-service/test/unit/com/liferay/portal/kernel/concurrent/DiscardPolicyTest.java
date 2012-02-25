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
public class DiscardPolicyTest extends TestCase {

	public void testDiscardPolicy1() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
			new DiscardPolicy(), Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());

		threadPoolExecutor.shutdown();

		MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

		threadPoolExecutor.execute(markerBlockingJob);

		assertFalse(markerBlockingJob.isEnded());
	}

	public void testDiscardPolicy2() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
			new DiscardPolicy(), Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());

		try {
			MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);
			MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);
			MarkerBlockingJob markerBlockingJob3 = new MarkerBlockingJob();

			threadPoolExecutor.execute(markerBlockingJob1);

			markerBlockingJob1.waitUntilBlock();

			threadPoolExecutor.execute(markerBlockingJob2);

			assertEquals(1, threadPoolExecutor.getActiveCount());
			assertEquals(1, threadPoolExecutor.getPendingTaskCount());

			threadPoolExecutor.execute(markerBlockingJob3);

			assertFalse(markerBlockingJob3.isStarted());
			assertEquals(1, threadPoolExecutor.getActiveCount());
			assertEquals(1, threadPoolExecutor.getPendingTaskCount());

			markerBlockingJob1.unBlock();
			markerBlockingJob2.waitUntilBlock();

			assertTrue(markerBlockingJob1.isEnded());
			assertEquals(1, threadPoolExecutor.getActiveCount());
			assertEquals(0, threadPoolExecutor.getPendingTaskCount());

			markerBlockingJob2.unBlock();

			TestUtil.waitUntilEnded(markerBlockingJob2);

			assertEquals(0, threadPoolExecutor.getActiveCount());
			assertEquals(0, threadPoolExecutor.getPendingTaskCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

}