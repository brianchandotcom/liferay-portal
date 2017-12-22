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

package com.liferay.portal.security.service.access.policy.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.security.service.access.policy.constants.SAPConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 * @deprecated As of 2.1.0, with no direct replacement
 */
@Component(
	immediate = true,
	property = {"resource.name=" + SAPConstants.RESOURCE_NAME},
	service = ResourcePermissionChecker.class
)
@Deprecated
public class SAPPermission implements ResourcePermissionChecker {

	public static final String RESOURCE_NAME = SAPConstants.RESOURCE_NAME;

	public static void check(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		_portletResourcePermission.check(permissionChecker, null, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, String actionId) {

		return _portletResourcePermission.contains(
			permissionChecker, null, actionId);
	}

	@Override
	public Boolean checkResource(
		PermissionChecker permissionChecker, long classPK, String actionId) {

		return _portletResourcePermission.contains(
			permissionChecker, null, actionId);
	}

	@Reference(
		target = "(resource.name=" + SAPConstants.RESOURCE_NAME + ")",
		unbind = "-"
	)
	protected void setPortletResourcePermission(
		PortletResourcePermission portletResourcePermission) {

		_portletResourcePermission = portletResourcePermission;
	}

	private static PortletResourcePermission _portletResourcePermission;

}