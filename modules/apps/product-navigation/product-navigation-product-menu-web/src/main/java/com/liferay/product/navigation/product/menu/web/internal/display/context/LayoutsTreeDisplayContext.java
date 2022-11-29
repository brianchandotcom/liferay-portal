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

package com.liferay.product.navigation.product.menu.web.internal.display.context;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.SessionTreeJSClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.layoutsadmin.util.LayoutsTreeUtil;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuPortletKeys;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuWebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class LayoutsTreeDisplayContext {

	public LayoutsTreeDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		LayoutService layoutService, RenderRequest renderRequest,
		RenderResponse renderResponse,
		SiteNavigationMenuItemLocalService siteNavigationMenuItemLocalService,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry,
		SiteNavigationMenuLocalService siteNavigationMenuLocalService) {

		_liferayPortletRequest = PortalUtil.getLiferayPortletRequest(
			renderRequest);

		_httpServletRequest = httpServletRequest;
		_language = language;
		_layoutService = layoutService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_siteNavigationMenuItemLocalService =
			siteNavigationMenuItemLocalService;
		_siteNavigationMenuItemTypeRegistry =
			siteNavigationMenuItemTypeRegistry;
		_siteNavigationMenuLocalService = siteNavigationMenuLocalService;

		_groupProvider = (GroupProvider)_liferayPortletRequest.getAttribute(
			ApplicationListWebKeys.GROUP_PROVIDER);
		_namespace = PortalUtil.getPortletNamespace(
			ProductNavigationProductMenuPortletKeys.
				PRODUCT_NAVIGATION_PRODUCT_MENU);
		_themeDisplay = (ThemeDisplay)_liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getAddCollectionLayoutURL() {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagingGroup()) {
			return null;
		}

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/select_layout_collections.jsp"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			_getBackURL()
		).setParameter(
			"groupId", _themeDisplay.getSiteGroupId()
		).setParameter(
			"privateLayout", isPrivateLayout()
		).buildPortletURL();
	}

	public PortletURL getAddLayoutURL() {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagingGroup()) {
			return null;
		}

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/select_layout_page_template_entry.jsp"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			_getBackURL()
		).setParameter(
			"groupId", _themeDisplay.getSiteGroupId()
		).setParameter(
			"privateLayout", isPrivateLayout()
		).buildPortletURL();
	}

	public String getAdministrationPortletURL() {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).buildString();
	}

	public PortletURL getConfigureLayoutSetURL() {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout_set"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			_getBackURL()
		).setParameter(
			"groupId", _themeDisplay.getScopeGroupId()
		).setParameter(
			"privateLayout", isPrivateLayout()
		).buildPortletURL();
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		Group group = _groupProvider.getGroup(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest));

		if (group != null) {
			_groupId = group.getGroupId();
		}
		else {
			_groupId = _themeDisplay.getSiteGroupId();
		}

		return _groupId;
	}

	public Map<String, Object> getLayoutFinderData() {
		return HashMapBuilder.<String, Object>put(
			"administrationPortletNamespace",
			PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES)
		).put(
			"administrationPortletURL", getAdministrationPortletURL()
		).put(
			"findLayoutsURL",
			() -> {
				LiferayPortletURL findLayoutsURL = PortletURLFactoryUtil.create(
					_liferayPortletRequest,
					ProductNavigationProductMenuPortletKeys.
						PRODUCT_NAVIGATION_PRODUCT_MENU,
					PortletRequest.RESOURCE_PHASE);

				findLayoutsURL.setResourceID(
					"/product_navigation_product_menu/find_layouts");

				return findLayoutsURL.toString();
			}
		).put(
			"namespace", getNamespace()
		).put(
			"productMenuPortletURL", getProductMenuPortletURL()
		).put(
			"spritemap", _themeDisplay.getPathThemeSpritemap()
		).build();
	}

	public String getNamespace() {
		return _namespace;
	}

	public Map<String, Object> getPagesTreeData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"config",
			HashMapBuilder.<String, Object>put(
				"loadMoreItemsURL",
				() -> {
					LiferayPortletURL liferayPortletURL =
						(LiferayPortletURL)ResourceURLBuilder.createResourceURL(
							_renderResponse
						).setResourceID(
							"/product_navigation_product_menu/get_layouts"
						).buildResourceURL();

					liferayPortletURL.setCopyCurrentRenderParameters(false);

					return liferayPortletURL.toString();
				}
			).put(
				"maxPageSize",
				GetterUtil.getInteger(
					PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN)
			).put(
				"moveItemURL",
				() -> {
					StringBundler sb = new StringBundler(3);

					sb.append(PortalUtil.getPortalURL(_httpServletRequest));
					sb.append(_themeDisplay.getPathMain());
					sb.append("/portal/edit_layout?cmd=parent_layout_id");

					return sb.toString();
				}
			).put(
				"namespace", getNamespace()
			).put(
				"stagingEnabled",
				() -> {
					Group scopeGroup = _themeDisplay.getScopeGroup();

					if (scopeGroup.hasLocalOrRemoteStagingGroup()) {
						return true;
					}

					return false;
				}
			).build()
		).put(
			"isPrivateLayoutsTree", isPrivateLayout()
		).put(
			"items", _getLayoutsJSONArray()
		).put(
			"selectedLayoutId", getSelPlid()
		).put(
			"selectedLayoutPath", _getSelectedLayoutPath()
		).build();
	}

	public String getPagesTreeURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_liferayPortletRequest,
				ProductNavigationProductMenuPortletKeys.
					PRODUCT_NAVIGATION_PRODUCT_MENU,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/portlet/pages_tree.jsp"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			_getBackURL()
		).setParameter(
			"selPpid", this::getSelPlid
		).setWindowState(
			LiferayWindowState.EXCLUSIVE
		).buildString();
	}

	public Map<String, Object> getPageTypeSelectorData() {
		return HashMapBuilder.<String, Object>put(
			"addCollectionLayoutURL", _setSelPlid(getAddCollectionLayoutURL())
		).put(
			"addLayoutURL", _setSelPlid(getAddLayoutURL())
		).put(
			"configureLayoutSetURL",
			() -> {
				if (!isShowConfigureLayout()) {
					return StringPool.BLANK;
				}

				return _setSelPlid(getConfigureLayoutSetURL());
			}
		).put(
			"namespace", getNamespace()
		).put(
			"pagesTreeURL", getPagesTreeURL()
		).put(
			"pageTypeOptions", _getPageTypeOptionsJSONArray()
		).put(
			"pageTypeSelectedOption", _getPageTypeSelectedOption()
		).put(
			"pageTypeSelectedOptionLabel", _getPageTypeSelectedOptionLabel()
		).put(
			"showAddIcon", this::_isShowAddIcon
		).build();
	}

	public String getProductMenuPortletURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_liferayPortletRequest,
				ProductNavigationProductMenuPortletKeys.
					PRODUCT_NAVIGATION_PRODUCT_MENU,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/portlet/product_menu.jsp"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			_getBackURL()
		).setWindowState(
			LiferayWindowState.EXCLUSIVE
		).buildString();
	}

	public long getSelectedSiteNavigationMenuItemId() {
		if (_selectedSiteNavigationMenuItemId != null) {
			return _selectedSiteNavigationMenuItemId;
		}

		_selectedSiteNavigationMenuItemId = ParamUtil.getLong(
			_httpServletRequest,
			_namespace + _SITE_NAVIGATION_MENU_ITEM_ID_PARAMETER_NAME);

		return _selectedSiteNavigationMenuItemId;
	}

	public long getSelPlid() {
		Layout layout = _themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return ParamUtil.get(
				_liferayPortletRequest, "selPlid",
				LayoutConstants.DEFAULT_PLID);
		}

		if (layout.isSystem() && layout.isTypeContent()) {
			return layout.getClassPK();
		}

		return layout.getPlid();
	}

	public Map<String, Object> getSiteNavigationMenuData() {
		return HashMapBuilder.<String, Object>put(
			"selectedSiteNavigationMenuItemId",
			String.valueOf(getSelectedSiteNavigationMenuItemId())
		).put(
			"siteNavigationMenuItems", _getSiteNavigationMenuItemsJSONArray()
		).build();
	}

	public boolean hasAddLayoutPermission() throws PortalException {
		if (GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroup(), ActionKeys.ADD_LAYOUT)) {

			return true;
		}

		return false;
	}

	public boolean hasAdministrationPortletPermission() throws Exception {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			_themeDisplay.getCompanyId(), LayoutAdminPortletKeys.GROUP_PAGES);

		if (portlet == null) {
			return false;
		}

		ControlPanelEntry controlPanelEntry =
			portlet.getControlPanelEntryInstance();

		if (!controlPanelEntry.hasAccessPermission(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroup(), portlet)) {

			return false;
		}

		return true;
	}

	public boolean hasConfigureLayoutPermission() throws PortalException {
		if (GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroup(), ActionKeys.MANAGE_LAYOUTS)) {

			return true;
		}

		return false;
	}

	public boolean isPrivateLayout() {
		return Objects.equals(
			ProductNavigationProductMenuWebKeys.PRIVATE_LAYOUT,
			_getPageTypeSelectedOption());
	}

	public boolean isPrivateLayoutsEnabled() {
		if (_privateLayoutsEnabled != null) {
			return _privateLayoutsEnabled;
		}

		Group group = _themeDisplay.getScopeGroup();

		if (group.isPrivateLayoutsEnabled()) {
			_privateLayoutsEnabled = true;
		}
		else {
			_privateLayoutsEnabled = false;
		}

		return _privateLayoutsEnabled;
	}

	public boolean isShowConfigureLayout() throws PortalException {
		if (_isPageHierarchySelectedOption() &&
			hasConfigureLayoutPermission()) {

			return true;
		}

		return false;
	}

	public boolean isSiteNavigationMenu() {
		if (_getSiteNavigationMenuId() > 0) {
			return true;
		}

		return false;
	}

	private String _getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		String backURL = ParamUtil.getString(_renderRequest, "backURL");

		if (Validator.isNull(backURL)) {
			backURL = ParamUtil.getString(
				PortalUtil.getOriginalServletRequest(
					PortalUtil.getHttpServletRequest(_liferayPortletRequest)),
				"backURL", _themeDisplay.getURLCurrent());
		}

		_backURL = backURL;

		return backURL;
	}

	private JSONArray _getChildSiteNavigationMenuItemsJSONArray(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId) {

		JSONArray childSiteNavigationMenuItemsJSONArray =
			JSONFactoryUtil.createJSONArray();

		List<SiteNavigationMenuItem> siteNavigationMenuItemList =
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItems(
				siteNavigationMenuId, parentSiteNavigationMenuItemId);

		for (SiteNavigationMenuItem childSiteNavigationMenuItem :
				siteNavigationMenuItemList) {

			childSiteNavigationMenuItemsJSONArray.put(
				_getSiteNavigationMenuItemJSONObject(
					childSiteNavigationMenuItem));
		}

		return childSiteNavigationMenuItemsJSONArray;
	}

	private JSONArray _getLayoutsJSONArray() throws Exception {
		JSONArray layoutsJSONArray = null;

		long[] openNodes = StringUtil.split(
			SessionTreeJSClicks.getOpenNodes(
				_httpServletRequest, "productMenuPagesTree"),
			0L);

		_httpServletRequest.setAttribute(
			ProductNavigationProductMenuWebKeys.RETURN_LAYOUTS_AS_ARRAY,
			Boolean.TRUE);

		String layoutsJSON = LayoutsTreeUtil.getLayoutsJSON(
			_httpServletRequest, _groupId, isPrivateLayout(),
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, openNodes, true,
			"productMenuPagesTree", null);

		if (layoutsJSON.startsWith(StringPool.OPEN_BRACKET)) {
			layoutsJSONArray = JSONFactoryUtil.createJSONArray(layoutsJSON);
		}
		else {
			layoutsJSONArray = JSONFactoryUtil.createJSONArray();
		}

		return JSONUtil.putAll(
			JSONUtil.put(
				"children", layoutsJSONArray
			).put(
				"hasChildren", true
			).put(
				"id", LayoutConstants.DEFAULT_PARENT_LAYOUT_ID
			).put(
				"name", _language.get(_themeDisplay.getLocale(), "pages")
			).put(
				"paginated",
				() -> {
					int layoutsCount = _layoutService.getLayoutsCount(
						_groupId, isPrivateLayout(),
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

					if (layoutsCount >
							PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN) {

						return true;
					}

					return false;
				}
			));
	}

	private JSONObject _getOptionGroupJSONObject(
		String nameKey, JSONArray itemsJSONArray) {

		return JSONUtil.put(
			"items", itemsJSONArray
		).put(
			"name", LanguageUtil.get(_themeDisplay.getLocale(), nameKey)
		);
	}

	private JSONObject _getOptionJSONObject(String name, String value) {
		return JSONUtil.put(
			"name", name
		).put(
			"value", value
		);
	}

	private JSONArray _getPagesOptionGroupJSONArray() {
		if (!isPrivateLayoutsEnabled()) {
			return JSONUtil.putAll(
				_getOptionJSONObject(
					LanguageUtil.get(
						_themeDisplay.getLocale(), "pages-hierarchy"),
					ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT));
		}

		return JSONUtil.putAll(
			_getOptionJSONObject(
				LanguageUtil.get(_themeDisplay.getLocale(), _PUBLIC_PAGES_KEY),
				ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT),
			_getOptionJSONObject(
				LanguageUtil.get(_themeDisplay.getLocale(), _PRIVATE_PAGES_KEY),
				ProductNavigationProductMenuWebKeys.PRIVATE_LAYOUT));
	}

	private JSONArray _getPageTypeOptionsJSONArray() {
		return JSONUtil.putAll(
			_getOptionGroupJSONObject("pages", _getPagesOptionGroupJSONArray()),
			_getOptionGroupJSONObject(
				"navigation-menus", _getSiteNavigationMenuJSONArray()));
	}

	private String _getPageTypeSelectedOption() {
		if (_pageTypeSelectedOption != null) {
			return _pageTypeSelectedOption;
		}

		String pageTypeSelectedOption =
			ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT;

		String pageTypeSelectedOptionSessionValue = SessionClicks.get(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest),
			getNamespace() +
				ProductNavigationProductMenuWebKeys.PAGE_TYPE_SELECTED_OPTION,
			ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT);

		if (_isValidPageTypeSelectedOption(
				pageTypeSelectedOptionSessionValue)) {

			pageTypeSelectedOption = pageTypeSelectedOptionSessionValue;
		}

		_pageTypeSelectedOption = pageTypeSelectedOption;

		return _pageTypeSelectedOption;
	}

	private String _getPageTypeSelectedOptionLabel() {
		if (Objects.equals(
				_getPageTypeSelectedOption(),
				ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT)) {

			if (!isPrivateLayoutsEnabled()) {
				return LanguageUtil.get(
					_themeDisplay.getLocale(), "pages-hierarchy");
			}

			return LanguageUtil.get(
				_themeDisplay.getLocale(), _PUBLIC_PAGES_KEY);
		}

		if (Objects.equals(
				_getPageTypeSelectedOption(),
				ProductNavigationProductMenuWebKeys.PRIVATE_LAYOUT)) {

			return LanguageUtil.get(
				_themeDisplay.getLocale(), _PRIVATE_PAGES_KEY);
		}

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchSiteNavigationMenu(
				_getSiteNavigationMenuId());

		if (siteNavigationMenu != null) {
			return siteNavigationMenu.getName();
		}

		return _getPageTypeSelectedOption();
	}

	private String _getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		String redirect = ParamUtil.getString(_renderRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = PortalUtil.escapeRedirect(_getBackURL());
		}

		_redirect = redirect;

		return _redirect;
	}

	private List<Long> _getSelectedLayoutPath() throws Exception {
		long selPlid = getSelPlid();

		List<Long> selectedLayoutPath = new ArrayList<>();

		selectedLayoutPath.add(LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (selPlid <= 0) {
			return selectedLayoutPath;
		}

		selectedLayoutPath.add(selPlid);

		selectedLayoutPath.addAll(
			ListUtil.toList(
				_layoutService.getAncestorLayouts(selPlid),
				layout -> layout.getPlid()));

		return selectedLayoutPath;
	}

	private long _getSiteNavigationMenuId() {
		if (_siteNavigationMenuId != null) {
			return _siteNavigationMenuId;
		}

		long siteNavigationMenuId = 0;

		if (!_isPageHierarchyOption(_getPageTypeSelectedOption())) {
			siteNavigationMenuId = GetterUtil.getLong(
				_getPageTypeSelectedOption());
		}

		_siteNavigationMenuId = siteNavigationMenuId;

		return _siteNavigationMenuId;
	}

	private JSONObject _getSiteNavigationMenuItemJSONObject(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_getSiteNavigationMenuItemType(siteNavigationMenuItem.getType());

		return JSONUtil.put(
			"children",
			_getChildSiteNavigationMenuItemsJSONArray(
				siteNavigationMenuItem.getSiteNavigationMenuId(),
				siteNavigationMenuItem.getSiteNavigationMenuItemId())
		).put(
			"id", siteNavigationMenuItem.getSiteNavigationMenuItemId()
		).put(
			"name",
			siteNavigationMenuItemType.getTitle(
				siteNavigationMenuItem, _themeDisplay.getSiteDefaultLocale())
		).put(
			"target",
			siteNavigationMenuItemType.getTarget(siteNavigationMenuItem)
		).put(
			"url",
			_getSiteNavigationMenuItemURL(
				siteNavigationMenuItem, siteNavigationMenuItemType)
		);
	}

	private JSONArray _getSiteNavigationMenuItemsJSONArray() {
		if (_siteNavigationMenuItemsJSONArray != null) {
			return _siteNavigationMenuItemsJSONArray;
		}

		if (_getSiteNavigationMenuId() > 0) {
			_siteNavigationMenuItemsJSONArray =
				_getChildSiteNavigationMenuItemsJSONArray(
					_getSiteNavigationMenuId(), 0L);
		}
		else {
			_siteNavigationMenuItemsJSONArray =
				JSONFactoryUtil.createJSONArray();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"GroupId: ", getGroupId(), " SiteNavigationMenuId: ",
					_getSiteNavigationMenuId(),
					" SiteNavigationMenuItemHierarchy: ",
					_siteNavigationMenuItemsJSONArray.toString()));
		}

		return _siteNavigationMenuItemsJSONArray;
	}

	private SiteNavigationMenuItemType _getSiteNavigationMenuItemType(
		String type) {

		if (!_siteNavigationMenuItemTypesMap.containsKey(type)) {
			_siteNavigationMenuItemTypesMap.put(
				type,
				_siteNavigationMenuItemTypeRegistry.
					getSiteNavigationMenuItemType(type));
		}

		return _siteNavigationMenuItemTypesMap.get(type);
	}

	private String _getSiteNavigationMenuItemURL(
		SiteNavigationMenuItem siteNavigationMenuItem,
		SiteNavigationMenuItemType siteNavigationMenuItemType) {

		String url = StringPool.BLANK;

		try {
			url = siteNavigationMenuItemType.getRegularURL(
				_liferayPortletRequest.getHttpServletRequest(),
				siteNavigationMenuItem);
		}
		catch (Exception exception) {
			_log.error(
				"Unabled to get url for siteNavigationMenuItemId: " +
					siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				exception);
		}

		if (Objects.equals(
				url, _themeDisplay.getURLCurrent() + StringPool.POUND)) {

			url = StringPool.POUND;
		}
		else if (Validator.isNotNull(url)) {
			url = HttpComponentsUtil.addParameter(
				url,
				getNamespace() + _SITE_NAVIGATION_MENU_ITEM_ID_PARAMETER_NAME,
				siteNavigationMenuItem.getSiteNavigationMenuItemId());
		}

		return url;
	}

	private JSONArray _getSiteNavigationMenuJSONArray() {
		if (_siteNavigationMenuJSONArray != null) {
			return _siteNavigationMenuJSONArray;
		}

		JSONArray siteNavigationMenuJSONArray =
			JSONFactoryUtil.createJSONArray();

		List<SiteNavigationMenu> siteNavigationMenuList =
			_siteNavigationMenuLocalService.getSiteNavigationMenus(
				getGroupId());

		for (SiteNavigationMenu siteNavigationMenu : siteNavigationMenuList) {
			siteNavigationMenuJSONArray.put(
				_getOptionJSONObject(
					siteNavigationMenu.getName(),
					String.valueOf(
						siteNavigationMenu.getSiteNavigationMenuId())));
		}

		_siteNavigationMenuJSONArray = siteNavigationMenuJSONArray;

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"GroupId: ", getGroupId(), " SiteNavigationMenuJSONArray: ",
					_siteNavigationMenuJSONArray));
		}

		return _siteNavigationMenuJSONArray;
	}

	private boolean _isPageHierarchyOption(String pageTypeOption) {
		if (Objects.equals(
				pageTypeOption,
				ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT) ||
			Objects.equals(
				pageTypeOption,
				ProductNavigationProductMenuWebKeys.PRIVATE_LAYOUT)) {

			return true;
		}

		return false;
	}

	private boolean _isPageHierarchySelectedOption() {
		if (_pageHierarchySelectedOption != null) {
			return _pageHierarchySelectedOption;
		}

		_pageHierarchySelectedOption = _isPageHierarchyOption(
			_getPageTypeSelectedOption());

		return _pageHierarchySelectedOption;
	}

	private boolean _isShowAddIcon() throws PortalException {
		if (!_isPageHierarchySelectedOption() || !hasAddLayoutPermission()) {
			return false;
		}

		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() &&
			Objects.equals(scopeGroup, StagingUtil.getLiveGroup(scopeGroup))) {

			return false;
		}

		return true;
	}

	private boolean _isValidPageTypeSelectedOption(
		String pageTypeSelectedOption) {

		if (_isPageHierarchyOption(pageTypeSelectedOption)) {
			return true;
		}

		long siteNavigationMenuId = GetterUtil.getLong(pageTypeSelectedOption);

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchSiteNavigationMenu(
				siteNavigationMenuId);

		if ((siteNavigationMenu != null) &&
			(siteNavigationMenu.getGroupId() == getGroupId())) {

			return true;
		}

		return false;
	}

	private String _setSelPlid(PortletURL portletURL) {
		if (portletURL == null) {
			return StringPool.BLANK;
		}

		portletURL.setParameter(
			"selPlid", String.valueOf(LayoutConstants.DEFAULT_PLID));

		return portletURL.toString();
	}

	private static final String _PRIVATE_PAGES_KEY = "private-pages";

	private static final String _PUBLIC_PAGES_KEY = "public-pages";

	private static final String _SITE_NAVIGATION_MENU_ITEM_ID_PARAMETER_NAME =
		"navigationItemMenuId";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutsTreeDisplayContext.class.getName());

	private String _backURL;
	private Long _groupId;
	private final GroupProvider _groupProvider;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final LayoutService _layoutService;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final String _namespace;
	private Boolean _pageHierarchySelectedOption;
	private String _pageTypeSelectedOption;
	private Boolean _privateLayoutsEnabled;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Long _selectedSiteNavigationMenuItemId;
	private Long _siteNavigationMenuId;
	private final SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;
	private JSONArray _siteNavigationMenuItemsJSONArray;
	private final SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;
	private final Map<String, SiteNavigationMenuItemType>
		_siteNavigationMenuItemTypesMap = new HashMap<>();
	private JSONArray _siteNavigationMenuJSONArray;
	private final SiteNavigationMenuLocalService
		_siteNavigationMenuLocalService;
	private final ThemeDisplay _themeDisplay;

}