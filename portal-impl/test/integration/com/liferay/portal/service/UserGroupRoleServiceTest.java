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
import com.liferay.portal.util.TestPropsValues;

import java.util.Random;

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
	public void testOrgAdminRemovingOrgAdminRoleByRoles() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User orgAdminOperator = UserTestUtil.addOrganizationAdminUser(
			organization);
		User orgAdminUpdated = UserTestUtil.addOrganizationAdminUser(
			organization);

		Role orgAdminRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		deleteUserGroupRoleByRoles(
			organization.getGroupId(), orgAdminRole.getRoleId(),
			orgAdminOperator, orgAdminUpdated);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				orgAdminUpdated.getUserId(), organization.getGroupId(),
				orgAdminRole.getRoleId()));
	}

	@Test
	public void testOrgAdminRemovingOrgAdminRoleByUsers() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User orgAdminOperator = UserTestUtil.addOrganizationAdminUser(
			organization);
		User orgAdminUpdated = UserTestUtil.addOrganizationAdminUser(
			organization);

		Role orgAdminRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		deleteUserGroupRoleByUsers(
			organization.getGroupId(), orgAdminRole.getRoleId(),
			orgAdminOperator, orgAdminUpdated);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				orgAdminUpdated.getUserId(), organization.getGroupId(),
				orgAdminRole.getRoleId()));
	}

	@Test
	public void testOrgAdminRemovingOrgOwnerRoleByRoles() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User orgAdminOperator = UserTestUtil.addOrganizationAdminUser(
			organization);
		User orgOwnerUpdated = UserTestUtil.addOrganizationOwnerUser(
			organization);

		Role orgOwnerRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		deleteUserGroupRoleByRoles(
			organization.getGroupId(), orgOwnerRole.getRoleId(),
			orgAdminOperator, orgOwnerUpdated);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				orgOwnerUpdated.getUserId(), organization.getGroupId(),
				orgOwnerRole.getRoleId()));
	}

	@Test
	public void testOrgAdminRemovingOrgOwnerRoleByUsers() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User orgAdminOperator = UserTestUtil.addOrganizationAdminUser(
			organization);
		User orgOwnerUpdated = UserTestUtil.addOrganizationOwnerUser(
			organization);

		Role orgOwnerRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		deleteUserGroupRoleByUsers(
			organization.getGroupId(), orgOwnerRole.getRoleId(),
			orgAdminOperator, orgOwnerUpdated);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				orgOwnerUpdated.getUserId(), organization.getGroupId(),
				orgOwnerRole.getRoleId()));
	}

	@Test
	public void testOrgOwnerRemovingOrgAdminRoleByRoles() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User orgOwnerOperator = UserTestUtil.addOrganizationOwnerUser(
			organization);
		User orgAdminUpdated = UserTestUtil.addOrganizationAdminUser(
			organization);

		Role orgAdminRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		deleteUserGroupRoleByRoles(
			organization.getGroupId(), orgAdminRole.getRoleId(),
			orgOwnerOperator, orgAdminUpdated);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				orgAdminUpdated.getUserId(), organization.getGroupId(),
				orgAdminRole.getRoleId()));
	}

	@Test
	public void testOrgOwnerRemovingOrgAdminRoleByUsers() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User orgOwnerOperator = UserTestUtil.addOrganizationOwnerUser(
			organization);
		User orgAdminUpdated = UserTestUtil.addOrganizationAdminUser(
			organization);

		Role orgAdminRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		deleteUserGroupRoleByUsers(
			organization.getGroupId(), orgAdminRole.getRoleId(),
			orgOwnerOperator, orgAdminUpdated);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				orgAdminUpdated.getUserId(), organization.getGroupId(),
				orgAdminRole.getRoleId()));
	}

	@Test
	public void testOrgOwnerRemovingOrgOwnerRoleByRoles() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User orgOwnerOperator = UserTestUtil.addOrganizationOwnerUser(
			organization);
		User orgOwnerUpdated = UserTestUtil.addOrganizationOwnerUser(
			organization);

		Role orgOwnerRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		deleteUserGroupRoleByRoles(
			organization.getGroupId(), orgOwnerRole.getRoleId(),
			orgOwnerOperator, orgOwnerUpdated);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				orgOwnerUpdated.getUserId(), organization.getGroupId(),
				orgOwnerRole.getRoleId()));
	}

	@Test
	public void testOrgOwnerRemovingOrgOwnerRoleByUsers() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User orgOwnerOperator = UserTestUtil.addOrganizationOwnerUser(
			organization);
		User orgOwnerUpdated = UserTestUtil.addOrganizationOwnerUser(
			organization);

		Role orgOwnerRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		deleteUserGroupRoleByUsers(
			organization.getGroupId(), orgOwnerRole.getRoleId(),
			orgOwnerOperator, orgOwnerUpdated);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				orgOwnerUpdated.getUserId(), organization.getGroupId(),
				orgOwnerRole.getRoleId()));
	}

	@Test
	public void testSiteAdminRemovingSiteAdminRoleByRoles() throws Exception {
		Group site = ServiceTestUtil.addGroup(0, "site");

		User siteAdminOperator = addGroupAdminUser(site);
		User siteAdminUpdated = addGroupAdminUser(site);

		Role siteAdminRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		deleteUserGroupRoleByRoles(
			site.getGroupId(), siteAdminRole.getRoleId(), siteAdminOperator,
			siteAdminUpdated);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				siteAdminUpdated.getUserId(), site.getGroupId(),
				siteAdminRole.getRoleId()));
	}

	@Test
	public void testSiteAdminRemovingSiteAdminRoleByUsers() throws Exception {
		Group site = ServiceTestUtil.addGroup(0, "site");

		User siteAdminOperator = addGroupAdminUser(site);
		User siteAdminUpdated = addGroupAdminUser(site);

		Role siteAdminRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		deleteUserGroupRoleByUsers(
			site.getGroupId(), siteAdminRole.getRoleId(), siteAdminOperator,
			siteAdminUpdated);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				siteAdminUpdated.getUserId(), site.getGroupId(),
				siteAdminRole.getRoleId()));
	}

	@Test
	public void testSiteAdminRemovingSiteOwnerRoleByRoles() throws Exception {
		Group site = ServiceTestUtil.addGroup(0, "site");

		User siteAdminOperator = addGroupAdminUser(site);
		User siteOwnerUpdated = addGroupOwnerUser(site);

		Role siteOwnerRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		deleteUserGroupRoleByRoles(
			site.getGroupId(), siteOwnerRole.getRoleId(), siteAdminOperator,
			siteOwnerUpdated);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				siteOwnerUpdated.getUserId(), site.getGroupId(),
				siteOwnerRole.getRoleId()));
	}

	@Test
	public void testSiteAdminRemovingSiteOwnerRoleByUsers() throws Exception {
		Group site = ServiceTestUtil.addGroup(0, "site");

		User siteAdminOperator = addGroupAdminUser(site);
		User siteOwnerUpdated = addGroupOwnerUser(site);

		Role siteOwnerRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		deleteUserGroupRoleByUsers(
			site.getGroupId(), siteOwnerRole.getRoleId(), siteAdminOperator,
			siteOwnerUpdated);

		Assert.assertTrue(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				siteOwnerUpdated.getUserId(), site.getGroupId(),
				siteOwnerRole.getRoleId()));
	}

	@Test
	public void testSiteOwnerRemovingSiteAdminRoleByRoles() throws Exception {
		Group site = ServiceTestUtil.addGroup(0, "site");

		User siteOwnerOperator = addGroupOwnerUser(site);
		User siteAdminUpdated = addGroupAdminUser(site);

		Role siteAdminRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		deleteUserGroupRoleByRoles(
			site.getGroupId(), siteAdminRole.getRoleId(), siteOwnerOperator,
			siteAdminUpdated);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				siteAdminUpdated.getUserId(), site.getGroupId(),
				siteAdminRole.getRoleId()));
	}

	@Test
	public void testSiteOwnerRemovingSiteAdminRoleByUsers() throws Exception {
		Group site = ServiceTestUtil.addGroup(0, "site");

		User siteOwnerOperator = addGroupOwnerUser(site);
		User siteAdminUpdated = addGroupAdminUser(site);

		Role siteAdminRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		deleteUserGroupRoleByUsers(
			site.getGroupId(), siteAdminRole.getRoleId(), siteOwnerOperator,
			siteAdminUpdated);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				siteAdminUpdated.getUserId(), site.getGroupId(),
				siteAdminRole.getRoleId()));
	}

	@Test
	public void testSiteOwnerRemovingSiteOwnerRoleByRoles() throws Exception {
		Group site = ServiceTestUtil.addGroup(0, "site");

		User siteOwnerOperator = addGroupOwnerUser(site);
		User siteOwnerUpdated = addGroupOwnerUser(site);

		Role siteOwnerRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		deleteUserGroupRoleByRoles(
			site.getGroupId(), siteOwnerRole.getRoleId(), siteOwnerOperator,
			siteOwnerUpdated);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				siteOwnerUpdated.getUserId(), site.getGroupId(),
				siteOwnerRole.getRoleId()));
	}

	@Test
	public void testSiteOwnerRemovingSiteOwnerRoleByUsers() throws Exception {
		Group site = ServiceTestUtil.addGroup(0, "site");

		User siteOwnerOperator = addGroupOwnerUser(site);
		User siteOwnerUpdated = addGroupOwnerUser(site);

		Role siteOwnerRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		deleteUserGroupRoleByUsers(
			site.getGroupId(), siteOwnerRole.getRoleId(), siteOwnerOperator,
			siteOwnerUpdated);

		Assert.assertFalse(
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				siteOwnerUpdated.getUserId(), site.getGroupId(),
				siteOwnerRole.getRoleId()));
	}

	protected static User addGroupAdminUser(Group group) throws Exception {
		return addGroupUser(group, RoleConstants.SITE_ADMINISTRATOR);
	}

	protected static User addGroupOwnerUser(Group group) throws Exception {
		return addGroupUser(group, RoleConstants.SITE_OWNER);
	}

	protected static User addGroupUser(Group group, String roleName)
		throws Exception {

		User groupUser = ServiceTestUtil.addUser(
			"groupUser" + _random.nextInt(), group.getGroupId());

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		long[] userIds = {groupUser.getUserId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			userIds, group.getGroupId(), role.getRoleId());

		return groupUser;
	}

	protected static void deleteUserGroupRoleByRoles(
			long groupId, long roleId, User operatorUser, User updatedUser)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(operatorUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			updatedUser.getUserId(), groupId, new long[] {roleId});
	}

	protected static void deleteUserGroupRoleByUsers(
			long groupId, long roleId, User operatorUser, User updatedUser)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(operatorUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			new long[] {updatedUser.getUserId()}, groupId, roleId);
	}

	private static Random _random = new Random();

}