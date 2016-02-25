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
public class DiscardPolicyTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testDiscardPolicy1() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
			new DiscardPolicy(), Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());

		threadPoolExecutor.shutdown();

		MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

		threadPoolExecutor.execute(markerBlockingJob);

		Assert.assertFalse(markerBlockingJob.isEnded());
	}

	@Test
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

			Assert.assertEquals(1, threadPoolExecutor.getActiveCount());
			Assert.assertEquals(1, threadPoolExecutor.getPendingTaskCount());

			threadPoolExecutor.execute(markerBlockingJob3);

			Assert.assertFalse(markerBlockingJob3.isStarted());
			Assert.assertEquals(1, threadPoolExecutor.getActiveCount());
			Assert.assertEquals(1, threadPoolExecutor.getPendingTaskCount());

			markerBlockingJob1.unBlock();
			markerBlockingJob2.waitUntilBlock();

			Assert.assertTrue(markerBlockingJob1.isEnded());
			Assert.assertEquals(1, threadPoolExecutor.getActiveCount());
			Assert.assertEquals(0, threadPoolExecutor.getPendingTaskCount());

			markerBlockingJob2.unBlock();

			TestUtil.waitUntilEnded(markerBlockingJob2);

			Assert.assertEquals(0, threadPoolExecutor.getActiveCount());
			Assert.assertEquals(0, threadPoolExecutor.getPendingTaskCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

}