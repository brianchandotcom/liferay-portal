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

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalServiceUtil;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.item.selector.CETItemSelectorReturnType;
import com.liferay.client.extension.type.item.selector.criterion.CETItemSelectorCriterion;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.helper.LayoutActionsHelper;
import com.liferay.layout.admin.web.internal.util.FaviconUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.theme.item.selector.criterion.LayoutThemeItemSelectorCriterion;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.comparator.LayoutCreateDateComparator;
import com.liferay.layout.util.comparator.LayoutRelevanceComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.RobotsUtil;
import com.liferay.site.display.context.GroupDisplayContextHelper;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutsAdminDisplayContext {

	public LayoutsAdminDisplayContext(
		ItemSelector itemSelector, LayoutActionsHelper layoutActionsHelper,
		LayoutCopyHelper layoutCopyHelper,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_itemSelector = itemSelector;
		_layoutActionsHelper = layoutActionsHelper;
		_layoutCopyHelper = layoutCopyHelper;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_cetManager = (CETManager)httpServletRequest.getAttribute(
			CETManager.class.getName());
		_groupDisplayContextHelper = new GroupDisplayContextHelper(
			httpServletRequest);

		themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getAddLayoutDropdownItems() {
		Group group = getSelGroup();

		if (!group.isPrivateLayoutsEnabled()) {
			return DropdownItemListBuilder.add(
				this::isShowPublicLayouts,
				dropdownItem -> {
					dropdownItem.setHref(
						getSelectLayoutPageTemplateEntryURL(false));
					dropdownItem.setLabel(
						LanguageUtil.get(httpServletRequest, "page"));
				}
			).add(
				this::isShowPublicLayouts,
				dropdownItem -> {
					dropdownItem.setHref(
						getSelectLayoutCollectionURL(
							LayoutConstants.DEFAULT_PLID, null, false));
					dropdownItem.setLabel(
						LanguageUtil.get(
							httpServletRequest, "collection-page"));
				}
			).build();
		}

		return DropdownItemListBuilder.add(
			this::isShowPublicLayouts,
			dropdownItem -> {
				dropdownItem.setHref(
					getSelectLayoutPageTemplateEntryURL(false));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "public-page"));
			}
		).add(
			this::isShowPublicLayouts,
			dropdownItem -> {
				dropdownItem.setHref(
					getSelectLayoutCollectionURL(
						LayoutConstants.DEFAULT_PLID, null, false));
				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "public-collection-page"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(getSelectLayoutPageTemplateEntryURL(true));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "private-page"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					getSelectLayoutCollectionURL(
						LayoutConstants.DEFAULT_PLID, null, true));
				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "private-collection-page"));
			}
		).build();
	}

	public String getAddLayoutURL() {
		PortletURL portletURL = PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setMVCPath(
			"/select_layout_page_template_entry.jsp"
		).setBackURL(
			getBackURL()
		).setPortletResource(
			getPortletResource()
		).setParameter(
			"explicitCreation", true
		).setParameter(
			"groupId", getGroupId()
		).setParameter(
			"liveGroupId", getLiveGroupId()
		).setParameter(
			"parentLayoutId", getParentLayoutId()
		).setParameter(
			"privateLayout", isPrivateLayout()
		).setParameter(
			"stagingGroupId", getStagingGroupId()
		).buildPortletURL();

		String type = ParamUtil.getString(httpServletRequest, "type");

		if (Validator.isNotNull(type)) {
			portletURL.setParameter("type", type);
		}

		long layoutPageTemplateEntryId = ParamUtil.getLong(
			httpServletRequest, "layoutPageTemplateEntryId");

		if (layoutPageTemplateEntryId > 0) {
			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/layout_admin/add_content_layout");
		}
		else {
			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/layout_admin/add_simple_layout");
		}

		portletURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(layoutPageTemplateEntryId));

		long masterLayoutPlid = ParamUtil.getLong(
			httpServletRequest, "masterLayoutPlid");

		portletURL.setParameter(
			"masterLayoutPlid", String.valueOf(masterLayoutPlid));

		if (Objects.equals(type, LayoutConstants.TYPE_COLLECTION)) {
			String collectionPK = ParamUtil.getString(
				httpServletRequest, "collectionPK");

			portletURL.setParameter("collectionPK", collectionPK);

			String collectionType = ParamUtil.getString(
				httpServletRequest, "collectionType");

			portletURL.setParameter("collectionType", collectionType);

			portletURL.setParameter(
				ActionRequest.ACTION_NAME,
				"/layout_admin/add_collection_layout");
		}

		return portletURL.toString();
	}

	public List<SiteNavigationMenu> getAutoSiteNavigationMenus() {
		return SiteNavigationMenuLocalServiceUtil.getAutoSiteNavigationMenus(
			themeDisplay.getScopeGroupId());
	}

	public String getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		String backURL = ParamUtil.getString(_liferayPortletRequest, "backURL");

		if (Validator.isNull(backURL)) {
			backURL = getRedirect();
		}

		_backURL = backURL;

		return _backURL;
	}

	public PortletURL getCETItemSelectorURL(
		boolean multipleSelection, String selectEventName, String type) {

		CETItemSelectorCriterion cetItemSelectorCriterion =
			new CETItemSelectorCriterion();

		cetItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new CETItemSelectorReturnType());
		cetItemSelectorCriterion.setMultipleSelection(multipleSelection);
		cetItemSelectorCriterion.setType(type);

		return _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			selectEventName, cetItemSelectorCriterion);
	}

	public String getConfigurationTitle(Layout layout, Locale locale) {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry.getName();
		}

		return layout.getName(locale);
	}

	public String getConfigureLayoutURL(Layout layout) {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setBackURL(
			_getBackURL()
		).setPortletResource(
			() -> {
				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				return portletDisplay.getId();
			}
		).setParameter(
			"groupId", layout.getGroupId()
		).setParameter(
			"privateLayout", layout.isPrivateLayout()
		).setParameter(
			"selPlid", layout.getPlid()
		).buildString();
	}

	public String getCopyLayoutActionURL(
		boolean copyPermissions, long sourcePlid) {

		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/layout_admin/copy_layout"
		).setRedirect(
			getRedirect()
		).setParameter(
			"copyPermissions", copyPermissions
		).setParameter(
			"explicitCreation", Boolean.TRUE
		).setParameter(
			"groupId", getGroupId()
		).setParameter(
			"liveGroupId", getLiveGroupId()
		).setParameter(
			"privateLayout", isPrivateLayout()
		).setParameter(
			"sourcePlid", sourcePlid
		).setParameter(
			"stagingGroupId", getStagingGroupId()
		).buildString();
	}

	public String getCopyLayoutRenderURL(boolean copyPermissions, Layout layout)
		throws Exception {

		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/layout_admin/add_layout"
		).setParameter(
			"copyPermissions", copyPermissions
		).setParameter(
			"privateLayout", isPrivateLayout()
		).setParameter(
			"sourcePlid", layout.getPlid()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getDeleteLayoutURL(Layout layout) throws PortalException {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/layout_admin/delete_layout"
		).setRedirect(
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setParameter(
				"layoutSetBranchId", getActiveLayoutSetBranchId()
			).setParameter(
				"selPlid", layout.getParentPlid()
			).buildString()
		).setParameter(
			"layoutSetBranchId", getActiveLayoutSetBranchId()
		).setParameter(
			"selPlid", layout.getPlid()
		).buildString();
	}

	public String getDiscardDraftURL(Layout layout) {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/layout_admin/discard_draft_layout"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setParameter(
			"selPlid",
			() -> {
				Layout draftLayout = layout.fetchDraftLayout();

				return draftLayout.getPlid();
			}
		).buildString();
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			"miller-columns");

		return _displayStyle;
	}

	public Layout getDraftLayout(Layout layout) throws Exception {
		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout != null) {
			return draftLayout;
		}

		if (!layout.isTypeContent()) {
			return null;
		}

		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		draftLayout = LayoutLocalServiceUtil.addLayout(
			layout.getUserId(), layout.getGroupId(), layout.isPrivateLayout(),
			layout.getParentLayoutId(), PortalUtil.getClassNameId(Layout.class),
			layout.getPlid(), layout.getNameMap(), layout.getTitleMap(),
			layout.getDescriptionMap(), layout.getKeywordsMap(),
			layout.getRobotsMap(), layout.getType(),
			unicodeProperties.toString(), true, true, Collections.emptyMap(),
			layout.getMasterLayoutPlid(), serviceContext);

		draftLayout = _layoutCopyHelper.copyLayoutContent(layout, draftLayout);

		serviceContext.setAttribute("published", Boolean.TRUE);

		return LayoutLocalServiceUtil.updateStatus(
			draftLayout.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	public String getEditLayoutURL(Layout layout) throws Exception {
		if (layout.isTypeContent()) {
			return _getDraftLayoutURL(layout);
		}

		if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET) ||
			(layout.fetchDraftLayout() == null)) {

			return StringPool.BLANK;
		}

		return _getDraftLayoutURL(layout);
	}

	public String getEditOrViewLayoutURL(Layout layout) throws Exception {
		if ((isConversionDraft(layout) || layout.isTypeContent()) &&
			_layoutActionsHelper.isShowConfigureAction(layout)) {

			return getEditLayoutURL(layout);
		}

		if (_layoutActionsHelper.isShowViewLayoutAction(layout)) {
			return getViewLayoutURL(layout);
		}

		return StringPool.BLANK;
	}

	public Map<String, Object> getFaviconButtonProps() {
		return HashMapBuilder.<String, Object>put(
			"clearButtonEnabled", isClearFaviconButtonEnabled()
		).put(
			"defaultImgURL", _getDefaultFaviconURL()
		).put(
			"defaultTitle", _getDefaultFaviconTitle()
		).put(
			"faviconFileEntryId",
			() -> {
				Layout selLayout = getSelLayout();

				if (selLayout != null) {
					return selLayout.getFaviconFileEntryId();
				}

				LayoutSet selLayoutSet = getSelLayoutSet();

				return selLayoutSet.getFaviconFileEntryId();
			}
		).put(
			"imgURL", getFaviconURL()
		).put(
			"isReadOnly", isReadOnly()
		).put(
			"themeFaviconCETExternalReferenceCode",
			getThemeFaviconCETExternalReferenceCode()
		).put(
			"title", HtmlUtil.escape(getFaviconTitle())
		).put(
			"url", getFileEntryItemSelectorURL()
		).build();
	}

	public String getFaviconTitle() {
		if (getSelLayout() != null) {
			return FaviconUtil.getFaviconTitle(
				_cetManager, getSelLayout(), themeDisplay.getLocale());
		}

		return FaviconUtil.getFaviconTitle(
			getSelLayoutSet(), themeDisplay.getLocale());
	}

	public String getFaviconURL() {
		String faviconURL = StringPool.BLANK;

		if (getSelLayout() != null) {
			faviconURL = FaviconUtil.getFaviconURL(_cetManager, getSelLayout());
		}

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		if (getSelLayoutSet() != null) {
			faviconURL = FaviconUtil.getFaviconURL(
				_cetManager, getSelLayoutSet());
		}

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		return themeDisplay.getPathThemeImages() + "/" +
			PropsUtil.get(PropsKeys.THEME_SHORTCUT_ICON);
	}

	public String getFileEntryItemSelectorURL() {
		FileItemSelectorCriterion itemSelectorCriterion =
			new FileItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		CETItemSelectorCriterion cetItemSelectorCriterion =
			new CETItemSelectorCriterion();

		cetItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new CETItemSelectorReturnType());
		cetItemSelectorCriterion.setType(
			ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

		return String.valueOf(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
				getSelectFaviconEventName(), itemSelectorCriterion,
				cetItemSelectorCriterion));
	}

	public String getFirstColumnConfigureLayoutURL(boolean privatePages) {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout_set"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setBackURL(
			_getBackURL()
		).setParameter(
			"groupId", themeDisplay.getScopeGroupId()
		).setParameter(
			"privateLayout", privatePages
		).setParameter(
			"selPlid", LayoutConstants.DEFAULT_PLID
		).buildString();
	}

	public String getFriendlyURLBase() {
		StringBuilder friendlyURLBase = new StringBuilder();

		friendlyURLBase.append(themeDisplay.getPortalURL());

		Layout selLayout = getSelLayout();

		LayoutSet layoutSet = selLayout.getLayoutSet();

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (virtualHostnames.isEmpty() ||
			!_matchesHostname(friendlyURLBase, virtualHostnames)) {

			Group group = getGroup();

			friendlyURLBase.append(
				group.getPathFriendlyURL(isPrivateLayout(), themeDisplay));
			friendlyURLBase.append(
				HttpComponentsUtil.decodeURL(group.getFriendlyURL()));
		}

		return friendlyURLBase.toString();
	}

	public Group getGroup() {
		return _groupDisplayContextHelper.getGroup();
	}

	public Long getGroupId() {
		return _groupDisplayContextHelper.getGroupId();
	}

	public UnicodeProperties getGroupTypeSettingsUnicodeProperties() {
		return _groupDisplayContextHelper.getGroupTypeSettings();
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(httpServletRequest, "keywords");

		return _keywords;
	}

	public String getLayoutConversionPreviewURL(Layout layout) {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/layout_admin/add_layout_conversion_preview"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setParameter(
			"selPlid", layout.getPlid()
		).buildString();
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

	public PortletURL getLayoutScreenNavigationPortletURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout"
		).setBackURL(
			getBackURL()
		).setPortletResource(
			ParamUtil.getString(httpServletRequest, "portletResource")
		).setParameter(
			"selPlid", getSelPlid()
		).buildPortletURL();
	}

	public PortletURL getLayoutSetScreenNavigationPortletURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout_set"
		).setBackURL(
			getBackURL()
		).setPortletResource(
			ParamUtil.getString(httpServletRequest, "portletResource")
		).setParameter(
			"privateLayout", isPrivateLayout()
		).buildPortletURL();
	}

	public SearchContainer<Layout> getLayoutsSearchContainer()
		throws PortalException {

		if (_layoutsSearchContainer != null) {
			return _layoutsSearchContainer;
		}

		String emptyResultMessage = "there-are-no-pages";

		Group group = themeDisplay.getScopeGroup();

		if (group.isPrivateLayoutsEnabled()) {
			emptyResultMessage = "there-are-no-public-pages";

			if (isPrivateLayout()) {
				emptyResultMessage = "there-are-no-private-pages";
			}
		}

		SearchContainer<Layout> layoutsSearchContainer = new SearchContainer(
			_liferayPortletRequest, getPortletURL(), null, emptyResultMessage);

		layoutsSearchContainer.setOrderByCol(_getOrderByCol());

		boolean orderByAsc = false;

		if (Objects.equals(_getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		OrderByComparator<Layout> orderByComparator = null;

		String keywords = getKeywords();

		if (Objects.equals(_getOrderByCol(), "create-date")) {
			orderByComparator = new LayoutCreateDateComparator(orderByAsc);
		}
		else if (Objects.equals(_getOrderByCol(), "relevance") &&
				 Validator.isNotNull(keywords)) {

			orderByComparator = new LayoutRelevanceComparator(orderByAsc);
		}

		layoutsSearchContainer.setOrderByComparator(orderByComparator);
		layoutsSearchContainer.setOrderByType(_getOrderByType());

		int[] statuses = null;

		if (Validator.isNotNull(keywords)) {
			statuses = new int[] {WorkflowConstants.STATUS_ANY};
		}

		int[] layoutStatuses = statuses;

		layoutsSearchContainer.setResultsAndTotal(
			() -> LayoutServiceUtil.getLayouts(
				getSelGroupId(), isPrivateLayout(), keywords, _getTypes(),
				layoutStatuses, layoutsSearchContainer.getStart(),
				layoutsSearchContainer.getEnd(),
				layoutsSearchContainer.getOrderByComparator()),
			LayoutServiceUtil.getLayoutsCount(
				getSelGroupId(), isPrivateLayout(), keywords, _getTypes(),
				layoutStatuses));

		layoutsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		_layoutsSearchContainer = layoutsSearchContainer;

		return _layoutsSearchContainer;
	}

	public Group getLiveGroup() {
		return _groupDisplayContextHelper.getLiveGroup();
	}

	public Long getLiveGroupId() {
		return _groupDisplayContextHelper.getLiveGroupId();
	}

	public String getMoveLayoutColumnItemURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/layout_admin/move_layout"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).buildString();
	}

	public String getNameMapAsXML() {
		Layout layout = getSelLayout();

		Map<Locale, String> nameMap = layout.getNameMap();

		Locale siteDefaultLocale = LocaleUtil.getSiteDefault();

		if (MapUtil.isNotEmpty(nameMap) &&
			!nameMap.containsKey(siteDefaultLocale)) {

			String name = nameMap.get(layout.getDefaultLanguageId());

			if (name == null) {
				Collection<String> values = nameMap.values();

				Iterator<String> iterator = values.iterator();

				name = iterator.next();
			}

			nameMap.put(siteDefaultLocale, name);
		}

		return LocalizationUtil.updateLocalization(
			nameMap, StringPool.BLANK, "Name",
			LocaleUtil.toLanguageId(siteDefaultLocale));
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(
					!Objects.equals(getTabs1(), "utility-pages"));
				navigationItem.setHref(getPortletURL(), "tabs1", "");
				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "static-pages"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(
					Objects.equals(getTabs1(), "utility-pages"));
				navigationItem.setHref(
					getPortletURL(), "tabs1", "utility-pages");
				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "utility-pages"));
			}
		).build();
	}

	public String getOrphanPortletsURL(Layout layout) {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/orphan_portlets.jsp"
		).setBackURL(
			_getBackURL()
		).setParameter(
			"selPlid", layout.getPlid()
		).buildString();
	}

	public long getParentLayoutId() {
		if (_parentLayoutId != null) {
			return _parentLayoutId;
		}

		_parentLayoutId = 0L;

		Layout layout = getSelLayout();

		if (layout != null) {
			_parentLayoutId = layout.getLayoutId();
		}

		return _parentLayoutId;
	}

	public String getPermissionsURL(Layout layout) throws Exception {
		return PermissionsURLTag.doTag(
			StringPool.BLANK, Layout.class.getName(),
			HtmlUtil.escape(layout.getName(themeDisplay.getLocale())), null,
			String.valueOf(layout.getPlid()),
			LiferayWindowState.POP_UP.toString(), null,
			themeDisplay.getRequest());
	}

	public List<BreadcrumbEntry> getPortletBreadcrumbEntries()
		throws PortalException {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		boolean privatePages = isPrivateLayout();

		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			privatePages = selLayout.isPrivateLayout();
		}

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(LanguageUtil.get(httpServletRequest, "pages"));
		breadcrumbEntry.setURL(
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setTabs1(
				getTabs1()
			).setParameter(
				"displayStyle",
				() -> {
					String displayStyle = getDisplayStyle();

					if (Validator.isNotNull(displayStyle)) {
						return displayStyle;
					}

					return null;
				}
			).setParameter(
				"firstColumn", true
			).setParameter(
				"selPlid", LayoutConstants.DEFAULT_PLID
			).buildString());

		breadcrumbEntries.add(breadcrumbEntry);

		if (isFirstColumn()) {
			return breadcrumbEntries;
		}

		if (isPrivateLayoutsEnabled()) {
			breadcrumbEntries.add(
				_getBreadcrumbEntry(
					LayoutConstants.DEFAULT_PLID, privatePages,
					getTitle(privatePages)));
		}

		if ((getSelPlid() == LayoutConstants.DEFAULT_PLID) ||
			(selLayout == null)) {

			return breadcrumbEntries;
		}

		List<Layout> layouts = selLayout.getAncestors();

		Collections.reverse(layouts);

		for (Layout layout : layouts) {
			breadcrumbEntries.add(
				_getBreadcrumbEntry(
					layout.getPlid(), layout.isPrivateLayout(),
					layout.getName(themeDisplay.getLocale())));
		}

		breadcrumbEntries.add(
			_getBreadcrumbEntry(
				selLayout.getPlid(), selLayout.isPrivateLayout(),
				selLayout.getName(themeDisplay.getLocale())));

		return breadcrumbEntries;
	}

	public String getPortletResource() {
		String portletResource = ParamUtil.getString(
			httpServletRequest, "portletResource");

		if (Validator.isNull(portletResource)) {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			portletResource = portletDisplay.getPortletName();
		}

		return portletResource;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setTabs1(
			getTabs1()
		).setParameter(
			"displayStyle",
			() -> {
				String displayStyle = getDisplayStyle();

				if (Validator.isNotNull(displayStyle)) {
					return displayStyle;
				}

				return null;
			}
		).setParameter(
			"privateLayout", isPrivateLayout()
		).buildPortletURL();
	}

	public String getPreviewCurrentDesignURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/preview_current_design.jsp"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setParameter(
			"readOnly", true
		).buildString();
	}

	public String getPreviewDraftURL(Layout layout) throws PortalException {
		return PortalUtil.getLayoutFriendlyURL(
			layout.fetchDraftLayout(), themeDisplay);
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(
			_liferayPortletRequest, "redirect", _getBackURL());

		return _redirect;
	}

	public PortletURL getRedirectURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setRedirect(
			getRedirect()
		).setParameter(
			"groupId", getSelGroupId()
		).buildPortletURL();
	}

	public List<BreadcrumbEntry> getRelativeBreadcrumbEntries(Layout layout)
		throws PortalException {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		List<Layout> ancestorLayouts = layout.getAncestors();

		Collections.reverse(ancestorLayouts);

		for (Layout ancestorLayout : ancestorLayouts) {
			BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

			if (LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), ancestorLayout,
					ActionKeys.VIEW)) {

				breadcrumbEntry.setTitle(
					ancestorLayout.getName(themeDisplay.getLocale()));
				breadcrumbEntry.setURL(
					PortletURLBuilder.create(
						getPortletURL()
					).setParameter(
						"privateLayout", ancestorLayout.isPrivateLayout()
					).setParameter(
						"selPlid", ancestorLayout.getPlid()
					).buildString());
			}
			else {
				breadcrumbEntry.setTitle(StringPool.TRIPLE_PERIOD);
			}

			breadcrumbEntries.add(breadcrumbEntry);
		}

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(layout.getName(themeDisplay.getLocale()));

		breadcrumbEntries.add(breadcrumbEntry);

		return breadcrumbEntries;
	}

	public String getRobots() {
		return ParamUtil.getString(
			httpServletRequest, "robots", _getStrictRobots());
	}

	public String getSelectFaviconEventName() {
		return _liferayPortletResponse.getNamespace() + "selectImage";
	}

	public String getSelectLayoutCollectionURL(
		long selPlid, String selectedTab, boolean privateLayout) {

		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/select_layout_collections.jsp"
		).setRedirect(
			getRedirect()
		).setBackURL(
			_getBackURL()
		).setParameter(
			"groupId", getSelGroupId()
		).setParameter(
			"privateLayout", privateLayout
		).setParameter(
			"selectedTab",
			() -> {
				if (Validator.isNotNull(selectedTab)) {
					return selectedTab;
				}

				return null;
			}
		).setParameter(
			"selPlid", selPlid
		).buildString();
	}

	public String getSelectLayoutPageTemplateEntryURL(boolean privateLayout) {
		return getSelectLayoutPageTemplateEntryURL(0, privateLayout);
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
			layoutPageTemplateCollectionId, selPlid, "basic-templates",
			privateLayout);
	}

	public String getSelectLayoutPageTemplateEntryURL(
		long layoutPageTemplateCollectionId, long selPlid, String selectedTab,
		boolean privateLayout) {

		PortletURL selectLayoutPageTemplateEntryURL =
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCPath(
				"/select_layout_page_template_entry.jsp"
			).setRedirect(
				getRedirect()
			).setBackURL(
				_getBackURL()
			).setParameter(
				"groupId", getSelGroupId()
			).setParameter(
				"privateLayout", privateLayout
			).setParameter(
				"selPlid", selPlid
			).buildPortletURL();

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

	public String getSelectThemeURL() {
		LayoutThemeItemSelectorCriterion layoutThemeItemSelectorCriterion =
			new LayoutThemeItemSelectorCriterion();

		layoutThemeItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.singletonList(new UUIDItemSelectorReturnType()));

		return String.valueOf(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(
					_liferayPortletRequest),
				_liferayPortletResponse.getNamespace() + "selectTheme",
				layoutThemeItemSelectorCriterion));
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

	public LayoutSet getSelLayoutSet() {
		if (_selLayoutSet != null) {
			return _selLayoutSet;
		}

		Group group = getStagingGroup();

		if (group == null) {
			group = getLiveGroup();
		}

		_selLayoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
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

	public String getStyleBookWarningMessage() {
		LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
			getSelGroupId(), false);

		String themeId = _getThemeId();

		if (Validator.isNull(themeId) ||
			Objects.equals(publicLayoutSet.getThemeId(), themeId) ||
			((_selLayout == null) && !_selLayoutSet.isPrivateLayout())) {

			return StringPool.BLANK;
		}

		if (_selLayout != null) {
			Group group = getGroup();

			if (group.isPrivateLayoutsEnabled()) {
				return LanguageUtil.get(
					httpServletRequest,
					"this-page-is-using-a-different-theme-than-the-one-set-" +
						"for-public-pages");
			}

			return LanguageUtil.get(
				httpServletRequest,
				"this-page-is-using-a-different-theme-than-the-one-set-for-" +
					"all-pages");
		}

		return LanguageUtil.format(
			httpServletRequest,
			"private-pages-is-using-a-different-theme-than-the-one-set-for-x-" +
				"public-pages-x",
			new String[] {
				"<a href =\"" +
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCRenderCommandName(
						"/layout_admin/edit_layout_set"
					).setRedirect(
						PortalUtil.getCurrentURL(httpServletRequest)
					).setBackURL(
						_backURL
					).setParameter(
						"groupId", themeDisplay.getScopeGroupId()
					).setParameter(
						"privateLayout", false
					).setWindowState(
						LiferayWindowState.MAXIMIZED
					).buildString() + "\">",
				"</a>"
			});
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_liferayPortletRequest, "tabs1", "pages");

		return _tabs1;
	}

	public String getTarget(Layout layout) {
		return HtmlUtil.escape(layout.getTypeSettingsProperty("target"));
	}

	public Map<String, Object> getThemeCSSReplacementSelectorProps()
		throws PortalException {

		String selectThemeCSSClientExtensionEventName =
			"selectThemeCSSClientExtension";

		LayoutSet setLayoutSet = getSelLayoutSet();

		String className = LayoutSet.class.getName();
		long classPK = setLayoutSet.getLayoutSetId();

		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			className = Layout.class.getName();
			classPK = selLayout.getPlid();
		}

		ClientExtensionEntryRel clientExtensionEntryRel =
			ClientExtensionEntryRelLocalServiceUtil.
				fetchClientExtensionEntryRel(
					PortalUtil.getClassNameId(className), classPK,
					ClientExtensionEntryConstants.TYPE_THEME_CSS);

		return HashMapBuilder.<String, Object>put(
			"isReadOnly", isReadOnly()
		).put(
			"placeholder", _getPlaceholder()
		).put(
			"selectThemeCSSClientExtensionEventName",
			selectThemeCSSClientExtensionEventName
		).put(
			"selectThemeCSSClientExtensionURL",
			() -> String.valueOf(
				getCETItemSelectorURL(
					false, selectThemeCSSClientExtensionEventName,
					ClientExtensionEntryConstants.TYPE_THEME_CSS))
		).put(
			"themeCSSCETExternalReferenceCode",
			() -> {
				if (clientExtensionEntryRel != null) {
					return clientExtensionEntryRel.
						getCETExternalReferenceCode();
				}

				return StringPool.BLANK;
			}
		).put(
			"themeCSSExtensionName",
			() -> {
				if (clientExtensionEntryRel != null) {
					CET cet = _cetManager.getCET(
						themeDisplay.getCompanyId(),
						clientExtensionEntryRel.getCETExternalReferenceCode());

					if (cet != null) {
						return cet.getName(themeDisplay.getLocale());
					}
				}

				return StringPool.BLANK;
			}
		).build();
	}

	public String getThemeFaviconCETExternalReferenceCode() {
		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			ClientExtensionEntryRel clientExtensionEntryRel =
				ClientExtensionEntryRelLocalServiceUtil.
					fetchClientExtensionEntryRel(
						PortalUtil.getClassNameId(Layout.class),
						selLayout.getPlid(),
						ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

			if (clientExtensionEntryRel != null) {
				return clientExtensionEntryRel.getCETExternalReferenceCode();
			}
		}

		LayoutSet setLayoutSet = getSelLayoutSet();

		ClientExtensionEntryRel clientExtensionEntryRel =
			ClientExtensionEntryRelLocalServiceUtil.
				fetchClientExtensionEntryRel(
					PortalUtil.getClassNameId(LayoutSet.class),
					setLayoutSet.getLayoutSetId(),
					ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel.getCETExternalReferenceCode();
		}

		return StringPool.BLANK;
	}

	public String getTitle(boolean privatePages) {
		String title = "pages";

		if (isShowPublicLayouts() && isPrivateLayoutsEnabled()) {
			if (privatePages) {
				title = "private-pages";
			}
			else {
				title = "public-pages";
			}
		}

		return LanguageUtil.get(httpServletRequest, title);
	}

	public String getViewCollectionItemsURL(Layout layout)
		throws PortalException, WindowStateException {

		if (!Objects.equals(
				layout.getType(), LayoutConstants.TYPE_COLLECTION)) {

			return null;
		}

		String collectionType = layout.getTypeSettingsProperty(
			"collectionType");

		if (Validator.isNull(collectionType)) {
			return null;
		}

		String collectionPK = layout.getTypeSettingsProperty("collectionPK");

		if (Validator.isNull(collectionPK)) {
			return null;
		}

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			_liferayPortletRequest, AssetListEntry.class.getName(),
			PortletProvider.Action.BROWSE);

		if (portletURL == null) {
			return null;
		}

		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setParameter("collectionPK", collectionPK);
		portletURL.setParameter("collectionType", collectionType);
		portletURL.setParameter("showActions", String.valueOf(Boolean.TRUE));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public String getViewLayoutURL(Layout layout) throws PortalException {
		String layoutFullURL = null;

		if (layout.isDenied() || layout.isPending()) {
			layoutFullURL = PortalUtil.getLayoutFullURL(
				layout.fetchDraftLayout(), themeDisplay);
		}
		else {
			layoutFullURL = PortalUtil.getLayoutFullURL(layout, themeDisplay);
		}

		try {
			layoutFullURL = HttpComponentsUtil.setParameter(
				layoutFullURL, "p_l_back_url", _getBackURL());
		}
		catch (Exception exception) {
			_log.error(
				"Unable to generate view layout URL for " + layoutFullURL,
				exception);
		}

		return layoutFullURL;
	}

	public String getVirtualHostname() {
		LayoutSet layoutSet = getSelLayoutSet();

		if (layoutSet == null) {
			return StringPool.BLANK;
		}

		String virtualHostname = null;

		TreeMap<String, String> virtualHostnames =
			PortalUtil.getVirtualHostnames(layoutSet);

		if (!virtualHostnames.isEmpty()) {
			virtualHostname = virtualHostnames.firstKey();
		}

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (Validator.isNull(virtualHostname) && scopeGroup.isStagingGroup()) {
			Group liveGroup = scopeGroup.getLiveGroup();

			LayoutSet liveGroupLayoutSet = liveGroup.getPublicLayoutSet();

			if (layoutSet.isPrivateLayout()) {
				liveGroupLayoutSet = liveGroup.getPrivateLayoutSet();
			}

			virtualHostname = null;

			virtualHostnames = PortalUtil.getVirtualHostnames(
				liveGroupLayoutSet);

			if (!virtualHostnames.isEmpty()) {
				virtualHostname = virtualHostnames.firstKey();
			}
		}

		return virtualHostname;
	}

	public boolean hasEditableMasterLayout() {
		if (_hasEditableMasterLayout != null) {
			return _hasEditableMasterLayout;
		}

		boolean hasEditableMasterLayout = false;

		Layout selLayout = getSelLayout();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(selLayout.getPlid());

		if (layoutPageTemplateEntry == null) {
			layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(selLayout.getClassPK());
		}

		if ((layoutPageTemplateEntry == null) ||
			!Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {

			hasEditableMasterLayout = true;
		}

		_hasEditableMasterLayout = hasEditableMasterLayout;

		return _hasEditableMasterLayout;
	}

	public boolean hasLayouts() {
		if (_hasLayouts != null) {
			return _hasLayouts;
		}

		boolean hasLayouts = false;

		if ((_getLayoutsCount(true) > 0) || (_getLayoutsCount(false) > 0)) {
			hasLayouts = true;
		}

		_hasLayouts = hasLayouts;

		return _hasLayouts;
	}

	public boolean hasRequiredVocabularies() {
		long classNameId = PortalUtil.getClassNameId(Layout.class);

		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(_getGroupIds());

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			if (assetVocabulary.isAssociatedToClassNameId(classNameId) &&
				assetVocabulary.isRequired(classNameId, 0)) {

				return true;
			}
		}

		return false;
	}

	public boolean isClearFaviconButtonEnabled() {
		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			if (selLayout.getFaviconFileEntryId() > 0) {
				return true;
			}

			ClientExtensionEntryRel clientExtensionEntryRel =
				ClientExtensionEntryRelLocalServiceUtil.
					fetchClientExtensionEntryRel(
						PortalUtil.getClassNameId(Layout.class),
						selLayout.getPlid(),
						ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

			if (clientExtensionEntryRel != null) {
				return true;
			}

			return false;
		}

		LayoutSet selLayoutSet = getSelLayoutSet();

		if (selLayoutSet.getFaviconFileEntryId() > 0) {
			return true;
		}

		ClientExtensionEntryRel clientExtensionEntryRel =
			ClientExtensionEntryRelLocalServiceUtil.
				fetchClientExtensionEntryRel(
					PortalUtil.getClassNameId(LayoutSet.class),
					selLayoutSet.getLayoutSetId(),
					ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

		if (clientExtensionEntryRel != null) {
			return true;
		}

		return false;
	}

	public boolean isConversionDraft(Layout layout) {
		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET) &&
			(layout.fetchDraftLayout() != null)) {

			return true;
		}

		return false;
	}

	public boolean isDraft() {
		Layout layout = getSelLayout();

		if (layout.isDraftLayout() && layout.isSystem()) {
			return true;
		}

		return false;
	}

	public boolean isFirstColumn() {
		if (_firstColumn != null) {
			return _firstColumn;
		}

		_firstColumn = ParamUtil.getBoolean(httpServletRequest, "firstColumn");

		return _firstColumn;
	}

	public boolean isLayoutPageTemplateEntry() {
		Layout layout = getSelLayout();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if (layout.isTypeAssetDisplay() ||
			((layoutPageTemplateEntry != null) && layout.isSystem())) {

			return true;
		}

		return false;
	}

	public boolean isPrivateLayout() {
		if (_privateLayout != null) {
			return _privateLayout;
		}

		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype()) {
			_privateLayout = true;

			return _privateLayout;
		}

		if (getSelLayout() != null) {
			Layout selLayout = getSelLayout();

			_privateLayout = selLayout.isPrivateLayout();

			return _privateLayout;
		}

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeControlPanel()) {
			_privateLayout = layout.isPrivateLayout();

			return _privateLayout;
		}

		String privateLayoutString = _liferayPortletRequest.getParameter(
			"privateLayout");

		if (Validator.isNotNull(privateLayoutString)) {
			_privateLayout = GetterUtil.getBoolean(privateLayoutString);

			return _privateLayout;
		}

		Boolean privateLayout = false;

		if ((_getLayoutsCount(true) > 0) && (_getLayoutsCount(false) <= 0)) {
			privateLayout = true;
		}

		_privateLayout = privateLayout;

		return _privateLayout;
	}

	public boolean isPrivateLayoutsEnabled() {
		if (_privateLayoutsEnabled != null) {
			return _privateLayoutsEnabled;
		}

		Group group = getSelGroup();

		if (group.isPrivateLayoutsEnabled()) {
			_privateLayoutsEnabled = true;
		}
		else {
			_privateLayoutsEnabled = false;
		}

		return _privateLayoutsEnabled;
	}

	public boolean isReadOnly() {
		if (_readOnly != null) {
			return _readOnly;
		}

		_readOnly = false;

		if (!FeatureFlagManagerUtil.isEnabled("LPS-153951")) {
			return _readOnly;
		}

		_readOnly = ParamUtil.getBoolean(_liferayPortletRequest, "readOnly");

		return _readOnly;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowAddChildPageAction(Layout layout)
		throws PortalException {

		return _layoutActionsHelper.isShowAddChildPageAction(layout);
	}

	public boolean isShowAddRootLayoutButton() throws PortalException {
		return _layoutActionsHelper.isShowAddRootLayoutButton(getSelGroup());
	}

	public boolean isShowCategorization() {
		long classNameId = PortalUtil.getClassNameId(Layout.class);

		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(_getGroupIds());

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			if (assetVocabulary.isAssociatedToClassNameId(classNameId) &&
				assetVocabulary.isRequired(classNameId, 0)) {

				int assetVocabularyCategoriesCount =
					AssetCategoryServiceUtil.getVocabularyCategoriesCount(
						assetVocabulary.getGroupId(),
						assetVocabulary.getVocabularyId());

				if (assetVocabularyCategoriesCount > 0) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isShowFirstColumnConfigureAction() throws PortalException {
		if (!GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), getSelGroupId(),
				ActionKeys.MANAGE_LAYOUTS)) {

			return false;
		}

		return true;
	}

	public boolean isShowPublicLayouts() {
		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype() || selGroup.isLayoutPrototype()) {
			return false;
		}

		return true;
	}

	public boolean isShowPublishedConfigurationMessage() {
		Layout selLayout = getSelLayout();

		Layout draftLayout = selLayout.fetchDraftLayout();

		if (draftLayout == null) {
			return false;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			draftLayout.getTypeSettingsProperties();

		if (GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty(
					"designConfigurationModified"))) {

			return true;
		}

		return false;
	}

	public boolean isShowUserPrivateLayouts() throws PortalException {
		Group selGroup = getSelGroup();

		if (selGroup.isUser()) {
			if (!PrefsPropsUtil.getBoolean(
					themeDisplay.getCompanyId(),
					PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED)) {

				return false;
			}
			else if (PropsValues.
						LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED) {

				boolean hasPowerUserRole = RoleLocalServiceUtil.hasUserRole(
					selGroup.getClassPK(), selGroup.getCompanyId(),
					RoleConstants.POWER_USER, true);

				if (!hasPowerUserRole) {
					return false;
				}
			}
		}

		if (!selGroup.isLayoutSetPrototype() &&
			!selGroup.isPrivateLayoutsEnabled()) {

			return false;
		}

		return true;
	}

	protected long getActiveLayoutSetBranchId() throws PortalException {
		if (_activeLayoutSetBranchId != null) {
			return _activeLayoutSetBranchId;
		}

		_activeLayoutSetBranchId = ParamUtil.getLong(
			httpServletRequest, "layoutSetBranchId");

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
				themeDisplay.getScopeGroupId(), isPrivateLayout());

		if ((_activeLayoutSetBranchId == 0) && !layoutSetBranches.isEmpty()) {
			LayoutSetBranch currentUserLayoutSetBranch =
				LayoutSetBranchLocalServiceUtil.getUserLayoutSetBranch(
					themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
					isPrivateLayout(), 0, 0);

			_activeLayoutSetBranchId =
				currentUserLayoutSetBranch.getLayoutSetBranchId();
		}

		if ((_activeLayoutSetBranchId == 0) && !layoutSetBranches.isEmpty()) {
			LayoutSetBranch layoutSetBranch =
				LayoutSetBranchLocalServiceUtil.getMasterLayoutSetBranch(
					themeDisplay.getScopeGroupId(), isPrivateLayout());

			_activeLayoutSetBranchId = layoutSetBranch.getLayoutSetBranchId();
		}

		return _activeLayoutSetBranchId;
	}

	protected List<String> getAvailableActions(Layout layout)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (_layoutActionsHelper.isShowConvertLayoutAction(layout)) {
			availableActions.add("convertSelectedPages");
		}

		if (LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), layout,
				ActionKeys.DELETE)) {

			availableActions.add("deleteSelectedPages");
		}

		if (_layoutActionsHelper.isShowExportTranslationAction(layout)) {
			availableActions.add("exportTranslation");
		}

		return availableActions;
	}

	protected boolean isActive(long plid) throws PortalException {
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

	protected final HttpServletRequest httpServletRequest;
	protected final ThemeDisplay themeDisplay;

	private String _getBackURL() {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, getGroup(),
			LayoutAdminPortletKeys.GROUP_PAGES, 0, 0,
			PortletRequest.RENDER_PHASE);

		return portletURL.toString();
	}

	private BreadcrumbEntry _getBreadcrumbEntry(
		long plid, boolean privateLayout, String title) {

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(title);
		breadcrumbEntry.setURL(
			PortletURLBuilder.create(
				getPortletURL()
			).setParameter(
				"privateLayout", privateLayout
			).setParameter(
				"selPlid", plid
			).buildString());

		return breadcrumbEntry;
	}

	private String _getDefaultFaviconTitle() {
		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			if (hasEditableMasterLayout() &&
				(selLayout.getMasterLayoutPlid() > 0)) {

				Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
					selLayout.getMasterLayoutPlid());

				if (masterLayout != null) {
					ClientExtensionEntryRel clientExtensionEntryRel =
						ClientExtensionEntryRelLocalServiceUtil.
							fetchClientExtensionEntryRel(
								PortalUtil.getClassNameId(Layout.class),
								selLayout.getPlid(),
								ClientExtensionEntryConstants.
									TYPE_THEME_FAVICON);

					if ((masterLayout.getFaviconFileEntryId() > 0) ||
						(clientExtensionEntryRel != null)) {

						return LanguageUtil.get(
							httpServletRequest, "favicon-from-master");
					}
				}
			}
			else {
				return FaviconUtil.getFaviconTitle(
					getSelLayoutSet(), themeDisplay.getLocale());
			}
		}

		return LanguageUtil.get(httpServletRequest, "favicon-from-theme");
	}

	private String _getDefaultFaviconURL() {
		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			if (hasEditableMasterLayout() &&
				(selLayout.getMasterLayoutPlid() > 0)) {

				Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
					selLayout.getMasterLayoutPlid());

				if (masterLayout != null) {
					String faviconURL = FaviconUtil.getFaviconURL(
						_cetManager, masterLayout);

					if (Validator.isNotNull(faviconURL)) {
						return faviconURL;
					}
				}
			}
			else {
				String faviconURL = FaviconUtil.getFaviconURL(
					_cetManager, getSelLayoutSet());

				if (Validator.isNotNull(faviconURL)) {
					return faviconURL;
				}
			}
		}

		return themeDisplay.getPathThemeImages() + "/" +
			PropsUtil.get(PropsKeys.THEME_SHORTCUT_ICON);
	}

	private String _getDraftLayoutURL(Layout layout) throws Exception {
		String layoutFullURL = HttpComponentsUtil.setParameter(
			PortalUtil.getLayoutFullURL(getDraftLayout(layout), themeDisplay),
			"p_l_back_url", _getBackURL());

		return HttpComponentsUtil.setParameter(
			layoutFullURL, "p_l_mode", Constants.EDIT);
	}

	private long[] _getGroupIds() {
		try {
			return PortalUtil.getCurrentAndAncestorSiteGroupIds(
				themeDisplay.getScopeGroupId());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return new long[0];
	}

	private int _getLayoutsCount(boolean privateLayouts) {
		try {
			if (GroupPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), getSelGroupId(),
					ActionKeys.MANAGE_LAYOUTS)) {

				return LayoutLocalServiceUtil.getLayoutsCount(
					getSelGroup(), privateLayouts, 0);
			}

			return LayoutServiceUtil.getLayoutsCount(
				getSelGroupId(), privateLayouts, 0);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return 0;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		String defaultOrderByCol = "create-date";

		if (isSearch()) {
			defaultOrderByCol = "relevance";
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			defaultOrderByCol);

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Objects.equals(_getOrderByCol(), "relevance")) {
			return "desc";
		}

		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES, "asc");

		return _orderByType;
	}

	private String _getPlaceholder() throws PortalException {
		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			ClientExtensionEntryRel clientExtensionEntryRel =
				ClientExtensionEntryRelLocalServiceUtil.
					fetchClientExtensionEntryRel(
						PortalUtil.getClassNameId(Layout.class),
						selLayout.getMasterLayoutPlid(),
						ClientExtensionEntryConstants.TYPE_THEME_CSS);

			if (clientExtensionEntryRel != null) {
				return LanguageUtil.get(
					themeDisplay.getLocale(),
					"theme-css-is-inherited-from-master");
			}
		}

		LayoutSet selLayoutSet = getSelLayoutSet();

		ClientExtensionEntryRel clientExtensionEntryRel =
			ClientExtensionEntryRelLocalServiceUtil.
				fetchClientExtensionEntryRel(
					PortalUtil.getClassNameId(LayoutSet.class),
					selLayoutSet.getLayoutSetId(),
					ClientExtensionEntryConstants.TYPE_THEME_CSS);

		if (clientExtensionEntryRel != null) {
			Group group = selLayoutSet.getGroup();

			return LanguageUtil.format(
				themeDisplay.getLocale(), "theme-css-is-inherited-from-x",
				group.getLayoutRootNodeName(
					selLayoutSet.isPrivateLayout(), themeDisplay.getLocale()),
				false);
		}

		return LanguageUtil.get(
			themeDisplay.getLocale(),
			"no-theme-css-client-extension-was-loaded");
	}

	private String _getStrictRobots() {
		LayoutSet layoutSet = getSelLayoutSet();

		if (layoutSet != null) {
			try {
				return GetterUtil.getString(
					layoutSet.getSettingsProperty(
						layoutSet.isPrivateLayout() + "-robots.txt"),
					StringUtil.read(
						RobotsUtil.class.getClassLoader(),
						PropsValues.ROBOTS_TXT_WITH_SITEMAP));
			}
			catch (IOException ioException) {
				_log.error(
					"Unable to read the content for " +
						PropsValues.ROBOTS_TXT_WITH_SITEMAP,
					ioException);
			}
		}

		try {
			return StringUtil.read(
				RobotsUtil.class.getClassLoader(),
				PropsValues.ROBOTS_TXT_WITHOUT_SITEMAP);
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to read the content for " +
					PropsValues.ROBOTS_TXT_WITHOUT_SITEMAP,
				ioException);

			return null;
		}
	}

	private String _getThemeId() {
		if (_themeId != null) {
			return _themeId;
		}

		String themeId = ParamUtil.getString(httpServletRequest, "themeId");

		if (Validator.isNull(themeId)) {
			if (_selLayout == null) {
				themeId = _selLayoutSet.getThemeId();
			}
			else {
				themeId = _selLayout.getThemeId();
			}
		}

		_themeId = themeId;

		return _themeId;
	}

	private String[] _getTypes() {
		if (_types != null) {
			return _types;
		}

		_types = new String[] {
			LayoutConstants.TYPE_COLLECTION, LayoutConstants.TYPE_CONTENT,
			LayoutConstants.TYPE_EMBEDDED, LayoutConstants.TYPE_LINK_TO_LAYOUT,
			LayoutConstants.TYPE_FULL_PAGE_APPLICATION,
			LayoutConstants.TYPE_NODE, LayoutConstants.TYPE_PANEL,
			LayoutConstants.TYPE_PORTLET, LayoutConstants.TYPE_URL
		};

		return _types;
	}

	private boolean _matchesHostname(
		StringBuilder friendlyURLBase,
		TreeMap<String, String> virtualHostnames) {

		for (String virtualHostname : virtualHostnames.keySet()) {
			if (friendlyURLBase.indexOf(virtualHostname) != -1) {
				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutsAdminDisplayContext.class);

	private Long _activeLayoutSetBranchId;
	private String _backURL;
	private final CETManager _cetManager;
	private String _displayStyle;
	private Boolean _firstColumn;
	private final GroupDisplayContextHelper _groupDisplayContextHelper;
	private Boolean _hasEditableMasterLayout;
	private Boolean _hasLayouts;
	private final ItemSelector _itemSelector;
	private String _keywords;
	private final LayoutActionsHelper _layoutActionsHelper;
	private final LayoutCopyHelper _layoutCopyHelper;
	private Long _layoutId;
	private SearchContainer<Layout> _layoutsSearchContainer;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private Long _parentLayoutId;
	private Boolean _privateLayout;
	private Boolean _privateLayoutsEnabled;
	private Boolean _readOnly;
	private String _redirect;
	private Layout _selLayout;
	private LayoutSet _selLayoutSet;
	private Long _selPlid;
	private String _tabs1;
	private String _themeId;
	private String[] _types;

}