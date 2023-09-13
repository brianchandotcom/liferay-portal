/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.service.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class RolePermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAssignMembersPermission() throws Exception {
		Role administratorRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ADMINISTRATOR);
		Role unrelatedRole = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		Assert.assertFalse(_hasPermission(administratorRole));
		Assert.assertFalse(_hasPermission(unrelatedRole));

		_addResourcePermission(
			ActionKeys.ASSIGN_MEMBERS, Role.class.getName(), _user.getUserId());

		Assert.assertTrue(_hasPermission(unrelatedRole));
		Assert.assertFalse(_hasPermission(administratorRole));
	}

	private void _addResourcePermission(
			String actionId, String resourceName, long userId)
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			role, resourceName, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), actionId);

		_userLocalService.addRoleUser(role.getRoleId(), userId);
	}

	private boolean _hasPermission(Role role) {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		return RolePermissionUtil.contains(
			permissionChecker, role.getRoleId(), ActionKeys.ASSIGN_MEMBERS);
	}

	@Inject
	private RoleLocalService _roleLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}