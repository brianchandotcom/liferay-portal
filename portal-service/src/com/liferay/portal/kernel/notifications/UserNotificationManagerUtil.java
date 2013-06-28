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

package com.liferay.portal.kernel.notifications;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;

import java.util.List;
import java.util.Map;

/**
 * @author Jonathan Lee
 */
public class UserNotificationManagerUtil {

	public static void addUserNotificationDefinition(
		String portletId,
		UserNotificationDefinition userNotificationDefinition) {

		getUserNotificationManager().addUserNotificationDefinition(portletId,
			userNotificationDefinition);
	}

	public static void addUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		getUserNotificationManager().addUserNotificationHandler(
			userNotificationHandler);
	}

	public static void deleteUserNotificationDefinitions(String portletId) {
		getUserNotificationManager().deleteUserNotificationDefinitions(
			portletId);
	}

	public static void deleteUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		getUserNotificationManager().deleteUserNotificationHandler(
			userNotificationHandler);
	}

	public static Map<String, Map<String, UserNotificationHandler>>
		getUserNotificationHandlers() {

		return getUserNotificationManager().getUserNotificationHandlers();
	}

	public static UserNotificationFeedEntry interpret(
			String selector, UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		return getUserNotificationManager().interpret(
			selector, userNotificationEvent, serviceContext);
	}

	public static UserNotificationManager getUserNotificationManager() {
		PortalRuntimePermission.checkGetBeanProperty(
			UserNotificationManagerUtil.class);

		return _userNotificationManager;
	}

	public void setUserNotificationManager (
		UserNotificationManager userNotificationManager) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_userNotificationManager = userNotificationManager;
	}

	private static UserNotificationManager _userNotificationManager;

}
