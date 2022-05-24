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

package com.liferay.notification.service.impl;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.service.base.NotificationQueueEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 * @author Gustavo Lima
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationQueueEntry",
	service = AopService.class
)
public class NotificationQueueEntryLocalServiceImpl
	extends NotificationQueueEntryLocalServiceBaseImpl {

	@Override
	public NotificationQueueEntry addNotificationQueueEntry(
			long userId, long notificationTemplateId, String bcc, String body,
			String cc, String className, long classPK, String from,
			String fromName, double priority, String subject, String to,
			String toName)
		throws PortalException {

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntryPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		notificationQueueEntry.setCompanyId(user.getCompanyId());
		notificationQueueEntry.setUserId(user.getUserId());
		notificationQueueEntry.setUserName(user.getFullName());

		notificationQueueEntry.setNotificationTemplateId(
			notificationTemplateId);
		notificationQueueEntry.setBcc(bcc);
		notificationQueueEntry.setBody(body);
		notificationQueueEntry.setCc(cc);
		notificationQueueEntry.setClassName(className);
		notificationQueueEntry.setClassPK(classPK);
		notificationQueueEntry.setFrom(from);
		notificationQueueEntry.setFromName(fromName);
		notificationQueueEntry.setPriority(priority);
		notificationQueueEntry.setSubject(subject);
		notificationQueueEntry.setTo(to);
		notificationQueueEntry.setToName(toName);

		return notificationQueueEntryPersistence.update(notificationQueueEntry);
	}

	@Override
	public void deleteNotificationQueueEntries(Date sentDate)
		throws PortalException {

		notificationQueueEntryPersistence.removeByLtSentDate(sentDate);
	}

	@Override
	public NotificationQueueEntry deleteNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntryPersistence.findByPrimaryKey(
				notificationQueueEntryId);

		return notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntry);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public NotificationQueueEntry deleteNotificationQueueEntry(
		NotificationQueueEntry notificationQueueEntry) {

		notificationQueueEntryPersistence.remove(notificationQueueEntry);

		return notificationQueueEntry;
	}

	@Override
	public NotificationQueueEntry resendNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		return notificationQueueEntryLocalService.updateSent(
			notificationQueueEntryId, false);
	}

	@Override
	public void sendNotificationQueueEntries() throws Exception {
		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryPersistence.findBySent(false);

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntries) {

			InternetAddress from = new InternetAddress(
				notificationQueueEntry.getFrom(),
				notificationQueueEntry.getFromName());
			InternetAddress to = new InternetAddress(
				notificationQueueEntry.getTo(),
				notificationQueueEntry.getToName());

			MailMessage mailMessage = new MailMessage(
				from, to, notificationQueueEntry.getSubject(),
				notificationQueueEntry.getBody(), true);

			List<InternetAddress> bccInternetAddresses = new ArrayList<>();
			List<InternetAddress> ccInternetAddresses = new ArrayList<>();

			String[] bccAddresses = StringUtil.split(
				notificationQueueEntry.getBcc());
			String[] ccAddresses = StringUtil.split(
				notificationQueueEntry.getCc());

			for (String bccAddress : bccAddresses) {
				bccInternetAddresses.add(new InternetAddress(bccAddress));
			}

			for (String ccAddress : ccAddresses) {
				ccInternetAddresses.add(new InternetAddress(ccAddress));
			}

			mailMessage.setBCC(
				bccInternetAddresses.toArray(new InternetAddress[0]));
			mailMessage.setCC(
				ccInternetAddresses.toArray(new InternetAddress[0]));

			try {
				_mailService.sendEmail(mailMessage);

				notificationQueueEntryLocalService.updateSent(
					notificationQueueEntry.getNotificationQueueEntryId(), true);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationQueueEntry updateSent(
			long notificationQueueEntryId, boolean sent)
		throws PortalException {

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntryPersistence.findByPrimaryKey(
				notificationQueueEntryId);

		notificationQueueEntry.setSent(sent);

		if (sent) {
			notificationQueueEntry.setSentDate(new Date());
		}
		else {
			notificationQueueEntry.setSentDate(null);
		}

		return notificationQueueEntryPersistence.update(notificationQueueEntry);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationQueueEntryLocalServiceImpl.class);

	@Reference
	private MailService _mailService;

	@Reference
	private UserLocalService _userLocalService;

}