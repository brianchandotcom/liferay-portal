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

package com.liferay.microblogs.web.internal.notifications;

import com.liferay.microblogs.constants.MicroblogsPortletKeys;
import com.liferay.microblogs.model.MicroblogsEntryConstants;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + MicroblogsPortletKeys.MICROBLOGS,
	service = UserNotificationDefinition.class
)
public class MicroblogsReplyToTaggedUserNotificationDefinition
	extends UserNotificationDefinition {

	public MicroblogsReplyToTaggedUserNotificationDefinition() {
		super(
			MicroblogsPortletKeys.MICROBLOGS, 0,
			MicroblogsEntryConstants.NOTIFICATION_TYPE_REPLY_TO_TAGGED,
			"receive-a-notification-when-someone-comments-on-a-microblog-you-" +
				"are-tagged-in");

		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"mobile", UserNotificationDeliveryConstants.TYPE_PUSH, true,
				true));
		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"website", UserNotificationDeliveryConstants.TYPE_WEBSITE, true,
				true));
	}

}