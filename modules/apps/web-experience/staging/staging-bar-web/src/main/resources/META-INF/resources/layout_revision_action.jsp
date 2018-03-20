<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/init.jsp" %>

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

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= !layoutRevision.isPending() && LayoutPermissionUtil.contains(permissionChecker, layoutRevision.getPlid(), ActionKeys.UPDATE) %>">
		<c:if test="<%= pendingLayoutRevisions.isEmpty() && !layoutRevision.isHead() && (layoutRevision.getStatus() != WorkflowConstants.STATUS_INCOMPLETE) %>">
			<portlet:actionURL name="updateLayoutRevision" var="publishURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(layoutRevision.getGroupId()) %>" />
				<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
				<portlet:param name="major" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="workflowAction" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />
			</portlet:actionURL>

			<%
			String taglibURL = "javascript:submitForm(document.hrefFm, '" + HtmlUtil.escapeJS(publishURL) + "');";
			%>

			<liferay-ui:icon
				message='<%= WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, LayoutRevision.class.getName()) ? "submit-for-publication" : "mark-as-ready-for-publication" %>'
				url="<%= taglibURL %>"
			/>
		</c:if>

		<c:if test="<%= !layoutRevision.isHead() && !layoutRevision.isPending() %>">
			<portlet:actionURL name="deleteLayoutRevision" var="deleteURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
				<portlet:param name="updateRecentLayoutRevisionId" value="<%= String.valueOf(updateRecentLayoutRevisionId) %>" />
			</portlet:actionURL>

			<c:choose>
				<c:when test="<%= (layoutRevision.getParentLayoutRevisionId() == 0) && updateRecentLayoutRevisionId && (layoutRevision.getStatus() != WorkflowConstants.STATUS_INCOMPLETE) %>">
					<liferay-ui:icon-delete
						confirmation="are-you-sure-you-want-to-remove-this-from-site-pages-variation"
						message="remove-from-site-pages-variation"
						url="<%= deleteURL %>"
					/>
				</c:when>
				<c:when test="<%= layoutRevision.getStatus() != WorkflowConstants.STATUS_INCOMPLETE %>">
					<liferay-ui:icon-delete
						url="<%= deleteURL %>"
					/>
				</c:when>
			</c:choose>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>