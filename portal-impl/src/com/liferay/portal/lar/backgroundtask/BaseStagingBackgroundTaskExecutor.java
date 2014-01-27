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

package com.liferay.portal.lar.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.lar.MissingReference;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Mate Thurzo
 */
public abstract class BaseStagingBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public BaseStagingBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new DefaultExportImportBackgroundTaskStatusMessageTranslator());

		setSerial(true);
	}

	@Override
	public String handleException(BackgroundTask backgroundTask, Exception e) {
		JSONObject jsonObject = StagingUtil.getExceptionMessagesJSONObject(
			getLocale(backgroundTask), e, backgroundTask.getTaskContextMap());

		return jsonObject.toString();
	}

	protected void clearBackgroundTaskStatus(BackgroundTask backgroundTask) {
		BackgroundTaskStatus backgroundTaskStatus =
			BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(
				backgroundTask.getBackgroundTaskId());

		backgroundTaskStatus.clearAttributes();
	}

	protected void markBackgroundTask(
			long backgroundTaskId, String backgroundTaskState)
		throws SystemException {

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.fetchBackgroundTask(
				backgroundTaskId);

		if (backgroundTask == null) {
			return;
		}

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		if (Validator.isNull(backgroundTaskState)) {
			return;
		}

		taskContextMap.put(backgroundTaskState, Boolean.TRUE);

		backgroundTask.setTaskContext(
			JSONFactoryUtil.serialize(taskContextMap));

		BackgroundTaskLocalServiceUtil.updateBackgroundTask(backgroundTask);
	}

	protected BackgroundTaskResult processMissingReferences(
			long backgroundTaskId, MissingReferences missingReferences)
		throws SystemException {

		BackgroundTaskResult backgroundTaskResult = new BackgroundTaskResult(
			BackgroundTaskConstants.STATUS_SUCCESSFUL);

		Map<String, MissingReference> weakMissingReferences =
			missingReferences.getWeakMissingReferences();

		if ((weakMissingReferences != null) &&
			!weakMissingReferences.isEmpty()) {

			BackgroundTask backgroundTask =
				BackgroundTaskLocalServiceUtil.fetchBackgroundTask(
					backgroundTaskId);

			JSONArray jsonArray = StagingUtil.getWarningMessagesJSONArray(
				getLocale(backgroundTask), weakMissingReferences,
				backgroundTask.getTaskContextMap());

			backgroundTaskResult.setStatusMessage(jsonArray.toString());
		}

		return backgroundTaskResult;
	}

}