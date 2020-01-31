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

<%@ include file="/document_library/ct_display/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);
%>

<p>
	<b><liferay-ui:message key="id" /></b>: <%= folder.getFolderId() %>
</p>

<p>
	<b><liferay-ui:message key="name" /></b>: <%= HtmlUtil.escape(folder.getName()) %>
</p>

<p>
	<b><liferay-ui:message key="description" /></b>: <%= HtmlUtil.escape(folder.getDescription()) %>
</p>

<p>
	<b><liferay-ui:message key="folders" /></b>: <%= DLAppServiceUtil.getFoldersCount(folder.getRepositoryId(), folder.getFolderId()) %>
</p>

<%
int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

int fileEntriesCount = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(folder.getRepositoryId(), folder.getFolderId(), status);
%>

<p>
	<b><liferay-ui:message key="documents" /></b>: <%= fileEntriesCount %>
</p>