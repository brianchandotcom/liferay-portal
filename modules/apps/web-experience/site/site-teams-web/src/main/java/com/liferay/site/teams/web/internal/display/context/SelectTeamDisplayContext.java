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

package com.liferay.site.teams.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.teams.web.internal.constants.SiteTeamsPortletKeys;
import com.liferay.site.teams.web.internal.search.TeamDisplayTerms;
import com.liferay.site.teams.web.internal.search.TeamSearch;

import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectTeamDisplayContext {

	public SelectTeamDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			SiteTeamsPortletKeys.SITE_TEAMS, "display-style", "icon");

		return _displayStyle;
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_request, "eventName",
			_renderResponse.getNamespace() + "selectTeam");

		return _eventName;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public List<NavigationItem> getNavigationItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(themeDisplay.getURLCurrent());
						navigationItem.setLabel(
							LanguageUtil.get(_request, "teams"));
					});
			}
		};
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_request, "orderByCol", "name");

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
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/select_team.jsp");
		portletURL.setParameter("eventName", getEventName());

		return portletURL;
	}

	public SearchContainer getTeamSearchContainer() {
		if (_teamSearchContainer != null) {
			return _teamSearchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		TeamSearch teamSearchContainer = new TeamSearch(
			_renderRequest, getPortletURL());

		TeamDisplayTerms searchTerms =
			(TeamDisplayTerms)teamSearchContainer.getSearchTerms();

		int teamsCount = TeamLocalServiceUtil.searchCount(
			themeDisplay.getScopeGroupId(), searchTerms.getKeywords(),
			searchTerms.getDescription(), new LinkedHashMap<>());

		teamSearchContainer.setTotal(teamsCount);

		List<Team> teams = TeamLocalServiceUtil.search(
			themeDisplay.getScopeGroupId(), searchTerms.getKeywords(),
			searchTerms.getDescription(), new LinkedHashMap<>(),
			teamSearchContainer.getStart(), teamSearchContainer.getEnd(),
			teamSearchContainer.getOrderByComparator());

		teamSearchContainer.setResults(teams);

		_teamSearchContainer = teamSearchContainer;

		return _teamSearchContainer;
	}

	public int getTotal() {
		SearchContainer userSearchContainer = getTeamSearchContainer();

		return userSearchContainer.getTotal();
	}

	public boolean isDisabledManagementBar() {
		if (getTotal() <= 0) {
			return true;
		}

		return false;
	}

	public boolean isShowSearch() {
		if (getTotal() > 0) {
			return true;
		}

		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	private String _displayStyle;
	private String _eventName;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private SearchContainer _teamSearchContainer;

}