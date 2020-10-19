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
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

SearchContainer<Object> bookmarksSearchContainer = (SearchContainer)request.getAttribute("view.jsp-bookmarksSearchContainer");

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

bookmarksSearchContainer.setRowChecker(entriesChecker);

entriesChecker.setCssClass("entry-selector");

if (folderId == 0) {
	entriesChecker.setRememberCheckBoxStateURLRegex("mvcRenderCommandName=/bookmarks/view(&.|$)");
}
else {
	entriesChecker.setRememberCheckBoxStateURLRegex("^(?!.*" + liferayPortletResponse.getNamespace() + "redirect).*(folderId=" + folderId + ")");
}

EntriesMover entriesMover = new EntriesMover(trashHelper.isTrashEnabled(scopeGroupId));

bookmarksSearchContainer.setRowMover(entriesMover);

String displayStyle = GetterUtil.getString((String)request.getAttribute("view.jsp-displayStyle"));

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
%>

<liferay-ui:search-container
	id='<%= ParamUtil.getString(request, "searchContainerId") %>'
	searchContainer="<%= bookmarksSearchContainer %>"
	totalVar="bookmarksSearchContainerTotal"
>
	<liferay-ui:search-container-results
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
				row.setData(
					HashMapBuilder.<String, Object>put(
						"folder", true
					).put(
						"folder-id", curFolder.getFolderId()
					).put(
						"title", curFolder.getName()
					).build());

				row.setPrimaryKey(String.valueOf(curFolder.getFolderId()));
				%>

				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/bookmarks/view_folder" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
				</liferay-portlet:renderURL>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-icon
							icon="folder"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="<%= 2 %>"
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
				row.setData(
					HashMapBuilder.<String, Object>put(
						"title", entry.getName()
					).build());

				row.setPrimaryKey(String.valueOf(entry.getEntryId()));

				String entryHREF = themeDisplay.getPathMain() + "/bookmarks/open_entry?entryId=" + entry.getEntryId();
				%>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-icon
							icon="link"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="<%= 2 %>"
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

	<liferay-ui:search-iterator
		displayStyle="<%= displayStyle %>"
		markupView="lexicon"
		resultRowSplitter="<%= new BookmarksResultRowSplitter() %>"
		searchContainer="<%= bookmarksSearchContainer %>"
	/>
</liferay-ui:search-container>