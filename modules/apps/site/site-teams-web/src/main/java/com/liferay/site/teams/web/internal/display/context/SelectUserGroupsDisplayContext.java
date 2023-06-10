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

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.persistence.constants.UserGroupFinderConstants;
import com.liferay.portlet.usergroupsadmin.search.UserGroupSearch;
import com.liferay.site.teams.web.internal.constants.SiteTeamsPortletKeys;
import com.liferay.site.teams.web.internal.search.UserGroupTeamChecker;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.LinkedHashMap;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectUserGroupsDisplayContext {

	public SelectUserGroupsDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			_httpServletRequest, SiteTeamsPortletKeys.SITE_TEAMS,
			"usergroup-display-style", "list");

		return _displayStyle;
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, "eventName",
			_renderResponse.getNamespace() + "selectUserGroup");

		return _eventName;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, SiteTeamsPortletKeys.SITE_TEAMS,
			"usergroup-order-by-col", "name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, SiteTeamsPortletKeys.SITE_TEAMS,
			"usergroup-order-by-type", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/select_user_groups.jsp"
		).setRedirect(
			getRedirect()
		).setKeywords(
			() -> {
				String keywords = getKeywords();

				if (Validator.isNotNull(keywords)) {
					return keywords;
				}

				return null;
			}
		).setParameter(
			"eventName", getEventName()
		).setParameter(
			"orderByCol",
			() -> {
				String orderByCol = getOrderByCol();

				if (Validator.isNotNull(orderByCol)) {
					return orderByCol;
				}

				return null;
			}
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = getOrderByType();

				if (Validator.isNotNull(orderByType)) {
					return orderByType;
				}

				return null;
			}
		).setParameter(
			"teamId", getTeamId()
		).buildPortletURL();
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public Team getTeam() {
		if (_team != null) {
			return _team;
		}

		_team = TeamLocalServiceUtil.fetchTeam(getTeamId());

		return _team;
	}

	public long getTeamId() {
		if (_teamId != null) {
			return _teamId;
		}

		_teamId = ParamUtil.getLong(_httpServletRequest, "teamId");

		return _teamId;
	}

	public SearchContainer<UserGroup> getUserGroupSearchContainer() {
		if (_userGroupSearchContainer != null) {
			return _userGroupSearchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<UserGroup> userGroupSearchContainer =
			new UserGroupSearch(_renderRequest, getPortletURL());

		userGroupSearchContainer.setOrderByCol(getOrderByCol());
		userGroupSearchContainer.setOrderByComparator(
			UsersAdminUtil.getUserGroupOrderByComparator(
				getOrderByCol(), getOrderByType()));
		userGroupSearchContainer.setOrderByType(getOrderByType());

		Team team = getTeam();

		LinkedHashMap<String, Object> userGroupParams =
			LinkedHashMapBuilder.<String, Object>put(
				UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_GROUPS,
				() -> {
					Group group = GroupLocalServiceUtil.fetchGroup(
						team.getGroupId());

					if (group != null) {
						group = StagingUtil.getLiveGroup(group.getGroupId());
					}

					return Long.valueOf(group.getGroupId());
				}
			).build();

		userGroupSearchContainer.setResultsAndTotal(
			() -> UserGroupLocalServiceUtil.search(
				themeDisplay.getCompanyId(), getKeywords(),
				userGroupParams, userGroupSearchContainer.getStart(),
				userGroupSearchContainer.getEnd(),
				userGroupSearchContainer.getOrderByComparator()),
			UserGroupLocalServiceUtil.searchCount(
				themeDisplay.getCompanyId(), getKeywords(),
				userGroupParams));

		userGroupSearchContainer.setRowChecker(
			new UserGroupTeamChecker(_renderResponse, getTeam()));

		_userGroupSearchContainer = userGroupSearchContainer;

		return _userGroupSearchContainer;
	}

	private String _displayStyle;
	private String _eventName;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Team _team;
	private Long _teamId;
	private SearchContainer<UserGroup> _userGroupSearchContainer;

}