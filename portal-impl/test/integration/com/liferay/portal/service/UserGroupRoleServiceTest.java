/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.security.auth.PrincipalException;
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

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addGroupAdminUser(group);
		User objectUser = UserTestUtil.addGroupAdminUser(group);

		deleteUserGroupRolesByRole(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupAdminRemovingGroupAdminRoleByUsers() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addGroupAdminUser(group);
		User objectUser = UserTestUtil.addGroupAdminUser(group);

		deleteUserGroupRolesByUser(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupAdminRemovingGroupOwnerRoleByRoles() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		User subjectUser = UserTestUtil.addGroupAdminUser(group);
		User objectUser = UserTestUtil.addGroupOwnerUser(group);

		deleteUserGroupRolesByRole(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupAdminRemovingGroupOwnerRoleByUsers() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		User subjectUser = UserTestUtil.addGroupAdminUser(group);
		User objectUser = UserTestUtil.addGroupOwnerUser(group);

		deleteUserGroupRolesByUser(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupAdminRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Group group = organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addGroupAdminUser(group);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

		try {
			deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Group group = organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addGroupAdminUser(group);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

		try {
			deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Group group = organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		User subjectUser = UserTestUtil.addGroupAdminUser(group);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		try {
			deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupAdminRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Group group = organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		User subjectUser = UserTestUtil.addGroupAdminUser(group);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		try {
			deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingGroupAdminRoleByRoles() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addGroupOwnerUser(group);
		User objectUser = UserTestUtil.addGroupAdminUser(group);

		deleteUserGroupRolesByRole(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupAdminRoleByUsers() throws Exception {
		Group site = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addGroupOwnerUser(site);
		User objectUser = UserTestUtil.addGroupAdminUser(site);

		deleteUserGroupRolesByUser(
			site.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), site.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupOwnerRoleByRoles() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		User subjectUser = UserTestUtil.addGroupOwnerUser(group);
		User objectUser = UserTestUtil.addGroupOwnerUser(group);

		deleteUserGroupRolesByRole(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingGroupOwnerRoleByUsers() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		User subjectUser = UserTestUtil.addGroupOwnerUser(group);
		User objectUser = UserTestUtil.addGroupOwnerUser(group);

		deleteUserGroupRolesByUser(
			group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test
	public void testGroupOwnerRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Group group = organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addGroupOwnerUser(group);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

		try {
			deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingOrganizationAdminRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Group group = organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addGroupOwnerUser(group);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

		try {
			deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingOrganizationOwnerRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Group group = organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		User subjectUser = UserTestUtil.addGroupOwnerUser(group);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		try {
			deleteUserGroupRolesByRole(
				group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testGroupOwnerRemovingOrganizationOwnerRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Group group = organization.getGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		User subjectUser = UserTestUtil.addGroupAdminUser(group);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		try {
			deleteUserGroupRolesByUser(
				group.getGroupId(), role.getRoleId(), subjectUser, objectUser);

			Assert.fail();
		}
		catch (PrincipalException pe) {
			Assert.assertTrue(
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					objectUser.getUserId(), group.getGroupId(),
					role.getRoleId()));
		}
	}

	@Test
	public void testOrganizationAdminRemovingOrganizationAdminRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

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

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

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

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

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

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		deleteUserGroupRolesByUser(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationAdminRemovingSiteAdminRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addGroupAdminUser(
			organization.getGroup());

		deleteUserGroupRolesByRole(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationAdminRemovingSiteAdminRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addGroupAdminUser(
			organization.getGroup());

		deleteUserGroupRolesByUser(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationAdminRemovingSiteOwnerRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addGroupOwnerUser(
			organization.getGroup());

		deleteUserGroupRolesByRole(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationAdminRemovingSiteOwnerRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addGroupOwnerUser(
			organization.getGroup());

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

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

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

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

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

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

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

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		deleteUserGroupRolesByUser(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteAdminRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addGroupAdminUser(
			organization.getGroup());

		deleteUserGroupRolesByRole(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteAdminRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addGroupAdminUser(
			organization.getGroup());

		deleteUserGroupRolesByUser(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteOwnerRoleByRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addGroupOwnerUser(
			organization.getGroup());

		deleteUserGroupRolesByRole(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
				role.getRoleId()));
	}

	@Test
	public void testOrganizationOwnerRemovingSiteOwnerRoleByUsers()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization(true);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addGroupOwnerUser(
			organization.getGroup());

		deleteUserGroupRolesByUser(
			organization.getGroupId(), role.getRoleId(), subjectUser,
			objectUser);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				objectUser.getUserId(), organization.getGroupId(),
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

}