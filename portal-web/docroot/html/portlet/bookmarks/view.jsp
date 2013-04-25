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
String topLink = ParamUtil.getString(request, "topLink", "home");

BookmarksFolder folder = (BookmarksFolder)request.getAttribute(WebKeys.BOOKMARKS_FOLDER);

long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (defaultFolderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = BookmarksFolderServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

int foldersCount = BookmarksFolderServiceUtil.getFoldersCount(scopeGroupId, folderId);
int entriesCount = BookmarksEntryServiceUtil.getEntriesCount(scopeGroupId, folderId);

long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

boolean useAssetEntryQuery = (assetCategoryId > 0) || Validator.isNotNull(assetTagName);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/bookmarks/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());

request.setAttribute("view.jsp-useAssetEntryQuery", String.valueOf(useAssetEntryQuery));
%>

<portlet:actionURL var="undoTrashURL">
	<portlet:param name="struts_action" value="/bookmarks/edit_entry" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-ui:trash-undo portletURL="<%= undoTrashURL %>" />

<liferay-util:include page="/html/portlet/bookmarks/top_links.jsp" />

<c:choose>
	<c:when test="<%= useAssetEntryQuery %>">
		<liferay-ui:categorization-filter
			assetType="bookmarks"
			portletURL="<%= portletURL %>"
		/>

		<%@ include file="/html/portlet/bookmarks/view_entries.jspf" %>

	</c:when>
	<c:when test='<%= topLink.equals("home") %>'>
		<aui:layout>
			<c:if test="<%= folder != null %>">

				<%
				long parentFolderId = folder.getParentFolderId();
				String parentFolderName = LanguageUtil.get(pageContext, "home");

				if (!folder.isRoot()) {
					BookmarksFolder parentFolder = BookmarksFolderLocalServiceUtil.getBookmarksFolder(parentFolderId);

					parentFolderId = parentFolder.getFolderId();
					parentFolderName = parentFolder.getName();
				}
				%>

				<portlet:renderURL var="backURL">
					<portlet:param name="struts_action" value="/bookmarks/view" />
					<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
				</portlet:renderURL>

				<liferay-ui:header
					backLabel="<%= parentFolderName %>"
					backURL="<%= backURL.toString() %>"
					localizeTitle="<%= false %>"
					title="<%= folder.getName() %>"
				/>
			</c:if>

			<aui:column columnWidth="<%= 75 %>" cssClass="lfr-asset-column lfr-asset-column-details" first="<%= true %>">
				<liferay-ui:panel-container extended="<%= false %>" id="bookmarksInfoPanelContainer" persistState="<%= true %>">
					<c:if test="<%= folder != null %>">
						<div class="lfr-asset-description">
							<%= HtmlUtil.escape(folder.getDescription()) %>
						</div>

						<div class="lfr-asset-metadata">
							<div class="lfr-asset-icon lfr-asset-date">
								<%= LanguageUtil.format(pageContext, "last-updated-x", dateFormatDate.format(folder.getModifiedDate())) %>
							</div>

							<div class="lfr-asset-icon lfr-asset-subfolders">
								<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "subfolder" : "subfolders" %>' />
							</div>

							<div class="lfr-asset-icon lfr-asset-items last">
								<%= entriesCount %> <liferay-ui:message key='<%= (entriesCount == 1) ? "entry" : "entries" %>' />
							</div>
						</div>

						<liferay-ui:custom-attributes-available className="<%= BookmarksFolder.class.getName() %>">
							<liferay-ui:custom-attribute-list
								className="<%= BookmarksFolder.class.getName() %>"
								classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
								editable="<%= false %>"
								label="<%= true %>"
							/>
						</liferay-ui:custom-attributes-available>
					</c:if>

					<c:if test="<%= foldersCount > 0 %>">
						<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="bookmarksEntriesFoldersListingPanel" persistState="<%= true %>" title='<%= (folder != null) ? "subfolders" : "folders" %>'>
							<liferay-ui:search-container
								curParam="cur1"
								delta="<%= foldersPerPage %>"
								deltaConfigurable="<%= false %>"
								headerNames="<%= StringUtil.merge(folderColumns) %>"
								iteratorURL="<%= portletURL %>"
							>
								<liferay-ui:search-container-results
									results="<%= BookmarksFolderServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
									total="<%= BookmarksFolderServiceUtil.getFoldersCount(scopeGroupId, folderId) %>"
								/>

								<liferay-ui:search-container-row
									className="com.liferay.portlet.bookmarks.model.BookmarksFolder"
									escapedModel="<%= true %>"
									keyProperty="folderId"
									modelVar="curFolder"
								>
									<liferay-portlet:renderURL varImpl="rowURL">
										<portlet:param name="struts_action" value="/bookmarks/view" />
										<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
									</liferay-portlet:renderURL>

									<%@ include file="/html/portlet/bookmarks/folder_columns.jspf" %>
								</liferay-ui:search-container-row>

								<liferay-ui:search-iterator />
							</liferay-ui:search-container>
						</liferay-ui:panel>
					</c:if>

					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="bookmarksEntriesListingPanel" persistState="<%= true %>" title="bookmarks">
						<%@ include file="/html/portlet/bookmarks/view_entries.jspf" %>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</aui:column>

			<aui:column columnWidth="<%= 25 %>" cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>">
				<div class="lfr-asset-summary">
					<liferay-ui:icon
						cssClass="lfr-asset-avatar"
						image='<%= "../file_system/large/" + (((foldersCount + entriesCount) > 0) ? "folder_full_bookmark" : "folder_empty_bookmark") %>'
						message='<%= (folder != null) ? HtmlUtil.escapeAttribute(folder.getName()) : "home" %>'
					/>

					<div class="lfr-asset-name">
						<h4><%= (folder != null) ? HtmlUtil.escape(folder.getName()) : LanguageUtil.get(pageContext, "home") %></h4>
					</div>
				</div>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<liferay-util:include page="/html/portlet/bookmarks/folder_action.jsp" />
			</aui:column>
		</aui:layout>

		<%
		if (folder != null) {
			BookmarksUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

			if (portletName.equals(PortletKeys.BOOKMARKS)) {
				PortalUtil.setPageSubtitle(folder.getName(), request);
				PortalUtil.setPageDescription(folder.getDescription(), request);
			}
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("mine") || topLink.equals("recent") %>'>
		<aui:layout>
			<liferay-ui:header
				title="<%= topLink %>"
			/>

			<liferay-ui:search-container
				delta="<%= entriesPerPage %>"
				deltaConfigurable="<%= false %>"
				emptyResultsMessage="there-are-no-entries"
				iteratorURL="<%= portletURL %>"
			>

				<%
				long groupEntriesUserId = 0;

				if (topLink.equals("mine") && themeDisplay.isSignedIn()) {
					groupEntriesUserId = user.getUserId();
				}
				%>

				<liferay-ui:search-container-results
					results="<%= BookmarksEntryServiceUtil.getGroupEntries(scopeGroupId, groupEntriesUserId, searchContainer.getStart(), searchContainer.getEnd()) %>"
					total="<%= BookmarksEntryServiceUtil.getGroupEntriesCount(scopeGroupId, groupEntriesUserId) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.bookmarks.model.BookmarksEntry"
					escapedModel="<%= true %>"
					keyProperty="entryId"
					modelVar="entry"
				>

					<%
					String rowHREF = null;

					if (BookmarksEntryPermission.contains(permissionChecker, entry, ActionKeys.VIEW)) {
						PortletURL tempRowURL = renderResponse.createRenderURL();

						tempRowURL.setParameter("struts_action", "/bookmarks/view_entry");
						tempRowURL.setParameter("redirect", currentURL);
						tempRowURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

						rowHREF = tempRowURL.toString();
					}
					%>

					<%@ include file="/html/portlet/bookmarks/entry_columns.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</aui:layout>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, topLink), currentURL);

		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
		%>

	</c:when>
</c:choose>