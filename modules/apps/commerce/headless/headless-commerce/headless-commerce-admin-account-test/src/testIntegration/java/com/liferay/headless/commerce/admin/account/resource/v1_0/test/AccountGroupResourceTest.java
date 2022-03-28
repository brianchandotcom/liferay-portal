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
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelLocalService;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.headless.commerce.admin.account.client.dto.v1_0.AccountGroup;
import com.liferay.headless.commerce.admin.account.client.pagination.Page;
import com.liferay.headless.commerce.admin.account.client.pagination.Pagination;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class AccountGroupResourceTest extends BaseAccountGroupResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());
	}

	@Ignore
	@Override
	@Test
	public void testDeleteAccountGroup() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testDeleteAccountGroupByExternalReferenceCode()
		throws Exception {
	}

	@Override
	@Test
	public void testGetAccountByExternalReferenceCodeAccountGroupsPage()
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.addCommerceAccount(
				RandomTestUtil.randomString(), 0,
				RandomTestUtil.randomString() + "@liferay.com", null, 2, true,
				RandomTestUtil.randomString(), _serviceContext);

		CommerceAccountGroup commerceAccountGroup1 =
			_commerceAccountGroupLocalService.addCommerceAccountGroup(
				_user.getCompanyId(), RandomTestUtil.randomString(), 0, false,
				RandomTestUtil.randomString(), _serviceContext);

		CommerceAccountGroup commerceAccountGroup2 =
			_commerceAccountGroupLocalService.addCommerceAccountGroup(
				_user.getCompanyId(), RandomTestUtil.randomString(), 0, false,
				RandomTestUtil.randomString(), _serviceContext);

		_commerceAccountGroupCommerceAccountRelLocalService.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroup1.getCommerceAccountGroupId(),
				commerceAccount.getCommerceAccountId(), _serviceContext);

		_commerceAccountGroupCommerceAccountRelLocalService.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroup2.getCommerceAccountGroupId(),
				commerceAccount.getCommerceAccountId(), _serviceContext);

		Page<AccountGroup> accountIdAccountGroupsPage =
			accountGroupResource.
				getAccountByExternalReferenceCodeAccountGroupsPage(
					commerceAccount.getExternalReferenceCode(),
					Pagination.of(1, 20));

		Assert.assertEquals(2, accountIdAccountGroupsPage.getTotalCount());

		List<Long> commerceAccountGroupsIds = new ArrayList<>();

		commerceAccountGroupsIds.add(
			commerceAccountGroup1.getCommerceAccountGroupId());
		commerceAccountGroupsIds.add(
			commerceAccountGroup2.getCommerceAccountGroupId());

		Collection<AccountGroup> items = accountIdAccountGroupsPage.getItems();

		for (AccountGroup accountGroup : items) {
			Assert.assertTrue(
				commerceAccountGroupsIds.contains(accountGroup.getId()));
		}
	}

	@Ignore
	@Override
	@Test
	public void testGetAccountByExternalReferenceCodeAccountGroupsPageWithPagination()
		throws Exception {
	}

	@Override
	@Test
	public void testGetAccountIdAccountGroupsPage() throws Exception {
		CommerceAccount commerceAccount =
			_commerceAccountLocalService.addCommerceAccount(
				RandomTestUtil.randomString(), 0,
				RandomTestUtil.randomString() + "@liferay.com", null, 2, true,
				RandomTestUtil.randomString(), _serviceContext);

		CommerceAccountGroup commerceAccountGroup1 =
			_commerceAccountGroupLocalService.addCommerceAccountGroup(
				_user.getCompanyId(), RandomTestUtil.randomString(), 0, false,
				RandomTestUtil.randomString(), _serviceContext);

		CommerceAccountGroup commerceAccountGroup2 =
			_commerceAccountGroupLocalService.addCommerceAccountGroup(
				_user.getCompanyId(), RandomTestUtil.randomString(), 0, false,
				RandomTestUtil.randomString(), _serviceContext);

		_commerceAccountGroupCommerceAccountRelLocalService.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroup1.getCommerceAccountGroupId(),
				commerceAccount.getCommerceAccountId(), _serviceContext);

		_commerceAccountGroupCommerceAccountRelLocalService.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroup2.getCommerceAccountGroupId(),
				commerceAccount.getCommerceAccountId(), _serviceContext);

		Page<AccountGroup> accountIdAccountGroupsPage =
			accountGroupResource.getAccountIdAccountGroupsPage(
				commerceAccount.getCommerceAccountId(), Pagination.of(1, 20));

		Assert.assertEquals(2, accountIdAccountGroupsPage.getTotalCount());

		List<Long> commerceAccountGroupsIds = new ArrayList<>();

		commerceAccountGroupsIds.add(
			commerceAccountGroup1.getCommerceAccountGroupId());
		commerceAccountGroupsIds.add(
			commerceAccountGroup2.getCommerceAccountGroupId());

		Collection<AccountGroup> items = accountIdAccountGroupsPage.getItems();

		for (AccountGroup accountGroup : items) {
			Assert.assertTrue(
				commerceAccountGroupsIds.contains(accountGroup.getId()));
		}
	}

	@Ignore
	@Override
	@Test
	public void testGetAccountIdAccountGroupsPageWithPagination()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteAccountGroup() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountGroup() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountGroupByExternalReferenceCode()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountGroupByExternalReferenceCodeNotFound()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountGroupNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountGroupsPage() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testPatchAccountGroup() throws Exception {
		Assert.assertTrue(false);
	}

	@Ignore
	@Override
	@Test
	public void testPatchAccountGroupByExternalReferenceCode()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Override
	protected AccountGroup testDeleteAccountGroup_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup
			testDeleteAccountGroupByExternalReferenceCode_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountGroupsPage_getExternalReferenceCode()
		throws Exception {

		AccountGroup accountGroup = _postAccountGroup(randomAccountGroup());

		return accountGroup.getExternalReferenceCode();
	}

	@Override
	protected AccountGroup testGetAccountGroup_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup
			testGetAccountGroupByExternalReferenceCode_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup testGetAccountGroupsPage_addAccountGroup(
			AccountGroup accountGroup)
		throws Exception {

		return _postAccountGroup(accountGroup);
	}

	@Override
	protected Long testGetAccountIdAccountGroupsPage_getId() throws Exception {
		AccountGroup accountGroup = _postAccountGroup(randomAccountGroup());

		return accountGroup.getId();
	}

	@Override
	protected AccountGroup testGraphQLAccountGroup_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup testPostAccountGroup_addAccountGroup(
			AccountGroup accountGroup)
		throws Exception {

		return _postAccountGroup(accountGroup);
	}

	private AccountGroup _postAccountGroup(AccountGroup accountGroup)
		throws Exception {

		return accountGroupResource.postAccountGroup(accountGroup);
	}

	@Inject
	private CommerceAccountGroupCommerceAccountRelLocalService
		_commerceAccountGroupCommerceAccountRelLocalService;

	@Inject
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}