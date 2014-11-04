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
String browseBy = ParamUtil.getString(request, "browseBy");

Folder folder = (Folder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

boolean expandFolder = ParamUtil.getBoolean(request, "expandFolder");

if (folder != null) {
	parentFolderId = folder.getParentFolderId();

	if (expandFolder) {
		parentFolderId = folderId;
	}

	if ((parentFolderId == 0) && (repositoryId != scopeGroupId) || (folder.isRoot() && !folder.isDefaultRepository())) {
		if (folder.isMountPoint()) {
			parentFolderId = folderId;
		}
		else {
			parentFolderId = DLAppLocalServiceUtil.getMountFolder(repositoryId).getFolderId();

			folderId = parentFolderId;

			folder = DLAppServiceUtil.getFolder(folderId);
		}
	}

	if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		try {
			DLAppServiceUtil.getFolder(folderId);
		}
		catch (NoSuchFolderException nsfe) {
			parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}
	}
}

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

int total = 0;

long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId);

if (browseBy.equals("file-entry-type")) {
	total = DLFileEntryTypeServiceUtil.getFileEntryTypesCount(groupIds);
}
else if ((folderId != rootFolderId) || expandFolder) {
	total = DLAppServiceUtil.getFoldersCount(repositoryId, parentFolderId, false);
}

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/document_library/view");
portletURL.setParameter("folderId", String.valueOf(folderId));

SearchContainer searchContainer = new SearchContainer(liferayPortletRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

searchContainer.setTotal(total);

String parentTitle = StringPool.BLANK;

if (browseBy.equals("file-entry-type")) {
	parentTitle = LanguageUtil.get(request, "browse-by-type");
}
else {
	if ((folderId != rootFolderId) && (parentFolderId > 0) && (folder != null) && (!folder.isMountPoint() || expandFolder)) {
		Folder grandparentFolder = DLAppServiceUtil.getFolder(parentFolderId);

		parentTitle = grandparentFolder.getName();
	}
	else if (((folderId != rootFolderId) && (parentFolderId == 0)) || ((folderId == rootFolderId) && (parentFolderId == 0) && expandFolder)) {
		parentTitle = LanguageUtil.get(request, "home");
	}
}
%>

<div id="<portlet:namespace />listViewContainer">
	<div id="<portlet:namespace />folderContainer">
		<aui:nav cssClass="list-group">
			<c:if test="<%= Validator.isNotNull(parentTitle) %>">
				<li class="list-group-item nav-header">
					<%= HtmlUtil.escape(parentTitle) %>
				</li>
			</c:if>

			<c:choose>
				<c:when test='<%= (((folderId == rootFolderId) && !expandFolder) || ((folder != null) && (folder.isRoot() && !folder.isDefaultRepository() && !expandFolder))) && !browseBy.equals("file-entry-type") %>'>
					<portlet:renderURL var="viewDocumentsHomeURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
					</portlet:renderURL>

					<%
					String navigation = ParamUtil.getString(request, "navigation", "home");

					request.setAttribute("view_entries.jsp-folder", folder);
					request.setAttribute("view_entries.jsp-folderId", String.valueOf(folderId));
					request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(repositoryId));
					%>

					<aui:nav-item
						cssClass="folder list-group-item navigation-entry"
						href="<%= viewDocumentsHomeURL %>"
						iconCssClass="icon-home"
						label='<%= HtmlUtil.escape(LanguageUtil.get(request, "home")) %>'
						localizeLabel="<%= false %>"
						selected='<%= ((navigation.equals("home")) && (folderId == rootFolderId) && (fileEntryTypeId == -1)) %>'
					>

						<%
						request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
						%>

						<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />
					</aui:nav-item>

					<portlet:renderURL var="viewRecentDocumentsURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="navigation" value="recent" />
						<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
					</portlet:renderURL>

					<aui:nav-item
						cssClass="folder list-group-item navigation-entry"
						href="<%= viewRecentDocumentsURL %>"
						iconCssClass="icon-time"
						label='<%= HtmlUtil.escape(LanguageUtil.get(request, "recent")) %>'
						localizeLabel="<%= false %>"
						selected='<%= navigation.equals("recent") %>'
					/>

					<c:if test="<%= themeDisplay.isSignedIn() %>">
						<portlet:renderURL var="viewMyDocumentsURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="navigation" value="mine" />
							<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
						</portlet:renderURL>

						<aui:nav-item
							cssClass="folder list-group-item navigation-entry"
							href="<%= viewMyDocumentsURL %>"
							iconCssClass="icon-user"
							label='<%= HtmlUtil.escape(LanguageUtil.get(request, "mine")) %>'
							localizeLabel="<%= false %>"
							selected='<%= navigation.equals("mine") %>'
						/>
					</c:if>

					<c:if test="<%= DLFileEntryTypeServiceUtil.getFileEntryTypesCount(groupIds) > 0 %>">
						<portlet:renderURL var="viewBasicFileEntryTypeURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="browseBy" value="file-entry-type" />
							<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
						</portlet:renderURL>

						<aui:nav-item
							cssClass="folder list-group-item navigation-entry"
							href="<%= viewBasicFileEntryTypeURL %>"
							iconCssClass="icon-file"
							label='<%= HtmlUtil.escape(LanguageUtil.get(request, "browse-by-type")) %>'
							localizeLabel="<%= false %>"
							selected='<%= browseBy.equals("file-entry-type") %>'
						/>
					</c:if>

					<%
					List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(scopeGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, searchContainer.getStart(), searchContainer.getEnd());

					for (Folder mountFolder : mountFolders) {
						request.setAttribute("view_entries.jsp-folder", mountFolder);
						request.setAttribute("view_entries.jsp-folderId", String.valueOf(mountFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-folderSelected", String.valueOf(folderId == mountFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(mountFolder.getRepositoryId()));

						try {
					%>

							<portlet:renderURL var="viewURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(mountFolder.getFolderId()) %>" />
							</portlet:renderURL>

							<aui:nav-item
								cssClass="folder list-group-item navigation-entry"
								href="<%= viewURL %>"
								iconCssClass="icon-hdd"
								label="<%= mountFolder.getName() %>"
								localizeLabel="<%= false %>"
								selected="<%= (mountFolder.getFolderId() == folderId) %>"
							>

								<%
								request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
								%>

								<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />
							</aui:nav-item>

						<%
						}
						catch (Exception e) {
							if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
								String errorMessage = null;

								if (e instanceof PrincipalException) {
									errorMessage = "an-authentication-error-occurred-while-connecting-to-the-repository";
								}
								else {
									errorMessage = "an-unexpected-error-occurred-while-connecting-to-the-repository";
								}
						%>

								<li class="error folder navigation-entry" title="<%= LanguageUtil.get(request, errorMessage) %>">

									<%
									request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
									%>

									<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />

									<span class="browse-folder">
										<liferay-ui:icon alt="drive-error" cssClass="drive-error" iconCssClass="icon-hdd" />

										<span class="entry-title">
											<%= HtmlUtil.escape(mountFolder.getName()) %>
										</span>
									</span>
								</li>

					<%
							}
						}
					}
					%>

				</c:when>
				<c:when test='<%= browseBy.equals("file-entry-type") %>'>
					<portlet:renderURL var="viewUpURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="structureId" value="<%= String.valueOf(0) %>" />
					</portlet:renderURL>

					<aui:nav-item
						cssClass="folder list-group-item navigation-entry"
						href="<%= viewUpURL %>"
						iconCssClass="icon-level-up"
						label='<%= HtmlUtil.escape(LanguageUtil.get(request, "up")) %>'
						localizeLabel="<%= false %>"
					/>

					<c:if test="<%= total > 0 %>">
						<c:if test="<%= searchContainer.getStart() == 0 %>">
							<portlet:renderURL var="viewBasicFileEntryTypeURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="browseBy" value="file-entry-type" />
								<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
								<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(0) %>" />
							</portlet:renderURL>

							<aui:nav-item
								cssClass="folder list-group-item navigation-entry"
								href="<%= viewBasicFileEntryTypeURL %>"
								iconCssClass="icon-file-alt"
								label='<%= HtmlUtil.escape(LanguageUtil.get(request, "basic-document")) %>'
								localizeLabel="<%= false %>"
								selected="<%= (fileEntryTypeId == 0) %>"
							/>
						</c:if>

						<%
						List<DLFileEntryType> fileEntryTypes = DLFileEntryTypeServiceUtil.getFileEntryTypes(groupIds, searchContainer.getStart(), searchContainer.getEnd());

						for (DLFileEntryType fileEntryType : fileEntryTypes) {
							request.setAttribute("view_folders.jsp-fileEntryType", fileEntryType);

							AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());
						%>

							<portlet:renderURL var="viewFileEntryTypeURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="browseBy" value="file-entry-type" />
								<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
								<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
							</portlet:renderURL>

							<aui:nav-item
								cssClass="folder list-group-item navigation-entry"
								href="<%= viewFileEntryTypeURL %>"
								iconCssClass="<%= assetRendererFactory.getIconCssClass() %>"
								label="<%= fileEntryType.getName(locale) %>"
								localizeLabel="<%= false %>"
								selected="<%= (fileEntryTypeId == fileEntryType.getFileEntryTypeId()) %>"
							/>

						<%
						}
						%>

					</c:if>
				</c:when>
				<c:otherwise>
					<portlet:renderURL var="viewURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
					</portlet:renderURL>

					<aui:nav-item
						cssClass="folder list-group-item navigation-entry"
						href="<%= viewURL %>"
						iconCssClass="icon-level-up"
						label='<%= LanguageUtil.get(request, "up") %>'
						localizeLabel="<%= false %>"
					/>

					<%
					List<Folder> folders = DLAppServiceUtil.getFolders(repositoryId, parentFolderId, false, searchContainer.getStart(), searchContainer.getEnd());

					for (Folder curFolder : folders) {
						request.setAttribute("view_entries.jsp-folder", curFolder);
						request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-folderSelected", String.valueOf(folderId == curFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(curFolder.getRepositoryId()));
					%>

						<portlet:renderURL var="viewURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
						</portlet:renderURL>

						<aui:nav-item
							cssClass="folder list-group-item navigation-entry"
							href="<%= viewURL %>"
							iconCssClass='<%= (DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(curFolder.getRepositoryId(), curFolder.getFolderId(), WorkflowConstants.STATUS_APPROVED, true) > 0) ? "icon-folder-open" : "icon-folder-close" %>'
							label="<%= curFolder.getName() %>"
							localizeLabel="<%= false %>"
							selected="<%= (curFolder.getFolderId() == folderId) %>"
						>

							<%
							request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
							%>

							<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />
						</aui:nav-item>

					<%
					}
					%>

				</c:otherwise>
			</c:choose>
		</aui:nav>
	</div>
</div>

<div class="document-entries-pagination">
	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
</div>