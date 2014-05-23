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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FileEntry fileEntry = (FileEntry)row.getObject();

MBMessage message = MBMessageAttachmentsUtil.getMessage(fileEntry.getFileEntryId());
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, message.getCategoryId(), ActionKeys.ADD_FILE) %>">

		<%
		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), fileEntry.getFileEntryId());
		%>

		<portlet:actionURL var="restoreEntryURL">
			<portlet:param name="struts_action" value="/message_boards/restore_message_attachment" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="trashEntryId" value="<%= String.valueOf(trashEntry.getEntryId()) %>" />
		</portlet:actionURL>

		<%
		String taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "checkEntry', {trashEntryId: " + trashEntry.getEntryId() + ", uri: '" + restoreEntryURL.toString() + "'});";
		%>

		<liferay-ui:icon
			iconCssClass="icon-undo"
			message="restore"
			onClick="<%= taglibOnClick %>"
			url="javascript:;"
		/>

		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/message_boards/edit_message_attachments" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
			<portlet:param name="fileName" value="<%= fileEntry.getTitle() %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>