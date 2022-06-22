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
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
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
		_objectFieldName = StringUtil.randomId();

		_objectDefinition1 = _createObjectDefinition(_objectFieldName);
		_objectDefinition1 = _publishCustomObjectDefinition(_objectDefinition1);

		_objectDefinitionName = _objectDefinition1.getShortName();
		_objectDefinitionPrimaryKeyName = StringUtil.removeFirst(
			_objectDefinition1.getPKObjectFieldName(), "c_");

		_objectEntry1 = _createObjectEntry(
			_objectDefinition1,
			HashMapBuilder.<String, Serializable>put(
				_objectFieldName, "peter@liferay.com"
			).build());

		_objectDefinition2 = _createObjectDefinition(_objectFieldName);
		_objectDefinition2 = _publishCustomObjectDefinition(_objectDefinition2);

		_objectEntry2 = _createObjectEntry(
			_objectDefinition2,
			HashMapBuilder.<String, Serializable>put(
				_objectFieldName, "jhon@liferay.com"
			).build());
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		String value = RandomTestUtil.randomString();

		Assert.assertEquals(
			value,
			JSONUtil.getValueAsString(
				_invoke(
					new GraphQLField(
						"mutation",
						new GraphQLField(
							"c",
							new GraphQLField(
								"create" + _objectDefinitionName,
								HashMapBuilder.<String, Object>put(
									_objectDefinitionName,
									StringBundler.concat(
										"{", _objectFieldName, ": \"", value,
										"\"}")
								).build(),
								new GraphQLField(_objectFieldName))))),
				"JSONObject/data", "JSONObject/c",
				"JSONObject/create" + _objectDefinitionName,
				"Object/" + _objectFieldName));

		Assert.assertEquals(
			"Bad Request",
			JSONUtil.getValueAsString(
				_invoke(
					new GraphQLField(
						"mutation",
						new GraphQLField(
							"c",
							new GraphQLField(
								"create" + _objectDefinitionName,
								HashMapBuilder.<String, Object>put(
									_objectDefinitionName,
									StringBundler.concat(
										"{", _objectFieldName, ": \"",
										RandomTestUtil.randomString(), "\"",
										", status: draft }")
								).build(),
								new GraphQLField(_objectFieldName))))),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testDeleteObjectEntry() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"c",
				new GraphQLField(
					"delete" + _objectDefinitionName,
					HashMapBuilder.<String, Object>put(
						_objectDefinitionPrimaryKeyName,
						_objectEntry1.getObjectEntryId()
					).build())));

		JSONObject jsonObject = _invoke(graphQLField);

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"Object/delete" + _objectDefinitionName));

		jsonObject = _invoke(graphQLField);

		Assert.assertFalse(
			JSONUtil.getValueAsBoolean(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"Object/delete" + _objectDefinitionName));
	}

	@Test
	public void testGetEmptyManyToManyRelatedObjectEntries() throws Exception {
		ObjectRelationship objectRelationship = _createObjectRelationship(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY, false);

		String key = StringUtil.lowerCaseFirstLetter(
			_objectDefinition2.getShortName());

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					"c",
					new GraphQLField(
						key,
						HashMapBuilder.<String, Object>put(
							StringUtil.removeFirst(
								_objectDefinition2.getPKObjectFieldName(),
								"c_"),
							_objectEntry2.getObjectEntryId()
						).build(),
						new GraphQLField(objectRelationship.getName())))));

		JSONArray jsonArray = JSONUtil.getValueAsJSONArray(
			jsonObject, "JSONObject/data", "JSONObject/c", "JSONObject/" + key,
			"Object/" + objectRelationship.getName());

		Assert.assertEquals(0, jsonArray.length());

		_cleanObjectRelationship(objectRelationship);
	}

	@Test
	public void testGetEmptyOneToManyRelatedObjectEntries() throws Exception {
		ObjectRelationship objectRelationship = _createObjectRelationship(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, false);

		String key = StringUtil.lowerCaseFirstLetter(
			_objectDefinition2.getShortName());

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					"c",
					new GraphQLField(
						key,
						HashMapBuilder.<String, Object>put(
							StringUtil.removeFirst(
								_objectDefinition2.getPKObjectFieldName(),
								"c_"),
							_objectEntry2.getObjectEntryId()
						).build(),
						new GraphQLField(objectRelationship.getName())))));

		Assert.assertEquals(
			"null",
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"JSONObject/" + key, "Object/" + objectRelationship.getName()));

		_cleanObjectRelationship(objectRelationship);
	}

	@Test
	public void testGetListObjectEntry() throws Exception {
		String key = TextFormatter.formatPlural(
			StringUtil.lowerCaseFirstLetter(_objectDefinitionName));

		Assert.assertEquals(
			"peter@liferay.com",
			JSONUtil.getValueAsString(
				_invoke(
					new GraphQLField(
						"query",
						new GraphQLField(
							"c",
							new GraphQLField(
								key,
								HashMapBuilder.<String, Object>put(
									"filter",
									"\"" + _objectFieldName +
										" eq 'peter@liferay.com'\""
								).build(),
								new GraphQLField(
									"items",
									new GraphQLField(_objectFieldName)))))),
				"JSONObject/data", "JSONObject/c", "JSONObject/" + key,
				"Object/items", "Object/0", "Object/" + _objectFieldName));
		Assert.assertEquals(
			"peter@liferay.com",
			JSONUtil.getValueAsString(
				_invoke(
					new GraphQLField(
						"query",
						new GraphQLField(
							"c",
							new GraphQLField(
								key,
								HashMapBuilder.<String, Object>put(
									"filter",
									"\"contains(" + _objectFieldName +
										",'peter@liferay.com')\""
								).build(),
								new GraphQLField(
									"items",
									new GraphQLField(_objectFieldName)))))),
				"JSONObject/data", "JSONObject/c", "JSONObject/" + key,
				"Object/items", "Object/0", "Object/" + _objectFieldName));
		Assert.assertEquals(
			"peter@liferay.com",
			JSONUtil.getValueAsString(
				_invoke(
					new GraphQLField(
						"query",
						new GraphQLField(
							"c",
							new GraphQLField(
								key,
								HashMapBuilder.<String, Object>put(
									"filter",
									"\"userId eq " +
										TestPropsValues.getUserId() + "\""
								).build(),
								new GraphQLField(
									"items",
									new GraphQLField(_objectFieldName)))))),
				"JSONObject/data", "JSONObject/c", "JSONObject/" + key,
				"Object/items", "Object/0", "Object/" + _objectFieldName));
	}

	@Test
	public void testGetListObjectEntryFilterByObjectFieldUsingNotEquals()
		throws Exception {

		String key = TextFormatter.formatPlural(
			StringUtil.lowerCaseFirstLetter(_objectDefinitionName));

		Assert.assertEquals(
			0,
			JSONUtil.getValueAsInt(
				_invoke(
					new GraphQLField(
						"query",
						new GraphQLField(
							"c",
							new GraphQLField(
								key,
								HashMapBuilder.<String, Object>put(
									"filter",
									"\"" + _objectFieldName +
										" ne 'peter@liferay.com'\""
								).build(),
								new GraphQLField("totalCount"))))),
				"JSONObject/data", "JSONObject/c", "JSONObject/" + key,
				"Object/totalCount"));
	}

	@Test
	public void testGetObjectEntry() throws Exception {
		String key = StringUtil.lowerCaseFirstLetter(_objectDefinitionName);

		Assert.assertEquals(
			"peter@liferay.com",
			JSONUtil.getValueAsString(
				_invoke(
					new GraphQLField(
						"query",
						new GraphQLField(
							"c",
							new GraphQLField(
								key,
								HashMapBuilder.<String, Object>put(
									_objectDefinitionPrimaryKeyName,
									_objectEntry1.getObjectEntryId()
								).build(),
								new GraphQLField(_objectFieldName))))),
				"JSONObject/data", "JSONObject/c", "JSONObject/" + key,
				"Object/" + _objectFieldName));

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					"c",
					new GraphQLField(
						key,
						HashMapBuilder.<String, Object>put(
							_objectDefinitionPrimaryKeyName,
							_objectEntry1.getObjectEntryId()
						).build(),
						new GraphQLField(_objectFieldName),
						new GraphQLField("dateCreated"),
						new GraphQLField("dateModified"),
						new GraphQLField("status")))));

		Assert.assertNotNull(
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"JSONObject/" + key, "Object/dateCreated"));
		Assert.assertNotNull(
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"JSONObject/" + key, "Object/dateModified"));
		Assert.assertNotNull(
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"JSONObject/" + key, "Object/status"));
	}

	@Test
	public void testGetRelatedManyToManyObjectEntries() throws Exception {
		ObjectRelationship objectRelationship = _createObjectRelationship(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY, true);

		String key = StringUtil.lowerCaseFirstLetter(
			_objectDefinition2.getShortName());

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					"c",
					new GraphQLField(
						key,
						HashMapBuilder.<String, Object>put(
							StringUtil.removeFirst(
								_objectDefinition2.getPKObjectFieldName(),
								"c_"),
							_objectEntry2.getObjectEntryId()
						).build(),
						new GraphQLField(objectRelationship.getName())))));

		Assert.assertNotNull(
			JSONUtil.getValueAsJSONArray(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"JSONObject/" + key, "Object/" + objectRelationship.getName()));

		_cleanObjectRelationship(objectRelationship);
	}

	@Test
	public void testGetRelatedOneToManyObjectEntries() throws Exception {
		ObjectRelationship objectRelationship = _createObjectRelationship(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, true);

		String key = StringUtil.lowerCaseFirstLetter(
			_objectDefinition2.getShortName());

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					"c",
					new GraphQLField(
						key,
						HashMapBuilder.<String, Object>put(
							StringUtil.removeFirst(
								_objectDefinition2.getPKObjectFieldName(),
								"c_"),
							_objectEntry2.getObjectEntryId()
						).build(),
						new GraphQLField(objectRelationship.getName())))));

		Assert.assertNotNull(
			JSONUtil.getValueAsJSONObject(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"JSONObject/" + key, "Object/" + objectRelationship.getName()));

		_cleanObjectRelationship(objectRelationship);
	}

	@Test
	public void testUpdateObjectEntry() throws Exception {
		String value = RandomTestUtil.randomString();

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"mutation",
				new GraphQLField(
					"c",
					new GraphQLField(
						"create" + _objectDefinitionName,
						HashMapBuilder.<String, Object>put(
							_objectDefinitionName,
							StringBundler.concat(
								"{", _objectFieldName, ": \"", value, "\"}")
						).build(),
						new GraphQLField(_objectFieldName),
						new GraphQLField(_objectDefinitionPrimaryKeyName)))));

		Assert.assertEquals(
			value,
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"JSONObject/create" + _objectDefinitionName,
				"Object/" + _objectFieldName));

		value = RandomTestUtil.randomString();

		Long objectEntryId = JSONUtil.getValueAsLong(
			jsonObject, "JSONObject/data", "JSONObject/c",
			"JSONObject/create" + _objectDefinitionName,
			"Object/" + _objectDefinitionPrimaryKeyName);

		Assert.assertEquals(
			value,
			JSONUtil.getValueAsString(
				_invoke(
					new GraphQLField(
						"mutation",
						new GraphQLField(
							"c",
							new GraphQLField(
								"update" + _objectDefinitionName,
								HashMapBuilder.<String, Object>put(
									_objectDefinitionName,
									StringBundler.concat(
										"{", _objectFieldName, ": \"", value,
										"\"}")
								).put(
									_objectDefinitionPrimaryKeyName,
									String.valueOf(objectEntryId)
								).build(),
								new GraphQLField(_objectFieldName))))),
				"JSONObject/data", "JSONObject/c",
				"JSONObject/update" + _objectDefinitionName,
				"Object/" + _objectFieldName));
	}

	private void _cleanObjectRelationship(ObjectRelationship objectRelationship)
		throws Exception {

		ObjectRelationshipLocalServiceUtil.
			deleteObjectRelationshipMappingTableValues(
				objectRelationship.getObjectRelationshipId(),
				_objectEntry1.getObjectEntryId(),
				_objectEntry2.getObjectEntryId());

		ObjectRelationshipLocalServiceUtil.deleteObjectRelationship(
			objectRelationship);
	}

	private ObjectDefinition _createObjectDefinition(String objectFieldName)
		throws Exception {

		return ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
			TestPropsValues.getUserId(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"A" + RandomTestUtil.randomString(), null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), objectFieldName, false)));
	}

	private ObjectEntry _createObjectEntry(
			ObjectDefinition objectDefinition,
			HashMap<String, Serializable> values)
		throws Exception {

		return ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), values,
			ServiceContextTestUtil.getServiceContext());
	}

	private ObjectRelationship _createObjectRelationship(
			String relationshipType, boolean populateRelationship)
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipLocalServiceUtil.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(),
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(), relationshipType);

		if (populateRelationship) {
			ObjectRelationshipLocalServiceUtil.
				addObjectRelationshipMappingTableValues(
					TestPropsValues.getUserId(),
					objectRelationship.getObjectRelationshipId(),
					_objectEntry1.getObjectEntryId(),
					_objectEntry2.getObjectEntryId(),
					ServiceContextTestUtil.getServiceContext());
		}

		return objectRelationship;
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
			).toString(),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation("http://localhost:8080/o/graphql");
		options.setPost(true);

		return JSONFactoryUtil.createJSONObject(HttpUtil.URLtoString(options));
	}

	private ObjectDefinition _publishCustomObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	private String _objectDefinitionName;
	private String _objectDefinitionPrimaryKeyName;

	@DeleteAfterTestRun
	private ObjectEntry _objectEntry1;

	@DeleteAfterTestRun
	private ObjectEntry _objectEntry2;

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