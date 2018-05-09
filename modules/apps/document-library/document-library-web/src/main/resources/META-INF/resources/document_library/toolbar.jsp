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

<%@ include file="/document_library/init.jsp" %>

<%
long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

DLAdminManagementToolbarDisplayContext dlAdminManagementToolbarDisplayContext = new DLAdminManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, dlAdminDisplayContext);
%>

<clay:management-toolbar
	actionDropdownItems="<%= dlAdminManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= dlAdminManagementToolbarDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= dlAdminManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= dlAdminManagementToolbarDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= dlAdminManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	infoPanelId="infoPanelId"
	itemsTotal="<%= dlAdminManagementToolbarDisplayContext.getTotalItems() %>"
	searchActionURL="<%= String.valueOf(dlAdminManagementToolbarDisplayContext.getSearchURL()) %>"
	searchContainerId="entries"
	selectable="<%= dlAdminManagementToolbarDisplayContext.isSelectable() %>"
	showInfoButton="<%= true %>"
	showSearch="<%= dlAdminManagementToolbarDisplayContext.isShowSearch() %>"
	sortingOrder="<%= dlAdminManagementToolbarDisplayContext.getSortingOrder() %>"
	sortingURL="<%= String.valueOf(dlAdminManagementToolbarDisplayContext.getSortingURL()) %>"
	viewTypeItems="<%= dlAdminManagementToolbarDisplayContext.getViewTypes() %>"
/>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId) %> || confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			Liferay.fire(
				'<%= renderResponse.getNamespace() %>editEntry',
				{
					action: '<%= dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>'
				}
			);
		}
	}
</aui:script>

<aui:script>
	<portlet:renderURL var="viewFileEntryTypeURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
		<portlet:param name="browseBy" value="file-entry-type" />
		<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
	</portlet:renderURL>

	Liferay.provide(
		window,
		'<portlet:namespace />openDocumentTypesSelector',
		function() {
			var A = AUI();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectFileEntryType',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								var uri = '<%= viewFileEntryTypeURL %>';

								uri = Liferay.Util.addParams('<portlet:namespace />fileEntryTypeId=' + selectedItem, uri);

								location.href = uri;
							}
						}
					},
					'strings.add': '<liferay-ui:message key="done" />',
					title: '<liferay-ui:message key="select-document-type" />',
					url: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/document_library/select_file_entry_type.jsp" /><portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" /></portlet:renderURL>'
				}
			);

			itemSelectorDialog.open();
		},
		['aui-base', 'liferay-item-selector-dialog']
	);
</aui:script>