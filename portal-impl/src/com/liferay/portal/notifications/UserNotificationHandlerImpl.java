/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.notifications;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Jonathan Lee
 */
public class UserNotificationHandlerImpl implements UserNotificationHandler {

	public UserNotificationHandlerImpl(
		UserNotificationHandler userNotificationHandler) {

		_userNotificationHandler = userNotificationHandler;
	}

	@Override
	public String getPortletId() {
		return _userNotificationHandler.getPortletId();
	}

	@Override
	public String getSelector() {
		return _userNotificationHandler.getSelector();
	}

	@Override
	public UserNotificationFeedEntry interpret(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		return _userNotificationHandler.interpret(
			userNotificationEvent, serviceContext);
	}

	@Override
	public boolean isDeliver(
			long userId, long classNameId, int notificationType,
			int deliveryType, ServiceContext serviceContext)
		throws PortalException {

		return _userNotificationHandler.isDeliver(
			userId, classNameId, notificationType, deliveryType,
			serviceContext);
	}

	@Override
	public boolean isOpenDialog() {
		return _userNotificationHandler.isOpenDialog();
	}

	private final UserNotificationHandler _userNotificationHandler;

}