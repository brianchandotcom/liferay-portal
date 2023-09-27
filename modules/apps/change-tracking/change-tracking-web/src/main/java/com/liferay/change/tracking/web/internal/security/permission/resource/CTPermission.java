/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.security.permission.resource;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

/**
 * @author Preston Crary
 */
public class CTPermission {

	public static boolean contains(
		PermissionChecker permissionChecker, String actionId) {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionSnapshot.get();

		return portletResourcePermission.contains(
			permissionChecker, null, actionId);
	}

	private static final Snapshot<PortletResourcePermission>
		_portletResourcePermissionSnapshot = new Snapshot<>(
			CTPermission.class, PortletResourcePermission.class,
			"(resource.name=" + CTConstants.RESOURCE_NAME + ")");

}