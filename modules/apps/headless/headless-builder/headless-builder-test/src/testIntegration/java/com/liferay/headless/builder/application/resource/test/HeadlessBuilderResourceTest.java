/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.application.resource.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.object.constants.ObjectRelationshipConstants;
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

import javax.ws.rs.core.Response;

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

		_objectDefinitionJSONObject1 = _addObjectDefinition(
			_OBJECT_FIELD_ERC_1, _OBJECT_FIELD_NAME_1);

		_objectDefinitionJSONObject2 = _addObjectDefinition(
			_OBJECT_FIELD_ERC_2, _OBJECT_FIELD_NAME_2);

		_objectDefinitionJSONObject3 = _addObjectDefinition(
			_OBJECT_FIELD_ERC_3, _OBJECT_FIELD_NAME_3);

		_objectRelationshipJSONObject1 = _addObjectRelationship(
			_objectDefinitionJSONObject1, _objectDefinitionJSONObject2,
			"a" + RandomTestUtil.randomString(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_objectRelationshipJSONObject2 = _addObjectRelationship(
			_objectDefinitionJSONObject2, _objectDefinitionJSONObject3,
			"a" + RandomTestUtil.randomString(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
	}

	@Test
	public void testGet() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinitionJSONObject1.getString("externalReferenceCode"),
			_API_APPLICATION_PATH_1);
		_addAPIApplication(
			_API_APPLICATION_ERC_2, _API_ENDPOINT_ERC_2, _BASE_URL_2,
			_objectDefinitionJSONObject1.getString("externalReferenceCode"),
			_API_APPLICATION_PATH_2);

		String endpointPath1 = "c/" + _BASE_URL_1 + _API_APPLICATION_PATH_1;

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				null, endpointPath1, Http.Method.GET));

		String endpointPath2 = "c/" + _BASE_URL_2 + _API_APPLICATION_PATH_2;

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				null, endpointPath2, Http.Method.GET));

		_publishAPIApplication(_API_APPLICATION_ERC_1);
		_publishAPIApplication(_API_APPLICATION_ERC_2);

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null, endpointPath1, Http.Method.GET));
		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null, endpointPath2, Http.Method.GET));

		JSONObject objectFieldJSONObject1 = _addCustomObjectEntry(
			_objectDefinitionJSONObject1, _OBJECT_FIELD_NAME_1,
			_OBJECT_FIELD_VALUE_1);

		JSONObject objectFieldJSONObject2 = _addCustomObjectEntry(
			_objectDefinitionJSONObject2, _OBJECT_FIELD_NAME_2,
			_OBJECT_FIELD_VALUE_2);

		JSONObject objectFieldJSONObject3 = _addCustomObjectEntry(
			_objectDefinitionJSONObject3, _OBJECT_FIELD_NAME_3,
			_OBJECT_FIELD_VALUE_3);

		_relateObjectEntries(
			_objectDefinitionJSONObject1, _objectRelationshipJSONObject1,
			objectFieldJSONObject1.getString("externalReferenceCode"),
			objectFieldJSONObject2.getString("externalReferenceCode"));

		_relateObjectEntries(
			_objectDefinitionJSONObject2, _objectRelationshipJSONObject2,
			objectFieldJSONObject2.getString("externalReferenceCode"),
			objectFieldJSONObject3.getString("externalReferenceCode"));

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.put(
					JSONUtil.put(
						"name", _OBJECT_FIELD_VALUE_1
					).put(
						"relatedFieldName1", JSONUtil.put(_OBJECT_FIELD_VALUE_2)
					).put(
						"relatedFieldName2", JSONUtil.put(_OBJECT_FIELD_VALUE_3)
					))
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null, endpointPath1, Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.put(
					JSONUtil.put(
						"name", _OBJECT_FIELD_VALUE_1
					).put(
						"relatedFieldName1", JSONUtil.put(_OBJECT_FIELD_VALUE_2)
					).put(
						"relatedFieldName2", JSONUtil.put(_OBJECT_FIELD_VALUE_3)
					))
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null, endpointPath2, Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				null,
				StringBundler.concat(
					"c/", _BASE_URL_1, StringPool.SLASH,
					RandomTestUtil.randomString()),
				Http.Method.GET));
		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				null,
				StringBundler.concat(
					"c/", _BASE_URL_2, StringPool.SLASH,
					RandomTestUtil.randomString()),
				Http.Method.GET));

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "unpublished"
			).toString(),
			"headless-builder/applications/by-external-reference-code/" +
				_API_APPLICATION_ERC_1,
			Http.Method.PATCH);

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				null, endpointPath1, Http.Method.GET));
		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null, endpointPath2, Http.Method.GET));
	}

	@Test
	public void testGetWithFilter() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinitionJSONObject1.getString("externalReferenceCode"),
			_API_APPLICATION_PATH_1);

		_addAPIFilter(
			_API_ENDPOINT_ERC_1,
			String.format(
				"%s eq '5' or %s eq '7'", _OBJECT_FIELD_NAME_1,
				_OBJECT_FIELD_NAME_1));

		_publishAPIApplication(_API_APPLICATION_ERC_1);

		for (int i = 0; i <= 25; i++) {
			_addCustomObjectEntry(
				_objectDefinitionJSONObject1, _OBJECT_FIELD_NAME_1,
				String.valueOf(i));
		}

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put("name", "5"), JSONUtil.put("name", "7"))
			).put(
				"lastPage", 1
			).put(
				"page", 1
			).put(
				"pageSize", 20
			).put(
				"totalCount", 2
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null, "c/" + _BASE_URL_1 + _API_APPLICATION_PATH_1,
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testGetWithPagination() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinitionJSONObject1.getString("externalReferenceCode"),
			_API_APPLICATION_PATH_1);

		_publishAPIApplication(_API_APPLICATION_ERC_1);

		for (int i = 0; i <= 25; i++) {
			_addCustomObjectEntry(
				_objectDefinitionJSONObject1, _OBJECT_FIELD_NAME_1,
				String.valueOf(i));
		}

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put("name", "5"), JSONUtil.put("name", "6"),
					JSONUtil.put("name", "7"), JSONUtil.put("name", "8"),
					JSONUtil.put("name", "9"))
			).put(
				"lastPage", 6
			).put(
				"page", 2
			).put(
				"pageSize", 5
			).put(
				"totalCount", 26
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				String.format(
					"%s?page=2&pageSize=5",
					"c/" + _BASE_URL_1 + _API_APPLICATION_PATH_1),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	private void _addAPIApplication(
			String apiApplicationExternalReferenceCode,
			String apiEndpointExternalReferenceCode, String baseURL,
			String objectDefinitionExternalReferenceCode, String path)
		throws Exception {

		String apiSchemaExternalReferenceCode = RandomTestUtil.randomString();

		_assertSuccessfulHttpCode(
			HTTPTestUtil.invokeToHttpCode(
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
							JSONUtil.putAll(
								JSONUtil.put(
									"description", "description"
								).put(
									"name", "name"
								).put(
									"objectFieldERC", _OBJECT_FIELD_ERC_1
								),
								JSONUtil.put(
									"description", "description"
								).put(
									"name", "relatedFieldName1"
								).put(
									"objectFieldERC", _OBJECT_FIELD_ERC_2
								).put(
									"objectRelationshipNames",
									_objectRelationshipJSONObject1.getString(
										"name")
								),
								JSONUtil.put(
									"description", "description"
								).put(
									"name", "relatedFieldName2"
								).put(
									"objectFieldERC", _OBJECT_FIELD_ERC_3
								).put(
									"objectRelationshipNames",
									String.format(
										"%s,%s",
										_objectRelationshipJSONObject1.
											getString("name"),
										_objectRelationshipJSONObject2.
											getString("name"))
								))
						).put(
							"description", "description"
						).put(
							"externalReferenceCode",
							apiSchemaExternalReferenceCode
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
					"externalReferenceCode", apiApplicationExternalReferenceCode
				).put(
					"title", RandomTestUtil.randomString()
				).toString(),
				"headless-builder/applications", Http.Method.POST));

		_assertSuccessfulHttpCode(
			HTTPTestUtil.invokeToHttpCode(
				null,
				StringBundler.concat(
					"headless-builder/schemas/by-external-reference-code/",
					apiSchemaExternalReferenceCode,
					"/requestAPISchemaToAPIEndpoints/",
					apiEndpointExternalReferenceCode),
				Http.Method.PUT));
		_assertSuccessfulHttpCode(
			HTTPTestUtil.invokeToHttpCode(
				null,
				StringBundler.concat(
					"headless-builder/schemas/by-external-reference-code/",
					apiSchemaExternalReferenceCode,
					"/responseAPISchemaToAPIEndpoints/",
					apiEndpointExternalReferenceCode),
				Http.Method.PUT));
	}

	private void _addAPIFilter(
			String apiEndpointExternalReferenceCode, String filterString)
		throws Exception {

		_assertSuccessfulHttpCode(
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					"oDataFilter", filterString
				).put(
					"r_apiEndpointToAPIFilters_c_apiEndpointERC",
					apiEndpointExternalReferenceCode
				).toString(),
				"headless-builder/filters", Http.Method.POST));
	}

	private JSONObject _addCustomObjectEntry(
			JSONObject objectDefinitionJSONObject, String objectFieldName,
			String objectFieldValue)
		throws Exception {

		String restContextPath = objectDefinitionJSONObject.getString(
			"restContextPath");

		String endpoint = StringUtil.removeSubstring(restContextPath, "/o/");

		return HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				objectFieldName, objectFieldValue
			).toString(),
			endpoint, Http.Method.POST);
	}

	private JSONObject _addObjectDefinition(
			String objectFieldExternalReferenceCode, String objectFieldName)
		throws Exception {

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
						objectFieldExternalReferenceCode
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
						"name", objectFieldName
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

	private JSONObject _addObjectRelationship(
			JSONObject objectDefinitionJSONObject1,
			JSONObject objectDefinitionJSONObject2, String relationshipName,
			String relationshipType)
		throws Exception {

		return HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"deletionType", "cascade"
			).put(
				"label", JSONUtil.put("en_US", RandomTestUtil.randomString())
			).put(
				"name", relationshipName
			).put(
				"objectDefinitionExternalReferenceCode1",
				objectDefinitionJSONObject1.getString("externalReferenceCode")
			).put(
				"objectDefinitionExternalReferenceCode2",
				objectDefinitionJSONObject2.getString("externalReferenceCode")
			).put(
				"objectDefinitionName2",
				objectDefinitionJSONObject2.getString("name")
			).put(
				"parameterObjectFieldId", 0
			).put(
				"parameterObjectFieldName", ""
			).put(
				"reverse", false
			).put(
				"type", relationshipType
			).toString(),
			"object-admin/v1.0/object-definitions/by-external-reference-code/" +
				objectDefinitionJSONObject1.getString("externalReferenceCode") +
					"/object-relationships",
			Http.Method.POST);
	}

	private void _assertSuccessfulHttpCode(int httpCode) {
		Assert.assertEquals(
			Response.Status.Family.SUCCESSFUL,
			Response.Status.Family.familyOf(httpCode));
	}

	private void _publishAPIApplication(
			String apiApplicationExternalReferenceCode)
		throws Exception {

		_assertSuccessfulHttpCode(
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					"applicationStatus", "published"
				).toString(),
				"headless-builder/applications/by-external-reference-code/" +
					apiApplicationExternalReferenceCode,
				Http.Method.PATCH));
	}

	private void _relateObjectEntries(
			JSONObject objectDefinitionJSONObject1,
			JSONObject objectRelationshipJSONObject,
			String objectEntry1ExternalReferenceCode,
			String objectEntry2ExternalReferenceCode)
		throws Exception {

		String restContextPath = objectDefinitionJSONObject1.getString(
			"restContextPath");

		String basePath = StringUtil.removeSubstring(restContextPath, "/o/");

		String objectRelationshipName = objectRelationshipJSONObject.getString(
			"name");

		String endpoint = String.format(
			"%s/by-external-reference-code/%s/%s/%s", basePath,
			objectEntry1ExternalReferenceCode, objectRelationshipName,
			objectEntry2ExternalReferenceCode);

		HTTPTestUtil.invokeToJSONObject(null, endpoint, Http.Method.PUT);
	}

	private static final String _API_APPLICATION_ERC_1 =
		RandomTestUtil.randomString();

	private static final String _API_APPLICATION_ERC_2 =
		RandomTestUtil.randomString();

	private static final String _API_APPLICATION_PATH_1 =
		StringPool.SLASH + RandomTestUtil.randomString();

	private static final String _API_APPLICATION_PATH_2 =
		StringPool.SLASH + RandomTestUtil.randomString();

	private static final String _API_ENDPOINT_ERC_1 =
		RandomTestUtil.randomString();

	private static final String _API_ENDPOINT_ERC_2 =
		RandomTestUtil.randomString();

	private static final String _BASE_URL_1 = RandomTestUtil.randomString();

	private static final String _BASE_URL_2 = RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_ERC_1 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_ERC_2 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_ERC_3 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_1 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_2 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_3 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE_1 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE_2 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE_3 =
		RandomTestUtil.randomString();

	private static JSONObject _objectDefinitionJSONObject1;
	private static JSONObject _objectDefinitionJSONObject2;
	private static JSONObject _objectDefinitionJSONObject3;
	private static JSONObject _objectRelationshipJSONObject1;
	private static JSONObject _objectRelationshipJSONObject2;

}