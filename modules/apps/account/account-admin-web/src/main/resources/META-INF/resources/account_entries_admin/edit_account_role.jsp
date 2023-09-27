<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

long accountEntryId = ParamUtil.getLong(request, "accountEntryId");

if (Validator.isNull(backURL)) {
	backURL = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCRenderCommandName(
		"/account_admin/edit_account_entry"
	).setParameter(
		"accountEntryId", accountEntryId
	).setParameter(
		"screenNavigationCategoryKey", AccountScreenNavigationEntryConstants.CATEGORY_KEY_ROLES
	).buildString();
}

long accountRoleId = ParamUtil.getLong(request, "accountRoleId");

AccountRole accountRole = AccountRoleLocalServiceUtil.fetchAccountRole(accountRoleId);

Role role = null;

if (accountRole != null) {
	role = accountRole.getRole();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((role == null) ? LanguageUtil.get(request, "add-new-role") : role.getTitle(locale));
%>

<liferay-frontend:screen-navigation
	context="<%= accountRole %>"
	key="<%= AccountScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_ACCOUNT_ROLE %>"
	portletURL='<%=
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCPath(
			"/account_entries_admin/edit_account_role.jsp"
		).setParameter(
			"accountEntryId", accountEntryId
		).setParameter(
			"accountRoleId", accountRoleId
		).buildPortletURL()
	%>'
/>