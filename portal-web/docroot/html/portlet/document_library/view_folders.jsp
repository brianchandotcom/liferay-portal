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

Folder parentFolder = null;

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
			parentFolder = DLAppServiceUtil.getFolder(folderId);
		}
		catch (NoSuchFolderException nsfe) {
			parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}
	}
}

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", dlPortletInstanceSettings.getEntriesPerPage());

int folderStart = ParamUtil.getInteger(request, "folderStart");
int folderEnd = ParamUtil.getInteger(request, "folderEnd", SearchContainer.DEFAULT_DELTA);

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

SearchContainer searchContainer = new SearchContainer(liferayPortletRequest, null, null, "cur2", folderEnd / (folderEnd - folderStart), (folderEnd - folderStart), portletURL, null, null);

searchContainer.setTotal(total);

String parentTitle = StringPool.BLANK;

if (browseBy.equals("file-entry-type")) {
	parentTitle = LanguageUtil.get(pageContext, "browse-by-type");
}
else {
	if ((folderId != rootFolderId) && (parentFolderId > 0) && (folder != null) && (!folder.isMountPoint() || expandFolder)) {
		Folder grandparentFolder = DLAppServiceUtil.getFolder(parentFolderId);

		parentTitle = grandparentFolder.getName();
	}
	else if (((folderId != rootFolderId) && (parentFolderId == 0)) || ((folderId == rootFolderId) && (parentFolderId == 0) && expandFolder)) {
		parentTitle = LanguageUtil.get(pageContext, "home");
	}
}
%>

<div id="<portlet:namespace />listViewContainer">
	<div id="<portlet:namespace />folderContainer">
		<aui:nav cssClass="list-group">
			<c:if test="<%= Validator.isNotNull(parentTitle) %>">
				<li class="dropdown-header list-group-item">
					<%= HtmlUtil.escape(parentTitle) %>
				</li>
			</c:if>

			<c:choose>
				<c:when test='<%= (((folderId == rootFolderId) && !expandFolder) || ((folder != null) && (folder.isRoot() && !folder.isDefaultRepository() && !expandFolder))) && !browseBy.equals("file-entry-type") %>'>
					<liferay-portlet:renderURL varImpl="viewDocumentsHomeURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
						<portlet:param name="entryStart" value="0" />
						<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
						<portlet:param name="folderStart" value="0" />
						<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
					</liferay-portlet:renderURL>

					<%
					String navigation = ParamUtil.getString(request, "navigation", "home");

					request.setAttribute("view_entries.jsp-folder", folder);
					request.setAttribute("view_entries.jsp-folderId", String.valueOf(folderId));
					request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(repositoryId));

					Map<String, Object> dataView = new HashMap<String, Object>();

					dataView.put("folder", true);
					dataView.put("folder-id", rootFolderId);
					dataView.put("navigation", "home");
					dataView.put("title", LanguageUtil.get(pageContext, "home"));
					%>

					<liferay-ui:app-view-navigation-entry
						actionJsp="/html/portlet/document_library/folder_action.jsp"
						dataView="<%= dataView %>"
						entryTitle='<%= LanguageUtil.get(pageContext, "home") %>'
						iconImage="icon-home"
						selected='<%= (navigation.equals("home") && (folderId == rootFolderId) && (fileEntryTypeId == -1)) %>'
						viewURL="<%= viewDocumentsHomeURL.toString() %>"
					/>

					<c:if test="<%= rootFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">

						<%
						dataView = new HashMap<String, Object>();

						dataView.put("navigation", "recent");
						%>

						<liferay-portlet:renderURL varImpl="viewRecentDocumentsURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="navigation" value="recent" />
							<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:app-view-navigation-entry
							dataView="<%= dataView %>"
							entryTitle='<%= LanguageUtil.get(pageContext, "recent") %>'
							iconImage="icon-time"
							selected='<%= navigation.equals("recent") %>'
							viewURL="<%= viewRecentDocumentsURL.toString() %>"
						/>

						<c:if test="<%= themeDisplay.isSignedIn() %>">

							<%
							dataView = new HashMap<String, Object>();

							dataView.put("navigation", "mine");
							%>

							<liferay-portlet:renderURL varImpl="viewMyDocumentsURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="navigation" value="mine" />
								<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<liferay-ui:app-view-navigation-entry
								dataView="<%= dataView %>"
								entryTitle='<%= LanguageUtil.get(pageContext, "mine") %>'
								iconImage="icon-user"
								selected='<%= navigation.equals("mine") %>'
								viewURL="<%= viewMyDocumentsURL.toString() %>"
							/>
						</c:if>

						<c:if test="<%= DLFileEntryTypeServiceUtil.getFileEntryTypesCount(groupIds) > 0 %>">

							<%
							dataView = new HashMap<String, Object>();

							dataView.put("browse-by", "file-entry-type");
							dataView.put("view-entries", Boolean.FALSE);
							dataView.put("view-folders", Boolean.TRUE);
							%>

							<liferay-portlet:renderURL varImpl="viewBasicFileEntryTypeURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="browseBy" value="file-entry-type" />
								<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<liferay-ui:app-view-navigation-entry
								cssClass="folder file-entry-type"
								dataView="<%= dataView %>"
								entryTitle='<%= LanguageUtil.get(pageContext, "browse-by-type") %>'
								iconImage="icon-file"
								selected='<%= browseBy.equals("file-entry-type") %>'
								viewURL="<%= viewBasicFileEntryTypeURL.toString() %>"
							/>
						</c:if>
					</c:if>

					<%
					List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(scopeGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, searchContainer.getStart(), searchContainer.getEnd());

					for (Folder mountFolder : mountFolders) {
						request.setAttribute("view_entries.jsp-folder", mountFolder);
						request.setAttribute("view_entries.jsp-folderId", String.valueOf(mountFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-folderSelected", String.valueOf(folderId == mountFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(mountFolder.getRepositoryId()));

						try {
							dataView = new HashMap<String, Object>();

							dataView.put("folder", true);
							dataView.put("folder-id", mountFolder.getFolderId());
							dataView.put("repository-id", mountFolder.getRepositoryId());
							dataView.put("title", mountFolder.getName());
					%>

							<liferay-portlet:renderURL varImpl="viewURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(mountFolder.getFolderId()) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<liferay-ui:app-view-navigation-entry
								actionJsp="/html/portlet/document_library/folder_action.jsp"
								dataView="<%= dataView %>"
								entryTitle="<%= mountFolder.getName() %>"
								iconImage="icon-hdd"
								selected="<%= (mountFolder.getFolderId() == folderId) %>"
								viewURL="<%= viewURL.toString() %>"
							/>

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

								<li class="app-view-navigation-entry error folder" title="<%= LanguageUtil.get(pageContext, errorMessage) %>">

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

					<%
					Map<String, Object> dataView = new HashMap<String, Object>();

					dataView.put("folder-id", parentFolderId);
					dataView.put("repository-id", repositoryId);
					dataView.put("view-folders", Boolean.TRUE);
					%>

					<liferay-portlet:renderURL varImpl="viewURL">
						<portlet:param name="struts_action" value="/journal/view" />
						<portlet:param name="structureId" value="<%= String.valueOf(0) %>" />
						<portlet:param name="entryStart" value="0" />
						<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
						<portlet:param name="folderStart" value="0" />
						<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
					</liferay-portlet:renderURL>

					<liferay-ui:app-view-navigation-entry
						dataView="<%= dataView %>"
						entryTitle='<%= LanguageUtil.get(pageContext, "up") %>'
						iconImage="icon-level-up"
						viewURL="<%= viewURL.toString() %>"
					/>

					<c:if test="<%= total > 0 %>">
						<c:if test="<%= searchContainer.getStart() == 0 %>">

							<%
							dataView = new HashMap<String, Object>();

							dataView.put("browse-by", "file-entry-type");
							dataView.put("file-entry-type-id", 0);
							%>

							<liferay-portlet:renderURL varImpl="viewBasicFileEntryTypeURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="browseBy" value="file-entry-type" />
								<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(0) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<liferay-ui:app-view-navigation-entry
								cssClass="folder file-entry-type"
								dataView="<%= dataView %>"
								entryTitle='<%= LanguageUtil.get(pageContext, "basic-document") %>'
								iconImage="icon-file-alt"
								selected="<%= (fileEntryTypeId == 0) %>"
								viewURL="<%= viewBasicFileEntryTypeURL.toString() %>"
							/>
						</c:if>

						<%
						List<DLFileEntryType> fileEntryTypes = DLFileEntryTypeServiceUtil.getFileEntryTypes(groupIds, searchContainer.getStart(), searchContainer.getEnd());

						for (DLFileEntryType fileEntryType : fileEntryTypes) {
							request.setAttribute("view_folders.jsp-fileEntryType", fileEntryType);

							dataView = new HashMap<String, Object>();

							dataView.put("browse-by", "file-entry-type");
							dataView.put("file-entry-type-id", fileEntryType.getFileEntryTypeId());

							AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());
						%>

							<liferay-portlet:renderURL varImpl="viewFileEntryTypeURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="browseBy" value="file-entry-type" />
								<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<liferay-ui:app-view-navigation-entry
								cssClass="folder file-entry-type"
								dataView="<%= dataView %>"
								entryTitle="<%= fileEntryType.getName(locale) %>"
								iconImage="<%= assetRendererFactory.getIconCssClass() %>"
								selected="<%= (fileEntryTypeId == fileEntryType.getFileEntryTypeId()) %>"
								viewURL="<%= viewFileEntryTypeURL.toString() %>"
							/>

						<%
						}
						%>

					</c:if>
				</c:when>
				<c:otherwise>

					<%
					Map<String, Object> dataView = new HashMap<String, Object>();

					dataView.put("folder-id", parentFolderId);
					dataView.put("repository-id", repositoryId);
					dataView.put("view-folders", Boolean.TRUE);
					%>

					<liferay-portlet:renderURL varImpl="viewURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
						<portlet:param name="entryStart" value="0" />
						<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
						<portlet:param name="folderStart" value="0" />
						<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
					</liferay-portlet:renderURL>

					<liferay-ui:app-view-navigation-entry
						dataView="<%= dataView %>"
						entryTitle='<%= LanguageUtil.get(pageContext, "up") %>'
						iconImage="icon-level-up"
						viewURL="<%= viewURL.toString() %>"
					/>

					<%
					List<Folder> folders = DLAppServiceUtil.getFolders(repositoryId, parentFolderId, false, searchContainer.getStart(), searchContainer.getEnd());

					for (Folder curFolder : folders) {
						request.setAttribute("view_entries.jsp-folder", curFolder);
						request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-folderSelected", String.valueOf(folderId == curFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(curFolder.getRepositoryId()));

						dataView = new HashMap<String, Object>();

						dataView.put("folder-id", curFolder.getFolderId());
						dataView.put("folder", true);
						dataView.put("repository-id", curFolder.getRepositoryId());
						dataView.put("title", curFolder.getName());
					%>

						<liferay-portlet:renderURL varImpl="viewURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:app-view-navigation-entry
							actionJsp="/html/portlet/document_library/folder_action.jsp"
							dataView="<%= dataView %>"
							entryTitle="<%= curFolder.getName() %>"
							iconImage='<%= (DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(curFolder.getRepositoryId(), curFolder.getFolderId(), WorkflowConstants.STATUS_APPROVED, true) > 0) ? "icon-folder-open" : "icon-folder-close" %>'
							selected="<%= (curFolder.getFolderId() == folderId) %>"
							viewURL="<%= viewURL.toString() %>"
						/>

					<%
					}
					%>

				</c:otherwise>
			</c:choose>
		</aui:nav>
	</div>
</div>

<%
request.setAttribute("view_folders.jsp-total", String.valueOf(total));

request.setAttribute("view_folders.jsp-folderEnd", searchContainer.getEnd());
request.setAttribute("view_folders.jsp-folderStart", searchContainer.getStart());
%>

<aui:script>
	Liferay.fire(
		'<portlet:namespace />pageLoaded',
		{
			pagination: {
				name: 'folderPagination',
				state: {
					page: <%= (total == 0) ? 0 : searchContainer.getCur() %>,
					rowsPerPage: <%= searchContainer.getDelta() %>,
					total: <%= total %>
				}
			}
		}
	);
</aui:script>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_library.view_folders_jsp");
%>