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

package com.liferay.site.item.selector.web.display.context;

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;
import com.liferay.site.util.RecentGroupManager;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class RecentSitesItemSelectorViewDisplayContext {

	public RecentSitesItemSelectorViewDisplayContext(
		HttpServletRequest request,
		SiteItemSelectorCriterion siteItemSelectorCriterion,
		String itemSelectedEventName, RecentGroupManager recentGroupManager) {

		_request = request;
		_siteItemSelectorCriterion = siteItemSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
		_recentGroupManager = recentGroupManager;
	}

	public GroupSearch getGroupSearch() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		GroupSearch groupSearch = new GroupSearch(
			getPortletRequest(), getPortletURL());

		groupSearch.setEmptyResultsMessage(
			"you-have-not-visited-any-site-recently");

		List<Group> results = _recentGroupManager.getRecentGroups(_request);

		groupSearch.setTotal(results.size());
		groupSearch.setResults(results);

		return groupSearch;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public PortletRequest getPortletRequest() {
		return (PortletRequest)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	public PortletResponse getPortletResponse() {
		return (PortletResponse)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(getPortletResponse());

		return liferayPortletResponse.createRenderURL();
	}

	public SiteItemSelectorCriterion getSiteItemSelectorCriterion() {
		return _siteItemSelectorCriterion;
	}

	public List<Group> getSites() {
		return _recentGroupManager.getRecentGroups(_request);
	}

	private final String _itemSelectedEventName;
	private final RecentGroupManager _recentGroupManager;
	private final HttpServletRequest _request;
	private final SiteItemSelectorCriterion _siteItemSelectorCriterion;

}