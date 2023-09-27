/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.test.util.CalendarStagingTestUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
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
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class CalendarResourceUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() {
		CalendarStagingTestUtil.cleanUp();
	}

	@Test
	public void testGetGroupCalendarFetchesSameResource()
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		CalendarResource createdCalendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		Assert.assertNotNull(createdCalendarResource);

		CalendarResource fetchedCalendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		Assert.assertNotNull(fetchedCalendarResource);
		Assert.assertEquals(
			createdCalendarResource.getCalendarResourceId(),
			fetchedCalendarResource.getCalendarResourceId());
	}

	@Test
	public void testGetGroupCalendarResourceCreatesResource()
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), new ServiceContext());

		Assert.assertNotNull(calendarResource);
	}

	@Test
	public void testGetGroupCalendarResourceCreatesStagingCalendarResource()
		throws Exception {

		GroupTestUtil.enableLocalStaging(_group);

		Group stagingGroup = _group.getStagingGroup();

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				stagingGroup.getGroupId(), new ServiceContext());

		Assert.assertNotNull(calendarResource);
	}

	@Test
	public void testGetGroupCalendarResourceDoesNotCreateLiveCalendarResource()
		throws Exception {

		GroupTestUtil.enableLocalStaging(_group);

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), new ServiceContext());

		Assert.assertNull(calendarResource);
	}

	private Group _group;

}