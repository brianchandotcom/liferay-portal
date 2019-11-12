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

package com.liferay.portal.reports.engine.console.internal.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.BaseResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsoleConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Peter Shin
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Component(
	immediate = true,
	property = "resource.name=" + ReportsEngineConsoleConstants.RESOURCE_NAME,
	service = ResourcePermissionChecker.class
)
@Deprecated
public class AdminResourcePermissionChecker
	extends BaseResourcePermissionChecker {

	public static final String RESOURCE_NAME =
		ReportsEngineConsoleConstants.RESOURCE_NAME;

	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		_portletResourcePermission.check(permissionChecker, groupId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long classPK, String actionId) {

		return _portletResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Override
	public Boolean checkResource(
		PermissionChecker permissionChecker, long classPK, String actionId) {

		return _portletResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference(
		target = "(resource.name=" + ReportsEngineConsoleConstants.RESOURCE_NAME + ")",
		unbind = "-"
	)
	protected void setPortletResourcePermission(
		PortletResourcePermission portletResourcePermission) {

		_portletResourcePermission = portletResourcePermission;
	}

	private static PortletResourcePermission _portletResourcePermission;

}