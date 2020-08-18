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

package com.liferay.users.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class UserDisplayContext {

	public UserDisplayContext(
			HttpServletRequest httpServletRequest,
			InitDisplayContext initDisplayContext)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_initDisplayContext = initDisplayContext;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_permissionChecker = themeDisplay.getPermissionChecker();

		_renderResponse = (RenderResponse)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		_selUser = PortalUtil.getSelectedUser(httpServletRequest);
		_themeDisplay = themeDisplay;
	}

	public Contact getContact() throws PortalException {
		if (_selUser != null) {
			return _selUser.getContact();
		}

		return null;
	}

	public List<Group> getGroups() throws PortalException {
		if (_selUser == null) {
			return Collections.emptyList();
		}

		if (!_initDisplayContext.isFilterManageableGroups()) {
			return _selUser.getGroups();
		}

		return UsersAdminUtil.filterGroups(
			_themeDisplay.getPermissionChecker(), _selUser.getGroups());
	}

	public List<Group> getInheritedSiteGroups() throws PortalException {
		SortedSet<Group> inheritedSiteGroupsSet = new TreeSet<>();

		inheritedSiteGroupsSet.addAll(
			GroupLocalServiceUtil.getUserGroupsRelatedGroups(getUserGroups()));
		inheritedSiteGroupsSet.addAll(_getOrganizationRelatedGroups());

		return ListUtil.fromCollection(inheritedSiteGroupsSet);
	}

	public List<UserGroupGroupRole> getInheritedSiteRoles() {
		if (_selUser == null) {
			return Collections.emptyList();
		}

		return UserGroupGroupRoleLocalServiceUtil.getUserGroupGroupRolesByUser(
			_selUser.getUserId());
	}

	public List<UserGroupRole> getOrganizationRoles() throws PortalException {
		return ListUtil.filter(_getUserGroupRoles(), this::_isOrganizationRole);
	}

	public List<Organization> getOrganizations() throws PortalException {
		if (_selUser != null) {
			if (!_initDisplayContext.isFilterManageableOrganizations()) {
				return _selUser.getOrganizations();
			}

			return UsersAdminUtil.filterOrganizations(
				_themeDisplay.getPermissionChecker(),
				_selUser.getOrganizations());
		}

		String organizationIds = ParamUtil.getString(
			_httpServletRequest, "organizationsSearchContainerPrimaryKeys");

		if (Validator.isNull(organizationIds)) {
			return Collections.emptyList();
		}

		long[] organizationIdsArray = StringUtil.split(organizationIds, 0L);

		return OrganizationLocalServiceUtil.getOrganizations(
			organizationIdsArray);
	}

	public PasswordPolicy getPasswordPolicy() throws PortalException {
		if (_selUser != null) {
			return _selUser.getPasswordPolicy();
		}

		return PasswordPolicyLocalServiceUtil.getDefaultPasswordPolicy(
			_themeDisplay.getCompanyId());
	}

	public List<Group> getRoleGroups() throws PortalException {
		return ListUtil.filter(
			_getAllGroups(),
			group -> RoleLocalServiceUtil.hasGroupRoles(group.getGroupId()));
	}

	public List<Role> getRoles() {
		if (_selUser == null) {
			return Collections.emptyList();
		}

		if (!_initDisplayContext.isFilterManageableRoles()) {
			return _selUser.getRoles();
		}

		return UsersAdminUtil.filterRoles(
			_permissionChecker, _selUser.getRoles());
	}

	public User getSelectedUser() {
		return _selUser;
	}

	public List<Group> getSiteGroups() throws PortalException {
		if (_selUser == null) {
			return Collections.emptyList();
		}

		if (!_initDisplayContext.isFilterManageableGroups()) {
			return _selUser.getSiteGroups();
		}

		return UsersAdminUtil.filterGroups(
			_themeDisplay.getPermissionChecker(), _selUser.getSiteGroups());
	}

	public List<UserGroupRole> getSiteRoles() throws PortalException {
		return ListUtil.filter(_getUserGroupRoles(), this::_isSiteRole);
	}

	public List<UserGroup> getUserGroups() {
		if (_selUser == null) {
			return Collections.emptyList();
		}

		if (!_initDisplayContext.isFilterManageableUserGroups()) {
			return _selUser.getUserGroups();
		}

		return UsersAdminUtil.filterUserGroups(
			_permissionChecker, _selUser.getUserGroups());
	}

	public List<NavigationItem> getViewNavigationItems() {
		String toolbarItem = ParamUtil.getString(
			_httpServletRequest, "toolbarItem", "view-all-users");

		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(toolbarItem.equals("view-all-users"));
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "toolbarItem",
					"view-all-users", "usersListView",
					UserConstants.LIST_VIEW_FLAT_USERS);
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "users"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(
					toolbarItem.equals("view-all-organizations"));
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "toolbarItem",
					"view-all-organizations", "usersListView",
					UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS);
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "organizations"));
			}
		).build();
	}

	private List<Group> _getAllGroups() throws PortalException {
		List<Group> allGroups = new ArrayList<>();

		allGroups.addAll(getGroups());
		allGroups.addAll(getInheritedSiteGroups());
		allGroups.addAll(
			GroupLocalServiceUtil.getOrganizationsGroups(getOrganizations()));
		allGroups.addAll(
			GroupLocalServiceUtil.getUserGroupsGroups(getUserGroups()));

		return allGroups;
	}

	private List<Group> _getOrganizationRelatedGroups() throws PortalException {
		List<Organization> organizations = getOrganizations();

		if (organizations.isEmpty()) {
			return Collections.emptyList();
		}

		return GroupLocalServiceUtil.getOrganizationsRelatedGroups(
			organizations);
	}

	private List<UserGroupRole> _getUserGroupRoles() throws PortalException {
		if (_selUser == null) {
			return Collections.emptyList();
		}

		List<UserGroupRole> userGroupRoles =
			UserGroupRoleLocalServiceUtil.getUserGroupRoles(
				_selUser.getUserId());

		if (!_initDisplayContext.isFilterManageableUserGroupRoles()) {
			return userGroupRoles;
		}

		return UsersAdminUtil.filterUserGroupRoles(
			_permissionChecker, userGroupRoles);
	}

	private boolean _isOrganizationRole(UserGroupRole userGroupRole) {
		Role role = RoleLocalServiceUtil.fetchRole(userGroupRole.getRoleId());

		if ((role != null) &&
			(role.getType() == RoleConstants.TYPE_ORGANIZATION)) {

			return true;
		}

		return false;
	}

	private boolean _isSiteRole(UserGroupRole userGroupRole) {
		try {
			Group group = userGroupRole.getGroup();
			Role role = userGroupRole.getRole();

			if ((group != null) && group.isSite() && (role != null) &&
				(role.getType() == RoleConstants.TYPE_SITE)) {

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

	private static final Log _log = LogFactoryUtil.getLog(
		UserDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final InitDisplayContext _initDisplayContext;
	private final PermissionChecker _permissionChecker;
	private final RenderResponse _renderResponse;
	private final User _selUser;
	private final ThemeDisplay _themeDisplay;

}