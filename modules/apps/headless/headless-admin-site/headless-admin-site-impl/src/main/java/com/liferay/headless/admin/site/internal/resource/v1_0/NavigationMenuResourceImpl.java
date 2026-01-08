/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.batch.engine.thread.local.BatchEngineThreadLocal;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.headless.admin.site.dto.v1_0.NavigationMenu;
import com.liferay.headless.admin.site.dto.v1_0.NavigationMenuItem;
import com.liferay.headless.admin.site.internal.odata.entity.v1_0.NavigationMenuEntityModel;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.GroupUtil;
import com.liferay.headless.admin.site.resource.v1_0.NavigationMenuResource;
import com.liferay.headless.admin.user.dto.v1_0.Creator;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PermissionService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.custom.field.CustomFieldsUtil;
import com.liferay.portal.vulcan.fields.NestedFieldsSupplier;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.Permission;
import com.liferay.portal.vulcan.permission.PermissionUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.constants.SiteNavigationActionKeys;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuItemOrderComparator;

import jakarta.ws.rs.core.MultivaluedMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
	property = "export.import.vulcan.batch.engine.task.item.delegate=true",
	scope = ServiceScope.PROTOTYPE, service = NavigationMenuResource.class
)
public class NavigationMenuResourceImpl
	extends BaseNavigationMenuResourceImpl
	implements ExportImportVulcanBatchEngineTaskItemDelegate<NavigationMenu> {

	@Override
	public void deleteSiteNavigationMenu(
			String siteExternalReferenceCode,
			String navigationMenuExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66179")) {

			throw new UnsupportedOperationException();
		}

		_siteNavigationMenuService.deleteSiteNavigationMenu(
			navigationMenuExternalReferenceCode,
			GroupUtil.getGroupId(
				true, contextCompany.getCompanyId(),
				siteExternalReferenceCode));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public ExportImportDescriptor getExportImportDescriptor() {
		return new ExportImportDescriptor() {

			@Override
			public String getLabelLanguageKey() {
				return "navigation-menus";
			}

			@Override
			public String getModelClassName() {
				return SiteNavigationMenu.class.getName();
			}

			@Override
			public String getPortletId() {
				return SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN;
			}

			@Override
			public String getResourceClassName() {
				return NavigationMenuResourceImpl.class.getName();
			}

			@Override
			public Scope getScope() {
				return Scope.SITE;
			}

			@Override
			public boolean isActive(PortletDataContext portletDataContext) {
				return FeatureFlagManagerUtil.isEnabled(
					portletDataContext.getCompanyId(), "LPD-66179");
			}

			@Override
			public boolean isStagingSupported() {
				return true;
			}

		};
	}

	@Override
	protected NavigationMenu doGetSiteNavigationMenu(
			String siteExternalReferenceCode,
			String navigationMenuExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66179")) {

			throw new UnsupportedOperationException();
		}

		return _toNavigationMenu(
			_siteNavigationMenuService.
				getSiteNavigationMenuByExternalReferenceCode(
					navigationMenuExternalReferenceCode,
					GroupUtil.getGroupId(
						true, contextCompany.getCompanyId(),
						siteExternalReferenceCode)));
	}

	@Override
	protected Page<NavigationMenu> doGetSiteNavigationMenusPage(
			String siteExternalReferenceCode, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66179")) {

			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			true, contextCompany.getCompanyId(), siteExternalReferenceCode);

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					SiteNavigationActionKeys.ADD_SITE_NAVIGATION_MENU,
					"postSiteNavigationMenu",
					SiteNavigationConstants.RESOURCE_NAME, groupId)
			).put(
				"createBatch",
				addAction(
					SiteNavigationActionKeys.ADD_SITE_NAVIGATION_MENU,
					"postSiteNavigationMenuBatch",
					SiteNavigationConstants.RESOURCE_NAME, groupId)
			).build(),
			booleanQuery -> {
			},
			filter, SiteNavigationMenu.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {groupId});
			},
			sorts,
			document -> _toNavigationMenu(
				_siteNavigationMenuService.fetchSiteNavigationMenu(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	protected NavigationMenu doPostSiteNavigationMenu(
			String siteExternalReferenceCode, NavigationMenu navigationMenu)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66179")) {

			throw new UnsupportedOperationException();
		}

		return _addNavigationMenu(
			navigationMenu.getExternalReferenceCode(),
			GroupUtil.getGroupId(
				true, contextCompany.getCompanyId(), siteExternalReferenceCode),
			navigationMenu);
	}

	@Override
	protected NavigationMenu doPutSiteNavigationMenu(
			String siteExternalReferenceCode,
			String navigationMenuExternalReferenceCode,
			NavigationMenu navigationMenu)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66179")) {

			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			true, contextCompany.getCompanyId(), siteExternalReferenceCode);

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.
				fetchSiteNavigationMenuByExternalReferenceCode(
					navigationMenuExternalReferenceCode, groupId);

		if (siteNavigationMenu != null) {
			return _updateNavigationMenu(navigationMenu, siteNavigationMenu);
		}

		return _addNavigationMenu(
			navigationMenuExternalReferenceCode, groupId, navigationMenu);
	}

	@Override
	protected Long getPermissionCheckerResourceId(
			String groupExternalReferenceCode, String externalReferenceCode)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuService.
				getSiteNavigationMenuByExternalReferenceCode(
					externalReferenceCode,
					getPermissionCheckerGroupId(groupExternalReferenceCode));

		return siteNavigationMenu.getPrimaryKey();
	}

	@Override
	protected String getPermissionCheckerResourceName(
		String groupExternalReferenceCode, String externalReferenceCode) {

		return SiteNavigationMenu.class.getName();
	}

	private NavigationMenu _addNavigationMenu(
			String externalReferenceCode, long groupId,
			NavigationMenu navigationMenu)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuService.addSiteNavigationMenu(
				externalReferenceCode, groupId, navigationMenu.getName(),
				_getNavigationMenuType(navigationMenu),
				_isAuto(navigationMenu.getAuto()),
				ServiceContextBuilder.create(
					groupId, contextHttpServletRequest, null
				).build());

		_createNavigationMenuItems(
			groupId, navigationMenu.getNavigationMenuItems(), 0,
			siteNavigationMenu.getSiteNavigationMenuId());

		return _toNavigationMenu(siteNavigationMenu);
	}

	private void _createNavigationMenuItem(
			long groupId, NavigationMenuItem navigationMenuItem,
			long parentNavigationMenuId, long siteNavigationMenuId)
		throws Exception {

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.putAll(
			navigationMenuItem.getTypeSettings()
		).build();

		if (!_hasModel(groupId, navigationMenuItem, unicodeProperties)) {
			throw new IllegalArgumentException(
				"Unable to find navigation menu item with external reference " +
					"code " +
						navigationMenuItem.getExternalReferenceCode());
		}

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemService.addSiteNavigationMenuItem(
				navigationMenuItem.getExternalReferenceCode(), groupId,
				siteNavigationMenuId, parentNavigationMenuId,
				navigationMenuItem.getType(), unicodeProperties.toString(),
				ServiceContextBuilder.create(
					groupId, contextHttpServletRequest, null
				).expandoBridgeAttributes(
					CustomFieldsUtil.toMap(
						SiteNavigationMenuItem.class.getName(),
						contextCompany.getCompanyId(),
						navigationMenuItem.getCustomFields(),
						contextAcceptLanguage.getPreferredLocale())
				).build());

		_createNavigationMenuItems(
			groupId, navigationMenuItem.getNavigationMenuItems(),
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			siteNavigationMenuId);
	}

	private void _createNavigationMenuItems(
			long groupId, NavigationMenuItem[] navigationMenuItems,
			long parentNavigationMenuId, long siteNavigationMenuId)
		throws Exception {

		if (navigationMenuItems == null) {
			return;
		}

		for (NavigationMenuItem navigationMenuItem : navigationMenuItems) {
			_createNavigationMenuItem(
				groupId, navigationMenuItem, parentNavigationMenuId,
				siteNavigationMenuId);
		}
	}

	private void _deleteNavigationMenuItems(
			List<SiteNavigationMenuItem> siteNavigationMenuItems)
		throws Exception {

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
				siteNavigationMenuItem.getSiteNavigationMenuItemId(), true);
		}
	}

	private Layout _getLayout(SiteNavigationMenuItem siteNavigationMenuItem) {
		UnicodeProperties unicodeProperties = _getUnicodeProperties(
			siteNavigationMenuItem);

		String externalReferenceCode = unicodeProperties.get(
			"externalReferenceCode");

		return _layoutLocalService.fetchLayoutByExternalReferenceCode(
			externalReferenceCode, siteNavigationMenuItem.getGroupId());
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
			Layout layout, String type, UnicodeProperties unicodeProperties,
			boolean useCustomName)
		throws JSONException {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());
		String preferredLanguageId =
			contextAcceptLanguage.getPreferredLanguageId();

		if (StringUtil.equals(type, "page")) {
			if (!useCustomName && (layout == null)) {
				return null;
			}

			if (!useCustomName && (layout != null)) {
				return layout.getName(
					contextAcceptLanguage.getPreferredLocale());
			}

			if (!useCustomName) {
				return null;
			}

			return unicodeProperties.getProperty(
				"name_" + preferredLanguageId,
				unicodeProperties.getProperty("name_" + defaultLanguageId));
		}

		if (useCustomName) {
			JSONObject customNameJSONObject = _jsonFactory.createJSONObject(
				unicodeProperties.getProperty("localizedNames"));

			return customNameJSONObject.getString(
				preferredLanguageId,
				customNameJSONObject.getString(defaultLanguageId));
		}

		if (StringUtil.equals(type, "navigationMenu") ||
			StringUtil.equals(type, "url")) {

			return unicodeProperties.getProperty(
				"name_" + preferredLanguageId,
				unicodeProperties.getProperty("name_" + defaultLanguageId));
		}

		return unicodeProperties.getProperty("title");
	}

	private int _getNavigationMenuType(NavigationMenu navigationMenu) {
		int type = SiteNavigationConstants.TYPE_DEFAULT;

		NavigationMenu.NavigationType navigationType =
			navigationMenu.getNavigationType();

		if (navigationType != null) {
			type = navigationType.ordinal() + 1;
		}

		return type;
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

	private UnicodeProperties _getUnicodeProperties(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		if (siteNavigationMenuItem == null) {
			return null;
		}

		return UnicodePropertiesBuilder.fastLoad(
			siteNavigationMenuItem.getTypeSettings()
		).build();
	}

	private boolean _hasModel(
			long groupId, NavigationMenuItem navigationMenuItem,
			UnicodeProperties unicodeProperties)
		throws Exception {

		if (BatchEngineThreadLocal.isBatchImportInProcess()) {
			return true;
		}

		String navigationMenuItemType = navigationMenuItem.getType();

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				navigationMenuItemType);

		return siteNavigationMenuItemType.hasModel(
			contextCompany.getCompanyId(), groupId, unicodeProperties);
	}

	private boolean _isAuto(Boolean auto) {
		if (auto == null) {
			return true;
		}

		return auto;
	}

	private boolean _isNameProperty(Map.Entry<String, String> entry) {
		String key = entry.getKey();

		return key.startsWith("name_");
	}

	private NavigationMenu _toNavigationMenu(
		SiteNavigationMenu siteNavigationMenu) {

		return new NavigationMenu() {
			{
				setActions(
					() -> HashMapBuilder.put(
						"delete",
						addAction(
							ActionKeys.DELETE, siteNavigationMenu,
							"deleteSiteNavigationMenu")
					).put(
						"replace",
						addAction(
							ActionKeys.UPDATE, siteNavigationMenu,
							"putSiteNavigationMenu")
					).build());
				setAuto(siteNavigationMenu::getAuto);
				setCreator(
					() -> {
						User user = _userLocalService.fetchUser(
							siteNavigationMenu.getUserId());

						if (user == null) {
							return null;
						}

						return new Creator() {
							{
								setExternalReferenceCode(
									user::getExternalReferenceCode);
							}
						};
					});
				setDateCreated(siteNavigationMenu::getCreateDate);
				setDateModified(siteNavigationMenu::getModifiedDate);
				setExternalReferenceCode(
					siteNavigationMenu::getExternalReferenceCode);
				setId(siteNavigationMenu::getSiteNavigationMenuId);
				setName(siteNavigationMenu::getName);
				setNavigationMenuItems(
					() -> {
						Map<Long, List<SiteNavigationMenuItem>>
							siteNavigationMenuItemsMap =
								_getSiteNavigationMenuItemsMap(
									_siteNavigationMenuItemService.
										getSiteNavigationMenuItems(
											siteNavigationMenu.
												getSiteNavigationMenuId(),
											SiteNavigationMenuItemOrderComparator.
												getInstance(true)));

						return transformToArray(
							siteNavigationMenuItemsMap.getOrDefault(
								0L, new ArrayList<>()),
							siteNavigationMenuItem -> _toNavigationMenuItem(
								siteNavigationMenuItem,
								siteNavigationMenuItemsMap),
							NavigationMenuItem.class);
					});
				setNavigationType(
					() -> {
						if (siteNavigationMenu.getType() == 0) {
							return null;
						}

						return NavigationType.values()
							[siteNavigationMenu.getType() - 1];
					});
				setPermissions(() -> _toPermissions(siteNavigationMenu));
				setSiteExternalReferenceCode(
					() -> {
						Group group = _groupLocalService.fetchGroup(
							siteNavigationMenu.getGroupId());

						if (group != null) {
							return group.getExternalReferenceCode();
						}

						return StringPool.BLANK;
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

		String navigationMenuItemType = _toType(
			siteNavigationMenuItem.getType());

		return new NavigationMenuItem() {
			{
				setAvailableLanguages(
					() -> {
						Map<Locale, String> localizedMap =
							_getLocalizedNamesFromProperties(unicodeProperties);

						Set<Locale> locales = localizedMap.keySet();

						return LocaleUtil.toW3cLanguageIds(
							locales.toArray(new Locale[localizedMap.size()]));
					});
				setCreator(
					() -> {
						User user = _userLocalService.fetchUser(
							siteNavigationMenuItem.getUserId());

						if (user == null) {
							return null;
						}

						return new Creator() {
							{
								setExternalReferenceCode(
									user::getExternalReferenceCode);
							}
						};
					});
				setCustomFields(
					() -> CustomFieldsUtil.toCustomFields(
						contextAcceptLanguage.isAcceptAllLanguages(),
						SiteNavigationMenuItem.class.getName(),
						siteNavigationMenuItem.getSiteNavigationMenuItemId(),
						siteNavigationMenuItem.getCompanyId(),
						contextAcceptLanguage.getPreferredLocale()));
				setDateCreated(siteNavigationMenuItem::getCreateDate);
				setDateModified(siteNavigationMenuItem::getModifiedDate);
				setExternalReferenceCode(
					siteNavigationMenuItem::getExternalReferenceCode);
				setId(siteNavigationMenuItem::getSiteNavigationMenuItemId);
				setName(
					() -> _getName(
						layout, navigationMenuItemType, unicodeProperties,
						getUseCustomName()));
				setName_i18n(
					() -> {
						if (!contextAcceptLanguage.isAcceptAllLanguages()) {
							return null;
						}

						Map<Locale, String> localizedNames =
							_getLocalizedNamesFromProperties(unicodeProperties);

						if ((!useCustomName || localizedNames.isEmpty()) &&
							(layout != null)) {

							localizedNames = layout.getNameMap();
						}

						return LocalizedMapUtil.getI18nMap(localizedNames);
					});
				setNavigationMenuItems(
					() -> transformToArray(
						siteNavigationMenuItemsMap.getOrDefault(
							siteNavigationMenuItem.
								getSiteNavigationMenuItemId(),
							new ArrayList<>()),
						item -> _toNavigationMenuItem(
							item, siteNavigationMenuItemsMap),
						NavigationMenuItem.class));
				setType(siteNavigationMenuItem::getType);
				setTypeSettings(() -> unicodeProperties);
				setUseCustomName(
					() -> Boolean.valueOf(
						unicodeProperties.getProperty("useCustomName")));
			}
		};
	}

	private Permission[] _toPermissions(SiteNavigationMenu siteNavigationMenu)
		throws Exception {

		return NestedFieldsSupplier.supply(
			"permissions",
			nestedFieldNames -> {
				_permissionService.checkPermission(
					siteNavigationMenu.getGroupId(),
					siteNavigationMenu.getModelClassName(),
					siteNavigationMenu.getSiteNavigationMenuId());

				Collection<Permission> permissions =
					PermissionUtil.getPermissions(
						siteNavigationMenu.getCompanyId(),
						_resourceActionLocalService.getResourceActions(
							siteNavigationMenu.getModelClassName()),
						siteNavigationMenu.getSiteNavigationMenuId(),
						siteNavigationMenu.getModelClassName(), null);

				return permissions.toArray(new Permission[0]);
			});
	}

	private String _toType(String type) {
		if (type.equals("layout")) {
			return "page";
		}
		else if (type.equals("node")) {
			return "navigationMenu";
		}
		else if (type.equals(FileEntry.class.getName())) {
			return DLFileEntry.class.getName();
		}

		return type;
	}

	private NavigationMenu _updateNavigationMenu(
			NavigationMenu navigationMenu,
			SiteNavigationMenu siteNavigationMenu)
		throws Exception {

		_updateNavigationMenuItems(
			siteNavigationMenu.getGroupId(),
			navigationMenu.getNavigationMenuItems(), 0,
			siteNavigationMenu.getSiteNavigationMenuId());

		ServiceContext serviceContext = ServiceContextBuilder.create(
			siteNavigationMenu.getGroupId(), contextHttpServletRequest, null
		).build();

		_siteNavigationMenuService.updateSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId(),
			_getNavigationMenuType(navigationMenu),
			_isAuto(navigationMenu.getAuto()), serviceContext);

		return _toNavigationMenu(
			_siteNavigationMenuService.updateSiteNavigationMenu(
				siteNavigationMenu.getSiteNavigationMenuId(),
				navigationMenu.getName(), serviceContext));
	}

	private void _updateNavigationMenuItems(
			long groupId, NavigationMenuItem[] navigationMenuItems,
			long parentSiteNavigationMenuItemId, long siteNavigationMenuId)
		throws Exception {

		List<SiteNavigationMenuItem> siteNavigationMenuItems = new ArrayList<>(
			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				siteNavigationMenuId, parentSiteNavigationMenuItemId));

		if (navigationMenuItems == null) {
			_deleteNavigationMenuItems(siteNavigationMenuItems);

			return;
		}

		for (NavigationMenuItem navigationMenuItem : navigationMenuItems) {
			String navigationMenuItemExternalReferenceCode =
				navigationMenuItem.getExternalReferenceCode();

			SiteNavigationMenuItem siteNavigationMenuItem = null;

			for (SiteNavigationMenuItem curSiteNavigationMenuItem :
					siteNavigationMenuItems) {

				if (Objects.equals(
						navigationMenuItemExternalReferenceCode,
						curSiteNavigationMenuItem.getExternalReferenceCode())) {

					siteNavigationMenuItem = curSiteNavigationMenuItem;

					break;
				}
			}

			if (siteNavigationMenuItem != null) {
				UnicodeProperties unicodeProperties =
					UnicodePropertiesBuilder.putAll(
						navigationMenuItem.getTypeSettings()
					).build();

				if (!_hasModel(
						groupId, navigationMenuItem, unicodeProperties)) {

					throw new IllegalArgumentException(
						"Unable to find navigation menu item with external " +
							"reference code " +
								navigationMenuItemExternalReferenceCode);
				}

				SiteNavigationMenuItem updatedSiteNavigationMenuItem =
					_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
						siteNavigationMenuItem.getSiteNavigationMenuItemId(),
						unicodeProperties.toString(),
						ServiceContextBuilder.create(
							groupId, contextHttpServletRequest, null
						).expandoBridgeAttributes(
							CustomFieldsUtil.toMap(
								SiteNavigationMenuItem.class.getName(),
								contextCompany.getCompanyId(),
								navigationMenuItem.getCustomFields(),
								contextAcceptLanguage.getPreferredLocale())
						).build());

				_updateNavigationMenuItems(
					groupId, navigationMenuItem.getNavigationMenuItems(),
					updatedSiteNavigationMenuItem.getSiteNavigationMenuItemId(),
					siteNavigationMenuId);

				siteNavigationMenuItems.remove(siteNavigationMenuItem);
			}
			else {
				_createNavigationMenuItem(
					groupId, navigationMenuItem, parentSiteNavigationMenuItemId,
					siteNavigationMenuId);
			}
		}

		_deleteNavigationMenuItems(siteNavigationMenuItems);
	}

	private static final EntityModel _entityModel =
		new NavigationMenuEntityModel();

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PermissionService _permissionService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

	@Reference
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@Reference
	private SiteNavigationMenuService _siteNavigationMenuService;

	@Reference
	private UserLocalService _userLocalService;

}