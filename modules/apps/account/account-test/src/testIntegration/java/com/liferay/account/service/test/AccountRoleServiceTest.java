/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.test;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.account.service.AccountRoleService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.account.service.test.util.UserRoleTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

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
		_accountEntry = AccountEntryTestUtil.addAccountEntry();

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

	@Test
	public void testSearchAccountRoles() throws Exception {
		AccountRole accountRole = _addAccountRole();

		_addAccountRole();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), _user.getUserId());

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_userLocalService.addRoleUser(role.getRoleId(), _user.getUserId());

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			accountRole.getCompanyId(), Role.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(accountRole.getRoleId()), role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		BaseModelSearchResult<AccountRole> baseModelSearchResult =
			_accountRoleService.searchAccountRoles(
				_accountEntry.getCompanyId(),
				new long[] {_accountEntry.getAccountEntryId()},
				StringPool.BLANK, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		Assert.assertEquals(1, baseModelSearchResult.getLength());

		List<AccountRole> accountRoles = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(accountRole, accountRoles.get(0));
	}

	@Test
	public void testSearchAccountRolesWithDefaultAccountEntryId()
		throws Exception {

		AccountRole accountRoleWithViewPermissions = _addAccountRole(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);
		AccountRole accountRoleWithoutViewPermissions = _addAccountRole(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_userLocalService.addRoleUser(role.getRoleId(), _user.getUserId());

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			accountRoleWithViewPermissions.getCompanyId(), Role.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(accountRoleWithViewPermissions.getRoleId()),
			role.getRoleId(), new String[] {ActionKeys.VIEW});

		BaseModelSearchResult<AccountRole> baseModelSearchResult =
			_accountRoleService.searchAccountRoles(
				_accountEntry.getCompanyId(),
				new long[] {AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT},
				StringPool.BLANK, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		List<AccountRole> accountRoles = baseModelSearchResult.getBaseModels();

		Assert.assertTrue(
			accountRoles.contains(accountRoleWithViewPermissions));
		Assert.assertFalse(
			accountRoles.contains(accountRoleWithoutViewPermissions));
	}

	private AccountRole _addAccountRole() throws Exception {
		return _addAccountRole(_accountEntry.getAccountEntryId());
	}

	private AccountRole _addAccountRole(long accountEntryId) throws Exception {
		return _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), accountEntryId,
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
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private AccountRoleService _accountRoleService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}