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

package com.liferay.notification.model.impl;

import com.liferay.notification.model.NotificationTemplateRecipientSetting;
import com.liferay.notification.service.NotificationTemplateRecipientSettingLocalServiceUtil;

import java.util.List;

/**
 * @author Feliphe Marinho
 */
public class NotificationTemplateRecipientImpl
	extends NotificationTemplateRecipientBaseImpl {

	public List<NotificationTemplateRecipientSetting>
		getNotificationTemplateRecipientSettings() {

		if (_notificationTemplateRecipientSettings != null) {
			return _notificationTemplateRecipientSettings;
		}

		return NotificationTemplateRecipientSettingLocalServiceUtil.
			getNotificationTemplateRecipientSettings(
				getNotificationTemplateRecipientId());
	}

	public void setNotificationTemplateRecipientSettings(
		List<NotificationTemplateRecipientSetting>
			notificationTemplateRecipientSettings) {

		_notificationTemplateRecipientSettings =
			notificationTemplateRecipientSettings;
	}

	private List<NotificationTemplateRecipientSetting>
		_notificationTemplateRecipientSettings;

}