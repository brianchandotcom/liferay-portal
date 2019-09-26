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

package com.liferay.portal.kernel.cal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.Format;

import java.util.Calendar;

/**
 * @author Brian Wing Shun Chan
 */
public class RecurrenceSerializer {

	public static String toCronText(Recurrence recurrence) {
		Calendar dtStart = recurrence.getDtStart();

		int frequency = recurrence.getFrequency();
		int interval = recurrence.getInterval();

		DayAndPosition[] byDay = recurrence.getByDay();
		int[] byMonthDay = recurrence.getByMonthDay();

		String startDateSecond = String.valueOf(dtStart.get(Calendar.SECOND));
		String startDateMinute = String.valueOf(dtStart.get(Calendar.MINUTE));

		int startDateHour = dtStart.get(Calendar.HOUR);

		if (dtStart.get(Calendar.AM_PM) == Calendar.PM) {
			startDateHour += 12;
		}

		String dayOfMonth = String.valueOf(dtStart.get(Calendar.DAY_OF_MONTH));
		String month = String.valueOf(dtStart.get(Calendar.MONTH) + 1);
		String dayOfWeek = String.valueOf(dtStart.get(Calendar.DAY_OF_WEEK));
		String year = String.valueOf(dtStart.get(Calendar.YEAR));

		if (frequency == Recurrence.NO_RECURRENCE) {
			dayOfWeek = StringPool.QUESTION;
		}
		else if (frequency == Recurrence.DAILY) {
			dayOfMonth = 1 + _getIntervalValue(interval, Recurrence.DAILY);
			month = StringPool.STAR;
			dayOfWeek = StringPool.QUESTION;
			year = StringPool.STAR;

			if (byDay != null) {
				dayOfMonth = StringPool.QUESTION;

				dayOfWeek = StringPool.BLANK;

				for (int i = 0; i < byDay.length; i++) {
					if (i > 0) {
						dayOfWeek += StringPool.COMMA;
					}

					dayOfWeek += getDayOfWeek(byDay[i]);
				}
			}
		}
		else if (frequency == Recurrence.WEEKLY) {
			dayOfMonth = StringPool.QUESTION;
			month = StringPool.STAR;
			year = StringPool.STAR;

			if (byDay != null) {
				dayOfWeek = StringPool.BLANK;

				for (int i = 0; i < byDay.length; i++) {
					if (i > 0) {
						dayOfWeek += StringPool.COMMA;
					}

					dayOfWeek += getDayOfWeek(byDay[i]);
				}
			}

			dayOfWeek += _getIntervalValue(interval, Recurrence.WEEKLY);
		}
		else if (frequency == Recurrence.MONTHLY) {
			dayOfMonth = StringPool.QUESTION;
			month = 1 + _getIntervalValue(interval, Recurrence.MONTHLY);
			dayOfWeek = StringPool.QUESTION;
			year = StringPool.STAR;

			if ((byMonthDay != null) && (byMonthDay.length == 1)) {
				dayOfMonth = String.valueOf(byMonthDay[0]);
			}
			else if ((byDay != null) && (byDay.length == 1)) {
				String pos = String.valueOf(byDay[0].getDayPosition());

				if (pos.equals("-1")) {
					dayOfWeek = getDayOfWeek(byDay[0]) + "L";
				}
				else {
					dayOfWeek = getDayOfWeek(byDay[0]) + StringPool.POUND + pos;
				}
			}
		}
		else if (frequency == Recurrence.YEARLY) {
			int[] byMonth = recurrence.getByMonth();

			dayOfMonth = StringPool.QUESTION;
			dayOfWeek = StringPool.QUESTION;
			year += _getIntervalValue(interval, Recurrence.YEARLY);

			if ((byMonth != null) && (byMonth.length == 1)) {
				month = String.valueOf(byMonth[0] + 1);

				if ((byMonthDay != null) && (byMonthDay.length == 1)) {
					dayOfMonth = String.valueOf(byMonthDay[0]);
				}
				else if ((byDay != null) && (byDay.length == 1)) {
					String pos = String.valueOf(byDay[0].getDayPosition());

					if (pos.equals("-1")) {
						dayOfWeek = getDayOfWeek(byDay[0]) + "L";
					}
					else {
						dayOfWeek =
							getDayOfWeek(byDay[0]) + StringPool.POUND + pos;
					}
				}
			}
		}

		StringBundler sb = new StringBundler(13);

		sb.append(startDateSecond);
		sb.append(StringPool.SPACE);
		sb.append(startDateMinute);
		sb.append(StringPool.SPACE);
		sb.append(startDateHour);
		sb.append(StringPool.SPACE);
		sb.append(dayOfMonth);
		sb.append(StringPool.SPACE);
		sb.append(month);
		sb.append(StringPool.SPACE);
		sb.append(dayOfWeek);
		sb.append(StringPool.SPACE);
		sb.append(year);

		return sb.toString();
	}

	protected static String getDayOfWeek(DayAndPosition dayPos) {
		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.set(Calendar.DAY_OF_WEEK, dayPos.getDayOfWeek());

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"EEE", LocaleUtil.US);

		return StringUtil.toUpperCase(format.format(calendar));
	}

	private static String _getIntervalValue(int interval, int period) {
		if ((interval <= 0) && (period == Recurrence.WEEKLY)) {
			return StringPool.BLANK;
		}
		else if (interval <= 0) {
			return StringPool.FORWARD_SLASH + 1;
		}

		return StringPool.FORWARD_SLASH + interval;
	}

}