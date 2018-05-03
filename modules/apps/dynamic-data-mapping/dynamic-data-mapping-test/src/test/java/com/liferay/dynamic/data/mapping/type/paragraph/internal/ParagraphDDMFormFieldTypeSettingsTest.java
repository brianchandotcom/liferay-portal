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

package com.liferay.dynamic.data.mapping.type.paragraph.internal;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest({PortalClassLoaderUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public class ParagraphDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Test
	public void testCreateParagraphDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			ParagraphDDMFormFieldTypeSettings.class);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField labelDDMFormField = ddmFormFieldsMap.get("label");

		Assert.assertNotNull(labelDDMFormField);
		Assert.assertEquals("key_value", labelDDMFormField.getType());
		Assert.assertNotNull(labelDDMFormField.getLabel());

		Map<String, Object> properties = labelDDMFormField.getProperties();

		Assert.assertTrue(properties.containsKey("placeholder"));

		DDMFormField textDDMFormField = ddmFormFieldsMap.get("text");

		Assert.assertNotNull(textDDMFormField);
		Assert.assertEquals("string", textDDMFormField.getDataType());
		Assert.assertNotNull(textDDMFormField.getLabel());

		properties = textDDMFormField.getProperties();

		Assert.assertTrue(properties.containsKey("placeholder"));

		Assert.assertEquals("editor", textDDMFormField.getType());

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

		Assert.assertEquals(actions.toString(), 8, actions.size());

		Assert.assertTrue(
			actions.toString(), actions.contains("setRequired('text', true)"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('dataType', false)"));
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
	}

}