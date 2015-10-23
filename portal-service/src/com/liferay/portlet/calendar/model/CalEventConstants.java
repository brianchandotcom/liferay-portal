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

package com.liferay.portlet.calendar.model;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Time;

/**
 * @author Brian Wing Shun Chan
 */
public class CalEventConstants {

	public static final String BIRTHDAY = "birthday";

	public static final int END_DATE_TYPE_END_AFTER = 1;

	public static final int END_DATE_TYPE_END_BY = 2;

	public static final int END_DATE_TYPE_NONE = 0;

	public static final int REMIND_BY_EMAIL = 1;

	public static final int REMIND_BY_NONE = 0;

	public static final int REMIND_BY_SMS = 2;

	public static final long[] REMINDERS = {
		Time.MINUTE * 5, Time.MINUTE * 15, Time.MINUTE * 30, Time.HOUR,
		Time.HOUR * 2, Time.HOUR * 3, Time.HOUR * 6, Time.HOUR * 12, Time.DAY,
		Time.DAY * 2, Time.DAY * 3, Time.DAY * 4, Time.DAY * 5, Time.DAY * 6,
		Time.DAY * 7, Time.DAY * 8, Time.DAY * 9, Time.DAY * 10, Time.DAY * 11,
		Time.DAY * 12, Time.DAY * 13, Time.DAY * 14
	};

	public static final String[] TYPES = PropsUtil.getArray(
		PropsKeys.CALENDAR_EVENT_TYPES);

}