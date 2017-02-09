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

package com.liferay.dynamic.data.mapping.type.grid.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidator;
import com.liferay.dynamic.data.mapping.type.BaseDDMFormFieldOptionsValidationTest;
import com.liferay.portal.json.JSONFactoryImpl;

import org.junit.Before;

/**
 * @author Pedro Queiroz
 */
public class GridDDMFormFieldValueValidatorTest
	extends BaseDDMFormFieldOptionsValidationTest {

	@Before
	public void setUp() {
		setUpDDMFormFieldValueValidator();
	}

	@Override
	protected DDMFormFieldValueValidator getDDMFormFieldValueValidator() {
		return _gridDDMFormFieldValueValidator;
	}

	protected void setUpDDMFormFieldValueValidator() {
		_gridDDMFormFieldValueValidator.jsonFactory = new JSONFactoryImpl();
	}

	private final GridDDMFormFieldValueValidator
		_gridDDMFormFieldValueValidator = new GridDDMFormFieldValueValidator();

}