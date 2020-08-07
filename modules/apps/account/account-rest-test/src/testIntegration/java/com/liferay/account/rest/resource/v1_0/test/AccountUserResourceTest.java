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
import com.liferay.account.rest.client.pagination.Page;
import com.liferay.account.rest.client.pagination.Pagination;
import com.liferay.account.rest.dto.v1_0.Account;
import com.liferay.account.rest.resource.v1_0.AccountResource;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountUserResourceTest extends BaseAccountUserResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_accountEntry = _getAccountEntry();
		_irrelevantAccountEntry = _getAccountEntry();

		_externalReferenceCode = RandomTestUtil.randomString();

		_accountResource = AccountResource.builder(
		).checkPermissions(
			false
		).preferredLocale(
			LocaleUtil.getDefault()
		).user(
			TestPropsValues.getUser()
		).build();
	}

	@Test
	public void testGetAccountUsersPageByAccountExternalReferenceCode()
		throws Exception {

		Account account = _addAccountByExternalReferenceCode();

		Page<AccountUser> page = accountUserResource.getAccountUsersPage(
			_externalReferenceCode, RandomTestUtil.randomString(), null,
			Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		String irrelevantAccountId =
			testGetAccountUsersPage_getIrrelevantAccountId();

		if (irrelevantAccountId != null) {
			AccountUser irrelevantAccountUser =
				testGetAccountUsersPage_addAccountUser(
					irrelevantAccountId, randomIrrelevantAccountUser());

			page = accountUserResource.getAccountUsersPage(
				irrelevantAccountId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountUser),
				(List<AccountUser>)page.getItems());
			assertValid(page);
		}

		AccountUser accountUser1 = testGetAccountUsersPage_addAccountUser(
			account.getId(), randomAccountUser());

		AccountUser accountUser2 = testGetAccountUsersPage_addAccountUser(
			account.getId(), randomAccountUser());

		page = accountUserResource.getAccountUsersPage(
			_externalReferenceCode, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountUser1, accountUser2),
			(List<AccountUser>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testPostAccountUserByAccountExternalReferenceCode()
		throws Exception {

		_addAccountByExternalReferenceCode();

		AccountUser randomAccountUser = randomAccountUser();

		AccountUser postAccountUser = _addAccountUser(
			_externalReferenceCode, randomAccountUser);

		assertEquals(randomAccountUser, postAccountUser);
		assertValid(postAccountUser);
	}

	@Test
	public void testPostAccountUserByAccountUserExternalReferenceCode()
		throws Exception {

		Assert.assertNull(
			_userLocalService.fetchUserByReferenceCode(
				TestPropsValues.getCompanyId(), _externalReferenceCode));

		AccountUser randomAccountUser = randomAccountUser();

		randomAccountUser.setId(_externalReferenceCode);

		AccountUser postAccountUser = testPostAccountUser_addAccountUser(
			randomAccountUser);

		assertEquals(randomAccountUser, postAccountUser);
		assertValid(postAccountUser);

		Assert.assertNotNull(
			_userLocalService.fetchUserByReferenceCode(
				TestPropsValues.getCompanyId(), _externalReferenceCode));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"firstName", "lastName", "screenName"};
	}

	@Override
	protected AccountUser randomAccountUser() throws Exception {
		AccountUser accountUser = super.randomAccountUser();

		accountUser.setId((String)null);

		return accountUser;
	}

	@Override
	protected AccountUser testGetAccountUsersPage_addAccountUser(
			String accountId, AccountUser accountUser)
		throws Exception {

		return _addAccountUser(accountId, accountUser);
	}

	@Override
	protected String testGetAccountUsersPage_getAccountId() {
		return _getAccountId();
	}

	@Override
	protected String testGetAccountUsersPage_getIrrelevantAccountId() {
		return _getIrrelevantAccountId();
	}

	@Override
	protected AccountUser testGraphQLAccountUser_addAccountUser()
		throws Exception {

		return _addAccountUser(_getAccountId(), randomAccountUser());
	}

	@Override
	protected AccountUser testPostAccountUser_addAccountUser(
			AccountUser accountUser)
		throws Exception {

		return _addAccountUser(_getAccountId(), accountUser);
	}

	private Account _addAccountByExternalReferenceCode() throws Exception {
		Account randomAccount = _randomAccount();

		randomAccount.setId(_externalReferenceCode);

		return _accountResource.postAccount(randomAccount);
	}

	private AccountUser _addAccountUser(
			String accountId, AccountUser accountUser)
		throws Exception {

		accountUser = accountUserResource.postAccountUser(
			accountId, accountUser);

		_accountUsers.add(accountUser);

		return accountUser;
	}

	private AccountEntry _getAccountEntry() throws Exception {
		return _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(20), RandomTestUtil.randomString(20),
			null, null, null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private String _getAccountId() {
		return String.valueOf(_accountEntry.getAccountEntryId());
	}

	private String _getIrrelevantAccountId() {
		return String.valueOf(_irrelevantAccountEntry.getAccountEntryId());
	}

	private Account _randomAccount() {
		return new Account() {
			{
				description = RandomTestUtil.randomString(20);
				domains = new String[0];
				name = RandomTestUtil.randomString(20);
				parentAccountId = String.valueOf(
					AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);
				status = WorkflowConstants.STATUS_APPROVED;
			}
		};
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private AccountResource _accountResource;
	private final List<AccountUser> _accountUsers = new ArrayList<>();
	private String _externalReferenceCode;

	@DeleteAfterTestRun
	private AccountEntry _irrelevantAccountEntry;

	@Inject
	private UserLocalService _userLocalService;

}