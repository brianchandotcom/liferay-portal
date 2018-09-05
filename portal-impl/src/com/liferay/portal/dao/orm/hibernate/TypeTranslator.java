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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Type;

import org.hibernate.type.StandardBasicTypes;

/**
 * @author Brian Wing Shun Chan
 */
public class TypeTranslator {

	public static final org.hibernate.type.Type BIG_DECIMAL =
		StandardBasicTypes.BIG_DECIMAL;

	public static final org.hibernate.type.Type BIG_INTEGER =
		StandardBasicTypes.BIG_INTEGER;

	public static final org.hibernate.type.Type BINARY =
		StandardBasicTypes.BINARY;

	public static final org.hibernate.type.Type BLOB = StandardBasicTypes.BLOB;

	public static final org.hibernate.type.Type BOOLEAN =
		StandardBasicTypes.BOOLEAN;

	public static final org.hibernate.type.Type BYTE = StandardBasicTypes.BYTE;

	public static final org.hibernate.type.Type CALENDAR =
		StandardBasicTypes.CALENDAR;

	public static final org.hibernate.type.Type CALENDAR_DATE =
		StandardBasicTypes.CALENDAR_DATE;

	public static final org.hibernate.type.Type CHAR_ARRAY =
		StandardBasicTypes.CHAR_ARRAY;

	public static final org.hibernate.type.Type CHARACTER =
		StandardBasicTypes.CHARACTER;

	public static final org.hibernate.type.Type CHARACTER_ARRAY =
		StandardBasicTypes.CHARACTER_ARRAY;

	public static final org.hibernate.type.Type CLASS =
		StandardBasicTypes.CLASS;

	public static final org.hibernate.type.Type CLOB = StandardBasicTypes.CLOB;

	public static final org.hibernate.type.Type CURRENCY =
		StandardBasicTypes.CURRENCY;

	public static final org.hibernate.type.Type DATE = StandardBasicTypes.DATE;

	public static final org.hibernate.type.Type DOUBLE =
		StandardBasicTypes.DOUBLE;

	public static final org.hibernate.type.Type FLOAT =
		StandardBasicTypes.FLOAT;

	public static final org.hibernate.type.Type IMAGE =
		StandardBasicTypes.IMAGE;

	public static final org.hibernate.type.Type INTEGER =
		StandardBasicTypes.INTEGER;

	public static final org.hibernate.type.Type LOCALE =
		StandardBasicTypes.LOCALE;

	public static final org.hibernate.type.Type LONG = StandardBasicTypes.LONG;

	public static final org.hibernate.type.Type MATERIALIZED_BLOB =
		StandardBasicTypes.MATERIALIZED_BLOB;

	public static final org.hibernate.type.Type MATERIALIZED_CLOB =
		StandardBasicTypes.MATERIALIZED_CLOB;

	public static final org.hibernate.type.Type NUMERIC_BOOLEAN =
		StandardBasicTypes.NUMERIC_BOOLEAN;

	public static final org.hibernate.type.Type SERIALIZABLE =
		StandardBasicTypes.SERIALIZABLE;

	public static final org.hibernate.type.Type SHORT =
		StandardBasicTypes.SHORT;

	public static final org.hibernate.type.Type STRING =
		StandardBasicTypes.STRING;

	public static final org.hibernate.type.Type TEXT = StandardBasicTypes.TEXT;

	public static final org.hibernate.type.Type TIME = StandardBasicTypes.TIME;

	public static final org.hibernate.type.Type TIMESTAMP =
		StandardBasicTypes.TIMESTAMP;

	public static final org.hibernate.type.Type TIMEZONE =
		StandardBasicTypes.TIMEZONE;

	public static final org.hibernate.type.Type TRUE_FALSE =
		StandardBasicTypes.TRUE_FALSE;

	public static final org.hibernate.type.Type URL = StandardBasicTypes.URL;

	public static final org.hibernate.type.Type UUID_BINARY =
		StandardBasicTypes.UUID_BINARY;

	public static final org.hibernate.type.Type UUID_CHAR =
		StandardBasicTypes.UUID_CHAR;

	public static final org.hibernate.type.Type WRAPPER_BINARY =
		StandardBasicTypes.WRAPPER_BINARY;

	public static final org.hibernate.type.Type YES_NO =
		StandardBasicTypes.YES_NO;

	public static org.hibernate.type.Type translate(Type type) {
		if (type == Type.BIG_DECIMAL) {
			return BIG_DECIMAL;
		}
		else if (type == Type.BIG_INTEGER) {
			return BIG_INTEGER;
		}
		else if (type == Type.BINARY) {
			return BINARY;
		}
		else if (type == Type.BLOB) {
			return BLOB;
		}
		else if (type == Type.BOOLEAN) {
			return BOOLEAN;
		}
		else if (type == Type.BYTE) {
			return BYTE;
		}
		else if (type == Type.CALENDAR) {
			return CALENDAR;
		}
		else if (type == Type.CALENDAR_DATE) {
			return CALENDAR_DATE;
		}
		else if (type == Type.CHAR_ARRAY) {
			return CHAR_ARRAY;
		}
		else if (type == Type.CHARACTER) {
			return CHARACTER;
		}
		else if (type == Type.CHARACTER_ARRAY) {
			return CHARACTER_ARRAY;
		}
		else if (type == Type.CLASS) {
			return CLASS;
		}
		else if (type == Type.CLOB) {
			return CLOB;
		}
		else if (type == Type.CURRENCY) {
			return CURRENCY;
		}
		else if (type == Type.DATE) {
			return DATE;
		}
		else if (type == Type.DOUBLE) {
			return DOUBLE;
		}
		else if (type == Type.FLOAT) {
			return FLOAT;
		}
		else if (type == Type.IMAGE) {
			return IMAGE;
		}
		else if (type == Type.INTEGER) {
			return INTEGER;
		}
		else if (type == Type.LOCALE) {
			return LOCALE;
		}
		else if (type == Type.LONG) {
			return LONG;
		}
		else if (type == Type.MATERIALIZED_BLOB) {
			return MATERIALIZED_BLOB;
		}
		else if (type == Type.MATERIALIZED_CLOB) {
			return MATERIALIZED_CLOB;
		}
		else if (type == Type.NUMERIC_BOOLEAN) {
			return NUMERIC_BOOLEAN;
		}
		else if (type == Type.SERIALIZABLE) {
			return SERIALIZABLE;
		}
		else if (type == Type.SHORT) {
			return SHORT;
		}
		else if (type == Type.STRING) {
			return STRING;
		}
		else if (type == Type.TEXT) {
			return TEXT;
		}
		else if (type == Type.TIME) {
			return TIME;
		}
		else if (type == Type.TIMESTAMP) {
			return TIMESTAMP;
		}
		else if (type == Type.TIMEZONE) {
			return TIMEZONE;
		}
		else if (type == Type.TRUE_FALSE) {
			return TRUE_FALSE;
		}
		else if (type == Type.URL) {
			return URL;
		}
		else if (type == Type.UUID_BINARY) {
			return UUID_BINARY;
		}
		else if (type == Type.UUID_CHAR) {
			return UUID_CHAR;
		}
		else if (type == Type.WRAPPER_BINARY) {
			return WRAPPER_BINARY;
		}
		else if (type == Type.YES_NO) {
			return YES_NO;
		}

		return null;
	}

}