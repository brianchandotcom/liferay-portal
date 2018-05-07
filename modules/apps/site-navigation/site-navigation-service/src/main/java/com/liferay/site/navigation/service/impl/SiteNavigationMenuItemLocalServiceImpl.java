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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.navigation.exception.InvalidSiteNavigationMenuItemOrderException;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.base.SiteNavigationMenuItemLocalServiceBaseImpl;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuItemOrderComparator;

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
			long parentSiteNavigationMenuItemId, String type, int order,
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
		siteNavigationMenuItem.setOrder(order);

		siteNavigationMenuItemPersistence.update(siteNavigationMenuItem);

		return siteNavigationMenuItem;
	}

	@Override
	public SiteNavigationMenuItem addSiteNavigationMenuItem(
			long userId, long groupId, long siteNavigationMenuId,
			long parentSiteNavigationMenuItemId, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		int siteNavigationMenuItemCount =
			siteNavigationMenuItemPersistence.
				countByParentSiteNavigationMenuItemId(
					parentSiteNavigationMenuItemId);

		return addSiteNavigationMenuItem(
			userId, groupId, siteNavigationMenuId,
			parentSiteNavigationMenuItemId, type, siteNavigationMenuItemCount,
			typeSettings, serviceContext);
	}

	@Override
	public SiteNavigationMenuItem deleteSiteNavigationMenuItem(
			long siteNavigationMenuItemId)
		throws PortalException {

		SiteNavigationMenuItem siteNavigationMenuItem =
			getSiteNavigationMenuItem(siteNavigationMenuItemId);

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			getChildSiteNavigationMenuItems(siteNavigationMenuItemId);

		for (SiteNavigationMenuItem childSiteNavigationMenuItem :
				siteNavigationMenuItems) {

			childSiteNavigationMenuItem.setParentSiteNavigationMenuItemId(
				siteNavigationMenuItem.getParentSiteNavigationMenuItemId());

			siteNavigationMenuItemPersistence.update(
				childSiteNavigationMenuItem);
		}

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
				parentSiteNavigationMenuItemId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new SiteNavigationMenuItemOrderComparator());
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
			siteNavigationMenuId, parentSiteNavigationMenuItemId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new SiteNavigationMenuItemOrderComparator());
	}

	@Override
	public SiteNavigationMenuItem updateSiteNavigationMenuItem(
			long siteNavigationMenuItemId, long parentSiteNavigationMenuItemId,
			int order)
		throws PortalException {

		// Site navigation menu item

		validate(siteNavigationMenuItemId, parentSiteNavigationMenuItemId);

		SiteNavigationMenuItem siteNavigationMenuItem =
			siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItemId);

		long oldParentSiteNavigationMenuItemId =
			siteNavigationMenuItem.getParentSiteNavigationMenuItemId();

		siteNavigationMenuItem.setParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId);
		siteNavigationMenuItem.setOrder(order);

		siteNavigationMenuItemPersistence.update(siteNavigationMenuItem);

		// Child site navigation menu item

		int newOrder = 0;

		List<SiteNavigationMenuItem> children = getSiteNavigationMenuItems(
			siteNavigationMenuItem.getSiteNavigationMenuId(),
			parentSiteNavigationMenuItemId);

		for (SiteNavigationMenuItem child : children) {
			if (newOrder == order) {
				newOrder++;
			}

			if (child.getSiteNavigationMenuItemId() ==
					siteNavigationMenuItemId) {

				continue;
			}

			child.setOrder(newOrder++);

			siteNavigationMenuItemPersistence.update(child);
		}

		if (parentSiteNavigationMenuItemId !=
				oldParentSiteNavigationMenuItemId) {

			List<SiteNavigationMenuItem> oldChildren =
				getSiteNavigationMenuItems(
					siteNavigationMenuItem.getSiteNavigationMenuId(),
					oldParentSiteNavigationMenuItemId);

			for (SiteNavigationMenuItem oldChild : oldChildren) {
				if (oldChild.getOrder() <= order) {
					continue;
				}

				oldChild.setOrder(oldChild.getOrder() - 1);

				siteNavigationMenuItemPersistence.update(oldChild);
			}
		}

		return siteNavigationMenuItem;
	}

	@Override
	public SiteNavigationMenuItem updateSiteNavigationMenuItem(
			long userId, long siteNavigationMenuItemId, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		SiteNavigationMenuItem siteNavigationMenuItem =
			siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItemId);

		siteNavigationMenuItem.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		siteNavigationMenuItem.setUserId(userId);
		siteNavigationMenuItem.setUserName(user.getFullName());
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