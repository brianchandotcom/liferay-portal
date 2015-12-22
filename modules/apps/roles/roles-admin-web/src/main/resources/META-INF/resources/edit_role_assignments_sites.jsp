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
String tabs3 = (String)request.getAttribute("edit_role_assignments.jsp-tabs3");

int cur = (Integer)request.getAttribute("edit_role_assignments.jsp-cur");

Role role = (Role)request.getAttribute("edit_role_assignments.jsp-role");

String displayStyle = (String)request.getAttribute("edit_role_assignments.jsp-displayStyle");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_role_assignments.jsp-portletURL");
%>

<aui:input name="addGroupIds" type="hidden" />
<aui:input name="removeGroupIds" type="hidden" />

<liferay-ui:search-container
	rowChecker="<%= new GroupRoleChecker(renderResponse, role) %>"
	searchContainer="<%= new GroupSearch(renderRequest, portletURL) %>"
>

	<%
	GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();

	groupParams.put("site", Boolean.TRUE);

	if (tabs3.equals("current")) {
		groupParams.put("groupsRoles", Long.valueOf(role.getRoleId()));
	}

	total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), groupParams);

	searchContainer.setTotal(total);
	%>

	<liferay-ui:search-container-results
		results="<%= GroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), groupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

	<%
	portletURL.setParameter("cur", String.valueOf(cur));

	String taglibOnClick = renderResponse.getNamespace() + "updateRoleGroups('" + portletURL.toString() + "');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Group"
		escapedModel="<%= true %>"
		keyProperty="groupId"
		modelVar="group"
		rowIdProperty="friendlyURL"
	>

		<liferay-ui:search-container-column-text
			name="name"
			value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
		/>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(request, group.getTypeLabel()) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
</liferay-ui:search-container>