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

package com.liferay.site.navigation.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.base.SiteNavigationMenuItemServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"json.web.service.context.name=sitenavigation",
		"json.web.service.context.path=SiteNavigationMenuItem"
	},
	service = AopService.class
)
public class SiteNavigationMenuItemServiceImpl
	extends SiteNavigationMenuItemServiceBaseImpl {

	@Override
	public SiteNavigationMenuItem addSiteNavigationMenuItem(
			long groupId, long siteNavigationMenuId,
			long parentSiteNavigationMenuItemId, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		return siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
			getUserId(), groupId, siteNavigationMenuId,
			parentSiteNavigationMenuItemId, type, typeSettings, serviceContext);
	}

	@Override
	public SiteNavigationMenuItem deleteSiteNavigationMenuItem(
			long siteNavigationMenuItemId)
		throws PortalException {

		return siteNavigationMenuItemLocalService.deleteSiteNavigationMenuItem(
			siteNavigationMenuItemId);
	}

	@Override
	public void deleteSiteNavigationMenuItems(long siteNavigationMenuId)
		throws PortalException {

		siteNavigationMenuItemLocalService.deleteSiteNavigationMenuItems(
			siteNavigationMenuId);
	}

	@Override
	public List<SiteNavigationMenuItem> getSiteNavigationMenuItems(
		long siteNavigationMenuId) {

		return siteNavigationMenuItemLocalService.getSiteNavigationMenuItems(
			siteNavigationMenuId);
	}

	@Override
	public SiteNavigationMenuItem updateSiteNavigationMenuItem(
			long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
			int order)
		throws PortalException {

		return siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			siteNavigationMenuId, parentSiteNavigationMenuItemId, order);
	}

	@Override
	public SiteNavigationMenuItem updateSiteNavigationMenuItem(
			long siteNavigationMenuId, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		return siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			getUserId(), siteNavigationMenuId, typeSettings, serviceContext);
	}

}