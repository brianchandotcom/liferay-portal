/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.security.permission.resource;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

/**
 * @author Preston Crary
 */
public class DLPermission {

	public static void check(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PrincipalException {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionSnapshot.get();

		portletResourcePermission.check(permissionChecker, classPK, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long classPK, String actionId) {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionSnapshot.get();

		return portletResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	private static final Snapshot<PortletResourcePermission>
		_portletResourcePermissionSnapshot = new Snapshot<>(
			DLPermission.class, PortletResourcePermission.class,
			"(resource.name=" + DLConstants.RESOURCE_NAME + ")");

}