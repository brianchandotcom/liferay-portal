/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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