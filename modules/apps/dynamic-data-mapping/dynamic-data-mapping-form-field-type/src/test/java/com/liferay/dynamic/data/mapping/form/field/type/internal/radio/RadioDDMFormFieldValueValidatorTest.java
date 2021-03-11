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

package com.liferay.dynamic.data.mapping.form.field.type.internal.radio;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldOptionsValidationTestCase;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Marcellus Tavares
 */
public class RadioDDMFormFieldValueValidatorTest
	extends BaseDDMFormFieldOptionsValidationTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	protected DDMFormFieldValueValidator getDDMFormFieldValueValidator() {
		return _radioDDMFormFieldValueValidator;
	}

	private final RadioDDMFormFieldValueValidator
		_radioDDMFormFieldValueValidator =
			new RadioDDMFormFieldValueValidator();

}