/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.test.util.DDMFormLayoutTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class SelectDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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

		DDMFormField requiredErrorMessage = ddmFormFieldsMap.get(
			"requiredErrorMessage");

		Assert.assertNotNull(requiredErrorMessage);

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 4, ddmFormRules.size());

		DDMFormRule ddmFormRule0 = ddmFormRules.get(0);

		Assert.assertEquals(
			"contains(getValue('dataSourceType'), \"data-provider\")",
			ddmFormRule0.getCondition());

		List<String> actions = ddmFormRule0.getActions();

		Assert.assertEquals(actions.toString(), 1, actions.size());

		Assert.assertEquals(
			StringBundler.concat(
				"call('getDataProviderInstanceOutputParameters', '",
				"dataProviderInstanceId=ddmDataProviderInstanceId', '",
				"ddmDataProviderInstanceOutput=outputParameterNames')"),
			actions.get(0));

		DDMFormRule ddmFormRule1 = ddmFormRules.get(1);

		Assert.assertEquals("TRUE", ddmFormRule1.getCondition());

		actions = ddmFormRule1.getActions();

		Assert.assertEquals(actions.toString(), 13, actions.size());
		Assert.assertEquals(
			"setEnabled('required', not(hasObjectField(getValue(" +
				"'objectFieldName'))))",
			actions.get(0));
		Assert.assertEquals(
			"setMultiple('predefinedValue', getValue('multiple'))",
			actions.get(1));
		Assert.assertEquals(
			"setOptions('predefinedValue', getValue('options'))",
			actions.get(2));
		Assert.assertEquals(
			"setRequired('ddmDataProviderInstanceId', contains(getValue(" +
				"'dataSourceType'), \"data-provider\"))",
			actions.get(3));
		Assert.assertEquals(
			"setRequired('ddmDataProviderInstanceOutput', contains(" +
				"getValue('dataSourceType'), \"data-provider\"))",
			actions.get(4));
		Assert.assertEquals(
			"setRequired('options', contains(getValue('dataSourceType'), " +
				"\"manual\") OR isEmpty(getValue('dataSourceType')))",
			actions.get(5));
		Assert.assertEquals(
			"setVisible('dataSourceType', not(hasObjectField(getValue(" +
				"'objectFieldName'))))",
			actions.get(6));
		Assert.assertEquals(
			"setVisible('ddmDataProviderInstanceId', contains(getValue(" +
				"'dataSourceType'), \"data-provider\"))",
			actions.get(7));
		Assert.assertEquals(
			"setVisible('ddmDataProviderInstanceOutput', contains(" +
				"getValue('dataSourceType'), \"data-provider\"))",
			actions.get(8));
		Assert.assertEquals(
			"setVisible('multiple', not(hasObjectField(getValue('" +
				"objectFieldName'))))",
			actions.get(9));
		Assert.assertEquals(
			"setVisible('options', (contains(getValue('dataSourceType'), " +
				"\"manual\") OR isEmpty(getValue('dataSourceType'))) AND " +
					"not(hasObjectField(getValue('objectFieldName'))))",
			actions.get(10));
		Assert.assertEquals(
			"setVisible('predefinedValue', " +
				"contains(getValue('dataSourceType'), \"manual\"))",
			actions.get(11));
		Assert.assertEquals(
			"setVisible('requiredErrorMessage', getValue('required'))",
			actions.get(12));

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

		DDMFormRule ddmFormRule4 = ddmFormRules.get(3);

		Assert.assertEquals(
			"hasObjectField(getValue('objectFieldName'))",
			ddmFormRule4.getCondition());

		actions = ddmFormRule4.getActions();

		Assert.assertEquals(actions.toString(), 2, actions.size());
		Assert.assertEquals(
			"setValue('options', getListTypeEntries(getValue('" +
				"objectFieldName')))",
			actions.get(0));
		Assert.assertEquals(
			"setValue('required', isRequiredObjectField(getValue('" +
				"objectFieldName')))",
			actions.get(1));
	}

	@Test
	public void testCreateSelectDDMFormFieldTypeSettingsDDMFormLayout() {
		assertDDMFormLayout(
			DDMFormLayoutFactory.create(SelectDDMFormFieldTypeSettings.class),
			DDMFormLayoutTestUtil.createDDMFormLayout(
				DDMFormLayout.TABBED_MODE,
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"label", "tip", "required", "requiredErrorMessage",
					"dataSourceType", "options", "ddmDataProviderInstanceId",
					"ddmDataProviderInstanceOutput"),
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"name", "fieldReference", "predefinedValue",
					"objectFieldName", "visibilityExpression", "fieldNamespace",
					"indexType", "labelAtStructureLevel", "localizable",
					"nativeField", "readOnly", "dataType", "type", "showLabel",
					"repeatable", "multiple", "alphabeticalOrder")));
	}

	@Override
	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

}