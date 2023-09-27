<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= permissionChecker.isCompanyAdmin() %>">
		<liferay-portlet:renderURL portletName="<%= KaleoDesignerPortletKeys.KALEO_DESIGNER %>" var="editURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/designer/edit_kaleo_definition_version.jsp" />
			<portlet:param name="tabs1" value="unpublished" />
			<portlet:param name="closeRedirect" value='<%= (String)row.getParameter("backURL") %>' />
			<portlet:param name="historyKey" value="workflow" />
			<portlet:param name="name" value="<%= kaleoDefinitionVersion.getName() %>" />
			<portlet:param name="draftVersion" value="<%= kaleoDefinitionVersion.getVersion() %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			onClick='<%= "javascript:" + liferayPortletResponse.getNamespace() + "editWorkflow('" + editURL + "');" %>'
			url="javascript:void(0);"
		/>

		<portlet:actionURL name="/kaleo_forms_admin/delete_kaleo_definition_versions" var="deleteURL">
			<portlet:param name="tabs1" value="unpublished" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="historyKey" value="workflow" />
			<portlet:param name="name" value="<%= kaleoDefinitionVersion.getName() %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>