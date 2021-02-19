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

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ScheduledJobDispatchTrigger scheduledJobDispatchTrigger = (ScheduledJobDispatchTrigger)row.getObject();
%>

<portlet:actionURL name="/dispatch/edit_scheduled_task_dispatch_trigger" var="editScheduledTaskURL">
	<portlet:param name="<%= Constants.CMD %>" value="runScheduledTask" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="jobName" value="<%= scheduledJobDispatchTrigger.getName() %>" />
	<portlet:param name="groupName" value="<%= scheduledJobDispatchTrigger.getGroupName() %>" />
	<portlet:param name="storageType" value="<%= scheduledJobDispatchTrigger.getStorageType().toString() %>" />
</portlet:actionURL>

<aui:button href="<%= editScheduledTaskURL %>" name='<%= "runNowButton" + row.getRowId() %>' value="run-now" />