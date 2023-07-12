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

package com.liferay.users.admin.item.selector.web.internal.display.context;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;
import com.liferay.users.admin.item.selector.UserSiteTeamItemSelectorCriterion;
import com.liferay.users.admin.item.selector.web.internal.search.UserSiteTeamChecker;

import java.util.LinkedHashMap;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserSiteTeamItemSelectorViewDisplayContext {

	public UserSiteTeamItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		RenderRequest renderRequest, RenderResponse renderResponse,
		UserSiteTeamItemSelectorCriterion userSiteTeamItemSelectorCriterion) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_userSiteTeamItemSelectorCriterion = userSiteTeamItemSelectorCriterion;
	}

	public SearchContainer<User> getUserSearchContainer() {
		if (_userSearchContainer != null) {
			return _userSearchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<User> userSearchContainer = new UserSearch(
			_renderRequest, _portletURL);

		UserSearchTerms searchTerms =
			(UserSearchTerms)userSearchContainer.getSearchTerms();

		Team team = TeamLocalServiceUtil.fetchTeam(
			_userSiteTeamItemSelectorCriterion.getTeamId());

		LinkedHashMap<String, Object> userParams =
			LinkedHashMapBuilder.<String, Object>put(
				"inherit", Boolean.TRUE
			).put(
				"usersGroups",
				() -> {
					Group group = GroupLocalServiceUtil.fetchGroup(
						team.getGroupId());

					if (group != null) {
						group = StagingUtil.getLiveGroup(group.getGroupId());
					}

					return group.getGroupId();
				}
			).build();

		userSearchContainer.setResultsAndTotal(
			() -> UserLocalServiceUtil.search(
				themeDisplay.getCompanyId(), searchTerms.getKeywords(),
				searchTerms.getStatus(), userParams,
				userSearchContainer.getStart(), userSearchContainer.getEnd(),
				userSearchContainer.getOrderByComparator()),
			UserLocalServiceUtil.searchCount(
				themeDisplay.getCompanyId(), searchTerms.getKeywords(),
				searchTerms.getStatus(), userParams));

		userSearchContainer.setRowChecker(
			new UserSiteTeamChecker(_renderResponse, team));

		_userSearchContainer = userSearchContainer;

		return _userSearchContainer;
	}

	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<User> _userSearchContainer;
	private final UserSiteTeamItemSelectorCriterion
		_userSiteTeamItemSelectorCriterion;

}