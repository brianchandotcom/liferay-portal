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

package com.liferay.portal.security.wedeploy.auth.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.security.wedeploy.auth.constants.WeDeployConstants;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp",
	service = ModelResourcePermission.class
)
public class WeDeployAuthAppModelResourcePermission
	implements ModelResourcePermission<WeDeployAuthApp> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long weDeployAuthAppId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, weDeployAuthAppId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, WeDeployAuthApp.class.getName(),
				weDeployAuthAppId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			WeDeployAuthApp weDeployAuthApp, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, weDeployAuthApp, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, WeDeployAuthApp.class.getName(),
				weDeployAuthApp.getWeDeployAuthAppId(), actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long weDeployAuthAppId,
		String actionId) {

		return permissionChecker.hasPermission(
			null, WeDeployAuthApp.class.getName(), weDeployAuthAppId, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, WeDeployAuthApp weDeployAuthApp,
		String actionId) {

		return permissionChecker.hasPermission(
			null, WeDeployAuthApp.class.getName(),
			weDeployAuthApp.getWeDeployAuthAppId(), actionId);
	}

	@Override
	public String getModelName() {
		return WeDeployAuthApp.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference(
		target = "(resource.name=" + WeDeployConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}