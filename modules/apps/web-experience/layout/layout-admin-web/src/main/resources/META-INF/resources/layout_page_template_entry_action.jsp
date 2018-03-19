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

LayoutPageTemplateEntry layoutPageTemplateEntry = (LayoutPageTemplateEntry)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editLayoutPageTemplateEntryURL">
			<portlet:param name="mvcPath" value="/edit_layout_page_template_entry.jsp" />
			<portlet:param name="layoutPageTemplateEntryId" value="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateEntryId()) %>" />
			<portlet:param name="layoutPageTemplateCollectionId" value="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editLayoutPageTemplateEntryURL %>"
		/>
	</c:if>

	<c:if test="<%= LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.UPDATE) %>">
		<portlet:actionURL name="/layout/update_layout_page_template_entry" var="updateLayoutPageTemplateEntryURL">
			<portlet:param name="layoutPageTemplateCollectionId" value="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()) %>" />
			<portlet:param name="layoutPageTemplateEntryId" value="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateEntryId()) %>" />
		</portlet:actionURL>

		<%
		Map<String, Object> updateLayoutPageTemplateEntryData = new HashMap<String, Object>();

		updateLayoutPageTemplateEntryData.put("form-submit-url", updateLayoutPageTemplateEntryURL.toString());
		updateLayoutPageTemplateEntryData.put("id-field-value", layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
		updateLayoutPageTemplateEntryData.put("main-field-value", layoutPageTemplateEntry.getName());
		%>

		<liferay-ui:icon
			cssClass='<%= renderResponse.getNamespace() + "update-layout-page-template-action-option" %>'
			data="<%= updateLayoutPageTemplateEntryData %>"
			message="rename"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= LayoutPageTemplateCollection.class.getName() %>"
			modelResourceDescription="<%= layoutPageTemplateEntry.getName() %>"
			resourcePrimKey="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateEntryId()) %>"
			var="layoutPageTemplateEntryPermissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= layoutPageTemplateEntryPermissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/layout/delete_layout_page_template_entry" var="deleteLayoutPageTemplateEntryURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="layoutPageTemplateEntryId" value="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteLayoutPageTemplateEntryURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>