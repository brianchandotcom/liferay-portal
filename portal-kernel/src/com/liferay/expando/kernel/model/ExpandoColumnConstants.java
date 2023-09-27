/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.model;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;

/**
 * @author Raymond Augé
 * @author Alexander Chow
 * @author Marcellus Tavares
 */
public class ExpandoColumnConstants {

	public static final int BOOLEAN = 1;

	public static final int BOOLEAN_ARRAY = 2;

	public static final String BOOLEAN_ARRAY_LABEL =
		"custom.field.boolean.array";

	public static final String BOOLEAN_LABEL = "custom.field.boolean";

	public static final String DATA_TYPE_DECIMAL = "Decimal";

	public static final String DATA_TYPE_INTEGER = "Integer";

	public static final String DATA_TYPE_TEXT = "Text";

	public static final int DATE = 3;

	public static final int DATE_ARRAY = 4;

	public static final String DATE_ARRAY_LABEL =
		"custom.field.java.util.Date.array";

	public static final String DATE_LABEL = "custom.field.java.util.Date";

	public static final int DOUBLE = 5;

	public static final int DOUBLE_ARRAY = 6;

	public static final String DOUBLE_ARRAY_LABEL = "custom.field.double.array";

	public static final String DOUBLE_LABEL = "custom.field.double";

	public static final int FLOAT = 7;

	public static final int FLOAT_ARRAY = 8;

	public static final String FLOAT_ARRAY_LABEL = "custom.field.float.array";

	public static final String FLOAT_LABEL = "custom.field.float";

	public static final int GEOLOCATION = 21;

	public static final String GEOLOCATION_LABEL = "custom.field.geolocation";

	public static final String INDEX_TYPE = "index-type";

	public static final int INDEX_TYPE_KEYWORD = 2;

	public static final int INDEX_TYPE_NONE = 0;

	public static final int INDEX_TYPE_TEXT = 1;

	public static final int INTEGER = 9;

	public static final int INTEGER_ARRAY = 10;

	public static final String INTEGER_ARRAY_LABEL = "custom.field.int.array";

	public static final String INTEGER_LABEL = "custom.field.int";

	public static final int LONG = 11;

	public static final int LONG_ARRAY = 12;

	public static final String LONG_ARRAY_LABEL = "custom.field.long.array";

	public static final String LONG_LABEL = "custom.field.long";

	public static final int NUMBER = 17;

	public static final int NUMBER_ARRAY = 18;

	public static final String NUMBER_ARRAY_LABEL = "custom.field.number.array";

	public static final String NUMBER_LABEL = "custom.field.number";

	public static final String PRECISION_16_BIT = "16-bit";

	public static final String PRECISION_32_BIT = "32-bit";

	public static final String PRECISION_64_BIT = "64-bit";

	public static final String PROPERTY_DISPLAY_TYPE = "display-type";

	public static final String PROPERTY_DISPLAY_TYPE_BOOLEAN = "boolean";

	public static final String PROPERTY_DISPLAY_TYPE_CHECKBOX = "checkbox";

	public static final String PROPERTY_DISPLAY_TYPE_DATE = "date";

	public static final String PROPERTY_DISPLAY_TYPE_GEOLOCATION =
		"geolocation";

	public static final String PROPERTY_DISPLAY_TYPE_INPUT_FIELD =
		"input-field";

	public static final String PROPERTY_DISPLAY_TYPE_RADIO = "radio";

	public static final String PROPERTY_DISPLAY_TYPE_SELECTION_LIST =
		"selection-list";

	public static final String PROPERTY_DISPLAY_TYPE_TEXT_BOX = "text-box";

	public static final String PROPERTY_HEIGHT = "height";

	public static final int PROPERTY_HEIGHT_DEFAULT = 150;

	public static final String PROPERTY_HIDDEN = "hidden";

	public static final String PROPERTY_LOCALIZE_FIELD_NAME =
		"localize-field-name";

	public static final String PROPERTY_SECRET = "secret";

	public static final String PROPERTY_VISIBLE_WITH_UPDATE_PERMISSION =
		"visible-with-update-permission";

	public static final String PROPERTY_WIDTH = "width";

	public static final int SHORT = 13;

	public static final int SHORT_ARRAY = 14;

	public static final String SHORT_ARRAY_LABEL = "custom.field.short.array";

	public static final String SHORT_LABEL = "custom.field.short";

	public static final int STRING = 15;

	public static final int STRING_ARRAY = 16;

	public static final String STRING_ARRAY_LABEL =
		"custom.field.java.lang.String.array";

	public static final int STRING_ARRAY_LOCALIZED = 19;

	public static final String STRING_ARRAY_LOCALIZED_LABEL =
		"custom.field.java.lang.String.array.localized";

	public static final String STRING_LABEL = "custom.field.java.lang.String";

	public static final int STRING_LOCALIZED = 20;

	public static final String STRING_LOCALIZED_LABEL =
		"custom.field.java.lang.String.localized";

	public static final int[] TYPES = {
		BOOLEAN, BOOLEAN_ARRAY, DATE, DATE_ARRAY, DOUBLE, DOUBLE_ARRAY, FLOAT,
		FLOAT_ARRAY, GEOLOCATION, INTEGER, INTEGER_ARRAY, LONG, LONG_ARRAY,
		NUMBER, NUMBER_ARRAY, SHORT, SHORT_ARRAY, STRING, STRING_ARRAY,
		STRING_ARRAY_LOCALIZED, STRING_LOCALIZED
	};

	public static final String UNKNOWN_LABEL = "Unknown";

	public static String getDataType(int type) {
		if ((type == DOUBLE) || (type == DOUBLE_ARRAY) || (type == FLOAT) ||
			(type == FLOAT_ARRAY)) {

			return DATA_TYPE_DECIMAL;
		}
		else if ((type == INTEGER) || (type == INTEGER_ARRAY) ||
				 (type == LONG) || (type == LONG_ARRAY) || (type == SHORT) ||
				 (type == SHORT_ARRAY)) {

			return DATA_TYPE_INTEGER;
		}
		else if ((type == STRING) || (type == STRING_ARRAY) ||
				 (type == STRING_LOCALIZED)) {

			return DATA_TYPE_TEXT;
		}

		return StringPool.BLANK;
	}

	public static String getDefaultDisplayTypeProperty(
		int type, UnicodeProperties unicodeProperties) {

		if (type == BOOLEAN) {
			return PROPERTY_DISPLAY_TYPE_BOOLEAN;
		}
		else if ((type == BOOLEAN_ARRAY) || (type == DATE_ARRAY) ||
				 (type == DOUBLE_ARRAY) || (type == FLOAT_ARRAY) ||
				 (type == INTEGER_ARRAY) || (type == LONG_ARRAY) ||
				 (type == NUMBER_ARRAY) || (type == SHORT_ARRAY) ||
				 (type == STRING_ARRAY) || (type == STRING_ARRAY_LOCALIZED)) {

			return PROPERTY_DISPLAY_TYPE_SELECTION_LIST;
		}
		else if (type == DATE) {
			return PROPERTY_DISPLAY_TYPE_DATE;
		}
		else if (type == GEOLOCATION) {
			return PROPERTY_DISPLAY_TYPE_GEOLOCATION;
		}
		else if ((type == STRING) || (type == STRING_LOCALIZED)) {
			int propertyHeight = GetterUtil.getInteger(
				unicodeProperties.get(PROPERTY_HEIGHT));

			if (propertyHeight > 0) {
				return PROPERTY_DISPLAY_TYPE_TEXT_BOX;
			}

			return PROPERTY_DISPLAY_TYPE_INPUT_FIELD;
		}

		return StringPool.BLANK;
	}

	public static String getPrecisionType(int type) {
		if ((type == DOUBLE) || (type == DOUBLE_ARRAY) || (type == LONG) ||
			(type == LONG_ARRAY)) {

			return PRECISION_64_BIT;
		}
		else if ((type == FLOAT) || (type == FLOAT_ARRAY) ||
				 (type == INTEGER) || (type == INTEGER_ARRAY)) {

			return PRECISION_32_BIT;
		}
		else if ((type == SHORT) || (type == SHORT_ARRAY)) {
			return PRECISION_16_BIT;
		}

		return StringPool.BLANK;
	}

	public static Serializable getSerializable(int type, String value) {
		if (type == BOOLEAN) {
			return GetterUtil.getBoolean(value);
		}
		else if (type == BOOLEAN_ARRAY) {
			return new Boolean[] {GetterUtil.getBoolean(value)};
		}
		else if (type == DATE) {
			try {
				DateFormat dateFormat = DateFormatFactoryUtil.getDateTime(
					LocaleUtil.getDefault());

				return dateFormat.parse(value);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to parse date " + value, exception);
				}
			}
		}
		else if (type == DATE_ARRAY) {
			Serializable dateSerializable = getSerializable(DATE, value);

			if (dateSerializable instanceof Date) {
				return new Date[] {(Date)dateSerializable};
			}
		}
		else if (type == DOUBLE) {
			return GetterUtil.getDouble(value);
		}
		else if (type == DOUBLE_ARRAY) {
			return new double[] {GetterUtil.getDouble(value)};
		}
		else if (type == FLOAT) {
			return GetterUtil.getFloat(value);
		}
		else if (type == FLOAT_ARRAY) {
			return new float[] {GetterUtil.getFloat(value)};
		}
		else if (type == INTEGER) {
			return GetterUtil.getInteger(value);
		}
		else if (type == INTEGER_ARRAY) {
			return new int[] {GetterUtil.getInteger(value)};
		}
		else if (type == LONG) {
			return GetterUtil.getLong(value);
		}
		else if (type == LONG_ARRAY) {
			return new long[] {GetterUtil.getLong(value)};
		}
		else if (type == NUMBER) {
			return GetterUtil.getNumber(value);
		}
		else if (type == NUMBER_ARRAY) {
			return new Number[] {GetterUtil.getNumber(value)};
		}
		else if (type == SHORT) {
			return GetterUtil.getShort(value);
		}
		else if (type == SHORT_ARRAY) {
			return new short[] {GetterUtil.getShort(value)};
		}
		else if (type == STRING_ARRAY) {
			return new String[] {value};
		}

		return value;
	}

	public static String getTypeLabel(int type) {
		if (type == BOOLEAN) {
			return BOOLEAN_LABEL;
		}
		else if (type == BOOLEAN_ARRAY) {
			return BOOLEAN_ARRAY_LABEL;
		}
		else if (type == DATE) {
			return DATE_LABEL;
		}
		else if (type == DATE_ARRAY) {
			return DATE_ARRAY_LABEL;
		}
		else if (type == DOUBLE) {
			return DOUBLE_LABEL;
		}
		else if (type == DOUBLE_ARRAY) {
			return DOUBLE_ARRAY_LABEL;
		}
		else if (type == FLOAT) {
			return FLOAT_LABEL;
		}
		else if (type == FLOAT_ARRAY) {
			return FLOAT_ARRAY_LABEL;
		}
		else if (type == GEOLOCATION) {
			return GEOLOCATION_LABEL;
		}
		else if (type == INTEGER) {
			return INTEGER_LABEL;
		}
		else if (type == INTEGER_ARRAY) {
			return INTEGER_ARRAY_LABEL;
		}
		else if (type == LONG) {
			return LONG_LABEL;
		}
		else if (type == LONG_ARRAY) {
			return LONG_ARRAY_LABEL;
		}
		else if (type == NUMBER) {
			return NUMBER_LABEL;
		}
		else if (type == NUMBER_ARRAY) {
			return NUMBER_ARRAY_LABEL;
		}
		else if (type == SHORT) {
			return SHORT_LABEL;
		}
		else if (type == SHORT_ARRAY) {
			return SHORT_ARRAY_LABEL;
		}
		else if (type == STRING) {
			return STRING_LABEL;
		}
		else if (type == STRING_ARRAY) {
			return STRING_ARRAY_LABEL;
		}
		else if (type == STRING_ARRAY_LOCALIZED) {
			return STRING_ARRAY_LOCALIZED_LABEL;
		}
		else if (type == STRING_LOCALIZED) {
			return STRING_LOCALIZED_LABEL;
		}

		return UNKNOWN_LABEL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoColumnConstants.class);

}