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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTestCase;
import com.liferay.portlet.dynamicdatamapping.StorageFieldNameException;
import com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException;
import com.liferay.portlet.dynamicdatamapping.StorageFieldValueException;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesValidatorTest extends BaseDDMTestCase {

	@Before
	public void setUp() {
		setUpDDMFormValuesValidatorUtil();
	}

	@Test(expected = StorageFieldNameException.class)
	public void testValidateDDMFormValuesWithInvalidFieldName()
		throws Exception {

		DDMForm ddmForm = createDDMForm("firstName");

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("lastName", null));

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldNameException.class)
	public void testValidateDDMFormValuesWithInvalidNestedFieldName()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = createTextDDMFormField("name");

		addNestedTextDDMFormFields(ddmFormField, "contact");

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, StringUtil.randomString());

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"name", localizedValue);

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(
			createDDMFormFieldValue("invalid", localizedValue));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidateDDMFormValuesWithLocalizableField()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = createTextDDMFormField("name");

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("name", new UnlocalizedValue("Joe")));

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldRequiredException.class)
	public void testValidateDDMFormValuesWithMissingNestedRequiredField()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("name", "text");

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		nestedDDMFormFields.add(
			createTextDDMFormField("contact", "", false, false, true));

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"name", null);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidateDDMFormValuesWithMissingNestedRequiredFieldValue()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("name", "text");

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		nestedDDMFormFields.add(
			createTextDDMFormField("contact", "", false, false, true));

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"name", null);

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(createDDMFormFieldValue("contact", null));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldRequiredException.class)
	public void testValidateDDMFormValuesWithMissingRequiredField()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = createTextDDMFormField("name");

		ddmFormField.setRequired(true);

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidateDDMFormValuesWithMissingRequiredFieldValue()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = createTextDDMFormField("name");

		ddmFormField.setRequired(true);

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("name", null));

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = NullPointerException.class)
	public void testValidateDDMFormValuesWithoutDDMFormReference()
		throws Exception {

		DDMFormValues ddmFormValues = new DDMFormValues(null);

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidateDDMFormValuesWithSeparatorField() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = createSeparatorDDMFormField(
			"separator", false);

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"separator", new UnlocalizedValue("separator value")));

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidateDDMFormValuesWithUnlocalizableField()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = createTextDDMFormField(
			"name", "", false, false, false);

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, "Joe");

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("name", localizedValue));

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidateDDMFormValuesWithValueSetForTransientField()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("fieldset", "fieldset");

		addNestedTextDDMFormFields(ddmFormField, "name");

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"fieldset", new UnlocalizedValue("Value"));

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(
			createDDMFormFieldValue("name", new UnlocalizedValue("Joe")));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidateDDMFormValuesWithWrongAvailableLocales()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = createTextDDMFormField("name");

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.BRAZIL, "Joao");
		localizedValue.addString(LocaleUtil.US, "Joe");

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("name", localizedValue));

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidateDDMFormValuesWithWrongDefaultLocale()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = createTextDDMFormField("name");

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.BRAZIL);

		localizedValue.addString(LocaleUtil.US, "Joe");

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("name", localizedValue));

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	@Test(expected = StorageFieldValueException.class)
	public void testValidateDDMFormValuesWithWrongValuesForNonRepeatableField()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("name", "text");

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		nestedDDMFormFields.add(
			createTextDDMFormField("contact", "", false, false, true));

		addDDMFormFields(ddmForm, ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"name", new UnlocalizedValue("name value"));

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		nestedDDMFormFieldValues.add(
			createDDMFormFieldValue(
				"contact", new UnlocalizedValue("contact value 1")));
		nestedDDMFormFieldValues.add(
			createDDMFormFieldValue(
				"contact", new UnlocalizedValue("contact value 2")));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	protected void setUpDDMFormValuesValidatorUtil() {
		DDMFormValuesValidatorUtil ddmFormValuesValidatorUtil =
			new DDMFormValuesValidatorUtil();

		ddmFormValuesValidatorUtil.setDDMFormValuesValidator(
			new DDMFormValuesValidatorImpl());
	}

}