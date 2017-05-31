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

package com.liferay.site.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 * @author Roberto Díaz
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class GroupServicePermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAddPermissionsCustomRole() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		givePermissionToManageSubsites(user, group);

		testGroup(
			user, group, null, null, true, false, false, false, true, true,
			true);
	}

	@Test
	public void testAddPermissionsCustomRoleInSubsite() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

		User user = UserTestUtil.addUser(null, group11.getGroupId());

		givePermissionToManageSubsites(user, group11);

		testGroup(
			user, group1, group11, null, true, false, false, false, false, true,
			true);
	}

	@Test
	public void testAddPermissionsRegularUser() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		testGroup(
			user, group, null, null, true, false, false, false, false, false,
			false);
	}

	@Test
	public void testAddPermissionsSiteAdmin() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		giveSiteAdminRole(user, group);

		testGroup(
			user, group, null, null, true, false, true, false, true, true,
			true);
	}

	@Test
	public void testAddPermissionsSubsiteAdmin() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

		User user = UserTestUtil.addUser(null, group11.getGroupId());

		giveSiteAdminRole(user, group11);

		testGroup(
			user, group1, group11, null, true, false, false, true, false, true,
			true);
	}

	@Test
	public void testUpdatePermissionsCustomRole() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		givePermissionToManageSubsites(user, group);

		testGroup(
			user, group, null, null, false, true, false, false, true, true,
			true);
	}

	@Test
	public void testUpdatePermissionsCustomRoleInSubsite() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

		User user = UserTestUtil.addUser(null, group11.getGroupId());

		givePermissionToManageSubsites(user, group11);

		testGroup(
			user, group1, group11, null, false, true, false, false, false, true,
			true);
	}

	@Test
	public void testUpdatePermissionsRegularUser() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		testGroup(
			user, group, null, null, false, true, false, false, false, false,
			false);
	}

	@Test
	public void testUpdatePermissionsSiteAdmin() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		giveSiteAdminRole(user, group);

		testGroup(
			user, group, null, null, false, true, true, false, true, true,
			true);
	}

	@Test
	public void testUpdatePermissionsSubsiteAdmin() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

		User user = UserTestUtil.addUser(null, group11.getGroupId());

		giveSiteAdminRole(user, group11);

		testGroup(
			user, group1, group11, null, false, true, false, true, false, true,
			true);
	}

	protected void givePermissionToManageSubsites(User user, Group group)
		throws Exception {

		Role role = RoleTestUtil.addRole(
			"Subsites Admin", RoleConstants.TYPE_SITE, Group.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.MANAGE_SUBGROUPS);

		long[] roleIds = new long[] {role.getRoleId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), roleIds);
	}

	protected void giveSiteAdminRole(User user, Group group) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		long[] roleIds = new long[] {role.getRoleId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), roleIds);
	}

	protected void testGroup(
			User user, Group group1, Group group11, Group group111,
			boolean addGroup, boolean updateGroup, boolean hasManageSite1,
			boolean hasManageSite11, boolean hasManageSubsitePermisionOnGroup1,
			boolean hasManageSubsitePermisionOnGroup11,
			boolean hasManageSubsitePermisionOnGroup111)
		throws Exception {

		if (group1 == null) {
			group1 = GroupTestUtil.addGroup();
		}

		if (group11 == null) {
			group11 = GroupTestUtil.addGroup(group1.getGroupId());
		}

		if (group111 == null) {
			group111 = GroupTestUtil.addGroup(group11.getGroupId());
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group1.getGroupId(), user.getUserId());

		if (addGroup) {
			try {
				GroupTestUtil.addGroup(
					GroupConstants.DEFAULT_PARENT_GROUP_ID, serviceContext);

				Assert.fail(
					"The user should not be able to add top level sites");
			}
			catch (PrincipalException pe) {
			}

			try {
				GroupTestUtil.addGroup(group1.getGroupId(), serviceContext);

				Assert.assertTrue(
					"The user should not be able to add this site",
					hasManageSubsitePermisionOnGroup1 || hasManageSite1);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to add this site",
					hasManageSubsitePermisionOnGroup1 || hasManageSite1);
			}

			try {
				GroupTestUtil.addGroup(group11.getGroupId(), serviceContext);

				Assert.assertTrue(
					"The user should not be able to add this site",
					hasManageSubsitePermisionOnGroup11 || hasManageSite1);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to add this site",
					hasManageSubsitePermisionOnGroup11 || hasManageSite1);
			}

			try {
				GroupTestUtil.addGroup(group111.getGroupId(), serviceContext);

				Assert.assertTrue(
					"The user should not be able to add this site",
					hasManageSubsitePermisionOnGroup111 || hasManageSite1);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to add this site",
					hasManageSubsitePermisionOnGroup111 || hasManageSite1);
			}
		}

		if (updateGroup) {
			try {
				GroupServiceUtil.updateGroup(group1.getGroupId(), "");

				Assert.assertTrue(
					"The user should not be able to update this site",
					hasManageSite1);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to update this site",
					hasManageSite1);
			}

			try {
				GroupServiceUtil.updateGroup(group11.getGroupId(), "");

				Assert.assertTrue(
					"The user should not be able to update this site",
					hasManageSubsitePermisionOnGroup1 || hasManageSite1 ||
						hasManageSite11);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to update this site",
					hasManageSubsitePermisionOnGroup1 || hasManageSite1 ||
						hasManageSite11);
			}

			try {
				GroupServiceUtil.updateGroup(group111.getGroupId(), "");

				Assert.assertTrue(
					"The user should not be able to update this site",
					hasManageSubsitePermisionOnGroup11 || hasManageSite1);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to update this site",
					hasManageSubsitePermisionOnGroup1 || hasManageSite1);
			}
		}
	}

}