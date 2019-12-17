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

package com.liferay.portal.kernel.dao.model.dsl.functions;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class FunctionType {

	public static final FunctionType ADDITION = new FunctionType(" + ");

	public static final FunctionType BITWISE_AND = new FunctionType(
		"BITAND(", ")");

	public static final FunctionType CAST_CLOB_TEXT = new FunctionType(
		"CAST_CLOB_TEXT(", ")");

	public static final FunctionType CAST_LONG = new FunctionType(
		"CAST_LONG(", ")");

	public static final FunctionType CAST_TEXT = new FunctionType(
		"CAST_TEXT(", ")");

	public static final FunctionType CONCAT = new FunctionType("CONCAT(", ")");

	public static final FunctionType DIVISION = new FunctionType(" / ");

	public static final FunctionType LOWER = new FunctionType("LOWER(", ")");

	public static final FunctionType MULTIPLICATION = new FunctionType(" * ");

	public static final FunctionType SUBTRACTION = new FunctionType(" - ");

	public FunctionType(String delimiter) {
		this("", delimiter, "");
	}

	public FunctionType(String prefix, String postfix) {
		this(prefix, ", ", postfix);
	}

	public FunctionType(String prefix, String delimiter, String postfix) {
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