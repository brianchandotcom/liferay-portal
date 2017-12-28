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
 * Provides a wrapper for {@link SiteNavigationMenuItemService}.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuItemService
 * @generated
 */
@ProviderType
public class SiteNavigationMenuItemServiceWrapper
	implements SiteNavigationMenuItemService,
		ServiceWrapper<SiteNavigationMenuItemService> {
	public SiteNavigationMenuItemServiceWrapper(
		SiteNavigationMenuItemService siteNavigationMenuItemService) {
		_siteNavigationMenuItemService = siteNavigationMenuItemService;
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenuItem addSiteNavigationMenuItem(
		long groupId, long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId, java.lang.String type,
		java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuItemService.addSiteNavigationMenuItem(groupId,
			siteNavigationMenuId, parentSiteNavigationMenuItemId, type,
			typeSettings, serviceContext);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenuItem deleteSiteNavigationMenuItem(
		long siteNavigationMenuItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuItemService.deleteSiteNavigationMenuItem(siteNavigationMenuItemId);
	}

	@Override
	public void deleteSiteNavigationMenuItems(long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_siteNavigationMenuItemService.deleteSiteNavigationMenuItems(siteNavigationMenuId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _siteNavigationMenuItemService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.site.navigation.model.SiteNavigationMenuItem> getSiteNavigationMenuItems(
		long siteNavigationMenuId) {
		return _siteNavigationMenuItemService.getSiteNavigationMenuItems(siteNavigationMenuId);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenuItem updateSiteNavigationMenuItem(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		int order) throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuItemService.updateSiteNavigationMenuItem(siteNavigationMenuId,
			parentSiteNavigationMenuItemId, order);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenuItem updateSiteNavigationMenuItem(
		long siteNavigationMenuId, java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuItemService.updateSiteNavigationMenuItem(siteNavigationMenuId,
			typeSettings, serviceContext);
	}

	@Override
	public SiteNavigationMenuItemService getWrappedService() {
		return _siteNavigationMenuItemService;
	}

	@Override
	public void setWrappedService(
		SiteNavigationMenuItemService siteNavigationMenuItemService) {
		_siteNavigationMenuItemService = siteNavigationMenuItemService;
	}

	private SiteNavigationMenuItemService _siteNavigationMenuItemService;
}