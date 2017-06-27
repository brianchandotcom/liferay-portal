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

package com.liferay.portal.security.wedeploy.auth.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourcePermissionChecker;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;

import org.osgi.service.component.annotations.Component;

/**
 * @author Supritha Sundaram
 */
@Component(
	property = {"resource.name=" + WeDeployAuthPermission.RESOURCE_NAME},
	service = ResourcePermissionChecker.class
)
public class WeDeployAuthPermission extends BaseResourcePermissionChecker {

	public static final String RESOURCE_NAME =
		"com.liferay.portal.security.wedeploy.auth";

	public static void check(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker.getUserId(), RESOURCE_NAME, 0, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, String actionId) {

		String portletId = PortletProviderUtil.getPortletId(
			WeDeployAuthApp.class.getName(), PortletProvider.Action.EDIT);

		return contains(
			permissionChecker, RESOURCE_NAME, portletId, 0, actionId);
	}

	@Override
	public Boolean checkResource(
		PermissionChecker permissionChecker, long classPK, String actionId) {

		return contains(permissionChecker, actionId);
	}

}