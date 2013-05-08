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

<%@ include file="/html/portlet/workflow_tasks/init.jsp" %>

<%
String randomId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);

String redirect = ParamUtil.getString(request, "redirect");

WorkflowTask workflowTask = (WorkflowTask)request.getAttribute(WebKeys.WORKFLOW_TASK);

WorkflowInstance workflowInstance = WorkflowInstanceManagerUtil.getWorkflowInstance(company.getCompanyId(), workflowTask.getWorkflowInstanceId());

Map<String, Serializable> workflowContext = workflowInstance.getWorkflowContext();

long companyId = GetterUtil.getLong((String)workflowContext.get(WorkflowConstants.CONTEXT_COMPANY_ID));
long groupId = GetterUtil.getLong((String)workflowContext.get(WorkflowConstants.CONTEXT_GROUP_ID));
String className = (String)workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
long classPK = GetterUtil.getLong((String)workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

WorkflowHandler workflowHandler = WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

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

String viewFullContentURLString = null;

if (assetRenderer.isPreviewInContext()) {
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
			<aui:col width="<%= 60 %>">
				<div class="lfr-asset-assigned">
					<aui:field-wrapper label="assigned-to">
						<c:choose>
							<c:when test="<%= workflowTask.isAssignedToSingleUser() %>">
								<%= HtmlUtil.escape(PortalUtil.getUserName(workflowTask.getAssigneeUserId(), StringPool.BLANK)) %>
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="nobody" />
							</c:otherwise>
						</c:choose>

						<c:if test="<%= !workflowTask.isAssignedToSingleUser() %>">
							<%= StringPool.DASH %>

							<portlet:actionURL var="assignToMeURL">
								<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ASSIGN %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
								<portlet:param name="assigneeUserId" value="<%= String.valueOf(user.getUserId()) %>" />
							</portlet:actionURL>

							<span class="workflow-task task-assign-to-me-link"><aui:a href="<%= assignToMeURL %>" id='<%= randomId + "taskAssignToMeLink" %>' label="assign-to-me" /></span>
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

							<span class="workflow-task task-assign-link"><aui:a href="<%= assignURL %>" id='<%= randomId + "taskAssignLink" %>' label="assign-to-..." /></span>
						</c:if>
					</aui:field-wrapper>
				</div>

				<div class="lfr-asset-status">
					<aui:field-wrapper label="state">
						<%= LanguageUtil.get(pageContext, WorkflowInstanceLinkLocalServiceUtil.getState(companyId, groupId, className, classPK)) %>
					</aui:field-wrapper>
				</div>
			</aui:col>

			<aui:col>
				<div class="lfr-asset-date">
					<aui:field-wrapper label="create-date">
						<%= dateFormatDateTime.format(workflowTask.getCreateDate()) %>
					</aui:field-wrapper>
				</div>

				<div class="lfr-asset-due-date">
					<aui:field-wrapper label="due-date">
						<%= (workflowTask.getDueDate() == null) ? LanguageUtil.get(pageContext, "never") : dateFormatDateTime.format(workflowTask.getDueDate()) %>

						<c:if test="<%= !workflowTask.isCompleted() %>">
							<portlet:actionURL var="updateDueDateURL">
								<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="workflowTaskId" value="<%= StringUtil.valueOf(workflowTask.getWorkflowTaskId()) %>" />
							</portlet:actionURL>

							<%= StringPool.DASH %> (<span class="workflow-task task-due-date-link"><aui:a href="<%= updateDueDateURL %>" id='<%= randomId + "taskDueDateLink" %>' label="change" />)
						</c:if>
					</aui:field-wrapper>
				</div>
			</aui:col>
		</aui:row>

		<c:if test="<%= Validator.isNotNull(workflowTask.getDescription()) %>">
			<div class="lfr-asset-field">
				<aui:field-wrapper label="description">
					<%= workflowTask.getDescription() %>
				</aui:field-wrapper>
			</div>
		</c:if>

		<liferay-ui:panel-container cssClass="task-panel-container" extended="<%= true %>">
			<c:if test="<%= assetRenderer != null %>">
				<liferay-ui:panel defaultState="open" title='<%= LanguageUtil.format(pageContext, "preview-of-x", ResourceActionsUtil.getModelResource(locale, className)) %>'>
					<div class="task-content-actions">
						<liferay-ui:icon-list>
							<c:if test="<%= assetRenderer.hasViewPermission(permissionChecker) %>">
								<liferay-ui:icon image="view" method="get" target='<%= assetRenderer.isPreviewInContext() ? "_blank" : StringPool.BLANK %>' url="<%= viewFullContentURLString %>" />
							</c:if>

							<c:if test="<%= editPortletURL != null %>">

								<%
								editPortletURL.setWindowState(LiferayWindowState.POP_UP);
								editPortletURL.setPortletMode(PortletMode.VIEW);

								String editPortletURLString = editPortletURL.toString();

								editPortletURLString = HttpUtil.addParameter(editPortletURLString, "doAsGroupId", assetRenderer.getGroupId());
								editPortletURLString = HttpUtil.addParameter(editPortletURLString, "refererPlid", plid);

								editPortletURL.setParameter("redirect", currentURL);
								%>

								<c:choose>
									<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && showEditURL %>">

										<%
										String taglibEditURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "editAsset', title: '" + LanguageUtil.format(pageContext, "edit-x", HtmlUtil.escape(assetRenderer.getTitle(locale))) + "', uri:'" + HtmlUtil.escapeURL(editPortletURLString) + "'});";
										%>

										<liferay-ui:icon image="edit" url="<%= taglibEditURL %>" />
									</c:when>
									<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && !showEditURL && !workflowTask.isCompleted() %>">
										<liferay-ui:icon-help message="please-assign-the-task-to-yourself-to-be-able-to-edit-the-content" />
									</c:when>
								</c:choose>
							</c:if>
						</liferay-ui:icon-list>
					</div>

					<h3 class="task-content-title">
						<img alt="" src="<%= workflowHandler.getIconPath(liferayPortletRequest) %>" /> <%= HtmlUtil.escape(workflowHandler.getTitle(classPK, locale)) %>
					</h3>

					<%
					String path = workflowHandler.render(classPK, renderRequest, renderResponse, AssetRenderer.TEMPLATE_ABSTRACT);

					request.setAttribute(WebKeys.ASSET_RENDERER, assetRenderer);
					request.setAttribute(WebKeys.ASSET_PUBLISHER_ABSTRACT_LENGTH, 200);
					%>

					<c:choose>
						<c:when test="<%= path == null %>">
							<%= workflowHandler.getSummary(classPK, locale) %>
						</c:when>
						<c:otherwise>
							<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>" />
						</c:otherwise>
					</c:choose>

					<%
					String selectionStyle = "manual";

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
					subject="<%= LanguageUtil.get(pageContext, workflowTask.getName()) %>"
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
				<%= LanguageUtil.get(pageContext, workflowTask.getName()) %>
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