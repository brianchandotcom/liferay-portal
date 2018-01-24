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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.RoleServiceUtil;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class RoleDisplayContext {

	public RoleDisplayContext(
		HttpServletRequest request, RenderResponse renderResponse) {

		_request = request;
		_renderResponse = renderResponse;
	}

	public List<NavigationItem> getAssigneesNavigationItems(
			PortletURL portletURL)
		throws Exception {

		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem entriesNavigationItem = new NavigationItem();

		entriesNavigationItem.setActive(true);

		PortletURL usersPortletURL = PortletURLUtil.clone(
			portletURL, _renderResponse);

		usersPortletURL.setParameter("tabs2", "users");

		entriesNavigationItem.setHref(usersPortletURL.toString());

		String tabs2 = ParamUtil.getString(_request, "tabs2", "users");

		entriesNavigationItem.setLabel(LanguageUtil.get(_request, tabs2));

		navigationItems.add(entriesNavigationItem);

		return navigationItems;
	}

	public List<NavigationItem> getEditRoleNavigationItems() throws Exception {
		long roleId = ParamUtil.getLong(_request, "roleId");

		Role role = RoleServiceUtil.fetchRole(roleId);

		List<NavigationItem> navigationItems = new ArrayList<>();

		if (role != null) {
			List<String> tabNames = _getTabNames();
			Map<String, String> tabsURLMap = _getTabsURLMap();

			String tabs1 = ParamUtil.getString(_request, "tabs1");

			for (int i = 0; i < tabNames.size(); i++) {
				NavigationItem entriesNavigationItem = new NavigationItem();

				String tabName = tabNames.get(i);

				entriesNavigationItem.setActive(tabName.equals(tabs1));
				entriesNavigationItem.setHref(tabsURLMap.get(tabName));
				entriesNavigationItem.setLabel(
					LanguageUtil.get(_request, tabName));

				navigationItems.add(entriesNavigationItem);
			}

			return navigationItems;
		}

		NavigationItem detailsNavigationItem = new NavigationItem();

		detailsNavigationItem.setActive(true);
		detailsNavigationItem.setHref(_getCurrentURL());
		detailsNavigationItem.setLabel(LanguageUtil.get(_request, "details"));

		navigationItems.add(detailsNavigationItem);

		NavigationItem definePermissionsNavigationItem = new NavigationItem();

		definePermissionsNavigationItem.setActive(false);
		definePermissionsNavigationItem.setDisabled(true);
		definePermissionsNavigationItem.setHref(StringPool.BLANK);
		definePermissionsNavigationItem.setLabel(
			LanguageUtil.get(_request, "define-permissions"));

		navigationItems.add(definePermissionsNavigationItem);

		int type = ParamUtil.getInteger(
			_request, "type", RoleConstants.TYPE_REGULAR);

		if (type == RoleConstants.TYPE_REGULAR) {
			NavigationItem assigneesNavigationItem = new NavigationItem();

			assigneesNavigationItem.setActive(false);
			assigneesNavigationItem.setDisabled(false);
			assigneesNavigationItem.setHref(StringPool.BLANK);
			assigneesNavigationItem.setLabel(
				LanguageUtil.get(_request, "assignees"));

			navigationItems.add(assigneesNavigationItem);
		}

		return navigationItems;
	}

	public List<NavigationItem> getSelectRoleNavigationItems(String label) {
		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem entriesNavigationItem = new NavigationItem();

		entriesNavigationItem.setActive(true);
		entriesNavigationItem.setHref(StringPool.BLANK);
		entriesNavigationItem.setLabel(LanguageUtil.get(_request, label));

		navigationItems.add(entriesNavigationItem);

		return navigationItems;
	}

	public List<NavigationItem> getViewRoleNavigationItems(
			PortletURL portletURL)
		throws Exception {

		int type = ParamUtil.getInteger(_request, "type", 1);

		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem regularRolesNavigationItem = new NavigationItem();

		regularRolesNavigationItem.setActive(
			type == RoleConstants.TYPE_REGULAR);

		PortletURL regularRolesURL = PortletURLUtil.clone(
			portletURL, _renderResponse);

		regularRolesURL.setParameter(
			"type", String.valueOf(RoleConstants.TYPE_REGULAR));

		regularRolesNavigationItem.setHref(regularRolesURL.toString());

		regularRolesNavigationItem.setLabel(
			LanguageUtil.get(_request, "regular-roles"));

		navigationItems.add(regularRolesNavigationItem);

		NavigationItem siteRolesNavigationItem = new NavigationItem();

		siteRolesNavigationItem.setActive(type == RoleConstants.TYPE_SITE);

		PortletURL siteRolesURL = PortletURLUtil.clone(
			portletURL, _renderResponse);

		siteRolesURL.setParameter(
			"type", String.valueOf(RoleConstants.TYPE_SITE));

		siteRolesNavigationItem.setHref(siteRolesURL.toString());

		siteRolesNavigationItem.setLabel(
			LanguageUtil.get(_request, "site-roles"));

		navigationItems.add(siteRolesNavigationItem);

		NavigationItem organizationRolesNavigationItem = new NavigationItem();

		organizationRolesNavigationItem.setActive(
			type == RoleConstants.TYPE_ORGANIZATION);

		PortletURL organizationRolesURL = PortletURLUtil.clone(
			portletURL, _renderResponse);

		organizationRolesURL.setParameter(
			"type", String.valueOf(RoleConstants.TYPE_ORGANIZATION));

		organizationRolesNavigationItem.setHref(
			organizationRolesURL.toString());

		organizationRolesNavigationItem.setLabel(
			LanguageUtil.get(_request, "organization-roles"));

		navigationItems.add(organizationRolesNavigationItem);

		return navigationItems;
	}

	private String _getCurrentURL() {
		PortletRequest portletRequest = (PortletRequest)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse =
			(PortletResponse)_request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			PortalUtil.getLiferayPortletRequest(portletRequest),
			PortalUtil.getLiferayPortletResponse(portletResponse));

		return currentURLObj.toString();
	}

	private List<String> _getTabNames() throws Exception {
		List<String> tabNames = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long roleId = ParamUtil.getLong(_request, "roleId");

		Role role = RoleServiceUtil.fetchRole(roleId);

		if (RolePermissionUtil.contains(
				permissionChecker, role.getRoleId(), ActionKeys.UPDATE)) {

			tabNames.add("details");
		}

		String name = role.getName();

		if (!name.equals(RoleConstants.ADMINISTRATOR) &&
			!name.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) &&
			!name.equals(RoleConstants.ORGANIZATION_OWNER) &&
			!name.equals(RoleConstants.OWNER) &&
			!name.equals(RoleConstants.SITE_ADMINISTRATOR) &&
			!name.equals(RoleConstants.SITE_OWNER) &&
			RolePermissionUtil.contains(
				permissionChecker, role.getRoleId(),
				ActionKeys.DEFINE_PERMISSIONS)) {

			tabNames.add("define-permissions");
		}

		boolean unassignableRole = false;

		if (name.equals(RoleConstants.GUEST) ||
			name.equals(RoleConstants.OWNER) ||
			name.equals(RoleConstants.USER)) {

			unassignableRole = true;
		}

		if (!unassignableRole &&
			(role.getType() == RoleConstants.TYPE_REGULAR) &&
			RolePermissionUtil.contains(
				permissionChecker, role.getRoleId(),
				ActionKeys.ASSIGN_MEMBERS)) {

			tabNames.add("assignees");
		}

		return tabNames;
	}

	private Map<String, String> _getTabsURLMap() throws Exception {
		String redirect = ParamUtil.getString(_request, "redirect");

		String backURL = ParamUtil.getString(_request, "backURL", redirect);

		long roleId = ParamUtil.getLong(_request, "roleId");

		Role role = RoleServiceUtil.fetchRole(roleId);

		Map<String, String> tabsURLMap = new HashMap<>();

		PortletURL editRoleURL = _renderResponse.createRenderURL();

		editRoleURL.setParameter("mvcPath", "/edit_role.jsp");
		editRoleURL.setParameter("tabs1", "details");
		editRoleURL.setParameter("redirect", backURL);
		editRoleURL.setParameter("roleId", String.valueOf(role.getRoleId()));

		tabsURLMap.put("details", editRoleURL.toString());

		PortletURL definePermissionsURL = _renderResponse.createRenderURL();

		definePermissionsURL.setParameter(
			"mvcPath", "/edit_role_permissions.jsp");
		definePermissionsURL.setParameter("tabs1", "define-permissions");
		definePermissionsURL.setParameter("redirect", backURL);
		definePermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
		definePermissionsURL.setParameter(
			"roleId", String.valueOf(role.getRoleId()));

		tabsURLMap.put("define-permissions", definePermissionsURL.toString());

		PortletURL assignMembersURL = _renderResponse.createRenderURL();

		assignMembersURL.setParameter("mvcPath", "/edit_role_assignments.jsp");
		assignMembersURL.setParameter("tabs1", "assignees");
		assignMembersURL.setParameter("redirect", backURL);
		assignMembersURL.setParameter(
			"roleId", String.valueOf(role.getRoleId()));

		tabsURLMap.put("assignees", assignMembersURL.toString());

		return tabsURLMap;
	}

	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}