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

<%@ include file="/html/portlet/background_task/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

BackgroundTask backgroundTask = (BackgroundTask)row.getObject();

BackgroundTaskDisplay backgroundTaskDisplay = BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(backgroundTask.getBackgroundTaskId(), locale);
%>

<strong class="background-task-status-<%= BackgroundTaskConstants.getStatusLabel(backgroundTask.getStatus()) %> <%= BackgroundTaskConstants.getStatusCssClass(backgroundTask.getStatus()) %> label">
	<liferay-ui:message key="<%= backgroundTask.getStatusLabel() %>" />
</strong>

<c:if test="<%= backgroundTask.isInProgress() %>">

	<c:if test="<%= backgroundTaskDisplay.hasBackgroundTaskStatus() %>">

		<%
		int percentage = backgroundTaskDisplay.getPercentage();
		%>

		<div class="active progress progress-striped">
			<div class="progress-bar" style="width: <%= percentage %>%;">
				<c:if test="<%= backgroundTaskDisplay.hasPercentage() %>">
					<%= percentage + StringPool.PERCENT %>
				</c:if>
			</div>
		</div>

		<c:if test="<%= backgroundTaskDisplay.hasMessage() %>">
			<div class="progress-current-item">
				<liferay-ui:message key="<%= backgroundTaskDisplay.getMessage() %>" localizeKey="<%= false %>" />
			</div>
		</c:if>
	</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(backgroundTask.getStatusMessage()) %>">

	<%
	long[] expandedBackgroundTaskIds = StringUtil.split(GetterUtil.getString(SessionClicks.get(request, "background-task-ids", null)), 0L);

	request.setAttribute("backgroundTaskDisplay", backgroundTaskDisplay);
	%>

	<a class="details-link toggler-header-<%= ArrayUtil.contains(expandedBackgroundTaskIds, backgroundTask.getBackgroundTaskId()) ? "expanded" : "collapsed" %>" data-persist-id="<%= backgroundTask.getBackgroundTaskId() %>" href="#"><liferay-ui:message key="details" /></a>

	<div class="background-task-status-message toggler-content-<%= ArrayUtil.contains(expandedBackgroundTaskIds, backgroundTask.getBackgroundTaskId()) ? "expanded" : "collapsed" %>">
		<liferay-util:include page="/html/portlet/background_task/background_task_details.jsp">
			<liferay-util:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
		</liferay-util:include>
	</div>
</c:if>