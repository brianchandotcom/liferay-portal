/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.OrganizationTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class UserGroupRoleServiceTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	public void testGroupAdminRemovingGroupAdminRoleByRoles() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User subjectUser = addGroupAdminUser(group);
		User objectUser = addGroupAdminUser(group);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		deleteUserGroupRolesByRole(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupAdminRemovingGroupAdminRoleByUsers() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User subjectUser = addGroupAdminUser(group);
		User objectUser = addGroupAdminUser(group);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		deleteUserGroupRolesByUser(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupAdminRemovingGroupOwnerRoleByRoles() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User subjectUser = addGroupAdminUser(group);
		User objectUser = addGroupOwnerUser(group);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		deleteUserGroupRolesByRole(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupAdminRemovingGroupOwnerRoleByUsers() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User subjectUser = addGroupAdminUser(group);
		User objectUser = addGroupOwnerUser(group);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		deleteUserGroupRolesByUser(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupAdminRoleByRoles() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User subjectUser = addGroupOwnerUser(group);
		User objectUser = addGroupAdminUser(group);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		deleteUserGroupRolesByRole(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupAdminRoleByUsers() throws Exception {
		Group site = GroupTestUtil.addGroup();

		User subjectUser = addGroupOwnerUser(site);
		User objectUser = addGroupAdminUser(site);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		deleteUserGroupRolesByUser(
			site.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), site.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupOwnerRoleByRoles() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User subjectUser = addGroupOwnerUser(group);
		User objectUser = addGroupOwnerUser(group);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		deleteUserGroupRolesByRole(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupOwnerRoleByUsers() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User subjectUser = addGroupOwnerUser(group);
		User objectUser = addGroupOwnerUser(group);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		deleteUserGroupRolesByUser(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		deleteUserGroupRolesByRole(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		deleteUserGroupRolesByUser(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		deleteUserGroupRolesByRole(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		deleteUserGroupRolesByUser(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		deleteUserGroupRolesByRole(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		deleteUserGroupRolesByUser(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		deleteUserGroupRolesByRole(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		deleteUserGroupRolesByUser(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	protected User addGroupAdminUser(Group group) throws Exception {
		return addGroupUser(group, RoleConstants.SITE_ADMINISTRATOR);
	}

	protected User addGroupOwnerUser(Group group) throws Exception {
		return addGroupUser(group, RoleConstants.SITE_OWNER);
	}

	protected User addGroupUser(Group group, String roleName) throws Exception {
		User groupUser = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		long[] userIds = {groupUser.getUserId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			userIds, group.getGroupId(), role.getRoleId());

		return groupUser;
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

}