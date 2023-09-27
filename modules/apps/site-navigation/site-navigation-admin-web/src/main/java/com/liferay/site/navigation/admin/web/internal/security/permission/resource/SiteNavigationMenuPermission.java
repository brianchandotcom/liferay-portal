/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.admin.web.internal.security.permission.resource;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.site.navigation.model.SiteNavigationMenu;

/**
 * @author Preston Crary
 */
public class SiteNavigationMenuPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long siteNavigationMenuId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<SiteNavigationMenu> modelResourcePermission =
			_siteNavigationMenuModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, siteNavigationMenuId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			SiteNavigationMenu siteNavigationMenu, String actionId)
		throws PortalException {

		ModelResourcePermission<SiteNavigationMenu> modelResourcePermission =
			_siteNavigationMenuModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, siteNavigationMenu, actionId);
	}

	private static final Snapshot<ModelResourcePermission<SiteNavigationMenu>>
		_siteNavigationMenuModelResourcePermissionSnapshot = new Snapshot<>(
			SiteNavigationMenuPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.site.navigation.model." +
				"SiteNavigationMenu)");

}