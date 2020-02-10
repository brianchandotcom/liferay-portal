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

package com.liferay.depot.web.internal.display.context;

import com.liferay.admin.kernel.util.PortalMyAccountApplicationType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.UserGroupRolePermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class DepotAdminRolesDisplayContext {

	public DepotAdminRolesDisplayContext(HttpServletRequest httpServletRequest)
		throws PortalException {

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_permissionChecker = _themeDisplay.getPermissionChecker();

		_user = PortalUtil.getSelectedUser(httpServletRequest);
	}

	public List<UserGroupRole> getUserGroupRoles(int start, int end)
		throws PortalException {

		List<UserGroupRole> userGroupRoles = _getUserGroupRoles();

		return ListUtil.subList(userGroupRoles, start, end);
	}

	public int getUserGroupRolesCount() throws PortalException {
		List<UserGroupRole> userGroupRoles = _getUserGroupRoles();

		return userGroupRoles.size();
	}

	private boolean _contains(UserGroupRole userGroupRole) {
		try {
			Group group = userGroupRole.getGroup();
			Role role = userGroupRole.getRole();

			if (!_permissionChecker.isCompanyAdmin() &&
				!_permissionChecker.isGroupOwner(group.getGroupId())) {

				String roleName = role.getName();

				if (roleName.equals("Depot Administrator") ||
					roleName.equals("Depot Owner")) {

					return false;
				}
			}

			return UserGroupRolePermissionUtil.contains(
				_permissionChecker, group, role);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return false;
	}

	private List<UserGroupRole> _getUserGroupRoles() throws PortalException {
		if (_userGroupRoles != null) {
			return _userGroupRoles;
		}

		if (_user == null) {
			_userGroupRoles = Collections.emptyList();

			return _userGroupRoles;
		}

		List<UserGroupRole> userGroupRoles = ListUtil.filter(
			UserGroupRoleLocalServiceUtil.getUserGroupRoles(_user.getUserId()),
			this::_isDepotRole);

		if (!_isFilterManageableUserGroups()) {
			_userGroupRoles = userGroupRoles;

			return _userGroupRoles;
		}

		List<UserGroupRole> filteredUserGroupRoles = ListUtil.copy(
			userGroupRoles);

		Iterator<UserGroupRole> itr = filteredUserGroupRoles.iterator();

		while (itr.hasNext()) {
			UserGroupRole userGroupRole = itr.next();

			Role role = userGroupRole.getRole();

			String roleName = role.getName();

			if (roleName.equals("Depot Member")) {
				itr.remove();
			}
		}

		if (_permissionChecker.isCompanyAdmin()) {
			return filteredUserGroupRoles;
		}

		itr = filteredUserGroupRoles.iterator();

		while (itr.hasNext()) {
			if (!_contains(itr.next())) {
				itr.remove();
			}
		}

		_userGroupRoles = filteredUserGroupRoles;

		return _userGroupRoles;
	}

	private boolean _isDepotRole(UserGroupRole userGroupRole) {
		try {
			Group group = userGroupRole.getGroup();
			Role role = userGroupRole.getRole();

			if ((group != null) &&
				Objects.equals(group.getType(), GroupConstants.TYPE_DEPOT) &&
				(role != null) &&
				(role.getType() == RoleConstants.TYPE_DEPOT)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return false;
	}

	private boolean _isFilterManageableUserGroups() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String myAccountPortletId = PortletProviderUtil.getPortletId(
			PortalMyAccountApplicationType.MyAccount.CLASS_NAME,
			PortletProvider.Action.VIEW);

		if (Objects.equals(
				portletDisplay.getPortletName(), myAccountPortletId)) {

			return false;
		}
		else if (_permissionChecker.isCompanyAdmin()) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotAdminRolesDisplayContext.class);

	private final PermissionChecker _permissionChecker;
	private final ThemeDisplay _themeDisplay;
	private final User _user;
	private List<UserGroupRole> _userGroupRoles;

}