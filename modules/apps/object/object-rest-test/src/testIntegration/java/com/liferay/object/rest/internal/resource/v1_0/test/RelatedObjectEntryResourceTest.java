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
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Miguel Barcos
 */
@RunWith(Arquillian.class)
public class RelatedObjectEntryResourceTest {

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

		_userSystemObjectDefinitionMetadata =
			_systemObjectDefinitionMetadataRegistry.
				getSystemObjectDefinitionMetadata("User");

		_userSystemObjectDefinition =
			_objectDefinitionLocalService.fetchSystemObjectDefinition(
				_userSystemObjectDefinitionMetadata.getName());
	}

	@After
	public void tearDown() throws Exception {
		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);
	}

	@Test
	public void testDeleteManyToManySystemObjectNotFoundRelatedObjectEntry()
		throws Exception {

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();
		Long irrelevantUserId = RandomTestUtil.randomLong();

		String name = StringUtil.randomId();

		_objectRelationship = _addObjectRelationship(
			name, _objectDefinition.getObjectDefinitionId(),
			_userSystemObjectDefinition.getObjectDefinitionId(),
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, irrelevantUserId, StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry.getPrimaryKey()),
			Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());
	}

	@Test
	public void testDeleteManyToManySystemObjectRelatedObjectEntry()
		throws Exception {

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		String name = StringUtil.randomId();

		_objectRelationship = _addObjectRelationship(
			name, _objectDefinition.getObjectDefinitionId(),
			_userSystemObjectDefinition.getObjectDefinitionId(),
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry.getPrimaryKey()),
			Http.Method.DELETE);

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, itemsJSONArray.length());
	}

	@Test
	public void testDeleteManyToManySystemObjectRelatedObjectEntryNotFound()
		throws Exception {

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		String name = StringUtil.randomId();

		_objectRelationship = _addObjectRelationship(
			name, _objectDefinition.getObjectDefinitionId(),
			_userSystemObjectDefinition.getObjectDefinitionId(),
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		Long irrelevantPrimaryKey = RandomTestUtil.randomLong();

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				irrelevantPrimaryKey),
			Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		// Inactive object definition

		_objectDefinition.setActive(false);

		_objectDefinitionLocalService.updateObjectDefinition(_objectDefinition);

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry.getPrimaryKey()),
			Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));
	}

	@Test
	public void testDeleteOneToManySystemObjectNotFoundRelatedObjectEntry()
		throws Exception {

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		Long irrelevantUserId = RandomTestUtil.randomLong();

		String name = StringUtil.randomId();

		_objectRelationship = _addObjectRelationship(
			name, _userSystemObjectDefinition.getObjectDefinitionId(),
			_objectDefinition.getObjectDefinitionId(), _user.getUserId(),
			_objectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, irrelevantUserId, StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry.getPrimaryKey()),
			Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		// Inactive object definition

		_objectDefinition.setActive(false);

		_objectDefinitionLocalService.updateObjectDefinition(_objectDefinition);

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry.getPrimaryKey()),
			Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));
	}

	@Test
	public void testDeleteOneToManySystemObjectRelatedObjectEntry()
		throws Exception {

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		String name = StringUtil.randomId();

		_objectRelationship = _addObjectRelationship(
			name, _userSystemObjectDefinition.getObjectDefinitionId(),
			_objectDefinition.getObjectDefinitionId(), _user.getUserId(),
			_objectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry.getPrimaryKey()),
			Http.Method.DELETE);

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, itemsJSONArray.length());
	}

	@Test
	public void testDeleteOneToManySystemObjectRelatedObjectEntryNotFound()
		throws Exception {

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		String name = StringUtil.randomId();

		_objectRelationship = _addObjectRelationship(
			name, _userSystemObjectDefinition.getObjectDefinitionId(),
			_objectDefinition.getObjectDefinitionId(), _user.getUserId(),
			_objectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Long irrelevantPrimaryKey = RandomTestUtil.randomLong();

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				irrelevantPrimaryKey),
			Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		// Inactive object definition

		_objectRelationship = _addObjectRelationship(
			StringUtil.randomId(), _objectDefinition.getObjectDefinitionId(),
			_userSystemObjectDefinition.getObjectDefinitionId(),
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_objectDefinition.setActive(false);

		_objectDefinitionLocalService.updateObjectDefinition(_objectDefinition);

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));
	}

	@Test
	public void testGetNotFoundSystemObjectRelatedObjectEntries()
		throws Exception {

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();
		Long irrelevantUserId = RandomTestUtil.randomLong();

		String name = StringUtil.randomId();

		_objectRelationship = _addObjectRelationship(
			name, _objectDefinition.getObjectDefinitionId(),
			_userSystemObjectDefinition.getObjectDefinitionId(),
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, irrelevantUserId, StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));
	}

	@Test
	public void testGetSystemObjectRelatedObjectEntries() throws Exception {
		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		String name = StringUtil.randomId();

		_objectRelationship = _addObjectRelationship(
			name, _objectDefinition.getObjectDefinitionId(),
			_userSystemObjectDefinition.getObjectDefinitionId(),
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			_OBJECT_FIELD_VALUE, itemJSONObject.getString(_OBJECT_FIELD_NAME));
	}

	@Test
	public void testGetSystemObjectRelatedObjectEntriesWithPagination()
		throws Exception {

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		_objectRelationship = _addObjectRelationship(
			StringUtil.randomId(), _objectDefinition.getObjectDefinitionId(),
			_userSystemObjectDefinition.getObjectDefinitionId(),
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition, _OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE);

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			_user.getUserId(), _objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), _user.getUserId(),
			ServiceContextTestUtil.getServiceContext());

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName(), "?page=1&pageSize=1"),
			Http.Method.GET);

		Assert.assertEquals(2, jsonObject.getLong("lastPage"));
		Assert.assertEquals(1, jsonObject.getLong("page"));
		Assert.assertEquals(1, jsonObject.getLong("pageSize"));
		Assert.assertEquals(2, jsonObject.getLong("totalCount"));
	}

	@Test
	public void testPutSystemObjectRelatedObjectEntry() throws Exception {
		_objectRelationship = _addObjectRelationship(
			StringUtil.randomId(), _objectDefinition.getObjectDefinitionId(),
			_userSystemObjectDefinition.getObjectDefinitionId(),
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testPutSystemObjectRelatedObjectEntry(_objectRelationship);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);

		_objectRelationship = _addObjectRelationship(
			StringUtil.randomId(),
			_userSystemObjectDefinition.getObjectDefinitionId(),
			_objectDefinition.getObjectDefinitionId(), _user.getUserId(),
			_objectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testPutSystemObjectRelatedObjectEntry(_objectRelationship);
	}

	@Test
	public void testPutSystemObjectRelatedObjectNotFound() throws Exception {
		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		String name = StringUtil.randomId();

		_objectRelationship = _addObjectRelationship(
			name, _objectDefinition.getObjectDefinitionId(),
			_userSystemObjectDefinition.getObjectDefinitionId(),
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		Long irrelevantPrimaryKey = RandomTestUtil.randomLong();

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				irrelevantPrimaryKey),
			Http.Method.PUT);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		// Inactive object definition

		String objectFieldValue = RandomTestUtil.randomString();

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition, _OBJECT_FIELD_NAME, objectFieldValue);

		_objectDefinition.setActive(false);

		_objectDefinitionLocalService.updateObjectDefinition(_objectDefinition);

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				objectEntry.getPrimaryKey()),
			Http.Method.PUT);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));
	}

	private ObjectRelationship _addObjectRelationship(
			String name, long objectDefinitionId1, long objectDefinitionId2,
			long primaryKey1, long primaryKey2, String type)
		throws Exception {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				_user.getUserId(), objectDefinitionId1, objectDefinitionId2, 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				name, type);

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			_user.getUserId(), objectRelationship.getObjectRelationshipId(),
			primaryKey1, primaryKey2,
			ServiceContextTestUtil.getServiceContext());

		return objectRelationship;
	}

	private void _testPutSystemObjectRelatedObjectEntry(
			ObjectRelationship objectRelationship)
		throws Exception {

		String objectFieldValue = RandomTestUtil.randomString();

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition, _OBJECT_FIELD_NAME, objectFieldValue);

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				objectRelationship.getName(), StringPool.SLASH,
				objectEntry.getPrimaryKey()),
			Http.Method.PUT);

		Assert.assertEquals(
			objectFieldValue, jsonObject.getString(_OBJECT_FIELD_NAME));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(2, itemsJSONArray.length());
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
	private ObjectRelationship _objectRelationship;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

	private User _user;
	private ObjectDefinition _userSystemObjectDefinition;
	private SystemObjectDefinitionMetadata _userSystemObjectDefinitionMetadata;

}