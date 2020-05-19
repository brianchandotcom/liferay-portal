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

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Albert Lee
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountGroupLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddAccountGroup() throws Exception {
		AccountGroup accountGroup = _addAccountGroup();

		Assert.assertNotNull(
			_accountGroupLocalService.fetchAccountGroup(
				accountGroup.getAccountGroupId()));
	}

	@Test
	public void testDeleteAccountGroupByModel() throws Exception {
		AccountGroup accountGroup = _addAccountGroup();

		_accountGroupLocalService.deleteAccountGroup(accountGroup);

		_assertDeleted(accountGroup.getAccountGroupId());
	}

	@Test
	public void testDeleteAccountGroupByPrimaryKey() throws Exception {
		AccountGroup accountGroup = _addAccountGroup();

		_accountGroupLocalService.deleteAccountGroup(
			accountGroup.getAccountGroupId());

		_assertDeleted(accountGroup.getAccountGroupId());
	}

	private AccountGroup _addAccountGroup() throws Exception {
		return AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	private void _assertDeleted(long accountGroupId) {
		Assert.assertNull(
			_accountGroupLocalService.fetchAccountGroup(accountGroupId));
	}

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

}