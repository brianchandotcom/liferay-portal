/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.notifications.internal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.sharing.constants.SharingPortletKeys;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.notifications.internal.helper.SharingNotificationHelper;
import com.liferay.sharing.notifications.internal.util.SharingNotificationSubcriptionSender;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalServiceWrapper;

import java.util.Collection;
import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ServiceWrapper.class)
public class NotificationsSharingEntryLocalServiceWrapper
	extends SharingEntryLocalServiceWrapper {

	@Override
	public SharingEntry addSharingEntry(
			long fromUserId, long toUserId, long classNameId, long classPK,
			long groupId, boolean shareable,
			Collection<SharingEntryAction> sharingEntryActions,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		SharingEntry sharingEntry = super.addSharingEntry(
			fromUserId, toUserId, classNameId, classPK, groupId, shareable,
			sharingEntryActions, expirationDate, serviceContext);

		_sendNotificationEvent(
			sharingEntry,
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			serviceContext);

		return sharingEntry;
	}

	@Override
	public SharingEntry updateSharingEntry(
			long sharingEntryId,
			Collection<SharingEntryAction> sharingEntryActions,
			boolean shareable, Date expirationDate,
			ServiceContext serviceContext)
		throws PortalException {

		SharingEntry sharingEntry = super.updateSharingEntry(
			sharingEntryId, sharingEntryActions, shareable, expirationDate,
			serviceContext);

		_sendNotificationEvent(
			sharingEntry,
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			serviceContext);

		return sharingEntry;
	}

	@Override
	public SharingEntry updateSharingEntry(
			long userId, long sharingEntryId,
			Collection<SharingEntryAction> sharingEntryActions,
			boolean shareable, Date expirationDate,
			ServiceContext serviceContext)
		throws PortalException {

		SharingEntry sharingEntry = super.updateSharingEntry(
			userId, sharingEntryId, sharingEntryActions, shareable,
			expirationDate, serviceContext);

		_sendNotificationEvent(
			sharingEntry,
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			serviceContext);

		return sharingEntry;
	}

	private void _sendNotificationEvent(
		SharingEntry sharingEntry, int notificationType,
		ServiceContext serviceContext) {

		try {
			User user = _userLocalService.getUser(sharingEntry.getToUserId());

			SharingNotificationSubcriptionSender
				sharingNotificationSubcriptionSender =
					new SharingNotificationSubcriptionSender();

			sharingNotificationSubcriptionSender.setSubject(
				_sharingNotificationHelper.getNotificationMessage(
					sharingEntry, user.getLocale()));

			String entryURL = _sharingNotificationHelper.getNotificationURL(
				sharingEntry, serviceContext.getLiferayPortletRequest());

			sharingNotificationSubcriptionSender.setBody(
				_sharingNotificationHelper.getNotificationEmailBody(
					sharingEntry, serviceContext.getLiferayPortletRequest()));

			sharingNotificationSubcriptionSender.setClassName(
				sharingEntry.getModelClassName());
			sharingNotificationSubcriptionSender.setClassPK(
				sharingEntry.getSharingEntryId());
			sharingNotificationSubcriptionSender.setCompanyId(
				user.getCompanyId());
			sharingNotificationSubcriptionSender.setCurrentUserId(
				serviceContext.getUserId());
			sharingNotificationSubcriptionSender.setEntryURL(entryURL);

			String fromName = PrefsPropsUtil.getString(
				user.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
			String fromAddress = PrefsPropsUtil.getString(
				user.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

			sharingNotificationSubcriptionSender.setFrom(fromAddress, fromName);

			sharingNotificationSubcriptionSender.setHtmlFormat(true);
			sharingNotificationSubcriptionSender.setMailId(
				"sharing_entry", sharingEntry.getSharingEntryId());
			sharingNotificationSubcriptionSender.setNotificationType(
				notificationType);
			sharingNotificationSubcriptionSender.setPortletId(
				SharingPortletKeys.SHARING);
			sharingNotificationSubcriptionSender.setScopeGroupId(
				sharingEntry.getGroupId());
			sharingNotificationSubcriptionSender.setServiceContext(
				serviceContext);

			sharingNotificationSubcriptionSender.addRuntimeSubscribers(
				user.getEmailAddress(), user.getFullName());

			sharingNotificationSubcriptionSender.flushNotificationsAsync();
		}
		catch (Exception exception) {
			_log.error(
				"Unable to send notification for sharing entry: " +
					sharingEntry.getSharingEntryId(),
				exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationsSharingEntryLocalServiceWrapper.class);

	@Reference
	private SharingNotificationHelper _sharingNotificationHelper;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}