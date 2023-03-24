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
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
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
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
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
	public static void setUpClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-153117", "true"
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-153117", "false"
			).build());
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_1,
					false)));

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);

		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_2,
					false)));

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);
		_objectEntry3 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

		_user1 = TestPropsValues.getUser();
		_user2 = UserTestUtil.addUser(TestPropsValues.getGroupId());

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
	public void testDeleteCustomObjectDefinition1WithCustomObjectDefinition2()
		throws Exception {

		Long irrelevantCurrentObjectId = RandomTestUtil.randomLong();

		_objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2(
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2(
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2NotFound(
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				irrelevantCurrentObjectId, StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				irrelevantCurrentObjectId),
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2NotFound(
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				irrelevantCurrentObjectId, StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				irrelevantCurrentObjectId),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2(
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2NotFound(
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				irrelevantCurrentObjectId, StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				irrelevantCurrentObjectId),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2NotFound(
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				irrelevantCurrentObjectId, StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				irrelevantCurrentObjectId),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));
	}

	@Test
	public void testDeleteCustomObjectDefinitionWithSystemObjectDefinition()
		throws Exception {

		_testDeleteCustomObjectDefinitionWithSystemObjectDefinition(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		_testDeleteCustomObjectDefinitionWithSystemObjectDefinition(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_testDeleteCustomObjectDefinitionWithSystemObjectDefinitionNotFound(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		_testDeleteCustomObjectDefinitionWithSystemObjectDefinitionNotFound(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
	}

	@Test
	public void testGetRelatedCustomObjectEntriesWhenRelationExists()
		throws Exception {

		// Many to many relationships

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(_objectEntry2, jsonObject.getJSONArray("items"));

		objectRelationship = _addObjectRelationship(
			_objectDefinition2, _objectDefinition1,
			_objectEntry2.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(_objectEntry2, jsonObject.getJSONArray("items"));

		// One to many relationship

		objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(_objectEntry2, jsonObject.getJSONArray("items"));
	}

	@Test
	public void testGetRelatedCustomObjectEntriesWithPagination()
		throws Exception {

		// Many to many relationships

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectRelationshipTestUtil.relateObjectEntries(
			_objectEntry1.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			objectRelationship, TestPropsValues.getUserId());

		_assertPagination(_objectEntry2, objectRelationship);

		objectRelationship = _addObjectRelationship(
			_objectDefinition2, _objectDefinition1,
			_objectEntry2.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectRelationshipTestUtil.relateObjectEntries(
			_objectEntry3.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			objectRelationship, TestPropsValues.getUserId());

		_assertPagination(_objectEntry2, objectRelationship);

		// One to many relationship

		objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectRelationshipTestUtil.relateObjectEntries(
			_objectEntry1.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			objectRelationship, TestPropsValues.getUserId());

		_assertPagination(_objectEntry2, objectRelationship);
	}

	@Test
	public void testGetRelatedObjectEntriesWhenRelationDoesNotExist()
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
			_objectDefinition1, relatedObjectDefinition,
			_objectEntry1.getPrimaryKey(), _user1.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(_user1, jsonObject.getJSONArray("items"));

		objectRelationship = _addObjectRelationship(
			relatedObjectDefinition, _objectDefinition1, _user1.getUserId(),
			_objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(_user1, jsonObject.getJSONArray("items"));

		// One to many relationship

		objectRelationship = _addObjectRelationship(
			_objectDefinition1, relatedObjectDefinition,
			_objectEntry1.getPrimaryKey(), _user1.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		_assertEquals(_user1, jsonObject.getJSONArray("items"));
	}

	@Test
	public void testGetRelatedSystemObjectsWithPagination() throws Exception {

		// Many to many relationships

		_userSystemObjectDefinitionMetadata =
			_systemObjectDefinitionMetadataRegistry.
				getSystemObjectDefinitionMetadata("User");

		ObjectDefinition relatedObjectDefinition =
			_objectDefinitionLocalService.fetchSystemObjectDefinition(
				_userSystemObjectDefinitionMetadata.getName());

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition1, relatedObjectDefinition,
			_objectEntry1.getPrimaryKey(), _user1.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectRelationshipTestUtil.relateObjectEntries(
			_objectEntry1.getPrimaryKey(), _user2.getUserId(),
			objectRelationship, TestPropsValues.getUserId());

		_assertPagination(_user1, objectRelationship);

		objectRelationship = _addObjectRelationship(
			relatedObjectDefinition, _objectDefinition1, _user1.getUserId(),
			_objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectRelationshipTestUtil.relateObjectEntries(
			_user2.getUserId(), _objectEntry1.getPrimaryKey(),
			objectRelationship, TestPropsValues.getUserId());

		_assertPagination(_user1, objectRelationship);

		// One to many relationship

		objectRelationship = _addObjectRelationship(
			_objectDefinition1, relatedObjectDefinition,
			_objectEntry1.getPrimaryKey(), _user1.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectRelationshipTestUtil.relateObjectEntries(
			_objectEntry1.getPrimaryKey(), _user2.getUserId(),
			objectRelationship, TestPropsValues.getUserId());

		_assertPagination(_user1, objectRelationship);
	}

	@Test
	public void testPostCustomObjectEntryWithInvalidNestedSystemObjectEntries()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.WARN)) {

			// Many to many

			_testPostCustomObjectEntryWithInvalidNestedSystemObjectEntries(
				_addObjectRelationship(
					_userSystemObjectDefinition, _objectDefinition1,
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY),
				false);

			// Many to one

			_testPostCustomObjectEntryWithInvalidNestedSystemObjectEntries(
				_addObjectRelationship(
					_userSystemObjectDefinition, _objectDefinition1,
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY),
				true);

			// One to many

			_testPostCustomObjectEntryWithInvalidNestedSystemObjectEntries(
				_addObjectRelationship(
					_objectDefinition1, _userSystemObjectDefinition,
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY),
				false);
		}
	}

	@Test
	public void testPostCustomObjectEntryWithNestedSystemObjectEntry()
		throws Exception {

		// Many to many

		_testPostCustomObjectEntryWithNestedSystemObjectEntry(
			_addObjectRelationship(
				_userSystemObjectDefinition, _objectDefinition1,
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY),
			false);

		// Many to one

		_testPostCustomObjectEntryWithNestedSystemObjectEntry(
			_addObjectRelationship(
				_userSystemObjectDefinition, _objectDefinition1,
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY),
			true);

		// One to many

		_testPostCustomObjectEntryWithNestedSystemObjectEntry(
			_addObjectRelationship(
				_objectDefinition1, _userSystemObjectDefinition,
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY),
			false);
	}

	@Test
	public void testPutCustomObjectEntryWithNestedSystemObjectEntry()
		throws Exception {

		// Many to many

		_testPutCustomObjectEntryWithNestedSystemObjectEntry(
			_addObjectRelationship(
				_userSystemObjectDefinition, _objectDefinition1,
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY),
			false);

		// Many to one

		_testPutCustomObjectEntryWithNestedSystemObjectEntry(
			_addObjectRelationship(
				_userSystemObjectDefinition, _objectDefinition1,
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY),
			true);

		// One to many

		_testPutCustomObjectEntryWithNestedSystemObjectEntry(
			_addObjectRelationship(
				_objectDefinition1, _userSystemObjectDefinition,
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY),
			false);
	}

	@Test
	public void testPutObjectEntryRelatedObjectEntry() throws Exception {

		// Many to many relationship

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, jsonArray.length());

		_assertEquals(
			_objectEntry2,
			JSONFactoryUtil.createJSONObject(
				_invoke(
					Http.Method.PUT,
					_getLocation(
						objectRelationship.getName(),
						_objectEntry2.getPrimaryKey()))));

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		_assertEquals(_objectEntry2, jsonArray);

		// One to many relationship

		objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, jsonArray.length());

		_assertEquals(
			_objectEntry2,
			JSONFactoryUtil.createJSONObject(
				_invoke(
					Http.Method.PUT,
					_getLocation(
						objectRelationship.getName(),
						_objectEntry2.getPrimaryKey()))));

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		_assertEquals(_objectEntry2, jsonArray);
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
			_objectDefinition1, relatedObjectDefinition,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, jsonArray.length());

		_assertEquals(
			_user1,
			JSONFactoryUtil.createJSONObject(
				_invoke(
					Http.Method.PUT,
					_getLocation(
						objectRelationship.getName(), _user1.getUserId()))));

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		_assertEquals(_user1, jsonArray);

		// One to many relationship

		objectRelationship = _addObjectRelationship(
			_objectDefinition1, relatedObjectDefinition,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, jsonArray.length());

		_assertEquals(
			_user1,
			JSONFactoryUtil.createJSONObject(
				_invoke(
					Http.Method.PUT,
					_getLocation(
						objectRelationship.getName(), _user1.getUserId()))));

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		jsonArray = jsonObject.getJSONArray("items");

		_assertEquals(_user1, jsonArray);
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

	private void _assertPagination(
			BaseModel<?> baseModel, ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET,
				_getLocation(objectRelationship.getName()) +
					"?page=1&pageSize=1"));

		_assertEquals(baseModel, jsonObject.getJSONArray("items"));

		Assert.assertEquals(2, jsonObject.getLong("lastPage"));
		Assert.assertEquals(1, jsonObject.getLong("page"));
		Assert.assertEquals(1, jsonObject.getLong("pageSize"));
		Assert.assertEquals(2, jsonObject.getLong("totalCount"));

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET,
				_getLocation(objectRelationship.getName()) +
					"?page=0&pageSize=0"));

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(2, itemsJSONArray.length());
	}

	private void _assertSystemObjectEntryValue(
			String objectEntryId, ObjectRelationship objectRelationship,
			String systemObjectEntryName, String systemObjectEntryValue,
			boolean nestedFields)
		throws Exception {

		JSONObject userSystemObjectEntryJSONObject;

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET,
				_getLocation(
					objectEntryId, objectRelationship.getName(),
					nestedFields)));

		if (nestedFields) {
			userSystemObjectEntryJSONObject = jsonObject.getJSONObject(
				objectRelationship.getName());
		}
		else {
			JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

			Assert.assertEquals(1, itemsJSONArray.length());

			userSystemObjectEntryJSONObject = itemsJSONArray.getJSONObject(0);
		}

		Assert.assertEquals(
			userSystemObjectEntryJSONObject.get(systemObjectEntryName),
			systemObjectEntryValue);
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

	private JSONArray _createSystemObjectEntryJSONArray(UserAccount userAccount)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userAccount.toString());

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		return jsonArray.put(jsonObject);
	}

	private String _getLocation(String name) {
		return StringBundler.concat(
			"http://localhost:8080/o/", _objectDefinition1.getRESTContextPath(),
			StringPool.SLASH, _objectEntry1.getObjectEntryId(),
			StringPool.SLASH, name);
	}

	private String _getLocation(String name, long primaryKey) {
		return StringBundler.concat(
			_getLocation(name), StringPool.SLASH, primaryKey);
	}

	private String _getLocation(
		String objectEntryId, String objectRelationshipName,
		boolean nestedFields) {

		if (nestedFields) {
			return StringBundler.concat(
				"http://localhost:8080/o/",
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=", objectRelationshipName);
		}

		return StringBundler.concat(
			"http://localhost:8080/o/", _objectDefinition1.getRESTContextPath(),
			StringPool.SLASH, objectEntryId, StringPool.SLASH,
			objectRelationshipName);
	}

	private String _getUserExternalReferenceCode(
			String objectEntryId, ObjectRelationship objectRelationship,
			boolean nestedFields)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET,
				_getLocation(
					objectEntryId, objectRelationship.getName(),
					nestedFields)));

		if (nestedFields) {
			return jsonObject.getString(
				StringBundler.concat(
					"r_", objectRelationship.getName(), "_",
					StringUtil.replaceLast(
						_userSystemObjectDefinition.getPKObjectFieldName(),
						"Id", "ERC")));
		}

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		jsonObject = itemsJSONArray.getJSONObject(0);

		return jsonObject.getString("externalReferenceCode");
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

	private UserAccount _randomUserAccount() throws Exception {
		return new UserAccount() {
			{
				additionalName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				alternateName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				birthDate = RandomTestUtil.nextDate();
				currentPassword = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dashboardURL = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				familyName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				givenName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				honorificPrefix = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				honorificSuffix = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				image = StringUtil.toLowerCase(RandomTestUtil.randomString());
				jobTitle = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				lastLoginDate = RandomTestUtil.nextDate();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				password = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				profileURL = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	private void _testDeleteCustomObjectDefinition1WithCustomObjectDefinition2(
			String deleteEndpoint, String getEndpoint)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, getEndpoint, Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		HTTPTestUtil.invoke(null, deleteEndpoint, Http.Method.DELETE);

		jsonObject = HTTPTestUtil.invoke(null, getEndpoint, Http.Method.GET);

		itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, itemsJSONArray.length());
	}

	private void
			_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2NotFound(
				String deleteEndpoint1, String deleteEndpoint2,
				String getEndpoint)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, deleteEndpoint1, Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = HTTPTestUtil.invoke(
			null, deleteEndpoint2, Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = HTTPTestUtil.invoke(null, getEndpoint, Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());
	}

	private void _testDeleteCustomObjectDefinitionWithSystemObjectDefinition(
			String type)
		throws Exception {

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition1, _userSystemObjectDefinition,
			_objectEntry1.getPrimaryKey(), _user1.getUserId(), type);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				objectRelationship.getName(), StringPool.SLASH,
				_user1.getUserId()),
			Http.Method.DELETE);

		jsonObject = JSONFactoryUtil.createJSONObject(
			_invoke(
				Http.Method.GET, _getLocation(objectRelationship.getName())));

		itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, itemsJSONArray.length());
	}

	private void
			_testDeleteCustomObjectDefinitionWithSystemObjectDefinitionNotFound(
				String type)
		throws Exception {

		Long irrelevantPrimaryKey = RandomTestUtil.randomLong();

		Long irrelevantUserId = RandomTestUtil.randomLong();

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition1, _userSystemObjectDefinition,
			_objectEntry1.getPrimaryKey(), _user1.getUserId(), type);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				irrelevantPrimaryKey, StringPool.SLASH,
				objectRelationship.getName(), StringPool.SLASH,
				_user1.getUserId()),
			Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
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

	private void _testPostCustomObjectEntryWithInvalidNestedSystemObjectEntries(
			ObjectRelationship objectRelationship, boolean manyToOne)
		throws Exception {

		JSONObject systemObjectEntryJSONObject;

		UserAccount userAccount = _randomUserAccount();

		if (manyToOne) {
			systemObjectEntryJSONObject = JSONUtil.put(
				objectRelationship.getName(),
				_createSystemObjectEntryJSONArray(userAccount));
		}
		else {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				userAccount.toString());

			systemObjectEntryJSONObject = JSONUtil.put(
				objectRelationship.getName(), jsonObject);
		}

		JSONObject jsonObject = HTTPTestUtil.invoke(
			systemObjectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
	}

	private void _testPostCustomObjectEntryWithNestedSystemObjectEntry(
			ObjectRelationship objectRelationship, boolean manyToOne)
		throws Exception {

		JSONObject systemObjectEntryJSONObject;

		String userEmailAddress =
			StringUtil.toLowerCase(RandomTestUtil.randomString()) +
				"@liferay.com";

		UserAccount userAccount = _randomUserAccount();

		userAccount.setEmailAddress(userEmailAddress);

		if (manyToOne) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				userAccount.toString());

			systemObjectEntryJSONObject = JSONUtil.put(
				objectRelationship.getName(), jsonObject);
		}
		else {
			systemObjectEntryJSONObject = JSONUtil.put(
				objectRelationship.getName(),
				_createSystemObjectEntryJSONArray(userAccount));
		}

		JSONObject jsonObject = HTTPTestUtil.invoke(
			systemObjectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		String objectEntryId = jsonObject.getString("id");

		_assertSystemObjectEntryValue(
			objectEntryId, objectRelationship, "emailAddress", userEmailAddress,
			manyToOne);
	}

	private void _testPutCustomObjectEntryWithNestedSystemObjectEntry(
			ObjectRelationship objectRelationship, boolean manyToOne)
		throws Exception {

		JSONObject systemObjectEntryJSONObject;

		String newEmailAddress =
			StringUtil.toLowerCase(RandomTestUtil.randomString()) +
				"@liferay.com";

		UserAccount userAccount = _randomUserAccount();

		if (manyToOne) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				userAccount.toString());

			systemObjectEntryJSONObject = JSONUtil.put(
				objectRelationship.getName(), jsonObject);
		}
		else {
			systemObjectEntryJSONObject = JSONUtil.put(
				objectRelationship.getName(),
				_createSystemObjectEntryJSONArray(userAccount));
		}

		JSONObject jsonObject = HTTPTestUtil.invoke(
			systemObjectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		String objectEntryId = jsonObject.getString("id");

		UserAccount updateUserAccount = _randomUserAccount();

		updateUserAccount.setExternalReferenceCode(
			_getUserExternalReferenceCode(
				objectEntryId, objectRelationship, manyToOne));
		updateUserAccount.setEmailAddress(newEmailAddress);

		if (manyToOne) {
			jsonObject = JSONFactoryUtil.createJSONObject(
				updateUserAccount.toString());

			systemObjectEntryJSONObject = JSONUtil.put(
				objectRelationship.getName(), jsonObject);
		}
		else {
			systemObjectEntryJSONObject = JSONUtil.put(
				objectRelationship.getName(),
				_createSystemObjectEntryJSONArray(updateUserAccount));
		}

		HTTPTestUtil.invoke(
			systemObjectEntryJSONObject.toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				objectEntryId),
			Http.Method.PUT);

		_assertSystemObjectEntryValue(
			objectEntryId, objectRelationship, "emailAddress", newEmailAddress,
			manyToOne);
	}

	private static final String _OBJECT_FIELD_NAME_1 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_2 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE_1 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE_2 =
		RandomTestUtil.randomString();

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@DeleteAfterTestRun
	private ObjectEntry _objectEntry1;

	@DeleteAfterTestRun
	private ObjectEntry _objectEntry2;

	@DeleteAfterTestRun
	private ObjectEntry _objectEntry3;

	private ObjectRelationship _objectRelationship;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private final List<ObjectRelationship> _objectRelationships =
		new ArrayList<>();

	@Inject
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

	private User _user1;
	private User _user2;
	private ObjectDefinition _userSystemObjectDefinition;
	private SystemObjectDefinitionMetadata _userSystemObjectDefinitionMetadata;

}