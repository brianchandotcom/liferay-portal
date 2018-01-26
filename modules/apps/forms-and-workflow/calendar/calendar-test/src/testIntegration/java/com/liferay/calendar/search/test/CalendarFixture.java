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

package com.liferay.calendar.search.test;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingConstants;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.test.ServiceTestUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class CalendarFixture {

	public CalendarFixture(
		List<Calendar> calendars, List<CalendarBooking> calendarBookings,
		List<Group> groups, List<User> users,
		CalendarLocalService calendarLocalService,
		CalendarBookingLocalService calendarBookingLocalService) {

		_calendars = calendars;
		_calendarBookings = calendarBookings;
		_groups = groups;
		_users = users;
		_calendarLocalService = calendarLocalService;
		_calendarBookingLocalService = calendarBookingLocalService;
	}

	public Calendar addCalendar(
			LocalizedValuesMap nameLocalizedValuesMap,
			LocalizedValuesMap descriptionLocalizedValuesMap,
			ServiceContext serviceContext)
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		Calendar calendar = _calendarLocalService.addCalendar(
			serviceContext.getUserId(), _group.getGroupId(),
			calendarResource.getCalendarResourceId(),
			nameLocalizedValuesMap.getValues(),
			descriptionLocalizedValuesMap.getValues(), StringPool.UTC,
			RandomTestUtil.randomInt(0, 255), false, false, false,
			serviceContext);

		_calendars.add(calendar);

		return calendar;
	}

	public CalendarBooking addCalendarBooking(
			LocalizedValuesMap titleLocalizedValuesMap, Calendar calendar,
			ServiceContext serviceContext)
		throws PortalException {

		long startTime = DateUtil.newTime() + RandomTestUtil.randomInt();

		long endTime = startTime + Time.HOUR;

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.addCalendarBooking(
				serviceContext.getUserId(), calendar.getCalendarId(),
				new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT, 0,
				titleLocalizedValuesMap.getValues(), Collections.emptyMap(),
				null, startTime, endTime, false, null, 0, "email", 0, "email",
				serviceContext);

		_calendarBookings.add(calendarBooking);

		return calendarBooking;
	}

	public Group addGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		return group;
	}

	public User addUser() throws Exception {
		User user = UserTestUtil.addUser();

		_users.add(user);

		return user;
	}

	public ServiceContext getServiceContext() throws PortalException {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), getUserId());
	}

	public void setGroup(Group group) {
		_group = group;
	}

	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());
	}

	public void setUser(User user) {
		_user = user;
	}

	protected long getUserId() throws PortalException {
		if (_user != null) {
			return _user.getUserId();
		}

		return TestPropsValues.getUserId();
	}

	private final CalendarBookingLocalService _calendarBookingLocalService;
	private final List<CalendarBooking> _calendarBookings;
	private final CalendarLocalService _calendarLocalService;
	private final List<Calendar> _calendars;
	private Group _group;
	private final List<Group> _groups;
	private User _user;
	private final List<User> _users;

}