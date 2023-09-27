/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		return addCalendarResource(
			group,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
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