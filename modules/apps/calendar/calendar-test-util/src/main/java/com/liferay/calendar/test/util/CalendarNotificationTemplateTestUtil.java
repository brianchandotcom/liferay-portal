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

import com.liferay.calendar.constants.CalendarNotificationTemplateConstants;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.notification.NotificationTemplateType;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.service.CalendarNotificationTemplateLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

/**
 * @author Adam Brandizzi
 */
public class CalendarNotificationTemplateTestUtil {

	public static CalendarNotificationTemplate addCalendarNotificationTemplate(
			Calendar calendar,
			NotificationTemplateType notificationTemplateType,
			String fromAddress, String fromName, String subject, String body)
		throws PortalException {

		return CalendarNotificationTemplateLocalServiceUtil.
			addCalendarNotificationTemplate(
				calendar.getUserId(), calendar.getCalendarId(),
				NotificationType.EMAIL,
				UnicodePropertiesBuilder.create(
					true
				).put(
					CalendarNotificationTemplateConstants.PROPERTY_FROM_ADDRESS,
					fromAddress
				).put(
					CalendarNotificationTemplateConstants.PROPERTY_FROM_NAME,
					fromName
				).buildString(),
				notificationTemplateType, subject, body,
				createServiceContext(
					UserLocalServiceUtil.getUser(calendar.getUserId())));
	}

	protected static ServiceContext createServiceContext(User user) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());

		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

}