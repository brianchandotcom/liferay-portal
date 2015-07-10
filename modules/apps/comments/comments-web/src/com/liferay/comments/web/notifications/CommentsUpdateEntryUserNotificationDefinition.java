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

package com.liferay.comments.web.notifications;

import com.liferay.comments.web.constants.CommentsPortletKeys;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;
import com.liferay.portal.model.UserNotificationDeliveryConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + CommentsPortletKeys.COMMENTS},
	service = UserNotificationDefinition.class
)
public class CommentsUpdateEntryUserNotificationDefinition
	extends UserNotificationDefinition {

	public CommentsUpdateEntryUserNotificationDefinition() {
		super(
			CommentsPortletKeys.COMMENTS, 0,
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			"receive-a-notification-when-someone-updates-a-comment-you-are-" +
				"subscribed-to");

		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"email", UserNotificationDeliveryConstants.TYPE_EMAIL, true,
				true));

		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"website", UserNotificationDeliveryConstants.TYPE_WEBSITE, true,
				true));
	}

}