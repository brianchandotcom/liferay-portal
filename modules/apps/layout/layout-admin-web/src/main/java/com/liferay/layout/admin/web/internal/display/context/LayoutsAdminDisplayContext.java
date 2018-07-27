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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateCollectionNameComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.LayoutDescription;
import com.liferay.portal.util.LayoutListUtil;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.portlet.layoutsadmin.display.context.GroupDisplayContextHelper;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

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

		_request = PortalUtil.getHttpServletRequest(_liferayPortletRequest);

		_groupDisplayContextHelper = new GroupDisplayContextHelper(_request);

		_liferayPortletRequest.setAttribute(
			WebKeys.LAYOUT_DESCRIPTIONS, getLayoutDescriptions());
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteSelectedPages");
						dropdownItem.setIcon("trash");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public List<DropdownItem> getAddLayoutDropdownItems() {
		return new DropdownItemList() {
			{
				if (isShowPublicPages()) {
					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setHref(
									getSelectLayoutPageTemplateEntryURL(false));
								dropdownItem.setLabel(
									LanguageUtil.get(_request, "public-page"));
							}));
				}

				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setHref(
								getSelectLayoutPageTemplateEntryURL(true));
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "private-page"));
						}));
			}
		};
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
				LayoutConstants.DEFAULT_PLID, isPrivatePages(),
				_getTitle(isPrivatePages())));

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
					layout.getPlid(), layout.isPrivateLayout(),
					layout.getName(_themeDisplay.getLocale())));
		}

		breadcrumbEntriesJSONArray.put(
			_getBreadcrumbEntryJSONObject(
				selLayout.getPlid(), selLayout.isPrivateLayout(),
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

	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				if (isShowPublicPages()) {
					addPrimaryDropdownItem(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setHref(
									getSelectLayoutPageTemplateEntryURL(false));
								dropdownItem.setLabel(
									LanguageUtil.get(_request, "public-page"));
							}));
				}

				addPrimaryDropdownItem(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setHref(
								getSelectLayoutPageTemplateEntryURL(true));
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "private-page"));
						}));
			}
		};
	}

	public String getDeleteLayoutURL(Layout layout) throws PortalException {
		PortletURL deleteLayoutURL = _liferayPortletResponse.createActionURL();

		deleteLayoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout/delete_layout");
		deleteLayoutURL.setParameter("redirect", _themeDisplay.getURLCurrent());
		deleteLayoutURL.setParameter(
			"selPlid", String.valueOf(layout.getPlid()));
		deleteLayoutURL.setParameter(
			"layoutSetBranchId", String.valueOf(_getActiveLayoutSetBranchId()));

		return deleteLayoutURL.toString();
	}

	public String getEditLayoutURL(Layout layout) {
		if (!Objects.equals(layout.getType(), "content")) {
			return StringPool.BLANK;
		}

		PortletURL editLayoutURL = _liferayPortletResponse.createRenderURL();

		editLayoutURL.setParameter("mvcPath", "/edit_content_layout.jsp");
		editLayoutURL.setParameter("redirect", _themeDisplay.getURLCurrent());
		editLayoutURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		editLayoutURL.setParameter("selPlid", String.valueOf(layout.getPlid()));

		return editLayoutURL.toString();
	}

	public long getFirstLayoutPageTemplateCollectionId() {
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
				getLayoutPageTemplateCollections(
					getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					layoutPageTemplateCollectionNameComparator);

		if (layoutPageTemplateCollections.isEmpty()) {
			return 0;
		}

		for (LayoutPageTemplateCollection layoutPageTemplateCollection :
				layoutPageTemplateCollections) {

			int layoutPageTemplateEntriesCount =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						_themeDisplay.getScopeGroupId(),
						layoutPageTemplateCollection.
							getLayoutPageTemplateCollectionId(),
						WorkflowConstants.STATUS_APPROVED);

			if (layoutPageTemplateEntriesCount > 0) {
				return layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId();
			}
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

	public Group getLiveGroup() {
		return _groupDisplayContextHelper.getLiveGroup();
	}

	public Long getLiveGroupId() {
		return _groupDisplayContextHelper.getLiveGroupId();
	}

	public String getMarkAsHomePageLayoutURL(Layout layout) {
		PortletURL markAsHomePageLayoutURL =
			_liferayPortletResponse.createActionURL();

		markAsHomePageLayoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout/mark_as_home_page_layout");
		markAsHomePageLayoutURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		markAsHomePageLayoutURL.setParameter(
			"selPlid", String.valueOf(layout.getPlid()));

		return markAsHomePageLayoutURL.toString();
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
		Group group = _themeDisplay.getScopeGroup();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		return new NavigationItemList() {
			{
				if (!group.isCompany()) {
					add(
						navigationItem -> {
							navigationItem.setActive(
								Objects.equals(getTabs1(), "pages"));
							navigationItem.setHref(
								getPortletURL(), "tabs1", "pages");
							navigationItem.setLabel(
								LanguageUtil.get(_request, "pages"));
						});
				}

				if (!(stagingGroupHelper.isLocalLiveGroup(group) ||
					  stagingGroupHelper.isRemoteLiveGroup(group))) {

					add(
						navigationItem -> {
							navigationItem.setActive(
								Objects.equals(getTabs1(), "page-templates"));
							navigationItem.setHref(
								getPortletURL(), "tabs1", "page-templates");
							navigationItem.setLabel(
								LanguageUtil.get(_request, "page-templates"));
						});
				}

				if (!group.isCompany() &&
					!(stagingGroupHelper.isLocalLiveGroup(group) ||
					  stagingGroupHelper.isRemoteLiveGroup(group))) {

					add(
						navigationItem -> {
							navigationItem.setActive(
								Objects.equals(getTabs1(), "display-pages"));
							navigationItem.setHref(
								getPortletURL(), "tabs1", "display-pages");
							navigationItem.setLabel(
								LanguageUtil.get(_request, "display-pages"));
						});
				}
			}
		};
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

	public String getPortletResource() {
		String portletResource = ParamUtil.getString(
			_request, "portletResource");

		if (Validator.isNull(portletResource)) {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			portletResource = portletDisplay.getPortletName();
		}

		return portletResource;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("tabs1", getTabs1());
		portletURL.setParameter("navigation", getNavigation());

		return portletURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(
			_liferayPortletRequest, "redirect", _themeDisplay.getURLCurrent());

		return _redirect;
	}

	public PortletURL getRedirectURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

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

	public String getSelectLayoutPageTemplateEntryURL(boolean privateLayout)
		throws PortalException {

		return getSelectLayoutPageTemplateEntryURL(
			getFirstLayoutPageTemplateCollectionId(), privateLayout);
	}

	public String getSelectLayoutPageTemplateEntryURL(
		long layoutPageTemplateCollectionId, boolean privateLayout) {

		return getSelectLayoutPageTemplateEntryURL(
			layoutPageTemplateCollectionId, LayoutConstants.DEFAULT_PLID,
			privateLayout);
	}

	public String getSelectLayoutPageTemplateEntryURL(
		long layoutPageTemplateCollectionId, long selPlid,
		boolean privateLayout) {

		return getSelectLayoutPageTemplateEntryURL(
			layoutPageTemplateCollectionId, selPlid, "basic-pages",
			privateLayout);
	}

	public String getSelectLayoutPageTemplateEntryURL(
		long layoutPageTemplateCollectionId, long selPlid, String selectedTab,
		boolean privateLayout) {

		PortletURL selectLayoutPageTemplateEntryURL =
			_liferayPortletResponse.createRenderURL();

		selectLayoutPageTemplateEntryURL.setParameter(
			"mvcPath", "/select_layout_page_template_entry.jsp");
		selectLayoutPageTemplateEntryURL.setParameter(
			"navigation", getNavigation());
		selectLayoutPageTemplateEntryURL.setParameter(
			"redirect", getRedirect());
		selectLayoutPageTemplateEntryURL.setParameter(
			"backURL", _themeDisplay.getURLCurrent());
		selectLayoutPageTemplateEntryURL.setParameter(
			"groupId", String.valueOf(getSelGroupId()));
		selectLayoutPageTemplateEntryURL.setParameter(
			"selPlid", String.valueOf(selPlid));
		selectLayoutPageTemplateEntryURL.setParameter(
			"privateLayout", String.valueOf(privateLayout));

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

		Group group = getGroup();

		_tabs1 = ParamUtil.getString(
			_liferayPortletRequest, "tabs1",
			group.isCompany() ? "page-templates" : "pages");

		return _tabs1;
	}

	public int getTotalItems() throws Exception {
		return LayoutLocalServiceUtil.getLayoutsCount(
			getSelGroup(), isPrivatePages());
	}

	public String getViewLayoutURL(Layout layout) throws PortalException {
		return PortalUtil.getLayoutFullURL(layout, _themeDisplay);
	}

	public boolean hasLayouts() {
		int privatePagesCount = LayoutLocalServiceUtil.getLayoutsCount(
			getSelGroup(), true, 0);

		int publicPagesCount = LayoutLocalServiceUtil.getLayoutsCount(
			getSelGroup(), false, 0);

		if ((privatePagesCount + publicPagesCount) > 0) {
			return true;
		}

		return false;
	}

	public boolean isPagesTab() {
		if (Objects.equals(getTabs1(), "pages")) {
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

	public boolean isShowAddChildPageAction(Layout layout)
		throws PortalException {

		return LayoutPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), layout,
			ActionKeys.ADD_LAYOUT);
	}

	public boolean isShowAddRootLayoutButton() throws PortalException {
		return GroupPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), getSelGroup(),
			ActionKeys.ADD_LAYOUT);
	}

	public boolean isShowConfigureAction(Layout layout) throws PortalException {
		return LayoutPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), layout, ActionKeys.UPDATE);
	}

	public boolean isShowCopyLayoutAction(Layout layout)
		throws PortalException {

		if (!isShowAddRootLayoutButton()) {
			return false;
		}

		if (!layout.isTypePortlet()) {
			return false;
		}

		return true;
	}

	public boolean isShowDeleteAction(Layout layout) throws PortalException {
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

	public boolean isShowOrphanPortletsAction(Layout layout)
		throws PortalException {

		if (StagingUtil.isIncomplete(layout)) {
			return false;
		}

		if (!layout.isSupportsEmbeddedPortlets()) {
			return false;
		}

		if (!isShowAddRootLayoutButton()) {
			return false;
		}

		OrphanPortletsDisplayContext orphanPortletsDisplayContext =
			new OrphanPortletsDisplayContext(
				_request, _liferayPortletRequest, _liferayPortletResponse);

		if (ListUtil.isEmpty(
				orphanPortletsDisplayContext.getOrphanPortlets(layout))) {

			return false;
		}

		return true;
	}

	public boolean isShowPermissionsAction(Layout layout)
		throws PortalException {

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

	public boolean isShowPublicPages() {
		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype() || selGroup.isLayoutPrototype()) {
			return false;
		}

		return true;
	}

	private JSONObject _getActionURLsJSONObject(Layout layout)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (isShowAddChildPageAction(layout)) {
			jsonObject.put(
				"addURL",
				getSelectLayoutPageTemplateEntryURL(
					getFirstLayoutPageTemplateCollectionId(), layout.getPlid(),
					layout.isPrivateLayout()));
		}

		if (isShowConfigureAction(layout)) {
			jsonObject.put("configureURL", getConfigureLayoutURL(layout));
		}

		if (isShowCopyLayoutAction(layout)) {
			jsonObject.put("copyLayoutURL", getCopyLayoutURL(layout));
		}

		if (isShowDeleteAction(layout)) {
			jsonObject.put("deleteURL", getDeleteLayoutURL(layout));
		}

		if (isShowConfigureAction(layout)) {
			jsonObject.put("editLayoutURL", getEditLayoutURL(layout));

			if ((layout.getParentLayoutId() ==
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) &&
				(_getHomePagePlid(isPrivatePages()) != layout.getPlid())) {

				jsonObject.put(
					"markAsHomePageLayoutURL",
					getMarkAsHomePageLayoutURL(layout));
			}
		}

		if (isShowOrphanPortletsAction(layout)) {
			jsonObject.put("orphanPortletsURL", getOrphanPortletsURL(layout));
		}

		if (isShowPermissionsAction(layout)) {
			jsonObject.put("permissionsURL", getPermissionsURL(layout));
		}

		jsonObject.put("viewLayoutURL", getViewLayoutURL(layout));

		return jsonObject;
	}

	private long _getActiveLayoutSetBranchId() throws PortalException {
		if (_activeLayoutSetBranchId != null) {
			return _activeLayoutSetBranchId;
		}

		_activeLayoutSetBranchId = ParamUtil.getLong(
			_request, "layoutSetBranchId");

		Layout layout = getSelLayout();

		if (layout != null) {
			LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
				layout);

			if (layoutRevision != null) {
				_activeLayoutSetBranchId =
					layoutRevision.getLayoutSetBranchId();
			}
		}

		List<LayoutSetBranch> layoutSetBranches =
			LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(
				_themeDisplay.getScopeGroupId(), isPrivatePages());

		if ((_activeLayoutSetBranchId == 0) && !layoutSetBranches.isEmpty()) {
			LayoutSetBranch currentUserLayoutSetBranch =
				LayoutSetBranchLocalServiceUtil.getUserLayoutSetBranch(
					_themeDisplay.getUserId(), _themeDisplay.getScopeGroupId(),
					isPrivateLayout(), 0, 0);

			_activeLayoutSetBranchId =
				currentUserLayoutSetBranch.getLayoutSetBranchId();
		}

		if ((_activeLayoutSetBranchId == 0) && !layoutSetBranches.isEmpty()) {
			LayoutSetBranch layoutSetBranch =
				LayoutSetBranchLocalServiceUtil.getMasterLayoutSetBranch(
					_themeDisplay.getScopeGroupId(), isPrivateLayout());

			_activeLayoutSetBranchId = layoutSetBranch.getLayoutSetBranchId();
		}

		return _activeLayoutSetBranchId;
	}

	private JSONObject _getBreadcrumbEntryJSONObject(
		long plid, boolean privateLayout, String title) {

		JSONObject breadcrumbEntryJSONObject =
			JSONFactoryUtil.createJSONObject();

		breadcrumbEntryJSONObject.put("title", title);

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("selPlid", String.valueOf(plid));
		portletURL.setParameter("privateLayout", String.valueOf(privateLayout));

		breadcrumbEntryJSONObject.put("url", portletURL.toString());

		return breadcrumbEntryJSONObject;
	}

	private JSONObject _getFirstColumn(boolean privatePages)
		throws PortalException {

		JSONObject pagesJSONObject = JSONFactoryUtil.createJSONObject();

		pagesJSONObject.put(
			"actionURLs", _getFirstColumnActionURLsJSONObject(privatePages));
		pagesJSONObject.put(
			"active", privatePages ? isPrivatePages() : isPublicPages());
		pagesJSONObject.put("hasChild", true);
		pagesJSONObject.put("plid", LayoutConstants.DEFAULT_PLID);
		pagesJSONObject.put("title", _getTitle(privatePages));

		PortletURL pagesURL = getPortletURL();

		pagesURL.setParameter(
			"navigation", privatePages ? "private-pages" : "public-pages");
		pagesURL.setParameter(
			"selPlid", String.valueOf(LayoutConstants.DEFAULT_PLID));
		pagesURL.setParameter("privateLayout", String.valueOf(privatePages));

		pagesJSONObject.put("url", pagesURL.toString());

		return pagesJSONObject;
	}

	private JSONObject _getFirstColumnActionURLsJSONObject(boolean privatePages)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), getSelGroupId(),
				ActionKeys.MANAGE_LAYOUTS)) {

			PortletURL editLayoutSetURL =
				_liferayPortletResponse.createRenderURL();

			editLayoutSetURL.setParameter("mvcPath", "/edit_layout_set.jsp");
			editLayoutSetURL.setParameter(
				"redirect", _themeDisplay.getURLCurrent());
			editLayoutSetURL.setParameter(
				"backURL", _themeDisplay.getURLCurrent());
			editLayoutSetURL.setParameter(
				"groupId", String.valueOf(_themeDisplay.getScopeGroupId()));
			editLayoutSetURL.setParameter(
				"privateLayout", String.valueOf(privatePages));

			jsonObject.put("configureURL", editLayoutSetURL.toString());
		}

		return jsonObject;
	}

	private long _getHomePagePlid(boolean privateLayout) {
		if (_homePagePlid != null) {
			return _homePagePlid;
		}

		_homePagePlid = LayoutLocalServiceUtil.getDefaultPlid(
			getSelGroupId(), privateLayout);

		return _homePagePlid;
	}

	private String _getHomePageTitle(boolean privateLayout) {
		if (_homePageTitle != null) {
			return _homePageTitle;
		}

		Layout defaultLayout = LayoutLocalServiceUtil.fetchDefaultLayout(
			getSelGroupId(), privateLayout);

		_homePageTitle = defaultLayout.getName(_themeDisplay.getLocale());

		return _homePageTitle;
	}

	private JSONArray _getLayoutColumnsJSONArray() throws Exception {
		JSONArray layoutColumnsJSONArray = JSONFactoryUtil.createJSONArray();

		JSONArray firstColumnJSONArray = JSONFactoryUtil.createJSONArray();

		if (LayoutLocalServiceUtil.hasLayouts(getSelGroup(), false) &&
			isShowPublicPages()) {

			firstColumnJSONArray.put(_getFirstColumn(false));
		}

		if (LayoutLocalServiceUtil.hasLayouts(getSelGroup(), true)) {
			firstColumnJSONArray.put(_getFirstColumn(true));
		}

		layoutColumnsJSONArray.put(firstColumnJSONArray);

		JSONArray layoutSetBranchesJSONArray = _getLayoutSetBranchesJSONArray();

		if (layoutSetBranchesJSONArray.length() > 0) {
			layoutColumnsJSONArray.put(layoutSetBranchesJSONArray);
		}

		JSONArray pagesJSONArray = _getLayoutsJSONArray(0, isPrivatePages());

		if (getSelPlid() == LayoutConstants.DEFAULT_PLID) {
			layoutColumnsJSONArray.put(pagesJSONArray);

			return layoutColumnsJSONArray;
		}

		Layout selLayout = getSelLayout();

		List<Layout> layouts = ListUtil.copy(selLayout.getAncestors());

		Collections.reverse(layouts);

		layouts.add(selLayout);

		for (Layout layout : layouts) {
			layoutColumnsJSONArray.put(
				_getLayoutsJSONArray(
					layout.getParentLayoutId(), selLayout.isPrivateLayout()));
		}

		layoutColumnsJSONArray.put(
			_getLayoutsJSONArray(
				selLayout.getLayoutId(), selLayout.isPrivateLayout()));

		return layoutColumnsJSONArray;
	}

	private JSONArray _getLayoutSetBranchesJSONArray() throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<LayoutSetBranch> layoutSetBranches =
			LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(
				_themeDisplay.getScopeGroupId(), isPrivatePages());

		for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				"active",
				layoutSetBranch.getLayoutSetBranchId() ==
					_getActiveLayoutSetBranchId());
			jsonObject.put("hasChild", true);
			jsonObject.put("plid", LayoutConstants.DEFAULT_PLID);
			jsonObject.put(
				"title", LanguageUtil.get(_request, layoutSetBranch.getName()));

			PortletURL portletURL = getPortletURL();

			portletURL.setParameter(
				"layoutSetBranchId",
				String.valueOf(layoutSetBranch.getLayoutSetBranchId()));
			portletURL.setParameter(
				"privateLayout",
				String.valueOf(layoutSetBranch.isPrivateLayout()));

			jsonObject.put("url", portletURL.toString());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private JSONArray _getLayoutsJSONArray(
			long parentLayoutId, boolean privateLayout)
		throws Exception {

		JSONArray layoutsJSONArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			getSelGroupId(), privateLayout, parentLayoutId, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (Layout layout : layouts) {
			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			boolean visible = GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("visible"), true);

			if (!visible) {
				continue;
			}

			if (_getActiveLayoutSetBranchId() > 0) {
				LayoutRevision layoutRevision =
					LayoutStagingUtil.getLayoutRevision(layout);

				if (layoutRevision.isIncomplete()) {
					continue;
				}
			}

			JSONObject layoutJSONObject = JSONFactoryUtil.createJSONObject();

			layoutJSONObject.put(
				"actionURLs", _getActionURLsJSONObject(layout));
			layoutJSONObject.put("active", _isActive(layout.getPlid()));

			LayoutTypeController layoutTypeController =
				LayoutTypeControllerTracker.getLayoutTypeController(
					layout.getType());

			ResourceBundle layoutTypeResourceBundle =
				ResourceBundleUtil.getBundle(
					"content.Language", _themeDisplay.getLocale(),
					layoutTypeController.getClass());

			layoutJSONObject.put(
				"description",
				LanguageUtil.get(
					_request, layoutTypeResourceBundle,
					"layout.types." + layout.getType()));

			layoutJSONObject.put(
				"homePage",
				_getHomePagePlid(privateLayout) == layout.getPlid());

			int childLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
				getSelGroup(), isPrivatePages(), layout.getLayoutId());

			layoutJSONObject.put("hasChild", childLayoutsCount > 0);

			layoutJSONObject.put(
				"homePageTitle", _getHomePageTitle(privateLayout));
			layoutJSONObject.put("plid", layout.getPlid());

			if (childLayoutsCount > 0) {
				PortletURL portletURL = getPortletURL();

				portletURL.setParameter(
					"selPlid", String.valueOf(layout.getPlid()));
				portletURL.setParameter(
					"layoutSetBranchId",
					String.valueOf(_getActiveLayoutSetBranchId()));
				portletURL.setParameter(
					"privateLayout", String.valueOf(layout.isPrivateLayout()));

				layoutJSONObject.put("url", portletURL.toString());
			}

			layoutJSONObject.put(
				"title", layout.getName(_themeDisplay.getLocale()));

			layoutsJSONArray.put(layoutJSONObject);
		}

		return layoutsJSONArray;
	}

	private String _getTitle(boolean privatePages) {
		String title = "pages";

		if (isShowPublicPages()) {
			if (privatePages) {
				title = "private-pages";
			}
			else {
				title = "public-pages";
			}
		}

		return LanguageUtil.get(_request, title);
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

	private Long _activeLayoutSetBranchId;
	private final GroupDisplayContextHelper _groupDisplayContextHelper;
	private Long _homePagePlid;
	private String _homePageTitle;
	private List<LayoutDescription> _layoutDescriptions;
	private Long _layoutId;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private Long _parentLayoutId;
	private Boolean _privateLayout;
	private String _redirect;
	private final HttpServletRequest _request;
	private String _rootNodeName;
	private Layout _selLayout;
	private LayoutSet _selLayoutSet;
	private Long _selPlid;
	private String _tabs1;
	private final ThemeDisplay _themeDisplay;

}