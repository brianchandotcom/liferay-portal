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

package com.liferay.site.navigation.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SiteNavigationMenuService}.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuService
 * @generated
 */
@ProviderType
public class SiteNavigationMenuServiceWrapper
	implements SiteNavigationMenuService,
		ServiceWrapper<SiteNavigationMenuService> {
	public SiteNavigationMenuServiceWrapper(
		SiteNavigationMenuService siteNavigationMenuService) {
		_siteNavigationMenuService = siteNavigationMenuService;
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu addSiteNavigationMenu(
		long groupId, long userId, java.lang.String name,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuService.addSiteNavigationMenu(groupId,
			userId, name, serviceContext);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu deleteSiteNavigationMenu(
		long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuService.deleteSiteNavigationMenu(siteNavigationMenuId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _siteNavigationMenuService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu> getSiteNavigationMenus(
		long groupId) {
		return _siteNavigationMenuService.getSiteNavigationMenus(groupId);
	}

	@Override
	public SiteNavigationMenuService getWrappedService() {
		return _siteNavigationMenuService;
	}

	@Override
	public void setWrappedService(
		SiteNavigationMenuService siteNavigationMenuService) {
		_siteNavigationMenuService = siteNavigationMenuService;
	}

	private SiteNavigationMenuService _siteNavigationMenuService;
}