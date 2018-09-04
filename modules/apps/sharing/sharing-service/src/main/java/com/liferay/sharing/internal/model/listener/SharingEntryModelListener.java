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

package com.liferay.sharing.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.sharing.model.SharingEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ModelListener.class)
public class SharingEntryModelListener extends BaseModelListener<SharingEntry> {

	@Override
	public void onAfterCreate(SharingEntry sharingEntry)
		throws ModelListenerException {

		try {
			String portletId = PortletProviderUtil.getPortletId(
				SharingEntry.class.getName(), PortletProvider.Action.ADD);

			if (UserNotificationManagerUtil.isDeliver(
					sharingEntry.getToUserId(), portletId, 0,
					UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
					UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

				JSONObject notificationEventJSONObject =
					JSONFactoryUtil.createJSONObject();

				notificationEventJSONObject.put(
					"classPK", sharingEntry.getSharingEntryId());

				NotificationEvent notificationEvent =
					NotificationEventFactoryUtil.createNotificationEvent(
						System.currentTimeMillis(), portletId,
						notificationEventJSONObject);

				notificationEvent.setDeliveryRequired(0);
				notificationEvent.setDeliveryType(
					UserNotificationDeliveryConstants.TYPE_WEBSITE);

				_userNotificationEventLocalService.addUserNotificationEvent(
					sharingEntry.getToUserId(), false, notificationEvent);
			}
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}