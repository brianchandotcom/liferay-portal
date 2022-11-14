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