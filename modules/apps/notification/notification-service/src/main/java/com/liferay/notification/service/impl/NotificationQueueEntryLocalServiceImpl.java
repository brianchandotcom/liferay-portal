/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.impl;

import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.exception.NotificationQueueEntryStatusException;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.service.NotificationQueueEntryAttachmentLocalService;
import com.liferay.notification.service.NotificationRecipientLocalService;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.base.NotificationQueueEntryLocalServiceBaseImpl;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Portal;

import java.util.Date;
import java.util.List;

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
			NotificationContext notificationContext)
		throws PortalException {

		NotificationQueueEntry notificationQueueEntry =
			notificationContext.getNotificationQueueEntry();

		NotificationType notificationType =
			_notificationTypeServiceTracker.getNotificationType(
				notificationQueueEntry.getType());

		notificationType.validateNotificationQueueEntry(notificationContext);

		notificationQueueEntry.setNotificationQueueEntryId(
			counterLocalService.increment());

		notificationQueueEntry = notificationQueueEntryPersistence.update(
			notificationQueueEntry);

		_resourceLocalService.addResources(
			notificationQueueEntry.getCompanyId(), 0,
			notificationQueueEntry.getUserId(),
			NotificationQueueEntry.class.getName(),
			notificationQueueEntry.getNotificationQueueEntryId(), false, true,
			true);

		for (long fileEntryId : notificationContext.getFileEntryIds()) {
			_notificationQueueEntryAttachmentLocalService.
				addNotificationQueueEntryAttachment(
					notificationQueueEntry.getCompanyId(), fileEntryId,
					notificationQueueEntry.getNotificationQueueEntryId());
		}

		NotificationRecipient notificationRecipient =
			notificationContext.getNotificationRecipient();

		notificationRecipient.setNotificationRecipientId(
			counterLocalService.increment());
		notificationRecipient.setClassNameId(
			_portal.getClassNameId(NotificationQueueEntry.class));
		notificationRecipient.setClassPK(
			notificationQueueEntry.getNotificationQueueEntryId());

		notificationRecipient =
			_notificationRecipientLocalService.updateNotificationRecipient(
				notificationRecipient);

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationContext.getNotificationRecipientSettings()) {

			notificationRecipientSetting.setNotificationRecipientSettingId(
				counterLocalService.increment());
			notificationRecipientSetting.setNotificationRecipientId(
				notificationRecipient.getNotificationRecipientId());

			_notificationRecipientSettingLocalService.
				updateNotificationRecipientSetting(
					notificationRecipientSetting);
		}

		return notificationQueueEntry;
	}

	@Override
	public void deleteNotificationQueueEntries(Date sentDate)
		throws PortalException {

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntryPersistence.findByLtSentDate(sentDate)) {

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
			NotificationQueueEntry notificationQueueEntry)
		throws PortalException {

		notificationQueueEntry = notificationQueueEntryPersistence.remove(
			notificationQueueEntry);

		_resourceLocalService.deleteResource(
			notificationQueueEntry, ResourceConstants.SCOPE_INDIVIDUAL);

		_notificationQueueEntryAttachmentLocalService.
			deleteNotificationQueueEntryAttachments(
				notificationQueueEntry.getNotificationQueueEntryId());

		NotificationRecipient notificationRecipient =
			notificationQueueEntry.getNotificationRecipient();

		_notificationRecipientLocalService.deleteNotificationRecipient(
			notificationRecipient);

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipient.getNotificationRecipientSettings()) {

			_notificationRecipientSettingLocalService.
				deleteNotificationRecipientSetting(
					notificationRecipientSetting);
		}

		return notificationQueueEntry;
	}

	@Override
	public List<NotificationQueueEntry> getNotificationEntries(
		String type, int status) {

		return notificationQueueEntryPersistence.findByT_S(type, status);
	}

	@Override
	public NotificationQueueEntry resendNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		NotificationQueueEntry notificationQueueEntry =
			getNotificationQueueEntry(notificationQueueEntryId);

		if (notificationQueueEntry.getStatus() ==
				NotificationQueueEntryConstants.STATUS_SENT) {

			throw new NotificationQueueEntryStatusException(
				"Notification queue entry " +
					notificationQueueEntry.getNotificationQueueEntryId() +
						" was already sent");
		}

		NotificationType notificationType =
			_notificationTypeServiceTracker.getNotificationType(
				notificationQueueEntry.getType());

		notificationType.resendNotification(notificationQueueEntry);

		return getNotificationQueueEntry(notificationQueueEntryId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationQueueEntry updateNotificationQueueEntry(
		NotificationQueueEntry notificationQueueEntry) {

		return notificationQueueEntryPersistence.update(notificationQueueEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationQueueEntry updateStatus(
			long notificationQueueEntryId, int status)
		throws PortalException {

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntryPersistence.findByPrimaryKey(
				notificationQueueEntryId);

		if (status == NotificationQueueEntryConstants.STATUS_SENT) {
			notificationQueueEntry.setSentDate(new Date());
		}
		else {
			notificationQueueEntry.setSentDate(null);
		}

		notificationQueueEntry.setStatus(status);

		return notificationQueueEntryPersistence.update(notificationQueueEntry);
	}

	@Reference
	private NotificationQueueEntryAttachmentLocalService
		_notificationQueueEntryAttachmentLocalService;

	@Reference
	private NotificationRecipientLocalService
		_notificationRecipientLocalService;

	@Reference
	private NotificationRecipientSettingLocalService
		_notificationRecipientSettingLocalService;

	@Reference
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}