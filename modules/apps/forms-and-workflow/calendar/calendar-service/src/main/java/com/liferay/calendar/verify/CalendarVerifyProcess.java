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
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.verify.VerifyProcess;

import java.sql.Timestamp;

import java.util.HashMap;
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
	}

	private CalendarBookingLocalService _calendarBookingLocalService;
	private CalendarResourceLocalService _calendarResourceLocalService;
	private ClassNameLocalService _classNameLocalService;
	private GroupLocalService _groupLocalService;
	private RoleLocalService _roleLocalService;
	private UserLocalService _userLocalService;

}