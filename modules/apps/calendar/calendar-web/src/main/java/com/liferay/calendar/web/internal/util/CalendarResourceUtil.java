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

import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletRequest;

/**
 * @author Preston Crary
 */
public class CalendarResourceUtil
	extends com.liferay.calendar.util.CalendarResourceUtil {

	public static CalendarResource getCalendarResource(
			PortletRequest portletRequest, long classNameId, long classPK)
		throws PortalException {

		long groupClassNameId = PortalUtil.getClassNameId(Group.class);

		if (classNameId == groupClassNameId) {
			return getGroupCalendarResource(portletRequest, classPK);
		}

		long userClassNameId = PortalUtil.getClassNameId(User.class);

		if (classNameId == userClassNameId) {
			return getUserCalendarResource(portletRequest, classPK);
		}

		return CalendarResourceServiceUtil.fetchCalendarResource(
			classNameId, classPK);
	}

	public static CalendarResource getGroupCalendarResource(
			PortletRequest portletRequest, long groupId)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			portletRequest);

		return getGroupCalendarResource(groupId, serviceContext);
	}

	public static CalendarResource getScopeGroupCalendarResource(
			long groupId, ServiceContext serviceContext)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isUser()) {
			return getUserCalendarResource(group.getClassPK(), serviceContext);
		}

		return getGroupCalendarResource(groupId, serviceContext);
	}

	public static CalendarResource getScopeGroupCalendarResource(
			PortletRequest portletRequest, long groupId)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			portletRequest);

		return getScopeGroupCalendarResource(groupId, serviceContext);
	}

	public static CalendarResource getUserCalendarResource(
			PortletRequest portletRequest, long userId)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			portletRequest);

		serviceContext.setUserId(userId);

		return getUserCalendarResource(userId, serviceContext);
	}

}