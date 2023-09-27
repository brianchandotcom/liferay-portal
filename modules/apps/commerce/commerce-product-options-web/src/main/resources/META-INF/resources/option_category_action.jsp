<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			<portlet:param name="mvcRenderCommandName" value="/cp_specification_options/edit_cp_option_category" />
			<portlet:param name="cpOptionCategoryId" value="<%= String.valueOf(cpOptionCategory.getCPOptionCategoryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= CPOptionCategoryPermission.contains(permissionChecker, cpOptionCategory, ActionKeys.UPDATE) %>">
		<portlet:actionURL name="/cp_specification_options/edit_cp_option_category" var="deleteURL">
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