/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.web.internal.permission;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsoleConstants;

/**
 * @author Leon Chi
 */
public class AdminResourcePermissionChecker {

	public static final String RESOURCE_NAME =
		ReportsEngineConsoleConstants.RESOURCE_NAME;

	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionSnapshot.get();

		portletResourcePermission.check(permissionChecker, groupId, actionId);
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
			AdminResourcePermissionChecker.class,
			PortletResourcePermission.class,
			"(resource.name=" + ReportsEngineConsoleConstants.RESOURCE_NAME +
				")");

}