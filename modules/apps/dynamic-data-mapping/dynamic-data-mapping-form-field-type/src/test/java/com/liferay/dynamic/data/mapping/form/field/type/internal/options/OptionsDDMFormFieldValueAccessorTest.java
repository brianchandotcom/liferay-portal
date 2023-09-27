/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		_setUpLanguage();

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

	private static void _setUpLanguage() {
		Mockito.when(
			_language.getLanguageId(Mockito.eq(LocaleUtil.US))
		).thenReturn(
			"en_US"
		);

		ReflectionTestUtil.setFieldValue(
			_optionsDDMFormFieldValueAccessor, "_language", _language);
	}

	private static void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	private static final Language _language = Mockito.mock(Language.class);
	private static final OptionsDDMFormFieldValueAccessor
		_optionsDDMFormFieldValueAccessor =
			new OptionsDDMFormFieldValueAccessor();

}