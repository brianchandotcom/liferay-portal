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
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupService;
import com.liferay.account.service.test.util.AccountGroupTestUtil;
import com.liferay.account.service.test.util.UserRoleTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.PortletKeys;
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
public class AccountGroupServiceTest {

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
	public void testAddAccountGroup() throws Exception {
		UserRoleTestUtil.addResourcePermission(
			AccountActionKeys.ADD_ACCOUNT_GROUP, PortletKeys.PORTAL,
			_user.getUserId());

		_accountGroupService.addAccountGroup(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Test(expected = PrincipalException.class)
	public void testAddAccountGroupWithoutPermission() throws Exception {
		_accountGroupService.addAccountGroup(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Test
	public void testDeleteAccountGroup() throws Exception {
		AccountGroup accountGroup = AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		UserRoleTestUtil.addResourcePermission(
			ActionKeys.DELETE, AccountGroup.class.getName(), _user.getUserId());

		_accountGroupService.deleteAccountGroup(
			accountGroup.getAccountGroupId());
	}

	@Test(expected = PrincipalException.class)
	public void testDeleteAccountGroupWithoutPermission() throws Exception {
		AccountGroup accountGroup = AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_accountGroupService.deleteAccountGroup(
			accountGroup.getAccountGroupId());
	}

	@Test
	public void testSearchAccountGroups() throws Exception {
		AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		UserRoleTestUtil.addResourcePermission(
			ActionKeys.VIEW, AccountGroup.class.getName(), _user.getUserId());

		OrderByComparator<AccountGroup> orderByComparator =
			OrderByComparatorFactoryUtil.create("AccountGroup", "name", true);

		BaseModelSearchResult<AccountGroup> baseModelSearchResult =
			_accountGroupService.searchAccountGroups(
				_user.getCompanyId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, orderByComparator);

		List<AccountGroup> expectedAccountGroups =
			_accountGroupLocalService.getAccountGroups(
				_user.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				orderByComparator);

		Assert.assertEquals(
			expectedAccountGroups.size(), baseModelSearchResult.getLength());
		Assert.assertEquals(
			expectedAccountGroups, baseModelSearchResult.getBaseModels());
	}

	@Test
	public void testSearchAccountGroupsWithoutPermission() throws Exception {
		AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		BaseModelSearchResult<AccountGroup> baseModelSearchResult =
			_accountGroupService.searchAccountGroups(
				_user.getCompanyId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(0, baseModelSearchResult.getLength());
		Assert.assertTrue(
			ListUtil.isEmpty(baseModelSearchResult.getBaseModels()));
	}

	@Test
	public void testUpdateAccountGroup() throws Exception {
		AccountGroup accountGroup = AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		UserRoleTestUtil.addResourcePermission(
			ActionKeys.UPDATE, AccountGroup.class.getName(), _user.getUserId());

		_accountGroupService.updateAccountGroup(
			accountGroup.getAccountGroupId(), RandomTestUtil.randomString(),
			accountGroup.getName());
	}

	@Test(expected = PrincipalException.class)
	public void testUpdateAccountGroupWithoutPermission() throws Exception {
		AccountGroup accountGroup = AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_accountGroupService.updateAccountGroup(
			accountGroup.getAccountGroupId(), RandomTestUtil.randomString(),
			accountGroup.getName());
	}

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private AccountGroupService _accountGroupService;

	private User _user;

}