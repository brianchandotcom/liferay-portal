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

package com.liferay.account.admin.web.internal.security.permission.resource;

import com.liferay.account.constants.AccountConstants;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.OrganizationLocalService;

import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public class AccountPermission {

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionSnapshot.get();

		return portletResourcePermission.contains(
			permissionChecker, groupId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, String portletId,
		String actionId) {

		try {
			OrganizationLocalService organizationLocalService =
				_organizationLocalServiceSnapshot.get();

			List<Organization> organizations =
				organizationLocalService.getUserOrganizations(
					permissionChecker.getUserId(), true);

			for (Organization organization : organizations) {
				if (permissionChecker.hasPermission(
						organization.getGroupId(), portletId, 0, actionId)) {

					return true;
				}
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountPermission.class);

	private static final Snapshot<OrganizationLocalService>
		_organizationLocalServiceSnapshot = new Snapshot<>(
			AccountPermission.class, OrganizationLocalService.class);
	private static final Snapshot<PortletResourcePermission>
		_portletResourcePermissionSnapshot = new Snapshot<>(
			AccountPermission.class, PortletResourcePermission.class,
			"(resource.name=" + AccountConstants.RESOURCE_NAME + ")");

}