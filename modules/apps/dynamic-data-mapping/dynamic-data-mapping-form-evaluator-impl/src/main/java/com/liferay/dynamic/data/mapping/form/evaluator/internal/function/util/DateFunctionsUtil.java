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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.ParseException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Carolina Barbosa
 */
public class DateFunctionsUtil {

	public static Boolean isFutureDate(
		String dateString, Locale locale, String timeZoneId, String type) {

		if (StringUtil.equals(type, "responseDate")) {
			LocalDate localDate = _getParsedLocalDate(dateString, locale);

			if (localDate.isBefore(_getCurrentLocalDate(timeZoneId))) {
				return false;
			}
		}

		return true;
	}

	public static Boolean isPastDate(
		String dateString, Locale locale, String timeZoneId, String type) {

		if (StringUtil.equals(type, "responseDate")) {
			LocalDate localDate = _getParsedLocalDate(dateString, locale);

			if (localDate.isAfter(_getCurrentLocalDate(timeZoneId))) {
				return false;
			}
		}

		return true;
	}

	private static LocalDate _getCurrentLocalDate(String timeZoneId) {
		if (Validator.isNull(timeZoneId)) {
			TimeZone timeZone = TimeZoneUtil.getDefault();

			timeZoneId = timeZone.getID();
		}

		return LocalDate.now(ZoneId.of(timeZoneId));
	}

	private static LocalDate _getParsedLocalDate(
		String dateString, Locale locale) {

		try {
			Date date = DateUtil.parseDate("yyyy-MM-dd", dateString, locale);

			ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(
				date.toInstant(), ZoneOffset.UTC);

			return zonedDateTime.toLocalDate();
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException, parseException);
			}
		}

		return LocalDate.parse(
			dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DateFunctionsUtil.class);

}