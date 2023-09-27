/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.recurrence;

import com.google.ical.values.DateTimeValue;
import com.google.ical.values.DateValue;
import com.google.ical.values.DateValueImpl;
import com.google.ical.values.RDateList;
import com.google.ical.values.RRule;
import com.google.ical.values.WeekdayNum;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author Marcellus Tavares
 */
public class RecurrenceSerializer {

	public static Recurrence deserialize(String data, TimeZone timeZone) {
		if (Validator.isNull(data)) {
			return null;
		}

		try {
			Recurrence recurrence = new Recurrence();

			int index = data.indexOf(CharPool.NEW_LINE);

			if (index != -1) {
				String exceptionDates = data.substring(index + 1);

				RDateList rDateList = new RDateList(exceptionDates, timeZone);

				for (DateValue dateValue : rDateList.getDatesUtc()) {
					Calendar jCalendar = _toJCalendar(dateValue, timeZone);

					recurrence.addExceptionJCalendar(jCalendar);
				}

				data = data.substring(0, index);
			}

			RRule rRule = new RRule(data);

			recurrence.setCount(rRule.getCount());
			recurrence.setFrequency(
				Frequency.parse(String.valueOf(rRule.getFreq())));
			recurrence.setInterval(rRule.getInterval());

			recurrence.setTimeZone(timeZone);

			DateValue dateValue = rRule.getUntil();

			if (dateValue != null) {
				Calendar jCalendar = _toJCalendar(dateValue, timeZone);

				recurrence.setUntilJCalendar(jCalendar);
			}

			List<PositionalWeekday> positionalWeekdays = new ArrayList<>();

			for (WeekdayNum weekdayNum : rRule.getByDay()) {
				Weekday weekday = Weekday.parse(weekdayNum.wday.toString());

				PositionalWeekday positionalWeekday = new PositionalWeekday(
					weekday, weekdayNum.num);

				positionalWeekdays.add(positionalWeekday);
			}

			recurrence.setPositionalWeekdays(positionalWeekdays);
			recurrence.setMonths(ListUtil.fromArray(rRule.getByMonth()));

			return recurrence;
		}
		catch (ParseException parseException) {
			_log.error("Unable to parse data " + data, parseException);
		}

		return null;
	}

	public static String serialize(Recurrence recurrence) {
		if (recurrence == null) {
			return null;
		}

		RRule rRule = new RRule();

		List<WeekdayNum> weekdayNums = new ArrayList<>();

		for (PositionalWeekday positionalWeekday :
				recurrence.getPositionalWeekdays()) {

			com.google.ical.values.Weekday wday = _weekdaysMap.get(
				positionalWeekday.getWeekday());

			WeekdayNum weekdayNum = new WeekdayNum(
				positionalWeekday.getPosition(), wday);

			weekdayNums.add(weekdayNum);
		}

		rRule.setByDay(weekdayNums);

		List<Integer> months = recurrence.getMonths();

		if (months != null) {
			int[] monthsArray = ArrayUtil.toIntArray(months);

			for (int i = 0; i < monthsArray.length; i++) {
				monthsArray[i]++;
			}

			rRule.setByMonth(monthsArray);
		}

		rRule.setCount(recurrence.getCount());

		com.google.ical.values.Frequency frequency =
			com.google.ical.values.Frequency.valueOf(
				String.valueOf(recurrence.getFrequency()));

		rRule.setFreq(frequency);

		rRule.setInterval(recurrence.getInterval());

		Calendar jCalendar = recurrence.getUntilJCalendar();

		if (jCalendar != null) {
			DateValue dateValue = _toDateValue(jCalendar);

			rRule.setUntil(dateValue);
		}

		String data = rRule.toIcal();

		List<Calendar> exceptionJCalendars =
			recurrence.getExceptionJCalendars();

		if (!exceptionJCalendars.isEmpty()) {
			DateValue[] dateValues = new DateValue[exceptionJCalendars.size()];

			for (int i = 0; i < exceptionJCalendars.size(); i++) {
				dateValues[i] = _toDateValue(exceptionJCalendars.get(i));
			}

			RDateList rDateList = new RDateList(
				TimeZone.getTimeZone(StringPool.UTC));

			rDateList.setDatesUtc(dateValues);
			rDateList.setName(_EXDATE);

			data = StringBundler.concat(
				data, StringPool.NEW_LINE, rDateList.toIcal());
		}

		return data;
	}

	private static DateValue _toDateValue(Calendar jCalendar) {
		return new DateValueImpl(
			jCalendar.get(Calendar.YEAR), jCalendar.get(Calendar.MONTH) + 1,
			jCalendar.get(Calendar.DATE));
	}

	private static Calendar _toJCalendar(
		DateValue dateValue, TimeZone timeZone) {

		int hour = 0;
		int minute = 0;
		int second = 0;

		if (dateValue instanceof DateTimeValue) {
			DateTimeValue dateTimeValue = (DateTimeValue)dateValue;

			hour = dateTimeValue.hour();
			minute = dateTimeValue.minute();
			second = dateTimeValue.second();
		}

		return CalendarFactoryUtil.getCalendar(
			dateValue.year(), dateValue.month() - 1, dateValue.day(), hour,
			minute, second, 0, timeZone);
	}

	private static final String _EXDATE = "EXDATE";

	private static final Log _log = LogFactoryUtil.getLog(
		RecurrenceSerializer.class);

	private static final Map<Weekday, com.google.ical.values.Weekday>
		_weekdaysMap =
			HashMapBuilder.<Weekday, com.google.ical.values.Weekday>put(
				Weekday.FRIDAY, com.google.ical.values.Weekday.FR
			).put(
				Weekday.MONDAY, com.google.ical.values.Weekday.MO
			).put(
				Weekday.SATURDAY, com.google.ical.values.Weekday.SA
			).put(
				Weekday.SUNDAY, com.google.ical.values.Weekday.SU
			).put(
				Weekday.THURSDAY, com.google.ical.values.Weekday.TH
			).put(
				Weekday.TUESDAY, com.google.ical.values.Weekday.TU
			).put(
				Weekday.WEDNESDAY, com.google.ical.values.Weekday.WE
			).build();

}