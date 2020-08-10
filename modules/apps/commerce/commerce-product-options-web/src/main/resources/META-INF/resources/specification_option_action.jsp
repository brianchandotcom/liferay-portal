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

CPSpecificationOption cpSpecificationOption = null;

if (row != null) {
	cpSpecificationOption = (CPSpecificationOption)row.getObject();
}
else {
	cpSpecificationOption = (CPSpecificationOption)request.getAttribute("info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= CPSpecificationOptionPermission.contains(permissionChecker, cpSpecificationOption, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="editProductSpecificationOption" />
			<portlet:param name="cpSpecificationOptionId" value="<%= String.valueOf(cpSpecificationOption.getCPSpecificationOptionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= CPSpecificationOptionPermission.contains(permissionChecker, cpSpecificationOption, ActionKeys.DELETE) %>">
		<portlet:actionURL name="editProductSpecificationOption" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="cpSpecificationOptionId" value="<%= String.valueOf(cpSpecificationOption.getCPSpecificationOptionId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>

	<c:if test="<%= CPSpecificationOptionPermission.contains(permissionChecker, cpSpecificationOption, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= CPSpecificationOption.class.getName() %>"
			modelResourceDescription="<%= cpSpecificationOption.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(cpSpecificationOption.getCPSpecificationOptionId()) %>"
			var="permissionsSpecificationURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsSpecificationURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>
</liferay-ui:icon-menu>