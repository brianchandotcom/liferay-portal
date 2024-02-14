/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.model.listener.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.headless.builder.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;

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
				"r_apiApplicationToAPISchemas_c_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).toString(),
			"headless-builder/schemas", Http.Method.POST);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
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

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", "APPLICATION_STATUS"
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
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
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
				apiSchemaJSONObject.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"A field API property cannot have empty Object Field ERC value.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
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
				"r_apiApplicationToAPISchemas_c_apiApplicationId",
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
				"r_apiApplicationToAPISchemas_c_apiApplicationId",
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
				"r_apiApplicationToAPISchemas_c_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).toString(),
			"headless-builder/schemas", Http.Method.POST);

		JSONObject apiSchemaJSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"mainObjectDefinitionERC",
				_objectDefinition.getExternalReferenceCode()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_c_apiApplicationId",
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
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
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
				"r_apiPropertyToAPIProperties_c_apiPropertyId",
				apiApplicationJSONObject.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"An API property must be related to an API property.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiPropertyToAPIProperties_c_apiPropertyId",
				apiPropertyJSONObject1.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"A field API property must be related to a record API property.",
			jsonObject.get("title"));

		JSONObject apiPropertyJSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
				apiSchemaJSONObject2.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", "APPLICATION_STATUS"
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).put(
				"type", "record"
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"A record API property cannot an object field ERC value.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectRelationshipNames", RandomTestUtil.randomString()
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
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
				"r_apiPropertyToAPIProperties_c_apiPropertyId",
				apiPropertyJSONObject1.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
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

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"objectFieldERC", _objectField1.getExternalReferenceCode()
			).put(
				"r_apiPropertyToAPIProperties_c_apiPropertyId",
				apiPropertyJSONObject2.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"A related API property must belong to the same API schema.",
			jsonObject.get("title"));

		String name = RandomTestUtil.randomString();

		JSONObject objectAPIPropertyJSONObject =
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"description", RandomTestUtil.randomString()
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"r_apiSchemaToAPIProperties_c_apiSchemaId",
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
				"r_apiPropertyToAPIProperties_c_apiPropertyId",
				objectAPIPropertyJSONObject.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
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
				"r_apiPropertyToAPIProperties_c_apiPropertyId",
				objectAPIPropertyJSONObject.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
				apiSchemaJSONObject1.get("id")
			).toString(),
			"headless-builder/properties", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"API property name must be unique.", jsonObject.get("title"));

		apiPropertyJSONObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
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
				"r_apiPropertyToAPIProperties_c_apiPropertyId",
				apiPropertyJSONObject1.getLong("id")
			).put(
				"r_apiSchemaToAPIProperties_c_apiSchemaId",
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
				"r_apiApplicationToAPISchemas_c_apiApplicationId",
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
				"r_apiApplicationToAPISchemas_c_apiApplicationId",
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

	@DeleteAfterTestRun
	private ObjectField _objectField1;

	@DeleteAfterTestRun
	private ObjectField _objectField2;

}