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
long accountEntryId = ParamUtil.getLong(request, "accountEntryId");
long accountRoleId = ParamUtil.getLong(request, "accountRoleId");
String backURL = ParamUtil.getString(request, "backURL");

if (Validator.isNull(backURL)) {
	PortletURL viewAccountRolesURL = renderResponse.createRenderURL();

	viewAccountRolesURL.setParameter("mvcRenderCommandName", "/account_admin/edit_account_entry");
	viewAccountRolesURL.setParameter("screenNavigationCategoryKey", AccountScreenNavigationEntryConstants.CATEGORY_KEY_ROLES);
	viewAccountRolesURL.setParameter("accountEntryId", String.valueOf(accountEntryId));

	backURL = viewAccountRolesURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

AccountRole accountRole = AccountRoleLocalServiceUtil.fetchAccountRole(accountRoleId);

Role role = null;

if (accountRole != null) {
	role = accountRole.getRole();
}

renderResponse.setTitle((role == null) ? LanguageUtil.get(request, "add-new-role") : role.getTitle(locale));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/account_entries_admin/edit_account_role.jsp");
portletURL.setParameter("accountEntryId", String.valueOf(accountEntryId));
portletURL.setParameter("accountRoleId", String.valueOf(accountRoleId));
%>

<liferay-frontend:screen-navigation
	containerWrapperCssClass=""
	context="<%= accountRole %>"
	headerContainerCssClass=""
	inverted="<%= true %>"
	key="<%= AccountScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_ACCOUNT_ROLE %>"
	portletURL="<%= portletURL %>"
/>