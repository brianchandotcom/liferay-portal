<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Definition definition = (Definition)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= DefinitionPermissionChecker.contains(permissionChecker, definition, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/admin/definition/edit_definition.jsp" />
			<portlet:param name="definitionId" value="<%= String.valueOf(definition.getDefinitionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= DefinitionPermissionChecker.contains(permissionChecker, definition, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= Definition.class.getName() %>"
			modelResourceDescription="<%= definition.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(definition.getDefinitionId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= DefinitionPermissionChecker.contains(permissionChecker, definition, ReportsActionKeys.ADD_REPORT) %>">
		<portlet:renderURL var="addReportURL">
			<portlet:param name="mvcPath" value="/admin/report/generate_report.jsp" />
			<portlet:param name="definitionId" value="<%= String.valueOf(definition.getDefinitionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="add-report"
			url="<%= addReportURL %>"
		/>
	</c:if>

	<c:if test="<%= DefinitionPermissionChecker.contains(permissionChecker, definition, ReportsActionKeys.ADD_REPORT) %>">
		<portlet:renderURL var="addScheduleURL">
			<portlet:param name="mvcPath" value="/admin/report/edit_schedule.jsp" />
			<portlet:param name="definitionId" value="<%= String.valueOf(definition.getDefinitionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="add-schedule"
			url="<%= addScheduleURL %>"
		/>
	</c:if>

	<c:if test="<%= DefinitionPermissionChecker.contains(permissionChecker, definition, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteDefinition" var="deleteURL">
			<portlet:param name="tabs1" value="definitions" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="definitionId" value="<%= String.valueOf(definition.getDefinitionId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>