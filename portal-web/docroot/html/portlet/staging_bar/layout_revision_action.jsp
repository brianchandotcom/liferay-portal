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

LayoutRevision layoutRevision = (LayoutRevision)row.getObject();

long layoutRevisionId = StagingUtil.getRecentLayoutRevisionId(request, layoutRevision.getLayoutSetBranchId(), layoutRevision.getPlid());

List<LayoutRevision> pendingLayoutRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutRevision.getLayoutSetBranchId(), layoutRevision.getPlid(), WorkflowConstants.STATUS_PENDING);

boolean updateRecentLayoutRevisionId = false;

if (layoutRevision.getLayoutRevisionId() == layoutRevisionId) {
	updateRecentLayoutRevisionId = true;
}
%>

<liferay-ui:icon-menu showWhenSingleIcon="<%= true %>">
	<c:if test="<%= !layoutRevision.isPending() && LayoutPermissionUtil.contains(permissionChecker, layoutRevision.getPlid(), ActionKeys.UPDATE) %>">
		<c:if test="<%= pendingLayoutRevisions.isEmpty() && !layoutRevision.isHead() %>">
			<portlet:actionURL var="publishURL">
				<portlet:param name="<%= Constants.CMD %>" value="update_layout_revision" />
				<portlet:param name="redirect" value="<%= PortalUtil.getLayoutFullURL(themeDisplay) %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(layoutRevision.getGroupId()) %>" />
				<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
				<portlet:param name="major" value="true" />
				<portlet:param name="workflowAction" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />
			</portlet:actionURL>

			<%
			String taglibURL = "javascript:submitForm(document.hrefFm, '" + HttpUtil.encodeURL(publishURL) + "');";
			%>

			<liferay-ui:icon
				image='<%= WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, LayoutRevision.class.getName()) ? "../aui/shuffle" : "../aui/circle-check" %>'
				message='<%= WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, LayoutRevision.class.getName()) ? "submit-for-publication" : "mark-as-ready-for-publication" %>'
				url="<%= taglibURL %>"
			/>
		</c:if>

		<%--<c:if test="<%= !layoutRevision.isMajor() && !layoutRevision.isHead() %>">
			<portlet:actionURL var="saveURL">
				<portlet:param name="struts_action" value="/staging_bar/edit_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="update_layout_revision" />
				<portlet:param name="redirect" value="<%= PortalUtil.getLayoutFullURL(themeDisplay) %>" />
				<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
				<portlet:param name="major" value="true" />
				<portlet:param name="workflowAction" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />
			</portlet:actionURL>

			<%
			String taglibURL = "javascript:submitForm(document.hrefFm, '" + HttpUtil.encodeURL(saveURL) + "');";
			%>

			<liferay-ui:icon
				image="export"
				message="save"
				url="<%= taglibURL %>"
			/>
		</c:if>--%>

		<c:if test="<%= !layoutRevision.isHead() && !layoutRevision.isPending() %>">
			<portlet:actionURL var="deleteURL">
				<portlet:param name="struts_action" value="/staging_bar/edit_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="delete_layout_revision" />
				<portlet:param name="redirect" value="<%= PortalUtil.getLayoutFullURL(themeDisplay) %>" />
				<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
				<portlet:param name="updateRecentLayoutRevisionId" value="<%= String.valueOf(updateRecentLayoutRevisionId) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete url="<%= deleteURL %>" />
		</c:if>
	</c:if>
</liferay-ui:icon-menu>