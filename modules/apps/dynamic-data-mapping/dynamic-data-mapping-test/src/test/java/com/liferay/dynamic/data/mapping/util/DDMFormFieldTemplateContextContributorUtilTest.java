/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.CheckboxDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.test.util.BaseDDMFormFieldTemplateContextContributorTestCase;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

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

		JSONObject defaultLocaleJSONObject = (JSONObject)parameters.get(
			"defaultLocale");
		JSONObject editingLocaleJSONObject = (JSONObject)parameters.get(
			"editingLocale");

		Assert.assertEquals(
			LocaleUtil.US.toString(), defaultLocaleJSONObject.get("localeId"));
		Assert.assertEquals(
			LocaleUtil.US.toString(), editingLocaleJSONObject.get("localeId"));
		Assert.assertTrue((boolean)parameters.get("editOnlyInDefaultLanguage"));
		Assert.assertTrue((boolean)parameters.get("isLocalizationSupported"));
	}

	@Test
	public void testGetOptions() {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(LocaleUtil.BRAZIL);

		String optionLabel = RandomTestUtil.randomString();
		String optionValue = RandomTestUtil.randomString();

		ddmFormFieldOptions.addOptionLabel(
			optionValue, LocaleUtil.US, optionLabel);

		Map<Locale, String> nameMap = LinkedHashMapBuilder.put(
			LocaleUtil.US, optionLabel
		).build();

		long listTypeDefinitionId = RandomTestUtil.randomLong();

		List<Map<String, Object>> options =
			DDMFormFieldTemplateContextContributorUtil.getOptions(
				ddmFormFieldOptions, listTypeDefinitionId,
				_mockListTypeEntryLocalService(
					listTypeDefinitionId, nameMap, optionValue));

		Assert.assertEquals(options.toString(), 1, options.size());

		Map<String, Object> option = options.get(0);

		Assert.assertEquals(optionLabel, option.get("label"));
		Assert.assertEquals(nameMap, option.get("labelMap"));
	}

	private ListTypeEntryLocalService _mockListTypeEntryLocalService(
		long listTypeDefinitionId, Map<Locale, String> nameMap,
		String optionValue) {

		ListTypeEntry listTypeEntry = Mockito.mock(ListTypeEntry.class);

		Mockito.when(
			listTypeEntry.getNameMap()
		).thenReturn(
			nameMap
		);

		ListTypeEntryLocalService listTypeEntryLocalService = Mockito.mock(
			ListTypeEntryLocalService.class);

		Mockito.when(
			listTypeEntryLocalService.fetchListTypeEntry(
				listTypeDefinitionId, optionValue)
		).thenReturn(
			listTypeEntry
		);

		return listTypeEntryLocalService;
	}

}