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

package com.liferay.portal.kernel.messaging;

import com.liferay.petra.concurrent.NoticeableExecutorService;
import com.liferay.petra.concurrent.NoticeableThreadPoolExecutor;
import com.liferay.petra.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author     Michael C. Han
 * @author     Shuyang Zhou
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.portal.messaging.internal.BaseAsyncDestination}
 */
@Deprecated
public abstract class BaseAsyncDestination extends BaseDestination {

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		open();
	}

	@Override
	public void close(boolean force) {
		if ((_noticeableThreadPoolExecutor == null) ||
			_noticeableThreadPoolExecutor.isShutdown()) {

			return;
		}

		if (force) {
			_noticeableThreadPoolExecutor.shutdownNow();
		}
		else {
			_noticeableThreadPoolExecutor.shutdown();
		}
	}

	@Override
	public DestinationStatistics getDestinationStatistics() {
		DestinationStatistics destinationStatistics =
			new DestinationStatistics();

		destinationStatistics.setActiveThreadCount(
			_noticeableThreadPoolExecutor.getActiveCount());
		destinationStatistics.setCurrentThreadCount(
			_noticeableThreadPoolExecutor.getPoolSize());
		destinationStatistics.setLargestThreadCount(
			_noticeableThreadPoolExecutor.getLargestPoolSize());
		destinationStatistics.setMaxThreadPoolSize(
			_noticeableThreadPoolExecutor.getMaximumPoolSize());
		destinationStatistics.setMinThreadPoolSize(
			_noticeableThreadPoolExecutor.getCorePoolSize());
		destinationStatistics.setPendingMessageCount(
			_noticeableThreadPoolExecutor.getPendingTaskCount());
		destinationStatistics.setSentMessageCount(
			_noticeableThreadPoolExecutor.getCompletedTaskCount());

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
		if ((_noticeableThreadPoolExecutor != null) &&
			!_noticeableThreadPoolExecutor.isShutdown()) {

			return;
		}

		if (_rejectedExecutionHandler == null) {
			_rejectedExecutionHandler = _createRejectionExecutionHandler();
		}

		NoticeableThreadPoolExecutor noticeableThreadPoolExecutor =
			new NoticeableThreadPoolExecutor(
				_workersCoreSize, _workersMaxSize, 60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(_maximumQueueSize),
				new NamedThreadFactory(
					getName(), Thread.NORM_PRIORITY,
					PortalClassLoaderUtil.getClassLoader()),
				_rejectedExecutionHandler, new ThreadPoolHandlerAdapter());

		NoticeableExecutorService oldNoticeableExecutorService =
			_portalExecutorManager.registerPortalExecutor(
				getName(), noticeableThreadPoolExecutor);

		if (oldNoticeableExecutorService != null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Abort creating a new thread pool for destination " +
						getName() + " and reuse previous one");
			}

			noticeableThreadPoolExecutor.shutdownNow();

			noticeableThreadPoolExecutor =
				(NoticeableThreadPoolExecutor)oldNoticeableExecutorService;
		}

		_noticeableThreadPoolExecutor = noticeableThreadPoolExecutor;
	}

	@Override
	public void send(Message message) {
		if (messageListeners.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug("No message listeners for destination " + getName());
			}

			return;
		}

		NoticeableThreadPoolExecutor noticeableThreadPoolExecutor =
			_noticeableThreadPoolExecutor;

		if (noticeableThreadPoolExecutor.isShutdown()) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Destination ", getName(), " is shutdown and cannot ",
					"receive more messages"));
		}

		populateMessageFromThreadLocals(message);

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

	public void setRejectedExecutionHandler(
		RejectedExecutionHandler rejectedExecutionHandler) {

		_rejectedExecutionHandler = rejectedExecutionHandler;
	}

	public void setWorkersCoreSize(int workersCoreSize) {
		_workersCoreSize = workersCoreSize;

		if (_noticeableThreadPoolExecutor != null) {
			_noticeableThreadPoolExecutor.setCorePoolSize(_workersMaxSize);
		}
	}

	public void setWorkersMaxSize(int workersMaxSize) {
		_workersMaxSize = workersMaxSize;

		if (_noticeableThreadPoolExecutor != null) {
			_noticeableThreadPoolExecutor.setMaximumPoolSize(workersMaxSize);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected RejectedExecutionHandler createRejectionExecutionHandler() {
		return _createRejectionExecutionHandler();
	}

	protected abstract void dispatch(
		Set<MessageListener> messageListeners, Message message);

	protected void execute(Runnable runnable) {
		_noticeableThreadPoolExecutor.execute(runnable);
	}

	protected void populateMessageFromThreadLocals(Message message) {
		if (!message.contains("companyId")) {
			message.put("companyId", CompanyThreadLocal.getCompanyId());
		}

		if (!ClusterInvokeThreadLocal.isEnabled()) {
			message.put("clusterInvoke", Boolean.FALSE);
		}

		if (!message.contains("defaultLocale")) {
			message.put("defaultLocale", LocaleThreadLocal.getDefaultLocale());
		}

		if (!message.contains("groupId")) {
			message.put("groupId", GroupThreadLocal.getGroupId());
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

		if (!message.contains("siteDefaultLocale")) {
			message.put(
				"siteDefaultLocale", LocaleThreadLocal.getSiteDefaultLocale());
		}

		if (!message.contains("themeDisplayLocale")) {
			message.put(
				"themeDisplayLocale",
				LocaleThreadLocal.getThemeDisplayLocale());
		}
	}

	protected void populateThreadLocalsFromMessage(Message message) {
		long companyId = message.getLong("companyId");

		if (companyId > 0) {
			CompanyThreadLocal.setCompanyId(companyId);
		}

		Boolean clusterInvoke = (Boolean)message.get("clusterInvoke");

		if (clusterInvoke != null) {
			ClusterInvokeThreadLocal.setEnabled(clusterInvoke);
		}

		Locale defaultLocale = (Locale)message.get("defaultLocale");

		if (defaultLocale != null) {
			LocaleThreadLocal.setDefaultLocale(defaultLocale);
		}

		long groupId = message.getLong("groupId");

		if (groupId > 0) {
			GroupThreadLocal.setGroupId(groupId);
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
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		if (permissionChecker != null) {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}

		String principalPassword = message.getString("principalPassword");

		if (Validator.isNotNull(principalPassword)) {
			PrincipalThreadLocal.setPassword(principalPassword);
		}

		Locale siteDefaultLocale = (Locale)message.get("siteDefaultLocale");

		if (siteDefaultLocale != null) {
			LocaleThreadLocal.setSiteDefaultLocale(siteDefaultLocale);
		}

		Locale themeDisplayLocale = (Locale)message.get("themeDisplayLocale");

		if (themeDisplayLocale != null) {
			LocaleThreadLocal.setThemeDisplayLocale(themeDisplayLocale);
		}
	}

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

	private static volatile PortalExecutorManager _portalExecutorManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			PortalExecutorManager.class, BaseAsyncDestination.class,
			"_portalExecutorManager", true, false);

	private int _maximumQueueSize = Integer.MAX_VALUE;
	private NoticeableThreadPoolExecutor _noticeableThreadPoolExecutor;
	private RejectedExecutionHandler _rejectedExecutionHandler;
	private int _workersCoreSize = _WORKERS_CORE_SIZE;
	private int _workersMaxSize = _WORKERS_MAX_SIZE;

}