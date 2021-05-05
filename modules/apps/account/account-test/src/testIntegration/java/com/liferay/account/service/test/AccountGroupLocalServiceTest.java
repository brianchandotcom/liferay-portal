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

import com.liferay.account.exception.DefaultAccountGroupException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.account.service.test.util.AccountGroupTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Albert Lee
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountGroupLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddAccountGroup() throws Exception {
		AccountGroup accountGroup = _addAccountGroup();

		Assert.assertNotNull(
			_accountGroupLocalService.fetchAccountGroup(
				accountGroup.getAccountGroupId()));
	}

	@Test
	public void testDeleteAccountGroup() throws Exception {
		_testDeleteAccountGroup(
			_addAccountGroup(),
			accountGroup -> _accountGroupLocalService.deleteAccountGroup(
				accountGroup));
		_testDeleteAccountGroup(
			_addAccountGroup(),
			accountGroup -> _accountGroupLocalService.deleteAccountGroup(
				accountGroup.getAccountGroupId()));
	}

	@Test
	public void testDeleteAccountGroupWithAccountGroupRel() throws Exception {
		AccountGroup accountGroup = _addAccountGroup();
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountGroupRelLocalService.addAccountGroupRel(
			accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		_accountGroupLocalService.deleteAccountGroup(accountGroup);

		Assert.assertEquals(
			0,
			_accountGroupRelLocalService.
				getAccountGroupRelsCountByAccountGroupId(
					accountGroup.getAccountGroupId()));
	}

	@Test
	public void testDeleteDefaultAccountGroup() throws Exception {
		try {
			_accountGroupLocalService.deleteAccountGroup(
				_accountGroupLocalService.getDefaultAccountGroup(
					TestPropsValues.getCompanyId()));
		}
		catch (ModelListenerException modelListenerException) {
			Assert.assertTrue(
				modelListenerException.getCause() instanceof
					DefaultAccountGroupException.
						MustNotDeleteDefaultAccountGroup);
		}
	}

	@Test
	public void testHasDefaultAccountGroupWhenCompanyIsCreated()
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		Assert.assertTrue(
			_accountGroupLocalService.hasDefaultAccountGroup(
				company.getCompanyId()));

		AccountGroup defaultAccountGroup =
			_accountGroupLocalService.getDefaultAccountGroup(
				company.getCompanyId());

		Assert.assertEquals(
			company.getCompanyId(), defaultAccountGroup.getCompanyId());
	}

	@Test
	public void testSearchAccountGroups() throws Exception {
		List<AccountGroup> expectedAccountGroups = Arrays.asList(
			_addAccountGroup(), _addAccountGroup());

		OrderByComparator<AccountGroup> orderByComparator =
			OrderByComparatorFactoryUtil.create(
				"AccountGroup", "createDate", true);

		expectedAccountGroups.sort(orderByComparator);

		BaseModelSearchResult<AccountGroup> baseModelSearchResult =
			_accountGroupLocalService.searchAccountGroups(
				TestPropsValues.getCompanyId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, orderByComparator);

		Assert.assertEquals(
			expectedAccountGroups.size(), baseModelSearchResult.getLength());
		Assert.assertEquals(
			expectedAccountGroups, baseModelSearchResult.getBaseModels());
	}

	@Test
	public void testSearchAccountGroupsWithKeywords() throws Exception {
		String keywords = RandomTestUtil.randomString();

		List<AccountGroup> expectedAccountGroups = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			expectedAccountGroups.add(
				_addAccountGroup(RandomTestUtil.randomString(), keywords + i));
		}

		BaseModelSearchResult<AccountGroup> baseModelSearchResult =
			_accountGroupLocalService.searchAccountGroups(
				TestPropsValues.getCompanyId(), keywords, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				OrderByComparatorFactoryUtil.create(
					"AccountGroup", "name", true));

		Assert.assertEquals(
			expectedAccountGroups.size(), baseModelSearchResult.getLength());
		Assert.assertEquals(
			expectedAccountGroups, baseModelSearchResult.getBaseModels());
	}

	@Test
	public void testSearchAccountGroupsWithPagination() throws Exception {
		String keywords = RandomTestUtil.randomString();

		List<AccountGroup> expectedAccountGroups = Arrays.asList(
			_addAccountGroup(keywords, RandomTestUtil.randomString()),
			_addAccountGroup(keywords, RandomTestUtil.randomString()),
			_addAccountGroup(keywords, RandomTestUtil.randomString()),
			_addAccountGroup(keywords, RandomTestUtil.randomString()),
			_addAccountGroup(keywords, RandomTestUtil.randomString()));

		Comparator<AccountGroup> comparator =
			(accountGroup1, accountGroup2) -> {
				String name1 = accountGroup1.getName();
				String name2 = accountGroup2.getName();

				return name1.compareToIgnoreCase(name2);
			};

		_testSearchAccountGroupsWithPagination(
			comparator, expectedAccountGroups, keywords, false);
		_testSearchAccountGroupsWithPagination(
			comparator, expectedAccountGroups, keywords, true);
	}

	@Test
	public void testUpdateDefaultAccountGroup() throws Exception {
		try {
			AccountGroup accountGroup =
				_accountGroupLocalService.getDefaultAccountGroup(
					TestPropsValues.getCompanyId());

			_accountGroupLocalService.updateAccountGroup(
				accountGroup.getAccountGroupId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());
		}
		catch (ModelListenerException modelListenerException) {
			Assert.assertTrue(
				modelListenerException.getCause() instanceof
					DefaultAccountGroupException.
						MustNotUpdateDefaultAccountGroup);
		}
	}

	private AccountGroup _addAccountGroup() throws Exception {
		return AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	private AccountGroup _addAccountGroup(String description, String name)
		throws Exception {

		return AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, description, name);
	}

	private void _testDeleteAccountGroup(
			AccountGroup accountGroup,
			UnsafeConsumer<AccountGroup, Exception> unsafeConsumer)
		throws Exception {

		unsafeConsumer.accept(accountGroup);

		Assert.assertNull(
			_accountGroupLocalService.fetchAccountGroup(
				accountGroup.getAccountGroupId()));
	}

	private void _testSearchAccountGroupsWithPagination(
			Comparator<AccountGroup> comparator,
			List<AccountGroup> expectedAccountGroups, String keywords,
			boolean reversed)
		throws Exception {

		int delta = 3;
		int start = 1;

		BaseModelSearchResult<AccountGroup> baseModelSearchResult =
			_accountGroupLocalService.searchAccountGroups(
				TestPropsValues.getCompanyId(), keywords, start, start + delta,
				OrderByComparatorFactoryUtil.create(
					"AccountGroup", "name", !reversed));

		Assert.assertEquals(
			expectedAccountGroups.size(), baseModelSearchResult.getLength());

		List<AccountGroup> actualAccountGroups =
			baseModelSearchResult.getBaseModels();

		Assert.assertEquals(
			actualAccountGroups.toString(), delta, actualAccountGroups.size());

		if (reversed) {
			expectedAccountGroups.sort(comparator.reversed());
		}
		else {
			expectedAccountGroups.sort(comparator);
		}

		Assert.assertEquals(
			ListUtil.subList(expectedAccountGroups, start, start + delta),
			actualAccountGroups);
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Inject
	private UserLocalService _userLocalService;

}