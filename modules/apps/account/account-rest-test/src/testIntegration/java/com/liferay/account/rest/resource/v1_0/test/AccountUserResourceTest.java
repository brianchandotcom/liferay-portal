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
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import org.junit.After;
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
	}

	@After
	@Override
	public void tearDown() throws Exception {
	}

	@Override
	@Test
	public void testPostAccountUser() throws Exception {
		super.testPostAccountUser();

		AccountUser randomAccountUser = randomAccountUser();

		Assert.assertNull(
			_userLocalService.fetchUserByReferenceCode(
				TestPropsValues.getCompanyId(),
				randomAccountUser.getExternalReferenceCode()));

		testPostAccountUser_addAccountUser(randomAccountUser);

		Assert.assertNotNull(
			_userLocalService.fetchUserByReferenceCode(
				TestPropsValues.getCompanyId(),
				randomAccountUser.getExternalReferenceCode()));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"firstName", "lastName", "screenName"};
	}

	@Override
	protected AccountUser
			testGetAccountUsersByExternalReferenceCodePage_addAccountUser(
				String externalReferenceCode, AccountUser accountUser)
		throws Exception {

		return accountUserResource.postAccountUserByExternalReferenceCode(
			externalReferenceCode, accountUser);
	}

	@Override
	protected String
		testGetAccountUsersByExternalReferenceCodePage_getExternalReferenceCode() {

		return _accountEntry.getExternalReferenceCode();
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

	@Override
	protected AccountUser
			testPostAccountUserByExternalReferenceCode_addAccountUser(
				AccountUser accountUser)
		throws Exception {

		return accountUserResource.postAccountUserByExternalReferenceCode(
			_accountEntry.getExternalReferenceCode(), accountUser);
	}

	private AccountUser _addAccountUser(Long accountId, AccountUser accountUser)
		throws Exception {

		return accountUserResource.postAccountUser(accountId, accountUser);
	}

	private AccountEntry _getAccountEntry() throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(20), RandomTestUtil.randomString(20),
			null, null, null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		accountEntry.setExternalReferenceCode(RandomTestUtil.randomString());

		_accountEntryLocalService.updateAccountEntry(accountEntry);

		return accountEntry;
	}

	private Long _getAccountEntryId() {
		return _accountEntry.getAccountEntryId();
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}