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

<%@ include file="/blogs_admin/init.jsp" %>

<%
PortletURL portletURL = (PortletURL)request.getAttribute("view_images.jsp-portletURL");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FileEntry fileEntry = (FileEntry)row.getObject();
%>

<c:if test="<%= (fileEntry.getUserId() == themeDisplay.getUserId()) || BlogsPermission.contains(permissionChecker, themeDisplay.getScopeGroupId(), ActionKeys.UPDATE) %>">
	<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
		<portlet:actionURL name="/blogs/edit_image" var="deleteImageURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
			<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete trash="<%= false %>" url="<%= deleteImageURL %>" />
	</liferay-ui:icon-menu>
</c:if>