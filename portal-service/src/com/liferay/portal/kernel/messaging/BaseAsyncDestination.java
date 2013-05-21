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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.proxy.MessageValuesThreadLocal;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public abstract class BaseAsyncDestination extends BaseDestination {

	public BaseAsyncDestination() {
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public BaseAsyncDestination(String name) {
		this(name, _WORKERS_CORE_SIZE, _WORKERS_MAX_SIZE);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public BaseAsyncDestination(
		String name, int workersCoreSize, int workersMaxSize) {

		this.name = name;
		_workersCoreSize = workersCoreSize;
		_workersMaxSize = workersMaxSize;

		open();
	}

	@Override
	public void close(boolean force) {
		PortalExecutorManagerUtil.shutdown(getName(), force);
	}

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
			_rejectedExecutionHandler = createRejectionExecutionHandler();
		}

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			_workersCoreSize, _workersMaxSize, 60L, TimeUnit.SECONDS, false,
			_maximumQueueSize, _rejectedExecutionHandler,
			new NamedThreadFactory(
				getName(), Thread.NORM_PRIORITY, classLoader),
			new ThreadPoolHandlerAdapter());

		ThreadPoolExecutor oldThreadPoolExecutor =
			PortalExecutorManagerUtil.registerPortalExecutor(
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

	public void send(Message message) {
		if (messageListeners.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug("No message listeners for destination " + getName());
			}

			return;
		}

		ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

		if (threadPoolExecutor.isShutdown()) {
			throw new IllegalStateException(
				"Destination " + getName() + " is shutdown and cannot " +
					"receive more messages");
		}

		populateMessageFromThreadLocals(message);

		dispatch(messageListeners, message);
	}

	public void setMaximumQueueSize(int maximumQueueSize) {
		_maximumQueueSize = maximumQueueSize;
	}

	public void setRejectedExecutionHandler(
		RejectedExecutionHandler rejectedExecutionHandler) {

		_rejectedExecutionHandler = rejectedExecutionHandler;
	}

	public void setWorkersCoreSize(int workersCoreSize) {
		_workersCoreSize = workersCoreSize;
	}

	public void setWorkersMaxSize(int workersMaxSize) {
		_workersMaxSize = workersMaxSize;
	}

	protected RejectedExecutionHandler createRejectionExecutionHandler() {
		return new RejectedExecutionHandler() {

			public void rejectedExecution(
				Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

				if (!_log.isWarnEnabled()) {
					return;
				}

				MessageRunnable messageRunnable = (MessageRunnable)runnable;

				_log.warn(
					"Discarding message " + messageRunnable.getMessage() +
						" because it exceeds the maximum queue size of " +
							_maximumQueueSize);
			}

		};
	}

	protected abstract void dispatch(
		Set<MessageListener> messageListeners, Message message);

	protected ThreadPoolExecutor getThreadPoolExecutor() {
		return _threadPoolExecutor;
	}

	protected void populateMessageFromThreadLocals(Message message) {
		if (!message.contains("companyId")) {
			message.put("companyId", CompanyThreadLocal.getCompanyId());
		}

		if (!message.contains("permissionChecker")) {
			message.put(
				"permissionChecker",
				PermissionThreadLocal.getPermissionChecker());
		}

		if (!message.contains("principalName")) {
			message.put("principalName", PrincipalThreadLocal.getName());
		}

		if (!message.contains("principalPassword")) {
			message.put(
				"principalPassword", PrincipalThreadLocal.getPassword());
		}
	}

	protected void populateThreadLocalsFromMessage(Message message) {
		long companyId = message.getLong("companyId");

		if (companyId > 0) {
			CompanyThreadLocal.setCompanyId(companyId);
		}

		PermissionChecker permissionChecker = (PermissionChecker)message.get(
			"permissionChecker");

		String principalName = message.getString("principalName");

		if (Validator.isNotNull(principalName)) {
			PrincipalThreadLocal.setName(principalName);
		}

		if ((permissionChecker == null) && Validator.isNotNull(principalName)) {
			try {
				User user = UserLocalServiceUtil.fetchUser(
					PrincipalThreadLocal.getUserId());

				permissionChecker = PermissionCheckerFactoryUtil.create(user);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		if (permissionChecker != null) {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}

		String principalPassword = message.getString("principalPassword");

		if (Validator.isNotNull(principalPassword)) {
			PrincipalThreadLocal.setPassword(principalPassword);
		}

		Boolean clusterForwardMessage = (Boolean)message.get(
			ClusterLink.CLUSTER_FORWARD_MESSAGE);

		if (clusterForwardMessage != null) {
			MessageValuesThreadLocal.setValue(
				ClusterLink.CLUSTER_FORWARD_MESSAGE, clusterForwardMessage);
		}
	}

	private static final int _WORKERS_CORE_SIZE = 2;

	private static final int _WORKERS_MAX_SIZE = 5;

	private static Log _log = LogFactoryUtil.getLog(BaseAsyncDestination.class);

	private int _maximumQueueSize = Integer.MAX_VALUE;
	private RejectedExecutionHandler _rejectedExecutionHandler;
	private ThreadPoolExecutor _threadPoolExecutor;
	private int _workersCoreSize = _WORKERS_CORE_SIZE;
	private int _workersMaxSize = _WORKERS_MAX_SIZE;

}