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

package com.liferay.calendar.upgrade.v1_0_6.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.test.util.CalendarResourceTestUtil;
import com.liferay.calendar.test.util.CalendarUpgradeTestUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PermissionedModel;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceBlock;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

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
public class UpgradeResourcePermissionTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		setUpGuestRole();

		setUpUpgradeResourcePermissions();
	}

	@Test
	public void testRemoveGuestPermissionsPermission() throws Exception {
		CalendarResource calendarResource =
			CalendarResourceTestUtil.addCalendarResource(_group);

		addGuestResourcePermission(calendarResource, ActionKeys.PERMISSIONS);

		assertHasGuestPermission(calendarResource, ActionKeys.PERMISSIONS);

		_upgradeResourcePermission.upgrade();

		assertDoesNotHaveGuestPermission(
			calendarResource, ActionKeys.PERMISSIONS);
	}

	@Test
	public void testRemoveGuestViewPermission() throws Exception {
		CalendarResource calendarResource =
			CalendarResourceTestUtil.addCalendarResource(_group);

		addGuestResourcePermission(calendarResource, ActionKeys.VIEW);

		assertHasGuestPermission(calendarResource, ActionKeys.VIEW);

		_upgradeResourcePermission.upgrade();

		assertDoesNotHaveGuestPermission(calendarResource, ActionKeys.VIEW);
	}

	protected void addGuestResourcePermission(
			PermissionedModel permissionedModel, String actionKey)
		throws PortalException {

		ResourceBlockLocalServiceUtil.addIndividualScopePermission(
			_group.getCompanyId(), _group.getGroupId(), _CALENDAR_RESOURCE_NAME,
			permissionedModel, _guestRole.getRoleId(), actionKey);
	}

	protected void assertDoesNotHaveGuestPermission(
			CalendarResource calendarResource, String actionKey)
		throws PortalException {

		EntityCacheUtil.clearCache();

		ResourceBlock resourceBlock =
			ResourceBlockLocalServiceUtil.getResourceBlock(
				_CALENDAR_RESOURCE_NAME,
				calendarResource.getCalendarResourceId());

		List<String> permissions = ResourceBlockLocalServiceUtil.getPermissions(
			resourceBlock, _guestRole.getRoleId());

		Assert.assertFalse(permissions.contains(actionKey));
	}

	protected void assertHasGuestPermission(
			CalendarResource calendarResource, String actionKey)
		throws PortalException {

		EntityCacheUtil.clearCache();

		ResourceBlock resourceBlock =
			ResourceBlockLocalServiceUtil.getResourceBlock(
				_CALENDAR_RESOURCE_NAME,
				calendarResource.getCalendarResourceId());

		List<String> permissions = ResourceBlockLocalServiceUtil.getPermissions(
			resourceBlock, _guestRole.getRoleId());

		Assert.assertTrue(permissions.contains(actionKey));
	}

	protected long getBitwiseValue(String actionKey) throws Exception {
		ResourceAction resourceAction =
			ResourceActionLocalServiceUtil.getResourceAction(
				_CALENDAR_RESOURCE_NAME, actionKey);

		return resourceAction.getBitwiseValue();
	}

	protected void setUpGuestRole() throws Exception, PortalException {
		_group = GroupTestUtil.addGroup();

		_guestRole = RoleLocalServiceUtil.getRole(
			_group.getCompanyId(), RoleConstants.GUEST);
	}

	protected void setUpUpgradeResourcePermissions() {
		_upgradeResourcePermission = CalendarUpgradeTestUtil.getUpgradeStep(
			"v1_0_6.UpgradeResourcePermission");
	}

	private static final String _CALENDAR_RESOURCE_NAME =
		"com.liferay.calendar.model.CalendarResource";

	@DeleteAfterTestRun
	private Group _group;

	private Role _guestRole;
	private UpgradeProcess _upgradeResourcePermission;

}