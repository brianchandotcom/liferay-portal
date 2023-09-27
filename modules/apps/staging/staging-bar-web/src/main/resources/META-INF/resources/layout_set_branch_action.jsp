<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

LayoutSetBranch layoutSetBranch = (LayoutSetBranch)row.getObject();

long currentLayoutSetBranchId = GetterUtil.getLong((String)request.getAttribute("view_layout_set_branches.jsp-currentLayoutSetBranchId"));
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= LayoutSetBranchPermissionUtil.contains(permissionChecker, layoutSetBranch, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editLayoutSetBranchURL">
			<portlet:param name="mvcRenderCommandName" value="/staging_bar/edit_layout_set_branch" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(layoutSetBranch.getGroupId()) %>" />
			<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editLayoutSetBranchURL %>"
		/>
	</c:if>

	<c:if test="<%= LayoutSetBranchPermissionUtil.contains(permissionChecker, layoutSetBranch, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= LayoutSetBranch.class.getName() %>"
			modelResourceDescription="<%= layoutSetBranchDisplayContext.getLayoutSetBranchDisplayName(layoutSetBranch) %>"
			resourcePrimKey="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>"
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

	<c:if test="<%= LayoutSetBranchPermissionUtil.contains(permissionChecker, layoutSetBranch, ActionKeys.MERGE) %>">
		<portlet:renderURL var="mergeLayoutSetBranchURL">
			<portlet:param name="mvcRenderCommandName" value="/staging_bar/merge_layout_set_branch" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(layoutSetBranch.getGroupId()) %>" />
			<portlet:param name="privateLayout" value="<%= String.valueOf(layoutSetBranch.isPrivateLayout()) %>" />
			<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="merge"
			url="<%= mergeLayoutSetBranchURL %>"
		/>
	</c:if>

	<c:if test="<%= !layoutSetBranch.isMaster() && LayoutSetBranchPermissionUtil.contains(permissionChecker, layoutSetBranch, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/staging_bar/delete_layout_set_branch" var="deleteLayoutSetBranchURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(layoutSetBranch.getGroupId()) %>" />
			<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
			<portlet:param name="currentLayoutSetBranchId" value="<%= String.valueOf(currentLayoutSetBranchId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteLayoutSetBranchURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>