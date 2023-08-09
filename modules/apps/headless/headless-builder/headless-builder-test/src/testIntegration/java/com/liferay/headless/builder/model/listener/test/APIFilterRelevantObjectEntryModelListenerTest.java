/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.model.listener.test;

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
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Alberto Javier Moreno Lage
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@FeatureFlags({"LPS-167253", "LPS-184413", "LPS-186757"})
public class APIFilterRelevantObjectEntryModelListenerTest
	extends BaseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_objectDefinitionJSONObject1 = _addObjectDefinition();
	}

	@Test
	public void testGetApplicationsWithAPIFilterAsNestedFieldsWithNestedFieldsDepth()
		throws Exception {

		_addAPIApplication(
			_objectDefinitionJSONObject1.getString("externalReferenceCode"));

		_addAPIFilter("name eq 'myTest' or name eq '1@ ab9'");

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						"apiApplicationToAPIEndpoints",
						JSONUtil.putAll(
							JSONUtil.put(
								"apiEndpointToAPIFilters",
								JSONUtil.putAll(
									JSONUtil.put(
										"oDataFilter",
										"name eq 'myTest' or name eq '1@ ab" +
											"9'"))))))
			).put(
				"lastPage", 1
			).put(
				"page", 1
			).put(
				"pageSize", 20
			).put(
				"totalCount", 1
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				"headless-builder/applications" +
					"?nestedFields=apiApplicationToAPIEndpoints," +
						"apiEndpointToAPIFilters&nestedFieldsDepth=2",
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testGetEndpointsWithApiEndpointToAPIFiltersAsNestedFields()
		throws Exception {

		_addAPIApplication(
			_objectDefinitionJSONObject1.getString("externalReferenceCode"));

		_addAPIFilter("name eq 'myTest'");

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						"apiEndpointToAPIFilters",
						JSONUtil.putAll(
							JSONUtil.put("oDataFilter", "name eq 'myTest'"))))
			).put(
				"lastPage", 1
			).put(
				"page", 1
			).put(
				"pageSize", 20
			).put(
				"totalCount", 1
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				"headless-builder/endpoints" +
					"?nestedFields=apiEndpointToAPIFilters",
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testPostFilter() throws Exception {
		_addAPIApplication(
			_objectDefinitionJSONObject1.getString("externalReferenceCode"));

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", JSONUtil.put("code", 0)
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"objectFieldERC", RandomTestUtil.randomString()
				).put(
					"oDataFilter", "test ne 1"
				).put(
					"r_apiEndpointToAPIFilters_c_apiEndpointERC",
					_API_ENDPOINT_ERC_1
				).toString(),
				"headless-builder/filters", Http.Method.POST
			).toString(),
			JSONCompareMode.LENIENT);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title", "An API filter must be related to an API endpoint."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"objectFieldERC", RandomTestUtil.randomString()
				).put(
					"oDataFilter", "test ne 1"
				).put(
					"r_apiEndpointToAPIFilters_c_apiEndpointId",
					RandomTestUtil.randomLong()
				).toString(),
				"headless-builder/filters", Http.Method.POST
			).toString(),
			JSONCompareMode.LENIENT);
	}

	private void _addAPIApplication(
			String objectDefinitionExternalReferenceCode)
		throws Exception {

		String apiSchemaExternalReferenceCode = RandomTestUtil.randomString();

		HTTPTestUtil.invokeToHttpCode(
			JSONUtil.put(
				"apiApplicationToAPIEndpoints",
				JSONUtil.put(
					JSONUtil.put(
						"description", "description"
					).put(
						"externalReferenceCode",
						APIFilterRelevantObjectEntryModelListenerTest.
							_API_ENDPOINT_ERC_1
					).put(
						"httpMethod", "get"
					).put(
						"name", "name"
					).put(
						"path",
						APIFilterRelevantObjectEntryModelListenerTest.
							_API_APPLICATION_PATH_1
					).put(
						"scope", "company"
					))
			).put(
				"apiApplicationToAPISchemas",
				JSONUtil.put(
					JSONUtil.put(
						"apiSchemaToAPIProperties",
						JSONUtil.putAll(
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "name"
							).put(
								"objectFieldERC", _OBJECT_FIELD_ERC_1
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
				"applicationStatus", "published"
			).put(
				"baseURL",
				APIFilterRelevantObjectEntryModelListenerTest._BASE_URL_1
			).put(
				"externalReferenceCode",
				APIFilterRelevantObjectEntryModelListenerTest.
					_API_APPLICATION_ERC_1
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		HTTPTestUtil.invokeToHttpCode(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				apiSchemaExternalReferenceCode,
				"/requestAPISchemaToAPIEndpoints/",
				APIFilterRelevantObjectEntryModelListenerTest.
					_API_ENDPOINT_ERC_1),
			Http.Method.PUT);
		HTTPTestUtil.invokeToHttpCode(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				apiSchemaExternalReferenceCode,
				"/responseAPISchemaToAPIEndpoints/",
				APIFilterRelevantObjectEntryModelListenerTest.
					_API_ENDPOINT_ERC_1),
			Http.Method.PUT);
	}

	private void _addAPIFilter(String filterString) throws Exception {
		HTTPTestUtil.invokeToHttpCode(
			JSONUtil.put(
				"oDataFilter", filterString
			).put(
				"r_apiEndpointToAPIFilters_c_apiEndpointERC",
				APIFilterRelevantObjectEntryModelListenerTest.
					_API_ENDPOINT_ERC_1
			).toString(),
			"headless-builder/filters", Http.Method.POST);
	}

	private JSONObject _addObjectDefinition() throws Exception {
		return HTTPTestUtil.invokeToJSONObject(
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
						"externalReferenceCode",
						APIFilterRelevantObjectEntryModelListenerTest.
							_OBJECT_FIELD_ERC_1
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
						"name",
						APIFilterRelevantObjectEntryModelListenerTest.
							_OBJECT_FIELD_NAME_1
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

	private static final String _API_APPLICATION_PATH_1 =
		StringPool.SLASH + RandomTestUtil.randomString();

	private static final String _API_ENDPOINT_ERC_1 =
		RandomTestUtil.randomString();

	private static final String _BASE_URL_1 = RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_ERC_1 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_1 =
		"x" + RandomTestUtil.randomString();

	private static JSONObject _objectDefinitionJSONObject1;

}