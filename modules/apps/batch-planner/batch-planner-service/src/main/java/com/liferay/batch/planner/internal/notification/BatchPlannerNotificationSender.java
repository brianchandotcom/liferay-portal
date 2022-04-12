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

package com.liferay.batch.planner.internal.notification;

import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;

/**
 * @author Igor Beslic
 */
public class BatchPlannerNotificationSender {

	public BatchPlannerNotificationSender(String taskType) {
		_taskType = taskType;
	}

	public JSONObject getNotificationEventJSONObject(
		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus,
		long batchPlannerPlanId, String className) {

		JSONObject notificationEventJSONObject =
			JSONFactoryUtil.createJSONObject();

		return notificationEventJSONObject.put(
			"batchPlannerPlanId", batchPlannerPlanId
		).put(
			"className", className
		).put(
			"status", batchEngineTaskExecuteStatus.name()
		).put(
			"taskType", _taskType
		);
	}

	public JSONObject getNotificationEventJSONObject(
		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus,
		long batchPlannerPlanId, String className, String fileName) {

		JSONObject notificationEventJSONObject = getNotificationEventJSONObject(
			batchEngineTaskExecuteStatus, batchPlannerPlanId, className);

		return notificationEventJSONObject.put("fileName", fileName);
	}

	public void sendUserNotificationEvents(
		long userId, String portletId, int notificationType,
		JSONObject jsonObject) {

		try {
			_userNotificationEventLocalService.sendUserNotificationEvents(
				userId, portletId, notificationType, jsonObject);
		}
		catch (Exception exception) {
			_log.error("Unable to send user notification", exception);
		}
	}

	public void setUserNotificationEventLocalService(
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchPlannerNotificationSender.class);

	private final String _taskType;
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}