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
LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = (LayoutPageTemplateDisplayContext)request.getAttribute(LayoutAdminWebKeys.LAYOUT_PAGE_TEMPLATE_DISPLAY_CONTEXT);

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
		<liferay-ui:icon
			cssClass='<%= renderResponse.getNamespace() + "update-layout-page-template-action-option" %>'
			data="<%= layoutPageTemplateDisplayContext.getUpdateLayoutPageTemplateEntryData(layoutPageTemplateEntry) %>"
			message="rename"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.UPDATE) %>">

		<%
		Map<String, Object> data = new HashMap<>();

		data.put("item-selector-url", layoutPageTemplateDisplayContext.getItemSelectorURL(layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
		data.put("layout-page-template-entry-id", layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
		%>

		<liferay-ui:icon
			cssClass="update-layout-page-template-entry-preview"
			data="<%= data %>"
			message="change-thumbnail"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= LayoutPageTemplateEntry.class.getName() %>"
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