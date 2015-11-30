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
long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getScopeGroupId());

PortletURL portletURL = currentURLObj;

portletURL.setParameter("tabs3", "current-and-previous");

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", orderByType);
}
else {
	orderByCol = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", "create-date");
	orderByType = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", "desc");
}

OrderByComparator<BackgroundTask> orderByComparator = BackgroundTaskComparatorFactoryUtil.getBackgroundTaskOrderByComparator(orderByCol, orderByType);
%>

<liferay-ui:search-container
	emptyResultsMessage="no-publication-processes-were-found"
	iteratorURL="<%= portletURL %>"
	orderByCol="<%= orderByCol %>"
	orderByComparator="<%= orderByComparator %>"
	orderByType="<%= orderByType %>"
	total="<%= BackgroundTaskManagerUtil.getBackgroundTasksCount(groupId, selPortlet.getPortletId(), BackgroundTaskExecutorNames.PORTLET_STAGING_BACKGROUND_TASK_EXECUTOR) %>"
>
	<liferay-ui:search-container-results
		results="<%= BackgroundTaskManagerUtil.getBackgroundTasks(groupId, selPortlet.getPortletId(), BackgroundTaskExecutorNames.PORTLET_STAGING_BACKGROUND_TASK_EXECUTOR, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

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

		<liferay-ui:search-container-column-jsp
			cssClass="background-task-status-column"
			name="status"
			path="/publish_process_message.jsp"
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
			<c:if test="<%= !backgroundTask.isInProgress() %>">

				<%
				Date completionDate = backgroundTask.getCompletionDate();
				%>

				<liferay-portlet:actionURL name="deleteBackgroundTask" portletName="<%= PortletKeys.EXPORT_IMPORT %>" var="deleteBackgroundTaskURL">
					<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
					<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon-delete
					label="<%= true %>"
					message='<%= ((completionDate != null) && completionDate.before(new Date())) ? "clear" : "cancel" %>'
					url="<%= deleteBackgroundTaskURL %>"
				/>
			</c:if>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" />
</liferay-ui:search-container>

<%
int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(groupId, selPortlet.getPortletId(), BackgroundTaskExecutorNames.PORTLET_STAGING_BACKGROUND_TASK_EXECUTOR, false);
%>

<div class="hide incomplete-process-message">
	<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
		<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
	</liferay-util:include>
</div>