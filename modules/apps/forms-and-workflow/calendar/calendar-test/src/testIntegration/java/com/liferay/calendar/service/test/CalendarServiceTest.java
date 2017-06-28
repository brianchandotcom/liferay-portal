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
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.calendar.service.CalendarServiceUtil;
import com.liferay.calendar.test.util.CalendarStagingTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class CalendarServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@After
	public void tearDown() {
		CalendarStagingTestUtil.cleanUp();
	}

	@Sync
	@Test
	public void testIsManageableFromGroup() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(_liveGroup);

		_notStagedGroup = GroupTestUtil.addGroup();

		Group stagingGroup = _liveGroup.getStagingGroup();

		_adminUser = UserTestUtil.addOmniAdminUser();

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_adminUser)) {

			Calendar notStagedCalendar = getGroupCalendar(_notStagedGroup);

			assertManageableFromGroup(notStagedCalendar, _notStagedGroup);
			assertManageableFromGroup(notStagedCalendar, _liveGroup);
			assertManageableFromGroup(notStagedCalendar, stagingGroup);

			Calendar liveCalendar = getGroupCalendar(_liveGroup);

			assertNotManageableFromGroup(liveCalendar, _notStagedGroup);
			assertNotManageableFromGroup(liveCalendar, _liveGroup);
			assertNotManageableFromGroup(liveCalendar, stagingGroup);

			Calendar stagingCalendar = getGroupCalendar(stagingGroup);

			assertNotManageableFromGroup(stagingCalendar, _notStagedGroup);
			assertNotManageableFromGroup(stagingCalendar, _liveGroup);
			assertManageableFromGroup(stagingCalendar, stagingGroup);
		}
	}

	protected void assertManageableFromGroup(Calendar calendar, Group group)
		throws PortalException {

		Assert.assertTrue(
			CalendarServiceUtil.isManageableFromGroup(
				calendar.getCalendarId(), group.getGroupId()));
	}

	protected void assertNotManageableFromGroup(Calendar calendar, Group group)
		throws PortalException {

		Assert.assertFalse(
			CalendarServiceUtil.isManageableFromGroup(
				calendar.getCalendarId(), group.getGroupId()));
	}

	protected Calendar getGroupCalendar(Group group) throws Exception {
		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.fetchCalendarResource(
				PortalUtil.getClassNameId(Group.class), group.getGroupId());

		if (calendarResource == null) {
			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.getDefault(), group.getDescriptiveName());

			Map<Locale, String> descriptionMap = new HashMap<>();

			ServiceContext serviceContext = new ServiceContext();

			calendarResource =
				CalendarResourceLocalServiceUtil.addCalendarResource(
					group.getCreatorUserId(), group.getGroupId(),
					PortalUtil.getClassNameId(Group.class), group.getGroupId(),
					null, null, nameMap, descriptionMap, true, serviceContext);
		}

		return calendarResource.getDefaultCalendar();
	}

	@DeleteAfterTestRun
	private User _adminUser;

	@DeleteAfterTestRun
	private Group _liveGroup;

	@DeleteAfterTestRun
	private Group _notStagedGroup;

}