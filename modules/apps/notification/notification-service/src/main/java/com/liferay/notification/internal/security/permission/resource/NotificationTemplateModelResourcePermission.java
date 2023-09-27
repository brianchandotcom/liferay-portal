/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.security.permission.resource;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationTemplate",
	service = ModelResourcePermission.class
)
public class NotificationTemplateModelResourcePermission
	implements ModelResourcePermission<NotificationTemplate> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long notificationTemplateId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, notificationTemplateId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, NotificationTemplate.class.getName(),
				notificationTemplateId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			NotificationTemplate notificationTemplate, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, notificationTemplate, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, NotificationTemplate.class.getName(),
				notificationTemplate.getPrimaryKey(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long notificationTemplateId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_notificationTemplateLocalService.getNotificationTemplate(
				notificationTemplateId),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			NotificationTemplate notificationTemplate, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				NotificationTemplate.class.getName(),
				notificationTemplate.getNotificationTemplateId(),
				notificationTemplate.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				null, NotificationTemplate.class.getName(),
				notificationTemplate.getPrimaryKey(), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public String getModelName() {
		return NotificationTemplate.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	@Reference(
		target = "(resource.name=" + NotificationConstants.RESOURCE_NAME_NOTIFICATION_TEMPLATE + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}