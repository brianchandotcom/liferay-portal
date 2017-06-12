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
List<BookmarksFolder> folders = (List<BookmarksFolder>)request.getAttribute(BookmarksWebKeys.BOOKMARKS_FOLDERS);
List<BookmarksEntry> entries = (List<BookmarksEntry>)request.getAttribute(BookmarksWebKeys.BOOKMARKS_ENTRIES);

if (ListUtil.isEmpty(folders) && ListUtil.isEmpty(entries)) {
	long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"), ParamUtil.getLong(request, "folderId"));

	folders = new ArrayList<BookmarksFolder>();

	BookmarksFolder folder = (BookmarksFolder)request.getAttribute("view.jsp-folder");

	if (folder != null) {
		folders.add(folder);
	}
	else if (folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		folders.add(BookmarksFolderLocalServiceUtil.getFolder(folderId));
	}
	else {
		folders.add(null);
	}
}
%>

<c:choose>
	<c:when test="<%= ListUtil.isEmpty(entries) && ListUtil.isNotEmpty(folders) && (folders.size() == 1) %>">

		<%
		BookmarksFolder folder = folders.get(0);

		request.setAttribute("info_panel.jsp-folder", folder);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-actions">
				<li>
					<liferay-util:include page="/bookmarks/subscribe.jsp" servletContext="<%= application %>" />
				</li>
				<li>
					<liferay-util:include page="/bookmarks/folder_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4 class="sidebar-title"><%= (folder != null) ? HtmlUtil.escape(folder.getName()) : LanguageUtil.get(request, "home") %></h4>

			<h5>
				<liferay-ui:message key="folder" />
			</h5>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<dl class="sidebar-block">
				<dt class="h5">
					<liferay-ui:message key="num-of-items" />
				</dt>

				<%
				long folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

				if (folder != null) {
					folderId = folder.getFolderId();
				}
				%>

				<dd class="h6 sidebar-caption">
					<%= BookmarksFolderServiceUtil.getFoldersAndEntriesCount(scopeGroupId, folderId, WorkflowConstants.STATUS_APPROVED) %>
				</dd>

				<c:if test="<%= folder != null %>">
					<dt class="h5">
						<liferay-ui:message key="created" />
					</dt>
					<dd class="h6 sidebar-caption">
						<%= HtmlUtil.escape(folder.getUserName()) %>
					</dd>
				</c:if>
			</dl>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isEmpty(folders) && ListUtil.isNotEmpty(entries) && (entries.size() == 1) %>">

		<%
		BookmarksEntry entry = entries.get(0);

		request.setAttribute("info_panel.jsp-entry", entry);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-actions">
				<li>
					<liferay-util:include page="/bookmarks/subscribe.jsp" servletContext="<%= application %>" />
				</li>
				<li>
					<liferay-util:include page="/bookmarks/entry_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4 class="sidebar-title"><%= HtmlUtil.escape(entry.getName()) %></h4>

			<h5>
				<liferay-ui:message key="entry" />
			</h5>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<dl class="sidebar-block">
				<dt class="h5">
					<liferay-ui:message key="created" />
				</dt>
				<dd class="h6 sidebar-caption">
					<%= HtmlUtil.escape(entry.getUserName()) %>
				</dd>

				<c:if test="<%= Validator.isNotNull(entry.getDescription()) %>">
					<dt class="h5">
						<liferay-ui:message key="description" />
					</dt>
					<dd class="h6 sidebar-caption">
						<%= HtmlUtil.escape(entry.getDescription()) %>
					</dd>
				</c:if>

				<dt class="h5">
					<liferay-ui:message key="url" />
				</dt>
				<dd class="h6 sidebar-caption">
					<%= HtmlUtil.escape(entry.getUrl()) %>
				</dd>
				<dt class="h5">
					<liferay-ui:message key="visits" />
				</dt>
				<dd class="h6 sidebar-caption">
					<%= entry.getVisits() %>
				</dd>
			</dl>

			<div class="lfr-asset-categories sidebar-block">
				<liferay-ui:asset-categories-summary
					className="<%= BookmarksEntry.class.getName() %>"
					classPK="<%= entry.getEntryId() %>"
				/>
			</div>

			<div class="lfr-asset-tags sidebar-block">
				<liferay-ui:asset-tags-summary
					className="<%= BookmarksEntry.class.getName() %>"
					classPK="<%= entry.getEntryId() %>"
					message="tags"
				/>
			</div>

			<liferay-ui:ratings
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= entry.getEntryId() %>"
				inTrash="<%= entry.isInTrash() %>"
			/>

			<liferay-expando:custom-attributes-available className="<%= BookmarksEntry.class.getName() %>">
				<liferay-expando:custom-attribute-list
					className="<%= BookmarksEntry.class.getName() %>"
					classPK="<%= entry.getEntryId() %>"
					editable="<%= false %>"
					label="<%= true %>"
				/>
			</liferay-expando:custom-attributes-available>

			<c:if test="<%= bookmarksGroupServiceOverriddenConfiguration.enableRelatedAssets() %>">

				<%
				AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.getEntry(BookmarksEntry.class.getName(), entry.getEntryId());
				%>

				<div class="entry-links">
					<liferay-ui:asset-links
						assetEntryId="<%= layoutAssetEntry.getEntryId() %>"
					/>
				</div>
			</c:if>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4 class="sidebar-title"><liferay-ui:message arguments="<%= folders.size() + entries.size() %>" key="x-items-are-selected" /></h4>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message arguments="<%= folders.size() + entries.size() %>" key="x-items-are-selected" /></h5>
		</div>
	</c:otherwise>
</c:choose>