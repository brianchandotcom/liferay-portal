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
boolean localPublishing = true;

if (liveGroup.isStaged() && liveGroup.isStagedRemotely()) {
	localPublishing = false;
}

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String navigation = ParamUtil.getString(request, "navigation", "all");
String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");
String searchContainerId = ParamUtil.getString(request, "searchContainerId");

PortletURL renderURL = liferayPortletResponse.createRenderURL();

renderURL.setParameter("mvcRenderCommandName", "publishLayoutsView");
renderURL.setParameter("tabs1", "processes");
renderURL.setParameter("localPublishing", String.valueOf(localPublishing));
renderURL.setParameter("displayStyle", displayStyle);
renderURL.setParameter("navigation", navigation);
renderURL.setParameter("orderByCol", orderByCol);
renderURL.setParameter("orderByType", orderByType);
renderURL.setParameter("searchContainerId", searchContainerId);

String taskExecutorClassName = localPublishing ? BackgroundTaskExecutorNames.LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR : BackgroundTaskExecutorNames.LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR;

OrderByComparator<BackgroundTask> orderByComparator = BackgroundTaskComparatorFactoryUtil.getBackgroundTaskOrderByComparator(orderByCol, orderByType);
%>

<div id="<portlet:namespace />publishProcessesSearchContainer">
	<liferay-ui:search-container
		emptyResultsMessage="no-publication-processes-were-found"
		id="<%= searchContainerId %>"
		iteratorURL="<%= renderURL %>"
		orderByCol="<%= orderByCol %>"
		orderByComparator="<%= orderByComparator %>"
		orderByType="<%= orderByType %>"
		rowChecker="<%= new EmptyOnClickRowChecker(liferayPortletResponse) %>"
	>

		<liferay-ui:search-container-results>

			<%
			int importProcessesCount = 0;
			List<BackgroundTask> importProcesses = null;

			if (navigation.equals("all")) {
				importProcessesCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(new long[] {groupId, liveGroupId}, taskExecutorClassName);
				importProcesses = BackgroundTaskManagerUtil.getBackgroundTasks(new long[] {groupId, liveGroupId}, taskExecutorClassName, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
			}
			else {
				boolean completed = false;

				if (navigation.equals("completed")) {
					completed = true;
				}

				importProcessesCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(new long[] {groupId, liveGroupId}, taskExecutorClassName, completed);
				importProcesses = BackgroundTaskManagerUtil.getBackgroundTasks(new long[] {groupId, liveGroupId}, taskExecutorClassName, completed, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
			}

			searchContainer.setResults(importProcesses);
			searchContainer.setTotal(importProcessesCount);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.backgroundtask.BackgroundTask"
			keyProperty="backgroundTaskId"
			modelVar="backgroundTask"
		>

			<liferay-ui:search-container-column-text
				cssClass="background-task-user-column"
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
				name="title"
			>

				<%
				String backgroundTaskName = backgroundTask.getName();

				if (backgroundTask.getGroupId() == liveGroupId) {
					backgroundTaskName = LanguageUtil.get(request, "initial-publication");
				}

				if (backgroundTaskName.equals(StringPool.BLANK)) {
					backgroundTaskName = LanguageUtil.get(request, "untitled");
				}
				%>

				<liferay-ui:message key="<%= HtmlUtil.escape(backgroundTaskName) %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				cssClass="background-task-status-column"
				name="status"
				path="/processes_list/publish_process_message.jsp"
			/>

			<liferay-ui:search-container-column-date
				name="create-date"
				orderable="<%= true %>"
				value="<%= backgroundTask.getCreateDate() %>"
			/>

			<liferay-ui:search-container-column-date
				name="completion-date"
				orderable="<%= true %>"
				value="<%= backgroundTask.getCompletionDate() %>"
			/>

			<liferay-ui:search-container-column-text>
				<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
					<c:if test="<%= backgroundTask.getGroupId() != liveGroupId %>">
						<portlet:actionURL name="editPublishConfiguration" var="relaunchURL">
							<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RELAUNCH %>" />
							<portlet:param name="redirect" value="<%= currentURL.toString() %>" />
							<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
						</portlet:actionURL>

						<liferay-ui:icon
							message="relaunch"
							url="<%= relaunchURL %>"
						/>
					</c:if>

					<portlet:actionURL name="deleteBackgroundTask" var="deleteBackgroundTaskURL">
						<portlet:param name="redirect" value="<%= currentURL.toString() %>" />
						<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
					</portlet:actionURL>

					<%
					Date completionDate = backgroundTask.getCompletionDate();
					%>

					<liferay-ui:icon
						message='<%= LanguageUtil.get(request, ((completionDate != null) && completionDate.before(new Date())) ? "clear" : "cancel") %>'
						url="<%= deleteBackgroundTaskURL %>"
					/>
				</liferay-ui:icon-menu>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" resultRowSplitter="<%= new PublishResultRowSplitter() %>" />
	</liferay-ui:search-container>
</div>

<%
int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(groupId, taskExecutorClassName, false);

if (localPublishing) {
	incompleteBackgroundTaskCount += BackgroundTaskManagerUtil.getBackgroundTasksCount(liveGroupId, taskExecutorClassName, false);
}
%>

<div class="hide incomplete-process-message">
	<liferay-util:include page="/processes_list/incomplete_processes_message.jsp" servletContext="<%= application %>">
		<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
	</liferay-util:include>
</div>