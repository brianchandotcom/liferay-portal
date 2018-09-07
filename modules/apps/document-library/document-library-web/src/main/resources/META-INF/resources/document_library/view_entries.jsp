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

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);

String displayStyle = GetterUtil.getString((String)request.getAttribute("view.jsp-displayStyle"));

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) ? "/document_library/view" : "/document_library/view_folder");
portletURL.setParameter("navigation", navigation);
portletURL.setParameter("curFolder", currentFolder);
portletURL.setParameter("deltaFolder", deltaFolder);
portletURL.setParameter("folderId", String.valueOf(folderId));

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

entriesChecker.setCssClass("entry-selector");
entriesChecker.setRememberCheckBoxStateURLRegex("^(?!.*" + liferayPortletResponse.getNamespace() + "redirect).*(folderId=" + String.valueOf(folderId) + ")");

EntriesMover entriesMover = new EntriesMover(dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId));

String[] entryColumns = dlPortletInstanceSettingsHelper.getEntryColumns();

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation && (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (folderId != rootFolderId)) {
	String redirect = ParamUtil.getString(request, "redirect");

	if (Validator.isNotNull(redirect)) {
		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(redirect);
	}

	Folder folder = DLAppServiceUtil.getFolder(folderId);

	renderResponse.setTitle(folder.getName());
}
%>

<div class="document-container" id="<portlet:namespace />entriesContainer">

	<%
	SearchContainer dlSearchContainer = dlAdminDisplayContext.getSearchContainer();
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
					FileVersion latestFileVersion = fileEntry.getFileVersion();

					if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
						latestFileVersion = fileEntry.getLatestFileVersion();
					}

					DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = null;

					if (fileShortcut == null) {
						dlViewFileVersionDisplayContext = dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileEntry.getFileVersion());

						row.setPrimaryKey(String.valueOf(fileEntry.getFileEntryId()));
					}
					else {
						dlViewFileVersionDisplayContext = dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileShortcut);

						row.setPrimaryKey(String.valueOf(fileShortcut.getFileShortcutId()));
					}

					boolean draggable = false;

					if (DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
						draggable = true;

						if (dlSearchContainer.getRowMover() == null) {
							dlSearchContainer.setRowMover(entriesMover);
						}
					}

					if (dlSearchContainer.getRowChecker() == null) {
						dlSearchContainer.setRowChecker(entriesChecker);
					}

					Map<String, Object> rowData = new HashMap<String, Object>();

					rowData.put("draggable", draggable);
					rowData.put("title", fileEntry.getTitle());
					rowData.put("actions", "checkin,download,move");

					row.setData(rowData);

					String thumbnailSrc = DLUtil.getThumbnailSrc(fileEntry, latestFileVersion, themeDisplay);
					%>

					<c:choose>
						<c:when test='<%= displayStyle.equals("descriptive") %>'>
							<c:choose>
								<c:when test="<%= fileShortcut != null %>">
									<liferay-ui:search-container-column-icon
										icon="shortcut"
										toggleRowChecker="<%= true %>"
									/>
								</c:when>
								<c:when test="<%= Validator.isNotNull(thumbnailSrc) %>">
									<liferay-ui:search-container-column-image
										src="<%= thumbnailSrc %>"
										toggleRowChecker="<%= true %>"
									/>
								</c:when>
								<c:when test="<%= Validator.isNotNull(latestFileVersion.getExtension()) %>">
									<liferay-ui:search-container-column-text>
										<div class="sticker sticker-secondary <%= dlViewFileVersionDisplayContext.getCssClassFileMimeType() %>">
											<%= StringUtil.shorten(StringUtil.upperCase(latestFileVersion.getExtension()), 3, StringPool.BLANK) %>
										</div>
									</liferay-ui:search-container-column-text>
								</c:when>
								<c:otherwise>
									<liferay-ui:search-container-column-icon
										icon="documents-and-media"
										toggleRowChecker="<%= true %>"
									/>
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
											cssClass="entry-display-style"
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
											cssClass="entry-display-style"
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
											cssClass="entry-display-style"
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
							<c:if test='<%= ArrayUtil.contains(entryColumns, "name") %>'>

								<%
								PortletURL rowURL = liferayPortletResponse.createRenderURL();

								rowURL.setParameter("mvcRenderCommandName", "/document_library/view_file_entry");
								rowURL.setParameter("redirect", HttpUtil.removeParameter(currentURL, liferayPortletResponse.getNamespace() + "ajax"));
								rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));
								%>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-content"
									name="title"
								>
									<aui:a href="<%= rowURL.toString() %>"><%= latestFileVersion.getTitle() %></aui:a>

									<c:if test="<%= fileEntry.hasLock() || fileEntry.isCheckedOut() %>">
										<span>
											<aui:icon cssClass="icon-monospaced" image="lock" markupView="lexicon" message="locked" />
										</span>
									</c:if>

									<c:if test="<%= fileShortcut != null %>">
										<span>
											<aui:icon cssClass="icon-monospaced" image="shortcut" markupView="lexicon" message="shortcut" />
										</span>
									</c:if>
								</liferay-ui:search-container-column-text>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "description") %>'>
								<liferay-ui:search-container-column-text
									cssClass="table-cell-content"
									name="description"
									value="<%= StringUtil.shorten(fileEntry.getDescription(), 100) %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "document-type") %>'>
								<c:choose>
									<c:when test="<%= latestFileVersion.getModel() instanceof DLFileVersion %>">

										<%
										DLFileVersion latestDLFileVersion = (DLFileVersion)latestFileVersion.getModel();

										DLFileEntryType dlFileEntryType = latestDLFileVersion.getDLFileEntryType();
										%>

										<liferay-ui:search-container-column-text
											name="document-type"
											value="<%= HtmlUtil.escape(dlFileEntryType.getName(locale)) %>"
										/>
									</c:when>
									<c:otherwise>
										<liferay-ui:search-container-column-text
											name="document-type"
											value="--"
										/>
									</c:otherwise>
								</c:choose>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "size") %>'>
								<liferay-ui:search-container-column-text
									name="size"
									value="<%= TextFormatter.formatStorageSize(latestFileVersion.getSize(), locale) %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "status") %>'>
								<liferay-ui:search-container-column-status
									name="status"
									status="<%= latestFileVersion.getStatus() %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "downloads") %>'>
								<liferay-ui:search-container-column-text
									name="downloads"
									value="<%= String.valueOf(fileEntry.getReadCount()) %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "create-date") %>'>
								<liferay-ui:search-container-column-date
									name="create-date"
									value="<%= fileEntry.getCreateDate() %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "modified-date") %>'>
								<liferay-ui:search-container-column-date
									name="modified-date"
									value="<%= latestFileVersion.getModifiedDate() %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "action") %>'>
								<liferay-ui:search-container-column-jsp
									path="/document_library/file_entry_action.jsp"
								/>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>

					<%
					if (dlSearchContainer.getRowChecker() == null) {
						dlSearchContainer.setRowChecker(entriesChecker);
					}

					Map<String, Object> rowData = new HashMap<String, Object>();

					boolean draggable = false;

					if (DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE)) {
						draggable = true;

						if (dlSearchContainer.getRowMover() == null) {
							dlSearchContainer.setRowMover(entriesMover);
						}
					}

					rowData.put("draggable", draggable);

					rowData.put("folder", true);
					rowData.put("folder-id", curFolder.getFolderId());
					rowData.put("title", curFolder.getName());
					rowData.put("actions", "move,edit,permissions");

					row.setData(rowData);

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

							PortletURL rowURL = liferayPortletResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "/document_library/view_folder");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
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
									url="<%= rowURL.toString() %>"
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
							<c:if test='<%= ArrayUtil.contains(entryColumns, "name") %>'>

								<%
								PortletURL rowURL = liferayPortletResponse.createRenderURL();

								rowURL.setParameter("mvcRenderCommandName", "/document_library/view_folder");
								rowURL.setParameter("redirect", currentURL);
								rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
								%>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-content"
									href="<%= rowURL %>"
									name="title"
									value="<%= curFolder.getName() %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "description") %>'>
								<liferay-ui:search-container-column-text
									cssClass="table-cell-content"
									name="description"
									value="<%= StringUtil.shorten(curFolder.getDescription(), 100) %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "document-type") %>'>
								<liferay-ui:search-container-column-text
									name="document-type"
									value="--"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "size") %>'>
								<liferay-ui:search-container-column-text
									name="size"
									value="--"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "status") %>'>
								<liferay-ui:search-container-column-text
									name="status"
									value="--"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "downloads") %>'>
								<liferay-ui:search-container-column-text
									name="downloads"
									value="--"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "create-date") %>'>
								<liferay-ui:search-container-column-date
									name="create-date"
									value="<%= curFolder.getCreateDate() %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "modified-date") %>'>
								<liferay-ui:search-container-column-date
									name="modified-date"
									value="<%= curFolder.getModifiedDate() %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "action") %>'>
								<liferay-ui:search-container-column-jsp
									path="/document_library/folder_action.jsp"
								/>
							</c:if>
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

<liferay-util:include page="/document_library/version_details.jsp" servletContext="<%= application %>" />

<%!
%>