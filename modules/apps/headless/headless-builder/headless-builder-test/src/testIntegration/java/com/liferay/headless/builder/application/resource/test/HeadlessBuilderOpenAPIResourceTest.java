/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.application.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
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
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Carlos Correa
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@FeatureFlags({"LPS-167253", "LPS-184413", "LPS-186757"})
@RunWith(Arquillian.class)
public class HeadlessBuilderOpenAPIResourceTest extends BaseTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		ListTypeEntry listTypeEntry = ListTypeEntryUtil.createListTypeEntry(
			RandomTestUtil.randomString(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()));

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				Collections.singletonList(listTypeEntry));

		_objectDefinition1 = _publishObjectDefinition(
			Arrays.asList(
				new AttachmentObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_ATTACHMENT_FIELD_ERC
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
					_API_SCHEMA_BOOLEAN_FIELD_ERC
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"booleanField"
				).build(),
				new DateObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_DATE_FIELD_ERC
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"dateField"
				).build(),
				new DateTimeObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_DATE_TIME_FIELD_ERC
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
					_API_SCHEMA_DECIMAL_FIELD_ERC
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"decimalField"
				).build(),
				new IntegerObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_INTEGER_FIELD_ERC
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"integerField"
				).build(),
				new LongIntegerObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_LONG_INTEGER_FIELD_ERC
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"longIntegerField"
				).build(),
				new LongTextObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_LONG_TEXT_FIELD_ERC
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"longTextField"
				).build(),
				new MultiselectPicklistObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_MULTISELECT_PICKLIST_FIELD_ERC
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
					_API_SCHEMA_PICKLIST_FIELD_ERC
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
					_API_SCHEMA_PRECISION_DECIMAL_FIELD_ERC
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"precisionDecimalField"
				).build(),
				new RichTextObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_RICH_TEXT_FIELD_ERC
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"richTextField"
				).build(),
				new TextObjectFieldBuilder(
				).externalReferenceCode(
					_API_SCHEMA_TEXT_FIELD_ERC
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"textField"
				).build()));

		_objectDefinition2 = _publishObjectDefinition(
			Arrays.asList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"textField"
				).build()));

		String relationshipName = "a" + RandomTestUtil.randomString();

		_objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(),
			_objectDefinition2.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			relationshipName, ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectField aggregationObjectField = new AggregationObjectFieldBuilder(
		).externalReferenceCode(
			_API_SCHEMA_AGGREGATION_FIELD_ERC
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).name(
			"aggregationField"
		).objectDefinitionId(
			_objectDefinition1.getObjectDefinitionId()
		).objectFieldSettings(
			Arrays.asList(
				_createObjectFieldSetting("function", "COUNT"),
				_createObjectFieldSetting(
					"objectRelationshipName", relationshipName))
		).build();

		_objectFieldLocalService.addCustomObjectField(
			aggregationObjectField.getExternalReferenceCode(),
			TestPropsValues.getUserId(),
			aggregationObjectField.getListTypeDefinitionId(),
			aggregationObjectField.getObjectDefinitionId(),
			aggregationObjectField.getBusinessType(),
			aggregationObjectField.getDBType(),
			aggregationObjectField.isIndexed(),
			aggregationObjectField.isIndexedAsKeyword(),
			aggregationObjectField.getIndexedLanguageId(),
			aggregationObjectField.getLabelMap(),
			aggregationObjectField.isLocalized(),
			aggregationObjectField.getName(),
			aggregationObjectField.getReadOnly(),
			aggregationObjectField.getReadOnlyConditionExpression(),
			aggregationObjectField.isRequired(),
			aggregationObjectField.isState(),
			aggregationObjectField.getObjectFieldSettings());
	}

	@Test
	public void test() throws Exception {
		_addAPIApplication();

		String apiApplicationURL = "/c/" + _API_BASE_URL;

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, "/openapi", Http.Method.GET);

		Assert.assertFalse(jsonObject.has("/" + _API_BASE_URL));

		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				null, apiApplicationURL + "/openapi.json", Http.Method.GET));

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "published"
			).toString(),
			"headless-builder/applications/by-external-reference-code/" +
				_API_APPLICATION_ERC,
			Http.Method.PATCH);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, "/openapi", Http.Method.GET);

		JSONAssert.assertEquals(
			JSONUtil.put(
				apiApplicationURL,
				JSONUtil.put(
					"http://localhost:8080/o/c/" + _API_BASE_URL +
						"/openapi.yaml")
			).toString(),
			jsonObject.toString(), JSONCompareMode.LENIENT);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, apiApplicationURL + "/openapi.json", Http.Method.GET);

		JSONAssert.assertEquals(
			StringUtil.replace(
				new String(
					FileUtil.getBytes(
						getClass(), "dependencies/expected_openapi.json")),
				"${BASE_URL}", "c/" + _API_BASE_URL),
			jsonObject.toString(), JSONCompareMode.STRICT);
	}

	private void _addAPIApplication() throws Exception {
		HTTPTestUtil.invokeToJSONObject(
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
						"path", "/path"
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
								"description", "aggregationProperty description"
							).put(
								"name", "aggregationProperty"
							).put(
								"objectFieldERC",
								_API_SCHEMA_AGGREGATION_FIELD_ERC
							),
							JSONUtil.put(
								"description", "attachmentProperty description"
							).put(
								"name", "attachmentProperty"
							).put(
								"objectFieldERC",
								_API_SCHEMA_ATTACHMENT_FIELD_ERC
							),
							JSONUtil.put(
								"description", "booleanProperty description"
							).put(
								"name", "booleanProperty"
							).put(
								"objectFieldERC", _API_SCHEMA_BOOLEAN_FIELD_ERC
							),
							JSONUtil.put(
								"description", "dateProperty description"
							).put(
								"name", "dateProperty"
							).put(
								"objectFieldERC", _API_SCHEMA_DATE_FIELD_ERC
							),
							JSONUtil.put(
								"description", "dateTimeProperty description"
							).put(
								"name", "dateTimeProperty"
							).put(
								"objectFieldERC",
								_API_SCHEMA_DATE_TIME_FIELD_ERC
							),
							JSONUtil.put(
								"description", "decimalProperty description"
							).put(
								"name", "decimalProperty"
							).put(
								"objectFieldERC", _API_SCHEMA_DECIMAL_FIELD_ERC
							),
							JSONUtil.put(
								"description", "integerProperty description"
							).put(
								"name", "integerProperty"
							).put(
								"objectFieldERC", _API_SCHEMA_INTEGER_FIELD_ERC
							),
							JSONUtil.put(
								"description", "longIntegerProperty description"
							).put(
								"name", "longIntegerProperty"
							).put(
								"objectFieldERC",
								_API_SCHEMA_LONG_INTEGER_FIELD_ERC
							),
							JSONUtil.put(
								"description", "longTextProperty description"
							).put(
								"name", "longTextProperty"
							).put(
								"objectFieldERC",
								_API_SCHEMA_LONG_TEXT_FIELD_ERC
							),
							JSONUtil.put(
								"description",
								"multiselectPicklistProperty description"
							).put(
								"name", "multiselectPicklistProperty"
							).put(
								"objectFieldERC",
								_API_SCHEMA_MULTISELECT_PICKLIST_FIELD_ERC
							),
							JSONUtil.put(
								"description", "picklistProperty description"
							).put(
								"name", "picklistProperty"
							).put(
								"objectFieldERC", _API_SCHEMA_PICKLIST_FIELD_ERC
							),
							JSONUtil.put(
								"description",
								"precisionDecimalProperty description"
							).put(
								"name", "precisionDecimalProperty"
							).put(
								"objectFieldERC",
								_API_SCHEMA_PRECISION_DECIMAL_FIELD_ERC
							),
							JSONUtil.put(
								"description", "richTextProperty description"
							).put(
								"name", "richTextProperty"
							).put(
								"objectFieldERC",
								_API_SCHEMA_RICH_TEXT_FIELD_ERC
							),
							JSONUtil.put(
								"description", "textProperty description"
							).put(
								"name", "textProperty"
							).put(
								"objectFieldERC", _API_SCHEMA_TEXT_FIELD_ERC
							))
					).put(
						"description", "description"
					).put(
						"externalReferenceCode", _API_SCHEMA_ERC
					).put(
						"mainObjectDefinitionERC",
						_objectDefinition1.getExternalReferenceCode()
					).put(
						"name", "SchemaName"
					))
			).put(
				"applicationStatus", "unpublished"
			).put(
				"baseURL", _API_BASE_URL
			).put(
				"externalReferenceCode", _API_APPLICATION_ERC
			).put(
				"title", "title"
			).toString(),
			"headless-builder/applications", Http.Method.POST);
		HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				_API_SCHEMA_ERC, "/requestAPISchemaToAPIEndpoints/",
				_API_ENDPOINT_ERC),
			Http.Method.PUT);
		HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				_API_SCHEMA_ERC, "/responseAPISchemaToAPIEndpoints/",
				_API_ENDPOINT_ERC),
			Http.Method.PUT);
	}

	private ObjectFieldSetting _createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(0L);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
	}

	private ObjectDefinition _publishObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private static final String _API_APPLICATION_ERC =
		RandomTestUtil.randomString();

	private static final String _API_BASE_URL = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	private static final String _API_ENDPOINT_ERC =
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

	private static final String _API_SCHEMA_ERC = RandomTestUtil.randomString();

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

	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	private ObjectDefinition _objectDefinition1;
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}