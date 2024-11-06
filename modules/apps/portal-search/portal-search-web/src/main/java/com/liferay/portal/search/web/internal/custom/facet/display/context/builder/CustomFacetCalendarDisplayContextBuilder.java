/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.facet.display.context.builder;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetCalendarDisplayContext;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Petteri Karttunen
 */
public class CustomFacetCalendarDisplayContextBuilder {

	public CustomFacetCalendarDisplayContext build() {
		_buildBounds();

		CustomFacetCalendarDisplayContext customFacetCalendarDisplayContext =
			new CustomFacetCalendarDisplayContext();

		Calendar fromCalendar = _getFromCalendar();

		customFacetCalendarDisplayContext.setFromDayValue(
			fromCalendar.get(Calendar.DATE));
		customFacetCalendarDisplayContext.setFromFirstDayOfWeek(
			fromCalendar.getFirstDayOfWeek() - 1);
		customFacetCalendarDisplayContext.setFromMonthValue(
			fromCalendar.get(Calendar.MONTH));
		customFacetCalendarDisplayContext.setFromYearValue(
			fromCalendar.get(Calendar.YEAR));

		Calendar toCalendar = _getToCalendar();

		customFacetCalendarDisplayContext.setToDayValue(
			toCalendar.get(Calendar.DATE));
		customFacetCalendarDisplayContext.setToFirstDayOfWeek(
			toCalendar.getFirstDayOfWeek() - 1);
		customFacetCalendarDisplayContext.setToMonthValue(
			toCalendar.get(Calendar.MONTH));
		customFacetCalendarDisplayContext.setToYearValue(
			toCalendar.get(Calendar.YEAR));
		customFacetCalendarDisplayContext.setRangeBackwards(
			_isRangeBackwards(fromCalendar, toCalendar));

		customFacetCalendarDisplayContext.setSelected(_isSelected());

		return customFacetCalendarDisplayContext;
	}

	public CustomFacetCalendarDisplayContextBuilder from(String from) {
		_from = from;

		return this;
	}

	public CustomFacetCalendarDisplayContextBuilder locale(Locale locale) {
		_locale = locale;

		return this;
	}

	public CustomFacetCalendarDisplayContextBuilder rangeString(
		String rangeString) {

		_rangeString = rangeString;

		return this;
	}

	public CustomFacetCalendarDisplayContextBuilder timeZone(
		TimeZone timeZone) {

		_timeZone = timeZone;

		return this;
	}

	public CustomFacetCalendarDisplayContextBuilder to(String to) {
		_to = to;

		return this;
	}

	private void _buildBounds() {
		String[] bounds = _getBounds();

		if (ArrayUtil.isNotEmpty(bounds)) {
			_parseFrom(bounds[0]);
			_parseTo(bounds[1]);
		}
	}

	private String[] _getBounds() {
		if (!Validator.isBlank(_rangeString)) {
			return RangeParserUtil.parserRange(_rangeString);
		}

		if (!Validator.isBlank(_from) && !Validator.isBlank(_to)) {
			return new String[] {
				StringUtil.removeChar(_from, CharPool.DASH),
				StringUtil.removeChar(_to, CharPool.DASH)
			};
		}

		return null;
	}

	private Calendar _getFromCalendar() {
		if (Validator.isGregorianDate(_fromMonth, _fromDay, _fromYear)) {
			return CalendarFactoryUtil.getCalendar(
				_fromYear, _fromMonth, _fromDay, 0, 0, 0, 0, _timeZone);
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(_timeZone, _locale);

		calendar.add(Calendar.DATE, -1);

		return calendar;
	}

	private Calendar _getToCalendar() {
		if (Validator.isGregorianDate(_toMonth, _toDay, _toYear)) {
			return CalendarFactoryUtil.getCalendar(
				_toYear, _toMonth, _toDay, 0, 0, 0, 0, _timeZone);
		}

		return CalendarFactoryUtil.getCalendar(_timeZone, _locale);
	}

	private boolean _isRangeBackwards(
		Calendar fromCalendar, Calendar toCalendar) {

		if (fromCalendar.compareTo(toCalendar) > 0) {
			return true;
		}

		return false;
	}

	private boolean _isSelected() {
		if (Validator.isBlank(_from) && Validator.isBlank(_to)) {
			return false;
		}

		return true;
	}

	private int[] _parseDate(String string) {
		int day = GetterUtil.getInteger(string.substring(6, 8));
		int month = GetterUtil.getInteger(string.substring(4, 6));
		int year = GetterUtil.getInteger(string.substring(0, 4));

		return new int[] {day, month, year};
	}

	private void _parseFrom(String dateString) {
		int[] from = _parseDate(dateString);

		_fromDay = from[0];
		_fromMonth = from[1] - 1;
		_fromYear = from[2];
	}

	private void _parseTo(String dateString) {
		int[] to = _parseDate(dateString);

		_toDay = to[0];
		_toMonth = to[1] - 1;
		_toYear = to[2];
	}

	private String _from;
	private int _fromDay;
	private int _fromMonth;
	private int _fromYear;
	private Locale _locale;
	private String _rangeString;
	private TimeZone _timeZone;
	private String _to;
	private int _toDay;
	private int _toMonth;
	private int _toYear;

}