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

package com.liferay.account.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.rest.client.dto.v1_0.AccountUser;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountUserResourceTest extends BaseAccountUserResourceTestCase {

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_deleteAccountUsers(_accountUsers);
	}

	@Override
	@Test
	public void testGetAccountUsersPageWithSortString() throws Exception {
		testGetAccountUsersPageWithSort(
			EntityField.Type.STRING,
			(entityField, accountUser1, accountUser2) -> {
				Class<?> clazz = accountUser1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						accountUser1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						accountUser2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (StringUtil.matchesIgnoreCase(
							entityField.getName(), "email")) {

					BeanUtils.setProperty(
						accountUser1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString() + "@liferay.com");
					BeanUtils.setProperty(
						accountUser2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString() + "@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						accountUser1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						accountUser2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountUsersPage() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"firstName", "lastName", "screenName"};
	}

	@Override
	protected AccountUser randomAccountUser() {
		return new AccountUser() {
			{
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				firstName = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				lastName = RandomTestUtil.randomString();
				middleName = RandomTestUtil.randomString();
				prefix = RandomTestUtil.randomString();
				screenName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				suffix = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected AccountUser testGetAccountUsersPage_addAccountUser(
			Long accountId, AccountUser accountUser)
		throws Exception {

		return _addAccountUser(accountId, accountUser);
	}

	@Override
	protected Long testGetAccountUsersPage_getAccountId() throws Exception {
		return _getAccountEntryId();
	}

	@Override
	protected Long testGetAccountUsersPage_getIrrelevantAccountId()
		throws Exception {

		return _getAccountEntryId();
	}

	@Override
	protected AccountUser testGraphQLAccountUser_addAccountUser()
		throws Exception {

		return _addAccountUser(_getAccountEntryId(), randomAccountUser());
	}

	@Override
	protected AccountUser testPostAccountUser_addAccountUser(
			AccountUser accountUser)
		throws Exception {

		return _addAccountUser(_getAccountEntryId(), accountUser);
	}

	private AccountEntry _addAccountEntry() throws PortalException {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(20), RandomTestUtil.randomString(20),
			null, null, WorkflowConstants.STATUS_APPROVED);

		_accountEntries.add(accountEntry);

		return accountEntry;
	}

	private AccountUser _addAccountUser(Long accountId, AccountUser accountUser)
		throws Exception {

		accountUser = accountUserResource.postAccountUser(
			accountId, accountUser);

		_accountUsers.add(accountUser);

		return accountUser;
	}

	private void _deleteAccountUsers(List<AccountUser> accountUsers) {
		for (AccountUser accountUser : accountUsers) {
			try {
				_userLocalService.deleteUser(accountUser.getId());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}
	}

	private Long _getAccountEntryId() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		return accountEntry.getAccountEntryId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountUserResourceTest.class);

	@DeleteAfterTestRun
	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private final List<AccountUser> _accountUsers = new ArrayList<>();

	@Inject
	private UserLocalService _userLocalService;

}