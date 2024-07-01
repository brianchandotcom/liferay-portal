/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.util;

import com.liferay.notification.constants.NotificationRecipientConstants;
import com.liferay.notification.constants.NotificationRecipientSettingConstants;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
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

		Map<String, String> recipientTypes = new HashMap<>();

		ListUtil.isNotEmptyForEach(
			notificationRecipientSettings,
			notificationRecipientSetting -> {
				String name = notificationRecipientSetting.getName();

				if (name.equals(
						NotificationRecipientSettingConstants.NAME_BCC_TYPE) ||
					name.equals(
						NotificationRecipientSettingConstants.NAME_CC_TYPE) ||
					name.equals(
						NotificationRecipientSettingConstants.NAME_TO_TYPE)) {

					recipientTypes.put(
						name, notificationRecipientSetting.getValue());
				}
			});

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipientSettings) {

			Object value = notificationRecipientSetting.getValue();

			if (StringUtil.equals(
					notificationRecipientSetting.getName(),
					NotificationRecipientSettingConstants.
						NAME_SINGLE_RECIPIENT) ||
				StringUtil.equals(
					notificationRecipientSetting.getName(),
					NotificationRecipientSettingConstants.
						NAME_USE_PREFERRED_LOCALE_FOR_GUEST_USERS)) {

				value = GetterUtil.getBoolean(
					notificationRecipientSetting.getValue());
			}
			else if (Validator.isXml(notificationRecipientSetting.getValue())) {
				value = notificationRecipientSetting.getValueMap();
			}

			String name = notificationRecipientSetting.getName();

			if (!StringUtil.equals(
					recipientTypes.get(
						NotificationRecipientSettingConstants.
							getRecipientTypeName(name)),
					NotificationRecipientConstants.TYPE_ROLE)) {

				map.put(name, value);

				continue;
			}

			List<Map<String, String>> roles =
				(List<Map<String, String>>)map.computeIfAbsent(
					name, key -> new ArrayList<>());

			roles.add(
				HashMapBuilder.put(
					NotificationRecipientSettingConstants.NAME_ROLE_NAME,
					String.valueOf(value)
				).build());
		}

		return map;
	}

}