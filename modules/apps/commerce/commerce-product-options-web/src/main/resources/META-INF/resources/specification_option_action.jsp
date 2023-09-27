<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			<portlet:param name="mvcRenderCommandName" value="/cp_specification_options/edit_cp_specification_option" />
			<portlet:param name="cpSpecificationOptionId" value="<%= String.valueOf(cpSpecificationOption.getCPSpecificationOptionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= CPSpecificationOptionPermission.contains(permissionChecker, cpSpecificationOption, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/cp_specification_options/edit_cp_specification_option" var="deleteURL">
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