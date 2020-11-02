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

package com.liferay.headless.commerce.admin.account.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalServiceUtil;
import com.liferay.headless.commerce.admin.account.client.dto.v1_0.AccountMember;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class AccountMemberResourceTest
	extends BaseAccountMemberResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addCompanyAdminUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceAccount = CommerceAccountLocalServiceUtil.addCommerceAccount(
			RandomTestUtil.randomString(),
			CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
			RandomTestUtil.randomString() + "@liferay.com", null,
			CommerceAccountConstants.ACCOUNT_TYPE_GUEST, true,
			RandomTestUtil.randomString(), _serviceContext);
	}

	@Override
	@Test
	public void testDeleteAccountByExternalReferenceCodeAccountMember()
		throws Exception {

		AccountMember accountMember = randomAccountMember();

		accountMemberResource.postAccountByExternalReferenceCodeAccountMember(
			_commerceAccount.getExternalReferenceCode(), accountMember);

		assertHttpResponseStatusCode(
			204,
			accountMemberResource.
				deleteAccountByExternalReferenceCodeAccountMemberHttpResponse(
					_commerceAccount.getExternalReferenceCode(),
					accountMember.getUserId()));

		assertHttpResponseStatusCode(
			404,
			accountMemberResource.
				getAccountByExternalReferenceCodeAccountMemberHttpResponse(
					_commerceAccount.getExternalReferenceCode(),
					accountMember.getUserId()));
	}

	@Override
	@Test
	public void testDeleteAccountIdAccountMember() throws Exception {
		AccountMember accountMember = randomAccountMember();

		accountMemberResource.postAccountIdAccountMember(
			_commerceAccount.getCommerceAccountId(), accountMember);

		assertHttpResponseStatusCode(
			204,
			accountMemberResource.deleteAccountIdAccountMemberHttpResponse(
				_commerceAccount.getCommerceAccountId(),
				accountMember.getUserId()));

		assertHttpResponseStatusCode(
			404,
			accountMemberResource.getAccountIdAccountMemberHttpResponse(
				_commerceAccount.getCommerceAccountId(),
				accountMember.getUserId()));
	}

	@Override
	@Test
	public void testGetAccountByExternalReferenceCodeAccountMember()
		throws Exception {

		AccountMember accountMember1 = randomAccountMember();

		accountMemberResource.postAccountByExternalReferenceCodeAccountMember(
			_commerceAccount.getExternalReferenceCode(), accountMember1);

		accountMember1.setAccountId(_commerceAccount.getCommerceAccountId());

		AccountMember accountMember2 =
			accountMemberResource.
				getAccountByExternalReferenceCodeAccountMember(
					_commerceAccount.getExternalReferenceCode(),
					accountMember1.getUserId());

		assertEquals(accountMember1, accountMember2);
	}

	@Override
	@Test
	public void testGetAccountIdAccountMember() throws Exception {
		AccountMember accountMember1 = randomAccountMember();

		accountMemberResource.postAccountIdAccountMember(
			_commerceAccount.getCommerceAccountId(), accountMember1);

		accountMember1.setAccountId(_commerceAccount.getCommerceAccountId());

		AccountMember accountMember2 =
			accountMemberResource.getAccountIdAccountMember(
				_commerceAccount.getCommerceAccountId(),
				accountMember1.getUserId());

		assertEquals(accountMember1, accountMember2);
	}

	@Override
	@Test
	public void testPatchAccountByExternalReferenceCodeAccountMember()
		throws Exception {

		AccountMember accountMember1 = randomAccountMember();

		accountMemberResource.postAccountByExternalReferenceCodeAccountMember(
			_commerceAccount.getExternalReferenceCode(), accountMember1);

		accountMember1.setAccountId(_commerceAccount.getCommerceAccountId());

		accountMemberResource.patchAccountByExternalReferenceCodeAccountMember(
			_commerceAccount.getExternalReferenceCode(),
			accountMember1.getUserId(), accountMember1);

		AccountMember accountMember2 =
			accountMemberResource.
				getAccountByExternalReferenceCodeAccountMember(
					_commerceAccount.getExternalReferenceCode(),
					accountMember1.getUserId());

		assertEquals(accountMember1, accountMember2);
	}

	@Override
	@Test
	public void testPatchAccountIdAccountMember() throws Exception {
		AccountMember accountMember1 = randomAccountMember();

		accountMemberResource.postAccountIdAccountMember(
			_commerceAccount.getCommerceAccountId(), accountMember1);

		accountMember1.setAccountId(_commerceAccount.getCommerceAccountId());

		accountMemberResource.patchAccountIdAccountMember(
			_commerceAccount.getCommerceAccountId(), accountMember1.getUserId(),
			accountMember1);

		AccountMember accountMember2 =
			accountMemberResource.getAccountIdAccountMember(
				_commerceAccount.getCommerceAccountId(),
				accountMember1.getUserId());

		assertEquals(accountMember1, accountMember2);
	}

	@Test
	public void testPostAccountByExternalReferenceCodeAccountMemberWithEmail()
		throws Exception {

		AccountMember accountMember1 = accountMemberOnlyEmail();

		accountMember1 =
			accountMemberResource.
				postAccountByExternalReferenceCodeAccountMember(
					_commerceAccount.getExternalReferenceCode(),
					accountMember1);

		accountMember1.setAccountId(_commerceAccount.getCommerceAccountId());

		AccountMember accountMember2 =
			accountMemberResource.
				getAccountByExternalReferenceCodeAccountMember(
					_commerceAccount.getExternalReferenceCode(),
					accountMember1.getUserId());

		assertEquals(accountMember1, accountMember2);
	}

	@Test
	public void testPostAccountIdAccountMemberWithEmail() throws Exception {
		AccountMember accountMember1 = accountMemberOnlyEmail();

		accountMember1 = accountMemberResource.postAccountIdAccountMember(
			_commerceAccount.getCommerceAccountId(), accountMember1);

		accountMember1.setAccountId(_commerceAccount.getCommerceAccountId());

		AccountMember accountMember2 =
			accountMemberResource.getAccountIdAccountMember(
				_commerceAccount.getCommerceAccountId(),
				accountMember1.getUserId());

		assertEquals(accountMember1, accountMember2);
	}

	protected AccountMember accountMemberOnlyEmail() throws Exception {
		User user = UserTestUtil.addUser(testCompany);

		return new AccountMember() {
			{
				email = user.getEmailAddress();
				name = user.getFullName();
			}
		};
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"accountId", "userId", "email", "name"};
	}

	@Override
	protected AccountMember randomAccountMember() throws Exception {
		User user = UserTestUtil.addUser(testCompany);

		return new AccountMember() {
			{
				email = user.getEmailAddress();
				name = user.getFullName();
				userId = user.getUserId();
			}
		};
	}

	@Override
	protected AccountMember
			testGetAccountByExternalReferenceCodeAccountMembersPage_addAccountMember(
				String externalReferenceCode, AccountMember accountMember)
		throws Exception {

		return accountMemberResource.
			postAccountByExternalReferenceCodeAccountMember(
				_commerceAccount.getExternalReferenceCode(), accountMember);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountMembersPage_getExternalReferenceCode()
		throws Exception {

		return _commerceAccount.getExternalReferenceCode();
	}

	@Override
	protected AccountMember testGetAccountIdAccountMembersPage_addAccountMember(
			Long id, AccountMember accountMember)
		throws Exception {

		return accountMemberResource.postAccountIdAccountMember(
			id, accountMember);
	}

	@Override
	protected Long testGetAccountIdAccountMembersPage_getId() throws Exception {
		return _commerceAccount.getCommerceAccountId();
	}

	@Override
	protected AccountMember
			testPostAccountByExternalReferenceCodeAccountMember_addAccountMember(
				AccountMember accountMember)
		throws Exception {

		accountMemberResource.postAccountByExternalReferenceCodeAccountMember(
			_commerceAccount.getExternalReferenceCode(), accountMember);

		accountMember.setAccountId(_commerceAccount.getCommerceAccountId());

		return accountMemberResource.
			getAccountByExternalReferenceCodeAccountMember(
				_commerceAccount.getExternalReferenceCode(),
				accountMember.getUserId());
	}

	@Override
	protected AccountMember testPostAccountIdAccountMember_addAccountMember(
			AccountMember accountMember)
		throws Exception {

		accountMemberResource.postAccountIdAccountMember(
			_commerceAccount.getCommerceAccountId(), accountMember);

		accountMember.setAccountId(_commerceAccount.getCommerceAccountId());

		return accountMemberResource.getAccountIdAccountMember(
			_commerceAccount.getCommerceAccountId(), accountMember.getUserId());
	}

	private CommerceAccount _commerceAccount;
	private ServiceContext _serviceContext;
	private User _user;

}