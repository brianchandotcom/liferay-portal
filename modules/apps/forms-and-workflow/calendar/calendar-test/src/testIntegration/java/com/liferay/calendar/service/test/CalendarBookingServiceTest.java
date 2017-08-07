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

package com.liferay.calendar.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.service.CalendarBookingServiceUtil;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lino Alves
 */
@RunWith(Arquillian.class)
@Sync
public class CalendarBookingServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user1 = UserTestUtil.addUser();
		_user2 = UserTestUtil.addUser();
		_adminUser = UserTestUtil.addOmniAdminUser();
	}

	@Test
	public void testGetUnapprovedCalendarBookingsForBookingManager()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = CalendarTestUtil.addCalendar(
			_user1, serviceContext);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(
				_user1, calendar, serviceContext);

		calendarBooking.setStatus(WorkflowConstants.STATUS_PENDING);

		CalendarBookingLocalServiceUtil.updateCalendarBooking(calendarBooking);

		int[] statuses = {WorkflowConstants.STATUS_PENDING};

		List<CalendarBooking> calendarBookings = Collections.emptyList();

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_adminUser)) {

			calendarBookings = CalendarBookingServiceUtil.getCalendarBookings(
				calendar.getCalendarId(), statuses);
		}

		Assert.assertTrue(!calendarBookings.isEmpty());
	}

	@Test
	public void testGetUnapprovedCalendarBookingsForRegularUser()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = CalendarTestUtil.addCalendar(
			_user1, serviceContext);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(
				_user1, calendar, serviceContext);

		calendarBooking.setStatus(WorkflowConstants.STATUS_PENDING);

		CalendarBookingLocalServiceUtil.updateCalendarBooking(calendarBooking);

		int[] statuses = {WorkflowConstants.STATUS_PENDING};

		List<CalendarBooking> calendarBookings = Collections.emptyList();

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_user1)) {

			calendarBookings = CalendarBookingServiceUtil.getCalendarBookings(
				calendar.getCalendarId(), statuses);
		}

		Assert.assertTrue(!calendarBookings.isEmpty());

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_user2)) {

			calendarBookings = CalendarBookingServiceUtil.getCalendarBookings(
				calendar.getCalendarId(), statuses);
		}

		Assert.assertTrue(calendarBookings.isEmpty());
	}

	protected ServiceContext createServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_user1.getCompanyId());

		serviceContext.setUserId(_user1.getUserId());

		return serviceContext;
	}

	@DeleteAfterTestRun
	private User _adminUser;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

}