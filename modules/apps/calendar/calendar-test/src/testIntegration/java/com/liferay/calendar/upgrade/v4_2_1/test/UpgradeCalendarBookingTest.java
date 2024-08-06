/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.upgrade.v4_2_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.test.util.CalendarBookingUpgradeProcessTestUtil;
import com.liferay.calendar.test.util.CalendarUpgradeTestUtil;
import com.liferay.calendar.test.util.UpgradeDatabaseTestHelper;
import com.liferay.calendar.util.JCalendarUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Calendar;

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
@RunWith(Arquillian.class)
public class UpgradeCalendarBookingTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_upgradeDatabaseTestHelper =
			CalendarUpgradeTestUtil.getUpgradeDatabaseTestHelper();
		_upgradeProcess = CalendarUpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			CalendarBookingUpgradeProcessTestUtil.getClassName("v4_2_1"));
	}

	@After
	public void tearDown() throws Exception {
		_upgradeDatabaseTestHelper.close();
	}

	@Test
	public void testUpgradeAllDayCalendarBookingStartAndEndTime()
		throws Exception {

		CalendarBooking calendarBooking =
			CalendarBookingUpgradeProcessTestUtil.createCalendarBooking(
				_userLocalService);

		Calendar expectedStartTimeJCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getStartTime());

		Calendar expectedEndTimeJCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getEndTime());

		_upgradeProcess.upgrade();

		EntityCacheUtil.clearCache();

		calendarBooking = _calendarBookingLocalService.getCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Calendar actualStartTimeJCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getStartTime());

		assertSameTime(expectedStartTimeJCalendar, actualStartTimeJCalendar);

		Calendar actualEndTimeJCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getEndTime());

		assertSameTime(expectedEndTimeJCalendar, actualEndTimeJCalendar);
	}

	protected void assertSameTime(
		Calendar expectedJCalendar, Calendar actualJCalendar) {

		Assert.assertNotNull(expectedJCalendar);
		Assert.assertNotNull(actualJCalendar);
		Assert.assertEquals(
			expectedJCalendar.get(Calendar.HOUR),
			actualJCalendar.get(Calendar.HOUR));
		Assert.assertEquals(
			expectedJCalendar.get(Calendar.MINUTE),
			actualJCalendar.get(Calendar.MINUTE));
	}

	@Inject
	private CalendarBookingLocalService _calendarBookingLocalService;

	private UpgradeDatabaseTestHelper _upgradeDatabaseTestHelper;
	private UpgradeProcess _upgradeProcess;

	@Inject(
		filter = "component.name=com.liferay.calendar.internal.upgrade.registry.CalendarServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private UserLocalService _userLocalService;

}