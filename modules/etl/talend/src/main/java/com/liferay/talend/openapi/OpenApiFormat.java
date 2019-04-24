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
public enum OpenApiFormat {

	BIGDECIMAL("bigdecimal", OpenApiType.NUMBER, true),
	BINARY("binary", OpenApiType.STRING, false),
	BOOLEAN("boolean", OpenApiType.BOOLEAN, true),
	BYTE("byte", OpenApiType.STRING, false),
	DATE("date", OpenApiType.STRING, false),
	DATE_TIME("date-time", OpenApiType.STRING, false),
	DICTIONARY(null, OpenApiType.DICTIONARY, true),
	DOUBLE("double", OpenApiType.NUMBER, false),
	FLOAT("float", OpenApiType.NUMBER, true),
	INT32("int32", OpenApiType.INTEGER, true),
	INT64("int64", OpenApiType.INTEGER, false),
	STRING(null, OpenApiType.STRING, true);

	public static OpenApiFormat fromOpenApiTypeAndFormat(
		OpenApiType openApiType, String openApiFormatDefinition) {

		OpenApiFormat defaultOpenApiFormat = null;

		for (OpenApiFormat openApiFormat : values()) {
			if (openApiType != openApiFormat._openApiType) {
				continue;
			}

			if ((openApiFormatDefinition == null) && openApiFormat._default) {
				return openApiFormat;
			}

			if ((openApiFormatDefinition != null) &&
				openApiFormatDefinition.equals(
					openApiFormat._openApiFormatDefinition)) {

				return openApiFormat;
			}

			if (openApiFormat._default) {
				defaultOpenApiFormat = openApiFormat;
			}
		}

		return defaultOpenApiFormat;
	}

	private OpenApiFormat(
		String openApiFormatDefinition, OpenApiType openApiType,
		boolean defaultFormat) {

		_default = defaultFormat;
		_openApiFormatDefinition = openApiFormatDefinition;
		_openApiType = openApiType;
	}

	private final boolean _default;
	private final String _openApiFormatDefinition;
	private final OpenApiType _openApiType;

}