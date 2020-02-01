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

package com.liferay.account.retriever.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.test.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountUserRetrieverTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);
	}

	@Test
	public void testGetAccountUsers() throws Exception {
		_users.add(UserTestUtil.addUser());
		_users.add(UserTestUtil.addUser());
		_users.add(UserTestUtil.addUser());

		for (User user : _users) {
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId());
		}

		long[] expectedUserIds = ListUtil.toLongArray(
			_users, User.USER_ID_ACCESSOR);

		Arrays.sort(expectedUserIds);

		List<User> actualUsers = _accountUserRetriever.getAccountUsers(
			_accountEntry.getAccountEntryId());

		long[] actualUserIds = ListUtil.toLongArray(
			actualUsers, User::getUserId);

		Arrays.sort(actualUserIds);

		Assert.assertArrayEquals(expectedUserIds, actualUserIds);
	}

	@Test
	public void testSearchAccountUsers() throws Exception {

		// Add a user that is part of the account but will not hit a keyword
		// search

		_users.add(UserTestUtil.addUser());

		// Add a user that is part of the account and will hit a keyword search

		String keywords = RandomTestUtil.randomString();

		_users.add(
			UserTestUtil.addUser(
				keywords + RandomTestUtil.randomString(), null));

		for (User user : _users) {
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId());
		}

		// Assert that null keyword search hits only account users

		_assertSearch(null, 2);

		// Assert that non-null keyword search hits only account users that
		// match

		_assertSearch(keywords, 1);
	}

	@Test
	public void testSearchAccountUsersWithMultipleAccountEntries()
		throws Exception {

		User user1 = UserTestUtil.addUser();

		_users.add(user1);

		AccountEntry accountEntry1 = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntries.add(accountEntry1);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry1.getAccountEntryId(), user1.getUserId());

		User user2 = UserTestUtil.addUser();

		_users.add(user2);

		AccountEntry accountEntry2 = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntries.add(accountEntry2);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry2.getAccountEntryId(), user2.getUserId());

		_users.add(UserTestUtil.addUser());

		BaseModelSearchResult<User> baseModelSearchResult = _searchAccountUsers(
			new long[] {
				accountEntry1.getAccountEntryId(),
				accountEntry2.getAccountEntryId()
			},
			null, -1, -1, "screenName", false);

		Assert.assertEquals(2, baseModelSearchResult.getLength());

		List<User> users = baseModelSearchResult.getBaseModels();

		Assert.assertTrue(users.contains(user1));
		Assert.assertTrue(users.contains(user2));
	}

	@Test
	public void testSearchAccountUsersWithNoAccountUsers() throws Exception {

		// Add a user that is not part of the account

		_users.add(UserTestUtil.addUser());

		// Assert that null keyword search does not hit non-account users

		_assertSearch(null, 0);
	}

	@Test
	public void testSearchAccountUsersWithPagination() throws Exception {
		String keywords = RandomTestUtil.randomString();

		for (int i = 1; i < 5; i++) {
			String name = keywords + i;

			User user = UserTestUtil.addUser(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				StringPool.BLANK, name + "@liferay.com", name,
				LocaleUtil.getDefault(), name, RandomTestUtil.randomString(),
				null, ServiceContextTestUtil.getServiceContext());

			_users.add(user);

			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId());
		}

		// Assert unpaginated search

		BaseModelSearchResult<User> baseModelSearchResult = _searchAccountUsers(
			keywords, 0, 4, false);

		Assert.assertEquals(4, baseModelSearchResult.getLength());

		List<User> users = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(users.toString(), 4, users.size());
		Assert.assertEquals(_users.get(0), users.get(0));

		// Test paginated search has a partial list, but full count

		baseModelSearchResult = _searchAccountUsers(keywords, 1, 2, false);

		Assert.assertEquals(4, baseModelSearchResult.getLength());

		users = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(users.toString(), 2, users.size());
		Assert.assertEquals(_users.get(1), users.get(0));

		// Test reversed sorting

		baseModelSearchResult = _searchAccountUsers(keywords, 0, 4, true);

		Assert.assertEquals(4, baseModelSearchResult.getLength());

		users = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(users.toString(), 4, users.size());
		Assert.assertEquals(_users.get(3), users.get(0));

		// Test sort by non-keyword-mapped non-sortable field name

		baseModelSearchResult = _searchAccountUsers(
			keywords, 0, 4, "firstName", true);

		Assert.assertEquals(4, baseModelSearchResult.getLength());

		users = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(users.toString(), 4, users.size());
		Assert.assertEquals(_users.get(3), users.get(0));

		// Test sort by non-keyword-mapped sortable field name

		baseModelSearchResult = _searchAccountUsers(
			keywords, 0, 4, "emailAddress", true);

		Assert.assertEquals(4, baseModelSearchResult.getLength());

		users = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(users.toString(), 4, users.size());
		Assert.assertEquals(_users.get(3), users.get(0));
	}

	private void _assertSearch(String keywords, int expectedSize)
		throws Exception {

		BaseModelSearchResult<User> baseModelSearchResult = _searchAccountUsers(
			keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, false);

		Assert.assertEquals(expectedSize, baseModelSearchResult.getLength());

		List<User> users = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(users.toString(), expectedSize, users.size());
	}

	private BaseModelSearchResult<User> _searchAccountUsers(
			long[] accountEntryIds, String keywords, int cur, int delta,
			String sortField, boolean reverse)
		throws Exception {

		return _accountUserRetriever.searchAccountUsers(
			accountEntryIds, keywords, WorkflowConstants.STATUS_APPROVED, cur,
			delta, sortField, reverse);
	}

	private BaseModelSearchResult<User> _searchAccountUsers(
			String keywords, int cur, int delta, boolean reverse)
		throws Exception {

		return _searchAccountUsers(keywords, cur, delta, "screenName", reverse);
	}

	private BaseModelSearchResult<User> _searchAccountUsers(
			String keywords, int cur, int delta, String sortField,
			boolean reverse)
		throws Exception {

		return _accountUserRetriever.searchAccountUsers(
			_accountEntry.getAccountEntryId(), keywords,
			WorkflowConstants.STATUS_APPROVED, cur, delta, sortField, reverse);
	}

	@DeleteAfterTestRun
	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountUserRetriever _accountUserRetriever;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}