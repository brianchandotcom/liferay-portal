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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-14986.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class ThreadPoolExecutor extends AbstractExecutorService {

	public ThreadPoolExecutor(int corePoolSize, int maxPoolSize) {
		this(
			corePoolSize, maxPoolSize, 60, TimeUnit.SECONDS, false,
			Integer.MAX_VALUE, new AbortPolicy(),
			Executors.defaultThreadFactory(), new ThreadPoolHandlerAdapter());
	}

	public ThreadPoolExecutor(
		int corePoolSize, int maxPoolSize, long keepAliveTime,
		TimeUnit timeUnit, boolean allowCoreThreadTimeout, int maxQueueSize) {

		this(
			corePoolSize, maxPoolSize, keepAliveTime, timeUnit,
			allowCoreThreadTimeout, maxQueueSize, new AbortPolicy(),
			Executors.defaultThreadFactory(), new ThreadPoolHandlerAdapter());
	}

	public ThreadPoolExecutor(
		int corePoolSize, int maxPoolSize, long keepAliveTime,
		TimeUnit timeUnit, boolean allowCoreThreadTimeout, int maxQueueSize,
		RejectedExecutionHandler rejectedExecutionHandler,
		ThreadFactory threadFactory, ThreadPoolHandler threadPoolHandler) {

		if ((corePoolSize < 0) || (maxPoolSize <= 0) ||
			(maxPoolSize < corePoolSize) || (keepAliveTime < 0) ||
			(maxQueueSize <= 0)) {

			throw new IllegalArgumentException();
		}

		if ((rejectedExecutionHandler == null) || (threadFactory == null) ||
			(threadPoolHandler == null)) {

			throw new NullPointerException();
		}

		_corePoolSize = corePoolSize;
		_maxPoolSize = maxPoolSize;
		_keepAliveTime = timeUnit.toNanos(keepAliveTime);
		_allowCoreThreadTimeout = allowCoreThreadTimeout;
		_rejectedExecutionHandler = rejectedExecutionHandler;
		_threadFactory = threadFactory;
		_threadPoolHandler = threadPoolHandler;
		_taskQueue = new TaskQueue<>(maxQueueSize);
		_workerTasks = new HashSet<>();
	}

	public void adjustPoolSize(int newCorePoolSize, int newMaxPoolSize) {
		if ((newCorePoolSize < 0) || (newMaxPoolSize <= 0) ||
			(newMaxPoolSize < newCorePoolSize)) {

			throw new IllegalArgumentException();
		}

		_mainLock.lock();

		try {
			int surplusCoreThreads = _corePoolSize - newCorePoolSize;
			int surplusMaxPoolSize = _maxPoolSize - newMaxPoolSize;

			_corePoolSize = newCorePoolSize;
			_maxPoolSize = newMaxPoolSize;

			if (((surplusCoreThreads > 0) && (_poolSize > _corePoolSize)) ||
				((surplusMaxPoolSize > 0) && (_poolSize > _maxPoolSize))) {

				int interruptCount = Math.max(
					surplusCoreThreads, surplusMaxPoolSize);

				for (WorkerTask workerTask : _workerTasks) {
					if (interruptCount > 0) {
						if (workerTask._interruptIfWaiting()) {
							interruptCount--;
						}
					}
					else {
						break;
					}
				}
			}
			else {
				Runnable runnable = null;

				while ((surplusCoreThreads++ < 0) &&
					   (_poolSize < _corePoolSize) &&
					   ((runnable = _taskQueue.poll()) != null)) {

					_addWorkerThread(runnable);
				}
			}
		}
		finally {
			_mainLock.unlock();
		}
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit timeUnit)
		throws InterruptedException {

		long nanos = timeUnit.toNanos(timeout);

		_mainLock.lock();

		try {
			while (true) {
				if (_runState == _TERMINATED) {
					return true;
				}

				if (nanos <= 0) {
					return false;
				}

				nanos = _terminationCondition.awaitNanos(nanos);
			}
		}
		finally {
			_mainLock.unlock();
		}
	}

	@Override
	public void execute(Runnable runnable) {
		if (runnable == null) {
			throw new NullPointerException();
		}

		boolean[] hasWaiterMarker = new boolean[1];

		if ((_runState == _RUNNING) &&
			_taskQueue.offer(runnable, hasWaiterMarker)) {

			if (_runState != _RUNNING) {
				if (_taskQueue.remove(runnable)) {
					_rejectedExecutionHandler.rejectedExecution(runnable, this);
				}

				return;
			}

			if (!hasWaiterMarker[0]) {
				_addWorkerThread();
			}

			return;
		}

		_rejectedExecutionHandler.rejectedExecution(runnable, this);
	}

	public int getActiveCount() {
		_mainLock.lock();

		try {
			int count = 0;

			for (WorkerTask workerTask : _workerTasks) {
				if (workerTask._isLocked()) {
					count++;
				}
			}

			return count;
		}
		finally {
			_mainLock.unlock();
		}
	}

	public long getCompletedTaskCount() {
		_mainLock.lock();

		try {
			long count = _completedTaskCount;

			for (WorkerTask workerTask : _workerTasks) {
				count += workerTask._localCompletedTaskCount;
			}

			return count;
		}
		finally {
			_mainLock.unlock();
		}
	}

	public int getCorePoolSize() {
		return _corePoolSize;
	}

	public long getKeepAliveTime(TimeUnit timeUnit) {
		return timeUnit.convert(_keepAliveTime, TimeUnit.NANOSECONDS);
	}

	public int getLargestPoolSize() {
		return _largestPoolSize;
	}

	public int getMaxPoolSize() {
		return _maxPoolSize;
	}

	public String getName() {
		return _name;
	}

	public int getPendingTaskCount() {
		return _taskQueue.size();
	}

	public int getPoolSize() {
		return _poolSize;
	}

	public RejectedExecutionHandler getRejectedExecutionHandler() {
		return _rejectedExecutionHandler;
	}

	public int getRemainingTaskQueueCapacity() {
		return _taskQueue.remainingCapacity();
	}

	public long getTaskCount() {
		_mainLock.lock();

		try {
			long count = _completedTaskCount;

			for (WorkerTask workerTask : _workerTasks) {
				count += workerTask._localCompletedTaskCount;

				if (workerTask._isLocked()) {
					count++;
				}
			}

			return count + _taskQueue.size();
		}
		finally {
			_mainLock.unlock();
		}
	}

	public ThreadFactory getThreadFactory() {
		return _threadFactory;
	}

	public ThreadPoolHandler getThreadPoolHandler() {
		return _threadPoolHandler;
	}

	public boolean isAllowCoreThreadTimeout() {
		return _allowCoreThreadTimeout;
	}

	@Override
	public boolean isShutdown() {
		if (_runState != _RUNNING) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isTerminated() {
		if (_runState == _TERMINATED) {
			return true;
		}

		return false;
	}

	public boolean isTerminating() {
		if (_runState == _STOP) {
			return true;
		}

		return false;
	}

	public void setAllowCoreThreadTimeout(boolean allowCoreThreadTimeout) {
		_allowCoreThreadTimeout = allowCoreThreadTimeout;
	}

	public void setKeepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
		if (keepAliveTime < 0) {
			throw new IllegalArgumentException();
		}

		_keepAliveTime = timeUnit.toNanos(keepAliveTime);
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRejectedExecutionHandler(
		RejectedExecutionHandler rejectedExecutionHandler) {

		if (rejectedExecutionHandler == null) {
			throw new NullPointerException();
		}

		_rejectedExecutionHandler = rejectedExecutionHandler;
	}

	public void setThreadFactory(ThreadFactory threadFactory) {
		if (threadFactory == null) {
			throw new NullPointerException();
		}

		_threadFactory = threadFactory;
	}

	public void setThreadPoolHandler(ThreadPoolHandler threadPoolHandler) {
		if (threadPoolHandler == null) {
			throw new NullPointerException();
		}

		_threadPoolHandler = threadPoolHandler;
	}

	@Override
	public void shutdown() {
		_mainLock.lock();

		try {
			int state = _runState;

			if (state < _SHUTDOWN) {
				_runState = _SHUTDOWN;
			}

			for (WorkerTask workerTask : _workerTasks) {
				workerTask._interruptIfWaiting();
			}

			_tryTerminate();
		}
		finally {
			_mainLock.unlock();
		}
	}

	@Override
	public List<Runnable> shutdownNow() {
		_mainLock.lock();

		try {
			int state = _runState;

			if (state < _STOP) {
				_runState = _STOP;
			}

			for (WorkerTask workerTask : _workerTasks) {
				workerTask._thread.interrupt();
			}

			List<Runnable> runnables = new ArrayList<>();

			_taskQueue.drainTo(runnables);

			_tryTerminate();

			return runnables;
		}
		finally {
			_mainLock.unlock();
		}
	}

	@Override
	public <T> NoticeableFuture<T> submit(Callable<T> callable) {
		if (callable == null) {
			throw new NullPointerException("Callable is null");
		}

		DefaultNoticeableFuture<T> defaultNoticeableFuture = newTaskFor(
			callable);

		execute(defaultNoticeableFuture);

		return defaultNoticeableFuture;
	}

	@Override
	public NoticeableFuture<?> submit(Runnable runnable) {
		return submit(runnable, null);
	}

	@Override
	public <T> NoticeableFuture<T> submit(Runnable runnable, T result) {
		if (runnable == null) {
			throw new NullPointerException("Runnable is null");
		}

		DefaultNoticeableFuture<T> defaultNoticeableFuture = newTaskFor(
			runnable, result);

		execute(defaultNoticeableFuture);

		return defaultNoticeableFuture;
	}

	public NoticeableFuture<Void> terminationNoticeableFuture() {
		return _terminationDefaultNoticeableFuture;
	}

	@Override
	protected void finalize() {
		shutdown();
	}

	protected ReentrantLock getMainLock() {
		return _mainLock;
	}

	protected TaskQueue<Runnable> getTaskQueue() {
		return _taskQueue;
	}

	protected Set<WorkerTask> getWorkerTasks() {
		return _workerTasks;
	}

	@Override
	protected <T> DefaultNoticeableFuture<T> newTaskFor(Callable<T> callable) {
		return new DefaultNoticeableFuture<>(callable);
	}

	@Override
	protected <T> DefaultNoticeableFuture<T> newTaskFor(
		Runnable runnable, T value) {

		return new DefaultNoticeableFuture<>(runnable, value);
	}

	private void _addWorkerThread() {
		int runState = _runState;
		int poolSize = _poolSize;

		if (((runState == _RUNNING) && (poolSize < _maxPoolSize)) ||
			((runState == _SHUTDOWN) && (poolSize == 0) &&
			 !_taskQueue.isEmpty())) {

			_mainLock.lock();

			try {
				runState = _runState;
				poolSize = _poolSize;

				if (((runState == _RUNNING) && (poolSize < _maxPoolSize)) ||
					((runState == _SHUTDOWN) && (poolSize == 0) &&
					 !_taskQueue.isEmpty())) {

					Runnable runnable = _taskQueue.poll();

					if (runnable != null) {
						_addWorkerThread(runnable);
					}
				}
			}
			finally {
				_mainLock.unlock();
			}
		}
	}

	private void _addWorkerThread(Runnable runnable) {
		WorkerTask workerTask = new WorkerTask(runnable);

		_workerTasks.add(workerTask);

		int poolSize = ++_poolSize;

		if (poolSize > _largestPoolSize) {
			_largestPoolSize = poolSize;
		}

		workerTask._startWork();
	}

	private Runnable _getTask(WorkerTask workerTask, boolean[] cleanUpMarker) {
		while (true) {
			try {
				int state = _runState;

				if (state >= _STOP) {
					return null;
				}

				Runnable runnable = null;

				if (state == _SHUTDOWN) {
					runnable = _taskQueue.poll();
				}
				else if ((_poolSize > _corePoolSize) ||
						 _allowCoreThreadTimeout) {

					runnable = _taskQueue.poll(
						_keepAliveTime, TimeUnit.NANOSECONDS);
				}
				else {
					runnable = _taskQueue.take();
				}

				if (runnable != null) {
					return runnable;
				}

				_mainLock.lock();

				try {
					if ((_runState >= _STOP) ||
						((_runState >= _SHUTDOWN) && _taskQueue.isEmpty()) ||
						(_allowCoreThreadTimeout &&
						 ((_poolSize > 1) || _taskQueue.isEmpty())) ||
						(!_allowCoreThreadTimeout &&
						 (_poolSize > _corePoolSize))) {

						_completedTaskCount +=
							workerTask._localCompletedTaskCount;

						_workerTasks.remove(workerTask);

						if (--_poolSize == 0) {
							_tryTerminate();
						}

						cleanUpMarker[0] = true;

						return null;
					}
				}
				finally {
					_mainLock.unlock();
				}
			}
			catch (InterruptedException ie) {
			}
		}
	}

	private void _tryTerminate() {
		if (_poolSize == 0) {
			int state = _runState;

			if ((state == _STOP) ||
				((state == _SHUTDOWN) && _taskQueue.isEmpty())) {

				_runState = _TERMINATED;

				_terminationCondition.signalAll();

				_threadPoolHandler.terminated();

				_terminationDefaultNoticeableFuture.run();

				return;
			}

			if (!_taskQueue.isEmpty()) {
				_addWorkerThread(_taskQueue.poll());
			}
		}
	}

	private static final int _RUNNING = 0;

	private static final int _SHUTDOWN = 1;

	private static final int _STOP = 2;

	private static final int _TERMINATED = 3;

	private volatile boolean _allowCoreThreadTimeout;
	private long _completedTaskCount;
	private volatile int _corePoolSize;
	private volatile long _keepAliveTime;
	private volatile int _largestPoolSize;
	private final ReentrantLock _mainLock = new ReentrantLock();
	private volatile int _maxPoolSize;
	private String _name;
	private volatile int _poolSize;
	private volatile RejectedExecutionHandler _rejectedExecutionHandler;
	private volatile int _runState;
	private final TaskQueue<Runnable> _taskQueue;
	private final Condition _terminationCondition = _mainLock.newCondition();

	private final DefaultNoticeableFuture<Void>
		_terminationDefaultNoticeableFuture =
			new DefaultNoticeableFuture<Void>() {

				@Override
				public boolean cancel(boolean mayInterruptIfRunning) {
					return false;
				}

			};

	private volatile ThreadFactory _threadFactory;
	private volatile ThreadPoolHandler _threadPoolHandler;
	private final Set<WorkerTask> _workerTasks;

	private class WorkerTask
		extends AbstractQueuedSynchronizer implements Runnable {

		public WorkerTask(Runnable runnable) {
			_runnable = runnable;
		}

		@Override
		public void run() {
			boolean[] cleanUpMarker = new boolean[1];

			try {
				Runnable runnable = _runnable;

				_runnable = null;

				do {
					if (runnable != null) {
						_runTask(runnable);

						runnable = null;
					}
				}
				while ((runnable = _getTask(this, cleanUpMarker)) != null);
			}
			finally {
				if (!cleanUpMarker[0]) {
					_mainLock.lock();

					try {
						_completedTaskCount += _localCompletedTaskCount;

						_workerTasks.remove(this);

						if (--_poolSize == 0) {
							_tryTerminate();
						}
					}
					finally {
						_mainLock.unlock();
					}
				}

				_threadPoolHandler.beforeThreadEnd(_thread);
			}
		}

		@Override
		protected boolean isHeldExclusively() {
			if (getState() == 1) {
				return true;
			}

			return false;
		}

		@Override
		protected boolean tryAcquire(int unused) {
			return compareAndSetState(0, 1);
		}

		@Override
		protected boolean tryRelease(int unused) {
			setState(0);

			return true;
		}

		private boolean _interruptIfWaiting() {
			if (!_thread.isInterrupted() && tryAcquire(1)) {
				try {
					_thread.interrupt();

					return true;
				}
				finally {
					_unlock();
				}
			}

			return false;
		}

		private boolean _isLocked() {
			return isHeldExclusively();
		}

		private void _lock() {
			acquire(1);
		}

		private void _runTask(Runnable task) {
			_lock();

			try {
				if ((_runState < _STOP) && Thread.interrupted() &&
					(_runState >= _STOP)) {

					_thread.interrupt();
				}

				Throwable throwable = null;

				_threadPoolHandler.beforeExecute(_thread, task);

				try {
					task.run();

					_localCompletedTaskCount++;
				}
				catch (RuntimeException re) {
					throwable = re;

					throw re;
				}
				finally {
					_threadPoolHandler.afterExecute(task, throwable);
				}
			}
			finally {
				_unlock();
			}
		}

		private void _startWork() {
			_thread = _threadFactory.newThread(this);

			_threadPoolHandler.beforeThreadStart(_thread);

			_thread.start();
		}

		private void _unlock() {
			release(1);
		}

		private volatile long _localCompletedTaskCount;
		private Runnable _runnable;
		private Thread _thread;

	}

}