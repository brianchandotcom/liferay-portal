/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.calendar.service.CalendarResourceService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.Inject;
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
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class CalendarResourceServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		UserTestUtil.setUser(_user);
	}

	@Test
	public void testAddCalendarResource() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.SITE_MEMBER);

		long classNameId = PortalUtil.getClassNameId(CalendarResource.class);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		serviceContext.setModelPermissions(
			ModelPermissionsFactory.create(
				_CALENDAR_RESOURCE_GROUP_PERMISSIONS, null));

		CalendarResource calendarResource =
			_calendarResourceLocalService.addCalendarResource(
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

		_calendarResourceLocalService.addCalendarResource(
			_user.getUserId(), _user.getGroupId(), classNameId, 0,
			PortalUUIDUtil.generate(), RandomTestUtil.randomString(8), nameMap,
			RandomTestUtil.randomLocaleStringMap(), true, new ServiceContext());

		int count = _calendarResourceService.searchCount(
			_user.getCompanyId(), new long[] {_user.getGroupId()},
			new long[] {classNameId}, nameMap.get(LocaleUtil.getSiteDefault()),
			true);

		Assert.assertEquals(1, count);
	}

	protected Map<Locale, String> createNameMap() {
		return HashMapBuilder.put(
			LocaleUtil.getSiteDefault(),
			StringBundler.concat(
				RandomTestUtil.randomString(), StringPool.SPACE,
				RandomTestUtil.randomString())
		).build();
	}

	private static final String[] _CALENDAR_RESOURCE_GROUP_PERMISSIONS = {
		"ADD_CALENDAR", "DELETE", "PERMISSIONS", "UPDATE", "VIEW"
	};

	@Inject
	private CalendarResourceLocalService _calendarResourceLocalService;

	@Inject
	private CalendarResourceService _calendarResourceService;

	private User _user;

}