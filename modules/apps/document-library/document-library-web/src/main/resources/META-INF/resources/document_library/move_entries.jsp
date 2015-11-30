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
String redirect = ParamUtil.getString(request, "redirect");

long newFolderId = ParamUtil.getLong(request, "newFolderId");

long[] fileShortcutIds = ParamUtil.getLongValues(request, "rowIdsDLFileShortcut");

List<Folder> folders = (List<Folder>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDERS);

List<Folder> invalidMoveFolders = new ArrayList<Folder>();
List<Folder> validMoveFolders = new ArrayList<Folder>();

for (Folder curFolder : folders) {
	boolean movePermission = DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE) && (!curFolder.isLocked() || curFolder.hasLock());

	if (movePermission) {
		validMoveFolders.add(curFolder);
	}
	else {
		invalidMoveFolders.add(curFolder);
	}
}

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

List<FileEntry> fileEntries = null;

if (fileEntry != null) {
	fileEntries = new ArrayList<FileEntry>();

	fileEntries.add(fileEntry);
}
else {
	fileEntries = (List<FileEntry>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRIES);
}

List<FileEntry> validMoveFileEntries = new ArrayList<FileEntry>();
List<FileEntry> invalidMoveFileEntries = new ArrayList<FileEntry>();

for (FileEntry curFileEntry : fileEntries) {
	boolean movePermission = DLFileEntryPermission.contains(permissionChecker, curFileEntry, ActionKeys.UPDATE) && (!curFileEntry.isCheckedOut() || curFileEntry.hasLock());

	if (movePermission) {
		validMoveFileEntries.add(curFileEntry);
	}
	else {
		invalidMoveFileEntries.add(curFileEntry);
	}
}

List<FileShortcut> fileShortcuts = (List<FileShortcut>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUTS);

List<FileShortcut> invalidShortcutEntries = new ArrayList<FileShortcut>();
List<FileShortcut> validShortcutEntries = new ArrayList<FileShortcut>();

for (FileShortcut curFileShortcut : fileShortcuts) {
	boolean movePermission = DLFileShortcutPermission.contains(permissionChecker, curFileShortcut, ActionKeys.UPDATE);

	if (movePermission) {
		validShortcutEntries.add(curFileShortcut);
	}
	else {
		invalidShortcutEntries.add(curFileShortcut);
	}
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(LanguageUtil.get(request, "move-files"));
}
%>

<div <%= portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN) ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<portlet:actionURL name="/document_library/move_entry" var="moveFileEntryURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/move_entry" />
	</portlet:actionURL>

	<aui:form action="<%= moveFileEntryURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFileEntry(false);" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="newFolderId" type="hidden" value="<%= newFolderId %>" />

		<c:if test="<%= !portletTitleBasedNavigation %>">
			<liferay-ui:header
				backURL="<%= redirect %>"
				title="move-files"
			/>
		</c:if>

		<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
		<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />

		<liferay-ui:error exception="<%= InvalidFolderException.class %>">

			<%
			InvalidFolderException ife = (InvalidFolderException)errorException;
			%>

			<liferay-ui:message arguments="<%= ife.getMessageArgument(locale) %>" key="<%= ife.getMessageKey() %>" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

		<c:if test="<%= !validMoveFolders.isEmpty() %>">
			<div class="move-list-info">
				<h4><liferay-ui:message arguments="<%= validMoveFolders.size() %>" key="x-folders-ready-to-be-moved" translateArguments="<%= false %>" /></h4>
			</div>

			<div class="move-list">
				<ul class="list-unstyled">

					<%
					for (Folder folder : validMoveFolders) {
						AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());

						AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(folder.getFolderId());
					%>

						<li class="move-folder">
							<i class="<%= assetRenderer.getIconCssClass() %>"></i>

							<span class="folder-title">
								<%= HtmlUtil.escape(folder.getName()) %>
							</span>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>

		<c:if test="<%= !invalidMoveFolders.isEmpty() %>">
			<div class="move-list-info">
				<h4><liferay-ui:message arguments="<%= invalidMoveFolders.size() %>" key="x-folders-cannot-be-moved" translateArguments="<%= false %>" /></h4>
			</div>

			<div class="move-list">
				<ul class="list-unstyled">

					<%
					for (Folder folder : invalidMoveFolders) {
						AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());

						AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(folder.getFolderId());
					%>

						<li class="icon-warning-sign move-error move-folder">
							<i class="<%= assetRenderer.getIconCssClass() %>"></i>

							<span class="folder-title">
								<%= HtmlUtil.escape(folder.getName()) %>
							</span>

							<span class="error-message">
								<c:choose>
									<c:when test="<%= folder.isLocked() && !folder.hasLock() %>">
										<liferay-ui:message key="you-cannot-modify-this-folder-because-it-was-locked" />
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="you-do-not-have-the-required-permissions" />
									</c:otherwise>
								</c:choose>
							</span>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>

		<aui:input name="rowIdsFolder" type="hidden" value="<%= ListUtil.toString(validMoveFolders, Folder.FOLDER_ID_ACCESSOR) %>" />

		<c:if test="<%= !validMoveFileEntries.isEmpty() %>">
			<div class="move-list-info">
				<h4><liferay-ui:message arguments="<%= validMoveFileEntries.size() %>" key="x-files-ready-to-be-moved" translateArguments="<%= false %>" /></h4>
			</div>

			<div class="move-list">
				<ul class="list-unstyled">

					<%
					for (FileEntry validMoveFileEntry : validMoveFileEntries) {
						AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());

						AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(validMoveFileEntry.getFileEntryId());
					%>

						<li class="move-file">
							<i class="<%= assetRenderer.getIconCssClass() %>"></i>

							<span class="file-title" title="<%= HtmlUtil.escapeAttribute(validMoveFileEntry.getTitle()) %>">
								<%= HtmlUtil.escape(validMoveFileEntry.getTitle()) %>
							</span>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>

		<c:if test="<%= !invalidMoveFileEntries.isEmpty() %>">
			<div class="move-list-info">
				<h4><liferay-ui:message arguments="<%= invalidMoveFileEntries.size() %>" key="x-files-cannot-be-moved" translateArguments="<%= false %>" /></h4>
			</div>

			<div class="move-list">
				<ul class="list-unstyled">

					<%
					for (FileEntry invalidMoveFileEntry : invalidMoveFileEntries) {
						AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());

						AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(invalidMoveFileEntry.getFileEntryId());

						com.liferay.portal.kernel.lock.Lock lock = invalidMoveFileEntry.getLock();
					%>

						<li class="icon-warning-sign move-error move-file">
							<i class="<%= assetRenderer.getIconCssClass() %>"></i>

							<span class="file-title" title="<%= HtmlUtil.escapeAttribute(invalidMoveFileEntry.getTitle()) %>">
								<%= HtmlUtil.escape(invalidMoveFileEntry.getTitle()) %>
							</span>

							<span class="error-message">
								<c:choose>
									<c:when test="<%= invalidMoveFileEntry.isCheckedOut() && !invalidMoveFileEntry.hasLock() %>">
										<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId()))), dateFormatDateTime.format(lock.getCreateDate())} %>" key="you-cannot-modify-this-document-because-it-was-checked-out-by-x-on-x" translateArguments="<%= false %>" />
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="you-do-not-have-the-required-permissions" />
									</c:otherwise>
								</c:choose>
							</span>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>

		<aui:input name="rowIdsFileEntry" type="hidden" value="<%= ListUtil.toString(validMoveFileEntries, FileEntry.FILE_ENTRY_ID_ACCESSOR) %>" />

		<c:if test="<%= !validShortcutEntries.isEmpty() %>">
			<div class="move-list-info">
				<h4><liferay-ui:message arguments="<%= validShortcutEntries.size() %>" key="x-shortcuts-ready-to-be-moved" translateArguments="<%= false %>" /></h4>
			</div>

			<div class="move-list">
				<ul class="list-unstyled">

					<%
					for (FileShortcut fileShortcut : validShortcutEntries) {
					%>

						<li class="move-file">
							<span class="file-title">
								<%= fileShortcut.getToTitle() + " (" + LanguageUtil.get(themeDisplay.getLocale(), "shortcut") + ")" %>
							</span>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>

		<c:if test="<%= !invalidShortcutEntries.isEmpty() %>">
			<div class="move-list-info">
				<h4><liferay-ui:message arguments="<%= invalidShortcutEntries.size() %>" key="x-shortcuts-cannot-be-moved" translateArguments="<%= false %>" /></h4>
			</div>

			<div class="move-list">
				<ul class="list-unstyled">

					<%
					for (FileShortcut fileShortcut : invalidShortcutEntries) {
					%>

						<li class="move-error move-file">
							<span class="file-title">
								<%= fileShortcut.getToTitle() + " (" + LanguageUtil.get(themeDisplay.getLocale(), "shortcut") + ")" %>
							</span>

							<span class="error-message">
								<liferay-ui:message key="you-do-not-have-the-required-permissions" />
							</span>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>

		<aui:input name="fileShortcutIds" type="hidden" value="<%= StringUtil.merge(fileShortcutIds) %>" />

		<aui:fieldset>

			<%
			String folderName = StringPool.BLANK;

			if (newFolderId > 0) {
				Folder folder = DLAppLocalServiceUtil.getFolder(newFolderId);

				folder = folder.toEscapedModel();

				folderName = folder.getName();
			}
			else {
				folderName = LanguageUtil.get(request, "home");
			}
			%>

			<div class="form-group">
				<aui:input label="new-folder" name="folderName" type="resource" value="<%= folderName %>" />

				<aui:button name="selectFolderButton" value="select" />
			</div>

			<aui:button-row>
				<aui:button type="submit" value="move" />

				<aui:button href="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</aui:fieldset>
	</aui:form>
</div>

<aui:script>
	AUI.$('#<portlet:namespace />selectFolderButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true,
						width: 680
					},
					id: '<portlet:namespace />selectFolder',
					title: '<liferay-ui:message arguments="folder" key="select-x" />',

					<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="mvcRenderCommandName" value="/document_library/select_folder" />
						<portlet:param name="folderId" value="<%= String.valueOf(newFolderId) %>" />
					</portlet:renderURL>

					uri: '<%= selectFolderURL.toString() %>'
				},
				function(event) {
					var folderData = {
						idString: 'newFolderId',
						idValue: event.folderid,
						nameString: 'folderName',
						nameValue: event.foldername
					};

					Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
				}
			);
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />saveFileEntry() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "move-files"), currentURL);
%>