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

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(portletURL, "tabs2", "users");

						String tabs2 = ParamUtil.getString(
							_request, "tabs2", "users");

						navigationItem.setLabel(
							LanguageUtil.get(_request, tabs2));
					});
			}
		};
	}

	public List<NavigationItem> getEditRoleNavigationItems() throws Exception {
		long roleId = ParamUtil.getLong(_request, "roleId");

		Role role = RoleServiceUtil.fetchRole(roleId);

		if (role != null) {
			List<String> tabNames = _getTabNames();
			Map<String, String> tabsURLMap = _getTabsURLMap();

			String tabs1 = ParamUtil.getString(_request, "tabs1");

			return new NavigationItemList() {
				{
					for (String tabName : tabNames) {
						add(
							navigationItem -> {
								navigationItem.setActive(tabName.equals(tabs1));
								navigationItem.setHref(tabsURLMap.get(tabName));
								navigationItem.setLabel(
									LanguageUtil.get(_request, tabName));
							});
					}
				}
			};
		}

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(_getCurrentURL());
						navigationItem.setLabel(
							LanguageUtil.get(_request, "details"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(false);
						navigationItem.setDisabled(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(
							LanguageUtil.get(_request, "define-permissions"));
					});

				int type = ParamUtil.getInteger(
					_request, "type", RoleConstants.TYPE_REGULAR);

				if (type == RoleConstants.TYPE_REGULAR) {
					add(
						navigationItem -> {
							navigationItem.setActive(false);
							navigationItem.setDisabled(false);
							navigationItem.setHref(StringPool.BLANK);
							navigationItem.setLabel(
								LanguageUtil.get(_request, "assignees"));
						});
				}
			}
		};
	}

	public List<NavigationItem> getSelectRoleNavigationItems(String label) {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(
							LanguageUtil.get(_request, label));
					});
			}
		};
	}

	public List<NavigationItem> getViewRoleNavigationItems(
			PortletURL portletURL)
		throws Exception {

		int type = ParamUtil.getInteger(_request, "type", 1);

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(
							type == RoleConstants.TYPE_REGULAR);
						navigationItem.setHref(
							portletURL, "type", RoleConstants.TYPE_REGULAR);
						navigationItem.setLabel(
							LanguageUtil.get(_request, "regular-roles"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(
							type == RoleConstants.TYPE_SITE);
						navigationItem.setHref(
							portletURL, "type", RoleConstants.TYPE_SITE);
						navigationItem.setLabel(
							LanguageUtil.get(_request, "site-roles"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(
							type == RoleConstants.TYPE_ORGANIZATION);
						navigationItem.setHref(
							portletURL, "type",
							RoleConstants.TYPE_ORGANIZATION);
						navigationItem.setLabel(
							LanguageUtil.get(_request, "organization-roles"));
					});
			}
		};
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