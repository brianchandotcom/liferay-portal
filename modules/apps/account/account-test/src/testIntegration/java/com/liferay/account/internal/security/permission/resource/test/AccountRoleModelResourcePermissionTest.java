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

package com.liferay.account.internal.security.permission.resource.test;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class AccountRoleModelResourcePermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAccountRolePermissions() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		AccountRole ownedAccountRole = _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), null, null);

		_testPermissions(Assert::assertTrue, accountEntry, ownedAccountRole);

		AccountRole sharedAccountRole = _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null);

		_testPermissions(Assert::assertFalse, accountEntry, sharedAccountRole);
	}

	@Test
	public void testViewPermissions() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);
		AccountRole accountRole = _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), null, null);

		AccountRole permissionAccountRole = _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), null, null);

		RoleTestUtil.addResourcePermission(
			permissionAccountRole.getRole(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
			AccountActionKeys.VIEW_ACCOUNT_ROLES);

		User userA = UserTestUtil.addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), userA.getUserId());

		_userGroupRoleLocalService.addUserGroupRole(
			userA.getUserId(), accountEntry.getAccountEntryGroupId(),
			permissionAccountRole.getRoleId());

		Assert.assertTrue(
			_accountRoleModelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(userA), accountRole,
				ActionKeys.VIEW));

		User userB = UserTestUtil.addUser();

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			role, Role.class.getName(), ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), ActionKeys.VIEW);

		UserLocalServiceUtil.addRoleUser(role.getRoleId(), userB.getUserId());

		Assert.assertTrue(
			_accountRoleModelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(userB), accountRole,
				ActionKeys.VIEW));
	}

	private void _addResourcePermission(
			Role role, String resourceName, String[] actionIds)
		throws Exception {

		for (String actionId : actionIds) {
			RoleTestUtil.addResourcePermission(
				role, resourceName, ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
				actionId);
		}
	}

	private void _testPermissions(
			Consumer<Boolean> assertConsumer, AccountEntry accountEntry,
			AccountRole accountRole)
		throws Exception {

		AccountRole permissionAccountRole =
			_accountRoleLocalService.addAccountRole(
				TestPropsValues.getUserId(), accountEntry.getAccountEntryId(),
				RandomTestUtil.randomString(), null, null);

		_addResourcePermission(
			permissionAccountRole.getRole(), AccountRole.class.getName(),
			new String[] {
				AccountActionKeys.ASSIGN_USERS, ActionKeys.DEFINE_PERMISSIONS,
				ActionKeys.DELETE, ActionKeys.UPDATE, ActionKeys.VIEW
			});

		User user = UserTestUtil.addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), user.getUserId());

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), accountEntry.getAccountEntryGroupId(),
			permissionAccountRole.getRoleId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		assertConsumer.accept(
			_accountRoleModelResourcePermission.contains(
				permissionChecker, accountRole,
				AccountActionKeys.ASSIGN_USERS));
		assertConsumer.accept(
			_accountRoleModelResourcePermission.contains(
				permissionChecker, accountRole, ActionKeys.DELETE));
		assertConsumer.accept(
			_accountRoleModelResourcePermission.contains(
				permissionChecker, accountRole, ActionKeys.UPDATE));
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject(filter = "model.class.name=com.liferay.account.model.AccountRole")
	private ModelResourcePermission<AccountRole>
		_accountRoleModelResourcePermission;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}