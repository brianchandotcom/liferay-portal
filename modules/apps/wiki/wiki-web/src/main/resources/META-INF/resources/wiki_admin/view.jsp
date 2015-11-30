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

<%@ include file="/wiki/init.jsp" %>

<%
WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/wiki/view_nodes");

List<String> headerNames = new ArrayList<String>();

headerNames.add("wiki");
headerNames.add("num-of-pages");
headerNames.add("last-post-date");
headerNames.add(StringPool.BLANK);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

int total = WikiNodeServiceUtil.getNodesCount(scopeGroupId);

searchContainer.setTotal(total);

List results = WikiNodeServiceUtil.getNodes(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);
%>

<portlet:actionURL name="/wiki/edit_node" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-ui:trash-undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<liferay-ui:error exception="<%= RequiredNodeException.class %>" message="the-last-main-node-is-required-and-cannot-be-deleted" />

<aui:form action="<%= wikiURLHelper.getSearchURL() %>" method="get" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		WikiNode node = (WikiNode)results.get(i);

		node = node.toEscapedModel();

		ResultRow row = new ResultRow(node, node.getNodeId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("mvcRenderCommandName", "/wiki/view_all_pages");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

		// Name

		row.addText(node.getName(), rowURL);

		// Number of pages

		int pagesCount = WikiPageServiceUtil.getPagesCount(scopeGroupId, node.getNodeId(), true);

		row.addText(String.valueOf(pagesCount), rowURL);

		// Last post date

		if (node.getLastPostDate() == null) {
			row.addText(LanguageUtil.get(request, "never"), rowURL);
		}
		else {
			row.addText(dateFormatDateTime.format(node.getLastPostDate()), rowURL);
		}

		// Action

		row.addJSP("/wiki/node_action.jsp", "entry-action", application, request, response);

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>

<%
boolean showAddNodeButton = WikiResourcePermissionChecker.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_NODE);
%>

<c:if test="<%= showAddNodeButton %>">
	<portlet:renderURL var="viewNodesURL">
		<portlet:param name="mvcRenderCommandName" value="/wiki_admin/view" />
	</portlet:renderURL>

	<portlet:renderURL var="addNodeURL">
		<portlet:param name="mvcRenderCommandName" value="/wiki/edit_node" />
		<portlet:param name="redirect" value="<%= viewNodesURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-wiki") %>' url="<%= addNodeURL %>" />
	</liferay-frontend:add-menu>
</c:if>