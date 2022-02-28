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
ScheduledPublishProcessesDisplayContext scheduledPublishProcessesDisplayContext = new ScheduledPublishProcessesDisplayContext(group, request, liferayPortletResponse, liveGroupId);
%>

<div id="<portlet:namespace />scheduledPublishProcessesSearchContainer">
	<liferay-ui:search-container
		id="scheduledPublishProcesses"
		searchContainer="<%= scheduledPublishProcessesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse"
			modelVar="schedulerResponse"
		>
			<liferay-ui:search-container-column-text
				cssClass="background-task-user-column"
				name="user"
			>

				<%
				Message message = schedulerResponse.getMessage();

				ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(GetterUtil.getLong(message.getPayload()));
				%>

				<liferay-ui:user-display
					displayStyle="3"
					showUserDetails="<%= false %>"
					showUserName="<%= false %>"
					userId="<%= exportImportConfiguration.getUserId() %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="title"
			>

				<%
				String description = schedulerResponse.getDescription();

				if (description.equals(StringPool.BLANK)) {
					description = LanguageUtil.get(request, "untitled-scheduled-publish-process");
				}
				%>

				<liferay-ui:message key="<%= HtmlUtil.escape(description) %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				name="create-date"
				orderable="<%= true %>"
				value="<%= SchedulerEngineHelperUtil.getStartTime(schedulerResponse) %>"
			/>

			<%
			Date endDate = SchedulerEngineHelperUtil.getEndTime(schedulerResponse);
			%>

			<c:choose>
				<c:when test="<%= endDate != null %>">
					<liferay-ui:search-container-column-date
						name="end-date"
						orderable="<%= true %>"
						value="<%= SchedulerEngineHelperUtil.getEndTime(schedulerResponse) %>"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						name="end-date"
					>
						<liferay-ui:message key='<%= LanguageUtil.get(request, "no-end-date") %>' />
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>

			<liferay-ui:search-container-column-text
				align="right"
			>
				<liferay-ui:icon-menu
					direction="left-side"
					icon="<%= StringPool.BLANK %>"
					markupView="lexicon"
					message="<%= StringPool.BLANK %>"
					showWhenSingleIcon="<%= true %>"
				>
					<portlet:renderURL var="deleteScheduledPublicationRedirectURL">
						<portlet:param name="mvcPath" value="/view.jsp" />
						<portlet:param name="tabs1" value="scheduled" />
					</portlet:renderURL>

					<portlet:actionURL name="/staging_processes/publish_layouts" var="deleteScheduledPublicationURL">
						<portlet:param name="cmd" value="<%= scheduledPublishProcessesDisplayContext.getCmd() %>" />
						<portlet:param name="stagingGroupId" value="<%= String.valueOf(stagingGroupId) %>" />
						<portlet:param name="jobName" value="<%= schedulerResponse.getJobName() %>" />
						<portlet:param name="redirect" value="<%= deleteScheduledPublicationRedirectURL %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						message="delete"
						url="<%= deleteScheduledPublicationURL %>"
					/>
				</liferay-ui:icon-menu>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>