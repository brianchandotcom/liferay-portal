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

package com.liferay.expando.kernel.util;

import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;

/**
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class ExpandoConverterUtil {

	public static Serializable getAttributeFromString(
		int type, String attribute) {

		if (attribute == null) {
			return null;
		}

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return GetterUtil.getBoolean(attribute);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			return GetterUtil.getBooleanValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return _getDate(attribute);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			return _getDates(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			return GetterUtil.getDouble(attribute);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			return GetterUtil.getDoubleValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			return GetterUtil.getFloat(attribute);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			return GetterUtil.getFloatValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			return GetterUtil.getInteger(attribute);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			return GetterUtil.getIntegerValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.LONG) {
			return GetterUtil.getLong(attribute);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			return GetterUtil.getLongValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.NUMBER) {
			return GetterUtil.getNumber(attribute);
		}
		else if (type == ExpandoColumnConstants.NUMBER_ARRAY) {
			return GetterUtil.getNumberValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			return GetterUtil.getShort(attribute);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			return GetterUtil.getShortValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			return StringUtil.split(attribute);
		}
		else if (type == ExpandoColumnConstants.STRING_LOCALIZED) {
			return (Serializable)LocalizationUtil.getLocalizationMap(attribute);
		}

		return attribute;
	}

	public static Serializable getAttributeFromStringArray(
		int type, String[] attribute) {

		if (ArrayUtil.isEmpty(attribute)) {
			return null;
		}

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return GetterUtil.getBoolean(attribute[0]);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			return GetterUtil.getBooleanValues(attribute);
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return _getDate(attribute[0]);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			return _getDates(attribute);
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			return GetterUtil.getDouble(attribute[0]);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			return GetterUtil.getDoubleValues(attribute);
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			return GetterUtil.getFloat(attribute[0]);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			return GetterUtil.getFloatValues(attribute);
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			return GetterUtil.getInteger(attribute[0]);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			return GetterUtil.getIntegerValues(attribute);
		}
		else if (type == ExpandoColumnConstants.LONG) {
			return GetterUtil.getLong(attribute[0]);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			return GetterUtil.getLongValues(attribute);
		}
		else if (type == ExpandoColumnConstants.NUMBER) {
			return GetterUtil.getNumber(attribute[0]);
		}
		else if (type == ExpandoColumnConstants.NUMBER_ARRAY) {
			return GetterUtil.getNumberValues(attribute);
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			return GetterUtil.getShort(attribute[0]);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			return GetterUtil.getShortValues(attribute);
		}
		else if (type == ExpandoColumnConstants.STRING) {
			return attribute[0];
		}

		return attribute;
	}

	public static String getStringFromAttribute(
		int type, Serializable attribute) {

		if (attribute == null) {
			return StringPool.BLANK;
		}

		if ((type == ExpandoColumnConstants.BOOLEAN) ||
			(type == ExpandoColumnConstants.DOUBLE) ||
			(type == ExpandoColumnConstants.FLOAT) ||
			(type == ExpandoColumnConstants.INTEGER) ||
			(type == ExpandoColumnConstants.LONG) ||
			(type == ExpandoColumnConstants.NUMBER) ||
			(type == ExpandoColumnConstants.SHORT)) {

			return String.valueOf(attribute);
		}
		else if ((type == ExpandoColumnConstants.BOOLEAN_ARRAY) ||
				 (type == ExpandoColumnConstants.DOUBLE_ARRAY) ||
				 (type == ExpandoColumnConstants.FLOAT_ARRAY) ||
				 (type == ExpandoColumnConstants.INTEGER_ARRAY) ||
				 (type == ExpandoColumnConstants.LONG_ARRAY) ||
				 (type == ExpandoColumnConstants.NUMBER_ARRAY) ||
				 (type == ExpandoColumnConstants.SHORT_ARRAY) ||
				 (type == ExpandoColumnConstants.STRING_ARRAY)) {

			return StringUtil.merge(
				ArrayUtil.toStringArray((Object[])attribute));
		}
		else if (type == ExpandoColumnConstants.DATE) {
			DateFormat dateFormat = _getDateFormat();

			return dateFormat.format((Date)attribute);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			return StringUtil.merge(
				ArrayUtil.toStringArray((Date[])attribute, _getDateFormat()));
		}

		return attribute.toString();
	}

	private static Date _getDate(String dateString) {
		if (Validator.isNumber(dateString)) {
			return new Date(GetterUtil.getLong(dateString));
		}

		return GetterUtil.getDate(dateString, _getDateFormat());
	}

	private static DateFormat _getDateFormat() {
		return DateUtil.getISO8601Format();
	}

	private static Date[] _getDates(String[] dateStrings) {
		Date[] dates = new Date[dateStrings.length];

		for (int i = 0; i < dateStrings.length; i++) {
			dates[i] = _getDate(dateStrings[i]);
		}

		return dates;
	}

}