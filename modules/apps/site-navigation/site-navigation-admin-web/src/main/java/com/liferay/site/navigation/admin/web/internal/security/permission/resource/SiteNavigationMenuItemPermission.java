/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.admin.web.internal.security.permission.resource;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;

/**
 * @author Preston Crary
 */
public class SiteNavigationMenuItemPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long siteNavigationMenuItemId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<SiteNavigationMenuItem>
			modelResourcePermission =
				_siteNavigationMenuItemModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, siteNavigationMenuItemId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			SiteNavigationMenuItem siteNavigationMenuItem, String actionId)
		throws PortalException {

		ModelResourcePermission<SiteNavigationMenuItem>
			modelResourcePermission =
				_siteNavigationMenuItemModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, siteNavigationMenuItem, actionId);
	}

	private static final Snapshot
		<ModelResourcePermission<SiteNavigationMenuItem>>
			_siteNavigationMenuItemModelResourcePermissionSnapshot =
				new Snapshot<>(
					SiteNavigationMenuItemPermission.class,
					Snapshot.cast(ModelResourcePermission.class),
					"(model.class.name=com.liferay.site.navigation.model." +
						"SiteNavigationMenuItem)");

}