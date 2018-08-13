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

package com.liferay.apio.architect.impl.wiring.osgi.validator;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Víctor Galán
 */
@Component
public class NameValidatorImpl implements NameValidator {

	@Override
	public String getValidationError() {
		return "Name can not be null and can only contains alphabetic " +
			"characters and hyphens";
	}

	@Override
	public boolean validate(String name) {
		return Optional.ofNullable(
			name
		).map(
			string -> string.matches(_VALID_CHARACTERS_REGEX)
		).orElse(
			false
		);
	}

	private static final String _VALID_CHARACTERS_REGEX = "[a-zA-Z-]+";

}