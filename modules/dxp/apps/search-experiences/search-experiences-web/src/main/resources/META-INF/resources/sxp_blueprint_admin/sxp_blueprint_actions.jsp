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

SXPBlueprint sxpBlueprint = (SXPBlueprint)row.getObject();

long sxpBlueprintId = sxpBlueprint.getSXPBlueprintId();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= SXPBlueprintEntryPermission.contains(permissionChecker, sxpBlueprint, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editSXPBlueprintURL">
			<portlet:param name="mvcRenderCommandName" value="/sxp_blueprint_admin/edit_sxp_blueprint" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sxpBlueprintId" value="<%= String.valueOf(sxpBlueprintId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editSXPBlueprintURL %>"
		/>
	</c:if>

	<c:if test="<%= SXPBlueprintEntryPermission.contains(permissionChecker, themeDisplay.getCompanyGroupId(), SXPActionKeys.ADD_SXP_BLUEPRINT) %>">
		<portlet:actionURL name="/sxp_blueprint_admin/copy_sxp_blueprint" var="copySXPBlueprintURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sxpBlueprintId" value="<%= String.valueOf(sxpBlueprintId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="copy"
			url="<%= copySXPBlueprintURL %>"
		/>
	</c:if>

	<portlet:resourceURL id="/sxp_blueprint_admin/export_sxp_blueprint" var="exportSXPBlueprintURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="sxpBlueprintId" value="<%= String.valueOf(sxpBlueprintId) %>" />
	</portlet:resourceURL>

	<liferay-ui:icon
		message="export"
		url="<%= exportSXPBlueprintURL %>"
	/>

	<c:if test="<%= SXPBlueprintEntryPermission.contains(permissionChecker, themeDisplay.getCompanyGroupId(), ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= SXPBlueprint.class.getName() %>"
			modelResourceDescription="<%= sxpBlueprint.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(sxpBlueprintId) %>"
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

	<c:if test="<%= SXPBlueprintEntryPermission.contains(permissionChecker, sxpBlueprint, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/sxp_blueprint_admin/delete_sxp_blueprint" var="deleteSXPBlueprintURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sxpBlueprintId" value="<%= String.valueOf(sxpBlueprintId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteSXPBlueprintURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>