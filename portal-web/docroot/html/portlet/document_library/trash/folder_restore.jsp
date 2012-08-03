<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library_display/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = folder.getFolderId();

boolean hasUpdatePermission = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE);
%>

<c:if test="<%= hasUpdatePermission && !folder.isMountPoint() %>">
	<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_LIBRARY %>" var="moveURL">
		<portlet:param name="struts_action" value="/document_library/move_folder" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.MOVE_FROM_TRASH %>" />
		<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(request) %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="parentFolderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
		<portlet:param name="repositoryId" value="<%= String.valueOf(folder.getRepositoryId()) %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		image="submit"
		message="move"
		url="<%= moveURL %>"
	/>
</c:if>