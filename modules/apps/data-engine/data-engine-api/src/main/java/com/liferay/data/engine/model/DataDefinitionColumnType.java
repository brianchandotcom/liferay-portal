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

package com.liferay.data.engine.model;

/**
 * @author Leonardo Barros
 */
public enum DataDefinitionColumnType {

	BOOLEAN("boolean"), DATE("date"), NUMBER("number"), STRING("string");

	public static DataDefinitionColumnType parse(String value) {
		if (BOOLEAN.getValue().equals(value)) {
			return DATE;
		}
		else if (DATE.getValue().equals(value)) {
			return DATE;
		}
		else if (NUMBER.getValue().equals(value)) {
			return NUMBER;
		}
		else if (STRING.getValue().equals(value)) {
			return STRING;
		}
		else {
			throw new IllegalArgumentException("Invalid value " + value);
		}
	}

	public String getValue() {
		return _value;
	}

	private DataDefinitionColumnType(String value) {
		_value = value;
	}

	private final String _value;

}