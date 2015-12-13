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
BookmarksFolder folder = (BookmarksFolder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));
%>

<div class="sidebar-header">
	<ul class="list-inline list-unstyled sidebar-header-actions">
		<li>
			<liferay-util:include page="/bookmarks/subscribe.jsp" servletContext="<%= application %>" />
		</li>

		<li>
			<liferay-util:include page="/bookmarks/folder_action.jsp" servletContext="<%= application %>" />
		</li>
	</ul>

	<h4><%= (folder != null) ? folder.getName() : LanguageUtil.get(request, "home") %></h4>

	<div>
		<liferay-ui:message key="folder" />
	</div>
</div>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="details" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<div class="sidebar-body">
	<h5><liferay-ui:message key="num-of-items" /></h5>

	<p>
		<%= BookmarksFolderServiceUtil.getFoldersAndEntriesCount(scopeGroupId, folderId, WorkflowConstants.STATUS_APPROVED) %>
	</p>

	<c:if test="<%= folder != null %>">
		<h5><liferay-ui:message key="created" /></h5>

		<p>
			<%= HtmlUtil.escape(folder.getUserName()) %>
		</p>
	</c:if>
</div>