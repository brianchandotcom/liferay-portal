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

package com.liferay.notifications.admin.service.impl;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.notifications.admin.model.NotificationsQueueEntry;
import com.liferay.notifications.admin.service.base.NotificationsQueueEntryLocalServiceBaseImpl;
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
	property = "model.class.name=com.liferay.notifications.admin.model.NotificationsQueueEntry",
	service = AopService.class
)
public class NotificationsQueueEntryLocalServiceImpl
	extends NotificationsQueueEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationsQueueEntry addNotificationsQueueEntry(
			long userId, long groupId, String className, long classPK,
			long notificationsTemplateId, String from, String fromName,
			String to, String toName, String cc, String bcc, String subject,
			String body, double priority)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long notificationsQueueEntryId = counterLocalService.increment();

		NotificationsQueueEntry notificationsQueueEntry =
			notificationsQueueEntryPersistence.create(
				notificationsQueueEntryId);

		notificationsQueueEntry.setGroupId(groupId);
		notificationsQueueEntry.setCompanyId(user.getCompanyId());
		notificationsQueueEntry.setUserId(user.getUserId());
		notificationsQueueEntry.setUserName(user.getFullName());
		notificationsQueueEntry.setClassName(className);
		notificationsQueueEntry.setNotificationsQueueEntryId(
			notificationsTemplateId);
		notificationsQueueEntry.setClassPK(classPK);
		notificationsQueueEntry.setFrom(from);
		notificationsQueueEntry.setFromName(fromName);
		notificationsQueueEntry.setTo(to);
		notificationsQueueEntry.setToName(toName);
		notificationsQueueEntry.setCc(cc);
		notificationsQueueEntry.setBcc(bcc);
		notificationsQueueEntry.setSubject(subject);
		notificationsQueueEntry.setBody(body);
		notificationsQueueEntry.setPriority(priority);

		return notificationsQueueEntryPersistence.update(
			notificationsQueueEntry);
	}

	@Override
	public void deleteNotificationsQueueEntries(Date sentDate) {
		notificationsQueueEntryPersistence.removeByLtSentDate(sentDate);
	}

	@Override
	public void deleteNotificationsQueueEntries(long groupId)
		throws PortalException {

		List<NotificationsQueueEntry> notificationsQueueEntries =
			notificationsQueueEntryPersistence.findByGroupId(groupId);

		for (NotificationsQueueEntry notificationsQueueEntry :
				notificationsQueueEntries) {

			notificationsQueueEntryLocalService.deleteNotificationsQueueEntry(
				notificationsQueueEntry);
		}
	}

	@Override
	public NotificationsQueueEntry deleteNotificationsQueueEntry(
			long notificationsQueueEntryId)
		throws PortalException {

		NotificationsQueueEntry notificationsQueueEntry =
			notificationsQueueEntryPersistence.findByPrimaryKey(
				notificationsQueueEntryId);

		return notificationsQueueEntryLocalService.
			deleteNotificationsQueueEntry(notificationsQueueEntry);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public NotificationsQueueEntry deleteNotificationsQueueEntry(
		NotificationsQueueEntry notificationsQueueEntry) {

		notificationsQueueEntryPersistence.remove(notificationsQueueEntry);

		return notificationsQueueEntry;
	}

	@Override
	public List<NotificationsQueueEntry> getNotificationsQueueEntries(
		boolean sent) {

		return notificationsQueueEntryPersistence.findBySent(sent);
	}

	@Override
	public List<NotificationsQueueEntry> getNotificationsQueueEntries(
		long groupId, String className, long classPK, boolean sent, int start,
		int end, OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return notificationsQueueEntryPersistence.findByG_C_C_S(
			groupId, classNameLocalService.getClassNameId(className), classPK,
			sent, start, end, orderByComparator);
	}

	@Override
	public int getNotificationsQueueEntriesCount(long groupId) {
		return notificationsQueueEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getNotificationsQueueEntriesCount(
		long groupId, String className, long classPK, boolean sent) {

		return notificationsQueueEntryPersistence.countByG_C_C_S(
			groupId, classNameLocalService.getClassNameId(className), classPK,
			sent);
	}

	@Override
	public NotificationsQueueEntry resendNotificationsQueueEntry(
			long notificationsQueueEntryId)
		throws PortalException {

		return notificationsQueueEntryLocalService.updateSent(
			notificationsQueueEntryId, false);
	}

	public void sendNotificationsQueueEntries() throws Exception {
		List<NotificationsQueueEntry> notificationsQueueEntries =
			notificationsQueueEntryPersistence.findBySent(false);

		for (NotificationsQueueEntry notificationsQueueEntry :
				notificationsQueueEntries) {

			InternetAddress from = new InternetAddress(
				notificationsQueueEntry.getFrom(),
				notificationsQueueEntry.getFromName());
			InternetAddress to = new InternetAddress(
				notificationsQueueEntry.getTo(),
				notificationsQueueEntry.getToName());

			MailMessage mailMessage = new MailMessage(
				from, to, notificationsQueueEntry.getSubject(),
				notificationsQueueEntry.getBody(), true);

			List<InternetAddress> bccInternetAddresses = new ArrayList<>();
			List<InternetAddress> ccInternetAddresses = new ArrayList<>();

			String[] bccAddresses = StringUtil.split(
				notificationsQueueEntry.getBcc());
			String[] ccAddresses = StringUtil.split(
				notificationsQueueEntry.getCc());

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

				notificationsQueueEntryLocalService.updateSent(
					notificationsQueueEntry.getNotificationsQueueEntryId(),
					true);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}
	}

	@Override
	public void updateNotificationsQueueEntriesTemplateIds(
		long notificationsTemplateId) {

		List<NotificationsQueueEntry> notificationsQueueEntries =
			notificationsQueueEntryPersistence.findByNotificationsTemplateId(
				notificationsTemplateId);

		for (NotificationsQueueEntry notificationsQueueEntry :
				notificationsQueueEntries) {

			updateNotificationsQueueEntry(notificationsQueueEntry, 0);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationsQueueEntry updateSent(
			long notificationsQueueEntryId, boolean sent)
		throws PortalException {

		NotificationsQueueEntry notificationsQueueEntry =
			notificationsQueueEntryPersistence.findByPrimaryKey(
				notificationsQueueEntryId);

		notificationsQueueEntry.setSent(sent);

		if (sent) {
			notificationsQueueEntry.setSentDate(new Date());
		}
		else {
			notificationsQueueEntry.setSentDate(null);
		}

		return notificationsQueueEntryPersistence.update(
			notificationsQueueEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	protected NotificationsQueueEntry updateNotificationsQueueEntry(
		NotificationsQueueEntry notificationsQueueEntry,
		long notificationsTemplateId) {

		notificationsQueueEntry.setNotificationsTemplateId(
			notificationsTemplateId);

		return notificationsQueueEntryPersistence.update(
			notificationsQueueEntry);
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationsQueueEntryLocalServiceImpl.class);

	@Reference
	private MailService _mailService;

}