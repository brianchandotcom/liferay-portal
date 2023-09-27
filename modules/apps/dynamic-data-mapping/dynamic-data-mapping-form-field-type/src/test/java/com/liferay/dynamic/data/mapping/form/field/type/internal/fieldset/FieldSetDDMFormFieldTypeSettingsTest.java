/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.fieldset;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 */
public class FieldSetDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateSelectDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			FieldSetDDMFormFieldTypeSettings.class);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField collapsibleDDMFormField = ddmFormFieldsMap.get(
			"collapsible");

		Assert.assertNotNull(collapsibleDDMFormField);
		Assert.assertEquals(
			"true", collapsibleDDMFormField.getProperty("showAsSwitcher"));
		Assert.assertEquals("checkbox", collapsibleDDMFormField.getType());

		DDMFormField ddmStructureIdDDMFormField = ddmFormFieldsMap.get(
			"ddmStructureId");

		Assert.assertNotNull(ddmStructureIdDDMFormField);
		Assert.assertEquals(
			"numeric", ddmStructureIdDDMFormField.getDataType());

		DDMFormField ddmStructureLayoutIdDDMFormField = ddmFormFieldsMap.get(
			"ddmStructureLayoutId");

		Assert.assertNotNull(ddmStructureLayoutIdDDMFormField);
		Assert.assertEquals(
			"numeric", ddmStructureLayoutIdDDMFormField.getDataType());

		DDMFormField rowsDDMFormField = ddmFormFieldsMap.get("rows");

		Assert.assertNotNull(rowsDDMFormField);
		Assert.assertEquals("json", rowsDDMFormField.getDataType());
		Assert.assertEquals("text", rowsDDMFormField.getType());

		DDMFormField upgradedStructureDDMFormField = ddmFormFieldsMap.get(
			"upgradedStructure");

		Assert.assertNotNull(upgradedStructureDDMFormField);
		Assert.assertNotNull(
			upgradedStructureDDMFormField.getPredefinedValue());

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 1, ddmFormRules.size());

		DDMFormRule ddmFormRule = ddmFormRules.get(0);

		Assert.assertEquals("TRUE", ddmFormRule.getCondition());

		List<String> actions = ddmFormRule.getActions();

		Assert.assertEquals(actions.toString(), 5, actions.size());
		Assert.assertEquals(
			"setVisible('ddmStructureId', FALSE)", actions.get(0));
		Assert.assertEquals(
			"setVisible('ddmStructureLayoutId', FALSE)", actions.get(1));
		Assert.assertEquals("setVisible('name', FALSE)", actions.get(2));
		Assert.assertEquals("setVisible('rows', FALSE)", actions.get(3));
		Assert.assertEquals(
			"setVisible('upgradedStructure', FALSE)", actions.get(4));
	}

}