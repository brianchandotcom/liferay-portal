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

package com.liferay.portal.backgroundtask.messaging;

import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.backgroundtask.ClassLoaderAwareBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.SerialBackgroundTaskExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.ClassLoaderUtil;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long backgroundTaskId = (Long)message.get("backgroundTaskId");

		BackgroundTaskThreadLocal.setBackgroundTaskId(backgroundTaskId);

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.amendBackgroundTask(
				backgroundTaskId, null,
				BackgroundTaskConstants.STATUS_IN_PROGRESS, serviceContext);

		if (backgroundTask == null) {
			return;
		}

		BackgroundTaskExecutor backgroundTaskExecutor = null;
		BackgroundTaskStatusMessageListener
			backgroundTaskStatusMessageListener = null;

		int status = backgroundTask.getStatus();
		String statusMessage = null;

		try {
			ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

			String servletContextNames =
				backgroundTask.getServletContextNames();

			if (Validator.isNotNull(servletContextNames)) {
				classLoader = ClassLoaderUtil.getAggregatePluginsClassLoader(
					StringUtil.split(servletContextNames), false);
			}

			backgroundTaskExecutor =
				(BackgroundTaskExecutor)InstanceFactory.newInstance(
					classLoader, backgroundTask.getTaskExecutorClassName());

			backgroundTaskExecutor = wrapBackgroundTaskExecutor(
				backgroundTaskExecutor, classLoader);

			BackgroundTaskStatusRegistryUtil.registerBackgroundTaskStatus(
				backgroundTaskId);

			BackgroundTaskStatusMessageTranslator
				backgroundTaskStatusMessageTranslator =
					backgroundTaskExecutor.
						getBackgroundTaskStatusMessageTranslator();

			if (backgroundTaskStatusMessageTranslator != null) {
				backgroundTaskStatusMessageListener =
					new BackgroundTaskStatusMessageListener(
						backgroundTaskId,
						backgroundTaskStatusMessageTranslator);

				MessageBusUtil.registerMessageListener(
					DestinationNames.BACKGROUND_TASK_STATUS,
					backgroundTaskStatusMessageListener);
			}

			BackgroundTaskResult backgroundTaskResult =
				backgroundTaskExecutor.execute(backgroundTask);

			status = backgroundTaskResult.getStatus();
			statusMessage = backgroundTaskResult.getStatusMessage();
		}
		catch (DuplicateLockException e) {
			status = BackgroundTaskConstants.STATUS_QUEUED;
		}
		catch (Exception e) {
			status = BackgroundTaskConstants.STATUS_FAILED;

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
			BackgroundTaskLocalServiceUtil.amendBackgroundTask(
				backgroundTaskId, null, status, statusMessage, serviceContext);

			BackgroundTaskStatusRegistryUtil.unregisterBackgroundTaskStatus(
				backgroundTaskId);

			if (backgroundTaskStatusMessageListener != null) {
				MessageBusUtil.unregisterMessageListener(
					DestinationNames.BACKGROUND_TASK_STATUS,
					backgroundTaskStatusMessageListener);
			}

			Message responseMessage = new Message();

			responseMessage.put(
				"backgroundTaskId", backgroundTask.getBackgroundTaskId());
			responseMessage.put("name", backgroundTask.getName());
			responseMessage.put("status", status);
			responseMessage.put(
				"taskExecutorClassName",
				backgroundTask.getTaskExecutorClassName());

			MessageBusUtil.sendMessage(
				DestinationNames.BACKGROUND_TASK_STATUS, responseMessage);
		}
	}

	protected BackgroundTaskExecutor wrapBackgroundTaskExecutor(
		BackgroundTaskExecutor backgroundTaskExecutor,
		ClassLoader classLoader) {

		if (classLoader != ClassLoaderUtil.getPortalClassLoader()) {
			backgroundTaskExecutor =
				new ClassLoaderAwareBackgroundTaskExecutor(
					backgroundTaskExecutor, classLoader);
		}

		if (backgroundTaskExecutor.isSerial()) {
			backgroundTaskExecutor = new SerialBackgroundTaskExecutor(
				backgroundTaskExecutor);
		}

		return backgroundTaskExecutor;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BackgroundTaskMessageListener.class);

}