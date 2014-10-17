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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "home");

BookmarksFolder folder = (BookmarksFolder)request.getAttribute(BookmarksWebKeys.BOOKMARKS_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", rootFolderId);

boolean defaultFolderView = false;

if ((folder == null) && (folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	defaultFolderView = true;
}

if (defaultFolderView) {
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

if (folder != null) {
	BookmarksUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}
%>

<liferay-ui:trash-undo />

<liferay-util:include page="/html/portlet/bookmarks/top_links.jsp" servletContext="<%= application %>" />

<c:choose>
	<c:when test="<%= useAssetEntryQuery %>">
		<liferay-ui:categorization-filter
			assetType="bookmarks"
			portletURL="<%= portletURL %>"
		/>

		<%@ include file="/html/portlet/bookmarks/view_entries.jspf" %>

	</c:when>
	<c:when test='<%= topLink.equals("home") %>'>
		<aui:row>
			<c:if test="<%= folder != null %>">
				<liferay-ui:header
					localizeTitle="<%= false %>"
					title="<%= folder.getName() %>"
				/>
			</c:if>
		</aui:row>

		<aui:row>
			<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="<%= 75 %>">
				<liferay-ui:panel-container extended="<%= false %>" id="bookmarksInfoPanelContainer" persistState="<%= true %>">
					<c:if test="<%= folder != null %>">
						<div class="lfr-asset-description">
							<%= HtmlUtil.escape(folder.getDescription()) %>
						</div>

						<div class="lfr-asset-metadata">
							<div class="icon-calendar lfr-asset-icon">
								<%= LanguageUtil.format(request, "last-updated-x", dateFormatDate.format(folder.getModifiedDate()), false) %>
							</div>

							<%
							AssetRendererFactory bookmarksEntryAssetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(BookmarksEntry.class.getName());

							AssetRendererFactory bookmarksFolderAssetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(BookmarksFolder.class.getName());
							%>

							<div class="<%= bookmarksFolderAssetRendererFactory.getIconCssClass() %> lfr-asset-icon">
								<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "subfolder" : "subfolders" %>' />
							</div>

							<div class="<%= bookmarksEntryAssetRendererFactory.getIconCssClass() %> last lfr-asset-icon">
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
								delta="<%= bookmarksSettings.getFoldersPerPage() %>"
								deltaConfigurable="<%= false %>"
								headerNames="<%= StringUtil.merge(folderColumns) %>"
								iteratorURL="<%= portletURL %>"
								total="<%= BookmarksFolderServiceUtil.getFoldersCount(scopeGroupId, folderId) %>"
							>
								<liferay-ui:search-container-results
									results="<%= BookmarksFolderServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
								/>

								<liferay-ui:search-container-row
									className="com.liferay.bookmarks.model.BookmarksFolder"
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
			</aui:col>

			<aui:col cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>" width="<%= 25 %>">
				<div class="lfr-asset-summary">
					<liferay-ui:icon
						cssClass="lfr-asset-avatar"
						image='<%= "../file_system/large/" + (((foldersCount + entriesCount) > 0) ? "folder_full_bookmark" : "folder_empty_bookmark") %>'
						message='<%= (folder != null) ? HtmlUtil.escapeAttribute(folder.getName()) : "home" %>'
					/>

					<div class="lfr-asset-name">
						<h4><%= (folder != null) ? HtmlUtil.escape(folder.getName()) : LanguageUtil.get(request, "home") %></h4>
					</div>
				</div>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<liferay-util:include page="/html/portlet/bookmarks/folder_action.jsp" servletContext="<%= application %>" />
			</aui:col>
		</aui:row>

		<%
		if (!defaultFolderView && (folder != null) && (portletName.equals(BookmarksPortletKeys.BOOKMARKS) || portletName.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN))) {
			PortalUtil.setPageSubtitle(folder.getName(), request);
			PortalUtil.setPageDescription(folder.getDescription(), request);
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("mine") || topLink.equals("recent") %>'>
		<aui:row>
			<liferay-ui:header
				title="<%= topLink %>"
			/>

			<%
			long groupEntriesUserId = 0;

			if (topLink.equals("mine") && themeDisplay.isSignedIn()) {
				groupEntriesUserId = user.getUserId();
			}
			%>

			<liferay-ui:search-container
				delta="<%= bookmarksSettings.getEntriesPerPage() %>"
				deltaConfigurable="<%= false %>"
				emptyResultsMessage="there-are-no-entries"
				iteratorURL="<%= portletURL %>"
				total="<%= BookmarksEntryServiceUtil.getGroupEntriesCount(scopeGroupId, groupEntriesUserId) %>"
			>
				<liferay-ui:search-container-results
					results="<%= BookmarksEntryServiceUtil.getGroupEntries(scopeGroupId, groupEntriesUserId, searchContainer.getStart(), searchContainer.getEnd()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.bookmarks.model.BookmarksEntry"
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
		</aui:row>

		<%
		if (!layout.isTypeControlPanel()) {
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, topLink), currentURL);
		}

		PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
		%>

	</c:when>
</c:choose>