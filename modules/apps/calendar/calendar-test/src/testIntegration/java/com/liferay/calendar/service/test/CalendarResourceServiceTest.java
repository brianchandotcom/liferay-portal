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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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

		ServiceTestUtil.setUser(_user);
	}

	@Test
	public void testAddCalendarResource() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.SITE_MEMBER);

		long classNameId = PortalUtil.getClassNameId(CalendarResource.class);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			_CALENDAR_RESOURCE_GROUP_PERMISSIONS, null);

		serviceContext.setModelPermissions(modelPermissions);

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.addCalendarResource(
				user.getUserId(), user.getGroupId(), classNameId, 0,
				PortalUUIDUtil.generate(), RandomTestUtil.randomString(8),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true, serviceContext);

		Assert.assertNotNull(calendarResource);
	}

	@Test
	public void testSearchCount() throws Exception {
		long classNameId = PortalUtil.getClassNameId(CalendarResource.class);

		Map<Locale, String> nameMap = createNameMap();

		CalendarResourceLocalServiceUtil.addCalendarResource(
			_user.getUserId(), _user.getGroupId(), classNameId, 0,
			PortalUUIDUtil.generate(), RandomTestUtil.randomString(8), nameMap,
			RandomTestUtil.randomLocaleStringMap(), true, new ServiceContext());

		int count = CalendarResourceServiceUtil.searchCount(
			_user.getCompanyId(), new long[] {_user.getGroupId()},
			new long[] {classNameId}, nameMap.get(LocaleUtil.getSiteDefault()),
			true);

		Assert.assertEquals(1, count);
	}

	protected Map<Locale, String> createNameMap() {
		String name =
			RandomTestUtil.randomString() + StringPool.SPACE +
				RandomTestUtil.randomString();

		return HashMapBuilder.<Locale, String>put(
			LocaleUtil.getSiteDefault(), name
		).build();
	}

	private static final String[] _CALENDAR_RESOURCE_GROUP_PERMISSIONS = {
		"ADD_CALENDAR", "DELETE", "PERMISSIONS", "UPDATE", "VIEW"
	};

	@DeleteAfterTestRun
	private User _user;

}