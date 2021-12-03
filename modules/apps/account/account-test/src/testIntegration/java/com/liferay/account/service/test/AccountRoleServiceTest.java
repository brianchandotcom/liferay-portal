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

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.account.service.AccountRoleService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.account.service.test.util.UserRoleTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class AccountRoleServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_user = UserTestUtil.addUser();

		UserTestUtil.setUser(_user);
	}

	@After
	public void tearDown() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testAddAccountRole() throws Exception {
		UserRoleTestUtil.addResourcePermission(
			AccountActionKeys.ADD_ACCOUNT_ROLE, AccountEntry.class.getName(),
			_user.getUserId());

		_accountRoleService.addAccountRole(
			_accountEntry.getAccountEntryId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());
	}

	@Test(expected = PrincipalException.class)
	public void testAddAccountRoleWithoutPermission() throws Exception {
		_accountRoleService.addAccountRole(
			_accountEntry.getAccountEntryId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());
	}

	@Test
	public void testAssociateUser() throws Exception {
		AccountRole accountRole = _addAccountRole();

		UserRoleTestUtil.addResourcePermission(
			AccountActionKeys.ASSIGN_USERS, AccountRole.class.getName(),
			_user.getUserId());

		User user = UserTestUtil.addUser();

		_associateUser(accountRole.getAccountRoleId(), user.getUserId());

		Assert.assertTrue(
			_accountRoleLocalService.hasUserAccountRole(
				_accountEntry.getAccountEntryId(),
				accountRole.getAccountRoleId(), user.getUserId()));

		_accountRoleService.unassociateUser(
			_accountEntry.getAccountEntryId(), accountRole.getAccountRoleId(),
			user.getUserId());

		Assert.assertFalse(
			_accountRoleLocalService.hasUserAccountRole(
				_accountEntry.getAccountEntryId(),
				accountRole.getAccountRoleId(), user.getUserId()));
	}

	@Test(expected = PrincipalException.class)
	public void testAssociateUserWithoutPermission() throws Exception {
		AccountRole accountRole = _addAccountRole();

		User user = UserTestUtil.addUser();

		_associateUser(accountRole.getAccountRoleId(), user.getUserId());
	}

	@Test
	public void testDeleteAccountRole() throws Exception {
		AccountRole accountRole = _addAccountRole();

		UserRoleTestUtil.addResourcePermission(
			ActionKeys.DELETE, AccountRole.class.getName(), _user.getUserId());

		_accountRoleService.deleteAccountRole(accountRole);
	}

	@Test(expected = PrincipalException.class)
	public void testDeleteAccountRoleWithoutPermission() throws Exception {
		AccountRole accountRole = _addAccountRole();

		_accountRoleService.deleteAccountRole(accountRole);
	}

	private AccountRole _addAccountRole() throws Exception {
		return _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());
	}

	private void _associateUser(long accountRoleId, long userId)
		throws Exception {

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), userId);

		_accountRoleService.associateUser(
			_accountEntry.getAccountEntryId(), accountRoleId, userId);
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private AccountRoleService _accountRoleService;

	private User _user;

}