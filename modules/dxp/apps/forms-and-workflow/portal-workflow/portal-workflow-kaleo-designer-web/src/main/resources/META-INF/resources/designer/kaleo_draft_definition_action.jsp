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

KaleoDraftDefinition kaleoDraftDefinition = (KaleoDraftDefinition)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= KaleoDraftDefinitionPermission.contains(permissionChecker, kaleoDraftDefinition, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value='<%= "/designer/edit_kaleo_draft_definition.jsp" %>' />
			<portlet:param name="name" value="<%= kaleoDraftDefinition.getName() %>" />
			<portlet:param name="version" value="<%= String.valueOf(kaleoDraftDefinition.getVersion()) %>" />
			<portlet:param name="draftVersion" value="<%= String.valueOf(kaleoDraftDefinition.getDraftVersion()) %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			method="get"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= KaleoDraftDefinitionPermission.contains(permissionChecker, kaleoDraftDefinition, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= KaleoDraftDefinition.class.getName() %>"
			modelResourceDescription="<%= kaleoDraftDefinition.getName() %>"
			resourcePrimKey="<%= String.valueOf(kaleoDraftDefinition.getKaleoDraftDefinitionId()) %>"
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

	<c:if test="<%= (kaleoDraftDefinition.getVersion() == 0) && KaleoDraftDefinitionPermission.contains(permissionChecker, kaleoDraftDefinition, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteKaleoDraftDefinition" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="name" value="<%= kaleoDraftDefinition.getName() %>" />
			<portlet:param name="version" value="<%= String.valueOf(kaleoDraftDefinition.getVersion()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>