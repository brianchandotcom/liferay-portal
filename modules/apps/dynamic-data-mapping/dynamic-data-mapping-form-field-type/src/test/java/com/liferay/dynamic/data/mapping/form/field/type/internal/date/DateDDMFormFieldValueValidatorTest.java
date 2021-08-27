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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidationException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.util.DateFormatFactoryImpl;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcela Cunha
 * @author Pedro Queiroz
 */
@RunWith(PowerMockRunner.class)
public class DateDDMFormFieldValueValidatorTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDateDDMFormFieldValueValidator();
		setUpDateFormatFactoryUtil();
		setUpFastDateFormatFactoryUtil();
	}

	@Test
	public void testValidationWithEmptyNotRequiredDateShouldNotThrowException()
		throws DDMFormFieldValueValidationException {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Date", "Date", "date", "string", false, false, false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Date", new UnlocalizedValue(""));

		_dateDDMFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	@Test
	public void testValidationWithEmptyRequiredDateShouldNotThrowException()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Date", "Date", "date", "string", false, false, true);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Date", new UnlocalizedValue(""));

		_dateDDMFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	@Test(expected = DDMFormFieldValueValidationException.class)
	public void testValidationWithInvalidDateShouldThrowException()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Date", "Date", "date", "string", false, false, false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Date", new UnlocalizedValue("this-is-not-valid"));

		_dateDDMFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	@Test
	public void testValidationWithValidRequiredDateShouldNotThrowException()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Date", "Date", "date", "string", false, false, true);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Date", new UnlocalizedValue("2018-04-18"));

		_dateDDMFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	protected void setUpDateDDMFormFieldValueValidator() throws Exception {
		_dateDDMFormFieldValueValidator = new DateDDMFormFieldValueValidator();

		field(
			DateDDMFormFieldValueValidator.class, "jsonFactory"
		).set(
			_dateDDMFormFieldValueValidator, _jsonFactory
		);
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

	private DateDDMFormFieldValueValidator _dateDDMFormFieldValueValidator;
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}