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
String tabs1 = (String)request.getAttribute("edit_site_assignments.jsp-tabs1");
String tabs2 = (String)request.getAttribute("edit_site_assignments.jsp-tabs2");

String redirect = (String)request.getAttribute("edit_site_assignments.jsp-redirect");

int cur = (Integer)request.getAttribute("edit_site_assignments.jsp-cur");

Group group = (Group)request.getAttribute("edit_site_assignments.jsp-group");

long userGroupId = ParamUtil.getLong(request, "userGroupId");

UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(userGroupId);

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

portletURL.setParameter("userGroupId", String.valueOf(userGroupId));
%>

<aui:input name="userGroupId" type="hidden" value="<%= userGroupId %>" />
<aui:input name="addRoleIds" type="hidden" />
<aui:input name="removeRoleIds" type="hidden" />

<liferay-ui:header
	backURL="<%= redirect %>"
	escapeXml="<%= false %>"
	localizeTitle="<%= false %>"
	title='<%= LanguageUtil.get(pageContext, "edit-site-roles-for-user-group") + ": " + HtmlUtil.escape(userGroup.getName()) %>'
/>

<%
RoleSearch searchContainer = new RoleSearch(renderRequest, portletURL);

searchContainer.setRowChecker(new UserGroupGroupRoleRoleChecker(renderResponse, userGroup, group));
%>

<liferay-ui:search-form
	page="/html/portlet/roles_admin/role_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<%
RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_SITE}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, searchContainer.getOrderByComparator());

roles = UsersAdminUtil.filterGroupRoles(permissionChecker, group.getGroupId(), roles);

int total = roles.size();

searchContainer.setTotal(total);

List<Role> results = ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);

PortletURL updateRoleAssignmentsURL = renderResponse.createRenderURL();

updateRoleAssignmentsURL.setParameter("struts_action", "/sites_admin/edit_site_assignments");
updateRoleAssignmentsURL.setParameter("tabs1", tabs1);
updateRoleAssignmentsURL.setParameter("tabs2", tabs2);
updateRoleAssignmentsURL.setParameter("redirect", redirect);
updateRoleAssignmentsURL.setParameter("userGroupId", String.valueOf(userGroupId));
updateRoleAssignmentsURL.setParameter("groupId", String.valueOf(group.getGroupId()));
%>

<div class="separator"><!-- --></div>

<%
String taglibOnClick = renderResponse.getNamespace() + "updateUserGroupGroupRole('" + updateRoleAssignmentsURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
%>

<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

<%
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	Role role = results.get(i);

	role = role.toEscapedModel();

	ResultRow row = new ResultRow(role, role.getRoleId(), i);

	// Name

	row.addText(role.getTitle(locale));

	// Type

	row.addText(LanguageUtil.get(pageContext, role.getTypeLabel()));

	// Description

	row.addText(role.getDescription(locale));

	// CSS

	row.setClassName(RolesAdminUtil.getCssClassName(role));
	row.setClassHoverName(RolesAdminUtil.getCssClassName(role));

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />