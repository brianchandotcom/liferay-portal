/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.internal.notification;

import com.liferay.antivirus.async.store.constants.AntivirusAsyncPortletKeys;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alvaro Saugar
 */
@Component(
	property = "jakarta.portlet.name=" + AntivirusAsyncPortletKeys.ANTIVIRUS_ASYNC_NOTIFICATION,
	service = UserNotificationDefinition.class
)
public class AntivirusAsyncUserNotificationDefinition
	extends UserNotificationDefinition {

	public AntivirusAsyncUserNotificationDefinition() {
		super(
			AntivirusAsyncPortletKeys.ANTIVIRUS_ASYNC_NOTIFICATION, 0,
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			"async-antivirus-notification");

		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"email", UserNotificationDeliveryConstants.TYPE_EMAIL, false,
				false));
		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"website", UserNotificationDeliveryConstants.TYPE_WEBSITE, true,
				false));
	}

}