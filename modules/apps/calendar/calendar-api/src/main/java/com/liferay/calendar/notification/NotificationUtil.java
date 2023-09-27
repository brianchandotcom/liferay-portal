/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.notification;

import com.liferay.calendar.configuration.CalendarServiceConfigurationKeys;
import com.liferay.calendar.configuration.CalendarServiceConfigurationUtil;
import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class NotificationUtil {

	public static String getDefaultTemplate(
			NotificationType notificationType,
			NotificationTemplateType notificationTemplateType,
			NotificationField notificationField)
		throws Exception {

		Filter filter = new Filter(
			notificationType.toString(), notificationTemplateType.toString());

		String propertyName =
			CalendarServiceConfigurationKeys.CALENDAR_NOTIFICATION_PREFIX +
				StringPool.PERIOD + notificationField.toString();

		String templatePath = CalendarServiceConfigurationUtil.get(
			propertyName, filter);

		return StringUtil.read(
			NotificationUtil.class.getClassLoader(), templatePath);
	}

	public static String getTemplatePropertyValue(
		CalendarNotificationTemplate calendarNotificationTemplate,
		String propertyName, String defaultValue) {

		if (calendarNotificationTemplate == null) {
			return defaultValue;
		}

		UnicodeProperties notificationTypeSettingsUnicodeProperties =
			calendarNotificationTemplate.
				getNotificationTypeSettingsProperties();

		return notificationTypeSettingsUnicodeProperties.get(propertyName);
	}

}