/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.object.exception.NoSuchObjectValidationRuleException;
import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.exception.ObjectValidationRuleNameException;
import com.liferay.object.exception.ObjectValidationRuleOutputTypeException;
import com.liferay.object.exception.ObjectValidationRuleScriptException;
import com.liferay.object.exception.ObjectValidationRuleSettingNameException;
import com.liferay.object.exception.ObjectValidationRuleSettingValueException;
import com.liferay.object.field.builder.DateObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.validation.rule.setting.builder.ObjectValidationRuleSettingBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcela Cunha
 */
@FeatureFlags("LPS-187846")
@RunWith(Arquillian.class)
public class ObjectValidationRuleLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			false, _objectDefinitionLocalService,
			Arrays.asList(
				new DateObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"dateObjectField"
				).objectFieldSettings(
					Collections.emptyList()
				).build(),
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"textObjectField"
				).objectFieldSettings(
					Collections.emptyList()
				).build()));
	}

	@Test
	public void testAddObjectValidationRule() throws Exception {
		AssertUtils.assertFailure(
			ObjectValidationRuleEngineException.MustNotBeNull.class,
			"Engine is null",
			() -> _addObjectValidationRule(
				StringPool.BLANK, _VALID_DDM_SCRIPT));
		AssertUtils.assertFailure(
			ObjectValidationRuleEngineException.NoSuchEngine.class,
			"Engine \"abcdefghijklmnopqrstuvwxyz\" does not exist",
			() -> _addObjectValidationRule(
				"abcdefghijklmnopqrstuvwxyz", _VALID_DDM_SCRIPT));

		AssertUtils.assertFailure(
			ObjectValidationRuleNameException.class,
			"Name is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _addObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				LocalizedMapUtil.getLocalizedMap(StringPool.BLANK),
				_VALID_DDM_SCRIPT));
		AssertUtils.assertFailure(
			ObjectValidationRuleNameException.class,
			"Name is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _addObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				null, _VALID_DDM_SCRIPT));

		Map<Locale, String> errorLabelMap = LocalizedMapUtil.getLocalizedMap(
			RandomTestUtil.randomString());
		Map<Locale, String> nameLabelMap = LocalizedMapUtil.getLocalizedMap(
			RandomTestUtil.randomString());

		String outputType = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			ObjectValidationRuleOutputTypeException.class,
			"Invalid output type " + outputType,
			() -> _addObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM, errorLabelMap,
				nameLabelMap, outputType, _VALID_DDM_SCRIPT,
				Collections.emptyList()));

		AssertUtils.assertFailure(
			ObjectValidationRuleScriptException.class, "The script is required",
			() -> _addObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				StringPool.BLANK));
		AssertUtils.assertFailure(
			ObjectValidationRuleScriptException.class,
			"The script syntax is invalid",
			() -> _addObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY,
				"import;\ninvalidFields = false;"));

		AssertUtils.assertFailure(
			ObjectValidationRuleSettingNameException.MissingRequiredName.class,
			String.format(
				"The object validation rule setting \"%s\" is required",
				ObjectValidationRuleSettingConstants.
					NAME_OUTPUT_OBJECT_FIELD_ID),
			() -> _addObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM, errorLabelMap,
				nameLabelMap,
				ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
				_VALID_DDM_SCRIPT, Collections.emptyList()));
		AssertUtils.assertFailure(
			ObjectValidationRuleSettingNameException.NotAllowedName.class,
			String.format(
				"The object validation rule setting \"%s\" is not allowed",
				ObjectValidationRuleSettingConstants.
					NAME_OUTPUT_OBJECT_FIELD_ID),
			() -> _addObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM, errorLabelMap,
				nameLabelMap,
				ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
				_VALID_DDM_SCRIPT,
				Collections.singletonList(
					new ObjectValidationRuleSettingBuilder(
					).name(
						ObjectValidationRuleSettingConstants.
							NAME_OUTPUT_OBJECT_FIELD_ID
					).value(
						RandomTestUtil.randomString()
					).build())));

		String objectValidationRuleSettingValue = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			ObjectValidationRuleSettingValueException.InvalidValue.class,
			String.format(
				"The value \"%s\" of the object validation rule setting " +
					"\"%s\" is invalid",
				objectValidationRuleSettingValue,
				ObjectValidationRuleSettingConstants.
					NAME_OUTPUT_OBJECT_FIELD_ID),
			() -> _addObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM, errorLabelMap,
				nameLabelMap,
				ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
				_VALID_DDM_SCRIPT,
				Collections.singletonList(
					new ObjectValidationRuleSettingBuilder(
					).name(
						ObjectValidationRuleSettingConstants.
							NAME_OUTPUT_OBJECT_FIELD_ID
					).value(
						objectValidationRuleSettingValue
					).build())));

		_assertObjectValidationRule(
			true, ObjectValidationRuleConstants.ENGINE_TYPE_DDM, errorLabelMap,
			nameLabelMap, null,
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			_VALID_DDM_SCRIPT,
			_addObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM, errorLabelMap,
				nameLabelMap, _VALID_DDM_SCRIPT));

		String script =
			"import com.liferay.commerce.service.CommerceOrderLocalService;\n" +
				"invalidFields = false;";

		_assertObjectValidationRule(
			true, ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY,
			errorLabelMap, nameLabelMap, null,
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION, script,
			_addObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY, errorLabelMap,
				nameLabelMap, script));

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			_objectDefinition.getObjectDefinitionId(), "textObjectField");

		ObjectValidationRule objectValidationRule = _addObjectValidationRule(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM, errorLabelMap,
			nameLabelMap,
			ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
			_VALID_DDM_SCRIPT,
			Collections.singletonList(
				new ObjectValidationRuleSettingBuilder(
				).name(
					ObjectValidationRuleSettingConstants.
						NAME_OUTPUT_OBJECT_FIELD_ID
				).value(
					String.valueOf(objectField.getObjectFieldId())
				).build()));

		_assertObjectValidationRule(
			true, ObjectValidationRuleConstants.ENGINE_TYPE_DDM, errorLabelMap,
			nameLabelMap, String.valueOf(objectField.getObjectFieldId()),
			ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
			_VALID_DDM_SCRIPT, objectValidationRule);

		_objectFieldLocalService.deleteObjectField(
			objectField.getObjectFieldId());

		objectValidationRule =
			_objectValidationRuleLocalService.getObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId());

		Assert.assertEquals(
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			objectValidationRule.getOutputType());
	}

	@Test
	public void testDeleteObjectValidationRule() throws Exception {
		ObjectValidationRule objectValidationRule = _addObjectValidationRule(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM, _VALID_DDM_SCRIPT);

		Assert.assertNotNull(
			_objectValidationRuleLocalService.fetchObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId()));

		_objectValidationRuleLocalService.deleteObjectValidationRule(
			objectValidationRule.getObjectValidationRuleId());

		Assert.assertNull(
			_objectValidationRuleLocalService.fetchObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId()));
	}

	@Test
	public void testUpdateObjectValidationRule() throws Exception {
		ObjectValidationRule objectValidationRule = _addObjectValidationRule(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM, _VALID_DDM_SCRIPT);

		long randomId = RandomTestUtil.randomLong();

		AssertUtils.assertFailure(
			NoSuchObjectValidationRuleException.class,
			String.format(
				"No ObjectValidationRule exists with the primary key %s",
				randomId),
			() -> _objectValidationRuleLocalService.updateObjectValidationRule(
				randomId, false, ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
				_VALID_DDM_SCRIPT, Collections.emptyList()));

		ObjectField textObjectField = _objectFieldLocalService.fetchObjectField(
			_objectDefinition.getObjectDefinitionId(), "textObjectField");

		objectValidationRule =
			_objectValidationRuleLocalService.updateObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId(), true,
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap("Field must be an URL"),
				LocalizedMapUtil.getLocalizedMap("URL Validation"),
				ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
				"isURL(textObjectField)",
				Collections.singletonList(
					new ObjectValidationRuleSettingBuilder(
					).name(
						ObjectValidationRuleSettingConstants.
							NAME_OUTPUT_OBJECT_FIELD_ID
					).value(
						String.valueOf(textObjectField.getObjectFieldId())
					).build()));

		_assertObjectValidationRule(
			true, ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			LocalizedMapUtil.getLocalizedMap("Field must be an URL"),
			LocalizedMapUtil.getLocalizedMap("URL Validation"),
			String.valueOf(textObjectField.getObjectFieldId()),
			ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
			"isURL(textObjectField)", objectValidationRule);

		ObjectField dateObjectField = _objectFieldLocalService.fetchObjectField(
			_objectDefinition.getObjectDefinitionId(), "dateObjectField");

		_assertObjectValidationRule(
			false, ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			LocalizedMapUtil.getLocalizedMap("Field must be an URL"),
			LocalizedMapUtil.getLocalizedMap("URL Validation"),
			String.valueOf(dateObjectField.getObjectFieldId()),
			ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
			"isURL(textObjectField)",
			_objectValidationRuleLocalService.updateObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId(), false,
				objectValidationRule.getEngine(),
				objectValidationRule.getErrorLabelMap(),
				objectValidationRule.getNameMap(),
				ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
				objectValidationRule.getScript(),
				Collections.singletonList(
					new ObjectValidationRuleSettingBuilder(
					).name(
						ObjectValidationRuleSettingConstants.
							NAME_OUTPUT_OBJECT_FIELD_ID
					).value(
						String.valueOf(dateObjectField.getObjectFieldId())
					).build())));
	}

	private ObjectValidationRule _addObjectValidationRule(
			String engine, Map<Locale, String> errorLabelMap,
			Map<Locale, String> nameLabelMap, String script)
		throws Exception {

		return _addObjectValidationRule(
			engine, errorLabelMap, nameLabelMap,
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION, script,
			Collections.emptyList());
	}

	private ObjectValidationRule _addObjectValidationRule(
			String engine, Map<Locale, String> errorLabelMap,
			Map<Locale, String> nameLabelMap, String outputType, String script,
			List<ObjectValidationRuleSetting> objectValidationRuleSettings)
		throws Exception {

		return _objectValidationRuleLocalService.addObjectValidationRule(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, engine,
			errorLabelMap, nameLabelMap, outputType, script, false,
			objectValidationRuleSettings);
	}

	private ObjectValidationRule _addObjectValidationRule(
			String engine, String script)
		throws Exception {

		return _addObjectValidationRule(
			engine,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			script);
	}

	private void _assertObjectValidationRule(
		boolean expectedActive, String expectedEngine,
		Map<Locale, String> expectedErrorLabelMap,
		Map<Locale, String> expectedNameLabelMap, String expectedObjectFieldId,
		String expectedOutputType, String expectedScript,
		ObjectValidationRule objectValidationRule) {

		Assert.assertEquals(expectedActive, objectValidationRule.isActive());
		Assert.assertEquals(expectedEngine, objectValidationRule.getEngine());
		Assert.assertEquals(
			expectedErrorLabelMap, objectValidationRule.getErrorLabelMap());
		Assert.assertEquals(
			expectedNameLabelMap, objectValidationRule.getNameMap());
		Assert.assertEquals(
			expectedOutputType, objectValidationRule.getOutputType());
		Assert.assertEquals(expectedScript, objectValidationRule.getScript());

		if (StringUtil.equals(
				objectValidationRule.getOutputType(),
				ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION)) {

			Assert.assertTrue(
				ListUtil.isEmpty(
					objectValidationRule.getObjectValidationRuleSettings()));
		}
		else if (StringUtil.equals(
					objectValidationRule.getOutputType(),
					ObjectValidationRuleConstants.
						OUTPUT_TYPE_PARTIAL_VALIDATION)) {

			Assert.assertTrue(
				ListUtil.isNotEmpty(
					objectValidationRule.getObjectValidationRuleSettings()));

			for (ObjectValidationRuleSetting objectValidationRuleSetting :
					objectValidationRule.getObjectValidationRuleSettings()) {

				if (StringUtil.equals(
						objectValidationRuleSetting.getName(),
						ObjectValidationRuleSettingConstants.
							NAME_OUTPUT_OBJECT_FIELD_ID)) {

					Assert.assertEquals(
						expectedObjectFieldId,
						objectValidationRuleSetting.getValue());
				}
			}
		}
	}

	private static final String _VALID_DDM_SCRIPT =
		"isEmailAddress(textObjectField)";

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

}