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
import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.UserGroupRolePermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Cristina González
 */
public class DepotAdminRolesDisplayContext {

	public DepotAdminRolesDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_permissionChecker = _themeDisplay.getPermissionChecker();

		_user = PortalUtil.getSelectedUser(liferayPortletRequest);
	}

	public String getAssetLibraryLabel() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		return ResourceBundleUtil.getString(resourceBundle, "asset-library");
	}

	public String getDepotRoleSyncEntitiesEventName() {
		String portletNamespace = PortalUtil.getPortletNamespace(
			DepotPortletKeys.DEPOT_ADMIN);

		return portletNamespace + "syncDepotRoles";
	}

	public String getLabel() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		return ResourceBundleUtil.getString(
			resourceBundle, "asset-library-roles");
	}

	public String getSelectDepotRolesEventName() {
		String portletNamespace = PortalUtil.getPortletNamespace(
			DepotPortletKeys.DEPOT_ADMIN);

		return portletNamespace + "selectDepotRole";
	}

	public PortletURL getSelectDepotRolesURL() throws WindowStateException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest);

		PortletURL portletURL = requestBackedPortletURLFactory.createRenderURL(
			DepotPortletKeys.DEPOT_ADMIN);

		portletURL.setParameter("mvcRenderCommandName", "/depot/select_role");
		portletURL.setParameter(
			"p_u_i_d",
			Optional.ofNullable(
				_user
			).map(
				User::getUserId
			).map(
				String::valueOf
			).orElse(
				"0"
			));
		portletURL.setParameter("step", "1");
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL;
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

	public boolean isDeletable() {
		return isSelectable();
	}

	public boolean isSelectable() {
		String myAccountPortletId = PortletProviderUtil.getPortletId(
			PortalMyAccountApplicationType.MyAccount.CLASS_NAME,
			PortletProvider.Action.VIEW);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		if (!Objects.equals(
				portletDisplay.getPortletName(), myAccountPortletId)) {

			return true;
		}

		return false;
	}

	private boolean _contains(UserGroupRole userGroupRole) {
		try {
			Group group = userGroupRole.getGroup();
			Role role = userGroupRole.getRole();

			if (!_permissionChecker.isCompanyAdmin() &&
				!_permissionChecker.isGroupOwner(group.getGroupId())) {

				String roleName = role.getName();

				if (Objects.equals(
						roleName,
						DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR) ||
					Objects.equals(
						roleName, DepotRolesConstants.ASSET_LIBRARY_OWNER)) {

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

			if (Objects.equals(
					role.getName(), DepotRolesConstants.ASSET_LIBRARY_MEMBER)) {

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

	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PermissionChecker _permissionChecker;
	private final ThemeDisplay _themeDisplay;
	private final User _user;
	private List<UserGroupRole> _userGroupRoles;

}