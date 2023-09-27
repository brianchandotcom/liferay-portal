/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.calendar;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.workflow.kaleo.definition.DelayDuration;
import com.liferay.portal.workflow.kaleo.definition.DurationScale;
import com.liferay.portal.workflow.kaleo.runtime.calendar.DueDateCalculator;

import java.util.Calendar;
import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = DueDateCalculator.class)
public class DefaultDueDateCalculator implements DueDateCalculator {

	@Override
	public Date getDueDate(Date startDate, DelayDuration delayDuration) {
		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.setTime(startDate);

		DurationScale durationScale = delayDuration.getDurationScale();

		int duration = (int)Math.round(delayDuration.getDuration());

		if (durationScale.equals(DurationScale.SECOND)) {
			cal.add(Calendar.SECOND, duration);
		}
		else if (durationScale.equals(DurationScale.MINUTE)) {
			cal.add(Calendar.MINUTE, duration);
		}
		else if (durationScale.equals(DurationScale.HOUR)) {
			cal.add(Calendar.HOUR, duration);
		}
		else if (durationScale.equals(DurationScale.DAY)) {
			cal.add(Calendar.DAY_OF_YEAR, duration);
		}
		else if (durationScale.equals(DurationScale.MONTH)) {
			cal.add(Calendar.MONTH, duration);
		}
		else if (durationScale.equals(DurationScale.YEAR)) {
			cal.add(Calendar.YEAR, duration);
		}

		return cal.getTime();
	}

}