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

CPOptionCategory cpOptionCategory = null;

if (row != null) {
	cpOptionCategory = (CPOptionCategory)row.getObject();
}
else {
	cpOptionCategory = (CPOptionCategory)request.getAttribute("info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= CPOptionCategoryPermission.contains(permissionChecker, cpOptionCategory, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="editProductOptionCategory" />
			<portlet:param name="cpOptionCategoryId" value="<%= String.valueOf(cpOptionCategory.getCPOptionCategoryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= CPOptionCategoryPermission.contains(permissionChecker, cpOptionCategory, ActionKeys.UPDATE) %>">
		<portlet:actionURL name="editProductOptionCategory" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="cpOptionCategoryId" value="<%= String.valueOf(cpOptionCategory.getCPOptionCategoryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>

	<c:if test="<%= CPOptionCategoryPermission.contains(permissionChecker, cpOptionCategory, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= CPOptionCategory.class.getName() %>"
			modelResourceDescription="<%= cpOptionCategory.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(cpOptionCategory.getCPOptionCategoryId()) %>"
			var="permissionsOptionCategoryURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsOptionCategoryURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>
</liferay-ui:icon-menu>