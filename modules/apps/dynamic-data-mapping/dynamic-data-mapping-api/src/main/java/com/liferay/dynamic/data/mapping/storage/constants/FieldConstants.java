/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage.constants;

import com.liferay.dynamic.data.mapping.util.NumberUtil;
import com.liferay.dynamic.data.mapping.util.NumericDDMFormFieldUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public class FieldConstants {

	public static final String BOOLEAN = "boolean";

	public static final String DATA_TYPE = "dataType";

	public static final String DATE = "date";

	public static final String DATETIME = "datetime";

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

				if (number.doubleValue() > Integer.MAX_VALUE) {
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

				String formattedValue = String.valueOf(number);

				if (!NumberUtil.hasDecimalSeparator(formattedValue) &&
					NumberUtil.hasDecimalSeparator(value)) {

					formattedValue = StringBundler.concat(
						formattedValue, StringPool.PERIOD,
						value.substring(
							NumberUtil.getDecimalSeparatorIndex(value) + 1));
				}

				if ((formattedValue.charAt(0) != CharPool.MINUS) &&
					(value.charAt(0) == CharPool.MINUS)) {

					formattedValue = StringPool.MINUS + formattedValue;
				}

				serializable = getSerializable(type, formattedValue);
			}
			catch (ParseException parseException) {
				if (_log.isDebugEnabled()) {
					_log.debug(parseException);
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
		else if (type.equals(FieldConstants.DOUBLE) ||
				 type.equals(FieldConstants.INTEGER)) {

			return ListUtil.toArray(
				values,
				new Accessor<Object, Number>() {

					@Override
					public Number get(Object value) {
						return GetterUtil.getNumber(value);
					}

					@Override
					public Class<Number> getAttributeClass() {
						return Number.class;
					}

					@Override
					public Class<Object> getTypeClass() {
						return Object.class;
					}

				});
		}
		else if (type.equals(FieldConstants.FLOAT)) {
			return values.toArray(new Float[0]);
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
		TypedFunction typedFunction = _typedFunctions.get(type);

		if (typedFunction == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid type " + type);
			}

			return value;
		}

		return typedFunction.apply(value);
	}

	public static boolean isNumericType(String type) {
		TypedFunction typedFunction = _typedFunctions.get(type);

		if (typedFunction == null) {
			return false;
		}

		return typedFunction._numericType;
	}

	private static final Log _log = LogFactoryUtil.getLog(FieldConstants.class);

	private static final Map<String, TypedFunction> _typedFunctions =
		HashMapBuilder.<String, TypedFunction>put(
			BOOLEAN, new TypedFunction(GetterUtil::getBoolean, false)
		).put(
			DATE, new TypedFunction(value -> value, false)
		).put(
			DOUBLE,
			new TypedFunction(
				value -> {
					if (Validator.isNull(value)) {
						return StringPool.BLANK;
					}

					if (NumberUtil.hasDecimalSeparator(value)) {
						return GetterUtil.getDouble(value);
					}

					return GetterUtil.getInteger(value);
				},
				true)
		).put(
			FLOAT,
			new TypedFunction(
				value -> {
					if (Validator.isNull(value)) {
						return StringPool.BLANK;
					}

					if (NumberUtil.hasDecimalSeparator(value)) {
						return GetterUtil.getFloat(value);
					}

					return GetterUtil.getInteger(value);
				},
				true)
		).put(
			INTEGER,
			new TypedFunction(
				value -> {
					if (Validator.isNull(value)) {
						return StringPool.BLANK;
					}

					return GetterUtil.getInteger(value);
				},
				true)
		).put(
			LONG,
			new TypedFunction(
				value -> {
					if (Validator.isNull(value)) {
						return StringPool.BLANK;
					}

					return GetterUtil.getLong(value);
				},
				true)
		).put(
			NUMBER,
			new TypedFunction(
				value -> {
					if (Validator.isNull(value)) {
						return StringPool.BLANK;
					}

					return GetterUtil.getNumber(value);
				},
				true)
		).put(
			SHORT,
			new TypedFunction(
				value -> {
					if (Validator.isNull(value)) {
						return StringPool.BLANK;
					}

					return GetterUtil.getShort(value);
				},
				true)
		).build();

	private static class TypedFunction {

		public Serializable apply(String value) {
			return _function.apply(value);
		}

		private TypedFunction(
			Function<String, Serializable> function, boolean numericType) {

			_function = function;
			_numericType = numericType;
		}

		private final Function<String, Serializable> _function;
		private final boolean _numericType;

	}

}