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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.test.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.comparator.UserEmailAddressComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
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
			_accountEntryLocalService, new String[] {"test.com"});

		BaseModelSearchResult<User> users =
			_accountUserRetriever.searchAccountUsers(
				AccountConstants.ACCOUNT_ENTRY_ID_ANY, null,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, "screen-name", false);

		BaseModelSearchResult<User> expectedUsers =
			_accountUserRetriever.searchAccountUsers(
				AccountConstants.ACCOUNT_ENTRY_ID_ANY, null,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, "screen-name", false);

		Assert.assertEquals(expectedUsers.getLength(), users.getLength());
		Assert.assertEquals(
			expectedUsers.getBaseModels(), users.getBaseModels());
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
			_accountEntryLocalService, emailAddressDomains);

		BaseModelSearchResult<User> baseModelSearchResult =
			_accountUserRetriever.searchAccountUsers(
				AccountConstants.ACCOUNT_ENTRY_ID_ANY, emailAddressDomains,
				null, WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, "email-address", false);

		Assert.assertEquals(
			users.size(), baseModelSearchResult.getLength());
		Assert.assertEquals(
			ListUtil.sort(users, new UserEmailAddressComparator(true)),
			baseModelSearchResult.getBaseModels());
	}

	@Test
	public void testShouldReturnNoAccountUsersIfAccountEntryHasNoDomains()
		throws Exception {

		_users.add(UserTestUtil.addUser());
		_users.add(_addAccountUser("test.com"));

		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		String[] emailAddressDomains = StringUtil.split(
			_accountEntry.getDomains());

		BaseModelSearchResult<User> baseModelSearchResult =
			_accountUserRetriever.searchAccountUsers(
				AccountConstants.ACCOUNT_ENTRY_ID_ANY, emailAddressDomains,
				null, WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, "email-address", false);

		Assert.assertEquals(0, baseModelSearchResult.getLength());
		Assert.assertTrue(
			ListUtil.isEmpty(baseModelSearchResult.getBaseModels()));
	}

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
			RandomTestUtil.randomString(),
			LocaleUtil.getDefault(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@DeleteAfterTestRun
	private final List<AccountEntryUserRel> _accountEntryUserRels =
		new ArrayList<>();

	@Inject
	private AccountUserRetriever _accountUserRetriever;

	@Inject
	private UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}