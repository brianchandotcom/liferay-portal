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

import com.liferay.portal.backgroundtask.executor.BackgroundTaskExecutor;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.BackgroundTaskConstants;
import com.liferay.portal.service.BackgroundTaskLocalService;
import com.liferay.portal.service.LockLocalServiceUtil;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskQueuingMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		String taskExecutorClassName = (String)message.get(
			"taskExecutorClassName");

		if (Validator.isNull(taskExecutorClassName)) {
			return;
		}

		int status = (Integer)message.get("status");

		if ((status == BackgroundTaskConstants.STATUS_FAILED) ||
			(status == BackgroundTaskConstants.STATUS_NEW)) {

			executeQueuedBackgroundTasks(taskExecutorClassName);
		}
		else if (status == BackgroundTaskConstants.STATUS_QUEUED) {
			boolean locked = LockLocalServiceUtil.isLocked(
				BackgroundTaskExecutor.class.getName(), taskExecutorClassName);

			if (!locked) {
				executeQueuedBackgroundTasks(taskExecutorClassName);
			}
		}
	}

	private void executeQueuedBackgroundTasks(String taskExecutorClassName)
		throws SystemException {

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchFirstBackgroundTask(
				taskExecutorClassName, BackgroundTaskConstants.STATUS_QUEUED);

		if (backgroundTask != null) {
			return;
		}

		_backgroundTaskLocalService.resumeBackgroundTask(
			backgroundTask.getBackgroundTaskId());
	}

	@BeanReference(type = BackgroundTaskLocalService.class)
	private BackgroundTaskLocalService _backgroundTaskLocalService;

}