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
String redirect = (String)request.getAttribute("edit_user_roles.jsp-redirect");

Group group = (Group)request.getAttribute("edit_user_roles.jsp-group");
int roleType = (Integer)request.getAttribute("edit_user_roles.jsp-roleType");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_user_roles.jsp-portletURL");
%>

<div>
	<liferay-ui:message arguments='<%= new String[] {"1", "2"} %>' key="step-x-of-x" translateArguments="<%= false %>" />

	<liferay-ui:message key="choose-a-role" />
</div>

<br />

<h3><liferay-ui:message key="roles" /></h3>

<liferay-ui:search-container
	searchContainer="<%= new RoleSearch(renderRequest, portletURL) %>"
>
	<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" cssClass="col-xs-12 form-search" placeholder="keywords" />

	<%
	RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

	List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {roleType}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, searchContainer.getOrderByComparator());

	roles = UsersAdminUtil.filterGroupRoles(permissionChecker, group.getGroupId(), roles);

	total = roles.size();

	searchContainer.setTotal(total);
	%>

	<liferay-ui:search-container-results
		results="<%= ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Role"
		escapedModel="<%= true %>"
		keyProperty="roleId"
		modelVar="role"
	>
		<portlet:renderURL var="rowURL">
			<portlet:param name="mvcPath" value="/edit_user_roles.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="title"
			value="<%= role.getTitle(locale) %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="type"
			value="<%= LanguageUtil.get(request, role.getTypeLabel()) %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="description"
			value="<%= role.getDescription(locale) %>"
		/>
	</liferay-ui:search-container-row>

	<div class="separator"><!-- --></div>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>