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
portletDisplay.setShowBackIcon(true);

LiferayPortletURL usersAdminURL = liferayPortletResponse.createLiferayPortletURL(UsersAdminPortletKeys.USERS_ADMIN, PortletRequest.RENDER_PHASE);

portletDisplay.setURLBack(usersAdminURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "export-personal-data")));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(LanguageUtil.get(request, "processes"));
					});
			}
		}
	%>'
/>

<aui:form cssClass="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage="no-personal-data-export-processes-were-found"
		orderByComparator='<%= BackgroundTaskComparatorFactoryUtil.getBackgroundTaskOrderByComparator("name", "asc") %>'
		total="<%= BackgroundTaskManagerUtil.getBackgroundTasksCount(themeDisplay.getScopeGroupId(), UADExportBackgroundTaskExecutor.class.getName()) %>"
	>
		<liferay-ui:search-container-results
			results="<%= BackgroundTaskManagerUtil.getBackgroundTasks(themeDisplay.getScopeGroupId(), UADExportBackgroundTaskExecutor.class.getName(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.backgroundtask.BackgroundTask"
			keyProperty="backgroundTaskId"
			modelVar="backgroundTask"
		>
			<liferay-ui:search-container-column-text
				cssClass="autofit-col-expand"
			>
				<div>
					<h5>
						<liferay-ui:message key="<%= backgroundTask.getName() %>" />
					</h5>

					<clay:label
						label="<%= LanguageUtil.get(request, backgroundTask.getStatusLabel()) %>"
						style="<%= UADExportProcessUtil.getStatusStyle(backgroundTask.getStatus()) %>"
					/>
				</div>
			</liferay-ui:search-container-column-text>

			<%
			Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy.MM.dd - hh:mm a", locale, themeDisplay.getTimeZone());
			%>

			<liferay-ui:search-container-column-text
				cssClass="autofit-col-expand"
			>
				<%= LanguageUtil.get(request, "start-date") + StringPool.COLON + dateFormat.format(backgroundTask.getCreateDate()) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="autofit-col-expand"
			>
				<%= LanguageUtil.get(request, "completion-date") + StringPool.COLON + dateFormat.format(backgroundTask.getCompletionDate()) %>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>