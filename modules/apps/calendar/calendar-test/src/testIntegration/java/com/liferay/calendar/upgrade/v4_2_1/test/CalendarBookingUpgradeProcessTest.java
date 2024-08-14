/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.upgrade.v4_2_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarUpgradeTestUtil;
import com.liferay.calendar.test.util.UpgradeDatabaseTestHelper;
import com.liferay.calendar.util.JCalendarUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
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
public class CalendarBookingUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_upgradeDatabaseTestHelper =
			CalendarUpgradeTestUtil.getUpgradeDatabaseTestHelper();
	}

	@After
	public void tearDown() throws Exception {
		_upgradeDatabaseTestHelper.close();
	}

	@Test
	public void testUpgrade() throws Exception {
		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addAllDayCalendarBooking("Europe/Paris");

		Calendar expectedStartTimeJCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getStartTime());
		Calendar expectedEndTimeJCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getEndTime());

		UpgradeProcess upgradeProcess = CalendarUpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.calendar.internal.upgrade.v4_2_1." +
				"CalendarBookingUpgradeProcess");

		upgradeProcess.upgrade();

		EntityCacheUtil.clearCache();

		calendarBooking = _calendarBookingLocalService.getCalendarBooking(
			calendarBooking.getCalendarBookingId());

		_assertJCalendar(
			expectedStartTimeJCalendar,
			JCalendarUtil.getJCalendar(calendarBooking.getStartTime()));
		_assertJCalendar(
			expectedEndTimeJCalendar,
			JCalendarUtil.getJCalendar(calendarBooking.getEndTime()));
	}

	private void _assertJCalendar(
		Calendar expectedJCalendar, Calendar actualJCalendar) {

		Assert.assertEquals(
			expectedJCalendar.get(Calendar.DATE),
			actualJCalendar.get(Calendar.DATE));
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

	@Inject(
		filter = "component.name=com.liferay.calendar.internal.upgrade.registry.CalendarServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}