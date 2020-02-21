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

package com.liferay.account.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.rest.client.dto.v1_0.AccountRole;
import com.liferay.account.rest.dto.v1_0.Account;
import com.liferay.account.rest.resource.v1_0.AccountResource;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountRoleResourceTest extends BaseAccountRoleResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_accountResource.setContextCompany(testCompany);
		_accountResource.setContextUser(
			UserTestUtil.addCompanyAdminUser(testCompany));
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_deleteAccounts(_accounts);
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountRolesPage() throws Exception {
	}

	@Override
	protected AccountRole testGetAccountRolesPage_addAccountRole(
			Long accountId, AccountRole accountRole)
		throws Exception {

		return accountRoleResource.postAccountRole(accountId, accountRole);
	}

	@Override
	protected Long testGetAccountRolesPage_getAccountId() throws Exception {
		return _getAccountId();
	}

	@Override
	protected Long testGetAccountRolesPage_getIrrelevantAccountId()
		throws Exception {

		return _getAccountId();
	}

	@Override
	protected AccountRole testGraphQLAccountRole_addAccountRole()
		throws Exception {

		return accountRoleResource.postAccountRole(
			_getAccountId(), randomAccountRole());
	}

	@Override
	protected AccountRole testPostAccountRole_addAccountRole(
			AccountRole accountRole)
		throws Exception {

		return accountRoleResource.postAccountRole(
			_getAccountId(), accountRole);
	}

	private Account _addAccount() throws Exception {
		Account account = _accountResource.postAccount(_randomAccount());

		_accounts.add(account);

		return account;
	}

	private void _deleteAccounts(List<Account> accounts) {
		for (Account account : accounts) {
			try {
				_accountEntryLocalService.deleteAccountEntry(account.getId());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}
	}

	private long _getAccountId() throws Exception {
		Account account = _addAccount();

		return account.getId();
	}

	private Account _randomAccount() {
		Account account = new Account();

		account.setStatus(WorkflowConstants.STATUS_APPROVED);
		account.setParentAccountId(AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);
		account.setName(RandomTestUtil.randomString(20));
		account.setDomains(new String[0]);
		account.setDescription(RandomTestUtil.randomString(20));

		return account;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountRoleResourceTest.class);

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountResource _accountResource;

	private final List<Account> _accounts = new ArrayList<>();

}