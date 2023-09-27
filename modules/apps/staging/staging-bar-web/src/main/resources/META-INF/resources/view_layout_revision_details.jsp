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

				<c:choose>
					<c:when test="<%= !layout.isTypeContent() && !layoutRevision.isIncomplete() && !workflowEnabled %>">
						<span class="staging-bar-control-toggle">
							<aui:input id="readyToggle" label="<%= StringPool.BLANK %>" labelOff="ready-for-publish-process" labelOn="ready-for-publish-process" name="readyToggle" onChange='<%= liferayPortletResponse.getNamespace() + "submitLayoutRevision('" + publishURL + "')" %>' type="toggle-switch" value="<%= false %>" />
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
							<a class="btn btn-secondary btn-sm" href="javascript:Liferay.fire('<portlet:namespace />submit', {incomplete: <%= layoutRevision.isIncomplete() %>, publishURL: '<%= publishURL %>', currentURL: '<%= currentURL %>'}); void(0);" id="submitLink">
								<liferay-ui:message key="<%= label %>" />
							</a>
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

	<li class="control-menu-nav-item">
		<div class="d-none d-sm-block dropdown">
			<button aria-haspopup="true" class="component-action dropdown-toggle taglib-icon" data-toggle="liferay-dropdown">
				<aui:icon cssClass="<%= StringPool.BLANK %>" image="ellipsis-v" markupView="lexicon" />

				<span class="sr-only">
					<liferay-ui:message key="options" />
				</span>
			</button>

			<ul class="dropdown-menu dropdown-menu-right" role="menu">
				<li>
					<a class="dropdown-item" href="javascript:void(0);" id="manageLayoutSetRevisions" onclick="<portlet:namespace />openSitePagesVariationsDialog();">
						<liferay-ui:message key="site-pages-variation" />
					</a>
				</li>

				<c:if test="<%= !layoutRevision.isIncomplete() && !layout.isTypeContent() %>">
					<li>
						<a class="dropdown-item" href="javascript:void(0);" id="manageLayoutRevisions" onclick="<portlet:namespace />openPageVariationsDialog();">
							<liferay-ui:message key="page-variations" />
						</a>
					</li>
					<li>
						<a class="dropdown-item" href="javascript:Liferay.fire('<portlet:namespace />viewHistory', {layoutRevisionId: '<%= layoutRevision.getLayoutRevisionId() %>', layoutSetBranchId: '<%= layoutRevision.getLayoutSetBranchId() %>'}); void(0);" id="viewHistoryLink">
							<liferay-ui:message key="history" />
						</a>
					</li>
				</c:if>

				<c:if test="<%= !hasWorkflowTask && !layout.isTypeContent() %>">
					<c:if test="<%= !layoutRevision.isMajor() && (layoutRevision.getParentLayoutRevisionId() != LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) %>">
						<li>
							<a class="dropdown-item" href="javascript:Liferay.fire('<portlet:namespace />undo', {layoutRevisionId: '<%= layoutRevision.getLayoutRevisionId() %>', layoutSetBranchId: '<%= layoutRevision.getLayoutSetBranchId() %>'}); void(0);" id="undoLink">
								<liferay-ui:message key="undo" />
							</a>
						</li>
					</c:if>

					<c:if test="<%= layoutRevision.hasChildren() %>">

						<%
						List<LayoutRevision> childLayoutRevisions = layoutRevision.getChildren();

						LayoutRevision firstChildLayoutRevision = childLayoutRevisions.get(0);
						%>

						<c:if test="<%= firstChildLayoutRevision.isInactive() %>">
							<li>
								<a class="dropdown-item" href="javascript:Liferay.fire('<portlet:namespace />redo', {layoutRevisionId: '<%= firstChildLayoutRevision.getLayoutRevisionId() %>', layoutSetBranchId: '<%= firstChildLayoutRevision.getLayoutSetBranchId() %>'}); void(0);" id="redoLink">
									<liferay-ui:message key="redo" />
								</a>
							</li>
						</c:if>
					</c:if>
				</c:if>
			</ul>
		</div>
	</li>
</ul>

<portlet:renderURL var="layoutRevisionStatusURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcPath" value="/view_layout_revision_status.jsp" />
</portlet:renderURL>

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

	stagingBar.init({
		layoutRevisionStatusURL: '<%= layoutRevisionStatusURL %>',
		markAsReadyForPublicationURL: '<%= markAsReadyForPublicationURL %>',
		namespace: '<portlet:namespace />',
		portletId: '<%= portletDisplay.getId() %>',
		viewHistoryURL: '<%= viewHistoryURL %>',
	});
</aui:script>

<aui:script>
	function <portlet:namespace />openPageVariationsDialog() {
		Liferay.Util.openWindow({
			dialog: {
				after: {
					destroy: function (event) {
						window.location.reload();
					},
				},
				destroyOnHide: true,
			},
			id: 'pagesVariationsDialog',
			title: '<liferay-ui:message key="page-variations" />',

			<liferay-portlet:renderURL var="layoutBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="/staging_bar/view_layout_branches" />
				<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
			</liferay-portlet:renderURL>

			uri: '<%= HtmlUtil.escapeJS(layoutBranchesURL) %>',
		});
	}

	function <portlet:namespace />openSitePagesVariationsDialog() {
		Liferay.Util.openWindow({
			dialog: {
				after: {
					destroy: function (event) {
						window.location.reload();
					},
				},
				destroyOnHide: true,
			},
			id: 'sitePagesVariationDialog',
			title: '<liferay-ui:message key="site-pages-variation" />',

			<liferay-portlet:renderURL var="layoutSetBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="/staging_bar/view_layout_set_branches" />
			</liferay-portlet:renderURL>

			uri: '<%= HtmlUtil.escapeJS(layoutSetBranchesURL) %>',
		});
	}

	function <portlet:namespace />submitLayoutRevision(publishURL) {
		Liferay.fire('<portlet:namespace />submit', {
			currentURL: '<%= currentURL %>',
			incomplete: <%= layoutRevision.isIncomplete() %>,
			publishURL: publishURL,
		});

		Liferay.Util.toggleDisabled('#<portlet:namespace />readyToggle', true);
	}
</aui:script>