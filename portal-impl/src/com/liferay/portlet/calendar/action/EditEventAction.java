/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.action;

import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.calendar.EventDurationException;
import com.liferay.portlet.calendar.EventEndDateException;
import com.liferay.portlet.calendar.EventStartDateException;
import com.liferay.portlet.calendar.EventTitleException;
import com.liferay.portlet.calendar.NoSuchEventException;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.CalEventConstants;
import com.liferay.portlet.calendar.service.CalEventServiceUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class EditEventAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			CalEvent event = null;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				event = updateEvent(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEvent(actionRequest);
			}

			WindowState windowState = actionRequest.getWindowState();

			if (!windowState.equals(LiferayWindowState.POP_UP)) {
				sendRedirect(actionRequest, actionResponse);
			}
			else {
				String redirect = PortalUtil.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					if (cmd.equals(Constants.ADD) && (event != null)) {
						redirect = HttpUtil.addParameter(
							redirect, "className", CalEvent.class.getName());
						redirect = HttpUtil.addParameter(
							redirect, "classPK", event.getEventId());
					}

					actionResponse.sendRedirect(redirect);
				}
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchEventException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.calendar.error");
			}
			else if (e instanceof EventDurationException ||
					 e instanceof EventEndDateException ||
					 e instanceof EventStartDateException ||
					 e instanceof EventTitleException ||
					 e instanceof SanitizerException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				Throwable cause = e.getCause();

				if (cause instanceof SanitizerException) {
					SessionErrors.add(actionRequest, SanitizerException.class);
				}
				else {
					throw e;
				}
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getEvent(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchEventException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.calendar.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.calendar.edit_event"));
	}

	protected void addWeeklyDayPos(
		ActionRequest actionRequest, List<DayAndPosition> list, int day) {

		if (ParamUtil.getBoolean(actionRequest, "weeklyDayPos" + day)) {
			list.add(new DayAndPosition(day, 0));
		}
	}

	protected void deleteEvent(ActionRequest actionRequest) throws Exception {
		long eventId = ParamUtil.getLong(actionRequest, "eventId");

		CalEventServiceUtil.deleteEvent(eventId);
	}

	protected CalEvent updateEvent(ActionRequest actionRequest)
		throws Exception {

		long eventId = ParamUtil.getLong(actionRequest, "eventId");

		String title = ParamUtil.getString(actionRequest, "title");
		String description = ParamUtil.getString(actionRequest, "description");
		String location = ParamUtil.getString(actionRequest, "location");

		int startDateMonth = ParamUtil.getInteger(
			actionRequest, "startDateMonth");
		int startDateDay = ParamUtil.getInteger(actionRequest, "startDateDay");
		int startDateYear = ParamUtil.getInteger(
			actionRequest, "startDateYear");
		int startDateHour = ParamUtil.getInteger(
			actionRequest, "startDateHour");
		int startDateMinute = ParamUtil.getInteger(
			actionRequest, "startDateMinute");
		int startDateAmPm = ParamUtil.getInteger(
			actionRequest, "startDateAmPm");

		if (startDateAmPm == Calendar.PM) {
			startDateHour += 12;
		}

		int durationHour = ParamUtil.getInteger(actionRequest, "durationHour");
		int durationMinute = ParamUtil.getInteger(
			actionRequest, "durationMinute");
		boolean allDay = ParamUtil.getBoolean(actionRequest, "allDay");

		boolean timeZoneSensitive = false;

		if (allDay) {
			timeZoneSensitive = false;
		}
		else {
			timeZoneSensitive = ParamUtil.getBoolean(
				actionRequest, "timeZoneSensitive");
		}

		String type = ParamUtil.getString(actionRequest, "type");

		int endDateMonth = ParamUtil.getInteger(actionRequest, "endDateMonth");
		int endDateDay = ParamUtil.getInteger(actionRequest, "endDateDay");
		int endDateYear = ParamUtil.getInteger(actionRequest, "endDateYear");

		boolean repeating = false;

		int recurrenceType = ParamUtil.getInteger(
			actionRequest, "recurrenceType");

		if (recurrenceType != Recurrence.NO_RECURRENCE) {
			repeating = true;
		}

		Locale locale = null;
		TimeZone timeZone = null;

		if (timeZoneSensitive) {
			User user = PortalUtil.getUser(actionRequest);

			locale = user.getLocale();
			timeZone = user.getTimeZone();
		}
		else {
			locale = LocaleUtil.getDefault();
			timeZone = TimeZoneUtil.getTimeZone(StringPool.UTC);
		}

		Calendar startDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

		startDate.set(Calendar.MONTH, startDateMonth);
		startDate.set(Calendar.DATE, startDateDay);
		startDate.set(Calendar.YEAR, startDateYear);
		startDate.set(Calendar.HOUR_OF_DAY, startDateHour);
		startDate.set(Calendar.MINUTE, startDateMinute);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);

		if (allDay) {
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);

			durationHour = 24;
			durationMinute = 0;
		}

		TZSRecurrence recurrence = null;

		if (repeating) {
			Calendar recStartCal = null;

			if (timeZoneSensitive) {
				recStartCal = CalendarFactoryUtil.getCalendar(
					TimeZoneUtil.getTimeZone(StringPool.UTC));

				recStartCal.setTime(startDate.getTime());
			}
			else {
				recStartCal = (Calendar)startDate.clone();
			}

			recurrence = new TZSRecurrence(
				recStartCal, new Duration(1, 0, 0, 0), recurrenceType);

			recurrence.setTimeZone(timeZone);

			recurrence.setWeekStart(Calendar.SUNDAY);

			if (recurrenceType == Recurrence.DAILY) {
				int dailyType = ParamUtil.getInteger(
					actionRequest, "dailyType");

				if (dailyType == 0) {
					int dailyInterval = ParamUtil.getInteger(
						actionRequest, "dailyInterval", 1);

					recurrence.setInterval(dailyInterval);
				}
				else {
					DayAndPosition[] dayPos = {
						new DayAndPosition(Calendar.MONDAY, 0),
						new DayAndPosition(Calendar.TUESDAY, 0),
						new DayAndPosition(Calendar.WEDNESDAY, 0),
						new DayAndPosition(Calendar.THURSDAY, 0),
						new DayAndPosition(Calendar.FRIDAY, 0)};

					recurrence.setByDay(dayPos);
				}
			}
			else if (recurrenceType == Recurrence.WEEKLY) {
				int weeklyInterval = ParamUtil.getInteger(
					actionRequest, "weeklyInterval", 1);

				recurrence.setInterval(weeklyInterval);

				List<DayAndPosition> dayPos = new ArrayList<DayAndPosition>();

				addWeeklyDayPos(actionRequest, dayPos, Calendar.SUNDAY);
				addWeeklyDayPos(actionRequest, dayPos, Calendar.MONDAY);
				addWeeklyDayPos(actionRequest, dayPos, Calendar.TUESDAY);
				addWeeklyDayPos(actionRequest, dayPos, Calendar.WEDNESDAY);
				addWeeklyDayPos(actionRequest, dayPos, Calendar.THURSDAY);
				addWeeklyDayPos(actionRequest, dayPos, Calendar.FRIDAY);
				addWeeklyDayPos(actionRequest, dayPos, Calendar.SATURDAY);

				if (dayPos.size() == 0) {
					dayPos.add(new DayAndPosition(Calendar.MONDAY, 0));
				}

				recurrence.setByDay(
					dayPos.toArray(new DayAndPosition[dayPos.size()]));
			}
			else if (recurrenceType == Recurrence.MONTHLY) {
				int monthlyType = ParamUtil.getInteger(
					actionRequest, "monthlyType");

				if (monthlyType == 0) {
					int monthlyDay = ParamUtil.getInteger(
						actionRequest, "monthlyDay0");

					recurrence.setByMonthDay(new int[] {monthlyDay});

					int monthlyInterval = ParamUtil.getInteger(
						actionRequest, "monthlyInterval0", 1);

					recurrence.setInterval(monthlyInterval);
				}
				else {
					int monthlyPos = ParamUtil.getInteger(
						actionRequest, "monthlyPos");
					int monthlyDay = ParamUtil.getInteger(
						actionRequest, "monthlyDay1");

					DayAndPosition[] dayPos = {
						new DayAndPosition(monthlyDay, monthlyPos)};

					recurrence.setByDay(dayPos);

					int monthlyInterval = ParamUtil.getInteger(
						actionRequest, "monthlyInterval1", 1);

					recurrence.setInterval(monthlyInterval);
				}
			}
			else if (recurrenceType == Recurrence.YEARLY) {
				int yearlyType = ParamUtil.getInteger(
					actionRequest, "yearlyType");

				if (yearlyType == 0) {
					int yearlyMonth = ParamUtil.getInteger(
						actionRequest, "yearlyMonth0");
					int yearlyDay = ParamUtil.getInteger(
						actionRequest, "yearlyDay0");

					recurrence.setByMonth(new int[] {yearlyMonth});
					recurrence.setByMonthDay(new int[] {yearlyDay});

					int yearlyInterval = ParamUtil.getInteger(
						actionRequest, "yearlyInterval0", 1);

					recurrence.setInterval(yearlyInterval);
				}
				else {
					int yearlyPos = ParamUtil.getInteger(
						actionRequest, "yearlyPos");
					int yearlyDay = ParamUtil.getInteger(
						actionRequest, "yearlyDay1");
					int yearlyMonth = ParamUtil.getInteger(
						actionRequest, "yearlyMonth1");

					DayAndPosition[] dayPos = {
						new DayAndPosition(yearlyDay, yearlyPos)};

					recurrence.setByDay(dayPos);

					recurrence.setByMonth(new int[] {yearlyMonth});

					int yearlyInterval = ParamUtil.getInteger(
						actionRequest, "yearlyInterval1", 1);

					recurrence.setInterval(yearlyInterval);
				}
			}

			int endDateType = ParamUtil.getInteger(
				actionRequest, "endDateType");

			if (endDateType == CalEventConstants.END_DATE_TYPE_END_AFTER) {
				int endDateOccurrence = ParamUtil.getInteger(
					actionRequest, "endDateOccurrence");

				recurrence.setOccurrence(endDateOccurrence);
			}
			else if (endDateType == CalEventConstants.END_DATE_TYPE_END_BY) {
				Calendar endDate = CalendarFactoryUtil.getCalendar(timeZone);

				endDate.set(Calendar.MONTH, endDateMonth);
				endDate.set(Calendar.DATE, endDateDay);
				endDate.set(Calendar.YEAR, endDateYear);
				endDate.set(Calendar.HOUR_OF_DAY, startDateHour);
				endDate.set(Calendar.MINUTE, startDateMinute);
				endDate.set(Calendar.SECOND, 0);
				endDate.set(Calendar.MILLISECOND, 0);

				Calendar recEndCal = null;

				if (timeZoneSensitive) {
					recEndCal = CalendarFactoryUtil.getCalendar(
						TimeZoneUtil.getTimeZone(StringPool.UTC));

					recEndCal.setTime(endDate.getTime());
				}
				else {
					recEndCal = (Calendar)endDate.clone();
				}

				recurrence.setUntil(recEndCal);
			}
		}

		int remindBy = ParamUtil.getInteger(actionRequest, "remindBy");
		int firstReminder = ParamUtil.getInteger(
			actionRequest, "firstReminder");
		int secondReminder = ParamUtil.getInteger(
			actionRequest, "secondReminder");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CalEvent.class.getName(), actionRequest);

		CalEvent event = null;

		if (eventId <= 0) {

			// Add event

			event = CalEventServiceUtil.addEvent(
				title, description, location, startDateMonth, startDateDay,
				startDateYear, startDateHour, startDateMinute, durationHour,
				durationMinute, allDay, timeZoneSensitive, type, repeating,
				recurrence, remindBy, firstReminder, secondReminder,
				serviceContext);

			AssetPublisherUtil.addAndStoreSelection(
				actionRequest, CalEvent.class.getName(), event.getEventId(),
				-1);
		}
		else {

			// Update event

			event = CalEventServiceUtil.updateEvent(
				eventId, title, description, location, startDateMonth,
				startDateDay, startDateYear, startDateHour, startDateMinute,
				durationHour, durationMinute, allDay, timeZoneSensitive, type,
				repeating, recurrence, remindBy, firstReminder, secondReminder,
				serviceContext);
		}

		return event;
	}

}