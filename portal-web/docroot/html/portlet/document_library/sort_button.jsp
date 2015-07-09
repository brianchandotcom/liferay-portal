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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL);

String orderByType = ParamUtil.getString(request, "orderByType");

String reverseOrderByType = "asc";

if (orderByType.equals("asc")) {
	reverseOrderByType = "desc";
}
%>

<aui:nav-item dropdown="<%= true %>" id="sortButtonContainer" label="sort-by">
	<portlet:renderURL var="sortTitleURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
		<portlet:param name="orderByCol" value="title" />
		<portlet:param name="orderByType" value="<%= reverseOrderByType %>" />
	</portlet:renderURL>

	<aui:nav-item href="<%= sortTitleURL %>" iconCssClass="icon-calendar" label="title" />

	<portlet:renderURL var="sortDisplayDateURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
		<portlet:param name="orderByCol" value="creationDate" />
		<portlet:param name="orderByType" value="<%= reverseOrderByType %>" />
	</portlet:renderURL>

	<aui:nav-item href="<%= sortDisplayDateURL %>" iconCssClass="icon-calendar" label="create-date" />

	<portlet:renderURL var="sortModifiedDateURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
		<portlet:param name="orderByCol" value="modifiedDate" />
		<portlet:param name="orderByType" value="<%= reverseOrderByType %>" />
	</portlet:renderURL>

	<aui:nav-item href="<%= sortModifiedDateURL %>" iconCssClass="icon-calendar" label="modified-date" />

	<portlet:renderURL var="sortDownloadsURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
		<portlet:param name="orderByCol" value="downloads" />
		<portlet:param name="orderByType" value="<%= reverseOrderByType %>" />
	</portlet:renderURL>

	<aui:nav-item href="<%= sortDownloadsURL %>" iconCssClass="icon-calendar" label="downloads" />

	<portlet:renderURL var="sortSizeURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
		<portlet:param name="orderByCol" value="size" />
		<portlet:param name="orderByType" value="<%= reverseOrderByType %>" />
	</portlet:renderURL>

	<aui:nav-item href="<%= sortSizeURL %>" iconCssClass="icon-calendar" label="size" />
</aui:nav-item>