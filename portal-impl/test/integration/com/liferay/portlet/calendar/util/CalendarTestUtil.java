/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.util;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.CalEventConstants;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;

/**
 * @author Zsolt Berentey
 */
public class CalendarTestUtil {

	public static CalEvent addEvent(long groupId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		int startDateDay = 1;
		int startDateMonth = 1;
		int startDateYear = 2012;
		int startDateHour = 12;
		int startDateMinute = 0;
		int durationHour = 1;
		int durationMinute = 0;

		CalEvent calEvent = CalEventLocalServiceUtil.addEvent(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, durationHour, durationMinute, false, false,
			ServiceTestUtil.randomString(), false, null,
			CalEventConstants.REMIND_BY_NONE, 0, 0, serviceContext);

		return calEvent;
	}

}