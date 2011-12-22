<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
String tabs3 = (String)request.getAttribute("edit_role_assignments.jsp-tabs3");

int cur = (Integer)request.getAttribute("edit_role_assignments.jsp-cur");

Role role = (Role)request.getAttribute("edit_role_assignments.jsp-role");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_role_assignments.jsp-portletURL");
%>

<aui:input name="addGroupIds" type="hidden" />
<aui:input name="removeGroupIds" type="hidden" />

<liferay-ui:tabs
	names="current,available"
	param="tabs3"
	url="<%= portletURL.toString() %>"
/>

<%
GroupSearch searchContainer = new GroupSearch(renderRequest, portletURL);

searchContainer.setRowChecker(new GroupRoleChecker(renderResponse, role));
%>

<liferay-ui:search-form
	page="/html/portlet/users_admin/group_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<%
GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

LinkedHashMap groupParams = new LinkedHashMap();

groupParams.put("site", Boolean.TRUE);

if (tabs3.equals("current")) {
	groupParams.put("groupsRoles", new Long(role.getRoleId()));
}

int total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), groupParams);

searchContainer.setTotal(total);

List results = GroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), groupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

searchContainer.setResults(results);
%>

<div class="separator"><!-- --></div>

<%
String taglibOnClick = renderResponse.getNamespace() + "updateRoleGroups('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
%>

<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

<br /><br />

<%
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	Group group = (Group)results.get(i);

	ResultRow row = new ResultRow(group, group.getGroupId(), i);

	// Name

	row.addText(group.getDescriptiveName(locale));

	// Type

	row.addText(LanguageUtil.get(pageContext, group.getTypeLabel()));

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />