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
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
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
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-153324", "true"
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-153324", "false"
			).build());
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition = _publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME, false)));

		_objectEntry = _createObjectEntry(_OBJECT_FIELD_VALUE);

		_userSystemObjectDefinitionMetadata =
			_systemObjectDefinitionMetadataTracker.
				getSystemObjectDefinitionMetadata("User");

		ObjectDefinition userSystemObjectDefinition =
			_getSystemObjectDefinition(_userSystemObjectDefinitionMetadata);

		_objectRelationship =
			ObjectRelationshipLocalServiceUtil.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(),
				userSystemObjectDefinition.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_user = TestPropsValues.getUser();

		ObjectRelationshipLocalServiceUtil.
			addObjectRelationshipMappingTableValues(
				TestPropsValues.getUserId(),
				_objectRelationship.getObjectRelationshipId(),
				_objectEntry.getPrimaryKey(), _user.getUserId(),
				ServiceContextTestUtil.getServiceContext());
	}

	@After
	public void tearDown() throws Exception {
		ObjectRelationshipLocalServiceUtil.
			deleteObjectRelationshipMappingTableValues(
				_objectRelationship.getObjectRelationshipId(),
				_objectEntry.getPrimaryKey(), _user.getUserId());
		ObjectRelationshipLocalServiceUtil.deleteObjectRelationship(
			_objectRelationship);
	}

	@Test
	public void testGetSystemObjectRelatedObjects() throws Exception {
		JSONObject jsonObject = _invoke(
			StringBundler.concat(
				_userSystemObjectDefinitionMetadata.getRESTContextPath(),
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
	public void testPutSystemObjectRelatedObject() throws Exception {
		String objectFieldValue = RandomTestUtil.randomString();

		ObjectEntry objectEntry = _createObjectEntry(objectFieldValue);

		JSONObject jsonObject = _invoke(
			StringBundler.concat(
				_userSystemObjectDefinitionMetadata.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				objectEntry.getPrimaryKey()),
			Http.Method.PUT);

		Assert.assertEquals(
			objectFieldValue, jsonObject.getString(_OBJECT_FIELD_NAME));

		jsonObject = _invoke(
			StringBundler.concat(
				_userSystemObjectDefinitionMetadata.getRESTContextPath(),
				StringPool.SLASH, _user.getUserId(), StringPool.SLASH,
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(2, itemsJSONArray.length());
	}

	private ObjectEntry _createObjectEntry(String objectFieldValue)
		throws Exception {

		return ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME, objectFieldValue
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private ObjectDefinition _getSystemObjectDefinition(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		List<ObjectDefinition> systemObjectDefinitions =
			_objectDefinitionLocalService.getSystemObjectDefinitions();

		for (ObjectDefinition systemObjectDefinition :
				systemObjectDefinitions) {

			if (Objects.equals(
					systemObjectDefinition.getName(),
					systemObjectDefinitionMetadata.getName())) {

				return systemObjectDefinition;
			}
		}

		return null;
	}

	private JSONObject _invoke(String endpoint, Http.Method httpMethod)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.addHeader(
			"Authorization",
			"Basic " + Base64.encode("test@liferay.com:test".getBytes()));
		options.setLocation("http://localhost:8080/o/" + endpoint);

		if (httpMethod == Http.Method.PUT) {
			options.setPut(true);
		}

		return JSONFactoryUtil.createJSONObject(HttpUtil.URLtoString(options));
	}

	private ObjectDefinition _publishObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
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
	private SystemObjectDefinitionMetadataTracker
		_systemObjectDefinitionMetadataTracker;

	private User _user;
	private SystemObjectDefinitionMetadata _userSystemObjectDefinitionMetadata;

}