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

package com.liferay.petra.sql.dsl.spi.expression;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class Operand {

	public static final Operand AND = new Operand("and");

	public static final Operand EQUAL = new Operand("=");

	public static final Operand GREATER_THAN = new Operand(">");

	public static final Operand GREATER_THAN_OR_EQUAL = new Operand(">=");

	public static final Operand IN = new Operand("in");

	public static final Operand IS = new Operand("is");

	public static final Operand IS_NOT = new Operand("is not");

	public static final Operand LESS_THAN = new Operand("<");

	public static final Operand LESS_THAN_OR_EQUAL = new Operand("<=");

	public static final Operand LIKE = new Operand("like");

	public static final Operand NOT_EQUAL = new Operand("!=");

	public static final Operand NOT_IN = new Operand("not in");

	public static final Operand NOT_LIKE = new Operand("not like");

	public static final Operand OR = new Operand("or");

	public Operand(String value) {
		_value = Objects.requireNonNull(value);

		_valueWithSpaces = " ".concat(_value.concat(" "));
	}

	public String getStringWithSpaces() {
		return _valueWithSpaces;
	}

	@Override
	public String toString() {
		return _value;
	}

	private final String _value;
	private final String _valueWithSpaces;

}