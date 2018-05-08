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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PasswordPolicyPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
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
		HttpServletRequest request, RenderResponse renderResponse) {

		_request = request;
		_renderResponse = renderResponse;
	}

	public List<NavigationItem> getEditPasswordPolicyNavigationItems()
		throws PortletException {

		String tabs1 = ParamUtil.getString(_request, "tabs1", "details");
		String redirect = ParamUtil.getString(_request, "redirect");

		long passwordPolicyId = ParamUtil.getLong(_request, "passwordPolicyId");

		PasswordPolicy passwordPolicy =
			PasswordPolicyLocalServiceUtil.fetchPasswordPolicy(
				passwordPolicyId);

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter(
			"passwordPolicyId", String.valueOf(passwordPolicyId));

		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem detailsNavigationItem = new NavigationItem();

		detailsNavigationItem.setActive(tabs1.equals("details"));

		PortletURL detailsURL = PortletURLUtil.clone(
			portletURL, _renderResponse);

		detailsURL.setParameter("mvcPath", "/edit_password_policy.jsp");
		detailsURL.setParameter("tabs1", "details");

		detailsNavigationItem.setHref(detailsURL.toString());

		detailsNavigationItem.setLabel(LanguageUtil.get(_request, "details"));

		navigationItems.add(detailsNavigationItem);

		NavigationItem assigneesNavigationItem = new NavigationItem();

		assigneesNavigationItem.setActive(tabs1.equals("assignees"));

		boolean showNav = false;

		if ((passwordPolicy != null) && hasAssignMembersPermission()) {
			showNav = true;
		}

		assigneesNavigationItem.setDisabled(!showNav);

		PortletURL assigneesURL = PortletURLUtil.clone(
			portletURL, _renderResponse);

		assigneesURL.setParameter(
			"mvcPath", "/edit_password_policy_assignments.jsp");
		assigneesURL.setParameter("tabs1", "assignees");

		assigneesNavigationItem.setHref(
			showNav ? assigneesURL.toString() : StringPool.BLANK);

		assigneesNavigationItem.setLabel(
			LanguageUtil.get(_request, "assignees"));

		navigationItems.add(assigneesNavigationItem);

		return navigationItems;
	}

	public List<NavigationItem> getSelectMembersNavigationItems() {
		String tabs2 = ParamUtil.getString(_request, "tabs2", "users");

		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem entriesNavigationItem = new NavigationItem();

		entriesNavigationItem.setActive(true);
		entriesNavigationItem.setHref(StringPool.BLANK);
		entriesNavigationItem.setLabel(LanguageUtil.get(_request, tabs2));

		navigationItems.add(entriesNavigationItem);

		return navigationItems;
	}

	public List<NavigationItem> getViewPasswordPoliciesNavigationItems() {
		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem entriesNavigationItem = new NavigationItem();

		entriesNavigationItem.setActive(true);
		entriesNavigationItem.setHref(StringPool.BLANK);
		entriesNavigationItem.setLabel(
			LanguageUtil.get(_request, "password-policies"));

		navigationItems.add(entriesNavigationItem);

		return navigationItems;
	}

	public boolean hasAssignMembersPermission() {
		long passwordPolicyId = ParamUtil.getLong(_request, "passwordPolicyId");

		PasswordPolicy passwordPolicy =
			PasswordPolicyLocalServiceUtil.fetchPasswordPolicy(
				passwordPolicyId);

		if (passwordPolicy == null) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return PasswordPolicyPermissionUtil.contains(
			themeDisplay.getPermissionChecker(),
			passwordPolicy.getPasswordPolicyId(), ActionKeys.ASSIGN_MEMBERS);
	}

	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}