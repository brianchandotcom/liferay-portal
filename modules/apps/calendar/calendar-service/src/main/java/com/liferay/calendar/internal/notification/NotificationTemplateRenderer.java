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

package com.liferay.calendar.internal.notification;

import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.notification.NotificationField;
import com.liferay.calendar.notification.NotificationTemplateContext;
import com.liferay.calendar.notification.NotificationUtil;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Eduardo Lundgren
 */
public class NotificationTemplateRenderer {

	public static final int MODE_HTML = 1;

	public static final int MODE_PLAIN = 2;

	public static String render(
			NotificationTemplateContext notificationTemplateContext,
			NotificationField notificationField, int mode)
		throws Exception {

		CalendarNotificationTemplate calendarNotificationTemplate =
			notificationTemplateContext.getCalendarNotificationTemplate();

		String defaultTemplate = NotificationUtil.getDefaultTemplate(
			notificationTemplateContext.getNotificationType(),
			notificationTemplateContext.getNotificationTemplateType(),
			notificationField);

		String notificationTemplate = BeanPropertiesUtil.getString(
			calendarNotificationTemplate, notificationField.toString(),
			defaultTemplate);

		return replaceTokens(
			notificationTemplate, notificationTemplateContext, mode);
	}

	protected static String replaceTokens(
			String notificationTemplate,
			NotificationTemplateContext notificationTemplateContext, int mode)
		throws Exception {

		Map<String, Serializable> attributes =
			notificationTemplateContext.getAttributes();

		String location = GetterUtil.getString(attributes.get("location"));
		String title = GetterUtil.getString(attributes.get("title"));

		if (mode == MODE_HTML) {
			location = HtmlUtil.escapeAttribute(location);
			title = HtmlUtil.escapeAttribute(title);
		}

		return StringUtil.replace(
			notificationTemplate,
			new String[] {
				"[$EVENT_END_DATE$]", "[$EVENT_LOCATION$]",
				"[$EVENT_START_DATE$]", "[$EVENT_TITLE$]", "[$EVENT_URL$]",
				"[$INSTANCE_START_TIME$]", "[$FROM_ADDRESS$]", "[$FROM_NAME$]",
				"[$PORTAL_URL$]", "[$PORTLET_NAME$]", "[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				GetterUtil.getString(attributes.get("endTime")), location,
				GetterUtil.getString(attributes.get("startTime")), title,
				GetterUtil.getString(attributes.get("url")),
				GetterUtil.getString(attributes.get("instanceStartTime")),
				GetterUtil.getString(
					notificationTemplateContext.getFromAddress()),
				GetterUtil.getString(notificationTemplateContext.getFromName()),
				GetterUtil.getString(attributes.get("portalURL")),
				GetterUtil.getString(attributes.get("portletName")),
				GetterUtil.getString(
					notificationTemplateContext.getToAddress()),
				GetterUtil.getString(notificationTemplateContext.getToName())
			});
	}

}