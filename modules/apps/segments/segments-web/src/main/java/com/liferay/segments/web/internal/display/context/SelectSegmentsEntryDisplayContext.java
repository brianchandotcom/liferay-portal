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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
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
public class SelectSegmentsEntryDisplayContext {

	public SelectSegmentsEntryDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse,
		SegmentsEntryLocalService segmentsEntryLocalService) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_segmentsEntryLocalService = segmentsEntryLocalService;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = _getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public String getEventName() {
		if (Validator.isNotNull(_eventName)) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, "eventName",
			_renderResponse.getNamespace() + "selectEntity");

		return _eventName;
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

	public String getGroupDescriptiveName(SegmentsEntry segmentsEntry)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(
			segmentsEntry.getGroupId());

		return group.getDescriptiveName(_themeDisplay.getLocale());
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

		searchContainer.setId("selectSegmentsEntry");
		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByComparator(_getOrderByComparator());
		searchContainer.setOrderByType(getOrderByType());

		BaseModelSearchResult<SegmentsEntry> baseModelSearchResult =
			_segmentsEntryLocalService.searchSegmentsEntries(
				_themeDisplay.getCompanyId(), _themeDisplay.getScopeGroupId(),
				_getKeywords(), true, searchContainer.getStart(),
				searchContainer.getEnd(), _getSort());

		searchContainer.setResults(baseModelSearchResult.getBaseModels());
		searchContainer.setTotal(baseModelSearchResult.getLength());

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public long[] getSelectedSegmentsEntryIds() {
		return StringUtil.split(
			ParamUtil.getString(
				_httpServletRequest, "selectedSegmentsEntryIds"),
			0L);
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

	public boolean isDisabledManagementBar() throws PortalException {
		if (_hasResults()) {
			return false;
		}

		if (_isSearch()) {
			return false;
		}

		return true;
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

		portletURL.setParameter("mvcRenderCommandName", "selectSegmentsEntry");

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
	private String _eventName;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer _searchContainer;
	private final SegmentsEntryLocalService _segmentsEntryLocalService;
	private final ThemeDisplay _themeDisplay;

}