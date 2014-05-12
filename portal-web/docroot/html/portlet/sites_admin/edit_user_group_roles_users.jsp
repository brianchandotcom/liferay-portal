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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
String tabs1 = (String)request.getAttribute("edit_user_group_roles.jsp-tabs1");

int cur = (Integer)request.getAttribute("edit_user_group_roles.jsp-cur");

Group group = (Group)request.getAttribute("edit_user_group_roles.jsp-group");
String groupDescriptiveName = (String)request.getAttribute("edit_user_group_roles.jsp-groupDescriptiveName");
Role role = (Role)request.getAttribute("edit_user_group_roles.jsp-role");
long roleId = (Long)request.getAttribute("edit_user_group_roles.jsp-roleId");
Organization organization = (Organization)request.getAttribute("edit_user_group_roles.jsp-organization");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_user_group_roles.jsp-portletURL");
%>

<aui:input name="addUserGroupIds" type="hidden" />
<aui:input name="removeUserGroupIds" type="hidden" />

<div>
	<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"2", "2"}, false) %>

	<%= LanguageUtil.format(pageContext, "current-signifies-current-user-groups-associated-with-the-x-role.-available-signifies-all-user-groups-associated-with-the-x-x", new String[] {HtmlUtil.escape(role.getTitle(locale)), HtmlUtil.escape(groupDescriptiveName), LanguageUtil.get(pageContext, (group.isOrganization() ? LanguageUtil.get(pageContext, "organization") : LanguageUtil.get(pageContext, "site")))}, false) %>
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
	UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)searchContainer.getSearchTerms();

	LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

	if (group.isSite()) {
		userGroupParams.put("userGroupsGroups", new Long(group.getGroupId()));
	}

	if (tabs1.equals("current")) {
		userGroupParams.put("userGroupGroupRole", new Long[] {new Long(roleId), new Long(group.getGroupId())});
	}

	total = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams);

	searchContainer.setTotal(total);
	%>

	<liferay-ui:search-container-results
		results="<%= UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
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