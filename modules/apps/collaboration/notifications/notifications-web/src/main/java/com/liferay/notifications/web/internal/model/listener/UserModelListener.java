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

package com.liferay.notifications.web.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserNotificationDeliveryLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(enabled = false, immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterRemove(User user) throws ModelListenerException {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Removing chat entries and status for user " +
						user.getUserId());
			}

			_userNotificationDeliveryLocalService.
				deleteUserNotificationDeliveries(user.getUserId());

			_userNotificationEventLocalService.deleteUserNotificationEvents(
				user.getUserId());
		}
		catch (Exception e) {
			_log.error(
				"Unable to remove chat entries and status for user " +
					user.getUserId(),
				e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelListener.class);

	@Reference
	private UserNotificationDeliveryLocalService
		_userNotificationDeliveryLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}