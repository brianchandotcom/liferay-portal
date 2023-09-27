<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Role role = (Role)row.getObject();

String name = role.getName();

RoleTypeContributor currentRoleTypeContributor = RoleTypeContributorRetrieverUtil.getCurrentRoleTypeContributor(request);
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message='<%= LanguageUtil.format(request, "show-actions-for-x", role.getTitle(locale)) %>'
	showWhenSingleIcon="<%= true %>"
	triggerCssClass="lfr-portal-tooltip"
>
	<c:if test="<%= RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_role.jsp" />
			<portlet:param name="tabs1" value="details" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= !name.equals(RoleConstants.OWNER) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.PERMISSIONS) %>">

		<%
		int[] roleTypes = {role.getType()};

		if (role.getType() != RoleConstants.TYPE_REGULAR) {
			roleTypes = new int[] {RoleConstants.TYPE_REGULAR, role.getType()};
		}
		%>

		<liferay-security:permissionsURL
			modelResource="<%= Role.class.getName() %>"
			modelResourceDescription="<%= role.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(role.getRoleId()) %>"
			roleTypes="<%= roleTypes %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= currentRoleTypeContributor.isAllowDelete(role) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteRole" var="deleteRoleURL">
			<portlet:param name="mvcPath" value="/view.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			confirmation="are-you-sure-you-want-to-delete-this-role?-task-assignments-may-be-deleted"
			url="<%= deleteRoleURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>