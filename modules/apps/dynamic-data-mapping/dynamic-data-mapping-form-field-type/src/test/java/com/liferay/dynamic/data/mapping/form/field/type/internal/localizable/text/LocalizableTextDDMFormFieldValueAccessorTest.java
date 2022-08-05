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