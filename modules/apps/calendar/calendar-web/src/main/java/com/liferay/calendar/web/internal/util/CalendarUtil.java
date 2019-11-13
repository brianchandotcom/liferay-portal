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

package com.liferay.calendar.web.internal.util;

import com.liferay.calendar.constants.CalendarActionKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.service.CalendarBookingService;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.calendar.service.CalendarService;
import com.liferay.calendar.util.JCalendarUtil;
import com.liferay.calendar.util.RecurrenceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Lundgren
 * @author Peter Shin
 * @author Fabio Pezzutto
 */
@Component(immediate = true, service = {})
public class CalendarUtil {

	public static JSONObject getCalendarRenderingRules(
			ThemeDisplay themeDisplay, long[] calendarIds, int[] statuses,
			long startTime, long endTime, String ruleName, TimeZone timeZone)
		throws PortalException {

		List<CalendarBooking> calendarBookings = _calendarBookingService.search(
			themeDisplay.getCompanyId(), null, calendarIds, new long[0], -1,
			null, startTime, endTime, true, statuses, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Map<Integer, Map<Integer, List<Integer>>> rulesMap = new HashMap<>();

		for (CalendarBooking calendarBooking : calendarBookings) {
			TimeZone displayTimeZone = timeZone;

			if (calendarBooking.isAllDay()) {
				displayTimeZone = _utcTimeZone;
			}

			long maxStartTime = Math.max(
				calendarBooking.getStartTime(), startTime);

			java.util.Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
				maxStartTime, displayTimeZone);

			long minEndTime = Math.min(calendarBooking.getEndTime(), endTime);

			java.util.Calendar endTimeJCalendar = JCalendarUtil.getJCalendar(
				minEndTime, displayTimeZone);

			long days = JCalendarUtil.getDaysBetween(
				startTimeJCalendar, endTimeJCalendar);

			for (int i = 0; i <= days; i++) {
				int year = startTimeJCalendar.get(java.util.Calendar.YEAR);

				Map<Integer, List<Integer>> rulesMonth = rulesMap.get(year);

				if (rulesMonth == null) {
					rulesMonth = new HashMap<>();

					rulesMap.put(year, rulesMonth);
				}

				int month = startTimeJCalendar.get(java.util.Calendar.MONTH);

				List<Integer> rulesDay = rulesMonth.get(month);

				if (rulesDay == null) {
					rulesDay = new ArrayList<>();

					rulesMonth.put(month, rulesDay);
				}

				int day = startTimeJCalendar.get(
					java.util.Calendar.DAY_OF_MONTH);

				if (!rulesDay.contains(day)) {
					rulesDay.add(day);
				}

				startTimeJCalendar.add(java.util.Calendar.DATE, 1);
			}
		}

		Set<Integer> years = rulesMap.keySet();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Integer year : years) {
			Map<Integer, List<Integer>> monthsMap = rulesMap.get(year);

			Set<Integer> months = monthsMap.keySet();

			JSONObject jsonObjectMonth = JSONFactoryUtil.createJSONObject();

			jsonObject.put(String.valueOf(year), jsonObjectMonth);

			for (Integer month : months) {
				List<Integer> days = monthsMap.get(month);

				JSONObject jsonObjectDay = JSONUtil.put(
					StringUtil.merge(days), ruleName);

				jsonObjectMonth.put(String.valueOf(month), jsonObjectDay);
			}
		}

		return jsonObject;
	}

	public static Collection<CalendarResource> getCalendarResources(
			List<CalendarBooking> calendarBookings)
		throws PortalException {

		Set<CalendarResource> calendarResources = new HashSet<>();

		for (CalendarBooking calendarBooking : calendarBookings) {
			calendarResources.add(calendarBooking.getCalendarResource());
		}

		return calendarResources;
	}

	public static JSONObject toCalendarBookingJSONObject(
			ThemeDisplay themeDisplay, CalendarBooking calendarBooking,
			TimeZone timeZone)
		throws PortalException {

		JSONObject jsonObject = JSONUtil.put(
			"allDay", calendarBooking.isAllDay()
		).put(
			"calendarBookingId", calendarBooking.getCalendarBookingId()
		).put(
			"calendarId", calendarBooking.getCalendarId()
		).put(
			"description",
			calendarBooking.getDescription(themeDisplay.getLocale())
		);

		java.util.Calendar endTimeJCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getEndTime(), timeZone);

		_addTimeProperties(jsonObject, "endTime", endTimeJCalendar);

		jsonObject.put(
			"firstReminder", calendarBooking.getFirstReminder()
		).put(
			"firstReminderType", calendarBooking.getFirstReminderType()
		);

		List<CalendarBooking> childCalendarBookings =
			calendarBooking.getChildCalendarBookings();

		jsonObject.put(
			"hasChildCalendarBookings", childCalendarBookings.size() > 1
		).put(
			"hasWorkflowInstanceLink",
			_workflowInstanceLinkLocalService.hasWorkflowInstanceLink(
				themeDisplay.getCompanyId(), calendarBooking.getGroupId(),
				CalendarBooking.class.getName(),
				calendarBooking.getCalendarBookingId())
		).put(
			"instanceIndex", calendarBooking.getInstanceIndex()
		).put(
			"location", calendarBooking.getLocation()
		).put(
			"parentCalendarBookingId",
			calendarBooking.getParentCalendarBookingId()
		);

		CalendarBooking lastInstanceCalendarBooking =
			_calendarBookingService.getLastInstanceCalendarBooking(
				calendarBooking.getCalendarBookingId());

		String recurrence = lastInstanceCalendarBooking.getRecurrence();

		java.util.Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getStartTime(), timeZone);

		if (Validator.isNotNull(recurrence)) {
			Recurrence recurrenceObj = RecurrenceUtil.inTimeZone(
				lastInstanceCalendarBooking.getRecurrenceObj(),
				startTimeJCalendar, timeZone);

			recurrence = RecurrenceSerializer.serialize(recurrenceObj);
		}

		jsonObject.put(
			"recurrence", recurrence
		).put(
			"recurringCalendarBookingId",
			calendarBooking.getRecurringCalendarBookingId()
		).put(
			"secondReminder", calendarBooking.getSecondReminder()
		).put(
			"secondReminderType", calendarBooking.getSecondReminder()
		);

		_addTimeProperties(jsonObject, "startTime", startTimeJCalendar);

		jsonObject.put(
			"status", calendarBooking.getStatus()
		).put(
			"title", calendarBooking.getTitle(themeDisplay.getLocale())
		);

		return jsonObject;
	}

	public static JSONArray toCalendarBookingsJSONArray(
			ThemeDisplay themeDisplay, List<CalendarBooking> calendarBookings)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (calendarBookings == null) {
			return jsonArray;
		}

		for (CalendarBooking calendarBooking : calendarBookings) {
			JSONObject jsonObject = toCalendarJSONObject(
				themeDisplay, calendarBooking.getCalendar());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public static JSONArray toCalendarBookingsJSONArray(
			ThemeDisplay themeDisplay, List<CalendarBooking> calendarBookings,
			TimeZone timeZone)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (CalendarBooking calendarBooking : calendarBookings) {
			if ((calendarBooking.getStatus() ==
					WorkflowConstants.STATUS_DRAFT) &&
				(calendarBooking.getUserId() != themeDisplay.getUserId())) {

				continue;
			}

			JSONObject jsonObject = toCalendarBookingJSONObject(
				themeDisplay, calendarBooking, timeZone);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public static JSONObject toCalendarJSONObject(
			ThemeDisplay themeDisplay, Calendar calendar)
		throws PortalException {

		JSONObject jsonObject = JSONUtil.put(
			"calendarId", calendar.getCalendarId());

		CalendarResource calendarResource =
			_calendarResourceLocalService.fetchCalendarResource(
				calendar.getCalendarResourceId());

		jsonObject.put(
			"calendarResourceId", calendarResource.getCalendarResourceId()
		).put(
			"calendarResourceName",
			calendarResource.getName(themeDisplay.getLocale())
		).put(
			"classNameId", calendarResource.getClassNameId()
		).put(
			"classPK", calendarResource.getClassPK()
		).put(
			"color", ColorUtil.toHexString(calendar.getColor())
		).put(
			"defaultCalendar", calendar.isDefaultCalendar()
		).put(
			"groupId", calendar.getGroupId()
		).put(
			"hasWorkflowDefinitionLink",
			_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				themeDisplay.getCompanyId(), calendarResource.getGroupId(),
				CalendarBooking.class.getName())
		).put(
			"manageable",
			_calendarService.isManageableFromGroup(
				calendar.getCalendarId(), themeDisplay.getScopeGroupId())
		).put(
			"name", calendar.getName(themeDisplay.getLocale())
		).put(
			"permissions",
			_getPermissionsJSONObject(
				themeDisplay.getPermissionChecker(), calendar)
		).put(
			"userId", calendar.getUserId()
		);

		return jsonObject;
	}

	public static JSONObject toCalendarResourceJSONObject(
		ThemeDisplay themeDisplay, CalendarResource calendarResource) {

		return JSONUtil.put(
			"calendarResourceId", calendarResource.getCalendarResourceId()
		).put(
			"classNameId", calendarResource.getClassNameId()
		).put(
			"classPK", calendarResource.getClassPK()
		).put(
			"classUuid", calendarResource.getClassUuid()
		).put(
			"code", calendarResource.getCode()
		).put(
			"groupId", calendarResource.getGroupId()
		).put(
			"name", calendarResource.getName(themeDisplay.getLocale())
		).put(
			"userId", calendarResource.getUserId()
		);
	}

	public static JSONArray toCalendarsJSONArray(
			ThemeDisplay themeDisplay, List<Calendar> calendars)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (calendars == null) {
			return jsonArray;
		}

		for (Calendar calendar : calendars) {
			JSONObject jsonObject = toCalendarJSONObject(
				themeDisplay, calendar);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Reference(unbind = "-")
	protected void setCalendarBookingService(
		CalendarBookingService calendarBookingService) {

		_calendarBookingService = calendarBookingService;
	}

	@Reference(unbind = "-")
	protected void setCalendarResourceLocalService(
		CalendarResourceLocalService calendarResourceLocalService) {

		_calendarResourceLocalService = calendarResourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setCalendarService(CalendarService calendarService) {
		_calendarService = calendarService;
	}

	@Reference(
		target = "(model.class.name=com.liferay.calendar.model.Calendar)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<Calendar> modelResourcePermission) {

		_calendarModelResourcePermission = modelResourcePermission;
	}

	@Reference(unbind = "-")
	protected void setWorkflowDefinitionLinkLocalService(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {

		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setWorkflowInstanceLinkLocalService(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {

		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	private static void _addTimeProperties(
		JSONObject jsonObject, String prefix, java.util.Calendar jCalendar) {

		jsonObject.put(
			prefix, jCalendar.getTimeInMillis()
		).put(
			prefix + "Day", jCalendar.get(java.util.Calendar.DAY_OF_MONTH)
		).put(
			prefix + "Hour", jCalendar.get(java.util.Calendar.HOUR_OF_DAY)
		).put(
			prefix + "Minute", jCalendar.get(java.util.Calendar.MINUTE)
		).put(
			prefix + "Month", jCalendar.get(java.util.Calendar.MONTH)
		).put(
			prefix + "Year", jCalendar.get(java.util.Calendar.YEAR)
		);
	}

	private static JSONObject _getPermissionsJSONObject(
			PermissionChecker permissionChecker, Calendar calendar)
		throws PortalException {

		return JSONUtil.put(
			ActionKeys.DELETE,
			_calendarModelResourcePermission.contains(
				permissionChecker, calendar, ActionKeys.DELETE)
		).put(
			ActionKeys.PERMISSIONS,
			_calendarModelResourcePermission.contains(
				permissionChecker, calendar, ActionKeys.PERMISSIONS)
		).put(
			ActionKeys.UPDATE,
			_calendarModelResourcePermission.contains(
				permissionChecker, calendar, ActionKeys.UPDATE)
		).put(
			ActionKeys.VIEW,
			_calendarModelResourcePermission.contains(
				permissionChecker, calendar, ActionKeys.VIEW)
		).put(
			CalendarActionKeys.MANAGE_BOOKINGS,
			_calendarModelResourcePermission.contains(
				permissionChecker, calendar, CalendarActionKeys.MANAGE_BOOKINGS)
		).put(
			CalendarActionKeys.VIEW_BOOKING_DETAILS,
			_calendarModelResourcePermission.contains(
				permissionChecker, calendar,
				CalendarActionKeys.VIEW_BOOKING_DETAILS)
		);
	}

	private static CalendarBookingService _calendarBookingService;
	private static ModelResourcePermission<Calendar>
		_calendarModelResourcePermission;
	private static CalendarResourceLocalService _calendarResourceLocalService;
	private static CalendarService _calendarService;
	private static final TimeZone _utcTimeZone = TimeZone.getTimeZone(
		StringPool.UTC);
	private static WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;
	private static WorkflowInstanceLinkLocalService
		_workflowInstanceLinkLocalService;

}