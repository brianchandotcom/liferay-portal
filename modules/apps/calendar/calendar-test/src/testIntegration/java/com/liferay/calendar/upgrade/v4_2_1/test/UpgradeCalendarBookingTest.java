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

package com.liferay.calendar.upgrade.v4_2_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.calendar.test.util.CalendarUpgradeTestUtil;
import com.liferay.calendar.test.util.UpgradeDatabaseTestHelper;
import com.liferay.calendar.util.JCalendarUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class UpgradeCalendarBookingTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		_calendar = CalendarTestUtil.addCalendar(_group);

		_upgradeProcess = CalendarUpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.calendar.internal.upgrade.v4_2_1." +
				"CalendarBookingUpgradeProcess");
		_upgradeDatabaseTestHelper =
			CalendarUpgradeTestUtil.getUpgradeDatabaseTestHelper();
	}

	@After
	public void tearDown() throws Exception {
		_upgradeDatabaseTestHelper.close();
	}

	@Test
	public void testUpgradeAllDayCalendarBookingStartAndEndTime()
		throws Exception {

		setUserTimeZoneId("Europe/Paris");

		java.util.Calendar expectedStartTimeJCalendar =
			CalendarFactoryUtil.getCalendar(
				2022, java.util.Calendar.JANUARY, 1, 0, 0);

		java.util.Calendar expectedEndTimeJCalendar =
			CalendarFactoryUtil.getCalendar(
				2022, java.util.Calendar.JANUARY, 1, 23, 59);

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addAllDayCalendarBooking(
				_user, _calendar, expectedStartTimeJCalendar.getTimeInMillis(),
				expectedEndTimeJCalendar.getTimeInMillis(), serviceContext);

		_upgradeProcess.upgrade();

		EntityCacheUtil.clearCache();

		calendarBooking = _calendarBookingLocalService.getCalendarBooking(
			calendarBooking.getCalendarBookingId());

		java.util.Calendar actualStartTimeJCalendar =
			JCalendarUtil.getJCalendar(calendarBooking.getStartTime());

		java.util.Calendar actualEndTimeJCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getEndTime());

		assertSameTime(expectedStartTimeJCalendar, actualStartTimeJCalendar);

		assertSameTime(expectedEndTimeJCalendar, actualEndTimeJCalendar);
	}

	protected void assertSameTime(
		java.util.Calendar expectedJCalendar,
		java.util.Calendar actualJCalendar) {

		Assert.assertNotNull(expectedJCalendar);
		Assert.assertNotNull(actualJCalendar);

		Assert.assertEquals(
			expectedJCalendar.get(java.util.Calendar.HOUR),
			actualJCalendar.get(java.util.Calendar.HOUR));

		Assert.assertEquals(
			expectedJCalendar.get(java.util.Calendar.MINUTE),
			actualJCalendar.get(java.util.Calendar.MINUTE));
	}

	protected ServiceContext createServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_user.getCompanyId());

		serviceContext.setUserId(_user.getUserId());

		return serviceContext;
	}

	protected void setUserTimeZoneId(String timeZoneId) {
		_user.setTimeZoneId(timeZoneId);

		_userLocalService.updateUser(_user);
	}

	private Calendar _calendar;

	@Inject
	private CalendarBookingLocalService _calendarBookingLocalService;

	private Group _group;
	private UpgradeDatabaseTestHelper _upgradeDatabaseTestHelper;
	private UpgradeProcess _upgradeProcess;

	@Inject(
		filter = "component.name=com.liferay.calendar.internal.upgrade.CalendarServiceUpgrade"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}