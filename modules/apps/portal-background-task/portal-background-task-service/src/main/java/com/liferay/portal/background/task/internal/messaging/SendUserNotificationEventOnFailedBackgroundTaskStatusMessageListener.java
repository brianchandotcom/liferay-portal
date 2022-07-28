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

import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Dante Wang
 */
public class
	SendUserNotificationEventOnFailedBackgroundTaskStatusMessageListener
		extends BaseMessageListener {

	public SendUserNotificationEventOnFailedBackgroundTaskStatusMessageListener(
		BackgroundTaskLocalService backgroundTaskLocalService,
		RoleLocalService roleLocalService, UserLocalService userLocalService,
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_backgroundTaskLocalService = backgroundTaskLocalService;
		_roleLocalService = roleLocalService;
		_userLocalService = userLocalService;
		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	@Override
	protected void doReceive(Message message) throws PortalException {
		int status = GetterUtil.getInteger(message.get("status"), -1);

		if (status != BackgroundTaskConstants.STATUS_FAILED) {
			return;
		}

		long backgroundTaskId = (Long)message.get(
			BackgroundTaskConstants.BACKGROUND_TASK_ID);

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(backgroundTaskId);

		User user = _userLocalService.fetchUser(backgroundTask.getUserId());

		if ((user != null) && !user.isDefaultUser()) {
			_sendUserNotification(backgroundTask, user.getUserId());

			return;
		}

		Role role = _roleLocalService.fetchRole(
			backgroundTask.getCompanyId(), RoleConstants.ADMINISTRATOR);

		long[] userIds = _userLocalService.getRoleUserIds(role.getRoleId());

		for (long userId : userIds) {
			_sendUserNotification(backgroundTask, userId);
		}
	}

	private void _sendUserNotification(
			BackgroundTask backgroundTask, long userId)
		throws PortalException {

		_userNotificationEventLocalService.sendUserNotificationEvents(
			userId, "BackgroundTask",
			UserNotificationDeliveryConstants.TYPE_WEBSITE, false,
			JSONUtil.put(
				"name", backgroundTask.getName()
			).put(
				"taskExecutorClassName",
				backgroundTask.getTaskExecutorClassName()
			));
	}

	private final BackgroundTaskLocalService _backgroundTaskLocalService;
	private final RoleLocalService _roleLocalService;
	private final UserLocalService _userLocalService;
	private final UserNotificationEventLocalService
		_userNotificationEventLocalService;

}