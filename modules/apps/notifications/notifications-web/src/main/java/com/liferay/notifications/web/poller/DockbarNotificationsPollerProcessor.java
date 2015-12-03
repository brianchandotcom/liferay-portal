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

package com.liferay.notifications.web.poller;

import com.liferay.notifications.web.constants.NotificationsPortletKeys;
import com.liferay.portal.kernel.poller.BasePollerProcessor;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.service.UserNotificationEventLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + NotificationsPortletKeys.DOCKBAR_NOTIFICATIONS
	},
	service = PollerProcessor.class
)
public class DockbarNotificationsPollerProcessor extends BasePollerProcessor {

	@Override
	protected PollerResponse doReceive(PollerRequest pollerRequest)
		throws Exception {

		return setUserNotificationsCount(pollerRequest);
	}

	@Override
	protected void doSend(PollerRequest pollerRequest) throws Exception {
	}

	@Reference(unbind = "-")
	protected void setUserNotificationEventLocalService(
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	protected PollerResponse setUserNotificationsCount(
			PollerRequest pollerRequest)
		throws Exception {

		PollerResponse pollerResponse = pollerRequest.createPollerResponse();

		pollerResponse.setParameter(
			"timestamp", String.valueOf(System.currentTimeMillis()));

		int newUserNotificationsCount =
			_userNotificationEventLocalService.
				getDeliveredUserNotificationEventsCount(
					pollerRequest.getUserId(),
					UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

		pollerResponse.setParameter(
			"newUserNotificationsCount",
			String.valueOf(newUserNotificationsCount));

		int unreadUserNotificationsCount =
			_userNotificationEventLocalService.
				getArchivedUserNotificationEventsCount(
					pollerRequest.getUserId(),
					UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

		pollerResponse.setParameter(
			"unreadUserNotificationsCount",
			String.valueOf(unreadUserNotificationsCount));

		return pollerResponse;
	}

	private volatile UserNotificationEventLocalService
		_userNotificationEventLocalService;

}