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

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidationException;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class SelectDDMFormFieldValueValidatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidate() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("option", "select");

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("A", LocaleUtil.US, "Option A");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		_selectDDMFormFieldValueValidator.validate(ddmFormField, null);
	}

	@Test(expected = DDMFormFieldValueValidationException.class)
	public void testValidateWithEmptyOptionsValues() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("option", "select");

		ddmFormField.setDDMFormFieldOptions(new DDMFormFieldOptions());

		_selectDDMFormFieldValueValidator.validate(ddmFormField, null);
	}

	@Test(expected = DDMFormFieldValueValidationException.class)
	public void testValidateWithNullDDMFormFieldOptions() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("option", "select");

		ddmFormField.setDDMFormFieldOptions(null);

		_selectDDMFormFieldValueValidator.validate(ddmFormField, null);
	}

	private final SelectDDMFormFieldValueValidator
		_selectDDMFormFieldValueValidator =
			new SelectDDMFormFieldValueValidator();

}