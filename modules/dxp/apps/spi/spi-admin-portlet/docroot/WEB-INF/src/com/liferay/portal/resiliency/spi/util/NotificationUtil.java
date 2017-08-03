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

package com.liferay.portal.resiliency.spi.util;

import com.liferay.petra.content.ContentUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 */
public class NotificationUtil {

	public static String getNotificationEmailBody(
		PortletPreferences portletPreferences) {

		String notificationEmailBody = portletPreferences.getValue(
			"notificationEmailBody", StringPool.BLANK);

		if (Validator.isNotNull(notificationEmailBody)) {
			return notificationEmailBody;
		}

		return ContentUtil.get(
			NotificationUtil.class.getClassLoader(),
			PortletPropsValues.SPI_NOTIFICATION_EMAIL_BODY);
	}

	public static String getNotificationEmailFromAddress(
		PortletPreferences portletPreferences) {

		String notificationEmailFromAddress =
			PortletPropsValues.SPI_NOTIFICATION_EMAIL_FROM_ADDRESS;

		return portletPreferences.getValue(
			"notificationEmailFromAddress", notificationEmailFromAddress);
	}

	public static String getNotificationEmailFromName(
		PortletPreferences portletPreferences) {

		String notificationEmailFromName =
			PortletPropsValues.SPI_NOTIFICATION_EMAIL_FROM_NAME;

		return portletPreferences.getValue(
			"notificationEmailFromName", notificationEmailFromName);
	}

	public static String getNotificationEmailSubject(
		PortletPreferences portletPreferences) {

		String notificationEmailSubject = portletPreferences.getValue(
			"notificationEmailSubject", StringPool.BLANK);

		if (Validator.isNotNull(notificationEmailSubject)) {
			return notificationEmailSubject;
		}

		return ContentUtil.get(
			NotificationUtil.class.getClassLoader(),
			PortletPropsValues.SPI_NOTIFICATION_EMAIL_SUBJECT);
	}

	public static String getNotificationRecipients(
		PortletPreferences portletPreferences) {

		String notificationRecipients = portletPreferences.getValue(
			"notificationRecipients", StringPool.BLANK);

		return portletPreferences.getValue(
			"notificationRecipients", notificationRecipients);
	}

}