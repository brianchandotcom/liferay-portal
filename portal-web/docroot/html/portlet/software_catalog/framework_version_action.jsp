<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/software_catalog/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SCFrameworkVersion frameworkVersion = (SCFrameworkVersion)row.getObject();

String frameworkVersionId = String.valueOf(frameworkVersion.getFrameworkVersionId());
%>

<liferay-ui:icon-menu>
	<c:if test="<%= SCFrameworkVersionPermission.contains(permissionChecker, frameworkVersion, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/software_catalog/edit_framework_version" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="frameworkVersionId" value="<%= frameworkVersionId %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= SCFrameworkVersionPermission.contains(permissionChecker, frameworkVersion, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= SCFrameworkVersion.class.getName() %>"
			modelResourceDescription="<%= frameworkVersion.getName() %>"
			resourcePrimKey="<%= frameworkVersionId %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon
			image="permissions"
			url="<%= permissionsURL %>"
		/>
	</c:if>

	<c:if test="<%= SCFrameworkVersionPermission.contains(permissionChecker, frameworkVersion, ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/software_catalog/edit_framework_version" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="frameworkVersionId" value="<%= frameworkVersionId %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>