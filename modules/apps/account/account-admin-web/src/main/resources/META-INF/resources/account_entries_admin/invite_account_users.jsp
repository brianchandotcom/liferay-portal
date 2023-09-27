<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
InviteUsersDisplayContext inviteUsersDisplayContext = new InviteUsersDisplayContext();
%>

<portlet:actionURL name="/account_admin/invite_account_users" var="inviteAccountUsersURL">
	<portlet:param name="accountEntryId" value='<%= ParamUtil.getString(request, "accountEntryId") %>' />
</portlet:actionURL>

<react:component
	module="account_entries_admin/js/InviteUsersForm"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"accountEntryId", ParamUtil.getString(request, "accountEntryId")
		).put(
			"availableAccountRoles", inviteUsersDisplayContext.getAvailableAccountRolesMultiselectItems(ParamUtil.getLong(request, "accountEntryId"), themeDisplay.getCompanyId())
		).put(
			"inviteAccountUsersURL", inviteAccountUsersURL
		).put(
			"portletNamespace", liferayPortletResponse.getNamespace()
		).put(
			"redirectURL", ParamUtil.getString(request, "redirect")
		).build()
	%>'
/>