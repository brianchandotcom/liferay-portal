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

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.OutputStreamWriter;
import java.io.Serializable;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionGraphQLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_name = "A" + RandomTestUtil.randomString(5);
		_columnName = "a" + RandomTestUtil.randomString(5);

		_objectDefinition =
			ObjectDefinitionLocalServiceUtil.addObjectDefinition(
				TestPropsValues.getUserId(), _name,
				Collections.singletonList(
					_createObjectField(_columnName, "String")));

		_objectEntry = ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				_columnName, "peter@liferay.com"
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testGraphQLAddObjectEntry() throws Exception {
		String value = RandomTestUtil.randomString();

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"mutation",
				new GraphQLField(
					"create" + _name,
					HashMapBuilder.<String, Object>put(
						_name,
						StringBundler.concat(
							"{", _columnName, ":\"", value, "\"}")
					).put(
						"siteId", TestPropsValues.getGroupId()
					).build(),
					new GraphQLField(_columnName))));

		Assert.assertEquals(
			value,
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/create" + _name,
				"Object/" + _columnName));
	}

	@Test
	public void testGraphQLDeleteObjectEntry() throws Exception {
		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"mutation",
				new GraphQLField(
					"delete" + _name,
					HashMapBuilder.<String, Object>put(
						StringUtil.removeSubstring(
							_objectDefinition.getDBPrimaryKeyColumnName(), "_"),
						_objectEntry.getObjectEntryId()
					).build())));

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				jsonObject, "JSONObject/data", "Object/delete" + _name));
	}

	@Test
	public void testGraphQLGetListObjectEntry() throws Exception {
		String fieldName = TextFormatter.formatPlural(
			StringUtil.lowerCaseFirstLetter(_name));

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					fieldName,
					HashMapBuilder.<String, Object>put(
						"filter",
						"\"userId eq " + TestPropsValues.getUserId() + "\""
					).build(),
					new GraphQLField("items", new GraphQLField(_columnName)))));

		Assert.assertEquals(
			"peter@liferay.com",
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/" + fieldName,
				"Object/items", "Object/0", "Object/" + _columnName));
	}

	@Test
	public void testGraphQLGetObjectEntry() throws Exception {
		String fieldName = StringUtil.lowerCaseFirstLetter(_name);

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					fieldName,
					HashMapBuilder.<String, Object>put(
						StringUtil.removeSubstring(
							_objectDefinition.getDBPrimaryKeyColumnName(), "_"),
						_objectEntry.getObjectEntryId()
					).build(),
					new GraphQLField(_columnName))));

		Assert.assertEquals(
			"peter@liferay.com",
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/" + fieldName,
				"Object/" + _columnName));
	}

	private ObjectField _createObjectField(String name, String type) {
		ObjectField objectField = ObjectFieldLocalServiceUtil.createObjectField(
			0);

		objectField.setName(name);
		objectField.setType(type);

		return objectField;
	}

	private HttpURLConnection _getHttpURLConnection() throws Exception {
		URL url = new URL("http://localhost:8080/o/graphql");

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		Base64.Encoder encoder = Base64.getEncoder();

		httpURLConnection.setDoOutput(true);
		String encoded = encoder.encodeToString(
			"test@liferay.com:test".getBytes(StandardCharsets.UTF_8));

		httpURLConnection.setRequestProperty(
			"Authorization", "Basic " + encoded);

		httpURLConnection.setRequestProperty(
			"Content-Type", "application/json");
		httpURLConnection.setRequestMethod("POST");

		return httpURLConnection;
	}

	private JSONObject _invoke(GraphQLField queryGraphQLField)
		throws Exception {

		HttpURLConnection httpURLConnection = _getHttpURLConnection();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			httpURLConnection.getOutputStream());

		outputStreamWriter.write(
			String.valueOf(
				JSONUtil.put("query", queryGraphQLField.toString())));

		outputStreamWriter.flush();

		return JSONFactoryUtil.createJSONObject(
			StringUtil.read(httpURLConnection.getInputStream()));
	}

	private String _columnName;
	private String _name;
	private ObjectDefinition _objectDefinition;
	private ObjectEntry _objectEntry;

	private static class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

}