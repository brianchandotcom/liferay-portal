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

package com.liferay.headless.builder.application.resource.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Luis Miguel Barcos
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@FeatureFlags({"LPS-167253", "LPS-184413", "LPS-186757"})
public class HeadlessBuilderResourceTest extends BaseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_objectDefinitionJSONObject = _createObjectDefinition();

		_addAPIApplication(
			_API_BASE_URL_1, _API_APPLICATION_ERC_1,
			_objectDefinitionJSONObject.getString("externalReferenceCode"),
			_API_APPLICATION_PATH_1);

		_addAPIApplication(
			_API_BASE_URL_2, _API_APPLICATION_ERC_2,
			_objectDefinitionJSONObject.getString("externalReferenceCode"),
			_API_APPLICATION_PATH_2);
	}

	@Test
	public void test() throws Exception {
		String endpointPath1 = _API_BASE_URL_1 + _API_APPLICATION_PATH_1;

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeHttpCode(null, endpointPath1, Http.Method.GET));

		String endpointPath2 = _API_BASE_URL_2 + _API_APPLICATION_PATH_2;

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeHttpCode(null, endpointPath2, Http.Method.GET));

		HTTPTestUtil.invoke(
			JSONUtil.put(
				"applicationStatus", "published"
			).toString(),
			"headless-builder/applications/by-external-reference-code/" +
				_API_APPLICATION_ERC_1,
			Http.Method.PATCH);
		HTTPTestUtil.invoke(
			JSONUtil.put(
				"applicationStatus", "published"
			).toString(),
			"headless-builder/applications/by-external-reference-code/" +
				_API_APPLICATION_ERC_2,
			Http.Method.PATCH);

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeHttpCode(null, endpointPath1, Http.Method.GET));
		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeHttpCode(null, endpointPath2, Http.Method.GET));

		String randomEndpointPath1 =
			_API_BASE_URL_1 + StringPool.SLASH + RandomTestUtil.randomString();

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeHttpCode(
				null, randomEndpointPath1, Http.Method.GET));

		String randomEndpointPath2 =
			_API_BASE_URL_2 + StringPool.SLASH + RandomTestUtil.randomString();

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeHttpCode(
				null, randomEndpointPath2, Http.Method.GET));

		HTTPTestUtil.invoke(
			JSONUtil.put(
				"applicationStatus", "unpublished"
			).toString(),
			"headless-builder/applications/by-external-reference-code/" +
				_API_APPLICATION_ERC_1,
			Http.Method.PATCH);

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeHttpCode(null, endpointPath1, Http.Method.GET));
		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeHttpCode(null, endpointPath2, Http.Method.GET));
	}

	@Test
	public void testEndpointReturnsProperSchema() throws Exception {
		_createCustomObjectEntry(_objectDefinitionJSONObject);

		HTTPTestUtil.invoke(
			JSONUtil.put(
				"applicationStatus", "published"
			).toString(),
			"headless-builder/applications/by-external-reference-code/" +
				_API_APPLICATION_ERC_1,
			Http.Method.PATCH);

		JSONAssert.assertEquals(
			JSONUtil.put(
				JSONUtil.put("name", _OBJECT_FIELD_VALUE)
			).toString(),
			HTTPTestUtil.invokeJSONArray(
				null, _API_BASE_URL_1 + _API_APPLICATION_PATH_1, Http.Method.GET
			).toString(),
			JSONCompareMode.STRICT);
	}

	private void _addAPIApplication(
			String baseURL, String externalReferenceCode,
			String objectDefinitionExternalReferenceCode, String path)
		throws Exception {

		String apiEndpointExternalReferenceCode = RandomTestUtil.randomString();
		String apiSchemaExternalReferenceCode = RandomTestUtil.randomString();

		HTTPTestUtil.invoke(
			JSONUtil.put(
				"apiApplicationToAPIEndpoints",
				JSONUtil.put(
					JSONUtil.put(
						"description", "description"
					).put(
						"externalReferenceCode",
						apiEndpointExternalReferenceCode
					).put(
						"httpMethod", "get"
					).put(
						"name", "name"
					).put(
						"path", path
					).put(
						"scope", "company"
					))
			).put(
				"apiApplicationToAPISchemas",
				JSONUtil.put(
					JSONUtil.put(
						"apiSchemaToAPIProperties",
						JSONUtil.put(
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "name"
							).put(
								"objectFieldERC", _OBJECT_FIELD_ERC
							))
					).put(
						"description", "description"
					).put(
						"externalReferenceCode", apiSchemaExternalReferenceCode
					).put(
						"mainObjectDefinitionERC",
						objectDefinitionExternalReferenceCode
					).put(
						"name", "name"
					))
			).put(
				"applicationStatus", "unpublished"
			).put(
				"baseURL", baseURL
			).put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				apiSchemaExternalReferenceCode,
				"/requestAPISchemaToAPIEndpoints/",
				apiEndpointExternalReferenceCode),
			Http.Method.PUT);
		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				apiSchemaExternalReferenceCode,
				"/responseAPISchemaToAPIEndpoints/",
				apiEndpointExternalReferenceCode),
			Http.Method.PUT);
	}

	private void _createCustomObjectEntry(JSONObject objectDefinitionJSONObject)
		throws Exception {

		String restContextPath = objectDefinitionJSONObject.getString(
			"restContextPath");

		String endpoint = StringUtil.removeSubstring(restContextPath, "/o/");

		HTTPTestUtil.invoke(
			JSONUtil.put(
				_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE
			).toString(),
			endpoint, Http.Method.POST);
	}

	private JSONObject _createObjectDefinition() throws Exception {
		return HTTPTestUtil.invoke(
			JSONUtil.put(
				"active", true
			).put(
				"label", JSONUtil.put("en-US", RandomTestUtil.randomString())
			).put(
				"name", "A" + RandomTestUtil.randomString()
			).put(
				"objectFields",
				JSONUtil.put(
					JSONUtil.put(
						"DBType", "String"
					).put(
						"externalReferenceCode", _OBJECT_FIELD_ERC
					).put(
						"indexed", true
					).put(
						"indexedAsKeyword", false
					).put(
						"indexedLanguageId", ""
					).put(
						"label", JSONUtil.put("en_US", "Test field")
					).put(
						"listTypeDefinitionId", 0
					).put(
						"name", _OBJECT_FIELD_NAME
					).put(
						"required", false
					).put(
						"type", "String"
					))
			).put(
				"pluralLabel",
				JSONUtil.put("en-US", RandomTestUtil.randomString())
			).put(
				"portlet", true
			).put(
				"scope", "company"
			).put(
				"status", JSONUtil.put("code", 0)
			).toString(),
			"object-admin/v1.0/object-definitions", Http.Method.POST);
	}

	private static final String _API_APPLICATION_ERC_1 =
		RandomTestUtil.randomString();

	private static final String _API_APPLICATION_ERC_2 =
		RandomTestUtil.randomString();

	private static final String _API_APPLICATION_PATH_1 =
		StringPool.SLASH + RandomTestUtil.randomString();

	private static final String _API_APPLICATION_PATH_2 =
		StringPool.SLASH + RandomTestUtil.randomString();

	private static final String _API_BASE_URL_1 = RandomTestUtil.randomString();

	private static final String _API_BASE_URL_2 = RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE =
		RandomTestUtil.randomString();

	private static JSONObject _objectDefinitionJSONObject;

}