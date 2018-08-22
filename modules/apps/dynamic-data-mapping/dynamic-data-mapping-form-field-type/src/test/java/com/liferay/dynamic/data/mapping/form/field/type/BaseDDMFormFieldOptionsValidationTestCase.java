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

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseDDMFormFieldOptionsValidationTestCase {

	@Test
	public void testValidationWithNonrequiredOptionAndEmptyDefaultLocaleValue()
		throws Exception {

		DDMFormField ddmFormField = new DDMFormField("option", "select");

		ddmFormField.setDataType("string");
		ddmFormField.setRequired(false);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("A", LocaleUtil.US, "Option A");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setLocalizable(false);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"option", new UnlocalizedValue(""));

		DDMFormFieldValueValidator ddmFormFieldValueValidator =
			getDDMFormFieldValueValidator();

		ddmFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	@Test(expected = DDMFormFieldValueValidationException.class)
	public void testValidationWithNonrequiredOptionAndInvalidLocalizedValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("option", "select");

		ddmFormField.setDataType("string");
		ddmFormField.setRequired(false);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("A", LocaleUtil.US, "Option A");
		ddmFormFieldOptions.addOptionLabel("B", LocaleUtil.US, "Option B");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setLocalizable(true);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addAvailableLocale(LocaleUtil.BRAZIL);

		LocalizedValue localizedValue =
			DDMFormValuesTestUtil.createLocalizedValue(
				"[\"\"]", "[\"C\"]", LocaleUtil.US);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"option", localizedValue);

		ddmFormFieldValue.setDDMFormValues(ddmFormValues);

		DDMFormFieldValueValidator ddmFormFieldValueValidator =
			getDDMFormFieldValueValidator();

		ddmFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	@Test(expected = DDMFormFieldValueValidationException.class)
	public void testValidationWithRequiredOptionAndEmptyDefaultLocaleValue()
		throws Exception {

		DDMFormField ddmFormField = new DDMFormField("option", "select");

		ddmFormField.setDataType("string");
		ddmFormField.setRequired(true);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("A", LocaleUtil.US, "Option A");
		ddmFormFieldOptions.addOptionLabel("B", LocaleUtil.US, "Option B");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setLocalizable(false);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"option", new UnlocalizedValue("[\"\"]"));

		DDMFormFieldValueValidator ddmFormFieldValueValidator =
			getDDMFormFieldValueValidator();

		ddmFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	@Test(expected = DDMFormFieldValueValidationException.class)
	public void testValidationWithWrongValueSetForOptions() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("option", "select");

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("A", LocaleUtil.US, "Option A");
		ddmFormFieldOptions.addOptionLabel("B", LocaleUtil.US, "Option B");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setLocalizable(false);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"option", new UnlocalizedValue("[\"Invalid\"]"));

		DDMFormFieldValueValidator ddmFormFieldValueValidator =
			getDDMFormFieldValueValidator();

		ddmFormFieldValueValidator.validate(
			ddmFormField, ddmFormFieldValue.getValue());
	}

	protected abstract DDMFormFieldValueValidator
		getDDMFormFieldValueValidator();

}