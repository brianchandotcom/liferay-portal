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

<%@ include file="/export/init.jsp" %>

<%
ExportLayoutsProcessesDisplayContext exportLayoutsProcessesDisplayContext = new ExportLayoutsProcessesDisplayContext(request, liferayPortletResponse);

PortletURL portletURL = exportLayoutsProcessesDisplayContext.getPortletURL();
%>

<portlet:actionURL name="/export_import/delete_layout_export_background_tasks" var="deleteBackgroundTasksURL">
	<portlet:param name="redirect" value="<%= currentURL.toString() %>" />
</portlet:actionURL>

<aui:form action="<%= deleteBackgroundTasksURL %>" method="get" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL.toString() %>" />
	<aui:input name="deleteBackgroundTaskIds" type="hidden" />

	<liferay-ui:search-container
		searchContainer="<%= exportLayoutsProcessesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.backgroundtask.BackgroundTask"
			keyProperty="backgroundTaskId"
			modelVar="backgroundTask"
		>

			<%
			BackgroundTaskDisplay backgroundTaskDisplay = BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(backgroundTask);

			String backgroundTaskName = backgroundTaskDisplay.getDisplayName(request);
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(exportLayoutsProcessesDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-text>
						<liferay-ui:user-portrait
							userId="<%= backgroundTask.getUserId() %>"
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

						Date createDate = backgroundTask.getCreateDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
						%>

						<span class="text-default">
							<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(backgroundTaskUserName), modifiedDateDescription} %>" key="x-modified-x-ago" />
						</span>

						<h2 class="h5">
							<span id="<portlet:namespace />backgroundTaskName<%= backgroundTask.getBackgroundTaskId() %>">
								<%= HtmlUtil.escape(backgroundTaskName) %>
							</span>

							<%
							for (FileEntry fileEntry : backgroundTask.getAttachmentsFileEntries()) {
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

						</h2>

						<c:if test="<%= backgroundTask.isInProgress() %>">

							<%
							BackgroundTaskStatus backgroundTaskStatus = BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(backgroundTask.getBackgroundTaskId());
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

						<clay:row>
							<clay:col>
								<liferay-staging:process-status
									backgroundTaskStatus="<%= backgroundTask.getStatus() %>"
									backgroundTaskStatusLabel="<%= backgroundTask.getStatusLabel() %>"
								/>
							</clay:col>
						</clay:row>

						<c:if test="<%= Validator.isNotNull(backgroundTask.getStatusMessage()) %>">
							<span class="background-task-status-row">
								<a class="details-link" href="javascript:;" onclick="<portlet:namespace />viewBackgroundTaskDetails(<%= backgroundTask.getBackgroundTaskId() %>);">
									<liferay-ui:message key="see-more-details" />
								</a>
							</span>

							<div class="background-task-status-message hide" id="<portlet:namespace />backgroundTaskStatusMessage<%= backgroundTask.getBackgroundTaskId() %>">
								<liferay-util:include page="/publish_process_message_task_details.jsp" servletContext="<%= application %>">
									<liferay-util:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
								</liferay-util:include>
							</div>
						</c:if>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(exportLayoutsProcessesDisplayContext.getDisplayStyle(), "list") %>'>
					<liferay-ui:search-container-column-text
						name="user"
					>
						<liferay-ui:user-display
							displayStyle="3"
							showUserDetails="<%= false %>"
							showUserName="<%= false %>"
							userId="<%= backgroundTask.getUserId() %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200 table-title"
						name="title"
					>
						<span id="<%= liferayPortletResponse.getNamespace() + "backgroundTaskName" + String.valueOf(backgroundTask.getBackgroundTaskId()) %>">
							<liferay-ui:message key="<%= HtmlUtil.escape(backgroundTaskName) %>" />
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="background-task-status-column"
						name="status"
					>
						<liferay-staging:process-status
							backgroundTaskStatus="<%= backgroundTask.getStatus() %>"
							backgroundTaskStatusLabel="<%= backgroundTask.getStatusLabel() %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
						name="create-date"
						orderable="<%= true %>"
						value="<%= backgroundTask.getCreateDate() %>"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
						name="completion-date"
						orderable="<%= true %>"
						value="<%= backgroundTask.getCompletionDate() %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200"
						name="download"
					>

						<%
						for (FileEntry fileEntry : backgroundTask.getAttachmentsFileEntries()) {
							StringBundler sb = new StringBundler(4);

							sb.append(fileEntry.getTitle());
							sb.append(StringPool.OPEN_PARENTHESIS);
							sb.append(LanguageUtil.formatStorageSize(fileEntry.getSize(), locale));
							sb.append(StringPool.CLOSE_PARENTHESIS);
						%>

							<liferay-ui:icon
								icon="download"
								label="<%= true %>"
								linkCssClass="table-link"
								markupView="lexicon"
								message="<%= sb.toString() %>"
								method="get"
								url="<%= PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, fileEntry, StringPool.BLANK) %>"
							/>

						<%
						}
						%>

					</liferay-ui:search-container-column-text>
				</c:when>
			</c:choose>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= !backgroundTask.isInProgress() %>">
					<liferay-ui:icon-menu
						direction="left-side"
						icon="<%= StringPool.BLANK %>"
						markupView="lexicon"
						showWhenSingleIcon="<%= true %>"
					>
						<portlet:actionURL name="/export_import/edit_export_configuration" var="relaunchURL">
							<portlet:param name="mvcRenderCommandName" value="/export_import/view_export_layouts" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RELAUNCH %>" />
							<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
							<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
						</portlet:actionURL>

						<liferay-ui:icon
							icon="reload"
							markupView="lexicon"
							message="relaunch"
							url="<%= relaunchURL %>"
						/>

						<portlet:actionURL name="/export_import/delete_layout_export_background_tasks" var="deleteBackgroundTaskURL">
							<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
							<portlet:param name="deleteBackgroundTaskIds" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
						</portlet:actionURL>

						<%
						Date completionDate = backgroundTask.getCompletionDate();
						%>

						<liferay-ui:icon-delete
							label="<%= true %>"
							message='<%= ((completionDate != null) && completionDate.before(new Date())) ? "clear" : "cancel" %>'
							url="<%= deleteBackgroundTaskURL %>"
						/>
					</liferay-ui:icon-menu>
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= exportLayoutsProcessesDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			resultRowSplitter="<%= new ExportImportResultRowSplitter() %>"
		/>
	</liferay-ui:search-container>
</aui:form>

<%
int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(exportLayoutsProcessesDisplayContext.getGroupId(), BackgroundTaskExecutorNames.LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR, false);
%>

<div class="hide incomplete-process-message">
	<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
		<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
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