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
DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);

String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");

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
	displayStyle = portalPreferences.getValue(DLPortletKeys.DOCUMENT_LIBRARY, "display-style", PropsValues.DL_DEFAULT_DISPLAY_VIEW);
}
else {
	if (ArrayUtil.contains(displayViews, displayStyle)) {
		portalPreferences.setValue(DLPortletKeys.DOCUMENT_LIBRARY, "display-style", displayStyle);

		request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, true);
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
	portalPreferences.setValue(DLPortletKeys.DOCUMENT_LIBRARY, "order-by-col", orderByCol);
	portalPreferences.setValue(DLPortletKeys.DOCUMENT_LIBRARY, "order-by-type", orderByType);
}
else {
	orderByCol = portalPreferences.getValue(DLPortletKeys.DOCUMENT_LIBRARY, "order-by-col", StringPool.BLANK);
	orderByType = portalPreferences.getValue(DLPortletKeys.DOCUMENT_LIBRARY, "order-by-type", "asc");
}

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));

request.setAttribute("view.jsp-displayStyle", displayStyle);
request.setAttribute("view.jsp-orderByCol", orderByCol);
request.setAttribute("view.jsp-orderByType", orderByType);
%>

<liferay-util:buffer var="uploadURL"><liferay-portlet:actionURL name="/document_library/edit_file_entry"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_DYNAMIC %>" /><portlet:param name="folderId" value="{folderId}" /><portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" /></liferay-portlet:actionURL></liferay-util:buffer>

<portlet:actionURL name="/document_library/edit_entry" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-ui:trash-undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<div id="<portlet:namespace />documentLibraryContainer">
	<aui:row cssClass="lfr-app-column-view">
		<aui:col cssClass="navigation-pane" width="<%= 25 %>">
			<liferay-util:include page="/document_library/view_folders.jsp" servletContext="<%= application %>" />
		</aui:col>

		<aui:col cssClass="context-pane" width="<%= dlPortletInstanceSettingsHelper.isFolderMenuVisible() ? 75 : 100 %>">
			<liferay-ui:app-view-toolbar
				includeDisplayStyle="<%= true %>"
				includeSelectAll="<%= showSelectAll %>"
			>
				<liferay-util:include page="/document_library/toolbar.jsp" servletContext="<%= application %>" />
			</liferay-ui:app-view-toolbar>

			<div class="document-library-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<c:if test='<%= !navigation.equals("recent") && !navigation.equals("mine") && Validator.isNull(browseBy) %>'>
					<liferay-util:include page="/document_library/breadcrumb.jsp" servletContext="<%= application %>" />
				</c:if>
			</div>

			<c:if test="<%= portletDisplay.isWebDAVEnabled() && BrowserSnifferUtil.isIeOnWin32(request) %>">
				<div class="alert alert-danger hide" id="<portlet:namespace />openMSOfficeError"></div>
			</c:if>

			<liferay-portlet:renderURL varImpl="editFileEntryURL">
				<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_entry" />
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
						<c:when test='<%= mvcRenderCommandName.equals("/document_library/search") %>'>
							<liferay-util:include page="/document_library/search_resources.jsp" servletContext="<%= application %>" />
						</c:when>
						<c:otherwise>
							<liferay-util:include page="/document_library/view_entries.jsp" servletContext="<%= application %>" />
						</c:otherwise>
					</c:choose>

					<%@ include file="/document_library/file_entries_template.jspf" %>
				</div>
			</aui:form>
		</aui:col>
	</aui:row>
</div>

<%
if (!defaultFolderView && (folder != null) && (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) || portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN))) {
	PortalUtil.setPageSubtitle(folder.getName(), request);
	PortalUtil.setPageDescription(folder.getDescription(), request);
}
%>

<aui:script>
	function <portlet:namespace />toggleActionsButton() {
		var form = AUI.$(document.<portlet:namespace />fm2);

		var hide = Liferay.Util.listCheckedExcept(form, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>').length == 0;

		$('#<portlet:namespace />actionsButtonContainer').toggleClass('hide', hide);
	}

	<portlet:namespace />toggleActionsButton();
</aui:script>

<aui:script use="liferay-document-library">

	<%
	String[] entryColumns = dlPortletInstanceSettingsHelper.getEntryColumns();
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
				editEntryUrl: '<portlet:actionURL name="/document_library/edit_entry" />',
				folderIdHashRegEx: /#.*&?<portlet:namespace />folderId=([\d]+)/i,
				folderIdRegEx: /&?<portlet:namespace />folderId=([\d]+)/i,
				form: {
					method: 'POST',
					node: A.one(document.<portlet:namespace />fm2)
				},
				moveEntryRenderUrl: '<portlet:renderURL><portlet:param name="mvcRenderCommandName" value="/document_library/move_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>',
				selectedCSSClass: 'active',
				trashLinkId: '<%= TrashUtil.isTrashEnabled(scopeGroupId) ? ("_" + PortletProviderUtil.getPortletId(PortalProductMenuApplicationType.ProductMenu.CLASS_NAME, PortletProvider.Action.VIEW) + "_portlet_" + PortletProviderUtil.getPortletId(TrashEntry.class.getName(), PortletProvider.Action.VIEW)) : StringPool.BLANK %>',
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

					, {
						id: '<%= mountFolder.getRepositoryId() %>',
						name: '<%= HtmlUtil.escapeJS(mountFolder.getName()) %>'
					}

				<%
				}
				%>

			],
			rowIds: '<portlet:namespace /><%= RowChecker.ROW_IDS %>',
			scopeGroupId: <%= scopeGroupId %>,
			select: {
				displayStyleCSSClass: 'list-group-item',
				selectedCSSClass: 'active'
			},
			trashEnabled: <%= (scopeGroupId == repositoryId) && TrashUtil.isTrashEnabled(scopeGroupId) %>,
			updateable: <%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) %>,
			uploadURL: '<%= uploadURL %>',
			viewFileEntryURL: '<portlet:renderURL><portlet:param name="mvcRenderCommandName" value="/document_library/view_file_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>'
		}
	);

	var clearDocumentLibraryHandles = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			documentLibrary.destroy();

			Liferay.detach('destroyPortlet', clearDocumentLibraryHandles);
		}
	};

	Liferay.on('destroyPortlet', clearDocumentLibraryHandles);

	var changeScopeHandles = function(event) {
		documentLibrary.destroy();

		Liferay.detach('changeScope', changeScopeHandles);
	};

	Liferay.on('changeScope', changeScopeHandles);
</aui:script>