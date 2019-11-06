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
long repositoryId = ParamUtil.getLong(request, "repositoryId");

if (repositoryId == 0) {
	repositoryId = scopeGroupId;
}

long searchRepositoryId = ParamUtil.getLong(request, "searchRepositoryId");

if (searchRepositoryId == 0) {
	searchRepositoryId = scopeGroupId;
}

long folderId = ParamUtil.getLong(request, "folderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId");

Folder folder = null;

if (searchFolderId > 0) {
	folder = DLAppServiceUtil.getFolder(searchFolderId);
}

String keywords = ParamUtil.getString(request, "keywords");

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

entriesChecker.setCssClass("entry-selector");
entriesChecker.setRememberCheckBoxStateURLRegex("^(?!.*" + liferayPortletResponse.getNamespace() + "redirect).*(folderId=" + String.valueOf(folderId) + ")");
%>

<c:if test='<%= dlAdminDisplayContext.isSearch() && ParamUtil.getBoolean(request, "showSearchInfo") %>'>
	<liferay-util:include page="/document_library/search_info.jsp" servletContext="<%= application %>" />
</c:if>

<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
<aui:input name="searchRepositoryId" type="hidden" value="<%= searchRepositoryId %>" />

<liferay-util:buffer
	var="searchResults"
>

	<%
	SearchContainer dlSearchContainer = dlAdminDisplayContext.getSearchContainer();
	%>

	<div class="document-container" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			emptyResultsMessage='<%= LanguageUtil.format(request, "no-documents-were-found-that-matched-the-keywords-x", HtmlUtil.escape(keywords), false) %>'
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
					<c:when test="<%= (fileEntry != null) && DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) %>">

						<%
						FileVersion latestFileVersion = fileEntry.getFileVersion();

						if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
							latestFileVersion = fileEntry.getLatestFileVersion();
						}

						if ((dlSearchContainer.getRowChecker() == null) && (DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE))) {
							dlSearchContainer.setRowChecker(entriesChecker);
						}

						String thumbnailSrc = DLURLHelperUtil.getThumbnailSrc(fileEntry, latestFileVersion, themeDisplay);

						row.setPrimaryKey(String.valueOf(fileEntry.getFileEntryId()));
						%>

						<c:choose>
							<c:when test="<%= Validator.isNotNull(thumbnailSrc) %>">
								<liferay-ui:search-container-column-image
									src="<%= thumbnailSrc %>"
									toggleRowChecker="<%= true %>"
								/>
							</c:when>
							<c:when test="<%= Validator.isNotNull(latestFileVersion.getExtension()) %>">
								<liferay-ui:search-container-column-text>
									<liferay-document-library:mime-type-sticker
										fileVersion="<%= fileEntry.getFileVersion() %>"
									/>
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
					<c:when test="<%= (curFolder != null) && DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.VIEW) %>">

						<%
						if ((dlSearchContainer.getRowChecker() == null) && (DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE))) {
							dlSearchContainer.setRowChecker(entriesChecker);
						}

						row.setPrimaryKey(String.valueOf(curFolder.getPrimaryKey()));
						%>

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
					<c:otherwise>
						<liferay-ui:search-container-column-icon
							icon="minus-circle"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="descriptive"
				markupView="lexicon"
				searchContainer="<%= dlSearchContainer %>"
			/>
		</liferay-ui:search-container>
	</div>
</liferay-util:buffer>

<div class="repository-search-results" data-repositoryId="<%= searchRepositoryId %>" id="<%= liferayPortletResponse.getNamespace() + "searchResultsContainer" + searchRepositoryId %>">
	<%= searchResults %>
</div>

<%
request.setAttribute("view.jsp-folderId", String.valueOf(folderId));
%>