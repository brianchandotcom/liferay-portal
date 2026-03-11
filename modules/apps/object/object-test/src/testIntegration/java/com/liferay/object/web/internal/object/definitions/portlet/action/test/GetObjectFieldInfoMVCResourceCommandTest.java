/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.FormulaObjectFieldBuilder;
import com.liferay.object.field.builder.ObjectFieldBuilder;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class GetObjectFieldInfoMVCResourceCommandTest
	extends BaseMVCResourceCommandTestCase {

	@Test
	public void testGetDefaultValueSidebarElementsObjectFieldInfo()
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_assertDefaultValueSidebarElementsBooleanFunctions(objectDefinition);
		_assertDefaultValueSidebarElementsDateFunctions(
			ObjectFieldConstants.BUSINESS_TYPE_DATE, objectDefinition);
		_assertDefaultValueSidebarElementsDateFunctions(
			ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME, objectDefinition);
		_assertDefaultValueSidebarElementsEmptyFunctions(
			ObjectFieldConstants.BUSINESS_TYPE_DECIMAL, objectDefinition);
		_assertDefaultValueSidebarElementsEmptyFunctions(
			ObjectFieldConstants.BUSINESS_TYPE_INTEGER, objectDefinition);
		_assertDefaultValueSidebarElementsEmptyFunctions(
			ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER, objectDefinition);
		_assertDefaultValueSidebarElementsEmptyFunctions(
			ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT, objectDefinition);
		_assertDefaultValueSidebarElementsEmptyFunctions(
			ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL,
			objectDefinition);
		_assertDefaultValueSidebarElementsEmptyFunctions(
			ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT, objectDefinition);
		_assertDefaultValueSidebarElementsEmptyFunctions(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT, objectDefinition);
	}

	@Test
	public void testGetFormulaObjectFieldInfo() throws Exception {
		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();
		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		ObjectRelationshipTestUtil.addObjectRelationship(
			_objectRelationshipLocalService, objectDefinition1,
			objectDefinition2,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			"objectRelationship");

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new FormulaObjectFieldBuilder(
			).labelMap(
				RandomTestUtil.randomLocaleStringMap()
			).name(
				"a" + RandomTestUtil.randomString()
			).objectDefinitionId(
				objectDefinition2.getObjectDefinitionId()
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						"output"
					).value(
						ObjectFieldConstants.BUSINESS_TYPE_INTEGER
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						"script"
					).value(
						String.valueOf(RandomTestUtil.randomInt())
					).build())
			).userId(
				TestPropsValues.getUserId()
			).build());

		JSONObject jsonObject = getJSONObject(
			Collections.singletonMap(
				"objectFieldId",
				String.valueOf(objectField.getObjectFieldId())));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"items",
					JSONUtil.putAll(
						JSONUtil.put(
							"content", "id"
						).put(
							"label", "ID"
						))
				).put(
					"key", "fields"
				).put(
					"label", "Fields"
				),
				JSONUtil.put(
					"items",
					JSONUtil.putAll(
						JSONUtil.put("label", "Divided By"),
						JSONUtil.put("label", "Minus"),
						JSONUtil.put("label", "Plus"),
						JSONUtil.put("label", "Times"))
				).put(
					"key", "operators"
				).put(
					"label", "Operators"
				),
				JSONUtil.put(
					"items",
					JSONUtil.putAll(
						JSONUtil.put(
							"content",
							"r_objectRelationship_" +
								objectDefinition1.getPKObjectFieldName() + "_id"
						).put(
							"label", "ID"
						))
				).put(
					"label",
					objectDefinition1.getLabel(LocaleUtil.US) + " Fields"
				)
			).toString(),
			String.valueOf(jsonObject.getJSONArray("sidebarElements")),
			JSONCompareMode.LENIENT);
	}

	@Override
	protected MVCResourceCommand getMVCResourceCommand() {
		return _mvcResourceCommand;
	}

	private ObjectField _addCustomObjectField(
			String businessType, ObjectDefinition objectDefinition)
		throws Exception {

		List<ObjectFieldSetting> objectFieldSettings = new ArrayList<>();

		if (businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME)) {
			objectFieldSettings.add(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_TIME_STORAGE
				).value(
					ObjectFieldSettingConstants.VALUE_USE_INPUT_AS_ENTERED
				).build());
		}

		return ObjectFieldUtil.addCustomObjectField(
			new ObjectFieldBuilder(
			).businessType(
				businessType
			).labelMap(
				RandomTestUtil.randomLocaleStringMap()
			).name(
				"a" + RandomTestUtil.randomString()
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				objectFieldSettings
			).userId(
				TestPropsValues.getUserId()
			).build());
	}

	private void _assertDefaultValueSidebarElementsBooleanFunctions(
			ObjectDefinition objectDefinition)
		throws Exception {

		JSONObject jsonObject = _getJSONObject(
			ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN, objectDefinition);

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"items",
					JSONUtil.putAll(
						JSONUtil.put(
							"content", "currentDate"
						).put(
							"label", "Current Date"
						),
						JSONUtil.put(
							"content", "currentUserId"
						).put(
							"label", "Current User"
						))
				).put(
					"label", "General Variables"
				),
				JSONUtil.put(
					"items",
					JSONUtil.putAll(
						JSONUtil.put("label", "Compare Dates"),
						JSONUtil.put("label", "Condition"),
						JSONUtil.put("label", "Contains"),
						JSONUtil.put("label", "Does Not Contain"),
						JSONUtil.put("label", "Future Dates"),
						JSONUtil.put("label", "Is a URL"),
						JSONUtil.put("label", "Is an email"),
						JSONUtil.put("label", "Is Decimal"),
						JSONUtil.put("label", "Is Empty"),
						JSONUtil.put("label", "Is Equal To"),
						JSONUtil.put("label", "Is Greater Than"),
						JSONUtil.put("label", "Is Greater Than Or Equal To"),
						JSONUtil.put("label", "Is Integer"),
						JSONUtil.put("label", "Is Less Than"),
						JSONUtil.put("label", "Is Less Than Or Equal To"),
						JSONUtil.put("label", "Is Not Equal To"),
						JSONUtil.put("label", "Match"),
						JSONUtil.put("label", "Past Dates"),
						JSONUtil.put("label", "Range"))
				).put(
					"label", "Functions"
				)
			).toString(),
			String.valueOf(
				jsonObject.getJSONArray("defaultValueSidebarElements")),
			JSONCompareMode.LENIENT);
	}

	private void _assertDefaultValueSidebarElementsDateFunctions(
			String businessType, ObjectDefinition objectDefinition)
		throws Exception {

		JSONObject jsonObject = _getJSONObject(businessType, objectDefinition);

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"items",
					JSONUtil.putAll(
						JSONUtil.put(
							"content", "currentDate"
						).put(
							"label", "Current Date"
						))
				).put(
					"label", "General Variables"
				),
				JSONUtil.put(
					"items",
					JSONUtil.putAll(
						JSONUtil.put("label", "Add Days"),
						JSONUtil.put("label", "Add Months"),
						JSONUtil.put("label", "Add Years"),
						JSONUtil.put("label", "Old Value"))
				).put(
					"label", "Functions"
				)
			).toString(),
			String.valueOf(
				jsonObject.getJSONArray("defaultValueSidebarElements")),
			JSONCompareMode.LENIENT);
	}

	private void _assertDefaultValueSidebarElementsEmptyFunctions(
			String businessType, ObjectDefinition objectDefinition)
		throws Exception {

		JSONObject jsonObject = _getJSONObject(businessType, objectDefinition);

		Assert.assertNull(
			jsonObject.getJSONArray("defaultValueSidebarElements"));
	}

	private JSONObject _getJSONObject(
			String businessType, ObjectDefinition objectDefinition)
		throws Exception {

		ObjectField objectField = _addCustomObjectField(
			businessType, objectDefinition);

		return getJSONObject(
			Collections.singletonMap(
				"objectFieldId",
				String.valueOf(objectField.getObjectFieldId())));
	}

	@Inject(
		filter = "mvc.command.name=/object_definitions/get_object_field_info"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}