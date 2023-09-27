/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.notification;

import com.liferay.calendar.notification.NotificationRecipient;
import com.liferay.calendar.notification.NotificationSender;
import com.liferay.calendar.notification.NotificationTemplateContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Lundgren
 */
@Component(
	property = "notification.type=im", service = NotificationSender.class
)
public class IMNotificationSender implements NotificationSender {

	@Override
	public void sendNotification(
		String fromAddress, String fromName,
		NotificationRecipient notificationRecipient,
		NotificationTemplateContext notificationTemplateContext) {
	}

}