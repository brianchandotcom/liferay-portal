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
	groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);

	Group group = GroupLocalServiceUtil.fetchGroup(groupId);

	if ((group != null) && group.isStaged()) {
		Group liveGroup = group.getLiveGroup();

		if ((liveGroup != null) && !liveGroup.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY)) {
			groupId = liveGroup.getGroupId();
		}
	}
}

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if ((folder != null) && (folder.getGroupId() != groupId)) {
	folder = null;

	folderId = 0;
}

long searchFolderIds = ParamUtil.getLong(request, "searchFolderIds");

long[] folderIdsArray = null;

if (folderId > 0) {
	folderIdsArray = new long[] {folderId};

	folder = DLAppServiceUtil.getFolder(folderId);
}
else {
	long defaultFolderId = DLFolderConstants.getFolderId(groupId, DLFolderConstants.getDataRepositoryId(groupId, searchFolderIds));

	List<Folder> folders = DLAppServiceUtil.getFolders(groupId, searchFolderIds);

	List<Long> folderIds = new ArrayList<Long>(folders.size() + 1);

	folderIds.add(defaultFolderId);

	for (Folder subFolder : folders) {
		folderIds.add(subFolder.getFolderId());
	}

	folderIdsArray = StringUtil.split(StringUtil.merge(folderIds), 0L);
}

long repositoryId = groupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();

	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}

String keywords = ParamUtil.getString(request, "keywords");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_mapping/select_document_library");
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("folderId", String.valueOf(folderId));
%>

<aui:form action="<%= portletURL %>" method="post" name="fm">

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

		<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

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

						<liferay-ui:icon image="copy" message="<%= HtmlUtil.escape(fileEntryType.getName(locale)) %>" url="<%= addFileEntryTypeURL %>" />

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

			StringBundler sb = new StringBundler(6);

			sb.append("<img align=\"left\" alt=\"");
			sb.append(LanguageUtil.get(locale, "folder"));
			sb.append("\" border=\"0\" src=\"");
			sb.append(HtmlUtil.escapeAttribute(themeDisplay.getPathThemeImages()));
			sb.append("/common/folder.png\">");
			sb.append(HtmlUtil.escape(curFolder.getName()));

			row.addText(sb.toString(), rowURL);

			// Statistics

			int foldersCount = DLAppServiceUtil.getFoldersCount(curFolder.getRepositoryId(), curFolder.getFolderId());

			int fileEntriesCount = DLAppServiceUtil.getFoldersFileEntriesCount(curFolder.getRepositoryId(), Arrays.asList(curFolder.getFolderId()), WorkflowConstants.STATUS_APPROVED);

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
		fileEntrySearchContainer = new FileEntrySearch(renderRequest, displayTerms, (FileEntrySearchTerms)fileEntrySearchContainer.getSearchTerms(), "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-documents-in-this-folder");

		try {
			SearchContext searchContext = SearchContextFactory.getInstance(request);

			searchContext.setAttribute("groupId", groupId);
			searchContext.setAttribute("paginationType", "regular");
			searchContext.setEnd(fileEntrySearchContainer.getEnd());
			searchContext.setFolderIds(folderIdsArray);
			searchContext.setGroupIds(new long[] {groupId});
			searchContext.setKeywords(keywords);

			QueryConfig queryConfig = new QueryConfig();

			queryConfig.setHighlightEnabled(true);
			queryConfig.setSearchSubfolders(true);

			searchContext.setQueryConfig(queryConfig);

			searchContext.setStart(fileEntrySearchContainer.getStart());

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