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

package com.liferay.notification.admin.service.impl;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.notification.admin.model.NotificationQueueEntry;
import com.liferay.notification.admin.service.base.NotificationQueueEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
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
	property = "model.class.name=com.liferay.notification.admin.model.NotificationQueueEntry",
	service = AopService.class
)
public class NotificationQueueEntryLocalServiceImpl
	extends NotificationQueueEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationQueueEntry addNotificationQueueEntry(
			long userId, long groupId, String className, long classPK,
			long notificationTemplateId, String from, String fromName,
			String to, String toName, String cc, String bcc, String subject,
			String body, double priority)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long notificationQueueEntryId = counterLocalService.increment();

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntryPersistence.create(notificationQueueEntryId);

		notificationQueueEntry.setGroupId(groupId);
		notificationQueueEntry.setCompanyId(user.getCompanyId());
		notificationQueueEntry.setUserId(user.getUserId());
		notificationQueueEntry.setUserName(user.getFullName());
		notificationQueueEntry.setClassName(className);
		notificationQueueEntry.setNotificationQueueEntryId(
			notificationTemplateId);
		notificationQueueEntry.setClassPK(classPK);
		notificationQueueEntry.setFrom(from);
		notificationQueueEntry.setFromName(fromName);
		notificationQueueEntry.setTo(to);
		notificationQueueEntry.setToName(toName);
		notificationQueueEntry.setCc(cc);
		notificationQueueEntry.setBcc(bcc);
		notificationQueueEntry.setSubject(subject);
		notificationQueueEntry.setBody(body);
		notificationQueueEntry.setPriority(priority);

		return notificationQueueEntryPersistence.update(notificationQueueEntry);
	}

	@Override
	public void deleteNotificationQueueEntries(Date sentDate) {
		notificationQueueEntryPersistence.removeByLtSentDate(sentDate);
	}

	@Override
	public void deleteNotificationQueueEntries(long groupId)
		throws PortalException {

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryPersistence.findByGroupId(groupId);

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntries) {

			notificationQueueEntryLocalService.deleteNotificationQueueEntry(
				notificationQueueEntry);
		}
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

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public NotificationQueueEntry deleteNotificationQueueEntry(
		NotificationQueueEntry notificationQueueEntry) {

		notificationQueueEntryPersistence.remove(notificationQueueEntry);

		return notificationQueueEntry;
	}

	@Override
	public List<NotificationQueueEntry> getNotificationQueueEntries(
		boolean sent) {

		return notificationQueueEntryPersistence.findBySent(sent);
	}

	@Override
	public List<NotificationQueueEntry> getNotificationQueueEntries(
		long groupId, String className, long classPK, boolean sent, int start,
		int end, OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return notificationQueueEntryPersistence.findByG_C_C_S(
			groupId, classNameLocalService.getClassNameId(className), classPK,
			sent, start, end, orderByComparator);
	}

	@Override
	public int getNotificationQueueEntriesCount(long groupId) {
		return notificationQueueEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getNotificationQueueEntriesCount(
		long groupId, String className, long classPK, boolean sent) {

		return notificationQueueEntryPersistence.countByG_C_C_S(
			groupId, classNameLocalService.getClassNameId(className), classPK,
			sent);
	}

	@Override
	public NotificationQueueEntry resendNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		return notificationQueueEntryLocalService.updateSent(
			notificationQueueEntryId, false);
	}

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

	@Override
	public void updateNotificationQueueEntriesTemplateIds(
		long notificationTemplateId) {

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryPersistence.findByNotificationTemplateId(
				notificationTemplateId);

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntries) {

			updateNotificationQueueEntry(notificationQueueEntry, 0);
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

	@Indexable(type = IndexableType.REINDEX)
	protected NotificationQueueEntry updateNotificationQueueEntry(
		NotificationQueueEntry notificationQueueEntry,
		long notificationTemplateId) {

		notificationQueueEntry.setNotificationTemplateId(
			notificationTemplateId);

		return notificationQueueEntryPersistence.update(notificationQueueEntry);
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationQueueEntryLocalServiceImpl.class);

	@Reference
	private MailService _mailService;

}