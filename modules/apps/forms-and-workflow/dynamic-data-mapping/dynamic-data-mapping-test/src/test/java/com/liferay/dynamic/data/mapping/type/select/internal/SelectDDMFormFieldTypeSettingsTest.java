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
import com.liferay.dynamic.data.mapping.type.BaseDDMFormFieldTypeSettingsTest;
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
public class SelectDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTest {

	@Test
	public void testCreateSelectDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			SelectDDMFormFieldTypeSettings.class);

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(1, ddmFormRules.size());

		DDMFormRule ddmFormRule = ddmFormRules.get(0);

		Assert.assertEquals("TRUE", ddmFormRule.getCondition());

		List<String> ddmFormRuleActions = ddmFormRule.getActions();

		Assert.assertArrayEquals(
			new String[] {
				"set(fieldAt(\"ddmDataProviderInstanceId\", 0), \"visible\", " +
					"equals(get(fieldAt(\"dataSourceType\", 0), \"value\"), " +
						"\"data-provider\"))",
				"set(fieldAt(\"options\", 0), \"visible\", " +
					"equals(get(fieldAt(\"dataSourceType\", 0), \"value\"), " +
						"\"manual\"))",
				"set(fieldAt(\"validation\", 0), \"visible\", false)"
			},
			ddmFormRuleActions.toArray());

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
		Assert.assertTrue(optionsDDMFormField.isRequired());
		Assert.assertEquals("options", optionsDDMFormField.getType());
	}

}