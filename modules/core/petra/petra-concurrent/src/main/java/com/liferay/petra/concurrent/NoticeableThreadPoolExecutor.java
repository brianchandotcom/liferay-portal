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

package com.liferay.petra.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 */
public class NoticeableThreadPoolExecutor
	extends BaseNoticeableExecutorService {

	public NoticeableThreadPoolExecutor(
		int corePoolSize, int maximumPoolSize, long keepAliveTime,
		TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue,
		ThreadFactory threadFactory,
		RejectedExecutionHandler rejectedExecutionHandler,
		ThreadPoolHandler threadPoolHandler) {

		if (corePoolSize < 1) {
			throw new IllegalArgumentException(
				"To ensure FIFO, core pool size must be 1 or greater");
		}

		AtomicInteger terminationCounter = new AtomicInteger(2);

		_terminationDefaultNoticeableFuture =
			new DefaultNoticeableFuture<Void>() {

				@Override
				public boolean cancel(boolean mayInterruptIfRunning) {
					return false;
				}

			};

		_terminationDefaultNoticeableFuture.addFutureListener(
			future -> threadPoolHandler.terminated());

		_workerThreadPoolExecutor = new ThreadPoolExecutor(
			corePoolSize, maximumPoolSize, keepAliveTime, timeUnit,
			new SynchronousQueue<>(), threadFactory,
			(runnable, threadPoolExecutor) -> {
				if (threadPoolExecutor.isShutdown()) {
					rejectedExecutionHandler.rejectedExecution(
						runnable, threadPoolExecutor);

					return;
				}

				BlockingQueue<Runnable> taskQueue =
					threadPoolExecutor.getQueue();

				try {
					taskQueue.put(runnable);
				}
				catch (InterruptedException ie) {
					rejectedExecutionHandler.rejectedExecution(
						runnable, threadPoolExecutor);
				}
			}) {

			@Override
			protected void afterExecute(
				Runnable runnable, Throwable throwable) {

				threadPoolHandler.afterExecute(runnable, throwable);
			}

			@Override
			protected void beforeExecute(Thread thread, Runnable runnable) {
				threadPoolHandler.beforeExecute(thread, runnable);
			}

			@Override
			protected void terminated() {
				if (terminationCounter.decrementAndGet() == 0) {
					_terminationDefaultNoticeableFuture.run();
				}
			}

		};

		_dispatcherThreadPoolExecutor = new ThreadPoolExecutor(
			1, 1, keepAliveTime, timeUnit, blockingQueue,
			runnable -> {
				Thread thread = threadFactory.newThread(runnable);

				thread.setName(thread.getName() + "-dispatcher");

				return thread;
			},
			rejectedExecutionHandler) {

			@Override
			protected void terminated() {
				if (terminationCounter.decrementAndGet() == 0) {
					_terminationDefaultNoticeableFuture.run();
				}
			}

		};

		_dispatcherThreadPoolExecutor.allowCoreThreadTimeOut(true);
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit timeUnit)
		throws InterruptedException {

		long startTime = System.currentTimeMillis();

		if (!_dispatcherThreadPoolExecutor.awaitTermination(
				timeout, timeUnit)) {

			return false;
		}

		timeout -= timeUnit.convert(
			System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);

		return _workerThreadPoolExecutor.awaitTermination(timeout, timeUnit);
	}

	@Override
	public void execute(Runnable runnable) {
		if (runnable == null) {
			throw new NullPointerException("Runnable is null");
		}

		_dispatcherThreadPoolExecutor.execute(
			() -> _workerThreadPoolExecutor.execute(runnable));
	}

	public int getActiveCount() {
		return _workerThreadPoolExecutor.getActiveCount();
	}

	public long getCompletedTaskCount() {
		return _workerThreadPoolExecutor.getCompletedTaskCount();
	}

	public int getCorePoolSize() {
		return _workerThreadPoolExecutor.getCorePoolSize();
	}

	public int getLargestPoolSize() {
		return _workerThreadPoolExecutor.getLargestPoolSize();
	}

	public int getMaximumPoolSize() {
		return _workerThreadPoolExecutor.getMaximumPoolSize();
	}

	public int getPendingTaskCount() {
		BlockingQueue<Runnable> dispatcherBlockingQueue =
			_dispatcherThreadPoolExecutor.getQueue();

		return dispatcherBlockingQueue.size();
	}

	public int getPoolSize() {
		return _workerThreadPoolExecutor.getPoolSize();
	}

	@Override
	public boolean isShutdown() {
		return _dispatcherThreadPoolExecutor.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		if (_dispatcherThreadPoolExecutor.isTerminated() ||
			_workerThreadPoolExecutor.isTerminated()) {

			return true;
		}

		return false;
	}

	public void setCorePoolSize(int corePoolSize) {
		if (corePoolSize < 1) {
			throw new IllegalArgumentException(
				"To ensure FIFO, core pool size must be 1 or greater");
		}

		_workerThreadPoolExecutor.setCorePoolSize(corePoolSize);
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		_workerThreadPoolExecutor.setMaximumPoolSize(maximumPoolSize);
	}

	@Override
	public synchronized void shutdown() {

		// Dispatcher thread must be interruptted, in case it is blocked on task
		// queue putting.

		_shutdownTasks.addAll(_dispatcherThreadPoolExecutor.shutdownNow());

		_workerThreadPoolExecutor.shutdown();
	}

	@Override
	public synchronized List<Runnable> shutdownNow() {
		_shutdownTasks.addAll(_dispatcherThreadPoolExecutor.shutdownNow());

		_workerThreadPoolExecutor.shutdownNow();

		List<Runnable> shutdownTasks = new ArrayList<>(_shutdownTasks);

		_shutdownTasks.clear();

		return shutdownTasks;
	}

	@Override
	public NoticeableFuture<Void> terminationNoticeableFuture() {
		return _terminationDefaultNoticeableFuture;
	}

	private final ThreadPoolExecutor _dispatcherThreadPoolExecutor;
	private final List<Runnable> _shutdownTasks = new ArrayList<>();
	private final DefaultNoticeableFuture<Void>
		_terminationDefaultNoticeableFuture;
	private final ThreadPoolExecutor _workerThreadPoolExecutor;

}