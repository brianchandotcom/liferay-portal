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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

LayoutPageTemplateCollection layoutPageTemplateCollection = (LayoutPageTemplateCollection)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= LayoutPageTemplateCollectionPermission.contains(permissionChecker, layoutPageTemplateCollection, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editLayoutPageTemplateCollectionURL">
			<portlet:param name="mvcPath" value="/edit_layout_page_template_collection.jsp" />
			<portlet:param name="tabs1" value="page-templates" />
			<portlet:param name="layoutPageTemplateCollectionId" value="<%= String.valueOf(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editLayoutPageTemplateCollectionURL %>"
		/>
	</c:if>

	<c:if test="<%= LayoutPageTemplateCollectionPermission.contains(permissionChecker, layoutPageTemplateCollection, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= LayoutPageTemplateCollection.class.getName() %>"
			modelResourceDescription="<%= layoutPageTemplateCollection.getName() %>"
			resourcePrimKey="<%= String.valueOf(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()) %>"
			var="layoutPageTemplateCollectionPermissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= layoutPageTemplateCollectionPermissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= LayoutPageTemplateCollectionPermission.contains(permissionChecker, layoutPageTemplateCollection, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/layout/delete_layout_page_template_collection" var="deleteLayoutPageTemplateCollectionURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="layoutPageTemplateCollectionId" value="<%= String.valueOf(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteLayoutPageTemplateCollectionURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>