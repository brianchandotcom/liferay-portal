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

package com.liferay.style.book.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.constants.StyleBookActionKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;
import com.liferay.style.book.util.comparator.StyleBookEntryCreateDateComparator;
import com.liferay.style.book.util.comparator.StyleBookEntryNameComparator;
import com.liferay.style.book.web.internal.security.permissions.resource.StyleBookPermission;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class StyleBookDisplayContext {

	public StyleBookDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = _getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = _getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public SearchContainer<StyleBookEntry>
		getStyleBookEntriesSearchContainer() {

		if (_styleBookEntriesSearchContainer != null) {
			return _styleBookEntriesSearchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<StyleBookEntry> styleBookEntriesSearchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, getPortletURL(), null,
				"there-are-no-style-books");

		styleBookEntriesSearchContainer.setOrderByCol(_getOrderByCol());

		OrderByComparator<StyleBookEntry> orderByComparator =
			_getStyleBookEntryOrderByComparator();

		styleBookEntriesSearchContainer.setOrderByComparator(orderByComparator);

		styleBookEntriesSearchContainer.setOrderByType(_getOrderByType());

		if (StyleBookPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				StyleBookActionKeys.MANAGE_STYLE_BOOK_ENTRIES)) {

			styleBookEntriesSearchContainer.setRowChecker(
				new EmptyOnClickRowChecker(_liferayPortletResponse));
		}

		List<StyleBookEntry> styleBookEntries = null;
		int styleBookEntriesCount = 0;

		if (_isSearch()) {
			styleBookEntries =
				StyleBookEntryLocalServiceUtil.getStyleBookEntries(
					themeDisplay.getScopeGroupId(), _getKeywords(),
					styleBookEntriesSearchContainer.getStart(),
					styleBookEntriesSearchContainer.getEnd(),
					orderByComparator);

			styleBookEntriesCount =
				StyleBookEntryLocalServiceUtil.getStyleBookEntriesCount(
					themeDisplay.getScopeGroupId(), _getKeywords());
		}
		else {
			styleBookEntries =
				StyleBookEntryLocalServiceUtil.getStyleBookEntries(
					themeDisplay.getScopeGroupId(),
					styleBookEntriesSearchContainer.getStart(),
					styleBookEntriesSearchContainer.getEnd(),
					orderByComparator);

			styleBookEntriesCount =
				StyleBookEntryLocalServiceUtil.getStyleBookEntriesCount(
					themeDisplay.getScopeGroupId());
		}

		styleBookEntriesSearchContainer.setResults(styleBookEntries);
		styleBookEntriesSearchContainer.setTotal(styleBookEntriesCount);

		_styleBookEntriesSearchContainer = styleBookEntriesSearchContainer;

		return _styleBookEntriesSearchContainer;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	private OrderByComparator<StyleBookEntry>
		_getStyleBookEntryOrderByComparator() {

		boolean orderByAsc = false;

		if (Objects.equals(_getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		OrderByComparator<StyleBookEntry> orderByComparator = null;

		if (Objects.equals(_getOrderByCol(), "create-date")) {
			orderByComparator = new StyleBookEntryCreateDateComparator(
				orderByAsc);
		}
		else if (Objects.equals(_getOrderByCol(), "name")) {
			orderByComparator = new StyleBookEntryNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private SearchContainer<StyleBookEntry> _styleBookEntriesSearchContainer;

}