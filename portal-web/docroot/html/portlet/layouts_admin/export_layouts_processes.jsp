<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
long groupId = ParamUtil.getLong(request, "groupId");

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/layouts_admin/export_layouts");
portletURL.setParameter("tabs2", "current-and-previous");
portletURL.setParameter("groupId", String.valueOf(groupId));

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

OrderByComparator orderByComparator = BackgroundTaskComparatorFactoryUtil.getBackgroundTaskOrderByComparator(orderByCol, orderByType);
%>

<liferay-ui:search-container
	emptyResultsMessage="no-export-processes-were-found"
	iteratorURL="<%= portletURL %>"
	orderByCol="<%= orderByCol %>"
	orderByComparator="<%= orderByComparator %>"
	orderByType="<%= orderByType %>"
	total="<%= BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(groupId, LayoutExportBackgroundTaskExecutor.class.getName()) %>"
>
	<liferay-ui:search-container-results
		results="<%= BackgroundTaskLocalServiceUtil.getBackgroundTasks(groupId, LayoutExportBackgroundTaskExecutor.class.getName(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.BackgroundTask"
		keyProperty="backgroundTaskId"
		modelVar="backgroundTask"
	>
		<liferay-ui:search-container-column-text
			name="user-name"
			value="<%= HtmlUtil.escape(backgroundTask.getUserName()) %>"
		/>

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

						String portletFileEntryURL = PortletFileRepositoryUtil.getPortletFileEntryURL(themeDisplay, fileEntry, StringPool.BLANK);

						String downloadPortletFileEntryURL = HttpUtil.addParameter(portletFileEntryURL, "download", true);
						%>

						<liferay-ui:icon
							image="download"
							label="<%= true %>"
							message="<%= sb.toString() %>"
							url="<%= downloadPortletFileEntryURL %>"
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
						image="download"
						label="<%= true %>"
						message='<%= HtmlUtil.escape(MapUtil.getString(taskContextMap, "fileName")) %>'
					/>
				</c:otherwise>
			</c:choose>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text>
			<c:if test="<%= !backgroundTask.isInProgress() %>">
				<portlet:actionURL var="deleteBackgroundTaskURL">
					<portlet:param name="struts_action" value="/group_pages/delete_background_task" />
					<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
					<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
				</portlet:actionURL>

				<%
				Date completionDate = backgroundTask.getCompletionDate();
				%>

				<liferay-ui:icon-delete
					label="true"
					message='<%= ((completionDate != null) && completionDate.before(new Date())) ? "clear" : "cancel" %>'
					url="<%= deleteBackgroundTaskURL %>"
				/>
			</c:if>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<%
int incompleteBackgroundTaskCount = BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(groupId, LayoutExportBackgroundTaskExecutor.class.getName(), false);
%>

<div class="hide incomplete-process-message">
	<liferay-util:include page="/html/portlet/layouts_admin/incomplete_processes_message.jsp">
		<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
	</liferay-util:include>
</div>