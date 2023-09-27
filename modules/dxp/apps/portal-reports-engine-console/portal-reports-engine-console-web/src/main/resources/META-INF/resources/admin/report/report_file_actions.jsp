<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Entry entry = (Entry)request.getAttribute("entry");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

String fileName = (String)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="deliverReportURL">
		<portlet:param name="mvcPath" value="/admin/report/deliver_report.jsp" />
		<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		<portlet:param name="fileName" value="<%= fileName %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="deliver-report"
		url="<%= deliverReportURL %>"
	/>

	<portlet:resourceURL id="download" var="downloadURL">
		<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		<portlet:param name="fileName" value="<%= fileName %>" />
	</portlet:resourceURL>

	<liferay-ui:icon
		message="download"
		method="get"
		url="<%= downloadURL %>"
	/>

	<c:if test="<%= EntryPermissionChecker.contains(permissionChecker, entry.getEntryId(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="/reports_admin/delete_report" var="deleteReportURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
			<portlet:param name="fileName" value="<%= fileName %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteReportURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>