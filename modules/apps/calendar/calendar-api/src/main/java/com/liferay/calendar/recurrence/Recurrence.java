/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.recurrence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Marcellus Tavares
 */
public class Recurrence {

	public void addExceptionJCalendar(Calendar jCalendar) {
		_exceptionJCalendars.add(jCalendar);
	}

	@Override
	public Recurrence clone() {
		Recurrence recurrence = new Recurrence();

		recurrence.setCount(_count);
		recurrence.setExceptionJCalendars(
			new ArrayList<>(_exceptionJCalendars));
		recurrence.setFrequency(_frequency);
		recurrence.setInterval(_interval);
		recurrence.setMonths(new ArrayList<>(_months));
		recurrence.setPositionalWeekdays(new ArrayList<>(_positionalWeekdays));
		recurrence.setTimeZone(_timeZone);

		Calendar untilJCalendar = null;

		if (_untilJCalendar != null) {
			untilJCalendar = (Calendar)_untilJCalendar.clone();
		}

		recurrence.setUntilJCalendar(untilJCalendar);

		return recurrence;
	}

	public int getCount() {
		return _count;
	}

	public List<Calendar> getExceptionJCalendars() {
		return _exceptionJCalendars;
	}

	public Frequency getFrequency() {
		return _frequency;
	}

	public int getInterval() {
		return _interval;
	}

	public List<Integer> getMonths() {
		return _months;
	}

	public PositionalWeekday getPositionalWeekday() {
		if (_positionalWeekdays.isEmpty()) {
			return null;
		}

		return _positionalWeekdays.get(0);
	}

	public List<PositionalWeekday> getPositionalWeekdays() {
		return _positionalWeekdays;
	}

	public TimeZone getTimeZone() {
		return _timeZone;
	}

	public Calendar getUntilJCalendar() {
		return _untilJCalendar;
	}

	public List<Weekday> getWeekdays() {
		List<Weekday> weekdays = new ArrayList<>();

		for (PositionalWeekday positionalWeekday : _positionalWeekdays) {
			weekdays.add(positionalWeekday.getWeekday());
		}

		return weekdays;
	}

	public void removeExceptionJCalendar(Calendar jCalendar) {
		_exceptionJCalendars.remove(jCalendar);
	}

	public void setCount(int count) {
		_count = count;
	}

	public void setExceptionJCalendars(List<Calendar> exceptionJCalendars) {
		_exceptionJCalendars = exceptionJCalendars;
	}

	public void setFrequency(Frequency frequency) {
		_frequency = frequency;
	}

	public void setInterval(int interval) {
		_interval = interval;
	}

	public void setMonths(List<Integer> months) {
		_months = months;
	}

	public void setPositionalWeekdays(
		List<PositionalWeekday> positionalWeekdays) {

		_positionalWeekdays = positionalWeekdays;
	}

	public void setTimeZone(TimeZone timeZone) {
		_timeZone = timeZone;
	}

	public void setUntilJCalendar(Calendar untilJCalendar) {
		_untilJCalendar = untilJCalendar;
	}

	private int _count;
	private List<Calendar> _exceptionJCalendars = new ArrayList<>();
	private Frequency _frequency;
	private int _interval;
	private List<Integer> _months = Collections.emptyList();
	private List<PositionalWeekday> _positionalWeekdays =
		Collections.emptyList();
	private TimeZone _timeZone;
	private Calendar _untilJCalendar;

}