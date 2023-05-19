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

package com.liferay.object.rest.internal.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.internal.resource.v1_0.test.util.HTTPTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectEntryTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectFieldTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.UserAccountTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.http.HttpStatus;

/**
 * @author Carlos Correa
 */
@FeatureFlags("LPS-153117")
@RunWith(Arquillian.class)
public class SystemObjectRelatedObjectEntriesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME, false)));

		_objectEntry = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition, _OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE);

		_user = TestPropsValues.getUser();

		_userSystemObjectDefinitionManager =
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager("User");

		_userSystemObjectDefinition =
			_objectDefinitionLocalService.fetchSystemObjectDefinition(
				_userSystemObjectDefinitionManager.getName());

		_userSystemObjectField = ObjectFieldTestUtil.addCustomObjectField(
			TestPropsValues.getUserId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, _userSystemObjectDefinition,
			_SYSTEM_OBJECT_FIELD_NAME);

		_userAccountJSONObject = UserAccountTestUtil.addUserAccountJSONObject(
			_userSystemObjectDefinitionManager,
			HashMapBuilder.<String, Serializable>put(
				_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE
			).build());
	}

	@After
	public void tearDown() throws Exception {
		for (ObjectRelationship objectRelationship : _objectRelationships) {
			ObjectRelationshipLocalServiceUtil.
				deleteObjectRelationshipMappingTableValues(
					objectRelationship.getObjectRelationshipId(),
					_objectEntry.getPrimaryKey(),
					_userAccountJSONObject.getLong("id"));

			ObjectRelationshipLocalServiceUtil.deleteObjectRelationship(
				objectRelationship);
		}

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());
	}

	@FeatureFlags("LPS-165819")
	@Test
	public void testGetManyToManySystemObjectRelatedObjectEntries()
		throws Exception {

		// Many to many relationship

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, _userSystemObjectDefinition,
			_objectEntry.getPrimaryKey(), _userAccountJSONObject.getLong("id"),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			null, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE}
			},
			Type.MANY_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			1, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE}
			},
			Type.MANY_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			2, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE},
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE}
			},
			Type.MANY_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			5, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE},
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE},
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE}
			},
			Type.MANY_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			6, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE},
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE},
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE}
			},
			Type.MANY_TO_MANY);

		// Many to many relationship (other side)

		objectRelationship = _addObjectRelationship(
			_userSystemObjectDefinition, _objectDefinition,
			_userAccountJSONObject.getLong("id"), _objectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			null, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE}
			},
			Type.MANY_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			1, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE}
			},
			Type.MANY_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			2, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE},
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE}
			},
			Type.MANY_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			5, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE},
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE},
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE}
			},
			Type.MANY_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			6, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE},
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE},
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE}
			},
			Type.MANY_TO_MANY);
	}

	@Test
	public void testGetManyToOneSystemObjectRelatedObjectEntries()
		throws Exception {

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, _userSystemObjectDefinition,
			_objectEntry.getPrimaryKey(), _userAccountJSONObject.getLong("id"),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, _getLocation(objectRelationship.getName()), Http.Method.GET);

		Assert.assertNull(jsonObject.get(objectRelationship.getName()));
	}

	@Test
	public void testGetNotFoundSystemObjectRelatedObjectEntries()
		throws Exception {

		String name = StringUtil.randomId();

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, _getLocation(name), Http.Method.GET);

		Assert.assertNull(jsonObject.getJSONArray(name));
	}

	@Test
	public void testGetOneToManySystemObjectRelatedObjectEntries()
		throws Exception {

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_userSystemObjectDefinition, _objectDefinition,
			_userAccountJSONObject.getLong("id"), _objectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			null, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE}
			},
			Type.ONE_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			1, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE}
			},
			Type.ONE_TO_MANY);

		_testGetSystemObjectRelatedObjectEntries(
			2, objectRelationship.getName(),
			new String[][] {
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE},
				{_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE},
				{_SYSTEM_OBJECT_FIELD_NAME, _SYSTEM_OBJECT_FIELD_VALUE}
			},
			Type.ONE_TO_MANY);
	}

	@Test
	public void testPostSystemObjectEntryWithInvalidNestedCustomObjectEntries()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.WARN)) {

			// Many to many relationship

			ObjectRelationship objectRelationship =
				ObjectRelationshipTestUtil.addObjectRelationship(
					_userSystemObjectDefinition, _objectDefinition,
					_user.getUserId(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

			_objectRelationships.add(objectRelationship);

			_testPostSystemObjectEntryWithInvalidNestedCustomObjectEntries(
				objectRelationship, false);

			// Many to one relationship

			objectRelationship =
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectDefinition, _userSystemObjectDefinition,
					_user.getUserId(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

			_objectRelationships.add(objectRelationship);

			_testPostSystemObjectEntryWithInvalidNestedCustomObjectEntries(
				objectRelationship, true);

			// One to many relationship

			objectRelationship =
				ObjectRelationshipTestUtil.addObjectRelationship(
					_userSystemObjectDefinition, _objectDefinition,
					_user.getUserId(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

			_testPostSystemObjectEntryWithInvalidNestedCustomObjectEntries(
				objectRelationship, false);
		}
	}

	@Test
	public void testPostSystemObjectEntryWithNestedCustomObjectEntries()
		throws Exception {

		// Many to many relationship

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_userSystemObjectDefinition, _objectDefinition,
				_user.getUserId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_objectRelationships.add(objectRelationship);

		_testPostSystemObjectEntryWithNestedCustomObjectEntries(
			objectRelationship);

		// One to many relationship

		objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_userSystemObjectDefinition, _objectDefinition, _user.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationships.add(objectRelationship);

		_testPostSystemObjectEntryWithNestedCustomObjectEntries(
			objectRelationship);
	}

	@Test
	public void testPostSystemObjectEntryWithNestedCustomObjectEntriesInManyToOneRelationship()
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition, _userSystemObjectDefinition,
				_user.getUserId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationships.add(objectRelationship);

		JSONObject jsonObject = UserAccountTestUtil.addUserAccountJSONObject(
			_userSystemObjectDefinitionManager,
			HashMapBuilder.<String, Serializable>put(
				objectRelationship.getName(),
				JSONFactoryUtil.createJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME, _NEW_OBJECT_FIELD_VALUE_1
					).put(
						"externalReferenceCode", _ERC_VALUE_1
					).toString())
			).build());

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_getLocation(), StringPool.SLASH, jsonObject.getString("id")),
			Http.Method.GET);

		Assert.assertEquals(
			jsonObject.getString(
				StringBundler.concat(
					"r_", objectRelationship.getName(), "_",
					StringUtil.replaceLast(
						_objectDefinition.getPKObjectFieldName(), "Id",
						"ERC"))),
			_ERC_VALUE_1);

		_assertObjectEntryField(
			_getObjectEntryByExternalReferenceCodeJSONObject(_ERC_VALUE_1),
			_OBJECT_FIELD_NAME, _NEW_OBJECT_FIELD_VALUE_1);
	}

	@Test
	public void testPostSystemObjectWithObjectRelationshipName()
		throws Exception {

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, _userSystemObjectDefinition,
			_objectEntry.getPrimaryKey(), _userAccountJSONObject.getLong("id"),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = UserAccountTestUtil.addUserAccountJSONObject(
			_userSystemObjectDefinitionManager,
			HashMapBuilder.<String, Serializable>put(
				objectRelationship.getName(), JSONFactoryUtil.createJSONArray()
			).build());

		Assert.assertEquals(
			HttpStatus.BAD_REQUEST.getReasonPhrase(
			).toUpperCase(
			).replace(
				StringPool.SPACE, StringPool.UNDERLINE
			),
			jsonObject.getString("status"));
	}

	@Test
	public void testPutSystemObjectEntryWithNestedCustomObjectEntries()
		throws Exception {

		// Many to many relationship

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_userSystemObjectDefinition, _objectDefinition,
				_user.getUserId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_objectRelationships.add(objectRelationship);

		_testPutSystemObjectEntryWithNestedCustomObjectEntries(
			objectRelationship);

		// One to many relationship

		objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_userSystemObjectDefinition, _objectDefinition, _user.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationships.add(objectRelationship);

		_testPutSystemObjectEntryWithNestedCustomObjectEntries(
			objectRelationship);
	}

	@Test
	public void testPutSystemObjectEntryWithNestedCustomObjectEntriesInManyToOneRelationship()
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition, _userSystemObjectDefinition,
				_user.getUserId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationships.add(objectRelationship);

		JSONObject jsonObject = UserAccountTestUtil.addUserAccountJSONObject(
			_userSystemObjectDefinitionManager,
			HashMapBuilder.<String, Serializable>put(
				objectRelationship.getName(),
				JSONFactoryUtil.createJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME, RandomTestUtil.randomString()
					).put(
						"externalReferenceCode", _ERC_VALUE_1
					).toString())
			).build());

		UserAccountTestUtil.updateUserAccountJSONObject(
			_userSystemObjectDefinitionManager, jsonObject,
			HashMapBuilder.<String, Serializable>put(
				objectRelationship.getName(),
				JSONFactoryUtil.createJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME, _NEW_OBJECT_FIELD_VALUE_1
					).put(
						"externalReferenceCode", _ERC_VALUE_1
					).toString())
			).build());

		_assertObjectEntryField(
			_getObjectEntryByExternalReferenceCodeJSONObject(_ERC_VALUE_1),
			_OBJECT_FIELD_NAME, _NEW_OBJECT_FIELD_VALUE_1);
	}

	private ObjectRelationship _addObjectRelationship(
			ObjectDefinition objectDefinition,
			ObjectDefinition relatedObjectDefinition, long primaryKey1,
			long primaryKey2, String type)
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				objectDefinition, relatedObjectDefinition, _user.getUserId(),
				type);

		ObjectRelationshipLocalServiceUtil.
			addObjectRelationshipMappingTableValues(
				_user.getUserId(), objectRelationship.getObjectRelationshipId(),
				primaryKey1, primaryKey2,
				ServiceContextTestUtil.getServiceContext());

		_objectRelationships.add(objectRelationship);

		return objectRelationship;
	}

	private void _assertNestedFieldsInRelationships(
		int currentDepth, int depth, JSONObject jsonObject,
		String nestedFieldName, String[][] objectFieldNamesAndObjectFieldValues,
		Type type) {

		if (objectFieldNamesAndObjectFieldValues[currentDepth][0] == null) {
			Assert.assertNull(jsonObject);
		}
		else {
			Assert.assertEquals(
				objectFieldNamesAndObjectFieldValues[currentDepth][1],
				jsonObject.getString(
					objectFieldNamesAndObjectFieldValues[currentDepth][0]));
		}

		if ((currentDepth == depth) ||
			(currentDepth ==
				PropsValues.OBJECT_NESTED_FIELDS_MAX_QUERY_DEPTH)) {

			Assert.assertEquals(
				Arrays.toString(objectFieldNamesAndObjectFieldValues),
				currentDepth + 1, objectFieldNamesAndObjectFieldValues.length);
			Assert.assertNull(jsonObject.get(nestedFieldName));

			return;
		}

		_assertNestedFieldsInRelationships(
			currentDepth + 1, depth,
			_getRelatedJSONObject(jsonObject, nestedFieldName, type),
			nestedFieldName, objectFieldNamesAndObjectFieldValues,
			_getReverseType(type));
	}

	private void _assertObjectEntryField(
		JSONObject objectEntryJSONObject, String objectFieldName,
		String objectFieldValue) {

		int objectEntryId = objectEntryJSONObject.getInt("id");

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			objectEntryId);

		Assert.assertEquals(
			MapUtil.getString(objectEntry.getValues(), objectFieldName),
			objectFieldValue);
	}

	private JSONArray _createObjectEntriesJSONArray(
			String[] externalReferenceCodeValues, String objectFieldName,
			String[] objectFieldValues)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < objectFieldValues.length; i++) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					objectFieldName, objectFieldValues[i]
				).put(
					"externalReferenceCode", externalReferenceCodeValues[i]
				).toString());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private String _getLocation() {
		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionManager.getJaxRsApplicationDescriptor();

		return jaxRsApplicationDescriptor.getRESTContextPath();
	}

	private String _getLocation(String name) {
		return StringBundler.concat(
			_getLocation(), StringPool.SLASH,
			_userAccountJSONObject.getLong("id"), "?nestedFields=", name);
	}

	private String _getLocation(String userId, String objectRelationshipName) {
		return StringBundler.concat(
			_getLocation(), StringPool.SLASH, userId, "?nestedFields=",
			objectRelationshipName);
	}

	private JSONObject _getObjectEntryByExternalReferenceCodeJSONObject(
			String externalReferenceCode)
		throws Exception {

		return HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(),
				"/by-external-reference-code/", externalReferenceCode),
			Http.Method.GET);
	}

	private JSONObject _getRelatedJSONObject(
		JSONObject jsonObject, String nestedFieldName, Type type) {

		if (type == Type.MANY_TO_ONE) {
			JSONObject nestedJSONObject = jsonObject.getJSONObject(
				nestedFieldName);

			Assert.assertNotNull(
				"Missing field " + nestedFieldName, nestedJSONObject);

			return jsonObject.getJSONObject(nestedFieldName);
		}

		JSONArray jsonArray = jsonObject.getJSONArray(nestedFieldName);

		Assert.assertNotNull("Missing field " + nestedFieldName, jsonArray);

		Assert.assertEquals(1, jsonArray.length());

		return jsonArray.getJSONObject(0);
	}

	private Type _getReverseType(Type type) {
		if (type == Type.MANY_TO_ONE) {
			return Type.ONE_TO_MANY;
		}
		else if (type == Type.ONE_TO_MANY) {
			return Type.MANY_TO_ONE;
		}

		return Type.MANY_TO_MANY;
	}

	private void _testGetSystemObjectRelatedObjectEntries(
			Integer nestedFieldDepth, String nestedFieldName,
			String[][] objectFieldNamesAndObjectFieldValues, Type type)
		throws Exception {

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionManager.getJaxRsApplicationDescriptor();

		String endpoint = StringBundler.concat(
			jaxRsApplicationDescriptor.getRESTContextPath(), StringPool.SLASH,
			_userAccountJSONObject.getLong("id"), "?nestedFields=",
			nestedFieldName);

		if (nestedFieldDepth != null) {
			endpoint += "&nestedFieldsDepth=" + nestedFieldDepth;
		}

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, endpoint, Http.Method.GET);

		_assertNestedFieldsInRelationships(
			0, GetterUtil.getInteger(nestedFieldDepth, 1), jsonObject,
			nestedFieldName, objectFieldNamesAndObjectFieldValues, type);
	}

	private void _testPostSystemObjectEntryWithInvalidNestedCustomObjectEntries(
			ObjectRelationship objectRelationship, boolean manyToOne)
		throws Exception {

		JSONObject jsonObject = null;

		if (manyToOne) {
			jsonObject = UserAccountTestUtil.addUserAccountJSONObject(
				_userSystemObjectDefinitionManager,
				HashMapBuilder.<String, Serializable>put(
					objectRelationship.getName(),
					_createObjectEntriesJSONArray(
						new String[] {_ERC_VALUE_1, _ERC_VALUE_2},
						_OBJECT_FIELD_NAME,
						new String[] {
							_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
						})
				).build());
		}
		else {
			jsonObject = UserAccountTestUtil.addUserAccountJSONObject(
				_userSystemObjectDefinitionManager,
				HashMapBuilder.<String, Serializable>put(
					objectRelationship.getName(),
					JSONFactoryUtil.createJSONObject(
						JSONUtil.put(
							"externalReferenceCode", _ERC_VALUE_1
						).toString())
				).build());
		}

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
	}

	private void _testPostSystemObjectEntryWithNestedCustomObjectEntries(
			ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject jsonObject = UserAccountTestUtil.addUserAccountJSONObject(
			_userSystemObjectDefinitionManager,
			HashMapBuilder.<String, Serializable>put(
				objectRelationship.getName(),
				_createObjectEntriesJSONArray(
					new String[] {_ERC_VALUE_1, _ERC_VALUE_2},
					_OBJECT_FIELD_NAME,
					new String[] {
						_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
					})
			).build());

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			objectRelationship.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0), _OBJECT_FIELD_NAME,
			_NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1), _OBJECT_FIELD_NAME,
			_NEW_OBJECT_FIELD_VALUE_2);

		jsonObject = HTTPTestUtil.invoke(
			null,
			_getLocation(
				jsonObject.getString("id"), objectRelationship.getName()),
			Http.Method.GET);

		nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			objectRelationship.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0), _OBJECT_FIELD_NAME,
			_NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1), _OBJECT_FIELD_NAME,
			_NEW_OBJECT_FIELD_VALUE_2);
	}

	private void _testPutSystemObjectEntryWithNestedCustomObjectEntries(
			ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject jsonObject = UserAccountTestUtil.addUserAccountJSONObject(
			_userSystemObjectDefinitionManager,
			HashMapBuilder.<String, Serializable>put(
				objectRelationship.getName(),
				_createObjectEntriesJSONArray(
					new String[] {_ERC_VALUE_1, _ERC_VALUE_2},
					_OBJECT_FIELD_NAME,
					new String[] {
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString()
					})
			).build());

		jsonObject = UserAccountTestUtil.updateUserAccountJSONObject(
			_userSystemObjectDefinitionManager, jsonObject,
			HashMapBuilder.<String, Serializable>put(
				objectRelationship.getName(),
				_createObjectEntriesJSONArray(
					new String[] {_ERC_VALUE_1, _ERC_VALUE_2},
					_OBJECT_FIELD_NAME,
					new String[] {
						_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
					})
			).build());

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			objectRelationship.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0), _OBJECT_FIELD_NAME,
			_NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1), _OBJECT_FIELD_NAME,
			_NEW_OBJECT_FIELD_VALUE_2);

		jsonObject = HTTPTestUtil.invoke(
			null,
			_getLocation(
				jsonObject.getString("id"), objectRelationship.getName()),
			Http.Method.GET);

		nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			objectRelationship.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0), _OBJECT_FIELD_NAME,
			_NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1), _OBJECT_FIELD_NAME,
			_NEW_OBJECT_FIELD_VALUE_2);
	}

	private static final String _ERC_VALUE_1 = RandomTestUtil.randomString();

	private static final String _ERC_VALUE_2 = RandomTestUtil.randomString();

	private static final String _NEW_OBJECT_FIELD_VALUE_1 =
		"x" + RandomTestUtil.randomString();

	private static final String _NEW_OBJECT_FIELD_VALUE_2 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE =
		RandomTestUtil.randomString();

	private static final String _SYSTEM_OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	private static final String _SYSTEM_OBJECT_FIELD_VALUE =
		RandomTestUtil.randomString();

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntry _objectEntry;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private final List<ObjectRelationship> _objectRelationships =
		new ArrayList<>();

	@Inject
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

	private User _user;
	private JSONObject _userAccountJSONObject;
	private ObjectDefinition _userSystemObjectDefinition;
	private SystemObjectDefinitionManager _userSystemObjectDefinitionManager;

	@DeleteAfterTestRun
	private ObjectField _userSystemObjectField;

	private enum Type {

		MANY_TO_MANY, MANY_TO_ONE, ONE_TO_MANY

	}

}