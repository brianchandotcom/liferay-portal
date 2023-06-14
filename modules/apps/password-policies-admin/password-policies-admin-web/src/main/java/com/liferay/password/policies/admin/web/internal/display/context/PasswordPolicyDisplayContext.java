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

package com.liferay.password.policies.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PasswordPolicyPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class PasswordPolicyDisplayContext {

	public PasswordPolicyDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;

		_passwordPolicyId = ParamUtil.getLong(
			httpServletRequest, "passwordPolicyId");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_permissionChecker = themeDisplay.getPermissionChecker();
	}

	public List<NavigationItem> getEditPasswordPolicyAssignmentsNavigationItems(
		PortletURL portletURL) {

		String tabs2 = ParamUtil.getString(
			_httpServletRequest, "tabs2", "users");

		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(tabs2.equals("users"));
				navigationItem.setHref(portletURL, "tabs2", "users");
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "users"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(tabs2.equals("organizations"));
				navigationItem.setHref(portletURL, "tabs2", "organizations");
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "organizations"));
			}
		).build();
	}

	public List<NavigationItem> getEditPasswordPolicyNavigationItems()
		throws PortletException {

		String tabs1 = ParamUtil.getString(
			_httpServletRequest, "tabs1", "details");

		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setRedirect(
			ParamUtil.getString(_httpServletRequest, "redirect")
		).setParameter(
			"passwordPolicyId", _passwordPolicyId
		).buildPortletURL();

		List<NavigationItem> navigationItems = NavigationItemListBuilder.add(
			() -> (_passwordPolicyId == 0) || _hasPermission(ActionKeys.UPDATE),
			navigationItem -> {
				navigationItem.setActive(tabs1.equals("details"));
				navigationItem.setHref(
					PortletURLBuilder.create(
						PortletURLUtil.clone(portletURL, _renderResponse)
					).setMVCPath(
						"/edit_password_policy.jsp"
					).setTabs1(
						"details"
					).buildString());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "details"));
			}
		).add(
			() -> _hasPermission(ActionKeys.ASSIGN_MEMBERS),
			navigationItem -> {
				navigationItem.setActive(tabs1.equals("assignees"));
				navigationItem.setHref(
					PortletURLBuilder.create(
						PortletURLUtil.clone(portletURL, _renderResponse)
					).setMVCPath(
						"/edit_password_policy_assignments.jsp"
					).setTabs1(
						"assignees"
					).buildString());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "assignees"));
			}
		).build();

		if (navigationItems.isEmpty()) {
			return null;
		}

		return navigationItems;
	}

	public boolean hasPermission(String actionId, long passwordPolicyId) {
		return _hasPermission(actionId, passwordPolicyId);
	}

	private boolean _hasPermission(String actionId) {
		return _hasPermission(actionId, _passwordPolicyId);
	}

	private boolean _hasPermission(String actionId, long passwordPolicyId) {
		if (passwordPolicyId <= 0) {
			return false;
		}

		return PasswordPolicyPermissionUtil.contains(
			_permissionChecker, passwordPolicyId, actionId);
	}

	private final HttpServletRequest _httpServletRequest;
	private final Long _passwordPolicyId;
	private final PermissionChecker _permissionChecker;
	private final RenderResponse _renderResponse;

}