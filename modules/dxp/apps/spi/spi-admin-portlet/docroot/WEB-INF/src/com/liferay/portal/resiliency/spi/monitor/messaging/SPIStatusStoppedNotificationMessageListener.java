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

package com.liferay.portal.resiliency.spi.monitor.messaging;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.util.NotificationUtil;
import com.liferay.portal.resiliency.spi.util.SPIAdminConstants;

import java.util.HashSet;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 */
public class SPIStatusStoppedNotificationMessageListener
	extends BaseSPIStatusMessageListener {

	public SPIStatusStoppedNotificationMessageListener() {
		setInterestedStatus(SPIAdminConstants.STATUS_STOPPED);
	}

	@Override
	protected void processSPIStatus(
			PortletPreferences portletPreferences, SPIDefinition spiDefinition,
			int status)
		throws Exception {

		if ((spiDefinition.getStatus() == SPIAdminConstants.STATUS_STOPPED) &&
			(status == SPIAdminConstants.STATUS_STOPPED)) {

			return;
		}

		if ((spiDefinition.getStatus() == SPIAdminConstants.STATUS_STARTING) ||
			(spiDefinition.getStatus() == SPIAdminConstants.STATUS_STOPPING)) {

			return;
		}

		String notificationRecipients =
			spiDefinition.getNotificationRecipients();

		if (Validator.isNull(notificationRecipients)) {
			notificationRecipients = portletPreferences.getValue(
				"notificationRecipients", null);

			if (Validator.isNull(notificationRecipients)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"No notification recipients configured for " +
							spiDefinition.getSpiDefinitionId());
				}

				return;
			}
		}

		String fromName = NotificationUtil.getNotificationEmailFromName(
			portletPreferences);
		String fromAddress = NotificationUtil.getNotificationEmailFromAddress(
			portletPreferences);

		InternetAddress fromInternetAddress = new InternetAddress(fromAddress);

		String subject = NotificationUtil.getNotificationEmailSubject(
			portletPreferences);

		subject = StringUtil.replace(
			subject,
			new String[] {"[$SANDBOX_NAME$]", "[$SANDBOX_RESTART_ATTEMPTS$]"},
			new String[] {
				spiDefinition.getName(),
				String.valueOf(spiDefinition.getRestartAttempts())
			});

		String body = NotificationUtil.getNotificationEmailBody(
			portletPreferences);

		StringBundler sb = new StringBundler(13);

		sb.append("<br /><table><tr><td>ID</td><td>");
		sb.append(spiDefinition.getSpiDefinitionId());
		sb.append("</td></tr><tr><td>Name</td><td>");
		sb.append(spiDefinition.getName());
		sb.append("</td></tr><tr><td>Description</td><td>");
		sb.append(spiDefinition.getDescription());
		sb.append("</td></tr><tr><td>Port</td><td>");
		sb.append(spiDefinition.getConnectorPort());
		sb.append("</td></tr><tr><td>Status</td><td>");
		sb.append(spiDefinition.getStatusLabel());
		sb.append("</td></tr><tr><td>Message</td><td>");
		sb.append(spiDefinition.getStatusMessage());
		sb.append("</td></tr></table>");

		body = StringUtil.replace(
			body,
			new String[] {
				"[$FROM_ADDRESS$]", "[$FROM_NAME$]", "[$SANDBOX_DETAILS$]"
			},
			new String[] {fromName, fromAddress, sb.toString()});

		MailMessage mailMessage = new MailMessage(
			fromInternetAddress, subject, body, true);

		String[] notificationRecipientsArray = StringUtil.split(
			notificationRecipients);

		Set<InternetAddress> recipientInternetAddresses = new HashSet<>(
			notificationRecipientsArray.length);

		for (String notificationRecipient : notificationRecipientsArray) {
			InternetAddress recipientInternetAddress = new InternetAddress(
				notificationRecipient);

			recipientInternetAddresses.add(recipientInternetAddress);
		}

		mailMessage.setTo(
			recipientInternetAddresses.toArray(
				new InternetAddress[recipientInternetAddresses.size()]));

		MailServiceUtil.sendEmail(mailMessage);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SPIStatusStoppedNotificationMessageListener.class);

}