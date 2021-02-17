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
ScheduledTaskDispatchTriggerDisplayContext scheduledTaskDispatchTriggerDisplayContext = (ScheduledTaskDispatchTriggerDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = scheduledTaskDispatchTriggerDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "liferayScheduledTasks");
portletURL.setParameter("tabs1", "scheduled-task");
%>

<clay:navigation-bar
	navigationItems="<%= scheduledTaskDispatchTriggerDisplayContext.getNavigationItems() %>"
/>

<div id="<portlet:namespace />scheduledTaskContainer">
	<div class="closed container" id="<portlet:namespace />infoPanelId">
		<liferay-ui:search-container
			id="scheduledTasks"
			searchContainer="<%= scheduledTaskDispatchTriggerDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dispatch.core.scheduler.ScheduledTaskDispatchTrigger"
				keyProperty="dispatchTriggerId"
				modelVar="scheduledTaskDispatchTrigger"
			>
				<liferay-ui:search-container-column-text
					cssClass="important table-cell-expand"
					name="name"
				>
					<liferay-ui:message key="<%= scheduledTaskDispatchTrigger.getSimpleName() %>" />

					<liferay-ui:icon-help message="<%= scheduledTaskDispatchTrigger.getName() %>" />
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="system"
					value='<%= scheduledTaskDispatchTrigger.isSystem() ? LanguageUtil.get(request, "yes") : LanguageUtil.get(request, "no") %>'
				/>

				<liferay-ui:search-container-column-text
					name="next-fire-date"
					value="<%= scheduledTaskDispatchTriggerDisplayContext.getNextFireDateString(scheduledTaskDispatchTrigger) %>"
				/>

				<liferay-ui:search-container-column-text
					name="status"
				>

					<%
					TriggerState triggerState = scheduledTaskDispatchTriggerDisplayContext.getTriggerState(scheduledTaskDispatchTrigger);
					%>

					<h6 class="<%= (triggerState == TriggerState.NORMAL) ? "text-success" : "text-info" %>">
						<liferay-ui:message key="<%= triggerState.toString() %>" />
					</h6>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					cssClass="table-cell-expand"
					path="/scheduled_task_buttons.jsp"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/scheduled_task_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="list"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</div>
</div>