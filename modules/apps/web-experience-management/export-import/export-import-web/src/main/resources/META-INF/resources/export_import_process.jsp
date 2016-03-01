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
long backgroundTaskId = ParamUtil.getLong(request, "backgroundTaskId");

BackgroundTask backgroundTask = null;

if (backgroundTaskId > 0) {
	backgroundTask = BackgroundTaskManagerUtil.getBackgroundTask(backgroundTaskId);
}
%>

<liferay-ui:search-container emptyResultsMessage="no-export-processes-were-found">
	<liferay-ui:search-container-results>

		<%
		List<BackgroundTask> backgroundTasks = new ArrayList<>();
		int backgroundTasksCount = 0;

		if (backgroundTask != null) {
			backgroundTasks.add(backgroundTask);
			backgroundTasksCount = 1;
		}

		searchContainer.setResults(backgroundTasks);
		searchContainer.setTotal(backgroundTasksCount);
		%>

	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.backgroundtask.BackgroundTask"
		keyProperty="backgroundTaskId"
		modelVar="curBackgroundTask"
	>

		<%
		String backgroundTaskName = curBackgroundTask.getName();

		if (backgroundTaskName.equals(StringPool.BLANK)) {
			backgroundTaskName = LanguageUtil.get(request, "untitled");
		}
		%>

		<liferay-ui:search-container-column-text>
			<liferay-ui:user-portrait
				cssClass="user-icon-lg"
				userId="<%= curBackgroundTask.getUserId() %>"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			colspan="<%= 2 %>"
		>

			<%
			User backgroundTaskUser = UserLocalServiceUtil.getUser(curBackgroundTask.getUserId());

			Date createDate = curBackgroundTask.getCreateDate();

			String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
			%>

			<h6 class="text-default">
				<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(backgroundTaskUser.getFullName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
			</h6>

			<h5>

				<%= HtmlUtil.escape(backgroundTaskName) %>

				<%
				List<FileEntry> attachmentsFileEntries = curBackgroundTask.getAttachmentsFileEntries();

				for (FileEntry fileEntry : attachmentsFileEntries) {
				%>

					<liferay-ui:icon
						icon="download"
						markupView="lexicon"
						method="get"
						url="<%= PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, fileEntry, StringPool.BLANK) %>"
					/>

				<%
				}
				%>

			</h5>

			<c:if test="<%= curBackgroundTask.isInProgress() %>">

				<%
				BackgroundTaskStatus backgroundTaskStatus = BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(curBackgroundTask.getBackgroundTaskId());
				%>

				<c:if test="<%= backgroundTaskStatus != null %>">

					<%
					int percentage = 100;

					long allModelAdditionCountersTotal = GetterUtil.getLong(backgroundTaskStatus.getAttribute("allModelAdditionCountersTotal"));
					long allPortletAdditionCounter = GetterUtil.getLong(backgroundTaskStatus.getAttribute("allPortletAdditionCounter"));
					long currentModelAdditionCountersTotal = GetterUtil.getLong(backgroundTaskStatus.getAttribute("currentModelAdditionCountersTotal"));
					long currentPortletAdditionCounter = GetterUtil.getLong(backgroundTaskStatus.getAttribute("currentPortletAdditionCounter"));

					long allProgressBarCountersTotal = allModelAdditionCountersTotal + allPortletAdditionCounter;
					long currentProgressBarCountersTotal = currentModelAdditionCountersTotal + currentPortletAdditionCounter;

					if (allProgressBarCountersTotal > 0) {
						percentage = Math.round((float)currentProgressBarCountersTotal / allProgressBarCountersTotal * 100);
					}
					%>

					<div class="active progress progress-striped progress-xs">
						<div class="progress-bar" style="width: <%= percentage %>%;">
							<c:if test="<%= allProgressBarCountersTotal > 0 %>">
								<%= percentage + StringPool.PERCENT %>
							</c:if>
						</div>
					</div>

					<%
					String stagedModelName = (String)backgroundTaskStatus.getAttribute("stagedModelName");
					String stagedModelType = (String)backgroundTaskStatus.getAttribute("stagedModelType");
					%>

					<c:if test="<%= Validator.isNotNull(stagedModelName) && Validator.isNotNull(stagedModelType) %>">
						<div class="progress-current-item">
							<strong><liferay-ui:message key="exporting" /><%= StringPool.TRIPLE_PERIOD %></strong> <%= ResourceActionsUtil.getModelResource(locale, stagedModelType) %> <em><%= HtmlUtil.escape(stagedModelName) %></em>
						</div>
					</c:if>
				</c:if>
			</c:if>

			<c:if test="<%= Validator.isNotNull(curBackgroundTask.getStatusMessage()) %>">

				<%
				long[] expandedBackgroundTaskIds = StringUtil.split(GetterUtil.getString(SessionClicks.get(request, "com.liferay.exportimport.web_backgroundTaskIds", null)), 0L);
				%>

				<a class="details-link toggler-header-<%= ArrayUtil.contains(expandedBackgroundTaskIds, curBackgroundTask.getBackgroundTaskId()) ? "expanded" : "collapsed" %>" data-persist-id="<%= curBackgroundTask.getBackgroundTaskId() %>" href="#"><liferay-ui:message key="details" /></a>

				<div class="background-task-status-message toggler-content-<%= ArrayUtil.contains(expandedBackgroundTaskIds, curBackgroundTask.getBackgroundTaskId()) ? "expanded" : "collapsed" %>">
					<liferay-util:include page="/publish_process_message_task_details.jsp" servletContext="<%= application %>">
						<liferay-util:param name="backgroundTaskId" value="<%= String.valueOf(curBackgroundTask.getBackgroundTaskId()) %>" />
					</liferay-util:include>
				</div>
			</c:if>

			<h6 class="background-task-status-<%= BackgroundTaskConstants.getStatusLabel(curBackgroundTask.getStatus()) %> <%= BackgroundTaskConstants.getStatusCssClass(curBackgroundTask.getStatus()) %>">
				<liferay-ui:message key="<%= curBackgroundTask.getStatusLabel() %>" />
			</h6>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
</liferay-ui:search-container>

<%
int incompleteBackgroundTaskCount = 0;

if ((backgroundTask != null) && backgroundTask.isInProgress()) {
	incompleteBackgroundTaskCount = 1;
}
%>

<div class="hide incomplete-process-message">
	<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
		<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
	</liferay-util:include>
</div>