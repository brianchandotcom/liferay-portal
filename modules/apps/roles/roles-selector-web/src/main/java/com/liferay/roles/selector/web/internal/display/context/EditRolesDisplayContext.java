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

package com.liferay.roles.selector.web.internal.display.context;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.rolesadmin.search.RoleSearch;
import com.liferay.portlet.rolesadmin.search.RoleSearchTerms;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mariano Álvaro Sáiz
 */
public class EditRolesDisplayContext {

	public EditRolesDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		Group group = _getGroup();

		_groupId = group.getGroupId();

		return _groupId;
	}

	public SearchContainer<Role> getSearchContainer() throws PortalException {
		if (_roleSearch != null) {
			return _roleSearch;
		}

		_roleSearch = new RoleSearch(
			_renderRequest,
			(PortletURL)_httpServletRequest.getAttribute(
				"edit_roles.jsp-portletURL"));

		RoleSearchTerms searchTerms =
			(RoleSearchTerms)_roleSearch.getSearchTerms();

		List<Role> roles = UsersAdminUtil.filterGroupRoles(
			_themeDisplay.getPermissionChecker(), getGroupId(),
			RoleLocalServiceUtil.search(
				_themeDisplay.getCompanyId(), searchTerms.getKeywords(),
				new Integer[] {
					(Integer)_httpServletRequest.getAttribute(
						"edit_roles.jsp-roleType")
				},
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				_roleSearch.getOrderByComparator()));

		_roleSearch.setResultsAndTotal(
			() -> ListUtil.subList(
				roles, _roleSearch.getStart(), _roleSearch.getEnd()),
			roles.size());

		return _roleSearch;
	}

	private Group _getGroup() {
		if (_group != null) {
			return _group;
		}

		_group = (Group)_httpServletRequest.getAttribute(
			"edit_roles.jsp-group");

		return _group;
	}

	private Group _group;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private RoleSearch _roleSearch;
	private final ThemeDisplay _themeDisplay;

}