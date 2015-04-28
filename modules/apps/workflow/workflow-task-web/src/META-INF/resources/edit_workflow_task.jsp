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

String headerTitle = LanguageUtil.get(request, workflowTask.getName());

headerTitle = headerTitle.concat(StringPool.COLON + StringPool.SPACE + workflowHandler.getTitle(classPK, locale));

boolean showEditURL = false;

if ((workflowTask.getAssigneeUserId() == user.getUserId()) && !workflowTask.isCompleted()) {
	showEditURL = true;
}

PortletURL editPortletURL = workflowHandler.getURLEdit(classPK, liferayPortletRequest, liferayPortletResponse);
PortletURL viewDiffsPortletURL = workflowHandler.getURLViewDiffs(classPK, liferayPortletRequest, liferayPortletResponse);
%>

<portlet:renderURL var="backURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:renderURL>

<liferay-ui:header
	backURL="<%= backURL.toString() %>"
	localizeTitle="<%= Boolean.FALSE %>"
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
							<aui:input cssClass="assigned-to" inlineField="<%= Boolean.TRUE %>" name="assignedTo" type="resource" value="<%= PortalUtil.getUserName(workflowTask.getAssigneeUserId(), StringPool.BLANK) %>" />
						</c:when>
						<c:otherwise>
							<aui:input cssClass="assigned-to" inlineField="<%= Boolean.TRUE %>" name="assignedTo" type="resource" value='<%= LanguageUtil.get(request, "nobody") %>' />
						</c:otherwise>
					</c:choose>

					<c:if test="<%= !workflowTask.isAssignedToSingleUser() %>">
						<portlet:actionURL name="assignTask" var="assignToMeURL">
							<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
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

						<portlet:actionURL name="assignTask" var="assignURL">
							<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
						</portlet:actionURL>

						<aui:a cssClass="icon-signin" href="<%= assignURL %>" id='<%= randomId + "taskAssignLink" %>' label="assign-to-..." />
					</c:if>
				</div>

				<aui:input name="state" type="resource" value="<%= LanguageUtil.get(request, HtmlUtil.escape(WorkflowInstanceLinkLocalServiceUtil.getState(companyId, groupId, className, classPK))) %>" />
			</aui:col>

			<aui:col width="<%= 50 %>">
				<aui:input name="createDate" type="resource" value="<%= dateFormatDateTime.format(workflowTask.getCreateDate()) %>" />

				<aui:input inlineField="<%= Boolean.TRUE %>" name="dueDate" type="resource" value='<%= (workflowTask.getDueDate() == null) ? LanguageUtil.get(request, "never") : dateFormatDateTime.format(workflowTask.getDueDate()) %>' />

				<c:if test="<%= !workflowTask.isCompleted() %>">
					<portlet:actionURL name="updateTask" var="updateDueDateURL">
						<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="workflowTaskId" value="<%= StringUtil.valueOf(workflowTask.getWorkflowTaskId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						iconCssClass="icon-time"
						id='<%= randomId + "taskDueDateLink" %>'
						label="<%= Boolean.TRUE %>"
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

		<liferay-ui:panel-container cssClass="task-panel-container" extended="<%= Boolean.TRUE %>">
			<c:if test="<%= assetRenderer != null %>">
				<liferay-ui:panel defaultState="open" title='<%= LanguageUtil.format(request, "preview-of-x", ResourceActionsUtil.getModelResource(locale, className), false) %>'>
					<div class="task-content-actions">
						<liferay-ui:icon-list>
							<c:if test="<%= assetRenderer.hasViewPermission(permissionChecker) %>">
								<portlet:renderURL var="viewFullContentURL">
									<portlet:param name="mvcPath" value="/view_content.jsp" />
									<portlet:param name="redirect" value="<%= currentURL %>" />

									<c:if test="<%= assetEntry != null %>">
										<portlet:param name="assetEntryId" value="<%= String.valueOf(assetEntry.getEntryId()) %>" />
										<portlet:param name="assetEntryVersionId" value="<%= String.valueOf(classPK) %>" />
									</c:if>

									<portlet:param name="type" value="<%= assetRendererFactory.getType() %>" />
									<portlet:param name="showEditURL" value="<%= String.valueOf(showEditURL) %>" />
								</portlet:renderURL>

								<liferay-ui:icon iconCssClass="icon-search" message="view[action]" method="get" target='<%= assetRenderer.isPreviewInContext() ? "_blank" : StringPool.BLANK %>' url="<%= assetRenderer.isPreviewInContext() ? assetRenderer.getURLViewInContext((LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse, null) : viewFullContentURL.toString() %>" />

								<c:if test="<%= viewDiffsPortletURL != null %>">

									<%
									viewDiffsPortletURL.setParameter("redirect", currentURL);
									viewDiffsPortletURL.setParameter("hideControls", Boolean.TRUE.toString());
									viewDiffsPortletURL.setWindowState(LiferayWindowState.POP_UP);
									viewDiffsPortletURL.setPortletMode(PortletMode.VIEW);

									String taglibViewDiffsURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "viewDiffs', title: '" + HtmlUtil.escapeJS(LanguageUtil.get(request, "diffs")) + "', uri:'" + HtmlUtil.escapeJS(viewDiffsPortletURL.toString()) + "'});";
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
										String taglibEditURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "editAsset', title: '" + HtmlUtil.escapeJS(LanguageUtil.format(request, "edit-x", HtmlUtil.escape(assetRenderer.getTitle(locale)), false)) + "', uri:'" + HtmlUtil.escapeJS(editPortletURLString) + "'});";
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

					<liferay-ui:asset-display
						assetRenderer="<%= assetRenderer %>"
						template="<%= AssetRenderer.TEMPLATE_ABSTRACT %>"
					/>

					<%
					String[] metadataFields = new String[] {"author", "categories", "tags"};
					%>

					<liferay-ui:asset-metadata
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK() %>"
						metadataFields="<%= metadataFields %>"
					/>
				</liferay-ui:panel>

				<liferay-ui:panel title="comments">
					<portlet:actionURL name="invokeTaglibDiscussion" var="discussionURL" />

					<portlet:resourceURL var="discussionPaginationURL">
						<portlet:param name="invokeTaglibDiscussion" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:resourceURL>

					<liferay-ui:discussion
						assetEntryVisible="<%= Boolean.FALSE %>"
						className="<%= assetRenderer.getClassName() %>"
						classPK="<%= assetRenderer.getClassPK() %>"
						formAction="<%= discussionURL %>"
						formName='<%= "fm" + assetRenderer.getClassPK() %>'
						paginationURL="<%= discussionPaginationURL %>"
						ratingsEnabled="<%= Boolean.FALSE %>"
						redirect="<%= currentURL %>"
						userId="<%= user.getUserId() %>"
					/>
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

				<%@ include file="/workflow_logs.jspf" %>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:col>

	<aui:col cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= Boolean.TRUE %>" width="<%= 25 %>">
		<div class="lfr-asset-summary">
			<liferay-ui:icon
				cssClass="lfr-asset-avatar"
				image="../file_system/large/task"
				message="download"
			/>

			<div class="task-name">
				<%= LanguageUtil.get(request, HtmlUtil.escape(workflowTask.getName())) %>
			</div>
		</div>

		<%
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		%>

		<liferay-util:include page="/workflow_task_action.jsp" servletContext="<%= application %>" />
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
