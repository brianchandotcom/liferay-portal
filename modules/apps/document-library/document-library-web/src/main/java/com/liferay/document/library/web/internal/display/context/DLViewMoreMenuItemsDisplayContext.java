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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian I. Kim
 */
public class DLViewMoreMenuItemsDisplayContext {

	public DLViewMoreMenuItemsDisplayContext(
		long folderId, RenderRequest renderRequest,
		RenderResponse renderResponse, HttpServletRequest request) {

		_folderId = folderId;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;
	}

	public String getClearResultsURL() {
		return getSearchActionURL();
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_renderRequest, "eventName",
			_renderResponse.getNamespace() + "selectAddMenuItem");

		return _eventName;
	}

	public List<NavigationItem> getNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(
							LanguageUtil.get(_request, "document-types"));
					});
			}
		};
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcPath", "/document_library/view_more_menu_items.jsp");
		portletURL.setParameter("folderId", String.valueOf(_folderId));
		portletURL.setParameter("eventName", getEventName());

		return portletURL;
	}

	public String getSearchActionURL() {
		return String.valueOf(getPortletURL());
	}

	public SearchContainer getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, new DisplayTerms(_request),
			new DisplayTerms(_request), SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_DELTA, getPortletURL(), null,
			LanguageUtil.get(_request, "there-are-no-results"));

		DisplayTerms searchTerms = searchContainer.getSearchTerms();

		boolean includeBasicFileEntryType = ParamUtil.getBoolean(
			_renderRequest, "includeBasicFileEntryType");

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeServiceUtil.search(
				themeDisplay.getCompanyId(), _folderId,
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					themeDisplay.getScopeGroupId()),
				searchTerms.getKeywords(), includeBasicFileEntryType,
				searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setTotal(dlFileEntryTypes.size());

		searchContainer.setResults(dlFileEntryTypes);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public int getTotalItems() throws PortalException {
		SearchContainer searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	private String _eventName;
	private final long _folderId;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private SearchContainer _searchContainer;

}