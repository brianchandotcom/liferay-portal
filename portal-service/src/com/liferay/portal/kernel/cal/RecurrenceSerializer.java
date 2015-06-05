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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

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
		int[] byMonth = recurrence.getByMonth();

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
			dayOfMonth = 1 + StringPool.FORWARD_SLASH + interval;
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

					dayOfWeek += byDay[i].getDayOfWeek();
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

					switch (byDay[i].getDayOfWeek()) {

						case Calendar.SUNDAY :
							dayOfWeek += "SUN";
							break;

						case Calendar.MONDAY :
							dayOfWeek += "MON";
							break;

						case Calendar.TUESDAY :
							dayOfWeek += "TUE";
							break;

						case Calendar.WEDNESDAY :
							dayOfWeek += "WED";
							break;

						case Calendar.THURSDAY :
							dayOfWeek += "THU";
							break;

						case Calendar.FRIDAY :
							dayOfWeek += "FRI";
							break;

						case Calendar.SATURDAY :
							dayOfWeek += "SAT";
							break;
					}
				}
			}

			dayOfWeek += StringPool.FORWARD_SLASH + interval;
		}
		else if (frequency == Recurrence.MONTHLY) {
			dayOfMonth = StringPool.QUESTION;
			month = 1 + StringPool.FORWARD_SLASH + interval;
			dayOfWeek = StringPool.QUESTION;
			year = StringPool.STAR;

			if ((byMonthDay != null) && (byMonthDay.length == 1)) {
				dayOfMonth = String.valueOf(byMonthDay[0]);
			}
			else if ((byDay != null) && (byDay.length == 1)) {
				String pos = String.valueOf(byDay[0].getDayPosition());

				if (pos.equals("-1")) {
					dayOfWeek = byDay[0].getDayOfWeek() + "L";
				}
				else {
					dayOfWeek =
						byDay[0].getDayOfWeek() + StringPool.POUND + pos;
				}
			}
		}
		else if (frequency == Recurrence.YEARLY) {
			dayOfMonth = StringPool.QUESTION;
			dayOfWeek = StringPool.QUESTION;
			year += StringPool.FORWARD_SLASH + interval;

			if ((byMonth != null) && (byMonth.length == 1)) {
				month = String.valueOf(byMonth[0] + 1);

				if ((byMonthDay != null) && (byMonthDay.length == 1)) {
					dayOfMonth = String.valueOf(byMonthDay[0]);
				}
				else if ((byDay != null) && (byDay.length == 1)) {
					String pos = String.valueOf(byDay[0].getDayPosition());

					if (pos.equals("-1")) {
						dayOfWeek = byDay[0].getDayOfWeek() + "L";
					}
					else {
						dayOfWeek =
							byDay[0].getDayOfWeek() + StringPool.POUND + pos;
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

}