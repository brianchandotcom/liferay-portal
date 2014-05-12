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

<%@ include file="/html/portlet/layout_prototypes/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

LayoutPrototype layoutPrototype = (LayoutPrototype)row.getObject();
%>

<liferay-ui:icon-menu direction="down" extended="<%= false %>" icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>" triggerCssClass="btn">
	<c:if test="<%= LayoutPrototypePermissionUtil.contains(permissionChecker, layoutPrototype.getLayoutPrototypeId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/layout_prototypes/edit_layout_prototype" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="layoutPrototypeId" value="<%= String.valueOf(layoutPrototype.getLayoutPrototypeId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= LayoutPrototypePermissionUtil.contains(permissionChecker, layoutPrototype.getLayoutPrototypeId(), ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= LayoutPrototype.class.getName() %>"
			modelResourceDescription="<%= layoutPrototype.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(layoutPrototype.getLayoutPrototypeId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			iconCssClass="icon-lock"
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, layoutPrototype.getGroup(), ActionKeys.EXPORT_IMPORT_LAYOUTS) %>">
		<portlet:renderURL var="exportURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="struts_action" value="/layout_prototypes/export_layouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(layoutPrototype.getGroupId()) %>" />
			<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
			<portlet:param name="rootNodeName" value="<%= layoutPrototype.getName(locale) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			cssClass="export-layout-prototype layout-prototype-action"
			iconCssClass="icon-arrow-down"
			message="export"
			method="get"
			url="<%= exportURL %>"
		/>
	</c:if>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, layoutPrototype.getGroup(), ActionKeys.EXPORT_IMPORT_LAYOUTS) %>">
		<portlet:renderURL var="importURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="struts_action" value="/layout_prototypes/import_layouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(layoutPrototype.getGroupId()) %>" />
			<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
			<portlet:param name="rootNodeName" value="<%= layoutPrototype.getName(locale) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			cssClass="import-layout-prototype layout-prototype-action"
			iconCssClass="icon-arrow-up"
			message="import"
			method="get"
			url="<%= importURL %>"
		/>
	</c:if>

	<c:if test="<%= LayoutPrototypePermissionUtil.contains(permissionChecker, layoutPrototype.getLayoutPrototypeId(), ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/layout_prototypes/edit_layout_prototype" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="layoutPrototypeIds" value="<%= String.valueOf(layoutPrototype.getLayoutPrototypeId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>