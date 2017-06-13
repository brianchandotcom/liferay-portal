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
long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"), ParamUtil.getLong(request, "repositoryId"));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));

List<Folder> folders = (List<Folder>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDERS);
List<FileEntry> fileEntries = (List<FileEntry>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRIES);
List<FileShortcut> fileShortcuts = (List<FileShortcut>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUTS);

Map<String, Object> infoPanelToggleData = new HashMap<String, Object>();

infoPanelToggleData.put("toggle", liferayPortletResponse.getNamespace() + "infoPanelId");

if (ListUtil.isEmpty(folders) && ListUtil.isEmpty(fileEntries) && ListUtil.isEmpty(fileShortcuts)) {
	long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"), ParamUtil.getLong(request, "folderId"));

	folders = new ArrayList<Folder>();

	Folder folder = (Folder)request.getAttribute("view.jsp-folder");

	if (folder != null) {
		folders.add(folder);
	}
	else if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		folders.add(DLAppLocalServiceUtil.getFolder(folderId));
	}
	else {
		folders.add(null);
	}
}
%>

<c:choose>
	<c:when test="<%= ListUtil.isEmpty(fileEntries) && ListUtil.isEmpty(fileShortcuts) && ListUtil.isNotEmpty(folders) && (folders.size() == 1) %>">

		<%
		Folder folder = folders.get(0);

		request.setAttribute("info_panel.jsp-folder", folder);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-actions">
				<li>
					<liferay-util:include page="/document_library/subscribe.jsp" servletContext="<%= application %>" />
				</li>
				<li>
					<liferay-util:include page="/document_library/folder_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4 class="sidebar-title"><%= (folder != null) ? folder.getName() : LanguageUtil.get(request, "home") %></h4>

			<h5>
				<liferay-ui:message key="folder" />
			</h5>
		</div>

		<liferay-ui:tabs cssClass="navbar-no-collapse" names="details" refresh="<%= false %>" type="dropdown">
			<liferay-ui:section>
				<div class="sidebar-body">
					<dl class="sidebar-block">
						<dt class="h5">
							<liferay-ui:message key="num-of-items" />
						</dt>

						<%
						long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

						if (folder != null) {
							folderId = folder.getFolderId();
						}
						%>

						<dd class="h6 sidebar-caption">
							<%= DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, WorkflowConstants.STATUS_APPROVED, true) %>
						</dd>

						<c:if test="<%= folder != null %>">
							<dt class="h5">
								<liferay-ui:message key="created" />
							</dt>
							<dd class="h6 sidebar-caption">
								<%= HtmlUtil.escape(folder.getUserName()) %>
							</dd>
						</c:if>
					</dl>
				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:when>
	<c:when test="<%= ListUtil.isEmpty(folders) && ListUtil.isEmpty(fileShortcuts) && ListUtil.isNotEmpty(fileEntries) && (fileEntries.size() == 1) %>">

		<%
		FileEntry fileEntry = fileEntries.get(0);

		request.setAttribute("info_panel.jsp-fileEntry", fileEntry);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-actions">
				<li>
					<liferay-util:include page="/document_library/file_entry_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4 class="sidebar-title"><%= HtmlUtil.escape(fileEntry.getTitle()) %></h4>

			<h5>
				<liferay-ui:message key="document" />
			</h5>
		</div>

		<liferay-ui:tabs cssClass="navbar-no-collapse" names="details,versions" refresh="<%= false %>" type="dropdown">
			<liferay-ui:section>
				<div class="sidebar-body">

					<%
					FileVersion fileVersion = null;

					if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
						fileVersion = fileEntry.getLatestFileVersion();
					}
					else {
						fileVersion = fileEntry.getFileVersion();
					}

					String thumbnailSrc = DLUtil.getThumbnailSrc(fileEntry, fileVersion, themeDisplay);
					%>

					<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
						<div class="crop-img crop-img-center crop-img-middle sidebar-panel">
							<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="thumbnail" />" class="img-responsive" src="<%= DLUtil.getThumbnailSrc(fileEntry, fileVersion, themeDisplay) %>" />
						</div>
					</c:if>

					<aui:button-row>
						<aui:button cssClass="btn-sm" href="<%= DLUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK) %>" value="download" />
					</aui:button-row>

					<div class="sidebar-block">
						<aui:input name="url" type="resource" value="<%= DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, true) %>" />

						<c:if test="<%= portletDisplay.isWebDAVEnabled() && fileEntry.isSupportsSocial() %>">

							<%
							String webDavHelpMessage = null;

							if (BrowserSnifferUtil.isWindows(request)) {
								webDavHelpMessage = LanguageUtil.format(request, "webdav-windows-help", new Object[] {"https://support.microsoft.com/en-us/kb/892211", "https://dev.liferay.com/discover/portal/-/knowledge_base/7-0/publishing-files#desktop-access-to-documents-and-media"}, false);
							}
							else {
								webDavHelpMessage = LanguageUtil.format(request, "webdav-help", "https://dev.liferay.com/discover/portal/-/knowledge_base/7-0/publishing-files#desktop-access-to-documents-and-media", false);
							}
							%>

							<aui:input helpMessage="<%= webDavHelpMessage %>" name="webDavURL" type="resource" value="<%= DLUtil.getWebDavURL(themeDisplay, fileEntry.getFolder(), fileEntry) %>" />
						</c:if>
					</div>

					<dl class="sidebar-block">
						<dt class="h5">
							<liferay-ui:message key="created" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(fileEntry.getUserName()) %>
						</dd>

						<c:if test="<%= Validator.isNotNull(fileEntry.getDescription()) %>">
							<dt class="h5">
								<liferay-ui:message key="description" />
							</dt>
							<dd class="h6 sidebar-caption">
								<%= HtmlUtil.escape(fileEntry.getDescription()) %>
							</dd>
						</c:if>

						<dt class="h5">
							<liferay-ui:message key="size" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(TextFormatter.formatStorageSize(fileEntry.getSize(), locale)) %>
						</dd>

						<c:if test="<%= Validator.isNotNull(fileVersion.getExtension()) %>">
							<dt class="h5">
								<liferay-ui:message key="extension" />
							</dt>
							<dd class="h6 sidebar-caption">
								<%= HtmlUtil.escape(fileVersion.getExtension()) %>
							</dd>
						</c:if>

						<dt class="h5">
							<liferay-ui:message key="version" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(fileVersion.getVersion()) %>
						</dd>
					</dl>

					<%
					long assetClassPK = 0;

					if (!fileVersion.isApproved() && !fileVersion.getVersion().equals(DLFileEntryConstants.VERSION_DEFAULT) && !fileEntry.isInTrash()) {
						assetClassPK = fileVersion.getFileVersionId();
					}
					else {
						assetClassPK = fileEntry.getFileEntryId();
					}
					%>

					<div class="lfr-asset-categories sidebar-block">
						<liferay-ui:asset-categories-summary
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= assetClassPK %>"
						/>
					</div>

					<div class="lfr-asset-tags sidebar-block">
						<liferay-ui:asset-tags-summary
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= assetClassPK %>"
							message="tags"
						/>
					</div>

					<liferay-ui:ratings
						className="<%= DLFileEntryConstants.getClassName() %>"
						classPK="<%= fileEntry.getFileEntryId() %>"
						inTrash="<%= fileEntry.isInTrash() %>"
					/>

					<liferay-expando:custom-attributes-available className="<%= DLFileEntryConstants.getClassName() %>">
						<liferay-expando:custom-attribute-list
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= fileVersion.getFileVersionId() %>"
							editable="<%= false %>"
							label="<%= true %>"
						/>
					</liferay-expando:custom-attributes-available>

					<%
					AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.fetchEntry(DLFileEntryConstants.getClassName(), assetClassPK);
					%>

					<c:if test="<%= (layoutAssetEntry != null) && dlPortletInstanceSettings.isEnableRelatedAssets() && fileEntry.isSupportsSocial() %>">
						<div class="entry-links">
							<liferay-ui:asset-links
								assetEntryId="<%= layoutAssetEntry.getEntryId() %>"
							/>
						</div>
					</c:if>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="sidebar-body">

					<%
					request.setAttribute("info_panel.jsp-fileEntry", fileEntry);
					%>

					<liferay-util:include page="/document_library/file_entry_history.jsp" servletContext="<%= application %>" />
				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:when>
	<c:when test="<%= ListUtil.isEmpty(folders) && ListUtil.isEmpty(fileEntries) && ListUtil.isNotEmpty(fileShortcuts) && (fileShortcuts.size() == 1) %>">

		<%
		FileShortcut fileShortcut = fileShortcuts.get(0);

		request.setAttribute("info_panel.jsp-fileShortcut", fileShortcut);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-actions">
				<li>
					<liferay-util:include page="/document_library/file_entry_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4 class="sidebar-title"><%= HtmlUtil.escape(fileShortcut.getToTitle()) %></h4>

			<h5>
				<liferay-ui:message key="shortcut" />
			</h5>
		</div>

		<liferay-ui:tabs cssClass="navbar-no-collapse" names="details" refresh="<%= false %>" type="dropdown">
			<liferay-ui:section>
				<div class="sidebar-body">

					<%
					FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileShortcut.getToFileEntryId());
					%>

					<dl class="sidebar-block">
						<dt class="h5">
							<liferay-ui:message key="description" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(fileEntry.getDescription()) %>
						</dd>

						<%
						Group fileEntryGroup = GroupLocalServiceUtil.getGroup(fileEntry.getGroupId());

						Group fileEntrySiteGroup = fileEntryGroup;

						while ((fileEntrySiteGroup != null) && !fileEntrySiteGroup.isSite()) {
							fileEntrySiteGroup = fileEntrySiteGroup.getParentGroup();
						}
						%>

						<c:if test="<%= fileEntrySiteGroup != null %>">
							<dt class="h5">
								<liferay-ui:message key="target-site" />
							</dt>
							<dd class="h6 sidebar-caption">
								<%= HtmlUtil.escape(fileEntrySiteGroup.getName(locale)) %>
							</dd>
						</c:if>

						<dt class="h5">
							<liferay-ui:message key="target-folder" />
						</dt>
						<dd class="h6 sidebar-caption">

							<%
							Folder folder = fileEntry.getFolder();
							%>

							<portlet:renderURL var="targetFolderURL">
								<portlet:param name="mvcRenderCommand" value="/document_library/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
							</portlet:renderURL>

							<a href="<%= targetFolderURL %>">
								<c:choose>
									<c:when test="<%= folder.getFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
										<liferay-ui:message key="home" />
									</c:when>
									<c:otherwise>
										<%= HtmlUtil.escape(folder.getName()) %>
									</c:otherwise>
								</c:choose>
							</a>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="size" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>
						</dd>

						<c:if test="<%= fileEntry.getModel() instanceof DLFileEntry %>">

							<%
							DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

							DLFileEntryType dlFileEntryType = dlFileEntry.getDLFileEntryType();
							%>

							<dt class="h5">
								<liferay-ui:message key="document-type" />
							</dt>
							<dd class="h6 sidebar-caption">
								<%= HtmlUtil.escape(dlFileEntryType.getName(locale)) %>
							</dd>
						</c:if>

						<dt class="h5">
							<liferay-ui:message key="content-type" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(fileEntry.getMimeType()) %>
						</dd>
					</dl>
				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4 class="sidebar-title"><liferay-ui:message arguments="<%= folders.size() + fileEntries.size() %>" key="x-items-are-selected" /></h4>
		</div>

		<liferay-ui:tabs cssClass="navbar-no-collapse" names="details" refresh="<%= false %>" type="dropdown">
			<liferay-ui:section>
				<div class="sidebar-body">
					<h5>
						<liferay-ui:message arguments="<%= folders.size() + fileEntries.size() %>" key="x-items-are-selected" />
					</h5>
				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:otherwise>
</c:choose>