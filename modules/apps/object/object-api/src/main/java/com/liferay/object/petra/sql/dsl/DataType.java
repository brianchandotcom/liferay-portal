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

package com.liferay.object.petra.sql.dsl;

import java.math.BigDecimal;

import java.sql.Blob;
import java.sql.Types;

import java.util.Date;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public enum DataType {

	BIG_DECIMAL(
		"DECIMAL(30, 16)", BigDecimal.class, "BigDecimal", Types.DECIMAL),
	BLOB("BLOB", Blob.class, "Blob", Types.BLOB),
	BOOLEAN("BOOLEAN", Boolean.class, "Boolean", Types.BOOLEAN),
	CLOB("TEXT", String.class, "Clob", Types.CLOB),
	DATE("DATE", Date.class, "Date", Types.DATE),
	DOUBLE("DOUBLE", Double.class, "Double", Types.DOUBLE),
	INTEGER("INTEGER", Integer.class, "Integer", Types.INTEGER),
	LONG("LONG", Long.class, "Long", Types.BIGINT),
	STRING("VARCHAR(280)", String.class, "String", Types.VARCHAR);

	public static String getDataType(String name) {
		DataType dataType = _getDataType(name);

		return dataType._dataType;
	}

	public static Class<?> getJavaClass(String name) {
		DataType dataType = _getDataType(name);

		return dataType._javaClass;
	}

	public static Integer getSQLType(String name) {
		DataType dataType = _getDataType(name);

		return dataType._sqlType;
	}

	private static DataType _getDataType(String name) {
		for (DataType dataType : values()) {
			if (Objects.equals(dataType._name, name)) {
				return dataType;
			}
		}

		throw new IllegalArgumentException("Invalid type " + name);
	}

	private DataType(
		String dataType, Class<?> javaClass, String name, Integer sqlType) {

		_dataType = dataType;
		_javaClass = javaClass;
		_name = name;
		_sqlType = sqlType;
	}

	private final String _dataType;
	private final Class<?> _javaClass;
	private final String _name;
	private final Integer _sqlType;

}