<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "details");

String redirect = ParamUtil.getString(request, "redirect");

long passwordPolicyId = ParamUtil.getLong(request, "passwordPolicyId");

PasswordPolicy passwordPolicy = PasswordPolicyLocalServiceUtil.fetchPasswordPolicy(passwordPolicyId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("redirect", redirect);
portletURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicyId));

boolean hasAssignMembersPermission = false;

if (passwordPolicy != null) {
	hasAssignMembersPermission = PasswordPolicyPermissionUtil.contains(permissionChecker, passwordPolicy.getPasswordPolicyId(), ActionKeys.ASSIGN_MEMBERS);
}

List<NavigationItem> navigationItems = new ArrayList<>();

NavigationItem detailsNavigationItem = new NavigationItem();

detailsNavigationItem.setActive(tabs1.equals("details"));

PortletURL detailsURL = PortletURLUtil.clone(portletURL, renderResponse);

detailsURL.setParameter("mvcPath", "/edit_password_policy.jsp");
detailsURL.setParameter("tabs1", "details");

detailsNavigationItem.setHref(detailsURL.toString());
detailsNavigationItem.setLabel(LanguageUtil.get(request, "details"));

navigationItems.add(detailsNavigationItem);

NavigationItem assigneesNavigationItem = new NavigationItem();

assigneesNavigationItem.setActive(tabs1.equals("assignees"));

boolean isShowNav = false;

if ((passwordPolicy != null) && hasAssignMembersPermission) {
	isShowNav = true;
}

assigneesNavigationItem.setDisabled(!isShowNav);

PortletURL assigneesURL = assigneesURL = PortletURLUtil.clone(portletURL, renderResponse);

assigneesURL.setParameter("mvcPath", "/edit_password_policy_assignments.jsp");
assigneesURL.setParameter("tabs1", "assignees");

assigneesNavigationItem.setHref(isShowNav ? assigneesURL.toString() : StringPool.BLANK);
assigneesNavigationItem.setLabel(LanguageUtil.get(request, "assignees"));

navigationItems.add(assigneesNavigationItem);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= navigationItems %>"
/>