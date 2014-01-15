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
String redirect = (String)request.getAttribute("edit_user_roles.jsp-redirect");

Group group = (Group)request.getAttribute("edit_user_roles.jsp-group");
int roleType = (Integer)request.getAttribute("edit_user_roles.jsp-roleType");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_user_roles.jsp-portletURL");
%>

<div>
	<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"1", "2"}, false) %>

	<liferay-ui:message key="choose-a-role" />
</div>

<br />

<h3><liferay-ui:message key="roles" /></h3>

<%
RoleSearch searchContainer = new RoleSearch(renderRequest, portletURL);
%>

<liferay-ui:search-form
	page="/html/portlet/roles_admin/role_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<%
RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {roleType}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, searchContainer.getOrderByComparator());

roles = UsersAdminUtil.filterGroupRoles(permissionChecker, group.getGroupId(), roles);

int total = roles.size();

searchContainer.setTotal(total);

List results = ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);
%>

<div class="separator"><!-- --></div>

<%
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	Role curRole = (Role)results.get(i);

	curRole = curRole.toEscapedModel();

	ResultRow row = new ResultRow(curRole, curRole.getRoleId(), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setParameter("struts_action", "/sites_admin/edit_user_roles");
	rowURL.setParameter("redirect", redirect);
	rowURL.setParameter("groupId", String.valueOf(group.getGroupId()));
	rowURL.setParameter("roleId", String.valueOf(curRole.getRoleId()));

	// Name

	row.addText(curRole.getTitle(locale), rowURL);

	// Type

	row.addText(LanguageUtil.get(pageContext, curRole.getTypeLabel()), rowURL);

	// Description

	row.addText(curRole.getDescription(locale), rowURL);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />