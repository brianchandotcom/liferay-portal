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

package com.liferay.dynamic.data.mapping.form.field.type.internal.paragraph;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
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
public class ParagraphDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Before
	@Override
	public void setUp() {
		setUpLanguageUtil();
		setUpPortalUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCreateParagraphDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			ParagraphDDMFormFieldTypeSettings.class);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField labelDDMFormField = ddmFormFieldsMap.get("label");

		Assert.assertNotNull(labelDDMFormField);
		Assert.assertNotNull(labelDDMFormField.getLabel());
		Assert.assertEquals("text", labelDDMFormField.getType());

		Map<String, Object> properties = labelDDMFormField.getProperties();

		Assert.assertTrue(properties.containsKey("placeholder"));

		DDMFormField textDDMFormField = ddmFormFieldsMap.get("text");

		Assert.assertNotNull(textDDMFormField);
		Assert.assertEquals("string", textDDMFormField.getDataType());
		Assert.assertNotNull(textDDMFormField.getLabel());

		properties = textDDMFormField.getProperties();

		Assert.assertTrue(properties.containsKey("placeholder"));

		Assert.assertEquals("rich_text", textDDMFormField.getType());

		DDMFormField validationDDMFormField = ddmFormFieldsMap.get(
			"validation");

		Assert.assertNotNull(validationDDMFormField);
		Assert.assertEquals("string", validationDDMFormField.getDataType());
		Assert.assertEquals("validation", validationDDMFormField.getType());

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 1, ddmFormRules.size());

		DDMFormRule ddmFormRule = ddmFormRules.get(0);

		Assert.assertEquals("TRUE", ddmFormRule.getCondition());

		List<String> actions = ddmFormRule.getActions();

		Assert.assertEquals(actions.toString(), 9, actions.size());

		Assert.assertTrue(
			actions.toString(), actions.contains("setRequired('text', true)"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('dataType', false)"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('indexType', false)"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('predefinedValue', false)"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('repeatable', false)"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('required', false)"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('showLabel', false)"));
		Assert.assertTrue(
			actions.toString(), actions.contains("setVisible('tip', false)"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('validation', false)"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('indexType', false)"));
	}

	@Override
	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = PowerMockito.mock(Language.class);

		languageUtil.setLanguage(language);
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