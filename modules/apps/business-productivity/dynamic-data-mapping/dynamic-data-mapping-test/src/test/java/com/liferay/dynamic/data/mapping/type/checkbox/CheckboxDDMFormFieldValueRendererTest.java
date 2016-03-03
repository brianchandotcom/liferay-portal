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

package com.liferay.dynamic.data.mapping.form.field.type.checkbox;

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.type.checkbox.CheckboxDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.type.checkbox.CheckboxDDMFormFieldValueRenderer;
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
@PrepareForTest({LanguageUtil.class})
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
			createCheckboxDDMFormFieldValueRenderer();

		String expectedCheckboxRenderedValue = LanguageUtil.get(
			LocaleUtil.US, "yes");

		Assert.assertEquals(
			expectedCheckboxRenderedValue,
			checkboxDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));

		ddmFormFieldValue.setValue(new UnlocalizedValue("false"));

		expectedCheckboxRenderedValue = LanguageUtil.get(LocaleUtil.US, "no");

		Assert.assertEquals(
			expectedCheckboxRenderedValue,
			checkboxDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));
	}

	protected CheckboxDDMFormFieldValueRenderer
			createCheckboxDDMFormFieldValueRenderer()
		throws Exception {

		CheckboxDDMFormFieldValueRenderer checkboxDDMFormFieldValueRenderer =
			new CheckboxDDMFormFieldValueRenderer();

		field(
			CheckboxDDMFormFieldValueRenderer.class,
			"_checkboxDDMFormFieldValueAccessor"
		).set(
			checkboxDDMFormFieldValueRenderer,
			new CheckboxDDMFormFieldValueAccessor()
		);

		return checkboxDDMFormFieldValueRenderer;
	}

	protected void setUpLanguageUtil() {
		whenLanguageGet(LocaleUtil.US, "no", "No");
		whenLanguageGet(LocaleUtil.US, "yes", "Yes");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(language);
	}

	protected void whenLanguageGet(
		Locale locale, String key, String returnValue) {

		when(
			language.get(Matchers.eq(locale), Matchers.eq(key))
		).thenReturn(
			returnValue
		);
	}

	@Mock
	protected Language language;

}