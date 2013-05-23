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

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", entriesPerPage);

int folderStart = ParamUtil.getInteger(request, "folderStart");
int folderEnd = ParamUtil.getInteger(request, "folderEnd", SearchContainer.DEFAULT_DELTA);

int total = 0;

if ((folderId != rootFolderId) || expandFolder) {
	total = DLAppServiceUtil.getFoldersCount(repositoryId, parentFolderId, false);
}

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/document_library/view");

SearchContainer searchContainer = new SearchContainer(liferayPortletRequest, null, null, "cur2", folderEnd / (folderEnd - folderStart), (folderEnd - folderStart), portletURL, null, null);

searchContainer.setTotal(total);

List<Folder> folders = DLAppServiceUtil.getFolders(repositoryId, parentFolderId, false, searchContainer.getStart(), searchContainer.getEnd());
List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(scopeGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, searchContainer.getStart(), searchContainer.getEnd());

String parentTitle = StringPool.BLANK;

if ((folderId != rootFolderId) && (parentFolderId > 0) && (folder != null) && (!folder.isMountPoint() || expandFolder)) {
	Folder grandParentFolder = DLAppServiceUtil.getFolder(parentFolderId);

	parentTitle = grandParentFolder.getName();
}
else if (((folderId != rootFolderId) && (parentFolderId == 0)) || ((folderId == rootFolderId) && (parentFolderId == 0) && expandFolder)) {
	parentTitle = LanguageUtil.get(pageContext, "home");
}
%>

<div id="<portlet:namespace />listViewContainer">
	<div id="<portlet:namespace />folderContainer">
		<aui:nav cssClass="nav-list well">
			<c:if test="<%= Validator.isNotNull(parentTitle) %>">
				<li class="nav-header">
					<%= parentTitle %>
				</li>
			</c:if>

			<c:choose>
				<c:when test="<%= ((folderId == rootFolderId) && !expandFolder) || ((folder != null) && (folder.isRoot() && !folder.isDefaultRepository() && !expandFolder)) %>">

					<%
					int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, folderId);
					%>

					<liferay-portlet:renderURL varImpl="viewDocumentsHomeURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
						<portlet:param name="entryStart" value="0" />
						<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
						<portlet:param name="folderStart" value="0" />
						<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
					</liferay-portlet:renderURL>

					<%
					PortletURL expandViewDocumentsHomeURL = PortletURLUtil.clone(viewDocumentsHomeURL, liferayPortletResponse);

					expandViewDocumentsHomeURL.setParameter("expandFolder", Boolean.TRUE.toString());

					String navigation = ParamUtil.getString(request, "navigation", "home");

					long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

					request.setAttribute("view_entries.jsp-folder", folder);
					request.setAttribute("view_entries.jsp-folderId", String.valueOf(folderId));
					request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(repositoryId));

					Map<String, Object> dataExpand = new HashMap<String, Object>();

					dataExpand.put("folder-id", rootFolderId);
					dataExpand.put("expand-folder", Boolean.TRUE);

					Map<String, Object> dataView = new HashMap<String, Object>();

					dataView.put("folder", true);
					dataView.put("folder-id", rootFolderId);
					dataView.put("navigation", "home");
					dataView.put("title", LanguageUtil.get(pageContext, "home"));
					%>

					<liferay-ui:app-view-navigation-entry
						actionJsp="/html/portlet/document_library/folder_action.jsp"
						dataExpand="<%= dataExpand %>"
						dataView="<%= dataView %>"
						entryTitle='<%= LanguageUtil.get(pageContext, "home") %>'
						expandURL="<%= expandViewDocumentsHomeURL.toString() %>"
						iconImage="icon-home"
						selected='<%= (navigation.equals("home") && (folderId == rootFolderId) && (fileEntryTypeId == -1)) %>'
						showExpand="<%= foldersCount > 0 %>"
						viewURL="<%= viewDocumentsHomeURL.toString() %>"
					/>

					<c:if test="<%= rootFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
						<liferay-portlet:renderURL varImpl="viewRecentDocumentsURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="navigation" value="recent" />
							<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<%
						dataView = new HashMap<String, Object>();

						dataView.put("navigation", "recent");
						%>

						<liferay-ui:app-view-navigation-entry
							dataView="<%= dataView %>"
							entryTitle='<%= LanguageUtil.get(pageContext, "recent") %>'
							iconImage="icon-time"
							selected='<%= navigation.equals("recent") %>'
							viewURL="<%= viewRecentDocumentsURL.toString() %>"
						/>

						<c:if test="<%= themeDisplay.isSignedIn() %>">
							<liferay-portlet:renderURL varImpl="viewMyDocumentsURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="navigation" value="mine" />
								<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<%
							dataView = new HashMap<String, Object>();

							dataView.put("navigation", "mine");
							%>

							<liferay-ui:app-view-navigation-entry
								dataView="<%= dataView %>"
								entryTitle='<%= LanguageUtil.get(pageContext, "mine") %>'
								iconImage="icon-user"
								selected='<%= navigation.equals("mine") %>'
								viewURL="<%= viewMyDocumentsURL.toString() %>"
							/>
						</c:if>

						<%
						List<DLFileEntryType> fileEntryTypes = DLFileEntryTypeServiceUtil.getFileEntryTypes(PortalUtil.getSiteAndCompanyGroupIds(themeDisplay));
						%>

						<c:if test="<%= !fileEntryTypes.isEmpty() %>">
							<liferay-portlet:renderURL varImpl="viewBasicFileEntryTypeURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(0) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<%
							dataView = new HashMap<String, Object>();

							dataView.put("file-entry-type-id", 0);
							%>

							<liferay-ui:app-view-navigation-entry
								cssClass="folder file-entry-type"
								dataView="<%= dataView %>"
								entryTitle='<%= LanguageUtil.get(pageContext, "basic-document") %>'
								iconImage="icon-file"
								selected="<%= (fileEntryTypeId == 0) %>"
								viewURL="<%= viewBasicFileEntryTypeURL.toString() %>"
							/>
						</c:if>

						<%
						for (DLFileEntryType fileEntryType : fileEntryTypes) {
							request.setAttribute("view_folders.jsp-fileEntryType", fileEntryType);
						%>

							<liferay-portlet:renderURL varImpl="viewFileEntryTypeURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<%
							dataView = new HashMap<String, Object>();

							dataView.put("file-entry-type-id", fileEntryType.getFileEntryTypeId());
							%>

							<liferay-ui:app-view-navigation-entry
								cssClass="folder file-entry-type"
								dataView="<%= dataView %>"
								entryTitle="<%= HtmlUtil.escape(fileEntryType.getName()) %>"
								iconImage="icon-file"
								selected="<%= (fileEntryTypeId == fileEntryType.getFileEntryTypeId()) %>"
								viewURL="<%= viewFileEntryTypeURL.toString() %>"
							/>

						<%
						}

						for (Folder mountFolder : mountFolders) {
							request.setAttribute("view_entries.jsp-folder", mountFolder);
							request.setAttribute("view_entries.jsp-folderId", String.valueOf(mountFolder.getFolderId()));
							request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(mountFolder.getRepositoryId()));

							try {
								int mountFoldersCount = DLAppServiceUtil.getFoldersCount(mountFolder.getRepositoryId(), mountFolder.getFolderId());
						%>

								<liferay-portlet:renderURL varImpl="viewURL">
									<portlet:param name="struts_action" value="/document_library/view" />
									<portlet:param name="folderId" value="<%= String.valueOf(mountFolder.getFolderId()) %>" />
									<portlet:param name="entryStart" value="0" />
									<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
									<portlet:param name="folderStart" value="0" />
									<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
								</liferay-portlet:renderURL>

								<%
								PortletURL expandViewURL = PortletURLUtil.clone(viewURL, liferayPortletResponse);

								expandViewURL.setParameter("expandFolder", Boolean.TRUE.toString());

								dataExpand = new HashMap<String, Object>();

								dataExpand.put("folder-id", mountFolder.getFolderId());
								dataExpand.put("expand-folder", Boolean.TRUE);

								dataView = new HashMap<String, Object>();

								dataView.put("folder", true);
								dataView.put("folder-id", mountFolder.getFolderId());
								dataView.put("repository-id", mountFolder.getRepositoryId());
								dataView.put("title", mountFolder.getName());
								%>

								<liferay-ui:app-view-navigation-entry
									actionJsp="/html/portlet/document_library/folder_action.jsp"
									dataExpand="<%= dataExpand %>"
									dataView="<%= dataView %>"
									entryTitle="<%= mountFolder.getName() %>"
									expandURL="<%= expandViewURL.toString() %>"
									iconImage="icon-hdd"
									selected="<%= (mountFolder.getFolderId() == folderId) %>"
									showExpand="<%= mountFoldersCount > 0 %>"
									viewURL="<%= viewURL.toString() %>"
								/>

						<%
							}
							catch (Exception e) {
								if (_log.isWarnEnabled()) {
									_log.warn("Unable to access repository", e);
								}
						%>

								<li class="app-view-navigation-entry folder error" title="<%= LanguageUtil.get(pageContext, "an-unexpected-error-occurred-while-connecting-to-the-repository") %>">

									<%
									request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
									%>

									<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />

									<span class="browse-folder">
										<liferay-ui:icon image="drive_error" />

										<span class="entry-title">
											<%= mountFolder.getName() %>
										</span>
									</span>
								</li>

						<%
							}
						}
						%>

					</c:if>
				</c:when>
				<c:otherwise>
					<liferay-portlet:renderURL varImpl="viewURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
						<portlet:param name="entryStart" value="0" />
						<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
						<portlet:param name="folderStart" value="0" />
						<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
					</liferay-portlet:renderURL>

					<%
					PortletURL expandViewURL = PortletURLUtil.clone(viewURL, liferayPortletResponse);

					expandViewURL.setParameter("expandFolder", Boolean.TRUE.toString());

					Map<String, Object> dataExpand = new HashMap<String, Object>();

					dataExpand.put("folder-id", parentFolderId);
					dataExpand.put("direction-right", Boolean.TRUE);

					Map<String, Object> dataView = new HashMap<String, Object>();

					dataView.put("folder-id", parentFolderId);
					dataView.put("repository-id", repositoryId);
					%>

					<liferay-ui:app-view-navigation-entry
						browseUp="<%= true %>"
						dataExpand="<%= dataExpand %>"
						dataView="<%= dataView %>"
						entryTitle='<%= LanguageUtil.get(pageContext, "up") %>'
						expandURL="<%= expandViewURL.toString() %>"
						iconSrc='<%= themeDisplay.getPathThemeImages() + "/arrows/01_up.png" %>'
						showExpand="<%= true %>"
						viewURL="<%= viewURL.toString() %>"
					/>

					<%
					for (Folder curFolder : folders) {
						request.setAttribute("view_entries.jsp-folder", curFolder);
						request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(curFolder.getRepositoryId()));
						request.setAttribute("view_entries.jsp-folderSelected", String.valueOf(folderId == curFolder.getFolderId()));
					%>

						<liferay-portlet:renderURL varImpl="viewURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<%
						expandViewURL = PortletURLUtil.clone(viewURL, liferayPortletResponse);

						expandViewURL.setParameter("expandFolder", Boolean.TRUE.toString());

						dataExpand = new HashMap<String, Object>();

						dataExpand.put("folder-id", curFolder.getFolderId());
						dataExpand.put("expand-folder", Boolean.TRUE);

						dataView = new HashMap<String, Object>();

						dataView.put("folder-id", curFolder.getFolderId());
						dataView.put("folder", true);
						dataView.put("repository-id", curFolder.getRepositoryId());
						dataView.put("title", curFolder.getName());
						%>

						<liferay-ui:app-view-navigation-entry
							actionJsp="/html/portlet/document_library/folder_action.jsp"
							dataExpand="<%= dataExpand %>"
							dataView="<%= dataView %>"
							entryTitle="<%= curFolder.getName() %>"
							expandURL="<%= expandViewURL.toString() %>"
							iconImage='<%= (DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(curFolder.getRepositoryId(), curFolder.getFolderId(), WorkflowConstants.STATUS_APPROVED, true) > 0) ? "icon-folder-open" : "icon-folder-close" %>'
							selected="<%= (curFolder.getFolderId() == folderId) %>"
							showExpand="<%= DLAppServiceUtil.getFoldersCount(repositoryId, curFolder.getFolderId()) > 0 %>"
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