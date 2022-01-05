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

package com.liferay.bookmarks.web.internal.display.context;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.web.internal.portlet.util.BookmarksUtil;
import com.liferay.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.bookmarks.util.comparator.EntryModifiedDateComparator;
import com.liferay.bookmarks.util.comparator.EntryNameComparator;
import com.liferay.bookmarks.util.comparator.EntryURLComparator;
import com.liferay.bookmarks.web.internal.item.selector.BookmarksEntryItemSelectorView;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 
 */
public class BookmarkEntriesItemSelectorDisplayContext {

	public BookmarkEntriesItemSelectorDisplayContext(
		BookmarksEntryItemSelectorView bookmarksEntryItemSelectorView,
		HttpServletRequest httpServletRequest, String itemSelectedEventName,
		PortletURL portletURL) {

		_bookmarksEntryItemSelectorView = bookmarksEntryItemSelectorView;
		_httpServletRequest = httpServletRequest;
		_itemSelectedEventName = itemSelectedEventName;
		_portletURL = portletURL;

		_portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_portletResponse = (RenderResponse)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "icon");

		return _displayStyle;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public PortletURL getPortletURL() throws PortletException {
		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL,
			PortalUtil.getLiferayPortletResponse(_portletResponse));

		portletURL.setParameter("displayStyle", getDisplayStyle());
		portletURL.setParameter(
			"selectedTab",
			String.valueOf(_getTitle(_httpServletRequest.getLocale())));

		return portletURL;
	}

	public SearchContainer getSearchContainer() throws PortletException, PortalException {
		if (_entriesSearchContainer != null) {
			return _entriesSearchContainer;
		}

		ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

		SearchContainer<BookmarksEntry> entriesSearchContainer =
				new SearchContainer<>(
						_portletRequest, getPortletURL(), null,
						"no-entries-were-found");

		String orderByColName = ParamUtil.getString(
				_httpServletRequest, "orderByCol", "name");

		entriesSearchContainer.setOrderByCol(orderByColName);

		String orderByColUrl = ParamUtil.getString(
				_httpServletRequest, "orderByCol", "url");

		entriesSearchContainer.setOrderByCol(orderByColUrl);

		String orderByType = ParamUtil.getString(
				_httpServletRequest, "orderByType", "asc");

		entriesSearchContainer.setOrderByType(orderByType);

		entriesSearchContainer.setOrderByComparator(
				BookmarksUtil.getEntryOrderByComparator(
						entriesSearchContainer.getOrderByCol(),
						entriesSearchContainer.getOrderByType()));

		entriesSearchContainer.setTotal(
				BookmarksEntryServiceUtil.getGroupEntriesCount(
						themeDisplay.getScopeGroupId()));

		List<BookmarksEntry> entriesResults = BookmarksEntryServiceUtil.getEntries(
				themeDisplay.getScopeGroupId(), 0,
				entriesSearchContainer.getStart(), entriesSearchContainer.getEnd(),
				entriesSearchContainer.getOrderByComparator());

		entriesSearchContainer.setResults(entriesResults);

		_entriesSearchContainer = entriesSearchContainer;

		return _entriesSearchContainer;

	}

	private String _getTitle(Locale locale) {
		return _bookmarksEntryItemSelectorView.getTitle(locale);
	}

	private final BookmarksEntryItemSelectorView _bookmarksEntryItemSelectorView;
	private String _displayStyle;
	private SearchContainer<BookmarksEntry> _entriesSearchContainer;
	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private final PortletURL _portletURL;
}