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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long repositoryId = scopeGroupId;
String folderName = LanguageUtil.get(pageContext, "home");

if (folder != null) {
	repositoryId = folder.getRepositoryId();
	folderName = folder.getName();

	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}
%>

<aui:form method="post" name="fm">
	<liferay-ui:header
		title="home"
	/>

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/document_library/select_folder");
	portletURL.setParameter("folderId", String.valueOf(folderId));

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("folder");
	headerNames.add("num-of-folders");
	headerNames.add("num-of-documents");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

	int total = DLAppServiceUtil.getFoldersCount(repositoryId, folderId);

	searchContainer.setTotal(total);

	List results = DLAppServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Folder curFolder = (Folder)results.get(i);

		curFolder = curFolder.toEscapedModel();

		ResultRow row = new ResultRow(curFolder, curFolder.getFolderId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/document_library/select_folder");
		rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

		// Name

		StringBundler sb = new StringBundler(7);

		sb.append("<img align=\"left\" border=\"0\" src=\"");
		sb.append(themeDisplay.getPathThemeImages());

		int foldersCount = 0;
		int fileEntriesCount = 0;

		try {
			List<Long> subfolderIds = DLAppServiceUtil.getSubfolderIds(curFolder.getRepositoryId(), curFolder.getFolderId(), false);

			foldersCount = subfolderIds.size();

			subfolderIds.clear();
			subfolderIds.add(curFolder.getFolderId());

			fileEntriesCount = DLAppServiceUtil.getFoldersFileEntriesCount(curFolder.getRepositoryId(), subfolderIds, WorkflowConstants.STATUS_APPROVED);
		}
		catch (com.liferay.portal.kernel.repository.RepositoryException re) {
			rowURL = null;
		}
		catch (com.liferay.portal.security.auth.PrincipalException pe) {
			rowURL = null;
		}

		if (curFolder.isMountPoint()) {
			if (rowURL != null) {
				sb.append("/common/drive.png\">");
			}
			else {
				sb.append("/common/drive_error.png\">");
			}
		}
		else {
			if ((foldersCount + fileEntriesCount) > 0) {
				sb.append("/common/folder_full_document.png\">");
			}
			else {
				sb.append("/common/folder_empty.png\">");
			}
		}

		sb.append(curFolder.getName());

		row.addText(sb.toString(), rowURL);

		// Statistics

		row.addText(String.valueOf(foldersCount), rowURL);
		row.addText(String.valueOf(fileEntriesCount), rowURL);

		// Action

		if (rowURL != null) {
			sb.setIndex(0);

			sb.append("opener.");
			sb.append(renderResponse.getNamespace());
			sb.append("selectFolder('");
			sb.append(curFolder.getFolderId());
			sb.append("', '");
			sb.append(UnicodeFormatter.toString(curFolder.getName()));
			sb.append("', ");
			sb.append(curFolder.isSupportsMetadata());
			sb.append(", ");
			sb.append(curFolder.isSupportsSocial());
			sb.append("); window.close();");

			row.addButton("right", SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, "choose"), sb.toString());
		}
		else {
			row.addText(StringPool.BLANK);
		}

		// Add result row

		resultRows.add(row);
	}

	showAddFolderButton = showAddFolderButton && DLFolderPermission.contains(permissionChecker, repositoryId, folderId, ActionKeys.ADD_FOLDER);
	%>

	<aui:button-row>
		<c:if test="<%= showAddFolderButton %>">
			<portlet:renderURL var="editFolderURL">
				<portlet:param name="struts_action" value="/document_library/edit_folder" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
				<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
			</portlet:renderURL>

			<aui:button href="<%= editFolderURL %>" value='<%= (folder == null) ? "add-folder" : "add-subfolder" %>' />
		</c:if>

		<%
		String taglibSelectOnClick = "opener." + renderResponse.getNamespace() + "selectFolder('" + folderId + "','" + folderName + "','" + ((folder != null) ? folder.isSupportsMetadata() : Boolean.TRUE.toString()) + "','" + ((folder != null) ? folder.isSupportsSocial() : Boolean.TRUE.toString()) + "'); window.close();";
		%>

		<aui:button onClick="<%= taglibSelectOnClick %>" value="choose-this-folder" />
	</aui:button-row>

	<c:if test="<%= !results.isEmpty() %>">
		<br />
	</c:if>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>