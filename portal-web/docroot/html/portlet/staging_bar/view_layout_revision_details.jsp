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
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute("view.jsp-layoutRevision");

if ((layoutRevision == null) && (layout != null)) {
	layoutRevision = LayoutStagingUtil.getLayoutRevision(layout);
}

LayoutSetBranch layoutSetBranch = (LayoutSetBranch)request.getAttribute("view.jsp-layoutSetBranch");

if (layoutSetBranch == null) {
	layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutRevision.getLayoutSetBranchId());
}

boolean workflowEnabled = WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, LayoutRevision.class.getName());

boolean hasWorkflowTask = false;

if (workflowEnabled) {
	hasWorkflowTask = StagingUtil.hasWorkflowTask(user.getUserId(), layoutRevision);
}

String taglibHelpMessage = null;

if (layoutRevision.isHead()) {
	taglibHelpMessage = LanguageUtil.format(pageContext, "this-version-will-be-published-when-x-is-published-to-live", HtmlUtil.escape(layoutSetBranch.getName()));
}
else if (hasWorkflowTask) {
	taglibHelpMessage = "you-are-currently-reviewing-this-page.-you-can-make-changes-and-send-them-to-the-next-step-in-the-workflow-when-ready";
}
else {
	taglibHelpMessage = "a-new-version-will-be-created-automatically-if-this-page-is-modified";
}
%>

<div class="layout-actions">
	<c:choose>
		<c:when test="<%= layoutRevision.getStatus() == WorkflowConstants.STATUS_INCOMPLETE %>">
			<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(layoutRevision.getName(locale)), HtmlUtil.escape(layoutSetBranch.getName())} %>" key="the-page-x-is-not-enabled-in-x,-but-is-available-in-other-pages-variations" />
		</c:when>
		<c:otherwise>
			<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

			<aui:workflow-status helpMessage="<%= taglibHelpMessage %>" status="<%= layoutRevision.getStatus() %>" statusMessage='<%= layoutRevision.isHead() ? "ready-for-publication" : null %>' version="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
		</c:otherwise>
	</c:choose>
</div>

<span class="layout-revision-toolbar" id="<portlet:namespace />layoutRevisionToolbar"></span>

<aui:script position="inline" use="liferay-staging-version">
	var stagingBar = Liferay.StagingBar;

	stagingBar.init(
		{
			namespace: '<portlet:namespace />',
			portletId: '<%= portletDisplay.getId() %>'

			<c:if test="<%= layoutRevision.getStatus() == WorkflowConstants.STATUS_INCOMPLETE %>">
				, hideHistory: true
			</c:if>
		}
	);

	<c:choose>
		<c:when test="<%= hasWorkflowTask %>">
			stagingBar.layoutRevisionToolbar.add(
				{
					type: 'ToolbarSpacer'
				}
			);

			stagingBar.layoutRevisionToolbar.add(
				{
					handler: function(event) {

						<%
						long controlPanelPlid = PortalUtil.getControlPanelPlid(company.getCompanyId());

						PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(controlPanelPlid, PortletKeys.MY_WORKFLOW_TASKS, PortletRequest.RENDER_PHASE);

						portletURL.setParameter("struts_action", "/my_workflow_tasks/edit_workflow_task");

						WorkflowTask workflowTask = StagingUtil.getWorkflowTask(user.getUserId(), layoutRevision);

						portletURL.setParameter("workflowTaskId", String.valueOf(workflowTask.getWorkflowTaskId()));

						portletURL.setPortletMode(PortletMode.VIEW);
						portletURL.setWindowState(LiferayWindowState.POP_UP);

						String layoutURL = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);

						layoutURL = HttpUtil.addParameter(layoutURL, "layoutSetBranchId", layoutRevision.getLayoutSetBranchId());
						layoutURL = HttpUtil.addParameter(layoutURL, "layoutRevisionId", layoutRevision.getLayoutRevisionId());

						portletURL.setParameter("closeRedirect", layoutURL);
						%>

						Liferay.Util.openWindow(
							{
								id: '<portlet:namespace />workflowTasks',
								title: '<%= UnicodeLanguageUtil.get(pageContext, "workflow") %>',
								uri: '<%= portletURL.toString() %>'
							}
						);
					},
					icon: 'shuffle',
					label: '<%= UnicodeLanguageUtil.get(pageContext, "workflow") %>'
				}
			);
		</c:when>
		<c:otherwise>
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

			<c:if test="<%= !layoutRevision.isHead() && LayoutPermissionUtil.contains(permissionChecker, layoutRevision.getPlid(), ActionKeys.UPDATE) %>">
				stagingBar.layoutRevisionToolbar.add(
					{
						type: 'ToolbarSpacer'
					}
				);

				stagingBar.layoutRevisionToolbar.add(
					{

						<%
						List<LayoutRevision> pendingLayoutRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutRevision.getLayoutSetBranchId(), layoutRevision.getPlid(), WorkflowConstants.STATUS_PENDING);
						%>

						<c:choose>
							<c:when test="<%= pendingLayoutRevisions.isEmpty() %>">
								<portlet:actionURL var="publishURL">
									<portlet:param name="struts_action" value="/staging_bar/edit_layouts" />
									<portlet:param name="<%= Constants.CMD %>" value="update_layout_revision" />
									<portlet:param name="redirect" value="<%= PortalUtil.getLayoutFullURL(themeDisplay) %>" />
									<portlet:param name="groupId" value="<%= String.valueOf(layoutRevision.getGroupId()) %>" />
									<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
									<portlet:param name="major" value="true" />
									<portlet:param name="workflowAction" value="<%= String.valueOf((layoutRevision.getStatus() == WorkflowConstants.STATUS_INCOMPLETE) ? WorkflowConstants.ACTION_SAVE_DRAFT : WorkflowConstants.ACTION_PUBLISH) %>" />
								</portlet:actionURL>

								handler: function(event) {
									A.io.request(
										'<%= publishURL %>',
										{
											after: {
												success: function() {
													<c:choose>
														<c:when test="<%= layoutRevision.getStatus() == WorkflowConstants.STATUS_INCOMPLETE %>">
															location.href = '<%= currentURL %>';
														</c:when>
														<c:otherwise>
															Liferay.fire('updatedLayout');
														</c:otherwise>
													</c:choose>
												}
											}
										}
									);
								},
							</c:when>
							<c:when test="<%= workflowEnabled %>">

								<%
								String submitMessage = "you-cannot-submit-your-changes-because-someone-else-has-submitted-changes-for-approval";

								LayoutRevision pendingLayoutRevision = pendingLayoutRevisions.get(0);

								if ((pendingLayoutRevision != null) && (pendingLayoutRevision.getUserId() == user.getUserId())) {
									submitMessage = "you-cannot-submit-your-changes-because-your-previous-submission-is-still-waiting-for-approval";
								}
								%>

								disabled: true,
								title: '<%= UnicodeLanguageUtil.get(pageContext, submitMessage) %>',
							</c:when>
						</c:choose>

						<%
						String icon = "circle-check";
						String label = null;

						if (layoutRevision.getStatus() == WorkflowConstants.STATUS_INCOMPLETE) {
							label = LanguageUtil.format(pageContext, "enable-in-x", layoutSetBranch.getName());
						}
						else {
							if (workflowEnabled) {
								icon = "shuffle";
								label = "submit-for-publication";
							}
							else {
								label = "mark-as-ready-for-publication";
							}
						}
						%>

						icon: '<%= icon %>',
						label: '<%= UnicodeLanguageUtil.get(pageContext, label) %>'
					}
				);
			</c:if>
		</c:otherwise>
	</c:choose>
</aui:script>