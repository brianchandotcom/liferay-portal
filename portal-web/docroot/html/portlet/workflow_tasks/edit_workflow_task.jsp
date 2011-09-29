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

PortletURL editPortletURL = workflowHandler.getURLEdit(classPK, liferayPortletRequest, liferayPortletResponse);

PortletURL viewFullContentURL = renderResponse.createRenderURL();

viewFullContentURL.setParameter("struts_action", "/workflow_tasks/view_content");
viewFullContentURL.setParameter("redirect", currentURL);

if (assetRendererFactory != null) {
	viewFullContentURL.setParameter("type", assetRendererFactory.getType());
}

if (assetEntry != null) {
	viewFullContentURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
	viewFullContentURL.setParameter("assetEntryVersionId", String.valueOf(classPK));
}

boolean showEditURL = false;

if ((workflowTask.getAssigneeUserId() == user.getUserId()) && !workflowTask.isCompleted()) {
	showEditURL = true;
}

viewFullContentURL.setParameter("showEditURL", String.valueOf(showEditURL));

viewFullContentURL.setParameter("workflowAssetPreview", Boolean.TRUE.toString());

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

<aui:layout>
	<aui:column columnWidth="<%= 75 %>" cssClass="lfr-asset-column lfr-asset-column-details" first="<%= true %>">
		<liferay-ui:error exception="<%= WorkflowTaskDueDateException.class %>" message="please-enter-a-valid-due-date" />

		<aui:layout>
			<aui:column columnWidth="60">
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
			</aui:column>

			<aui:column>
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
			</aui:column>
		</aui:layout>

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
								<liferay-ui:icon image="view" method="get" url="<%= viewFullContentURL.toString() %>" />
							</c:if>

							<c:if test="<%= editPortletURL != null %>">

								<%
								editPortletURL.setWindowState(WindowState.MAXIMIZED);
								editPortletURL.setPortletMode(PortletMode.VIEW);

								editPortletURL.setParameter("redirect", currentURL);
								%>

								<c:choose>
									<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && showEditURL %>">
										<liferay-ui:icon image="edit" method="get" url="<%= editPortletURL.toString() %>" />
									</c:when>
									<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && !showEditURL && !workflowTask.isCompleted() %>">
										<liferay-ui:icon-help message="please-assign-the-task-to-yourself-to-be-able-to-edit-the-content" />
									</c:when>
								</c:choose>
							</c:if>
						</liferay-ui:icon-list>
					</div>

					<h3 class="task-content-title">
						<img src="<%= workflowHandler.getIconPath(liferayPortletRequest) %>" alt="" /> <%= HtmlUtil.escape(workflowHandler.getTitle(classPK, locale)) %>
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

				for (WorkflowLog workflowLog : workflowLogs) {
					Role curRole = null;
					User curUser = null;
					String actorName = null;

					if (workflowLog.getRoleId() != 0) {
						curRole = RoleLocalServiceUtil.getRole(workflowLog.getRoleId());
						actorName = curRole.getDescriptiveName();
					}
					else if (workflowLog.getUserId() != 0) {
						curUser = UserLocalServiceUtil.getUser(workflowLog.getUserId());
						actorName = curUser.getFullName();
					}
				%>

					<div class="task-activity task-type-<%= workflowLog.getType() %>">
						<div class="task-activity-date">
							<%= dateFormatDateTime.format(workflowLog.getCreateDate()) %>
						</div>

						<c:choose>
							<c:when test="<%= workflowLog.getType() == WorkflowLog.TASK_COMPLETION %>">
								<div>
									<%= LanguageUtil.format(pageContext, "x-completed-the-task-x", new Object[] {HtmlUtil.escape(actorName), workflowLog.getState()}) %>
								</div>
							</c:when>
							<c:when test="<%= workflowLog.getType() == WorkflowLog.TASK_UPDATE %>">
								<div>
									<%= LanguageUtil.format(pageContext, "x-updated-the-due-date", HtmlUtil.escape(actorName)) %>
								</div>
							</c:when>
							<c:when test="<%= (workflowLog.getType() == WorkflowLog.TRANSITION) %>">
								<div>
									<%= LanguageUtil.format(pageContext, "x-changed-the-state-from-x-to-x", new Object[] {HtmlUtil.escape(actorName), workflowLog.getPreviousState(), workflowLog.getState()}) %>
								</div>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="<%= (workflowLog.getPreviousUserId() == 0) && (curUser != null) %>">
										<div>
											<%= LanguageUtil.format(pageContext, curUser.isMale() ? "x-assigned-the-task-to-himself" : "x-assigned-the-task-to-herself", HtmlUtil.escape(curUser.getFullName())) %>
										</div>
									</c:when>
									<c:otherwise>

										<%
										String previousActorName = null;

										if (curRole == null) {
											previousActorName = PortalUtil.getUserName(workflowLog.getPreviousUserId(), StringPool.BLANK);
										%>

											<div>
												<%= LanguageUtil.format(pageContext, "task-assigned-to-x.-previous-assignee-was-x", new Object[] {actorName, HtmlUtil.escape(previousActorName)}) %>
											</div>

										<%
										}
										else {
											previousActorName = curRole.getDescriptiveName();
										%>

											<div>
												<%= LanguageUtil.format(pageContext, "task-initially-assigned-to-the-x-role", new Object[] {actorName}) %>
											</div>

										<%
										}
										%>

									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>

						<div>
							<%= HtmlUtil.escape(workflowLog.getComment()) %>
						</div>
					</div>

				<%
				}
				%>

			</liferay-ui:panel>

			<liferay-ui:panel title="comments">
				<portlet:actionURL var="discussionURL">
					<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task_discussion" />
				</portlet:actionURL>

				<liferay-ui:discussion
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
	</aui:column>

	<aui:column columnWidth="<%= 25 %>" cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>">
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
	</aui:column>
</aui:layout>

<aui:script use="liferay-workflow-tasks">
	var onTaskClickFn = A.rbind(Liferay.WorkflowTasks.onTaskClick, Liferay.WorkflowTasks, '');

	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskAssignToMeLink', onTaskClickFn);
	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskAssignLink', onTaskClickFn);
	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskDueDateLink', onTaskClickFn);
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, headerTitle, currentURL);
%>