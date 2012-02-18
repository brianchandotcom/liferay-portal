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
String redirect = ParamUtil.getString(request, "redirect");

long repositoryId = ParamUtil.getLong(request, "repositoryId");
long folderId = ParamUtil.getLong(request, "folderId");

long breadcrumbsFolderId = ParamUtil.getLong(request, "breadcrumbsFolderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId");
long searchFolderIds = ParamUtil.getLong(request, "searchFolderIds");

long[] folderIdsArray = null;

if (searchFolderId > 0) {
	folderIdsArray = new long[] {searchFolderId};
}
else {
	long dataRepositoryId = DLFolderConstants.getFolderId(scopeGroupId, DLFolderConstants.getDataRepositoryId(scopeGroupId, searchFolderIds));

	List<Long> folderIds = DLAppServiceUtil.getSubfolderIds(scopeGroupId, searchFolderIds);

	folderIds.add(0, dataRepositoryId);

	folderIdsArray = StringUtil.split(StringUtil.merge(folderIds), 0L);
}

String keywords = ParamUtil.getString(request, "keywords");

int mountFoldersCount = DLAppServiceUtil.getMountFoldersCount(scopeGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/document_library_display/search" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= breadcrumbsFolderId %>" />
	<aui:input name="searchFolderId" type="hidden" value="<%= searchFolderId %>" />
	<aui:input name="searchFolderIds" type="hidden" value="<%= searchFolderIds %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="search"
	/>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/document_library_display/search");
	portletURL.setParameter("redirect", redirect);
	portletURL.setParameter("repositoryId", String.valueOf(repositoryId));
	portletURL.setParameter("folderId", String.valueOf(folderId));
	portletURL.setParameter("breadcrumbsFolderId", String.valueOf(breadcrumbsFolderId));
	portletURL.setParameter("searchFolderId", String.valueOf(searchFolderId));
	portletURL.setParameter("searchFolderIds", String.valueOf(searchFolderIds));
	portletURL.setParameter("keywords", keywords);

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("#");
	headerNames.add("folder");
	headerNames.add("document");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(pageContext, "no-documents-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>"));

	try {
		Indexer indexer = IndexerRegistryUtil.getIndexer(DLFileEntryConstants.getClassName());

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "more");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setFolderIds(folderIdsArray);
		searchContext.setKeywords(keywords);
		searchContext.setStart(searchContainer.getStart());

		Hits results = DLAppServiceUtil.search(repositoryId, searchContext);

		int total = results.getLength();

		searchContainer.setTotal(total);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.getDocs().length; i++) {
			Document doc = results.doc(i);

			ResultRow row = new ResultRow(doc, i, i);

			// Position

			row.addText(searchContainer.getStart() + i + 1 + StringPool.PERIOD);

			// Folder and document

			long fileEntryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			FileEntry fileEntry = null;

			try {
				fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Document library search index is stale and contains file entry {" + fileEntryId + "}");
				}

				continue;
			}

			row.setObject(fileEntry);

			Folder folder = fileEntry.getFolder();

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/document_library_display/view_file_entry");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

			String rowHREF = rowURL.toString();

			row.addText(folder.getName(), rowHREF);
			row.addText(fileEntry.getTitle(), rowHREF);

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/document_library/file_entry_action.jsp");

			// Add result row

			resultRows.add(row);
		}
	%>

		<span class="aui-search-bar">
			<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" title="search-documents" type="text" value="<%= keywords %>" />

			<aui:button type="submit" value="search" />
		</span>

		<br /><br />

		<c:if test="<%= (mountFoldersCount > 0) && (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>">

			<%
			List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(scopeGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			StringBundler sb = new StringBundler((6 * mountFoldersCount) - 1);

			for (int i = 0; i < mountFoldersCount; i++) {
				Folder mountFolder = mountFolders.get(i);

				PortletURL searchExternalRepositoryURL = renderResponse.createRenderURL();

				searchExternalRepositoryURL.setParameter("struts_action", "/document_library_display/search");
				searchExternalRepositoryURL.setParameter("redirect", redirect);
				searchExternalRepositoryURL.setParameter("repositoryId", String.valueOf(mountFolder.getRepositoryId()));
				searchExternalRepositoryURL.setParameter("folderId", String.valueOf(mountFolder.getFolderId()));
				searchExternalRepositoryURL.setParameter("breadcrumbsFolderId", String.valueOf(breadcrumbsFolderId));
				searchExternalRepositoryURL.setParameter("searchFolderId", String.valueOf(searchFolderId));
				searchExternalRepositoryURL.setParameter("searchFolderIds", String.valueOf(searchFolderIds));
				searchExternalRepositoryURL.setParameter("keywords", keywords);

				sb.append("<a href=\"");
				sb.append(searchExternalRepositoryURL.toString());
				sb.append("\">");
				sb.append(mountFolder.getName());
				sb.append("</a>");

				if ((i + 1) > mountFoldersCount) {
					sb.append(", ");
				}
			}
			%>

			<span class="portlet-msg-info">
				<liferay-ui:message arguments="<%= sb.toString() %>" key="results-from-the-local-repository-search-in-x" />
			</span>
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" type="more" />

	<%
	}
	catch (Exception e) {
		_log.error(e.getMessage());
	}
	%>

</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</aui:script>
</c:if>

<%
if (searchFolderId > 0) {
	DLUtil.addPortletBreadcrumbEntries(searchFolderId, request, renderResponse);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "search") + ": " + keywords, currentURL);
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_library_display.search_jsp");
%>