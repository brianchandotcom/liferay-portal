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

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.role.AccountRoleManager;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

	@Test
	public void testAddAccountRole() throws Exception {
		List<Role> accountRoles = _getAccountRoles();

		Assert.assertEquals(accountRoles.toString(), 0, accountRoles.size());

		String name = RandomTestUtil.randomString(50);

		_roles.add(
			_accountRoleManager.addAccountRole(
				TestPropsValues.getUserId(), name,
				_getLocalizedValueMap(RandomTestUtil.randomString(50)),
				_getLocalizedValueMap(RandomTestUtil.randomString(50))));

		accountRoles = _getAccountRoles();

		Assert.assertEquals(accountRoles.toString(), 1, accountRoles.size());

		Role accountRole = accountRoles.get(0);

		Assert.assertEquals(name, accountRole.getName());

		Assert.assertEquals(
			AccountEntry.class.getName(), accountRole.getClassName());
		Assert.assertEquals(0, accountRole.getClassPK());
	}

	@Test
	public void testGetAccountRoles() throws Exception {
		List<Role> accountRoles = _accountRoleManager.getAccountRoles(
			TestPropsValues.getCompanyId());

		Assert.assertNotNull(accountRoles);

		Assert.assertEquals(accountRoles.toString(), 0, accountRoles.size());

		_addAccountRole();

		accountRoles = _accountRoleManager.getAccountRoles(
			TestPropsValues.getCompanyId());

		Assert.assertNotNull(accountRoles);

		Assert.assertEquals(accountRoles.toString(), 1, accountRoles.size());
	}

	private Role _addAccountRole() throws Exception {
		Role role = _roleLocalService.addRole(
			TestPropsValues.getUserId(), AccountEntry.class.getName(), 0,
			RandomTestUtil.randomString(50), null, null,
			AccountRoleConstants.TYPE_ACCOUNT, null, null);

		_roles.add(role);

		return role;
	}

	private List<Role> _getAccountRoles() {
		return _roleLocalService.getTypeRoles(
			AccountRoleConstants.TYPE_ACCOUNT);
	}

	private Map<Locale, String> _getLocalizedValueMap(String value) {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		return new HashMap<Locale, String>() {
			{
				put(locale, value);
			}
		};
	}

	@Inject
	private AccountRoleManager _accountRoleManager;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private final List<Role> _roles = new ArrayList<>();

}