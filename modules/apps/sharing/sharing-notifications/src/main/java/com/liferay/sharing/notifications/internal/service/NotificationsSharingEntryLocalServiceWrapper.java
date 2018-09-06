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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.constants.SharingPortletKeys;
import com.liferay.sharing.model.SharingEntry;
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

		_sendNotificationEvent(sharingEntry);

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

		_sendNotificationEvent(sharingEntry);

		return sharingEntry;
	}

	private void _sendNotificationEvent(SharingEntry sharingEntry)
		throws PortalException {

		if (UserNotificationManagerUtil.isDeliver(
				sharingEntry.getToUserId(), SharingPortletKeys.SHARING, 0,
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

			JSONObject notificationEventJSONObject =
				JSONFactoryUtil.createJSONObject();

			notificationEventJSONObject.put(
				"classPK", sharingEntry.getSharingEntryId());

			User fromUser = _userLocalService.fetchUser(
				sharingEntry.getFromUserId());

			if (fromUser != null) {
				notificationEventJSONObject.put(
					"fromUserFullName", fromUser.getFullName());
			}

			_userNotificationEventLocalService.sendUserNotificationEvents(
				sharingEntry.getToUserId(), SharingPortletKeys.SHARING,
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				notificationEventJSONObject);
		}
	}

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}