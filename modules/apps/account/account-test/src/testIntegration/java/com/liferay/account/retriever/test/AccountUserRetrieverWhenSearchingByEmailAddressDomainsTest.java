/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.retriever.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.test.util.AccountEntryArgs;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
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
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.comparator.UserEmailAddressComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class AccountUserRetrieverWhenSearchingByEmailAddressDomainsTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldReturnAccountUsersWithAnyEmailAddressDomain()
		throws Exception {

		_users.add(UserTestUtil.addUser());
		_users.add(_addAccountUser("liferay.com"));
		_users.add(_addAccountUser("test.com"));

		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withDomains("test.com"));

		BaseModelSearchResult<User> expectedBaseModelSearchResult =
			_accountUserRetriever.searchAccountUsers(
				new long[] {AccountConstants.ACCOUNT_ENTRY_ID_ANY}, null,
				new LinkedHashMap<>(), WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, "screen-name", false);
		BaseModelSearchResult<User> actualBaseModelSearchResult =
			_accountUserRetriever.searchAccountUsers(
				new long[] {AccountConstants.ACCOUNT_ENTRY_ID_ANY}, null,
				new LinkedHashMap<>(), WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, "screen-name", false);

		Assert.assertEquals(
			expectedBaseModelSearchResult.getLength(),
			actualBaseModelSearchResult.getLength());
		Assert.assertEquals(
			expectedBaseModelSearchResult.getBaseModels(),
			actualBaseModelSearchResult.getBaseModels());
	}

	@Test
	public void testShouldReturnAccountUsersWithMatchingEmailAddressDomain()
		throws Exception {

		_users.add(_addUser("liferay.com"));
		_users.add(_addAccountUser("liferay.com"));

		String[] emailAddressDomains = {"test1.com", "test2.com"};

		List<User> users = new ArrayList<>();

		for (String emailAddressDomain : emailAddressDomains) {
			users.add(_addAccountUser(emailAddressDomain));

			_users.add(_addUser(emailAddressDomain));
		}

		_users.addAll(users);

		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withDomains(emailAddressDomains));

		BaseModelSearchResult<User> baseModelSearchResult =
			_accountUserRetriever.searchAccountUsers(
				new long[] {AccountConstants.ACCOUNT_ENTRY_ID_ANY}, null,
				LinkedHashMapBuilder.<String, Serializable>put(
					"emailAddressDomains", emailAddressDomains
				).build(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, "email-address", false);

		Assert.assertEquals(users.size(), baseModelSearchResult.getLength());
		Assert.assertEquals(
			ListUtil.sort(users, new UserEmailAddressComparator(true)),
			baseModelSearchResult.getBaseModels());
	}

	@Test
	public void testShouldReturnNoAccountUsersIfAccountEntryHasNoDomains()
		throws Exception {

		_users.add(UserTestUtil.addUser());
		_users.add(_addAccountUser("test.com"));

		_accountEntry = AccountEntryTestUtil.addAccountEntry();

		String[] emailAddressDomains = StringUtil.split(
			_accountEntry.getDomains());

		BaseModelSearchResult<User> baseModelSearchResult =
			_accountUserRetriever.searchAccountUsers(
				new long[] {AccountConstants.ACCOUNT_ENTRY_ID_ANY}, null,
				LinkedHashMapBuilder.<String, Serializable>put(
					"emailAddressDomains", emailAddressDomains
				).build(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, "email-address", false);

		Assert.assertEquals(0, baseModelSearchResult.getLength());
		Assert.assertTrue(
			ListUtil.isEmpty(baseModelSearchResult.getBaseModels()));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private User _addAccountUser(String emailAddressDomain) throws Exception {
		User user = _addUser(emailAddressDomain);

		_accountEntryUserRels.add(
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, user.getUserId()));

		return user;
	}

	private User _addUser(String emailAddressDomain) throws Exception {
		return UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringPool.BLANK,
			RandomTestUtil.randomString() + StringPool.AT + emailAddressDomain,
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@DeleteAfterTestRun
	private final List<AccountEntryUserRel> _accountEntryUserRels =
		new ArrayList<>();

	@Inject
	private AccountUserRetriever _accountUserRetriever;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}