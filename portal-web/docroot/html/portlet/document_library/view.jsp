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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

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

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", PropsValues.DL_DEFAULT_DISPLAY_VIEW);
}

if (!ArrayUtil.contains(displayViews, displayStyle)) {
	displayStyle = displayViews[0];
}

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", entriesPerPage);

int folderStart = ParamUtil.getInteger(request, "folderStart");
int folderEnd = ParamUtil.getInteger(request, "folderEnd", SearchContainer.DEFAULT_DELTA);

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-type", orderByType);
}

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));
%>

<liferay-util:buffer var="uploadURL"><liferay-portlet:actionURL><portlet:param name="struts_action" value="/document_library/view_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_DYNAMIC %>" /><portlet:param name="folderId" value="{folderId}" /><portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" /></liferay-portlet:actionURL><liferay-ui:input-permissions-params modelName="<%= DLFileEntryConstants.getClassName() %>" /></liferay-util:buffer>

<liferay-ui:trash-undo />

<div id="<portlet:namespace />documentLibraryContainer">
	<aui:row cssClass="lfr-app-column-view">
		<aui:col cssClass="navigation-pane" width="<%= 25 %>">
			<liferay-util:include page="/html/portlet/document_library/view_folders.jsp" />

			<div class="folder-pagination"></div>
		</aui:col>

		<aui:col cssClass="context-pane" width="<%= showFolderMenu ? 75 : 100 %>">
			<liferay-ui:app-view-toolbar
				includeDisplayStyle="<%= true %>"
				includeSelectAll="<%= true %>"
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
				<img alt="<%= LanguageUtil.get(pageContext, "show-liferay-sync-tip") %>" class="show-sync-message" id="<portlet:namespace />showSyncMessageIcon" src="<%= themeDisplay.getPathThemeImages() + "/common/liferay_sync.png" %>" title="<%= LanguageUtil.get(pageContext, "liferay-sync") %>" />
			</div>

			<div class="document-library-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<liferay-util:include page="/html/portlet/document_library/breadcrumb.jsp" />
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

				<div class="document-container" id="<portlet:namespace />entriesContainer">
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

				<div class="document-entries-pagination"></div>
			</aui:form>
		</aui:col>
	</aui:row>
</div>

<%
int entriesTotal = GetterUtil.getInteger((String)request.getAttribute("view.jsp-total"));
int foldersTotal = GetterUtil.getInteger((String)request.getAttribute("view_folders.jsp-total"));

entryEnd = GetterUtil.getInteger(request.getAttribute("view_entries.jsp-entryEnd"), entryEnd);
entryStart = GetterUtil.getInteger(request.getAttribute("view_entries.jsp-entryStart"), entryStart);

folderEnd = GetterUtil.getInteger(request.getAttribute("view_folders.jsp-folderEnd"), folderEnd);
folderStart = GetterUtil.getInteger(request.getAttribute("view_folders.jsp-folderStart"), folderStart);

if (!defaultFolderView && (folder != null) && portletName.equals(PortletKeys.DOCUMENT_LIBRARY)) {
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

			var hide = (Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox').length == 0);

			if (actionsButton) {
				actionsButton.toggle(!hide);
			}
		},
		['liferay-util-list-fields']
	);

	<portlet:namespace />toggleActionsButton();
</aui:script>

<aui:script use="liferay-document-library">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="mainURL" />

	new Liferay.Portlet.DocumentLibrary(
		{
			columnNames: ['<%= StringUtil.merge(entryColumns, "','") %>'],
			displayStyle: '<%= HtmlUtil.escapeJS(displayStyle) %>',
			folders: {
				defaultParams: {
					p_p_id: <%= HtmlUtil.escapeJS(portletId) %>,
					p_p_lifecycle: 0
				},
				defaultParentFolderId: '<%= DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>',
				dimensions: {
					height: '<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT) %>',
					width: '<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH) %>'
				},
				'listViewConfig.useTransition': false,
				mainUrl: '<%= mainURL %>',
				strutsAction: '/document_library/view'
			},
			trashEnabled: <%= TrashUtil.isTrashEnabled(scopeGroupId) %>,
			maxFileSize: <%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %>,
			move: {
				allRowIds: '<%= RowChecker.ALL_ROW_IDS %>',
				editEntryUrl: '<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_entry" /></portlet:actionURL>',
				folderIdRegEx: /&?<portlet:namespace />folderId=([\d]+)/i,
				folderIdHashRegEx: /#.*&?<portlet:namespace />folderId=([\d]+)/i,
				form: {
					method: 'post',
					node: A.one(document.<portlet:namespace />fm2)
				},
				moveEntryRenderUrl: '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/move_entry" /></portlet:renderURL>',
				trashLinkId: '<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "_" + PortletKeys.CONTROL_PANEL_MENU + "_portlet_" + PortletKeys.TRASH : StringPool.BLANK %>',
				updateable: <%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) %>
			},
			paginator: {
				entriesTotal: <%= entriesTotal %>,
				entryEnd: <%= entryEnd %>,
				entryRowsPerPage: <%= entryEnd - entryStart %>,
				entryStart: <%= entryStart %>,
				folderEnd: <%= folderEnd %>,
				folderId: <%= folderId %>,
				folderRowsPerPage: <%= folderEnd - folderStart %>,
				folderStart: <%= folderStart %>,
				foldersTotal: <%= foldersTotal %>
			},
			namespace: '<portlet:namespace />',
			portletId: '<%= HtmlUtil.escapeJS(portletId) %>',
			redirect: encodeURIComponent('<%= currentURL %>'),
			repositories: [
				{
					id: '<%= scopeGroupId %>',
					name: '<%= LanguageUtil.get(pageContext, "local") %>'
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
				displayViews: ['<%= StringUtil.merge(displayViews, "','") %>']
			},
			syncMessageDisabled: <%= !PropsValues.DL_SHOW_LIFERAY_SYNC_MESSAGE %>,
			syncMessageSuppressed: <%= !GetterUtil.getBoolean(SessionClicks.get(request, liferayPortletResponse.getNamespace() + "show-sync-message", "true")) %>,
			updateable: <%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) %>,
			uploadURL: '<%= uploadURL %>',
			viewFileEntryURL: '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/view_file_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>'
		}
	);
</aui:script>