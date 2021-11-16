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
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class AccountGroupServiceTest extends BaseResourcePermissionTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddAccountGroup() throws Exception {
		addResourcePermission(
			PortletKeys.PORTAL, AccountActionKeys.ADD_ACCOUNT_GROUP);

		_accountGroupService.addAccountGroup(
			user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Test(expected = PrincipalException.class)
	public void testAddAccountGroupWithoutPermission() throws Exception {
		_accountGroupService.addAccountGroup(
			user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Test
	public void testDeleteAccountGroup() throws Exception {
		AccountGroup accountGroup = AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		addResourcePermission(AccountGroup.class.getName(), ActionKeys.DELETE);

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
	public void testUpdateAccountGroup() throws Exception {
		AccountGroup accountGroup = AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		addResourcePermission(AccountGroup.class.getName(), ActionKeys.UPDATE);

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

}