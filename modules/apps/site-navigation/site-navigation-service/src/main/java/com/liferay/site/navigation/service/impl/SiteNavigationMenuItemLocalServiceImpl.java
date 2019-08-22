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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.navigation.exception.InvalidSiteNavigationMenuItemOrderException;
import com.liferay.site.navigation.exception.InvalidSiteNavigationMenuItemTypeException;
import com.liferay.site.navigation.exception.SiteNavigationMenuItemNameException;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.base.SiteNavigationMenuItemLocalServiceBaseImpl;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuItemOrderComparator;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.site.navigation.model.SiteNavigationMenuItem",
	service = AopService.class
)
public class SiteNavigationMenuItemLocalServiceImpl
	extends SiteNavigationMenuItemLocalServiceBaseImpl {

	@Override
	public SiteNavigationMenuItem addSiteNavigationMenuItem(
			long userId, long groupId, long siteNavigationMenuId,
			long parentSiteNavigationMenuItemId, String type, int order,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				type);

		if (siteNavigationMenuItemType == null) {
			throw new InvalidSiteNavigationMenuItemTypeException(type);
		}

		String name = siteNavigationMenuItemType.getName(typeSettings);

		validateName(name);

		User user = userLocalService.getUser(userId);

		long siteNavigationMenuItemId = counterLocalService.increment();

		SiteNavigationMenuItem siteNavigationMenuItem =
			siteNavigationMenuItemPersistence.create(siteNavigationMenuItemId);

		siteNavigationMenuItem.setUuid(serviceContext.getUuid());
		siteNavigationMenuItem.setGroupId(groupId);
		siteNavigationMenuItem.setCompanyId(user.getCompanyId());
		siteNavigationMenuItem.setUserId(userId);
		siteNavigationMenuItem.setUserName(user.getFullName());
		siteNavigationMenuItem.setSiteNavigationMenuId(siteNavigationMenuId);
		siteNavigationMenuItem.setParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId);
		siteNavigationMenuItem.setName(name);
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
			siteNavigationMenuItemPersistence.countByS_P(
				siteNavigationMenuId, parentSiteNavigationMenuItemId);

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
			getSiteNavigationMenuItems(
				siteNavigationMenuItem.getSiteNavigationMenuId(),
				siteNavigationMenuItemId);

		if (!siteNavigationMenuItems.isEmpty()) {
			List<SiteNavigationMenuItem> siblingsSiteNavigationMenuItems =
				getSiteNavigationMenuItems(
					siteNavigationMenuItem.getSiteNavigationMenuId(),
					siteNavigationMenuItem.getParentSiteNavigationMenuItemId());

			for (SiteNavigationMenuItem siblingSiteNavigationMenuItem :
					siblingsSiteNavigationMenuItems) {

				if (siblingSiteNavigationMenuItem.getOrder() <=
						siteNavigationMenuItem.getOrder()) {

					continue;
				}

				siblingSiteNavigationMenuItem.setOrder(
					siteNavigationMenuItems.size() +
						siteNavigationMenuItem.getOrder());

				siteNavigationMenuItemPersistence.update(
					siblingSiteNavigationMenuItem);
			}
		}

		for (int i = 0; i < siteNavigationMenuItems.size(); i++) {
			SiteNavigationMenuItem childSiteNavigationMenuItem =
				siteNavigationMenuItems.get(i);

			childSiteNavigationMenuItem.setParentSiteNavigationMenuItemId(
				siteNavigationMenuItem.getParentSiteNavigationMenuItemId());
			childSiteNavigationMenuItem.setOrder(
				siteNavigationMenuItem.getOrder() + i);

			siteNavigationMenuItemPersistence.update(
				childSiteNavigationMenuItem);
		}

		return siteNavigationMenuItemLocalService.deleteSiteNavigationMenuItem(
			siteNavigationMenuItem);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SiteNavigationMenuItem deleteSiteNavigationMenuItem(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		return siteNavigationMenuItemPersistence.remove(siteNavigationMenuItem);
	}

	@Override
	public void deleteSiteNavigationMenuItems(long siteNavigationMenuId) {
		siteNavigationMenuItemPersistence.removeBySiteNavigationMenuId(
			siteNavigationMenuId);
	}

	@Override
	public void deleteSiteNavigationMenuItemsByGroupId(long groupId) {
		List<SiteNavigationMenu> siteNavigationMenus =
			siteNavigationMenuPersistence.findByGroupId(groupId);

		for (SiteNavigationMenu siteNavigationMenu : siteNavigationMenus) {
			siteNavigationMenuItemPersistence.removeBySiteNavigationMenuId(
				siteNavigationMenu.getSiteNavigationMenuId());
		}
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
	public int getSiteNavigationMenuItemsCount(long siteNavigationMenuId) {
		return siteNavigationMenuItemPersistence.countBySiteNavigationMenuId(
			siteNavigationMenuId);
	}

	@Override
	public SiteNavigationMenuItem updateSiteNavigationMenuItem(
			long siteNavigationMenuItemId, long parentSiteNavigationMenuItemId,
			int order)
		throws PortalException {

		// Site navigation menu item

		SiteNavigationMenuItem siteNavigationMenuItem =
			siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItemId);

		validate(
			siteNavigationMenuItem.getSiteNavigationMenuId(),
			siteNavigationMenuItemId, parentSiteNavigationMenuItemId);

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
			long userId, long siteNavigationMenuItemId, long groupId,
			long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
			String type, int order, String typeSettings)
		throws PortalException {

		SiteNavigationMenuItem siteNavigationMenuItem =
			siteNavigationMenuItemPersistence.findByPrimaryKey(
				siteNavigationMenuItemId);

		siteNavigationMenuItem.setGroupId(groupId);
		siteNavigationMenuItem.setUserId(userId);
		siteNavigationMenuItem.setSiteNavigationMenuId(siteNavigationMenuId);
		siteNavigationMenuItem.setParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId);
		siteNavigationMenuItem.setType(type);
		siteNavigationMenuItem.setTypeSettings(typeSettings);
		siteNavigationMenuItem.setOrder(order);

		return siteNavigationMenuItemPersistence.update(siteNavigationMenuItem);
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

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				siteNavigationMenuItem.getType());

		if (siteNavigationMenuItemType == null) {
			throw new InvalidSiteNavigationMenuItemTypeException(
				siteNavigationMenuItem.getType());
		}

		String name = siteNavigationMenuItemType.getName(typeSettings);

		validateName(name);

		validateLayout(typeSettings);

		siteNavigationMenuItem.setUserId(userId);
		siteNavigationMenuItem.setUserName(user.getFullName());
		siteNavigationMenuItem.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		siteNavigationMenuItem.setName(name);
		siteNavigationMenuItem.setTypeSettings(typeSettings);

		siteNavigationMenuItemPersistence.update(siteNavigationMenuItem);

		return siteNavigationMenuItem;
	}

	protected void validate(
			long siteNavigationMenuId, long siteNavigationMenuItemId,
			long parentSiteNavigationMenuItemId)
		throws PortalException {

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			getSiteNavigationMenuItems(
				siteNavigationMenuId, siteNavigationMenuItemId);

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			siteNavigationMenuItemId =
				siteNavigationMenuItem.getSiteNavigationMenuItemId();

			if (siteNavigationMenuItemId == parentSiteNavigationMenuItemId) {
				throw new InvalidSiteNavigationMenuItemOrderException();
			}

			validate(
				siteNavigationMenuId, siteNavigationMenuItemId,
				parentSiteNavigationMenuItemId);
		}
	}

	protected void validateLayout(String typeSettings) throws PortalException {
		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		String layoutUuid = typeSettingsProperties.getProperty("layoutUuid");

		if (Validator.isNull(layoutUuid)) {
			return;
		}

		long groupId = GetterUtil.getLong(
			typeSettingsProperties.getProperty("groupId"));
		boolean privateLayout = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("privateLayout"));

		_layoutService.getLayoutByUuidAndGroupId(
			layoutUuid, groupId, privateLayout);
	}

	protected void validateName(String name) throws PortalException {
		if (name == null) {
			return;
		}

		int maxLength = ModelHintsUtil.getMaxLength(
			SiteNavigationMenuItem.class.getName(), "name");

		if (name.length() > maxLength) {
			throw new SiteNavigationMenuItemNameException(
				"Maximum length of name exceeded");
		}
	}

	@Reference
	private LayoutService _layoutService;

	@Reference
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

}