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

package com.liferay.roles.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.rolesadmin.search.RoleSearch;
import com.liferay.portlet.rolesadmin.search.RoleSearchTerms;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;
import com.liferay.roles.admin.web.internal.role.type.contributor.util.RoleTypeContributorRetrieverUtil;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class SelectRoleManagementToolbarDisplayContext {

	public SelectRoleManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse, String eventName) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_eventName = eventName;

		_currentRoleTypeContributor =
			RoleTypeContributorRetrieverUtil.getCurrentRoleTypeContributor(
				renderRequest);
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		User selUser = _getSelectedUser();

		if (selUser != null) {
			portletURL.setParameter(
				"p_u_i_d", String.valueOf(selUser.getUserId()));
		}

		portletURL.setParameter("mvcPath", "/select_role.jsp");
		portletURL.setParameter(
			"roleType", String.valueOf(_currentRoleTypeContributor.getType()));

		portletURL.setParameter("eventName", _eventName);

		String[] keywords = ParamUtil.getStringValues(
			_httpServletRequest, "keywords");

		if (ArrayUtil.isNotEmpty(keywords)) {
			portletURL.setParameter("keywords", keywords[keywords.length - 1]);
		}

		long groupId = ParamUtil.getLong(_httpServletRequest, "groupId");

		if (groupId != 0) {
			portletURL.setParameter("groupId", String.valueOf(groupId));
		}

		String organizationId = ParamUtil.getString(
			_httpServletRequest, "organizationId");

		if (Validator.isNotNull(organizationId)) {
			portletURL.setParameter("organizationId", organizationId);
		}

		String organizationIds = ParamUtil.getString(
			_httpServletRequest, "organizationIds");

		if (Validator.isNotNull(organizationIds)) {
			portletURL.setParameter("organizationIds", organizationIds);
		}

		int step = ParamUtil.getInteger(_httpServletRequest, "step", 1);

		portletURL.setParameter("step", String.valueOf(step));

		return portletURL;
	}

	public SearchContainer getRoleSearchContainer(boolean filterManageableRoles)
		throws Exception {

		return getRoleSearchContainer(filterManageableRoles, 0);
	}

	public SearchContainer getRoleSearchContainer(
			boolean filterManageableRoles, long groupId)
		throws Exception {

		if (_roleSearch != null) {
			return _roleSearch;
		}

		RoleSearch roleSearch = new RoleSearch(_renderRequest, getPortletURL());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		RoleSearchTerms roleSearchTerms =
			(RoleSearchTerms)roleSearch.getSearchTerms();

		List<Role> results = null;
		int total = 0;

		if (filterManageableRoles) {
			results = RoleLocalServiceUtil.search(
				themeDisplay.getCompanyId(), roleSearchTerms.getKeywords(),
				new Integer[] {_currentRoleTypeContributor.getType()},
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				roleSearch.getOrderByComparator());

			if (groupId == 0) {
				results = UsersAdminUtil.filterRoles(
					themeDisplay.getPermissionChecker(), results);
			}
			else {
				results = UsersAdminUtil.filterGroupRoles(
					themeDisplay.getPermissionChecker(), groupId, results);
			}

			total = results.size();

			results = ListUtil.subList(
				results, roleSearch.getStart(), roleSearch.getEnd());
		}
		else {
			total = RoleLocalServiceUtil.searchCount(
				themeDisplay.getCompanyId(), roleSearchTerms.getKeywords(),
				new Integer[] {_currentRoleTypeContributor.getType()});

			results = RoleLocalServiceUtil.search(
				themeDisplay.getCompanyId(), roleSearchTerms.getKeywords(),
				new Integer[] {_currentRoleTypeContributor.getType()},
				roleSearch.getStart(), roleSearch.getEnd(),
				roleSearch.getOrderByComparator());
		}

		roleSearch.setResults(results);
		roleSearch.setTotal(total);

		_roleSearch = roleSearch;

		return _roleSearch;
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getPortletURL(), "list") {
			{
				addTableViewTypeItem();
			}
		};
	}

	private User _getSelectedUser() {
		try {
			return PortalUtil.getSelectedUser(_httpServletRequest);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SelectRoleManagementToolbarDisplayContext.class);

	private final RoleTypeContributor _currentRoleTypeContributor;
	private final String _eventName;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RoleSearch _roleSearch;

}