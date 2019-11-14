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

package com.liferay.portal.messaging.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.DestinationStatistics;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageRunnable;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public abstract class BaseAsyncDestination extends BaseDestination {

	@Override
	public void close(boolean force) {
		if ((_threadPoolExecutor == null) || _threadPoolExecutor.isShutdown()) {
			return;
		}

		if (force) {
			_threadPoolExecutor.shutdownNow();
		}
		else {
			_threadPoolExecutor.shutdown();
		}
	}

	@Override
	public DestinationStatistics getDestinationStatistics() {
		DestinationStatistics destinationStatistics =
			new DestinationStatistics();

		destinationStatistics.setActiveThreadCount(
			_threadPoolExecutor.getActiveCount());
		destinationStatistics.setCurrentThreadCount(
			_threadPoolExecutor.getPoolSize());
		destinationStatistics.setLargestThreadCount(
			_threadPoolExecutor.getLargestPoolSize());
		destinationStatistics.setMaxThreadPoolSize(
			_threadPoolExecutor.getMaxPoolSize());
		destinationStatistics.setMinThreadPoolSize(
			_threadPoolExecutor.getCorePoolSize());
		destinationStatistics.setPendingMessageCount(
			_threadPoolExecutor.getPendingTaskCount());
		destinationStatistics.setSentMessageCount(
			_threadPoolExecutor.getCompletedTaskCount());

		return destinationStatistics;
	}

	public int getMaximumQueueSize() {
		return _maximumQueueSize;
	}

	public int getWorkersCoreSize() {
		return _workersCoreSize;
	}

	public int getWorkersMaxSize() {
		return _workersMaxSize;
	}

	@Override
	public void open() {
		if ((_threadPoolExecutor != null) &&
			!_threadPoolExecutor.isShutdown()) {

			return;
		}

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		if (_rejectedExecutionHandler == null) {
			_rejectedExecutionHandler = _createRejectionExecutionHandler();
		}

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			_workersCoreSize, _workersMaxSize, 60L, TimeUnit.SECONDS, false,
			_maximumQueueSize, _rejectedExecutionHandler,
			new NamedThreadFactory(
				getName(), Thread.NORM_PRIORITY, classLoader),
			new ThreadPoolHandlerAdapter());

		ThreadPoolExecutor oldThreadPoolExecutor =
			_portalExecutorManager.registerPortalExecutor(
				getName(), threadPoolExecutor);

		if (oldThreadPoolExecutor != null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Abort creating a new thread pool for destination " +
						getName() + " and reuse previous one");
			}

			threadPoolExecutor.shutdownNow();

			threadPoolExecutor = oldThreadPoolExecutor;
		}

		_threadPoolExecutor = threadPoolExecutor;
	}

	@Override
	public void send(Message message) {
		if (messageListeners.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug("No message listeners for destination " + getName());
			}

			return;
		}

		ThreadPoolExecutor threadPoolExecutor = _threadPoolExecutor;

		if (threadPoolExecutor.isShutdown()) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Destination ", getName(), " is shutdown and cannot ",
					"receive more messages"));
		}

		MessageBusThreadLocalHelper.populateMessageFromThreadLocals(message);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Sending message ", message, " from destination ",
					getName(), " to message listeners ", messageListeners));
		}

		dispatch(messageListeners, message);
	}

	public void setMaximumQueueSize(int maximumQueueSize) {
		_maximumQueueSize = maximumQueueSize;
	}

	public void setPermissionCheckerFactory(
		PermissionCheckerFactory permissionCheckerFactory) {

		this.permissionCheckerFactory = permissionCheckerFactory;
	}

	public void setPortalExecutorManager(
		PortalExecutorManager portalExecutorManager) {

		_portalExecutorManager = portalExecutorManager;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setRejectedExecutionHandler(
		RejectedExecutionHandler rejectedExecutionHandler) {

		_rejectedExecutionHandler = rejectedExecutionHandler;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public void setWorkersCoreSize(int workersCoreSize) {
		_workersCoreSize = workersCoreSize;

		if (_threadPoolExecutor != null) {
			_threadPoolExecutor.adjustPoolSize(
				workersCoreSize, _workersMaxSize);
		}
	}

	public void setWorkersMaxSize(int workersMaxSize) {
		_workersMaxSize = workersMaxSize;

		if (_threadPoolExecutor != null) {
			_threadPoolExecutor.adjustPoolSize(
				_workersCoreSize, workersMaxSize);
		}
	}

	protected abstract void dispatch(
		Set<MessageListener> messageListeners, Message message);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected ThreadPoolExecutor getThreadPoolExecutor() {
		return _threadPoolExecutor;
	}

	protected PermissionCheckerFactory permissionCheckerFactory;
	protected UserLocalService userLocalService;

	private RejectedExecutionHandler _createRejectionExecutionHandler() {
		return new RejectedExecutionHandler() {

			@Override
			public void rejectedExecution(
				Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

				if (!_log.isWarnEnabled()) {
					return;
				}

				MessageRunnable messageRunnable = (MessageRunnable)runnable;

				_log.warn(
					StringBundler.concat(
						"Discarding message ", messageRunnable.getMessage(),
						" because it exceeds the maximum queue size of ",
						_maximumQueueSize));
			}

		};
	}

	private static final int _WORKERS_CORE_SIZE = 2;

	private static final int _WORKERS_MAX_SIZE = 5;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAsyncDestination.class);

	private int _maximumQueueSize = Integer.MAX_VALUE;
	private PortalExecutorManager _portalExecutorManager;
	private RejectedExecutionHandler _rejectedExecutionHandler;
	private ThreadPoolExecutor _threadPoolExecutor;
	private int _workersCoreSize = _WORKERS_CORE_SIZE;
	private int _workersMaxSize = _WORKERS_MAX_SIZE;

}