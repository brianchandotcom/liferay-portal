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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/wiki/view_nodes");

List<String> headerNames = new ArrayList<String>();

headerNames.add("wiki");
headerNames.add("num-of-pages");
headerNames.add("last-post-date");
headerNames.add(StringPool.BLANK);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

int total = WikiNodeLocalServiceUtil.getNodesCount(scopeGroupId);

searchContainer.setTotal(total);

List results = WikiNodeLocalServiceUtil.getNodes(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);
%>

<portlet:actionURL var="undoTrashURL">
	<portlet:param name="struts_action" value="/wiki/edit_node" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-ui:trash-undo portletURL="<%= undoTrashURL %>" />

<liferay-ui:error exception="<%= RequiredNodeException.class %>" message="the-last-main-node-is-required-and-cannot-be-deleted" />

<liferay-portlet:renderURL var="searchURL">
	<portlet:param name="struts_action" value="/wiki/search" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		WikiNode node = (WikiNode)results.get(i);

		ResultRow row = new ResultRow(node, node.getNodeId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/wiki/view_all_pages");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

		// Name

		row.addText(node.getName(), rowURL);

		// Number of pages

		int pagesCount = WikiPageLocalServiceUtil.getPagesCount(node.getNodeId(), true);

		row.addText(String.valueOf(pagesCount), rowURL);

		// Last post date

		if (node.getLastPostDate() == null) {
			row.addText(LanguageUtil.get(pageContext, "never"), rowURL);
		}
		else {
			row.addText(dateFormatDateTime.format(node.getLastPostDate()), rowURL);
		}

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/wiki/node_action.jsp");

		// Add result row

		resultRows.add(row);
	}

	boolean showAddNodeButton = WikiPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_NODE);
	boolean showPermissionsButton = WikiPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
	%>

	<aui:fieldset>
		<c:if test="<%= showAddNodeButton || showPermissionsButton %>">
			<aui:button-row>
				<c:if test="<%= showAddNodeButton %>">
					<portlet:renderURL var="addNodeURL">
						<portlet:param name="struts_action" value="/wiki/edit_node" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="nodeId" value="0" />
					</portlet:renderURL>

					<aui:button href="<%= addNodeURL %>" name="addNodeButton" value="add-wiki" />
				</c:if>

				<c:if test="<%= showPermissionsButton %>">
					<liferay-security:permissionsURL
						modelResource="com.liferay.portlet.wiki"
						modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
						resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
						var="permissionsURL"
					/>

					<aui:button href="<%= permissionsURL %>" name="permissionsButton" value="permissions" />
				</c:if>
			</aui:button-row>
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</aui:fieldset>
</aui:form>