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

package com.liferay.blogs.web.internal.display.context;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryServiceUtil;
import com.liferay.blogs.web.internal.item.selector.BlogsEntryItemSelectorView;
import com.liferay.blogs.web.internal.util.BlogsUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class BlogEntriesItemSelectorDisplayContext {

	public BlogEntriesItemSelectorDisplayContext(
		BlogsEntryItemSelectorView blogsEntryItemSelectorView,
		HttpServletRequest httpServletRequest, String itemSelectedEventName,
		PortletURL portletURL) {

		_blogsEntryItemSelectorView = blogsEntryItemSelectorView;
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

	public SearchContainer getSearchContainer() throws PortletException {
		if (_entriesSearchContainer != null) {
			return _entriesSearchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<BlogsEntry> entriesSearchContainer =
			new SearchContainer<>(
				_portletRequest, getPortletURL(), null,
				"no-entries-were-found");

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "title");

		entriesSearchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		entriesSearchContainer.setOrderByType(orderByType);

		entriesSearchContainer.setOrderByComparator(
			BlogsUtil.getOrderByComparator(
				entriesSearchContainer.getOrderByCol(),
				entriesSearchContainer.getOrderByType()));

		entriesSearchContainer.setTotal(
			BlogsEntryServiceUtil.getGroupEntriesCount(
				themeDisplay.getScopeGroupId(),
				WorkflowConstants.STATUS_APPROVED));

		List<BlogsEntry> entriesResults = BlogsEntryServiceUtil.getGroupEntries(
			themeDisplay.getScopeGroupId(), WorkflowConstants.STATUS_APPROVED,
			entriesSearchContainer.getStart(), entriesSearchContainer.getEnd(),
			entriesSearchContainer.getOrderByComparator());

		entriesSearchContainer.setResults(entriesResults);

		_entriesSearchContainer = entriesSearchContainer;

		return _entriesSearchContainer;
	}

	private String _getTitle(Locale locale) {
		return _blogsEntryItemSelectorView.getTitle(locale);
	}

	private final BlogsEntryItemSelectorView _blogsEntryItemSelectorView;
	private String _displayStyle;
	private SearchContainer<BlogsEntry> _entriesSearchContainer;
	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private final PortletURL _portletURL;

}