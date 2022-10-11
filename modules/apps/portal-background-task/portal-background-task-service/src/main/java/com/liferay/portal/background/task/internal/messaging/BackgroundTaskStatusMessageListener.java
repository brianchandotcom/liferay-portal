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
import com.liferay.portal.background.task.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.background.task.internal.BackgroundTaskImpl;
import com.liferay.portal.background.task.internal.lock.helper.BackgroundTaskLockHelper;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskStatusMessageListener extends BaseMessageListener {

	public BackgroundTaskStatusMessageListener(
		BackgroundTaskLocalService backgroundTaskLocalService,
		BackgroundTaskStatusRegistry backgroundTaskStatusRegistry,
		LockManager lockManager) {

		_backgroundTaskLocalService = backgroundTaskLocalService;
		_backgroundTaskStatusRegistry = backgroundTaskStatusRegistry;

		_backgroundTaskLockHelper = new BackgroundTaskLockHelper(lockManager);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_translateBackgroundTaskStatusMessage(message);

		int status = message.getInteger("status");

		if ((status == BackgroundTaskConstants.STATUS_CANCELLED) ||
			(status == BackgroundTaskConstants.STATUS_FAILED)) {

			_executeBackgroundTasks(message);
		}
		else if (status == BackgroundTaskConstants.STATUS_QUEUED) {
			long backgroundTaskId = message.getLong(
				BackgroundTaskConstants.BACKGROUND_TASK_ID);

			if (!_backgroundTaskLockHelper.isLockedBackgroundTask(
					new BackgroundTaskImpl(
						_backgroundTaskLocalService.fetchBackgroundTask(
							backgroundTaskId)))) {

				_executeBackgroundTasks(message);
			}
		}
		else if (status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			_executeBackgroundTasks(message);

			_deleteBackgroundTask(message);
		}
	}

	private void _deleteBackgroundTask(Message message) throws Exception {
		long backgroundTaskId = message.getLong(
			BackgroundTaskConstants.BACKGROUND_TASK_ID);

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(backgroundTaskId);

		if (backgroundTask == null) {
			return;
		}

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		boolean deleteOnSuccess = GetterUtil.getBoolean(
			taskContextMap.get(
				BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS));

		if (!deleteOnSuccess) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Deleting background task " + backgroundTask.toString());
		}

		_backgroundTaskLocalService.deleteBackgroundTask(backgroundTaskId);
	}

	private void _executeBackgroundTasks(Message message) {
		String taskExecutorClassName = message.getString(
			"taskExecutorClassName");

		if (Validator.isNull(taskExecutorClassName)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Message " + message +
						" is missing the key \"taskExecutorClassName\"");
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Acquiring next queued background task for " +
					taskExecutorClassName);
		}

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchFirstBackgroundTask(
				taskExecutorClassName, BackgroundTaskConstants.STATUS_QUEUED);

		if (backgroundTask == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No additional queued background tasks for " +
						taskExecutorClassName);
			}

			return;
		}

		_backgroundTaskLocalService.resumeBackgroundTask(
			backgroundTask.getBackgroundTaskId());
	}

	private void _translateBackgroundTaskStatusMessage(Message message) {
		long backgroundTaskId = message.getLong(
			BackgroundTaskConstants.BACKGROUND_TASK_ID);

		BackgroundTaskStatus backgroundTaskStatus =
			_backgroundTaskStatusRegistry.getBackgroundTaskStatus(
				backgroundTaskId);

		if (backgroundTaskStatus == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to locate status for background task ",
						backgroundTaskId, " to process ", message));
			}

			return;
		}

		BackgroundTaskStatusMessageTranslator
			backgroundTaskStatusMessageTranslator =
				_backgroundTaskStatusRegistry.
					getBackgroundTaskStatusMessageTranslator(backgroundTaskId);

		if (backgroundTaskStatusMessageTranslator != null) {
			backgroundTaskStatusMessageTranslator.translate(
				backgroundTaskStatus, message);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskStatusMessageListener.class);

	private final BackgroundTaskLocalService _backgroundTaskLocalService;
	private final BackgroundTaskLockHelper _backgroundTaskLockHelper;
	private final BackgroundTaskStatusRegistry _backgroundTaskStatusRegistry;

}