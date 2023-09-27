<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/blogs_admin/init.jsp" %>

<%
BlogsViewImagesDisplayContext blogsViewImagesDisplayContext = (BlogsViewImagesDisplayContext)request.getAttribute(BlogsViewImagesDisplayContext.class.getName());
%>

<liferay-document-library:repository-browser
	actions="delete"
	folderId="<%= blogsViewImagesDisplayContext.getFolderId() %>"
	repositoryId="<%= blogsViewImagesDisplayContext.getRepositoryId() %>"
/>