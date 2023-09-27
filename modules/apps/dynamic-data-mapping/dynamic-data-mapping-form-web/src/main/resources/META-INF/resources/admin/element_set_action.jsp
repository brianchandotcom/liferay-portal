<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
PortletURL portletURL = ddmFormAdminDisplayContext.getPortletURL();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMStructure ddmStructure = (DDMStructure)row.getObject();

FieldSetPermissionCheckerHelper fieldSetPermissionCheckerHelper = ddmFormAdminDisplayContext.getPermissionCheckerHelper();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= fieldSetPermissionCheckerHelper.isShowEditIcon(ddmStructure) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/admin/edit_element_set" />
			<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
			<portlet:param name="structureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= fieldSetPermissionCheckerHelper.isShowPermissionsIcon(ddmStructure) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDMStructure.class.getName() %>"
			modelResourceDescription="<%= ddmStructure.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(ddmStructure.getStructureId()) %>"
			var="permissionsElementSetURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsElementSetURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= fieldSetPermissionCheckerHelper.isShowDeleteIcon(ddmStructure) %>">
		<portlet:actionURL name="/dynamic_data_mapping_form/delete_structure" var="deleteURL">
			<portlet:param name="structureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>