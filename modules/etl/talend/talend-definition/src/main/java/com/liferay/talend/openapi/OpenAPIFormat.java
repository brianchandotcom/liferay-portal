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

package com.liferay.talend.openapi;

/**
 * @author Igor Beslic
 */
public enum OpenAPIFormat {

	BIGDECIMAL("bigdecimal", OpenAPIType.NUMBER, true),
	BINARY("binary", OpenAPIType.STRING, false),
	BOOLEAN("boolean", OpenAPIType.BOOLEAN, true),
	BYTE("byte", OpenAPIType.STRING, false),
	DATE("date", OpenAPIType.STRING, false),
	DATE_TIME("date-time", OpenAPIType.STRING, false),
	DICTIONARY("string", OpenAPIType.OBJECT, true),
	DOUBLE("double", OpenAPIType.NUMBER, false),
	FLOAT("float", OpenAPIType.NUMBER, true),
	INT32("int32", OpenAPIType.INTEGER, true),
	INT64("int64", OpenAPIType.INTEGER, false),
	STRING(null, OpenAPIType.STRING, true);

	public static OpenAPIFormat fromOpenAPITypeAndFormat(
		OpenAPIType openAPIType, String openAPIFormatDefinition) {

		OpenAPIFormat defaultOpenAPIFormat = null;

		for (OpenAPIFormat openAPIFormat : values()) {
			if (openAPIType != openAPIFormat._openAPIType) {
				continue;
			}

			if ((openAPIFormatDefinition == null) && openAPIFormat._default) {
				return openAPIFormat;
			}

			if ((openAPIFormatDefinition != null) &&
				openAPIFormatDefinition.equals(
					openAPIFormat._openAPIFormatDefinition)) {

				return openAPIFormat;
			}

			if (openAPIFormat._default) {
				defaultOpenAPIFormat = openAPIFormat;
			}
		}

		return defaultOpenAPIFormat;
	}

	private OpenAPIFormat(
		String openAPIFormatDefinition, OpenAPIType openAPIType,
		boolean defaultFormat) {

		_default = defaultFormat;
		_openAPIFormatDefinition = openAPIFormatDefinition;
		_openAPIType = openAPIType;
	}

	private final boolean _default;
	private final String _openAPIFormatDefinition;
	private final OpenAPIType _openAPIType;

}