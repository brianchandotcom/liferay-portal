<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow resultRow = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

BatchPlannerPlanTemplateDisplay batchPlannerPlanTemplateDisplay = (BatchPlannerPlanTemplateDisplay)resultRow.getObject();
%>

<liferay-ui:icon-menu
	direction="right-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= BatchPlannerPlanPermission.contains(permissionChecker, batchPlannerPlanTemplateDisplay.getBatchPlannerPlanId(), ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= BatchPlannerPlan.class.getName() %>"
			modelResourceDescription="<%= batchPlannerPlanTemplateDisplay.getTitle() %>"
			resourcePrimKey="<%= String.valueOf(batchPlannerPlanTemplateDisplay.getBatchPlannerPlanId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="edit-permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= BatchPlannerPlanPermission.contains(permissionChecker, batchPlannerPlanTemplateDisplay.getBatchPlannerPlanId(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="/batch_planner/delete_batch_planner_plan_template" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="batchPlannerPlanId" value="<%= String.valueOf(batchPlannerPlanTemplateDisplay.getBatchPlannerPlanId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>