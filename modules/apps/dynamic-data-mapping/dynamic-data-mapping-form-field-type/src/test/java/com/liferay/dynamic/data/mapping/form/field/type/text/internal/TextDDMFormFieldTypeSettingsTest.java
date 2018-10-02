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

package com.liferay.dynamic.data.mapping.form.field.type.text.internal;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.petra.string.StringBundler;
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
public class TextDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Test
	public void testCreateTextDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			TextDDMFormFieldTypeSettings.class);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

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

		DDMFormField displayStyleDDMFormField = ddmFormFieldsMap.get(
			"displayStyle");

		Assert.assertNotNull(displayStyleDDMFormField);
		Assert.assertNotNull(displayStyleDDMFormField.getLabel());
		Assert.assertEquals(
			"true", displayStyleDDMFormField.getProperty("inline"));
		Assert.assertEquals("radio", displayStyleDDMFormField.getType());

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

		DDMFormField tooltipDDMFormField = ddmFormFieldsMap.get("tooltip");

		Assert.assertNotNull(tooltipDDMFormField);
		Assert.assertEquals(
			"FALSE", tooltipDDMFormField.getVisibilityExpression());

		DDMFormField validationDDMFormField = ddmFormFieldsMap.get(
			"validation");

		Assert.assertNotNull(validationDDMFormField);
		Assert.assertEquals("string", validationDDMFormField.getDataType());

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 2, ddmFormRules.size());

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

		Assert.assertEquals("TRUE", ddmFormRule1.getCondition());

		actions = ddmFormRule1.getActions();

		Assert.assertEquals(actions.toString(), 6, actions.size());
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
				"setVisible('options', equals(getValue('dataSourceType'), " +
					"\"manual\") and getValue('autocomplete'))"));
	}

}