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
import com.liferay.change.tracking.service.CTCollectionLocalServiceUtil;
import com.liferay.change.tracking.web.internal.scheduler.PublishScheduler;
import com.liferay.change.tracking.web.internal.scheduler.ScheduledPublishInfo;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.time.Instant;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Samuel Trong Tran
 */
public class SchedulePublicationDisplayContext {

	public SchedulePublicationDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		long ctCollectionId = ParamUtil.getLong(
			_httpServletRequest, "ctCollectionId");

		_ctCollection = CTCollectionLocalServiceUtil.getCTCollection(
			ctCollectionId);

		_scheduledPublishInfo = _getScheduledPublishInfo();
	}

	public Calendar getCalendar() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			themeDisplay.getTimeZone(), themeDisplay.getLocale());

		String publishDate = ParamUtil.getString(
			_httpServletRequest, "publishDate");

		if (Validator.isNotNull(publishDate)) {
			int day = ParamUtil.getInteger(
				_httpServletRequest, "publishTimeDay");
			int minute = ParamUtil.getInteger(
				_httpServletRequest, "publishTimeMinute");
			int month = ParamUtil.getInteger(
				_httpServletRequest, "publishTimeMonth");
			int year = ParamUtil.getInteger(
				_httpServletRequest, "publishTimeYear");

			int hour = ParamUtil.getInteger(
				_httpServletRequest, "publishTimeHour");

			if (ParamUtil.getInteger(_httpServletRequest, "publishTimeAmPm") ==
					Calendar.PM) {

				hour += 12;
			}

			calendar.setTime(
				PortalUtil.getDate(
					month, day, year, hour, minute, themeDisplay.getTimeZone(),
					PortalException.class));

			return calendar;
		}

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

	public String getRedirect() {
		return ParamUtil.getString(_httpServletRequest, "redirect");
	}

	public String getSubmitURL() {
		ActionURL submitURL = _liferayPortletResponse.createActionURL();

		if (isScheduled()) {
			submitURL.setParameter(
				ActionRequest.ACTION_NAME,
				"/change_lists/reschedule_publication");
		}
		else {
			submitURL.setParameter(
				ActionRequest.ACTION_NAME,
				"/change_lists/schedule_publication");
		}

		submitURL.setParameter("redirect", getRedirect());

		return submitURL.toString();
	}

	public String getTimeZoneDisplay() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		TimeZone timeZone = themeDisplay.getTimeZone();

		if (Objects.equals(timeZone.getID(), StringPool.UTC)) {
			return StringPool.OPEN_PARENTHESIS + StringPool.UTC +
				StringPool.CLOSE_PARENTHESIS;
		}

		Instant instant = Instant.now();

		return StringBundler.concat(
			StringPool.OPEN_PARENTHESIS,
			timeZone.getDisplayName(
				false, TimeZone.SHORT, themeDisplay.getLocale()),
			StringPool.SPACE,
			String.format("%tz", instant.atZone(timeZone.toZoneId())),
			StringPool.CLOSE_PARENTHESIS);
	}

	public String getTitle() {
		return StringBundler.concat(
			LanguageUtil.get(
				_httpServletRequest,
				isScheduled() ? "reschedule" : "schedule-to-publish-later"),
			": ", _ctCollection.getName());
	}

	public boolean isScheduled() {
		if (_scheduledPublishInfo != null) {
			return true;
		}

		return false;
	}

	private PublishScheduler _getPublishScheduler() {
		return _serviceTracker.getService();
	}

	private ScheduledPublishInfo _getScheduledPublishInfo()
		throws PortalException {

		PublishScheduler publishScheduler = _getPublishScheduler();

		return publishScheduler.getScheduledPublishInfo(_ctCollection);
	}

	private final CTCollection _ctCollection;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ScheduledPublishInfo _scheduledPublishInfo;
	private final ServiceTracker<PublishScheduler, PublishScheduler>
		_serviceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(SchedulePublicationDisplayContext.class),
			PublishScheduler.class);

}