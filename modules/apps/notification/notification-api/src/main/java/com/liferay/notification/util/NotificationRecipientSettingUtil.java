/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.util;

import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class NotificationRecipientSettingUtil {

	public static Map<String, Object> getNotificationRecipientSettingsMap(
		NotificationQueueEntry notificationQueueEntry) {

		NotificationRecipient notificationRecipient =
			notificationQueueEntry.getNotificationRecipient();

		return toMap(notificationRecipient.getNotificationRecipientSettings());
	}

	public static Map<String, Object> toMap(
		List<NotificationRecipientSetting> notificationRecipientSettings) {

		Map<String, Object> map = new HashMap<>();

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipientSettings) {

			Object value = notificationRecipientSetting.getValue();

			if (FeatureFlagManagerUtil.isEnabled("LPS-187854") &&
				StringUtil.equals(
					notificationRecipientSetting.getName(),
					"singleRecipient")) {

				value = GetterUtil.getBoolean(
					notificationRecipientSetting.getValue());
			}
			else if (Validator.isXml(notificationRecipientSetting.getValue())) {
				value = notificationRecipientSetting.getValueMap();
			}

			map.put(notificationRecipientSetting.getName(), value);
		}

		return map;
	}

}