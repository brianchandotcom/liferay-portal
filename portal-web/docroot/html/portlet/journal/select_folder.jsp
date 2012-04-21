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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalFolder folder = (JournalFolder)request.getAttribute(WebKeys.JOURNAL_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

String folderName = LanguageUtil.get(pageContext, "home");

if (folder != null) {
	folderName = folder.getName();

	JournalUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}
%>

<aui:form method="post" name="fm">
	<liferay-ui:header
		title="home"
	/>

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/journal/select_folder");
	portletURL.setParameter("folderId", String.valueOf(folderId));

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("folder");
	headerNames.add("num-of-folders");
	headerNames.add("num-of-articles");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

	int total = JournalFolderServiceUtil.getFoldersCount(scopeGroupId, folderId);

	searchContainer.setTotal(total);

	List results = JournalFolderServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		JournalFolder curFolder = (JournalFolder)results.get(i);

		curFolder = curFolder.toEscapedModel();

		ResultRow row = new ResultRow(curFolder, curFolder.getFolderId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/journal/select_folder");
		rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

		// Name

		StringBundler sb = new StringBundler(7);

		sb.append("<img align=\"left\" border=\"0\" src=\"");
		sb.append(themeDisplay.getPathThemeImages());

		int foldersCount = 0;
		int articlesCount = 0;

		List<Long> subfolderIds = JournalFolderServiceUtil.getSubfolderIds(scopeGroupId, curFolder.getFolderId(), false);

		foldersCount = subfolderIds.size();

		subfolderIds.clear();
		subfolderIds.add(curFolder.getFolderId());

		articlesCount = JournalArticleServiceUtil.getFoldersArticlesCount(scopeGroupId, subfolderIds);

		if ((foldersCount + articlesCount) > 0) {
			sb.append("/common/folder_full_document.png\">");
		}
		else {
			sb.append("/common/folder_empty.png\">");
		}

		sb.append(curFolder.getName());

		row.addText(sb.toString(), rowURL);

		// Statistics

		row.addText(String.valueOf(foldersCount), rowURL);
		row.addText(String.valueOf(articlesCount), rowURL);

		// Action

		if (rowURL != null) {
			sb.setIndex(0);

			sb.append("opener.");
			sb.append(renderResponse.getNamespace());
			sb.append("selectFolder('");
			sb.append(curFolder.getFolderId());
			sb.append("', '");
			sb.append(UnicodeFormatter.toString(curFolder.getName()));
			sb.append("'); window.close();");

			row.addButton("right", SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, "choose"), sb.toString());
		}
		else {
			row.addText(StringPool.BLANK);
		}

		// Add result row

		resultRows.add(row);
	}

	boolean showAddFolderButton = JournalFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER);
	%>

	<aui:button-row>
		<c:if test="<%= showAddFolderButton %>">
			<portlet:renderURL var="editFolderURL">
				<portlet:param name="struts_action" value="/journal/edit_folder" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
			</portlet:renderURL>

			<aui:button href="<%= editFolderURL %>" value='<%= (folder == null) ? "add-folder" : "add-subfolder" %>' />
		</c:if>

		<%
		String taglibSelectOnClick = "opener." + renderResponse.getNamespace() + "selectFolder('" + folderId + "','" + folderName + "'); window.close();";
		%>

		<aui:button onClick="<%= taglibSelectOnClick %>" value="choose-this-folder" />
	</aui:button-row>

	<c:if test="<%= !results.isEmpty() %>">
		<br />
	</c:if>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>