<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SchedulerResponse schedulerResponse = (SchedulerResponse)row.getObject();
%>

<portlet:actionURL name="/dispatch/edit_scheduler_response" var="editSchedulerResponseURL">
	<portlet:param name="<%= Constants.CMD %>" value="runScheduledJob" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="jobName" value="<%= schedulerResponse.getJobName() %>" />
	<portlet:param name="groupName" value="<%= schedulerResponse.getGroupName() %>" />
	<portlet:param name="storageType" value="<%= schedulerResponse.getStorageType().toString() %>" />
</portlet:actionURL>

<aui:button href="<%= editSchedulerResponseURL %>" name='<%= "runNowButton" + row.getRowId() %>' value="run-now" />