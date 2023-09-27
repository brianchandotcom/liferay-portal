/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.modified.facet.display.context.builder;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetCalendarDisplayContext;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author André de Oliveira
 */
public class ModifiedFacetCalendarDisplayContextBuilder {

	public ModifiedFacetCalendarDisplayContext build() {
		_buildBounds();

		ModifiedFacetCalendarDisplayContext
			modifiedFacetCalendarDisplayContext =
				new ModifiedFacetCalendarDisplayContext();

		Calendar fromCalendar = _getFromCalendar();

		modifiedFacetCalendarDisplayContext.setFromDayValue(
			fromCalendar.get(Calendar.DATE));
		modifiedFacetCalendarDisplayContext.setFromFirstDayOfWeek(
			fromCalendar.getFirstDayOfWeek() - 1);
		modifiedFacetCalendarDisplayContext.setFromMonthValue(
			fromCalendar.get(Calendar.MONTH));
		modifiedFacetCalendarDisplayContext.setFromYearValue(
			fromCalendar.get(Calendar.YEAR));

		Calendar toCalendar = _getToCalendar();

		modifiedFacetCalendarDisplayContext.setToDayValue(
			toCalendar.get(Calendar.DATE));
		modifiedFacetCalendarDisplayContext.setToFirstDayOfWeek(
			toCalendar.getFirstDayOfWeek() - 1);
		modifiedFacetCalendarDisplayContext.setToMonthValue(
			toCalendar.get(Calendar.MONTH));
		modifiedFacetCalendarDisplayContext.setToYearValue(
			toCalendar.get(Calendar.YEAR));
		modifiedFacetCalendarDisplayContext.setRangeBackwards(
			_isRangeBackwards(fromCalendar, toCalendar));

		modifiedFacetCalendarDisplayContext.setSelected(_isSelected());

		return modifiedFacetCalendarDisplayContext;
	}

	public void setFrom(String from) {
		_from = from;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setRangeString(String rangeString) {
		_rangeString = rangeString;
	}

	public void setTimeZone(TimeZone timeZone) {
		_timeZone = timeZone;
	}

	public void setTo(String to) {
		_to = to;
	}

	private void _buildBounds() {
		String[] bounds = _getBounds();

		if (!ArrayUtil.isEmpty(bounds)) {
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