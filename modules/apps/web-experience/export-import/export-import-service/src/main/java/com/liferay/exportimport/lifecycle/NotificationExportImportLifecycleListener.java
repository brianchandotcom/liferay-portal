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

package com.liferay.exportimport.lifecycle;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.lifecycle.BaseProcessExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(immediate = true, service = {ExportImportLifecycleListener.class})
@ProviderType
public class NotificationExportImportLifecycleListener
	extends BaseProcessExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return false;
	}

	protected JSONObject getPayload(
		long backgroundTaskId, long exportImportConfigurationId, int status) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("backgroundTaskId", backgroundTaskId);
		jsonObject.put(
			"exportImportConfigurationId", exportImportConfigurationId);
		jsonObject.put("status", status);

		return jsonObject;
	}

	@Override
	protected void onProcessFailed(List<Serializable> attributes)
		throws Exception {

		sendNotification(BackgroundTaskConstants.STATUS_FAILED);
	}

	@Override
	protected void onProcessSucceeded(List<Serializable> attributes)
		throws Exception {

		sendNotification(BackgroundTaskConstants.STATUS_SUCCESSFUL);
	}

	protected void sendNotification(int status) throws PortalException {
		long backgroundTaskId = BackgroundTaskThreadLocal.getBackgroundTaskId();

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.getBackgroundTask(backgroundTaskId);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long exportImportConfigurationId = GetterUtil.getLong(
			taskContextMap.get("exportImportConfigurationId"));

		_userNotificationEventLocalService.sendUserNotificationEvents(
			backgroundTask.getUserId(), ExportImportPortletKeys.EXPORT_IMPORT,
			UserNotificationDeliveryConstants.TYPE_WEBSITE,
			getPayload(backgroundTaskId, exportImportConfigurationId, status));
	}

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}