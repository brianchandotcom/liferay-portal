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

import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
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
public abstract class BasePostalAddressGraphQLTestCase {

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
	public void testGetPostalAddress() throws Exception {
		PostalAddress postPostalAddress =
			testGetPostalAddress_addPostalAddress();

		Map<String, Object> parameterMap = new HashMap<String, Object>() {
			{
				put("postalAddressId", postPostalAddress.getId());
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
				"getPostalAddress", parameterMap,
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject responseJSONObject = new JSONObjectImpl(
			_invoke(graphQLField.toString()));

		JSONObject dataJSONObject = responseJSONObject.getJSONObject("data");

		JSONObject jsonObject = dataJSONObject.getJSONObject(
			"getPostalAddress");

		Assert.assertTrue(equals(postPostalAddress, jsonObject));
	}

	protected PostalAddress testGetPostalAddress_addPostalAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean equals(
		PostalAddress postalAddress, JSONObject jsonObject) {

		List<String> fields = new ArrayList(
			Arrays.asList(getAdditionalAssertFieldNames()));

		fields.add("id");

		for (String field : fields) {
			if (Objects.equals("addressCountry", field)) {
				if (!Objects.equals(
						postalAddress.getAddressCountry(),
						(String)jsonObject.getString("addressCountry"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressLocality", field)) {
				if (!Objects.equals(
						postalAddress.getAddressLocality(),
						(String)jsonObject.getString("addressLocality"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressRegion", field)) {
				if (!Objects.equals(
						postalAddress.getAddressRegion(),
						(String)jsonObject.getString("addressRegion"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressType", field)) {
				if (!Objects.equals(
						postalAddress.getAddressType(),
						(String)jsonObject.getString("addressType"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", field)) {
				if (!Objects.equals(
						postalAddress.getId(),
						(Long)jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("postalCode", field)) {
				if (!Objects.equals(
						postalAddress.getPostalCode(),
						(String)jsonObject.getString("postalCode"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("primary", field)) {
				if (!Objects.equals(
						postalAddress.getPrimary(),
						(Boolean)jsonObject.getBoolean("primary"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("streetAddressLine1", field)) {
				if (!Objects.equals(
						postalAddress.getStreetAddressLine1(),
						(String)jsonObject.getString("streetAddressLine1"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("streetAddressLine2", field)) {
				if (!Objects.equals(
						postalAddress.getStreetAddressLine2(),
						(String)jsonObject.getString("streetAddressLine2"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("streetAddressLine3", field)) {
				if (!Objects.equals(
						postalAddress.getStreetAddressLine3(),
						(String)jsonObject.getString("streetAddressLine3"))) {

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

	protected PostalAddress randomPostalAddress() throws Exception {
		return new PostalAddress() {
			{
				addressCountry = RandomTestUtil.randomString();
				addressLocality = RandomTestUtil.randomString();
				addressRegion = RandomTestUtil.randomString();
				addressType = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				postalCode = RandomTestUtil.randomString();
				primary = RandomTestUtil.randomBoolean();
				streetAddressLine1 = RandomTestUtil.randomString();
				streetAddressLine2 = RandomTestUtil.randomString();
				streetAddressLine3 = RandomTestUtil.randomString();
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