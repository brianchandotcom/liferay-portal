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

package com.liferay.calendar.test.util;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.util.TimeZone;

import org.junit.Assert;

/**
 * @author Adam Brandizzi
 */
public class CalendarTestUtil {

	public static Calendar addCalendar(
			CalendarResource calendarResource, ServiceContext serviceContext)
		throws PortalException {

		return CalendarLocalServiceUtil.addCalendar(
			calendarResource.getUserId(), calendarResource.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			calendarResource.getTimeZoneId(), RandomTestUtil.randomInt(), false,
			false, false, serviceContext);
	}

	public static Calendar addCalendar(Group group) throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		return addCalendar(group, serviceContext);
	}

	public static Calendar addCalendar(
			Group group, ServiceContext serviceContext)
		throws PortalException {

		return addCalendar(group, null, serviceContext);
	}

	public static Calendar addCalendar(
			Group group, TimeZone timeZone, ServiceContext serviceContext)
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				group.getGroupId(), serviceContext);

		if (timeZone == null) {
			timeZone = TimeZoneUtil.getDefault();
		}

		Calendar calendar = CalendarLocalServiceUtil.addCalendar(
			group.getCreatorUserId(), group.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), timeZone.getID(),
			RandomTestUtil.randomInt(), false, false, false, serviceContext);

		return calendar;
	}

	public static Calendar addCalendar(User user) throws PortalException {
		return addCalendar(user, null, createServiceContext(user));
	}

	public static Calendar addCalendar(User user, ServiceContext serviceContext)
		throws PortalException {

		return addCalendar(user, null, serviceContext);
	}

	public static Calendar addCalendar(
			User user, TimeZone timeZone, ServiceContext serviceContext)
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		if (timeZone != null) {
			calendar.setTimeZoneId(timeZone.getID());

			CalendarLocalServiceUtil.updateCalendar(calendar);
		}

		return calendar;
	}

	public static Calendar addCalendarResourceCalendar(User user)
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.addCalendarResource(
				user.getUserId(), user.getGroupId(),
				ClassNameLocalServiceUtil.getClassNameId(
					CalendarResource.class),
				0, null, null, RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				createServiceContext(user));

		return calendarResource.getDefaultCalendar();
	}

	public static ServiceContext createServiceContext(User user) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	public static Calendar getDefaultCalendar(Group group)
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				group.getGroupId(),
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		return calendarResource.getDefaultCalendar();
	}

	public static Calendar getStagingCalendar(Group group, Calendar calendar)
		throws PortalException {

		if (group.hasStagingGroup()) {
			group = group.getStagingGroup();
		}

		Assert.assertTrue(group.isStaged());

		return CalendarLocalServiceUtil.fetchCalendarByUuidAndGroupId(
			calendar.getUuid(), group.getGroupId());
	}

}