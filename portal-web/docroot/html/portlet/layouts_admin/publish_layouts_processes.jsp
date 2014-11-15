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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String closeRedirect = ParamUtil.getString(request, "closeRedirect");

long groupId = ParamUtil.getLong(request, "groupId");
long liveGroupId = ParamUtil.getLong(request, "liveGroupId");
long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");
String layoutSetBranchName = ParamUtil.getString(request, "layoutSetBranchName");
boolean localPublishing = ParamUtil.getBoolean(request, "localPublishing");

PortletURL renderURL = liferayPortletResponse.createRenderURL();

renderURL.setParameter("struts_action", "/layouts_admin/publish_layouts");
renderURL.setParameter("tabs2", "current-and-previous");
renderURL.setParameter("closeRedirect", closeRedirect);
renderURL.setParameter("groupId", String.valueOf(groupId));
renderURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
renderURL.setParameter("layoutSetBranchName", layoutSetBranchName);
renderURL.setParameter("localPublishing", String.valueOf(localPublishing));

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

String taskExecutorClassName = localPublishing ? LayoutStagingBackgroundTaskExecutor.class.getName() : LayoutRemoteStagingBackgroundTaskExecutor.class.getName();
%>

<liferay-ui:search-container
	emptyResultsMessage="no-publication-processes-were-found"
	iteratorURL="<%= renderURL %>"
	orderByCol="<%= orderByCol %>"
	orderByComparator="<%= orderByComparator %>"
	orderByType="<%= orderByType %>"
>
	<liferay-ui:search-container-results>

		<%
		List<BackgroundTask> backgroundTasks = BackgroundTaskLocalServiceUtil.getBackgroundTasks(groupId, taskExecutorClassName, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);

		results.addAll(backgroundTasks);

		if (localPublishing) {
			results.addAll(BackgroundTaskLocalServiceUtil.getBackgroundTasks(liveGroupId, taskExecutorClassName, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator));
		}

		searchContainer.setTotal(results.size());

		results = ListUtil.subList(results, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		%>

	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.BackgroundTask"
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
			path="/html/portlet/layouts_admin/publish_process_message.jsp"
		/>

		<c:if test="<%= localPublishing %>">
			<liferay-ui:search-container-column-text name="type">
				<c:if test="<%= backgroundTask.getGroupId() == liveGroupId %>">
					<strong class="label label-info">
						<liferay-ui:message key="initial-publication" />
					</strong>
				</c:if>

				<strong class="label label-default">
					<c:choose>
						<c:when test='<%= MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "privateLayout") %>'>
							<liferay-ui:message key="private-pages" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="public-pages" />
						</c:otherwise>
					</c:choose>
				</strong>
			</liferay-ui:search-container-column-text>
		</c:if>

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
				<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
					<c:if test="<%= backgroundTask.getGroupId() != liveGroupId %>">
						<portlet:actionURL var="relaunchURL">
							<portlet:param name="struts_action" value="/layouts_admin/edit_publish_configuration" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RELAUNCH %>" />
							<portlet:param name="redirect" value="<%= renderURL.toString() %>" />
							<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
						</portlet:actionURL>

						<liferay-ui:icon iconCssClass="icon-repeat" message="relaunch" url="<%= relaunchURL %>" />
					</c:if>

					<portlet:actionURL var="deleteBackgroundTaskURL">
						<portlet:param name="struts_action" value="/group_pages/delete_background_task" />
						<portlet:param name="redirect" value="<%= renderURL.toString() %>" />
						<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
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

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<%
int incompleteBackgroundTaskCount = BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(groupId, taskExecutorClassName, false);

if (localPublishing) {
	incompleteBackgroundTaskCount += BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(liveGroupId, taskExecutorClassName, false);
}
%>

<div class="hide incomplete-process-message">
	<liferay-util:include page="/html/portlet/layouts_admin/incomplete_processes_message.jsp">
		<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
	</liferay-util:include>
</div>