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
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.retriever.AccountsUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.test.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountsUserRetrieverTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetAccountUsers() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		List<User> users = new ArrayList<>();

		users.add(UserTestUtil.addUser());
		users.add(UserTestUtil.addUser());
		users.add(UserTestUtil.addUser());

		_users.addAll(users);

		for (User user : users) {
			_accountEntryUserRels.add(
				_accountEntryUserRelLocalService.addAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
		}

		long[] expectedUserIds = ListUtil.toLongArray(
			users, User.USER_ID_ACCESSOR);

		Arrays.sort(expectedUserIds);

		List<User> actualUsers = _accountsUserRetriever.getAccountUsers(
			_accountEntry.getAccountEntryId());

		long[] actualUserIds = ListUtil.toLongArray(
			actualUsers, User::getUserId);

		Arrays.sort(actualUserIds);

		Assert.assertArrayEquals(expectedUserIds, actualUserIds);
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
	private AccountsUserRetriever _accountsUserRetriever;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}