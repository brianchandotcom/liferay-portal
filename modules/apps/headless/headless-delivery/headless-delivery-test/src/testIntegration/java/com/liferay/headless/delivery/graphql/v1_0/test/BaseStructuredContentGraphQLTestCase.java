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

package com.liferay.headless.delivery.graphql.v1_0.test;

import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.graphql.query.GraphQLField;
import com.liferay.portal.vulcan.util.GraphQLQueryUtil;

import java.io.IOException;

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
public abstract class BaseStructuredContentGraphQLTestCase {

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
	public void testGetSiteStructuredContentByKey() throws Exception {
		StructuredContent postStructuredContent =
			testGetSiteStructuredContentByKey_addStructuredContent();

		Map<String, Object> parameterMap = new HashMap<String, Object>() {
			{
				put("structuredContentId", postStructuredContent.getId());
			}
		};

		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(GraphQLQueryUtil.field("id"));

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(
				GraphQLQueryUtil.field(additionalAssertFieldName));
		}

		GraphQLField graphQLField = GraphQLQueryUtil.field(
			"query",
			GraphQLQueryUtil.field(
				"getSiteStructuredContentByKey", parameterMap,
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject responseJSONObject = new JSONObjectImpl(
			_invoke(graphQLField.toString()));

		JSONObject dataJSONObject = responseJSONObject.getJSONObject("data");

		JSONObject jsonObject = dataJSONObject.getJSONObject(
			"getSiteStructuredContentByKey");

		Assert.assertTrue(equals(postStructuredContent, jsonObject));
	}

	protected StructuredContent
			testGetSiteStructuredContentByKey_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSiteStructuredContentByUuid() throws Exception {
		StructuredContent postStructuredContent =
			testGetSiteStructuredContentByUuid_addStructuredContent();

		Map<String, Object> parameterMap = new HashMap<String, Object>() {
			{
				put("structuredContentId", postStructuredContent.getId());
			}
		};

		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(GraphQLQueryUtil.field("id"));

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(
				GraphQLQueryUtil.field(additionalAssertFieldName));
		}

		GraphQLField graphQLField = GraphQLQueryUtil.field(
			"query",
			GraphQLQueryUtil.field(
				"getSiteStructuredContentByUuid", parameterMap,
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject responseJSONObject = new JSONObjectImpl(
			_invoke(graphQLField.toString()));

		JSONObject dataJSONObject = responseJSONObject.getJSONObject("data");

		JSONObject jsonObject = dataJSONObject.getJSONObject(
			"getSiteStructuredContentByUuid");

		Assert.assertTrue(equals(postStructuredContent, jsonObject));
	}

	protected StructuredContent
			testGetSiteStructuredContentByUuid_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testGetStructuredContent_addStructuredContent();

		Map<String, Object> parameterMap = new HashMap<String, Object>() {
			{
				put("structuredContentId", postStructuredContent.getId());
			}
		};

		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(GraphQLQueryUtil.field("id"));

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(
				GraphQLQueryUtil.field(additionalAssertFieldName));
		}

		GraphQLField graphQLField = GraphQLQueryUtil.field(
			"query",
			GraphQLQueryUtil.field(
				"getStructuredContent", parameterMap,
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject responseJSONObject = new JSONObjectImpl(
			_invoke(graphQLField.toString()));

		JSONObject dataJSONObject = responseJSONObject.getJSONObject("data");

		JSONObject jsonObject = dataJSONObject.getJSONObject(
			"getStructuredContent");

		Assert.assertTrue(equals(postStructuredContent, jsonObject));
	}

	protected StructuredContent testGetStructuredContent_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean equals(
		StructuredContent structuredContent, JSONObject jsonObject) {

		List<String> fields = new ArrayList(
			Arrays.asList(getAdditionalAssertFieldNames()));

		fields.add("id");

		for (String field : fields) {
			if (Objects.equals("contentStructureId", field)) {
				if (!Objects.equals(
						structuredContent.getContentStructureId(),
						(Long)jsonObject.getLong("contentStructureId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", field)) {
				if (!Objects.equals(
						structuredContent.getDescription(),
						(String)jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", field)) {
				if (!Objects.equals(
						structuredContent.getFriendlyUrlPath(),
						(String)jsonObject.getString("friendlyUrlPath"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", field)) {
				if (!Objects.equals(
						structuredContent.getId(),
						(Long)jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", field)) {
				if (!Objects.equals(
						structuredContent.getKey(),
						(String)jsonObject.getString("key"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("siteId", field)) {
				if (!Objects.equals(
						structuredContent.getSiteId(),
						(Long)jsonObject.getLong("siteId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", field)) {
				if (!Objects.equals(
						structuredContent.getTitle(),
						(String)jsonObject.getString("title"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("uuid", field)) {
				if (!Objects.equals(
						structuredContent.getUuid(),
						(String)jsonObject.getString("uuid"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " + field);
		}

		return true;
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected StructuredContent randomStructuredContent() throws Exception {
		return new StructuredContent() {
			{
				contentStructureId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				friendlyUrlPath = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				key = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
				title = RandomTestUtil.randomString();
				uuid = RandomTestUtil.randomString();
			}
		};
	}

	private String _invoke(String query) throws IOException {
		JSONObject jsonObject = JSONUtil.put("query", query);

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();
		httpInvoker.body(jsonObject.toString(), "application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected Company testCompany;
	protected Group testGroup;

}