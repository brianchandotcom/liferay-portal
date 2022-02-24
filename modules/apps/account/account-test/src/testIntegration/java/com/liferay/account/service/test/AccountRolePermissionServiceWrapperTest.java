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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.account.service.test.util.UserRoleTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PermissionService;
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
public class AccountRolePermissionServiceWrapperTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountRole = _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_user = UserTestUtil.addUser();

		UserTestUtil.setUser(_user);
	}

	@After
	public void tearDown() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testCheckPermission() throws Exception {
		UserRoleTestUtil.addResourcePermission(
			ActionKeys.DEFINE_PERMISSIONS, AccountRole.class.getName(),
			_user.getUserId());

		_permissionService.checkPermission(
			TestPropsValues.getGroupId(), Role.class.getName(),
			_accountRole.getRoleId());
	}

	@Test(expected = PrincipalException.class)
	public void testCheckPermissionWithoutPermission() throws Exception {
		_permissionService.checkPermission(
			TestPropsValues.getGroupId(), Role.class.getName(),
			_accountRole.getRoleId());
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private AccountRole _accountRole;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private PermissionService _permissionService;

	private User _user;

}