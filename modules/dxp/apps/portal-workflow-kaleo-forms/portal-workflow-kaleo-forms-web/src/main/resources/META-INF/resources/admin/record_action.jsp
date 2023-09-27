<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
SearchContainer<?> searchContainer = (SearchContainer<?>)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = String.valueOf(searchContainer.getIteratorURL());

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

	<liferay-ui:icon
		message="track-workflow"
		url="<%= WorkflowInstanceTrackerURLProviderUtil.getURL(ddlRecord, request, KaleoProcess.class, true) %>"
	/>

	<c:if test="<%= KaleoProcessPermission.contains(permissionChecker, kaleoProcessId, ActionKeys.UPDATE) %>">
		<portlet:actionURL name="/kaleo_forms_admin/delete_record" var="deleteDDLRecordURL">
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="ddlRecordId" value="<%= String.valueOf(ddlRecord.getRecordId()) %>" />
			<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcessId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteDDLRecordURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>