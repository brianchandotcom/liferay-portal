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

<%@ include file="/admin/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDLRecord ddlRecord = (DDLRecord)row.getObject();

long kaleoProcessId = GetterUtil.getLong((String)row.getParameter("kaleoProcessId"));

DDLRecordVersion ddlRecordVersion = ddlRecord.getLatestRecordVersion();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="viewDDLRecordURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
		<portlet:param name="mvcPath" value="/admin/view_record.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="ddlRecordId" value="<%= String.valueOf(ddlRecord.getRecordId()) %>" />
		<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcessId) %>" />
		<portlet:param name="version" value="<%= ddlRecordVersion.getVersion() %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view[action]"
		url="<%= viewDDLRecordURL %>"
	/>

	<portlet:actionURL name="deleteDDLRecord" var="deleteDDLRecordURL">
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="ddlRecordId" value="<%= String.valueOf(ddlRecord.getRecordId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteDDLRecordURL %>"
	/>
</liferay-ui:icon-menu>