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

package com.liferay.headless.admin.user.graphql.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class UserAccountGraphQLTest extends BaseUserAccountGraphQLTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		User testUser = UserLocalServiceUtil.getUserByEmailAddress(
			testGroup.getCompanyId(), "test@liferay.com");

		UserLocalServiceUtil.deleteGroupUser(
			testGroup.getGroupId(), testUser.getUserId());

		// See LPS-94496 for why we have to delete all users except for the
		// test user

		List<User> users = UserLocalServiceUtil.getUsers(
			PortalUtil.getDefaultCompanyId(), false,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (User user : users) {
			if (user.getUserId() != testUser.getUserId()) {
				UserLocalServiceUtil.deleteUser(user);
			}
		}

		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			testUser.getModelClassName());

		List<Company> companies = CompanyLocalServiceUtil.getCompanies();

		for (Company company : companies) {
			IndexWriterHelperUtil.deleteEntityDocuments(
				indexer.getSearchEngineId(), company.getCompanyId(),
				testUser.getModelClassName(), true);

			indexer.reindex(
				new String[] {String.valueOf(company.getCompanyId())});
		}
	}

	@Ignore
	@Override
	@Test
	public void testGetMyUserAccount() throws Exception {
	}

	@Test
	public void testGetUserAccountsPage() throws Exception {
		UserAccount userAccount1 = testUserAccount_addUserAccount();
		UserAccount userAccount2 = testUserAccount_addUserAccount();

		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"userAccounts",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 3);
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject userAccountsJSONObject = dataJSONObject.getJSONObject(
			"userAccounts");

		Assert.assertEquals(3, userAccountsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2),
			userAccountsJSONObject.getJSONArray("items"));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"familyName", "givenName"};
	}

	@Override
	protected UserAccount randomUserAccount() throws Exception {
		UserAccount userAccount = super.randomUserAccount();

		userAccount.setEmailAddress(
			userAccount.getEmailAddress() + "@liferay.com");

		return userAccount;
	}

	@Override
	protected UserAccount testUserAccount_addUserAccount() throws Exception {
		UserAccount userAccount = randomUserAccount();

		User user = UserLocalServiceUtil.addUser(
			UserConstants.USER_ID_DEFAULT, PortalUtil.getDefaultCompanyId(),
			true, null, null, Validator.isNull(userAccount.getAlternateName()),
			userAccount.getAlternateName(), userAccount.getEmailAddress(), 0,
			StringPool.BLANK, LocaleUtil.getDefault(),
			userAccount.getGivenName(), StringPool.BLANK,
			userAccount.getFamilyName(), 0, 0, true, 1, 1, 1970,
			userAccount.getJobTitle(), null, null, null, null, false,
			new ServiceContext() {
				{
					setCreateDate(userAccount.getDateCreated());
					setModifiedDate(userAccount.getDateModified());
				}
			});

		userAccount.setDateModified(user.getModifiedDate());
		userAccount.setId(user.getUserId());

		_users.add(UserLocalServiceUtil.getUser(user.getUserId()));

		return userAccount;
	}

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}