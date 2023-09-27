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
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
@DataGuard(scope = DataGuard.Scope.METHOD)
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
	public void testAddAccountEntryUserRelForAccountEntryTypeBusiness()
		throws Exception {

		AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withUsers(_user, UserTestUtil.addUser()));
	}

	@Test(expected = ModelListenerException.class)
	public void testAddAccountEntryUserRelForAccountEntryTypePerson()
		throws Exception {

		AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.TYPE_PERSON,
			AccountEntryArgs.withUsers(_user, UserTestUtil.addUser()));
	}

	@Test
	public void testDeleteAccountEntryUserRelsForUserWithMultipleAccountEntries()
		throws Exception {

		AccountEntry accountEntry1 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withUsers(_user));
		AccountEntry accountEntry2 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withUsers(_user));

		List<AccountEntryUserRel> userAccountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(_user.getUserId());

		Assert.assertEquals(
			userAccountEntryUserRels.toString(), 2,
			userAccountEntryUserRels.size());

		_accountEntryUserRelLocalService.deleteAccountEntryUserRels(
			accountEntry1.getAccountEntryId(), new long[] {_user.getUserId()});

		_assertGetAccountEntryUserRelByAccountUserId(
			_user.getUserId(), accountEntry2.getAccountEntryId());
	}

	@Test
	public void testDeleteAccountEntryUserRelsForUserWithSingleAccountEntry()
		throws Exception {

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withUsers(_user));

		List<AccountEntryUserRel> userAccountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(_user.getUserId());

		Assert.assertEquals(
			userAccountEntryUserRels.toString(), 1,
			userAccountEntryUserRels.size());

		_accountEntryUserRelLocalService.deleteAccountEntryUserRels(
			accountEntry.getAccountEntryId(), new long[] {_user.getUserId()});

		userAccountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(_user.getUserId());

		Assert.assertEquals(
			userAccountEntryUserRels.toString(), 0,
			userAccountEntryUserRels.size());
	}

	private void _assertGetAccountEntryUserRelByAccountUserId(
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

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	private User _user;

}