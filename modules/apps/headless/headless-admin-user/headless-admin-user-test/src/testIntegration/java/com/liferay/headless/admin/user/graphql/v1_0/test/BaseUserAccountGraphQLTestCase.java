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

import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseUserAccountGraphQLTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetMyUserAccount() throws Exception {
		UserAccount userAccount = testUserAccount_addUserAccount();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"myUserAccount",
				new HashMap<String, Object>() {
					{
						put("userAccountId", userAccount.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equals(userAccount, dataJSONObject.getJSONObject("myUserAccount")));
	}

	@Test
	public void testGetUserAccountsPage() throws Exception {
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
						put("pageSize", 2);
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject userAccountsJSONObject = dataJSONObject.getJSONObject(
			"userAccounts");

		Assert.assertEquals(0, userAccountsJSONObject.get("totalCount"));

		UserAccount userAccount1 = testUserAccount_addUserAccount();
		UserAccount userAccount2 = testUserAccount_addUserAccount();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		userAccountsJSONObject = dataJSONObject.getJSONObject("userAccounts");

		Assert.assertEquals(2, userAccountsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2),
			userAccountsJSONObject.getJSONArray("items"));
	}

	@Test
	public void testGetUserAccount() throws Exception {
		UserAccount userAccount = testUserAccount_addUserAccount();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"userAccount",
				new HashMap<String, Object>() {
					{
						put("userAccountId", userAccount.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equals(userAccount, dataJSONObject.getJSONObject("userAccount")));
	}

	protected void assertEqualsIgnoringOrder(
		List<UserAccount> userAccounts, JSONArray jsonArray) {

		for (UserAccount userAccount : userAccounts) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equals(userAccount, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + userAccount, contains);
		}
	}

	protected boolean equals(UserAccount userAccount, JSONObject jsonObject) {
		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("additionalName", fieldName)) {
				if (!Objects.equals(
						userAccount.getAdditionalName(),
						(String)jsonObject.getString("additionalName"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("alternateName", fieldName)) {
				if (!Objects.equals(
						userAccount.getAlternateName(),
						(String)jsonObject.getString("alternateName"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dashboardURL", fieldName)) {
				if (!Objects.equals(
						userAccount.getDashboardURL(),
						(String)jsonObject.getString("dashboardURL"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("emailAddress", fieldName)) {
				if (!Objects.equals(
						userAccount.getEmailAddress(),
						(String)jsonObject.getString("emailAddress"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("familyName", fieldName)) {
				if (!Objects.equals(
						userAccount.getFamilyName(),
						(String)jsonObject.getString("familyName"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("givenName", fieldName)) {
				if (!Objects.equals(
						userAccount.getGivenName(),
						(String)jsonObject.getString("givenName"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("honorificPrefix", fieldName)) {
				if (!Objects.equals(
						userAccount.getHonorificPrefix(),
						(String)jsonObject.getString("honorificPrefix"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("honorificSuffix", fieldName)) {
				if (!Objects.equals(
						userAccount.getHonorificSuffix(),
						(String)jsonObject.getString("honorificSuffix"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.equals(
						userAccount.getId(), (Long)jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", fieldName)) {
				if (!Objects.equals(
						userAccount.getImage(),
						(String)jsonObject.getString("image"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("jobTitle", fieldName)) {
				if (!Objects.equals(
						userAccount.getJobTitle(),
						(String)jsonObject.getString("jobTitle"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.equals(
						userAccount.getName(),
						(String)jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("profileURL", fieldName)) {
				if (!Objects.equals(
						userAccount.getProfileURL(),
						(String)jsonObject.getString("profileURL"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("id"));

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected UserAccount randomUserAccount() throws Exception {
		return new UserAccount() {
			{
				additionalName = RandomTestUtil.randomString();
				alternateName = RandomTestUtil.randomString();
				birthDate = RandomTestUtil.nextDate();
				dashboardURL = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				emailAddress = RandomTestUtil.randomString();
				familyName = RandomTestUtil.randomString();
				givenName = RandomTestUtil.randomString();
				honorificPrefix = RandomTestUtil.randomString();
				honorificSuffix = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				image = RandomTestUtil.randomString();
				jobTitle = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				profileURL = RandomTestUtil.randomString();
			}
		};
	}

	protected UserAccount testUserAccount_addUserAccount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

}