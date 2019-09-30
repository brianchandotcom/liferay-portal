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

package com.liferay.portal.background.task.internal.messaging;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.background.task.internal.SerialBackgroundTaskExecutor;
import com.liferay.portal.background.task.internal.ThreadLocalAwareBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutorRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocalManager;
import com.liferay.portal.kernel.backgroundtask.ClassLoaderAwareBackgroundTaskExecutor;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskMessageListener extends BaseMessageListener {

	public BackgroundTaskMessageListener(
		BackgroundTaskExecutorRegistry backgroundTaskExecutorRegistry,
		BackgroundTaskManager backgroundTaskManager,
		BackgroundTaskStatusRegistry backgroundTaskStatusRegistry,
		BackgroundTaskThreadLocalManager backgroundTaskThreadLocalManager,
		LockManager lockManager, MessageBus messageBus) {

		_backgroundTaskExecutorRegistry = backgroundTaskExecutorRegistry;
		_backgroundTaskManager = backgroundTaskManager;
		_backgroundTaskStatusRegistry = backgroundTaskStatusRegistry;
		_backgroundTaskThreadLocalManager = backgroundTaskThreadLocalManager;
		_lockManager = lockManager;
		_messageBus = messageBus;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		long backgroundTaskId = (Long)message.get(
			BackgroundTaskConstants.BACKGROUND_TASK_ID);

		BackgroundTaskThreadLocal.setBackgroundTaskId(backgroundTaskId);

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask backgroundTask =
			_backgroundTaskManager.amendBackgroundTask(
				backgroundTaskId, null,
				BackgroundTaskConstants.STATUS_IN_PROGRESS, serviceContext);

		if (backgroundTask == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to find background task " + backgroundTaskId);
			}

			return;
		}

		BackgroundTaskExecutor backgroundTaskExecutor = null;
		BackgroundTaskStatusMessageListener
			backgroundTaskStatusMessageListener = null;

		int status = backgroundTask.getStatus();
		String statusMessage = null;

		try {
			ClassLoader classLoader = getBackgroundTaskExecutorClassLoader(
				backgroundTask);

			backgroundTaskExecutor = wrapBackgroundTaskExecutor(
				backgroundTask, classLoader);

			_backgroundTaskStatusRegistry.registerBackgroundTaskStatus(
				backgroundTaskId);

			BackgroundTaskStatusMessageTranslator
				backgroundTaskStatusMessageTranslator =
					backgroundTaskExecutor.
						getBackgroundTaskStatusMessageTranslator();

			if (backgroundTaskStatusMessageTranslator != null) {
				backgroundTaskStatusMessageListener =
					new BackgroundTaskStatusMessageListener(
						backgroundTaskId, backgroundTaskStatusMessageTranslator,
						_backgroundTaskStatusRegistry);

				_messageBus.registerMessageListener(
					DestinationNames.BACKGROUND_TASK_STATUS,
					backgroundTaskStatusMessageListener);
			}

			backgroundTask = _backgroundTaskManager.fetchBackgroundTask(
				backgroundTask.getBackgroundTaskId());

			BackgroundTaskResult backgroundTaskResult =
				backgroundTaskExecutor.execute(backgroundTask);

			status = backgroundTaskResult.getStatus();
			statusMessage = backgroundTaskResult.getStatusMessage();
		}
		catch (DuplicateLockException dle) {
			status = BackgroundTaskConstants.STATUS_QUEUED;

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to acquire lock, queuing background task " +
						backgroundTaskId,
					dle);
			}
			else if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to acquire lock, queuing background task " +
						backgroundTaskId);
			}
		}
		catch (Exception e) {
			status = BackgroundTaskConstants.STATUS_FAILED;

			if (e instanceof SystemException) {
				Throwable cause = e.getCause();

				if (cause instanceof Exception) {
					e = (Exception)cause;
				}
			}

			if (backgroundTaskExecutor != null) {
				statusMessage = backgroundTaskExecutor.handleException(
					backgroundTask, e);
			}

			if (_log.isInfoEnabled()) {
				if (statusMessage != null) {
					statusMessage = statusMessage.concat(
						StackTraceUtil.getStackTrace(e));
				}
				else {
					statusMessage = StackTraceUtil.getStackTrace(e);
				}
			}

			_log.error("Unable to execute background task", e);
		}
		finally {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Completing background task ", backgroundTaskId,
						" with status: ", status));
			}

			_backgroundTaskManager.amendBackgroundTask(
				backgroundTaskId, null, status, statusMessage, serviceContext);

			_backgroundTaskStatusRegistry.unregisterBackgroundTaskStatus(
				backgroundTaskId);

			if (backgroundTaskStatusMessageListener != null) {
				_messageBus.unregisterMessageListener(
					DestinationNames.BACKGROUND_TASK_STATUS,
					backgroundTaskStatusMessageListener);
			}

			Message responseMessage = new Message();

			responseMessage.put(
				BackgroundTaskConstants.BACKGROUND_TASK_ID,
				backgroundTask.getBackgroundTaskId());
			responseMessage.put("name", backgroundTask.getName());
			responseMessage.put("status", status);
			responseMessage.put(
				"taskExecutorClassName",
				backgroundTask.getTaskExecutorClassName());

			_messageBus.sendMessage(
				DestinationNames.BACKGROUND_TASK_STATUS, responseMessage);
		}
	}

	protected BackgroundTaskExecutor getBackgroundTaskExecutor(
		BackgroundTask backgroundTask) {

		BackgroundTaskExecutor backgroundTaskExecutor = null;

		String servletContextNames = backgroundTask.getServletContextNames();

		if (Validator.isNull(servletContextNames)) {
			backgroundTaskExecutor =
				_backgroundTaskExecutorRegistry.getBackgroundTaskExecutor(
					backgroundTask.getTaskExecutorClassName());

			if (backgroundTaskExecutor == null) {
				throw new IllegalStateException(
					"Unknown background task executor " +
						backgroundTask.getTaskExecutorClassName());
			}

			backgroundTaskExecutor = backgroundTaskExecutor.clone();
		}
		else {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			if (Validator.isNotNull(servletContextNames)) {
				classLoader = _getAggregatePluginsClassLoader(
					servletContextNames);
			}

			try {
				backgroundTaskExecutor =
					(BackgroundTaskExecutor)InstanceFactory.newInstance(
						classLoader, backgroundTask.getTaskExecutorClassName());
			}
			catch (Exception e) {
				throw new IllegalStateException(
					"Cannot instantiate BackgroundTaskExecutor: " +
						backgroundTask.getTaskExecutorClassName(),
					e);
			}
		}

		return backgroundTaskExecutor;
	}

	protected ClassLoader getBackgroundTaskExecutorClassLoader(
		BackgroundTask backgroundTask) {

		if (Validator.isNull(backgroundTask.getServletContextNames())) {
			return null;
		}

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		String servletContextNames = backgroundTask.getServletContextNames();

		if (Validator.isNotNull(servletContextNames)) {
			classLoader = _getAggregatePluginsClassLoader(servletContextNames);
		}

		return classLoader;
	}

	protected BackgroundTaskExecutor wrapBackgroundTaskExecutor(
		BackgroundTask backgroundTask, ClassLoader classLoader) {

		BackgroundTaskExecutor backgroundTaskExecutor =
			getBackgroundTaskExecutor(backgroundTask);

		if (classLoader != null) {
			backgroundTaskExecutor = new ClassLoaderAwareBackgroundTaskExecutor(
				backgroundTaskExecutor, classLoader);
		}

		if (backgroundTaskExecutor.isSerial()) {
			backgroundTaskExecutor = new SerialBackgroundTaskExecutor(
				backgroundTaskExecutor, _lockManager);
		}

		backgroundTaskExecutor = new ThreadLocalAwareBackgroundTaskExecutor(
			backgroundTaskExecutor, _backgroundTaskThreadLocalManager);

		return backgroundTaskExecutor;
	}

	private ClassLoader _getAggregatePluginsClassLoader(
		String servletContextNamesString) {

		String[] servletContextNames = StringUtil.split(
			servletContextNamesString);

		List<ClassLoader> classLoaders = new ArrayList<>(
			servletContextNames.length);

		for (String servletContextName : servletContextNames) {
			ClassLoader classLoader =
				ServletContextClassLoaderPool.getClassLoader(
					servletContextName);

			if (classLoader == null) {
				_log.error(
					"Unable to find class loader for servlet context " +
						servletContextName);
			}
			else {
				classLoaders.add(classLoader);
			}
		}

		return AggregateClassLoader.getAggregateClassLoader(
			classLoaders.toArray(new ClassLoader[0]));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskMessageListener.class);

	private final BackgroundTaskExecutorRegistry
		_backgroundTaskExecutorRegistry;
	private final BackgroundTaskManager _backgroundTaskManager;
	private final BackgroundTaskStatusRegistry _backgroundTaskStatusRegistry;
	private final BackgroundTaskThreadLocalManager
		_backgroundTaskThreadLocalManager;
	private final LockManager _lockManager;
	private final MessageBus _messageBus;

}