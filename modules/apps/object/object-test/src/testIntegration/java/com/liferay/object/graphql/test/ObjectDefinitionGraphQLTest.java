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

package com.liferay.object.graphql.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Arrays;
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
		_objectDefinitionName = "A" + RandomTestUtil.randomString(5);
		_objectFieldName = "a" + RandomTestUtil.randomString(5);

		_objectDefinition =
			ObjectDefinitionLocalServiceUtil.addObjectDefinition(
				TestPropsValues.getUserId(), _objectDefinitionName,
				Collections.singletonList(
					_createObjectField(_objectFieldName, "String")));

		_objectEntry = ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				_objectFieldName, "peter@liferay.com"
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		String value = RandomTestUtil.randomString();

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"mutation",
				new GraphQLField(
					"create" + _objectDefinitionName,
					HashMapBuilder.<String, Object>put(
						_objectDefinitionName,
						StringBundler.concat(
							"{", _objectFieldName, ": \"", value, "\"}")
					).put(
						"siteId", TestPropsValues.getGroupId()
					).build(),
					new GraphQLField(_objectFieldName))));

		Assert.assertEquals(
			value,
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data",
				"JSONObject/create" + _objectDefinitionName,
				"Object/" + _objectFieldName));
	}

	@Test
	public void testDeleteObjectEntry() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"delete" + _objectDefinitionName,
				HashMapBuilder.<String, Object>put(
					_objectDefinition.getPrimaryKeyColumnName(),
					_objectEntry.getObjectEntryId()
				).build()));

		JSONObject jsonObject = _invoke(graphQLField);

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				jsonObject, "JSONObject/data",
				"Object/delete" + _objectDefinitionName));

		jsonObject = _invoke(graphQLField);

		Assert.assertFalse(
			JSONUtil.getValueAsBoolean(
				jsonObject, "JSONObject/data",
				"Object/delete" + _objectDefinitionName));
	}

	@Test
	public void testGetListObjectEntry() throws Exception {
		String key = TextFormatter.formatPlural(
			StringUtil.lowerCaseFirstLetter(_objectDefinitionName));

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					key,
					HashMapBuilder.<String, Object>put(
						"filter",
						"\"userId eq " + TestPropsValues.getUserId() + "\""
					).build(),
					new GraphQLField(
						"items", new GraphQLField(_objectFieldName)))));

		Assert.assertEquals(
			"peter@liferay.com",
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/" + key,
				"Object/items", "Object/0", "Object/" + _objectFieldName));
	}

	@Test
	public void testGetObjectEntry() throws Exception {
		String key = StringUtil.lowerCaseFirstLetter(_objectDefinitionName);

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					key,
					HashMapBuilder.<String, Object>put(
						_objectDefinition.getPrimaryKeyColumnName(),
						_objectEntry.getObjectEntryId()
					).build(),
					new GraphQLField(_objectFieldName))));

		Assert.assertEquals(
			"peter@liferay.com",
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/" + key,
				"Object/" + _objectFieldName));
	}

	@Test
	public void testUpdateObjectEntry() throws Exception {
		String value = RandomTestUtil.randomString();

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"mutation",
				new GraphQLField(
					"create" + _objectDefinitionName,
					HashMapBuilder.<String, Object>put(
						_objectDefinitionName,
						StringBundler.concat(
							"{", _objectFieldName, ": \"", value, "\"}")
					).put(
						"siteId", TestPropsValues.getGroupId()
					).build(),
					new GraphQLField(_objectFieldName),
					new GraphQLField(
						_objectDefinition.getPrimaryKeyColumnName()))));

		Assert.assertEquals(
			value,
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data",
				"JSONObject/create" + _objectDefinitionName,
				"Object/" + _objectFieldName));

		value = RandomTestUtil.randomString();

		Long objectEntryId = JSONUtil.getValueAsLong(
			jsonObject, "JSONObject/data",
			"JSONObject/create" + _objectDefinitionName,
			"Object/" + _objectDefinition.getPrimaryKeyColumnName());

		jsonObject = _invoke(
			new GraphQLField(
				"mutation",
				new GraphQLField(
					"update" + _objectDefinitionName,
					HashMapBuilder.<String, Object>put(
						_objectDefinitionName,
						StringBundler.concat(
							"{", _objectFieldName, ": \"", value, "\"}")
					).put(
						_objectDefinition.getPrimaryKeyColumnName(),
						String.valueOf(objectEntryId)
					).build(),
					new GraphQLField(_objectFieldName))));

		Assert.assertEquals(
			value,
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data",
				"JSONObject/update" + _objectDefinitionName,
				"Object/" + _objectFieldName));
	}

	private ObjectField _createObjectField(String name, String type) {
		ObjectField objectField = ObjectFieldLocalServiceUtil.createObjectField(
			0);

		objectField.setName(name);
		objectField.setType(type);

		return objectField;
	}

	private JSONObject _invoke(GraphQLField queryGraphQLField)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.addHeader(
			"Authorization",
			"Basic " + Base64.encode("test@liferay.com:test".getBytes()));
		options.setBody(
			JSONUtil.put(
				"query", queryGraphQLField.toString()
			).toJSONString(),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation("http://localhost:8080/o/graphql");
		options.setPost(true);

		return JSONFactoryUtil.createJSONObject(HttpUtil.URLtoString(options));
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	private String _objectDefinitionName;
	private ObjectEntry _objectEntry;
	private String _objectFieldName;

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