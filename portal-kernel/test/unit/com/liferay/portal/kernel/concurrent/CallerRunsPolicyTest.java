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

import com.liferay.portal.kernel.concurrent.test.MarkerBlockingJob;
import com.liferay.portal.kernel.concurrent.test.TestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class CallerRunsPolicyTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
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

		Assert.assertFalse(markerBlockingJob.isStarted());

		Assert.assertFalse(markerThreadPoolHandler.isBeforeExecuteRan());
		Assert.assertFalse(markerThreadPoolHandler.isAfterExecuteRan());
	}

	@Test
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

			Assert.assertTrue(markerBlockingJob.isEnded());

			Assert.assertTrue(markerThreadPoolHandler.isBeforeExecuteRan());
			Assert.assertTrue(markerThreadPoolHandler.isAfterExecuteRan());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	@Test
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

				Assert.fail();
			}
			catch (RuntimeException re) {
			}

			Assert.assertTrue(markerBlockingJob.isStarted());
			Assert.assertFalse(markerBlockingJob.isEnded());
			Assert.assertTrue(markerThreadPoolHandler.isBeforeExecuteRan());
			Assert.assertTrue(markerThreadPoolHandler.isAfterExecuteRan());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

}