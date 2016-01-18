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
import com.liferay.calendar.service.CalendarServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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

	@Sync
	@Test
	public void testIsManageableFromGroup() throws Exception {
		Group liveGroup = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(liveGroup);

		Group notStagedGroup = GroupTestUtil.addGroup();

		Group stagingGroup = liveGroup.getStagingGroup();

		User adminUser = UserTestUtil.addOmniAdminUser();

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				adminUser)) {

			Calendar notStagedCalendar = getGroupCalendar(notStagedGroup);

			Assert.assertTrue(
				CalendarServiceUtil.isManageableFromGroup(
					notStagedCalendar.getCalendarId(),
					notStagedGroup.getGroupId()));
			Assert.assertTrue(
				CalendarServiceUtil.isManageableFromGroup(
					notStagedCalendar.getCalendarId(), liveGroup.getGroupId()));
			Assert.assertTrue(
				CalendarServiceUtil.isManageableFromGroup(
					notStagedCalendar.getCalendarId(),
					stagingGroup.getGroupId()));

			Calendar liveCalendar = getGroupCalendar(liveGroup);

			Assert.assertFalse(
				CalendarServiceUtil.isManageableFromGroup(
					liveCalendar.getCalendarId(), notStagedGroup.getGroupId()));
			Assert.assertFalse(
				CalendarServiceUtil.isManageableFromGroup(
					liveCalendar.getCalendarId(), liveGroup.getGroupId()));
			Assert.assertFalse(
				CalendarServiceUtil.isManageableFromGroup(
					liveCalendar.getCalendarId(), stagingGroup.getGroupId()));

			Calendar stagingCalendar = getGroupCalendar(stagingGroup);

			Assert.assertFalse(
				CalendarServiceUtil.isManageableFromGroup(
					stagingCalendar.getCalendarId(),
					notStagedGroup.getGroupId()));
			Assert.assertFalse(
				CalendarServiceUtil.isManageableFromGroup(
					stagingCalendar.getCalendarId(), liveGroup.getGroupId()));
			Assert.assertTrue(
				CalendarServiceUtil.isManageableFromGroup(
					stagingCalendar.getCalendarId(),
					stagingGroup.getGroupId()));
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(liveGroup);
			GroupLocalServiceUtil.deleteGroup(notStagedGroup);
			UserLocalServiceUtil.deleteUser(adminUser);
		}
	}

	protected Calendar getGroupCalendar(Group group) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				group.getGroupId(), serviceContext);

		return calendarResource.getDefaultCalendar();
	}

}