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

package com.liferay.notification.service.impl;

import com.liferay.notification.model.NotificationTemplateRecipientSetting;
import com.liferay.notification.service.base.NotificationTemplateRecipientSettingLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationTemplateRecipientSetting",
	service = AopService.class
)
public class NotificationTemplateRecipientSettingLocalServiceImpl
	extends NotificationTemplateRecipientSettingLocalServiceBaseImpl {

	public NotificationTemplateRecipientSetting
		getNotificationTemplateRecipientSetting(
			String name, long notificationTemplateRecipientId) {

		return null;
	}

	public List<NotificationTemplateRecipientSetting>
		getNotificationTemplateRecipientSettings(
			long notificationTemplateRecipientId) {

		return notificationTemplateRecipientSettingPersistence.
			findByNotificationTemplateRecipientId(
				notificationTemplateRecipientId);
	}

}