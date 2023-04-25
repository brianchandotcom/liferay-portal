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

package com.liferay.notification.service.test.util;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryLocalServiceUtil;
import com.liferay.notification.service.NotificationRecipientLocalServiceUtil;
import com.liferay.notification.service.NotificationRecipientSettingLocalServiceUtil;
import com.liferay.notification.service.NotificationTemplateLocalServiceUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Arrays;

/**
 * @author Murilo Stodolni
 */
public class NotificationTemplateUtil {

	public static NotificationContext createNotificationContext(User user) {
		return createNotificationContext(
			user, RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString(), NotificationConstants.TYPE_EMAIL);
	}

	public static NotificationContext createNotificationContext(
		User user, String description) {

		return createNotificationContext(
			user, RandomTestUtil.randomString(), description,
			RandomTestUtil.randomString(), NotificationConstants.TYPE_EMAIL);
	}

	public static NotificationContext createNotificationContext(
		User user, String body, String description, String subject,
		String type) {

		NotificationContext notificationContext = new NotificationContext();

		NotificationTemplate notificationTemplate =
			NotificationTemplateLocalServiceUtil.createNotificationTemplate(
				RandomTestUtil.randomInt());

		notificationTemplate.setUserId(user.getUserId());
		notificationTemplate.setBody(body);
		notificationTemplate.setDescription(description);
		notificationTemplate.setEditorType(
			NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT);
		notificationTemplate.setName(RandomTestUtil.randomString());
		notificationTemplate.setSubject(subject);
		notificationTemplate.setType(type);

		notificationContext.setNotificationTemplate(notificationTemplate);

		NotificationQueueEntry notificationQueueEntry =
			NotificationQueueEntryLocalServiceUtil.createNotificationQueueEntry(
				RandomTestUtil.randomInt());

		notificationQueueEntry.setUserId(user.getUserId());
		notificationQueueEntry.setUserName(user.getFullName());
		notificationQueueEntry.setBody(notificationTemplate.getBody());
		notificationQueueEntry.setSubject(notificationTemplate.getSubject());
		notificationQueueEntry.setType(notificationTemplate.getType());
		notificationQueueEntry.setStatus(
			NotificationQueueEntryConstants.STATUS_UNSENT);

		notificationContext.setNotificationQueueEntry(notificationQueueEntry);

		NotificationRecipient notificationRecipient =
			NotificationRecipientLocalServiceUtil.createNotificationRecipient(
				RandomTestUtil.randomInt());

		notificationRecipient.setClassName(
			NotificationQueueEntry.class.getName());
		notificationRecipient.setClassPK(
			notificationQueueEntry.getNotificationQueueEntryId());

		notificationContext.setNotificationRecipient(notificationRecipient);

		NotificationRecipientSetting notificationRecipientSetting =
			NotificationRecipientSettingLocalServiceUtil.
				createNotificationRecipientSetting(RandomTestUtil.randomInt());

		notificationRecipientSetting.setNotificationRecipientId(
			notificationRecipient.getNotificationRecipientId());

		notificationContext.setNotificationRecipientSettings(
			Arrays.asList(notificationRecipientSetting));

		notificationContext.setType(
			NotificationConstants.TYPE_USER_NOTIFICATION);

		return notificationContext;
	}

}