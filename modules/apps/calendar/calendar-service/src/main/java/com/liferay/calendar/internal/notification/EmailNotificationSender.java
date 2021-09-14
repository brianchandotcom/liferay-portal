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

import com.liferay.calendar.constants.CalendarNotificationTemplateConstants;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.notification.NotificationField;
import com.liferay.calendar.notification.NotificationRecipient;
import com.liferay.calendar.notification.NotificationSender;
import com.liferay.calendar.notification.NotificationSenderException;
import com.liferay.calendar.notification.NotificationTemplateContext;
import com.liferay.calendar.notification.NotificationUtil;
import com.liferay.calendar.service.impl.CalendarBookingLocalServiceImpl;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;

import java.io.File;
import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Lundgren
 */
@Component(
	immediate = true, property = "notification.type=email",
	service = NotificationSender.class
)
public class EmailNotificationSender implements NotificationSender {

	@Override
	public void sendNotification(
			String fromAddress, String fromName,
			NotificationRecipient notificationRecipient,
			NotificationTemplateContext notificationTemplateContext)
		throws NotificationSenderException {

		try {
			CalendarNotificationTemplate calendarNotificationTemplate =
				notificationTemplateContext.getCalendarNotificationTemplate();

			String fromAddressValue = NotificationUtil.getTemplatePropertyValue(
				calendarNotificationTemplate,
				CalendarNotificationTemplateConstants.PROPERTY_FROM_ADDRESS,
				fromAddress);
			String fromNameValue = NotificationUtil.getTemplatePropertyValue(
				calendarNotificationTemplate,
				CalendarNotificationTemplateConstants.PROPERTY_FROM_NAME,
				fromName);

			notificationTemplateContext.setFromAddress(fromAddressValue);
			notificationTemplateContext.setFromName(fromNameValue);
			notificationTemplateContext.setToAddress(
				notificationRecipient.getEmailAddress());
			notificationTemplateContext.setToName(
				notificationRecipient.getName());

			_sendNotification(
				(File)notificationTemplateContext.getAttribute("icsFile"),
				notificationRecipient, notificationTemplateContext,
				NotificationTemplateRenderer.render(
					notificationTemplateContext, NotificationField.BODY,
					NotificationTemplateRenderer.MODE_HTML),
				fromAddressValue, fromNameValue,
				NotificationTemplateRenderer.render(
					notificationTemplateContext, NotificationField.SUBJECT,
					NotificationTemplateRenderer.MODE_PLAIN));
		}
		catch (Exception exception) {
			throw new NotificationSenderException(exception);
		}
	}

	private void _sendNotification(
			File icsFile, NotificationRecipient notificationRecipient,
			NotificationTemplateContext notificationTemplateContext,
			String body, String fromEmail, String fromName, String subject)
		throws NotificationSenderException {

		try {
			SubscriptionSender subscriptionSender = new SubscriptionSender();

			subscriptionSender.setClassName(
				CalendarBookingLocalServiceImpl.class.getName());
			subscriptionSender.setClassPK(
				notificationTemplateContext.getCalendarId());
			subscriptionSender.setCompanyId(
				notificationTemplateContext.getCompanyId());

			Map<String, Serializable> attributes =
				notificationTemplateContext.getAttributes();

			subscriptionSender.setContextAttributes(
				"[$COMPANY_ID$]",
				GetterUtil.getString(
					notificationTemplateContext.getCompanyId()),
				"[$CALENDAR_NAME$]",
				GetterUtil.getString(attributes.get("calendarName")),
				"[$EVENT_END_DATE$]",
				GetterUtil.getString(attributes.get("endTime")),
				"[$EVENT_LOCATION$]",
				GetterUtil.getString(attributes.get("location")),
				"[$EVENT_START_DATE$]",
				GetterUtil.getString(attributes.get("startTime")),
				"[$EVENT_TITLE$]",
				GetterUtil.getString(attributes.get("title")), "[$EVENT_URL$]",
				GetterUtil.getString(attributes.get("url")),
				"[$INSTANCE_START_TIME$]",
				GetterUtil.getString(attributes.get("instanceStartTime")),
				"[$PORTAL_URL$]",
				GetterUtil.getString(attributes.get("portalURL")),
				"[$PORTLET_NAME$]",
				GetterUtil.getString(attributes.get("portletName")),
				"[$SITE_NAME$]",
				GetterUtil.getString(attributes.get("siteName")));

			CalendarNotificationTemplate calendarNotificationTemplate =
				notificationTemplateContext.getCalendarNotificationTemplate();

			subscriptionSender.setContextCreatorUserPrefix("EVENT");
			subscriptionSender.setFrom(fromEmail, fromName);
			subscriptionSender.setHtmlFormat(
				notificationRecipient.isHTMLFormat());

			if (calendarNotificationTemplate != null) {
				subscriptionSender.setCreatorUserId(
					calendarNotificationTemplate.getUserId());

				Map<Locale, String> localizedSubjectMap =
					LocalizationUtil.getLocalizationMap(
						calendarNotificationTemplate.getSubject());

				Map<Locale, String> localizedBodyMap =
					LocalizationUtil.getLocalizationMap(
						calendarNotificationTemplate.getBody());

				subscriptionSender.setLocalizedBodyMap(localizedBodyMap);

				subscriptionSender.setLocalizedSubjectMap(localizedSubjectMap);
			}
			else {
				subscriptionSender.setBody(body);
				subscriptionSender.setSubject(subject);
			}

			if (icsFile != null) {
				subscriptionSender.addFileAttachment(icsFile);
			}

			subscriptionSender.setMailId(
				"event", notificationTemplateContext.getCalendarId());

			String portletId = PortletProviderUtil.getPortletId(
				CalendarBooking.class.getName(), PortletProvider.Action.EDIT);

			subscriptionSender.setPortletId(portletId);

			subscriptionSender.setScopeGroupId(
				notificationTemplateContext.getGroupId());

			subscriptionSender.addRuntimeSubscribers(
				notificationRecipient.getEmailAddress(),
				notificationRecipient.getName());

			subscriptionSender.flushNotificationsAsync();
		}
		catch (Exception exception) {
			throw new NotificationSenderException(
				"Unable to send mail message", exception);
		}
	}

}