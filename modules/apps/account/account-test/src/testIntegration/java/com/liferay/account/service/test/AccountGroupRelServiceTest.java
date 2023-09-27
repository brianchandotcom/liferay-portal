/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.test;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.account.service.AccountGroupRelService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.account.service.test.util.AccountGroupTestUtil;
import com.liferay.account.service.test.util.UserRoleTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class AccountGroupRelServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		UserTestUtil.setUser(_user);
	}

	@After
	public void tearDown() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testAddAccountGroupRel() throws Exception {
		UserRoleTestUtil.addResourcePermission(
			AccountActionKeys.ASSIGN_ACCOUNTS, AccountGroup.class.getName(),
			_user.getUserId());

		_addAccountGroupRel();
	}

	@Test(expected = PrincipalException.class)
	public void testAddAccountGroupRelWithoutPermission() throws Exception {
		_addAccountGroupRel();
	}

	@Test
	public void testDeleteAccountGroupRel() throws Exception {
		UserRoleTestUtil.addResourcePermission(
			AccountActionKeys.ASSIGN_ACCOUNTS, AccountGroup.class.getName(),
			_user.getUserId());

		_deleteAccountGroupRel();
	}

	@Test(expected = PrincipalException.class)
	public void testDeleteAccountGroupRelWithoutPermission() throws Exception {
		_deleteAccountGroupRel();
	}

	private void _addAccountGroupRel() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();
		AccountGroup accountGroup = AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_accountGroupRelService.addAccountGroupRel(
			accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());
	}

	private void _deleteAccountGroupRel() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();
		AccountGroup accountGroup = AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_accountGroupRelLocalService.addAccountGroupRel(
			accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		_accountGroupRelService.deleteAccountGroupRels(
			accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
			new long[] {accountEntry.getAccountEntryId()});
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Inject
	private AccountGroupRelService _accountGroupRelService;

	private User _user;

}