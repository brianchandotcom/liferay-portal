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
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute("view.jsp-layoutRevision");

if (layoutRevision == null && layout != null) {
	layoutRevision = LayoutStagingUtil.getLayoutRevision(layout);
}

LayoutSetBranch layoutSetBranch = (LayoutSetBranch)request.getAttribute("view.jsp-layoutSetBranch");

if (layoutSetBranch == null) {
	layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutRevision.getLayoutSetBranchId());
}

String taglibHelpMessage = null;

if (layoutRevision.isHead()) {
	taglibHelpMessage = LanguageUtil.format(pageContext, "this-version-will-be-published-when-x-is-published-to-live", layoutSetBranch.getName());
}
else {
	taglibHelpMessage = "a-new-version-will-be-created-automatically-if-this-page-is-modified";
}
%>

<div class="layout-actions">
	<c:choose>
		<c:when test="<%= layoutRevision.getStatus() == WorkflowConstants.STATUS_INCOMPLETE %>">
			<liferay-ui:message arguments="<%= new Object[] {layoutRevision.getName(locale), layoutSetBranch.getName()} %>" key="the-page-x-is-not-enabled-in-x,-but-is-available-in-other-pages-variations" />
		</c:when>
		<c:otherwise>
			<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

			<aui:workflow-status helpMessage="<%= taglibHelpMessage %>" status='<%= layoutRevision.getStatus() %>' statusMessage='<%= layoutRevision.isHead() ? "ready-for-publication" : null %>' version="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
		</c:otherwise>
	</c:choose>
</div>

<span class="layout-revision-toolbar" id="<portlet:namespace />layoutRevisionToolbar"></span>

<aui:script position="inline" use="liferay-staging-version">
	var stagingBar = Liferay.StagingBar;

	stagingBar.init(
		{
			namespace: '<portlet:namespace />'

			<c:if test="<%= layoutRevision.getStatus() == WorkflowConstants.STATUS_INCOMPLETE %>">
				, hideHistory: true
			</c:if>
		}
	);

	<c:if test="<%= layoutRevision.hasChildren() %>">

		<%
		List<LayoutRevision> childLayoutRevisions = layoutRevision.getChildren();

		LayoutRevision firstChildLayoutRevision = childLayoutRevisions.get(0);

		if (firstChildLayoutRevision.getStatus() == WorkflowConstants.STATUS_INACTIVE) {
		%>

			var redoButton = stagingBar.redoButton;

			stagingBar.layoutRevisionToolbar.add(redoButton, 0);

			redoButton.get('contentBox').attr(
				{
					'data-layoutRevisionId': '<%= firstChildLayoutRevision.getLayoutRevisionId() %>',
					'data-layoutSetBranchId': '<%= firstChildLayoutRevision.getLayoutSetBranchId() %>'
				}
			);

		<%
		}
		%>

	</c:if>

	<c:if test="<%= !layoutRevision.isMajor() && (layoutRevision.getParentLayoutRevisionId() != LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) %>">
		var undoButton = stagingBar.undoButton;

		stagingBar.layoutRevisionToolbar.add(undoButton, 0);

		undoButton.get('contentBox').attr(
			{
				'data-layoutRevisionId': '<%= layoutRevision.getLayoutRevisionId() %>',
				'data-layoutSetBranchId': '<%= layoutRevision.getLayoutSetBranchId() %>'
			}
		);
	</c:if>

	<c:if test="<%= !layoutRevision.isPending() && LayoutPermissionUtil.contains(permissionChecker, layoutRevision.getPlid(), ActionKeys.UPDATE) %>">

		<%
		List<LayoutRevision> pendingLayoutRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutRevision.getLayoutSetBranchId(), layoutRevision.getPlid(), WorkflowConstants.STATUS_PENDING);
		%>

		<c:if test="<%= pendingLayoutRevisions.isEmpty() && !layoutRevision.isHead() %>">

			<%
			String icon = null;
			String label = null;

			if (layoutRevision.getStatus() == WorkflowConstants.STATUS_INCOMPLETE) {
				icon = "circle-check";
				label = LanguageUtil.format(pageContext, "enable-in-x", layoutSetBranch.getName());
			}
			else {
				if(WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, LayoutRevision.class.getName())) {
					icon = "shuffle";
					label = "submit-for-publication";
				}
				else {
					icon = "circle-check";
					label = "mark-as-ready-for-publication";
				}
			}
			%>

			stagingBar.layoutRevisionToolbar.add(
				{
					type: 'ToolbarSpacer'
				}
			);

			<portlet:actionURL var="publishURL">
				<portlet:param name="struts_action" value="/staging_bar/edit_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="update_layout_revision" />
				<portlet:param name="redirect" value="<%= PortalUtil.getLayoutFullURL(themeDisplay) %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(layoutRevision.getGroupId()) %>" />
				<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
				<portlet:param name="major" value="true" />
				<portlet:param name="workflowAction" value="<%= String.valueOf((layoutRevision.getStatus() == WorkflowConstants.STATUS_INCOMPLETE) ? WorkflowConstants.ACTION_SAVE_DRAFT : WorkflowConstants.ACTION_PUBLISH) %>" />
			</portlet:actionURL>

			stagingBar.layoutRevisionToolbar.add(
				{
					handler: function(event) {
						A.io.request(
							'<%= publishURL %>',
							{
								after: {
									success: function() {
										Liferay.fire('updatedLayout');
									}
								}
							}
						);
					},
					icon: '<%= icon %>',
					label: '<%= UnicodeLanguageUtil.get(pageContext, label) %>'
				}
			);
		</c:if>
	</c:if>
</aui:script>