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

package com.liferay.portal.service;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class UserGroupRoleServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGroupAdminRemovingGroupAdminRoleByRoles() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupAdminUser(_group);
		_objectUser = UserTestUtil.addGroupAdminUser(_group);

		try {
			deleteUserGroupRolesByRole(
				_group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingGroupAdminRoleByUsers() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupAdminUser(_group);
		_objectUser = UserTestUtil.addGroupAdminUser(_group);

		try {
			deleteUserGroupRolesByUser(
				_group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingGroupOwnerRoleByRoles() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addGroupAdminUser(_group);
		_objectUser = UserTestUtil.addGroupOwnerUser(_group);

		try {
			deleteUserGroupRolesByRole(
				_group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingGroupOwnerRoleByUsers() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addGroupAdminUser(_group);
		_objectUser = UserTestUtil.addGroupOwnerUser(_group);

		try {
			deleteUserGroupRolesByUser(
				_group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupAdminUser(group);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupAdminUser(group);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addGroupAdminUser(group);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addGroupAdminUser(group);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingGroupAdminRoleByRoles() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupOwnerUser(_group);
		_objectUser = UserTestUtil.addGroupAdminUser(_group);

		deleteUserGroupRolesByRole(
			_group.getGroupId(), role.getRoleId(), _subjectUser, _objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _group.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupAdminRoleByUsers() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupOwnerUser(_group);
		_objectUser = UserTestUtil.addGroupAdminUser(_group);

		deleteUserGroupRolesByUser(
			_group.getGroupId(), role.getRoleId(), _subjectUser, _objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _group.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupOwnerRoleByRoles() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addGroupOwnerUser(_group);
		_objectUser = UserTestUtil.addGroupOwnerUser(_group);

		deleteUserGroupRolesByRole(
			_group.getGroupId(), role.getRoleId(), _subjectUser, _objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _group.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupOwnerRoleByUsers() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addGroupOwnerUser(_group);
		_objectUser = UserTestUtil.addGroupOwnerUser(_group);

		deleteUserGroupRolesByUser(
			_group.getGroupId(), role.getRoleId(), _subjectUser, _objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _group.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupOwnerUser(group);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addGroupOwnerUser(group);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addGroupOwnerUser(group);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Group group = _organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addGroupAdminUser(group);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			deleteUserGroupRolesByRole(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		try {
			deleteUserGroupRolesByUser(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			deleteUserGroupRolesByRole(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		try {
			deleteUserGroupRolesByUser(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingSiteAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		try {
			deleteUserGroupRolesByRole(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingSiteAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		try {
			deleteUserGroupRolesByUser(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingSiteOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addGroupOwnerUser(_organization.getGroup());

		try {
			deleteUserGroupRolesByRole(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingSiteOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addOrganizationAdminUser(_organization);
		_objectUser = UserTestUtil.addGroupOwnerUser(_organization.getGroup());

		try {
			deleteUserGroupRolesByUser(
				_organization.getGroupId(), role.getRoleId(), _subjectUser,
				_objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_objectUser.getUserId(), _organization.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		deleteUserGroupRolesByRole(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addOrganizationAdminUser(_organization);

		deleteUserGroupRolesByUser(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		deleteUserGroupRolesByRole(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addOrganizationOwnerUser(_organization);

		deleteUserGroupRolesByUser(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteAdminRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		deleteUserGroupRolesByRole(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteAdminRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		deleteUserGroupRolesByUser(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteOwnerRoleByRoles()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addGroupOwnerUser(_organization.getGroup());

		deleteUserGroupRolesByRole(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteOwnerRoleByUsers()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		_subjectUser = UserTestUtil.addOrganizationOwnerUser(_organization);
		_objectUser = UserTestUtil.addGroupOwnerUser(_organization.getGroup());

		deleteUserGroupRolesByUser(
			_organization.getGroupId(), role.getRoleId(), _subjectUser,
			_objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_objectUser.getUserId(), _organization.getGroupId(),
				role.getRoleId()));
	}

	protected void deleteUserGroupRolesByRole(
			long groupId, long roleId, User subjectUser, User objectUser)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(subjectUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			objectUser.getUserId(), groupId, new long[] {roleId});
	}

	protected void deleteUserGroupRolesByUser(
			long groupId, long roleId, User subjectUser, User objectUser)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(subjectUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			new long[] {objectUser.getUserId()}, groupId, roleId);
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _objectUser;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private User _subjectUser;

}