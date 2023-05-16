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

package com.liferay.object.field.util;

import java.math.BigDecimal;

import java.sql.Blob;
import java.sql.Types;

import java.util.Date;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public enum DBType {

	BIG_DECIMAL(
		"DECIMAL(30, 16)", "BigDecimal", BigDecimal.class, Types.DECIMAL),
	BLOB("BLOB", "Blob", Blob.class, Types.BLOB),
	BOOLEAN("BOOLEAN", "Boolean", Boolean.class, Types.BOOLEAN),
	CLOB("TEXT", "Clob", String.class, Types.CLOB),
	DATE("DATE", "Date", Date.class, Types.DATE),
	DOUBLE("DOUBLE", "Double", Double.class, Types.DOUBLE),
	INTEGER("INTEGER", "Integer", Integer.class, Types.INTEGER),
	LONG("LONG", "Long", Long.class, Types.BIGINT),
	STRING("VARCHAR(280)", "String", String.class, Types.VARCHAR);

	public static String getDataType(String dbTypeString) {
		DBType dbType = getDBType(dbTypeString);

		return dbType._dataType;
	}

	public static DBType getDBType(String dbTypeString) {
		for (DBType dbType : values()) {
			if (Objects.equals(dbType._dbType, dbTypeString)) {
				return dbType;
			}
		}

		throw new IllegalArgumentException("Invalid dbType " + dbTypeString);
	}

	public static Class<?> getJavaClass(String dbTypeString) {
		DBType dbType = getDBType(dbTypeString);

		return dbType._javaClass;
	}

	public static Integer getSQLType(String dbTypeString) {
		DBType dbType = getDBType(dbTypeString);

		return dbType._sqlType;
	}

	private DBType(
		String dataType, String dbType, Class<?> javaClass, Integer sqlType) {

		_dataType = dataType;
		_dbType = dbType;
		_javaClass = javaClass;
		_sqlType = sqlType;
	}

	private final String _dataType;
	private final String _dbType;
	private final Class<?> _javaClass;
	private final Integer _sqlType;

}