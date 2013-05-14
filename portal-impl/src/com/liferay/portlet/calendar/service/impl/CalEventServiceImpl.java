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

package com.liferay.portlet.calendar.service.impl;

import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.base.CalEventServiceBaseImpl;
import com.liferay.portlet.calendar.service.permission.CalEventPermission;
import com.liferay.portlet.calendar.service.permission.CalendarPermission;

import java.io.File;
import java.io.InputStream;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
public class CalEventServiceImpl extends CalEventServiceBaseImpl {

	@Override
	public CalEvent addEvent(
			String title, String description, String location,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, TZSRecurrence recurrence,
			int remindBy, int firstReminder, int secondReminder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		CalendarPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_EVENT);

		return calEventLocalService.addEvent(
			getUserId(), title, description, location, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			durationHour, durationMinute, allDay, timeZoneSensitive, type,
			repeating, recurrence, remindBy, firstReminder, secondReminder,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addEvent(String, String,
	 *             String, int, int, int, int, int, int, int, boolean, boolean,
	 *             String, boolean, TZSRecurrence, int, int, int,
	 *             ServiceContext)}
	 */
	@Override
	public CalEvent addEvent(
			String title, String description, String location,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, TZSRecurrence recurrence,
			int remindBy, int firstReminder, int secondReminder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		CalendarPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_EVENT);

		return calEventLocalService.addEvent(
			getUserId(), title, description, location, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, durationHour, durationMinute,
			allDay, timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	@Override
	public void deleteEvent(long eventId)
		throws PortalException, SystemException {

		CalEventPermission.check(
			getPermissionChecker(), eventId, ActionKeys.DELETE);

		calEventLocalService.deleteEvent(eventId);
	}

	@Override
	public File exportEvent(long eventId)
		throws PortalException, SystemException {

		CalEventPermission.check(
			getPermissionChecker(), eventId, ActionKeys.VIEW);

		return calEventLocalService.exportEvent(getGuestOrUserId(), eventId);
	}

	@Override
	public File exportEvents(List<CalEvent> events, String fileName)
		throws PortalException, SystemException {

		for (CalEvent event : events) {
			CalEventPermission.check(
				getPermissionChecker(), event.getEventId(), ActionKeys.VIEW);
		}

		return calEventLocalService.exportEvents(
			getGuestOrUserId(), events, fileName);
	}

	@Override
	public File exportGroupEvents(long groupId, String fileName)
		throws PortalException, SystemException {

		CalendarPermission.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_ALL_EVENTS);

		return calEventLocalService.exportGroupEvents(
			getGuestOrUserId(), groupId, fileName);
	}

	@Override
	public CalEvent getEvent(long eventId)
		throws PortalException, SystemException {

		CalEventPermission.check(
			getPermissionChecker(), eventId, ActionKeys.VIEW);

		return calEventLocalService.getEvent(eventId);
	}

	@Override
	public List<CalEvent> getEvents(long groupId, Calendar cal, String type)
		throws PortalException, SystemException {

		return getEvents(groupId, cal, new String[] {type});
	}

	@Override
	public List<CalEvent> getEvents(long groupId, Calendar cal, String[] types)
		throws PortalException, SystemException {

		List<CalEvent> events = calEventLocalService.getEvents(
			groupId, cal, types);

		events = ListUtil.copy(events);

		Iterator<CalEvent> itr = events.iterator();

		while (itr.hasNext()) {
			CalEvent event = itr.next();

			if (!CalEventPermission.contains(
					getPermissionChecker(), event, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return events;
	}

	@Override
	public List<CalEvent> getEvents(
			long groupId, String type, int start, int end)
		throws SystemException {

		return getEvents(groupId, new String[] {type}, start, end);
	}

	@Override
	public List<CalEvent> getEvents(
			long groupId, String[] types, int start, int end)
		throws SystemException {

		if ((types != null) && (types.length > 0) &&
			((types.length > 1) || Validator.isNotNull(types[0]))) {

			return calEventPersistence.filterFindByG_T(
				groupId, types, start, end);
		}
		else {
			return calEventPersistence.filterFindByGroupId(groupId, start, end);
		}
	}

	@Override
	public int getEventsCount(long groupId, String type)
		throws SystemException {

		return getEventsCount(groupId, new String[] {type});
	}

	@Override
	public int getEventsCount(long groupId, String[] types)
		throws SystemException {

		if ((types != null) && (types.length > 0) &&
			((types.length > 1) || Validator.isNotNull(types[0]))) {

			return calEventPersistence.filterCountByG_T(groupId, types);
		}
		else {
			return calEventPersistence.filterCountByGroupId(groupId);
		}
	}

	@Override
	public boolean hasEvents(long groupId, Calendar cal)
		throws PortalException, SystemException {

		return hasEvents(groupId, cal, new String[0]);
	}

	@Override
	public boolean hasEvents(long groupId, Calendar cal, String type)
		throws PortalException, SystemException {

		return hasEvents(groupId, cal, new String[] {type});
	}

	@Override
	public boolean hasEvents(long groupId, Calendar cal, String[] types)
		throws PortalException, SystemException {

		List<CalEvent> events = getEvents(groupId, cal, types);

		if (events.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void importICal4j(long groupId, InputStream inputStream)
		throws PortalException, SystemException {

		CalendarPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_EVENT);

		calEventLocalService.importICal4j(getUserId(), groupId, inputStream);
	}

	@Override
	public CalEvent updateEvent(
			long eventId, String title, String description, String location,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, TZSRecurrence recurrence,
			int remindBy, int firstReminder, int secondReminder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		CalEventPermission.check(
			getPermissionChecker(), eventId, ActionKeys.UPDATE);

		return calEventLocalService.updateEvent(
			getUserId(), eventId, title, description, location, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			durationHour, durationMinute, allDay, timeZoneSensitive, type,
			repeating, recurrence, remindBy, firstReminder, secondReminder,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #updateEvent(long, String,
	 *             String, String, int, int, int, int, int, int, int, boolean,
	 *             boolean, String, boolean, TZSRecurrence, int, int, int,
	 *             ServiceContext)}
	 */
	@Override
	public CalEvent updateEvent(
			long eventId, String title, String description, String location,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, TZSRecurrence recurrence,
			int remindBy, int firstReminder, int secondReminder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		CalEventPermission.check(
			getPermissionChecker(), eventId, ActionKeys.UPDATE);

		return calEventLocalService.updateEvent(
			getUserId(), eventId, title, description, location, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, durationHour, durationMinute,
			allDay, timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

}