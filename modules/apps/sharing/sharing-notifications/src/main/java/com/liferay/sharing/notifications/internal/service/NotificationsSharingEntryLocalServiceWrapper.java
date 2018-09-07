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

package com.liferay.sharing.notifications.internal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.constants.SharingPortletKeys;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.notifications.internal.constants.SharingNotificationsConstants;
import com.liferay.sharing.notifications.internal.notifications.SharingNotificationMessageProvider;
import com.liferay.sharing.notifications.internal.util.NotificationsSharingEntryUtil;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.service.SharingEntryLocalServiceWrapper;

import java.util.Collection;
import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class NotificationsSharingEntryLocalServiceWrapper
	extends SharingEntryLocalServiceWrapper {

	public NotificationsSharingEntryLocalServiceWrapper() {
		super(null);
	}

	public NotificationsSharingEntryLocalServiceWrapper(
		SharingEntryLocalService sharingEntryLocalService) {

		super(sharingEntryLocalService);
	}

	@Override
	public SharingEntry addSharingEntry(
			long fromUserId, long toUserId, long classNameId, long classPK,
			long groupId, boolean shareable,
			Collection<SharingEntryActionKey> sharingEntryActionKeys,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		SharingEntry sharingEntry = super.addSharingEntry(
			fromUserId, toUserId, classNameId, classPK, groupId, shareable,
			sharingEntryActionKeys, expirationDate, serviceContext);

		_sendNotificationEvent(sharingEntry, serviceContext);

		return sharingEntry;
	}

	@Override
	public SharingEntry updateSharingEntry(
			long sharingEntryId,
			Collection<SharingEntryActionKey> sharingEntryActionKeys,
			boolean shareable, Date expirationDate)
		throws PortalException {

		SharingEntry sharingEntry = super.updateSharingEntry(
			sharingEntryId, sharingEntryActionKeys, shareable, expirationDate);

		_sendNotificationEvent(sharingEntry, null);

		return sharingEntry;
	}

	private void _sendNotificationEvent(
		SharingEntry sharingEntry, ServiceContext serviceContext) {

		try {
			User user = _userLocalService.getUser(sharingEntry.getToUserId());

			SubscriptionSender subscriptionSender = new SubscriptionSender();

			subscriptionSender.setBody(
				_sharingNotificationMessageProvider.getBody(
					sharingEntry, user.getLocale()));
			subscriptionSender.setClassName(sharingEntry.getModelClassName());
			subscriptionSender.setClassPK(sharingEntry.getSharingEntryId());
			subscriptionSender.setCompanyId(user.getCompanyId());
			subscriptionSender.setCurrentUserId(serviceContext.getUserId());
			subscriptionSender.setEntryURL(
				NotificationsSharingEntryUtil.getEntryURL(
					sharingEntry, serviceContext.getLiferayPortletRequest(),
					serviceContext.getLiferayPortletResponse()));

			String fromName = PrefsPropsUtil.getString(
				user.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
			String fromAddress = PrefsPropsUtil.getString(
				user.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

			subscriptionSender.setFrom(fromAddress, fromName);

			subscriptionSender.setHtmlFormat(true);
			subscriptionSender.setMailId(
				"sharing_entry", sharingEntry.getSharingEntryId());
			subscriptionSender.setNotificationType(
				SharingNotificationsConstants.NOTIFICATION_TYPE_SHARE);
			subscriptionSender.setPortletId(SharingPortletKeys.SHARING);
			subscriptionSender.setScopeGroupId(sharingEntry.getGroupId());
			subscriptionSender.setServiceContext(serviceContext);

			subscriptionSender.addRuntimeSubscribers(
				user.getEmailAddress(), user.getFullName());

			subscriptionSender.flushNotificationsAsync();
		}
		catch (Exception e) {
			_log.error(
				"Unable to send notification for sharing entry: " +
					sharingEntry.getSharingEntryId(),
				e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationsSharingEntryLocalServiceWrapper.class);

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.sharing.notifications)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SharingNotificationMessageProvider
		_sharingNotificationMessageProvider;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}