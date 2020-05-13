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
import com.liferay.account.model.AccountEntry;
import com.liferay.account.rest.client.dto.v1_0.AccountUser;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountUserResourceTest extends BaseAccountUserResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_accountEntry = _getAccountEntry();
		_irrelevantAccountEntry = _getAccountEntry();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_deleteAccountUsers(_accountUsers);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"firstName", "lastName", "screenName"};
	}

	@Override
	protected AccountUser randomAccountUser() {
		return new AccountUser() {
			{
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				firstName = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				lastName = RandomTestUtil.randomString();
				middleName = RandomTestUtil.randomString();
				prefix = RandomTestUtil.randomString();
				screenName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				suffix = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected AccountUser testGetAccountUsersPage_addAccountUser(
			Long accountId, AccountUser accountUser)
		throws Exception {

		return _addAccountUser(accountId, accountUser);
	}

	@Override
	protected Long testGetAccountUsersPage_getAccountId() {
		return _getAccountEntryId();
	}

	@Override
	protected Long testGetAccountUsersPage_getIrrelevantAccountId() {
		return _getIrrelevantAccountEntryId();
	}

	@Override
	protected AccountUser testGraphQLAccountUser_addAccountUser()
		throws Exception {

		return _addAccountUser(_getAccountEntryId(), randomAccountUser());
	}

	@Override
	protected AccountUser testPostAccountUser_addAccountUser(
			AccountUser accountUser)
		throws Exception {

		return _addAccountUser(_getAccountEntryId(), accountUser);
	}

	private AccountUser _addAccountUser(Long accountId, AccountUser accountUser)
		throws Exception {

		accountUser = accountUserResource.postAccountUser(
			accountId, accountUser);

		_accountUsers.add(accountUser);

		return accountUser;
	}

	private void _deleteAccountUsers(List<AccountUser> accountUsers) {
		for (AccountUser accountUser : accountUsers) {
			try {
				_userLocalService.deleteUser(accountUser.getId());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}
	}

	private AccountEntry _getAccountEntry() throws Exception {
		return _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(20), RandomTestUtil.randomString(20),
			null, null, WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private Long _getAccountEntryId() {
		return _accountEntry.getAccountEntryId();
	}

	private Long _getIrrelevantAccountEntryId() {
		return _irrelevantAccountEntry.getAccountEntryId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountUserResourceTest.class);

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private final List<AccountUser> _accountUsers = new ArrayList<>();

	@DeleteAfterTestRun
	private AccountEntry _irrelevantAccountEntry;

	@Inject
	private UserLocalService _userLocalService;

}