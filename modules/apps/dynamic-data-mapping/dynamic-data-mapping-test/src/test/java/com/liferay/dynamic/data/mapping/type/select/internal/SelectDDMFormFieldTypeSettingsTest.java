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

package com.liferay.dynamic.data.mapping.type.select.internal;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;

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
public class SelectDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

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
		Assert.assertEquals("radio", dataSourceTypeDDMFormField.getType());

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

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 2, ddmFormRules.size());

		DDMFormRule ddmFormRule0 = ddmFormRules.get(0);

		Assert.assertEquals(
			"not(equals(getValue('ddmDataProviderInstanceId'), ''))",
			ddmFormRule0.getCondition());

		List<String> actions = ddmFormRule0.getActions();

		Assert.assertEquals(actions.toString(), 1, actions.size());

		StringBundler sb = new StringBundler(4);

		sb.append("call('getDataProviderInstanceOutputParameters', concat('");
		sb.append("dataProviderInstanceId=', getValue('ddmDataProvider");
		sb.append("InstanceId')), 'ddmDataProviderInstanceOutput=output");
		sb.append("ParameterNames')");

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
				"setRequired('options', equals(getValue('dataSourceType'), " +
					"\"manual\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('ddmDataProviderInstanceId', equals(getValue(" +
					"'dataSourceType'), \"data-provider\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('ddmDataProviderInstanceOutput', equals(getValue(" +
					"'dataSourceType'), \"data-provider\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('options', equals(getValue('dataSourceType'), " +
					"\"manual\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains(
				"setVisible('predefinedValue', " +
					"equals(getValue('dataSourceType'), \"manual\"))"));
		Assert.assertTrue(
			actions.toString(),
			actions.contains("setVisible('validation', false)"));
	}

}