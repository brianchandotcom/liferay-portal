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

<%@ include file="/document_library/init.jsp" %>

<%
Folder parentFolder = (Folder)request.getAttribute("info_panel_location.jsp-parentFolder");

long parentFolderId = (parentFolder == null) ? DLFolderConstants.DEFAULT_PARENT_FOLDER_ID : parentFolder.getFolderId();
%>

<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, parentFolderId, ActionKeys.VIEW) %>">
	<dt class="sidebar-dt">
		<liferay-ui:message key="location" />
	</dt>
	<dd class="sidebar-dd">

		<%
		PortletURL viewFolderURL = liferayPortletResponse.createRenderURL();

		if (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			viewFolderURL.setParameter("mvcRenderCommandName", "/document_library/view");
		}
		else {
			viewFolderURL.setParameter("mvcRenderCommandName", "/document_library/view_folder");
			viewFolderURL.setParameter("folderId", String.valueOf(parentFolderId));
		}

		viewFolderURL.setParameter("redirect", currentURL);
		%>

		<clay:link
			href="<%= viewFolderURL.toString() %>"
			icon="folder"
			label='<%= (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) ? LanguageUtil.get(request, "home") : parentFolder.getName() %>'
			style="secondary"
		/>
	</dd>
</c:if>