<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute(WebKeys.LAYOUT_REVISION);

if ((layoutRevision == null) && (layout != null)) {
	layoutRevision = LayoutStagingUtil.getLayoutRevision(layout);
}

layoutRevision = stagingBarDisplayContext.updateLayoutRevision(layoutRevision);

LayoutSetBranch layoutSetBranch = (LayoutSetBranch)request.getAttribute(StagingProcessesWebKeys.LAYOUT_SET_BRANCH);

if (layoutSetBranch == null) {
	layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutRevision.getLayoutSetBranchId());
}

boolean workflowEnabled = WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, LayoutRevision.class.getName());

boolean hasWorkflowTask = false;

if (workflowEnabled) {
	hasWorkflowTask = StagingUtil.hasWorkflowTask(user.getUserId(), layoutRevision);
}

String taglibHelpMessage = null;

String layoutSetBranchName = HtmlUtil.escape(layoutSetBranchDisplayContext.getLayoutSetBranchDisplayName(layoutSetBranch));

if (layoutRevision.isHead()) {
	taglibHelpMessage = LanguageUtil.format(request, "this-version-will-be-published-when-x-is-published-to-live", layoutSetBranchName, false);
}
else if (hasWorkflowTask) {
	taglibHelpMessage = "you-are-currently-reviewing-this-page.-you-can-make-changes-and-send-them-to-the-next-step-in-the-workflow-when-ready";
}
else {
	taglibHelpMessage = "a-new-version-is-created-automatically-if-this-page-is-modified";
}
%>

<ul class="control-menu-nav staging-layout-revision-details-list">
	<c:if test="<%= !hasWorkflowTask %>">
		<c:if test="<%= !layoutRevision.isHead() && LayoutPermissionUtil.contains(permissionChecker, layoutRevision.getPlid(), ActionKeys.UPDATE) %>">
			<li class="control-menu-nav-item">

				<%
				List<LayoutRevision> pendingLayoutRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutRevision.getLayoutSetBranchId(), layoutRevision.getPlid(), WorkflowConstants.STATUS_PENDING);
				%>

				<portlet:actionURL name="updateLayoutRevision" var="publishURL">
					<portlet:param name="redirect" value="<%= PortalUtil.getLayoutFullURL(themeDisplay) %>" />
					<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
					<portlet:param name="major" value="<%= Boolean.TRUE.toString() %>" />
					<portlet:param name="workflowAction" value="<%= String.valueOf(layoutRevision.isIncomplete() ? WorkflowConstants.ACTION_SAVE_DRAFT : WorkflowConstants.ACTION_PUBLISH) %>" />
				</portlet:actionURL>

				<liferay-frontend:component
					context='<%=
						HashMapBuilder.<String, Object>put(
							"currentURL", currentURL
						).put(
							"incomplete", layoutRevision.isIncomplete()
						).put(
							"publishURL", publishURL
						).build()
					%>'
					module="{PublishProcess} from staging-bar-web"
				/>

				<c:choose>
					<c:when test="<%= !layout.isTypeContent() && !layoutRevision.isIncomplete() && !workflowEnabled %>">
						<span class="staging-bar-control-toggle">
							<aui:input id="readyToggle" label="<%= StringPool.BLANK %>" labelOff="ready-for-publish-process" labelOn="ready-for-publish-process" name="readyToggle" type="toggle-switch" value="<%= false %>" />
						</span>
					</c:when>
					<c:when test="<%= !workflowEnabled || pendingLayoutRevisions.isEmpty() %>">

						<%
						String label = null;

						if (layoutRevision.isIncomplete()) {
							label = LanguageUtil.format(request, "enable-in-x", layoutSetBranchName, false);
						}
						else if (workflowEnabled) {
							label = "submit-for-workflow";
						}
						%>

						<div class="btn-group-item">
							<clay:button
								displayType="secondary"
								id='<%= liferayPortletResponse.getNamespace() + "submitLink" %>'
								label="<%= label %>"
								small="<%= true %>"
								type="button"
							/>
						</div>
					</c:when>
				</c:choose>
			</li>
		</c:if>
	</c:if>

	<c:if test="<%= !layoutRevision.isIncomplete() %>">
		<li class="control-menu-nav-item">
			<c:if test="<%= !layout.isTypeContent() && layoutRevision.isHead() %>">
				<span class="staging-bar-control-toggle">
					<aui:input disabled="<%= true %>" id="readyToggle" label="<%= StringPool.BLANK %>" labelOn="ready-for-publish-process" name="readyToggle" type="toggle-switch" value="<%= true %>" />
				</span>
			</c:if>

			<c:if test="<%= hasWorkflowTask %>">

				<%
				WorkflowTask workflowTask = StagingUtil.getWorkflowTask(user.getUserId(), layoutRevision);

				String layoutURL = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);

				layoutURL = HttpComponentsUtil.addParameter(layoutURL, "layoutSetBranchId", layoutRevision.getLayoutSetBranchId());
				layoutURL = HttpComponentsUtil.addParameter(layoutURL, "layoutRevisionId", layoutRevision.getLayoutRevisionId());
				%>

				<liferay-ui:icon
					cssClass="submit-link"
					icon="workflow"
					id="reviewTaskIcon"
					message="workflow"
					method="get"
					url='<%=
						PortletURLBuilder.create(
							PortalUtil.getControlPanelPortletURL(request, PortletKeys.MY_WORKFLOW_TASK, PortletRequest.RENDER_PHASE)
						).setMVCPath(
							"/edit_workflow_task.jsp"
						).setParameter(
							"closeRedirect", layoutURL
						).setParameter(
							"workflowTaskId", workflowTask.getWorkflowTaskId()
						).setPortletMode(
							PortletMode.VIEW
						).setWindowState(
							LiferayWindowState.POP_UP
						).buildString()
					%>'
					useDialog="<%= true %>"
				/>
			</c:if>
		</li>
	</c:if>

	<%
	request.setAttribute(StagingProcessesWebKeys.BRANCHING_ENABLED, Boolean.TRUE.toString());
	request.setAttribute("view_layout_revision_details.jsp-hasWorkflowTask", String.valueOf(hasWorkflowTask));
	request.setAttribute("view_layout_revision_details.jsp-layoutRevision", layoutRevision);
	%>

	<liferay-staging:menu
		cssClass="branching-enabled col-md-4"
		layoutSetBranchId="<%= layoutRevision.getLayoutSetBranchId() %>"
		onlyActions="<%= true %>"
	/>

	<portlet:renderURL var="layoutRevisionStatusURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
		<portlet:param name="mvcPath" value="/view_layout_revision_status.jsp" />
	</portlet:renderURL>

	<portlet:renderURL var="markAsReadyForPublicationURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
		<portlet:param name="mvcPath" value="/view_layout_revision_details.jsp" />
	</portlet:renderURL>

	<li class="control-menu-nav-item">
		<div class="d-none d-sm-block">
			<clay:dropdown-menu
				additionalProps='<%=
					HashMapBuilder.<String, Object>put(
						"layoutRevisionStatusURL", layoutRevisionStatusURL
					).put(
						"markAsReadyForPublicationURL", markAsReadyForPublicationURL
					).build()
				%>'
				aria-label='<%= LanguageUtil.get(request, "show-staging-version-options") %>'
				borderless="<%= true %>"
				displayType="unstyled"
				dropdownItems="<%= stagingBarDisplayContext.getDropdownItems(layout, layoutRevision, hasWorkflowTask, layoutSetBranch) %>"
				icon="ellipsis-v"
				monospaced="<%= true %>"
				propsTransformer="{StagingVersionPropsTransformer} from staging-bar-web"
				small="<%= true %>"
			/>
		</div>
	</li>
</ul>