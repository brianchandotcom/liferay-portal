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
import com.liferay.portal.backgroundtask.executor.BackgroundTaskExecutor;
import com.liferay.portal.backgroundtask.executor.ClassLoaderAwareBackgroundTaskExecutor;
import com.liferay.portal.backgroundtask.executor.SerialBackgroundTaskExecutor;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.BackgroundTaskConstants;
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

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTaskLocalServiceUtil.updateBackgroundTask(
			backgroundTaskId, null, BackgroundTaskConstants.STATUS_IN_PROGRESS,
			serviceContext);

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.getBackgroundTask(backgroundTaskId);

		int status = backgroundTask.getStatus();

		try {
			ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

			String servletContextNames =
				backgroundTask.getServletContextNames();

			if (Validator.isNotNull(servletContextNames)) {
				classLoader = ClassLoaderUtil.getAggregatePluginsClassLoader(
					StringUtil.split(servletContextNames), false);
			}

			BackgroundTaskExecutor backgroundTaskExecutor =
				(BackgroundTaskExecutor)InstanceFactory.newInstance(
					classLoader, backgroundTask.getTaskExecutorClassName());

			backgroundTaskExecutor = wrapBackgroundTaskExecutor(
				backgroundTaskExecutor, classLoader);

			backgroundTaskExecutor.execute(backgroundTask);

			status = BackgroundTaskConstants.STATUS_SUCCESSFUL;
		}
		catch (DuplicateLockException e) {
			status = BackgroundTaskConstants.STATUS_QUEUED;
		}
		catch (Exception e) {
			status = BackgroundTaskConstants.STATUS_FAILED;
		}
		finally {
			BackgroundTaskLocalServiceUtil.updateBackgroundTask(
				backgroundTaskId, null, status, serviceContext);

			Message completionMessage = new Message();
			completionMessage.put(
				"backgroundTaskId", backgroundTask.getBackgroundTaskId());
			completionMessage.put("name", backgroundTask.getName());
			completionMessage.put("status", status);
			completionMessage.put(
				"taskExecutorClassName",
				backgroundTask.getTaskExecutorClassName());

			MessageBusUtil.sendMessage(
				DestinationNames.BACKGROUND_TASK_STATUS, completionMessage);
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

}