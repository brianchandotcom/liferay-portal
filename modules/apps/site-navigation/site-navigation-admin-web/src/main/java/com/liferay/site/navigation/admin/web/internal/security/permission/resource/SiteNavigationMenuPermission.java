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

package com.liferay.site.navigation.admin.web.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.site.navigation.model.SiteNavigationMenu;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class SiteNavigationMenuPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long siteNavigationMenuId,
			String actionId)
		throws PortalException {

		return _siteNavigationMenuModelResourcePermission.contains(
			permissionChecker, siteNavigationMenuId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			SiteNavigationMenu siteNavigationMenu, String actionId)
		throws PortalException {

		return _siteNavigationMenuModelResourcePermission.contains(
			permissionChecker, siteNavigationMenu, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.site.navigation.model.SiteNavigationMenu)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<SiteNavigationMenu> modelResourcePermission) {

		_siteNavigationMenuModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<SiteNavigationMenu>
		_siteNavigationMenuModelResourcePermission;

}