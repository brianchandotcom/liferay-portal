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
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.site.navigation.model.SiteNavigationMenu",
	service = ModelResourcePermission.class
)
public class SiteNavigationMenuModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<SiteNavigationMenu> {

	@Override
	protected ModelResourcePermission<SiteNavigationMenu>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			SiteNavigationMenu.class,
			SiteNavigationMenu::getSiteNavigationMenuId,
			_siteNavigationMenuLocalService::getSiteNavigationMenu,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> {
			});
	}

	@Reference(
		target = "(resource.name=" + SiteNavigationConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}