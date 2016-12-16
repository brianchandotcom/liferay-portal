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

package com.liferay.dynamic.data.mapping.type.select.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidator;
import com.liferay.dynamic.data.mapping.type.BaseDDMFormFieldOptionsValidationTest;
import com.liferay.portal.json.JSONFactoryImpl;

import org.junit.Before;

/**
 * @author Marcellus Tavares
 */
public class SelectDDMFormFieldValueValidatorTest
	extends BaseDDMFormFieldOptionsValidationTest {

	@Before
	public void setUp() {
		setUpDDMFormFieldValueValidator();
	}

	@Override
	protected DDMFormFieldValueValidator getDDMFormFieldValueValidator() {
		return _selectDDMFormFieldValueValidator;
	}

	protected void setUpDDMFormFieldValueValidator() {
		_selectDDMFormFieldValueValidator.jsonFactory = new JSONFactoryImpl();
	}

	private final SelectDDMFormFieldValueValidator
		_selectDDMFormFieldValueValidator =
			new SelectDDMFormFieldValueValidator();

}