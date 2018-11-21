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

package com.liferay.users.admin.web.internal.display.context;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.Format;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class OrgLaborDisplay {

	public OrgLaborDisplay(Locale locale, OrgLabor orgLabor) throws Exception {
		ListType listType = orgLabor.getType();

		_title = LanguageUtil.get(
			locale, StringUtil.toUpperCase(listType.getName()));

		_dayKeyValuePairs = _buildDayKeyValuePairs(locale, orgLabor);
	}

	public KeyValuePair[] getDayKeyValuePairs() {
		return _dayKeyValuePairs;
	}

	public String getTitle() {
		return _title;
	}

	private KeyValuePair[] _buildDayKeyValuePairs(
		Locale locale, OrgLabor orgLabor) {

		KeyValuePair[] dayKeyValuePairs = new KeyValuePair[7];

		String[] longDayNames = CalendarUtil.getDays(locale);

		for (int i = 0; i < _SHORT_DAY_NAMES.length; i++) {
			dayKeyValuePairs[i] = new KeyValuePair(
				longDayNames[i],
				_getHoursDisplayValue(locale, orgLabor, _SHORT_DAY_NAMES[i]));
		}

		return dayKeyValuePairs;
	}

	private String _formatHourAndMinute(Locale locale, int hourAndMinute) {
		if (hourAndMinute < 0) {
			return "";
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);

		String hourAndMinuteString = String.valueOf(hourAndMinute);

		if (hourAndMinuteString.length() == 4) {
			calendar.set(
				Calendar.HOUR_OF_DAY,
				Integer.valueOf(hourAndMinuteString.substring(0, 2)));
			calendar.set(
				Calendar.MINUTE,
				Integer.valueOf(hourAndMinuteString.substring(2)));
		}
		else if (hourAndMinuteString.length() == 3) {
			calendar.set(
				Calendar.HOUR_OF_DAY,
				Integer.valueOf(hourAndMinuteString.charAt(0)));
			calendar.set(
				Calendar.MINUTE,
				Integer.valueOf(hourAndMinuteString.substring(1)));
		}
		else {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
		}

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"HH:mm", locale);

		return format.format(calendar.getTime());
	}

	private String _getHoursDisplayValue(
		Locale locale, OrgLabor orgLabor, String shortDayName) {

		String closeHourAndMinuteString = _formatHourAndMinute(
			locale,
			BeanPropertiesUtil.getInteger(
				orgLabor, shortDayName + "Close", -1));
		String openHourAndMinuteString = _formatHourAndMinute(
			locale,
			BeanPropertiesUtil.getInteger(orgLabor, shortDayName + "Open", -1));

		if (Validator.isNull(closeHourAndMinuteString) &&
			Validator.isNull(openHourAndMinuteString)) {

			return LanguageUtil.get(locale, "closed");
		}

		return openHourAndMinuteString + " - " + closeHourAndMinuteString;
	}

	private static final String[] _SHORT_DAY_NAMES = {
		"sun", "mon", "tue", "wed", "thu", "fri", "sat"
	};

	private final KeyValuePair[] _dayKeyValuePairs;
	private final String _title;

}