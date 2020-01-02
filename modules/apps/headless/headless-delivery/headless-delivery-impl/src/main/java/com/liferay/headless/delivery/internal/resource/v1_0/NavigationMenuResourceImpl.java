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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.NavigationMenu;
import com.liferay.headless.delivery.dto.v1_0.NavigationMenuItem;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.resource.v1_0.NavigationMenuResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
import com.liferay.site.navigation.service.SiteNavigationMenuService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/navigation-menu.properties",
	scope = ServiceScope.PROTOTYPE, service = NavigationMenuResource.class
)
public class NavigationMenuResourceImpl extends BaseNavigationMenuResourceImpl {

	@Override
	public NavigationMenu getNavigationMenu(Long navigationMenuId)
		throws Exception {

		return _toNavigationMenu(
			true,
			_siteNavigationMenuService.fetchSiteNavigationMenu(
				navigationMenuId));
	}

	@Override
	public Page<NavigationMenu> getSiteNavigationMenusPage(
		Long siteId, Pagination pagination) {

		MultivaluedMap<String, String> queryParameters =
			contextUriInfo.getQueryParameters();

		String nestedFields = queryParameters.getFirst("nestedFields");

		return Page.of(
			transform(
				_siteNavigationMenuService.getSiteNavigationMenus(
					siteId, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				siteNavigationMenu -> _toNavigationMenu(
					(nestedFields != null) &&
					nestedFields.contains("navigationMenuItems"),
					siteNavigationMenu)),
			pagination,
			_siteNavigationMenuService.getSiteNavigationMenusCount(siteId));
	}

	private Layout _getLayout(SiteNavigationMenuItem siteNavigationMenuItem) {
		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

		String layoutUuid = typeSettingsProperties.get("layoutUuid");

		boolean privateLayout = GetterUtil.getBoolean(
			typeSettingsProperties.get("privateLayout"));

		return _layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, siteNavigationMenuItem.getGroupId(), privateLayout);
	}

	private Map<Long, List<SiteNavigationMenuItem>>
		_getSiteNavigationMenuItemsMap(
			List<SiteNavigationMenuItem> siteNavigationMenuItems) {

		Map<Long, List<SiteNavigationMenuItem>> siteNavigationMenuItemsMap =
			new HashMap<>();

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			long parentId =
				siteNavigationMenuItem.getParentSiteNavigationMenuItemId();

			if (siteNavigationMenuItemsMap.containsKey(parentId)) {
				continue;
			}

			for (SiteNavigationMenuItem childSiteNavigationMenuItem :
					siteNavigationMenuItems) {

				if (parentId ==
						childSiteNavigationMenuItem.
							getParentSiteNavigationMenuItemId()) {

					List<SiteNavigationMenuItem> list =
						siteNavigationMenuItemsMap.getOrDefault(
							parentId, new ArrayList<>());

					list.add(childSiteNavigationMenuItem);

					siteNavigationMenuItemsMap.put(parentId, list);
				}
			}
		}

		return siteNavigationMenuItemsMap;
	}

	private String _getType(String siteNavigationMenuItem) {
		if (siteNavigationMenuItem.equals("layout")) {
			return "page";
		}
		else if (siteNavigationMenuItem.equals("node")) {
			return "navigationMenuItem";
		}

		return siteNavigationMenuItem;
	}

	private NavigationMenu _toNavigationMenu(
			boolean nestNavigationMenuItems,
			SiteNavigationMenu siteNavigationMenu)
		throws PortalException {

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				siteNavigationMenu.getSiteNavigationMenuId());

		Map<Long, List<SiteNavigationMenuItem>> siteNavigationMenuItemsMap =
			_getSiteNavigationMenuItemsMap(siteNavigationMenuItems);

		return new NavigationMenu() {
			{
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(siteNavigationMenu.getUserId()));
				dateCreated = siteNavigationMenu.getCreateDate();
				dateModified = siteNavigationMenu.getModifiedDate();
				id = siteNavigationMenu.getSiteNavigationMenuId();
				name = siteNavigationMenu.getName();

				if (nestNavigationMenuItems) {
					navigationMenuItems = transformToArray(
						siteNavigationMenuItemsMap.getOrDefault(
							0L, new ArrayList<>()),
						siteNavigationMenuItem -> _toNavigationMenuItem(
							nestNavigationMenuItems, siteNavigationMenuItem,
							siteNavigationMenuItemsMap),
						NavigationMenuItem.class);
				}

				siteId = siteNavigationMenu.getGroupId();
			}
		};
	}

	private NavigationMenuItem _toNavigationMenuItem(
			boolean nestNavigationMenuItems,
			SiteNavigationMenuItem siteNavigationMenuItem,
			Map<Long, List<SiteNavigationMenuItem>> siteNavigationMenuItemsMap)
		throws PortalException {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

		Layout layout = _getLayout(siteNavigationMenuItem);

		return new NavigationMenuItem() {
			{
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(
						siteNavigationMenuItem.getUserId()));
				dateCreated = siteNavigationMenuItem.getCreateDate();
				dateModified = siteNavigationMenuItem.getModifiedDate();
				id = siteNavigationMenuItem.getSiteNavigationMenuId();
				name = typeSettingsProperties.getProperty(
					"name_" + contextAcceptLanguage.getPreferredLanguageId(),
					typeSettingsProperties.getProperty(
						LocaleUtil.toLanguageId(LocaleUtil.getDefault())));
				navigationMenuItems = transformToArray(
					siteNavigationMenuItemsMap.getOrDefault(
						siteNavigationMenuItem.getSiteNavigationMenuItemId(),
						new ArrayList<>()),
					item -> _toNavigationMenuItem(
						nestNavigationMenuItems, item,
						siteNavigationMenuItemsMap),
					NavigationMenuItem.class);
				type = _getType(siteNavigationMenuItem.getType());
				url = typeSettingsProperties.getProperty("url");

				if (layout != null) {
					link = layout.getFriendlyURL();
					name = layout.getName(
						contextAcceptLanguage.getPreferredLocale());
				}
			}
		};
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

	@Reference
	private SiteNavigationMenuService _siteNavigationMenuService;

	@Reference
	private UserLocalService _userLocalService;

}