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

package com.liferay.calendar.upgrade.v1_0_5.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.calendar.test.util.CalendarUpgradeTestUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class UpgradeCalendarResourceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		setUpUpgradeCalendarResource();
	}

	@Test
	public void testUpgradeCalendarResourceUserId() throws Exception {
		CalendarResource calendarResource = getDefaultUserCalendarResource();

		long userId = calendarResource.getUserId();

		assertUserIsDefault(userId);

		_upgradeProcess.upgrade();

		userId = getCalendarResourceUserId(calendarResource);

		assertUserIsAdministrator(userId);
	}

	@Test
	public void testUpgradeCalendarUserId() throws Exception {
		CalendarResource calendarResource = getDefaultUserCalendarResource();

		Calendar calendar = calendarResource.getDefaultCalendar();

		long userId = calendar.getUserId();

		assertUserIsDefault(userId);

		_upgradeProcess.upgrade();

		userId = getCalendarUserId(calendar);

		assertUserIsAdministrator(userId);
	}

	protected void assertUserIsAdministrator(long userId)
		throws PortalException {

		User user = UserLocalServiceUtil.getUser(userId);

		Assert.assertFalse(user.isDefaultUser());

		Role administratorRole = RoleLocalServiceUtil.getRole(
			_group.getCompanyId(), RoleConstants.ADMINISTRATOR);

		Assert.assertTrue(
			RoleLocalServiceUtil.hasUserRole(
				user.getUserId(), administratorRole.getRoleId()));
	}

	protected void assertUserIsDefault(long userId) throws PortalException {
		User user = UserLocalServiceUtil.getUser(userId);

		Assert.assertTrue(user.isDefaultUser());
	}

	protected long getCalendarResourceUserId(CalendarResource calendarResource)
		throws SQLException {

		try (Connection con = DataAccess.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
				"select userId from CalendarResource where " +
					"calendarResourceId = ?");

			ps.setLong(1, calendarResource.getCalendarResourceId());

			ResultSet rs = ps.executeQuery();

			rs.next();

			return rs.getLong(1);
		}
	}

	protected long getCalendarUserId(Calendar calendar) throws SQLException {
		try (Connection con = DataAccess.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
				"select userId from Calendar where calendarId = ?");

			ps.setLong(1, calendar.getCalendarId());

			ResultSet rs = ps.executeQuery();

			rs.next();

			return rs.getLong(1);
		}
	}

	protected CalendarResource getDefaultUserCalendarResource()
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			_group.getCompanyId());

		calendar.setUserId(defaultUserId);
		calendarResource.setUserId(defaultUserId);

		CalendarLocalServiceUtil.updateCalendar(calendar);

		return CalendarResourceLocalServiceUtil.updateCalendarResource(
			calendarResource);
	}

	protected void setUpUpgradeCalendarResource() {
		_upgradeProcess = CalendarUpgradeTestUtil.getServiceUpgradeStep(
			"v1_0_5.UpgradeCalendarResource");
	}

	@DeleteAfterTestRun
	private Group _group;

	private UpgradeProcess _upgradeProcess;

}