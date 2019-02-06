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
JournalManagementToolbarDisplayContext journalManagementToolbarlDisplayContext = new JournalManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, journalDisplayContext, trashHelper);
%>

<clay:management-toolbar
	displayContext="<%= journalManagementToolbarlDisplayContext %>"
/>

<portlet:renderURL var="addArticleURL">
	<portlet:param name="mvcPath" value="/edit_article.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
	<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
</portlet:renderURL>

<portlet:renderURL var="openViewMoreStructuresURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/view_more_menu_items.jsp" />
	<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
	<portlet:param name="eventName" value='<%= renderResponse.getNamespace() + "selectAddMenuItem" %>' />
</portlet:renderURL>

<portlet:renderURL var="selectEntityURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/select_ddm_structure.jsp" />
</portlet:renderURL>

<portlet:renderURL var="viewDDMStructureArticlesURL">
	<portlet:param name="navigation" value="structure" />
	<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
</portlet:renderURL>

<%
Map<String, Object> context = new HashMap<>();

context.put("addArticleURL", addArticleURL);
context.put("folderId", String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID));
context.put("openViewMoreStructuresURL", openViewMoreStructuresURL);
context.put("selectEntityURL", selectEntityURL);
context.put("trashEnabled", trashHelper.isTrashEnabled(scopeGroupId));
context.put("viewDDMStructureArticlesURL", viewDDMStructureArticlesURL);
%>

<liferay-frontend:component
	componentId="<%= journalManagementToolbarlDisplayContext.getDefaultEventHandler() %>"
	context="<%= context %>"
	module="js/ManagementToolbarDefaultEventHandler.es"
/>