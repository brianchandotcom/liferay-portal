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
import com.liferay.portal.background.task.internal.BackgroundTaskImpl;
import com.liferay.portal.background.task.internal.lock.helper.BackgroundTaskLockHelper;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
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
public class BackgroundTaskGlobalStatusMessageListener
	extends BaseMessageListener {

	public BackgroundTaskGlobalStatusMessageListener(
		BackgroundTaskLocalService backgroundTaskLocalService,
		LockManager lockManager) {

		_backgroundTaskLocalService = backgroundTaskLocalService;

		_backgroundTaskLockHelper = new BackgroundTaskLockHelper(lockManager);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		int status = message.getInteger("status");

		if ((status == BackgroundTaskConstants.STATUS_CANCELLED) ||
			(status == BackgroundTaskConstants.STATUS_FAILED)) {

			_executeQueuedBackgroundTasks(message);
		}
		else if (status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			_executeQueuedBackgroundTasks(message);

			_deleteCompletedTask(message);
		}
		else if (status == BackgroundTaskConstants.STATUS_QUEUED) {
			long backgroundTaskId = message.getLong(
				BackgroundTaskConstants.BACKGROUND_TASK_ID);

			if (!_backgroundTaskLockHelper.isLockedBackgroundTask(
					new BackgroundTaskImpl(
						_backgroundTaskLocalService.fetchBackgroundTask(
							backgroundTaskId)))) {

				_executeQueuedBackgroundTasks(message);
			}
		}
	}

	private void _deleteCompletedTask(Message message) throws Exception {
		long backgroundTaskId = message.getLong(
			BackgroundTaskConstants.BACKGROUND_TASK_ID);

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(backgroundTaskId);

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

		if (_log.isInfoEnabled()) {
			_log.info("Deleting background task " + backgroundTask.toString());
		}

		_backgroundTaskLocalService.deleteBackgroundTask(backgroundTaskId);
	}

	private void _executeQueuedBackgroundTasks(Message message) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskGlobalStatusMessageListener.class);

	private final BackgroundTaskLocalService _backgroundTaskLocalService;
	private final BackgroundTaskLockHelper _backgroundTaskLockHelper;

}