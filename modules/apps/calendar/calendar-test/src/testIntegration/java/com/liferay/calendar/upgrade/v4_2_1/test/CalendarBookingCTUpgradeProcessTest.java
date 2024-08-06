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
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Calendar;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Richard Jeremias
 */
@RunWith(Arquillian.class)
public class CalendarBookingCTUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return CalendarBookingTestUtil.addAllDayCalendarBooking(
			_userLocalService, "Europe/Paris");
	}

	@Override
	protected CTService<?> getCTService() {
		return _calendarBookingLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = CalendarUpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			CalendarBookingTestUtil.getUpgradeStepClassName("v4_2_1"));

		upgradeProcess.upgrade();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) {
		_calendarBooking = (CalendarBooking)ctModel;

		Calendar startTimeCalendar = CalendarFactoryUtil.getCalendar(
			2022, Calendar.JANUARY, 1, 23, 0);

		_calendarBooking.setStartTime(startTimeCalendar.getTimeInMillis());

		Calendar endTimeCalendar = CalendarFactoryUtil.getCalendar(
			2022, Calendar.JANUARY, 2, 22, 59);

		_calendarBooking.setEndTime(endTimeCalendar.getTimeInMillis());

		_calendarBooking = _calendarBookingLocalService.updateCalendarBooking(
			_calendarBooking);

		return _calendarBooking;
	}

	@DeleteAfterTestRun
	private CalendarBooking _calendarBooking;

	@Inject
	private CalendarBookingLocalService _calendarBookingLocalService;

	@Inject(
		filter = "component.name=com.liferay.calendar.internal.upgrade.registry.CalendarServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private UserLocalService _userLocalService;

}