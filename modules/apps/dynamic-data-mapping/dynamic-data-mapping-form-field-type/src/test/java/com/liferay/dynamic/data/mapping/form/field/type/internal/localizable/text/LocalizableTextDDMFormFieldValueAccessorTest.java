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

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Gabriel Ibson
 */
@RunWith(PowerMockRunner.class)
public class LocalizableTextDDMFormFieldValueAccessorTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setUpLocalizableTextDDMFormFieldValueAccessor();
	}

	@Test
	public void testEmpty() {
		Assert.assertTrue(
			_localizableTextDDMFormFieldValueAccessor.isEmpty(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"localizableText", new UnlocalizedValue("{}")),
				LocaleUtil.US));
	}

	@Test
	public void testMalformedJson() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"localizableText", new UnlocalizedValue("{"));

		JSONObject value = _localizableTextDDMFormFieldValueAccessor.getValue(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertTrue(value.length() == 0);
	}

	@Test
	public void testNotEmpty() {
		StringBundler sb = new StringBundler(2);

		sb.append("{\"title\":\"Welcome to Liferay Forms!\",");
		sb.append("\"type\":\"document\"}");

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"localizableText", new UnlocalizedValue(sb.toString()));

		Assert.assertFalse(
			_localizableTextDDMFormFieldValueAccessor.isEmpty(
				ddmFormFieldValue, LocaleUtil.US));
	}

	private void _setUpLocalizableTextDDMFormFieldValueAccessor()
		throws Exception {

		_localizableTextDDMFormFieldValueAccessor =
			new LocalizableTextDDMFormFieldValueAccessor();

		field(
			LocalizableTextDDMFormFieldValueAccessor.class, "jsonFactory"
		).set(
			_localizableTextDDMFormFieldValueAccessor, _jsonFactory
		);
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private LocalizableTextDDMFormFieldValueAccessor
		_localizableTextDDMFormFieldValueAccessor;

}