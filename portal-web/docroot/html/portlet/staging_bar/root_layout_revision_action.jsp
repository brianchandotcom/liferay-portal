<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
%>

<liferay-ui:icon-menu>
	<c:if test="<%= !rootLayoutRevision.isPending() && LayoutPermissionUtil.contains(permissionChecker, rootLayoutRevision.getPlid(), ActionKeys.UPDATE) %>">

		<%
		LayoutBranch layoutBranch = LayoutBranchLocalServiceUtil.getLayoutBranch(rootLayoutRevision.getLayoutBranchId());
		%>

		<c:if test="<%= !layoutBranch.isMaster() && !rootLayoutRevision.isHead() && GroupPermissionUtil.contains(permissionChecker, rootLayoutRevision.getGroupId(), ActionKeys.DELETE_LAYOUT_BRANCH) %>">
			<portlet:actionURL var="deleteURL">
				<portlet:param name="struts_action" value="/staging_bar/edit_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="delete_layout_branch" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(rootLayoutRevision.getGroupId()) %>" />
				<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(rootLayoutRevision.getLayoutSetBranchId()) %>" />
				<portlet:param name="layoutBranchId" value="<%= String.valueOf(rootLayoutRevision.getLayoutBranchId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				url="<%= deleteURL %>"
			/>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>