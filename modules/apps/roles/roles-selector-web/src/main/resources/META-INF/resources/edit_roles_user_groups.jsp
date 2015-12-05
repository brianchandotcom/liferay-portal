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
String tabs1 = (String)request.getAttribute("edit_roles.jsp-tabs1");

int cur = (Integer)request.getAttribute("edit_roles.jsp-cur");

Group group = (Group)request.getAttribute("edit_roles.jsp-group");
String groupDescriptiveName = (String)request.getAttribute("edit_roles.jsp-groupDescriptiveName");
Role role = (Role)request.getAttribute("edit_roles.jsp-role");
long roleId = (Long)request.getAttribute("edit_roles.jsp-roleId");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_roles.jsp-portletURL");
%>

<aui:input name="addUserGroupIds" type="hidden" />
<aui:input name="removeUserGroupIds" type="hidden" />

<div>
	<liferay-ui:message arguments='<%= new String[] {"2", "2"} %>' key="step-x-of-x" translateArguments="<%= false %>" />

	<liferay-ui:message arguments='<%= new String[] {HtmlUtil.escape(role.getTitle(locale)), HtmlUtil.escape(groupDescriptiveName), LanguageUtil.get(request, (group.isOrganization() ? LanguageUtil.get(request, "organization") : LanguageUtil.get(request, "site")))} %>' key="current-signifies-current-user-groups-associated-with-the-x-role.-available-signifies-all-user-groups-associated-with-the-x-x" translateArguments="<%= false %>" />
</div>

<br />

<liferay-ui:tabs
	names="current,available"
	param="tabs1"
	url="<%= portletURL.toString() %>"
/>

<liferay-ui:search-container
	rowChecker="<%= new UserGroupGroupRoleUserGroupChecker(renderResponse, group, role) %>"
	searchContainer="<%= new UserGroupSearch(renderRequest, portletURL) %>"
>
	<liferay-ui:input-search />

	<%
	UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)searchContainer.getSearchTerms();

	LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

	if (group.isSite()) {
		userGroupParams.put("userGroupsGroups", Long.valueOf(group.getGroupId()));
	}

	if (tabs1.equals("current")) {
		userGroupParams.put("userGroupGroupRole", new Long[] {Long.valueOf(roleId), Long.valueOf(group.getGroupId())});
	}
	%>

	<liferay-ui:user-group-search-container-results
		searchTerms="<%= searchTerms %>"
		userGroupParams="<%= userGroupParams %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.UserGroup"
		escapedModel="<%= true %>"
		keyProperty="userGroupId"
		modelVar="userGroup"
	>
		<liferay-ui:search-container-column-text
			name="name"
			orderable="<%= true %>"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			name="description"
			orderable="<%= true %>"
			property="description"
		/>
	</liferay-ui:search-container-row>

	<div class="separator"><!-- --></div>

	<%
	portletURL.setParameter("cur", String.valueOf(cur));

	String taglibOnClick = renderResponse.getNamespace() + "updateUserGroupGroupRoleUsers('" + portletURL.toString() + "');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" primary="<%= true %>" value="update-associations" />

	<liferay-ui:search-iterator />
</liferay-ui:search-container>