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

<%@ include file="/designer/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)row.getObject();

KaleoDefinition kaleoDefinition = kaleoDefinitionVersion.getKaleoDefinition();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= KaleoDefinitionVersionPermission.contains(permissionChecker, kaleoDefinitionVersion, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value='<%= "/designer/edit_kaleo_definition_version.jsp" %>' />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="name" value="<%= kaleoDefinitionVersion.getName() %>" />
			<portlet:param name="draftVersion" value="<%= kaleoDefinitionVersion.getVersion() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			method="get"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= KaleoDefinitionVersionPermission.contains(permissionChecker, kaleoDefinitionVersion, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= KaleoDefinitionVersion.class.getName() %>"
			modelResourceDescription="<%= kaleoDefinitionVersion.getName() %>"
			resourcePrimKey="<%= String.valueOf(kaleoDefinitionVersion.getKaleoDefinitionVersionId()) %>"
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

	<c:choose>
		<c:when test="<%= (kaleoDefinition != null) && kaleoDefinition.isActive() && KaleoDefinitionVersionPermission.contains(permissionChecker, kaleoDefinitionVersion, ActionKeys.UPDATE) %>">
			<portlet:actionURL name="unpublishKaleoDefinitionVersion" var="unpublishURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="name" value="<%= kaleoDefinitionVersion.getName() %>" />
				<portlet:param name="version" value="<%= String.valueOf(kaleoDefinition.getVersion()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message="unpublish"
				url="<%= unpublishURL %>"
			/>
		</c:when>
		<c:otherwise>
			<c:if test="<%= KaleoDefinitionVersionPermission.contains(permissionChecker, kaleoDefinitionVersion, ActionKeys.DELETE) %>">
				<portlet:actionURL name="deleteKaleoDefinitionVersion" var="deleteURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="name" value="<%= kaleoDefinitionVersion.getName() %>" />
					<portlet:param name="draftVersion" value="<%= kaleoDefinitionVersion.getVersion() %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="delete"
					onClick='<%= renderResponse.getNamespace() + "confirmDeleteDefinition('" + deleteURL + "'); return false;" %>'
					url="<%= deleteURL %>"
				/>
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>