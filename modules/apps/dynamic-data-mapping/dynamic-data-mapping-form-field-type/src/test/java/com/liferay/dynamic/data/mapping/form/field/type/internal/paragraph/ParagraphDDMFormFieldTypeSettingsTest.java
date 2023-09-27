/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.paragraph;

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
public class ParagraphDDMFormFieldTypeSettingsTest
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
			actions.contains("setVisible('indexType', false)"));
	}

	@Test
	public void testCreateParagraphDDMFormFieldTypeSettingsDDMFormLayout() {
		assertDDMFormLayout(
			DDMFormLayoutFactory.create(
				ParagraphDDMFormFieldTypeSettings.class),
			DDMFormLayoutTestUtil.createDDMFormLayout(
				DDMFormLayout.TABBED_MODE,
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"label", "text", "tip", "required"),
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"name", "fieldReference", "showLabel", "repeatable",
					"predefinedValue", "visibilityExpression", "fieldNamespace",
					"indexType", "localizable", "readOnly", "dataType", "type",
					"rulesConditionDisabled")));
	}

	@Override
	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

}