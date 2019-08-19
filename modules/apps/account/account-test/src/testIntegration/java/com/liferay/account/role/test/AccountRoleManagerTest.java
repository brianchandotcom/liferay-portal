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

package com.liferay.account.role.test;

import com.liferay.account.role.AccountRole;
import com.liferay.account.role.AccountRoleManager;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountRoleManagerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

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
		List<AccountRole> accountRoles = _accountRoleManager.getAccountRoles(
			TestPropsValues.getCompanyId());

		Assert.assertEquals(accountRoles.toString(), 0, accountRoles.size());

		String name = RandomTestUtil.randomString(50);

		_addAccountRole(name);

		accountRoles = _accountRoleManager.getAccountRoles(
			TestPropsValues.getCompanyId());

		Assert.assertEquals(accountRoles.toString(), 1, accountRoles.size());

		AccountRole accountRole = accountRoles.get(0);

		Assert.assertEquals("lfr-account-" + name, accountRole.getName());
	}

	@Test
	public void testGetAccountRoles() throws Exception {
		List<AccountRole> accountRoles = _accountRoleManager.getAccountRoles(
			TestPropsValues.getCompanyId());

		Assert.assertNotNull(accountRoles);

		Assert.assertEquals(accountRoles.toString(), 0, accountRoles.size());

		_addAccountRole(RandomTestUtil.randomString(50));

		accountRoles = _accountRoleManager.getAccountRoles(
			TestPropsValues.getCompanyId());

		Assert.assertNotNull(accountRoles);

		Assert.assertEquals(accountRoles.toString(), 1, accountRoles.size());
	}

	private AccountRole _addAccountRole(String name) throws Exception {
		AccountRole accountRole = _accountRoleManager.addAccountRole(
			TestPropsValues.getUserId(), name, null, null);

		_names.add(accountRole.getName());

		return accountRole;
	}

	@Inject
	private AccountRoleManager _accountRoleManager;

	private final List<String> _names = new ArrayList<>();

	@Inject
	private RoleLocalService _roleLocalService;

}