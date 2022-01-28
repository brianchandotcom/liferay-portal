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

SearchContainer<BatchPlannerPlan> batchPlannerPlanSearchContainer = batchPlannerPlanDisplayContext.getSearchContainer();

BatchPlannerPlanManagementToolbarDisplayContext batchPlannerPlanManagementToolbarDisplayContext = new BatchPlannerPlanManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, batchPlannerPlanSearchContainer);
%>

<clay:navigation-bar
	navigationItems="<%= batchPlannerPlanDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= batchPlannerPlanManagementToolbarDisplayContext %>"
	propsTransformer="js/BatchPlannerPlanManagementToolbarPropsTransformer"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="batchPlannerPlanIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= batchPlannerPlanSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.batch.planner.model.BatchPlannerPlan"
				keyProperty="batchPlannerPlanId"
				modelVar="batchPlannerPlan"
			>

				<%
				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(batchPlannerPlanManagementToolbarDisplayContext.getAvailableActions(), StringPool.COMMA)
					).build());
				%>

				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value='<%= batchPlannerPlan.isExport() ?"/batch_planner/edit_export_batch_planner_plan" : "/batch_planner/edit_import_batch_planner_plan" %>' />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="batchPlannerPlanId" value="<%= String.valueOf(batchPlannerPlan.getBatchPlannerPlanId()) %>" />
					<portlet:param name="editable" value="true" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					cssClass="important"
					href="<%= rowURL %>"
					name="name"
					value="<%= HtmlUtil.escape(batchPlannerPlan.getName()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="create-date"
					value="<%= dateFormatDateTime.format(batchPlannerPlan.getCreateDate()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="action"
					value='<%= LanguageUtil.get(request, batchPlannerPlan.isExport() ? "export" : "import") %>'
				/>

				<liferay-ui:search-container-column-text
					name="type"
					value="<%= batchPlannerPlanDisplayContext.getSimpleInternalClassName(batchPlannerPlan.getInternalClassName()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="format"
					value="<%= batchPlannerPlan.getExternalType() %>"
				/>

				<liferay-ui:search-container-column-text
					name="user"
					value="<%= batchPlannerPlan.getUserName() %>"
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
	</aui:form>
</clay:container-fluid>