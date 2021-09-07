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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Permission;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.RoleServiceUtil;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;
import com.liferay.roles.admin.web.internal.role.type.contributor.util.RoleTypeContributorRetrieverUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class RoleDisplayContext {

	public RoleDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;

		_currentRoleTypeContributor =
			RoleTypeContributorRetrieverUtil.getCurrentRoleTypeContributor(
				httpServletRequest);
	}

	public List<NavigationItem> getEditRoleNavigationItems() throws Exception {
		List<String> tabsNames = _getTabsNames();
		Map<String, String> tabsURLs = _getTabsURLs();

		String tabs1 = ParamUtil.getString(_httpServletRequest, "tabs1");

		return new NavigationItemList() {
			{
				for (String tabsName : tabsNames) {
					add(
						navigationItem -> {
							navigationItem.setActive(tabsName.equals(tabs1));
							navigationItem.setHref(tabsURLs.get(tabsName));
							navigationItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, tabsName));
						});
				}
			}
		};
	}

	public String getEditRolePermissionsTabs1() {
		if (isAccountRoleGroupScope()) {
			return "define-group-scope-permissions";
		}

		return "define-permissions";
	}

	public List<NavigationItem> getRoleAssignmentsNavigationItems(
			PortletURL portletURL)
		throws Exception {

		String tabs2 = ParamUtil.getString(
			_httpServletRequest, "tabs2", "users");

		return new NavigationItemList() {
			{
				for (String assigneeTypeName : _ASSIGNEE_TYPE_NAMES) {
					add(
						navigationItem -> {
							navigationItem.setActive(
								assigneeTypeName.equals(tabs2));
							navigationItem.setHref(
								portletURL, "tabs2", assigneeTypeName);
							navigationItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, assigneeTypeName));
						});
				}
			}
		};
	}

	public List<NavigationItem> getSelectAssigneesNavigationItems(
			PortletURL portletURL)
		throws Exception {

		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setHref(portletURL, "tabs2", "users");

				String tabs2 = ParamUtil.getString(
					_httpServletRequest, "tabs2", "users");

				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, tabs2));
			}
		).build();
	}

	public List<NavigationItem> getViewRoleNavigationItems(
			LiferayPortletResponse liferayPortletResponse,
			PortletURL portletURL)
		throws Exception {

		NavigationItemList navigationItemList = new NavigationItemList();

		for (RoleTypeContributor roleTypeContributor :
				RoleTypeContributorRetrieverUtil.getRoleTypeContributors(
					_httpServletRequest)) {

			navigationItemList.add(
				navigationItem -> {
					navigationItem.setActive(
						_currentRoleTypeContributor.getType() ==
							roleTypeContributor.getType());

					PortletURL viewRegularRoleNavigationURL =
						PortletURLUtil.clone(
							portletURL, liferayPortletResponse);

					navigationItem.setHref(
						viewRegularRoleNavigationURL, "roleType",
						roleTypeContributor.getType());

					navigationItem.setLabel(
						LanguageUtil.get(
							_httpServletRequest,
							roleTypeContributor.getTabTitle(
								_httpServletRequest.getLocale())));
				});
		}

		return navigationItemList;
	}

	public boolean isAccountRoleGroupScope() {
		if (_accountRoleGroupScope == null) {
			_accountRoleGroupScope = false;

			if ((_currentRoleTypeContributor.getType() ==
					RoleConstants.TYPE_ACCOUNT) &&
				ParamUtil.getBoolean(
					_httpServletRequest, "accountRoleGroupScope")) {

				_accountRoleGroupScope = true;
			}
		}

		return _accountRoleGroupScope;
	}

	public boolean isAllowGroupScope() {
		if (_allowGroupScope == null) {
			_allowGroupScope = false;

			if ((_currentRoleTypeContributor.getType() ==
					RoleConstants.TYPE_REGULAR) ||
				isAccountRoleGroupScope()) {

				_allowGroupScope = true;
			}
		}

		return _allowGroupScope;
	}

	public boolean isAutomaticallyAssigned(Role role) {
		List<RoleTypeContributor> roleTypeContributors =
			RoleTypeContributorRetrieverUtil.getRoleTypeContributors(
				_httpServletRequest);

		for (RoleTypeContributor roleTypeContributor : roleTypeContributors) {
			if (roleTypeContributor.isAutomaticallyAssigned(role)) {
				return true;
			}
		}

		return false;
	}

	public boolean isValidPermission(Role role, Permission permission) {
		if (role.getType() != RoleConstants.TYPE_ACCOUNT) {
			return true;
		}

		if (isAccountRoleGroupScope() &&
			((permission.getScope() == ResourceConstants.SCOPE_COMPANY) ||
			 (permission.getScope() == ResourceConstants.SCOPE_GROUP))) {

			return true;
		}
		else if (permission.getScope() ==
					ResourceConstants.SCOPE_GROUP_TEMPLATE) {

			return true;
		}

		return false;
	}

	private List<String> _getTabsNames() throws Exception {
		List<String> tabsNames = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long roleId = ParamUtil.getLong(_httpServletRequest, "roleId");

		Role role = RoleServiceUtil.fetchRole(roleId);

		if (RolePermissionUtil.contains(
				permissionChecker, role.getRoleId(), ActionKeys.UPDATE)) {

			tabsNames.add("details");
		}

		if (_currentRoleTypeContributor.isAllowDefinePermissions(role) &&
			RolePermissionUtil.contains(
				permissionChecker, role.getRoleId(),
				ActionKeys.DEFINE_PERMISSIONS)) {

			tabsNames.add("define-permissions");

			if (role.getType() == RoleConstants.TYPE_ACCOUNT) {
				tabsNames.add("define-group-scope-permissions");
			}
		}

		if (_currentRoleTypeContributor.isAllowAssignMembers(role) &&
			RolePermissionUtil.contains(
				permissionChecker, role.getRoleId(),
				ActionKeys.ASSIGN_MEMBERS)) {

			tabsNames.add("assignees");
		}

		return tabsNames;
	}

	private Map<String, String> _getTabsURLs() throws Exception {
		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		String backURL = ParamUtil.getString(
			_httpServletRequest, "backURL", redirect);

		long roleId = ParamUtil.getLong(_httpServletRequest, "roleId");

		Role role = RoleServiceUtil.fetchRole(roleId);

		return HashMapBuilder.put(
			"assignees",
			() -> PortletURLBuilder.createRenderURL(
				_renderResponse
			).setMVCPath(
				"/edit_role_assignments.jsp"
			).setRedirect(
				redirect
			).setBackURL(
				backURL
			).setTabs1(
				"assignees"
			).setParameter(
				"roleId", role.getRoleId()
			).buildString()
		).put(
			"define-group-scope-permissions",
			() -> PortletURLBuilder.createRenderURL(
				_renderResponse
			).setMVCPath(
				"/edit_role_permissions.jsp"
			).setCMD(
				Constants.VIEW
			).setRedirect(
				redirect
			).setBackURL(
				backURL
			).setTabs1(
				"define-group-scope-permissions"
			).setParameter(
				"accountRoleGroupScope", true
			).setParameter(
				"roleId", role.getRoleId()
			).buildString()
		).put(
			"define-permissions",
			() -> PortletURLBuilder.createRenderURL(
				_renderResponse
			).setMVCPath(
				"/edit_role_permissions.jsp"
			).setCMD(
				Constants.VIEW
			).setRedirect(
				redirect
			).setBackURL(
				backURL
			).setTabs1(
				"define-permissions"
			).setParameter(
				"roleId", role.getRoleId()
			).buildString()
		).put(
			"details",
			() -> PortletURLBuilder.createRenderURL(
				_renderResponse
			).setMVCPath(
				"/edit_role.jsp"
			).setRedirect(
				redirect
			).setBackURL(
				backURL
			).setTabs1(
				"details"
			).setParameter(
				"roleId", role.getRoleId()
			).buildString()
		).build();
	}

	private static final String[] _ASSIGNEE_TYPE_NAMES = {
		"users", "sites", "organizations", "user-groups", "segments"
	};

	private Boolean _accountRoleGroupScope;
	private Boolean _allowGroupScope;
	private final RoleTypeContributor _currentRoleTypeContributor;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;

}