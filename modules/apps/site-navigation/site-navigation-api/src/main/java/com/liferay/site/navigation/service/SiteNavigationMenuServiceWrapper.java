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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SiteNavigationMenuService}.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuService
 * @generated
 */
public class SiteNavigationMenuServiceWrapper
	implements ServiceWrapper<SiteNavigationMenuService>,
			   SiteNavigationMenuService {

	public SiteNavigationMenuServiceWrapper(
		SiteNavigationMenuService siteNavigationMenuService) {

		_siteNavigationMenuService = siteNavigationMenuService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SiteNavigationMenuServiceUtil} to access the site navigation menu remote service. Add custom service methods to <code>com.liferay.site.navigation.service.impl.SiteNavigationMenuServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu
			addSiteNavigationMenu(
				long groupId, String name, int type, boolean auto,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteNavigationMenuService.addSiteNavigationMenu(
			groupId, name, type, auto, serviceContext);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu
			addSiteNavigationMenu(
				long groupId, String name, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteNavigationMenuService.addSiteNavigationMenu(
			groupId, name, type, serviceContext);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu
			addSiteNavigationMenu(
				long groupId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteNavigationMenuService.addSiteNavigationMenu(
			groupId, name, serviceContext);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu
			deleteSiteNavigationMenu(long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteNavigationMenuService.deleteSiteNavigationMenu(
			siteNavigationMenuId);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu
			fetchSiteNavigationMenu(long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteNavigationMenuService.fetchSiteNavigationMenu(
			siteNavigationMenuId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _siteNavigationMenuService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu>
		getSiteNavigationMenus(long groupId) {

		return _siteNavigationMenuService.getSiteNavigationMenus(groupId);
	}

	@Override
	public java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu>
		getSiteNavigationMenus(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				orderByComparator) {

		return _siteNavigationMenuService.getSiteNavigationMenus(
			groupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu>
		getSiteNavigationMenus(
			long groupId, String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				orderByComparator) {

		return _siteNavigationMenuService.getSiteNavigationMenus(
			groupId, keywords, start, end, orderByComparator);
	}

	@Override
	public int getSiteNavigationMenusCount(long groupId) {
		return _siteNavigationMenuService.getSiteNavigationMenusCount(groupId);
	}

	@Override
	public int getSiteNavigationMenusCount(long groupId, String keywords) {
		return _siteNavigationMenuService.getSiteNavigationMenusCount(
			groupId, keywords);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu
			updateSiteNavigationMenu(
				long siteNavigationMenuId, int type, boolean auto,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteNavigationMenuService.updateSiteNavigationMenu(
			siteNavigationMenuId, type, auto, serviceContext);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu
			updateSiteNavigationMenu(
				long siteNavigationMenuId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteNavigationMenuService.updateSiteNavigationMenu(
			siteNavigationMenuId, name, serviceContext);
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