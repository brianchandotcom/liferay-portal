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

package com.liferay.site.memberships.web.internal.display.context;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.RoleNameComparator;
import com.liferay.portlet.rolesadmin.search.RoleSearch;
import com.liferay.portlet.rolesadmin.search.RoleSearchTerms;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserGroupRolesDisplayContext {

	public UserGroupRolesDisplayContext(
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

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "icon");

		return _displayStyle;
	}

	public String getEventName() {
		if (Validator.isNotNull(_eventName)) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, "eventName",
			_renderResponse.getNamespace() + "selectUserGroupsRoles");

		return _eventName;
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_groupId = ParamUtil.getLong(
			_httpServletRequest, "groupId",
			themeDisplay.getSiteGroupIdOrLiveGroupId());

		return _groupId;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_renderRequest, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "title");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/user_groups_roles.jsp");
		portletURL.setParameter(
			"userGroupId", String.valueOf(getUserGroupId()));

		portletURL.setParameter(
			"assignRoles", String.valueOf(_isAssignRoles()));

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public SearchContainer getRoleSearchSearchContainer()
		throws PortalException {

		if (_roleSearch != null) {
			return _roleSearch;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		RoleSearch roleSearch = new RoleSearch(_renderRequest, getPortletURL());

		RoleSearchTerms searchTerms =
			(RoleSearchTerms)roleSearch.getSearchTerms();

		roleSearch.setRowChecker(new EmptyOnClickRowChecker(_renderResponse));
		roleSearch.setOrderByCol(_getOrderByCol());

		boolean orderByAsc = false;

		if (Objects.equals(_getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		OrderByComparator<Role> orderByComparator = new RoleNameComparator(
			orderByAsc);

		roleSearch.setOrderByComparator(orderByComparator);

		roleSearch.setOrderByType(getOrderByType());

		List<Role> roles = RoleLocalServiceUtil.search(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			new Integer[] {RoleConstants.TYPE_SITE}, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, orderByComparator);

		List<Role> selectedRoles = _getSelectedRoles();

		Stream<Role> stream = roles.stream();

		roles = stream.filter(
			role -> {
				if ((_isAssignRoles() && !selectedRoles.contains(role)) ||
					(!_isAssignRoles() && selectedRoles.contains(role))) {

					return true;
				}

				return false;
			}
		).collect(
			Collectors.toList()
		);

		roles = UsersAdminUtil.filterGroupRoles(
			themeDisplay.getPermissionChecker(), getGroupId(), roles);

		int rolesCount = roles.size();

		roleSearch.setTotal(rolesCount);

		roles = ListUtil.subList(
			roles, roleSearch.getStart(), roleSearch.getEnd());

		roleSearch.setResults(roles);

		_roleSearch = roleSearch;

		return _roleSearch;
	}

	public long getUserGroupId() {
		if (_userGroupId != null) {
			return _userGroupId;
		}

		_userGroupId = ParamUtil.getLong(_httpServletRequest, "userGroupId");

		return _userGroupId;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "title");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	private List<Role> _getSelectedRoles() {
		List<UserGroupGroupRole> userGroupGroupRoles =
			UserGroupGroupRoleLocalServiceUtil.getUserGroupGroupRoles(
				getUserGroupId(), getGroupId());

		Stream<UserGroupGroupRole> stream = userGroupGroupRoles.stream();

		return stream.map(
			userGroupGroupRole -> RoleLocalServiceUtil.fetchRole(
				userGroupGroupRole.getRoleId())
		).collect(
			Collectors.toList()
		);
	}

	private boolean _isAssignRoles() {
		if (_assignRoles != null) {
			return _assignRoles;
		}

		_assignRoles = ParamUtil.getBoolean(
			_httpServletRequest, "assignRoles", true);

		return _assignRoles;
	}

	private Boolean _assignRoles;
	private String _displayStyle;
	private String _eventName;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RoleSearch _roleSearch;
	private Long _userGroupId;

}