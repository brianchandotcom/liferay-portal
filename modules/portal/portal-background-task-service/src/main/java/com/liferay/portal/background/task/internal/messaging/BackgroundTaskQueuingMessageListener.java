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

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskLockHelperUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {"destination.name=" + DestinationNames.BACKGROUND_TASK_STATUS},
	service = MessageListener.class
)
public class BackgroundTaskQueuingMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		String taskExecutorClassName = (String)message.get(
			"taskExecutorClassName");

		if (Validator.isNull(taskExecutorClassName)) {
			return;
		}

		int status = (Integer)message.get("status");

		if ((status == BackgroundTaskConstants.STATUS_CANCELLED) ||
			(status == BackgroundTaskConstants.STATUS_FAILED) ||
			(status == BackgroundTaskConstants.STATUS_SUCCESSFUL)) {

			executeQueuedBackgroundTasks(taskExecutorClassName);
		}
		else if (status == BackgroundTaskConstants.STATUS_QUEUED) {
			long backgroundTaskId = (Long)message.get("backgroundTaskId");

			BackgroundTask backgroundTask =
				_backgroundTaskManager.fetchBackgroundTask(backgroundTaskId);

			if (!BackgroundTaskLockHelperUtil.isLockedBackgroundTask(
					backgroundTask)) {

				executeQueuedBackgroundTasks(taskExecutorClassName);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setBackgroundTaskManager(
		BackgroundTaskManager backgroundTaskManager) {

		_backgroundTaskManager = backgroundTaskManager;
	}

	@Reference(
		target = "(destination.name=" + DestinationNames.BACKGROUND_TASK_STATUS + ")",
		unbind = "-"
	)
	protected void setDestination(Destination destination) {
	}

	private void executeQueuedBackgroundTasks(String taskExecutorClassName) {
		BackgroundTask backgroundTask =
			_backgroundTaskManager.fetchFirstBackgroundTask(
				taskExecutorClassName, BackgroundTaskConstants.STATUS_QUEUED);

		if (backgroundTask == null) {
			return;
		}

		_backgroundTaskManager.resumeBackgroundTask(
			backgroundTask.getBackgroundTaskId());
	}

	private volatile BackgroundTaskManager _backgroundTaskManager;

}