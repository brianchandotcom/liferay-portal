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