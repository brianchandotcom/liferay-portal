/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.SimpleDateFormat;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * @author Carolina Barbosa
 */
public class DateDDMFormFieldUtil {

	public static String getPattern(boolean dateTime, Locale locale) {
		SimpleDateFormat simpleDateFormat = getSimpleDateFormat(
			dateTime, locale);

		String pattern = simpleDateFormat.toPattern();

		if (StringUtils.countMatches(pattern, "d") == 1) {
			pattern = StringUtil.replace(pattern, 'd', "dd");
		}

		if (StringUtils.countMatches(pattern, "h") == 1) {
			pattern = StringUtil.replace(pattern, 'h', "hh");
		}

		if (StringUtils.countMatches(pattern, "H") == 1) {
			pattern = StringUtil.replace(pattern, 'H', "HH");
		}

		if (StringUtils.countMatches(pattern, "M") == 1) {
			pattern = StringUtil.replace(pattern, 'M', "MM");
		}

		if (StringUtils.countMatches(pattern, "y") == 2) {
			pattern = StringUtil.replace(pattern, 'y', "yy");
		}

		return pattern;
	}

	public static SimpleDateFormat getSimpleDateFormat(
		boolean dateTime, Locale locale) {

		if (dateTime) {
			return (SimpleDateFormat)DateFormatFactoryUtil.getDateTime(locale);
		}

		return (SimpleDateFormat)DateFormatFactoryUtil.getDate(locale);
	}

}