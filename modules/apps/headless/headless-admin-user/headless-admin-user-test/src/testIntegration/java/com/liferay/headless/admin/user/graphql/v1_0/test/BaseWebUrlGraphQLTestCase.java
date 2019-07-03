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

import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
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
public abstract class BaseWebUrlGraphQLTestCase {

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
	public void testGetWebUrl() throws Exception {
		WebUrl postWebUrl = testGetWebUrl_addWebUrl();

		Map<String, Object> parameterMap = new HashMap<String, Object>() {
			{
				put("webUrlId", postWebUrl.getId());
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
				"getWebUrl", parameterMap,
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject responseJSONObject = new JSONObjectImpl(
			_invoke(graphQLField.toString()));

		JSONObject dataJSONObject = responseJSONObject.getJSONObject("data");

		JSONObject jsonObject = dataJSONObject.getJSONObject("getWebUrl");

		Assert.assertTrue(equals(postWebUrl, jsonObject));
	}

	protected WebUrl testGetWebUrl_addWebUrl() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean equals(WebUrl webUrl, JSONObject jsonObject) {
		List<String> fields = new ArrayList(
			Arrays.asList(getAdditionalAssertFieldNames()));

		fields.add("id");

		for (String field : fields) {
			if (Objects.equals("id", field)) {
				if (!Objects.equals(
						webUrl.getId(), (Long)jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("url", field)) {
				if (!Objects.equals(
						webUrl.getUrl(), (String)jsonObject.getString("url"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("urlType", field)) {
				if (!Objects.equals(
						webUrl.getUrlType(),
						(String)jsonObject.getString("urlType"))) {

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

	protected WebUrl randomWebUrl() throws Exception {
		return new WebUrl() {
			{
				id = RandomTestUtil.randomLong();
				url = RandomTestUtil.randomString();
				urlType = RandomTestUtil.randomString();
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