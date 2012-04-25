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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

FileEntry fileEntry = (FileEntry)objArray[0];
FileVersion fileVersion = (FileVersion)objArray[1];
%>

<liferay-ui:icon-menu align='<%= "auto" %>' direction='<%= "down" %>' extended="<%= false %>" icon="<%= StringPool.BLANK %>" message='<%= StringPool.BLANK %>'>
	<liferay-ui:icon
		image="download"
		url="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK) %>"
	/>

	<portlet:renderURL var="viewFileVersionURL">
		<portlet:param name="struts_action" value="/document_library/view_file_entry" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
		<portlet:param name="version" value="<%= fileVersion.getVersion() %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		image="view"
		url="<%= viewFileVersionURL %>"
	/>

	<portlet:renderURL var="viewFileEntryURL">
		<portlet:param name="struts_action" value="/document_library/view_file_entry" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
	</portlet:renderURL>

	<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE) && (fileVersion.getStatus() == WorkflowConstants.STATUS_APPROVED) && (row.getPos() != 0) %>">
		<portlet:actionURL var="revertURL">
			<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.REVERT %>" />
			<portlet:param name="redirect" value="<%= viewFileEntryURL %>" />
			<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
			<portlet:param name="version" value="<%= String.valueOf(fileVersion.getVersion()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="undo"
			message="revert"
			url="<%= revertURL %>"
		/>
	</c:if>

	<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) && (fileVersion.getStatus() == WorkflowConstants.STATUS_APPROVED) && (fileEntry.getModel() instanceof DLFileEntry) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= viewFileEntryURL %>" />
			<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
			<portlet:param name="version" value="<%= String.valueOf(fileVersion.getVersion()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			message="delete-version"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>