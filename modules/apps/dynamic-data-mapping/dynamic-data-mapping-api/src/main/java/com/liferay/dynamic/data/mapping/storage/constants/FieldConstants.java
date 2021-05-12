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

package com.liferay.dynamic.data.mapping.storage.constants;

import com.liferay.dynamic.data.mapping.util.NumberUtil;
import com.liferay.dynamic.data.mapping.util.NumericDDMFormFieldUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public class FieldConstants {

	public static final String BOOLEAN = "boolean";

	public static final String DATA_TYPE = "dataType";

	public static final String DATE = "date";

	public static final String DOCUMENT_LIBRARY = "document-library";

	public static final String DOUBLE = "double";

	public static final String EDITABLE = "editable";

	public static final String FLOAT = "float";

	public static final String HTML = "html";

	public static final String IMAGE = "image";

	public static final String INTEGER = "integer";

	public static final String LABEL = "label";

	public static final String LONG = "long";

	public static final String NAME = "name";

	public static final String NUMBER = "number";

	public static final String PREDEFINED_VALUE = "predefinedValue";

	public static final String PRIVATE = "private";

	public static final String REQUIRED = "required";

	public static final String SHORT = "short";

	public static final String SHOW = "showLabel";

	public static final String SORTABLE = "sortable";

	public static final String STRING = "string";

	public static final String TYPE = "type";

	public static final String VALUE = "value";

	public static Serializable getSerializable(
		Locale defaultLocale, Locale locale, String type, String value) {

		Serializable serializable = null;

		if (isNumericType(type)) {
			DecimalFormat decimalFormat = null;

			if (locale.equals(LocaleUtil.ROOT)) {
				decimalFormat = NumericDDMFormFieldUtil.getDecimalFormat(
					defaultLocale);
			}
			else {
				decimalFormat = NumericDDMFormFieldUtil.getDecimalFormat(
					locale);
			}

			if (type.equals(FieldConstants.DOUBLE) ||
				type.equals(FieldConstants.FLOAT)) {

				decimalFormat.setMinimumFractionDigits(1);
			}

			value = GetterUtil.getString(value);

			try {
				Number number = decimalFormat.parse(
					GetterUtil.getString(value));

				String formattedValue = String.valueOf(number);

				if ((number.doubleValue() > Integer.MAX_VALUE) ||
					formattedValue.matches(_SCIENTIFIC_NOTATION_PATTERN)) {

					return value;
				}

				DecimalFormatSymbols decimalFormatSymbols =
					decimalFormat.getDecimalFormatSymbols();

				String[] valueParts = StringUtil.split(
					value, decimalFormatSymbols.getDecimalSeparator());

				if (valueParts.length > 1) {
					String decimalPart = valueParts[1];

					if ((decimalPart.length() > 1) &&
						StringUtil.endsWith(decimalPart, "0")) {

						return value;
					}
				}

				if (!NumberUtil.hasDecimalSeparator(formattedValue) &&
					NumberUtil.hasDecimalSeparator(value)) {

					formattedValue = StringBundler.concat(
						formattedValue, StringPool.PERIOD,
						value.substring(
							NumberUtil.getDecimalSeparatorIndex(value) + 1));
				}

				serializable = getSerializable(type, formattedValue);
			}
			catch (ParseException parseException) {
				if (_log.isDebugEnabled()) {
					_log.debug(parseException, parseException);
				}

				serializable = getSerializable(type, value);
			}
		}
		else {
			serializable = getSerializable(type, value);
		}

		return serializable;
	}

	public static Serializable getSerializable(
		String type, List<Serializable> values) {

		if (Validator.isNull(type)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid type " + type);
			}

			return values.toArray(new String[0]);
		}

		if (isNumericType(type)) {
			values.removeAll(Collections.singleton(StringPool.BLANK));
		}

		if (type.equals(FieldConstants.BOOLEAN)) {
			return values.toArray(new Boolean[0]);
		}
		else if (type.equals(FieldConstants.DATE)) {
			return values.toArray(new String[0]);
		}
		else if (type.equals(FieldConstants.DOUBLE)) {
			return values.toArray(new Double[0]);
		}
		else if (type.equals(FieldConstants.FLOAT)) {
			return values.toArray(new Float[0]);
		}
		else if (type.equals(FieldConstants.INTEGER)) {
			return values.toArray(new Integer[0]);
		}
		else if (type.equals(FieldConstants.LONG)) {
			return values.toArray(new Long[0]);
		}
		else if (type.equals(FieldConstants.NUMBER)) {
			return values.toArray(new Number[0]);
		}
		else if (type.equals(FieldConstants.SHORT)) {
			return values.toArray(new Short[0]);
		}

		return values.toArray(new String[0]);
	}

	public static Serializable getSerializable(String type, String value) {
		if (Validator.isNull(type)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid type " + type);
			}

			return value;
		}

		if (isNumericType(type) && Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		if (type.equals(BOOLEAN)) {
			return GetterUtil.getBoolean(value);
		}
		else if (type.equals(DATE) && Validator.isNotNull(value)) {
			return value;
		}
		else if (type.equals(DOUBLE)) {
			if (!NumberUtil.hasDecimalSeparator(value)) {
				return GetterUtil.getInteger(value);
			}

			return GetterUtil.getDouble(value);
		}
		else if (type.equals(FLOAT)) {
			if (!NumberUtil.hasDecimalSeparator(value)) {
				return GetterUtil.getInteger(value);
			}

			return GetterUtil.getFloat(value);
		}
		else if (type.equals(INTEGER)) {
			return GetterUtil.getInteger(value);
		}
		else if (type.equals(LONG)) {
			return GetterUtil.getLong(value);
		}
		else if (type.equals(NUMBER)) {
			return GetterUtil.getNumber(value);
		}
		else if (type.equals(SHORT)) {
			return GetterUtil.getShort(value);
		}

		return value;
	}

	public static boolean isNumericType(String type) {
		if (type.equals(DOUBLE) || type.equals(FLOAT) || type.equals(INTEGER) ||
			type.equals(LONG) || type.equals(NUMBER) || type.equals(SHORT)) {

			return true;
		}

		return false;
	}

	private static final String _SCIENTIFIC_NOTATION_PATTERN =
		"^[+-]?\\d+(?:\\.\\d*(?:[eE][+-]?\\d+)+)+$";

	private static final Log _log = LogFactoryUtil.getLog(FieldConstants.class);

}