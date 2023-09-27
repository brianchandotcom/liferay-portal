/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (RuntimeException runtimeException) {
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