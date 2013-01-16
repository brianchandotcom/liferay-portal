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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DLFileEntryType fileEntryType = null;

if (row != null) {
	fileEntryType = (DLFileEntryType)row.getObject();
}
else {
	fileEntryType = (DLFileEntryType)request.getAttribute("view_folders.jsp-fileEntryType");
}
%>

<liferay-ui:icon-menu align='<%= (row == null) ? "auto" : "right" %>' direction='<%= (row == null) ? "down" : null %>' extended="<%= (row == null) ? false : true %>" icon="<%= (row == null) ? StringPool.BLANK : null %>" message='<%= (row == null) ? StringPool.BLANK : "actions" %>' showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= (fileEntryType != null) && DLFileEntryTypePermission.contains(permissionChecker, fileEntryType, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/document_library/edit_file_entry_type" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= DLPermission.contains(permissionChecker, scopeGroupId, ActionKeys.SUBSCRIBE) && (row == null) %>">

		<%
		Set<Long> fileEntryTypeSubscriptionClassPKs = (Set<Long>)request.getAttribute("file_entry_type_action.jsp-fileEntryTypeSubscriptionClassPKs");

		if (themeDisplay.isSignedIn() && (fileEntryTypeSubscriptionClassPKs == null)) {
			fileEntryTypeSubscriptionClassPKs = DLUtil.getFileEntryTypeSubscriptionClassPKs(user.getUserId());

			request.setAttribute("file_entry_type_action.jsp-fileEntryTypeSubscriptionClassPKs", fileEntryTypeSubscriptionClassPKs);
		}
		%>

		<c:choose>
			<c:when test="<%= (fileEntryTypeSubscriptionClassPKs != null) && fileEntryTypeSubscriptionClassPKs.contains((fileEntryType != null) ? fileEntryType.getFileEntryTypeId() : scopeGroupId) %>">
				<portlet:actionURL var="unsubscribeURL">
					<portlet:param name="struts_action" value="/document_library/edit_file_entry_type" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="fileEntryTypeId" value="<%= String.valueOf((fileEntryType != null) ? fileEntryType.getFileEntryTypeId() : DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					image="unsubscribe"
					url="<%= unsubscribeURL %>"
				/>
			</c:when>
			<c:otherwise>
				<portlet:actionURL var="subscribeURL">
					<portlet:param name="struts_action" value="/document_library/edit_file_entry_type" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="fileEntryTypeId" value="<%= String.valueOf((fileEntryType != null) ? fileEntryType.getFileEntryTypeId() : DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					image="subscribe"
					url="<%= subscribeURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= (fileEntryType != null) && DLFileEntryTypePermission.contains(permissionChecker, fileEntryType, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DLFileEntryType.class.getName() %>"
			modelResourceDescription="<%= fileEntryType.getName() %>"
			resourcePrimKey="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon
			image="permissions"
			url="<%= permissionsURL %>"
		/>
	</c:if>

	<c:if test="<%= (fileEntryType != null) && DLFileEntryTypePermission.contains(permissionChecker, fileEntryType, ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/document_library/edit_file_entry_type" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>