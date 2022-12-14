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
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.internal.resource.v1_0.test.util.HTTPTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectEntryTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.http.HttpStatus;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class ObjectEntryRelatedObjectsResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-162966", "true"
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-162966", "false"
			).build());
	}

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

		_userSystemObjectDefinitionMetadata =
			_systemObjectDefinitionMetadataRegistry.
				getSystemObjectDefinitionMetadata("User");

		_userSystemObjectDefinition =
			_objectDefinitionLocalService.fetchSystemObjectDefinition(
				_userSystemObjectDefinitionMetadata.getName());
	}

	@After
	public void tearDown() throws Exception {
		for (ObjectRelationship objectRelationship : _objectRelationships) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				objectRelationship);
		}
	}

	@Test
	public void testDeleteCustomObjectDefinitionWithSystemObjectDefinition()
		throws Exception {

		_testDeleteOneToManyAndManyToManyCustomObjectDefinitionWithSystemObjectDefinition(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testDeleteOneToManyAndManyToManyCustomObjectDefinitionWithSystemObjectDefinitionNotFound(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testDeleteOneToManyAndManyToManyCustomObjectDefinitionWithSystemObjectDefinition(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testDeleteOneToManyAndManyToManyCustomObjectDefinitionWithSystemObjectDefinitionNotFound(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
	}

	@Test
	public void testGetRelatedCustomObjectsWhenRelationExists()
		throws Exception {

		_relatedObjectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null,
						RandomTestUtil.randomString(), _OBJECT_FIELD_NAME,
						false)));

		ObjectEntry relatedObjectEntry = ObjectEntryTestUtil.addObjectEntry(
			_relatedObjectDefinition, _OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE);

		// Many to many relationships

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, _relatedObjectDefinition,
			_objectEntry.getPrimaryKey(), relatedObjectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(relatedObjectEntry, jsonObject.getJSONArray("items"));

		objectRelationship = _addObjectRelationship(
			_relatedObjectDefinition, _objectDefinition,
			relatedObjectEntry.getPrimaryKey(), _objectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(relatedObjectEntry, jsonObject.getJSONArray("items"));

		// One to many relationship

		objectRelationship = _addObjectRelationship(
			_objectDefinition, _relatedObjectDefinition,
			_objectEntry.getPrimaryKey(), relatedObjectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(relatedObjectEntry, jsonObject.getJSONArray("items"));
	}

	@Test
	public void testGetRelatedObjectsWhenRelationDoesNotExist()
		throws Exception {

		Assert.assertEquals(
			HttpStatus.NOT_FOUND.value(),
			_invokeGetHttpCode(_getLocation(StringUtil.randomId())));
	}

	@Test
	public void testGetRelatedSystemObjectsWhenRelationExists()
		throws Exception {

		_userSystemObjectDefinitionMetadata =
			_systemObjectDefinitionMetadataRegistry.
				getSystemObjectDefinitionMetadata("User");

		ObjectDefinition relatedObjectDefinition =
			_objectDefinitionLocalService.fetchSystemObjectDefinition(
				_userSystemObjectDefinitionMetadata.getName());

		// Many to many relationships

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, relatedObjectDefinition,
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(_user, jsonObject.getJSONArray("items"));

		objectRelationship = _addObjectRelationship(
			relatedObjectDefinition, _objectDefinition, _user.getUserId(),
			_objectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(_user, jsonObject.getJSONArray("items"));

		// One to many relationship

		objectRelationship = _addObjectRelationship(
			_objectDefinition, relatedObjectDefinition,
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(_user, jsonObject.getJSONArray("items"));
	}

	@Test
	public void testPutObjectEntryRelatedObjectEntry() throws Exception {
		_relatedObjectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null,
						RandomTestUtil.randomString(), _OBJECT_FIELD_NAME,
						false)));

		ObjectEntry relatedObjectEntry = ObjectEntryTestUtil.addObjectEntry(
			_relatedObjectDefinition, _OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE);

		// Many to many relationship

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, _relatedObjectDefinition,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, jsonArray.length());

		_assertEquals(
			relatedObjectEntry,
			JSONFactoryUtil.createJSONObject(
				_invoke(
					Http.Method.PUT,
					_getLocation(
						objectRelationship.getName(),
						relatedObjectEntry.getPrimaryKey()))));

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		_assertEquals(relatedObjectEntry, jsonArray);

		// One to many relationship

		objectRelationship = _addObjectRelationship(
			_objectDefinition, _relatedObjectDefinition,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, jsonArray.length());

		_assertEquals(
			relatedObjectEntry,
			JSONFactoryUtil.createJSONObject(
				_invoke(
					Http.Method.PUT,
					_getLocation(
						objectRelationship.getName(),
						relatedObjectEntry.getPrimaryKey()))));

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		_assertEquals(relatedObjectEntry, jsonArray);
	}

	@Test
	public void testPutObjectEntryRelatedSystemObject() throws Exception {
		_userSystemObjectDefinitionMetadata =
			_systemObjectDefinitionMetadataRegistry.
				getSystemObjectDefinitionMetadata("User");

		ObjectDefinition relatedObjectDefinition =
			_objectDefinitionLocalService.fetchSystemObjectDefinition(
				_userSystemObjectDefinitionMetadata.getName());

		// Many to many relationship

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, relatedObjectDefinition,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, jsonArray.length());

		_assertEquals(
			_user,
			JSONFactoryUtil.createJSONObject(
				_invoke(
					Http.Method.PUT,
					_getLocation(
						objectRelationship.getName(), _user.getUserId()))));

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		_assertEquals(_user, jsonArray);

		// One to many relationship

		objectRelationship = _addObjectRelationship(
			_objectDefinition, relatedObjectDefinition,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, jsonArray.length());

		_assertEquals(
			_user,
			JSONFactoryUtil.createJSONObject(
				_invoke(
					Http.Method.PUT,
					_getLocation(
						objectRelationship.getName(), _user.getUserId()))));

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		_assertEquals(_user, jsonArray);
	}

	private ObjectRelationship _addObjectRelationship(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, long primaryKey1,
			long primaryKey2, String type)
		throws Exception {

		ObjectRelationship objectRelationship = _addObjectRelationship(
			objectDefinition1, objectDefinition2, type);

		ObjectRelationshipTestUtil.relateObjectEntries(
			primaryKey1, primaryKey2, objectRelationship,
			TestPropsValues.getUserId());

		return objectRelationship;
	}

	private ObjectRelationship _addObjectRelationship(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, String type)
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				objectDefinition1, objectDefinition2,
				TestPropsValues.getUserId(), type);

		_objectRelationships.add(objectRelationship);

		return objectRelationship;
	}

	private <T> void _assertEquals(
		BaseModel<T> baseModel, JSONArray jsonArray) {

		Assert.assertEquals(1, jsonArray.length());

		_assertEquals(baseModel, jsonArray.getJSONObject(0));
	}

	private <T> void _assertEquals(
		BaseModel<T> baseModel, JSONObject jsonObject) {

		Assert.assertEquals(
			baseModel.getPrimaryKeyObj(), jsonObject.getLong("id"));
	}

	private Http.Options _createOptions(
		Http.Method httpMethod, String location) {

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.addHeader(
			"Authorization",
			"Basic " + Base64.encode("test@liferay.com:test".getBytes()));
		options.setLocation(location);
		options.setMethod(httpMethod);

		return options;
	}

	private String _getLocation(String name) {
		return StringBundler.concat(
			"http://localhost:8080/o/", _objectDefinition.getRESTContextPath(),
			StringPool.SLASH, _objectEntry.getObjectEntryId(), StringPool.SLASH,
			name);
	}

	private String _getLocation(String name, long primaryKey) {
		return StringBundler.concat(
			_getLocation(name), StringPool.SLASH, primaryKey);
	}

	private String _invoke(Http.Method httpMethod, String location)
		throws Exception {

		Http.Options options = _createOptions(httpMethod, location);

		return HttpUtil.URLtoString(options);
	}

	private int _invokeGetHttpCode(String location) throws Exception {
		Http.Options options = _createOptions(Http.Method.GET, location);

		HttpUtil.URLtoString(options);

		Http.Response response = options.getResponse();

		return response.getResponseCode();
	}

	private void
			_testDeleteOneToManyAndManyToManyCustomObjectDefinitionWithSystemObjectDefinition(
				String type)
		throws Exception {

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, _userSystemObjectDefinition,
			_objectEntry.getPrimaryKey(), _user.getUserId(), type);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), StringPool.SLASH,
				_objectEntry.getPrimaryKey(), StringPool.SLASH,
				objectRelationship.getName(), StringPool.SLASH,
				_user.getUserId()),
			Http.Method.DELETE);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, itemsJSONArray.length());
	}

	private void
			_testDeleteOneToManyAndManyToManyCustomObjectDefinitionWithSystemObjectDefinitionNotFound(
				String type)
		throws Exception {

		Long irrelevantPrimaryKey = RandomTestUtil.randomLong();

		Long irrelevantUserId = RandomTestUtil.randomLong();

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, _userSystemObjectDefinition,
			_objectEntry.getPrimaryKey(), _user.getUserId(), type);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), StringPool.SLASH,
				irrelevantPrimaryKey, StringPool.SLASH,
				objectRelationship.getName(), StringPool.SLASH,
				_user.getUserId()),
			Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), StringPool.SLASH,
				_objectEntry.getPrimaryKey(), StringPool.SLASH,
				objectRelationship.getName(), StringPool.SLASH,
				irrelevantUserId),
			Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());
	}

	private static final String _OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE =
		RandomTestUtil.randomString();

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntry _objectEntry;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private final List<ObjectRelationship> _objectRelationships =
		new ArrayList<>();

	@DeleteAfterTestRun
	private ObjectDefinition _relatedObjectDefinition;

	@Inject
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

	private User _user;
	private ObjectDefinition _userSystemObjectDefinition;
	private SystemObjectDefinitionMetadata _userSystemObjectDefinitionMetadata;

}