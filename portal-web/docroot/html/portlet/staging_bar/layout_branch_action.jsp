<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/staging_bar/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

LayoutRevision rootLayoutRevision = (LayoutRevision)row.getObject();

LayoutBranch layoutBranch = rootLayoutRevision.getLayoutBranch();

long currentLayoutBranchId = GetterUtil.getLong((String)request.getAttribute("view_layout_branches.jsp-currentLayoutBranchId"));
%>

<liferay-ui:icon-menu>
	<c:if test="<%= LayoutBranchPermissionUtil.contains(permissionChecker, layoutBranch, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
			<portlet:param name="struts_action" value="/staging_bar/edit_layout_branch" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(layoutBranch.getGroupId()) %>" />
			<portlet:param name="layoutBranchId" value="<%= String.valueOf(layoutBranch.getLayoutBranchId()) %>" />
		</portlet:renderURL>

		<%
		String taglibURL = "javascript:Liferay.StagingBar.updateBranch({uri: '" + HtmlUtil.escapeJS(editURL) + "', dialogTitle: '" + HtmlUtil.escapeJS(LanguageUtil.get(pageContext, "update-page-variation")) + "'});";
		%>

		<liferay-ui:icon
			image="edit"
			url="<%= taglibURL %>"
		/>

		<c:if test="<%= !rootLayoutRevision.isPending() && !layoutBranch.isMaster() && !rootLayoutRevision.isHead() && LayoutBranchPermissionUtil.contains(permissionChecker, layoutBranch, ActionKeys.DELETE) %>">
			<portlet:actionURL var="deleteURL">
				<portlet:param name="struts_action" value="/staging_bar/edit_layout_branch" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(layoutBranch.getGroupId()) %>" />
				<portlet:param name="layoutBranchId" value="<%= String.valueOf(layoutBranch.getLayoutBranchId()) %>" />
				<portlet:param name="currentLayoutBranchId" value="<%= String.valueOf(currentLayoutBranchId) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				url="<%= deleteURL %>"
			/>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>