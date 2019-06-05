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

package com.liferay.site.memberships.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class UserGroupRoleServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGroupAdminRemovingGroupAdminRoleByRoles() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupAdminUser(_group);
		_objectUser = UserTestUtil.addGroupAdminUser(_group);

		try {
			_deleteUserGroupRolesByRole(
				_group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingGroupAdminRoleByUsers() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupAdminUser(_group);
		_objectUser = UserTestUtil.addGroupAdminUser(_group);

		try {
			_deleteUserGroupRolesByUser(
				_group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingGroupOwnerRoleByRoles() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addGroupAdminUser(_group);
		_objectUser = UserTestUtil.addGroupOwnerUser(_group);

		try {
			_deleteUserGroupRolesByRole(
				_group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingGroupOwnerRoleByUsers() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addGroupAdminUser(_group);
		_objectUser = UserTestUtil.addGroupOwnerUser(_group);

		try {
			_deleteUserGroupRolesByUser(
				_group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupAdminUser(group);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			_deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupAdminUser(group);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			_deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addGroupAdminUser(group);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			_deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addGroupAdminUser(group);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			_deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingGroupAdminRoleByRoles() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupOwnerUser(_group);
		_objectUser = UserTestUtil.addGroupAdminUser(_group);

		_deleteUserGroupRolesByRole(
			_group.getGroupId(), role.getRoleId(), _subjectUser, _objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _group.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupAdminRoleByUsers() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupOwnerUser(_group);
		_objectUser = UserTestUtil.addGroupAdminUser(_group);

		_deleteUserGroupRolesByUser(
			_group.getGroupId(), role.getRoleId(), _subjectUser, _objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _group.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupOwnerRoleByRoles() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addGroupOwnerUser(_group);
		_objectUser = UserTestUtil.addGroupOwnerUser(_group);

		_deleteUserGroupRolesByRole(
			_group.getGroupId(), role.getRoleId(), _subjectUser, _objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _group.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupOwnerRoleByUsers() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addGroupOwnerUser(_group);
		_objectUser = UserTestUtil.addGroupOwnerUser(_group);

		_deleteUserGroupRolesByUser(
			_group.getGroupId(), role.getRoleId(), _subjectUser, _objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _group.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupOwnerUser(group);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			_deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupOwnerUser(group);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			_deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addGroupOwnerUser(group);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			_deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addGroupAdminUser(group);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			_deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			_deleteUserGroupRolesByRole(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			_deleteUserGroupRolesByUser(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			_deleteUserGroupRolesByRole(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			_deleteUserGroupRolesByUser(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingSiteAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		try {
			_deleteUserGroupRolesByRole(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingSiteAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		try {
			_deleteUserGroupRolesByUser(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingSiteOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addGroupOwnerUser(_organization.getGroup());

		try {
			_deleteUserGroupRolesByRole(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingSiteOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addGroupOwnerUser(_organization.getGroup());

		try {
			_deleteUserGroupRolesByUser(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				_userGroupRoleLocalService.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		_deleteUserGroupRolesByRole(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		_deleteUserGroupRolesByUser(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		_deleteUserGroupRolesByRole(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		_deleteUserGroupRolesByUser(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		_deleteUserGroupRolesByRole(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		_deleteUserGroupRolesByUser(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addGroupOwnerUser(_organization.getGroup());

		_deleteUserGroupRolesByRole(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addGroupOwnerUser(_organization.getGroup());

		_deleteUserGroupRolesByUser(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			_userGroupRoleLocalService.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	private void _deleteUserGroupRolesByRole(
			long groupId, long roleId, User subjectUser, User objectUser)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(subjectUser));

		_userGroupRoleService.deleteUserGroupRoles(
			objectUser.getUserId(), groupId, new long[] {roleId});
	}

	private void _deleteUserGroupRolesByUser(
			long groupId, long roleId, User subjectUser, User objectUser)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(subjectUser));

		_userGroupRoleService.deleteUserGroupRoles(
			new long[] {objectUser.getUserId()}, groupId, roleId);
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _objectUser;

	@DeleteAfterTestRun
	private Organization _organization;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _subjectUser;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserGroupRoleService _userGroupRoleService;

}