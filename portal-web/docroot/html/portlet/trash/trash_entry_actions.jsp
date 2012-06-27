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

<%@ include file="/html/portlet/trash/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

TrashEntry entry = (TrashEntry)row.getObject();
%>

<liferay-ui:icon-menu>
	<portlet:actionURL var="restoreTrashEntryURL">
		<portlet:param name="struts_action" value="/trash/edit_entry" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
	</portlet:actionURL>

	<%
	String taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "checkEntry', {entryId: " + entry.getEntryId() + ",uri: '" + restoreTrashEntryURL.toString() + "'});";
	%>

	<liferay-ui:icon
		image="undo"
		message="restore"
		onClick="<%= taglibOnClick %>"
		url="javascript:;"
	/>

	<portlet:actionURL var="deleteTrashEntryURL">
		<portlet:param name="struts_action" value="/trash/edit_entry" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		image="delete"
		url="<%= deleteTrashEntryURL %>"
	/>
</liferay-ui:icon-menu>