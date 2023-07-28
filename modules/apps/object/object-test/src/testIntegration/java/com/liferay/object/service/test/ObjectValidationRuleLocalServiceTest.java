/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.object.exception.NoSuchObjectValidationRuleException;
import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.exception.ObjectValidationRuleNameException;
import com.liferay.object.exception.ObjectValidationRuleScriptException;
import com.liferay.object.exception.ObjectValidationRuleSettingNameException;
import com.liferay.object.exception.ObjectValidationRuleSettingValueException;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.service.persistence.ObjectValidationRuleSettingUtil;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
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
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING,
					RandomTestUtil.randomString(), "textField"),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DATE,
					ObjectFieldConstants.DB_TYPE_DATE,
					RandomTestUtil.randomString(), "dateField")));
	}

	@Test
	public void testAddObjectValidationRule() throws Exception {
		_testAddObjectValidationRuleFailure(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			ObjectValidationRuleNameException.class,
			"Name is null for locale " + LocaleUtil.US.getDisplayName(),
			StringPool.BLANK, Collections.emptyList(),
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			_VALID_DDM_SCRIPT);
		_testAddObjectValidationRuleFailure(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			ObjectValidationRuleScriptException.class, "required",
			RandomTestUtil.randomString(), Collections.emptyList(),
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			StringPool.BLANK);
		_testAddObjectValidationRuleFailure(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			ObjectValidationRuleSettingNameException.MissingRequiredName.class,
			String.format(
				"The object validation rule setting \"%s\" is required",
				ObjectValidationRuleSettingConstants.NAME_OBJECT_FIELD_ID),
			RandomTestUtil.randomString(), Collections.emptyList(),
			ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
			_VALID_DDM_SCRIPT);
		_testAddObjectValidationRuleFailure(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			ObjectValidationRuleSettingNameException.NotAllowedName.class,
			String.format(
				"The object validation rule setting \"%s\" is not allowed",
				ObjectValidationRuleSettingConstants.NAME_OBJECT_FIELD_ID),
			RandomTestUtil.randomString(),
			Collections.singletonList(
				_createObjectValidationRuleSetting(
					ObjectValidationRuleSettingConstants.NAME_OBJECT_FIELD_ID,
					RandomTestUtil.randomString())),
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			_VALID_DDM_SCRIPT);

		String value = RandomTestUtil.randomString();

		_testAddObjectValidationRuleFailure(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			ObjectValidationRuleSettingValueException.InvalidValue.class,
			String.format(
				"The value \"%s\" of the object validation rule setting " +
					"\"%s\" is invalid",
				value,
				ObjectValidationRuleSettingConstants.NAME_OBJECT_FIELD_ID),
			RandomTestUtil.randomString(),
			Collections.singletonList(
				_createObjectValidationRuleSetting(
					ObjectValidationRuleSettingConstants.NAME_OBJECT_FIELD_ID,
					value)),
			ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
			_VALID_DDM_SCRIPT);

		_testAddObjectValidationRuleFailure(
			StringPool.BLANK,
			ObjectValidationRuleEngineException.MustNotBeNull.class,
			"Engine is null", RandomTestUtil.randomString(),
			Collections.emptyList(),
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			_VALID_DDM_SCRIPT);
		_testAddObjectValidationRuleFailure(
			"abcdefghijklmnopqrstuvwxyz",
			ObjectValidationRuleEngineException.NoSuchEngine.class,
			"Engine \"abcdefghijklmnopqrstuvwxyz\" does not exist",
			RandomTestUtil.randomString(), Collections.emptyList(),
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			_VALID_DDM_SCRIPT);
		_testAddObjectValidationRuleFailure(
			ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY,
			ObjectValidationRuleScriptException.class, "syntax-error",
			RandomTestUtil.randomString(), Collections.emptyList(),
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			"import;\ninvalidFields = false;");

		_testAddObjectValidationRuleSuccess(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM, _VALID_DDM_SCRIPT);
		_testAddObjectValidationRuleSuccess(
			ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY,
			"import com.liferay.commerce.service.CommerceOrderLocalService;\n" +
				"invalidFields = false;");

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			_objectDefinition.getObjectDefinitionId(), "textField");

		ObjectValidationRule objectValidationRule = _addObjectValidationRule(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
			Collections.singletonList(
				_createObjectValidationRuleSetting(
					ObjectValidationRuleSettingConstants.NAME_OBJECT_FIELD_ID,
					String.valueOf(objectField.getObjectFieldId()))),
			_VALID_DDM_SCRIPT);

		Assert.assertEquals(
			ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
			objectValidationRule.getOutputType());

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
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			Collections.emptyList(), _VALID_DDM_SCRIPT);

		objectValidationRule =
			_objectValidationRuleLocalService.fetchObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId());

		Assert.assertNotNull(objectValidationRule);

		_objectValidationRuleLocalService.deleteObjectValidationRule(
			objectValidationRule.getObjectValidationRuleId());

		objectValidationRule =
			_objectValidationRuleLocalService.fetchObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId());

		Assert.assertNull(objectValidationRule);
	}

	@Test
	public void testUpdateObjectValidationRule() throws Exception {
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

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId(), false);

		ObjectField objectField = objectFields.get(0);

		ObjectValidationRule objectValidationRule = _addObjectValidationRule(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			Collections.emptyList(), _VALID_DDM_SCRIPT);

		objectValidationRule =
			_objectValidationRuleLocalService.updateObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId(), true,
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap("Field must be an URL"),
				LocalizedMapUtil.getLocalizedMap("URL Validation"),
				ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
				"isURL(textField)",
				Collections.singletonList(
					_createObjectValidationRuleSetting(
						ObjectValidationRuleSettingConstants.
							NAME_OBJECT_FIELD_ID,
						String.valueOf(objectField.getObjectFieldId()))));

		Assert.assertTrue(objectValidationRule.isActive());
		Assert.assertEquals(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			objectValidationRule.getEngine());
		Assert.assertEquals(
			ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
			objectValidationRule.getOutputType());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Field must be an URL"),
			objectValidationRule.getErrorLabelMap());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("URL Validation"),
			objectValidationRule.getNameMap());
		Assert.assertEquals(
			"isURL(textField)", objectValidationRule.getScript());
		_assertObjectValidationRuleSettingsValues(
			HashMapBuilder.put(
				ObjectValidationRuleSettingConstants.NAME_OBJECT_FIELD_ID,
				String.valueOf(objectField.getObjectFieldId())
			).build(),
			objectValidationRule.getObjectValidationRuleSettings());

		objectField = objectFields.get(1);

		objectValidationRule =
			_objectValidationRuleLocalService.updateObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId(), false,
				objectValidationRule.getEngine(),
				objectValidationRule.getErrorLabelMap(),
				objectValidationRule.getNameMap(),
				ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
				objectValidationRule.getScript(),
				Collections.singletonList(
					_createObjectValidationRuleSetting(
						ObjectValidationRuleSettingConstants.
							NAME_OBJECT_FIELD_ID,
						String.valueOf(objectField.getObjectFieldId()))));

		Assert.assertFalse(objectValidationRule.isActive());
		_assertObjectValidationRuleSettingsValues(
			HashMapBuilder.put(
				ObjectValidationRuleSettingConstants.NAME_OBJECT_FIELD_ID,
				String.valueOf(objectField.getObjectFieldId())
			).build(),
			objectValidationRule.getObjectValidationRuleSettings());
	}

	private ObjectValidationRule _addObjectValidationRule(
			String engine, String outputType,
			List<ObjectValidationRuleSetting> objectValidationRuleSettings,
			String script)
		throws Exception {

		return _objectValidationRuleLocalService.addObjectValidationRule(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, engine,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			outputType, script, objectValidationRuleSettings);
	}

	private void _assertObjectValidationRuleSettingsValues(
		Map<String, String> expectedObjectValidationRuleSettingsValues,
		List<ObjectValidationRuleSetting> objectValidationRuleSettings) {

		for (ObjectValidationRuleSetting objectValidationRuleSetting :
				objectValidationRuleSettings) {

			if (!expectedObjectValidationRuleSettingsValues.containsKey(
					objectValidationRuleSetting.getName())) {

				continue;
			}

			Assert.assertEquals(
				expectedObjectValidationRuleSettingsValues.get(
					objectValidationRuleSetting.getName()),
				objectValidationRuleSetting.getValue());
		}
	}

	private ObjectValidationRuleSetting _createObjectValidationRuleSetting(
		String name, String value) {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			ObjectValidationRuleSettingUtil.create(0L);

		objectValidationRuleSetting.setName(name);
		objectValidationRuleSetting.setValue(value);

		return objectValidationRuleSetting;
	}

	private void _testAddObjectValidationRuleFailure(
		String engine, Class<?> expectedExceptionClass, String expectedMessage,
		String name,
		List<ObjectValidationRuleSetting> objectValidationRuleSettings,
		String outputType, String script) {

		try {
			_objectValidationRuleLocalService.addObjectValidationRule(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true, engine,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				LocalizedMapUtil.getLocalizedMap(name), outputType, script,
				objectValidationRuleSettings);

			Assert.fail();
		}
		catch (PortalException portalException) {
			Assert.assertTrue(
				expectedExceptionClass.isInstance(portalException));

			String actualMessage = portalException.getMessage();

			if (portalException instanceof
					ObjectValidationRuleScriptException) {

				ObjectValidationRuleScriptException
					objectValidationRuleScriptException =
						(ObjectValidationRuleScriptException)portalException;

				actualMessage =
					objectValidationRuleScriptException.getMessageKey();
			}

			Assert.assertEquals(expectedMessage, actualMessage);
		}
	}

	private ObjectValidationRule _testAddObjectValidationRuleSuccess(
			String engine, String script)
		throws Exception {

		Map<Locale, String> errorLabelMap = LocalizedMapUtil.getLocalizedMap(
			RandomTestUtil.randomString());
		Map<Locale, String> nameLabelMap = LocalizedMapUtil.getLocalizedMap(
			RandomTestUtil.randomString());

		ObjectValidationRule objectValidationRule =
			_objectValidationRuleLocalService.addObjectValidationRule(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true, engine,
				errorLabelMap, nameLabelMap,
				ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
				script, Collections.emptyList());

		Assert.assertTrue(objectValidationRule.isActive());
		Assert.assertEquals(engine, objectValidationRule.getEngine());
		Assert.assertEquals(
			errorLabelMap, objectValidationRule.getErrorLabelMap());
		Assert.assertEquals(nameLabelMap, objectValidationRule.getNameMap());
		Assert.assertEquals(script, objectValidationRule.getScript());

		return objectValidationRule;
	}

	private static final String _VALID_DDM_SCRIPT = "isEmailAddress(textField)";

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

}