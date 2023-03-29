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

package com.liferay.notification.internal.type.users.provider;

import com.liferay.notification.constants.NotificationRecipientConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "recipient.type=" + NotificationRecipientConstants.TYPE_ROLE,
	service = UsersProvider.class
)
public class RoleUsersProvider
	extends BaseUsersProvider implements UsersProvider {

	@Override
	public String getRecipientType() {
		return NotificationRecipientConstants.TYPE_ROLE;
	}

	@Override
	public List<User> provide(NotificationContext notificationContext)
		throws PortalException {

		Set<Long> userIds = new LinkedHashSet<>();

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		NotificationRecipient notificationRecipient =
			notificationTemplate.getNotificationRecipient();

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipient.getNotificationRecipientSettings()) {

			Role role = _roleLocalService.getRole(
				notificationRecipientSetting.getCompanyId(),
				notificationRecipientSetting.getValue());

			for (long userId :
					_userLocalService.getRoleUserIds(role.getRoleId())) {

				userIds.add(userId);
			}
		}

		return TransformUtil.unsafeTransform(
			userIds,
			userId -> {
				User user = _userLocalService.getUser(userId);

				if (!hasViewPermission(
						notificationContext.getClassName(),
						notificationContext.getClassPK(), user)) {

					return null;
				}

				return user;
			});
	}

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}