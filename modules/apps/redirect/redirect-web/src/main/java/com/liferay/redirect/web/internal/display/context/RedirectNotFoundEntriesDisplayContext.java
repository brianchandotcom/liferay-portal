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

package com.liferay.redirect.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalServiceUtil;
import com.liferay.redirect.web.internal.search.RedirectNotFoundEntrySearch;

import java.util.TreeMap;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class RedirectNotFoundEntriesDisplayContext {

	public RedirectNotFoundEntriesDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getGroupBaseURL() {
		StringBuilder groupBaseURL = new StringBuilder();

		groupBaseURL.append(_themeDisplay.getPortalURL());

		Group group = _themeDisplay.getScopeGroup();

		LayoutSet layoutSet = group.getPublicLayoutSet();

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (virtualHostnames.isEmpty() ||
			!_matchesHostname(groupBaseURL, virtualHostnames)) {

			groupBaseURL.append(group.getPathFriendlyURL(false, _themeDisplay));
			groupBaseURL.append(HttpUtil.decodeURL(group.getFriendlyURL()));
		}

		return groupBaseURL.toString();
	}

	public String getSearchContainerId() {
		return "redirectEntries";
	}

	public SearchContainer<RedirectNotFoundEntry> searchContainer() {
		if (_redirectNotFoundEntrySearch != null) {
			return _redirectNotFoundEntrySearch;
		}

		_redirectNotFoundEntrySearch = new RedirectNotFoundEntrySearch(
			_liferayPortletRequest, _getPortletURL(), getSearchContainerId());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_redirectNotFoundEntrySearch.setTotal(
			RedirectNotFoundEntryLocalServiceUtil.
				getRedirectNotFoundEntriesCount(
					themeDisplay.getScopeGroupId()));

		_redirectNotFoundEntrySearch.setResults(
			RedirectNotFoundEntryLocalServiceUtil.getRedirectNotFoundEntries(
				themeDisplay.getScopeGroupId(),
				_redirectNotFoundEntrySearch.getStart(),
				_redirectNotFoundEntrySearch.getEnd(), null));

		return _redirectNotFoundEntrySearch;
	}

	private PortletURL _getPortletURL() {
		return _liferayPortletResponse.createRenderURL();
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

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private RedirectNotFoundEntrySearch _redirectNotFoundEntrySearch;
	private final ThemeDisplay _themeDisplay;

}