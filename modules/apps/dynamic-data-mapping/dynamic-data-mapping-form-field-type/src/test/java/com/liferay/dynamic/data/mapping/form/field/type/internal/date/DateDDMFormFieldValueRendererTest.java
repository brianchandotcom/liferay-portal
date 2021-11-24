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
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.util.DateFormatFactoryImpl;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

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
		mockStatic(LocaleThreadLocal.class);

		when(
			LocaleThreadLocal.getThemeDisplayLocale()
		).thenReturn(
			LocaleUtil.BRAZIL
		);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"birthday", new UnlocalizedValue("2015-01-25"));

		Assert.assertEquals(
			"25/01/2015",
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"25/01/2015",
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.HUNGARY));
		Assert.assertEquals(
			"25/01/2015",
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));
	}

	@Test
	public void testRenderDisplayLocaleNull() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"birthday", new UnlocalizedValue("2015-01-25"));

		Assert.assertEquals(
			"25/01/2015",
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"25.01.2015",
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.GERMANY));
		Assert.assertEquals(
			"2015.01.25.",
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.HUNGARY));
		Assert.assertEquals(
			"2015/01/25",
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.JAPAN));
		Assert.assertEquals(
			"01/25/2015",
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));

		ddmFormFieldValue.setValue(new UnlocalizedValue(""));

		Assert.assertEquals(
			StringPool.BLANK,
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));
	}

	@Test
	public void testRenderDisplayLocaleUS() {
		mockStatic(LocaleThreadLocal.class);

		when(
			LocaleThreadLocal.getThemeDisplayLocale()
		).thenReturn(
			LocaleUtil.US
		);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"birthday", new UnlocalizedValue("2015-01-25"));

		Assert.assertEquals(
			"01/25/2015",
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"01/25/2015",
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.HUNGARY));
		Assert.assertEquals(
			"01/25/2015",
			_dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));
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

	private final DateDDMFormFieldValueRenderer _dateDDMFormFieldValueRenderer =
		new DateDDMFormFieldValueRenderer();

}