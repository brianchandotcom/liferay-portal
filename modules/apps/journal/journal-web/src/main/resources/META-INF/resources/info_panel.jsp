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

<%@ include file="/init.jsp" %>

<%
List<Long> folders = ListUtil.toList(ParamUtil.getLongValues(request, "rowIdsJournalFolder"));
List<String> entries = ListUtil.toList(ParamUtil.getStringValues(request, "rowIdsJournalArticle"));

if (ListUtil.isEmpty(folders) && ListUtil.isEmpty(entries)) {
	long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"), ParamUtil.getLong(request, "folderId"));

	folders = new ArrayList<Long>();

	JournalFolder folder = (JournalFolder)request.getAttribute("view.jsp-folder");

	if (folder != null) {
		folders.add(folder.getFolderId());
	}
	else if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		folders.add(folderId);
	}
	else {
		folders.add(null);
	}
}
%>

<c:choose>
	<c:when test="<%= (ListUtil.isEmpty(entries) && ListUtil.isNotEmpty(folders) && (folders.size() == 1)) %>">

		<%
		Long folderId = folders.get(0);

		if (folderId == null) {
			folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		JournalFolder folder = JournalFolderServiceUtil.fetchFolder(folderId);

		request.setAttribute("info_panel.jsp-folder", folder);
		%>

		<div class="sidebar-header">
			<c:if test="<%= journalDisplayContext.isShowEditActions() %>">
				<ul class="list-inline list-unstyled sidebar-header-actions">
					<li>
						<liferay-util:include page="/subscribe.jsp" servletContext="<%= application %>" />
					</li>

					<li>
						<liferay-util:include page="/folder_action.jsp" servletContext="<%= application %>" />
					</li>
				</ul>
			</c:if>

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
				<%= JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, folderId, journalDisplayContext.getStatus()) %>
			</p>

			<c:if test="<%= folder != null %>">
				<h5><liferay-ui:message key="created" /></h5>

				<p>
					<%= HtmlUtil.escape(folder.getUserName()) %>
				</p>
			</c:if>
		</div>
	</c:when>
</c:choose>