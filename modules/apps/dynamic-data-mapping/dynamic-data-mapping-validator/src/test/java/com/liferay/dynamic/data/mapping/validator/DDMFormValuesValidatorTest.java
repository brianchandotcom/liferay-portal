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

package com.liferay.dynamic.data.mapping.validator;

import com.liferay.dynamic.data.mapping.exception.StorageFieldValueException;
import com.liferay.dynamic.data.mapping.exception.StorageFieldValueException.RequiredValue;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.validator.internal.DDMFormValuesValidatorImpl;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesValidatorTest {

	@Test
	public void testValidationWithInvalidFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("firstName");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue("lastName", null));

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test
	public void testValidationWithInvalidNestedFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		DDMFormTestUtil.addNestedTextDDMFormFields(ddmFormField, "contact");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		Value localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, StringUtil.randomString());

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue);

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"invalid", localizedValue));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidationWithLocalizableField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", new UnlocalizedValue("Joe")));

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithMissingNestedRequiredField()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("name", "text");

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		nestedDDMFormFields.add(
			DDMFormTestUtil.createTextDDMFormField(
				"contact", "", false, false, true));

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue("name", null);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithMissingNestedRequiredFieldValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("name", "text");

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		nestedDDMFormFields.add(
			DDMFormTestUtil.createTextDDMFormField(
				"contact", "", false, false, true));

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue("name", null);

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(
			DDMFormValuesTestUtil.createDDMFormFieldValue("contact", null));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithMissingRequiredField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		ddmFormField.setRequired(true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithMissingRequiredFieldValue() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		ddmFormField.setRequired(true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue("name", null));

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test
	public void testValidationWithNonRequiredFieldAndEmptyDefaultLocaleValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, false);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, StringPool.BLANK);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test
	public void testValidationWithNonRequiredFieldValue() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, false);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = NullPointerException.class)
	public void testValidationWithoutDDMFormReference() throws Exception {
		DDMFormValues ddmFormValues = new DDMFormValues(null);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidationWithRequiredFieldAndEmptyDefaultLocaleValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, StringPool.BLANK);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidationWithRequiredFieldAndEmptyTranslatedValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(
				LocaleUtil.US, LocaleUtil.BRAZIL),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm,
			DDMFormTestUtil.createAvailableLocales(
				LocaleUtil.US, LocaleUtil.BRAZIL),
			LocaleUtil.US);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, StringUtil.randomString());
		localizedValue.addString(LocaleUtil.BRAZIL, StringPool.BLANK);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidationWithRequiredFieldAndNullValue() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = RequiredValue.class)
	public void testValidationWithRequiredFieldAndWithNoValue()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "Name", true, false, true);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidationWithSeparatorField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createSeparatorDDMFormField(
			"separator", false);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"separator", new UnlocalizedValue("separator value")));

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidationWithUnlocalizableField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", "", false, false, false);

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, "Joe");

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue));

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidationWithValueSetForTransientField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("fieldset", "fieldset");

		DDMFormTestUtil.addNestedTextDDMFormFields(ddmFormField, "name");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"fieldset", new UnlocalizedValue("Value"));

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", new UnlocalizedValue("Joe")));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidationWithWrongAvailableLocales() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.BRAZIL, "Joao");
		localizedValue.addString(LocaleUtil.US, "Joe");

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue));

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidationWithWrongDefaultLocale() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.BRAZIL);

		localizedValue.addString(LocaleUtil.US, "Joe");

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", localizedValue));

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidationWithWrongValuesForNonRepeatableField()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("name", "text");

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		nestedDDMFormFields.add(
			DDMFormTestUtil.createTextDDMFormField(
				"contact", "", false, false, true));

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name", new UnlocalizedValue("name value"));

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"contact", new UnlocalizedValue("contact value 1")));
		nestedDDMFormFieldValues.add(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"contact", new UnlocalizedValue("contact value 2")));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	private final DDMFormValuesValidator _ddmFormValuesValidator =
		new DDMFormValuesValidatorImpl();

}