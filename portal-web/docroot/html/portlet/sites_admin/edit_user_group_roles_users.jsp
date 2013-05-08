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
String tabs1 = (String)request.getAttribute("edit_user_group_roles.jsp-tabs1");

int cur = (Integer)request.getAttribute("edit_user_group_roles.jsp-cur");

Group group = (Group)request.getAttribute("edit_user_group_roles.jsp-group");
String groupName = (String)request.getAttribute("edit_user_group_roles.jsp-groupName");
Role role = (Role)request.getAttribute("edit_user_group_roles.jsp-role");
long roleId = (Long)request.getAttribute("edit_user_group_roles.jsp-roleId");
Organization organization = (Organization)request.getAttribute("edit_user_group_roles.jsp-organization");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_user_group_roles.jsp-portletURL");
%>

<aui:input name="addUserGroupIds" type="hidden" />
<aui:input name="removeUserGroupIds" type="hidden" />

<div>
	<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"2", "2"}) %>

	<em>Current</em> signifies current user groups associated with the <em><%= HtmlUtil.escape(role.getTitle(locale)) %></em> role. <em>Available</em> signifies all user groups associated with the <em><%= HtmlUtil.escape(groupName) %></em> <%= (group.isOrganization()) ? "organization" : "site" %>.
</div>

<br />

<h3><liferay-ui:message key="user-groups" /></h3>

<liferay-ui:tabs
	names="current,available"
	param="tabs1"
	url="<%= portletURL.toString() %>"
/>

<liferay-ui:search-container
	rowChecker="<%= new UserGroupGroupRoleUserGroupChecker(renderResponse, group, role) %>"
	searchContainer="<%= new UserGroupSearch(renderRequest, portletURL) %>"
>
	<liferay-ui:search-form
		page="/html/portlet/users_admin/user_group_search.jsp"
	/>

	<%
	UserGroupSearchTerms searchTerms = (UserGroupSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

	if (group.isSite()) {
		userGroupParams.put("userGroupsGroups", new Long(group.getGroupId()));
	}

	if (tabs1.equals("current")) {
		userGroupParams.put("userGroupGroupRole", new Long[] {new Long(roleId), new Long(group.getGroupId())});
	}
	%>

	<liferay-ui:search-container-results
		results="<%= UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		total="<%= UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams) %>"
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
	String taglibOnClick = renderResponse.getNamespace() + "updateUserGroupGroupRoleUsers('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

	<br /><br />

	<liferay-ui:search-iterator />
</liferay-ui:search-container>