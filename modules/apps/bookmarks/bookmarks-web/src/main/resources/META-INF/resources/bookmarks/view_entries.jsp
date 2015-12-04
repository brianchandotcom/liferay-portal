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

<%@ include file="/bookmarks/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

boolean useAssetEntryQuery = GetterUtil.getBoolean((String)request.getAttribute("view.jsp-useAssetEntryQuery"));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/bookmarks/view");
portletURL.setParameter("navigation", navigation);
portletURL.setParameter("folderId", String.valueOf(folderId));

SearchContainer bookmarksSearchContainer = new SearchContainer(liferayPortletRequest, null, null, "curEntry", SearchContainer.DEFAULT_DELTA, portletURL, null, "there-are-no-bookmarks-in-this-folder");

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

bookmarksSearchContainer.setRowChecker(entriesChecker);

entriesChecker.setCssClass("entry-selector");

String displayStyle = GetterUtil.getString((String)request.getAttribute("view.jsp-displayStyle"));

List results = null;
int total = 0;

if (navigation.equals("mine") || navigation.equals("recent")) {
	long groupEntriesUserId = 0;

	if (navigation.equals("mine") && themeDisplay.isSignedIn()) {
		groupEntriesUserId = user.getUserId();
	}

	results = BookmarksEntryServiceUtil.getGroupEntries(scopeGroupId, groupEntriesUserId, bookmarksSearchContainer.getStart(), bookmarksSearchContainer.getEnd());
	total = BookmarksEntryServiceUtil.getGroupEntriesCount(scopeGroupId, groupEntriesUserId);
}
else {
	if (useAssetEntryQuery) {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery(BookmarksEntry.class.getName(), bookmarksSearchContainer);

		assetEntryQuery.setEnablePermissions(true);
		assetEntryQuery.setExcludeZeroViewCount(false);
		assetEntryQuery.setEnd(bookmarksSearchContainer.getEnd());
		assetEntryQuery.setStart(bookmarksSearchContainer.getStart());

		results = AssetEntryServiceUtil.getEntries(assetEntryQuery);
	}
	else {
		results = BookmarksFolderServiceUtil.getFoldersAndEntries(scopeGroupId, folderId, WorkflowConstants.STATUS_APPROVED, bookmarksSearchContainer.getStart(), bookmarksSearchContainer.getEnd());
		total = BookmarksFolderServiceUtil.getFoldersAndEntriesCount(scopeGroupId, folderId);
	}
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation && (folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (folderId != rootFolderId)) {
	String redirect = ParamUtil.getString(request, "redirect");

	if (Validator.isNotNull(redirect)) {
		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(redirect);
	}

	BookmarksFolder folder = BookmarksFolderServiceUtil.getFolder(folderId);

	renderResponse.setTitle(folder.getName());
}

String searchContainerId = ParamUtil.getString(request, "searchContainerId");
%>

<liferay-ui:search-container
	id="<%= searchContainerId %>"
	searchContainer="<%= bookmarksSearchContainer %>"
	total="<%= total %>"
	totalVar="bookmarksSearchContainerTotal"
>
	<liferay-ui:search-container-results
		results="<%= results %>"
		resultsVar="bookmarksSearchContainerResults"
	/>

	<liferay-ui:search-container-row
		className="Object"
		modelVar="result"
	>
		<%@ include file="/bookmarks/cast_result.jspf" %>

		<c:choose>
			<c:when test="<%= curFolder != null %>">

				<%
				row.setPrimaryKey(String.valueOf(curFolder.getFolderId()));
				%>

				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/bookmarks/view" />
					<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</liferay-portlet:renderURL>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-icon
							icon="folder"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="2"
							path="/bookmarks/view_folder_descriptive.jsp"
						/>

						<liferay-ui:search-container-column-jsp
							path="/bookmarks/folder_action.jsp"
						/>
					</c:when>
					<c:otherwise>
						<%@ include file="/bookmarks/folder_columns.jspf" %>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>

				<%
				row.setPrimaryKey(String.valueOf(entry.getEntryId()));
				%>

				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/bookmarks/view_entry" />
					<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</liferay-portlet:renderURL>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-icon
							icon="link"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="2"
							path="/bookmarks/view_entry_descriptive.jsp"
							/>

						<liferay-ui:search-container-column-jsp
							path="/bookmarks/entry_action.jsp"
						/>
					</c:when>
					<c:otherwise>
						<%@ include file="/bookmarks/entry_columns.jspf" %>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" resultRowSplitter="<%= new BookmarksResultRowSplitter() %>" searchContainer="<%= bookmarksSearchContainer %>" />
</liferay-ui:search-container>