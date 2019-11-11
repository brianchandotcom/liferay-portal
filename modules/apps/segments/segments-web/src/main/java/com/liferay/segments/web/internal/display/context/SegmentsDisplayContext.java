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

package com.liferay.segments.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryService;
import com.liferay.segments.web.internal.security.permission.resource.SegmentsEntryPermission;
import com.liferay.segments.web.internal.security.permission.resource.SegmentsResourcePermission;
import com.liferay.segments.web.internal.util.comparator.SegmentsEntryModifiedDateComparator;
import com.liferay.segments.web.internal.util.comparator.SegmentsEntryNameComparator;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class SegmentsDisplayContext {

	public SegmentsDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse,
		SegmentsEntryService segmentsEntryService) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_segmentsEntryService = segmentsEntryService;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteSegmentsEntries");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getAvailableActions(SegmentsEntry segmentsEntry)
		throws PortalException {

		if (SegmentsEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), segmentsEntry,
				ActionKeys.DELETE)) {

			return "deleteSegmentsEntries";
		}

		return StringPool.BLANK;
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = _getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(),
							"mvcRenderCommandName", "editSegmentsEntry", "type",
							User.class.getName());
						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "user-segment"));
					});
			}
		};
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "filter-by-navigation"));
					});
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "order-by"));
					});
			}
		};
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public String getSearchActionURL() {
		PortletURL portletURL = _getPortletURL();

		return portletURL.toString();
	}

	public SearchContainer getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, _getPortletURL(), null, "there-are-no-segments");

		searchContainer.setId("segmentsEntries");
		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByComparator(_getOrderByComparator());
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		List<SegmentsEntry> segmentsEntries = null;

		int segmentsEntriesCount = 0;

		if (_isSearch()) {
			BaseModelSearchResult<SegmentsEntry> baseModelSearchResult =
				_segmentsEntryService.searchSegmentsEntries(
					_themeDisplay.getCompanyId(),
					_themeDisplay.getScopeGroupId(), _getKeywords(), true,
					searchContainer.getStart(), searchContainer.getEnd(),
					_getSort());

			segmentsEntries = baseModelSearchResult.getBaseModels();
			segmentsEntriesCount = baseModelSearchResult.getLength();
		}
		else {
			segmentsEntries = _segmentsEntryService.getSegmentsEntries(
				_themeDisplay.getScopeGroupId(), true,
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());

			segmentsEntriesCount =
				_segmentsEntryService.getSegmentsEntriesCount(
					_themeDisplay.getScopeGroupId(), true);
		}

		searchContainer.setResults(segmentsEntries);
		searchContainer.setTotal(segmentsEntriesCount);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public String getSegmentsEntryURL(SegmentsEntry segmentsEntry) {
		if (segmentsEntry == null) {
			return StringPool.BLANK;
		}

		if (Objects.equals(
				segmentsEntry.getSource(),
				SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND)) {

			String asahFaroURL = PrefsPropsUtil.getString(
				segmentsEntry.getCompanyId(), "liferayAnalyticsURL");

			if (Validator.isNull(asahFaroURL)) {
				return StringPool.BLANK;
			}

			return asahFaroURL + "/contacts/segments/" +
				segmentsEntry.getSegmentsEntryKey();
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "editSegmentsEntry");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(_renderRequest));
		portletURL.setParameter(
			"segmentsEntryId",
			String.valueOf(segmentsEntry.getSegmentsEntryId()));
		portletURL.setParameter("showInEditMode", Boolean.FALSE.toString());

		return portletURL.toString();
	}

	public String getSegmentsEntryURLTarget(SegmentsEntry segmentsEntry) {
		if ((segmentsEntry == null) ||
			Objects.equals(
				segmentsEntry.getSource(),
				SegmentsEntryConstants.SOURCE_DEFAULT)) {

			return "_self";
		}

		return "_blank";
	}

	public String getSortingURL() {
		PortletURL sortingURL = _getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<?> searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	public boolean isAsahEnabled(long companyId) {
		String asahFaroURL = PrefsPropsUtil.getString(
			companyId, "liferayAnalyticsURL");

		if (Validator.isNotNull(asahFaroURL)) {
			return true;
		}

		return false;
	}

	public boolean isDisabledManagementBar() throws PortalException {
		if (_hasResults()) {
			return false;
		}

		if (_isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isShowCreationMenu() {
		if (SegmentsResourcePermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES)) {

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
						dropdownItem.setHref(_renderResponse.createRenderURL());
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "all"));
					});
			}
		};
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "modified-date");

		return _orderByCol;
	}

	private OrderByComparator<SegmentsEntry> _getOrderByComparator() {
		boolean orderByAsc = false;

		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		String orderByCol = _getOrderByCol();

		OrderByComparator<SegmentsEntry> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new SegmentsEntryModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new SegmentsEntryNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(_getOrderByCol(), "modified-date"));
						dropdownItem.setHref(
							_getPortletURL(), "orderByCol", "modified-date");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "modified-date"));
					});
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(_getOrderByCol(), "name"));
						dropdownItem.setHref(
							_getPortletURL(), "orderByCol", "name");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "name"));
					});
			}
		};
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		portletURL.setParameter("displayStyle", getDisplayStyle());
		portletURL.setParameter("orderByCol", _getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	private Sort _getSort() {
		boolean orderByAsc = false;

		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		String orderByCol = _getOrderByCol();

		Sort sort = null;

		if (orderByCol.equals("name")) {
			String sortFieldName = Field.getSortableFieldName(
				"localized_name_".concat(_themeDisplay.getLanguageId()));

			sort = new Sort(sortFieldName, Sort.STRING_TYPE, !orderByAsc);
		}
		else {
			sort = new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
		}

		return sort;
	}

	private boolean _hasResults() throws PortalException {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer _searchContainer;
	private final SegmentsEntryService _segmentsEntryService;
	private final ThemeDisplay _themeDisplay;

}