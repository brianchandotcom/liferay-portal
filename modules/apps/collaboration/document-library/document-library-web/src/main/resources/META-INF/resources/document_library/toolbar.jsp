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
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");

String navigation = ParamUtil.getString(request, "navigation", "home");

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

String searchContainerId = ParamUtil.getString(request, "searchContainerId");

boolean search = mvcRenderCommandName.equals("/document_library/search");

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);
%>

<liferay-frontend:management-bar
	disabled="<%= DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, WorkflowConstants.STATUS_ANY, true) <= 0 %>"
	includeCheckBox="<%= dlPortletInstanceSettingsHelper.isShowActions() %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<c:if test="<%= !search %>">
			<liferay-util:include page="/document_library/display_style_buttons.jsp" servletContext="<%= application %>" />
		</c:if>

		<c:if test="<%= portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN) %>">
			<liferay-util:include page="/document_library/add_button.jsp" servletContext="<%= application %>" />
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<%
	String label = null;

	if (fileEntryTypeId != -1) {
		String fileEntryTypeName = LanguageUtil.get(request, "basic-document");

		if (fileEntryTypeId != DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {
			DLFileEntryType fileEntryType = DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);

			fileEntryTypeName = fileEntryType.getName(locale);
		}

		label = LanguageUtil.get(request, "document-types") + StringPool.COLON + StringPool.SPACE + fileEntryTypeName;
	}
	%>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			label="<%= label %>"
		>
			<portlet:renderURL var="viewDocumentsHomeURL">
				<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
				<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
			</portlet:renderURL>

			<liferay-frontend:management-bar-filter-item
				active='<%= ((navigation.equals("home")) && (folderId == rootFolderId) && (fileEntryTypeId == -1)) %>'
				label="all"
				url="<%= viewDocumentsHomeURL.toString() %>"
			/>

			<portlet:renderURL var="viewRecentDocumentsURL">
				<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
				<portlet:param name="navigation" value="recent" />
				<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
			</portlet:renderURL>

			<liferay-frontend:management-bar-filter-item
				active='<%= navigation.equals("recent") %>'
				label="recent"
				url="<%= viewRecentDocumentsURL.toString() %>"
			/>

			<c:if test="<%= themeDisplay.isSignedIn() %>">
				<portlet:renderURL var="viewMyDocumentsURL">
					<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
					<portlet:param name="navigation" value="mine" />
					<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
				</portlet:renderURL>

				<liferay-frontend:management-bar-filter-item
					active='<%= navigation.equals("mine") %>'
					label="mine"
					url="<%= viewMyDocumentsURL.toString() %>"
				/>
			</c:if>

			<liferay-frontend:management-bar-filter-item
				active="<%= fileEntryTypeId != -1 %>"
				id="fileEntryTypes"
				label="document-types"
				url="javascript:;"
			/>
		</liferay-frontend:management-bar-navigation>

		<c:if test='<%= !search && !navigation.equals("recent") %>'>

			<%
			int deltaEntry = ParamUtil.getInteger(request, "deltaEntry");

			String orderByCol = GetterUtil.getString((String)request.getAttribute("view.jsp-orderByCol"));
			String orderByType = GetterUtil.getString((String)request.getAttribute("view.jsp-orderByType"));

			Map<String, String> orderColumns = new HashMap<String, String>();

			orderColumns.put("creationDate", "create-date");
			orderColumns.put("downloads", "downloads");
			orderColumns.put("modifiedDate", "modified-date");
			orderColumns.put("size", "size");
			orderColumns.put("title", "title");

			PortletURL sortURL = renderResponse.createRenderURL();

			sortURL.setParameter("mvcRenderCommandName", (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) ? "/document_library/view" : "/document_library/view_folder");
			sortURL.setParameter("navigation", navigation);

			if (deltaEntry > 0) {
				sortURL.setParameter("deltaEntry", String.valueOf(deltaEntry));
			}

			sortURL.setParameter("folderId", String.valueOf(folderId));
			sortURL.setParameter("fileEntryTypeId", String.valueOf(fileEntryTypeId));
			%>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns="<%= orderColumns %>"
				portletURL="<%= sortURL %>"
			/>
		</c:if>

		<c:if test="<%= dlPortletInstanceSettingsHelper.isShowSearch() %>">
			<li>
				<liferay-portlet:renderURL varImpl="searchURL">
					<portlet:param name="mvcRenderCommandName" value="/document_library/search" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="searchRepositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					<portlet:param name="searchFolderId" value="<%= String.valueOf(folderId) %>" />
					<portlet:param name="showRepositoryTabs" value="<%= (folderId == 0) ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" />
					<portlet:param name="showSearchInfo" value="<%= Boolean.TRUE.toString() %>" />
				</liferay-portlet:renderURL>

				<aui:form action="<%= searchURL.toString() %>" method="get" name="searchFm">
					<liferay-portlet:renderURLParams varImpl="searchURL" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

					<liferay-ui:input-search
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<%
		Group scopeGroup = themeDisplay.getScopeGroup();
		%>

		<c:if test="<%= !user.isDefaultUser() && (!scopeGroup.isStaged() || scopeGroup.isStagingGroup() || !scopeGroup.isStagedPortlet(DLPortletKeys.DOCUMENT_LIBRARY)) %>">

			<%
			String taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: 'download'}); void(0);";
			%>

			<liferay-frontend:management-bar-button
				href="<%= taglibURL %>"
				icon="download"
				label="download"
			/>

			<%
			taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CHECKIN + "'}); void(0);";
			%>

			<liferay-frontend:management-bar-button
				href="<%= taglibURL %>"
				icon="unlock"
				label="unlock"
			/>

			<%
			taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CHECKOUT + "'}); void(0);";
			%>

			<liferay-frontend:management-bar-button
				href="<%= taglibURL %>"
				icon="lock"
				label="lock"
			/>

			<%
			taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.MOVE + "'}); void(0);";
			%>

			<liferay-frontend:management-bar-button
				href="<%= taglibURL %>"
				icon="change"
				label="move"
			/>
		</c:if>

		<c:if test="<%= !user.isDefaultUser() %>">
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "deleteEntries();" %>'
				icon='<%= dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId) ? "trash" : "times" %>'
				id="deleteAction"
				label='<%= dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId) ? "recycle-bin" : "delete" %>'
			/>
		</c:if>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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

<aui:script use="liferay-item-selector-dialog">
	<portlet:renderURL var="viewFileEntryTypeURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
		<portlet:param name="browseBy" value="file-entry-type" />
		<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
	</portlet:renderURL>

	AUI.$('#<portlet:namespace />fileEntryTypes').on(
		'click',
		function(event) {
			event.preventDefault();

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
		}
	);
</aui:script>