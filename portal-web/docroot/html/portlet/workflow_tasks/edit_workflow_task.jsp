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

<%@ include file="/html/portlet/workflow_tasks/init.jsp" %>

<%
String randomId = StringUtil.randomId();

String redirect = ParamUtil.getString(request, "redirect");

WorkflowTask workflowTask = (WorkflowTask)request.getAttribute(WebKeys.WORKFLOW_TASK);

WorkflowInstance workflowInstance = WorkflowInstanceManagerUtil.getWorkflowInstance(company.getCompanyId(), workflowTask.getWorkflowInstanceId());

Map<String, Serializable> workflowContext = workflowInstance.getWorkflowContext();

long companyId = GetterUtil.getLong((String)workflowContext.get(WorkflowConstants.CONTEXT_COMPANY_ID));
long groupId = GetterUtil.getLong((String)workflowContext.get(WorkflowConstants.CONTEXT_GROUP_ID));
String className = (String)workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
long classPK = GetterUtil.getLong((String)workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

WorkflowHandler<?> workflowHandler = WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

AssetRenderer assetRenderer = workflowHandler.getAssetRenderer(classPK);
AssetRendererFactory assetRendererFactory = workflowHandler.getAssetRendererFactory();

AssetEntry assetEntry = null;

if (assetRenderer != null) {
	assetEntry = assetRendererFactory.getAssetEntry(assetRendererFactory.getClassName(), assetRenderer.getClassPK());
}

String headerTitle = LanguageUtil.get(pageContext, workflowTask.getName());

headerTitle = headerTitle.concat(StringPool.COLON + StringPool.SPACE + workflowHandler.getTitle(classPK, locale));

boolean showEditURL = false;

if ((workflowTask.getAssigneeUserId() == user.getUserId()) && !workflowTask.isCompleted()) {
	showEditURL = true;
}

PortletURL editPortletURL = workflowHandler.getURLEdit(classPK, liferayPortletRequest, liferayPortletResponse);
PortletURL viewDiffsPortletURL = workflowHandler.getURLViewDiffs(classPK, liferayPortletRequest, liferayPortletResponse);

String viewFullContentURLString = null;

if ((assetRenderer != null) && assetRenderer.isPreviewInContext()) {
	viewFullContentURLString = assetRenderer.getURLViewInContext((LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse, null);
}
else {
	PortletURL viewFullContentURL = renderResponse.createRenderURL();

	viewFullContentURL.setParameter("struts_action", "/workflow_tasks/view_content");
	viewFullContentURL.setParameter("redirect", currentURL);

	if (assetEntry != null) {
		viewFullContentURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
		viewFullContentURL.setParameter("assetEntryVersionId", String.valueOf(classPK));
	}

	if (assetRendererFactory != null) {
		viewFullContentURL.setParameter("type", assetRendererFactory.getType());
	}

	viewFullContentURL.setParameter("showEditURL", String.valueOf(showEditURL));
	viewFullContentURL.setParameter("workflowAssetPreview", Boolean.TRUE.toString());

	viewFullContentURLString = viewFullContentURL.toString();
}

request.setAttribute(WebKeys.WORKFLOW_ASSET_PREVIEW, Boolean.TRUE);
%>

<portlet:renderURL var="backURL">
	<portlet:param name="struts_action" value="/workflow_tasks/view" />
</portlet:renderURL>

<liferay-ui:header
	backURL="<%= backURL.toString() %>"
	localizeTitle="<%= false %>"
	title="<%= headerTitle %>"
/>

<aui:row>
	<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="<%= 75 %>">
		<liferay-ui:error exception="<%= WorkflowTaskDueDateException.class %>" message="please-enter-a-valid-due-date" />

		<aui:row>
			<aui:col width="<%= 50 %>">
				<div class="lfr-asset-assigned">
					<c:choose>
						<c:when test="<%= workflowTask.isAssignedToSingleUser() %>">
							<aui:input cssClass="assigned-to" inlineField="<%= true %>" name="assignedTo" type="resource" value="<%= PortalUtil.getUserName(workflowTask.getAssigneeUserId(), StringPool.BLANK) %>" />
						</c:when>
						<c:otherwise>
							<aui:input cssClass="assigned-to" inlineField="<%= true %>" name="assignedTo" type="resource" value='<%= LanguageUtil.get(pageContext, "nobody") %>' />
						</c:otherwise>
					</c:choose>

					<c:if test="<%= !workflowTask.isAssignedToSingleUser() %>">
						<portlet:actionURL var="assignToMeURL">
							<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ASSIGN %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
							<portlet:param name="assigneeUserId" value="<%= String.valueOf(user.getUserId()) %>" />
						</portlet:actionURL>

						<aui:a cssClass="icon-signin" href="<%= assignToMeURL %>" id='<%= randomId + "taskAssignToMeLink" %>' label="assign-to-me" />
					</c:if>

					&nbsp;

					<%
					long[] pooledActorsIds = WorkflowTaskManagerUtil.getPooledActorsIds(company.getCompanyId(), workflowTask.getWorkflowTaskId());
					%>

					<c:if test="<%= _hasOtherAssignees(pooledActorsIds, workflowTask, user) %>">
						<%= StringPool.DASH %>

						<portlet:actionURL var="assignURL">
							<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ASSIGN %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
						</portlet:actionURL>

						<aui:a cssClass="icon-signin" href="<%= assignURL %>" id='<%= randomId + "taskAssignLink" %>' label="assign-to-..." />
					</c:if>
				</div>

				<aui:input name="state" type="resource" value="<%= LanguageUtil.get(pageContext, HtmlUtil.escape(WorkflowInstanceLinkLocalServiceUtil.getState(companyId, groupId, className, classPK))) %>" />
			</aui:col>

			<aui:col width="<%= 50 %>">
				<aui:input name="createDate" type="resource" value="<%= dateFormatDateTime.format(workflowTask.getCreateDate()) %>" />

				<aui:input inlineField="<%= true %>" name="dueDate" type="resource" value='<%= (workflowTask.getDueDate() == null) ? LanguageUtil.get(pageContext, "never") : dateFormatDateTime.format(workflowTask.getDueDate()) %>' />

				<c:if test="<%= !workflowTask.isCompleted() %>">
					<portlet:actionURL var="updateDueDateURL">
						<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="workflowTaskId" value="<%= StringUtil.valueOf(workflowTask.getWorkflowTaskId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						iconCssClass="icon-time"
						id='<%= randomId + "taskDueDateLink" %>'
						label="<%= true %>"
						message="change"
						url="javascript:;"
					/>
				</c:if>
			</aui:col>
		</aui:row>

		<c:if test="<%= Validator.isNotNull(workflowTask.getDescription()) %>">
			<div class="lfr-asset-field">
				<aui:field-wrapper label="description">
					<%= HtmlUtil.escape(workflowTask.getDescription()) %>
				</aui:field-wrapper>
			</div>
		</c:if>

		<liferay-ui:panel-container cssClass="task-panel-container" extended="<%= true %>">
			<c:if test="<%= assetRenderer != null %>">
				<liferay-ui:panel defaultState="open" title='<%= LanguageUtil.format(pageContext, "preview-of-x", ResourceActionsUtil.getModelResource(locale, className), false) %>'>
					<div class="task-content-actions">
						<liferay-ui:icon-list>
							<c:if test="<%= assetRenderer.hasViewPermission(permissionChecker) %>">
								<liferay-ui:icon iconCssClass="icon-search" message="view[action]" method="get" target='<%= assetRenderer.isPreviewInContext() ? "_blank" : StringPool.BLANK %>' url="<%= viewFullContentURLString %>" />

								<c:if test="<%= viewDiffsPortletURL != null %>">

									<%
									viewDiffsPortletURL.setParameter("redirect", currentURL);
									viewDiffsPortletURL.setParameter("hideControls", Boolean.TRUE.toString());
									viewDiffsPortletURL.setWindowState(LiferayWindowState.POP_UP);
									viewDiffsPortletURL.setPortletMode(PortletMode.VIEW);

									String taglibViewDiffsURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "viewDiffs', title: '" + HtmlUtil.escapeJS(LanguageUtil.get(pageContext, "diffs")) + "', uri:'" + HtmlUtil.escapeJS(viewDiffsPortletURL.toString()) + "'});";
									%>

									<liferay-ui:icon iconCssClass="icon-copy" message="diffs" url="<%= taglibViewDiffsURL %>" />
								</c:if>
							</c:if>

							<c:if test="<%= editPortletURL != null %>">

								<%
								editPortletURL.setWindowState(LiferayWindowState.POP_UP);
								editPortletURL.setPortletMode(PortletMode.VIEW);

								String editPortletURLString = editPortletURL.toString();

								editPortletURLString = HttpUtil.addParameter(editPortletURLString, "doAsGroupId", assetRenderer.getGroupId());
								editPortletURLString = HttpUtil.addParameter(editPortletURLString, "refererPlid", plid);
								%>

								<c:choose>
									<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && showEditURL %>">

										<%
										String taglibEditURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "editAsset', title: '" + HtmlUtil.escapeJS(LanguageUtil.format(pageContext, "edit-x", HtmlUtil.escape(assetRenderer.getTitle(locale)), false)) + "', uri:'" + HtmlUtil.escapeJS(editPortletURLString) + "'});";
										%>

										<liferay-ui:icon iconCssClass="icon-edit" message="edit" url="<%= taglibEditURL %>" />
									</c:when>
									<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && !showEditURL && !workflowTask.isCompleted() %>">
										<liferay-ui:icon-help message="please-assign-the-task-to-yourself-to-be-able-to-edit-the-content" />
									</c:when>
								</c:choose>
							</c:if>
						</liferay-ui:icon-list>
					</div>

					<h3 class="task-content-title">
						<liferay-ui:icon
							iconCssClass="<%= workflowHandler.getIconCssClass() %>"
							label="<%= true %>"
							message="<%= HtmlUtil.escape(workflowHandler.getTitle(classPK, locale)) %>"
						/>
					</h3>

					<%
					String path = workflowHandler.render(classPK, renderRequest, renderResponse, AssetRenderer.TEMPLATE_ABSTRACT);

					request.setAttribute(WebKeys.ASSET_RENDERER, assetRenderer);
					request.setAttribute(WebKeys.ASSET_PUBLISHER_ABSTRACT_LENGTH, 200);
					%>

					<c:choose>
						<c:when test="<%= path == null %>">
							<%= HtmlUtil.escape(workflowHandler.getSummary(classPK, renderRequest, renderResponse)) %>
						</c:when>
						<c:otherwise>
							<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>" />
						</c:otherwise>
					</c:choose>

					<%
					boolean filterByMetadata = false;

					String[] metadataFields = new String[] {"author", "categories", "tags"};
					%>

					<div class="asset-metadata">
						<%@ include file="/html/portlet/asset_publisher/asset_metadata.jspf" %>
					</div>
				</liferay-ui:panel>
			</c:if>

			<liferay-ui:panel defaultState="closed" title="activities">

				<%
				List<Integer> logTypes = new ArrayList<Integer>();

				logTypes.add(WorkflowLog.TASK_ASSIGN);
				logTypes.add(WorkflowLog.TASK_COMPLETION);
				logTypes.add(WorkflowLog.TASK_UPDATE);
				logTypes.add(WorkflowLog.TRANSITION);

				List<WorkflowLog> workflowLogs = WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(company.getCompanyId(), workflowTask.getWorkflowInstanceId(), logTypes, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getLogCreateDateComparator(true));
				%>

				<%@ include file="/html/portlet/workflow_instances/workflow_logs.jspf" %>
			</liferay-ui:panel>

			<liferay-ui:panel title="comments">
				<portlet:actionURL var="discussionURL">
					<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task_discussion" />
				</portlet:actionURL>

				<liferay-ui:discussion
					assetEntryVisible="<%= false %>"
					className="<%= WorkflowInstance.class.getName() %>"
					classPK="<%= workflowTask.getWorkflowInstanceId() %>"
					formAction="<%= discussionURL %>"
					formName="fm1"
					ratingsEnabled="<%= false %>"
					redirect="<%= currentURL %>"
					userId="<%= user.getUserId() %>"
				/>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:col>

	<aui:col cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>" width="<%= 25 %>">
		<div class="lfr-asset-summary">
			<liferay-ui:icon
				cssClass="lfr-asset-avatar"
				image="../file_system/large/task"
				message="download"
			/>

			<div class="task-name">
				<%= LanguageUtil.get(pageContext, HtmlUtil.escape(workflowTask.getName())) %>
			</div>
		</div>

		<%
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		%>

		<liferay-util:include page="/html/portlet/workflow_tasks/workflow_task_action.jsp" />
	</aui:col>
</aui:row>

<aui:script use="liferay-workflow-tasks">
	var onTaskClickFn = A.rbind('onTaskClick', Liferay.WorkflowTasks, '');

	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskAssignToMeLink', onTaskClickFn);
	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskAssignLink', onTaskClickFn);
	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskDueDateLink', onTaskClickFn);
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, headerTitle, currentURL);
%>