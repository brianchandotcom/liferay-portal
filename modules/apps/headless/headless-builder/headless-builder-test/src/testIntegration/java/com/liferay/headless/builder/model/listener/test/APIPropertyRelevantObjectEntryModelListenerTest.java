/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.model.listener.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.headless.builder.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.test.util.ObjectFieldTestUtil;
import com.liferay.object.rest.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Sergio Jiménez del Coso
 */
@FeatureFlags("LPS-178642")
public class APIPropertyRelevantObjectEntryModelListenerTest
	extends BaseTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_objectField1 = ObjectFieldUtil.createObjectField(
			"Text", "String", true, true, null,
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			"x" + RandomTestUtil.randomString(), false);

		_objectField1.setExternalReferenceCode(RandomTestUtil.randomString());

		_objectField2 = ObjectFieldUtil.createObjectField(
			"Text", "String", true, true, null,
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			"x" + RandomTestUtil.randomString(), false);

		_objectField2.setExternalReferenceCode(RandomTestUtil.randomString());

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Arrays.asList(_objectField1, _objectField2),
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	@Test
	public void test() throws Exception {
		JSONObject apiApplicationJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		JSONObject apiSchemaJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"mainObjectDefinitionERC",
				_objectDefinition.getExternalReferenceCode()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_l_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).toString(),
			"headless-builder/schemas", Http.Method.POST);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"A field API property cannot have an empty object field external " +
				"reference code.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", "APPLICATION_STATUS"
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"An API property must be related to an API schema.",
			jsonObject.get("title"));

		ObjectDefinition userSystemObjectDefinition =
			_objectDefinitionLocalService.fetchSystemObjectDefinition("User");

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition, userSystemObjectDefinition,
				TestPropsValues.getUserId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectField userSystemObjectField =
			ObjectFieldTestUtil.addCustomObjectField(
				TestPropsValues.getUserId(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, userSystemObjectDefinition,
				"x" + RandomTestUtil.randomString());

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC",
				userSystemObjectField.getExternalReferenceCode()
			).put(
				"objectRelationshipNames", objectRelationship.getName()
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"An API property must belong to a modifiable object definition.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", "APPLICATION_STATUS"
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"An API property must be related to an existing object field.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));
	}

	@Test
	public void testAddAPIProperty() throws Exception {
		JSONObject apiApplicationJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		String externalReferenceCode1 = RandomTestUtil.randomString();

		JSONObject apiSchemaJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"apiSchemaToAPIProperties",
				JSONUtil.put(
					JSONUtil.put(
						"description", RandomTestUtil.randomString()
					).put(
						"externalReferenceCode", externalReferenceCode1
					).put(
						"name", RandomTestUtil.randomString()
					).put(
						"objectFieldERC",
						_objectField1.getExternalReferenceCode()
					))
			).put(
				"mainObjectDefinitionERC",
				_objectDefinition.getExternalReferenceCode()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_l_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).toString(),
			"headless-builder/schemas", Http.Method.POST);

		String externalReferenceCode2 = RandomTestUtil.randomString();

		apiSchemaJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"apiSchemaToAPIProperties",
				JSONUtil.putAll(
					JSONUtil.put(
						"description", RandomTestUtil.randomString()
					).put(
						"externalReferenceCode", externalReferenceCode1
					).put(
						"name", RandomTestUtil.randomString()
					).put(
						"objectFieldERC",
						_objectField1.getExternalReferenceCode()
					),
					JSONUtil.put(
						"description", RandomTestUtil.randomString()
					).put(
						"externalReferenceCode", externalReferenceCode2
					).put(
						"name", RandomTestUtil.randomString()
					).put(
						"objectFieldERC",
						_objectField2.getExternalReferenceCode()
					))
			).put(
				"mainObjectDefinitionERC",
				_objectDefinition.getExternalReferenceCode()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_l_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).toString(),
			"headless-builder/schemas/by-external-reference-code/" +
				apiSchemaJSONObject.getString("externalReferenceCode"),
			Http.Method.PUT);

		JSONArray apiPropertiesJSONArray = apiSchemaJSONObject.getJSONArray(
			"apiSchemaToAPIProperties");

		Assert.assertEquals(2, apiPropertiesJSONArray.length());

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put("externalReferenceCode", externalReferenceCode1),
				JSONUtil.put("externalReferenceCode", externalReferenceCode2)
			).toString(),
			apiPropertiesJSONArray.toString(), JSONCompareMode.LENIENT);
	}

	@FeatureFlags("LPD-10964")
	@Test
	public void testAddRecordAPIProperty() throws Exception {
		JSONObject apiApplicationJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		String valueAPIPropertyExternalReferenceCode =
			RandomTestUtil.randomString();

		JSONObject apiSchemaJSONObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"apiSchemaToAPIProperties",
				JSONUtil.put(
					JSONUtil.put(
						"description", RandomTestUtil.randomString()
					).put(
						"externalReferenceCode",
						valueAPIPropertyExternalReferenceCode
					).put(
						"name", RandomTestUtil.randomString()
					).put(
						"objectFieldERC",
						_objectField1.getExternalReferenceCode()
					))
			).put(
				"mainObjectDefinitionERC",
				_objectDefinition.getExternalReferenceCode()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_l_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).toString(),
			"headless-builder/schemas", Http.Method.POST);

		JSONObject apiPropertyJSONObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiPropertyToAPIProperties_l_apiPropertyId",
				apiPropertyJSONObject1.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"A field API property must be related to a record API property.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", "APPLICATION_STATUS"
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).put(
				"type", "record"
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"A record API property cannot have an object field external " +
				"reference code.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectRelationshipNames", RandomTestUtil.randomString()
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).put(
				"type", "record"
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"A record API property cannot have an object relationship names " +
				"value.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiPropertyToAPIProperties_l_apiPropertyId",
				apiPropertyJSONObject1.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).put(
				"type", "record"
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"A record API property must be related to another record API " +
				"property.",
			jsonObject.get("title"));

		JSONObject apiSchemaJSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"mainObjectDefinitionERC",
				_objectDefinition.getExternalReferenceCode()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_l_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).toString(),
			"headless-builder/schemas", Http.Method.POST);

		JSONObject apiPropertyJSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject2.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiPropertyToAPIProperties_l_apiPropertyId",
				apiPropertyJSONObject2.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"A related API property must belong to the same API schema.",
			jsonObject.get("title"));

		String name = RandomTestUtil.randomString();

		JSONObject recordAPIPropertyJSONObject =
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"description", RandomTestUtil.randomString()
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"r_apiSchemaToAPIProperties_l_apiSchemaId",
					apiSchemaJSONObject1.get("id")
				).put(
					"type", "record"
				).toString(),
				"headless-builder/properties", Http.Method.POST);

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", name
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiPropertyToAPIProperties_l_apiPropertyId",
				recordAPIPropertyJSONObject.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", name
			).put(
				"objectFieldERC", _objectField2.getExternalReferenceCode()
			).put(
				"r_apiPropertyToAPIProperties_l_apiPropertyId",
				recordAPIPropertyJSONObject.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"API property name must be unique.", jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiPropertyToAPIProperties_l_apiPropertyId",
				apiApplicationJSONObject.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"The value is invalid for object field " +
				"\"r_apiPropertyToAPIProperties_l_apiPropertyId\"",
			jsonObject.get("title"));

		apiPropertyJSONObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).put(
				"type", "record"
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiPropertyToAPIProperties_l_apiPropertyId",
				apiPropertyJSONObject1.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_l_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));
	}

	@Test
	public void testRemoveAPIProperty() throws Exception {
		JSONObject apiApplicationJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		String externalReferenceCode1 = RandomTestUtil.randomString();
		String externalReferenceCode2 = RandomTestUtil.randomString();

		JSONObject apiSchemaJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"apiSchemaToAPIProperties",
				JSONUtil.putAll(
					JSONUtil.put(
						"description", RandomTestUtil.randomString()
					).put(
						"externalReferenceCode", externalReferenceCode1
					).put(
						"name", RandomTestUtil.randomString()
					).put(
						"objectFieldERC",
						_objectField1.getExternalReferenceCode()
					),
					JSONUtil.put(
						"description", RandomTestUtil.randomString()
					).put(
						"externalReferenceCode", externalReferenceCode2
					).put(
						"name", RandomTestUtil.randomString()
					).put(
						"objectFieldERC",
						_objectField2.getExternalReferenceCode()
					))
			).put(
				"mainObjectDefinitionERC",
				_objectDefinition.getExternalReferenceCode()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_l_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).toString(),
			"headless-builder/schemas", Http.Method.POST);

		apiSchemaJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"apiSchemaToAPIProperties",
				JSONUtil.put(
					JSONUtil.put(
						"description", RandomTestUtil.randomString()
					).put(
						"externalReferenceCode", externalReferenceCode1
					).put(
						"name", RandomTestUtil.randomString()
					).put(
						"objectFieldERC",
						_objectField1.getExternalReferenceCode()
					))
			).put(
				"mainObjectDefinitionERC",
				_objectDefinition.getExternalReferenceCode()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_l_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).toString(),
			"headless-builder/schemas/by-external-reference-code/" +
				apiSchemaJSONObject.getString("externalReferenceCode"),
			Http.Method.PUT);

		JSONArray apiPropertiesJSONArray = apiSchemaJSONObject.getJSONArray(
			"apiSchemaToAPIProperties");

		Assert.assertEquals(1, apiPropertiesJSONArray.length());

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put("externalReferenceCode", externalReferenceCode1)
			).toString(),
			apiPropertiesJSONArray.toString(), JSONCompareMode.LENIENT);
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@DeleteAfterTestRun
	private ObjectField _objectField1;

	@DeleteAfterTestRun
	private ObjectField _objectField2;

}