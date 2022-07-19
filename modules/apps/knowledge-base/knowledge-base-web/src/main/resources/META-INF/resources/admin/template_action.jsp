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

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(KBWebKeys.SEARCH_CONTAINER_RESULT_ROW);

KBTemplate kbTemplate = (KBTemplate)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="actions"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= KBTemplatePermission.contains(permissionChecker, kbTemplate, KBActionKeys.VIEW) %>">
		<liferay-portlet:renderURL var="viewURL">
			<portlet:param name="mvcPath" value="/admin/common/view_template.jsp" />
			<portlet:param name="kbTemplateId" value="<%= String.valueOf(kbTemplate.getKbTemplateId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="view"
			url="<%= viewURL %>"
		/>
	</c:if>

	<c:if test="<%= KBTemplatePermission.contains(permissionChecker, kbTemplate, KBActionKeys.UPDATE) %>">
		<liferay-portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/admin/common/edit_template.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="kbTemplateId" value="<%= String.valueOf(kbTemplate.getKbTemplateId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= KBTemplatePermission.contains(permissionChecker, kbTemplate, KBActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= KBTemplate.class.getName() %>"
			modelResourceDescription="<%= kbTemplate.getTitle() %>"
			resourcePrimKey="<%= String.valueOf(kbTemplate.getKbTemplateId()) %>"
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

	<c:if test="<%= KBTemplatePermission.contains(permissionChecker, kbTemplate, KBActionKeys.DELETE) %>">
		<liferay-portlet:actionURL name="/knowledge_base/delete_kb_template" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="kbTemplateId" value="<%= String.valueOf(kbTemplate.getKbTemplateId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>