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

package com.liferay.portal.kernel.notification.bundle.usernotificationmanagerutil;

import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestUserNotificationHandler implements UserNotificationHandler {

	public static final String LINK = "http://www.liferay.com";

	public static final String PORTLET_ID = "PORTLET_ID";

	public static final String SELECTOR = "SELECTOR";

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public String getSelector() {
		return SELECTOR;
	}

	@Override
	public UserNotificationFeedEntry interpret(
		UserNotificationEvent userNotificationEvent,
		ServiceContext serviceContext) {

		boolean applicable = isApplicable(
			userNotificationEvent, serviceContext);

		return new UserNotificationFeedEntry(false, "body", LINK, applicable);
	}

	@Override
	public boolean isDeliver(
		long userId, long classNameId, int notificationType, int deliveryType,
		ServiceContext serviceContext) {

		if (userId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isOpenDialog() {
		return false;
	}

}