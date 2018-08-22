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

package com.liferay.dynamic.data.mapping.form.field.type.numeric.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidationException;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class NumericDDMFormFieldValueValidatorTest {

	@Before
	public void setUp() {
		_numericDDMFormFieldValueValidator =
			new NumericDDMFormFieldValueValidator();
	}

	@Test(expected = DDMFormFieldValueValidationException.class)
	public void testValidationWithInvalidNumber() throws Exception {
		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"integer", "integer", "numeric", "integer", true, false, false);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, "invalid number");

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"integer", localizedValue);

		_numericDDMFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	@Test
	public void testValidationWithNonrequiredFieldAndEmptyValue()
		throws Exception {

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"integer", "integer", "numeric", "integer", true, false, false);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, "");

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"integer", localizedValue);

		_numericDDMFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	@Test
	public void testValidationWithValidDouble() throws Exception {
		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"double", "double", "numeric", "double", true, false, false);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, "2");

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"double", localizedValue);

		_numericDDMFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	@Test
	public void testValidationWithValidInteger() throws Exception {
		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"integer", "integer", "numeric", "integer", true, false, false);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, "1");
		localizedValue.addString(LocaleUtil.BRAZIL, "2");

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"integer", localizedValue);

		_numericDDMFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	private NumericDDMFormFieldValueValidator
		_numericDDMFormFieldValueValidator;

}