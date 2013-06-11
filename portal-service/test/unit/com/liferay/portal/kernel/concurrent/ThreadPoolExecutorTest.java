/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ThreadPoolExecutorTest extends TestCase {

	@Test
	public void testAdjustPoolSize1() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		try {
			threadPoolExecutor.adjustPoolSize(-1, 10);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		try {
			threadPoolExecutor.adjustPoolSize(1, -1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		try {
			threadPoolExecutor.adjustPoolSize(1, 0);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		try {
			threadPoolExecutor.adjustPoolSize(2, 1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}
	}

	@Test
	public void testAdjustPoolSize2() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		assertEquals(5, threadPoolExecutor.getCorePoolSize());
		assertEquals(10, threadPoolExecutor.getMaxPoolSize());

		threadPoolExecutor.adjustPoolSize(10, 20);

		assertEquals(10, threadPoolExecutor.getCorePoolSize());
		assertEquals(20, threadPoolExecutor.getMaxPoolSize());
	}

	@Test
	public void testAdjustPoolSize3() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			10, 20, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		assertEquals(10, threadPoolExecutor.getCorePoolSize());
		assertEquals(20, threadPoolExecutor.getMaxPoolSize());

		threadPoolExecutor.adjustPoolSize(5, 10);

		assertEquals(5, threadPoolExecutor.getCorePoolSize());
		assertEquals(10, threadPoolExecutor.getMaxPoolSize());
	}

	@Test
	public void testAdjustPoolSize4() throws InterruptedException {
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

			assertEquals(2, threadPoolExecutor.getPoolSize());
			assertEquals(0, threadPoolExecutor.getActiveCount());

			threadPoolExecutor.adjustPoolSize(1, 3);

			assertEquals(1, threadPoolExecutor.getCorePoolSize());
			assertEquals(3, threadPoolExecutor.getMaxPoolSize());

			Thread.sleep(TestUtil.KEEPALIVE_WAIT * 2);

			assertEquals(1, threadPoolExecutor.getPoolSize());
			assertEquals(0, threadPoolExecutor.getActiveCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testAdjustPoolSize5() throws InterruptedException {
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

			assertEquals(3, threadPoolExecutor.getPoolSize());
			assertEquals(0, threadPoolExecutor.getActiveCount());

			Thread.sleep(TestUtil.KEEPALIVE_WAIT * 2);

			assertEquals(2, threadPoolExecutor.getPoolSize());
			assertEquals(0, threadPoolExecutor.getActiveCount());

			threadPoolExecutor.adjustPoolSize(1, 1);

			assertEquals(1, threadPoolExecutor.getCorePoolSize());
			assertEquals(1, threadPoolExecutor.getMaxPoolSize());

			Thread.sleep(TestUtil.KEEPALIVE_WAIT * 2);

			assertEquals(1, threadPoolExecutor.getPoolSize());
			assertEquals(0, threadPoolExecutor.getActiveCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testAdjustPoolSize6() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(true);

			threadPoolExecutor.execute(markerBlockingJob);

			markerBlockingJob.waitUntilBlock();

			assertEquals(1, threadPoolExecutor.getPoolSize());
			assertEquals(1, threadPoolExecutor.getActiveCount());

			threadPoolExecutor.adjustPoolSize(2, 2);

			assertEquals(2, threadPoolExecutor.getCorePoolSize());
			assertEquals(2, threadPoolExecutor.getMaxPoolSize());

			Thread.sleep(TestUtil.SHORT_WAIT);

			assertEquals(1, threadPoolExecutor.getPoolSize());
			assertEquals(1, threadPoolExecutor.getActiveCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	@Test
	public void testAdjustPoolSize7() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);
			MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);

			threadPoolExecutor.execute(markerBlockingJob1);
			threadPoolExecutor.execute(markerBlockingJob2);

			markerBlockingJob1.waitUntilBlock();

			assertEquals(1, threadPoolExecutor.getPoolSize());
			assertEquals(1, threadPoolExecutor.getActiveCount());
			assertEquals(1, threadPoolExecutor.getPendingTaskCount());

			threadPoolExecutor.adjustPoolSize(2, 2);

			assertEquals(2, threadPoolExecutor.getCorePoolSize());
			assertEquals(2, threadPoolExecutor.getMaxPoolSize());

			markerBlockingJob2.waitUntilBlock();

			assertEquals(2, threadPoolExecutor.getPoolSize());
			assertEquals(2, threadPoolExecutor.getActiveCount());
			assertEquals(0, threadPoolExecutor.getPendingTaskCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	@Test
	public void testAutoResizePool1() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		try {
			Queue<MarkerBlockingJob> markerBlockingJobQueue =
				new LinkedList<MarkerBlockingJob>();

			assertEquals(0, threadPoolExecutor.getPoolSize());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(
					true);

				markerBlockingJobQueue.add(markerBlockingJob);

				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilBlock();

				assertEquals(i + 1, threadPoolExecutor.getPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getLargestPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(
					true);

				markerBlockingJobQueue.add(markerBlockingJob);

				threadPoolExecutor.execute(markerBlockingJob);

				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getPendingTaskCount());
				assertEquals(i + 11, threadPoolExecutor.getTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				try {
					threadPoolExecutor.execute(new MarkerBlockingJob(true));

					fail();
				}
				catch (RejectedExecutionException ree) {
				}

				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(10, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
			}

			assertEquals(20, markerBlockingJobQueue.size());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();

				markerBlockingJob.unBlock();

				TestUtil.waitUntilEnded(markerBlockingJob);

				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(9 - i, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
				assertEquals(i + 1, threadPoolExecutor.getCompletedTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();

				markerBlockingJob.unBlock();

				TestUtil.waitUntilEnded(markerBlockingJob);

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				assertEquals(9 - i, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(0, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
				assertEquals(
					i + 11, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	@Test
	public void testAutoResizePool2() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			Queue<MarkerBlockingJob> markerBlockingJobQueue =
				new LinkedList<MarkerBlockingJob>();

			assertEquals(0, threadPoolExecutor.getPoolSize());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(
					true);

				markerBlockingJobQueue.add(markerBlockingJob);

				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilBlock();

				assertEquals(i + 1, threadPoolExecutor.getPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getLargestPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(
					true);

				markerBlockingJobQueue.add(markerBlockingJob);

				threadPoolExecutor.execute(markerBlockingJob);

				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getPendingTaskCount());
				assertEquals(i + 11, threadPoolExecutor.getTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				try {
					threadPoolExecutor.execute(new MarkerBlockingJob(true));

					fail();
				}
				catch (RejectedExecutionException ree) {
				}

				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(10, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
			}

			assertEquals(20, markerBlockingJobQueue.size());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();

				markerBlockingJob.unBlock();

				TestUtil.waitUntilEnded(markerBlockingJob);

				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(9 - i, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
				assertEquals(i + 1, threadPoolExecutor.getCompletedTaskCount());
			}

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();

				markerBlockingJob.unBlock();

				TestUtil.waitUntilEnded(markerBlockingJob);

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				assertEquals(
					(i > 4) ? 5 : 9 - i, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(0, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
				assertEquals(
					i + 11, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	@Test
	public void testAwaitTermination1() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		long startTime = System.nanoTime();

		assertFalse(
			threadPoolExecutor.awaitTermination(
				TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS));

		long waitTime = System.nanoTime() - startTime;

		assertTrue(
			waitTime >= TimeUnit.MILLISECONDS.toNanos(TestUtil.KEEPALIVE_TIME));
	}

	@Test
	public void testAwaitTermination2() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		long startTime = System.currentTimeMillis();

		assertFalse(
			threadPoolExecutor.awaitTermination(0, TimeUnit.MILLISECONDS));

		long waitTime = System.currentTimeMillis() - startTime;

		assertTrue(waitTime < TestUtil.SHORT_WAIT);
	}

	@Test
	public void testAwaitTermination3() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		long startTime = System.currentTimeMillis();

		assertFalse(
			threadPoolExecutor.awaitTermination(0, TimeUnit.MILLISECONDS));

		long waitTime = System.currentTimeMillis() - startTime;

		assertTrue(waitTime < TestUtil.SHORT_WAIT);
	}

	@Test
	public void testAwaitTermination4() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, 1, TimeUnit.SECONDS, true, 3);

		threadPoolExecutor.shutdown();

		long startTime = System.currentTimeMillis();

		assertTrue(
			threadPoolExecutor.awaitTermination(10, TimeUnit.MILLISECONDS));

		long waitTime = System.currentTimeMillis() - startTime;

		assertTrue(waitTime < TestUtil.SHORT_WAIT);
	}

	@Test
	public void testConstructor1() {
		try {
			new ThreadPoolExecutor(
				-1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				1, -1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				1, 0, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				2, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(1, 1, -1, TimeUnit.MILLISECONDS, true, 1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, -1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 0);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
				null, Executors.defaultThreadFactory(),
				new ThreadPoolHandlerAdapter());

			fail();
		}
		catch (NullPointerException npe) {
		}

		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
				new AbortPolicy(), null, new ThreadPoolHandlerAdapter());

			fail();
		}
		catch (NullPointerException npe) {
		}

		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
				new AbortPolicy(), Executors.defaultThreadFactory(), null);

			fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testConstructor2() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2);

		assertEquals(1, threadPoolExecutor.getCorePoolSize());
		assertEquals(2, threadPoolExecutor.getMaxPoolSize());
		assertEquals(
			60 * 1000000000L,
			threadPoolExecutor.getKeepAliveTime(TimeUnit.NANOSECONDS));
		assertEquals(false, threadPoolExecutor.isAllowCoreThreadTimeout());
		assertEquals(
			Integer.MAX_VALUE,
			threadPoolExecutor.getRemainingTaskQueueCapacity());

		RejectedExecutionHandler rejectedExecutionHandler =
			threadPoolExecutor.getRejectedExecutionHandler();

		assertTrue(rejectedExecutionHandler instanceof AbortPolicy);

		ThreadPoolHandler threadPoolHandler =
			threadPoolExecutor.getThreadPoolHandler();

		assertTrue(threadPoolHandler instanceof ThreadPoolHandlerAdapter);
		assertFalse(threadPoolExecutor.isShutdown());
		assertFalse(threadPoolExecutor.isTerminating());
		assertFalse(threadPoolExecutor.isTerminated());
	}

	@Test
	public void testConstructor3() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		assertEquals(1, threadPoolExecutor.getCorePoolSize());
		assertEquals(2, threadPoolExecutor.getMaxPoolSize());
		assertEquals(
			TestUtil.KEEPALIVE_TIME * 1000000,
			threadPoolExecutor.getKeepAliveTime(TimeUnit.NANOSECONDS));
		assertEquals(true, threadPoolExecutor.isAllowCoreThreadTimeout());
		assertEquals(3, threadPoolExecutor.getRemainingTaskQueueCapacity());

		RejectedExecutionHandler rejectedExecutionHandler =
			threadPoolExecutor.getRejectedExecutionHandler();

		assertTrue(rejectedExecutionHandler instanceof AbortPolicy);

		ThreadPoolHandler threadPoolHandler =
			threadPoolExecutor.getThreadPoolHandler();

		assertTrue(threadPoolHandler instanceof ThreadPoolHandlerAdapter);
		assertFalse(threadPoolExecutor.isShutdown());
		assertFalse(threadPoolExecutor.isTerminating());
		assertFalse(threadPoolExecutor.isTerminated());
	}

	@Test
	public void testConstructor4() {
		RejectedExecutionHandler rejectedExecutionHandler =
			new CallerRunsPolicy();

		ThreadFactory threadFactory = Executors.defaultThreadFactory();

		ThreadPoolHandler threadPoolHandler = new ThreadPoolHandlerAdapter();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3,
			rejectedExecutionHandler, threadFactory, threadPoolHandler);

		assertEquals(1, threadPoolExecutor.getCorePoolSize());
		assertEquals(2, threadPoolExecutor.getMaxPoolSize());
		assertEquals(
			TestUtil.KEEPALIVE_TIME * 1000000,
			threadPoolExecutor.getKeepAliveTime(TimeUnit.NANOSECONDS));
		assertEquals(true, threadPoolExecutor.isAllowCoreThreadTimeout());
		assertEquals(3, threadPoolExecutor.getRemainingTaskQueueCapacity());
		assertSame(
			rejectedExecutionHandler,
			threadPoolExecutor.getRejectedExecutionHandler());
		assertSame(threadFactory, threadPoolExecutor.getThreadFactory());
		assertSame(
			threadPoolHandler, threadPoolExecutor.getThreadPoolHandler());
		assertFalse(threadPoolExecutor.isShutdown());
		assertFalse(threadPoolExecutor.isTerminating());
		assertFalse(threadPoolExecutor.isTerminated());
	}

	@Test
	public void testExecute1() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		try {
			threadPoolExecutor.execute(null);

			fail();
		}
		catch (NullPointerException npe) {
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecute2() {
		RecordRejectedExecutionHandler recordRejectedExecutionHandler =
			new RecordRejectedExecutionHandler();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3,
			recordRejectedExecutionHandler, Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());

		threadPoolExecutor.shutdown();

		MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

		threadPoolExecutor.execute(markerBlockingJob);

		assertFalse(markerBlockingJob.isStarted());

		List<Runnable> rejectedList =
			recordRejectedExecutionHandler.getRejectedList();

		assertEquals(1, rejectedList.size());
		assertSame(markerBlockingJob, rejectedList.get(0));
	}

	@Test
	public void testExecute3() {
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

			assertEquals(0, rejectedList.size());

			threadPoolExecutor.execute(new MarkerBlockingJob(true));

			assertEquals(0, rejectedList.size());

			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

			threadPoolExecutor.execute(markerBlockingJob);

			assertEquals(1, rejectedList.size());
			assertSame(markerBlockingJob, rejectedList.get(0));
			assertFalse(markerBlockingJob.isStarted());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	@Test
	public void testExecute4() throws InterruptedException {
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

			assertEquals(1, rejectedList.size());
			assertSame(markerBlockingJob, rejectedList.get(0));
			assertFalse(markerBlockingJob.isStarted());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecute5() throws InterruptedException {
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

						assertNotNull(taskQueue.take());

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

			assertTrue(
				recordRejectedExecutionHandler.getRejectedList().isEmpty());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecute6() throws InterruptedException {
		SetRecordUncaughtExceptionThreadFactory threadFactory =
			new SetRecordUncaughtExceptionThreadFactory();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10,
			new AbortPolicy(), threadFactory, new ThreadPoolHandlerAdapter());

		RecordUncaughtExceptionHandler recordUncaughtExceptionHandler =
			threadFactory.getRecordUncaughtExceptionHandler();

		Queue<MarkerBlockingJob> markerBlockingJobQueue =
			new LinkedList<MarkerBlockingJob>();

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

			Thread.sleep(TestUtil.SHORT_WAIT);
		}

		assertEquals(1, threadPoolExecutor.getLargestPoolSize());
		assertEquals(
			10, recordUncaughtExceptionHandler.getUncaughtMap().size());

		for (MarkerBlockingJob markerBlockingJob : markerBlockingJobQueue) {
			assertTrue(markerBlockingJob.isStarted());
			assertFalse(markerBlockingJob.isEnded());
		}
	}

	@Test
	public void testExecute7() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		try {
			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

			threadPoolExecutor.execute(markerBlockingJob);

			Thread.sleep(10);

			assertTrue(markerBlockingJob.isEnded());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecute8() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		try {
			assertEquals(0, threadPoolExecutor.getActiveCount());
			assertEquals(0, threadPoolExecutor.getPoolSize());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilEnded();

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				assertTrue(markerBlockingJob.isEnded());
				assertEquals(0, threadPoolExecutor.getActiveCount());
				assertEquals(0, threadPoolExecutor.getPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecute9() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 3);

		try {
			assertEquals(0, threadPoolExecutor.getActiveCount());
			assertEquals(0, threadPoolExecutor.getPoolSize());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilEnded();

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				assertTrue(markerBlockingJob.isEnded());
				assertEquals(0, threadPoolExecutor.getActiveCount());
				assertEquals(1, threadPoolExecutor.getPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	@Test
	public void testExecute10() throws InterruptedException {
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

		assertTrue(markerBlockingJob2.isEnded());
	}

	@Test
	public void testFinalize() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		assertFalse(threadPoolExecutor.isShutdown());

		threadPoolExecutor.finalize();

		assertTrue(threadPoolExecutor.isShutdown());
	}

	@Test
	public void testShutdownNow1() throws InterruptedException {
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

			assertEquals(1, workerTasks.size());

			headWorkerTask = workerTasks.iterator().next();

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

		assertTrue(threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS));
		assertTrue(markerBlockingJob2.isInterrupted());
	}

	@Test
	public void testShutdownNow2() throws InterruptedException {
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

		assertTrue(threadPoolExecutor.isShutdown());
		assertTrue(threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS));
	}

}