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

package com.liferay.calendar.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.service.persistence.CalendarFinder;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.calendar.util.comparator.CalendarNameComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
@Sync
public class CalendarFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = bundle.getBundleContext();
		
		_serviceReference = _bundleContext.getServiceReference(
			CalendarFinder.class);

		_calendarFinder = _bundleContext.getService(_serviceReference);
	}

	@Test
	public void testFindByC_G_C_N_D() throws PortalException {
		ServiceContext serviceContext = new ServiceContext();
		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		Locale locale = LocaleUtil.getDefault();
		Map<Locale, String> nameMap =
			RandomTestUtil.randomLocaleStringMap(locale);
		
		Calendar calendar = CalendarLocalServiceUtil.addCalendar(
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

		List<Calendar> calendars = _calendarFinder.findByKeywords(
			_group.getCompanyId(), new long[]{_group.getGroupId()},
			new long[]{calendarResource.getCalendarResourceId()},
			nameMap.get(locale), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new CalendarNameComparator());

		Assert.assertEquals(1, calendars.size());
		Calendar foundCalendar = calendars.get(0);

		Assert.assertEquals(
			calendar.getCalendarId(), foundCalendar.getCalendarId());
		Assert.assertEquals(calendar.getNameMap(), foundCalendar.getNameMap());
	}

	private BundleContext _bundleContext;
	private ServiceReference<CalendarFinder> _serviceReference;
	
	private CalendarFinder _calendarFinder;
	
	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}