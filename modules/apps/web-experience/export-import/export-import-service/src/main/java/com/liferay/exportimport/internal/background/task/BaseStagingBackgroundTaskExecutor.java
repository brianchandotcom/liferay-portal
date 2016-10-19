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

package com.liferay.exportimport.internal.background.task;

import com.liferay.exportimport.kernel.lar.MissingReference;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public abstract class BaseStagingBackgroundTaskExecutor
	extends BaseExportImportBackgroundTaskExecutor {

	public BaseStagingBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new DefaultExportImportBackgroundTaskStatusMessageTranslator());

		// Isolation level guarantees this will be serial in a group

		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_GROUP);
	}

	protected void clearBackgroundTaskStatus(BackgroundTask backgroundTask) {
		BackgroundTaskStatus backgroundTaskStatus =
			BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(
				backgroundTask.getBackgroundTaskId());

		backgroundTaskStatus.clearAttributes();
	}

	protected void deleteTempLarOnFailure(File file) {
		if (PropsValues.STAGING_DELETE_TEMP_LAR_ON_FAILURE) {
			FileUtil.delete(file);
		}
		else if (file != null) {
			_log.error("Kept temporary LAR file " + file.getAbsolutePath());
		}
	}

	protected void deleteTempLarOnSuccess(File file) {
		if (PropsValues.STAGING_DELETE_TEMP_LAR_ON_SUCCESS) {
			FileUtil.delete(file);
		}
		else if ((file != null) && _log.isDebugEnabled()) {
			_log.debug("Kept temporary LAR file " + file.getAbsolutePath());
		}
	}

	protected void initThreadLocals(long groupId, boolean privateLayout)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.popServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		serviceContext.setCompanyId(layoutSet.getCompanyId());

		serviceContext.setSignedIn(false);

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			layoutSet.getCompanyId());

		serviceContext.setUserId(defaultUserId);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	protected void markBackgroundTask(
		long backgroundTaskId, String backgroundTaskState) {

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.fetchBackgroundTask(backgroundTaskId);

		if ((backgroundTask == null) || Validator.isNull(backgroundTaskState)) {
			return;
		}

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		if (taskContextMap == null) {
			taskContextMap = new HashMap<>();
		}

		taskContextMap.put(backgroundTaskState, Boolean.TRUE);

		backgroundTask.setTaskContextMap(taskContextMap);

		BackgroundTaskManagerUtil.amendBackgroundTask(
			backgroundTask.getBackgroundTaskId(), taskContextMap,
			backgroundTask.getStatus(), new ServiceContext());
	}

	protected BackgroundTaskResult processMissingReferences(
		long backgroundTaskId, MissingReferences missingReferences) {

		BackgroundTaskResult backgroundTaskResult = new BackgroundTaskResult(
			BackgroundTaskConstants.STATUS_SUCCESSFUL);

		if (missingReferences == null) {
			return backgroundTaskResult;
		}

		Map<String, MissingReference> weakMissingReferences =
			missingReferences.getWeakMissingReferences();

		if (MapUtil.isNotEmpty(weakMissingReferences)) {
			BackgroundTask backgroundTask =
				BackgroundTaskManagerUtil.fetchBackgroundTask(backgroundTaskId);

			JSONArray jsonArray = StagingUtil.getWarningMessagesJSONArray(
				getLocale(backgroundTask), weakMissingReferences);

			backgroundTaskResult.setStatusMessage(jsonArray.toString());
		}

		return backgroundTaskResult;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseStagingBackgroundTaskExecutor.class);

}