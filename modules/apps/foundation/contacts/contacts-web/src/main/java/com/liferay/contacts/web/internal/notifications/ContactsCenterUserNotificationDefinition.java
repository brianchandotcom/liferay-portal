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

package com.liferay.contacts.web.internal.notifications;

import com.liferay.contacts.constants.SocialRelationConstants;
import com.liferay.contacts.web.internal.constants.ContactsPortletKeys;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + ContactsPortletKeys.CONTACTS_CENTER},
	service = UserNotificationDefinition.class
)
public class ContactsCenterUserNotificationDefinition
	extends UserNotificationDefinition {

	public ContactsCenterUserNotificationDefinition() {
		super(
			ContactsPortletKeys.CONTACTS_CENTER, 0,
			SocialRelationConstants.SOCIAL_RELATION_REQUEST,
			"receive-a-notification-when-someone-sends-you-a-social-" +
				"relationship-request");

		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"website", UserNotificationDeliveryConstants.TYPE_WEBSITE, true,
				true));
	}

}