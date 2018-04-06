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

package com.liferay.calendar.internal.notification;

import com.liferay.calendar.notification.NotificationRecipient;
import com.liferay.calendar.notification.NotificationSender;
import com.liferay.calendar.notification.NotificationTemplateContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Lundgren
 */
@Component(
	immediate = true, property = "notification.type=im",
	service = NotificationSender.class
)
public class IMNotificationSender implements NotificationSender {

	@Override
	public void sendNotification(
		String fromAddress, String fromName,
		NotificationRecipient notificationRecipient,
		NotificationTemplateContext notificationTemplateContext) {
	}

	@Override
	public void sendNotification(
		String fromAddress, String fromName,
		NotificationRecipient notificationRecipient, String subject,
		String notificationMessage) {
	}

}