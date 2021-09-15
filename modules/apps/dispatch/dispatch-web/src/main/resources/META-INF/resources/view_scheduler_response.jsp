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
SchedulerResponseDisplayContext schedulerResponseDisplayContext = (SchedulerResponseDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:navigation-bar
	navigationItems="<%= schedulerResponseDisplayContext.getNavigationItems() %>"
/>

<div id="<portlet:namespace />scheduledTaskContainer">
	<div class="closed container" id="<portlet:namespace />infoPanelId">
		<liferay-ui:search-container
			id="schedulerResponses"
			searchContainer="<%= schedulerResponseDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse"
				modelVar="schedulerResponse"
			>
				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/scheduler_response_action.jsp"
				/>

				<liferay-ui:search-container-column-text
					cssClass="font-weight-bold table-cell-expand"
					name="name"
				>
					<liferay-ui:message key="<%= schedulerResponseDisplayContext.getSimpleName(schedulerResponse.getJobName()) %>" />

					<liferay-ui:icon-help message="<%= schedulerResponse.getJobName() %>" />
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="system"
					value='<%= LanguageUtil.get(request, "yes") %>'
				/>

				<liferay-ui:search-container-column-text
					name="next-fire-date"
					value="<%= schedulerResponseDisplayContext.getNextFireDateString(schedulerResponse) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="font-weight-bold table-cell-ws-nowrap"
					name="status"
				>

					<%
					TriggerState triggerState = schedulerResponseDisplayContext.getTriggerState(schedulerResponse);
					%>

					<h6 class="<%= (triggerState == TriggerState.NORMAL) ? "text-success" : "text-info" %>">
						<liferay-ui:message key="<%= triggerState.toString() %>" />
					</h6>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					cssClass="table-cell-ws-nowrap"
					path="/scheduler_response_buttons.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="list"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</div>
</div>