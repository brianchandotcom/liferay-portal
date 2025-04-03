/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.CheckboxDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.test.util.BaseDDMFormFieldTemplateContextContributorTestCase;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pedro Leite
 */
public class DDMFormFieldTemplateContextContributorUtilTest
	extends BaseDDMFormFieldTemplateContextContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		setUpLanguageUtil();
	}

	@Test
	public void testGetLocalizationParameters() {
		CheckboxDDMFormFieldTemplateContextContributor
			checkboxDDMFormFieldTemplateContextContributor =
				new CheckboxDDMFormFieldTemplateContextContributor();

		DDMFormField ddmFormField = new DDMFormField("field", "checkbox");

		ddmFormField.setDDMForm(getDDMForm());
		ddmFormField.setProperty("editOnlyInDefaultLanguage", true);
		ddmFormField.setProperty("isLocalizationSupported", true);

		Map<String, Object> parameters =
			checkboxDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, createDDMFormFieldRenderingContext());

		JSONArray availableLocalesJSONArray = (JSONArray)parameters.get(
			"availableLocales");
		JSONObject defaultLocaleJSONObject = (JSONObject)parameters.get(
			"defaultLocale");
		JSONObject editingLocaleJSONObject = (JSONObject)parameters.get(
			"editingLocale");

		Assert.assertEquals(
			availableLocales.size(), availableLocalesJSONArray.length());
		Assert.assertEquals(
			LocaleUtil.US.toString(), defaultLocaleJSONObject.get("localeId"));
		Assert.assertEquals(
			LocaleUtil.US.toString(), editingLocaleJSONObject.get("localeId"));
		Assert.assertTrue((boolean)parameters.get("editOnlyInDefaultLanguage"));
		Assert.assertTrue((boolean)parameters.get("isLocalizationSupported"));
	}

}