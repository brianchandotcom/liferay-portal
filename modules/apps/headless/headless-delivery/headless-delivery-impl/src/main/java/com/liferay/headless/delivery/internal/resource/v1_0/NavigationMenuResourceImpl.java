/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.headless.delivery.dto.v1_0.NavigationMenu;
import com.liferay.headless.delivery.dto.v1_0.NavigationMenuItem;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.resource.v1_0.NavigationMenuResource;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.JaxRsLinkUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.site.navigation.constants.SiteNavigationActionKeys;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
import com.liferay.site.navigation.service.SiteNavigationMenuService;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuItemOrderComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
			HashMapBuilder.put(
				"create",
				addAction(
					SiteNavigationActionKeys.ADD_SITE_NAVIGATION_MENU,
					"postSiteNavigationMenu",
					SiteNavigationConstants.RESOURCE_NAME, siteId)
			).put(
				"createBatch",
				addAction(
					SiteNavigationActionKeys.ADD_SITE_NAVIGATION_MENU,
					"postSiteNavigationMenuBatch",
					SiteNavigationConstants.RESOURCE_NAME, siteId)
			).put(
				"deleteBatch",
				addAction(
					ActionKeys.DELETE, "deleteNavigationMenuBatch",
					SiteNavigationConstants.RESOURCE_NAME, null)
			).put(
				"updateBatch",
				addAction(
					ActionKeys.UPDATE, "putNavigationMenuBatch",
					SiteNavigationConstants.RESOURCE_NAME, null)
			).build(),
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
				ServiceContextBuilder.create(
					siteId, contextHttpServletRequest, null
				).build());

		_createNavigationMenuItems(
			navigationMenu.getNavigationMenuItems(), 0, siteId,
			siteNavigationMenu.getSiteNavigationMenuId());

		return _toNavigationMenu(siteNavigationMenu);
	}

	@Override
	public NavigationMenu putNavigationMenu(
			Long navigationMenuId, NavigationMenu navigationMenu)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuService.fetchSiteNavigationMenu(
				navigationMenuId);

		_updateNavigationMenuItems(
			navigationMenu.getNavigationMenuItems(), 0,
			siteNavigationMenu.getGroupId(),
			siteNavigationMenu.getSiteNavigationMenuId());

		ServiceContext serviceContext = ServiceContextBuilder.create(
			siteNavigationMenu.getGroupId(), contextHttpServletRequest, null
		).build();

		NavigationMenu.NavigationType navigationType =
			navigationMenu.getNavigationType();

		if (navigationType != null) {
			_siteNavigationMenuService.updateSiteNavigationMenu(
				navigationMenuId, navigationType.ordinal() + 1, true,
				serviceContext);
		}

		return _toNavigationMenu(
			_siteNavigationMenuService.updateSiteNavigationMenu(
				navigationMenuId, navigationMenu.getName(), serviceContext));
	}

	@Override
	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuService.fetchSiteNavigationMenu((Long)id);

		return siteNavigationMenu.getGroupId();
	}

	@Override
	protected String getPermissionCheckerPortletName(Object id) {
		return SiteNavigationConstants.RESOURCE_NAME;
	}

	@Override
	protected String getPermissionCheckerResourceName(Object id) {
		return SiteNavigationMenu.class.getName();
	}

	private void _createNavigationMenuItem(
			NavigationMenuItem navigationMenuItem, long parentNavigationMenuId,
			long siteId, long siteNavigationMenuId)
		throws Exception {

		String unicodeProperties = _getUnicodeProperties(
			true, navigationMenuItem, siteId, null);

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemService.addSiteNavigationMenuItem(
				siteId, siteNavigationMenuId, parentNavigationMenuId,
				_getType(navigationMenuItem), unicodeProperties,
				ServiceContextBuilder.create(
					siteId, contextHttpServletRequest, null
				).build());

		_createNavigationMenuItems(
			navigationMenuItem.getNavigationMenuItems(),
			siteNavigationMenuItem.getSiteNavigationMenuItemId(), siteId,
			siteNavigationMenuId);
	}

	private void _createNavigationMenuItems(
			NavigationMenuItem[] navigationMenuItems,
			long parentNavigationMenuId, long siteId, long siteNavigationMenuId)
		throws Exception {

		if (navigationMenuItems == null) {
			return;
		}

		for (NavigationMenuItem navigationMenuItem : navigationMenuItems) {
			_createNavigationMenuItem(
				navigationMenuItem, parentNavigationMenuId, siteId,
				siteNavigationMenuId);
		}
	}

	private Layout _getLayout(SiteNavigationMenuItem siteNavigationMenuItem) {
		UnicodeProperties unicodeProperties = _getUnicodeProperties(
			siteNavigationMenuItem);

		String layoutUuid = unicodeProperties.get("layoutUuid");
		boolean privateLayout = GetterUtil.getBoolean(
			unicodeProperties.get("privateLayout"));

		return _layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, siteNavigationMenuItem.getGroupId(), privateLayout);
	}

	private Layout _getLayout(String link, long siteId) throws Exception {
		Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
			siteId, false, link);

		if (layout == null) {
			layout = _layoutLocalService.getLayoutByFriendlyURL(
				siteId, true, link);
		}

		return layout;
	}

	private Locale _getLocaleFromProperty(Map.Entry<String, String> property) {
		return LocaleUtil.fromLanguageId(
			StringUtil.removeSubstring(property.getKey(), "name_"));
	}

	private Map<Locale, String> _getLocalizedNamesFromProperties(
		UnicodeProperties unicodeProperties) {

		if (unicodeProperties == null) {
			return new HashMap<>();
		}

		Map<Locale, String> properties = new HashMap<>();

		for (Map.Entry<String, String> entry : unicodeProperties.entrySet()) {
			if (!_isNameProperty(entry)) {
				continue;
			}

			properties.put(_getLocaleFromProperty(entry), entry.getValue());
		}

		return properties;
	}

	private String _getName(
			String type, UnicodeProperties unicodeProperties,
			boolean useCustomName)
		throws JSONException {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		if (useCustomName) {
			JSONObject customNameJSONObject = _jsonFactory.createJSONObject(
				unicodeProperties.getProperty("localizedNames"));

			return customNameJSONObject.getString(defaultLanguageId);
		}

		if (StringUtil.equals(type, "url")) {
			String preferredLanguageId =
				contextAcceptLanguage.getPreferredLanguageId();

			return unicodeProperties.getProperty(
				"name_" + preferredLanguageId,
				unicodeProperties.getProperty("name_" + defaultLanguageId));
		}

		return unicodeProperties.getProperty("title");
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

	private String _getType(NavigationMenuItem navigationMenuItem) {
		if (navigationMenuItem.getLink() != null) {
			return "layout";
		}
		else if (navigationMenuItem.getUrl() != null) {
			return "url";
		}

		return "node";
	}

	private String _getUnicodeProperties(
			boolean add, NavigationMenuItem navigationMenuItem, long siteId,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception {

		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		if (navigationMenuItem.getLink() != null) {
			unicodeProperties.setProperty(
				"defaultLanguageId",
				LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

			Layout layout = _getLayout(navigationMenuItem.getLink(), siteId);

			unicodeProperties.setProperty(
				"groupId", String.valueOf(layout.getGroupId()));
			unicodeProperties.setProperty("layoutUuid", layout.getUuid());

			Map<Locale, String> nameMap = LocalizedMapUtil.getLocalizedMap(
				contextAcceptLanguage.getPreferredLocale(),
				navigationMenuItem.getName(), navigationMenuItem.getName_i18n(),
				_getLocalizedNamesFromProperties(
					_getUnicodeProperties(siteNavigationMenuItem)));

			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				unicodeProperties.setProperty(
					"name_" + LocaleUtil.toLanguageId(entry.getKey()),
					nameMap.get(entry.getKey()));
			}

			unicodeProperties.setProperty(
				"privateLayout", String.valueOf(layout.isPrivateLayout()));
			unicodeProperties.setProperty(
				"useCustomName",
				String.valueOf(navigationMenuItem.getUseCustomName()));
		}
		else {
			Map<Locale, String> nameMap = LocalizedMapUtil.getLocalizedMap(
				contextAcceptLanguage.getPreferredLocale(),
				navigationMenuItem.getName(), navigationMenuItem.getName_i18n(),
				_getLocalizedNamesFromProperties(
					_getUnicodeProperties(siteNavigationMenuItem)));

			LocalizedMapUtil.validateI18n(
				add, LocaleUtil.getSiteDefault(), "Navigation Menu item",
				nameMap, new HashSet<>());

			unicodeProperties.setProperty(
				"defaultLanguageId",
				LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				unicodeProperties.setProperty(
					"name_" + LocaleUtil.toLanguageId(entry.getKey()),
					nameMap.get(entry.getKey()));
			}

			if (navigationMenuItem.getUrl() != null) {
				unicodeProperties.setProperty(
					"url", navigationMenuItem.getUrl());
			}
		}

		return unicodeProperties.toString();
	}

	private UnicodeProperties _getUnicodeProperties(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		if (siteNavigationMenuItem == null) {
			return null;
		}

		return UnicodePropertiesBuilder.fastLoad(
			siteNavigationMenuItem.getTypeSettings()
		).build();
	}

	private boolean _isNameProperty(Map.Entry<String, String> property) {
		String propertyKey = property.getKey();

		return propertyKey.startsWith("name_");
	}

	private NavigationMenu _toNavigationMenu(
		SiteNavigationMenu siteNavigationMenu) {

		Map<Long, List<SiteNavigationMenuItem>> siteNavigationMenuItemsMap =
			_getSiteNavigationMenuItemsMap(
				_siteNavigationMenuItemService.getSiteNavigationMenuItems(
					siteNavigationMenu.getSiteNavigationMenuId(),
					new SiteNavigationMenuItemOrderComparator()));

		return new NavigationMenu() {
			{
				creator = CreatorUtil.toCreator(
					_portal, contextUriInfo,
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

				setActions(
					() -> HashMapBuilder.put(
						"delete",
						addAction(
							ActionKeys.DELETE, siteNavigationMenu,
							"deleteNavigationMenu")
					).put(
						"replace",
						addAction(
							ActionKeys.UPDATE, siteNavigationMenu,
							"putNavigationMenu")
					).build());
				setNavigationType(
					() -> {
						if (siteNavigationMenu.getType() == 0) {
							return null;
						}

						return NavigationType.values()
							[siteNavigationMenu.getType() - 1];
					});
			}
		};
	}

	private NavigationMenuItem _toNavigationMenuItem(
		SiteNavigationMenuItem siteNavigationMenuItem,
		Map<Long, List<SiteNavigationMenuItem>> siteNavigationMenuItemsMap) {

		Layout layout = _getLayout(siteNavigationMenuItem);

		UnicodeProperties unicodeProperties = _getUnicodeProperties(
			siteNavigationMenuItem);

		Map<Locale, String> localizedMap = _getLocalizedNamesFromProperties(
			unicodeProperties);

		return new NavigationMenuItem() {
			{
				creator = CreatorUtil.toCreator(
					_portal, contextUriInfo,
					_userLocalService.fetchUser(
						siteNavigationMenuItem.getUserId()));
				dateCreated = siteNavigationMenuItem.getCreateDate();
				dateModified = siteNavigationMenuItem.getModifiedDate();
				id = siteNavigationMenuItem.getSiteNavigationMenuItemId();
				navigationMenuItems = transformToArray(
					siteNavigationMenuItemsMap.getOrDefault(
						siteNavigationMenuItem.getSiteNavigationMenuItemId(),
						new ArrayList<>()),
					item -> _toNavigationMenuItem(
						item, siteNavigationMenuItemsMap),
					NavigationMenuItem.class);
				type = _toType(siteNavigationMenuItem.getType());
				url = unicodeProperties.getProperty("url");

				useCustomName = Boolean.valueOf(
					unicodeProperties.getProperty("useCustomName"));

				setAvailableLanguages(
					() -> {
						Set<Locale> locales = localizedMap.keySet();

						return LocaleUtil.toW3cLanguageIds(
							locales.toArray(new Locale[localizedMap.size()]));
					});
				setContentURL(
					() -> {
						if (Objects.equals(type, FileEntry.class.getName())) {
							type = DLFileEntry.class.getName();
						}

						DTOConverter<?, ?> dtoConverter =
							_dtoConverterRegistry.getDTOConverter(type);

						if (dtoConverter == null) {
							return null;
						}

						return dtoConverter.getJaxRsLink(
							GetterUtil.getLong(
								unicodeProperties.getProperty("classPK")),
							contextUriInfo);
					});
				setLink(
					() -> {
						if (layout == null) {
							return null;
						}

						return layout.getFriendlyURL(
							contextAcceptLanguage.getPreferredLocale());
					});
				setLink_i18n(
					() -> {
						if ((layout == null) ||
							!contextAcceptLanguage.isAcceptAllLanguages()) {

							return null;
						}

						Map<String, String> i18nMap = new HashMap<>();

						List<LayoutFriendlyURL> layoutFriendlyURLs =
							_layoutFriendlyURLLocalService.
								getLayoutFriendlyURLs(layout.getPlid());

						for (LayoutFriendlyURL layoutFriendlyURL :
								layoutFriendlyURLs) {

							i18nMap.put(
								LocaleUtil.toBCP47LanguageId(
									layoutFriendlyURL.getLanguageId()),
								layoutFriendlyURL.getFriendlyURL());
						}

						return i18nMap;
					});
				setName(
					() -> {
						String name = _getName(
							type, unicodeProperties, useCustomName);

						if ((name == null) && (layout != null)) {
							return layout.getName(
								contextAcceptLanguage.getPreferredLocale());
						}

						return name;
					});
				setName_i18n(
					() -> {
						if (contextAcceptLanguage.isAcceptAllLanguages()) {
							Map<Locale, String> localizedNames =
								_getLocalizedNamesFromProperties(
									unicodeProperties);

							if (localizedNames.isEmpty() && (layout != null)) {
								localizedNames = layout.getNameMap();
							}

							return LocalizedMapUtil.getI18nMap(localizedNames);
						}

						return null;
					});
				setSitePageURL(
					() -> {
						if (layout == null) {
							return null;
						}

						List<Object> arguments = new ArrayList<>();

						arguments.add(layout.getGroupId());

						String friendlyURL = layout.getFriendlyURL(
							contextAcceptLanguage.getPreferredLocale());

						arguments.add(friendlyURL.substring(1));

						return JaxRsLinkUtil.getJaxRsLink(
							"headless-delivery", BaseSitePageResourceImpl.class,
							"getSiteSitePage", contextUriInfo,
							arguments.toArray(new Object[0]));
					});
				setType(
					() -> {
						DTOConverter<?, ?> dtoConverter =
							_dtoConverterRegistry.getDTOConverter(type);

						if (dtoConverter == null) {
							return type;
						}

						String contentType = dtoConverter.getContentType();

						return Character.toLowerCase(contentType.charAt(0)) +
							contentType.substring(1);
					});
			}
		};
	}

	private String _toType(String type) {
		if (type.equals("layout")) {
			return "page";
		}
		else if (type.equals("node")) {
			return "navigationMenu";
		}

		return type;
	}

	private void _updateNavigationMenuItems(
			NavigationMenuItem[] navigationMenuItems,
			long parentSiteNavigationMenuId, Long siteId,
			long siteNavigationMenuId)
		throws Exception {

		List<SiteNavigationMenuItem> siteNavigationMenuItems = new ArrayList<>(
			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				siteNavigationMenuId, parentSiteNavigationMenuId));

		if (navigationMenuItems != null) {
			for (NavigationMenuItem navigationMenuItem : navigationMenuItems) {
				Long navigationMenuItemId = navigationMenuItem.getId();

				SiteNavigationMenuItem siteNavigationMenuItem = null;

				for (SiteNavigationMenuItem curSiteNavigationMenuItem :
						siteNavigationMenuItems) {

					if (Objects.equals(
							navigationMenuItemId,
							curSiteNavigationMenuItem.
								getSiteNavigationMenuItemId())) {

						siteNavigationMenuItem = curSiteNavigationMenuItem;

						break;
					}
				}

				if (siteNavigationMenuItem != null) {
					SiteNavigationMenuItem updatedSiteNavigationMenuItem =
						_siteNavigationMenuItemService.
							updateSiteNavigationMenuItem(
								navigationMenuItemId,
								_getUnicodeProperties(
									false, navigationMenuItem, siteId,
									siteNavigationMenuItem),
								ServiceContextBuilder.create(
									siteId, contextHttpServletRequest, null
								).build());

					_updateNavigationMenuItems(
						navigationMenuItem.getNavigationMenuItems(),
						updatedSiteNavigationMenuItem.
							getSiteNavigationMenuItemId(),
						siteId, siteNavigationMenuId);

					siteNavigationMenuItems.remove(siteNavigationMenuItem);
				}
				else {
					_createNavigationMenuItem(
						navigationMenuItem, parentSiteNavigationMenuId, siteId,
						siteNavigationMenuId);
				}
			}
		}

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
				siteNavigationMenuItem.getSiteNavigationMenuItemId());
		}
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

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