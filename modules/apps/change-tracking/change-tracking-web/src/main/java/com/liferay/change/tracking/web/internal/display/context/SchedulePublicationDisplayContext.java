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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.web.internal.scheduler.ScheduledPublishInfo;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class SchedulePublicationDisplayContext {

	public SchedulePublicationDisplayContext(
		CTCollection ctCollection, HttpServletRequest httpServletRequest,
		ScheduledPublishInfo scheduledPublishInfo) {

		_ctCollection = ctCollection;
		_httpServletRequest = httpServletRequest;
		_scheduledPublishInfo = scheduledPublishInfo;
	}

	public Calendar getCalendar() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			themeDisplay.getTimeZone(), themeDisplay.getLocale());

		if (_scheduledPublishInfo != null) {
			calendar.setTime(_scheduledPublishInfo.getStartDate());

			return calendar;
		}

		Date currentDate = new Date(System.currentTimeMillis());

		calendar.setTime(currentDate);

		if (calendar.get(Calendar.MINUTE) <= 30) {
			calendar.set(Calendar.MINUTE, 30);
		}
		else {
			calendar.add(Calendar.HOUR, 1);
			calendar.set(Calendar.MINUTE, 0);
		}

		return calendar;
	}

	public CTCollection getCTCollection() {
		return _ctCollection;
	}

	public boolean isScheduled() {
		if (_scheduledPublishInfo != null) {
			return true;
		}

		return false;
	}

	private final CTCollection _ctCollection;
	private final HttpServletRequest _httpServletRequest;
	private final ScheduledPublishInfo _scheduledPublishInfo;

}