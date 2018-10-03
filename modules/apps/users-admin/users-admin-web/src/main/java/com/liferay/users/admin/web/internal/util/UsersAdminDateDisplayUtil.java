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

package com.liferay.users.admin.web.internal.util;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;

import java.text.Format;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author Samuel Trong Tran
 */
public class UsersAdminDateDisplayUtil {

	public static String formatTimeInt(Locale locale, int timeInt) {
		if (timeInt < 0) {
			return "";
		}

		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);

		String timeString = String.valueOf(timeInt);

		if (timeString.length() == 4) {
			cal.set(
				Calendar.HOUR_OF_DAY,
				Integer.valueOf(timeString.substring(0, 2)));
			cal.set(Calendar.MINUTE, Integer.valueOf(timeString.substring(2)));
		}
		else if (timeString.length() == 3) {
			cal.set(
				Calendar.HOUR_OF_DAY, Integer.valueOf(timeString.charAt(0)));
			cal.set(Calendar.MINUTE, Integer.valueOf(timeString.substring(1)));
		}
		else {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
		}

		Format timeFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"HH:mm", locale);

		return timeFormat.format(cal.getTime());
	}

}