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
String closeRedirect = ParamUtil.getString(request, "closeRedirect");
long groupId = ParamUtil.getLong(request, "groupId");

PortletURL renderURL = liferayPortletResponse.createRenderURL();

renderURL.setParameter("struts_action", "/layouts_admin/publish_layouts");
renderURL.setParameter("tabs2", "all-publication-processes");
renderURL.setParameter("closeRedirect", closeRedirect);
renderURL.setParameter("groupId", String.valueOf(groupId));

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

OrderByComparator orderByComparator = BackgroundTaskUtil.getBackgroundTaskOrderByComparator(orderByCol, orderByType);
%>

<liferay-ui:search-container
	emptyResultsMessage="no-publication-processes-were-found"
	iteratorURL="<%= renderURL %>"
	orderByCol="<%= orderByCol %>"
	orderByComparator="<%= orderByComparator %>"
	orderByType="<%= orderByType %>"
	total="<%= BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(groupId, LayoutStagingBackgroundTaskExecutor.class.getName()) %>"
>
	<liferay-ui:search-container-results
		results="<%= BackgroundTaskLocalServiceUtil.getBackgroundTasks(groupId, LayoutStagingBackgroundTaskExecutor.class.getName(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.BackgroundTask"
		modelVar="backgroundTask"
	>
		<liferay-ui:search-container-column-text
			name="user-name"
			value="<%= backgroundTask.getUserName() %>"
		/>

		<liferay-ui:search-container-column-text
			name="status"
			value="<%= LanguageUtil.get(pageContext, backgroundTask.getStatusLabel()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="create-date"
			orderable="<%= true %>"
			orderableProperty="createDate"
			value="<%= dateFormatDateTime.format(backgroundTask.getCreateDate()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="completion-date"
			orderable="<%= true %>"
			orderableProperty="completionDate"
			value="<%= backgroundTask.getCompletionDate() != null ? dateFormatDateTime.format(backgroundTask.getCompletionDate()) : StringPool.BLANK %>"
		/>

		<liferay-ui:search-container-column-text>
			<portlet:actionURL var="deleteBackgroundTaskURL">
				<portlet:param name="struts_action" value="/group_pages/delete_background_task" />
				<portlet:param name="redirect" value="<%= renderURL.toString() %>" />
				<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				label="true"
				url="<%= deleteBackgroundTaskURL %>"
			/>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>