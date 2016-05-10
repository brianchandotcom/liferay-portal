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

package com.liferay.calendar.verify;


import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.recurrence.Frequency;
import com.liferay.calendar.recurrence.PositionalWeekday;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.recurrence.Weekday;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.verify.VerifyProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.calendar.service"},
	service = VerifyProcess.class
)
public class CalendarVerifyProcess extends VerifyProcess {

	protected CalendarBooking addCalendarBooking(
		String uuid, long calendarBookingId, long companyId, long groupId,
		long userId, String userName, Timestamp createDate,
		Timestamp modifiedDate, long calendarId, long calendarResourceId,
		String title, String description, String location, long startTime,
		long endTime, boolean allDay, String recurrence, int firstReminder,
		NotificationType firstReminderType, int secondReminder,
		NotificationType secondReminderType) {

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.createCalendarBooking(
				calendarBookingId);

		calendarBooking.setUuid(uuid);
		calendarBooking.setCompanyId(companyId);
		calendarBooking.setGroupId(groupId);
		calendarBooking.setUserId(userId);
		calendarBooking.setUserName(userName);
		calendarBooking.setCreateDate(createDate);
		calendarBooking.setModifiedDate(modifiedDate);
		calendarBooking.setCalendarId(calendarId);
		calendarBooking.setCalendarResourceId(calendarResourceId);
		calendarBooking.setParentCalendarBookingId(calendarBookingId);
		calendarBooking.setVEventUid(uuid);
		calendarBooking.setTitle(title);
		calendarBooking.setDescription(description);
		calendarBooking.setLocation(location);
		calendarBooking.setStartTime(startTime);
		calendarBooking.setEndTime(endTime);
		calendarBooking.setAllDay(allDay);
		calendarBooking.setRecurrence(recurrence);
		calendarBooking.setFirstReminder(firstReminder);
		calendarBooking.setFirstReminderType(firstReminderType.toString());
		calendarBooking.setSecondReminder(secondReminder);
		calendarBooking.setSecondReminderType(secondReminderType.toString());
		calendarBooking.setStatus(WorkflowConstants.STATUS_APPROVED);
		calendarBooking.setStatusByUserId(userId);
		calendarBooking.setStatusByUserName(userName);
		calendarBooking.setStatusDate(createDate);

		return _calendarBookingLocalService.updateCalendarBooking(
			calendarBooking);
	}

	protected String convertRecurrence(String originalRecurrence) {
		if (Validator.isNull(originalRecurrence)) {
			return null;
		}

		TZSRecurrence tzsRecurrence =
			(TZSRecurrence)JSONFactoryUtil.deserialize(originalRecurrence);

		if (tzsRecurrence == null) {
			return null;
		}

		Recurrence recurrence = new Recurrence();

		Frequency frequency = _frequencyMap.get(tzsRecurrence.getFrequency());

		int interval = tzsRecurrence.getInterval();

		List<PositionalWeekday> positionalWeekdays = new ArrayList<>();

		if ((frequency == Frequency.DAILY) && (interval == 0)) {
			frequency = Frequency.WEEKLY;

			interval = 1;

			positionalWeekdays.add(new PositionalWeekday(Weekday.MONDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.TUESDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.WEDNESDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.THURSDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.FRIDAY, 0));
		}
		else {
			DayAndPosition[] dayAndPositions = tzsRecurrence.getByDay();

			if (dayAndPositions != null) {
				for (DayAndPosition dayAndPosition : dayAndPositions) {
					Weekday weekday = _weekdayMap.get(
						dayAndPosition.getDayOfWeek());

					PositionalWeekday positionalWeekday = new PositionalWeekday(
						weekday, dayAndPosition.getDayPosition());

					positionalWeekdays.add(positionalWeekday);
				}
			}

			int[] months = tzsRecurrence.getByMonth();

			if (ArrayUtil.isNotEmpty(months)) {
				List<Integer> monthsList = new ArrayList<>();

				for (int month : months) {
					monthsList.add(month);
				}

				recurrence.setMonths(monthsList);
			}
		}

		recurrence.setInterval(interval);
		recurrence.setFrequency(frequency);
		recurrence.setPositionalWeekdays(positionalWeekdays);

		java.util.Calendar untilJCalendar = tzsRecurrence.getUntil();

		int ocurrence = tzsRecurrence.getOccurrence();

		if (untilJCalendar != null) {
			recurrence.setUntilJCalendar(untilJCalendar);
		}
		else if (ocurrence > 0) {
			recurrence.setCount(ocurrence);
		}

		return RecurrenceSerializer.serialize(recurrence);
	}

	@Override
	protected void doVerify() throws Exception {
		verifyCalEvent();
	}

	protected CalendarResource getCalendarResource(long companyId, long groupId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);

		long userId = _userLocalService.getDefaultUserId(companyId);

		serviceContext.setUserId(userId);

		Group group = _groupLocalService.getGroup(groupId);

		if (group.isUser()) {
			userId = group.getClassPK();

			CalendarResource calendarResource =
				_calendarResourceLocalService.fetchCalendarResource(
					_classNameLocalService.getClassNameId(User.class), userId);

			if (calendarResource != null) {
				return calendarResource;
			}

			User user = _userLocalService.getUser(userId);

			Group userGroup = null;

			String userName = user.getFullName();

			if (user.isDefaultUser()) {
				userGroup = _groupLocalService.getGroup(
					serviceContext.getCompanyId(), GroupConstants.GUEST);

				userName = GroupConstants.GUEST;
			}
			else {
				userGroup = _groupLocalService.getUserGroup(
					serviceContext.getCompanyId(), userId);
			}

			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.getDefault(), userName);

			Map<Locale, String> descriptionMap = new HashMap<>();

			return _calendarResourceLocalService.addCalendarResource(
				userId, userGroup.getGroupId(),
				_classNameLocalService.getClassNameId(User.class), userId, null,
				null, nameMap, descriptionMap, true, serviceContext);
		}
		else {
			CalendarResource calendarResource =
				_calendarResourceLocalService.fetchCalendarResource(
					_classNameLocalService.getClassNameId(Group.class),
					groupId);

			if (calendarResource != null) {
				return calendarResource;
			}

			userId = group.getCreatorUserId();

			User user = _userLocalService.fetchUserById(userId);

			if ((user == null) || user.isDefaultUser()) {
				Role role = _roleLocalService.getRole(
					group.getCompanyId(), RoleConstants.ADMINISTRATOR);

				long[] userIds = _userLocalService.getRoleUserIds(
					role.getRoleId());

				userId = userIds[0];
			}

			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.getDefault(), group.getDescriptiveName());

			Map<Locale, String> descriptionMap = new HashMap<>();

			return _calendarResourceLocalService.addCalendarResource(
				userId, groupId,
				_classNameLocalService.getClassNameId(Group.class), groupId,
				null, null, nameMap, descriptionMap, true, serviceContext);
		}
	}

	protected CalendarBooking importCalEvent(
			String uuid, long eventId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, String title, String description,
			String location, Timestamp startDate, int durationHour,
			int durationMinute, boolean allDay, String type, String recurrence,
			int firstReminder, int secondReminder)
		throws Exception {

		CalendarResource calendarResource = getCalendarResource(
			companyId, groupId);

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.fetchCalendarBookingByUuidAndGroupId(
				uuid, calendarResource.getGroupId());

		if (calendarBooking != null) {
			return calendarBooking;
		}

		long calendarBookingId = _counterLocalService.increment();

		long startTime = startDate.getTime();
		long endTime =
			startTime + durationHour * Time.HOUR + durationMinute * Time.MINUTE;

		if (allDay) {
			endTime = endTime - 1;
		}

		return addCalendarBooking(
			uuid, calendarBookingId, companyId, groupId, userId, userName,
			createDate, modifiedDate, calendarResource.getDefaultCalendarId(),
			calendarResource.getCalendarResourceId(), title, description,
			location, startTime, endTime, allDay, convertRecurrence(recurrence),
			firstReminder, NotificationType.EMAIL, secondReminder,
			NotificationType.EMAIL);
	}

	protected void importCalEvents() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(5);

			sb.append("select uuid_, eventId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, title, ");
			sb.append("description, location, startDate, endDate, ");
			sb.append("durationHour, durationMinute, allDay, type_, ");
			sb.append("repeating, recurrence, firstReminder, secondReminder ");
			sb.append("from CalEvent ");

			try (PreparedStatement ps =
					connection.prepareStatement(sb.toString())) {

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					String uuid = rs.getString("uuid_");
					long eventId = rs.getLong("eventId");
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					long userId = rs.getLong("userId");
					String userName = rs.getString("userName");
					Timestamp createDate = rs.getTimestamp("createDate");
					Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
					String title = rs.getString("title");
					String description = rs.getString("description");
					String location = rs.getString("location");
					Timestamp startDate = rs.getTimestamp("startDate");
					int durationHour = rs.getInt("durationHour");
					int durationMinute = rs.getInt("durationMinute");
					boolean allDay = rs.getBoolean("allDay");
					String type = rs.getString("type_");

					// boolean repeatiing = rs.getBoolean("repeating");

					String recurrence = rs.getString("recurrence");
					int firstReminder = rs.getInt("firstReminder");
					int secondReminder = rs.getInt("secondReminder");

					importCalEvent(
						uuid, eventId, groupId, companyId, userId, userName,
						createDate, modifiedDate, title, description, location,
						startDate, durationHour, durationMinute, allDay, type,
						recurrence, firstReminder, secondReminder);
				}
			}
		}
	}

	@Reference(unbind = "-")
	protected void setCalendarBookingLocalService(
		CalendarBookingLocalService calendarBookingLocalService) {

		_calendarBookingLocalService = calendarBookingLocalService;
	}

	@Reference(unbind = "-")
	protected void setCalendarResourceLocalService(
		CalendarResourceLocalService calendarResourceLocalService) {

		_calendarResourceLocalService = calendarResourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Reference(unbind = "-")
	protected void setCounterLocalService(
		CounterLocalService counterLocalService) {

		_counterLocalService = counterLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void verifyCalEvent() throws Exception {
		if (hasTable("CalEvent")) {
			importCalEvents();
		}
	}

	private static final Map<Integer, Frequency> _frequencyMap =
		new HashMap<>();
	private static final Map<Integer, Weekday> _weekdayMap = new HashMap<>();

	static {
		_frequencyMap.put(TZSRecurrence.DAILY, Frequency.DAILY);
		_frequencyMap.put(TZSRecurrence.WEEKLY, Frequency.WEEKLY);
		_frequencyMap.put(TZSRecurrence.MONTHLY, Frequency.MONTHLY);
		_frequencyMap.put(TZSRecurrence.YEARLY, Frequency.YEARLY);

		_weekdayMap.put(java.util.Calendar.SUNDAY, Weekday.SUNDAY);
		_weekdayMap.put(java.util.Calendar.MONDAY, Weekday.MONDAY);
		_weekdayMap.put(java.util.Calendar.TUESDAY, Weekday.TUESDAY);
		_weekdayMap.put(java.util.Calendar.WEDNESDAY, Weekday.WEDNESDAY);
		_weekdayMap.put(java.util.Calendar.THURSDAY, Weekday.THURSDAY);
		_weekdayMap.put(java.util.Calendar.FRIDAY, Weekday.FRIDAY);
		_weekdayMap.put(java.util.Calendar.SATURDAY, Weekday.SATURDAY);
	}

	private CalendarBookingLocalService _calendarBookingLocalService;
	private CalendarResourceLocalService _calendarResourceLocalService;
	private ClassNameLocalService _classNameLocalService;
	private CounterLocalService _counterLocalService;
	private GroupLocalService _groupLocalService;
	private RoleLocalService _roleLocalService;
	private UserLocalService _userLocalService;

}