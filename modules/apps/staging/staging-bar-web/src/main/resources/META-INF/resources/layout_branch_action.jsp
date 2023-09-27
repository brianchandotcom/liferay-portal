<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

LayoutRevision rootLayoutRevision = (LayoutRevision)row.getObject();

LayoutBranch layoutBranch = rootLayoutRevision.getLayoutBranch();

long currentLayoutBranchId = GetterUtil.getLong((String)request.getAttribute("view_layout_branches.jsp-currentLayoutBranchId"));
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= LayoutBranchPermissionUtil.contains(permissionChecker, layoutBranch, ActionKeys.UPDATE) %>">
		<liferay-portlet:renderURL var="editLayoutBranchURL">
			<portlet:param name="mvcRenderCommandName" value="/staging_bar/edit_layout_branch" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(layoutBranch.getGroupId()) %>" />
			<portlet:param name="layoutBranchId" value="<%= String.valueOf(layoutBranch.getLayoutBranchId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editLayoutBranchURL %>"
		/>

		<c:if test="<%= !rootLayoutRevision.isPending() && !layoutBranch.isMaster() && LayoutBranchPermissionUtil.contains(permissionChecker, layoutBranch, ActionKeys.DELETE) %>">
			<portlet:actionURL name="/staging_bar/delete_layout_branch" var="deleteLayoutBranchURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(layoutBranch.getGroupId()) %>" />
				<portlet:param name="layoutBranchId" value="<%= String.valueOf(layoutBranch.getLayoutBranchId()) %>" />
				<portlet:param name="currentLayoutBranchId" value="<%= String.valueOf(currentLayoutBranchId) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				url="<%= deleteLayoutBranchURL %>"
			/>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>