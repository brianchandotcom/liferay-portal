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

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SXPElement sxpElement = (SXPElement)row.getObject();

long sxpElementId = sxpElement.getSXPElementId();

long companyGroupId = themeDisplay.getCompanyGroupId();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= SXPElementEntryPermission.contains(permissionChecker, sxpElement, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editSXPElementURL">
			<portlet:param name="mvcRenderCommandName" value="/sxp_blueprint_admin/edit_sxp_element" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sxpElementId" value="<%= String.valueOf(sxpElementId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message='<%= sxpElement.getReadOnly() ? "view" : "edit" %>'
			url="<%= editSXPElementURL %>"
		/>
	</c:if>

	<c:if test="<%= SXPElementEntryPermission.contains(permissionChecker, companyGroupId, SXPActionKeys.ADD_SXP_ELEMENT) %>">
		<portlet:actionURL name="/sxp_blueprint_admin/copy_sxp_element" var="copySXPElementURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sxpElementId" value="<%= String.valueOf(sxpElementId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="copy"
			url="<%= copySXPElementURL %>"
		/>
	</c:if>

	<portlet:resourceURL id="/sxp_blueprint_admin/export_sxp_element" var="exportSXPElementURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="sxpElementId" value="<%= String.valueOf(sxpElementId) %>" />
	</portlet:resourceURL>

	<liferay-ui:icon
		message="export"
		url="<%= exportSXPElementURL %>"
	/>

	<c:if test="<%= SXPElementEntryPermission.contains(permissionChecker, companyGroupId, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= SXPElement.class.getName() %>"
			modelResourceDescription="<%= sxpElement.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(sxpElementId) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			label="<%= true %>"
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= SXPElementEntryPermission.contains(permissionChecker, sxpElement, ActionKeys.UPDATE) %>">
		<portlet:actionURL name="/sxp_blueprint_admin/edit_sxp_element" var="hideSXPElementURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sxpElementId" value="<%= String.valueOf(sxpElementId) %>" />
			<portlet:param name="<%= Constants.CMD %>" value="hide" />
			<portlet:param name="hidden" value="<%= String.valueOf(!sxpElement.getHidden()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= sxpElement.getHidden() ? "show" : "hide" %>'
			url="<%= hideSXPElementURL %>"
		/>
	</c:if>

	<c:if test="<%= SXPElementEntryPermission.contains(permissionChecker, sxpElement, ActionKeys.DELETE) && !sxpElement.getReadOnly() %>">
		<portlet:actionURL name="/sxp_blueprint_admin/element_sxp_element" var="deleteSXPElementURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sxpElementId" value="<%= String.valueOf(sxpElementId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteSXPElementURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>