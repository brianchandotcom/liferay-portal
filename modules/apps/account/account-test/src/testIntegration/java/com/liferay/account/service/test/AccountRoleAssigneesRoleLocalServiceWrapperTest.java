/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bruno Queiroz
 * @author Erick Monteiro
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountRoleAssigneesRoleLocalServiceWrapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetAssigneesTotalForAccountSpecificAccountRole()
		throws Exception {

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		AccountRole accountRole = _addAccountRole(
			accountEntry.getAccountEntryId(), RandomTestUtil.randomString());

		User user = UserTestUtil.addUser();

		_accountRoleLocalService.associateUser(
			accountEntry.getAccountEntryId(), accountRole.getAccountRoleId(),
			user.getUserId());

		Assert.assertEquals(
			1, _roleLocalService.getAssigneesTotal(accountRole.getRoleId()));

		_userLocalService.updateStatus(
			user.getUserId(), WorkflowConstants.STATUS_INACTIVE,
			new ServiceContext());

		Assert.assertEquals(
			0, _roleLocalService.getAssigneesTotal(accountRole.getRoleId()));
	}

	@Test
	public void testGetAssigneesTotalForSharedAccountRole() throws Exception {
		AccountEntry accountEntry1 = AccountEntryTestUtil.addAccountEntry();
		AccountEntry accountEntry2 = AccountEntryTestUtil.addAccountEntry();

		AccountRole accountRole = _addAccountRole(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString());

		User user1 = UserTestUtil.addUser();

		_accountRoleLocalService.associateUser(
			accountEntry1.getAccountEntryId(), accountRole.getAccountRoleId(),
			user1.getUserId());

		_accountRoleLocalService.associateUser(
			accountEntry2.getAccountEntryId(), accountRole.getAccountRoleId(),
			user1.getUserId());

		Assert.assertEquals(
			1, _roleLocalService.getAssigneesTotal(accountRole.getRoleId()));

		User user2 = UserTestUtil.addUser();

		_accountRoleLocalService.associateUser(
			accountEntry2.getAccountEntryId(), accountRole.getAccountRoleId(),
			user2.getUserId());

		Assert.assertEquals(
			2, _roleLocalService.getAssigneesTotal(accountRole.getRoleId()));
	}

	private AccountRole _addAccountRole(long accountEntryId, String name)
		throws Exception {

		return _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), accountEntryId, name, null, null);
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}