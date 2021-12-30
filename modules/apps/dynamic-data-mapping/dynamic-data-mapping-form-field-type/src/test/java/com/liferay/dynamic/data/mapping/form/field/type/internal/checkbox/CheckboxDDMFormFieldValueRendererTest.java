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
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Renato Rego
 */
@PrepareForTest(LanguageUtil.class)
@RunWith(PowerMockRunner.class)
public class CheckboxDDMFormFieldValueRendererTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpLanguageUtil();
	}

	@Test
	public void testRender() throws Exception {
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

	@Mock
	protected Language language;

	private CheckboxDDMFormFieldValueRenderer
			_createCheckboxDDMFormFieldValueRenderer()
		throws Exception {

		CheckboxDDMFormFieldValueRenderer checkboxDDMFormFieldValueRenderer =
			new CheckboxDDMFormFieldValueRenderer();

		checkboxDDMFormFieldValueRenderer.checkboxDDMFormFieldValueAccessor =
			new CheckboxDDMFormFieldValueAccessor();

		return checkboxDDMFormFieldValueRenderer;
	}

	private void _whenLanguageGet(
		Locale locale, String key, String returnValue) {

		when(
			language.get(Matchers.eq(locale), Matchers.eq(key))
		).thenReturn(
			returnValue
		);
	}

}