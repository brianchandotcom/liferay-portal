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

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
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
public class SelectDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Before
	@Override
	public void setUp() {
		setUpLanguageUtil();
		setUpPortalUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCreateSelectDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			SelectDDMFormFieldTypeSettings.class);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField dataSourceTypeDDMFormField = ddmFormFieldsMap.get(
			"dataSourceType");

		Assert.assertNotNull(dataSourceTypeDDMFormField);
		Assert.assertNotNull(dataSourceTypeDDMFormField.getLabel());
		Assert.assertNotNull(dataSourceTypeDDMFormField.getPredefinedValue());
		Assert.assertEquals("select", dataSourceTypeDDMFormField.getType());

		DDMFormField ddmDataProviderInstanceIdDDMFormField =
			ddmFormFieldsMap.get("ddmDataProviderInstanceId");

		Assert.assertNotNull(ddmDataProviderInstanceIdDDMFormField);
		Assert.assertNotNull(ddmDataProviderInstanceIdDDMFormField.getLabel());
		Assert.assertEquals(
			"select", ddmDataProviderInstanceIdDDMFormField.getType());

		DDMFormField ddmDataProviderInstanceOutputDDMFormField =
			ddmFormFieldsMap.get("ddmDataProviderInstanceOutput");

		Assert.assertNotNull(
			ddmDataProviderInstanceOutputDDMFormField.getLabel());
		Assert.assertEquals(
			"select", ddmDataProviderInstanceOutputDDMFormField.getType());

		DDMFormField multipleDDMFormField = ddmFormFieldsMap.get("multiple");

		Assert.assertNotNull(multipleDDMFormField);
		Assert.assertNotNull(multipleDDMFormField.getLabel());
		Assert.assertEquals(
			"true", multipleDDMFormField.getProperty("showAsSwitcher"));

		DDMFormField optionsDDMFormField = ddmFormFieldsMap.get("options");

		Assert.assertNotNull(optionsDDMFormField);
		Assert.assertEquals("ddm-options", optionsDDMFormField.getDataType());
		Assert.assertNotNull(optionsDDMFormField.getLabel());
		Assert.assertEquals(
			"false", optionsDDMFormField.getProperty("showLabel"));
		Assert.assertEquals("options", optionsDDMFormField.getType());

		DDMFormField indexTypeDDMFormField = ddmFormFieldsMap.get("indexType");

		Assert.assertNotNull(indexTypeDDMFormField);
		Assert.assertNotNull(indexTypeDDMFormField.getLabel());
		Assert.assertEquals("radio", indexTypeDDMFormField.getType());

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 3, ddmFormRules.size());

		DDMFormRule ddmFormRule0 = ddmFormRules.get(0);

		Assert.assertEquals(
			"contains(getValue('dataSourceType'), \"data-provider\")",
			ddmFormRule0.getCondition());

		List<String> actions = ddmFormRule0.getActions();

		Assert.assertEquals(actions.toString(), 1, actions.size());

		StringBundler sb = new StringBundler(3);

		sb.append("call('getDataProviderInstanceOutputParameters', '");
		sb.append("dataProviderInstanceId=ddmDataProviderInstanceId', '");
		sb.append("ddmDataProviderInstanceOutput=outputParameterNames')");

		Assert.assertEquals(sb.toString(), actions.get(0));

		DDMFormRule ddmFormRule1 = ddmFormRules.get(1);

		Assert.assertEquals("TRUE", ddmFormRule1.getCondition());

		actions = ddmFormRule1.getActions();

		Assert.assertEquals(actions.toString(), 10, actions.size());
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setMultiple('predefinedValue', getValue('multiple'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setOptions('predefinedValue', getValue('options'))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setRequired('ddmDataProviderInstanceId', contains(getValue(" +
					"'dataSourceType'), \"data-provider\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setRequired('ddmDataProviderInstanceOutput', contains(" +
					"getValue('dataSourceType'), \"data-provider\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setRequired('options', contains(getValue('dataSourceType'), " +
					"\"manual\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('ddmDataProviderInstanceId', contains(getValue(" +
					"'dataSourceType'), \"data-provider\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('ddmDataProviderInstanceOutput', contains(" +
					"getValue('dataSourceType'), \"data-provider\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('options', contains(getValue('dataSourceType'), " +
					"\"manual\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('predefinedValue', " +
					"contains(getValue('dataSourceType'), \"manual\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('validation', false)"));

		DDMFormRule ddmFormRule3 = ddmFormRules.get(2);

		Assert.assertEquals(
			"not(equals(getValue('dataSourceType'), \"data-provider\"))",
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