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

package com.liferay.dynamic.data.mapping.type.date;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
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
@PrepareForTest({LanguageUtil.class})
@RunWith(PowerMockRunner.class)
public class DateDDMFormFieldValueRendererTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpFastDateFormatFactoryUtil();
	}

	@Test
	public void testRender() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Date", new UnlocalizedValue("2015-01-25T00:00:00.000Z"));

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Date");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		ddmFormField.setProperty("mask", "%d/%m/%Y");

		DateDDMFormFieldValueRenderer dateDDMFormFieldValueRenderer =
			createDateDDMFormFieldValueRenderer();

		String expectedDateRenderedValue = "25/01/2015";

		Assert.assertEquals(
			expectedDateRenderedValue,
			dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));

		ddmFormFieldValue.setValue(new UnlocalizedValue(""));

		expectedDateRenderedValue = StringPool.BLANK;

		Assert.assertEquals(
			expectedDateRenderedValue,
			dateDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));
	}

	protected DateDDMFormFieldValueRenderer
		createDateDDMFormFieldValueRenderer() {

		return new DateDDMFormFieldValueRenderer();
	}

	protected void setUpFastDateFormatFactoryUtil() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

}