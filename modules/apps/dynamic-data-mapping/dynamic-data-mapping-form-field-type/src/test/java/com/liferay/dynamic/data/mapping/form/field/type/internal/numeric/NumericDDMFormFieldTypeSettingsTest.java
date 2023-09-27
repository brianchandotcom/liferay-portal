/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormLayoutTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class NumericDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() {
		setUpLanguageUtil();
		setUpPortalUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCreateNumericDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			NumericDDMFormFieldTypeSettings.class);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField confirmationErrorMessageDDMFormField =
			ddmFormFieldsMap.get("confirmationErrorMessage");

		Assert.assertNotNull(confirmationErrorMessageDDMFormField.getLabel());
		Assert.assertNotNull(
			confirmationErrorMessageDDMFormField.getProperty("initialValue"));

		DDMFormField confirmationLabelDDMFormField = ddmFormFieldsMap.get(
			"confirmationLabel");

		Assert.assertNotNull(confirmationLabelDDMFormField.getLabel());
		Assert.assertNotNull(
			confirmationLabelDDMFormField.getProperty("initialValue"));

		DDMFormField dataTypeDDMFormField = ddmFormFieldsMap.get("dataType");

		Assert.assertNotNull(dataTypeDDMFormField);
		Assert.assertNotNull(dataTypeDDMFormField.getLabel());
		Assert.assertEquals("radio", dataTypeDDMFormField.getType());

		DDMFormField requiredErrorMessage = ddmFormFieldsMap.get(
			"requiredErrorMessage");

		Assert.assertNotNull(requiredErrorMessage);

		LocalizedValue predefinedValue =
			dataTypeDDMFormField.getPredefinedValue();

		Assert.assertEquals(
			"integer",
			predefinedValue.getString(predefinedValue.getDefaultLocale()));

		DDMFormField directionDDMFormField = ddmFormFieldsMap.get("direction");

		Assert.assertEquals(
			"false", directionDDMFormField.getProperty("showEmptyOption"));

		LocalizedValue directionPredefinedValue =
			directionDDMFormField.getPredefinedValue();

		Assert.assertEquals(
			"[\"vertical\"]",
			directionPredefinedValue.getString(
				directionPredefinedValue.getDefaultLocale()));

		DDMFormField hideFieldDDMFormField = ddmFormFieldsMap.get("hideField");

		Assert.assertNotNull(hideFieldDDMFormField);
		Assert.assertNotNull(hideFieldDDMFormField.getLabel());
		Assert.assertEquals(
			"true", hideFieldDDMFormField.getProperty("showAsSwitcher"));

		DDMFormField inputMaskDDMFormField = ddmFormFieldsMap.get("inputMask");

		Assert.assertEquals(
			"true", inputMaskDDMFormField.getProperty("showAsSwitcher"));

		DDMFormField inputMaskFormatDDMFormField = ddmFormFieldsMap.get(
			"inputMaskFormat");

		Assert.assertEquals(
			"string", inputMaskFormatDDMFormField.getDataType());

		DDMFormFieldValidation ddmFormFieldValidation =
			inputMaskFormatDDMFormField.getDDMFormFieldValidation();

		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			ddmFormFieldValidation.getDDMFormFieldValidationExpression();

		Assert.assertEquals(
			"match(inputMaskFormat, '^$|^(?=.*[09])([^1-8]+)$')",
			ddmFormFieldValidationExpression.getValue());

		Assert.assertEquals("text", inputMaskFormatDDMFormField.getType());
		Assert.assertTrue(inputMaskFormatDDMFormField.isRequired());
		Assert.assertNotNull(inputMaskFormatDDMFormField.getLabel());
		Assert.assertNotNull(
			inputMaskFormatDDMFormField.getProperty("invalidCharacters"));
		Assert.assertNotNull(
			inputMaskFormatDDMFormField.getProperty("placeholder"));
		Assert.assertNotNull(
			inputMaskFormatDDMFormField.getProperty("tooltip"));
		Assert.assertNotNull(inputMaskFormatDDMFormField.getTip());

		DDMFormField numericInputMaskDDMFormField = ddmFormFieldsMap.get(
			"numericInputMask");

		Assert.assertNotNull(numericInputMaskDDMFormField);
		Assert.assertNotNull(numericInputMaskDDMFormField.getPredefinedValue());

		DDMFormField placeholderDDMFormField = ddmFormFieldsMap.get(
			"placeholder");

		Assert.assertNotNull(placeholderDDMFormField);
		Assert.assertEquals("string", placeholderDDMFormField.getDataType());
		Assert.assertEquals("text", placeholderDDMFormField.getType());

		DDMFormField requireConfirmationDDMFormField = ddmFormFieldsMap.get(
			"requireConfirmation");

		Assert.assertEquals(
			"true",
			requireConfirmationDDMFormField.getProperty("showAsSwitcher"));

		DDMFormField tooltipDDMFormField = ddmFormFieldsMap.get("tooltip");

		Assert.assertNotNull(tooltipDDMFormField);

		DDMFormField validationDDMFormField = ddmFormFieldsMap.get(
			"validation");

		Assert.assertNotNull(validationDDMFormField);
		Assert.assertEquals("numeric", validationDDMFormField.getDataType());

		DDMFormField indexTypeDDMFormField = ddmFormFieldsMap.get("indexType");

		Assert.assertNotNull(indexTypeDDMFormField);
		Assert.assertNotNull(indexTypeDDMFormField.getLabel());
		Assert.assertEquals("radio", indexTypeDDMFormField.getType());

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 4, ddmFormRules.size());

		DDMFormRule ddmFormRule0 = ddmFormRules.get(0);

		Assert.assertEquals(
			"equals(getValue('hideField'), FALSE)",
			ddmFormRule0.getCondition());

		List<String> actions = ddmFormRule0.getActions();

		Assert.assertEquals(actions.toString(), 6, actions.size());
		Assert.assertEquals("setVisible('inputMask', TRUE)", actions.get(0));
		Assert.assertEquals("setVisible('repeatable', TRUE)", actions.get(1));
		Assert.assertEquals(
			"setVisible('requireConfirmation', TRUE)", actions.get(2));
		Assert.assertEquals("setVisible('required', TRUE)", actions.get(3));
		Assert.assertEquals("setVisible('showLabel', TRUE)", actions.get(4));
		Assert.assertEquals("setVisible('validation', TRUE)", actions.get(5));

		DDMFormRule ddmFormRule1 = ddmFormRules.get(1);

		Assert.assertEquals(
			"equals(getValue('hideField'), TRUE)", ddmFormRule1.getCondition());

		actions = ddmFormRule1.getActions();

		Assert.assertEquals(actions.toString(), 11, actions.size());
		Assert.assertEquals("setValue('inputMask', FALSE)", actions.get(0));
		Assert.assertEquals("setValue('repeatable', FALSE)", actions.get(1));
		Assert.assertEquals(
			"setValue('requireConfirmation', FALSE)", actions.get(2));
		Assert.assertEquals("setValue('required', FALSE)", actions.get(3));
		Assert.assertEquals("setValue('showLabel', TRUE)", actions.get(4));
		Assert.assertEquals("setVisible('inputMask', FALSE)", actions.get(5));
		Assert.assertEquals("setVisible('repeatable', FALSE)", actions.get(6));
		Assert.assertEquals(
			"setVisible('requireConfirmation', FALSE)", actions.get(7));
		Assert.assertEquals("setVisible('required', FALSE)", actions.get(8));
		Assert.assertEquals("setVisible('showLabel', FALSE)", actions.get(9));
		Assert.assertEquals("setVisible('validation', FALSE)", actions.get(10));

		DDMFormRule ddmFormRule2 = ddmFormRules.get(2);

		Assert.assertEquals(
			"hasObjectField(getValue('objectFieldName'))",
			ddmFormRule2.getCondition());

		actions = ddmFormRule2.getActions();

		Assert.assertEquals(actions.toString(), 1, actions.size());
		Assert.assertEquals(
			"setValue('required', isRequiredObjectField(getValue(" +
				"'objectFieldName')))",
			actions.get(0));

		DDMFormRule ddmFormRule3 = ddmFormRules.get(3);

		Assert.assertEquals("TRUE", ddmFormRule3.getCondition());

		actions = ddmFormRule3.getActions();

		Assert.assertEquals(actions.toString(), 18, actions.size());
		Assert.assertEquals(
			"setDataType('predefinedValue', getValue('dataType'))",
			actions.get(0));
		Assert.assertEquals(
			"setEnabled('required', not(hasObjectField(" +
				"getValue('objectFieldName'))))",
			actions.get(1));
		Assert.assertEquals(
			"setPropertyValue('predefinedValue', 'inputMask', " +
				"getValue('inputMask'))",
			actions.get(2));
		Assert.assertEquals(
			"setPropertyValue('predefinedValue', 'inputMaskFormat', " +
				"getLocalizedValue('inputMaskFormat'))",
			actions.get(3));
		Assert.assertEquals(
			"setPropertyValue('predefinedValue', 'numericInputMask', " +
				"getLocalizedValue('numericInputMask'))",
			actions.get(4));
		Assert.assertEquals(
			"setPropertyValue('validation', 'inputMask', " +
				"getValue('inputMask'))",
			actions.get(5));
		Assert.assertEquals(
			"setPropertyValue('validation', 'inputMaskFormat', " +
				"getLocalizedValue('inputMaskFormat'))",
			actions.get(6));
		Assert.assertEquals(
			"setPropertyValue('validation', 'numericInputMask', " +
				"getLocalizedValue('numericInputMask'))",
			actions.get(7));
		Assert.assertEquals(
			"setValidationDataType('validation', getValue('dataType'))",
			actions.get(8));
		Assert.assertEquals(
			"setValidationFieldName('validation', getValue('name'))",
			actions.get(9));
		Assert.assertEquals(
			"setVisible('characterOptions', equals(getValue('dataType'), " +
				"'integer') and equals(getValue('inputMask'), TRUE))",
			actions.get(10));
		Assert.assertEquals(
			"setVisible('confirmationErrorMessage', getValue(" +
				"'requireConfirmation'))",
			actions.get(11));
		Assert.assertEquals(
			"setVisible('confirmationLabel', getValue('requireConfirmation'))",
			actions.get(12));
		Assert.assertEquals(
			"setVisible('direction', getValue('requireConfirmation'))",
			actions.get(13));
		Assert.assertEquals(
			"setVisible('inputMaskFormat', equals(getValue('dataType'), " +
				"'integer') and equals(getValue('inputMask'), TRUE))",
			actions.get(14));
		Assert.assertEquals(
			"setVisible('numericInputMask', equals(getValue('dataType'), " +
				"'double') and equals(getValue('inputMask'), TRUE))",
			actions.get(15));
		Assert.assertEquals(
			"setVisible('requiredErrorMessage', getValue('required'))",
			actions.get(16));
		Assert.assertEquals("setVisible('tooltip', false)", actions.get(17));
	}

	@Test
	public void testCreateNumericDDMFormFieldTypeSettingsDDMFormLayout() {
		assertDDMFormLayout(
			DDMFormLayoutFactory.create(NumericDDMFormFieldTypeSettings.class),
			DDMFormLayoutTestUtil.createDDMFormLayout(
				DDMFormLayout.TABBED_MODE,
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"label", "placeholder", "tip", "dataType", "required",
					"requiredErrorMessage"),
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"name", "fieldReference", "predefinedValue",
					"objectFieldName", "visibilityExpression", "fieldNamespace",
					"indexType", "labelAtStructureLevel", "localizable",
					"nativeField", "readOnly", "type", "hideField", "showLabel",
					"repeatable", "requireConfirmation", "direction",
					"confirmationLabel", "confirmationErrorMessage",
					"validation", "tooltip", "inputMask", "inputMaskFormat",
					"characterOptions", "numericInputMask")));
	}

	@Override
	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

}