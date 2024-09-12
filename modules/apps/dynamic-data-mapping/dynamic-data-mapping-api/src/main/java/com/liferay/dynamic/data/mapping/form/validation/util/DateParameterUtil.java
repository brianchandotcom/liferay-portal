/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.validation.util;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carolina Barbosa
 */
public class DateParameterUtil {

	public static LocalDate getLocalDate(String dateString) {
		if (Validator.isNull(dateString)) {
			return null;
		}

		Matcher matcher = _pattern.matcher(dateString);

		if (!matcher.find()) {
			return null;
		}

		return LocalDate.parse(
			matcher.group(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public static LocalDateTime getLocalDateTime(String dateTimeString) {
		if (Validator.isNull(dateTimeString)) {
			return null;
		}

		String parseException = null;

		for (String dateTimePattern : _dateTimePatterns) {
			try {
				return LocalDateTime.parse(
					dateTimeString,
					DateTimeFormatter.ofPattern(dateTimePattern));
			}
			catch (DateTimeParseException dateTimeParseException) {
				parseException = String.valueOf(dateTimeParseException);
			}
		}

		if ((parseException != null) && _log.isWarnEnabled()) {
			_log.warn(
				dateTimeString + " could not be parsed by patterns: " +
					_dateTimePatterns);
		}

		LocalDate localDate = getLocalDate(dateTimeString);

		if (localDate == null) {
			return null;
		}

		return localDate.atStartOfDay();
	}

	public static String getParameter(
		DDMFormValues ddmFormValues, String key, String parameter,
		String timeZoneId) {

		JSONObject jsonObject;

		try {
			jsonObject = JSONFactoryUtil.createJSONObject(parameter);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return StringPool.BLANK;
		}

		LocalDate localDate = _getComparisonLocalDate(
			_getCurrentLocalDate(timeZoneId), ddmFormValues,
			jsonObject.getJSONObject(key));

		if (localDate == null) {
			return StringPool.BLANK;
		}

		return localDate.toString();
	}

	private static LocalDate _getComparisonLocalDate(
		LocalDate currentLocalDate, DDMFormValues ddmFormValues,
		JSONObject jsonObject) {

		if (jsonObject == null) {
			return null;
		}

		String type = jsonObject.getString("type");

		if (StringUtil.equals(type, "customDate")) {
			if (StringUtil.equals(
					jsonObject.getString("date"), "responseDate")) {

				return _getCustomLocalDate(
					currentLocalDate, jsonObject.getInt("quantity"),
					jsonObject.getString("unit"));
			}
			else if (StringUtil.equals(
						jsonObject.getString("date"), "dateField")) {

				return _getCustomLocalDate(
					getLocalDate(
						_getDateFieldValue(
							jsonObject.getString("dateFieldName"),
							ddmFormValues)),
					jsonObject.getInt("quantity"),
					jsonObject.getString("unit"));
			}
		}
		else if (StringUtil.equals(type, "dateField")) {
			return getLocalDate(
				_getDateFieldValue(
					jsonObject.getString("dateFieldName"), ddmFormValues));
		}
		else if (StringUtil.equals(type, "responseDate")) {
			return currentLocalDate;
		}

		return null;
	}

	private static LocalDate _getCurrentLocalDate(String timeZoneId) {
		if (Validator.isNull(timeZoneId)) {
			TimeZone timeZone = TimeZoneUtil.getDefault();

			timeZoneId = timeZone.getID();
		}

		return LocalDate.now(ZoneId.of(timeZoneId));
	}

	private static LocalDate _getCustomLocalDate(
		LocalDate localDate, int quantity, String unit) {

		if (localDate == null) {
			return null;
		}

		if (StringUtil.equals(unit, "days")) {
			return localDate.plusDays(quantity);
		}
		else if (StringUtil.equals(unit, "months")) {
			return localDate.plusMonths(quantity);
		}
		else if (StringUtil.equals(unit, "years")) {
			return localDate.plusYears(quantity);
		}

		return null;
	}

	private static String _getDateFieldValue(
		String dateFieldName, DDMFormValues ddmFormValues) {

		if (ddmFormValues == null) {
			return null;
		}

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(true);

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			dateFieldName);

		if (ListUtil.isNotEmpty(ddmFormFieldValues)) {
			DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

			Value value = ddmFormFieldValue.getValue();

			return value.getString(ddmFormValues.getDefaultLocale());
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DateParameterUtil.class);

	private static final List<String> _dateTimePatterns = Arrays.asList(
		"yyyy-MM-dd H:mm", "yyyy-MM-dd HH:mm:ss",
		"EEE MMM dd HH:mm:ss zzz yyyy");
	private static final Pattern _pattern = Pattern.compile(
		"^\\d{4}-\\d{2}-\\d{2}");

}