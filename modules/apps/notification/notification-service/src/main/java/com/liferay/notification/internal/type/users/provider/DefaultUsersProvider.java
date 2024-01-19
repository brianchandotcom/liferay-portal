/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type.users.provider;

import com.liferay.notification.constants.NotificationRecipientConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

/**
 * @author Feliphe Marinho
 */
public class DefaultUsersProvider
	extends BaseUsersProvider implements UsersProvider {

	public DefaultUsersProvider(
		PermissionCheckerFactory permissionCheckerFactory,
		UserLocalService userLocalService) {

		super(permissionCheckerFactory);

		_userLocalService = userLocalService;
	}

	@Override
	public String getRecipientType() {
		return NotificationRecipientConstants.TYPE_USER;
	}

	@Override
	public List<User> provide(NotificationContext notificationContext)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		NotificationRecipient notificationRecipient =
			notificationTemplate.getNotificationRecipient();

		return TransformUtil.unsafeTransform(
			notificationRecipient.getNotificationRecipientSettings(),
			notificationRecipientSetting -> {
				User user = _userLocalService.getUserByScreenName(
					notificationRecipientSetting.getCompanyId(),
					notificationRecipientSetting.getValue());

				if (!hasViewPermission(
						notificationContext.getClassName(),
						notificationContext.getClassPK(), user)) {

					return null;
				}

				return user;
			});
	}

	private final UserLocalService _userLocalService;

}