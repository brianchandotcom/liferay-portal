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
BatchPlannerPlanDisplayContext batchPlannerPlanDisplayContext = (BatchPlannerPlanDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<BatchPlannerPlanDisplay> batchPlannerPlanDisplaySearchContainer = batchPlannerPlanDisplayContext.getSearchContainer();
%>

<clay:navigation-bar
	navigationItems="<%= batchPlannerPlanDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new BatchPlannerPlanManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, batchPlannerPlanDisplaySearchContainer) %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		cssClass="mt-3"
		searchContainer="<%= batchPlannerPlanDisplaySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.batch.planner.web.internal.display.BatchPlannerPlanDisplay"
			keyProperty="batchPlannerPlanId"
			modelVar="batchPlannerPlanDisplay"
		>
			<liferay-ui:search-container-column-text
				cssClass="font-weight-bold important table-cell-expand"
				href='<%=
					PortletURLBuilder.createRenderURL(
						renderResponse
					).setMVCRenderCommandName(
						"/batch_planner/view_batch_planner_plan"
					).setRedirect(
						currentURL
					).setParameter(
						"batchPlannerPlanId", batchPlannerPlanDisplay.getBatchPlannerPlanId()
					).buildPortletURL()
				%>'
				name="name"
				value="<%= batchPlannerPlanDisplay.getTitle() %>"
			/>

			<liferay-ui:search-container-column-text
				name="action"
				value="<%= LanguageUtil.get(request, batchPlannerPlanDisplay.getAction()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				value="<%= batchPlannerPlanDisplayContext.getSimpleInternalClassName(batchPlannerPlanDisplay.getInternalClassName()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="creation-date"
				value="<%= dateFormatDateTime.format(batchPlannerPlanDisplay.getCreateDate()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="author"
				value="<%= PortalUtil.getUserEmailAddress(batchPlannerPlanDisplay.getUserId()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="status"
			>
				<h6 class="text-uppercase <%= BatchPlannerPlanConstants.getStatusCssClass(batchPlannerPlanDisplay.getStatus()) %>">
					<liferay-ui:message key="<%= BatchPlannerPlanConstants.getStatusLabel(batchPlannerPlanDisplay.getStatus()) %>" />
				</h6>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="rows-processed"
				value="<%= String.valueOf(batchPlannerPlanDisplay.getProcessedItemsCount()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="rows-failed"
				value="<%= String.valueOf(batchPlannerPlanDisplay.getFailedItemsCount()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="total"
				value="<%= String.valueOf(batchPlannerPlanDisplay.getTotalItemsCount()) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/batch_planner_plan_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>