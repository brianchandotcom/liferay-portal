/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.resource.test;

import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.headless.delivery.client.dto.v1_0.Document;
import com.liferay.headless.delivery.client.resource.v1_0.DocumentResource;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectFieldValidationConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.AggregationObjectFieldBuilder;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.field.builder.BooleanObjectFieldBuilder;
import com.liferay.object.field.builder.DateObjectFieldBuilder;
import com.liferay.object.field.builder.DateTimeObjectFieldBuilder;
import com.liferay.object.field.builder.DecimalObjectFieldBuilder;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.LongIntegerObjectFieldBuilder;
import com.liferay.object.field.builder.LongTextObjectFieldBuilder;
import com.liferay.object.field.builder.MultiselectPicklistObjectFieldBuilder;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.PrecisionDecimalObjectFieldBuilder;
import com.liferay.object.field.builder.RichTextObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.rest.test.util.ObjectFieldTestUtil;
import com.liferay.object.rest.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.File;
import java.io.Serializable;

import java.text.DateFormat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Luis Miguel Barcos
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@FeatureFlags({"LPS-167253", "LPS-178642"})
public class HeadlessBuilderResourceTest extends BaseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");
		_dateTimeFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_documentResource = DocumentResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();

		_group = GroupTestUtil.addGroup();

		List<ListTypeEntry> listTypeEntries = TransformUtil.transformToList(
			ListTypeValue.values(),
			listTypeValue -> ListTypeEntryUtil.createListTypeEntry(
				listTypeValue.name(),
				Collections.singletonMap(LocaleUtil.US, listTypeValue.name())));

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				false, listTypeEntries);

		_objectDefinition1 = _addObjectDefinition(
			1, ObjectDefinitionConstants.SCOPE_COMPANY);
		_objectDefinition2 = _addObjectDefinition(
			2, ObjectDefinitionConstants.SCOPE_COMPANY);
		_objectDefinition3 = _addObjectDefinition(
			3, ObjectDefinitionConstants.SCOPE_COMPANY);

		_objectRelationship1 = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2);
		_objectRelationship2 = _addObjectRelationship(
			_objectDefinition2, _objectDefinition3);

		_addAggregationObjectField(
			_objectDefinition1, _objectRelationship1.getName());
		_addAggregationObjectField(
			_objectDefinition2, _objectRelationship2.getName());

		_siteScopedObjectDefinition1 = _addObjectDefinition(
			1, ObjectDefinitionConstants.SCOPE_SITE);
		_siteScopedObjectDefinition2 = _addObjectDefinition(
			2, ObjectDefinitionConstants.SCOPE_SITE);
		_siteScopedObjectDefinition3 = _addObjectDefinition(
			3, ObjectDefinitionConstants.SCOPE_SITE);

		_siteScopedObjectRelationship1 = _addObjectRelationship(
			_siteScopedObjectDefinition1, _siteScopedObjectDefinition2);
		_siteScopedObjectRelationship2 = _addObjectRelationship(
			_siteScopedObjectDefinition2, _siteScopedObjectDefinition3);

		_addAggregationObjectField(
			_siteScopedObjectDefinition1,
			_siteScopedObjectRelationship1.getName());
		_addAggregationObjectField(
			_siteScopedObjectDefinition2,
			_siteScopedObjectRelationship2.getName());
	}

	@Test
	public void testGetInDifferentCompany() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinition1.getExternalReferenceCode(),
			_objectRelationship1.getName(), _objectRelationship2.getName(),
			_API_APPLICATION_PATH_1, null, "collection",
			APIApplication.Endpoint.Scope.COMPANY);
		_publishAPIApplication(_API_APPLICATION_ERC_1);

		assertSuccessfulHttpCode(
			null, "c/" + _BASE_URL_1 + _API_APPLICATION_PATH_1,
			Http.Method.GET);

		Assert.assertTrue(
			HTTPTestUtil.invokeToJSONObject(
				null, "openapi", Http.Method.GET
			).has(
				"/c/" + _BASE_URL_1
			));

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"domain", "able.com"
			).put(
				"portalInstanceId", "able.com"
			).put(
				"virtualHost", "www.able.com"
			).toString(),
			"headless-portal-instances/v1.0/portal-instances",
			Http.Method.POST);

		HTTPTestUtil.customize(
		).withBaseURL(
			"http://www.able.com:8080"
		).withCredentials(
			"test@able.com", "test"
		).apply(
			() -> {
				Assert.assertEquals(
					404,
					HTTPTestUtil.invokeToHttpCode(
						null, "c/" + _BASE_URL_1 + _API_APPLICATION_PATH_1,
						Http.Method.GET));
				Assert.assertFalse(
					HTTPTestUtil.invokeToJSONObject(
						null, "openapi", Http.Method.GET
					).has(
						"/c/" + _BASE_URL_1
					));

				String externalReferenceCode = RandomTestUtil.randomString();

				assertSuccessfulHttpCode(
					JSONUtil.put(
						"applicationStatus", "published"
					).put(
						"baseURL", _BASE_URL_1
					).put(
						"externalReferenceCode", externalReferenceCode
					).put(
						"title", "test-app"
					).toString(),
					"headless-builder/applications", Http.Method.POST);

				Assert.assertTrue(
					HTTPTestUtil.invokeToJSONObject(
						null, "openapi", Http.Method.GET
					).has(
						"/c/" + _BASE_URL_1
					));

				assertSuccessfulHttpCode(
					null,
					"headless-builder/applications/by-external-reference-code" +
						"/" + externalReferenceCode,
					Http.Method.DELETE);

				Assert.assertFalse(
					HTTPTestUtil.invokeToJSONObject(
						null, "openapi", Http.Method.GET
					).has(
						"/c/" + _BASE_URL_1
					));
			}
		);
	}

	@Test
	public void testGetByExternalReferenceCode()
		throws Exception {

		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinition1.getExternalReferenceCode(),
			_objectRelationship1.getName(), _objectRelationship2.getName(),
			_API_APPLICATION_PATH_1, "externalReferenceCode", "singleElement",
			APIApplication.Endpoint.Scope.COMPANY);

		_publishAPIApplication(_API_APPLICATION_ERC_1);

		ObjectEntry objectEntry = _addCustomObjectEntry(
			1, null, _objectDefinition1, "value1");

		JSONAssert.assertEquals(
			JSONUtil.put(
				"textProperty", "value1"
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					"c/", _BASE_URL_1, _API_APPLICATION_PATH_1,
					StringPool.FORWARD_SLASH,
					objectEntry.getExternalReferenceCode()),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testGetByObjectEntryId() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinition1.getExternalReferenceCode(),
			_objectRelationship1.getName(), _objectRelationship2.getName(),
			_API_APPLICATION_PATH_1, "ID", "singleElement",
			APIApplication.Endpoint.Scope.COMPANY);

		_publishAPIApplication(_API_APPLICATION_ERC_1);

		ObjectEntry objectEntry = _addCustomObjectEntry(
			1, null, _objectDefinition1, "value1");

		JSONAssert.assertEquals(
			JSONUtil.put(
				"textProperty", "value1"
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					"c/", _BASE_URL_1, _API_APPLICATION_PATH_1,
					StringPool.FORWARD_SLASH, objectEntry.getObjectEntryId()),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testGetWithAPIFilter() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinition1.getExternalReferenceCode(),
			_objectRelationship1.getName(), _objectRelationship2.getName(),
			_API_APPLICATION_PATH_1, null, "collection",
			APIApplication.Endpoint.Scope.COMPANY);

		_addAPIFilter(
			_API_ENDPOINT_ERC_1,
			"textField eq 'value5' or textField eq 'value7'");

		_publishAPIApplication(_API_APPLICATION_ERC_1);

		for (int i = 0; i <= 25; i++) {
			_addCustomObjectEntry(i, null, _objectDefinition1, "value" + i);
		}

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put("textProperty", "value5"),
					JSONUtil.put("textProperty", "value7"))
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

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items", JSONUtil.putAll(JSONUtil.put("textProperty", "value5"))
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
				StringBundler.concat(
					"c/", _BASE_URL_1, _API_APPLICATION_PATH_1, "?filter=",
					URLCodec.encodeURL(
						"textProperty eq 'value5' or textProperty eq " +
							"'value8'")),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testGetWithAPISortAsc() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinition1.getExternalReferenceCode(),
			_objectRelationship1.getName(), _objectRelationship2.getName(),
			_API_APPLICATION_PATH_1, null, "collection",
			APIApplication.Endpoint.Scope.COMPANY);

		_addAPISort(_API_ENDPOINT_ERC_1, String.format("%s:asc", "textField"));

		_publishAPIApplication(_API_APPLICATION_ERC_1);

		_addCustomObjectEntry(1, null, _objectDefinition1, "value1");
		_addCustomObjectEntry(2, null, _objectDefinition1, "value2");
		_addCustomObjectEntry(3, null, _objectDefinition1, "value3");

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, "c/" + _BASE_URL_1 + _API_APPLICATION_PATH_1,
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"integerProperty", 1
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value1"
				),
				JSONUtil.put(
					"integerProperty", 2
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value2"
				),
				JSONUtil.put(
					"integerProperty", 3
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value3"
				)
			).toString(),
			itemsJSONArray.toString(), JSONCompareMode.STRICT);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				"c/", _BASE_URL_1, _API_APPLICATION_PATH_1, "?sort=",
				URLCodec.encodeURL("textProperty:desc")),
			Http.Method.GET);

		itemsJSONArray = jsonObject.getJSONArray("items");

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"integerProperty", 3
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value3"
				),
				JSONUtil.put(
					"integerProperty", 2
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value2"
				),
				JSONUtil.put(
					"integerProperty", 1
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value1"
				)
			).toString(),
			itemsJSONArray.toString(), JSONCompareMode.STRICT);
	}

	@Test
	public void testGetWithAPISortDesc() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinition1.getExternalReferenceCode(),
			_objectRelationship1.getName(), _objectRelationship2.getName(),
			_API_APPLICATION_PATH_1, null, "collection",
			APIApplication.Endpoint.Scope.COMPANY);

		_addAPISort(_API_ENDPOINT_ERC_1, String.format("%s:desc", "textField"));

		_publishAPIApplication(_API_APPLICATION_ERC_1);

		_addCustomObjectEntry(1, null, _objectDefinition1, "value1");
		_addCustomObjectEntry(2, null, _objectDefinition1, "value2");
		_addCustomObjectEntry(3, null, _objectDefinition1, "value3");

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, "c/" + _BASE_URL_1 + _API_APPLICATION_PATH_1,
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"integerProperty", 3
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value3"
				),
				JSONUtil.put(
					"integerProperty", 2
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value2"
				),
				JSONUtil.put(
					"integerProperty", 1
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value1"
				)
			).toString(),
			itemsJSONArray.toString(), JSONCompareMode.STRICT);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				"c/", _BASE_URL_1, _API_APPLICATION_PATH_1, "?sort=",
				URLCodec.encodeURL("textProperty:asc")),
			Http.Method.GET);

		itemsJSONArray = jsonObject.getJSONArray("items");

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"integerProperty", 1
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value1"
				),
				JSONUtil.put(
					"integerProperty", 2
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value2"
				),
				JSONUtil.put(
					"integerProperty", 3
				).put(
					"multiselectPicklistProperty", Collections.emptyList()
				).put(
					"relatedIntegerProperty1", Collections.emptyList()
				).put(
					"relatedIntegerProperty2", Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty1",
					Collections.emptyList()
				).put(
					"relatedMultiselectPicklistProperty2",
					Collections.emptyList()
				).put(
					"relatedTextProperty1", Collections.emptyList()
				).put(
					"relatedTextProperty2", Collections.emptyList()
				).put(
					"textProperty", "value3"
				)
			).toString(),
			itemsJSONArray.toString(), JSONCompareMode.STRICT);
	}

	@Test
	public void testGetWithCompanyScopedEndpoint() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinition1.getExternalReferenceCode(),
			_objectRelationship1.getName(), _objectRelationship2.getName(),
			_API_APPLICATION_PATH_1, null, "collection",
			APIApplication.Endpoint.Scope.COMPANY);
		_addAPIApplication(
			_API_APPLICATION_ERC_2, _API_ENDPOINT_ERC_2, _BASE_URL_2,
			_objectDefinition1.getExternalReferenceCode(),
			_objectRelationship1.getName(), _objectRelationship2.getName(),
			_API_APPLICATION_PATH_2, null, "collection",
			APIApplication.Endpoint.Scope.COMPANY);

		String endpoint1 = "c/" + _BASE_URL_1 + _API_APPLICATION_PATH_1;
		String scopedEndpoint1 = StringBundler.concat(
			"c/", _BASE_URL_1, "/scopes/%s", _API_APPLICATION_PATH_1);

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(null, endpoint1, Http.Method.GET));

		String endpoint2 = "c/" + _BASE_URL_2 + _API_APPLICATION_PATH_2;
		String scopedEndpoint2 = StringBundler.concat(
			"c/", _BASE_URL_2, "/scopes/%s", _API_APPLICATION_PATH_2);

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(null, endpoint2, Http.Method.GET));

		_publishAPIApplication(_API_APPLICATION_ERC_1);
		_publishAPIApplication(_API_APPLICATION_ERC_2);

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(null, endpoint1, Http.Method.GET));
		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(null, endpoint2, Http.Method.GET));
		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				null,
				String.format(scopedEndpoint1, TestPropsValues.getGroupId()),
				Http.Method.GET));
		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				null,
				String.format(scopedEndpoint2, TestPropsValues.getGroupId()),
				Http.Method.GET));

		ObjectEntry objectEntry1 = _addCustomObjectEntry(
			1, null, _objectDefinition1, "value1");
		ObjectEntry objectEntry2 = _addCustomObjectEntry(
			2, null, _objectDefinition2, "value2");

		_relateObjectEntries(objectEntry1, objectEntry2, _objectRelationship1);

		ObjectEntry objectEntry3 = _addCustomObjectEntry(
			3, null, _objectDefinition3, "value3");

		_relateObjectEntries(objectEntry2, objectEntry3, _objectRelationship2);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.put(
					JSONUtil.put(
						"relatedTextProperty1", JSONUtil.put("value2")
					).put(
						"relatedTextProperty2", JSONUtil.put("value3")
					).put(
						"textProperty", "value1"
					))
			).put(
				"totalCount", 1
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null, endpoint1, Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.put(
					JSONUtil.put(
						"relatedTextProperty1", JSONUtil.put("value2")
					).put(
						"relatedTextProperty2", JSONUtil.put("value3")
					).put(
						"textProperty", "value1"
					))
			).put(
				"totalCount", 1
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null, endpoint2, Http.Method.GET
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
			HTTPTestUtil.invokeToHttpCode(null, endpoint1, Http.Method.GET));
		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(null, endpoint2, Http.Method.GET));
	}

	@Test
	public void testGetWithPagination() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinition1.getExternalReferenceCode(),
			_objectRelationship1.getName(), _objectRelationship2.getName(),
			_API_APPLICATION_PATH_1, null, "collection",
			APIApplication.Endpoint.Scope.COMPANY);

		_publishAPIApplication(_API_APPLICATION_ERC_1);

		for (int i = 0; i <= 25; i++) {
			_addCustomObjectEntry(i, null, _objectDefinition1, "value" + i);
		}

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put("textProperty", "value5"),
					JSONUtil.put("textProperty", "value6"),
					JSONUtil.put("textProperty", "value7"),
					JSONUtil.put("textProperty", "value8"),
					JSONUtil.put("textProperty", "value9"))
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

	@Test
	public void testGetWithRequestFilter() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_objectDefinition1.getExternalReferenceCode(),
			_objectRelationship1.getName(), _objectRelationship2.getName(),
			_API_APPLICATION_PATH_1, null, "collection",
			APIApplication.Endpoint.Scope.COMPANY);

		_publishAPIApplication(_API_APPLICATION_ERC_1);

		ObjectEntry objectEntry1 = _addCustomObjectEntry(
			1, Arrays.asList(ListTypeValue.VALUE1), _objectDefinition1,
			"1value1");
		ObjectEntry objectEntry2 = _addCustomObjectEntry(
			2, Arrays.asList(ListTypeValue.VALUE2, ListTypeValue.VALUE3),
			_objectDefinition1, "2value2");

		// Comparison operators

		_assertFilterString("integerProperty", 1, "integerProperty eq 1");
		_assertFilterString("integerProperty", 1, "integerProperty ne 2");
		_assertFilterString("integerProperty", 2, "integerProperty gt 1");
		_assertFilterString("integerProperty", 2, "integerProperty ge 2");
		_assertFilterString("integerProperty", 1, "integerProperty lt 2");
		_assertFilterString("integerProperty", 1, "integerProperty le 1");
		_assertFilterString(
			"integerProperty", 1, "startswith(textProperty,'1value')");
		_assertFilterString(
			"integerProperty", 1, "textProperty in ('1value1','3value3')");

		// Grouping operators

		_assertFilterString(
			"integerProperty", 2,
			"((integerProperty gt 1 or integerProperty lt 1) and " +
				"(textProperty eq '2value2'))");

		// Lambda operators

		_assertFilterString(
			"integerProperty", 1,
			"multiselectPicklistProperty/any(k:contains(k,'LUE1'))");

		// Logical operators

		_assertFilterString(
			"integerProperty", 1,
			"integerProperty ge 1 and integerProperty lt 2");
		_assertFilterString(
			"integerProperty", 2,
			"integerProperty gt 1 or integerProperty lt 1");
		_assertFilterString("integerProperty", 1, "not (integerProperty ge 2)");

		// String functions

		_assertFilterString(
			"integerProperty", 1, "contains(textProperty, 'value1')");

		// Filter using related object entry fields

		ObjectEntry level1RelatedObjectEntry1 = _addCustomObjectEntry(
			3, Arrays.asList(ListTypeValue.VALUE2), _objectDefinition2,
			"3value3");

		_relateObjectEntries(
			objectEntry1, level1RelatedObjectEntry1, _objectRelationship1);

		ObjectEntry level1RelatedObjectEntry2 = _addCustomObjectEntry(
			4, Arrays.asList(ListTypeValue.VALUE1, ListTypeValue.VALUE3),
			_objectDefinition2, "4value4");

		_relateObjectEntries(
			objectEntry2, level1RelatedObjectEntry2, _objectRelationship1);

		ObjectEntry level2RelatedObjectEntry1 = _addCustomObjectEntry(
			5, Arrays.asList(ListTypeValue.VALUE3), _objectDefinition3,
			"5value5");

		_relateObjectEntries(
			level1RelatedObjectEntry1, level2RelatedObjectEntry1,
			_objectRelationship2);

		ObjectEntry level2RelatedObjectEntry2 = _addCustomObjectEntry(
			6, Arrays.asList(ListTypeValue.VALUE1, ListTypeValue.VALUE2),
			_objectDefinition3, "6value6");

		_relateObjectEntries(
			level1RelatedObjectEntry2, level2RelatedObjectEntry2,
			_objectRelationship2);

		// Comparison operators (using related object entries fields)

		_assertFilterString(
			"integerProperty", 1, "relatedIntegerProperty1 eq 3");
		_assertFilterString(
			"integerProperty", 1, "relatedIntegerProperty2 eq 5");
		_assertFilterString(
			"integerProperty", 1, "relatedIntegerProperty1 ne 4");
		_assertFilterString(
			"integerProperty", 1, "relatedIntegerProperty2 ne 6");
		_assertFilterString(
			"integerProperty", 2, "relatedIntegerProperty1 gt 3");
		_assertFilterString(
			"integerProperty", 2, "relatedIntegerProperty2 gt 5");
		_assertFilterString(
			"integerProperty", 2, "relatedIntegerProperty1 ge 4");
		_assertFilterString(
			"integerProperty", 2, "relatedIntegerProperty2 ge 6");
		_assertFilterString(
			"integerProperty", 1, "relatedIntegerProperty1 lt 4");
		_assertFilterString(
			"integerProperty", 1, "relatedIntegerProperty2 lt 6");
		_assertFilterString(
			"integerProperty", 1, "relatedIntegerProperty1 le 3");
		_assertFilterString(
			"integerProperty", 1, "relatedIntegerProperty2 le 5");
		_assertFilterString(
			"integerProperty", 1, "startswith(relatedTextProperty1,'3value')");
		_assertFilterString(
			"integerProperty", 1, "startswith(relatedTextProperty2,'5value')");
		_assertFilterString(
			"integerProperty", 1,
			"relatedTextProperty1 in ('1value1','3value3')");
		_assertFilterString(
			"integerProperty", 1,
			"relatedTextProperty2 in ('1value1','5value5')");

		// Grouping operators (using related object entries fields)

		_assertFilterString(
			"integerProperty", 2,
			"((relatedIntegerProperty1 gt 3 or relatedIntegerProperty1 lt 3) " +
				"and (relatedTextProperty1 eq '4value4'))");

		_assertFilterString(
			"integerProperty", 2,
			"((relatedIntegerProperty2 gt 5 or relatedIntegerProperty2 lt 5) " +
				"and (relatedTextProperty2 eq '6value6'))");

		// Lambda operators (using related object entries fields)

		_assertFilterString(
			"integerProperty", 1,
			"relatedMultiselectPicklistProperty1/any(k:contains(k,'LUE2'))");
		_assertFilterString(
			"integerProperty", 1,
			"relatedMultiselectPicklistProperty2/any(k:contains(k,'LUE3'))");

		// Logical operators (using related object entries fields)

		_assertFilterString(
			"integerProperty", 1,
			"relatedIntegerProperty1 ge 3 and relatedIntegerProperty1 lt 4");
		_assertFilterString(
			"integerProperty", 1,
			"relatedIntegerProperty2 ge 5 and relatedIntegerProperty2 lt 6");
		_assertFilterString(
			"integerProperty", 2,
			"relatedIntegerProperty1 gt 3 or relatedIntegerProperty1 lt 3");
		_assertFilterString(
			"integerProperty", 2,
			"relatedIntegerProperty2 gt 5 or relatedIntegerProperty2 lt 5");

		_assertFilterString(
			"integerProperty", 1, "not (relatedIntegerProperty1 ge 4)");
		_assertFilterString(
			"integerProperty", 1, "not (relatedIntegerProperty2 ge 6)");

		// String functions (using related object entries fields)

		_assertFilterString(
			"integerProperty", 1, "contains(relatedTextProperty1, 'value3')");

		_assertFilterString(
			"integerProperty", 1, "contains(relatedTextProperty2, 'value5')");
	}

	@Test
	public void testGetWithSiteScopedEndpoint() throws Exception {
		_addAPIApplication(
			_API_APPLICATION_ERC_1, _API_ENDPOINT_ERC_1, _BASE_URL_1,
			_siteScopedObjectDefinition1.getExternalReferenceCode(),
			_siteScopedObjectRelationship1.getName(),
			_siteScopedObjectRelationship2.getName(), _API_APPLICATION_PATH_1,
			null, "collection", APIApplication.Endpoint.Scope.GROUP);

		String endpointPath = "c/" + _BASE_URL_1 + _API_APPLICATION_PATH_1;
		String scopedEndpointPath = StringBundler.concat(
			"c/", _BASE_URL_1, "/scopes/%s", _API_APPLICATION_PATH_1);

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(null, endpointPath, Http.Method.GET));
		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				null,
				String.format(scopedEndpointPath, TestPropsValues.getGroupId()),
				Http.Method.GET));

		_publishAPIApplication(_API_APPLICATION_ERC_1);

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(null, endpointPath, Http.Method.GET));
		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null,
				String.format(scopedEndpointPath, TestPropsValues.getGroupId()),
				Http.Method.GET));

		ObjectEntry objectEntry1 = _addCustomObjectEntry(
			_group.getGroupId(), 1, null, _siteScopedObjectDefinition1,
			"value1");
		ObjectEntry objectEntry2 = _addCustomObjectEntry(
			_group.getGroupId(), 2, null, _siteScopedObjectDefinition2,
			"value2");

		_relateObjectEntries(
			objectEntry1, objectEntry2, _siteScopedObjectRelationship1);

		ObjectEntry objectEntry3 = _addCustomObjectEntry(
			_group.getGroupId(), 3, null, _siteScopedObjectDefinition3,
			"value3");

		_relateObjectEntries(
			objectEntry2, objectEntry3, _siteScopedObjectRelationship2);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items", JSONFactoryUtil.createJSONArray()
			).put(
				"totalCount", 0
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				String.format(scopedEndpointPath, TestPropsValues.getGroupId()),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.put(
					JSONUtil.put(
						"relatedTextProperty1", JSONUtil.put("value2")
					).put(
						"relatedTextProperty2", JSONUtil.put("value3")
					).put(
						"textProperty", "value1"
					))
			).put(
				"totalCount", 1
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null, String.format(scopedEndpointPath, _group.getGroupId()),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "unpublished"
			).toString(),
			"headless-builder/applications/by-external-reference-code/" +
				_API_APPLICATION_ERC_1,
			Http.Method.PATCH);

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(null, endpointPath, Http.Method.GET));
		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				null, String.format(scopedEndpointPath, _group.getGroupId()),
				Http.Method.GET));
	}

	private void _addAggregationObjectField(
			ObjectDefinition objectDefinition, String relationshipName)
		throws Exception {

		ObjectField aggregationObjectField = new AggregationObjectFieldBuilder(
		).externalReferenceCode(
			_API_SCHEMA_AGGREGATION_FIELD_ERC
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).name(
			"aggregationField"
		).objectDefinitionId(
			objectDefinition.getObjectDefinitionId()
		).objectFieldSettings(
			Arrays.asList(
				_createObjectFieldSetting("function", "COUNT"),
				_createObjectFieldSetting(
					"objectRelationshipName", relationshipName))
		).build();

		ObjectFieldTestUtil.addCustomObjectField(
			TestPropsValues.getUserId(), aggregationObjectField);
	}

	private void _addAPIApplication(
			String apiApplicationExternalReferenceCode,
			String apiEndpointExternalReferenceCode, String baseURL,
			String objectDefinitionExternalReferenceCode,
			String objectRelationshipName1, String objectRelationshipName2,
			String path, String pathParameter, String retrieveType,
			APIApplication.Endpoint.Scope scope)
		throws Exception {

		String apiSchemaExternalReferenceCode = RandomTestUtil.randomString();

		assertSuccessfulHttpCode(
			JSONUtil.put(
				"apiApplicationToAPIEndpoints",
				_createAPIEndpoint(
					apiEndpointExternalReferenceCode, path, pathParameter,
					retrieveType, scope)
			).put(
				"apiApplicationToAPISchemas",
				JSONUtil.put(
					JSONUtil.put(
						"apiSchemaToAPIProperties",
						JSONUtil.putAll(
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "integerProperty"
							).put(
								"objectFieldERC",
								_API_SCHEMA_INTEGER_FIELD_ERC + 1
							),
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "multiselectPicklistProperty"
							).put(
								"objectFieldERC",
								_API_SCHEMA_MULTISELECT_PICKLIST_FIELD_ERC + 1
							),
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "textProperty"
							).put(
								"objectFieldERC", _API_SCHEMA_TEXT_FIELD_ERC + 1
							),
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "relatedIntegerProperty1"
							).put(
								"objectFieldERC",
								_API_SCHEMA_INTEGER_FIELD_ERC + 2
							).put(
								"objectRelationshipNames",
								objectRelationshipName1
							),
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "relatedMultiselectPicklistProperty1"
							).put(
								"objectFieldERC",
								_API_SCHEMA_MULTISELECT_PICKLIST_FIELD_ERC + 2
							).put(
								"objectRelationshipNames",
								objectRelationshipName1
							),
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "relatedTextProperty1"
							).put(
								"objectFieldERC", _API_SCHEMA_TEXT_FIELD_ERC + 2
							).put(
								"objectRelationshipNames",
								objectRelationshipName1
							),
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "relatedIntegerProperty2"
							).put(
								"objectFieldERC",
								_API_SCHEMA_INTEGER_FIELD_ERC + 3
							).put(
								"objectRelationshipNames",
								String.format(
									"%s,%s", objectRelationshipName1,
									objectRelationshipName2)
							),
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "relatedMultiselectPicklistProperty2"
							).put(
								"objectFieldERC",
								_API_SCHEMA_MULTISELECT_PICKLIST_FIELD_ERC + 3
							).put(
								"objectRelationshipNames",
								String.format(
									"%s,%s", objectRelationshipName1,
									objectRelationshipName2)
							),
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "relatedTextProperty2"
							).put(
								"objectFieldERC", _API_SCHEMA_TEXT_FIELD_ERC + 3
							).put(
								"objectRelationshipNames",
								String.format(
									"%s,%s", objectRelationshipName1,
									objectRelationshipName2)
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
				"externalReferenceCode", apiApplicationExternalReferenceCode
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);
		assertSuccessfulHttpCode(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				apiSchemaExternalReferenceCode,
				"/requestAPISchemaToAPIEndpoints/",
				apiEndpointExternalReferenceCode),
			Http.Method.PUT);
		assertSuccessfulHttpCode(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				apiSchemaExternalReferenceCode,
				"/responseAPISchemaToAPIEndpoints/",
				apiEndpointExternalReferenceCode),
			Http.Method.PUT);
	}

	private void _addAPIFilter(
			String apiEndpointExternalReferenceCode, String filterString)
		throws Exception {

		assertSuccessfulHttpCode(
			JSONUtil.put(
				"oDataFilter", filterString
			).put(
				"r_apiEndpointToAPIFilters_c_apiEndpointERC",
				apiEndpointExternalReferenceCode
			).toString(),
			"headless-builder/filters", Http.Method.POST);
	}

	private void _addAPISort(
			String apiEndpointExternalReferenceCode, String sortString)
		throws Exception {

		assertSuccessfulHttpCode(
			JSONUtil.put(
				"oDataSort", sortString
			).put(
				"r_apiEndpointToAPISorts_c_apiEndpointERC",
				apiEndpointExternalReferenceCode
			).toString(),
			"headless-builder/sorts", Http.Method.POST);
	}

	private ObjectEntry _addCustomObjectEntry(
			int integerFieldValue,
			List<ListTypeValue> multiselectPicklistFieldValue,
			ObjectDefinition objectDefinition, String textFieldValue)
		throws Exception {

		return _addCustomObjectEntry(
			0L, integerFieldValue, multiselectPicklistFieldValue,
			objectDefinition, textFieldValue);
	}

	private ObjectEntry _addCustomObjectEntry(
			long groupId, int integerFieldValue,
			List<ListTypeValue> multiselectPicklistFieldValue,
			ObjectDefinition objectDefinition, String textFieldValue)
		throws Exception {

		ListTypeValue listTypeValue = RandomTestUtil.randomEnum(
			ListTypeValue.class);

		return ObjectEntryTestUtil.addObjectEntry(
			groupId, objectDefinition,
			HashMapBuilder.<String, Serializable>put(
				"attachmentField",
				() -> {
					Document document = new Document() {
						{
							description = RandomTestUtil.randomString();
							fileName = RandomTestUtil.randomString() + ".txt";
							title = RandomTestUtil.randomString();
						}
					};

					document = _documentResource.postSiteDocument(
						TestPropsValues.getGroupId(), document,
						HashMapBuilder.<String, File>put(
							"file",
							() -> FileUtil.createTempFile(
								TestDataConstants.TEST_BYTE_ARRAY)
						).build());

					return document.getId();
				}
			).put(
				"booleanField", RandomTestUtil.randomBoolean()
			).put(
				"dateField", _dateFormat.format(RandomTestUtil.nextDate())
			).put(
				"dateTimeField",
				_dateTimeFormat.format(RandomTestUtil.nextDate())
			).put(
				"decimalField", RandomTestUtil.randomDouble()
			).put(
				"integerField", integerFieldValue
			).put(
				"longIntegerField",
				RandomTestUtil.randomLong(
					ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MIN,
					ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MAX)
			).put(
				"longTextField", RandomTestUtil.randomString()
			).put(
				"multiselectPicklistField",
				(Serializable)TransformUtil.transform(
					multiselectPicklistFieldValue, ListTypeValue::name)
			).put(
				"picklistField", listTypeValue.name()
			).put(
				"precisionDecimalField", RandomTestUtil.randomDouble()
			).put(
				"richTextField", RandomTestUtil.randomString()
			).put(
				"textField", textFieldValue
			).build());
	}

	private ObjectDefinition _addObjectDefinition(int index, String scope)
		throws Exception {

		return ObjectDefinitionTestUtil.publishObjectDefinition(
			Arrays.asList(
				new AttachmentObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_ATTACHMENT_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"attachmentField"
				).objectFieldSettings(
					Arrays.asList(
						_createObjectFieldSetting(
							"acceptedFileExtensions", "txt"),
						_createObjectFieldSetting(
							"fileSource", "documentsAndMedia"),
						_createObjectFieldSetting("maximumFileSize", "100"))
				).build(),
				new BooleanObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_BOOLEAN_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"booleanField"
				).build(),
				new DateObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_DATE_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"dateField"
				).build(),
				new DateTimeObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_DATE_TIME_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"dateTimeField"
				).objectFieldSettings(
					Collections.singletonList(
						_createObjectFieldSetting(
							ObjectFieldSettingConstants.NAME_TIME_STORAGE,
							ObjectFieldSettingConstants.
								VALUE_USE_INPUT_AS_ENTERED))
				).build(),
				new DecimalObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_DECIMAL_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"decimalField"
				).build(),
				new IntegerObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_INTEGER_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"integerField"
				).build(),
				new LongIntegerObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_LONG_INTEGER_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"longIntegerField"
				).build(),
				new LongTextObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_LONG_TEXT_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"longTextField"
				).build(),
				new MultiselectPicklistObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_MULTISELECT_PICKLIST_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).listTypeDefinitionId(
					_listTypeDefinition.getListTypeDefinitionId()
				).name(
					"multiselectPicklistField"
				).build(),
				new PicklistObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_PICKLIST_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"picklistField"
				).listTypeDefinitionId(
					_listTypeDefinition.getListTypeDefinitionId()
				).build(),
				new PrecisionDecimalObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_PRECISION_DECIMAL_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"precisionDecimalField"
				).build(),
				new RichTextObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_RICH_TEXT_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"richTextField"
				).build(),
				new TextObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_TEXT_FIELD_ERC + index
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"textField"
				).build()),
			scope);
	}

	private ObjectRelationship _addObjectRelationship(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2)
		throws Exception {

		return ObjectRelationshipTestUtil.addObjectRelationship(
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			objectDefinition1, objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
	}

	private void _assertFilterString(
			String expectedObjectFieldName,
			Serializable expectedObjectFieldValue, String filterString)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				"c/", _BASE_URL_1, _API_APPLICATION_PATH_1, "?filter=",
				URLCodec.encodeURL(filterString)),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			String.valueOf(expectedObjectFieldValue),
			String.valueOf(itemJSONObject.get(expectedObjectFieldName)));
	}

	private JSONArray _createAPIEndpoint(
		String apiEndpointExternalReferenceCode, String path,
		String pathParameter, String retrieveType,
		APIApplication.Endpoint.Scope scope) {

		if (Objects.equals(retrieveType, "singleElement") &&
			(pathParameter != null)) {

			return JSONUtil.put(
				JSONUtil.put(
					"description", "description"
				).put(
					"externalReferenceCode", apiEndpointExternalReferenceCode
				).put(
					"httpMethod", "get"
				).put(
					"name", "name"
				).put(
					"path", path + "/{pathElement}"
				).put(
					"pathParameter", pathParameter
				).put(
					"retrieveType", retrieveType
				).put(
					"scope", StringUtil.toLowerCase(scope.name())
				));
		}

		return JSONUtil.put(
			JSONUtil.put(
				"description", "description"
			).put(
				"externalReferenceCode", apiEndpointExternalReferenceCode
			).put(
				"httpMethod", "get"
			).put(
				"name", "name"
			).put(
				"path", path
			).put(
				"retrieveType", retrieveType
			).put(
				"scope", StringUtil.toLowerCase(scope.name())
			));
	}

	private ObjectFieldSetting _createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(0L);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
	}

	private void _publishAPIApplication(
			String apiApplicationExternalReferenceCode)
		throws Exception {

		assertSuccessfulHttpCode(
			JSONUtil.put(
				"applicationStatus", "published"
			).toString(),
			"headless-builder/applications/by-external-reference-code/" +
				apiApplicationExternalReferenceCode,
			Http.Method.PATCH);
	}

	private void _relateObjectEntries(
			ObjectEntry objectEntry1, ObjectEntry objectEntry2,
			ObjectRelationship objectRelationship)
		throws Exception {

		ObjectRelationshipTestUtil.relateObjectEntries(
			objectEntry1.getObjectEntryId(), objectEntry2.getObjectEntryId(),
			objectRelationship, TestPropsValues.getUserId());
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

	private static final String _API_SCHEMA_AGGREGATION_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_ATTACHMENT_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_BOOLEAN_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_DATE_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_DATE_TIME_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_DECIMAL_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_INTEGER_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_LONG_INTEGER_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_LONG_TEXT_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_MULTISELECT_PICKLIST_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_PICKLIST_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_PRECISION_DECIMAL_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_RICH_TEXT_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _API_SCHEMA_TEXT_FIELD_ERC =
		RandomTestUtil.randomString();

	private static final String _BASE_URL_1 = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	private static final String _BASE_URL_2 = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	private static DateFormat _dateFormat;
	private static DateFormat _dateTimeFormat;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	private DocumentResource _documentResource;
	private Group _group;
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition3;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	private ObjectRelationship _objectRelationship1;
	private ObjectRelationship _objectRelationship2;

	@DeleteAfterTestRun
	private ObjectDefinition _siteScopedObjectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _siteScopedObjectDefinition2;

	@DeleteAfterTestRun
	private ObjectDefinition _siteScopedObjectDefinition3;

	private ObjectRelationship _siteScopedObjectRelationship1;
	private ObjectRelationship _siteScopedObjectRelationship2;

	private enum ListTypeValue {

		VALUE1, VALUE2, VALUE3

	}

}