/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.web.internal.security.permission.resource;

import com.liferay.audiences.constants.AudiencesConstants;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

/**
 * @author Eudaldo Alonso
 */
public class AudiencesResourcePermission {

	public static void check(
			PermissionChecker permissionChecker, long scopeGroupId,
			String actionId)
		throws PrincipalException {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionSnapshot.get();

		portletResourcePermission.check(
			permissionChecker, scopeGroupId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionSnapshot.get();

		return portletResourcePermission.contains(
			permissionChecker, groupId, actionId);
	}

	private static final Snapshot<PortletResourcePermission>
		_portletResourcePermissionSnapshot = new Snapshot<>(
			AudiencesResourcePermission.class, PortletResourcePermission.class,
			"(resource.name=" + AudiencesConstants.RESOURCE_NAME + ")");

}