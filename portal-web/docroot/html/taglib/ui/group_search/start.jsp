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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.usersadmin.search.GroupSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.GroupSearchTerms" %>

<portlet:defineObjects />

<%
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:group-search:portletURL");
RowChecker rowChecker = (RowChecker)request.getAttribute("liferay-ui:group-search:rowChecker");
LinkedHashMap<String, Object> groupParams = (LinkedHashMap<String, Object>)request.getAttribute("liferay-ui:group-search:groupParams");

GroupSearch searchContainer = new GroupSearch(renderRequest, portletURL);

request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);

searchContainer.setRowChecker(rowChecker);
%>

<liferay-ui:search-form
	page="/html/portlet/users_admin/group_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<div>
	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) %>">
		<aui:button onClick='<%= renderResponse.getNamespace() + "addGroup();" %>' value="add-site" />
	</c:if>
</div>

<aui:script>
	function <portlet:namespace />addGroup() {
		document.<portlet:namespace />fm.method = 'post';
		submitForm(document.<portlet:namespace />fm, '<portlet:renderURL><portlet:param name="struts_action" value="/sites_admin/edit_site" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>');
	}
</aui:script>

<%
GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

List<Group> results = null;
int total = 0;
%>

<%@ include file="/html/portlet/users_admin/group_search_results.jspf" %>

<%
searchContainer.setResults(results);
searchContainer.setTotal(total);
%>