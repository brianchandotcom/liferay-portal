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

package com.liferay.dynamic.data.mapping.form.field.type.internal.text;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest({PortalClassLoaderUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public class TextDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Before
	@Override
	public void setUp() {
		setUpLanguageUtil();
		setUpPortalUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCreateTextDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			TextDDMFormFieldTypeSettings.class);

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

		DDMFormField dataSourceTypeDDMFormField = ddmFormFieldsMap.get(
			"dataSourceType");

		Assert.assertNotNull(dataSourceTypeDDMFormField);
		Assert.assertNotNull(dataSourceTypeDDMFormField.getLabel());
		Assert.assertEquals(
			"false", dataSourceTypeDDMFormField.getProperty("showLabel"));
		Assert.assertEquals("radio", dataSourceTypeDDMFormField.getType());
		Assert.assertNotNull(dataSourceTypeDDMFormField.getPredefinedValue());

		DDMFormField ddmDataProviderInstanceIdDDMFormField =
			ddmFormFieldsMap.get("ddmDataProviderInstanceId");

		Assert.assertNotNull(ddmDataProviderInstanceIdDDMFormField.getLabel());
		Assert.assertEquals(
			"select", ddmDataProviderInstanceIdDDMFormField.getType());

		DDMFormField ddmDataProviderInstanceOutputDDMFormField =
			ddmFormFieldsMap.get("ddmDataProviderInstanceOutput");

		Assert.assertNotNull(
			ddmDataProviderInstanceOutputDDMFormField.getLabel());
		Assert.assertEquals(
			"select", ddmDataProviderInstanceOutputDDMFormField.getType());

		DDMFormField directionDDMFormField = ddmFormFieldsMap.get("direction");

		Assert.assertEquals(
			"false", directionDDMFormField.getProperty("showEmptyOption"));

		LocalizedValue directionPredefinedValue =
			directionDDMFormField.getPredefinedValue();

		Assert.assertEquals(
			"[\"vertical\"]",
			directionPredefinedValue.getString(
				directionPredefinedValue.getDefaultLocale()));

		DDMFormField displayStyleDDMFormField = ddmFormFieldsMap.get(
			"displayStyle");

		Assert.assertNotNull(displayStyleDDMFormField);
		Assert.assertNotNull(displayStyleDDMFormField.getLabel());
		Assert.assertEquals("radio", displayStyleDDMFormField.getType());

		DDMFormFieldOptions displayStyleDDMFormFieldOptions =
			displayStyleDDMFormField.getDDMFormFieldOptions();

		Set<String> displayStyleDDMFormFieldOptionsValue =
			displayStyleDDMFormFieldOptions.getOptionsValues();

		Assert.assertTrue(
			displayStyleDDMFormFieldOptionsValue.contains("multiline"));
		Assert.assertTrue(
			displayStyleDDMFormFieldOptionsValue.contains("singleline"));

		DDMFormField indexTypeDDMFormField = ddmFormFieldsMap.get("indexType");

		DDMFormFieldOptions indexTypeDDMFormFieldOptions =
			indexTypeDDMFormField.getDDMFormFieldOptions();

		Set<String> indexTypeDDMFormFieldOptionsValue =
			indexTypeDDMFormFieldOptions.getOptionsValues();

		Assert.assertNotNull(indexTypeDDMFormField);
		Assert.assertNotNull(indexTypeDDMFormField.getLabel());
		Assert.assertEquals("radio", indexTypeDDMFormField.getType());
		Assert.assertTrue(indexTypeDDMFormFieldOptionsValue.contains("text"));

		DDMFormField optionsDDMFormField = ddmFormFieldsMap.get("options");

		Assert.assertEquals("ddm-options", optionsDDMFormField.getDataType());
		Assert.assertNotNull(optionsDDMFormField.getLabel());
		Assert.assertEquals(false, optionsDDMFormField.isRequired());
		Assert.assertEquals("options", optionsDDMFormField.getType());
		Assert.assertEquals(
			"false", optionsDDMFormField.getProperty("showLabel"));
		Assert.assertEquals(
			"true", optionsDDMFormField.getProperty("allowEmptyOptions"));

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
		Assert.assertEquals(
			"FALSE", tooltipDDMFormField.getVisibilityExpression());

		DDMFormField validationDDMFormField = ddmFormFieldsMap.get(
			"validation");

		Assert.assertNotNull(validationDDMFormField);
		Assert.assertEquals("string", validationDDMFormField.getDataType());

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 4, ddmFormRules.size());

		DDMFormRule ddmFormRule0 = ddmFormRules.get(0);

		Assert.assertEquals(
			"not(equals(getValue('ddmDataProviderInstanceId'), ''))",
			ddmFormRule0.getCondition());

		List<String> actions = ddmFormRule0.getActions();

		Assert.assertEquals(actions.toString(), 1, actions.size());

		StringBundler sb = new StringBundler(3);

		sb.append("call('getDataProviderInstanceOutputParameters', '");
		sb.append("dataProviderInstanceId=ddmDataProviderInstanceId', '");
		sb.append("ddmDataProviderInstanceOutput=outputParameterNames')");

		Assert.assertEquals(sb.toString(), actions.get(0));

		DDMFormRule ddmFormRule1 = ddmFormRules.get(1);

		Assert.assertEquals(
			"not(equals(getValue('displayStyle'), 'singleline'))",
			ddmFormRule1.getCondition());

		actions = ddmFormRule1.getActions();

		Assert.assertEquals(actions.toString(), 4, actions.size());
		Assert.assertEquals("setValue('autocomplete', FALSE)", actions.get(0));
		Assert.assertEquals(
			"setValue('requireConfirmation', FALSE)", actions.get(1));
		Assert.assertEquals(
			"setVisible('autocomplete', FALSE)", actions.get(2));
		Assert.assertEquals(
			"setVisible('requireConfirmation', FALSE)", actions.get(3));

		DDMFormRule ddmFormRule2 = ddmFormRules.get(2);

		Assert.assertEquals("TRUE", ddmFormRule2.getCondition());

		actions = ddmFormRule2.getActions();

		Assert.assertEquals(actions.toString(), 11, actions.size());
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setRequired('ddmDataProviderInstanceId', equals(getValue(" +
					"'dataSourceType'), \"data-provider\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setRequired('ddmDataProviderInstanceOutput', equals(" +
					"getValue('dataSourceType'), \"data-provider\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setValidationDataType('validation', getValue('dataType'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setValidationFieldName('validation', getValue('name'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('confirmationErrorMessage', getValue(" +
					"'requireConfirmation'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('confirmationLabel', getValue(" +
					"'requireConfirmation'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('dataSourceType', getValue('autocomplete'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('ddmDataProviderInstanceId', equals(getValue(" +
					"'dataSourceType'), \"data-provider\") and getValue(" +
						"'autocomplete'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('ddmDataProviderInstanceOutput', equals(getValue(" +
					"'dataSourceType'), \"data-provider\") and getValue(" +
						"'autocomplete'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('direction', getValue('requireConfirmation'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('options', contains(getValue('dataSourceType'), " +
					"\"manual\") and getValue('autocomplete'))"));

		DDMFormRule ddmFormRule3 = ddmFormRules.get(3);

		Assert.assertEquals(
			"not(equals(getValue('dataSourceType'), \"data-provider\")) or " +
				"not(getValue('autocomplete'))",
			ddmFormRule3.getCondition());

		actions = ddmFormRule3.getActions();

		Assert.assertEquals(actions.toString(), 2, actions.size());
		Assert.assertEquals(
			"setValue('ddmDataProviderInstanceId', '')", actions.get(0));
		Assert.assertEquals(
			"setValue('ddmDataProviderInstanceOutput', '')", actions.get(1));
	}

	@Override
	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(PowerMockito.mock(Language.class));
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		ResourceBundle resourceBundle = mock(ResourceBundle.class);

		when(
			portal.getResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
	}

	@Override
	protected void setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		PowerMockito.when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

}