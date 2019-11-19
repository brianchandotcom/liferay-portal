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

package com.liferay.account.service.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountRoleLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry1 = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);
		_accountEntry2 = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);
	}

	@After
	public void tearDown() throws Exception {
		for (String name : _names) {
			Role role = _roleLocalService.fetchRole(
				TestPropsValues.getCompanyId(), name);

			if (role != null) {
				_roleLocalService.deleteRole(role);
			}
		}
	}

	@Test
	public void testAddAccountRole() throws Exception {
		List<AccountRole> accountRoles =
			_accountRoleLocalService.getAccountRolesByAccountEntryIds(
				new long[] {_accountEntry1.getAccountEntryId()});

		Assert.assertEquals(accountRoles.toString(), 0, accountRoles.size());

		String name = RandomTestUtil.randomString(50);

		_addAccountRole(_accountEntry1.getAccountEntryId(), name);

		accountRoles =
			_accountRoleLocalService.getAccountRolesByAccountEntryIds(
				new long[] {_accountEntry1.getAccountEntryId()});

		Assert.assertEquals(accountRoles.toString(), 1, accountRoles.size());

		AccountRole accountRole = accountRoles.get(0);

		Assert.assertEquals("lfr-account-" + name, accountRole.getRoleName());
	}

	@Test
	public void testAssociateUser() throws Exception {
		AccountRole accountRole = _addAccountRole(
			_accountEntry1.getAccountEntryId(), RandomTestUtil.randomString());

		User user = UserTestUtil.addUser();

		_users.add(user);

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				_accountEntry1.getAccountEntryId(), user.getUserId());

		_accountEntryUserRels.add(accountEntryUserRel);

		Assert.assertFalse(
			_hasRoleId(
				_accountEntry1.getAccountEntryId(), accountRole.getRoleId(),
				user.getUserId()));

		_accountRoleLocalService.associateUser(
			_accountEntry1.getAccountEntryId(), accountRole.getAccountRoleId(),
			user.getUserId());

		Assert.assertTrue(
			_hasRoleId(
				_accountEntry1.getAccountEntryId(), accountRole.getRoleId(),
				user.getUserId()));

		_accountRoleLocalService.unassociateUser(
			_accountEntry1.getAccountEntryId(), accountRole.getAccountRoleId(),
			user.getUserId());

		Assert.assertFalse(
			_hasRoleId(
				_accountEntry1.getAccountEntryId(), accountRole.getRoleId(),
				user.getUserId()));

		// Permissions

		AccountRole defaultAccountRole = _addAccountRole(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString());

		long[] roleIds = _getRoleIds(user);

		Assert.assertFalse(
			ArrayUtil.contains(roleIds, accountRole.getRoleId()));
		Assert.assertFalse(
			ArrayUtil.contains(roleIds, defaultAccountRole.getRoleId()));

		_accountRoleLocalService.associateUser(
			_accountEntry1.getAccountEntryId(), accountRole.getAccountRoleId(),
			user.getUserId());
		_accountRoleLocalService.associateUser(
			_accountEntry1.getAccountEntryId(),
			defaultAccountRole.getAccountRoleId(), user.getUserId());

		roleIds = _getRoleIds(user);

		Assert.assertTrue(ArrayUtil.contains(roleIds, accountRole.getRoleId()));
		Assert.assertTrue(
			ArrayUtil.contains(roleIds, defaultAccountRole.getRoleId()));

		_assertHasPermission(user, ActionKeys.DELETE, false);
		_assertHasPermission(user, ActionKeys.MANAGE_USERS, false);
		_assertHasPermission(user, ActionKeys.UPDATE, false);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			accountRole.getRoleId(), ActionKeys.DELETE);
		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(_accountEntry1.getAccountEntryGroupId()),
			accountRole.getRoleId(), ActionKeys.MANAGE_USERS);
		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
			defaultAccountRole.getRoleId(), ActionKeys.UPDATE);

		_assertHasPermission(user, ActionKeys.DELETE, true);
		_assertHasPermission(user, ActionKeys.MANAGE_USERS, true);
		_assertHasPermission(user, ActionKeys.UPDATE, true);

		_accountEntryUserRelLocalService.deleteAccountEntryUserRel(
			accountEntryUserRel);

		_accountEntryUserRels.remove(accountEntryUserRel);

		_assertHasPermission(user, ActionKeys.DELETE, true);
		_assertHasPermission(user, ActionKeys.MANAGE_USERS, true);
		_assertHasPermission(user, ActionKeys.UPDATE, true);
	}

	@Test
	public void testGetAccountRoles() throws Exception {
		List<AccountRole> accountRoles =
			_accountRoleLocalService.getAccountRolesByAccountEntryIds(
				new long[] {_accountEntry1.getAccountEntryId()});

		Assert.assertNotNull(accountRoles);

		Assert.assertEquals(accountRoles.toString(), 0, accountRoles.size());

		_addAccountRole(
			_accountEntry1.getAccountEntryId(),
			RandomTestUtil.randomString(50));
		_addAccountRole(
			_accountEntry2.getAccountEntryId(),
			RandomTestUtil.randomString(50));

		accountRoles =
			_accountRoleLocalService.getAccountRolesByAccountEntryIds(
				new long[] {_accountEntry1.getAccountEntryId()});

		Assert.assertNotNull(accountRoles);

		Assert.assertEquals(accountRoles.toString(), 1, accountRoles.size());
	}

	@Test
	public void testGetAccountRolesMultipleAccountEntries() throws Exception {
		List<AccountRole> accountRoles = new ArrayList<>();

		accountRoles.add(
			_addAccountRole(
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
				RandomTestUtil.randomString(50)));
		accountRoles.add(
			_addAccountRole(
				_accountEntry1.getAccountEntryId(),
				RandomTestUtil.randomString(50)));

		_addAccountRole(
			_accountEntry2.getAccountEntryId(),
			RandomTestUtil.randomString(50));

		List<AccountRole> actualAccountRoles =
			_accountRoleLocalService.getAccountRolesByAccountEntryIds(
				new long[] {
					AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
					_accountEntry1.getAccountEntryId()
				});

		Assert.assertEquals(
			actualAccountRoles.toString(), 2, actualAccountRoles.size());

		long[] expectedRoleIds = ListUtil.toLongArray(
			accountRoles, AccountRole::getRoleId);

		Arrays.sort(expectedRoleIds);

		long[] actualRoleIds = ListUtil.toLongArray(
			actualAccountRoles, AccountRole::getRoleId);

		Arrays.sort(actualRoleIds);

		Assert.assertArrayEquals(expectedRoleIds, actualRoleIds);
	}

	private AccountRole _addAccountRole(long accountEntryId, String name)
		throws Exception {

		AccountRole accountRole = _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), accountEntryId, name, null, null);

		_names.add(accountRole.getRoleName());

		return accountRole;
	}

	private void _assertHasPermission(
		User user, String actionKey, boolean hasPermission) {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		Assert.assertEquals(
			hasPermission,
			permissionChecker.hasPermission(
				_accountEntry1.getAccountEntryGroup(),
				AccountEntry.class.getName(),
				_accountEntry1.getAccountEntryId(), actionKey));
	}

	private long[] _getRoleIds(User user) throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		return permissionChecker.getRoleIds(
			user.getUserId(), _accountEntry1.getAccountEntryGroupId());
	}

	private boolean _hasRoleId(
			long accountEntryId, long roleId, long accountUserId)
		throws Exception {

		long[] accountRoleIds = ListUtil.toLongArray(
			_accountRoleLocalService.getAccountRoles(
				accountEntryId, accountUserId),
			AccountRole::getRoleId);

		return ArrayUtil.contains(accountRoleIds, roleId);
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry1;

	@DeleteAfterTestRun
	private AccountEntry _accountEntry2;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@DeleteAfterTestRun
	private final List<AccountEntryUserRel> _accountEntryUserRels =
		new ArrayList<>();

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	private final List<String> _names = new ArrayList<>();

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}