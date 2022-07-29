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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox;

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Renato Rego
 */
public class CheckboxDDMFormFieldValueRendererTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		setUpLanguageUtil();
	}

	@Test
	public void testRender() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Checkbox", new UnlocalizedValue("true"));

		CheckboxDDMFormFieldValueRenderer checkboxDDMFormFieldValueRenderer =
			_createCheckboxDDMFormFieldValueRenderer();

		String expectedCheckboxRenderedValue = LanguageUtil.get(
			LocaleUtil.US, "true");

		Assert.assertEquals(
			expectedCheckboxRenderedValue,
			checkboxDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));

		ddmFormFieldValue.setValue(new UnlocalizedValue("false"));

		expectedCheckboxRenderedValue = LanguageUtil.get(
			LocaleUtil.US, "false");

		Assert.assertEquals(
			expectedCheckboxRenderedValue,
			checkboxDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));
	}

	protected void setUpLanguageUtil() {
		_whenLanguageGet(LocaleUtil.US, "false", "False");
		_whenLanguageGet(LocaleUtil.US, "true", "True");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(language);
	}

	protected Language language = Mockito.mock(Language.class);

	private CheckboxDDMFormFieldValueRenderer
		_createCheckboxDDMFormFieldValueRenderer() {

		CheckboxDDMFormFieldValueRenderer checkboxDDMFormFieldValueRenderer =
			new CheckboxDDMFormFieldValueRenderer();

		checkboxDDMFormFieldValueRenderer.checkboxDDMFormFieldValueAccessor =
			new CheckboxDDMFormFieldValueAccessor();

		ReflectionTestUtil.setFieldValue(
			checkboxDDMFormFieldValueRenderer, "_language", language);

		return checkboxDDMFormFieldValueRenderer;
	}

	private void _whenLanguageGet(
		Locale locale, String key, String returnValue) {

		Mockito.when(
			language.get(Mockito.eq(locale), Mockito.eq(key))
		).thenReturn(
			returnValue
		);
	}

}