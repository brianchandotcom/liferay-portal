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

package com.liferay.apio.architect.impl.wiring.validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.impl.wiring.osgi.validator.NameValidator;
import com.liferay.apio.architect.impl.wiring.osgi.validator.NameValidatorImpl;

import org.junit.Test;

/**
 * @author Víctor Galán
 */
public class NameValidatorImpTest {

	@Test
	public void testValidatorErrorMessageIsCorrect() {
		String error =
			"Name can not be null and can only contains alphabetic " +
				"characters and hyphens";

		assertThat(_nameValidator.getValidationError(), is(error));
	}

	@Test
	public void testValidatorShouldReturnFalseIfNameIsInvalid() {
		String emptyName = "";
		String nameWithSlash = "blog-posting/";
		String nameWithNumbers = "123blog";

		assertThat(_validate(emptyName), is(false));
		assertThat(_validate(nameWithSlash), is(false));
		assertThat(_validate(nameWithNumbers), is(false));
	}

	@Test
	public void testValidatorShouldReturnFalseIfNameIsNull() {
		String name = null;

		assertThat(_validate(name), is(false));
	}

	@Test
	public void testValidatorShouldReturnTrueIfNameIsValid() {
		String name = "blog-posting";

		assertThat(_validate(name), is(true));
	}

	private static boolean _validate(String name) {
		return _nameValidator.validate(name);
	}

	private static final NameValidator _nameValidator = new NameValidatorImpl();

}