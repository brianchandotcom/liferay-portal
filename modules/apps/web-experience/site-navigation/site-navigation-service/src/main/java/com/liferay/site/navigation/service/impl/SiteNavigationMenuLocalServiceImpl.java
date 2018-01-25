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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.exception.PrimarySiteNavigationMenuException;
import com.liferay.site.navigation.exception.SiteNavigationMenuNameException;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.base.SiteNavigationMenuLocalServiceBaseImpl;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuLocalServiceImpl
	extends SiteNavigationMenuLocalServiceBaseImpl {

	@Override
	public SiteNavigationMenu addDefaultSiteNavigationMenu(
			long userId, long groupId, ServiceContext serviceContext)
		throws PortalException {

		// Site navigation menu

		SiteNavigationMenu siteNavigationMenu = addSiteNavigationMenu(
			userId, groupId, "Default", SiteNavigationConstants.TYPE_PRIMARY,
			serviceContext);

		// Site navigation menu items

		List<Layout> layouts = layoutLocalService.getLayouts(groupId, false, 0);

		for (Layout layout : layouts) {
			_addSiteNavigationMenuItems(
				userId, groupId, siteNavigationMenu.getSiteNavigationMenuId(),
				0, layout, serviceContext);
		}

		return null;
	}

	@Override
	public SiteNavigationMenu addSiteNavigationMenu(
			long userId, long groupId, String name, int type,
			ServiceContext serviceContext)
		throws PortalException {

		// Site navigation menu

		validate(name);

		User user = userLocalService.getUser(userId);

		long siteNavigationMenuId = counterLocalService.increment();

		SiteNavigationMenu siteNavigationMenu =
			siteNavigationMenuPersistence.create(siteNavigationMenuId);

		siteNavigationMenu.setGroupId(groupId);
		siteNavigationMenu.setCompanyId(user.getCompanyId());
		siteNavigationMenu.setUserId(userId);
		siteNavigationMenu.setUserName(user.getFullName());
		siteNavigationMenu.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		siteNavigationMenu.setName(name);
		siteNavigationMenu.setType(type);

		siteNavigationMenuPersistence.update(siteNavigationMenu);

		// Resources

		resourceLocalService.addResources(
			siteNavigationMenu.getCompanyId(), siteNavigationMenu.getGroupId(),
			siteNavigationMenu.getUserId(), SiteNavigationMenu.class.getName(),
			siteNavigationMenu.getSiteNavigationMenuId(), false, true, true);

		return siteNavigationMenu;
	}

	@Override
	public SiteNavigationMenu addSiteNavigationMenu(
			long userId, long groupId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		int type = SiteNavigationConstants.TYPE_DEFAULT;

		int siteNavigationMenusCount = getSiteNavigationMenusCount(
			serviceContext.getScopeGroupId());

		if (siteNavigationMenusCount <= 0) {
			type = SiteNavigationConstants.TYPE_PRIMARY;
		}

		return addSiteNavigationMenu(
			userId, groupId, name, type, serviceContext);
	}

	@Override
	public SiteNavigationMenu deleteSiteNavigationMenu(
			long siteNavigationMenuId)
		throws PortalException {

		SiteNavigationMenu siteNavigationMenu = getSiteNavigationMenu(
			siteNavigationMenuId);

		return deleteSiteNavigationMenu(siteNavigationMenu);
	}

	@Override
	public SiteNavigationMenu deleteSiteNavigationMenu(
			SiteNavigationMenu siteNavigationMenu)
		throws PortalException {

		// Site navigation menu

		siteNavigationMenuPersistence.remove(
			siteNavigationMenu.getSiteNavigationMenuId());

		// Resources

		resourceLocalService.deleteResource(
			siteNavigationMenu.getCompanyId(),
			SiteNavigationMenuItem.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			siteNavigationMenu.getSiteNavigationMenuId());

		// Site navigation menu items

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			siteNavigationMenuItemLocalService.getSiteNavigationMenuItems(
				siteNavigationMenu.getSiteNavigationMenuId());

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			siteNavigationMenuItemLocalService.deleteSiteNavigationMenuItem(
				siteNavigationMenuItem.getSiteNavigationMenuItemId());
		}

		return siteNavigationMenu;
	}

	@Override
	public SiteNavigationMenu fetchPrimarySiteNavigationMenu(long groupId) {
		return siteNavigationMenuPersistence.fetchByG_T(
			groupId, SiteNavigationConstants.TYPE_PRIMARY);
	}

	@Override
	public List<SiteNavigationMenu> getSiteNavigationMenus(long groupId) {
		return siteNavigationMenuPersistence.findByGroupId(groupId);
	}

	@Override
	public List<SiteNavigationMenu> getSiteNavigationMenus(
		long groupId, int start, int end, OrderByComparator orderByComparator) {

		return siteNavigationMenuPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<SiteNavigationMenu> getSiteNavigationMenus(
		long groupId, String keywords, int start, int end,
		OrderByComparator orderByComparator) {

		return siteNavigationMenuPersistence.findByG_N(
			groupId, keywords, start, end, orderByComparator);
	}

	@Override
	public int getSiteNavigationMenusCount(long groupId) {
		return siteNavigationMenuPersistence.countByGroupId(groupId);
	}

	@Override
	public int getSiteNavigationMenusCount(long groupId, String keywords) {
		return siteNavigationMenuPersistence.countByG_N(groupId, keywords);
	}

	@Override
	public SiteNavigationMenu updateSiteNavigationMenu(
			long userId, long siteNavigationMenuId, int type,
			ServiceContext serviceContext)
		throws PortalException {

		SiteNavigationMenu siteNavigationMenu = getSiteNavigationMenu(
			siteNavigationMenuId);

		SiteNavigationMenu actualTypeSiteNavigationMenu =
			siteNavigationMenuPersistence.fetchByG_T(
				siteNavigationMenu.getGroupId(), type);

		if ((actualTypeSiteNavigationMenu != null) &&
			(actualTypeSiteNavigationMenu.getType() != type) &&
			(actualTypeSiteNavigationMenu.getSiteNavigationMenuId() !=
				siteNavigationMenuId)) {

			actualTypeSiteNavigationMenu.setType(
				SiteNavigationConstants.TYPE_DEFAULT);

			siteNavigationMenuPersistence.update(actualTypeSiteNavigationMenu);
		}

		User user = userLocalService.getUser(userId);

		siteNavigationMenu.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		siteNavigationMenu.setUserId(userId);
		siteNavigationMenu.setUserName(user.getFullName());
		siteNavigationMenu.setType(type);

		return siteNavigationMenuPersistence.update(siteNavigationMenu);
	}

	@Override
	public SiteNavigationMenu updateSiteNavigationMenu(
			long userId, long siteNavigationMenuId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		validate(name);

		User user = userLocalService.getUser(userId);

		SiteNavigationMenu siteNavigationMenu = getSiteNavigationMenu(
			siteNavigationMenuId);

		siteNavigationMenu.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		siteNavigationMenu.setUserId(userId);
		siteNavigationMenu.setUserName(user.getFullName());
		siteNavigationMenu.setName(name);

		return siteNavigationMenuPersistence.update(siteNavigationMenu);
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new SiteNavigationMenuNameException();
		}
	}

	protected void validatePrimarySiteNavigationMenu(
			SiteNavigationMenu siteNavigationMenu)
		throws PrimarySiteNavigationMenuException {

		SiteNavigationMenu primarySiteNavigationMenu =
			fetchPrimarySiteNavigationMenu(siteNavigationMenu.getGroupId());

		if (primarySiteNavigationMenu == null) {
			return;
		}

		if (primarySiteNavigationMenu.getSiteNavigationMenuId() !=
				siteNavigationMenu.getSiteNavigationMenuId()) {

			throw new PrimarySiteNavigationMenuException();
		}
	}

	private void _addSiteNavigationMenuItems(
			long userId, long groupId, long siteNavigationMenuId,
			long parentSiteNavigationMenuId, Layout layout,
			ServiceContext serviceContext)
		throws PortalException {

		Optional<SiteNavigationMenuItemType> optional = Optional.ofNullable(
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				layout.getType()));

		SiteNavigationMenuItemType siteNavigationMenuItemType = optional.orElse(
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				"layout"));

		if (siteNavigationMenuItemType == null) {
			return;
		}

		String typeSettings = layout.getTypeSettings();

		if (siteNavigationMenuItemType.getType().equals("layout")) {
			UnicodeProperties unicodeProperties = new UnicodeProperties();

			unicodeProperties.setProperty(
				"groupId", String.valueOf(layout.getGroupId()));
			unicodeProperties.setProperty("layoutUuid", layout.getUuid());
			unicodeProperties.setProperty(
				"privateLayout", String.valueOf(layout.isPrivateLayout()));

			typeSettings = unicodeProperties.toString();
		}

		SiteNavigationMenuItem siteNavigationMenuItem =
			siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				userId, groupId, siteNavigationMenuId,
				parentSiteNavigationMenuId,
				siteNavigationMenuItemType.getType(), typeSettings,
				serviceContext);

		List<Layout> children = layoutLocalService.getLayouts(
			groupId, false, layout.getLayoutId());

			for (Layout childLayout : children) {
				_addSiteNavigationMenuItems(
					userId, groupId, siteNavigationMenuId,
					siteNavigationMenuItem.getSiteNavigationMenuItemId(),
					childLayout, serviceContext);

		}
	}

	@ServiceReference(type = SiteNavigationMenuItemTypeRegistry.class)
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

}