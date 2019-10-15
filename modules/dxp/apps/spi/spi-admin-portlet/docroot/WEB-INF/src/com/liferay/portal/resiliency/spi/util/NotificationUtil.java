/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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