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
		<portlet:actionURL name="testDataSource" var="testConnectionURL">
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
		<portlet:actionURL name="deleteDataSource" var="deleteURL">
			<portlet:param name="tabs1" value="sources" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sourceId" value="<%= String.valueOf(source.getSourceId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>