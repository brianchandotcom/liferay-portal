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

package com.liferay.calendar.upgrade.v1_0_6.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingConstants;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.upgrade.v1_0_6.UpgradeCalendarBooking;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class UpgradeCalendarBookingTest extends UpgradeCalendarBooking {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testUpgradeCreatesCalendarBookingRecurringId()
		throws Exception {

		dropColumnRecurringCalendarBookingId();

		upgrade();

		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			connection = con;

			Assert.assertTrue(
				hasColumn("CalendarBooking", "recurringCalendarBookingId"));
		}
	}

	@Test
	public void testUpgradeSetsRecurringCalendarBookingId() throws Exception {
		CalendarBooking calendarBooking = addCalendarBooking();

		long recurringCalendarBookingId =
			calendarBooking.getRecurringCalendarBookingId();

		dropColumnRecurringCalendarBookingId();

		upgrade();

		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			connection = con;

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(
				"select recurringCalendarBookingId from CalendarBooking " +
					"where calendarBookingId = " +
						calendarBooking.getCalendarBookingId());

			rs.next();

			Assert.assertEquals(recurringCalendarBookingId, rs.getLong(1));
		}
	}

	protected Calendar addCalendar() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setCompanyId(_user.getCompanyId());

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		return calendarResource.getDefaultCalendar();
	}

	protected CalendarBooking addCalendarBooking() throws PortalException {
		Calendar calendar = addCalendar();

		long startTime = DateUtil.newTime();

		return CalendarBookingLocalServiceUtil.addCalendarBooking(
			_user.getUserId(), calendar.getCalendarId(), new long[0],
			CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
			CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime, startTime + Time.HOUR,
			false, null, 0, null, 0, null,
			ServiceContextTestUtil.getServiceContext());
	}

	protected void dropColumnRecurringCalendarBookingId() throws Exception {
		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			connection = con;

			if (hasColumn("CalendarBooking", "recurringCalendarBookingId")) {
				runSQL(
					"alter table CalendarBooking drop column " +
						"recurringCalendarBookingId");
			}
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}