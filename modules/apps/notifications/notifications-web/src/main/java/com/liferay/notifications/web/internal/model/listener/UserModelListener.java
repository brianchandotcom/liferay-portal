/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ModelListener.class)
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
		catch (Exception exception) {
			_log.error(
				"Unable to remove chat entries and status for user " +
					user.getUserId(),
				exception);
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