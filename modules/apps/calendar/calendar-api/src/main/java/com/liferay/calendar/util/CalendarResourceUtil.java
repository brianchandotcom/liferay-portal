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

package com.liferay.calendar.util;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 * @author Fabio Pezzutto
 * @author Marcellus Tavares
 */
public class CalendarResourceUtil {

	public static CalendarResource fetchGuestCalendarResource(long companyId)
		throws PortalException {

		return CalendarResourceLocalServiceUtil.fetchCalendarResource(
			PortalUtil.getClassNameId(User.class),
			UserLocalServiceUtil.getDefaultUserId(companyId));
	}

	public static CalendarResource getGroupCalendarResource(
			long groupId, ServiceContext serviceContext)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isUser()) {
			return null;
		}

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.fetchCalendarResource(
				PortalUtil.getClassNameId(Group.class), groupId);

		if (calendarResource != null) {
			return calendarResource;
		}
		else if (group.hasStagingGroup() &&
				 group.isStagedPortlet(CalendarPortletKeys.CALENDAR)) {

			return null;
		}

		long userId = group.getCreatorUserId();

		User user = UserLocalServiceUtil.fetchUserById(userId);

		if ((user == null) || user.isDefaultUser()) {
			Role role = RoleLocalServiceUtil.getRole(
				group.getCompanyId(), RoleConstants.ADMINISTRATOR);

			long[] userIds = UserLocalServiceUtil.getRoleUserIds(
				role.getRoleId());

			userId = userIds[0];
		}

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), group.getDescriptiveName());

		Map<Locale, String> descriptionMap = new HashMap<>();

		return CalendarResourceLocalServiceUtil.addCalendarResource(
			userId, groupId, PortalUtil.getClassNameId(Group.class), groupId,
			null, null,
			LocalizationUtil.populateLocalizationMap(
				nameMap, LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
				groupId),
			descriptionMap, true, serviceContext);
	}

	public static CalendarResource getUserCalendarResource(
			long userId, ServiceContext serviceContext)
		throws PortalException {

		long classNameId = PortalUtil.getClassNameId(User.class);

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.fetchCalendarResource(
				classNameId, userId);

		if (calendarResource != null) {
			return calendarResource;
		}

		User user = UserLocalServiceUtil.getUser(userId);

		Group userGroup = null;

		String userName = user.getFullName();

		if (user.isDefaultUser()) {
			userGroup = GroupLocalServiceUtil.getGroup(
				serviceContext.getCompanyId(), GroupConstants.GUEST);

			userName = GroupConstants.GUEST;
		}
		else {
			userGroup = GroupLocalServiceUtil.getUserGroup(
				serviceContext.getCompanyId(), userId);
		}

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), userName);

		Map<Locale, String> descriptionMap = new HashMap<>();

		return CalendarResourceLocalServiceUtil.addCalendarResource(
			userId, userGroup.getGroupId(),
			PortalUtil.getClassNameId(User.class), userId, null, null,
			LocalizationUtil.populateLocalizationMap(
				nameMap, LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
				userGroup.getGroupId()),
			descriptionMap, true, serviceContext);
	}

}