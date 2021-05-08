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

package com.liferay.dynamic.data.mapping.form.field.type.internal.options;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class OptionsDDMFormFieldContextHelperTest {

	@Before
	public void setUp() {
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

		Assert.assertEquals(_DEFAULT_OPTION_LABEL, defaultOption.get("label"));

		Matcher matcher = _defaultOptionValuePattern.matcher(
			defaultOption.get("reference"));

		Assert.assertTrue(matcher.matches());

		matcher = _defaultOptionValuePattern.matcher(
			defaultOption.get("value"));

		Assert.assertTrue(matcher.matches());
	}

	private DDMForm _createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_defaultLocale);
		ddmForm.setDefaultLocale(_defaultLocale);

		return ddmForm;
	}

	private Language _mockLanguage() {
		Language language = PowerMockito.mock(Language.class);

		PowerMockito.when(
			language.get(
				Matchers.any(ResourceBundle.class), Matchers.eq("option"))
		).thenReturn(
			_DEFAULT_OPTION_LABEL
		);

		return language;
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_mockLanguage());
	}

	private void _setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		PowerMockito.when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.eq(_defaultLocale),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private static final String _DEFAULT_OPTION_LABEL = "Option";

	private static final Pattern _defaultOptionValuePattern = Pattern.compile(
		"^Option[\\d]{8}$");

	private final Locale _defaultLocale = LocaleUtil.US;

}