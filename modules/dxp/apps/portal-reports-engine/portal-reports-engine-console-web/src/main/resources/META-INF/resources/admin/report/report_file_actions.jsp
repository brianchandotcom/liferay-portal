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
		<portlet:actionURL name="deleteReport" var="deleteReportURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
			<portlet:param name="fileName" value="<%= fileName %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteReportURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>