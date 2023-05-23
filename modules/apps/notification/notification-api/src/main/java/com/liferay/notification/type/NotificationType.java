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

package com.liferay.notification.type;

import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.List;

/**
 * @author Feliphe Marinho
 */
public interface NotificationType {

	public NotificationQueueEntry createNotificationQueueEntry(
		User user, String body, NotificationContext notificationContext,
		String subject);

	public List<NotificationRecipientSetting>
		createNotificationRecipientSettings(
			long notificationRecipientId, Object[] recipients, User user);

	public default String getFromName(
		NotificationQueueEntry notificationQueueEntry) {

		return "-";
	}

	public default String getRecipientSummary(
		NotificationQueueEntry notificationQueueEntry) {

		return "-";
	}

	public String getType();

	public String getTypeLanguageKey();

	public default void resendNotification(
			NotificationQueueEntry notificationQueueEntry)
		throws PortalException {
	}

	public default void resendNotifications(int status, String type)
		throws PortalException {
	}

	public void sendNotification(NotificationContext notificationContext)
		throws PortalException;

	public Object[] toRecipients(
		List<NotificationRecipientSetting> notificationRecipientSettings);

	public void validateNotificationQueueEntry(
			NotificationContext notificationContext)
		throws PortalException;

	public void validateNotificationTemplate(
			NotificationContext notificationContext)
		throws PortalException;

}