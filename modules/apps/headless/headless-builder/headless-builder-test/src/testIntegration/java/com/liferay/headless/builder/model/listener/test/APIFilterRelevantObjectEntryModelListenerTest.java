/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.model.listener.test;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
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
import com.liferay.portal.kernel.util.TextFormatter;
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
@FeatureFlags("LPS-178642")
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

		_objectDefinitionJSONObject = _addObjectDefinition();

		_objectEntryJSONObject = _addObjectEntry();
	}

	@Test
	public void testGetApplicationsWithAPIFilterAsNestedFieldsWithNestedFieldsDepth()
		throws Exception {

		_addAPIApplication(
			_objectDefinitionJSONObject.getString("externalReferenceCode"));

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
				"headless-builder/applications?nestedFields=" +
					"apiApplicationToAPIEndpoints," +
						"apiEndpointToAPIFilters&nestedFieldsDepth=2",
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testGetEndpointsWithAPIEndpointToAPIFiltersAsNestedFields()
		throws Exception {

		_addAPIApplication(
			_objectDefinitionJSONObject.getString("externalReferenceCode"));

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
				"headless-builder/endpoints?nestedFields=" +
					"apiEndpointToAPIFilters",
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testPostFilter() throws Exception {
		_addAPIApplication(
			_objectDefinitionJSONObject.getString("externalReferenceCode"));

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
					"r_apiEndpointToAPIFilters_l_apiEndpointERC",
					_API_ENDPOINT_ERC
				).toString(),
				"headless-builder/filters", Http.Method.POST
			).toString(),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"Object entry value exceeds the maximum length of 1000 " +
					"characters for object field \"oDataFilter\""
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"objectFieldERC", RandomTestUtil.randomString()
				).put(
					"oDataFilter", RandomTestUtil.randomString(1001)
				).put(
					"r_apiEndpointToAPIFilters_l_apiEndpointERC",
					_API_ENDPOINT_ERC
				).toString(),
				"headless-builder/filters", Http.Method.POST
			).toString(),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"The API endpoint already has an associated API filter."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"objectFieldERC", RandomTestUtil.randomString()
				).put(
					"oDataFilter", "test ne 1"
				).put(
					"r_apiEndpointToAPIFilters_l_apiEndpointERC",
					_API_ENDPOINT_ERC
				).toString(),
				"headless-builder/filters", Http.Method.POST
			).toString(),
			JSONCompareMode.LENIENT);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"description", "description"
			).put(
				"externalReferenceCode", RandomTestUtil.randomString()
			).put(
				"httpMethod", "get"
			).put(
				"name", "name"
			).put(
				"path",
				StringBundler.concat(
					StringPool.FORWARD_SLASH,
					StringUtil.toLowerCase(RandomTestUtil.randomString()),
					"/{pathParameterId}")
			).put(
				"pathParameter", "id"
			).put(
				"r_apiApplicationToAPIEndpoints_l_apiApplicationERC",
				_API_APPLICATION_ERC
			).put(
				"r_responseAPISchemaToAPIEndpoints_l_apiSchemaERC",
				_API_SCHEMA_ERC
			).put(
				"retrieveType", "singleElement"
			).put(
				"scope", APIApplication.Endpoint.Scope.COMPANY.getValue()
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"The API filter can only be associated to API endpoints with " +
					"a retrieve type of \"collection.\""
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"oDataFilter", "test:desc"
				).put(
					"r_apiEndpointToAPIFilters_l_apiEndpointId",
					jsonObject.get("id")
				).toString(),
				"headless-builder/filters", Http.Method.POST
			).toString(),
			JSONCompareMode.LENIENT);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"The value is invalid for object field " +
					"\"r_apiEndpointToAPIFilters_l_apiEndpointId\""
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"objectFieldERC", RandomTestUtil.randomString()
				).put(
					"oDataFilter", "test ne 1"
				).put(
					"r_apiEndpointToAPIFilters_l_apiEndpointId",
					_objectEntryJSONObject.getLong("id")
				).toString(),
				"headless-builder/filters", Http.Method.POST
			).toString(),
			JSONCompareMode.LENIENT);
	}

	private void _addAPIApplication(
			String objectDefinitionExternalReferenceCode)
		throws Exception {

		HTTPTestUtil.invokeToHttpCode(
			JSONUtil.put(
				"apiApplicationToAPIEndpoints",
				JSONUtil.put(
					JSONUtil.put(
						"description", "description"
					).put(
						"externalReferenceCode", _API_ENDPOINT_ERC
					).put(
						"httpMethod", "get"
					).put(
						"name", "name"
					).put(
						"path", _API_APPLICATION_PATH
					).put(
						"retrieveType", "collection"
					).put(
						"scope",
						APIApplication.Endpoint.Scope.COMPANY.getValue()
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
								"objectFieldERC", _OBJECT_FIELD_ERC
							))
					).put(
						"description", "description"
					).put(
						"externalReferenceCode", _API_SCHEMA_ERC
					).put(
						"mainObjectDefinitionERC",
						objectDefinitionExternalReferenceCode
					).put(
						"name", "name"
					))
			).put(
				"applicationStatus", "published"
			).put(
				"baseURL", StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				"externalReferenceCode", _API_APPLICATION_ERC
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		HTTPTestUtil.invokeToHttpCode(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				_API_SCHEMA_ERC, "/requestAPISchemaToAPIEndpoints/",
				_API_ENDPOINT_ERC),
			Http.Method.PUT);
		HTTPTestUtil.invokeToHttpCode(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				_API_SCHEMA_ERC, "/responseAPISchemaToAPIEndpoints/",
				_API_ENDPOINT_ERC),
			Http.Method.PUT);
	}

	private void _addAPIFilter(String filterString) throws Exception {
		HTTPTestUtil.invokeToHttpCode(
			JSONUtil.put(
				"oDataFilter", filterString
			).put(
				"r_apiEndpointToAPIFilters_l_apiEndpointERC", _API_ENDPOINT_ERC
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
				"name", _OBJECT_NAME
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
				"scope", APIApplication.Endpoint.Scope.COMPANY.getValue()
			).put(
				"status", JSONUtil.put("code", 0)
			).toString(),
			"object-admin/v1.0/object-definitions", Http.Method.POST);
	}

	private JSONObject _addObjectEntry() throws Exception {
		String pluralObjectName = TextFormatter.formatPlural(
			StringUtil.toLowerCase(_OBJECT_NAME));

		return HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME, RandomTestUtil.randomString()
			).toString(),
			"c/" + pluralObjectName, Http.Method.POST);
	}

	private static final String _API_APPLICATION_ERC =
		RandomTestUtil.randomString();

	private static final String _API_APPLICATION_PATH =
		StringPool.SLASH +
			StringUtil.toLowerCase(RandomTestUtil.randomString());

	private static final String _API_ENDPOINT_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_ERC = RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_NAME =
		ObjectDefinitionTestUtil.getRandomName();

	private static JSONObject _objectDefinitionJSONObject;
	private static JSONObject _objectEntryJSONObject;

}