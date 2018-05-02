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