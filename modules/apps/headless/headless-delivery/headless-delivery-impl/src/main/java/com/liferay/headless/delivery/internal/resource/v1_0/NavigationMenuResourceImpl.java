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

import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.delivery.dto.v1_0.NavigationMenu;
import com.liferay.headless.delivery.dto.v1_0.NavigationMenuItem;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.resource.v1_0.NavigationMenuResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
import com.liferay.site.navigation.service.SiteNavigationMenuService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

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
	public void deleteNavigationMenu(Long navigationMenuId) throws Exception {
		_siteNavigationMenuService.deleteSiteNavigationMenu(navigationMenuId);
	}

	@Override
	public NavigationMenu getNavigationMenu(Long navigationMenuId)
		throws Exception {

		return _toNavigationMenu(
			_siteNavigationMenuService.fetchSiteNavigationMenu(
				navigationMenuId));
	}

	@Override
	public Page<NavigationMenu> getSiteNavigationMenusPage(
		Long siteId, Pagination pagination) {

		return Page.of(
			transform(
				_siteNavigationMenuService.getSiteNavigationMenus(
					siteId, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toNavigationMenu),
			pagination,
			_siteNavigationMenuService.getSiteNavigationMenusCount(siteId));
	}

	@Override
	public NavigationMenu postSiteNavigationMenu(
			Long siteId, NavigationMenu navigationMenu)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuService.addSiteNavigationMenu(
				siteId, navigationMenu.getName(),
				SiteNavigationConstants.TYPE_DEFAULT, true,
				ServiceContextUtil.createServiceContext(siteId, null));

		_createNavigationMenuItems(
			Arrays.asList(navigationMenu.getNavigationMenuItems()), 0, siteId,
			siteNavigationMenu.getSiteNavigationMenuId());

		return _toNavigationMenu(siteNavigationMenu);
	}

	private void _createNavigationMenuItems(
		List<NavigationMenuItem> navigationMenuItems,
		long parentNavigationMenuId, long siteId, long siteNavigationMenuId) {

		navigationMenuItems.forEach(
			item -> {
				Layout layout = _getLayout(item.getLink(), siteId);

				try {
					SiteNavigationMenuItem siteNavigationMenuItem =
						_siteNavigationMenuItemService.
							addSiteNavigationMenuItem(
								siteId, siteNavigationMenuId,
								parentNavigationMenuId, "layout",
								_getNavigationMenuItemTypeSettings(layout),
								ServiceContextUtil.createServiceContext(
									siteId, null));

					if (_hasNavigationMenuItems(item)) {
						_createNavigationMenuItems(
							Arrays.asList(item.getNavigationMenuItems()),
							siteNavigationMenuItem.
								getSiteNavigationMenuItemId(),
							siteId, siteNavigationMenuId);
					}
				}
				catch (PortalException portalException) {
					throw new RuntimeException(
						"Not possible to add navigation menu item",
						portalException);
				}
			});
	}

	private Layout _getLayout(SiteNavigationMenuItem siteNavigationMenuItem) {
		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

		String layoutUuid = typeSettingsUnicodeProperties.get("layoutUuid");
		boolean privateLayout = GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.get("privateLayout"));

		return _layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, siteNavigationMenuItem.getGroupId(), privateLayout);
	}

	private Layout _getLayout(String link, long siteId) {
		Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
			siteId, false, link);

		if (layout == null) {
			layout = _layoutLocalService.fetchLayoutByFriendlyURL(
				siteId, true, link);
		}

		if (layout == null) {
			throw new BadRequestException(
				"No page found with friendly URL " + link);
		}

		return layout;
	}

	private Locale _getLocaleFromProperty(Map.Entry<String, String> property) {
		return LocaleUtil.fromLanguageId(
			StringUtil.removeSubstring(property.getKey(), "name_"));
	}

	private Map<Locale, String> _getLocalizedNamesFromProperties(
		UnicodeProperties typeSettingsUnicodeProperties) {

		Set<Map.Entry<String, String>> properties =
			typeSettingsUnicodeProperties.entrySet();

		Stream<Map.Entry<String, String>> propertiesStream =
			properties.stream();

		return propertiesStream.filter(
			this::_isNameProperty
		).collect(
			Collectors.toMap(this::_getLocaleFromProperty, Map.Entry::getValue)
		);
	}

	private String _getNavigationMenuItemTypeSettings(Layout layout) {
		UnicodeProperties navigationMenuItemUnicodeProperties =
			new UnicodeProperties(true);

		navigationMenuItemUnicodeProperties.setProperty(
			"groupId", String.valueOf(layout.getGroupId()));
		navigationMenuItemUnicodeProperties.setProperty(
			"layoutUuid", layout.getUuid());
		navigationMenuItemUnicodeProperties.setProperty(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));
		navigationMenuItemUnicodeProperties.setProperty(
			"name", layout.getName(LocaleUtil.getDefault()));

		return navigationMenuItemUnicodeProperties.toString();
	}

	private Map<Long, List<SiteNavigationMenuItem>>
		_getSiteNavigationMenuItemsMap(
			List<SiteNavigationMenuItem> siteNavigationMenuItems) {

		Map<Long, List<SiteNavigationMenuItem>> siteNavigationMenuItemsMap =
			new HashMap<>();

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			long parentSiteNavigationMenuItemId =
				siteNavigationMenuItem.getParentSiteNavigationMenuItemId();

			if (siteNavigationMenuItemsMap.containsKey(
					parentSiteNavigationMenuItemId)) {

				continue;
			}

			for (SiteNavigationMenuItem childSiteNavigationMenuItem :
					siteNavigationMenuItems) {

				if (parentSiteNavigationMenuItemId !=
						childSiteNavigationMenuItem.
							getParentSiteNavigationMenuItemId()) {

					continue;
				}

				List<SiteNavigationMenuItem> parentSiteNavigationMenuItems =
					siteNavigationMenuItemsMap.getOrDefault(
						parentSiteNavigationMenuItemId, new ArrayList<>());

				parentSiteNavigationMenuItems.add(childSiteNavigationMenuItem);

				siteNavigationMenuItemsMap.put(
					parentSiteNavigationMenuItemId,
					parentSiteNavigationMenuItems);
			}
		}

		return siteNavigationMenuItemsMap;
	}

	private boolean _hasNavigationMenuItems(
		NavigationMenuItem siteNavigationMenuItem) {

		return ArrayUtil.isNotEmpty(
			siteNavigationMenuItem.getNavigationMenuItems());
	}

	private boolean _isNameProperty(Map.Entry<String, String> property) {
		String propertyKey = property.getKey();

		return propertyKey.startsWith("name_");
	}

	private NavigationMenu _toNavigationMenu(
			SiteNavigationMenu siteNavigationMenu)
		throws Exception {

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				siteNavigationMenu.getSiteNavigationMenuId());

		Map<Long, List<SiteNavigationMenuItem>> siteNavigationMenuItemsMap =
			_getSiteNavigationMenuItemsMap(siteNavigationMenuItems);

		return new NavigationMenu() {
			{
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.fetchUser(
						siteNavigationMenu.getUserId()));
				dateCreated = siteNavigationMenu.getCreateDate();
				dateModified = siteNavigationMenu.getModifiedDate();
				id = siteNavigationMenu.getSiteNavigationMenuId();
				name = siteNavigationMenu.getName();
				navigationMenuItems = transformToArray(
					siteNavigationMenuItemsMap.getOrDefault(
						0L, new ArrayList<>()),
					siteNavigationMenuItem -> _toNavigationMenuItem(
						siteNavigationMenuItem, siteNavigationMenuItemsMap),
					NavigationMenuItem.class);
				siteId = siteNavigationMenu.getGroupId();
			}
		};
	}

	private NavigationMenuItem _toNavigationMenuItem(
			SiteNavigationMenuItem siteNavigationMenuItem,
			Map<Long, List<SiteNavigationMenuItem>> siteNavigationMenuItemsMap)
		throws Exception {

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

		Layout layout = _getLayout(siteNavigationMenuItem);

		return new NavigationMenuItem() {
			{
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.fetchUser(
						siteNavigationMenuItem.getUserId()));
				dateCreated = siteNavigationMenuItem.getCreateDate();
				dateModified = siteNavigationMenuItem.getModifiedDate();
				navigationMenuItems = transformToArray(
					siteNavigationMenuItemsMap.getOrDefault(
						siteNavigationMenuItem.getSiteNavigationMenuItemId(),
						new ArrayList<>()),
					item -> _toNavigationMenuItem(
						item, siteNavigationMenuItemsMap),
					NavigationMenuItem.class);
				type = _toType(siteNavigationMenuItem.getType());
				url = typeSettingsUnicodeProperties.getProperty("url");

				setLink(
					() -> {
						if (layout == null) {
							return null;
						}

						return layout.getFriendlyURL();
					});
				setName(
					() -> {
						String preferredLanguageId =
							contextAcceptLanguage.getPreferredLanguageId();
						String defaultLanguageId = LocaleUtil.toLanguageId(
							LocaleUtil.getDefault());

						String name = typeSettingsUnicodeProperties.getProperty(
							"name_" + preferredLanguageId,
							typeSettingsUnicodeProperties.getProperty(
								"name_" + defaultLanguageId));

						if (name == null) {
							name = layout.getName(
								contextAcceptLanguage.getPreferredLocale());
						}

						return name;
					});
				setName_i18n(
					() -> {
						if (contextAcceptLanguage.isAcceptAllLanguages()) {
							Map<Locale, String> localizedNames =
								_getLocalizedNamesFromProperties(
									typeSettingsUnicodeProperties);

							if (localizedNames.isEmpty() && (layout != null)) {
								localizedNames = layout.getNameMap();
							}

							return LocalizedMapUtil.getI18nMap(
								true, localizedNames);
						}

						return null;
					});
			}
		};
	}

	private String _toType(String siteNavigationMenuItem) {
		if (siteNavigationMenuItem.equals("layout")) {
			return "page";
		}
		else if (siteNavigationMenuItem.equals("node")) {
			return "navigationMenu";
		}

		return siteNavigationMenuItem;
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