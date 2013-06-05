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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long breadcrumbsFolderId = ParamUtil.getLong(request, "breadcrumbsFolderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId");
long searchFolderIds = ParamUtil.getLong(request, "searchFolderIds");

long[] folderIdsArray = null;

if (searchFolderId > 0) {
	folderIdsArray = new long[] {searchFolderId};
}
else {
	List folderIds = new ArrayList();

	folderIds.add(new Long(searchFolderIds));

	BookmarksFolderServiceUtil.getSubfolderIds(folderIds, scopeGroupId, searchFolderIds);

	folderIdsArray = StringUtil.split(StringUtil.merge(folderIds), 0L);
}

String keywords = ParamUtil.getString(request, "keywords");

if (searchFolderId > 0) {
	BookmarksUtil.addPortletBreadcrumbEntries(searchFolderId, request, renderResponse);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "search") + ": " + keywords, currentURL);
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/bookmarks/search" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= breadcrumbsFolderId %>" />
	<aui:input name="searchFolderId" type="hidden" value="<%= searchFolderId %>" />
	<aui:input name="searchFolderIds" type="hidden" value="<%= searchFolderIds %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="search"
	/>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/bookmarks/search");
	portletURL.setParameter("redirect", redirect);
	portletURL.setParameter("breadcrumbsFolderId", String.valueOf(breadcrumbsFolderId));
	portletURL.setParameter("searchFolderId", String.valueOf(searchFolderId));
	portletURL.setParameter("searchFolderIds", String.valueOf(searchFolderIds));
	portletURL.setParameter("keywords", keywords);
	%>

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(pageContext, "no-entries-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>") %>'
		iteratorURL="<%= portletURL %>"
	>

		<%
		Indexer indexer = BookmarksSearcher.getInstance();

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "more");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setFolderIds(folderIdsArray);
		searchContext.setKeywords(keywords);
		searchContext.setStart(searchContainer.getStart());

		Hits hits = indexer.search(searchContext);
		%>

		<liferay-ui:search-container-results
			results="<%= BookmarksUtil.getEntries(hits) %>"
			total="<%= hits.getLength() %>"
		/>

		<liferay-ui:search-container-row
			className="Object"
			modelVar="obj"
		>

			<c:choose>
				<c:when test="<%= obj instanceof BookmarksEntry %>">

					<%
					BookmarksEntry entry = (BookmarksEntry)obj;

					entry = entry.toEscapedModel();

					BookmarksFolder folder = entry.getFolder();

					String rowHREF = themeDisplay.getPathMain().concat("/bookmarks/open_entry?entryId=").concat(String.valueOf(entry.getEntryId()));
					%>

					<liferay-ui:search-container-column-text
						name="entry"
						title="<%= entry.getDescription() %>"
					>
						<liferay-ui:icon
							image="../ratings/star_hover"
							label="<%= true %>"
							message="<%= entry.getName() %>"
							target="_blank"
							url="<%= rowHREF %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="type"
						target="_blank"
						title="<%= entry.getDescription() %>"
						value='<%= LanguageUtil.get(locale, "entry") %>'
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="folder"
						target="_blank"
						title="<%= entry.getDescription() %>"
						value="<%= folder.getName() %>"
					/>

					<c:if test='<%= ArrayUtil.contains(entryColumns, "action") %>'>
						<liferay-ui:search-container-column-jsp
							path="/html/portlet/bookmarks/entry_action.jsp"
						/>
					</c:if>
				</c:when>
				<c:when test="<%= obj instanceof BookmarksFolder %>">

					<%
					BookmarksFolder folder = (BookmarksFolder)obj;

					BookmarksFolder parentFolder = folder.getParentFolder();
					%>

					<liferay-portlet:renderURL var="rowURL">
						<portlet:param name="struts_action" value="/bookmarks/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</liferay-portlet:renderURL>

					<liferay-ui:search-container-column-text
						name="entry"
						title="<%= folder.getDescription() %>"
					>
						<liferay-ui:icon
							image='<%= (BookmarksFolderLocalServiceUtil.getFoldersAndEntriesCount(folder.getGroupId(), folder.getFolderId(), WorkflowConstants.STATUS_ANY) > 0) ? "folder_full_document" : "folder_empty" %>'
							label="<%= true %>"
							message="<%= folder.getName() %>"
							url="<%= rowURL %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="type"
						title="<%= folder.getDescription() %>"
						value='<%= LanguageUtil.get(locale, "folder") %>'
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="folder"
						title="<%= folder.getDescription() %>"
						value='<%= (parentFolder != null) ? parentFolder.getName() : LanguageUtil.get(locale, "home") %>'
					/>

					<c:if test='<%= ArrayUtil.contains(folderColumns, "action") %>'>
						<liferay-ui:search-container-column-jsp
							path="/html/portlet/bookmarks/folder_action.jsp"
						/>
					</c:if>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<span class="form-search">
			<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" title="search-bookmarks" type="text" value="<%= keywords %>" />

			<aui:button type="submit" value="search" />
		</span>

		<br /><br />

		<liferay-ui:search-iterator type="more" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</aui:script>
</c:if>