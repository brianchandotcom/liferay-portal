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

<aui:script require='<%= npmResolvedPackageName + "/js/ManagementToolbarDefaultEventHandler.es as ManagementToolbarDefaultEventHandler" %>'>
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

	Liferay.component(
		'<%= journalManagementToolbarlDisplayContext.getDefaultEventHandler() %>',
		new ManagementToolbarDefaultEventHandler.default(
			{
				folderId: '<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>',
				namespace: '<%= renderResponse.getNamespace() %>',
				openViewMoreStructuresURL: '<%= openViewMoreStructuresURL %>',
				selectEntityURL: '<%= selectEntityURL %>',
				trashEnabled: <%= trashHelper.isTrashEnabled(scopeGroupId) %>,
				viewDDMStructureArticlesURL: '<%= viewDDMStructureArticlesURL %>'
			}
		),
		{
			destroyOnNavigate: true,
			portletId: '<%= HtmlUtil.escapeJS(portletDisplay.getId()) %>'
		}
	);
</aui:script>

<aui:script sandbox="<%= true %>">
	<portlet:renderURL var="addArticleURL">
		<portlet:param name="mvcPath" value="/edit_article.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
	</portlet:renderURL>

	Liferay.on(
		'<portlet:namespace />selectAddMenuItem',
		function(event) {
			var uri = '<%= addArticleURL %>';

			uri = Liferay.Util.addParams('<portlet:namespace />ddmStructureKey=' + event.ddmStructureKey, uri);

			location.href = uri;
		}
	);
</aui:script>