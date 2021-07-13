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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.test.util.DDMFormLayoutTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
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
 * @author Rafael Praxedes
 */
@PrepareForTest({PortalClassLoaderUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public class CheckboxMultipleDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Before
	@Override
	public void setUp() {
		setUpLanguageUtil();
		setUpPortalUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCreateCheckboxDDMFormFieldTypeSettingsDDMFormLayout() {
		assertDDMFormLayout(
			DDMFormLayoutFactory.create(
				CheckboxMultipleDDMFormFieldTypeSettings.class),
			DDMFormLayoutTestUtil.createDDMFormLayout(
				DDMFormLayout.TABBED_MODE,
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"label", "tip", "required", "requiredErrorMessage",
					"showAsSwitcher", "options"),
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"name", "fieldReference", "visibilityExpression",
					"predefinedValue", "objectFieldName", "fieldNamespace",
					"indexType", "labelAtStructureLevel", "localizable",
					"nativeField", "readOnly", "dataType", "type", "showLabel",
					"repeatable", "inline")));
	}

	@Test
	public void testCreateCheckboxMultipleDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			CheckboxMultipleDDMFormFieldTypeSettings.class);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField inlineDDMFormField = ddmFormFieldsMap.get("inline");

		Assert.assertNotNull(inlineDDMFormField);
		Assert.assertNotNull(inlineDDMFormField.getLabel());
		Assert.assertEquals(
			"true", inlineDDMFormField.getProperty("showAsSwitcher"));

		DDMFormField optionsDDMFormField = ddmFormFieldsMap.get("options");

		Assert.assertNotNull(optionsDDMFormField);
		Assert.assertEquals("ddm-options", optionsDDMFormField.getDataType());
		Assert.assertNotNull(optionsDDMFormField.getLabel());
		Assert.assertEquals("options", optionsDDMFormField.getType());

		DDMFormField requiredErrorMessage = ddmFormFieldsMap.get(
			"requiredErrorMessage");

		Assert.assertNotNull(requiredErrorMessage);

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 1, ddmFormRules.size());

		DDMFormRule ddmFormRule = ddmFormRules.get(0);

		Assert.assertEquals("TRUE", ddmFormRule.getCondition());

		List<String> actions = ddmFormRule.getActions();

		Assert.assertEquals(actions.toString(), 3, actions.size());

		Assert.assertEquals(
			"setOptions('predefinedValue', getValue('options'))",
			actions.get(0));
		Assert.assertEquals("setRequired('options', true)", actions.get(1));
		Assert.assertEquals(
			"setVisible('requiredErrorMessage', getValue('required'))",
			actions.get(2));
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