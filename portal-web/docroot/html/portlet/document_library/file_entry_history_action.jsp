<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

FileEntry fileEntry = (FileEntry)objArray[0];
FileVersion fileVersion = (FileVersion)objArray[1];
%>

<liferay-ui:icon
	image="download"
	label="<%= false %>"
	url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(fileEntry.getTitle()) + "?version=" + fileVersion.getVersion() %>'
/>

<portlet:renderURL var="viewFileVersionURL">
	<portlet:param name="struts_action" value="/document_library/view_file_entry" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
	<portlet:param name="version" value="<%= fileVersion.getVersion() %>" />
</portlet:renderURL>

<liferay-ui:icon
	image="view"
	label="<%= false %>"
	url="<%= viewFileVersionURL %>"
/>