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

package com.liferay.calendar.upgrade.v2_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.calendar.test.util.CalendarUpgradeTestUtil;
import com.liferay.calendar.test.util.CheckBookingsMessageListenerTestUtil;
import com.liferay.calendar.test.util.UpgradeDatabaseTestHelper;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import org.junit.After;
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
public class UpgradeSchemaTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_calendar = CalendarTestUtil.addCalendar(_group);

		_upgradeDatabaseTestHelper =
			CalendarUpgradeTestUtil.getUpgradeDatabaseTestHelper();
		_upgradeProcess = CalendarUpgradeTestUtil.getServiceUpgradeStep(
			"com.liferay.calendar.internal.upgrade.v2_0_0.UpgradeSchema");

		CheckBookingsMessageListenerTestUtil.setUp();
	}

	@After
	public void tearDown() throws Exception {
		CheckBookingsMessageListenerTestUtil.tearDown();

		_upgradeDatabaseTestHelper.close();
	}

	@Test
	public void testUpgradeCreatesCalendarBookingRecurringId()
		throws Exception {

		dropColumnRecurringCalendarBookingId();

		assertDoesNotHaveColumn("recurringCalendarBookingId");

		_upgradeProcess.upgrade();

		assertHasColumn("recurringCalendarBookingId");
	}

	@Test
	public void testUpgradeSetsRecurringCalendarBookingId() throws Exception {
		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(_calendar);

		long recurringCalendarBookingId =
			calendarBooking.getRecurringCalendarBookingId();

		dropColumnRecurringCalendarBookingId();

		_upgradeProcess.upgrade();

		assertRecurringCalendarBookingIdValue(
			calendarBooking, recurringCalendarBookingId);
	}

	protected void assertDoesNotHaveColumn(String columnName) throws Exception {
		Assert.assertFalse(
			_upgradeDatabaseTestHelper.hasColumn(
				"CalendarBooking", columnName));
	}

	protected void assertHasColumn(String columnName) throws Exception {
		Assert.assertTrue(
			_upgradeDatabaseTestHelper.hasColumn(
				"CalendarBooking", columnName));
	}

	protected void assertRecurringCalendarBookingIdValue(
			CalendarBooking calendarBooking, long recurringCalendarBookingId)
		throws PortalException {

		EntityCacheUtil.clearCache();

		Assert.assertNotEquals(
			0, calendarBooking.getRecurringCalendarBookingId());

		calendarBooking = CalendarBookingLocalServiceUtil.getCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			recurringCalendarBookingId,
			calendarBooking.getRecurringCalendarBookingId());
	}

	protected void dropColumnRecurringCalendarBookingId() throws Exception {
		_upgradeDatabaseTestHelper.dropColumn(
			"com.liferay.calendar.internal.upgrade.v1_0_0.util." +
				"CalendarBookingTable",
			"CalendarBooking", "recurringCalendarBookingId");
	}

	@DeleteAfterTestRun
	private Calendar _calendar;

	@DeleteAfterTestRun
	private Group _group;

	private UpgradeDatabaseTestHelper _upgradeDatabaseTestHelper;
	private UpgradeProcess _upgradeProcess;

}