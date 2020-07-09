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

import com.liferay.account.exception.DuplicateAccountGroupAccountEntryRelException;
import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.exception.NoSuchGroupException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupAccountEntryRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupAccountEntryRelLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.account.service.test.util.AccountGroupTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
 * @author Albert Lee
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountGroupAccountEntryRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountGroup = AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Test
	public void testAddAccountGroupAccountEntryRel() throws Exception {
		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			_accountGroupAccountEntryRelLocalService.
				addAccountGroupAccountEntryRel(
					_accountGroup.getAccountGroupId(),
					_accountEntry.getAccountEntryId());

		Assert.assertNotNull(accountGroupAccountEntryRel);
		Assert.assertNotNull(
			_accountGroupAccountEntryRelLocalService.
				fetchAccountGroupAccountEntryRel(
					accountGroupAccountEntryRel.getPrimaryKey()));
	}

	@Test
	public void testAddAccountGroupAccountEntryRels() throws Exception {
		List<AccountEntry> accountEntries = new ArrayList<>();

		accountEntries.add(
			AccountEntryTestUtil.addAccountEntry(
				_accountEntryLocalService, RandomTestUtil.randomString(),
				RandomTestUtil.randomString()));
		accountEntries.add(
			AccountEntryTestUtil.addAccountEntry(
				_accountEntryLocalService, RandomTestUtil.randomString(),
				RandomTestUtil.randomString()));

		_accountGroupAccountEntryRelLocalService.
			addAccountGroupAccountEntryRels(
				_accountGroup.getAccountGroupId(),
				ListUtil.toLongArray(
					accountEntries, AccountEntry.ACCOUNT_ENTRY_ID_ACCESSOR));

		Assert.assertEquals(
			2,
			_accountGroupAccountEntryRelLocalService.
				getAccountGroupAccountEntryRelsCountByAccountGroupId(
					_accountGroup.getAccountGroupId()));

		List<AccountGroupAccountEntryRel> accountGroupAccountEntryRels =
			_accountGroupAccountEntryRelLocalService.
				getAccountGroupAccountEntryRelsByAccountGroupId(
					_accountGroup.getAccountGroupId());

		for (AccountGroupAccountEntryRel accountGroupAccountEntryRel :
				accountGroupAccountEntryRels) {

			Assert.assertEquals(
				_accountGroup.getAccountGroupId(),
				accountGroupAccountEntryRel.getAccountGroupId());

			long[] accountEntryIds = ListUtil.toLongArray(
				accountEntries, AccountEntry.ACCOUNT_ENTRY_ID_ACCESSOR);

			Assert.assertTrue(
				ArrayUtil.contains(
					accountEntryIds,
					accountGroupAccountEntryRel.getAccountEntryId()));
		}
	}

	@Test(expected = DuplicateAccountGroupAccountEntryRelException.class)
	public void testAddAccountGroupAccountEntryRelThrowsDuplicateAccountGroupAccountEntryRelException()
		throws Exception {

		_accountGroupAccountEntryRelLocalService.addAccountGroupAccountEntryRel(
			_accountGroup.getAccountGroupId(),
			_accountEntry.getAccountEntryId());

		_accountGroupAccountEntryRelLocalService.addAccountGroupAccountEntryRel(
			_accountGroup.getAccountGroupId(),
			_accountEntry.getAccountEntryId());
	}

	@Test(expected = NoSuchEntryException.class)
	public void testAddAccountGroupAccountEntryRelThrowsNoSuchEntryException()
		throws Exception {

		_accountGroupAccountEntryRelLocalService.addAccountGroupAccountEntryRel(
			_accountGroup.getAccountGroupId(),
			_accountEntry.getAccountEntryId() + RandomTestUtil.nextLong());
	}

	@Test(expected = NoSuchGroupException.class)
	public void testAddAccountGroupAccountEntryRelThrowsNoSuchGroupException()
		throws Exception {

		_accountGroupAccountEntryRelLocalService.addAccountGroupAccountEntryRel(
			_accountGroup.getAccountGroupId() + RandomTestUtil.nextLong(),
			_accountEntry.getAccountEntryId());
	}

	@Test
	public void testDeleteAccountGroupAccountEntryRels() throws Exception {
		_accountGroupAccountEntryRelLocalService.
			addAccountGroupAccountEntryRels(
				_accountGroup.getAccountGroupId(),
				new long[] {_accountEntry.getAccountEntryId()});

		List<AccountGroupAccountEntryRel> accountGroupAccountEntryRels =
			_accountGroupAccountEntryRelLocalService.
				getAccountGroupAccountEntryRelsByAccountGroupId(
					_accountGroup.getAccountGroupId());

		Assert.assertEquals(
			accountGroupAccountEntryRels.toString(), 1,
			accountGroupAccountEntryRels.size());

		_accountGroupAccountEntryRelLocalService.
			deleteAccountGroupAccountEntryRels(
				_accountGroup.getAccountGroupId(),
				new long[] {_accountEntry.getAccountEntryId()});

		Assert.assertEquals(
			0,
			_accountGroupAccountEntryRelLocalService.
				getAccountGroupAccountEntryRelsCountByAccountGroupId(
					_accountGroup.getAccountGroupId()));
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private AccountGroup _accountGroup;

	@Inject
	private AccountGroupAccountEntryRelLocalService
		_accountGroupAccountEntryRelLocalService;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

}