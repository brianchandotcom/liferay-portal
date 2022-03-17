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
BatchPlannerLogDisplayContext batchPlannerLogDisplayContext = (BatchPlannerLogDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<BatchPlannerLogDisplay> batchPlannerLogDisplaySearchContainer = batchPlannerLogDisplayContext.getSearchContainer();
%>

<clay:navigation-bar
	navigationItems="<%= batchPlannerLogDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new BatchPlannerLogManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, batchPlannerLogDisplaySearchContainer) %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		cssClass="mt-3"
		searchContainer="<%= batchPlannerLogDisplaySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.batch.planner.web.internal.display.BatchPlannerLogDisplay"
			keyProperty="batchPlannerLogId"
			modelVar="batchPlannerLogDisplay"
		>
			<liferay-ui:search-container-column-text
				cssClass="important table-cell-expand"
				href='<%=
					PortletURLBuilder.createRenderURL(
						renderResponse
					).setMVCRenderCommandName(
						"/batch_planner/view_batch_planner_log"
					).setRedirect(
						currentURL
					).setParameter(
						"batchPlannerLogId", batchPlannerLogDisplay.getBatchPlannerLogId()
					).buildPortletURL()
				%>'
				name="title"
				value="<%= batchPlannerLogDisplay.getTitle() %>"
			/>

			<liferay-ui:search-container-column-text
				name="action"
				value="<%= batchPlannerLogDisplay.getAction() %>"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				value="<%= batchPlannerLogDisplayContext.getSimpleInternalClassName(batchPlannerLogDisplay.getInternalClassName()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="creation-date"
				value="<%= dateFormatDateTime.format(batchPlannerLogDisplay.getCreateDate()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="author"
				value="<%= PortalUtil.getUserEmailAddress(batchPlannerLogDisplay.getUserId()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="status"
			>
				<h6 class="text-uppercase <%= BatchPlannerLogConstants.getStatusCssClass(batchPlannerLogDisplay.getStatus()) %>">
					<liferay-ui:message key="<%= BatchPlannerLogConstants.getStatusLabel(batchPlannerLogDisplay.getStatus()) %>" />
				</h6>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="rows-processed"
				value="<%= String.valueOf(batchPlannerLogDisplay.getProcessedItemsCount()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="rows-failed"
				value="<%= String.valueOf(batchPlannerLogDisplay.getFailedItemsCount()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="total"
				value="<%= String.valueOf(batchPlannerLogDisplay.getTotalItemsCount()) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/batch_planner_log_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>