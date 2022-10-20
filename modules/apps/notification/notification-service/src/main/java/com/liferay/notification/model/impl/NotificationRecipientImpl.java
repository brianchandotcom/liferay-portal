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

import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.service.NotificationRecipientSettingLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class NotificationRecipientImpl extends NotificationRecipientBaseImpl {

	public List<NotificationRecipientSetting>
		getNotificationRecipientSettings() {

		return NotificationRecipientSettingLocalServiceUtil.
			getNotificationRecipientSettings(getNotificationRecipientId());
	}

	public Map<String, Object> getNotificationRecipientSettingsMap() {
		Map<String, Object> notificationRecipientSettingsMap = new HashMap<>();

		for (NotificationRecipientSetting notificationRecipientSetting :
				getNotificationRecipientSettings()) {

			Object value = notificationRecipientSetting.getValue();

			if (Validator.isXml(notificationRecipientSetting.getValue())) {
				value = notificationRecipientSetting.getValueMap();
			}

			notificationRecipientSettingsMap.put(
				notificationRecipientSetting.getName(), value);
		}

		return notificationRecipientSettingsMap;
	}

}