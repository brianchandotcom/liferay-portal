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

<aui:script sandbox="<%= true %>">
	var deleteEntries = function() {
		if (<%= trashHelper.isTrashEnabled(scopeGroupId) %> || confirm(' <%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			Liferay.fire(
				'<%= renderResponse.getNamespace() %>editEntry',
				{
					action: '<%= trashHelper.isTrashEnabled(scopeGroupId) ? "moveEntriesToTrash" : "deleteEntries" %>'
				}
			);
		}
	};

	var expireEntries = function() {
		Liferay.fire(
			'<portlet:namespace />editEntry',
			{
				action: 'expireEntries'
			}
		);
	};

	var moveEntries = function() {
		Liferay.fire(
			'<portlet:namespace />editEntry',
			{
				action: 'moveEntries'
			}
		);
	};

	<portlet:renderURL var="viewDDMStructureArticlesURL">
		<portlet:param name="navigation" value="structure" />
		<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
	</portlet:renderURL>

	var openDDMStructuresSelector = function() {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					modal: true
				},
				eventName: '<portlet:namespace />selectDDMStructure',
				title: '<%= UnicodeLanguageUtil.get(request, "structures") %>',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_ddm_structure.jsp" /></portlet:renderURL>'
			},
			function(event) {
				var uri = '<%= viewDDMStructureArticlesURL %>';

				uri = Liferay.Util.addParams('<portlet:namespace />ddmStructureKey=' + event.ddmstructurekey, uri);

				location.href = uri;
			}
		);
	};

	var openViewMoreDDMStructuresSelector = function() {
		Liferay.Util.openWindow(
			{
				dialog: {
					destroyOnHide: true,
					modal: true
				},
				id: '<portlet:namespace />selectAddMenuItem',
				title: '<liferay-ui:message key="more" />',

				<portlet:renderURL var="viewMoreURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/view_more_menu_items.jsp" />
					<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
					<portlet:param name="eventName" value='<%= renderResponse.getNamespace() + "selectAddMenuItem" %>' />
				</portlet:renderURL>

				uri: '<%= viewMoreURL %>'
			}
		);
	};

	var ACTIONS = {
		'deleteEntries': deleteEntries,
		'expireEntries': expireEntries,
		'moveEntries': moveEntries,
		'openDDMStructuresSelector': openDDMStructuresSelector,
		'openViewMoreDDMStructuresSelector': openViewMoreDDMStructuresSelector
	};

	Liferay.componentReady('journalWebManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on(
				['actionItemClicked', 'filterItemClicked'],
				function(event) {
					var itemData = event.data.item.data;

					if (itemData && itemData.action && ACTIONS[itemData.action]) {
						ACTIONS[itemData.action]();
					}
				}
			);

			managementToolbar.on('creationMenuMoreButtonClicked', openViewMoreDDMStructuresSelector);
		}
	);

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