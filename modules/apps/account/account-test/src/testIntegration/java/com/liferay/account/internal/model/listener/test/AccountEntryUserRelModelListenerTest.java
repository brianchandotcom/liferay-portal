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

package com.liferay.account.internal.model.listener.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.test.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

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
public class AccountEntryUserRelModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAddAccountEntryUserRelsForUserWithDefaultAccount()
		throws Exception {

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntries.add(accountEntry);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), _user.getUserId());

		_accountEntryUserRelLocalService.deleteAccountEntryUserRels(
			accountEntry.getAccountEntryId(), new long[] {_user.getUserId()});

		_assertAccountEntryUserRelByAccountUserId(
			_user.getUserId(), AccountConstants.DEFAULT_ACCOUNT_ENTRY_ID);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), _user.getUserId());

		_assertAccountEntryUserRelByAccountUserId(
			_user.getUserId(), accountEntry.getAccountEntryId());
	}

	@Test
	public void testDeleteAccountEntryUserRelsForUserWithMultipleAccounts()
		throws Exception {

		AccountEntry accountEntry1 = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntries.add(accountEntry1);

		AccountEntry accountEntry2 = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntries.add(accountEntry2);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry1.getAccountEntryId(), _user.getUserId());
		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry2.getAccountEntryId(), _user.getUserId());

		List<AccountEntryUserRel> userAccountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(_user.getUserId());

		Assert.assertEquals(
			userAccountEntryUserRels.toString(), 2,
			userAccountEntryUserRels.size());

		_accountEntryUserRelLocalService.deleteAccountEntryUserRels(
			accountEntry1.getAccountEntryId(), new long[] {_user.getUserId()});

		_assertAccountEntryUserRelByAccountUserId(
			_user.getUserId(), accountEntry2.getAccountEntryId());
	}

	@Test
	public void testDeleteAccountEntryUserRelsForUserWithSingleAccount()
		throws Exception {

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntries.add(accountEntry);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), _user.getUserId());

		List<AccountEntryUserRel> userAccountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(_user.getUserId());

		Assert.assertEquals(
			userAccountEntryUserRels.toString(), 1,
			userAccountEntryUserRels.size());

		_accountEntryUserRelLocalService.deleteAccountEntryUserRels(
			accountEntry.getAccountEntryId(), new long[] {_user.getUserId()});

		_assertAccountEntryUserRelByAccountUserId(
			_user.getUserId(), AccountConstants.DEFAULT_ACCOUNT_ENTRY_ID);
	}

	private void _assertAccountEntryUserRelByAccountUserId(
		long accountUserId, long expectedAccountEntryId) {

		List<AccountEntryUserRel> accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(accountUserId);

		Assert.assertEquals(
			accountEntryUserRels.toString(), 1, accountEntryUserRels.size());

		AccountEntryUserRel accountEntryUserRel = accountEntryUserRels.get(0);

		Assert.assertEquals(
			expectedAccountEntryId, accountEntryUserRel.getAccountEntryId());
	}

	@DeleteAfterTestRun
	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@DeleteAfterTestRun
	private User _user;

}