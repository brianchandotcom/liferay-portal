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
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;

import java.util.List;
import java.util.Map;

/**
 * @author Jonathan Lee
 */
public interface UserNotificationManager {

	public void addUserNotificationDefinition(
		String portletIf,
		UserNotificationDefinition userNotificationDefinition);

	public void addUserNotificationHandler(
		UserNotificationHandler userNotificationHandler);

	public void deleteUserNotificationDefinitions(String portletId);

	public void deleteUserNotificationHandler(
		UserNotificationHandler userNotificationHandler);

	public Map<String, Map<String, UserNotificationHandler>>
		getUserNotificationHandlers();

	public UserNotificationFeedEntry interpret(
			String selector, UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException;

}
