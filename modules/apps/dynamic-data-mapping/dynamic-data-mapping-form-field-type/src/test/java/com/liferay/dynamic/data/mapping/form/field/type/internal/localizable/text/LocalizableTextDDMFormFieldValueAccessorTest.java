/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.localizable.text;

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Gabriel Ibson
 */
public class LocalizableTextDDMFormFieldValueAccessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpLocalizableTextDDMFormFieldValueAccessor();
	}

	@Test
	public void testEmpty() {
		Assert.assertTrue(
			_localizableTextDDMFormFieldValueAccessor.isEmpty(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"localizableText", new UnlocalizedValue("{}")),
				LocaleUtil.US));

		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(
			LocaleUtil.US,
			JSONUtil.put(
				"en_US", StringPool.BLANK
			).put(
				"pt_BR", StringPool.BLANK
			).toString());

		Assert.assertTrue(
			_localizableTextDDMFormFieldValueAccessor.isEmpty(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"localizableText", localizedValue),
				LocaleUtil.US));
	}

	@Test
	public void testMalformedJson() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"localizableText", new UnlocalizedValue("{"));

		JSONObject valueJSONObject =
			_localizableTextDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, LocaleUtil.US);

		Assert.assertTrue(valueJSONObject.length() == 0);
	}

	@Test
	public void testNotEmpty() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"localizableText",
				new UnlocalizedValue(
					"{\"title\":\"Welcome to Liferay Forms!\"," +
						"\"type\":\"document\"}"));

		Assert.assertFalse(
			_localizableTextDDMFormFieldValueAccessor.isEmpty(
				ddmFormFieldValue, LocaleUtil.US));
	}

	private static void _setUpLocalizableTextDDMFormFieldValueAccessor() {
		_localizableTextDDMFormFieldValueAccessor =
			new LocalizableTextDDMFormFieldValueAccessor();

		ReflectionTestUtil.setFieldValue(
			_localizableTextDDMFormFieldValueAccessor, "jsonFactory",
			new JSONFactoryImpl());
	}

	private static LocalizableTextDDMFormFieldValueAccessor
		_localizableTextDDMFormFieldValueAccessor;

}