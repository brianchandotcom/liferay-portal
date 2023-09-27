/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.graphql.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalServiceUtil;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
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

		_listFieldName = StringUtil.randomId();

		ListTypeDefinition listTypeDefinition =
			ListTypeDefinitionLocalServiceUtil.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(_listFieldName), false,
				Collections.emptyList());

		_addListTypeEntry(listTypeDefinition, StringUtil.randomId());
		_addListTypeEntry(listTypeDefinition, StringUtil.randomId());

		_listFieldValueKey = StringUtil.randomId();

		_addListTypeEntry(listTypeDefinition, _listFieldValueKey);

		_parentObjectDefinition = _addObjectDefinition();

		ObjectFieldUtil.addCustomObjectField(
			new PicklistObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).listTypeDefinitionId(
				listTypeDefinition.getListTypeDefinitionId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).indexedAsKeyword(
				true
			).name(
				_listFieldName
			).objectDefinitionId(
				_parentObjectDefinition.getObjectDefinitionId()
			).required(
				true
			).build());

		_parentObjectDefinitionName = _parentObjectDefinition.getShortName();
		_parentObjectDefinitionPrimaryKeyName = StringUtil.removeFirst(
			_parentObjectDefinition.getPKObjectFieldName(), "c_");

		ObjectDefinition childObjectDefinition = _addObjectDefinition();

		_childObjectDefinitionName = childObjectDefinition.getShortName();

		ObjectRelationshipLocalServiceUtil.addObjectRelationship(
			TestPropsValues.getUserId(),
			_parentObjectDefinition.getObjectDefinitionId(),
			childObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			_RELATIONSHIP_NAME, ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_parentObjectDefinition =
			ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_parentObjectDefinition.getObjectDefinitionId());

		childObjectDefinition =
			ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				childObjectDefinition.getObjectDefinitionId());

		_parentObjectEntry = ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_parentObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				_listFieldName, _listFieldValueKey
			).put(
				_objectFieldName, "peter@liferay.com"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_childObjectEntry = ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			childObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				StringBundler.concat(
					"r_", _RELATIONSHIP_NAME, "_",
					_parentObjectDefinition.getPKObjectFieldName()),
				_parentObjectEntry.getObjectEntryId()
			).put(
				_objectFieldName, "igor@liferay.com"
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	@After
	public void tearDown() throws PortalException {
		ObjectEntryLocalServiceUtil.deleteObjectEntry(_childObjectEntry);
		ObjectEntryLocalServiceUtil.deleteObjectEntry(_parentObjectEntry);
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
								"create" + _parentObjectDefinitionName,
								HashMapBuilder.<String, Object>put(
									_parentObjectDefinitionName,
									StringBundler.concat(
										"{", _objectFieldName, ": \"", value,
										"\", ", _listFieldName, ": {key: \"",
										_listFieldValueKey, "\"}}")
								).build(),
								new GraphQLField(_objectFieldName),
								new GraphQLField(_listFieldName + " {key}"))))),
				"JSONObject/data", "JSONObject/c",
				"JSONObject/create" + _parentObjectDefinitionName,
				"Object/" + _objectFieldName));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"notprivacysafe.graphql.GraphQL", LoggerTestUtil.ERROR)) {

			Assert.assertEquals(
				"Bad Request",
				JSONUtil.getValueAsString(
					_invoke(
						new GraphQLField(
							"mutation",
							new GraphQLField(
								"c",
								new GraphQLField(
									"create" + _parentObjectDefinitionName,
									HashMapBuilder.<String, Object>put(
										_parentObjectDefinitionName,
										StringBundler.concat(
											"{", _objectFieldName, ": \"",
											RandomTestUtil.randomString(), "\"",
											", status: draft }")
									).build(),
									new GraphQLField(_objectFieldName))))),
					"JSONArray/errors", "Object/0", "JSONObject/extensions",
					"Object/code"));
		}
	}

	@Test
	public void testDeleteObjectEntry() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"c",
				new GraphQLField(
					"delete" + _parentObjectDefinitionName,
					HashMapBuilder.<String, Object>put(
						_parentObjectDefinitionPrimaryKeyName,
						_parentObjectEntry.getObjectEntryId()
					).build())));

		JSONObject jsonObject = _invoke(graphQLField);

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"Object/delete" + _parentObjectDefinitionName));

		jsonObject = _invoke(graphQLField);

		Assert.assertFalse(
			JSONUtil.getValueAsBoolean(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"Object/delete" + _parentObjectDefinitionName));
	}

	@Test
	public void testGetListObjectEntry() throws Exception {
		String key = TextFormatter.formatPlural(
			StringUtil.lowerCaseFirstLetter(_parentObjectDefinitionName));

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
	}

	@Test
	public void testGetListObjectEntryFilterByObjectFieldUsingNotEquals()
		throws Exception {

		String key = TextFormatter.formatPlural(
			StringUtil.lowerCaseFirstLetter(_parentObjectDefinitionName));

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
		String key = StringUtil.lowerCaseFirstLetter(
			_parentObjectDefinitionName);

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
									_parentObjectDefinitionPrimaryKeyName,
									_parentObjectEntry.getObjectEntryId()
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
							_parentObjectDefinitionPrimaryKeyName,
							_parentObjectEntry.getObjectEntryId()
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
	public void testGetObjectEntryRelatedParentObjectEntry() throws Exception {
		String key = TextFormatter.formatPlural(
			StringUtil.lowerCaseFirstLetter(_childObjectDefinitionName));

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
								new GraphQLField(
									"items",
									new GraphQLField(_RELATIONSHIP_NAME)))))),
				"JSONObject/data", "JSONObject/c", "JSONObject/" + key,
				"Object/items", "Object/0", "Object/" + _RELATIONSHIP_NAME,
				"Object/" + _objectFieldName));
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
						"create" + _parentObjectDefinitionName,
						HashMapBuilder.<String, Object>put(
							_parentObjectDefinitionName,
							StringBundler.concat(
								"{", _objectFieldName, ": \"", value, "\", ",
								_listFieldName, ": {key: \"",
								_listFieldValueKey, "\"}}")
						).build(),
						new GraphQLField(_objectFieldName),
						new GraphQLField(_listFieldName + " {key}"),
						new GraphQLField(
							_parentObjectDefinitionPrimaryKeyName)))));

		Assert.assertEquals(
			value,
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/c",
				"JSONObject/create" + _parentObjectDefinitionName,
				"Object/" + _objectFieldName));

		value = RandomTestUtil.randomString();

		Long objectEntryId = JSONUtil.getValueAsLong(
			jsonObject, "JSONObject/data", "JSONObject/c",
			"JSONObject/create" + _parentObjectDefinitionName,
			"Object/" + _parentObjectDefinitionPrimaryKeyName);

		Assert.assertEquals(
			value,
			JSONUtil.getValueAsString(
				_invoke(
					new GraphQLField(
						"mutation",
						new GraphQLField(
							"c",
							new GraphQLField(
								"update" + _parentObjectDefinitionName,
								HashMapBuilder.<String, Object>put(
									_parentObjectDefinitionName,
									StringBundler.concat(
										"{", _objectFieldName, ": \"", value,
										"\", ", _listFieldName, ": {key: \"",
										_listFieldValueKey, "\"}}")
								).put(
									_parentObjectDefinitionPrimaryKeyName,
									String.valueOf(objectEntryId)
								).build(),
								new GraphQLField(_objectFieldName),
								new GraphQLField(_listFieldName + " {key}"))))),
				"JSONObject/data", "JSONObject/c",
				"JSONObject/update" + _parentObjectDefinitionName,
				"Object/" + _objectFieldName));
	}

	private void _addListTypeEntry(
			ListTypeDefinition listTypeDefinition, String key)
		throws Exception {

		ListTypeEntryLocalServiceUtil.addListTypeEntry(
			null, TestPropsValues.getUserId(),
			listTypeDefinition.getListTypeDefinitionId(), key,
			LocalizedMapUtil.getLocalizedMap(key));
	}

	private ObjectDefinition _addObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList());

		ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).indexed(
				true
			).indexedAsKeyword(
				true
			).name(
				_objectFieldName
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).build());

		return objectDefinition;
	}

	private JSONObject _invoke(GraphQLField queryGraphQLField)
		throws Exception {

		return HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"query", queryGraphQLField.toString()
			).toString(),
			"graphql", Http.Method.POST);
	}

	private static final String _RELATIONSHIP_NAME = "parent";

	private String _childObjectDefinitionName;
	private ObjectEntry _childObjectEntry;
	private String _listFieldName;
	private String _listFieldValueKey;
	private String _objectFieldName;

	@DeleteAfterTestRun
	private ObjectDefinition _parentObjectDefinition;

	private String _parentObjectDefinitionName;
	private String _parentObjectDefinitionPrimaryKeyName;
	private ObjectEntry _parentObjectEntry;

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