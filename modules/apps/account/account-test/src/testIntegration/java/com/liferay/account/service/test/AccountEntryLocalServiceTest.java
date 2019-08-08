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
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.helper.AccountEntryTestHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntryTestHelper = new AccountEntryTestHelper(
			_accountEntryLocalService, TestPropsValues.getUserId());
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AccountEntry> accountEntryIterator =
			_accountEntries.iterator();

		while (accountEntryIterator.hasNext()) {
			AccountEntry accountEntry = accountEntryIterator.next();

			accountEntry = _accountEntryLocalService.fetchAccountEntry(
				accountEntry.getAccountEntryId());

			if (accountEntry != null) {
				_accountEntryLocalService.deleteAccountEntry(accountEntry);
			}
		}
	}

	@Test
	public void testActivateAccountEntries() throws Exception {
		long[] accountEntryIds = _addMultipleAccountEntries(
			this::_addInactiveAccountEntry);

		_accountEntryLocalService.activateAccountEntries(accountEntryIds);

		_assertMultiple(
			accountEntryIds,
			accountEntry -> Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, accountEntry.getStatus()));
	}

	@Test
	public void testDeactivateAccountEntries() throws Exception {
		long[] accountEntryIds = _addMultipleAccountEntries(
			this::_addAccountEntry);

		_accountEntryLocalService.deactivateAccountEntries(accountEntryIds);

		_assertMultiple(
			accountEntryIds,
			accountEntry -> Assert.assertEquals(
				WorkflowConstants.STATUS_INACTIVE, accountEntry.getStatus()));
	}

	@Test
	public void testDeleteAccountEntries() throws Exception {
		long[] accountEntryIds = _addMultipleAccountEntries(
			this::_addAccountEntry);

		_accountEntryLocalService.deleteAccountEntries(accountEntryIds);

		_assertMultiple(accountEntryIds, Assert::assertNull);

		for (long accountEntryId : accountEntryIds) {
			int resourcePermissionsCount =
				_resourcePermissionLocalService.getResourcePermissionsCount(
					TestPropsValues.getCompanyId(),
					AccountEntry.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(accountEntryId));

			Assert.assertEquals(0, resourcePermissionsCount);
		}
	}

	private AccountEntry _addAccountEntry() throws Exception {
		return _addAccountEntry(_accountEntryTestHelper::addAccountEntry);
	}

	private AccountEntry _addAccountEntry(
			UnsafeSupplier<AccountEntry, Exception> accountEntryUnsafeSupplier)
		throws Exception {

		AccountEntry accountEntry = accountEntryUnsafeSupplier.get();

		_accountEntries.add(accountEntry);

		accountEntry = _accountEntryLocalService.fetchAccountEntry(
			accountEntry.getAccountEntryId());

		Assert.assertNotNull(accountEntry);

		int resourcePermissionsCount =
			_resourcePermissionLocalService.getResourcePermissionsCount(
				TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(accountEntry.getAccountEntryId()));

		Assert.assertEquals(1, resourcePermissionsCount);

		return accountEntry;
	}

	private AccountEntry _addInactiveAccountEntry() throws Exception {
		AccountEntry accountEntry = _addAccountEntry(
			_accountEntryTestHelper::addInactiveAccountEntry);

		Assert.assertEquals(
			WorkflowConstants.STATUS_INACTIVE, accountEntry.getStatus());

		return accountEntry;
	}

	private long[] _addMultipleAccountEntries(
			UnsafeSupplier<AccountEntry, Exception> accountEntryUnsafeSupplier)
		throws Exception {

		List<AccountEntry> accountEntries = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			accountEntries.add(accountEntryUnsafeSupplier.get());
		}

		return ListUtil.toLongArray(
			accountEntries, AccountEntry.ACCOUNT_ENTRY_ID_ACCESSOR);
	}

	private void _assertMultiple(
		long[] accountEntryIds, Consumer<AccountEntry> accountEntryConsumer) {

		for (long accountEntryId : accountEntryIds) {
			accountEntryConsumer.accept(
				_accountEntryLocalService.fetchAccountEntry(accountEntryId));
		}
	}

	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private AccountEntryTestHelper _accountEntryTestHelper;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}