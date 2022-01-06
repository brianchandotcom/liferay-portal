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

package com.liferay.dynamic.data.mapping.form.field.type.internal.date;

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.util.DateFormatFactoryImpl;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Bruno Basto
 */
@PrepareForTest(LocaleThreadLocal.class)
@RunWith(PowerMockRunner.class)
public class DateDDMFormFieldValueRendererTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpDateFormatFactoryUtil();
		setUpFastDateFormatFactoryUtil();
	}

	@Test
	public void testRenderDisplayLocaleBrazil() {
		_mockThemeDisplayLocale(LocaleUtil.BRAZIL);

		_assertRenderValues(
			_getSingleValueExpectedValuesMap("25/01/2015"), "2015-01-25");
		_assertRenderValues(
			_getSingleValueExpectedValuesMap("25/01/2015 01:00"),
			"2015-01-25 1:00");
	}

	@Test
	public void testRenderDisplayLocaleNull() {
		_assertRenderValues(
			HashMapBuilder.put(
				new Locale.Builder().setLanguage(
					"ar"
				).setRegion(
					"SA"
				).setExtension(
					Locale.UNICODE_LOCALE_EXTENSION, "nu-arab"
				).build(),
				"٢٥/٠١/٢٠١٥"
			).put(
				LocaleUtil.BRAZIL, "25/01/2015"
			).put(
				new Locale("ca", "ES"), "25/01/2015"
			).put(
				new Locale("fi", "FI"), "25.01.2015"
			).put(
				LocaleUtil.FRANCE, "25/01/2015"
			).put(
				LocaleUtil.GERMANY, "25.01.2015"
			).put(
				LocaleUtil.HUNGARY, "2015.01.25."
			).put(
				LocaleUtil.JAPAN, "2015/01/25"
			).put(
				LocaleUtil.NETHERLANDS, "25-01-2015"
			).put(
				LocaleUtil.SIMPLIFIED_CHINESE, "2015-01-25"
			).put(
				LocaleUtil.SPAIN, "25/01/2015"
			).put(
				new Locale("sv", "SE"), "2015-01-25"
			).put(
				LocaleUtil.US, "01/25/2015"
			).build(),
			"2015-01-25");
		_assertRenderValues(
			HashMapBuilder.put(
				new Locale.Builder().setLanguage(
					"ar"
				).setRegion(
					"SA"
				).setExtension(
					Locale.UNICODE_LOCALE_EXTENSION, "nu-arab"
				).build(),
				"٢٥/٠١/٢٠١٥ ٠١:٠٠ ص"
			).put(
				LocaleUtil.BRAZIL, "25/01/2015 01:00"
			).put(
				new Locale("ca", "ES"), "25/01/2015 01:00"
			).put(
				new Locale("fi", "FI"), "25.01.2015 01:00"
			).put(
				LocaleUtil.FRANCE, "25/01/2015 01:00"
			).put(
				LocaleUtil.GERMANY, "25.01.2015 01:00"
			).put(
				LocaleUtil.HUNGARY, "2015.01.25. 01:00"
			).put(
				LocaleUtil.JAPAN, "2015/01/25 01:00"
			).put(
				LocaleUtil.NETHERLANDS, "25-01-2015 01:00"
			).put(
				LocaleUtil.SIMPLIFIED_CHINESE, "2015-01-25 上午01:00"
			).put(
				LocaleUtil.SPAIN, "25/01/2015 01:00"
			).put(
				new Locale("sv", "SE"), "2015-01-25 01:00"
			).put(
				LocaleUtil.US, "01/25/2015 01:00 AM"
			).build(),
			"2015-01-25 1:00");
		_assertRenderValues(
			HashMapBuilder.put(
				LocaleUtil.US, StringPool.BLANK
			).build(),
			"");
	}

	@Test
	public void testRenderDisplayLocaleUS() {
		_mockThemeDisplayLocale(LocaleUtil.US);

		_assertRenderValues(
			_getSingleValueExpectedValuesMap("01/25/2015"), "2015-01-25");
		_assertRenderValues(
			_getSingleValueExpectedValuesMap("01/25/2015 01:00 AM"),
			"2015-01-25 1:00");
	}

	protected void setUpDateFormatFactoryUtil() {
		DateFormatFactoryUtil dateFormatFactoryUtil =
			new DateFormatFactoryUtil();

		dateFormatFactoryUtil.setDateFormatFactory(new DateFormatFactoryImpl());
	}

	protected void setUpFastDateFormatFactoryUtil() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

	private void _assertRenderValues(
		Map<Locale, String> expectedValuesMap, String inputValue) {

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"birthday", new UnlocalizedValue(inputValue));

		for (Map.Entry<Locale, String> entry : expectedValuesMap.entrySet()) {
			Assert.assertEquals(
				"locale " + entry.getKey(), entry.getValue(),
				_dateDDMFormFieldValueRenderer.render(
					ddmFormFieldValue, entry.getKey()));
		}
	}

	private Map<Locale, String> _getSingleValueExpectedValuesMap(
		String expectedValue) {

		return HashMapBuilder.put(
			new Locale.Builder().setLanguage(
				"ar"
			).setRegion(
				"SA"
			).setExtension(
				Locale.UNICODE_LOCALE_EXTENSION, "nu-arab"
			).build(),
			expectedValue
		).put(
			LocaleUtil.BRAZIL, expectedValue
		).put(
			new Locale("ca", "ES"), expectedValue
		).put(
			new Locale("fi", "FI"), expectedValue
		).put(
			LocaleUtil.FRANCE, expectedValue
		).put(
			LocaleUtil.GERMANY, expectedValue
		).put(
			LocaleUtil.HUNGARY, expectedValue
		).put(
			LocaleUtil.JAPAN, expectedValue
		).put(
			LocaleUtil.NETHERLANDS, expectedValue
		).put(
			LocaleUtil.SIMPLIFIED_CHINESE, expectedValue
		).put(
			LocaleUtil.SPAIN, expectedValue
		).put(
			new Locale("sv", "SE"), expectedValue
		).put(
			LocaleUtil.US, expectedValue
		).build();
	}

	private void _mockThemeDisplayLocale(Locale locale) {
		mockStatic(LocaleThreadLocal.class);

		when(
			LocaleThreadLocal.getThemeDisplayLocale()
		).thenReturn(
			locale
		);
	}

	private final DateDDMFormFieldValueRenderer _dateDDMFormFieldValueRenderer =
		new DateDDMFormFieldValueRenderer();

}