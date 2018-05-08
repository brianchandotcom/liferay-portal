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

import com.liferay.portal.background.task.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public class RemoveOnCompletionBackgroundTaskStatusMessageListener
	extends BaseMessageListener {

	public RemoveOnCompletionBackgroundTaskStatusMessageListener(
		BackgroundTaskManager backgroundTaskManager) {

		_backgroundTaskManager = backgroundTaskManager;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		long backgroundTaskId = (Long)message.get(
			BackgroundTaskConstants.BACKGROUND_TASK_ID);

		BackgroundTask backgroundTask =
			_backgroundTaskManager.fetchBackgroundTask(backgroundTaskId);

		if (backgroundTask == null) {
			return;
		}

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		boolean deleteOnCompetion = GetterUtil.getBoolean(
			taskContextMap.get(
				BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS));

		if (!deleteOnCompetion) {
			return;
		}

		int status = GetterUtil.getInteger(message.get("status"), -1);

		if (status == -1) {
			return;
		}

		if (status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Deleting background task " + backgroundTask.toString());
			}

			_backgroundTaskManager.deleteBackgroundTask(backgroundTaskId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoveOnCompletionBackgroundTaskStatusMessageListener.class);

	private final BackgroundTaskManager _backgroundTaskManager;

}