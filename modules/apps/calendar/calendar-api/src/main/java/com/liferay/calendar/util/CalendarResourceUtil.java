/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.HashMapBuilder;
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
			UserLocalServiceUtil.getGuestUserId(companyId));
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
				 group.isStagedPortlet(CalendarPortletKeys.CALENDAR_ADMIN)) {

			return null;
		}

		long userId = group.getCreatorUserId();

		User user = UserLocalServiceUtil.fetchUserById(userId);

		if ((user == null) || user.isGuestUser()) {
			Role role = RoleLocalServiceUtil.getRole(
				group.getCompanyId(), RoleConstants.ADMINISTRATOR);

			long[] userIds = UserLocalServiceUtil.getRoleUserIds(
				role.getRoleId());

			userId = userIds[0];
		}

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), group.getDescriptiveName()
		).build();

		Map<Locale, String> descriptionMap = new HashMap<>();

		if (serviceContext != null) {
			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setModelPermissions(null);
		}

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

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.fetchCalendarResource(
				PortalUtil.getClassNameId(User.class), userId);

		if (calendarResource != null) {
			return calendarResource;
		}

		User user = UserLocalServiceUtil.getUser(userId);

		Group userGroup = null;

		String userName = user.getFullName();

		if (user.isGuestUser()) {
			userGroup = GroupLocalServiceUtil.getGroup(
				serviceContext.getCompanyId(), GroupConstants.GUEST);

			userName = GroupConstants.GUEST;
		}
		else {
			userGroup = GroupLocalServiceUtil.getUserGroup(
				serviceContext.getCompanyId(), userId);
		}

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), userName
		).build();

		Map<Locale, String> descriptionMap = new HashMap<>();

		if (serviceContext != null) {
			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setModelPermissions(null);
		}

		return CalendarResourceLocalServiceUtil.addCalendarResource(
			userId, userGroup.getGroupId(),
			PortalUtil.getClassNameId(User.class), userId, null, null,
			LocalizationUtil.populateLocalizationMap(
				nameMap, LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
				userGroup.getGroupId()),
			descriptionMap, true, serviceContext);
	}

}