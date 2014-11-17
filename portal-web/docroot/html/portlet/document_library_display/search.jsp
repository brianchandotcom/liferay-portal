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

DLActionsDisplayContext dlActionsDisplayContext = new DLActionsDisplayContext(request, dlPortletInstanceSettings);
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

	<div class="form-search">
		<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" placeholder='<%= LanguageUtil.get(locale, "keywords") %>' title='<%= LanguageUtil.get(locale, "search-documents") %>' />
	</div>

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
			sb.append(HtmlUtil.escape(mountFolder.getName()));
			sb.append("</a>");

			if ((i + 1) < mountFoldersCount) {
				sb.append(", ");
			}
		}
		%>

		<span class="alert alert-info">
			<liferay-ui:message arguments="<%= sb.toString() %>" key="results-from-the-local-repository-search-in-x" translateArguments="<%= false %>" />
		</span>
	</c:if>

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
	%>

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(request, "no-documents-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>", false) %>'
		iteratorURL="<%= portletURL %>"
	>

		<%
		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "more");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setFolderIds(folderIdsArray);
		searchContext.setIncludeDiscussions(true);
		searchContext.setKeywords(keywords);
		searchContext.setStart(searchContainer.getStart());

		Hits hits = DLAppServiceUtil.search(repositoryId, searchContext);

		searchContainer.setTotal(hits.getLength());

		PortletURL hitURL = renderResponse.createRenderURL();
		%>

		<liferay-ui:search-container-results
			results="<%= SearchResultUtil.getSearchResults(hits, locale, hitURL) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.search.SearchResult"
			modelVar="searchResult"
		>

			<%
			Summary summary = searchResult.getSummary();

			FileEntry fileEntry = null;
			Folder folder = null;

			String className = searchResult.getClassName();

			if (className.equals(DLFileEntry.class.getName()) || FileEntry.class.isAssignableFrom(Class.forName(className))) {
				fileEntry = DLAppLocalServiceUtil.getFileEntry(searchResult.getClassPK());
			}
			else if (searchResult.getClassName().equals(DLFolder.class.getName())) {
				folder = DLAppLocalServiceUtil.getFolder(searchResult.getClassPK());
			}

			request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
			%>

			<c:choose>
				<c:when test="<%= fileEntry != null %>">

					<%
					request.setAttribute("view_file_entry.jsp-fileEntry", fileEntry);
					%>

					<portlet:renderURL var="rowURL">
						<portlet:param name="struts_action" value="/document_library_display/view_file_entry" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
					</portlet:renderURL>

					<liferay-ui:app-view-search-entry
						actionJsp='<%= (dlActionsDisplayContext.isShowActions()) ? "/html/portlet/document_library/file_entry_action.jsp" : StringPool.BLANK %>'
						containerName="<%= DLUtil.getAbsolutePath(renderRequest, fileEntry.getFolderId()) %>"
						cssClass='<%= MathUtil.isEven(index) ? "search" : "search alt" %>'
						description="<%= (summary != null) ? summary.getContent() : fileEntry.getDescription() %>"
						mbMessages="<%= searchResult.getMBMessages() %>"
						queryTerms="<%= hits.getQueryTerms() %>"
						thumbnailSrc="<%= DLUtil.getThumbnailSrc(fileEntry, themeDisplay) %>"
						title="<%= (summary != null) ? summary.getTitle() : fileEntry.getTitle() %>"
						url="<%= rowURL %>"
					/>
				</c:when>
				<c:when test="<%= folder != null %>">

					<%
					String folderImage = "folder_empty_document";

					if (DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(folder.getRepositoryId(), folder.getFolderId(), WorkflowConstants.STATUS_APPROVED, true) > 0) {
						folderImage = "folder_full_document";
					}

					request.setAttribute("view.jsp-folder", folder);
					request.setAttribute("view.jsp-folderId", String.valueOf(folder.getFolderId()));
					request.setAttribute("view.jsp-repositoryId", String.valueOf(folder.getRepositoryId()));
					%>

					<portlet:renderURL var="rowURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
					</portlet:renderURL>

					<liferay-ui:app-view-search-entry
						actionJsp='<%= (dlActionsDisplayContext.isShowActions()) ? "/html/portlet/document_library/folder_action.jsp" : StringPool.BLANK %>'
						containerName="<%= DLUtil.getAbsolutePath(renderRequest, folder.getParentFolderId()) %>"
						cssClass='<%= MathUtil.isEven(index) ? "search" : "search alt" %>'
						description="<%= (summary != null) ? summary.getContent() : folder.getDescription() %>"
						queryTerms="<%= hits.getQueryTerms() %>"
						thumbnailSrc='<%= themeDisplay.getPathThemeImages() + "/file_system/large/" + folderImage + ".png" %>'
						title="<%= (summary != null) ? summary.getTitle() : folder.getName() %>"
						url="<%= rowURL %>"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="more" />
	</liferay-ui:search-container>
</aui:form>

<%
if (searchFolderId > 0) {
	DLUtil.addPortletBreadcrumbEntries(searchFolderId, request, renderResponse);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "search") + ": " + keywords, currentURL);
%>