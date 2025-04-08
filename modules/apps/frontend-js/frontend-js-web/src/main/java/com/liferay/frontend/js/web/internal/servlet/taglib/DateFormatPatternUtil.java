/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet.taglib;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Iván Zaera Avellón
 */
public class DateFormatPatternUtil {

	public static String getDateFormatPattern(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		String dateFormatPattern = _dateFormatPatterns.get(languageId);

		if (dateFormatPattern != null) {
			return dateFormatPattern;
		}

		SimpleDateFormat simpleDateFormat =
			(SimpleDateFormat)DateFormat.getDateInstance(
				DateFormat.SHORT, locale);

		dateFormatPattern = simpleDateFormat.toPattern();

		String delimiterString = StringPool.FORWARD_SLASH;
		boolean endDelimiter = false;

		for (char dateDelimiter : _DATE_DELIMITERS) {
			if (dateFormatPattern.indexOf(dateDelimiter) != -1) {
				delimiterString = String.valueOf(dateDelimiter);

				endDelimiter = dateFormatPattern.endsWith(delimiterString);

				break;
			}
		}

		int dayIndex = dateFormatPattern.indexOf('d');
		int monthIndex = dateFormatPattern.indexOf('M');
		int yearIndex = dateFormatPattern.indexOf('y');

		if ((yearIndex < dayIndex) && (yearIndex < monthIndex)) {
			dateFormatPattern = StringBundler.concat(
				"%Y", delimiterString, "%m", delimiterString, "%d");
		}
		else if (dayIndex < monthIndex) {
			dateFormatPattern = StringBundler.concat(
				"%d", delimiterString, "%m", delimiterString, "%Y");
		}
		else {
			dateFormatPattern = StringBundler.concat(
				"%m", delimiterString, "%d", delimiterString, "%Y");
		}

		if (endDelimiter) {
			dateFormatPattern += delimiterString;
		}

		_dateFormatPatterns.put(languageId, dateFormatPattern);
	}

	private static final char[] _DATE_DELIMITERS = {
		CharPool.DASH, CharPool.FORWARD_SLASH, CharPool.PERIOD
	};

	private static final Map<String, String> _dateFormatPatterns =
		new ConcurrentHashMap<>();

}