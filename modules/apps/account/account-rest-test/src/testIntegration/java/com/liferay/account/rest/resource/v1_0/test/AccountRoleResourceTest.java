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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.rest.client.dto.v1_0.Account;
import com.liferay.account.rest.client.dto.v1_0.AccountRole;
import com.liferay.account.rest.client.dto.v1_0.AccountUser;
import com.liferay.account.rest.client.resource.v1_0.AccountResource;
import com.liferay.account.rest.client.resource.v1_0.AccountUserResource;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
public class AccountRoleResourceTest extends BaseAccountRoleResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_accountResource = AccountResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();

		_accountUserResource = AccountUserResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();

		_account = _accountResource.postAccount(_randomAccount());
	}

	@After
	@Override
	public void tearDown() throws Exception {
	}

	@Override
	@Test
	public void testDeleteAccountRoleUserAssociation() throws Exception {
		AccountRole accountRole = _addAccountRole(_account);
		AccountUser accountUser = _addAccountUser(_account);

		_assertAccountRoleUserAssociation(
			_account, accountRole, accountUser, false);

		_accountRoleLocalService.associateUser(
			_account.getId(), accountRole.getId(), accountUser.getId());

		_assertAccountRoleUserAssociation(
			_account, accountRole, accountUser, true);

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.deleteAccountRoleUserAssociationHttpResponse(
				_account.getId(), accountRole.getId(), accountUser.getId()));

		_assertAccountRoleUserAssociation(
			_account, accountRole, accountUser, false);
	}

	@Override
	@Test
	public void testDeleteAccountRoleUserAssociationByExternalReferenceCode()
		throws Exception {

		AccountRole accountRole = _addAccountRole(_account);
		AccountUser accountUser = _addAccountUser(_account);

		_assertAccountRoleUserAssociation(
			_account, accountRole, accountUser, false);

		_accountRoleLocalService.associateUser(
			_account.getId(), accountRole.getId(), accountUser.getId());

		_assertAccountRoleUserAssociation(
			_account, accountRole, accountUser, true);

		accountRoleResource.
			deleteAccountRoleUserAssociationByExternalReferenceCode(
				_account.getExternalReferenceCode(), accountRole.getId(),
				accountUser.getExternalReferenceCode());

		_assertAccountRoleUserAssociation(
			_account, accountRole, accountUser, false);
	}

	@Override
	@Test
	public void testPostAccountRoleUserAssociation() throws Exception {
		AccountRole accountRole = _addAccountRole(_account);
		AccountUser accountUser = _addAccountUser(_account);

		_assertAccountRoleUserAssociation(
			_account, accountRole, accountUser, false);

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.postAccountRoleUserAssociationHttpResponse(
				_account.getId(), accountRole.getId(), accountUser.getId()));

		_assertAccountRoleUserAssociation(
			_account, accountRole, accountUser, true);

		assertHttpResponseStatusCode(
			404,
			accountRoleResource.postAccountRoleUserAssociationHttpResponse(
				_account.getId(), 0L, accountUser.getId()));
	}

	@Override
	@Test
	public void testPostAccountRoleUserAssociationByExternalReferenceCode()
		throws Exception {

		AccountRole accountRole = _addAccountRole(_account);
		AccountUser accountUser = _addAccountUser(_account);

		_assertAccountRoleUserAssociation(
			_account, accountRole, accountUser, false);

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.
				postAccountRoleUserAssociationByExternalReferenceCodeHttpResponse(
					_account.getExternalReferenceCode(), accountRole.getId(),
					accountUser.getExternalReferenceCode()));

		_assertAccountRoleUserAssociation(
			_account, accountRole, accountUser, true);

		assertHttpResponseStatusCode(
			404,
			accountRoleResource.
				postAccountRoleUserAssociationByExternalReferenceCodeHttpResponse(
					_account.getExternalReferenceCode(), 0L,
					accountUser.getExternalReferenceCode()));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected AccountRole testDeleteAccountRoleUserAssociation_addAccountRole()
		throws Exception {

		return _addAccountRole(_account);
	}

	@Override
	protected AccountRole
			testDeleteAccountRoleUserAssociationByExternalReferenceCode_addAccountRole()
		throws Exception {

		return _addAccountRole(_account);
	}

	@Override
	protected AccountRole
			testGetAccountRolesByExternalReferenceCodePage_addAccountRole(
				String externalReferenceCode, AccountRole accountRole)
		throws Exception {

		return accountRoleResource.postAccountRoleByExternalReferenceCode(
			externalReferenceCode, accountRole);
	}

	@Override
	protected String
			testGetAccountRolesByExternalReferenceCodePage_getExternalReferenceCode()
		throws Exception {

		return _account.getExternalReferenceCode();
	}

	@Override
	protected AccountRole testGetAccountRolesPage_addAccountRole(
			Long accountId, AccountRole accountRole)
		throws Exception {

		return accountRoleResource.postAccountRole(accountId, accountRole);
	}

	@Override
	protected Long testGetAccountRolesPage_getAccountId() {
		return _account.getId();
	}

	@Override
	protected AccountRole testGraphQLAccountRole_addAccountRole()
		throws Exception {

		return accountRoleResource.postAccountRole(
			_account.getId(), randomAccountRole());
	}

	@Override
	protected AccountRole testPostAccountRole_addAccountRole(
			AccountRole accountRole)
		throws Exception {

		return accountRoleResource.postAccountRole(
			_account.getId(), accountRole);
	}

	@Override
	protected AccountRole
			testPostAccountRoleByExternalReferenceCode_addAccountRole(
				AccountRole accountRole)
		throws Exception {

		return accountRoleResource.postAccountRoleByExternalReferenceCode(
			_account.getExternalReferenceCode(), accountRole);
	}

	@Override
	protected AccountRole testPostAccountRoleUserAssociation_addAccountRole()
		throws Exception {

		return _addAccountRole(_account);
	}

	@Override
	protected AccountRole
			testPostAccountRoleUserAssociationByExternalReferenceCode_addAccountRole()
		throws Exception {

		return _addAccountRole(_account);
	}

	private AccountRole _addAccountRole(Account account) throws Exception {
		return accountRoleResource.postAccountRole(
			account.getId(), randomAccountRole());
	}

	private AccountUser _addAccountUser(Account account) throws Exception {
		return _accountUserResource.postAccountUser(
			account.getId(), _randomAccountUser());
	}

	private void _assertAccountRoleUserAssociation(
			Account account, AccountRole accountRole, AccountUser accountUser,
			boolean hasAssociation)
		throws Exception {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			account.getId());

		UserGroupRole userGroupRole =
			_userGroupRoleLocalService.fetchUserGroupRole(
				accountUser.getId(), accountEntry.getAccountEntryGroupId(),
				accountRole.getRoleId());

		if (hasAssociation) {
			Assert.assertNotNull(userGroupRole);
		}
		else {
			Assert.assertNull(userGroupRole);
		}
	}

	private Account _randomAccount() {
		return new Account() {
			{
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				parentAccountId = RandomTestUtil.randomLong();
				status = RandomTestUtil.randomInt();
			}
		};
	}

	private AccountUser _randomAccountUser() throws Exception {
		return new AccountUser() {
			{
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				externalReferenceCode = RandomTestUtil.randomString();
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

	private Account _account;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private AccountResource _accountResource;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	private AccountUserResource _accountUserResource;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}