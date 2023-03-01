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
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
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
@RunWith(Arquillian.class)
public class SystemObjectRelatedObjectsTest {

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
		for (ObjectRelationship objectRelationship : _objectRelationships) {
			ObjectRelationshipLocalServiceUtil.
				deleteObjectRelationshipMappingTableValues(
					objectRelationship.getObjectRelationshipId(),
					_objectEntry.getPrimaryKey(), _user.getUserId());

			ObjectRelationshipLocalServiceUtil.deleteObjectRelationship(
				objectRelationship);
		}

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testGetManyToManySystemObjectRelatedObjects() throws Exception {
		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, _userSystemObjectDefinition,
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, _getLocation(objectRelationship.getName()), Http.Method.GET);

		JSONArray jsonArray = jsonObject.getJSONArray(
			objectRelationship.getName());

		_assertEquals(jsonArray, _objectEntry);

		objectRelationship = _addObjectRelationship(
			_userSystemObjectDefinition, _objectDefinition, _user.getUserId(),
			_objectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		jsonObject = HTTPTestUtil.invoke(
			null, _getLocation(objectRelationship.getName()), Http.Method.GET);

		jsonArray = jsonObject.getJSONArray(objectRelationship.getName());

		_assertEquals(jsonArray, _objectEntry);
	}

	@Test
	public void testGetManyToOneSystemObjectRelatedObjects() throws Exception {
		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, _userSystemObjectDefinition,
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, _getLocation(objectRelationship.getName()), Http.Method.GET);

		Assert.assertNull(jsonObject.get(objectRelationship.getName()));
	}

	@Test
	public void testGetNotFoundSystemObjectRelatedObjects() throws Exception {
		String name = StringUtil.randomId();

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, _getLocation(name), Http.Method.GET);

		Assert.assertNull(jsonObject.getJSONArray(name));
	}

	@Test
	public void testGetOneToManySystemObjectRelatedObjects() throws Exception {
		ObjectRelationship objectRelationship = _addObjectRelationship(
			_userSystemObjectDefinition, _objectDefinition, _user.getUserId(),
			_objectEntry.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, _getLocation(objectRelationship.getName()), Http.Method.GET);

		_assertEquals(
			jsonObject.getJSONArray(objectRelationship.getName()),
			_objectEntry);
	}

	@Test
	public void testPostSystemObjectWithObjectRelationshipName()
		throws Exception {

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition, _userSystemObjectDefinition,
			_objectEntry.getPrimaryKey(), _user.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		UserAccount userAccount = new UserAccount() {
			{
				name = RandomTestUtil.randomString();
			}
		};

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userAccount.toString());

		jsonObject.put(
			objectRelationship.getName(), JSONFactoryUtil.createJSONArray());

		JSONObject postJSONObject = HTTPTestUtil.invoke(
			jsonObject.toString(), _getLocation(), Http.Method.POST);

		Assert.assertEquals(
			HttpStatus.BAD_REQUEST.getReasonPhrase(
			).toUpperCase(
			).replace(
				StringPool.SPACE, StringPool.UNDERLINE
			),
			postJSONObject.getString("status"));
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

	private void _assertEquals(JSONArray jsonArray, ObjectEntry objectEntry) {
		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		JSONObject jsonObject = (JSONObject)jsonArray.get(0);

		Assert.assertEquals(
			objectEntry.getObjectEntryId(), jsonObject.getLong("id"));
	}

	private String _getLocation() {
		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_userSystemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		return jaxRsApplicationDescriptor.getRESTContextPath();
	}

	private String _getLocation(String name) {
		return StringBundler.concat(
			_getLocation(), StringPool.SLASH, _user.getUserId(),
			"?nestedFields=", name);
	}

	private static final String _OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE =
		RandomTestUtil.randomString();

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntry _objectEntry;
	private final List<ObjectRelationship> _objectRelationships =
		new ArrayList<>();

	@Inject
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

	private User _user;
	private ObjectDefinition _userSystemObjectDefinition;
	private SystemObjectDefinitionMetadata _userSystemObjectDefinitionMetadata;

}