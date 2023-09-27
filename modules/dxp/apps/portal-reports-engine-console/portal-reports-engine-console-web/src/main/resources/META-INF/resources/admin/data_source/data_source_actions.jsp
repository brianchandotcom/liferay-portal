<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Source source = (Source)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= SourcePermissionChecker.contains(permissionChecker, source, ActionKeys.VIEW) %>">
		<portlet:actionURL name="/reports_admin/test_data_source" var="testConnectionURL">
			<portlet:param name="tabs1" value="sources" />
			<portlet:param name="sourceId" value="<%= String.valueOf(source.getSourceId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="test-database-connection"
			url="<%= testConnectionURL %>"
		/>
	</c:if>

	<c:if test="<%= SourcePermissionChecker.contains(permissionChecker, source, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/admin/data_source/edit_data_source.jsp" />
			<portlet:param name="sourceId" value="<%= String.valueOf(source.getSourceId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= SourcePermissionChecker.contains(permissionChecker, source, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= Source.class.getName() %>"
			modelResourceDescription="<%= source.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(source.getSourceId()) %>"
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

	<c:if test="<%= SourcePermissionChecker.contains(permissionChecker, source, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/reports_admin/delete_data_source" var="deleteURL">
			<portlet:param name="tabs1" value="sources" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sourceId" value="<%= String.valueOf(source.getSourceId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>