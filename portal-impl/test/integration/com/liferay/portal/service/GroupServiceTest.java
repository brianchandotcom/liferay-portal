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

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class GroupServiceTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	@Transactional
	public void testAddPermissionsCustomRole() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");

		User user = ServiceTestUtil.addUser(
			null, true, new long[] {group1.getGroupId()});

		_givePermissionToManageSubsites(user, group1);

		_testGroup(
			user, group1, null, null, true, false, false, false, true, true,
			true);
	}

	@Test
	@Transactional
	public void testAddPermissionsCustomRoleInSubsite() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");

		Group group11 = ServiceTestUtil.addGroup(
			group1.getGroupId(), "Test 1.1");

		User user = ServiceTestUtil.addUser(
			null, true, new long[] {group11.getGroupId()});

		_givePermissionToManageSubsites(user, group11);

		_testGroup(
			user, group1, group11, null, true, false, false, false, false, true,
			true);
	}

	@Test
	@Transactional
	public void testAddPermissionsRegularUser() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");

		User user = ServiceTestUtil.addUser(
			null, true, new long[] {group1.getGroupId()});

		_testGroup(
			user, group1, null, null, true, false, false, false, false, false,
			false);
	}

	@Test
	@Transactional
	public void testAddPermissionsSiteAdmin() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");

		User user = ServiceTestUtil.addUser(
			null, true, new long[] {group1.getGroupId()});

		_giveSiteAdminRole(user, group1);

		_testGroup(
			user, group1, null, null, true, false, true, false, true, true,
			true);
	}

	@Test
	@Transactional
	public void testAddPermissionsSubsiteAdmin() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");

		Group group11 = ServiceTestUtil.addGroup(
			group1.getGroupId(), "Test 1.1");

		User user = ServiceTestUtil.addUser(
			null, true, new long[] {group11.getGroupId()});

		_giveSiteAdminRole(user, group11);

		_testGroup(
			user, group1, group11, null, true, false, false, true, false, true,
			true);
	}

	@Test
	@Transactional
	public void testScopes() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");

		Layout layout = ServiceTestUtil.addLayout(
			group1.getGroupId(), "Page 1");

		Assert.assertFalse(layout.hasScopeGroup());

		Group scope = GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			layout.getName(LocaleUtil.getDefault()), null, 0, null, false, true,
			null);

		Assert.assertFalse(scope.isRoot());
		Assert.assertEquals(scope.getParentGroupId(), group1.getGroupId());
	}

	@Test
	@Transactional
	public void testSubsites() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");
		Group group11 = ServiceTestUtil.addGroup(
			group1.getGroupId(), "Test 1.1");
		Group group111 = ServiceTestUtil.addGroup(
			group11.getGroupId(), "Test 1.1.1");

		Assert.assertTrue(group1.isRoot());
		Assert.assertFalse(group11.isRoot());
		Assert.assertFalse(group111.isRoot());
		Assert.assertEquals(group1.getGroupId(), group11.getParentGroupId());
		Assert.assertEquals(group11.getGroupId(), group111.getParentGroupId());
	}

	@Test
	@Transactional
	public void testUpdatePermissionsCustomRole() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");

		User user = ServiceTestUtil.addUser(
			null, true, new long[] {group1.getGroupId()});

		_givePermissionToManageSubsites(user, group1);

		_testGroup(
			user, group1, null, null, false, true, false, false, true, true,
			true);
	}

	@Test
	@Transactional
	public void testUpdatePermissionsCustomRoleInSubsite() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");

		Group group11 = ServiceTestUtil.addGroup(
			group1.getGroupId(), "Test 1.1");

		User user = ServiceTestUtil.addUser(
			null, true, new long[] {group11.getGroupId()});

		_givePermissionToManageSubsites(user, group11);

		_testGroup(
			user, group1, group11, null, false, true, false, false, false, true,
			true);
	}

	@Test
	@Transactional
	public void testUpdatePermissionsRegularUser() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");

		User user = ServiceTestUtil.addUser(
			null, true, new long[] {group1.getGroupId()});

		_testGroup(
			user, group1, null, null, false, true, false, false, false, false,
			false);
	}

	@Test
	@Transactional
	public void testUpdatePermissionsSiteAdmin() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");

		User user = ServiceTestUtil.addUser(
			null, true, new long[] {group1.getGroupId()});

		_giveSiteAdminRole(user, group1);

		_testGroup(
			user, group1, null, null, false, true, true, false, true, true,
			true);
	}

	@Test
	@Transactional
	public void testUpdatePermissionsSubsiteAdmin() throws Exception {
		Group group1 = ServiceTestUtil.addGroup("Test 1");

		Group group11 = ServiceTestUtil.addGroup(
			group1.getGroupId(), "Test 1.1");

		User user = ServiceTestUtil.addUser(
			null, true, new long[] {group11.getGroupId()});

		_giveSiteAdminRole(user, group11);

		_testGroup(
			user, group1, group11, null, false, true, false, true, false, true,
			true);
	}

	private Group _addGroup(
		String name, long parentGroupId, ServiceContext serviceContext)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(
			TestPropsValues.getCompanyId(), name);

		if (group != null) {
			return group;
		}

		String description = "This is a test group.";
		int type = GroupConstants.TYPE_SITE_OPEN;
		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);
		boolean site = true;
		boolean active = true;

		if (serviceContext == null) {
			serviceContext = ServiceTestUtil.getServiceContext();
		}

		return GroupServiceUtil.addGroup(
			parentGroupId, GroupConstants.DEFAULT_LIVE_GROUP_ID, name,
			description, type, friendlyURL, site, active, serviceContext);
	}

	private void _givePermissionToManageSubsites(User user, Group group)
		throws Exception {

		Role role = null;

		try {
			role = RoleLocalServiceUtil.getRole(
				TestPropsValues.getCompanyId(), "Subsites Admin");
		}
		catch (NoSuchRoleException nsre) {
			role = RoleLocalServiceUtil.addRole(
				TestPropsValues.getUserId(), null, 0, "Subsites Admin", null,
				null, RoleConstants.TYPE_SITE, null);
		}

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			group.getCompanyId(), Group.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			role.getRoleId(), ActionKeys.MANAGE_SUBGROUPS);

		long[] roleIds = new long[] {role.getRoleId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), roleIds);
	}

	private void _giveSiteAdminRole(User user, Group group) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		long[] roleIds = new long[] {role.getRoleId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), roleIds);
	}

	private void _testGroup(
			User user, Group group1, Group group11, Group group111,
			boolean addGroup, boolean updateGroup, boolean hasManageSite1,
			boolean hasManageSite11, boolean hasManageSubsitePermisionOnGroup1,
			boolean hasManageSubsitePermisionOnGroup11,
			boolean hasManageSubsitePermisionOnGroup111)
		throws Exception {

		if (group1 == null) {
			group1 = ServiceTestUtil.addGroup("Example1");
		}

		if (group11 == null) {
			group11 = ServiceTestUtil.addGroup(
				group1.getGroupId(), "Example11");
		}

		if (group111 == null) {
			group111 = ServiceTestUtil.addGroup(
				group11.getGroupId(), "Example111");
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group1.getGroupId());
		serviceContext.setUserId(user.getUserId());

		if (addGroup) {
			try {
				_addGroup(
					"Test 2", GroupConstants.DEFAULT_PARENT_GROUP_ID,
					serviceContext);

				Assert.fail(
					"The user should not be able to add top level sites");
			}
			catch (PrincipalException pe) {
			}

			try {
				_addGroup("Test 1.2", group1.getGroupId(), serviceContext);

				if (!hasManageSubsitePermisionOnGroup1 && !hasManageSite1) {
					Assert.fail("The user should not be able to add this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSubsitePermisionOnGroup1 || hasManageSite1) {
					Assert.fail("The user should be able to add this site");
				}
			}

			try {
				_addGroup("Test 1.1.2", group11.getGroupId(), serviceContext);

				if (!hasManageSubsitePermisionOnGroup11 && !hasManageSite1) {
					Assert.fail("The user should not be able to add this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSubsitePermisionOnGroup11 || hasManageSite1) {
					Assert.fail("The user should be able to add this site");
				}
			}

			try {
				_addGroup(
					"Test 1.1.1.2", group111.getGroupId(), serviceContext);

				if (!hasManageSubsitePermisionOnGroup111 && !hasManageSite1) {
					Assert.fail("The user should not be able to add this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSubsitePermisionOnGroup111 || hasManageSite1) {
					Assert.fail("The user should be able to add this site");
				}
			}
		}

		if (updateGroup) {
			try {
				GroupServiceUtil.updateGroup(group1.getGroupId(), "");

				if (!hasManageSite1) {
					Assert.fail(
						"The user should not be able to update this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSite1) {
					Assert.fail("The user should be able to update this site");
				}
			}

			try {
				GroupServiceUtil.updateGroup(group11.getGroupId(), "");

				if (!hasManageSubsitePermisionOnGroup1 && !hasManageSite1 &&
					!hasManageSite11) {

					Assert.fail(
						"The user should not be able to update this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSubsitePermisionOnGroup1 || hasManageSite1 ||
					hasManageSite11) {

					Assert.fail("The user should be able to update this site");
				}
			}

			try {
				GroupServiceUtil.updateGroup(group111.getGroupId(), "");

				if (!hasManageSubsitePermisionOnGroup11 && !hasManageSite1) {
					Assert.fail(
						"The user should not be able to update this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSubsitePermisionOnGroup1 || hasManageSite1) {
					Assert.fail("The user should be able to update this site");
				}
			}
		}
	}

}