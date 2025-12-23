/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.service.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class UserPermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testContainsPermissionsActionId() throws Exception {
		_user1 = UserTestUtil.addUser();
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_userLocalService.addRoleUser(_role.getRoleId(), _user1);

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			_user1);

		Assert.assertFalse(
			UserPermissionUtil.contains(
				permissionChecker, _user1.getUserId(), null,
				ActionKeys.PERMISSIONS));

		RoleTestUtil.addResourcePermission(
			_role, User.class.getName(), ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_user1.getCompanyId()), ActionKeys.PERMISSIONS);

		Assert.assertTrue(
			UserPermissionUtil.contains(
				permissionChecker, _user1.getUserId(), null,
				ActionKeys.PERMISSIONS));
	}

	@Test
	public void testContainsViewActionId() throws Exception {
		_user1 = UserTestUtil.addUser();
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			_role, User.class.getName(), ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_user1.getCompanyId()), ActionKeys.VIEW);

		_userLocalService.addRoleUser(_role.getRoleId(), _user1);

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			_user1);

		Assert.assertTrue(
			UserPermissionUtil.contains(
				permissionChecker, ResourceConstants.PRIMKEY_DNE,
				ActionKeys.VIEW));
	}

	@Test
	public void testOrganizationActionIds() throws Exception {
		_user1 = UserTestUtil.addUser();
		_user2 = UserTestUtil.addUser();

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			_role, Organization.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_user1.getCompanyId()), ActionKeys.MANAGE_USERS);

		_userLocalService.addRoleUser(_role.getRoleId(), _user1);

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			_user1);

		Assert.assertFalse(
			UserPermissionUtil.contains(
				permissionChecker, _user2.getUserId(), ActionKeys.UPDATE));

		_organization = OrganizationTestUtil.addOrganization();

		_userLocalService.addOrganizationUser(
			_organization.getOrganizationId(), _user2.getUserId());

		Assert.assertTrue(
			UserPermissionUtil.contains(
				permissionChecker, _user2.getUserId(), ActionKeys.UPDATE));
	}

	@DeleteAfterTestRun
	private Organization _organization;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

	@Inject
	private UserLocalService _userLocalService;

}