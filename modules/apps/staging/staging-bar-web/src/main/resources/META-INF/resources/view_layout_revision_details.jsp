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
	taglibHelpMessage = LanguageUtil.format(request, "this-version-will-be-published-when-x-is-published-to-live", HtmlUtil.escape(layoutSetBranch.getName()), false);
}
else if (hasWorkflowTask) {
	taglibHelpMessage = "you-are-currently-reviewing-this-page.-you-can-make-changes-and-send-them-to-the-next-step-in-the-workflow-when-ready";
}
else {
	taglibHelpMessage = "a-new-version-is-created-automatically-if-this-page-is-modified";
}
%>

<ul class="control-menu-nav">
	<c:if test="<%= !hasWorkflowTask %>">
		<c:if test="<%= !layoutRevision.isHead() && LayoutPermissionUtil.contains(permissionChecker, layoutRevision.getPlid(), ActionKeys.UPDATE) %>">
			<li class="control-menu-nav-item">
				<c:if test="<%= layoutRevision.isIncomplete() %>">
					<p>
						<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(layoutRevision.getName(locale)), HtmlUtil.escape(layoutSetBranch.getName())} %>" key="the-page-x-is-not-enabled-in-x,-but-is-available-in-other-pages-variations" translateArguments="<%= false %>" />
					</p>
				</c:if>

				<%
				List<LayoutRevision> pendingLayoutRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutRevision.getLayoutSetBranchId(), layoutRevision.getPlid(), WorkflowConstants.STATUS_PENDING);
				%>

				<c:choose>
					<c:when test="<%= workflowEnabled && !pendingLayoutRevisions.isEmpty() %>">

						<%
						String submitMessage = "you-cannot-submit-your-changes-because-someone-else-has-submitted-changes-for-approval";

						LayoutRevision pendingLayoutRevision = pendingLayoutRevisions.get(0);

						if ((pendingLayoutRevision != null) && (pendingLayoutRevision.getUserId() == user.getUserId())) {
							submitMessage = "you-cannot-submit-your-changes-because-your-previous-submission-is-still-waiting-for-approval";
						}
						%>

						<aui:script>
							AUI.$('.submit-link').on(
								'mouseenter',
								function(event) {
									Liferay.Portal.ToolTip.show(event.currentTarget, '<liferay-ui:message key="<%= submitMessage %>" />');
								}
							);
						</aui:script>
					</c:when>
					<c:otherwise>
						<portlet:actionURL name="updateLayoutRevision" var="publishURL">
							<portlet:param name="redirect" value="<%= PortalUtil.getLayoutFullURL(themeDisplay) %>" />
							<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
							<portlet:param name="major" value="true" />
							<portlet:param name="workflowAction" value="<%= String.valueOf(layoutRevision.isIncomplete() ? WorkflowConstants.ACTION_SAVE_DRAFT : WorkflowConstants.ACTION_PUBLISH) %>" />
						</portlet:actionURL>

						<%
						String label = null;

						if (layoutRevision.isIncomplete()) {
							label = LanguageUtil.format(request, "enable-in-x", layoutSetBranch.getName(), false);
						}
						else {
							if (workflowEnabled) {
								label = "submit-for-publication";
							}
							else {
								label = "mark-as-ready-for-publication";
							}
						}
						%>

						<a href="javascript:Liferay.fire('<%= liferayPortletResponse.getNamespace() %>submit', {incomplete: <%= layoutRevision.isIncomplete() %>, publishURL: '<%= publishURL %>', currentURL: '<%= currentURL %>'}); void(0);" id="submitLink">
							<liferay-ui:message key="<%= label %>" />
						</a>
					</c:otherwise>
				</c:choose>
			</li>
		</c:if>
	</c:if>

	<li class="control-menu-nav-item">
		<c:if test="<%= !layoutRevision.isIncomplete() %>">
			<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

			<aui:workflow-status showIcon="<%= false %>" showLabel="<%= false %>" status="<%= layoutRevision.getStatus() %>" statusMessage='<%= layoutRevision.isHead() ? "ready-for-publication" : null %>' />

			<aui:script>
				AUI.$('.layout-revision-info .taglib-workflow-status').on(
					'mouseenter',
					function(event) {
						Liferay.Portal.ToolTip.show(event.currentTarget, '<liferay-ui:message key="<%= taglibHelpMessage %>" />');
					}
				);
			</aui:script>

			<c:if test="<%= hasWorkflowTask %>">

				<%
				PortletURL portletURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.MY_WORKFLOW_TASK, PortletRequest.RENDER_PHASE);

				portletURL.setParameter("mvcPath", "/edit_workflow_task.jsp");

				WorkflowTask workflowTask = StagingUtil.getWorkflowTask(user.getUserId(), layoutRevision);

				portletURL.setParameter("workflowTaskId", String.valueOf(workflowTask.getWorkflowTaskId()));

				portletURL.setPortletMode(PortletMode.VIEW);
				portletURL.setWindowState(LiferayWindowState.POP_UP);

				String layoutURL = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);

				layoutURL = HttpUtil.addParameter(layoutURL, "layoutSetBranchId", layoutRevision.getLayoutSetBranchId());
				layoutURL = HttpUtil.addParameter(layoutURL, "layoutRevisionId", layoutRevision.getLayoutRevisionId());

				portletURL.setParameter("closeRedirect", layoutURL);
				%>

				<liferay-ui:icon
					cssClass="submit-link"
					iconCssClass="icon-random"
					id="reviewTaskIcon"
					message="workflow"
					method="get"
					url="<%= portletURL.toString() %>"
					useDialog="<%= true %>"
				/>
			</c:if>
		</c:if>
	</li>

	<%
	request.setAttribute(StagingProcessesWebKeys.BRANCHING_ENABLED, String.valueOf(true));
	request.setAttribute("view_layout_revision_details.jsp-hasWorkflowTask", String.valueOf(hasWorkflowTask));
	request.setAttribute("view_layout_revision_details.jsp-layoutRevision", layoutRevision);
	%>

	<liferay-staging:menu cssClass="branching-enabled col-md-4" extended="<%= false %>" layoutSetBranchId="<%= layoutRevision.getLayoutSetBranchId() %>" onlyActions="<%= true %>" />

	<li class="control-menu-nav-item">
		<div class="dropdown hidden-xs">
			<a class="dropdown-toggle taglib-icon" data-toggle="dropdown">
				<aui:icon image="ellipsis-v" markupView="lexicon" />

				<span class="sr-only">
					<liferay-ui:message key="options" />
				</span>
			</a>

			<ul class="dropdown-menu dropdown-menu-right" role="menu">
				<c:if test="<%= !layoutRevision.isIncomplete() %>">
					<li>
						<a href="javascript:Liferay.fire('<%= liferayPortletResponse.getNamespace() %>viewHistory', {layoutRevisionId: '<%= layoutRevision.getLayoutRevisionId() %>', layoutSetBranchId: '<%= layoutRevision.getLayoutSetBranchId() %>'}); void(0);" id="viewHistoryLink">
							<liferay-ui:message key="history" />
						</a>
					</li>
				</c:if>

				<c:if test="<%= !hasWorkflowTask %>">
					<c:if test="<%= !layoutRevision.isMajor() && (layoutRevision.getParentLayoutRevisionId() != LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) %>">
						<li>
							<a href="javascript:Liferay.fire('<%= liferayPortletResponse.getNamespace() %>undo', {layoutRevisionId: '<%= layoutRevision.getLayoutRevisionId() %>', layoutSetBranchId: '<%= layoutRevision.getLayoutSetBranchId() %>'}); void(0);" id="undoLink">
								<liferay-ui:message key="undo" />
							</a>
						</li>
					</c:if>

					<c:if test="<%= layoutRevision.hasChildren() %>">

						<%
						List<LayoutRevision> childLayoutRevisions = layoutRevision.getChildren();

						LayoutRevision firstChildLayoutRevision = childLayoutRevisions.get(0);

						if (firstChildLayoutRevision.isInactive()) {
						%>

						<li>
							<a href="javascript:Liferay.fire('<%= liferayPortletResponse.getNamespace() %>redo', {layoutRevisionId: '<%= firstChildLayoutRevision.getLayoutRevisionId() %>', layoutSetBranchId: '<%= firstChildLayoutRevision.getLayoutSetBranchId() %>'}); void(0);" id="redoLink">
								<liferay-ui:message key="redo" />
							</a>
						</li>

						<%
						}
						%>

					</c:if>
				</c:if>
			</ul>
		</div>
	</li>

	<portlet:renderURL var="markAsReadyForPublicationURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
		<portlet:param name="mvcPath" value="/view_layout_revision_details.jsp" />
	</portlet:renderURL>

	<portlet:renderURL var="viewHistoryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="mvcPath" value="/view_layout_revisions.jsp" />
		<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
	</portlet:renderURL>

	<aui:script position="inline" use="liferay-staging-version">
		var stagingBar = Liferay.StagingBar;

		stagingBar.init(
			{
				markAsReadyForPublicationURL: '<%= markAsReadyForPublicationURL %>',
				namespace: '<portlet:namespace />',
				portletId: '<%= portletDisplay.getId() %>',
				viewHistoryURL: '<%= viewHistoryURL %>'
			}
		);
	</aui:script>
</ul>