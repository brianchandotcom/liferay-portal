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

package com.liferay.notification.internal.type.test;

import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.NotificationRecipientLocalService;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * @author Feliphe Marinho
 */
public class BaseNotificationTypeTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		user = TestPropsValues.getUser();
	}

	protected NotificationRecipientSetting createNotificationRecipientSetting(
		String name, Object value) {

		NotificationRecipientSetting notificationRecipientSetting =
			notificationRecipientSettingLocalService.
				createNotificationRecipientSetting(0L);

		notificationRecipientSetting.setName(name);

		if (value instanceof String) {
			notificationRecipientSetting.setValue(String.valueOf(value));
		}
		else {
			notificationRecipientSetting.setValueMap(
				(Map<Locale, String>)value);
		}

		return notificationRecipientSetting;
	}

	protected void sendNotification(
			NotificationContext notificationContext, String type)
		throws PortalException {

		NotificationType notificationType =
			_notificationTypeServiceTracker.getNotificationType(type);

		if (notificationType == null) {
			Assert.fail("There is no notificationType with type " + type);
		}

		notificationType.sendNotification(notificationContext);
	}

	protected static User user;

	@Inject
	protected NotificationQueueEntryLocalService
		notificationQueueEntryLocalService;

	@Inject
	protected NotificationRecipientLocalService
		notificationRecipientLocalService;

	@Inject
	protected NotificationRecipientSettingLocalService
		notificationRecipientSettingLocalService;

	@Inject
	protected NotificationTemplateLocalService notificationTemplateLocalService;

	@Inject
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

}