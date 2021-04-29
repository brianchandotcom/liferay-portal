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

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
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
public class NumericDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

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
			confirmationErrorMessageDDMFormField.getPredefinedValue());

		DDMFormField confirmationLabelDDMFormField = ddmFormFieldsMap.get(
			"confirmationLabel");

		Assert.assertNotNull(confirmationLabelDDMFormField.getLabel());
		Assert.assertNotNull(
			confirmationLabelDDMFormField.getPredefinedValue());

		DDMFormField dataTypeDDMFormField = ddmFormFieldsMap.get("dataType");

		Assert.assertNotNull(dataTypeDDMFormField);
		Assert.assertNotNull(dataTypeDDMFormField.getLabel());
		Assert.assertEquals("radio", dataTypeDDMFormField.getType());

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

		DDMFormField inputMaskDDMFormField = ddmFormFieldsMap.get("inputMask");

		Assert.assertEquals(
			"true", inputMaskDDMFormField.getProperty("showAsSwitcher"));

		DDMFormField inputMaskFormatDDMFormField = ddmFormFieldsMap.get(
			"inputMaskFormat");

		Assert.assertEquals(
			"string", inputMaskFormatDDMFormField.getDataType());
		Assert.assertEquals(
			"^(?=.*[09])([^1-8]+)$",
			inputMaskFormatDDMFormField.getProperty("regex"));
		Assert.assertEquals("text", inputMaskFormatDDMFormField.getType());
		Assert.assertEquals(true, inputMaskFormatDDMFormField.isRequired());
		Assert.assertNotNull(inputMaskFormatDDMFormField.getLabel());
		Assert.assertNotNull(
			inputMaskFormatDDMFormField.getProperty("placeholder"));
		Assert.assertNotNull(
			inputMaskFormatDDMFormField.getProperty("tooltip"));
		Assert.assertNotNull(inputMaskFormatDDMFormField.getTip());

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

		Assert.assertEquals(ddmFormRules.toString(), 2, ddmFormRules.size());

		DDMFormRule ddmFormRule0 = ddmFormRules.get(0);

		Assert.assertEquals(
			"equals(getValue('dataType'), 'double')",
			ddmFormRule0.getCondition());

		List<String> actions = ddmFormRule0.getActions();

		Assert.assertEquals(actions.toString(), 2, actions.size());
		Assert.assertEquals("setValue('inputMask', FALSE)", actions.get(0));
		Assert.assertEquals("setVisible('inputMask', FALSE)", actions.get(1));

		DDMFormRule ddmFormRule1 = ddmFormRules.get(1);

		Assert.assertEquals("TRUE", ddmFormRule1.getCondition());

		actions = ddmFormRule1.getActions();

		Assert.assertEquals(actions.toString(), 8, actions.size());

		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setDataType('predefinedValue', getValue('dataType'))"));
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
				"setVisible('direction', getValue('requireConfirmation'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('inputMaskFormat', getValue('inputMask'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('tooltip', false)"));
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