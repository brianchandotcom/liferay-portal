<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<portlet:actionURL name="/reports_admin/delete_definition" var="deleteURL">
			<portlet:param name="tabs1" value="definitions" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="definitionId" value="<%= String.valueOf(definition.getDefinitionId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>