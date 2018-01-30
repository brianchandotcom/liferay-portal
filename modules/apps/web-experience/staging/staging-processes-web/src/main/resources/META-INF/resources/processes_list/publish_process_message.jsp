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

BackgroundTask backgroundTask = (BackgroundTask)row.getObject();
%>

<h5 class="background-task-status-<%= BackgroundTaskConstants.getStatusLabel(backgroundTask.getStatus()) %> <%= BackgroundTaskConstants.getStatusCssClass(backgroundTask.getStatus()) %>">
	<liferay-ui:message key="<%= backgroundTask.getStatusLabel() %>" />
</h5>

<c:if test="<%= backgroundTask.isInProgress() %>">

	<%
	BackgroundTaskStatus backgroundTaskStatus = BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(backgroundTask.getBackgroundTaskId());
	%>

	<c:if test="<%= backgroundTaskStatus != null %>">

		<%
		String cmd = ArrayUtil.toString(ExportImportConfigurationHelper.getExportImportConfigurationParameter(backgroundTask, Constants.CMD), StringPool.BLANK);

		int percentage = 100;

		long allModelAdditionCountersTotal = GetterUtil.getLong(backgroundTaskStatus.getAttribute("allModelAdditionCountersTotal"));
		long allPortletAdditionCounter = GetterUtil.getLong(backgroundTaskStatus.getAttribute("allPortletAdditionCounter"));
		long currentModelAdditionCountersTotal = GetterUtil.getLong(backgroundTaskStatus.getAttribute("currentModelAdditionCountersTotal"));
		long currentPortletAdditionCounter = GetterUtil.getLong(backgroundTaskStatus.getAttribute("currentPortletAdditionCounter"));

		long allProgressBarCountersTotal = allModelAdditionCountersTotal + allPortletAdditionCounter;
		long currentProgressBarCountersTotal = currentModelAdditionCountersTotal + currentPortletAdditionCounter;

		if (allProgressBarCountersTotal > 0) {
			int base = 100;

			String phase = GetterUtil.getString(backgroundTaskStatus.getAttribute("phase"));

			if (phase.equals(Constants.EXPORT) && !Objects.equals(cmd, Constants.PUBLISH_TO_REMOTE)) {
				base = 50;
			}

			percentage = Math.round((float)currentProgressBarCountersTotal / allProgressBarCountersTotal * base);
		}
		%>

		<div class="active progress progress-striped progress-xs">
			<div class="progress-bar" style="width: <%= percentage %>%;">
				<c:if test="<%= (allProgressBarCountersTotal > 0) && (!Objects.equals(cmd, Constants.PUBLISH_TO_REMOTE) || (percentage < 100)) %>">
					<%= percentage + StringPool.PERCENT %>
				</c:if>
			</div>
		</div>

		<%
		String stagedModelName = (String)backgroundTaskStatus.getAttribute("stagedModelName");
		String stagedModelType = (String)backgroundTaskStatus.getAttribute("stagedModelType");
		%>

		<c:choose>
			<c:when test="<%= Objects.equals(cmd, Constants.PUBLISH_TO_REMOTE) && (percentage == 100) %>">
				<div class="progress-current-item">
					<strong><liferay-ui:message key="please-wait-as-the-publication-processes-on-the-remote-site" /></strong>
				</div>
			</c:when>
			<c:when test="<%= Validator.isNotNull(stagedModelName) && Validator.isNotNull(stagedModelType) %>">
				<div class="progress-current-item">
					<strong><liferay-ui:message key="publishing" /><%= StringPool.TRIPLE_PERIOD %></strong> <%= ResourceActionsUtil.getModelResource(locale, stagedModelType) %> <em><%= HtmlUtil.escape(stagedModelName) %></em>
				</div>
			</c:when>
		</c:choose>
	</c:if>
</c:if>

<liferay-staging:process-message-task-details
	backgroundTaskId="<%= backgroundTask.getBackgroundTaskId() %>"
	backgroundTaskStatusMessage="<%= backgroundTask.getStatusMessage() %>"
/>