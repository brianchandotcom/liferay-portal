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

package com.liferay.talend.common.oas;

/**
 * @author Igor Beslic
 */
public enum OASFormat {

	BIGDECIMAL("bigdecimal", OASType.NUMBER, true),
	BINARY("binary", OASType.STRING, false),
	BOOLEAN("boolean", OASType.BOOLEAN, true),
	BYTE("byte", OASType.STRING, false), DATE("date", OASType.STRING, false),
	DATE_TIME("date-time", OASType.STRING, false),
	DICTIONARY("string", OASType.OBJECT, true),
	DOUBLE("double", OASType.NUMBER, false),
	FLOAT("float", OASType.NUMBER, true), INT32("int32", OASType.INTEGER, true),
	INT64("int64", OASType.INTEGER, false), STRING(null, OASType.STRING, true);

	public static OASFormat fromOpenAPITypeAndFormat(
		OASType oasType, String openAPIFormatDefinition) {

		OASFormat defaultOASFormat = null;

		for (OASFormat oasFormat : values()) {
			if (oasType != oasFormat._oasType) {
				continue;
			}

			if ((openAPIFormatDefinition == null) && oasFormat._defaultFormat) {
				return oasFormat;
			}

			if ((openAPIFormatDefinition != null) &&
				openAPIFormatDefinition.equals(
					oasFormat._openAPIFormatDefinition)) {

				return oasFormat;
			}

			if (oasFormat._defaultFormat) {
				defaultOASFormat = oasFormat;
			}
		}

		return defaultOASFormat;
	}

	private OASFormat(
		String openAPIFormatDefinition, OASType oasType,
		boolean defaultFormat) {

		_openAPIFormatDefinition = openAPIFormatDefinition;
		_oasType = oasType;
		_defaultFormat = defaultFormat;
	}

	private final boolean _defaultFormat;
	private final OASType _oasType;
	private final String _openAPIFormatDefinition;

}