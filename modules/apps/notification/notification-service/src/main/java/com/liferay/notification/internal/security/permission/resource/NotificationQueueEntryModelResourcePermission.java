/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.security.permission.resource;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Paulo Albuquerque
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationQueueEntry",
	service = ModelResourcePermission.class
)
public class NotificationQueueEntryModelResourcePermission
	implements ModelResourcePermission<NotificationQueueEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long notificationQueueEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, notificationQueueEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, NotificationQueueEntry.class.getName(),
				notificationQueueEntryId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			NotificationQueueEntry notificationQueueEntry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, notificationQueueEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, NotificationQueueEntry.class.getName(),
				notificationQueueEntry.getPrimaryKey(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long notificationQueueEntryId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_notificationQueueEntryLocalService.getNotificationQueueEntry(
				notificationQueueEntryId),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			NotificationQueueEntry notificationQueueEntry, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				NotificationQueueEntry.class.getName(),
				notificationQueueEntry.getNotificationQueueEntryId(),
				notificationQueueEntry.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				null, NotificationQueueEntry.class.getName(),
				notificationQueueEntry.getPrimaryKey(), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public String getModelName() {
		return NotificationQueueEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Reference(
		target = "(resource.name=" + NotificationConstants.RESOURCE_NAME_NOTIFICATION_QUEUE + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}