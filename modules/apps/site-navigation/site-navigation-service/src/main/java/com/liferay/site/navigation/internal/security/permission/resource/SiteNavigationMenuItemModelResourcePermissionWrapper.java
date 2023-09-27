/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.internal.security.permission.resource;

import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.site.navigation.model.SiteNavigationMenuItem",
	service = ModelResourcePermission.class
)
public class SiteNavigationMenuItemModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<SiteNavigationMenuItem> {

	@Override
	protected ModelResourcePermission<SiteNavigationMenuItem>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			SiteNavigationMenuItem.class,
			SiteNavigationMenuItem::getSiteNavigationMenuItemId,
			_siteNavigationMenuItemLocalService::getSiteNavigationMenuItem,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> {
			});
	}

	@Reference(
		target = "(resource.name=" + SiteNavigationConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

}