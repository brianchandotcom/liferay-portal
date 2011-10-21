<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

long groupId = ParamUtil.getLong(request, "groupId");

long repositoryId = groupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/select_document_library");
portletURL.setParameter("folderId", String.valueOf(folderId));
portletURL.setParameter("groupId", String.valueOf(groupId));

if (folder != null) {
	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse, true);
}
%>

<aui:form method="post" name="fm">
	<liferay-ui:header
		title="folders"
	/>

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

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

	<liferay-ui:header
		title="documents"
	/>

	<%
	headerNames.clear();

	headerNames.add("document");
	headerNames.add("size");

	if (PropsValues.DL_FILE_ENTRY_READ_COUNT_ENABLED) {
		headerNames.add("downloads");
	}

	headerNames.add("locked");
	headerNames.add(StringPool.BLANK);

	searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-documents-in-this-folder");

	total = DLAppServiceUtil.getFileEntriesCount(groupId, folderId);

	searchContainer.setTotal(total);

	results = DLAppServiceUtil.getFileEntries(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		FileEntry fileEntry = (FileEntry)results.get(i);

		FileVersion fileVersion = fileEntry.getFileVersion();

		DLFileShortcut fileShortcut = null;
	%>

		<%@ include file="/html/portlet/document_library/document_thumbnail.jspf" %>

	<%
		ResultRow row = new ResultRow(fileEntry, fileEntry.getFileEntryId(), i);

		String rowHREF = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + folderId + StringPool.SLASH + HttpUtil.encodeURL(fileEntry.getTitle());

		// Title

		StringBundler sb = new StringBundler(10);

		sb.append("<img alt=\"\" align=\"left\" border=\"0\" src=\"");
		sb.append(thumbnailSrc);
		sb.append("\" style=\"");
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

		sb.append("parent.");
		sb.append(renderResponse.getNamespace());
		sb.append("selectDocumentLibrary('");
		sb.append(themeDisplay.getPathContext());
		sb.append("/documents/");
		sb.append(groupId);
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getFolderId());
		sb.append(StringPool.SLASH);
		sb.append(HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())));

		Set<String> imageMimeTypes = ImageProcessor.getImageMimeTypes();

		if (imageMimeTypes.contains(fileEntry.getMimeType())) {
			sb.append("?t=");
			sb.append(WebServerServletTokenUtil.getToken(fileEntry.getFileEntryId()));
		}

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
</aui:form>