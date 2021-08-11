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
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.calendar.test.util.CalendarUpgradeTestUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

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
@DataGuard(scope = DataGuard.Scope.METHOD)
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

		User user = _userLocalService.getUser(userId);

		Assert.assertFalse(user.isDefaultUser());

		Role administratorRole = _roleLocalService.getRole(
			_group.getCompanyId(), RoleConstants.ADMINISTRATOR);

		Assert.assertTrue(
			_roleLocalService.hasUserRole(
				user.getUserId(), administratorRole.getRoleId()));
	}

	protected void assertUserIsDefault(long userId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		Assert.assertTrue(user.isDefaultUser());
	}

	protected long getCalendarResourceUserId(CalendarResource calendarResource)
		throws SQLException {

		try (Connection connection = DataAccess.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select userId from CalendarResource where " +
					"calendarResourceId = ?");

			preparedStatement.setLong(
				1, calendarResource.getCalendarResourceId());

			ResultSet resultSet = preparedStatement.executeQuery();

			resultSet.next();

			return resultSet.getLong(1);
		}
	}

	protected long getCalendarUserId(Calendar calendar) throws SQLException {
		try (Connection connection = DataAccess.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select userId from Calendar where calendarId = ?");

			preparedStatement.setLong(1, calendar.getCalendarId());

			ResultSet resultSet = preparedStatement.executeQuery();

			resultSet.next();

			return resultSet.getLong(1);
		}
	}

	protected CalendarResource getDefaultUserCalendarResource()
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), new ServiceContext());

		Calendar calendar = calendarResource.getDefaultCalendar();

		long defaultUserId = _userLocalService.getDefaultUserId(
			_group.getCompanyId());

		calendar.setUserId(defaultUserId);
		calendarResource.setUserId(defaultUserId);

		_calendarLocalService.updateCalendar(calendar);

		return _calendarResourceLocalService.updateCalendarResource(
			calendarResource);
	}

	protected void setUpUpgradeCalendarResource() {
		_upgradeProcess = CalendarUpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, "v1_0_5.CalendarResourceUpgradeProcess");
	}

	@Inject
	private CalendarLocalService _calendarLocalService;

	@Inject
	private CalendarResourceLocalService _calendarResourceLocalService;

	private Group _group;

	@Inject
	private RoleLocalService _roleLocalService;

	private UpgradeProcess _upgradeProcess;

	@Inject(
		filter = "component.name=com.liferay.calendar.internal.upgrade.CalendarServiceUpgrade"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private UserLocalService _userLocalService;

}