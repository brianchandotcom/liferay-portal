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

import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Rodrigo Paulino
 */
public class OptionsDDMFormFieldValueAccessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpJSONFactory(new JSONFactoryImpl());
		_setUpLanguageUtil();
	}

	@Test
	public void testIsEmpty() {
		Assert.assertTrue(
			_optionsDDMFormFieldValueAccessor.isEmpty(
				DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
					null, null),
				LocaleUtil.US));
		Assert.assertTrue(
			_optionsDDMFormFieldValueAccessor.isEmpty(
				DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
					null, StringPool.BLANK),
				LocaleUtil.US));
		Assert.assertTrue(
			_optionsDDMFormFieldValueAccessor.isEmpty(
				DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
					null, StringUtil.randomString()),
				LocaleUtil.US));
		Assert.assertTrue(
			_optionsDDMFormFieldValueAccessor.isEmpty(
				DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
					null,
					JSONUtil.put(
						"en_US", new String[0]
					).toString()),
				LocaleUtil.US));
		Assert.assertTrue(
			_optionsDDMFormFieldValueAccessor.isEmpty(
				DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
					null,
					JSONUtil.put(
						"es_ES", new String[] {StringUtil.randomString()}
					).toString()),
				LocaleUtil.US));
	}

	@Test
	public void testIsNotEmpty() {
		Assert.assertFalse(
			_optionsDDMFormFieldValueAccessor.isEmpty(
				DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
					null,
					JSONUtil.put(
						"en_US", new String[] {StringUtil.randomString()}
					).toString()),
				LocaleUtil.US));
	}

	private static void _setUpJSONFactory(JSONFactory jsonFactory) {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(jsonFactory);

		ReflectionTestUtil.setFieldValue(
			_optionsDDMFormFieldValueAccessor, "_jsonFactory", jsonFactory);
	}

	private static void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.getLanguageId(Matchers.eq(LocaleUtil.US))
		).thenReturn(
			"en_US"
		);

		languageUtil.setLanguage(language);
	}

	private static final OptionsDDMFormFieldValueAccessor
		_optionsDDMFormFieldValueAccessor =
			new OptionsDDMFormFieldValueAccessor();

}