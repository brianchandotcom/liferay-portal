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

package com.liferay.calendar.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.test.util.CalendarUpgradeTestUtil;
import com.liferay.calendar.test.util.UpgradeDatabaseTestHelper;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

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
public class UpgradeCalendarTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_upgradeCalendar = CalendarUpgradeTestUtil.getUpgradeStep(
			"com.liferay.calendar.internal.upgrade.v1_0_2.UpgradeCalendar");
		_upgradeDatabaseTestHelper =
			CalendarUpgradeTestUtil.getUpgradeDatabaseTestHelper();
	}

	@Test
	public void testUpgradeCreatesCalendarTimeZoneId() throws Exception {
		_upgradeCalendar.upgrade();

		Assert.assertTrue(
			_upgradeDatabaseTestHelper.hasColumn("Calendar", "timeZoneId"));
	}

	@Test
	public void testUpgradeSetsSiteCalendarTimeZoneId() throws Exception {
		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), new ServiceContext());

		_upgradeCalendar.upgrade();

		List<Calendar> calendars =
			CalendarLocalServiceUtil.getCalendarResourceCalendars(
				_group.getGroupId(), calendarResource.getCalendarResourceId());

		Calendar calendar = calendars.get(0);

		Assert.assertEquals(
			PropsUtil.get(PropsKeys.COMPANY_DEFAULT_TIME_ZONE),
			calendar.getTimeZoneId());
	}

	@Test
	public void testUpgradeSetsUserCalendarTimeZoneId() throws Exception {
		_user.setTimeZoneId("Asia/Shangai");

		UserLocalServiceUtil.updateUser(_user);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_user.getCompanyId());

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		_upgradeCalendar.upgrade();

		List<Calendar> calendars =
			CalendarLocalServiceUtil.getCalendarResourceCalendars(
				_user.getGroupId(), calendarResource.getCalendarResourceId());

		Calendar calendar = calendars.get(0);

		Assert.assertEquals("Asia/Shangai", calendar.getTimeZoneId());
	}

	@DeleteAfterTestRun
	private Group _group;

	private UpgradeProcess _upgradeCalendar;
	private UpgradeDatabaseTestHelper _upgradeDatabaseTestHelper;

	@DeleteAfterTestRun
	private User _user;

}