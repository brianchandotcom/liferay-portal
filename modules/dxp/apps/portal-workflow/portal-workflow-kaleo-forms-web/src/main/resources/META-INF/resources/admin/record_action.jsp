<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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