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
DLAdminDisplayContext dlAdminDisplayContext = (DLAdminDisplayContext)request.getAttribute(DLAdminDisplayContext.class.getName());
DLAdminManagementToolbarDisplayContext dlAdminManagementToolbarDisplayContext = (DLAdminManagementToolbarDisplayContext)request.getAttribute(DLAdminManagementToolbarDisplayContext.class.getName());

DLViewDisplayContext dlViewDisplayContext = new DLViewDisplayContext(dlAdminDisplayContext, request, renderRequest, renderResponse);
%>

<liferay-ui:success key='<%= portletDisplay.getId() + "requestProcessed" %>' message="your-request-completed-successfully" />

<c:choose>
	<c:when test="<%= dlViewDisplayContext.isFileEntryTypesNavigation() %>">
		<liferay-util:include page="/document_library/view_file_entry_types.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test="<%= dlViewDisplayContext.isFileEntryMetadataSetsNavigation() %>">
		<liferay-util:include page="/document_library/view_file_entry_metadata_sets.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/view.jsp#pre" />

		<%
		request.setAttribute("view.jsp-folderId", String.valueOf(dlViewDisplayContext.getFolderId()));

		request.setAttribute("view.jsp-repositoryId", String.valueOf(dlViewDisplayContext.getRepositoryId()));
		%>

		<liferay-trash:undo
			portletURL="<%= dlViewDisplayContext.getRestoreTrashEntriesURL() %>"
		/>

		<liferay-util:include page="/document_library/navigation.jsp" servletContext="<%= application %>" />

		<clay:management-toolbar
			additionalProps='<%=
				HashMapBuilder.<String, Object>put(
					"bulkPermissionsConfiguration",
					HashMapBuilder.<String, Object>put(
						"defaultModelClassName", Folder.class.getSimpleName()
					).put(
						"permissionsURLs",
						HashMapBuilder.<String, Object>put(
							DLFileShortcut.class.getSimpleName(), dlViewDisplayContext.getPermissionURL(DLFileShortcutConstants.getClassName())
						).put(
							FileEntry.class.getSimpleName(), dlViewDisplayContext.getPermissionURL(DLFileEntryConstants.getClassName())
						).put(
							Folder.class.getSimpleName(), dlViewDisplayContext.getPermissionURL(DLFolderConstants.getClassName())
						).build()
					).build()
				).put(
					"collectDigitalSignaturePortlet", DigitalSignaturePortletKeys.COLLECT_DIGITAL_SIGNATURE
				).put(
					"downloadEntryURL", dlViewDisplayContext.getDownloadEntryURL()
				).put(
					"editEntryURL", dlViewDisplayContext.getEditEntryURL()
				).put(
					"folderConfiguration",
					HashMapBuilder.<String, Object>put(
						"defaultParentFolderId", dlViewDisplayContext.getFolderId()
					).put(
						"dimensions",
						HashMapBuilder.<String, Object>put(
							"height", PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT)
						).put(
							"width", PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH)
						).build()
					).build()
				).put(
					"openViewMoreFileEntryTypesURL", dlViewDisplayContext.getViewMoreFileEntryTypesURL()
				).put(
					"selectAssetCategoriesURL", dlViewDisplayContext.getSelectAssetCategoriesURL()
				).put(
					"selectAssetTagsURL", dlViewDisplayContext.getSelectAssetTagsURL()
				).put(
					"selectExtensionURL", dlViewDisplayContext.getSelectExtensionURL()
				).put(
					"selectFileEntryTypeURL", dlViewDisplayContext.getSelectFileEntryTypeURL()
				).put(
					"selectFolderURL", dlViewDisplayContext.getSelectFolderURL()
				).put(
					"trashEnabled", dlTrashHelper.isTrashEnabled(scopeGroupId, dlViewDisplayContext.getRepositoryId())
				).put(
					"viewFileEntryTypeURL", dlViewDisplayContext.getViewFileEntryTypeURL()
				).put(
					"viewFileEntryURL", dlViewDisplayContext.getViewFileEntryURL()
				).build()
			%>'
			managementToolbarDisplayContext="<%= dlAdminManagementToolbarDisplayContext %>"
			propsTransformer="document_library/js/DLManagementToolbarPropsTransformer"
		/>

		<%
		BulkSelectionRunner bulkSelectionRunner = BulkSelectionRunnerUtil.getBulkSelectionRunner();
		%>

		<div>
			<react:component
				module="document_library/js/bulk/BulkStatus.es"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"bulkComponentId", liferayPortletResponse.getNamespace() + "BulkStatus"
					).put(
						"bulkInProgress", bulkSelectionRunner.isBusy(user)
					).put(
						"pathModule", PortalUtil.getPathModule()
					).build()
				%>'
			/>
		</div>

		<div id="<portlet:namespace />documentLibraryContainer">

			<%
			boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));
			%>

			<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
				<liferay-frontend:sidebar-panel
					resourceURL="<%= dlViewDisplayContext.getSidebarPanelURL() %>"
					searchContainerId="entries"
					title='<%= LanguageUtil.get(request, "info-panel") %>'
				>
					<liferay-util:include page="/document_library/info_panel.jsp" servletContext="<%= application %>" />
				</liferay-frontend:sidebar-panel>

				<div class="sidenav-content <%= portletTitleBasedNavigation ? "container-fluid container-fluid-max-xl container-view" : StringPool.BLANK %>">
					<div class="document-library-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
						<c:if test="<%= !dlViewDisplayContext.isSearch() %>">

							<%
							DLBreadcrumbUtil.addPortletBreadcrumbEntries(dlViewDisplayContext.getFolder(), request, liferayPortletResponse);
							%>

							<liferay-site-navigation:breadcrumb
								breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, false, false, false, false, true) %>"
							/>
						</c:if>
					</div>

					<c:if test="<%= dlViewDisplayContext.isOpenInMSOfficeEnabled() %>">
						<div class="alert alert-danger hide" id="<portlet:namespace />openMSOfficeError"></div>
					</c:if>

					<aui:form action="<%= dlViewDisplayContext.getEditFileEntryURL() %>" method="get" name="fm2">
						<aui:input name="<%= Constants.CMD %>" type="hidden" />
						<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
						<aui:input name="repositoryId" type="hidden" value="<%= dlViewDisplayContext.getRepositoryId() %>" />
						<aui:input name="newFolderId" type="hidden" />
						<aui:input name="folderId" type="hidden" value="<%= dlViewDisplayContext.getFolderId() %>" />
						<aui:input name="changeLog" type="hidden" />
						<aui:input name="versionIncrease" type="hidden" />
						<aui:input name="selectAll" type="hidden" value="<%= false %>" />

						<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/view.jsp#errors" />

						<liferay-ui:error exception="<%= AuthenticationRepositoryException.class %>" message="you-cannot-access-the-repository-because-you-are-not-allowed-to-or-it-is-unavailable" />
						<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
						<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
						<liferay-ui:error exception="<%= FileEntryLockException.MustBeUnlocked.class %>" message="you-cannot-perform-this-operation-on-checked-out-documents-.please-check-it-in-or-cancel-the-checkout-first" />
						<liferay-ui:error exception="<%= FileEntryLockException.MustOwnLock.class %>" message="you-can-only-checkin-documents-you-have-checked-out-yourself" />
						<liferay-ui:error key="externalServiceFailed" message="you-cannot-access-external-service-because-you-are-not-allowed-to-or-it-is-unavailable" />

						<c:if test='<%= SessionErrors.contains(renderRequest, "googleDriveFileMissing") %>'>
							<aui:script>
								Liferay.Util.openToast({
									message: '<liferay-ui:message key="the-google-drive-file-was-missing" />',
									title: Liferay.Language.get('warning'),
									toastProps: {
										autoClose: 5000,
									},
									type: 'warning',
								});
							</aui:script>
						</c:if>

						<c:choose>
							<c:when test="<%= dlViewDisplayContext.isSearch() %>">
								<liferay-util:include page="/document_library/search_resources.jsp" servletContext="<%= application %>" />
							</c:when>
							<c:otherwise>
								<liferay-util:include page="/document_library/view_entries.jsp" servletContext="<%= application %>" />
							</c:otherwise>
						</c:choose>

						<div class="d-none" id="<portlet:namespace />appViewEntryTemplates">
							<clay:vertical-card
								verticalCard="<%= new FileEntryTemplateVerticalCard(dlViewDisplayContext, request) %>"
							/>

							<dd class="display-descriptive entry-display-style list-group-item list-group-item-flex">
								<div class="autofit-col"></div>

								<div class="autofit-col">
									<clay:sticker
										cssClass="file-icon-color-0"
										icon="document-default"
									/>
								</div>

								<div class="autofit-col autofit-col-expand">
									<h2 class="h5">
										<aui:a href="<%= dlViewDisplayContext.getUploadURL() %>">
											{title}
										</aui:a>
									</h2>

									<span>
										<liferay-ui:message arguments="<%= HtmlUtil.escape(user.getFullName()) %>" key="right-now-by-x" />
									</span>
								</div>

								<div class="autofit-col"></div>
							</dd>
						</div>
					</aui:form>
				</div>
			</div>

			<div id="<portlet:namespace />documentLibraryModal"></div>
		</div>

		<%
		if (dlViewDisplayContext.isShowFolderDescription()) {
			Folder folder = dlViewDisplayContext.getFolder();

			PortalUtil.setPageDescription(folder.getDescription(), request);
		}
		%>

		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"columnNames", dlViewDisplayContext.getEntryColumnNames()
				).put(
					"defaultParentFolderId", dlViewDisplayContext.getFolderId()
				).put(
					"displayStyle", HtmlUtil.escapeJS(dlAdminDisplayContext.getDisplayStyle())
				).put(
					"editEntryUrl", dlViewDisplayContext.getEditEntryURL()
				).put(
					"maxFileSize", DLValidatorUtil.getMaxAllowableSize(themeDisplay.getScopeGroupId(), null)
				).put(
					"namespace", "<portlet:namespace />"
				).put(
					"redirect", currentURL
				).put(
					"scopeGroupId", scopeGroupId
				).put(
					"searchContainerId", "entries"
				).put(
					"selectFolderURL", dlViewDisplayContext.getSelectFolderURL()
				).put(
					"uploadable", dlViewDisplayContext.isUploadable()
				).put(
					"uploadURL", dlViewDisplayContext.getUploadURL()
				).put(
					"viewFileEntryURL", dlViewDisplayContext.getViewFileEntryURL()
				).build()
			%>'
			destroyOnNavigate="<%= true %>"
			module="document_library/js/DocumentLibrary"
		/>

		<%
		long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId);

		Map<String, Object> editTagsProps = HashMapBuilder.<String, Object>put(
			"groupIds", groupIds
		).put(
			"pathModule", PortalUtil.getPathModule()
		).put(
			"repositoryId", String.valueOf(dlViewDisplayContext.getRepositoryId())
		).build();
		%>

		<div>
			<react:component
				module="document_library/js/categorization/tags/EditTags.es"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"context", Collections.singletonMap("namespace", liferayPortletResponse.getNamespace())
					).put(
						"props", editTagsProps
					).build()
				%>'
			/>
		</div>

		<%
		Map<String, Object> editCategoriesProps = HashMapBuilder.<String, Object>put(
			"groupIds", groupIds
		).put(
			"pathModule", PortalUtil.getPathModule()
		).put(
			"repositoryId", String.valueOf(dlViewDisplayContext.getRepositoryId())
		).put(
			"selectCategoriesUrl", dlViewDisplayContext.getSelectCategoriesURL()
		).build();
		%>

		<div>
			<react:component
				module="document_library/js/categorization/categories/EditCategories.es"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"context", Collections.singletonMap("namespace", liferayPortletResponse.getNamespace())
					).put(
						"props", editCategoriesProps
					).build()
				%>'
			/>
		</div>

		<portlet:actionURL name="/document_library/edit_file_entry_image_editor" var="editImageURL" />

		<div>
			<react:component
				module="document_library/js/image-editor/EditImageWithImageEditor"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"editImageURL", editImageURL
					).put(
						"redirectURL", currentURL
					).build()
				%>'
			/>
		</div>

		<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/view.jsp#post" />
	</c:otherwise>
</c:choose>

<%@ include file="/document_library/friendly_url_changed_message.jspf" %>