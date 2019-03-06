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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ThreadPoolExecutorTest {

	@Test
	public void testAdjustPoolSizeDecreaseCoreAndMaxPoolSizeAfterConstruct() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			10, 20, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		Assert.assertEquals(10, threadPoolExecutor.getCorePoolSize());
		Assert.assertEquals(20, threadPoolExecutor.getMaxPoolSize());

		threadPoolExecutor.adjustPoolSize(5, 10);

		Assert.assertEquals(5, threadPoolExecutor.getCorePoolSize());
		Assert.assertEquals(10, threadPoolExecutor.getMaxPoolSize());
	}

	@Test
	public void testAdjustPoolSizeDecreaseCorePoolSizeToLessThanPoolSize()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			2, 3, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);
			MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);

			threadPoolExecutor.execute(markerBlockingJob1);
			threadPoolExecutor.execute(markerBlockingJob2);

			TestUtil.waitUntilBlock(markerBlockingJob1, markerBlockingJob2);
			TestUtil.unblock(markerBlockingJob1, markerBlockingJob2);
			TestUtil.waitUntilEnded(markerBlockingJob1, markerBlockingJob2);

			Assert.assertEquals(2, threadPoolExecutor.getPoolSize());
			Assert.assertEquals(0, threadPoolExecutor.getActiveCount());

			threadPoolExecutor.adjustPoolSize(1, 3);

			Assert.assertEquals(1, threadPoolExecutor.getCorePoolSize());
			Assert.assertEquals(3, threadPoolExecutor.getMaxPoolSize());

			Thread.sleep(TestUtil.KEEPALIVE_WAIT * 2);

			Assert.assertEquals(1, threadPoolExecutor.getPoolSize());
			Assert.assertEquals(0, threadPoolExecutor.getActiveCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testAdjustPoolSizeDecreseCoreAndMaxPoolSizeToLessThanPoolSize()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			2, 3, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);
			MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);
			MarkerBlockingJob markerBlockingJob3 = new MarkerBlockingJob(true);

			threadPoolExecutor.execute(markerBlockingJob1);
			threadPoolExecutor.execute(markerBlockingJob2);
			threadPoolExecutor.execute(markerBlockingJob3);

			TestUtil.waitUntilBlock(
				markerBlockingJob1, markerBlockingJob2, markerBlockingJob3);
			TestUtil.unblock(
				markerBlockingJob1, markerBlockingJob2, markerBlockingJob3);
			TestUtil.waitUntilEnded(
				markerBlockingJob1, markerBlockingJob2, markerBlockingJob3);

			Thread.sleep(TestUtil.SHORT_WAIT);

			Assert.assertEquals(3, threadPoolExecutor.getPoolSize());
			Assert.assertEquals(0, threadPoolExecutor.getActiveCount());

			Thread.sleep(TestUtil.KEEPALIVE_WAIT * 2);

			Assert.assertEquals(2, threadPoolExecutor.getPoolSize());
			Assert.assertEquals(0, threadPoolExecutor.getActiveCount());

			threadPoolExecutor.adjustPoolSize(1, 1);

			Assert.assertEquals(1, threadPoolExecutor.getCorePoolSize());
			Assert.assertEquals(1, threadPoolExecutor.getMaxPoolSize());

			Thread.sleep(TestUtil.KEEPALIVE_WAIT * 2);

			Assert.assertEquals(1, threadPoolExecutor.getPoolSize());
			Assert.assertEquals(0, threadPoolExecutor.getActiveCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testAdjustPoolSizeIllegalArgumentExceptions() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		try {
			threadPoolExecutor.adjustPoolSize(-1, 10);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		try {
			threadPoolExecutor.adjustPoolSize(1, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		try {
			threadPoolExecutor.adjustPoolSize(1, 0);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		try {
			threadPoolExecutor.adjustPoolSize(2, 1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}
	}

	@Test
	public void testAdjustPoolSizeIncreaseCoreAndMaxPoolSizeAfterConstruct() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		Assert.assertEquals(5, threadPoolExecutor.getCorePoolSize());
		Assert.assertEquals(10, threadPoolExecutor.getMaxPoolSize());

		threadPoolExecutor.adjustPoolSize(10, 20);

		Assert.assertEquals(10, threadPoolExecutor.getCorePoolSize());
		Assert.assertEquals(20, threadPoolExecutor.getMaxPoolSize());
	}

	@Test
	public void testAdjustPoolSizeIncreaseCoreAndMaxPoolSizeWithEmptyTaskQueue()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(true);

			threadPoolExecutor.execute(markerBlockingJob);

			markerBlockingJob.waitUntilBlock();

			Assert.assertEquals(1, threadPoolExecutor.getPoolSize());
			Assert.assertEquals(1, threadPoolExecutor.getActiveCount());

			threadPoolExecutor.adjustPoolSize(2, 2);

			Assert.assertEquals(2, threadPoolExecutor.getCorePoolSize());
			Assert.assertEquals(2, threadPoolExecutor.getMaxPoolSize());

			Thread.sleep(TestUtil.SHORT_WAIT);

			Assert.assertEquals(1, threadPoolExecutor.getPoolSize());
			Assert.assertEquals(1, threadPoolExecutor.getActiveCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	@Test
	public void testAdjustPoolSizeIncreaseCoreAndMaxPoolSizeWithNonemptyTaskQueue()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);
			MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);

			threadPoolExecutor.execute(markerBlockingJob1);
			threadPoolExecutor.execute(markerBlockingJob2);

			markerBlockingJob1.waitUntilBlock();

			Assert.assertEquals(1, threadPoolExecutor.getPoolSize());
			Assert.assertEquals(1, threadPoolExecutor.getActiveCount());
			Assert.assertEquals(1, threadPoolExecutor.getPendingTaskCount());

			threadPoolExecutor.adjustPoolSize(2, 2);

			Assert.assertEquals(2, threadPoolExecutor.getCorePoolSize());
			Assert.assertEquals(2, threadPoolExecutor.getMaxPoolSize());

			markerBlockingJob2.waitUntilBlock();

			Assert.assertEquals(2, threadPoolExecutor.getPoolSize());
			Assert.assertEquals(2, threadPoolExecutor.getActiveCount());
			Assert.assertEquals(0, threadPoolExecutor.getPendingTaskCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	@Test
	public void testAutoResizePoolAllowCoreThreadTimeout()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		try {
			Queue<MarkerBlockingJob> markerBlockingJobQueue =
				new LinkedList<>();

			Assert.assertEquals(0, threadPoolExecutor.getPoolSize());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(
					true);

				markerBlockingJobQueue.add(markerBlockingJob);

				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilBlock();

				Assert.assertEquals(i + 1, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					i + 1, threadPoolExecutor.getLargestPoolSize());
				Assert.assertEquals(i + 1, threadPoolExecutor.getTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(
					true);

				markerBlockingJobQueue.add(markerBlockingJob);

				threadPoolExecutor.execute(markerBlockingJob);

				Assert.assertEquals(10, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					10, threadPoolExecutor.getLargestPoolSize());
				Assert.assertEquals(
					i + 1, threadPoolExecutor.getPendingTaskCount());
				Assert.assertEquals(i + 11, threadPoolExecutor.getTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				try {
					threadPoolExecutor.execute(new MarkerBlockingJob(true));

					Assert.fail();
				}
				catch (RejectedExecutionException ree) {
				}

				Assert.assertEquals(10, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					10, threadPoolExecutor.getLargestPoolSize());
				Assert.assertEquals(
					10, threadPoolExecutor.getPendingTaskCount());
				Assert.assertEquals(20, threadPoolExecutor.getTaskCount());
			}

			Assert.assertEquals(20, markerBlockingJobQueue.size());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();

				markerBlockingJob.unBlock();

				TestUtil.waitUntilEnded(markerBlockingJob);

				Assert.assertEquals(10, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					10, threadPoolExecutor.getLargestPoolSize());
				Assert.assertEquals(
					9 - i, threadPoolExecutor.getPendingTaskCount());
				Assert.assertEquals(20, threadPoolExecutor.getTaskCount());
				Assert.assertEquals(
					i + 1, threadPoolExecutor.getCompletedTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();

				markerBlockingJob.unBlock();

				TestUtil.waitUntilEnded(markerBlockingJob);

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				Assert.assertEquals(9 - i, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					10, threadPoolExecutor.getLargestPoolSize());
				Assert.assertEquals(
					0, threadPoolExecutor.getPendingTaskCount());
				Assert.assertEquals(20, threadPoolExecutor.getTaskCount());
				Assert.assertEquals(
					i + 11, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	@Test
	public void testAutoResizePoolDoNotAllowCoreThreadTimeout()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			Queue<MarkerBlockingJob> markerBlockingJobQueue =
				new LinkedList<>();

			Assert.assertEquals(0, threadPoolExecutor.getPoolSize());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(
					true);

				markerBlockingJobQueue.add(markerBlockingJob);

				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilBlock();

				Assert.assertEquals(i + 1, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					i + 1, threadPoolExecutor.getLargestPoolSize());
				Assert.assertEquals(i + 1, threadPoolExecutor.getTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(
					true);

				markerBlockingJobQueue.add(markerBlockingJob);

				threadPoolExecutor.execute(markerBlockingJob);

				Assert.assertEquals(10, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					10, threadPoolExecutor.getLargestPoolSize());
				Assert.assertEquals(
					i + 1, threadPoolExecutor.getPendingTaskCount());
				Assert.assertEquals(i + 11, threadPoolExecutor.getTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				try {
					threadPoolExecutor.execute(new MarkerBlockingJob(true));

					Assert.fail();
				}
				catch (RejectedExecutionException ree) {
				}

				Assert.assertEquals(10, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					10, threadPoolExecutor.getLargestPoolSize());
				Assert.assertEquals(
					10, threadPoolExecutor.getPendingTaskCount());
				Assert.assertEquals(20, threadPoolExecutor.getTaskCount());
			}

			Assert.assertEquals(20, markerBlockingJobQueue.size());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();

				markerBlockingJob.unBlock();

				TestUtil.waitUntilEnded(markerBlockingJob);

				Assert.assertEquals(10, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					10, threadPoolExecutor.getLargestPoolSize());
				Assert.assertEquals(
					9 - i, threadPoolExecutor.getPendingTaskCount());
				Assert.assertEquals(20, threadPoolExecutor.getTaskCount());
				Assert.assertEquals(
					i + 1, threadPoolExecutor.getCompletedTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();

				markerBlockingJob.unBlock();

				TestUtil.waitUntilEnded(markerBlockingJob);

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				Assert.assertEquals(
					(i > 4) ? 5 : 9 - i, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					10, threadPoolExecutor.getLargestPoolSize());
				Assert.assertEquals(
					0, threadPoolExecutor.getPendingTaskCount());
				Assert.assertEquals(20, threadPoolExecutor.getTaskCount());
				Assert.assertEquals(
					i + 11, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	@Test
	public void testAwaitTerminationSuccess() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, 1, TimeUnit.SECONDS, true, 3);

		threadPoolExecutor.shutdown();

		long startTime = System.currentTimeMillis();

		Assert.assertTrue(
			threadPoolExecutor.awaitTermination(10, TimeUnit.MILLISECONDS));

		long waitTime = System.currentTimeMillis() - startTime;

		Assert.assertTrue(waitTime < TestUtil.SHORT_WAIT);
	}

	@Test
	public void testAwaitTerminationWithNegativeWaitTime()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		long startTime = System.currentTimeMillis();

		Assert.assertFalse(
			threadPoolExecutor.awaitTermination(-1, TimeUnit.MILLISECONDS));

		long waitTime = System.currentTimeMillis() - startTime;

		Assert.assertTrue(waitTime < TestUtil.SHORT_WAIT);
	}

	@Test
	public void testAwaitTerminationWithoutShutdown()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		long startTime = System.nanoTime();

		Assert.assertFalse(
			threadPoolExecutor.awaitTermination(
				TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS));

		long waitTime = System.nanoTime() - startTime;

		Assert.assertTrue(
			waitTime >= TimeUnit.MILLISECONDS.toNanos(TestUtil.KEEPALIVE_TIME));
	}

	@Test
	public void testAwaitTerminationWithZeroWaitTime()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		long startTime = System.currentTimeMillis();

		Assert.assertFalse(
			threadPoolExecutor.awaitTermination(0, TimeUnit.MILLISECONDS));

		long waitTime = System.currentTimeMillis() - startTime;

		Assert.assertTrue(waitTime < TestUtil.SHORT_WAIT);
	}

	@Test
	public void testConstructor1() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2);

		Assert.assertEquals(1, threadPoolExecutor.getCorePoolSize());
		Assert.assertEquals(2, threadPoolExecutor.getMaxPoolSize());
		Assert.assertEquals(
			60 * 1000000000L,
			threadPoolExecutor.getKeepAliveTime(TimeUnit.NANOSECONDS));
		Assert.assertEquals(
			false, threadPoolExecutor.isAllowCoreThreadTimeout());
		Assert.assertEquals(
			Integer.MAX_VALUE,
			threadPoolExecutor.getRemainingTaskQueueCapacity());

		RejectedExecutionHandler rejectedExecutionHandler =
			threadPoolExecutor.getRejectedExecutionHandler();

		Assert.assertTrue(rejectedExecutionHandler instanceof AbortPolicy);

		ThreadPoolHandler threadPoolHandler =
			threadPoolExecutor.getThreadPoolHandler();

		Assert.assertTrue(
			threadPoolHandler instanceof ThreadPoolHandlerAdapter);

		Assert.assertFalse(threadPoolExecutor.isShutdown());
		Assert.assertFalse(threadPoolExecutor.isTerminating());
		Assert.assertFalse(threadPoolExecutor.isTerminated());
	}

	@Test
	public void testConstructor2() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		Assert.assertEquals(1, threadPoolExecutor.getCorePoolSize());
		Assert.assertEquals(2, threadPoolExecutor.getMaxPoolSize());
		Assert.assertEquals(
			TestUtil.KEEPALIVE_TIME * 1000000,
			threadPoolExecutor.getKeepAliveTime(TimeUnit.NANOSECONDS));
		Assert.assertEquals(
			true, threadPoolExecutor.isAllowCoreThreadTimeout());
		Assert.assertEquals(
			3, threadPoolExecutor.getRemainingTaskQueueCapacity());

		RejectedExecutionHandler rejectedExecutionHandler =
			threadPoolExecutor.getRejectedExecutionHandler();

		Assert.assertTrue(rejectedExecutionHandler instanceof AbortPolicy);

		ThreadPoolHandler threadPoolHandler =
			threadPoolExecutor.getThreadPoolHandler();

		Assert.assertTrue(
			threadPoolHandler instanceof ThreadPoolHandlerAdapter);

		Assert.assertFalse(threadPoolExecutor.isShutdown());
		Assert.assertFalse(threadPoolExecutor.isTerminating());
		Assert.assertFalse(threadPoolExecutor.isTerminated());
	}

	@Test
	public void testConstructor3() {
		RejectedExecutionHandler rejectedExecutionHandler =
			new CallerRunsPolicy();

		ThreadFactory threadFactory = Executors.defaultThreadFactory();

		ThreadPoolHandler threadPoolHandler = new ThreadPoolHandlerAdapter();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3,
			rejectedExecutionHandler, threadFactory, threadPoolHandler);

		Assert.assertEquals(1, threadPoolExecutor.getCorePoolSize());
		Assert.assertEquals(2, threadPoolExecutor.getMaxPoolSize());
		Assert.assertEquals(
			TestUtil.KEEPALIVE_TIME * 1000000,
			threadPoolExecutor.getKeepAliveTime(TimeUnit.NANOSECONDS));
		Assert.assertEquals(
			true, threadPoolExecutor.isAllowCoreThreadTimeout());
		Assert.assertEquals(
			3, threadPoolExecutor.getRemainingTaskQueueCapacity());
		Assert.assertSame(
			rejectedExecutionHandler,
			threadPoolExecutor.getRejectedExecutionHandler());
		Assert.assertSame(threadFactory, threadPoolExecutor.getThreadFactory());
		Assert.assertSame(
			threadPoolHandler, threadPoolExecutor.getThreadPoolHandler());
		Assert.assertFalse(threadPoolExecutor.isShutdown());
		Assert.assertFalse(threadPoolExecutor.isTerminating());
		Assert.assertFalse(threadPoolExecutor.isTerminated());
	}

	@Test
	public void testConstructorExceptions() {
		try {
			new ThreadPoolExecutor(
				-1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				1, -1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				1, 0, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				2, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(1, 1, -1, TimeUnit.MILLISECONDS, true, 1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 0);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
				null, Executors.defaultThreadFactory(),
				new ThreadPoolHandlerAdapter());

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
				new AbortPolicy(), null, new ThreadPoolHandlerAdapter());

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
				new AbortPolicy(), Executors.defaultThreadFactory(), null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testExecuteAcceptedOnConcurrentShutdown()
		throws InterruptedException {

		RecordRejectedExecutionHandler recordRejectedExecutionHandler =
			new RecordRejectedExecutionHandler();

		final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3,
			recordRejectedExecutionHandler, Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());

		final TaskQueue<Runnable> taskQueue = threadPoolExecutor.getTaskQueue();

		final CountDownLatch executeLatch = new CountDownLatch(1);

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					ReentrantLock takeLock = taskQueue.getTakeLock();

					takeLock.lock();

					executeLatch.countDown();

					try {
						while (!takeLock.hasQueuedThreads()) {
							Thread.sleep(1);
						}

						Assert.assertNotNull(taskQueue.take());

						threadPoolExecutor.shutdown();
					}
					finally {
						takeLock.unlock();
					}
				}
				catch (InterruptedException ie) {
				}
			}

		};

		thread.start();

		executeLatch.await();

		try {
			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

			threadPoolExecutor.execute(markerBlockingJob);

			List<Runnable> rejectedList =
				recordRejectedExecutionHandler.getRejectedList();

			Assert.assertTrue(rejectedList.isEmpty());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecuteAfterShutdown() {
		RecordRejectedExecutionHandler recordRejectedExecutionHandler =
			new RecordRejectedExecutionHandler();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3,
			recordRejectedExecutionHandler, Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());

		threadPoolExecutor.shutdown();

		MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

		threadPoolExecutor.execute(markerBlockingJob);

		Assert.assertFalse(markerBlockingJob.isStarted());

		List<Runnable> rejectedList =
			recordRejectedExecutionHandler.getRejectedList();

		Assert.assertEquals(rejectedList.toString(), 1, rejectedList.size());
		Assert.assertSame(markerBlockingJob, rejectedList.get(0));
	}

	@Test
	public void testExecuteAfterTaskQueuePollingTimeoutBeforeExitChecking()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

		MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);

		threadPoolExecutor.execute(markerBlockingJob1);

		markerBlockingJob1.waitUntilBlock();

		ReentrantLock mainLock = threadPoolExecutor.getMainLock();

		TaskQueue<Runnable> taskQueue = threadPoolExecutor.getTaskQueue();

		ReentrantLock takeLock = taskQueue.getTakeLock();

		takeLock.lock();

		try {
			markerBlockingJob1.unBlock();

			while (!takeLock.hasQueuedThreads()) {
				Thread.sleep(1);
			}

			mainLock.lock();
		}
		finally {
			takeLock.unlock();
		}

		MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);

		try {
			while (!mainLock.hasQueuedThreads()) {
				Thread.sleep(1);
			}

			threadPoolExecutor.execute(markerBlockingJob2);
		}
		finally {
			mainLock.unlock();
		}

		markerBlockingJob2.waitUntilBlock();
		markerBlockingJob2.unBlock();

		TestUtil.closePool(threadPoolExecutor);

		Assert.assertTrue(markerBlockingJob2.isEnded());
	}

	@Test
	public void testExecuteFastConsumerSlowProducerAllowCoreThreadTimeout()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		try {
			Assert.assertEquals(0, threadPoolExecutor.getActiveCount());
			Assert.assertEquals(0, threadPoolExecutor.getPoolSize());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilEnded();

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				Assert.assertTrue(markerBlockingJob.isEnded());
				Assert.assertEquals(0, threadPoolExecutor.getActiveCount());
				Assert.assertEquals(0, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					i + 1, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecuteFastConsumerSlowProducerDoNotAllowCoreThreadTimeout()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 3);

		try {
			Assert.assertEquals(0, threadPoolExecutor.getActiveCount());
			Assert.assertEquals(0, threadPoolExecutor.getPoolSize());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilEnded();

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				Assert.assertTrue(markerBlockingJob.isEnded());
				Assert.assertEquals(0, threadPoolExecutor.getActiveCount());
				Assert.assertEquals(1, threadPoolExecutor.getPoolSize());
				Assert.assertEquals(
					i + 1, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecuteNullRunnable() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		try {
			threadPoolExecutor.execute(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecuteRejectedOnConcurrentShutdown()
		throws InterruptedException {

		RecordRejectedExecutionHandler recordRejectedExecutionHandler =
			new RecordRejectedExecutionHandler();

		final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3,
			recordRejectedExecutionHandler, Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());

		final TaskQueue<Runnable> taskQueue = threadPoolExecutor.getTaskQueue();

		final CountDownLatch executeLatch = new CountDownLatch(1);

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					ReentrantLock putLock = taskQueue.getPutLock();

					putLock.lock();

					executeLatch.countDown();

					try {
						while (!putLock.hasQueuedThreads()) {
							Thread.sleep(1);
						}

						threadPoolExecutor.shutdown();
					}
					finally {
						putLock.unlock();
					}
				}
				catch (InterruptedException ie) {
				}
			}

		};

		thread.start();

		executeLatch.await();

		try {
			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

			threadPoolExecutor.execute(markerBlockingJob);

			List<Runnable> rejectedList =
				recordRejectedExecutionHandler.getRejectedList();

			Assert.assertEquals(
				rejectedList.toString(), 1, rejectedList.size());
			Assert.assertSame(markerBlockingJob, rejectedList.get(0));

			Assert.assertFalse(markerBlockingJob.isStarted());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecuteSuccess() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		try {
			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

			threadPoolExecutor.execute(markerBlockingJob);

			markerBlockingJob.waitUntilEnded();

			Assert.assertTrue(markerBlockingJob.isEnded());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecuteWithFullTaskQueue() {
		RecordRejectedExecutionHandler recordRejectedExecutionHandler =
			new RecordRejectedExecutionHandler();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
			recordRejectedExecutionHandler, Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());

		try {
			List<Runnable> rejectedList =
				recordRejectedExecutionHandler.getRejectedList();

			threadPoolExecutor.execute(new MarkerBlockingJob(true));

			Assert.assertEquals(
				rejectedList.toString(), 0, rejectedList.size());

			threadPoolExecutor.execute(new MarkerBlockingJob(true));

			Assert.assertEquals(
				rejectedList.toString(), 0, rejectedList.size());

			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

			threadPoolExecutor.execute(markerBlockingJob);

			Assert.assertEquals(
				rejectedList.toString(), 1, rejectedList.size());
			Assert.assertSame(markerBlockingJob, rejectedList.get(0));
			Assert.assertFalse(markerBlockingJob.isStarted());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	@Test
	public void testExecuteWorkerThreadDead() throws InterruptedException {
		SetRecordUncaughtExceptionThreadFactory threadFactory =
			new SetRecordUncaughtExceptionThreadFactory();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10,
			new AbortPolicy(), threadFactory, new ThreadPoolHandlerAdapter());

		RecordUncaughtExceptionHandler recordUncaughtExceptionHandler =
			threadFactory.getRecordUncaughtExceptionHandler();

		Queue<MarkerBlockingJob> markerBlockingJobQueue = new LinkedList<>();

		try {
			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(
					false, true);

				markerBlockingJobQueue.add(markerBlockingJob);

				threadPoolExecutor.execute(markerBlockingJob);
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);

			for (Thread thread : threadFactory.getCreatedThreads()) {
				thread.join();
			}
		}

		Assert.assertEquals(1, threadPoolExecutor.getLargestPoolSize());

		Map<Thread, Throwable> uncaughtMap =
			recordUncaughtExceptionHandler.getUncaughtMap();

		Assert.assertEquals(uncaughtMap.toString(), 10, uncaughtMap.size());

		for (MarkerBlockingJob markerBlockingJob : markerBlockingJobQueue) {
			Assert.assertTrue(markerBlockingJob.isStarted());
			Assert.assertFalse(markerBlockingJob.isEnded());
		}
	}

	@Test
	public void testFinalize() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		Assert.assertFalse(threadPoolExecutor.isShutdown());

		threadPoolExecutor.finalize();

		Assert.assertTrue(threadPoolExecutor.isShutdown());
	}

	@Test
	public void testShutdownNowAfterTaskPolledBeforeTaskRunning()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 1);

		MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);
		MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);

		threadPoolExecutor.execute(markerBlockingJob1);

		markerBlockingJob1.waitUntilBlock();

		threadPoolExecutor.execute(markerBlockingJob2);

		TaskQueue<Runnable> taskQueue = threadPoolExecutor.getTaskQueue();

		AbstractQueuedSynchronizer headWorkerTask = null;

		ReentrantLock takeLock = taskQueue.getTakeLock();

		takeLock.lock();

		try {
			markerBlockingJob1.unBlock();

			while (!takeLock.hasQueuedThreads()) {
				Thread.sleep(1);
			}

			Set<? extends AbstractQueuedSynchronizer> workerTasks =
				threadPoolExecutor.getWorkerTasks();

			Assert.assertEquals(workerTasks.toString(), 1, workerTasks.size());

			Iterator<? extends AbstractQueuedSynchronizer> iterator =
				workerTasks.iterator();

			headWorkerTask = iterator.next();

			headWorkerTask.acquire(1);
		}
		finally {
			takeLock.unlock();
		}

		while (!headWorkerTask.hasQueuedThreads()) {
			Thread.sleep(1);
		}

		threadPoolExecutor.shutdownNow();

		headWorkerTask.release(1);

		Assert.assertTrue(
			threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS));
		Assert.assertTrue(markerBlockingJob2.isInterrupted());
	}

	@Test
	public void testShutdownNowAfterTaskQueuePollingTimeoutBeforeExitChecking()
		throws InterruptedException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

		MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(true);

		threadPoolExecutor.execute(markerBlockingJob);

		markerBlockingJob.waitUntilBlock();

		ReentrantLock mainLock = threadPoolExecutor.getMainLock();

		TaskQueue<Runnable> taskQueue = threadPoolExecutor.getTaskQueue();

		ReentrantLock takeLock = taskQueue.getTakeLock();

		takeLock.lock();

		try {
			markerBlockingJob.unBlock();

			while (!takeLock.hasQueuedThreads()) {
				Thread.sleep(1);
			}

			mainLock.lock();
		}
		finally {
			takeLock.unlock();
		}

		try {
			while (!mainLock.hasQueuedThreads()) {
				Thread.sleep(1);
			}

			threadPoolExecutor.shutdownNow();
		}
		finally {
			mainLock.unlock();
		}

		Assert.assertTrue(threadPoolExecutor.isShutdown());
		Assert.assertTrue(
			threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS));
	}

	@Test
	public void testTerminationNoticeableFuture() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

		NoticeableFuture<Void> terminationNoticeableFutute =
			threadPoolExecutor.terminationNoticeableFuture();

		Assert.assertFalse(terminationNoticeableFutute.isDone());
		Assert.assertFalse(terminationNoticeableFutute.cancel(true));

		final AtomicBoolean marker = new AtomicBoolean();

		terminationNoticeableFutute.addFutureListener(
			new FutureListener<Void>() {

				@Override
				public void complete(Future<Void> future) {
					marker.set(true);
				}

			});

		threadPoolExecutor.shutdown();

		Assert.assertTrue(
			threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS));
		Assert.assertTrue(terminationNoticeableFutute.isDone());
		Assert.assertTrue(marker.get());
	}

}