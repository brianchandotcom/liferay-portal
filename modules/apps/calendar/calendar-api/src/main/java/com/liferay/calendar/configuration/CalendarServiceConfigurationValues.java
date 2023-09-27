/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.configuration;

import com.liferay.calendar.notification.NotificationType;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Adam Brandizzi
 */
public class CalendarServiceConfigurationValues {

	public static final int CALENDAR_COLOR_DEFAULT = Integer.decode(
		CalendarServiceConfigurationUtil.get("calendar.color.default"));

	public static final int CALENDAR_NOTIFICATION_CHECK_INTERVAL =
		GetterUtil.getInteger(
			CalendarServiceConfigurationUtil.get(
				"calendar.notification.check.interval"));

	public static final NotificationType CALENDAR_NOTIFICATION_DEFAULT_TYPE =
		NotificationType.parse(
			CalendarServiceConfigurationUtil.get(
				"calendar.notification.default.type"));

	public static final boolean CALENDAR_RESOURCE_FORCE_AUTOGENERATE_CODE =
		GetterUtil.getBoolean(
			CalendarServiceConfigurationUtil.get(
				"calendar.resource.force.autogenerate.code"));

	public static final String CALENDAR_RSS_TEMPLATE = GetterUtil.getString(
		CalendarServiceConfigurationUtil.get("calendar.rss.template"));

}