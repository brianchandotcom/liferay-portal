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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplatePermission;
import com.liferay.layout.admin.web.internal.util.LayoutPageTemplatePortletUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateDisplayContext {

	public LayoutPageTemplateDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_request = request;
	}

	public List<DropdownItem> geLayoutPageTemplateEntriesActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							"javascript:" + _renderResponse.getNamespace() +
								"deleteLayoutPageTemplateEntries();");
						dropdownItem.setIcon("trash");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			LayoutAdminPortletKeys.GROUP_PAGES, "display-style", "icon");

		return _displayStyle;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public LayoutPageTemplateCollection getLayoutPageTemplateCollection()
		throws PortalException {

		if (_layoutPageTemplateCollection != null) {
			return _layoutPageTemplateCollection;
		}

		_layoutPageTemplateCollection =
			LayoutPageTemplateCollectionServiceUtil.
				fetchLayoutPageTemplateCollection(
					getLayoutPageTemplateCollectionId());

		return _layoutPageTemplateCollection;
	}

	public long getLayoutPageTemplateCollectionId() {
		if (Validator.isNotNull(_layoutPageTemplateCollectionId)) {
			return _layoutPageTemplateCollectionId;
		}

		long defaultLayoutPageTemplateCollectionId = 0;

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			getLayoutPageTemplateCollections();

		if (ListUtil.isNotEmpty(layoutPageTemplateCollections)) {
			LayoutPageTemplateCollection layoutPageTemplateCollection =
				layoutPageTemplateCollections.get(0);

			defaultLayoutPageTemplateCollectionId =
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId();
		}

		_layoutPageTemplateCollectionId = ParamUtil.getLong(
			_request, "layoutPageTemplateCollectionId",
			defaultLayoutPageTemplateCollectionId);

		return _layoutPageTemplateCollectionId;
	}

	public List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections() {

		if (_layoutPageTemplateCollections != null) {
			return _layoutPageTemplateCollections;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_layoutPageTemplateCollections =
			LayoutPageTemplateCollectionServiceUtil.
				getLayoutPageTemplateCollections(
					themeDisplay.getScopeGroupId());

		return _layoutPageTemplateCollections;
	}

	public SearchContainer getLayoutPageTemplateEntriesSearchContainer() {
		if (_layoutPageTemplateEntriesSearchContainer != null) {
			return _layoutPageTemplateEntriesSearchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer layoutPageTemplateEntriesSearchContainer =
			new SearchContainer(
				_renderRequest, _renderResponse.createRenderURL(), null,
				"there-are-no-page-templates");

		if (isSearch()) {
			layoutPageTemplateEntriesSearchContainer.setSearch(true);
		}

		layoutPageTemplateEntriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		layoutPageTemplateEntriesSearchContainer.setOrderByCol(getOrderByCol());

		OrderByComparator<LayoutPageTemplateEntry> orderByComparator =
			LayoutPageTemplatePortletUtil.
				getLayoutPageTemplateEntryOrderByComparator(
					getOrderByCol(), getOrderByType());

		layoutPageTemplateEntriesSearchContainer.setOrderByComparator(
			orderByComparator);

		layoutPageTemplateEntriesSearchContainer.setOrderByType(
			getOrderByType());

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries = null;
		int layoutPageTemplateEntriesCount = 0;

		if (isSearch()) {
			layoutPageTemplateEntries =
				LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
					themeDisplay.getScopeGroupId(),
					getLayoutPageTemplateCollectionId(), getKeywords(),
					layoutPageTemplateEntriesSearchContainer.getStart(),
					layoutPageTemplateEntriesSearchContainer.getEnd(),
					orderByComparator);

			layoutPageTemplateEntriesCount =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						themeDisplay.getScopeGroupId(),
						getLayoutPageTemplateCollectionId(), getKeywords());
		}
		else {
			layoutPageTemplateEntries =
				LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
					themeDisplay.getScopeGroupId(),
					getLayoutPageTemplateCollectionId(),
					layoutPageTemplateEntriesSearchContainer.getStart(),
					layoutPageTemplateEntriesSearchContainer.getEnd(),
					orderByComparator);

			layoutPageTemplateEntriesCount =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						themeDisplay.getScopeGroupId(),
						getLayoutPageTemplateCollectionId());
		}

		layoutPageTemplateEntriesSearchContainer.setResults(
			layoutPageTemplateEntries);
		layoutPageTemplateEntriesSearchContainer.setTotal(
			layoutPageTemplateEntriesCount);

		_layoutPageTemplateEntriesSearchContainer =
			layoutPageTemplateEntriesSearchContainer;

		return _layoutPageTemplateEntriesSearchContainer;
	}

	public LayoutPageTemplateEntry getLayoutPageTemplateEntry()
		throws PortalException {

		if (_layoutPageTemplateEntry != null) {
			return _layoutPageTemplateEntry;
		}

		_layoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.fetchLayoutPageTemplateEntry(
				getLayoutPageTemplateEntryId());

		return _layoutPageTemplateEntry;
	}

	public long getLayoutPageTemplateEntryId() {
		if (Validator.isNotNull(_layoutPageTemplateEntryId)) {
			return _layoutPageTemplateEntryId;
		}

		_layoutPageTemplateEntryId = ParamUtil.getLong(
			_request, "layoutPageTemplateEntryId");

		return _layoutPageTemplateEntryId;
	}

	public String getLayoutPageTemplateEntryTitle() throws PortalException {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			getLayoutPageTemplateEntry();

		if (layoutPageTemplateEntry == null) {
			return LanguageUtil.get(_request, "add-page-template");
		}

		return layoutPageTemplateEntry.getName();
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/layout/view");
		portletURL.setParameter("tabs1", "page-templates");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		long layoutPageTemplateCollectionId =
			getLayoutPageTemplateCollectionId();

		if (layoutPageTemplateCollectionId > 0) {
			portletURL.setParameter(
				"layoutPageTemplateCollectionId",
				String.valueOf(layoutPageTemplateCollectionId));
		}

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() {
		SearchContainer layoutPageTemplateEntriesSearchContainer =
			getLayoutPageTemplateEntriesSearchContainer();

		return layoutPageTemplateEntriesSearchContainer.getTotal();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getPortletURL(), getDisplayStyle()) {
			{
				addCardViewTypeItem();
			}
		};
	}

	public boolean isDisabledLayoutPageTemplateEntriesManagementBar() {
		if (_hasLayoutPageTemplateEntriesResults()) {
			return false;
		}

		return true;
	}

	public boolean isDisplayPage() throws PortalException {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			getLayoutPageTemplateEntry();

		if (Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE)) {

			return true;
		}

		return false;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowAddButton(String actionId) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (LayoutPageTemplatePermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getSiteGroupId(), actionId)) {

			return true;
		}

		return false;
	}

	public boolean isShowLayoutPageTemplateEntriesSearch() {
		if (_hasLayoutPageTemplateEntriesResults()) {
			return true;
		}

		if (isSearch()) {
			return true;
		}

		return false;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(getPortletURL());
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "create-date"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "create-date");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "create-date"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "name"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "name");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "name"));
					});
			}
		};
	}

	private boolean _hasLayoutPageTemplateEntriesResults() {
		SearchContainer searchContainer =
			getLayoutPageTemplateEntriesSearchContainer();

		if (searchContainer.getTotal() > 0) {
			return true;
		}

		return false;
	}

	private String _displayStyle;
	private String _keywords;
	private LayoutPageTemplateCollection _layoutPageTemplateCollection;
	private Long _layoutPageTemplateCollectionId;
	private List<LayoutPageTemplateCollection> _layoutPageTemplateCollections;
	private SearchContainer _layoutPageTemplateEntriesSearchContainer;
	private LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private Long _layoutPageTemplateEntryId;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}