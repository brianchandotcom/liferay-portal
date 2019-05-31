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
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.calendar.util.comparator.CalendarNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
public class CalendarLocalServiceTest {

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
	public void testSearch() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		Locale locale = LocaleUtil.getSiteDefault();

		Map<Locale, String> nameMap = RandomTestUtil.randomLocaleStringMap(
			locale);

		Calendar expectedCalendar = CalendarLocalServiceUtil.addCalendar(
			_user.getUserId(), _group.getGroupId(),
			calendarResource.getCalendarResourceId(), nameMap,
			RandomTestUtil.randomLocaleStringMap(), StringPool.UTC,
			RandomTestUtil.randomInt(0, 255), false, false, false,
			serviceContext);

		CalendarLocalServiceUtil.addCalendar(
			_user.getUserId(), _group.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), StringPool.UTC,
			RandomTestUtil.randomInt(0, 255), false, false, false,
			serviceContext);

		List<Calendar> actualCalendars = CalendarLocalServiceUtil.search(
			_group.getCompanyId(), new long[] {_group.getGroupId()},
			new long[] {calendarResource.getCalendarResourceId()},
			nameMap.get(locale), true, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new CalendarNameComparator());

		Assert.assertEquals(
			actualCalendars.toString(), 1, actualCalendars.size());

		Calendar actualCalendar = actualCalendars.get(0);

		Assert.assertEquals(
			expectedCalendar.getCalendarId(), actualCalendar.getCalendarId());
		Assert.assertEquals(
			expectedCalendar.getNameMap(), actualCalendar.getNameMap());
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}