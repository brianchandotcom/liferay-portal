/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.UserGroupRoleModel;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Feliphe Marinho
 */
public class RoleUsersProvider
	extends BaseUsersProvider implements UsersProvider {

	public RoleUsersProvider(
		PermissionCheckerFactory permissionCheckerFactory,
		RoleLocalService roleLocalService,
		UserGroupRoleLocalService userGroupRoleLocalService,
		UserLocalService userLocalService) {

		super(permissionCheckerFactory);

		_roleLocalService = roleLocalService;
		_userGroupRoleLocalService = userGroupRoleLocalService;
		_userLocalService = userLocalService;
	}

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

			if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
				(role.getType() == RoleConstants.TYPE_SITE)) {

				userIds.addAll(
					ListUtil.toList(
						_userGroupRoleLocalService.getUserGroupRolesByGroup(
							notificationContext.getGroupId()),
						UserGroupRoleModel::getUserId));

				continue;
			}

			for (long userId :
					_userLocalService.getRoleUserIds(
						role.getRoleId(), UserConstants.TYPE_REGULAR)) {

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

	private final RoleLocalService _roleLocalService;
	private final UserGroupRoleLocalService _userGroupRoleLocalService;
	private final UserLocalService _userLocalService;

}