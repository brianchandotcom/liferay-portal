<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

ExportImportProcessDisplayContext exportImportProcessDisplayContext = new ExportImportProcessDisplayContext(request);

BackgroundTask backgroundTask = exportImportProcessDisplayContext.getBackgroundTask();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}
%>

<liferay-ui:search-container
	searchContainer="<%= exportImportProcessDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.backgroundtask.BackgroundTask"
		keyProperty="backgroundTaskId"
		modelVar="curBackgroundTask"
	>

		<%
		BackgroundTaskDisplay backgroundTaskDisplay = BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(curBackgroundTask);

		String backgroundTaskName = backgroundTaskDisplay.getDisplayName(request);
		%>

		<liferay-ui:search-container-column-text>
			<liferay-user:user-portrait
				userId="<%= curBackgroundTask.getUserId() %>"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			colspan="<%= 2 %>"
		>

			<%
			String backgroundTaskUserName = LanguageUtil.get(request, "deleted-user");

			User backgroundTaskUser = UserLocalServiceUtil.fetchUser(backgroundTask.getUserId());

			if (backgroundTaskUser != null) {
				backgroundTaskUserName = backgroundTaskUser.getFullName();
			}

			Date createDate = curBackgroundTask.getCreateDate();

			String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
			%>

			<h6 class="text-default">
				<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(backgroundTaskUserName), modifiedDateDescription} %>" key="x-modified-x-ago" />
			</h6>

			<h5>
				<span id="<portlet:namespace />backgroundTaskName<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>">
					<%= HtmlUtil.escape(backgroundTaskName) %>
				</span>

				<%
				for (FileEntry fileEntry : curBackgroundTask.getAttachmentsFileEntries()) {
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

					<div class="active progress">
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

			<h6 class="background-task-status-row background-task-status-<%= BackgroundTaskConstants.getStatusLabel(curBackgroundTask.getStatus()) %> <%= BackgroundTaskConstants.getStatusCssClass(curBackgroundTask.getStatus()) %>">
				<liferay-ui:message key="<%= curBackgroundTask.getStatusLabel() %>" />
			</h6>

			<c:if test="<%= Validator.isNotNull(curBackgroundTask.getStatusMessage()) %>">
				<h6 class="background-task-status-row">
					<a class="details-link" href="javascript:void(0);" onclick="<portlet:namespace />viewBackgroundTaskDetails(<%= curBackgroundTask.getBackgroundTaskId() %>);">
						<liferay-ui:message key="see-more-details" />
					</a>
				</h6>

				<div class="background-task-status-message hide" id="<portlet:namespace />backgroundTaskStatusMessage<%= curBackgroundTask.getBackgroundTaskId() %>">
					<liferay-util:include page="/publish_process_message_task_details.jsp" servletContext="<%= application %>">
						<liferay-util:param name="backgroundTaskId" value="<%= String.valueOf(curBackgroundTask.getBackgroundTaskId()) %>" />
					</liferay-util:include>
				</div>
			</c:if>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="descriptive"
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<%
int incompleteBackgroundTasksCount = 0;

if ((backgroundTask != null) && backgroundTask.isInProgress()) {
	incompleteBackgroundTasksCount = 1;
}
%>

<div class="hide incomplete-process-message">
	<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
		<liferay-util:param name="incompleteBackgroundTasksCount" value="<%= String.valueOf(incompleteBackgroundTasksCount) %>" />
	</liferay-util:include>
</div>

<script>
	function <portlet:namespace />viewBackgroundTaskDetails(backgroundTaskId) {
		var title = '';

		var backgroundTaskNameElement = document.getElementById(
			'<portlet:namespace />backgroundTaskName' + backgroundTaskId
		);

		if (backgroundTaskNameElement) {
			title = backgroundTaskNameElement.textContent;
		}

		Liferay.fire('<portlet:namespace />viewBackgroundTaskDetails', {
			nodeId: 'backgroundTaskStatusMessage' + backgroundTaskId,
			title: title,
		});
	}
</script>