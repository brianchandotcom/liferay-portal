/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.model.listener.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.headless.builder.util.ObjectDefinitionTestUtil;
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
@FeatureFlags({"LPS-167253", "LPS-178642"})
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
			Arrays.asList(_objectField1, _objectField2));
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

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@DeleteAfterTestRun
	private ObjectField _objectField1;

	@DeleteAfterTestRun
	private ObjectField _objectField2;

}