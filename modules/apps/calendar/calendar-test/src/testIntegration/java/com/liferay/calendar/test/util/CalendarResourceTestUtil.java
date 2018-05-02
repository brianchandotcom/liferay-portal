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

package com.liferay.calendar.test.util;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Inácio Nery
 */
public class CalendarResourceTestUtil {

	public static CalendarResource addCalendarResource(Group group)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		return addCalendarResource(group, serviceContext);
	}

	public static CalendarResource addCalendarResource(
			Group group, ServiceContext serviceContext)
		throws PortalException {

		return CalendarResourceLocalServiceUtil.addCalendarResource(
			group.getCreatorUserId(), group.getGroupId(),
			ClassNameLocalServiceUtil.getClassNameId(CalendarResource.class), 0,
			null, null, RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), true, serviceContext);
	}

	public static CalendarResource getCalendarResource(Group group)
		throws PortalException {

		return CalendarResourceUtil.getGroupCalendarResource(
			group.getGroupId(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

}