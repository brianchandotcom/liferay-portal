<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
String tabs1 = (String)request.getAttribute("edit_user_roles.jsp-tabs1");

int cur = (Integer)request.getAttribute("edit_user_roles.jsp-cur");

Group group = (Group)request.getAttribute("edit_user_roles.jsp-group");
String groupDescriptiveName = (String)request.getAttribute("edit_user_roles.jsp-groupDescriptiveName");
Role role = (Role)request.getAttribute("edit_user_roles.jsp-role");
long roleId = (Long)request.getAttribute("edit_user_roles.jsp-roleId");
Organization organization = (Organization)request.getAttribute("edit_user_roles.jsp-organization");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_user_roles.jsp-portletURL");
%>

<aui:input name="addUserIds" type="hidden" />
<aui:input name="removeUserIds" type="hidden" />

<div>
	<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"2", "2"}) %>

	<%= LanguageUtil.format(pageContext, "current-signifies-current-users-associated-with-the-x-role.-available-signifies-all-users-associated-with-the-x-x", new String[] {HtmlUtil.escape(role.getTitle(locale)), HtmlUtil.escape(groupDescriptiveName), LanguageUtil.get(pageContext, (group.isOrganization() ? "organization" : "site"))}) %>
</div>

<br />

<h3><liferay-ui:message key="users" /></h3>

<liferay-ui:tabs
	names="current,available"
	param="tabs1"
	url="<%= portletURL.toString() %>"
/>

<liferay-ui:membership-policy-error />

<liferay-ui:search-container
	rowChecker="<%= (role.getType() == RoleConstants.TYPE_SITE) ? new UserGroupRoleUserChecker(renderResponse, group, role) : new OrganizationRoleUserChecker(renderResponse, organization, role) %>"
	searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
	var="userSearchContainer"
>
	<liferay-ui:search-form
		page="/html/portlet/users_admin/user_search.jsp"
	/>

	<%
	UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

	LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

	userParams.put("inherit", Boolean.TRUE);
	userParams.put("usersGroups", new Long(group.getGroupId()));

	if (tabs1.equals("current")) {
		userParams.put("userGroupRole", new Long[] {new Long(group.getGroupId()), new Long(roleId)});
	}
	%>

	<liferay-ui:search-container-results>
		<%@ include file="/html/portlet/users_admin/user_search_results.jspf" %>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.User"
		escapedModel="<%= true %>"
		keyProperty="userId"
		modelVar="user2"
		rowIdProperty="screenName"
	>
		<liferay-ui:search-container-column-text
			name="name"
			property="fullName"
		/>

		<liferay-ui:search-container-column-text
			name="screen-name"
			property="screenName"
		/>
	</liferay-ui:search-container-row>

	<div class="separator"><!-- --></div>

	<%
	String taglibOnClick = renderResponse.getNamespace() + "updateUserGroupRoleUsers('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" primary="<%= true %>" value="update-associations" />

	<liferay-ui:search-iterator />
</liferay-ui:search-container>