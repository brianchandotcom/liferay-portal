/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.test.util;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryLocalServiceUtil;
import com.liferay.notification.service.NotificationRecipientLocalServiceUtil;
import com.liferay.notification.service.NotificationRecipientSettingLocalServiceUtil;
import com.liferay.notification.service.NotificationTemplateLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Collections;
import java.util.List;

/**
 * @author Murilo Stodolni
 */
public class NotificationTemplateUtil {

	public static NotificationContext createNotificationContext(
			List<NotificationRecipientSetting> notificationRecipientSettings,
			String type)
		throws PortalException {

		return createNotificationContext(
			TestPropsValues.getUser(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), notificationRecipientSettings,
			RandomTestUtil.randomString(), type);
	}

	public static NotificationContext createNotificationContext(User user) {
		return createNotificationContext(
			user, null, NotificationConstants.TYPE_USER_NOTIFICATION);
	}

	public static NotificationContext createNotificationContext(
		User user, String description, String type) {

		return createNotificationContext(
			user, RandomTestUtil.randomString(), description,
			RandomTestUtil.randomString(), type);
	}

	public static NotificationContext createNotificationContext(
		User user, String body, String description,
		List<NotificationRecipientSetting> notificationRecipientSettings,
		String subject, String type) {

		NotificationContext notificationContext = new NotificationContext();

		notificationContext.setNotificationQueueEntry(
			createNotificationQueueEntry(user, body, subject, type));
		notificationContext.setNotificationRecipient(
			NotificationRecipientLocalServiceUtil.createNotificationRecipient(
				RandomTestUtil.randomInt()));
		notificationContext.setNotificationRecipientSettings(
			notificationRecipientSettings);
		notificationContext.setNotificationTemplate(
			createNotificationTemplate(
				user.getUserId(), body, description, subject, type));
		notificationContext.setType(type);

		return notificationContext;
	}

	public static NotificationContext createNotificationContext(
		User user, String body, String description, String subject,
		String type) {

		return createNotificationContext(
			user, body, description,
			Collections.singletonList(
				createNotificationRecipientSetting(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString())),
			subject, type);
	}

	public static NotificationQueueEntry createNotificationQueueEntry(
		User user, String body, String subject, String type) {

		NotificationQueueEntry notificationQueueEntry =
			NotificationQueueEntryLocalServiceUtil.createNotificationQueueEntry(
				RandomTestUtil.randomInt());

		notificationQueueEntry.setUserId(user.getUserId());
		notificationQueueEntry.setUserName(user.getFullName());
		notificationQueueEntry.setBody(body);
		notificationQueueEntry.setSubject(subject);
		notificationQueueEntry.setType(type);
		notificationQueueEntry.setStatus(
			NotificationQueueEntryConstants.STATUS_UNSENT);

		return notificationQueueEntry;
	}

	public static NotificationRecipientSetting
		createNotificationRecipientSetting(String name, String value) {

		NotificationRecipientSetting notificationRecipientSetting =
			NotificationRecipientSettingLocalServiceUtil.
				createNotificationRecipientSetting(RandomTestUtil.randomInt());

		notificationRecipientSetting.setName(name);
		notificationRecipientSetting.setValue(value);

		return notificationRecipientSetting;
	}

	public static NotificationTemplate createNotificationTemplate(
		long userId, String body, String description, String subject,
		String type) {

		NotificationTemplate notificationTemplate =
			NotificationTemplateLocalServiceUtil.createNotificationTemplate(
				RandomTestUtil.randomInt());

		notificationTemplate.setUserId(userId);
		notificationTemplate.setBody(body);
		notificationTemplate.setDescription(description);
		notificationTemplate.setEditorType(
			NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT);
		notificationTemplate.setName(RandomTestUtil.randomString());
		notificationTemplate.setSubject(subject);
		notificationTemplate.setType(type);

		return notificationTemplate;
	}

}