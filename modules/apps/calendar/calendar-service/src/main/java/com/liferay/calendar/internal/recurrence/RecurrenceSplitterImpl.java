/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.recurrence;

import com.google.ical.iter.RecurrenceIterator;
import com.google.ical.iter.RecurrenceIteratorFactory;
import com.google.ical.values.DateValue;
import com.google.ical.values.DateValueImpl;

import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adam Brandizzi
 */
@Component(service = RecurrenceSplitter.class)
public class RecurrenceSplitterImpl implements RecurrenceSplitter {

	@Override
	public RecurrenceSplit split(
		Recurrence recurrence, Calendar startTimeJCalendar,
		Calendar splitTimeJCalendar) {

		Recurrence firstRecurrence = recurrence.clone();

		Recurrence secondRecurrence = recurrence.clone();

		try {
			if (recurrence.getCount() != 0) {
				_setCount(
					recurrence, firstRecurrence, secondRecurrence,
					startTimeJCalendar, splitTimeJCalendar);
			}
			else {
				_setUntilJCalendar(
					recurrence, firstRecurrence, startTimeJCalendar,
					splitTimeJCalendar);
			}

			_copyExceptionJCalendars(
				firstRecurrence, secondRecurrence,
				recurrence.getExceptionJCalendars(), splitTimeJCalendar);
		}
		catch (SplitTimeOutsideRecurrenceException
					splitTimeOutsideRecurrenceException) {

			if (_log.isDebugEnabled()) {
				_log.debug(splitTimeOutsideRecurrenceException);
			}

			firstRecurrence = recurrence.clone();

			secondRecurrence = null;
		}

		return new RecurrenceSplitImpl(firstRecurrence, secondRecurrence);
	}

	private void _copyExceptionJCalendars(
		Recurrence firstRecurrence, Recurrence secondRecurrence,
		List<Calendar> exceptionJCalendars, Calendar splitTimeJCalendar) {

		firstRecurrence.setExceptionJCalendars(new ArrayList<Calendar>());

		secondRecurrence.setExceptionJCalendars(new ArrayList<Calendar>());

		for (Calendar exceptionJCalendar : exceptionJCalendars) {
			if (exceptionJCalendar.before(splitTimeJCalendar)) {
				firstRecurrence.addExceptionJCalendar(exceptionJCalendar);
			}
			else {
				secondRecurrence.addExceptionJCalendar(exceptionJCalendar);
			}
		}
	}

	private RecurrenceIterator _getRecurrenceIterator(
		Recurrence recurrence, DateValue startTimeDateValue) {

		try {
			return RecurrenceIteratorFactory.createRecurrenceIterator(
				RecurrenceSerializer.serialize(recurrence), startTimeDateValue,
				recurrence.getTimeZone());
		}
		catch (ParseException parseException) {
			throw new IllegalStateException(parseException);
		}
	}

	private void _setCount(
			Recurrence recurrence, Recurrence firstRecurrence,
			Recurrence secondRecurrence, Calendar startTimeJCalendar,
			Calendar splitTimeJCalendar)
		throws SplitTimeOutsideRecurrenceException {

		DateValue startTimeDateValue = _toDateValue(startTimeJCalendar);

		DateValue splitTimeDateValue = _toDateValue(splitTimeJCalendar);

		RecurrenceIterator recurrenceIterator = _getRecurrenceIterator(
			recurrence, startTimeDateValue);

		DateValue dateValue = recurrenceIterator.next();

		int count = 0;

		while (recurrenceIterator.hasNext()) {
			if (dateValue.compareTo(splitTimeDateValue) >= 0) {
				break;
			}

			count++;
			dateValue = recurrenceIterator.next();
		}

		_validateCount(splitTimeDateValue, dateValue);

		firstRecurrence.setCount(count);

		secondRecurrence.setCount(recurrence.getCount() - count);
	}

	private void _setUntilJCalendar(
			Recurrence recurrence, Recurrence firstRecurrence,
			Calendar startTimeJCalendar, Calendar splitTimeJCalendar)
		throws SplitTimeOutsideRecurrenceException {

		Calendar untilJCalendar = (Calendar)splitTimeJCalendar.clone();

		untilJCalendar.add(Calendar.DATE, -1);

		_validateUntilJCalendar(
			untilJCalendar, recurrence.getUntilJCalendar(), startTimeJCalendar);

		firstRecurrence.setUntilJCalendar(untilJCalendar);
	}

	private DateValue _toDateValue(Calendar jCalendar) {
		return new DateValueImpl(
			jCalendar.get(Calendar.YEAR), jCalendar.get(Calendar.MONTH) + 1,
			jCalendar.get(Calendar.DAY_OF_MONTH));
	}

	private void _validateCount(
			DateValue splitTimeDateValue, DateValue dateValue)
		throws SplitTimeOutsideRecurrenceException {

		if (dateValue.compareTo(splitTimeDateValue) < 0) {
			throw new SplitTimeOutsideRecurrenceException(
				"There is no instance after split time");
		}
	}

	private void _validateUntilJCalendar(
			Calendar newUntilJCalendar, Calendar oldUntilJCalendar,
			Calendar startTimeJCalendar)
		throws SplitTimeOutsideRecurrenceException {

		if (newUntilJCalendar.after(oldUntilJCalendar)) {
			throw new SplitTimeOutsideRecurrenceException(
				"Split date comes after the limit date of the recurrence");
		}
		else if (newUntilJCalendar.before(startTimeJCalendar)) {
			throw new SplitTimeOutsideRecurrenceException(
				"Split date comes before the start date of the recurrence");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RecurrenceSplitterImpl.class);

	private static class SplitTimeOutsideRecurrenceException extends Exception {

		public SplitTimeOutsideRecurrenceException(String message) {
			super(message);
		}

	}

}