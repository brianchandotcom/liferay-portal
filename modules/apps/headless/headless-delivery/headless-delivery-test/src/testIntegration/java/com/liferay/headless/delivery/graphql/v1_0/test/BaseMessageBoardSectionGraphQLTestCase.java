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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardSection;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
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
public abstract class BaseMessageBoardSectionGraphQLTestCase {

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
	public void testDeleteMessageBoardSection() throws Exception {
		MessageBoardSection messageBoardSection =
			testMessageBoardSection_addMessageBoardSection();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteMessageBoardSection",
				new HashMap<String, Object>() {
					{
						put(
							"messageBoardSectionId",
							messageBoardSection.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			dataJSONObject.getBoolean("deleteMessageBoardSection"));

		graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"messageBoardSection",
				new HashMap<String, Object>() {
					{
						put(
							"messageBoardSectionId",
							messageBoardSection.getId());
					}
				},
				new GraphQLField("id")));

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONArray errors = jsonObject.getJSONArray("errors");

		Assert.assertTrue(errors.length() > 0);
	}

	@Test
	public void testGetMessageBoardSection() throws Exception {
		MessageBoardSection messageBoardSection =
			testMessageBoardSection_addMessageBoardSection();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"messageBoardSection",
				new HashMap<String, Object>() {
					{
						put(
							"messageBoardSectionId",
							messageBoardSection.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equals(
				messageBoardSection,
				dataJSONObject.getJSONObject("messageBoardSection")));
	}

	@Test
	public void testGetSiteMessageBoardSectionsPage() throws Exception {
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
				"messageBoardSections",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
						put("siteId", testGroup.getGroupId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject messageBoardSectionsJSONObject =
			dataJSONObject.getJSONObject("messageBoardSections");

		Assert.assertEquals(
			0, messageBoardSectionsJSONObject.get("totalCount"));

		MessageBoardSection messageBoardSection1 =
			testMessageBoardSection_addMessageBoardSection();
		MessageBoardSection messageBoardSection2 =
			testMessageBoardSection_addMessageBoardSection();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		messageBoardSectionsJSONObject = dataJSONObject.getJSONObject(
			"messageBoardSections");

		Assert.assertEquals(
			2, messageBoardSectionsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardSection1, messageBoardSection2),
			messageBoardSectionsJSONObject.getJSONArray("items"));
	}

	@Test
	public void testPostSiteMessageBoardSection() throws Exception {
		MessageBoardSection randomMessageBoardSection =
			randomMessageBoardSection();

		MessageBoardSection messageBoardSection =
			testMessageBoardSection_addMessageBoardSection(
				randomMessageBoardSection);

		Assert.assertTrue(
			equals(
				randomMessageBoardSection,
				JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.serialize(messageBoardSection))));
	}

	protected void assertEqualsIgnoringOrder(
		List<MessageBoardSection> messageBoardSections, JSONArray jsonArray) {

		for (MessageBoardSection messageBoardSection : messageBoardSections) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equals(messageBoardSection, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + messageBoardSection,
				contains);
		}
	}

	protected boolean equals(
		MessageBoardSection messageBoardSection, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("description", fieldName)) {
				if (!Objects.equals(
						messageBoardSection.getDescription(),
						(String)jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.equals(
						messageBoardSection.getId(),
						(Long)jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("siteId", fieldName)) {
				if (!Objects.equals(
						messageBoardSection.getSiteId(),
						(Long)jsonObject.getLong("siteId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", fieldName)) {
				if (!Objects.equals(
						messageBoardSection.getTitle(),
						(String)jsonObject.getString("title"))) {

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

	protected MessageBoardSection randomMessageBoardSection() throws Exception {
		return new MessageBoardSection() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected MessageBoardSection
			testMessageBoardSection_addMessageBoardSection()
		throws Exception {

		return testMessageBoardSection_addMessageBoardSection(
			randomMessageBoardSection());
	}

	protected MessageBoardSection
			testMessageBoardSection_addMessageBoardSection(
				MessageBoardSection messageBoardSection)
		throws Exception {

		StringBuilder sb = new StringBuilder("{");

		for (String field : getAdditionalAssertFieldNames()) {
			if (Objects.equals("description", field)) {
				sb.append(field);
				sb.append(":");

				Object value = messageBoardSection.getDescription();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(",");
			}

			if (Objects.equals("id", field)) {
				sb.append(field);
				sb.append(":");

				Object value = messageBoardSection.getId();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(",");
			}

			if (Objects.equals("siteId", field)) {
				sb.append(field);
				sb.append(":");

				Object value = messageBoardSection.getSiteId();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(",");
			}

			if (Objects.equals("title", field)) {
				sb.append(field);
				sb.append(":");

				Object value = messageBoardSection.getTitle();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(",");
			}
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"createSiteMessageBoardSection",
				new HashMap<String, Object>() {
					{
						put("siteId", testGroup.getGroupId());
						put("messageBoardSection", sb.toString());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONDeserializer<MessageBoardSection> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		String object = invoke(graphQLField.toString());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(object);

		String data = jsonObject.getJSONObject(
			"data"
		).getJSONObject(
			"createSiteMessageBoardSection"
		).toString();

		return jsonDeserializer.deserialize(data, MessageBoardSection.class);
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