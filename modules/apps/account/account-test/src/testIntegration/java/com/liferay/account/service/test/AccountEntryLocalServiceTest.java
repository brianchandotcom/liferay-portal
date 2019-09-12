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
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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
public class AccountEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		for (AccountEntry accountEntry : _accountEntries) {
			accountEntry = _accountEntryLocalService.fetchAccountEntry(
				accountEntry.getAccountEntryId());

			if (accountEntry != null) {
				_accountEntryLocalService.deleteAccountEntry(accountEntry);
			}
		}
	}

	@Test
	public void testActivateAccountEntries() throws Exception {
		long[] accountEntryIds = _addAccountEntries(
			WorkflowConstants.STATUS_INACTIVE);

		for (long accountEntryId : accountEntryIds) {
			_assertStatus(accountEntryId, WorkflowConstants.STATUS_INACTIVE);
		}

		_accountEntryLocalService.activateAccountEntries(accountEntryIds);

		for (long accountEntryId : accountEntryIds) {
			_assertStatus(accountEntryId, WorkflowConstants.STATUS_APPROVED);
		}
	}

	@Test
	public void testActivateAccountEntryByModel() throws Exception {
		AccountEntry accountEntry = _addAccountEntry(
			WorkflowConstants.STATUS_INACTIVE);

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_INACTIVE);

		_accountEntryLocalService.activateAccountEntry(accountEntry);

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Test
	public void testActivateAccountEntryByPrimaryKey() throws Exception {
		AccountEntry accountEntry = _addAccountEntry(
			WorkflowConstants.STATUS_INACTIVE);

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_INACTIVE);

		_accountEntryLocalService.activateAccountEntry(
			accountEntry.getAccountEntryId());

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Test
	public void testAddAccountEntry() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		Assert.assertNotNull(
			_accountEntryLocalService.fetchAccountEntry(
				accountEntry.getAccountEntryId()));

		int resourcePermissionsCount =
			_resourcePermissionLocalService.getResourcePermissionsCount(
				TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(accountEntry.getAccountEntryId()));

		Assert.assertEquals(1, resourcePermissionsCount);
	}

	@Test
	public void testDeactivateAccountEntries() throws Exception {
		long[] accountEntryIds = _addAccountEntries();

		for (long accountEntryId : accountEntryIds) {
			_assertStatus(accountEntryId, WorkflowConstants.STATUS_APPROVED);
		}

		_accountEntryLocalService.deactivateAccountEntries(accountEntryIds);

		for (long accountEntryId : accountEntryIds) {
			_assertStatus(accountEntryId, WorkflowConstants.STATUS_INACTIVE);
		}
	}

	@Test
	public void testDeactivateAccountEntryByModel() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_APPROVED);

		_accountEntryLocalService.deactivateAccountEntry(accountEntry);

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_INACTIVE);
	}

	@Test
	public void testDeactivateAccountEntryByPrimaryKey() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_APPROVED);

		_accountEntryLocalService.deactivateAccountEntry(
			accountEntry.getAccountEntryId());

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_INACTIVE);
	}

	@Test
	public void testDeleteAccountEntries() throws Exception {
		long[] accountEntryIds = _addAccountEntries();

		_accountEntryLocalService.deleteAccountEntries(accountEntryIds);

		for (long accountEntryId : accountEntryIds) {
			_assertDeleted(accountEntryId);
		}
	}

	@Test
	public void testDeleteAccountEntryByModel() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		_accountEntryLocalService.deleteAccountEntry(accountEntry);

		_assertDeleted(accountEntry.getAccountEntryId());
	}

	@Test
	public void testDeleteAccountEntryByPrimaryKey() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		_accountEntryLocalService.deleteAccountEntry(
			accountEntry.getAccountEntryId());

		_assertDeleted(accountEntry.getAccountEntryId());
	}

	private long[] _addAccountEntries() throws Exception {
		return _addAccountEntries(WorkflowConstants.STATUS_APPROVED);
	}

	private long[] _addAccountEntries(int status) throws Exception {
		int size = 5;

		long[] accountEntryIds = new long[size];

		for (int i = 0; i < size; i++) {
			AccountEntry accountEntry = _addAccountEntry(status);

			accountEntryIds[i] = accountEntry.getAccountEntryId();
		}

		return accountEntryIds;
	}

	private AccountEntry _addAccountEntry() throws Exception {
		return _addAccountEntry(WorkflowConstants.STATUS_APPROVED);
	}

	private AccountEntry _addAccountEntry(int status) throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService, status);

		_accountEntries.add(accountEntry);

		return accountEntry;
	}

	private void _assertDeleted(long accountEntryId) throws Exception {
		Assert.assertNull(
			_accountEntryLocalService.fetchAccountEntry(accountEntryId));

		int resourcePermissionsCount =
			_resourcePermissionLocalService.getResourcePermissionsCount(
				TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(accountEntryId));

		Assert.assertEquals(0, resourcePermissionsCount);
	}

	private void _assertStatus(long accountEntryId, int expectedStatus) {
		AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(
			accountEntryId);

		Assert.assertEquals(expectedStatus, accountEntry.getStatus());
	}

	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}