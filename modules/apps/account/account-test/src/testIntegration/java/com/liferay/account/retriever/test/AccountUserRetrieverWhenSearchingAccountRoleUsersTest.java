/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.retriever.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.comparator.UserIdComparator;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class AccountUserRetrieverWhenSearchingAccountRoleUsersTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry1 = AccountEntryTestUtil.addAccountEntry();
		_accountEntry2 = AccountEntryTestUtil.addAccountEntry();

		_accountRole = _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), _accountEntry1.getAccountEntryId(),
			RandomTestUtil.randomString(), null, null);
	}

	@Test
	public void testShouldReturnAccountRoleUsersInSpecifiedAccount()
		throws Exception {

		List<AccountRole> accountRoles =
			_accountRoleLocalService.getAccountRolesByAccountEntryIds(
				new long[] {AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT});

		AccountRole accountRole = accountRoles.get(0);

		User expectedUser = _addAccountRoleUser(
			_accountEntry1.getAccountEntryId(), accountRole.getAccountRoleId());

		_addAccountRoleUser(
			_accountEntry2.getAccountEntryId(), accountRole.getAccountRoleId());

		BaseModelSearchResult<User> baseModelSearchResult =
			_accountUserRetriever.searchAccountRoleUsers(
				_accountEntry1.getAccountEntryId(),
				accountRole.getAccountRoleId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, baseModelSearchResult.getLength());

		List<User> users = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(expectedUser, users.get(0));
	}

	@Test
	public void testShouldReturnAccountRoleUsersWithMatchingKeywords()
		throws Exception {

		_addAccountUser(_accountEntry1.getAccountEntryId());
		_addAccountUser(_accountEntry1.getAccountEntryId());

		List<User> accountRoleUsers = new ArrayList<>();

		accountRoleUsers.add(
			_addAccountRoleUser(
				_accountEntry1.getAccountEntryId(),
				_accountRole.getAccountRoleId()));
		accountRoleUsers.add(
			_addAccountRoleUser(
				_accountEntry1.getAccountEntryId(),
				_accountRole.getAccountRoleId()));

		String keywords = RandomTestUtil.randomString();

		User user = _addAccountRoleUser(
			_accountEntry1.getAccountEntryId(), _accountRole.getAccountRoleId(),
			keywords);

		accountRoleUsers.add(user);

		BaseModelSearchResult<User> baseModelSearchResult =
			_accountUserRetriever.searchAccountRoleUsers(
				_accountEntry1.getAccountEntryId(),
				_accountRole.getAccountRoleId(), StringPool.BLANK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, new UserIdComparator());

		Assert.assertEquals(
			accountRoleUsers.size(), baseModelSearchResult.getLength());
		Assert.assertEquals(
			ListUtil.sort(accountRoleUsers, new UserIdComparator()),
			baseModelSearchResult.getBaseModels());

		baseModelSearchResult = _accountUserRetriever.searchAccountRoleUsers(
			_accountEntry1.getAccountEntryId(), _accountRole.getAccountRoleId(),
			keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserIdComparator());

		Assert.assertEquals(1, baseModelSearchResult.getLength());

		List<User> users = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(user, users.get(0));
	}

	@Test
	public void testShouldReturnAccountRoleUsersWithPagination()
		throws Exception {

		String keywords = RandomTestUtil.randomString();

		for (int i = 0; i < 5; i++) {
			_addAccountRoleUser(
				_accountEntry1.getAccountEntryId(),
				_accountRole.getAccountRoleId(), keywords + i);
		}

		BaseModelSearchResult<User> baseModelSearchResult =
			_accountUserRetriever.searchAccountRoleUsers(
				_accountEntry1.getAccountEntryId(),
				_accountRole.getAccountRoleId(), keywords, 1, 2,
				new UserIdComparator(true));

		Assert.assertEquals(_users.size(), baseModelSearchResult.getLength());

		List<User> users = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_users.get(1), users.get(0));

		baseModelSearchResult = _accountUserRetriever.searchAccountRoleUsers(
			_accountEntry1.getAccountEntryId(), _accountRole.getAccountRoleId(),
			keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserIdComparator(false));

		Assert.assertEquals(
			ListUtil.sort(_users, new UserIdComparator(false)),
			baseModelSearchResult.getBaseModels());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private User _addAccountRoleUser(long accountEntryId, long accountRoleId)
		throws Exception {

		return _addAccountRoleUser(
			accountEntryId, accountRoleId, RandomTestUtil.randomString());
	}

	private User _addAccountRoleUser(
			long accountEntryId, long accountRoleId, String screenName)
		throws Exception {

		User user = _addAccountUser(accountEntryId, screenName);

		_accountRoleLocalService.associateUser(
			accountEntryId, accountRoleId, user.getUserId());

		return user;
	}

	private User _addAccountUser(long accountEntryId) throws Exception {
		return _addAccountUser(accountEntryId, RandomTestUtil.randomString());
	}

	private User _addAccountUser(long accountEntryId, String screenName)
		throws Exception {

		User user = UserTestUtil.addUser(screenName);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntryId, user.getUserId());

		_users.add(user);

		return user;
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry1;

	@DeleteAfterTestRun
	private AccountEntry _accountEntry2;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	private AccountRole _accountRole;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private AccountUserRetriever _accountUserRetriever;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}