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
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelLocalService;
import com.liferay.headless.commerce.admin.account.client.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.account.client.pagination.Page;
import com.liferay.headless.commerce.admin.account.client.pagination.Pagination;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class AccountResourceTest extends BaseAccountResourceTestCase {

	@Override
	@Test
	public void testDeleteAccount() throws Exception {
		Account account = testDeleteAccount_addAccount();

		assertHttpResponseStatusCode(
			204, accountResource.deleteAccountHttpResponse(account.getId()));
	}

	@Override
	@Test
	public void testDeleteAccountByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account = testDeleteAccountByExternalReferenceCode_addAccount();

		assertHttpResponseStatusCode(
			204,
			accountResource.deleteAccountByExternalReferenceCodeHttpResponse(
				account.getExternalReferenceCode()));
	}

	@Ignore
	@Override
	@Test
	public void testDeleteAccountGroupByExternalReferenceCodeAccount()
		throws Exception {
	}

	@Override
	@Test
	public void testGetAccountsPage() throws Exception {
		super.testGetAccountsPage();

		Account account1 = testGetAccountsPage_addAccount(randomAccount());

		Account account2 = testGetAccountsPage_addAccount(randomAccount());

		Organization organization = OrganizationTestUtil.addOrganization();

		_testGetAccountsPage(Arrays.asList(account1, account2));

		_testGetAccountsPage(Collections.emptyList());

		_commerceAccountOrganizationRelLocalService.
			addCommerceAccountOrganizationRel(
				account1.getId(), organization.getOrganizationId(),
				ServiceContextTestUtil.getServiceContext());

		_testGetAccountsPage(Collections.singletonList(account1));
	}

	@Ignore
	@Override
	@Test
	public void testGetAccountsPageWithFilterDateTimeEquals() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetAccountsPageWithFilterStringEquals() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetAccountsPageWithPagination() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetAccountsPageWithSortDateTime() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetAccountsPageWithSortString() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteAccount() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccount() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountByExternalReferenceCode()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountByExternalReferenceCodeNotFound()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountsPage() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testPatchAccount() throws Exception {
		Assert.assertTrue(false);
	}

	@Ignore
	@Override
	@Test
	public void testPatchAccountByExternalReferenceCode() throws Exception {
		Assert.assertTrue(false);
	}

	@Ignore
	@Override
	@Test
	public void testPostAccountByExternalReferenceCodeLogo() throws Exception {
		Assert.assertTrue(false);
	}

	@Ignore
	@Override
	@Test
	public void testPostAccountGroupByExternalReferenceCodeAccount()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Ignore
	@Override
	@Test
	public void testPostAccountLogo() throws Exception {
		Assert.assertTrue(false);
	}

	@Override
	protected Account randomAccount() throws Exception {
		return new Account() {
			{
				active = true;
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				defaultBillingAccountAddressId = RandomTestUtil.randomLong();
				defaultShippingAccountAddressId = RandomTestUtil.randomLong();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				logoId = RandomTestUtil.randomLong();
				logoURL = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				root = true;
				taxId = StringUtil.toLowerCase(RandomTestUtil.randomString());
				type = 2;
			}
		};
	}

	@Override
	protected Account testDeleteAccount_addAccount() throws Exception {
		return _postAccount(randomAccount());
	}

	@Override
	protected Account testDeleteAccountByExternalReferenceCode_addAccount()
		throws Exception {

		Account account = randomAccount();

		account.setExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));

		return _postAccount(account);
	}

	@Override
	protected Account testGetAccount_addAccount() throws Exception {
		return _postAccount(randomAccount());
	}

	@Override
	protected Account testGetAccountByExternalReferenceCode_addAccount()
		throws Exception {

		Account account = randomAccount();

		account.setExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));

		return _postAccount(account);
	}

	@Override
	protected Account testGetAccountsPage_addAccount(Account account)
		throws Exception {

		return _postAccount(account);
	}

	@Override
	protected Account testGraphQLAccount_addAccount() throws Exception {
		return _postAccount(randomAccount());
	}

	@Override
	protected Account testPostAccount_addAccount(Account account)
		throws Exception {

		return _postAccount(account);
	}

	private Account _postAccount(Account account) throws Exception {
		return accountResource.postAccount(account);
	}

	private void _testGetAccountsPage(List<Account> expectedAccountEntries)
		throws Exception {

		Page<Account> accountsPage = accountResource.getAccountsPage(
			null, null, Pagination.of(1, 10), null);

		for (Account account : accountsPage.getItems()) {
			expectedAccountEntries.contains(account);
		}
	}

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Inject
	private CommerceAccountOrganizationRelLocalService
		_commerceAccountOrganizationRelLocalService;

}