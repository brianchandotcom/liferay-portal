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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.navigation.exception.InvalidSiteNavigationMenuItemOrderException;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.base.SiteNavigationMenuItemLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuItemLocalServiceImpl
	extends SiteNavigationMenuItemLocalServiceBaseImpl {

	@Override
	public SiteNavigationMenuItem addSiteNavigationMenuItem(
			long userId, long groupId, long siteNavigationMenuId,
			long parentSiteNavigationMenuItemId, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long siteNavigationMenuItemId = counterLocalService.increment();

		SiteNavigationMenuItem siteNavigationMenuItem =
			siteNavigationMenuItemPersistence.create(siteNavigationMenuItemId);

		siteNavigationMenuItem.setGroupId(groupId);
		siteNavigationMenuItem.setCompanyId(user.getCompanyId());
		siteNavigationMenuItem.setUserId(userId);
		siteNavigationMenuItem.setUserName(user.getFullName());
		siteNavigationMenuItem.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		siteNavigationMenuItem.setSiteNavigationMenuId(siteNavigationMenuId);
		siteNavigationMenuItem.setParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId);
		siteNavigationMenuItem.setType(type);
		siteNavigationMenuItem.setTypeSettings(typeSettings);

		siteNavigationMenuItemPersistence.update(siteNavigationMenuItem);

		return siteNavigationMenuItem;
	}

	@Override
	public SiteNavigationMenuItem deleteSiteNavigationMenuItem(
			long siteNavigationMenuItemId)
		throws PortalException {

		return siteNavigationMenuItemPersistence.remove(
			siteNavigationMenuItemId);
	}

	@Override
	public void deleteSiteNavigationMenuItems(long siteNavigationMenuId)
		throws PortalException {

		siteNavigationMenuItemPersistence.removeBySiteNavigationMenuId(
			siteNavigationMenuId);
	}

	@Override
	public List<SiteNavigationMenuItem> getChildSiteNavigationMenuItems(
		long parentSiteNavigationMenuItemId) {

		return siteNavigationMenuItemPersistence.
			findByParentSiteNavigationMenuItemId(
				parentSiteNavigationMenuItemId);
	}

	@Override
	public List<SiteNavigationMenuItem> getSiteNavigationMenuItems(
		long siteNavigationMenuId) {

		return siteNavigationMenuItemPersistence.findBySiteNavigationMenuId(
			siteNavigationMenuId);
	}

	@Override
	public List<SiteNavigationMenuItem> getSiteNavigationMenuItems(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId) {

		return siteNavigationMenuItemPersistence.findByS_P(
			siteNavigationMenuId, parentSiteNavigationMenuItemId);
	}

	@Override
	public SiteNavigationMenuItem updateSiteNavigationMenuItem(
			long userId, long siteNavigationMenuItemId,
			long parentSiteNavigationMenuItemId, ServiceContext serviceContext)
		throws PortalException {

		SiteNavigationMenuItem siteNavigationMenuItem =
			siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItemId);

		return updateSiteNavigationMenuItem(
			userId, siteNavigationMenuItemId, parentSiteNavigationMenuItemId,
			siteNavigationMenuItem.getTypeSettings(), serviceContext);
	}

	@Override
	public SiteNavigationMenuItem updateSiteNavigationMenuItem(
			long userId, long siteNavigationMenuItemId,
			long parentSiteNavigationMenuItemId, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		validate(siteNavigationMenuItemId, parentSiteNavigationMenuItemId);

		User user = userLocalService.getUser(userId);

		SiteNavigationMenuItem siteNavigationMenuItem =
			siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItemId);

		siteNavigationMenuItem.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		siteNavigationMenuItem.setUserId(userId);
		siteNavigationMenuItem.setUserName(user.getFullName());
		siteNavigationMenuItem.setParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId);
		siteNavigationMenuItem.setTypeSettings(typeSettings);

		siteNavigationMenuItemPersistence.update(siteNavigationMenuItem);

		return siteNavigationMenuItem;
	}

	protected void validate(
			long siteNavigationMenuItemId, long parentSiteNavigationMenuItemId)
		throws PortalException {

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			getChildSiteNavigationMenuItems(siteNavigationMenuItemId);

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			siteNavigationMenuItemId =
				siteNavigationMenuItem.getSiteNavigationMenuItemId();

			if (siteNavigationMenuItemId == parentSiteNavigationMenuItemId) {
				throw new InvalidSiteNavigationMenuItemOrderException();
			}

			validate(siteNavigationMenuItemId, parentSiteNavigationMenuItemId);
		}
	}

}