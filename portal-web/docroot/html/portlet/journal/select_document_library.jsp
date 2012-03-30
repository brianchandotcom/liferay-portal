<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long searchFolderIds = ParamUtil.getLong(request, "searchFolderIds");

long[] folderIdsArray = null;

long groupId = ParamUtil.getLong(request, FileEntryDisplayTerms.SELECTED_GROUP_ID);

if (groupId == 0) {
	groupId = ParamUtil.getLong(request, "groupId");
}

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

String keywords = ParamUtil.getString(request, "keywords");

long repositoryId = groupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();
}

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/select_document_library");
portletURL.setParameter("folderId", String.valueOf(folderId));
portletURL.setParameter("groupId", String.valueOf(groupId));

PortletURL backURL = renderResponse.createRenderURL();
backURL.setParameter("struts_action", "/journal/select_document_library");
backURL.setParameter("groupId", String.valueOf(groupId));

if (folder != null) {
	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}

%>

<aui:form method="post" name="fm">

	<%
		FileEntrySearch fileEntrySearchContainer = new FileEntrySearch(renderRequest, portletURL);

		FileEntryDisplayTerms displayTerms = (FileEntryDisplayTerms)fileEntrySearchContainer.getDisplayTerms();
		FileEntrySearchTerms searchTerms = (FileEntrySearchTerms)fileEntrySearchContainer.getSearchTerms();
		displayTerms.setSelectedGroupId(groupId);

		boolean isSearch = Validator.isNotNull(displayTerms.getKeywords());

		List<String> headerNames = new ArrayList<String>();
		int total = 0;
		List results = null;
		List resultRows = null;
	%>

	<liferay-ui:search-form
		page="/html/portlet/journal/file_entry_search.jsp"
		searchContainer="<%= fileEntrySearchContainer %>"
	/>

	<% if (!isSearch) { %>
		<liferay-ui:header
			title="folders"
		/>

		<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

		<%
		headerNames.add("folder");
		headerNames.add("num-of-folders");
		headerNames.add("num-of-documents");

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-folders");

		total = DLAppServiceUtil.getFoldersCount(groupId, folderId);

		searchContainer.setTotal(total);

		results = DLAppServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Folder curFolder = (Folder)results.get(i);

			ResultRow row = new ResultRow(curFolder, curFolder.getFolderId(), i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/journal/select_document_library");
			rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
			rowURL.setParameter("groupId", String.valueOf(groupId));

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
	<%
	}
	%>

	<%if (!isSearch) { %>
		<liferay-ui:header
			title="documents"
		/>
	<%
	}
	else {
	%>
	<liferay-ui:header
		backURL="<%= backURL.toString() %>"
		title="documents"
	/>

	<%
	}

	headerNames.clear();

	headerNames.add("document");
	headerNames.add("size");

	if (PropsValues.DL_FILE_ENTRY_READ_COUNT_ENABLED) {
		headerNames.add("downloads");
	}

	headerNames.add("locked");
	headerNames.add(StringPool.BLANK);

	if (!isSearch) {
		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-documents-in-this-folder");

		total = DLAppServiceUtil.getFileEntriesCount(groupId, folderId);

		searchContainer.setTotal(total);

		results = DLAppServiceUtil.getFileEntries(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			FileEntry fileEntry = (FileEntry)results.get(i);

			ResultRow row = new ResultRow(fileEntry, fileEntry.getFileEntryId(), i);

			String rowHREF = DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, true);

			// Title

			StringBundler sb = new StringBundler(10);

			sb.append("<img alt=\"\" align=\"left\" border=\"0\" src=\"");

			DLFileShortcut fileShortcut = null;

			String thumbnailSrc = DLUtil.getThumbnailSrc(fileEntry, fileShortcut, themeDisplay);

			sb.append(thumbnailSrc);
			sb.append("\" style=\"");

			String thumbnailStyle = DLUtil.getThumbnailStyle();

			sb.append(thumbnailStyle);
			sb.append("\">");
			sb.append(fileEntry.getTitle());

			row.addText(sb.toString(), rowHREF);

			// Statistics

			row.addText(TextFormatter.formatKB(fileEntry.getSize(), locale) + "k", rowHREF);

			if (PropsValues.DL_FILE_ENTRY_READ_COUNT_ENABLED) {
				row.addText(String.valueOf(fileEntry.getReadCount()), rowHREF);
			}

			// Locked

			boolean isCheckedOut = fileEntry.isCheckedOut();

			row.addText(LanguageUtil.get(pageContext, isCheckedOut ? "yes" : "no"), rowHREF);

			// Action

			sb.setIndex(0);

			sb.append("Liferay.Util.getOpener().");
			sb.append(renderResponse.getNamespace());
			sb.append("selectDocumentLibrary('");
			sb.append(themeDisplay.getPathContext());
			sb.append(DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, false));
			sb.append("', '");
			sb.append(fileEntry.getUuid());
			sb.append("', '");
			sb.append(fileEntry.getTitle());
			sb.append("', '");
			sb.append(fileEntry.getVersion());
			sb.append("'); Liferay.Util.getWindow().close();");

			row.addButton("right", SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, "choose"), sb.toString());

			// Add result row

			resultRows.add(row);
		}
		%>
		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		<%
	}
	else {

		portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/journal/select_document_library");
		portletURL.setParameter("folderId", String.valueOf(folderId));
		portletURL.setParameter("groupId", String.valueOf(groupId));

		fileEntrySearchContainer = new FileEntrySearch(renderRequest, displayTerms, searchTerms, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-documents-in-this-folder");

		try {

			SearchContext searchContext = SearchContextFactory.getInstance(request);

			searchContext.setAttribute("groupId",groupId);
			searchContext.setAttribute("paginationType", "regular");
			searchContext.setEnd(entryEnd);
			searchContext.setFolderIds(folderIdsArray);
			searchContext.setGroupIds(new long[] {groupId});
			searchContext.setKeywords(keywords);
			searchContext.setStart(entryStart);

			searchContext.setScopeStrict(false);

			Hits hits = DLAppServiceUtil.search(repositoryId, searchContext);

			results = new ArrayList();

			fileEntrySearchContainer.getResultRows().clear();

			resultRows = fileEntrySearchContainer.getResultRows();

			Document[] docs = hits.getDocs();

			if (docs != null) {
				for (Document doc : docs) {

					// Folder and document

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

			total = hits.getLength();

			fileEntrySearchContainer.setResults(results);
			fileEntrySearchContainer.setTotal(total);

			for (int i = 0; i < results.size(); i++) {
				FileEntry fileEntry = (FileEntry)results.get(i);

				ResultRow row = new ResultRow(fileEntry, fileEntry.getFileEntryId(), i);

				String rowHREF = DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, true);

				// Title

				StringBundler sb = new StringBundler(10);

				sb.append("<img alt=\"\" align=\"left\" border=\"0\" src=\"");

				DLFileShortcut fileShortcut = null;

				String thumbnailSrc = DLUtil.getThumbnailSrc(fileEntry, fileShortcut, themeDisplay);

				sb.append(thumbnailSrc);
				sb.append("\" style=\"");

				String thumbnailStyle = DLUtil.getThumbnailStyle();

				sb.append(thumbnailStyle);
				sb.append("\">");
				sb.append(fileEntry.getTitle());

				row.addText(sb.toString(), rowHREF);

				// Statistics

				row.addText(TextFormatter.formatKB(fileEntry.getSize(), locale) + "k", rowHREF);

				if (PropsValues.DL_FILE_ENTRY_READ_COUNT_ENABLED) {
					row.addText(String.valueOf(fileEntry.getReadCount()), rowHREF);
				}

				// Locked

				boolean isCheckedOut = fileEntry.isCheckedOut();

				row.addText(LanguageUtil.get(pageContext, isCheckedOut ? "yes" : "no"), rowHREF);

				// Action

				sb.setIndex(0);

				sb.append("Liferay.Util.getOpener().");
				sb.append(renderResponse.getNamespace());
				sb.append("selectDocumentLibrary('");
				sb.append(themeDisplay.getPathContext());
				sb.append(DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, false));
				sb.append("', '");
				sb.append(fileEntry.getUuid());
				sb.append("', '");
				sb.append(fileEntry.getTitle());
				sb.append("', '");
				sb.append(fileEntry.getVersion());
				sb.append("'); Liferay.Util.getWindow().close();");

				row.addButton("right", SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, "choose"), sb.toString());

				// Add result row

				resultRows.add(row);
			}
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
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.journal.select_document_library_jsp");
%>