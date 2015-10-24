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
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.calendar.service.CalendarResourceServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;

import java.util.HashMap;
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
public class CalendarResourceServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testSearchCountByKeywords() throws PortalException {
		Locale locale = LocaleUtil.getDefault();
		String name =
			RandomTestUtil.randomString() + StringPool.SPACE +
			RandomTestUtil.randomString();
		Map<Locale, String> nameMap = new HashMap<>();
		nameMap.put(locale, name);
		long classNameId = PortalUtil.getClassNameId(CalendarResource.class);

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.addCalendarResource(
				_user.getUserId(), _user.getGroupId(), classNameId, 0,
				PortalUUIDUtil.generate(), RandomTestUtil.randomString(8),
				nameMap, RandomTestUtil.randomLocaleStringMap(), true,
				new ServiceContext());

		int count = CalendarResourceServiceUtil.searchCount(
			_user.getCompanyId(), new long[] {_user.getGroupId()},
			new long[] {classNameId}, name, true);

		Assert.assertEquals(1, count);
	}

	@DeleteAfterTestRun
	private User _user;

}