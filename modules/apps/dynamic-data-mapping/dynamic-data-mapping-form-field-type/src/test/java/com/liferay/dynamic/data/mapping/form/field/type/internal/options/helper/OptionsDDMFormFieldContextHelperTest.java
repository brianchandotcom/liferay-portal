/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.options.helper;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class OptionsDDMFormFieldContextHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpLanguageUtil();
		_setUpResourceBundleUtil();
	}

	@Test
	public void testCreateDefaultOptions() {
		DDMFormField ddmFormField = new DDMFormField(
			"radioField", DDMFormFieldTypeConstants.RADIO);

		ddmFormField.setDDMForm(_createDDMForm());

		OptionsDDMFormFieldContextHelper optionsDDMFormFieldContextHelper =
			new OptionsDDMFormFieldContextHelper(
				new JSONFactoryImpl(), ddmFormField,
				new DDMFormFieldRenderingContext());

		List<Object> defaultOptions =
			optionsDDMFormFieldContextHelper.createDefaultOptions();

		Map<String, String> defaultOption =
			(Map<String, String>)defaultOptions.get(0);

		Assert.assertEquals("Option", defaultOption.get("label"));

		Matcher matcher = _pattern.matcher(defaultOption.get("reference"));

		Assert.assertTrue(matcher.matches());

		matcher = _pattern.matcher(defaultOption.get("value"));

		Assert.assertTrue(matcher.matches());
	}

	private static Language _mockLanguage() {
		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(
				Mockito.any(ResourceBundle.class), Mockito.eq("option"))
		).thenReturn(
			"Option"
		);

		return language;
	}

	private static void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_mockLanguage());
	}

	private static void _setUpResourceBundleUtil() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(_defaultLocale)
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private DDMForm _createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_defaultLocale);
		ddmForm.setDefaultLocale(_defaultLocale);

		return ddmForm;
	}

	private static final Locale _defaultLocale = LocaleUtil.US;
	private static final Pattern _pattern = Pattern.compile("^Option[\\d]{8}$");

}