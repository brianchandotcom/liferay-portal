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

package com.liferay.portlet.dynamicdatamapping.validator;

import com.liferay.portal.bean.BeanPropertiesImpl;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTestCase;
import com.liferay.portlet.dynamicdatamapping.DDMFormValidationException;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValidatorTest extends BaseDDMTestCase {

	@Before
	public void setUp() {
		setUpBeanPropertiesUtil();
		setUpDDMFormValidatorUtil();
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithDefaultLocaleMissingAsAvailableLocale()
		throws Exception {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(createAvailableLocales(LocaleUtil.BRAZIL));
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormValidatorUtil.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithInvalidFieldIndexType() throws Exception {
		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		ddmFormField.setIndexType("Invalid");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorUtil.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithInvalidFieldName() throws Exception {
		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"*", DDMFormFieldType.TEXT);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorUtil.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithInvalidFieldType() throws Exception {
		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Text", null);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorUtil.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithNoOptionsSetForFieldOptions()
		throws Exception {

		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorUtil.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithNullAvailableLocales() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(null);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormValidatorUtil.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithNullDefaultLocale() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(null);

		DDMFormValidatorUtil.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithWrongAvailableLocalesSetForFieldOptions()
		throws Exception {

		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(
			"Value", LocaleUtil.BRAZIL, "Portuguese Label");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorUtil.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithWrongAvailableLocalesSetForLabel()
		throws Exception {

		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.BRAZIL, "Portuguese Label");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorUtil.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithWrongDefaultLocaleSetForFieldOptions()
		throws Exception {

		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(
			"Value", LocaleUtil.US, "Value Label");

		ddmFormFieldOptions.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorUtil.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithWrongDefaultLocaleSetForLabel()
		throws Exception {

		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.US, "Label");

		label.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValidatorUtil.validate(ddmForm);
	}

	protected void setUpBeanPropertiesUtil() {
		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(new BeanPropertiesImpl());
	}

	protected void setUpDDMFormValidatorUtil() {
		DDMFormValidatorUtil ddmFormValidatorUtil = new DDMFormValidatorUtil();

		ddmFormValidatorUtil.setDDMFormValidator(new DDMFormValidatorImpl());
	}

}