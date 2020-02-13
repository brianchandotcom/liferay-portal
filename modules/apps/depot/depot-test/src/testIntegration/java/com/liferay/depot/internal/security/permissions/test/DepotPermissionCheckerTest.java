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

package com.liferay.depot.internal.security.permissions.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class DepotPermissionCheckerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testIsGroupAdminWithDepotGroupAndDepotAdmin() throws Exception {
		User user = UserTestUtil.addUser();

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), "Depot Administrator");

		try {
			_userGroupRoleLocalService.addUserGroupRoles(
				user.getUserId(), depotEntry.getGroupId(),
				new long[] {role.getRoleId()});

			_userLocalService.addGroupUsers(
				depotEntry.getGroupId(), new long[] {user.getUserId()});

			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			Assert.assertTrue(
				permissionChecker.isGroupAdmin(depotEntry.getGroupId()));
		}
		finally {
			_depotEntryLocalService.deleteDepotEntry(depotEntry);

			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testIsGroupAdminWithDepotGroupAndDepotMember()
		throws Exception {

		User user = UserTestUtil.addUser();

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		try {
			_userLocalService.addGroupUsers(
				depotEntry.getGroupId(), new long[] {user.getUserId()});

			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			Assert.assertFalse(
				permissionChecker.isGroupAdmin(depotEntry.getGroupId()));
		}
		finally {
			_depotEntryLocalService.deleteDepotEntry(depotEntry);

			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testIsGroupAdminWithDepotGroupAndDepotOwner() throws Exception {
		User user = UserTestUtil.addUser();

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), user.getUserId()));

		try {
			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			Assert.assertTrue(
				permissionChecker.isGroupAdmin(depotEntry.getGroupId()));
		}
		finally {
			_userLocalService.deleteUser(user);

			_depotEntryLocalService.deleteDepotEntry(depotEntry);
		}
	}

	@Test
	public void testIsGroupAdminWithGroup0AndNoOmniAdmin() throws Exception {
		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		Assert.assertFalse(permissionChecker.isGroupAdmin(0));
	}

	@Test
	public void testIsGroupAdminWithGuestUser() throws PortalException {
		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		Assert.assertFalse(
			permissionChecker.isGroupAdmin(TestPropsValues.getGroupId()));
	}

	@Test
	public void testIsGroupAdminWithOmniAdmin() throws PortalException {
		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			TestPropsValues.getUser());

		Assert.assertTrue(permissionChecker.isGroupAdmin(0));
	}

	@Test
	public void testIsGroupAdminWithSiteOwner() throws PortalException {
		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isGroupAdmin(TestPropsValues.getGroupId()));
	}

	@Test
	public void testIsGroupMemberWithDepotGroupAndDepotAdmin()
		throws Exception {

		User user = UserTestUtil.addUser();

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), "Depot Administrator");

		try {
			_userGroupRoleLocalService.addUserGroupRoles(
				user.getUserId(), depotEntry.getGroupId(),
				new long[] {role.getRoleId()});

			_userLocalService.addGroupUsers(
				depotEntry.getGroupId(), new long[] {user.getUserId()});

			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			Assert.assertTrue(
				permissionChecker.isGroupMember(depotEntry.getGroupId()));
		}
		finally {
			_depotEntryLocalService.deleteDepotEntry(depotEntry);

			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testIsGroupMemberWithDepotGroupAndDepotMember()
		throws Exception {

		User user = UserTestUtil.addUser();

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		try {
			_userLocalService.addGroupUsers(
				depotEntry.getGroupId(), new long[] {user.getUserId()});

			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			Assert.assertTrue(
				permissionChecker.isGroupMember(depotEntry.getGroupId()));
		}
		finally {
			_depotEntryLocalService.deleteDepotEntry(depotEntry);

			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testIsGroupMemberWithDepotGroupAndDepotOwner()
		throws Exception {

		User user = UserTestUtil.addUser();

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), user.getUserId()));

		try {
			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			Assert.assertTrue(
				permissionChecker.isGroupMember(depotEntry.getGroupId()));
		}
		finally {
			_userLocalService.deleteUser(user);

			_depotEntryLocalService.deleteDepotEntry(depotEntry);
		}
	}

	@Test
	public void testIsGroupMemberWithGroup0AndNoOmniAdmin() throws Exception {
		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		Assert.assertFalse(permissionChecker.isGroupMember(0));
	}

	@Test
	public void testIsGroupMemberWithGuestUser() throws PortalException {
		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		Assert.assertFalse(
			permissionChecker.isGroupMember(TestPropsValues.getGroupId()));
	}

	@Test
	public void testIsGroupMemberWithOmniAdmin() throws PortalException {
		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			TestPropsValues.getUser());

		Assert.assertFalse(permissionChecker.isGroupMember(0));
	}

	@Test
	public void testIsGroupMemberWithSiteOwner() throws PortalException {
		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isGroupMember(TestPropsValues.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithDepotGroupAndDepotAdmin() throws Exception {
		User user = UserTestUtil.addUser();

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), "Depot Administrator");

		try {
			_userGroupRoleLocalService.addUserGroupRoles(
				user.getUserId(), depotEntry.getGroupId(),
				new long[] {role.getRoleId()});

			_userLocalService.addGroupUsers(
				depotEntry.getGroupId(), new long[] {user.getUserId()});

			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			Assert.assertFalse(
				permissionChecker.isGroupOwner(depotEntry.getGroupId()));
		}
		finally {
			_depotEntryLocalService.deleteDepotEntry(depotEntry);

			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testIsGroupOwnerWithDepotGroupAndDepotMember()
		throws Exception {

		User user = UserTestUtil.addUser();

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		try {
			_userLocalService.addGroupUsers(
				depotEntry.getGroupId(), new long[] {user.getUserId()});

			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			Assert.assertFalse(
				permissionChecker.isGroupOwner(depotEntry.getGroupId()));
		}
		finally {
			_depotEntryLocalService.deleteDepotEntry(depotEntry);

			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testIsGroupOwnerWithDepotGroupAndDepotOwner() throws Exception {
		User user = UserTestUtil.addUser();

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), user.getUserId()));

		try {
			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			Assert.assertTrue(
				permissionChecker.isGroupOwner(depotEntry.getGroupId()));
		}
		finally {
			_userLocalService.deleteUser(user);

			_depotEntryLocalService.deleteDepotEntry(depotEntry);
		}
	}

	@Test
	public void testIsGroupOwnerWithGroup0AndNoOmniAdmin() throws Exception {
		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		Assert.assertFalse(permissionChecker.isGroupOwner(0));
	}

	@Test
	public void testIsGroupOwnerWithGuestUser() throws PortalException {
		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		Assert.assertFalse(
			permissionChecker.isGroupOwner(TestPropsValues.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithOmniAdmin() throws PortalException {
		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			TestPropsValues.getUser());

		Assert.assertTrue(permissionChecker.isGroupOwner(0));
	}

	@Test
	public void testIsGroupOwnerWithSiteOwner() throws PortalException {
		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isGroupOwner(TestPropsValues.getGroupId()));
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}