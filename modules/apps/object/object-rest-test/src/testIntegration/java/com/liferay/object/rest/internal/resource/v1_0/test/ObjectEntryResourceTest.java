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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.internal.resource.v1_0.test.util.HTTPTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectEntryTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

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
public class ObjectEntryResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME, false)));

		_objectEntry = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition, _OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE);
	}

	@Test
	public void testGetNestedFieldDetailsInOneToManyRelationship()
		throws Exception {

		String relatedObjectFieldName = "x" + RandomTestUtil.randomString();

		ObjectDefinition relatedObjectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null,
						RandomTestUtil.randomString(), relatedObjectFieldName,
						false)));

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition, relatedObjectDefinition,
				TestPropsValues.getUserId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		String relatedObjectFieldValue = "x" + RandomTestUtil.randomString();

		ObjectEntry relatedObjectEntry = ObjectEntryTestUtil.addObjectEntry(
			relatedObjectDefinition, relatedObjectFieldName,
			relatedObjectFieldValue);

		ObjectRelationshipLocalServiceUtil.
			addObjectRelationshipMappingTableValues(
				TestPropsValues.getUserId(),
				objectRelationship.getObjectRelationshipId(),
				_objectEntry.getPrimaryKey(),
				relatedObjectEntry.getPrimaryKey(),
				ServiceContextTestUtil.getServiceContext());

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				relatedObjectDefinition.getRESTContextPath(),
				"?nestedFields=r_", objectRelationship.getName(), "_",
				_objectDefinition.getPKObjectFieldName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			relatedObjectFieldValue,
			itemJSONObject.getString(relatedObjectFieldName));

		JSONObject relatedObjectJSONObject = itemJSONObject.getJSONObject(
			StringBundler.concat(
				"r_", objectRelationship.getName(), "_",
				StringUtil.replaceLast(
					_objectDefinition.getPKObjectFieldName(), "Id", "")));

		Assert.assertEquals(
			_OBJECT_FIELD_VALUE,
			relatedObjectJSONObject.getString(_OBJECT_FIELD_NAME));

		ObjectRelationshipLocalServiceUtil.
			deleteObjectRelationshipMappingTableValues(
				objectRelationship.getObjectRelationshipId(),
				_objectEntry.getPrimaryKey(),
				relatedObjectEntry.getPrimaryKey());

		ObjectRelationshipLocalServiceUtil.deleteObjectRelationship(
			objectRelationship);

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			relatedObjectDefinition);
	}

	private static final String _OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE =
		RandomTestUtil.randomString();

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	private ObjectEntry _objectEntry;

}