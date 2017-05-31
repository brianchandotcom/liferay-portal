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
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();

		_group11 = GroupTestUtil.addGroup(_group1.getGroupId());

		_group111 = GroupTestUtil.addGroup(_group11.getGroupId());

		setUpPrincipalThreadLocal();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group111);

		GroupLocalServiceUtil.deleteGroup(_group11);

		GroupLocalServiceUtil.deleteGroup(_group1);

		UserLocalServiceUtil.deleteUser(_user);

		PrincipalThreadLocal.setName(_name);
	}

	@Test
	public void testAddPermissionsCustomRole() throws Exception {
		_user = UserTestUtil.addUser(null, _group1.getGroupId());

		givePermissionToManageSubsites(_group1);

		testAddGroup(false, true, true, true);
	}

	@Test
	public void testAddPermissionsCustomRoleInSubsite() throws Exception {
		_user = UserTestUtil.addUser(null, _group11.getGroupId());

		givePermissionToManageSubsites(_group11);

		testAddGroup(false, false, true, true);
	}

	@Test
	public void testAddPermissionsRegularUser() throws Exception {
		_user = UserTestUtil.addUser(null, _group1.getGroupId());

		testAddGroup(false, false, false, false);
	}

	@Test
	public void testAddPermissionsSiteAdmin() throws Exception {
		_user = UserTestUtil.addUser(null, _group1.getGroupId());

		giveSiteAdminRole(_group1);

		testAddGroup(true, true, true, true);
	}

	@Test
	public void testAddPermissionsSubsiteAdmin() throws Exception {
		_user = UserTestUtil.addUser(null, _group11.getGroupId());

		giveSiteAdminRole(_group11);

		testAddGroup(false, false, true, true);
	}

	@Test
	public void testUpdatePermissionsCustomRole() throws Exception {
		_user = UserTestUtil.addUser(null, _group1.getGroupId());

		givePermissionToManageSubsites(_group1);

		testUpdateGroup(false, false, true, true);
	}

	@Test
	public void testUpdatePermissionsCustomRoleInSubsite() throws Exception {
		_user = UserTestUtil.addUser(null, _group11.getGroupId());

		givePermissionToManageSubsites(_group11);

		testUpdateGroup(false, false, false, true);
	}

	@Test
	public void testUpdatePermissionsRegularUser() throws Exception {
		_user = UserTestUtil.addUser(null, _group1.getGroupId());

		testUpdateGroup(false, false, false, false);
	}

	@Test
	public void testUpdatePermissionsSiteAdmin() throws Exception {
		_user = UserTestUtil.addUser(null, _group1.getGroupId());

		giveSiteAdminRole(_group1);

		testUpdateGroup(true, false, true, true);
	}

	@Test
	public void testUpdatePermissionsSubsiteAdmin() throws Exception {
		_user = UserTestUtil.addUser(null, _group11.getGroupId());

		giveSiteAdminRole(_group11);

		testUpdateGroup(false, true, false, true);
	}

	protected void givePermissionToManageSubsites(Group group)
		throws Exception {

		Role role = RoleTestUtil.addRole(
			"Subsites Admin", RoleConstants.TYPE_SITE, Group.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.MANAGE_SUBGROUPS);

		long[] roleIds = new long[] {role.getRoleId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			_user.getUserId(), group.getGroupId(), roleIds);
	}

	protected void giveSiteAdminRole(Group group) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		long[] roleIds = new long[] {role.getRoleId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			_user.getUserId(), group.getGroupId(), roleIds);
	}

	protected void setUpPrincipalThreadLocal() throws Exception {
		_name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	protected void testAddGroup(
			boolean hasManageSite1, boolean hasManageSubsitePermisionOnGroup1,
			boolean hasManageSubsitePermisionOnGroup11,
			boolean hasManageSubsitePermisionOnGroup111)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group1.getGroupId(), _user.getUserId());

		try {
			Group group = GroupTestUtil.addGroup(
				GroupConstants.DEFAULT_PARENT_GROUP_ID, serviceContext);

			Assert.fail("The user should not be able to add top level sites");

			if (group != null) {
				GroupLocalServiceUtil.deleteGroup(group);
			}
		}
		catch (PrincipalException pe) {
		}

		try {
			Group group = GroupTestUtil.addGroup(
				_group1.getGroupId(), serviceContext);

			Assert.assertTrue(
				"The user should not be able to add this site",
				hasManageSubsitePermisionOnGroup1 || hasManageSite1);

			if (group != null) {
				GroupLocalServiceUtil.deleteGroup(group);
			}
		}
		catch (PrincipalException pe) {
			Assert.assertFalse(
				"The user should be able to add this site",
				hasManageSubsitePermisionOnGroup1 || hasManageSite1);
		}

		try {
			Group group = GroupTestUtil.addGroup(
				_group11.getGroupId(), serviceContext);

			Assert.assertTrue(
				"The user should not be able to add this site",
				hasManageSubsitePermisionOnGroup11 || hasManageSite1);

			if (group != null) {
				GroupLocalServiceUtil.deleteGroup(group);
			}
		}
		catch (PrincipalException pe) {
			Assert.assertFalse(
				"The user should be able to add this site",
				hasManageSubsitePermisionOnGroup11 || hasManageSite1);
		}

		try {
			Group group = GroupTestUtil.addGroup(
				_group111.getGroupId(), serviceContext);

			Assert.assertTrue(
				"The user should not be able to add this site",
				hasManageSubsitePermisionOnGroup111 || hasManageSite1);

			if (group != null) {
				GroupLocalServiceUtil.deleteGroup(group);
			}
		}
		catch (PrincipalException pe) {
			Assert.assertFalse(
				"The user should be able to add this site",
				hasManageSubsitePermisionOnGroup111 || hasManageSite1);
		}
	}

	protected void testUpdateGroup(
			boolean hasManageSite1, boolean hasManageSite11,
			boolean hasManageSubsitePermisionOnGroup1,
			boolean hasManageSubsitePermisionOnGroup11)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		try {
			GroupServiceUtil.updateGroup(_group1.getGroupId(), "");

			Assert.assertTrue(
				"The user should not be able to update this site",
				hasManageSite1);
		}
		catch (PrincipalException pe) {
			Assert.assertFalse(
				"The user should be able to update this site", hasManageSite1);
		}

		try {
			GroupServiceUtil.updateGroup(_group11.getGroupId(), "");

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
			GroupServiceUtil.updateGroup(_group111.getGroupId(), "");

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

	private Group _group1;
	private Group _group11;
	private Group _group111;
	private String _name;
	private User _user;

}