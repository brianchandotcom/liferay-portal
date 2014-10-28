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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
DLEntryListDisplayContext dlEntriesListDisplayContext = new DLEntryListDisplayContext(request, dlPortletInstanceSettings);

DLActionsDisplayContext dlActionsDisplayContext = dlEntriesListDisplayContext.getDLActionsDisplayContext();

String strutsAction = ParamUtil.getString(request, "struts_action");

String navigation = ParamUtil.getString(request, "navigation");
String browseBy = ParamUtil.getString(request, "browseBy");

Folder folder = (com.liferay.portal.kernel.repository.model.Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", rootFolderId);

boolean defaultFolderView = false;

if ((folder == null) && (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	defaultFolderView = true;
}

if (defaultFolderView) {
	try {
		folder = DLAppLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

long repositoryId = scopeGroupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();
}

String displayStyle = ParamUtil.getString(request, "displayStyle");

String[] displayViews = dlPortletInstanceSettings.getDisplayViews();

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", PropsValues.DL_DEFAULT_DISPLAY_VIEW);
}
else {
	if (ArrayUtil.contains(displayViews, displayStyle)) {
		portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", displayStyle);
	}
}

if (!ArrayUtil.contains(displayViews, displayStyle)) {
	displayStyle = displayViews[0];
}

int total = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, WorkflowConstants.STATUS_ANY, false);

boolean showSelectAll = false;

if (total > 0) {
	showSelectAll = true;
}

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-type", orderByType);
}
else {
	orderByCol = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-col", StringPool.BLANK);
	orderByType = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-type", "asc");
}

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));

request.setAttribute("view.jsp-displayStyle", displayStyle);
request.setAttribute("view.jsp-orderByCol", orderByCol);
request.setAttribute("view.jsp-orderByType", orderByType);
%>

<liferay-util:buffer var="uploadURL"><liferay-portlet:actionURL><portlet:param name="struts_action" value="/document_library/view_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_DYNAMIC %>" /><portlet:param name="folderId" value="{folderId}" /><portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" /></liferay-portlet:actionURL></liferay-util:buffer>

<liferay-ui:trash-undo />

<div id="<portlet:namespace />documentLibraryContainer">
	<aui:row cssClass="lfr-app-column-view">
		<aui:col cssClass="navigation-pane" width="<%= 25 %>">
			<liferay-util:include page="/html/portlet/document_library/view_folders.jsp" />
		</aui:col>

		<aui:col cssClass="context-pane" width="<%= dlActionsDisplayContext.isFolderMenuVisible() ? 75 : 100 %>">
			<liferay-ui:app-view-toolbar
				includeDisplayStyle="<%= true %>"
				includeSelectAll="<%= showSelectAll %>"
			>
				<liferay-util:include page="/html/portlet/document_library/toolbar.jsp" />
			</liferay-ui:app-view-toolbar>

			<%
			boolean showSyncMessage = GetterUtil.getBoolean(SessionClicks.get(request, liferayPortletResponse.getNamespace() + "show-sync-message", "true"));

			String cssClass = "show-sync-message-icon-container";

			if (showSyncMessage || !PropsValues.DL_SHOW_LIFERAY_SYNC_MESSAGE) {
				cssClass += " hide";
			}
			%>

			<div class="<%= cssClass %>" id="<portlet:namespace />showSyncMessageIconContainer">
				<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="show-liferay-sync-tip" />" class="show-sync-message" id="<portlet:namespace />showSyncMessageIcon" src='<%= themeDisplay.getPathThemeImages() + "/common/liferay_sync.png" %>' title='<%= LanguageUtil.get(request, "liferay-sync") %>' />
			</div>

			<div class="document-library-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<c:if test='<%= !navigation.equals("recent") && !navigation.equals("mine") && Validator.isNull(browseBy) %>'>
					<liferay-util:include page="/html/portlet/document_library/breadcrumb.jsp" />
				</c:if>
			</div>

			<div class="hide" id="<portlet:namespace />syncNotification">
				<div class="alert alert-info sync-notification" id="<portlet:namespace />syncNotificationContent">
					<a href="http://www.liferay.com/products/liferay-sync" target="_blank">
						<liferay-ui:message key="access-these-files-offline-using-liferay-sync" />
					</a>
				</div>
			</div>

			<liferay-portlet:renderURL varImpl="editFileEntryURL">
				<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
			</liferay-portlet:renderURL>

			<aui:form action="<%= editFileEntryURL.toString() %>" method="get" name="fm2">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
				<aui:input name="newFolderId" type="hidden" />
				<aui:input name="folderIds" type="hidden" />
				<aui:input name="fileEntryIds" type="hidden" />
				<aui:input name="fileShortcutIds" type="hidden" />

				<div class="document-container">
					<c:choose>
						<c:when test='<%= strutsAction.equals("/document_library/search") %>'>
							<liferay-util:include page="/html/portlet/document_library/search_resources.jsp" />
						</c:when>
						<c:otherwise>
							<liferay-util:include page="/html/portlet/document_library/view_entries.jsp" />
						</c:otherwise>
					</c:choose>

					<%@ include file="/html/portlet/document_library/file_entries_template.jspf" %>
				</div>
			</aui:form>
		</aui:col>
	</aui:row>
</div>

<%
if (!defaultFolderView && (folder != null) && (portletName.equals(PortletKeys.DOCUMENT_LIBRARY) || portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN))) {
	PortalUtil.setPageSubtitle(folder.getName(), request);
	PortalUtil.setPageDescription(folder.getDescription(), request);
}
%>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />toggleActionsButton',
		function() {
			var A = AUI();

			var actionsButton = A.one('#<portlet:namespace />actionsButtonContainer');

			var hide = (Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>').length == 0);

			if (actionsButton) {
				actionsButton.toggle(!hide);
			}
		},
		['liferay-util-list-fields']
	);

	<portlet:namespace />toggleActionsButton();
</aui:script>

<aui:script use="liferay-document-library">

	<%
	String[] entryColumns = dlEntriesListDisplayContext.getEntryColumns();
	String[] escapedEntryColumns = new String[entryColumns.length];

	for (int i = 0; i < entryColumns.length; i++) {
		escapedEntryColumns[i] = HtmlUtil.escapeJS(entryColumns[i]);
	}

	String[] escapedDisplayViews = new String[displayViews.length];

	for (int i = 0; i < displayViews.length; i++) {
		escapedDisplayViews[i] = HtmlUtil.escapeJS(displayViews[i]);
	}
	%>

	var documentLibrary = new Liferay.Portlet.DocumentLibrary(
		{
			columnNames: ['<%= StringUtil.merge(escapedEntryColumns, "','") %>'],

			<%
			DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
			%>

			decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',
			displayStyle: '<%= HtmlUtil.escapeJS(displayStyle) %>',

			folders: {
				defaultParentFolderId: '<%= folderId %>',
				dimensions: {
					height: '<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT) %>',
					width: '<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH) %>'
				}
			},
			maxFileSize: <%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %>,
			move: {
				allRowIds: '<%= RowChecker.ALL_ROW_IDS %>',
				editEntryUrl: '<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_entry" /></portlet:actionURL>',
				folderIdHashRegEx: /#.*&?<portlet:namespace />folderId=([\d]+)/i,
				folderIdRegEx: /&?<portlet:namespace />folderId=([\d]+)/i,
				form: {
					method: 'POST',
					node: A.one(document.<portlet:namespace />fm2)
				},
				moveEntryRenderUrl: '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/move_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>',
				trashLinkId: '<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "_" + PortletKeys.CONTROL_PANEL_MENU + "_portlet_" + PortletKeys.TRASH : StringPool.BLANK %>',
				updateable: <%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) %>
			},
			namespace: '<portlet:namespace />',
			portletId: '<%= HtmlUtil.escapeJS(portletId) %>',
			redirect: encodeURIComponent('<%= currentURL %>'),
			repositories: [
				{
					id: '<%= scopeGroupId %>',
					name: '<%= LanguageUtil.get(request, "local") %>'
				}

				<%
				List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (Folder mountFolder : mountFolders) {
				%>

					,{
						id: '<%= mountFolder.getRepositoryId() %>',
						name: '<%= HtmlUtil.escapeJS(mountFolder.getName()) %>'
					}

				<%
				}
				%>

			],
			rowIds: '<%= RowChecker.ROW_IDS %>',
			select: {
				displayViews: ['<%= StringUtil.merge(escapedDisplayViews, "','") %>']
			},
			syncMessageDisabled: <%= !PropsValues.DL_SHOW_LIFERAY_SYNC_MESSAGE %>,
			syncMessageSuppressed: <%= !GetterUtil.getBoolean(SessionClicks.get(request, liferayPortletResponse.getNamespace() + "show-sync-message", "true")) %>,
			trashEnabled: <%= (scopeGroupId == repositoryId) && TrashUtil.isTrashEnabled(scopeGroupId) %>,
			updateable: <%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) %>,
			uploadURL: '<%= uploadURL %>',
			viewFileEntryURL: '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/view_file_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>'
		}
	);

	var clearDocumentLibraryHandles = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			documentLibrary.destroy();

			Liferay.detach('destroyPortlet', clearDocumentLibraryHandles);
		}
	};

	Liferay.on('destroyPortlet', clearDocumentLibraryHandles);
</aui:script>