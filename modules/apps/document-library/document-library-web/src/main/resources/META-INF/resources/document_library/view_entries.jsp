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
String navigation = ParamUtil.getString(request, "navigation", "home");

String currentFolder = ParamUtil.getString(request, "curFolder");
String deltaFolder = ParamUtil.getString(request, "deltaFolder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

DLAdminDisplayContext dlAdminDisplayContext = (DLAdminDisplayContext)request.getAttribute(DLAdminDisplayContext.class.getName());
DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);

String displayStyle = dlAdminDisplayContext.getDisplayStyle();

FolderActionDisplayContext folderActionDisplayContext = new FolderActionDisplayContext(dlTrashHelper, request, liferayPortletResponse);

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) ? "/document_library/view" : "/document_library/view_folder");
portletURL.setParameter("navigation", navigation);
portletURL.setParameter("curFolder", currentFolder);
portletURL.setParameter("deltaFolder", deltaFolder);
portletURL.setParameter("folderId", String.valueOf(folderId));

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

entriesChecker.setCssClass("entry-selector");

entriesChecker.setRememberCheckBoxStateURLRegex(dlAdminDisplayContext.getRememberCheckBoxStateURLRegex());

EntriesMover entriesMover = new EntriesMover(dlTrashHelper.isTrashEnabled(scopeGroupId, repositoryId));

String[] entryColumns = dlPortletInstanceSettingsHelper.getEntryColumns();

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation && (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (folderId != dlAdminDisplayContext.getRootFolderId())) {
	String redirect = ParamUtil.getString(request, "redirect");

	if (Validator.isNotNull(redirect)) {
		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(redirect);
	}
}
%>

<div class="document-container" id="<portlet:namespace />entriesContainer">

	<%
	DLAdminManagementToolbarDisplayContext dlAdminManagementToolbarDisplayContext = (DLAdminManagementToolbarDisplayContext)request.getAttribute(DLAdminManagementToolbarDisplayContext.class.getName());

	SearchContainer<Object> dlSearchContainer = dlAdminDisplayContext.getSearchContainer();
	%>

	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= dlSearchContainer %>"
		total="<%= dlSearchContainer.getTotal() %>"
	>
		<liferay-ui:search-container-row
			className="Object"
			modelVar="result"
		>
			<%@ include file="/document_library/cast_result.jspf" %>

			<c:choose>
				<c:when test="<%= fileEntry != null %>">

					<%
					boolean draggable = false;

					if (!BrowserSnifferUtil.isMobile(request) && (DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE))) {
						draggable = true;

						if (dlSearchContainer.getRowMover() == null) {
							dlSearchContainer.setRowMover(entriesMover);
						}
					}

					if (dlSearchContainer.getRowChecker() == null) {
						dlSearchContainer.setRowChecker(entriesChecker);
					}

					row.setData(
						HashMapBuilder.<String, Object>put(
							"actions", StringUtil.merge(dlAdminManagementToolbarDisplayContext.getAvailableActions(fileEntry))
						).put(
							"draggable", draggable
						).put(
							"title", fileEntry.getTitle()
						).build());

					DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = null;

					if (fileShortcut == null) {
						dlViewFileVersionDisplayContext = dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileEntry.getFileVersion());

						row.setPrimaryKey(String.valueOf(fileEntry.getFileEntryId()));
					}
					else {
						dlViewFileVersionDisplayContext = dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileShortcut);

						row.setPrimaryKey(String.valueOf(fileShortcut.getFileShortcutId()));
					}

					FileVersion latestFileVersion = fileEntry.getFileVersion();

					if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
						latestFileVersion = fileEntry.getLatestFileVersion();
					}

					latestFileVersion = latestFileVersion.toEscapedModel();

					String thumbnailSrc = DLURLHelperUtil.getThumbnailSrc(fileEntry, latestFileVersion, themeDisplay);
					%>

					<c:choose>
						<c:when test='<%= displayStyle.equals("descriptive") %>'>
							<c:choose>
								<c:when test="<%= Validator.isNotNull(thumbnailSrc) %>">
									<liferay-ui:search-container-column-image
										src="<%= thumbnailSrc %>"
										toggleRowChecker="<%= true %>"
									/>
								</c:when>
								<c:otherwise>
									<liferay-ui:search-container-column-text>
										<liferay-document-library:mime-type-sticker
											cssClass="sticker-secondary"
											fileVersion="<%= latestFileVersion %>"
										/>
									</liferay-ui:search-container-column-text>
								</c:otherwise>
							</c:choose>

							<liferay-ui:search-container-column-jsp
								colspan="<%= 2 %>"
								path="/document_library/view_file_entry_descriptive.jsp"
							/>

							<liferay-ui:search-container-column-jsp
								path="/document_library/file_entry_action.jsp"
							/>
						</c:when>
						<c:when test='<%= displayStyle.equals("icon") %>'>

							<%
							row.setCssClass("entry-card lfr-asset-item");
							%>

							<liferay-ui:search-container-column-text>

								<%
								PortletURL rowURL = liferayPortletResponse.createRenderURL();

								rowURL.setParameter("mvcRenderCommandName", "/document_library/view_file_entry");
								rowURL.setParameter("redirect", HttpUtil.removeParameter(currentURL, liferayPortletResponse.getNamespace() + "ajax"));
								rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));
								%>

								<c:choose>
									<c:when test="<%= dlViewFileVersionDisplayContext.hasCustomThumbnail() %>">
										<liferay-util:buffer
											var="customThumbnailHtml"
										>

											<%
											dlViewFileVersionDisplayContext.renderCustomThumbnail(request, PipingServletResponse.createPipingServletResponse(pageContext));
											%>

										</liferay-util:buffer>

										<liferay-frontend:html-vertical-card
											actionJsp="/document_library/file_entry_action.jsp"
											actionJspServletContext="<%= application %>"
											cssClass="entry-display-style file-card"
											html="<%= customThumbnailHtml %>"
											resultRow="<%= row %>"
											rowChecker="<%= entriesChecker %>"
											title="<%= latestFileVersion.getTitle() %>"
											url="<%= (rowURL != null) ? rowURL.toString() : null %>"
										>
											<%@ include file="/document_library/file_entry_vertical_card.jspf" %>
										</liferay-frontend:html-vertical-card>
									</c:when>
									<c:when test="<%= Validator.isNull(thumbnailSrc) %>">
										<liferay-frontend:icon-vertical-card
											actionJsp="/document_library/file_entry_action.jsp"
											actionJspServletContext="<%= application %>"
											cssClass="entry-display-style file-card"
											icon="documents-and-media"
											resultRow="<%= row %>"
											rowChecker="<%= entriesChecker %>"
											title="<%= latestFileVersion.getTitle() %>"
											url="<%= (rowURL != null) ? rowURL.toString() : null %>"
										>
											<%@ include file="/document_library/file_entry_vertical_card.jspf" %>
										</liferay-frontend:icon-vertical-card>
									</c:when>
									<c:otherwise>
										<liferay-frontend:vertical-card
											actionJsp="/document_library/file_entry_action.jsp"
											actionJspServletContext="<%= application %>"
											cssClass="entry-display-style file-card"
											imageUrl="<%= thumbnailSrc %>"
											resultRow="<%= row %>"
											rowChecker="<%= entriesChecker %>"
											title="<%= latestFileVersion.getTitle() %>"
											url="<%= (rowURL != null) ? rowURL.toString() : null %>"
										>
											<%@ include file="/document_library/file_entry_vertical_card.jspf" %>
										</liferay-frontend:vertical-card>
									</c:otherwise>
								</c:choose>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>

							<%
							for (int i = 0; i < entryColumns.length; i++) {
								String curEntryColumn = entryColumns[i];
							%>

								<c:choose>
									<c:when test='<%= curEntryColumn.equals("name") %>'>

										<%
										PortletURL rowURL = liferayPortletResponse.createRenderURL();

										rowURL.setParameter("mvcRenderCommandName", "/document_library/view_file_entry");
										rowURL.setParameter("redirect", HttpUtil.removeParameter(currentURL, liferayPortletResponse.getNamespace() + "ajax"));
										rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));
										%>

										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand table-cell-minw-200 table-title"
											name="name"
										>
											<liferay-document-library:mime-type-sticker
												cssClass="sticker-secondary"
												fileVersion="<%= latestFileVersion %>"
											/>

											<aui:a href="<%= rowURL.toString() %>"><%= latestFileVersion.getTitle() %></aui:a>

											<c:if test="<%= fileEntry.hasLock() || fileEntry.isCheckedOut() %>">
												<span class="inline-item inline-item-after state-icon">
													<aui:icon image="lock" markupView="lexicon" message="locked" />
												</span>
											</c:if>

											<c:if test="<%= dlViewFileVersionDisplayContext.isShared() %>">
												<span class="inline-item inline-item-after lfr-portal-tooltip state-icon" title="<%= LanguageUtil.get(request, "shared") %>">
													<aui:icon image="users" markupView="lexicon" message="shared" />
												</span>
											</c:if>

											<c:if test="<%= fileShortcut != null %>">
												<span class="inline-item inline-item-after state-icon">
													<aui:icon image="shortcut" markupView="lexicon" message="shortcut" />
												</span>
											</c:if>
										</liferay-ui:search-container-column-text>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("description") %>'>
										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand table-cell-minw-200"
											name="description"
											value="<%= StringUtil.shorten(fileEntry.getDescription(), 100) %>"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("document-type") %>'>
										<c:choose>
											<c:when test="<%= latestFileVersion.getModel() instanceof DLFileVersion %>">

												<%
												DLFileVersion latestDLFileVersion = (DLFileVersion)latestFileVersion.getModel();

												DLFileEntryType dlFileEntryType = latestDLFileVersion.getDLFileEntryType();
												%>

												<liferay-ui:search-container-column-text
													cssClass="table-cell-expand-smaller table-cell-minw-150"
													name="document-type"
													value="<%= HtmlUtil.escape(dlFileEntryType.getName(locale)) %>"
												/>
											</c:when>
											<c:otherwise>
												<liferay-ui:search-container-column-text
													cssClass="table-cell-expand-smaller table-cell-minw-150"
													name="document-type"
													value="--"
												/>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("size") %>'>
										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand-smallest"
											name="size"
											value="<%= LanguageUtil.formatStorageSize(latestFileVersion.getSize(), locale) %>"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("status") %>'>
										<liferay-ui:search-container-column-status
											cssClass="table-cell-expand-smallest"
											name="status"
											status="<%= latestFileVersion.getStatus() %>"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("downloads") %>'>
										<c:if test="<%= ViewCountManagerUtil.isViewCountEnabled(PortalUtil.getClassNameId(DLFileEntryConstants.getClassName())) %>">
											<liferay-ui:search-container-column-text
												cssClass="table-cell-expand-smallest"
												name="downloads"
												value="<%= String.valueOf(fileEntry.getReadCount()) %>"
											/>
										</c:if>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("create-date") %>'>
										<liferay-ui:search-container-column-date
											cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
											name="create-date"
											value="<%= fileEntry.getCreateDate() %>"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("modified-date") %>'>
										<liferay-ui:search-container-column-date
											cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
											name="modified-date"
											value="<%= latestFileVersion.getModifiedDate() %>"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("action") %>'>
										<liferay-ui:search-container-column-jsp
											path="/document_library/file_entry_action.jsp"
										/>
									</c:when>
								</c:choose>

							<%
							}
							%>

						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>

					<%
					if (dlSearchContainer.getRowChecker() == null) {
						dlSearchContainer.setRowChecker(entriesChecker);
					}

					boolean draggable = false;

					if (!BrowserSnifferUtil.isMobile(request) && (DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE))) {
						draggable = true;

						if (dlSearchContainer.getRowMover() == null) {
							dlSearchContainer.setRowMover(entriesMover);
						}
					}

					row.setData(
						HashMapBuilder.<String, Object>put(
							"actions", StringUtil.merge(dlAdminManagementToolbarDisplayContext.getAvailableActions(curFolder))
						).put(
							"draggable", draggable
						).put(
							"folder", true
						).put(
							"folder-id", curFolder.getFolderId()
						).put(
							"title", curFolder.getName()
						).build());

					row.setPrimaryKey(String.valueOf(curFolder.getPrimaryKey()));
					%>

					<c:choose>
						<c:when test='<%= displayStyle.equals("descriptive") %>'>
							<liferay-ui:search-container-column-icon
								icon='<%= curFolder.isMountPoint() ? "repository" : "folder" %>'
								toggleRowChecker="<%= true %>"
							/>

							<liferay-ui:search-container-column-jsp
								colspan="<%= 2 %>"
								path="/document_library/view_folder_descriptive.jsp"
							/>

							<liferay-ui:search-container-column-jsp
								path="/document_library/folder_action.jsp"
							/>
						</c:when>
						<c:when test='<%= displayStyle.equals("icon") %>'>

							<%
							row.setCssClass("entry-card lfr-asset-folder");
							%>

							<liferay-ui:search-container-column-text
								colspan="<%= 2 %>"
							>
								<liferay-frontend:horizontal-card
									actionJsp="/document_library/folder_action.jsp"
									actionJspServletContext="<%= application %>"
									resultRow="<%= row %>"
									rowChecker="<%= entriesChecker %>"
									text="<%= curFolder.getName() %>"
									url="<%= folderActionDisplayContext.getRowURL(curFolder) %>"
								>
									<liferay-frontend:horizontal-card-col>
										<liferay-frontend:horizontal-card-icon
											icon='<%= curFolder.isMountPoint() ? "repository" : "folder" %>'
										/>
									</liferay-frontend:horizontal-card-col>
								</liferay-frontend:horizontal-card>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>

							<%
							for (int i = 0; i < entryColumns.length; i++) {
								String curEntryColumn = entryColumns[i];
							%>

								<c:choose>
									<c:when test='<%= curEntryColumn.equals("name") %>'>
										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand table-cell-minw-200 table-title"
											name="name"
										>
											<clay:sticker
												cssClass="sticker-document"
												displayType="secondary"
												icon='<%= curFolder.isMountPoint() ? "repository" : "folder" %>'
											/>

											<aui:a href="<%= folderActionDisplayContext.getRowURL(curFolder) %>"><%= HtmlUtil.escape(curFolder.getName()) %></aui:a>
										</liferay-ui:search-container-column-text>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("description") %>'>
										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand table-cell-minw-200"
											name="description"
											value="<%= StringUtil.shorten(curFolder.getDescription(), 100) %>"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("document-type") %>'>
										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand-smaller"
											name="document-type"
											value="--"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("size") %>'>
										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand-smallest"
											name="size"
											value="--"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("status") %>'>
										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand-smallest"
											name="status"
											value="--"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("downloads") %>'>
										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand-smallest"
											name="downloads"
											value="--"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("create-date") %>'>
										<liferay-ui:search-container-column-date
											cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
											name="create-date"
											value="<%= curFolder.getCreateDate() %>"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("modified-date") %>'>
										<liferay-ui:search-container-column-date
											cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
											name="modified-date"
											value="<%= curFolder.getLastPostDate() %>"
										/>
									</c:when>
									<c:when test='<%= curEntryColumn.equals("action") %>'>
										<liferay-ui:search-container-column-jsp
											path="/document_library/folder_action.jsp"
										/>
									</c:when>
								</c:choose>

							<%
							}
							%>

						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
			resultRowSplitter="<%= new DLResultRowSplitter() %>"
			searchContainer="<%= dlSearchContainer %>"
		/>
	</liferay-ui:search-container>
</div>

<%
request.setAttribute("edit_file_entry.jsp-checkedOut", true);
%>

<c:if test="<%= dlAdminDisplayContext.isVersioningStrategyOverridable() %>">
	<liferay-util:include page="/document_library/version_details.jsp" servletContext="<%= application %>" />
</c:if>