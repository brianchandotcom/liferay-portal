/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.test.util;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.Calendar;

/**
 * @author Richard Jeremias
 */
public class CalendarBookingUpgradeProcessTestUtil {

	public static CalendarBooking createCalendarBooking(
			UserLocalService userLocalService)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(group.getGroupId());

		user.setTimeZoneId("Europe/Paris");

		user = userLocalService.updateUser(user);

		Calendar startTimeCalendar = CalendarFactoryUtil.getCalendar(
			2022, Calendar.JANUARY, 1, 0, 0);

		Calendar endTimeCalendar = CalendarFactoryUtil.getCalendar(
			2022, Calendar.JANUARY, 1, 23, 59);

		return CalendarBookingTestUtil.addAllDayCalendarBooking(
			user, CalendarTestUtil.addCalendar(group),
			startTimeCalendar.getTimeInMillis(),
			endTimeCalendar.getTimeInMillis(),
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId()));
	}

	public static String getClassName(String schemaVersion) {
		return "com.liferay.calendar.internal.upgrade." + schemaVersion +
			".CalendarBookingUpgradeProcess";
	}

}