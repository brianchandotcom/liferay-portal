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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.configuration.LayoutAdminWebConfiguration;
import com.liferay.layout.admin.web.constants.LayoutAdminDisplayStyleKeys;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateCollectionNameComparator;
import com.liferay.layout.util.comparator.LayoutCreateDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.LayoutDescription;
import com.liferay.portal.util.LayoutListUtil;
import com.liferay.portlet.layoutsadmin.display.context.GroupDisplayContextHelper;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
public class LayoutsAdminDisplayContext {

	public LayoutsAdminDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_groupDisplayContextHelper = new GroupDisplayContextHelper(
			PortalUtil.getHttpServletRequest(liferayPortletRequest));

		_liferayPortletRequest.setAttribute(
			WebKeys.LAYOUT_DESCRIPTIONS, getLayoutDescriptions());
	}

	public String getAutoSiteNavigationMenuNames() {
		List<SiteNavigationMenu> siteNavigationMenus =
			SiteNavigationMenuLocalServiceUtil.getAutoSiteNavigationMenus(
				_themeDisplay.getScopeGroupId());

		return ListUtil.toString(
			siteNavigationMenus, SiteNavigationMenu.NAME_ACCESSOR,
			StringPool.COMMA_AND_SPACE);
	}

	public JSONArray getBreadcrumbEntriesJSONArray() throws PortalException {
		JSONArray breadcrumbEntriesJSONArray =
			JSONFactoryUtil.createJSONArray();

		breadcrumbEntriesJSONArray.put(
			_getBreadcrumbEntryJSONObject(
				LayoutConstants.DEFAULT_PLID,
				LanguageUtil.get(_themeDisplay.getLocale(), "home")));

		if (getSelPlid() == LayoutConstants.DEFAULT_PLID) {
			return breadcrumbEntriesJSONArray;
		}

		Layout selLayout = getSelLayout();

		if (selLayout == null) {
			return breadcrumbEntriesJSONArray;
		}

		List<Layout> layouts = selLayout.getAncestors();

		Collections.reverse(layouts);

		for (Layout layout : layouts) {
			breadcrumbEntriesJSONArray.put(
				_getBreadcrumbEntryJSONObject(
					layout.getPlid(),
					layout.getName(_themeDisplay.getLocale())));
		}

		breadcrumbEntriesJSONArray.put(
			_getBreadcrumbEntryJSONObject(
				selLayout.getPlid(),
				selLayout.getName(_themeDisplay.getLocale())));

		return breadcrumbEntriesJSONArray;
	}

	public String getConfigureLayoutURL(Layout layout) {
		PortletURL configureLayoutURL =
			_liferayPortletResponse.createRenderURL();

		configureLayoutURL.setParameter(
			"mvcRenderCommandName", "/layout/edit_layout");
		configureLayoutURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		configureLayoutURL.setParameter(
			"backURL", _themeDisplay.getURLCurrent());

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		configureLayoutURL.setParameter(
			"portletResource", portletDisplay.getId());

		configureLayoutURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		configureLayoutURL.setParameter(
			"selPlid", String.valueOf(layout.getPlid()));
		configureLayoutURL.setParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));

		return configureLayoutURL.toString();
	}

	public String getCopyApplicationsURL(Layout layout) {
		PortletURL copyApplicationsURL =
			_liferayPortletResponse.createRenderURL();

		copyApplicationsURL.setParameter("mvcPath", "/copy_applications.jsp");
		copyApplicationsURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		copyApplicationsURL.setParameter(
			"backURL", _themeDisplay.getURLCurrent());
		copyApplicationsURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		copyApplicationsURL.setParameter(
			"selPlid", String.valueOf(layout.getPlid()));
		copyApplicationsURL.setParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));

		return copyApplicationsURL.toString();
	}

	public String getCopyLayoutURL(Layout layout) {
		PortletURL copyLayoutURL = _liferayPortletResponse.createActionURL();

		copyLayoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout/copy_layout");
		copyLayoutURL.setParameter("groupId", String.valueOf(getGroupId()));
		copyLayoutURL.setParameter(
			"liveGroupId", String.valueOf(getLiveGroupId()));
		copyLayoutURL.setParameter(
			"stagingGroupId", String.valueOf(getStagingGroupId()));
		copyLayoutURL.setParameter(
			"privateLayout", String.valueOf(isPrivateLayout()));
		copyLayoutURL.setParameter(
			"layoutId", String.valueOf(layout.getLayoutId()));

		return copyLayoutURL.toString();
	}

	public String getDeleteLayoutURL(Layout layout) {
		PortletURL deleteLayoutURL = _liferayPortletResponse.createActionURL();

		deleteLayoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout/delete_layout");
		deleteLayoutURL.setParameter("redirect", _themeDisplay.getURLCurrent());
		deleteLayoutURL.setParameter(
			"selPlid", String.valueOf(layout.getPlid()));

		return deleteLayoutURL.toString();
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_liferayPortletRequest);

		_displayStyle = portalPreferences.getValue(
			LayoutAdminPortletKeys.GROUP_PAGES, "display-style", "list");

		return _displayStyle;
	}

	public String getEditLayoutURL(Layout layout) throws PortalException {
		if (!Objects.equals(layout.getType(), "content")) {
			return PortalUtil.getLayoutFullURL(layout, _themeDisplay);
		}

		PortletURL editLayoutURL = _liferayPortletResponse.createRenderURL();

		editLayoutURL.setParameter("mvcPath", "/edit_content_layout.jsp");
		editLayoutURL.setParameter("backURL", _themeDisplay.getURLCurrent());

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		editLayoutURL.setParameter("portletResource", portletDisplay.getId());

		editLayoutURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		editLayoutURL.setParameter("selPlid", String.valueOf(layout.getPlid()));

		return editLayoutURL.toString();
	}

	public long getFirstLayoutPageTemplateCollectionId()
		throws PortalException {

		LayoutPageTemplateCollectionService
			layoutPageTemplateCollectionService =
				(LayoutPageTemplateCollectionService)_liferayPortletRequest.
					getAttribute(
						LayoutAdminWebKeys.
							LAYOUT_PAGE_TEMPLATE_COLLECTION_SERVICE);

		LayoutPageTemplateCollectionNameComparator
			layoutPageTemplateCollectionNameComparator =
				new LayoutPageTemplateCollectionNameComparator(true);

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			layoutPageTemplateCollectionService.
				getBasicLayoutPageTemplateCollections(
					getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					layoutPageTemplateCollectionNameComparator);

		if (ListUtil.isNotEmpty(layoutPageTemplateCollections)) {
			LayoutPageTemplateCollection layoutPageTemplateCollection =
				layoutPageTemplateCollections.get(0);

			return layoutPageTemplateCollection.
				getLayoutPageTemplateCollectionId();
		}

		return 0;
	}

	public Group getGroup() {
		return _groupDisplayContextHelper.getGroup();
	}

	public Long getGroupId() {
		return _groupDisplayContextHelper.getGroupId();
	}

	public UnicodeProperties getGroupTypeSettings() {
		return _groupDisplayContextHelper.getGroupTypeSettings();
	}

	public JSONArray getLayoutColumnsJSONArray() throws Exception {
		JSONArray layoutColumnsJSONArray = _getLayoutColumnsJSONArray();

		while (layoutColumnsJSONArray.length() < 3) {
			layoutColumnsJSONArray.put(JSONFactoryUtil.createJSONArray());
		}

		return layoutColumnsJSONArray;
	}

	public List<LayoutDescription> getLayoutDescriptions() {
		if (_layoutDescriptions != null) {
			return _layoutDescriptions;
		}

		_layoutDescriptions = LayoutListUtil.getLayoutDescriptions(
			getGroupId(), isPrivateLayout(), getRootNodeName(),
			_themeDisplay.getLocale());

		return _layoutDescriptions;
	}

	public Long getLayoutId() {
		if (_layoutId != null) {
			return _layoutId;
		}

		_layoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			_layoutId = selLayout.getLayoutId();
		}

		return _layoutId;
	}

	public SearchContainer getLayoutsSearchContainer() throws PortalException {
		if (_layoutsSearchContainer != null) {
			return _layoutsSearchContainer;
		}

		String emptyResultMessage = "there-are-no-public-pages";

		if (isPrivatePages()) {
			emptyResultMessage = "there-are-no-private-pages";
		}

		SearchContainer layoutsSearchContainer = new SearchContainer(
			_liferayPortletRequest, getPortletURL(), null, emptyResultMessage);

		layoutsSearchContainer.setOrderByCol(getOrderByCol());

		OrderByComparator orderByComparator = _getOrderByComparator();

		layoutsSearchContainer.setOrderByComparator(orderByComparator);

		layoutsSearchContainer.setOrderByType(getOrderByType());

		EmptyOnClickRowChecker emptyOnClickRowChecker =
			new EmptyOnClickRowChecker(_liferayPortletResponse);

		layoutsSearchContainer.setRowChecker(emptyOnClickRowChecker);

		int layoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			getSelGroup(), isPrivatePages());
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			getSelGroupId(), isPrivatePages(),
			layoutsSearchContainer.getStart(), layoutsSearchContainer.getEnd(),
			orderByComparator);

		layoutsSearchContainer.setTotal(layoutsCount);
		layoutsSearchContainer.setResults(layouts);

		_layoutsSearchContainer = layoutsSearchContainer;

		return _layoutsSearchContainer;
	}

	public Group getLiveGroup() {
		return _groupDisplayContextHelper.getLiveGroup();
	}

	public Long getLiveGroupId() {
		return _groupDisplayContextHelper.getLiveGroupId();
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		String defaultNavigation = "public-pages";

		if (!isShowPublicPages()) {
			defaultNavigation = "private-pages";
		}

		_navigation = ParamUtil.getString(
			_liferayPortletRequest, "navigation", defaultNavigation);

		return _navigation;
	}

	public List<NavigationItem> getNavigationItems() {
		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem pagesNavigationItem = new NavigationItem();

		pagesNavigationItem.setActive(Objects.equals(getTabs1(), "pages"));

		PortletURL pagesURL = getPortletURL();

		pagesURL.setParameter("tabs1", "pages");

		pagesNavigationItem.setHref(pagesURL.toString());

		pagesNavigationItem.setLabel(
			LanguageUtil.get(_themeDisplay.getLocale(), "pages"));

		navigationItems.add(pagesNavigationItem);

		NavigationItem pageTemplatesNavigationItem = new NavigationItem();

		pageTemplatesNavigationItem.setActive(
			Objects.equals(getTabs1(), "page-templates"));

		PortletURL pageTemplatesURL = getPortletURL();

		pageTemplatesURL.setParameter("tabs1", "page-templates");

		pageTemplatesNavigationItem.setHref(pageTemplatesURL.toString());

		pageTemplatesNavigationItem.setLabel(
			LanguageUtil.get(_themeDisplay.getLocale(), "page-templates"));

		navigationItems.add(pageTemplatesNavigationItem);

		return navigationItems;
	}

	public String[] getNavigationKeys() {
		if (_navigationKeys != null) {
			return _navigationKeys;
		}

		_navigationKeys = new String[] {"public-pages", "private-pages"};

		if (!isShowPublicPages()) {
			_navigationKeys = new String[] {"private-pages"};
		}

		return _navigationKeys;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_liferayPortletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_liferayPortletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public String[] getOrderColumns() {
		return new String[] {"create-date"};
	}

	public String getOrphanPortletsURL(Layout layout) {
		PortletURL orphanPortletsURL =
			_liferayPortletResponse.createRenderURL();

		orphanPortletsURL.setParameter("mvcPath", "/orphan_portlets.jsp");
		orphanPortletsURL.setParameter(
			"backURL", _themeDisplay.getURLCurrent());
		orphanPortletsURL.setParameter(
			"selPlid", String.valueOf(layout.getPlid()));

		return orphanPortletsURL.toString();
	}

	public long getParentLayoutId() {
		if (_parentLayoutId != null) {
			return _parentLayoutId;
		}

		_parentLayoutId = Long.valueOf(0);

		Layout layout = getSelLayout();

		if (layout != null) {
			_parentLayoutId = layout.getLayoutId();
		}

		return _parentLayoutId;
	}

	public String getPath(Layout layout, Locale locale) throws PortalException {
		List<Layout> layouts = layout.getAncestors();

		StringBundler sb = new StringBundler(layouts.size() * 4);

		for (Layout curLayout : layouts) {
			sb.append(curLayout.getName(locale));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		return sb.toString();
	}

	public String getPermissionsURL(Layout layout) throws Exception {
		return PermissionsURLTag.doTag(
			StringPool.BLANK, Layout.class.getName(),
			HtmlUtil.escape(layout.getName(_themeDisplay.getLocale())), null,
			String.valueOf(layout.getPlid()),
			LiferayWindowState.POP_UP.toString(), null,
			_themeDisplay.getRequest());
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter("tabs1", getTabs1());
		portletURL.setParameter("navigation", getNavigation());
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_liferayPortletRequest, "redirect");

		return _redirect;
	}

	public PortletURL getRedirectURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter("groupId", String.valueOf(getSelGroupId()));

		return portletURL;
	}

	public String getRootNodeName() {
		if (_rootNodeName != null) {
			return _rootNodeName;
		}

		_rootNodeName = getRootNodeName(isPrivateLayout());

		return _rootNodeName;
	}

	public String getRootNodeName(boolean privateLayout) {
		Group liveGroup = getLiveGroup();

		return liveGroup.getLayoutRootNodeName(
			privateLayout, _themeDisplay.getLocale());
	}

	public String getSelectLayoutPageTemplateEntryURL() throws PortalException {
		return getSelectLayoutPageTemplateEntryURL(
			getFirstLayoutPageTemplateCollectionId());
	}

	public String getSelectLayoutPageTemplateEntryURL(
		long layoutPageTemplateCollectionId) {

		return getSelectLayoutPageTemplateEntryURL(
			layoutPageTemplateCollectionId, LayoutConstants.DEFAULT_PLID);
	}

	public String getSelectLayoutPageTemplateEntryURL(
		long layoutPageTemplateCollectionId, long selPlid) {

		return getSelectLayoutPageTemplateEntryURL(
			layoutPageTemplateCollectionId, selPlid, "basic-pages");
	}

	public String getSelectLayoutPageTemplateEntryURL(
		long layoutPageTemplateCollectionId, long selPlid, String selectedTab) {

		PortletURL selectLayoutPageTemplateEntryURL =
			_liferayPortletResponse.createRenderURL();

		selectLayoutPageTemplateEntryURL.setParameter(
			"mvcPath", "/select_layout_page_template_entry.jsp");
		selectLayoutPageTemplateEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		selectLayoutPageTemplateEntryURL.setParameter(
			"backURL", _themeDisplay.getURLCurrent());
		selectLayoutPageTemplateEntryURL.setParameter(
			"groupId", String.valueOf(getSelGroupId()));
		selectLayoutPageTemplateEntryURL.setParameter(
			"selPlid", String.valueOf(selPlid));
		selectLayoutPageTemplateEntryURL.setParameter(
			"privateLayout", String.valueOf(isPrivatePages()));

		if (layoutPageTemplateCollectionId > 0) {
			selectLayoutPageTemplateEntryURL.setParameter(
				"layoutPageTemplateCollectionId",
				String.valueOf(layoutPageTemplateCollectionId));
		}
		else if (Validator.isNotNull(selectedTab)) {
			selectLayoutPageTemplateEntryURL.setParameter(
				"selectedTab", selectedTab);
		}

		return selectLayoutPageTemplateEntryURL.toString();
	}

	public Group getSelGroup() {
		return _groupDisplayContextHelper.getSelGroup();
	}

	public long getSelGroupId() {
		Group selGroup = getSelGroup();

		if (selGroup != null) {
			return selGroup.getGroupId();
		}

		return 0;
	}

	public Layout getSelLayout() {
		if (_selLayout != null) {
			return _selLayout;
		}

		if (getSelPlid() != LayoutConstants.DEFAULT_PLID) {
			_selLayout = LayoutLocalServiceUtil.fetchLayout(getSelPlid());
		}

		return _selLayout;
	}

	public LayoutSet getSelLayoutSet() throws PortalException {
		if (_selLayoutSet != null) {
			return _selLayoutSet;
		}

		Group group = getStagingGroup();

		if (group == null) {
			group = getLiveGroup();
		}

		_selLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			group.getGroupId(), isPrivateLayout());

		return _selLayoutSet;
	}

	public Long getSelPlid() {
		if (_selPlid != null) {
			return _selPlid;
		}

		_selPlid = ParamUtil.getLong(
			_liferayPortletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		return _selPlid;
	}

	public Group getStagingGroup() {
		return _groupDisplayContextHelper.getStagingGroup();
	}

	public Long getStagingGroupId() {
		return _groupDisplayContextHelper.getStagingGroupId();
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_liferayPortletRequest, "tabs1", "pages");

		return _tabs1;
	}

	public boolean isMillerColumnsEnabled() {
		if (_millerColumnsEnabled != null) {
			return _millerColumnsEnabled;
		}

		_millerColumnsEnabled = false;

		LayoutAdminWebConfiguration layoutAdminWebConfiguration =
			(LayoutAdminWebConfiguration)_liferayPortletRequest.getAttribute(
				LayoutAdminWebKeys.LAYOUT_ADMIN_CONFIGURATION);

		if (Objects.equals(
				layoutAdminWebConfiguration.layoutDisplayStyle(),
				LayoutAdminDisplayStyleKeys.MILLER)) {

			_millerColumnsEnabled = true;
		}

		return _millerColumnsEnabled;
	}

	public boolean isPageTemplates() {
		if (Objects.equals(getTabs1(), "page-templates")) {
			return true;
		}

		return false;
	}

	public boolean isPrivateLayout() {
		if (_privateLayout != null) {
			return _privateLayout;
		}

		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype() ||
			selGroup.isLayoutSetPrototype()) {

			_privateLayout = true;

			return _privateLayout;
		}

		Layout selLayout = getSelLayout();

		if (getSelLayout() != null) {
			_privateLayout = selLayout.isPrivateLayout();

			return _privateLayout;
		}

		Layout layout = _themeDisplay.getLayout();

		if (!layout.isTypeControlPanel()) {
			_privateLayout = layout.isPrivateLayout();

			return _privateLayout;
		}

		_privateLayout = ParamUtil.getBoolean(
			_liferayPortletRequest, "privateLayout");

		return _privateLayout;
	}

	public boolean isPrivatePages() {
		if (Objects.equals(getNavigation(), "private-pages")) {
			return true;
		}

		return false;
	}

	public boolean isPublicPages() {
		if (Objects.equals(getNavigation(), "public-pages")) {
			return true;
		}

		return false;
	}

	public boolean isShowAddRootLayoutButton() throws PortalException {
		return GroupPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), getSelGroup(),
			ActionKeys.ADD_LAYOUT);
	}

	public boolean isShowPublicPages() {
		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype() || selGroup.isLayoutPrototype()) {
			return false;
		}

		return true;
	}

	public boolean showAddChildPageAction(Layout layout)
		throws PortalException {

		return LayoutPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), layout,
			ActionKeys.ADD_LAYOUT);
	}

	public boolean showConfigureAction(Layout layout) throws PortalException {
		return LayoutPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), layout, ActionKeys.UPDATE);
	}

	public boolean showCopyApplicationsAction(Layout layout)
		throws PortalException {

		// Check if layout is incomplete

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		boolean incomplete = false;

		if (layoutRevision != null) {
			long layoutSetBranchId = layoutRevision.getLayoutSetBranchId();

			incomplete = StagingUtil.isIncomplete(layout, layoutSetBranchId);
		}

		if (incomplete) {
			return false;
		}

		// Check if layout is a layout prototype

		Group group = layout.getGroup();

		if (group.isLayoutPrototype()) {
			return false;
		}

		return LayoutPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), layout, ActionKeys.UPDATE);
	}

	public boolean showCopyLayoutAction(Layout layout) throws PortalException {
		if (!isShowAddRootLayoutButton()) {
			return false;
		}

		if (!layout.isTypePortlet()) {
			return false;
		}

		return true;
	}

	public boolean showDeleteAction(Layout layout) throws PortalException {
		if (StagingUtil.isIncomplete(layout)) {
			return false;
		}

		if (!LayoutPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), layout,
				ActionKeys.DELETE)) {

			return false;
		}

		Group group = layout.getGroup();

		int layoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			group, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (group.isGuest() && !layout.isPrivateLayout() &&
			layout.isRootLayout() && (layoutsCount == 1)) {

			return false;
		}

		return true;
	}

	public boolean showOrphanPortletsAction(Layout layout) {
		if (StagingUtil.isIncomplete(layout)) {
			return false;
		}

		if (!layout.isSupportsEmbeddedPortlets()) {
			return false;
		}

		OrphanPortletsDisplayContext orphanPortletsDisplayContext =
			new OrphanPortletsDisplayContext(
				_liferayPortletRequest, _liferayPortletResponse);

		if (ListUtil.isEmpty(
				orphanPortletsDisplayContext.getOrphanPortlets(layout))) {

			return false;
		}

		return true;
	}

	public boolean showPermissionsAction(Layout layout) throws PortalException {
		if (StagingUtil.isIncomplete(layout)) {
			return false;
		}

		Group selGroup = getSelGroup();

		if (selGroup.isLayoutPrototype()) {
			return false;
		}

		return LayoutPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), layout,
			ActionKeys.PERMISSIONS);
	}

	private JSONObject _getActionURLsJSONObject(Layout layout)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (showAddChildPageAction(layout)) {
			jsonObject.put(
				"addURL",
				getSelectLayoutPageTemplateEntryURL(
					getFirstLayoutPageTemplateCollectionId(),
					layout.getPlid()));
		}

		if (showConfigureAction(layout)) {
			jsonObject.put("configureURL", getConfigureLayoutURL(layout));
		}

		if (showCopyLayoutAction(layout)) {
			jsonObject.put("copyLayoutURL", getCopyLayoutURL(layout));
		}

		if (showCopyApplicationsAction(layout)) {
			jsonObject.put(
				"copyApplicationsURL", getCopyApplicationsURL(layout));
		}

		if (showDeleteAction(layout)) {
			jsonObject.put("deleteURL", getDeleteLayoutURL(layout));
		}

		jsonObject.put("editLayoutURL", getEditLayoutURL(layout));

		if (showOrphanPortletsAction(layout)) {
			jsonObject.put("orphanPortletsURL", getOrphanPortletsURL(layout));
		}

		if (showPermissionsAction(layout)) {
			jsonObject.put("permissionsURL", getPermissionsURL(layout));
		}

		return jsonObject;
	}

	private JSONObject _getBreadcrumbEntryJSONObject(long plid, String title) {
		JSONObject breadcrumbEntryJSONObject =
			JSONFactoryUtil.createJSONObject();

		breadcrumbEntryJSONObject.put("title", title);

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("selPlid", String.valueOf(plid));

		breadcrumbEntryJSONObject.put("url", portletURL.toString());

		return breadcrumbEntryJSONObject;
	}

	private JSONArray _getLayoutColumnsJSONArray() throws Exception {
		JSONArray layoutColumnsJSONArray = JSONFactoryUtil.createJSONArray();

		layoutColumnsJSONArray.put(_getLayoutsJSONArray(0));

		if (getSelPlid() == LayoutConstants.DEFAULT_PLID) {
			return layoutColumnsJSONArray;
		}

		Layout selLayout = getSelLayout();

		if (selLayout == null) {
			return layoutColumnsJSONArray;
		}

		List<Layout> layouts = selLayout.getAncestors();

		Collections.reverse(layouts);

		for (Layout layout : layouts) {
			layoutColumnsJSONArray.put(
				_getLayoutsJSONArray(layout.getLayoutId()));
		}

		layoutColumnsJSONArray.put(
			_getLayoutsJSONArray(selLayout.getLayoutId()));

		return layoutColumnsJSONArray;
	}

	private JSONArray _getLayoutsJSONArray(long parentLayoutId)
		throws Exception {

		JSONArray layoutsJSONArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			getSelGroupId(), isPrivatePages(), parentLayoutId, false,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, _getOrderByComparator());

		for (Layout layout : layouts) {
			JSONObject layoutJSONObject = JSONFactoryUtil.createJSONObject();

			layoutJSONObject.put(
				"actionURLs", _getActionURLsJSONObject(layout));
			layoutJSONObject.put("active", _isActive(layout.getPlid()));

			int childLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
				getGroup(), isPrivatePages(), layout.getLayoutId());

			layoutJSONObject.put("hasChild", childLayoutsCount > 0);

			layoutJSONObject.put("plid", layout.getPlid());

			if (childLayoutsCount > 0) {
				PortletURL portletURL = getPortletURL();

				portletURL.setParameter(
					"selPlid", String.valueOf(layout.getPlid()));

				layoutJSONObject.put("url", portletURL.toString());
			}

			layoutJSONObject.put(
				"title", layout.getName(_themeDisplay.getLocale()));

			layoutsJSONArray.put(layoutJSONObject);
		}

		return layoutsJSONArray;
	}

	private OrderByComparator _getOrderByComparator() {
		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		OrderByComparator<Layout> orderByComparator = null;

		if (Objects.equals(getOrderByCol(), "create-date")) {
			orderByComparator = new LayoutCreateDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private boolean _isActive(long plid) throws PortalException {
		if (plid == getSelPlid()) {
			return true;
		}

		Layout selLayout = getSelLayout();

		if (selLayout == null) {
			return false;
		}

		for (Layout layout : selLayout.getAncestors()) {
			if (plid == layout.getPlid()) {
				return true;
			}
		}

		return false;
	}

	private String _displayStyle;
	private final GroupDisplayContextHelper _groupDisplayContextHelper;
	private List<LayoutDescription> _layoutDescriptions;
	private Long _layoutId;
	private SearchContainer _layoutsSearchContainer;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Boolean _millerColumnsEnabled;
	private String _navigation;
	private String[] _navigationKeys;
	private String _orderByCol;
	private String _orderByType;
	private Long _parentLayoutId;
	private Boolean _privateLayout;
	private String _redirect;
	private String _rootNodeName;
	private Layout _selLayout;
	private LayoutSet _selLayoutSet;
	private Long _selPlid;
	private String _tabs1;
	private final ThemeDisplay _themeDisplay;

}