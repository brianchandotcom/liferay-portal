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
public class DSLFunctionType {

	public static final DSLFunctionType ADDITION = new DSLFunctionType(" + ");

	public static final DSLFunctionType BITWISE_AND = new DSLFunctionType(
		"BITAND(", ")");

	public static final DSLFunctionType CAST_CLOB_TEXT = new DSLFunctionType(
		"CAST_CLOB_TEXT(", ")");

	public static final DSLFunctionType CAST_LONG = new DSLFunctionType(
		"CAST_LONG(", ")");

	public static final DSLFunctionType CAST_TEXT = new DSLFunctionType(
		"CAST_TEXT(", ")");

	public static final DSLFunctionType CONCAT = new DSLFunctionType(
		"CONCAT(", ")");

	public static final DSLFunctionType DIVISION = new DSLFunctionType(" / ");

	public static final DSLFunctionType LOWER = new DSLFunctionType(
		"LOWER(", ")");

	public static final DSLFunctionType MULTIPLICATION = new DSLFunctionType(
		" * ");

	public static final DSLFunctionType SUBTRACTION = new DSLFunctionType(
		" - ");

	public DSLFunctionType(String delimiter) {
		this("", delimiter, "");
	}

	public DSLFunctionType(String prefix, String postfix) {
		this(prefix, ", ", postfix);
	}

	public DSLFunctionType(String prefix, String delimiter, String postfix) {
		_prefix = Objects.requireNonNull(prefix);
		_delimiter = Objects.requireNonNull(delimiter);
		_postfix = Objects.requireNonNull(postfix);
	}

	public String getDelimiter() {
		return _delimiter;
	}

	public String getPostfix() {
		return _postfix;
	}

	public String getPrefix() {
		return _prefix;
	}

	private final String _delimiter;
	private final String _postfix;
	private final String _prefix;

}