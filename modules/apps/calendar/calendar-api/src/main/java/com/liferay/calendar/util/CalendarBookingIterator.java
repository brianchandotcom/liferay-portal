/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.util;

import com.google.ical.iter.RecurrenceIterator;
import com.google.ical.iter.RecurrenceIteratorFactory;
import com.google.ical.values.DateValue;
import com.google.ical.values.DateValueImpl;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.text.ParseException;

import java.util.Calendar;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TimeZone;

/**
 * @author Adam Brandizzi
 */
public class CalendarBookingIterator implements Iterator<CalendarBooking> {

	public CalendarBookingIterator(CalendarBooking calendarBooking)
		throws ParseException {

		this(calendarBooking, calendarBooking.getTimeZone());
	}

	public CalendarBookingIterator(
			CalendarBooking calendarBooking, TimeZone displayTimeZone)
		throws ParseException {

		_calendarBooking = calendarBooking;
		_displayTimeZone = displayTimeZone;

		_recurrenceIterator =
			RecurrenceIteratorFactory.createRecurrenceIterator(
				calendarBooking.getRecurrence(),
				_toDateValue(calendarBooking.getStartTime()),
				calendarBooking.getTimeZone());
	}

	@Override
	public boolean hasNext() {
		if (_recurrenceIterator.hasNext() && !_isExceededCount()) {
			return true;
		}

		return false;
	}

	@Override
	public CalendarBooking next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		CalendarBooking newCalendarBooking =
			(CalendarBooking)_calendarBooking.clone();

		_currentDateValue = _recurrenceIterator.next();

		Calendar jCalendar = _getStartTimeJCalendar(_currentDateValue);

		newCalendarBooking.setEndTime(
			jCalendar.getTimeInMillis() + _calendarBooking.getDuration());

		newCalendarBooking.setInstanceIndex(_instanceIndex);
		newCalendarBooking.setStartTime(jCalendar.getTimeInMillis());

		_instanceIndex++;

		return newCalendarBooking;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	private Calendar _getStartTimeJCalendar(DateValue dateValue) {
		Calendar jCalendar = JCalendarUtil.getJCalendar(
			_calendarBooking.getStartTime(), _getTimeZone(_calendarBooking));

		Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			dateValue.year(), dateValue.month() - 1, dateValue.day(),
			jCalendar.get(Calendar.HOUR_OF_DAY), jCalendar.get(Calendar.MINUTE),
			jCalendar.get(Calendar.SECOND), jCalendar.get(Calendar.MILLISECOND),
			_getTimeZone(_calendarBooking));

		int shift = JCalendarUtil.getDSTShift(
			jCalendar, startTimeJCalendar,
			_calendarBooking.isAllDay() ? TimeZone.getTimeZone(StringPool.UTC) :
				_displayTimeZone);

		startTimeJCalendar.add(Calendar.MILLISECOND, shift);

		return startTimeJCalendar;
	}

	private TimeZone _getTimeZone(CalendarBooking calendarBooking) {
		try {
			if (calendarBooking.isAllDay()) {
				return TimeZone.getTimeZone(StringPool.UTC);
			}

			return calendarBooking.getTimeZone();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return TimeZoneUtil.getDefault();
	}

	private boolean _isExceededCount() {
		Recurrence recurrence = _calendarBooking.getRecurrenceObj();

		if (recurrence == null) {
			return false;
		}

		int count = recurrence.getCount();

		if ((count != 0) && (_instanceIndex >= count)) {
			return true;
		}

		return false;
	}

	private DateValue _toDateValue(long time) {
		Calendar jCalendar = JCalendarUtil.getJCalendar(
			time, _getTimeZone(_calendarBooking));

		return new DateValueImpl(
			jCalendar.get(Calendar.YEAR), jCalendar.get(Calendar.MONTH) + 1,
			jCalendar.get(Calendar.DAY_OF_MONTH));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarBookingIterator.class);

	private final CalendarBooking _calendarBooking;
	private DateValue _currentDateValue;
	private final TimeZone _displayTimeZone;
	private int _instanceIndex;
	private final RecurrenceIterator _recurrenceIterator;

}