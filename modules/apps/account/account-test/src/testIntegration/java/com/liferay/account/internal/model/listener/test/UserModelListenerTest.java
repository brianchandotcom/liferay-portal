/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.model.listener.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.test.util.AccountEntryArgs;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class UserModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeleteUserWithMultipleAccountEntries() throws Exception {
		User user = UserTestUtil.addUser();

		List<AccountEntry> accountEntries =
			AccountEntryTestUtil.addAccountEntries(
				2, AccountEntryArgs.withUsers(user));

		List<AccountEntryUserRel> accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(user.getUserId());

		Assert.assertEquals(
			accountEntryUserRels.toString(), accountEntries.size(),
			accountEntryUserRels.size());

		_userLocalService.deleteUser(user);

		accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(user.getUserId());

		Assert.assertTrue(ListUtil.isEmpty(accountEntryUserRels));
	}

	@Test
	public void testDeleteUserWithSingleAccountEntry() throws Exception {
		User user = UserTestUtil.addUser();

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withUsers(user));

		Assert.assertTrue(
			_accountEntryUserRelLocalService.hasAccountEntryUserRel(
				accountEntry.getAccountEntryId(), user.getUserId()));

		_userLocalService.deleteUser(user);

		Assert.assertFalse(
			_accountEntryUserRelLocalService.hasAccountEntryUserRel(
				accountEntry.getAccountEntryId(), user.getUserId()));
	}

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private UserLocalService _userLocalService;

}