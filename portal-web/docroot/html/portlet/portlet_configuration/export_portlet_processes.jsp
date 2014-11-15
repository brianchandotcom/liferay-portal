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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

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
	emptyResultsMessage="no-export-processes-were-found"
	iteratorURL="<%= portletURL %>"
	orderByCol="<%= orderByCol %>"
	orderByComparator="<%= orderByComparator %>"
	orderByType="<%= orderByType %>"
	total="<%= BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(groupId, selPortlet.getPortletId(), PortletExportBackgroundTaskExecutor.class.getName()) %>"
>
	<liferay-ui:search-container-results
		results="<%= BackgroundTaskLocalServiceUtil.getBackgroundTasks(groupId, selPortlet.getPortletId(), PortletExportBackgroundTaskExecutor.class.getName(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

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

		<liferay-ui:search-container-column-text
			name="download"
		>

			<%
			List<FileEntry> attachmentsFileEntries = backgroundTask.getAttachmentsFileEntries();
			%>

			<c:choose>
				<c:when test="<%= !attachmentsFileEntries.isEmpty() %>">

					<%
					for (FileEntry fileEntry : attachmentsFileEntries) {
					%>

						<%
						StringBundler sb = new StringBundler(4);

						sb.append(fileEntry.getTitle());
						sb.append(StringPool.OPEN_PARENTHESIS);
						sb.append(TextFormatter.formatStorageSize(fileEntry.getSize(), locale));
						sb.append(StringPool.CLOSE_PARENTHESIS);
						%>

						<liferay-ui:icon
							iconCssClass="icon-download"
							label="<%= true %>"
							message="<%= sb.toString() %>"
							url="<%= PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, fileEntry, StringPool.BLANK) %>"
						/>

					<%
					}
					%>

				</c:when>
				<c:otherwise>

					<%
					Map taskContextMap = backgroundTask.getTaskContextMap();
					%>

					<liferay-ui:icon
						iconCssClass="icon-download"
						label="<%= true %>"
						message='<%= HtmlUtil.escape(MapUtil.getString(taskContextMap, "fileName")) %>'
					/>
				</c:otherwise>
			</c:choose>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text>
			<c:if test="<%= !backgroundTask.isInProgress() %>">

				<%
				Date completionDate = backgroundTask.getCompletionDate();
				%>

				<portlet:actionURL var="deleteBackgroundTaskURL">
					<portlet:param name="struts_action" value="/group_pages/delete_background_task" />
					<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
					<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete
					label="<%= true %>"
					message='<%= ((completionDate != null) && completionDate.before(new Date())) ? "clear" : "cancel" %>'
					url="<%= deleteBackgroundTaskURL %>"
				/>
			</c:if>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<%
int incompleteBackgroundTaskCount = BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(groupId, selPortlet.getPortletId(), PortletExportBackgroundTaskExecutor.class.getName(), false);
%>

<div class="hide incomplete-process-message">
	<liferay-util:include page="/html/portlet/layouts_admin/incomplete_processes_message.jsp">
		<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
	</liferay-util:include>
</div>