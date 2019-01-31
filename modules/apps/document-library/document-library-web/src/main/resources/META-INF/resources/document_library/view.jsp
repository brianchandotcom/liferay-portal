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
String navigation = ParamUtil.getString(request, "navigation");
%>

<c:choose>
	<c:when test='<%= navigation.equals("file_entry_types") %>'>
		<liferay-util:include page="/document_library/view_file_entry_types.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/view.jsp#pre" />

		<%
		DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);

		String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");

		boolean defaultFolderView = dlAdminDisplayContext.isDefaultFolderView();

		String displayStyle = dlAdminDisplayContext.getDisplayStyle();

		Folder folder = dlAdminDisplayContext.getFolder();

		long folderId = dlAdminDisplayContext.getFolderId();

		long repositoryId = dlAdminDisplayContext.getRepositoryId();

		request.setAttribute("view.jsp-folder", folder);

		request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

		request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));

		request.setAttribute("view.jsp-displayStyle", displayStyle);
		%>

		<liferay-util:buffer var="uploadURL"><liferay-portlet:actionURL name="/document_library/edit_file_entry"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_DYNAMIC %>" /><portlet:param name="folderId" value="{folderId}" /><portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" /></liferay-portlet:actionURL></liferay-util:buffer>

		<portlet:actionURL name="/document_library/edit_entry" var="restoreTrashEntriesURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
		</portlet:actionURL>

		<liferay-trash:undo
			portletURL="<%= restoreTrashEntriesURL %>"
		/>

		<liferay-util:include page="/document_library/navigation.jsp" servletContext="<%= application %>" />

		<liferay-util:include page="/document_library/toolbar.jsp" servletContext="<%= application %>" />

		<%
		BulkSelectionRunner bulkSelectionRunner = BulkSelectionRunnerUtil.getBulkSelectionRunner();

		Map<String, Object> context = new HashMap<>();

		context.put("bulkInProgress", bulkSelectionRunner.isBusy(user));
		context.put("portletNamespace", liferayPortletResponse.getNamespace());
		%>

		<soy:component-renderer
			context="<%= context %>"
			module="document_library/bulk/BulkStatus.es"
			templateNamespace="com.liferay.document.library.web.BulkStatus.render"
		/>

		<div id="<portlet:namespace />documentLibraryContainer">

			<%
			boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));
			%>

			<div class="closed <%= portletTitleBasedNavigation ? "container-fluid-1280" : StringPool.BLANK %> sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
				<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/document_library/info_panel" var="sidebarPanelURL">
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
				</liferay-portlet:resourceURL>

				<liferay-frontend:sidebar-panel
					resourceURL="<%= sidebarPanelURL %>"
					searchContainerId="entries"
				>
					<liferay-util:include page="/document_library/info_panel.jsp" servletContext="<%= application %>" />
				</liferay-frontend:sidebar-panel>

				<div class="sidenav-content">
					<div class="document-library-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
						<c:if test='<%= !mvcRenderCommandName.equals("/document_library/search") %>'>
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

						<liferay-ui:error exception="<%= AuthenticationRepositoryException.class %>" message="you-cannot-access-the-repository-because-you-are-not-allowed-to-or-it-is-unavailable" />
						<liferay-ui:error exception="<%= FileEntryLockException.MustOwnLock.class %>" message="you-can-only-checkin-documents-you-have-checked-out-yourself" />

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
				</div>
			</div>

			<div id="<portlet:namespace />documentLibraryModal"></div>
		</div>

		<%
		if (!defaultFolderView && (folder != null) && (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) || portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN))) {
			PortalUtil.setPageSubtitle(folder.getName(), request);
			PortalUtil.setPageDescription(folder.getDescription(), request);
		}

		boolean uploadable = true;

		if (!DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT)) {
			uploadable = false;
		}
		else {
			List<AssetVocabulary> assetVocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(scopeGroupId);

			if (!assetVocabularies.isEmpty()) {
				long classNameId = ClassNameLocalServiceUtil.getClassNameId(DLFileEntryConstants.getClassName());

				for (AssetVocabulary assetVocabulary : assetVocabularies) {
					if (assetVocabulary.isRequired(classNameId, DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)) {
						uploadable = false;

						break;
					}
				}
			}
		}
		%>

		<aui:script>
			function <portlet:namespace />toggleActionsButton() {
				var form = AUI.$(document.<portlet:namespace />fm2);

				var hide = Liferay.Util.listCheckedExcept(form, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>').length == 0;

				AUI.$('#<portlet:namespace />actionsButtonContainer').toggleClass('hide', hide);
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

			long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

			PortletURL viewFileEntryTypeURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);

			viewFileEntryTypeURL.setParameter("browseBy", "file-entry-type");
			viewFileEntryTypeURL.setParameter("fileEntryTypeId", (String)null);
			%>

			Liferay.component(
				'<portlet:namespace />DocumentLibrary',
				new Liferay.Portlet.DocumentLibrary(
					{
						classNameId: '<%= ClassNameLocalServiceUtil.getClassNameId(DLFileEntryConstants.getClassName()) %>',
						columnNames: ['<%= StringUtil.merge(escapedEntryColumns, "','") %>'],

						<%
						DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
						%>

						decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',
						displayStyle: '<%= HtmlUtil.escapeJS(displayStyle) %>',
						editEntryUrl: '<portlet:actionURL name="/document_library/edit_entry" />',
						downloadEntryUrl: '<portlet:resourceURL id="/document_library/download_entry"><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:resourceURL>',
						folders: {
							defaultParentFolderId: '<%= folderId %>',
							dimensions: {
								height: '<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT) %>',
								width: '<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH) %>'
							}
						},
						form: {
							method: 'POST',
							node: A.one(document.<portlet:namespace />fm2)
						},
						maxFileSize: <%= dlConfiguration.fileMaxSize() %>,
						moveEntryUrl: '<portlet:renderURL><portlet:param name="mvcRenderCommandName" value="/document_library/move_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="newFolderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>',
						namespace: '<portlet:namespace />',
						npmResolvedPackageName: '<%= npmResolvedPackageName %>',
						portletId: '<%= HtmlUtil.escapeJS(portletId) %>',
						redirect: encodeURIComponent('<%= currentURL %>'),
						repositories: [
							{
								id: '<%= scopeGroupId %>',
								name: '<liferay-ui:message key="local" />'
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
						selectFileEntryTypeURL: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/document_library/select_file_entry_type.jsp" /><portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" /></portlet:renderURL>',
						scopeGroupId: <%= scopeGroupId %>,
						searchContainerId: 'entries',
						trashEnabled: <%= (scopeGroupId == repositoryId) && dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId) %>,
						uploadable: <%= uploadable %>,
						uploadURL: '<%= uploadURL %>',
						viewFileEntryURL: '<portlet:renderURL><portlet:param name="mvcRenderCommandName" value="/document_library/view_file_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>',
						viewFileEntryTypeURL: '<%= viewFileEntryTypeURL %>'
					}
				),
				{
					destroyOnNavigate: true,
					portletId: '<%= HtmlUtil.escapeJS(portletId) %>'
				}
			);

			var changeScopeHandles = function(event) {
				documentLibrary.destroy();

				Liferay.detach('changeScope', changeScopeHandles);
			};

			Liferay.on('changeScope', changeScopeHandles);
		</aui:script>

		<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/view.jsp#post" />
	</c:otherwise>
</c:choose>