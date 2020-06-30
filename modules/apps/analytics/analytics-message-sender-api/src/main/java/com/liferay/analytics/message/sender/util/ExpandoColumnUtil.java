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

package com.liferay.analytics.message.sender.util;

import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Rachael Koestartyo
 */
public class ExpandoColumnUtil {

	public static final String DATA_TYPE_DECIMAL = "Decimal";

	public static final String DATA_TYPE_INTEGER = "Integer";

	public static final String DATA_TYPE_TEXT = "Text";

	public static final String PROPERTY_DISPLAY_TYPE_BOOLEAN = "boolean";

	public static final String PROPERTY_DISPLAY_TYPE_DATE = "date";

	public static final String PROPERTY_DISPLAY_TYPE_GEOLOCATION =
		"geolocation";

	public static final String PROPERTY_DISPLAY_TYPE_INPUT_FIELD =
		"input-field";

	public static final String getDataType(int type) {
		if ((type == ExpandoColumnConstants.DOUBLE) ||
			(type == ExpandoColumnConstants.DOUBLE_ARRAY) ||
			(type == ExpandoColumnConstants.FLOAT) ||
			(type == ExpandoColumnConstants.FLOAT_ARRAY)) {

			return DATA_TYPE_DECIMAL;
		}
		else if ((type == ExpandoColumnConstants.INTEGER) ||
				 (type == ExpandoColumnConstants.INTEGER_ARRAY) ||
				 (type == ExpandoColumnConstants.LONG) ||
				 (type == ExpandoColumnConstants.LONG_ARRAY) ||
				 (type == ExpandoColumnConstants.SHORT) ||
				 (type == ExpandoColumnConstants.SHORT_ARRAY)) {

			return DATA_TYPE_INTEGER;
		}
		else if ((type == ExpandoColumnConstants.STRING) ||
				 (type == ExpandoColumnConstants.STRING_ARRAY) ||
				 (type == ExpandoColumnConstants.STRING_LOCALIZED)) {

			return DATA_TYPE_TEXT;
		}

		return StringPool.BLANK;
	}

	public static final String getDefaultDisplayTypeProperty(
		int type, UnicodeProperties properties) {

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return PROPERTY_DISPLAY_TYPE_BOOLEAN;
		}
		else if ((type == ExpandoColumnConstants.BOOLEAN_ARRAY) ||
				 (type == ExpandoColumnConstants.DATE_ARRAY) ||
				 (type == ExpandoColumnConstants.DOUBLE_ARRAY) ||
				 (type == ExpandoColumnConstants.FLOAT_ARRAY) ||
				 (type == ExpandoColumnConstants.INTEGER_ARRAY) ||
				 (type == ExpandoColumnConstants.LONG_ARRAY) ||
				 (type == ExpandoColumnConstants.NUMBER_ARRAY) ||
				 (type == ExpandoColumnConstants.SHORT_ARRAY) ||
				 (type == ExpandoColumnConstants.STRING_ARRAY) ||
				 (type == ExpandoColumnConstants.STRING_ARRAY_LOCALIZED)) {

			return ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST;
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return PROPERTY_DISPLAY_TYPE_DATE;
		}
		else if (type == ExpandoColumnConstants.GEOLOCATION) {
			return PROPERTY_DISPLAY_TYPE_GEOLOCATION;
		}
		else if ((type == ExpandoColumnConstants.STRING) ||
				 (type == ExpandoColumnConstants.STRING_LOCALIZED)) {

			int propertyHeight = GetterUtil.getInteger(
				properties.get(ExpandoColumnConstants.PROPERTY_HEIGHT));

			if (propertyHeight > 0) {
				return ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX;
			}

			return PROPERTY_DISPLAY_TYPE_INPUT_FIELD;
		}

		return StringPool.BLANK;
	}

}