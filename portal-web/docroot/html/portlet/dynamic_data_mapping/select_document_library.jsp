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

<%@ include file="/html/portlet/document_library_display/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, FileEntryDisplayTerms.SELECTED_GROUP_ID);

if (groupId == 0) {
	groupId = ParamUtil.getLong(request, "groupId");
}

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long searchFolderIds = ParamUtil.getLong(request, "searchFolderIds");

long[] folderIdsArray = null;

if (folderId > 0) {
	folderIdsArray = new long[] {folderId};

	folder = DLAppServiceUtil.getFolder(folderId);
}
else {
	long defaultFolderId = DLFolderConstants.getFolderId(groupId, DLFolderConstants.getDataRepositoryId(groupId, searchFolderIds));

	List<Long> folderIds = DLAppServiceUtil.getSubfolderIds(groupId, searchFolderIds);

	folderIds.add(0, defaultFolderId);

	folderIdsArray = StringUtil.split(StringUtil.merge(folderIds), 0L);
}

long repositoryId = groupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();

	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);

String keywords = ParamUtil.getString(request, "keywords");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_mapping/select_document_library");
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("folderId", String.valueOf(folderId));
%>

<aui:form method="post" name="fm">

	<%
	FileEntrySearch fileEntrySearchContainer = new FileEntrySearch(renderRequest, portletURL);

	FileEntryDisplayTerms displayTerms = (FileEntryDisplayTerms)fileEntrySearchContainer.getDisplayTerms();

	displayTerms.setSelectedGroupId(groupId);
	%>

	<liferay-ui:search-form
		page="/html/portlet/dynamic_data_mapping/file_entry_search.jsp"
		searchContainer="<%= fileEntrySearchContainer %>"
	/>

	<c:if test="<%= Validator.isNull(displayTerms.getKeywords()) %>">
		<liferay-ui:header
			title="folders"
		/>

		<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

		<div class="helper-clearfix">
			<liferay-ui:icon-menu cssClass="lfr-document-library-add-menu" icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>' message="add">
				<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
					<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_LIBRARY %>" var="addFolderURL">
						<portlet:param name="struts_action" value="/document_library/edit_folder" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
						<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
					</liferay-portlet:renderURL>

					<liferay-ui:icon image="folder" message='<%= (folder != null) ? "subfolder" : "folder" %>' url="<%= addFolderURL %>" />
				</c:if>

				<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT) %>">

					<%
					List<DLFileEntryType> fileEntryTypes = Collections.emptyList();

					if ((folder == null) || folder.isSupportsMetadata()) {
						fileEntryTypes = DLFileEntryTypeLocalServiceUtil.getFolderFileEntryTypes(PortalUtil.getSiteAndCompanyGroupIds(themeDisplay), folderId, true);
					}
					%>

					<c:if test="<%= fileEntryTypes.isEmpty() %>">
						<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_LIBRARY %>" var="editFileEntryURL">
							<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="backURL" value="<%= currentURL %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:icon image="copy" message="basic-document" url="<%= editFileEntryURL %>" />
					</c:if>

					<%
					for (DLFileEntryType fileEntryType : fileEntryTypes) {
					%>

						<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_LIBRARY %>" var="addFileEntryTypeURL">
							<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:icon image="copy" message="<%= HtmlUtil.escape(fileEntryType.getName()) %>" url="<%= addFileEntryTypeURL %>" />

					<%
					}
					%>

				</c:if>
			</liferay-ui:icon-menu>
		</div>

		<%
		List<String> headerNames = new ArrayList<String>();

		headerNames.add("folder");
		headerNames.add("num-of-folders");
		headerNames.add("num-of-documents");

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-folders");

		int total = DLAppServiceUtil.getFoldersCount(groupId, folderId);

		searchContainer.setTotal(total);

		List results = DLAppServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Folder curFolder = (Folder)results.get(i);

			ResultRow row = new ResultRow(curFolder, curFolder.getFolderId(), i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/dynamic_data_mapping/select_document_library");
			rowURL.setParameter("groupId", String.valueOf(groupId));
			rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

			// Name

			StringBundler sb = new StringBundler(4);

			sb.append("<img align=\"left\" border=\"0\" src=\"");
			sb.append(themeDisplay.getPathThemeImages());
			sb.append("/common/folder.png\">");
			sb.append(curFolder.getName());

			row.addText(sb.toString(), rowURL);

			// Statistics

			List<Long> subfolderIds = DLAppServiceUtil.getSubfolderIds(curFolder.getRepositoryId(), curFolder.getFolderId(), false);

			int foldersCount = subfolderIds.size();

			subfolderIds.clear();
			subfolderIds.add(curFolder.getFolderId());

			int fileEntriesCount = DLAppServiceUtil.getFoldersFileEntriesCount(curFolder.getRepositoryId(), subfolderIds, WorkflowConstants.STATUS_APPROVED);

			row.addText(String.valueOf(foldersCount), rowURL);
			row.addText(String.valueOf(fileEntriesCount), rowURL);

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<br />
	</c:if>

	<c:choose>
		<c:when test="<%= Validator.isNull(displayTerms.getKeywords()) %>">
			<liferay-ui:header
				title="documents"
			/>
		</c:when>
		<c:otherwise>

			<%
			PortletURL backURL = renderResponse.createRenderURL();

			backURL.setParameter("struts_action", "/dynamic_data_mapping/select_document_library");
			backURL.setParameter("groupId", String.valueOf(groupId));
			%>

			<liferay-ui:header
				backURL="<%= backURL.toString() %>"
				title="documents"
			/>
		</c:otherwise>
	</c:choose>

	<%
	List<String> headerNames = new ArrayList<String>();

	headerNames.add("document");
	headerNames.add("size");

	if (PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED) {
		headerNames.add("downloads");
	}

	headerNames.add("locked");
	headerNames.add(StringPool.BLANK);

	if (Validator.isNull(displayTerms.getKeywords())) {
		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-documents-in-this-folder");

		int total = DLAppServiceUtil.getFileEntriesCount(groupId, folderId);

		searchContainer.setTotal(total);

		List results = DLAppServiceUtil.getFileEntries(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();
	%>

		<%@ include file="/html/portlet/dynamic_data_mapping/select_document_library_search_results.jspf" %>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<%
	}
	else {
		PortletURL iteratorURL = renderResponse.createRenderURL();

		iteratorURL.setParameter("struts_action", "/dynamic_data_mapping/select_document_library");
		iteratorURL.setParameter("groupId", String.valueOf(groupId));
		iteratorURL.setParameter("folderId", String.valueOf(folderId));

		fileEntrySearchContainer = new FileEntrySearch(renderRequest, displayTerms, (FileEntrySearchTerms)fileEntrySearchContainer.getSearchTerms(), "cur2", SearchContainer.DEFAULT_DELTA, iteratorURL, headerNames, "there-are-no-documents-in-this-folder");

		try {
			SearchContext searchContext = SearchContextFactory.getInstance(request);

			searchContext.setAttribute("groupId", groupId);
			searchContext.setAttribute("paginationType", "regular");
			searchContext.setEnd(entryEnd);
			searchContext.setFolderIds(folderIdsArray);
			searchContext.setGroupIds(new long[] {groupId});
			searchContext.setKeywords(keywords);
			searchContext.setStart(entryStart);

			searchContext.setScopeStrict(false);

			Hits hits = DLAppServiceUtil.search(repositoryId, searchContext);

			fileEntrySearchContainer.setTotal(hits.getLength());

			List results = new ArrayList();

			Document[] docs = hits.getDocs();

			if (docs != null) {
				for (Document doc : docs) {
					long fileEntryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

					FileEntry fileEntry = null;

					try {
						fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn("Documents and Media search index is stale and contains file entry {" + fileEntryId + "}");
						}

						continue;
					}

					results.add(fileEntry);
				}

				if (docs.length == 0) {
					request.removeAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);
				}
			}

			fileEntrySearchContainer.setResults(results);

			List resultRows = fileEntrySearchContainer.getResultRows();
	%>

			<%@ include file="/html/portlet/dynamic_data_mapping/select_document_library_search_results.jspf" %>

	<%
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	%>

		<liferay-ui:search-iterator searchContainer="<%= fileEntrySearchContainer %>" />

	<%
	}
	%>

</aui:form>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.dynamic_data_mapping.select_document_library_jsp");
%>