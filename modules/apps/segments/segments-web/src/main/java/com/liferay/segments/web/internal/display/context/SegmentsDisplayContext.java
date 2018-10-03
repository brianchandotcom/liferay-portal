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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryService;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Garcia
 */
public class SegmentsDisplayContext {

	public SegmentsDisplayContext(
		HttpServletRequest request, RenderRequest renderRequest,
		RenderResponse renderResponse,
		SegmentsEntryService segmentsEntryService) {

		_request = request;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_segmentsEntryService = segmentsEntryService;
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.putData(
								"action", "deleteSegmentsEntries");

							dropdownItem.setIcon("times");

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "delete"));

							dropdownItem.setQuickAction(true);
						}));
			}
		};
	}

	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(),
							"mvcRenderCommandName", "editSegmentsEntry");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "add"));
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

	public SearchContainer getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, getPortletURL(), null, "there-are-no-segments");

		searchContainer.setId("segmentsEntries");
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		int total = _segmentsEntryService.getSegmentsEntriesCount(
			_themeDisplay.getScopeGroupId());

		searchContainer.setTotal(total);

		List results = _segmentsEntryService.getSegmentsEntries(
			_themeDisplay.getScopeGroupId(), searchContainer.getStart(),
			searchContainer.getEnd(), searchContainer.getOrderByComparator());

		searchContainer.setResults(results);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public SegmentsEntry getSegmentsEntry() throws PortalException {
		if (_segmentsEntry != null) {
			return _segmentsEntry;
		}

		long segmentsEntryId = ParamUtil.getLong(_request, "segmentsEntryId");

		if (segmentsEntryId > 0) {
			_segmentsEntry = _segmentsEntryService.getSegmentsEntry(
				segmentsEntryId);
		}

		return _segmentsEntry;
	}

	public long getSegmentsEntryId() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry == null) {
			return 0;
		}

		return segmentsEntry.getSegmentsEntryId();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<?> searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	protected PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		return portletURL;
	}

	private String _displayStyle;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private SearchContainer _searchContainer;
	private SegmentsEntry _segmentsEntry;
	private final SegmentsEntryService _segmentsEntryService;
	private final ThemeDisplay _themeDisplay;

}