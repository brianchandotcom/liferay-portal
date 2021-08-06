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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.AccountRole;
import com.liferay.headless.admin.user.client.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccountContactInformation;
import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.client.resource.v1_0.UserAccountResource;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Calendar;

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

		_userAccountResource = UserAccountResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();

		_account = _accountResource.putAccountByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			_randomAccount());
	}

	@After
	@Override
	public void tearDown() throws Exception {
	}

	@Override
	@Test
	public void testDeleteAccountAccountRoleUserAccountAssociation()
		throws Exception {

		AccountRole accountRole = _addAccountAccountRole(_account);
		UserAccount userAccount = _addAccountUserAccount(_account);

		_assertAccountRoleUserAccountAssociation(
			_account, accountRole, userAccount, false);

		_accountRoleLocalService.associateUser(
			_account.getId(), accountRole.getId(), userAccount.getId());

		_assertAccountRoleUserAccountAssociation(
			_account, accountRole, userAccount, true);

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.
				deleteAccountAccountRoleUserAccountAssociationHttpResponse(
					_account.getId(), accountRole.getId(),
					userAccount.getId()));

		_assertAccountRoleUserAccountAssociation(
			_account, accountRole, userAccount, false);
	}

	@Override
	@Test
	public void testDeleteAccountAccountRoleUserAccountAssociationByExternalReferenceCode()
		throws Exception {

		AccountRole accountRole = _addAccountAccountRole(_account);
		UserAccount userAccount = _addAccountUserAccount(_account);

		_assertAccountRoleUserAccountAssociation(
			_account, accountRole, userAccount, false);

		_accountRoleLocalService.associateUser(
			_account.getId(), accountRole.getId(), userAccount.getId());

		_assertAccountRoleUserAccountAssociation(
			_account, accountRole, userAccount, true);

		accountRoleResource.
			deleteAccountAccountRoleUserAccountAssociationByExternalReferenceCode(
				_account.getExternalReferenceCode(), accountRole.getId(),
				userAccount.getExternalReferenceCode());

		_assertAccountRoleUserAccountAssociation(
			_account, accountRole, userAccount, false);
	}

	@Override
	@Test
	public void testPostAccountAccountRoleUserAccountAssociation()
		throws Exception {

		AccountRole accountRole = _addAccountAccountRole(_account);
		UserAccount userAccount = _addAccountUserAccount(_account);

		_assertAccountRoleUserAccountAssociation(
			_account, accountRole, userAccount, false);

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.
				postAccountAccountRoleUserAccountAssociationHttpResponse(
					_account.getId(), accountRole.getId(),
					userAccount.getId()));

		_assertAccountRoleUserAccountAssociation(
			_account, accountRole, userAccount, true);

		assertHttpResponseStatusCode(
			404,
			accountRoleResource.
				postAccountAccountRoleUserAccountAssociationHttpResponse(
					_account.getId(), 0L, userAccount.getId()));
	}

	@Override
	@Test
	public void testPostAccountAccountRoleUserAccountAssociationByExternalReferenceCode()
		throws Exception {

		AccountRole accountRole = _addAccountAccountRole(_account);
		UserAccount userAccount = _addAccountUserAccount(_account);

		_assertAccountRoleUserAccountAssociation(
			_account, accountRole, userAccount, false);

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.
				postAccountAccountRoleUserAccountAssociationByExternalReferenceCodeHttpResponse(
					_account.getExternalReferenceCode(), accountRole.getId(),
					userAccount.getExternalReferenceCode()));

		_assertAccountRoleUserAccountAssociation(
			_account, accountRole, userAccount, true);

		assertHttpResponseStatusCode(
			404,
			accountRoleResource.
				postAccountAccountRoleUserAccountAssociationByExternalReferenceCodeHttpResponse(
					_account.getExternalReferenceCode(), 0L,
					userAccount.getExternalReferenceCode()));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected AccountRole
			testDeleteAccountAccountRoleUserAccountAssociation_addAccountRole()
		throws Exception {

		return _addAccountAccountRole(_account);
	}

	@Override
	protected AccountRole
			testDeleteAccountAccountRoleUserAccountAssociationByExternalReferenceCode_addAccountRole()
		throws Exception {

		return _addAccountAccountRole(_account);
	}

	@Override
	protected AccountRole
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				String externalReferenceCode, AccountRole accountRole)
		throws Exception {

		return accountRoleResource.
			postAccountAccountRoleByExternalReferenceCode(
				externalReferenceCode, accountRole);
	}

	@Override
	protected String
			testGetAccountAccountRolesByExternalReferenceCodePage_getExternalReferenceCode()
		throws Exception {

		return _account.getExternalReferenceCode();
	}

	@Override
	protected AccountRole testGetAccountAccountRolesPage_addAccountRole(
			Long accountId, AccountRole accountRole)
		throws Exception {

		return accountRoleResource.postAccountAccountRole(
			accountId, accountRole);
	}

	@Override
	protected Long testGetAccountAccountRolesPage_getAccountId() {
		return _account.getId();
	}

	@Override
	protected AccountRole testGraphQLAccountRole_addAccountRole()
		throws Exception {

		return accountRoleResource.postAccountAccountRole(
			_account.getId(), randomAccountRole());
	}

	@Override
	protected AccountRole testPostAccountAccountRole_addAccountRole(
			AccountRole accountRole)
		throws Exception {

		return accountRoleResource.postAccountAccountRole(
			_account.getId(), accountRole);
	}

	@Override
	protected AccountRole
			testPostAccountAccountRoleByExternalReferenceCode_addAccountRole(
				AccountRole accountRole)
		throws Exception {

		return accountRoleResource.
			postAccountAccountRoleByExternalReferenceCode(
				_account.getExternalReferenceCode(), accountRole);
	}

	@Override
	protected AccountRole
			testPostAccountAccountRoleUserAccountAssociation_addAccountRole()
		throws Exception {

		return _addAccountAccountRole(_account);
	}

	@Override
	protected AccountRole
			testPostAccountAccountRoleUserAccountAssociationByExternalReferenceCode_addAccountRole()
		throws Exception {

		return _addAccountAccountRole(_account);
	}

	private AccountRole _addAccountAccountRole(Account account)
		throws Exception {

		return accountRoleResource.postAccountAccountRole(
			account.getId(), randomAccountRole());
	}

	private UserAccount _addAccountUserAccount(Account account)
		throws Exception {

		UserAccount userAccount =
			_userAccountResource.putUserAccountByExternalReferenceCode(
				RandomTestUtil.randomString(), _randomUserAccount());

		_userAccountResource.postAccountUserAccountByEmailAddress(
			account.getId(), userAccount.getEmailAddress());

		return userAccount;
	}

	private void _assertAccountRoleUserAccountAssociation(
			Account account, AccountRole accountRole, UserAccount userAccount,
			boolean hasAssociation)
		throws Exception {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			account.getId());

		UserGroupRole userGroupRole =
			_userGroupRoleLocalService.fetchUserGroupRole(
				userAccount.getId(), accountEntry.getAccountEntryGroupId(),
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
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				parentAccountId = RandomTestUtil.randomLong();
				status = RandomTestUtil.randomInt();
			}
		};
	}

	private EmailAddress _randomEmailAddress() throws Exception {
		return new EmailAddress() {
			{
				setEmailAddress(RandomTestUtil.randomString() + "@liferay.com");
				setPrimary(true);
				setType("email-address");
			}
		};
	}

	private Phone _randomPhone() throws Exception {
		return new Phone() {
			{
				setExtension(String.valueOf(RandomTestUtil.randomInt()));
				setPhoneNumber(String.valueOf(RandomTestUtil.randomInt()));
				setPhoneType("personal");
				setPrimary(true);
			}
		};
	}

	private PostalAddress _randomPostalAddress() throws Exception {
		return new PostalAddress() {
			{
				setAddressCountry("united-states");
				setAddressLocality("Diamond Bar");
				setAddressRegion("California");
				setAddressType("personal");
				setPostalCode("91765");
				setPrimary(true);
				setStreetAddressLine1(RandomTestUtil.randomString());
				setStreetAddressLine2(RandomTestUtil.randomString());
				setStreetAddressLine3(RandomTestUtil.randomString());
			}
		};
	}

	private UserAccount _randomUserAccount() throws Exception {
		return new UserAccount() {
			{
				additionalName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				alternateName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				birthDate = RandomTestUtil.nextDate();
				dashboardURL = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				familyName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				givenName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				honorificPrefix = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				honorificSuffix = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				image = StringUtil.toLowerCase(RandomTestUtil.randomString());
				jobTitle = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				profileURL = StringUtil.toLowerCase(
					RandomTestUtil.randomString());

				setBirthDate(
					() -> {
						Calendar calendar = CalendarFactoryUtil.getCalendar();

						calendar.setTime(RandomTestUtil.nextDate());
						calendar.set(Calendar.HOUR_OF_DAY, 0);
						calendar.set(Calendar.MINUTE, 0);
						calendar.set(Calendar.SECOND, 0);
						calendar.set(Calendar.MILLISECOND, 0);

						return calendar.getTime();
					});
				setUserAccountContactInformation(
					_randomUserAccountContactInformation());
			}
		};
	}

	private UserAccountContactInformation _randomUserAccountContactInformation()
		throws Exception {

		return new UserAccountContactInformation() {
			{
				setEmailAddresses(new EmailAddress[] {_randomEmailAddress()});
				setFacebook(RandomTestUtil.randomString());
				setJabber(RandomTestUtil.randomString());
				setPostalAddresses(
					new PostalAddress[] {_randomPostalAddress()});
				setSkype(RandomTestUtil.randomString());
				setSms(RandomTestUtil.randomString() + "@liferay.com");
				setTelephones(new Phone[] {_randomPhone()});
				setTwitter(RandomTestUtil.randomString());
				setWebUrls(new WebUrl[] {_randomWebUrl()});
			}
		};
	}

	private WebUrl _randomWebUrl() throws Exception {
		return new WebUrl() {
			{
				setPrimary(true);
				setUrl("https://" + RandomTestUtil.randomString() + ".com");
				setUrlType("personal");
			}
		};
	}

	private Account _account;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private AccountResource _accountResource;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	private UserAccountResource _userAccountResource;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}