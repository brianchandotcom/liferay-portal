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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import org.junit.After;
import org.junit.Assert;
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

	@Override
	@Test
	public void testGraphQLGetAccountUsersPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		long accountEntryId = _getAccountEntryId();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"accountUsers",
				HashMapBuilder.<String, Object>put(
					"accountId", accountEntryId
				).put(
					"page", 1
				).put(
					"pageSize", 2
				).build(),
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject accountUsersJSONObject = dataJSONObject.getJSONObject(
			"accountUsers");

		Assert.assertEquals(0, accountUsersJSONObject.get("totalCount"));

		AccountUser accountUser1 = testGraphQLAccountUser_addAccountUser(
			accountEntryId);
		AccountUser accountUser2 = testGraphQLAccountUser_addAccountUser(
			accountEntryId);

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		accountUsersJSONObject = dataJSONObject.getJSONObject("accountUsers");

		Assert.assertEquals(2, accountUsersJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(accountUser1, accountUser2),
			accountUsersJSONObject.getJSONArray("items"));
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

	protected AccountUser testGraphQLAccountUser_addAccountUser(
			long accountEntryId)
		throws Exception {

		return _addAccountUser(accountEntryId, randomAccountUser());
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