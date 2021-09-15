<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
			url="javascript:;"
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