/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.mcp.server.rest.dto.v1_0.Tool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Petteri Karttunen
 */
public class ToolSetUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToRequiredInputSchemaWithAnObjectBody() throws Exception {
		JSONAssert.assertEquals(
			JSONUtil.put(
				"studentCode", JSONUtil.put("type", "string")
			).put(
				"studentName", JSONUtil.put("type", "string")
			).toString(),
			_getBodyPropertiesJSON(true, _getOpenAPIJSONObject()), true);
	}

	@Test
	public void testToRequiredInputSchemaWithAnObjectBodyAndARequiredField()
		throws Exception {

		JSONAssert.assertEquals(
			JSONUtil.put(
				"studentCode", JSONUtil.put("type", "string")
			).toString(),
			_getBodyPropertiesJSON(true, _getOpenAPIJSONObject("studentCode")),
			true);
	}

	@Test
	public void testToRequiredInputSchemaWithFields() {
		Map<String, Object> requiredInputSchema = _getRequiredInputSchema(
			false, _getOpenAPIJSONObject(), "getStudentsPage");

		Map<String, Object> requiredProperties =
			(Map<String, Object>)requiredInputSchema.get("properties");

		Assert.assertEquals(
			Collections.singleton("fields"), requiredProperties.keySet());
	}

	@Test
	public void testToRequiredInputSchemaWithoutAnObjectBody()
		throws Exception {

		JSONAssert.assertEquals(
			JSONUtil.put(
				"dateCreated", JSONUtil.put("type", "string")
			).put(
				"keywords", JSONUtil.put("type", "array")
			).put(
				"studentCode", JSONUtil.put("type", "string")
			).put(
				"studentName", JSONUtil.put("type", "string")
			).toString(),
			_getBodyPropertiesJSON(false, _getOpenAPIJSONObject()), true);
	}

	private String _getBodyPropertiesJSON(
			boolean objectToolSet, JSONObject openAPIJSONObject)
		throws Exception {

		Map<String, Object> requiredInputSchema = _getRequiredInputSchema(
			objectToolSet, openAPIJSONObject, "postStudent");

		Map<String, Object> properties =
			(Map<String, Object>)requiredInputSchema.get("properties");

		Map<String, Object> body = (Map<String, Object>)properties.get("body");

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(body.get("properties"));
	}

	private JSONObject _getContentJSONObject() {
		return JSONUtil.put(
			"application/json",
			JSONUtil.put(
				"schema",
				JSONUtil.put("$ref", "#/components/schemas/Student")));
	}

	private JSONObject _getOpenAPIJSONObject(String... requiredPropertyNames) {
		JSONObject schemaJSONObject = JSONUtil.put(
			"properties",
			JSONUtil.put(
				"dateCreated", JSONUtil.put("type", "string")
			).put(
				"keywords",
				JSONUtil.put(
					"items", JSONUtil.put("type", "string")
				).put(
					"type", "array"
				)
			).put(
				"studentCode", JSONUtil.put("type", "string")
			).put(
				"studentName", JSONUtil.put("type", "string")
			)
		).put(
			"type", "object"
		);

		if (requiredPropertyNames.length > 0) {
			schemaJSONObject.put(
				"required", JSONUtil.putAll((Object[])requiredPropertyNames));
		}

		return JSONUtil.put(
			"components",
			JSONUtil.put("schemas", JSONUtil.put("Student", schemaJSONObject))
		).put(
			"paths",
			JSONUtil.put(
				"/students",
				JSONUtil.put(
					"get",
					JSONUtil.put(
						"operationId", "getStudentsPage"
					).put(
						"responses", _getResponsesJSONObject()
					)
				).put(
					"post",
					JSONUtil.put(
						"operationId", "postStudent"
					).put(
						"requestBody",
						JSONUtil.put("content", _getContentJSONObject())
					).put(
						"responses", _getResponsesJSONObject()
					)
				))
		);
	}

	private Map<String, Object> _getRequiredInputSchema(
		boolean objectToolSet, JSONObject openAPIJSONObject, String toolName) {

		Tool tool = OpenAPIUtil.getTool(
			true, openAPIJSONObject, null, toolName);

		return ReflectionTestUtil.invoke(
			ToolSetUtil.class, "_toRequiredInputSchema",
			new Class<?>[] {Map.class, boolean.class}, tool.getInputSchema(),
			objectToolSet);
	}

	private JSONObject _getResponsesJSONObject() {
		return JSONUtil.put(
			"200", JSONUtil.put("content", _getContentJSONObject()));
	}

}