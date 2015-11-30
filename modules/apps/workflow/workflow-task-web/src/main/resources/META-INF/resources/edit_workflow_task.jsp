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
String tabs1 = ParamUtil.getString(request, "tabs1");
%>

<portlet:renderURL var="backURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
	<portlet:param name="tabs1" value="<%= tabs1 %>" />
</portlet:renderURL>

<%
String randomId = StringUtil.randomId();

String redirect = ParamUtil.getString(request, "redirect");

WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();

long classPK = workflowTaskDisplayContext.getWorkflowContextEntryClassPK(workflowTask);

WorkflowHandler<?> workflowHandler = workflowTaskDisplayContext.getWorkflowHandler(workflowTask);

AssetEntry assetEntry = null;

AssetRenderer<?> assetRenderer = workflowHandler.getAssetRenderer(classPK);
AssetRendererFactory<?> assetRendererFactory = workflowHandler.getAssetRendererFactory();

if (assetRenderer != null) {
	assetEntry = assetRendererFactory.getAssetEntry(assetRendererFactory.getClassName(), assetRenderer.getClassPK());
}

String headerTitle = workflowTaskDisplayContext.getHeaderTitle(workflowTask);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(headerTitle);
%>

<div class="container-fluid-1280">
	<aui:row>
		<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="<%= 75 %>">
			<liferay-ui:error exception="<%= WorkflowTaskDueDateException.class %>" message="please-enter-a-valid-due-date" />

			<aui:row>
				<aui:col width="<%= 50 %>">
					<div class="lfr-asset-assigned">
						<c:choose>
							<c:when test="<%= workflowTask.isAssignedToSingleUser() %>">
								<aui:input cssClass="assigned-to" inlineField="<%= true %>" name="assignedTo" type="resource" value="<%= workflowTaskDisplayContext.getWorkflowTaskAssigneeUserName(workflowTask) %>" />
							</c:when>
							<c:otherwise>
								<aui:input cssClass="assigned-to" inlineField="<%= true %>" name="assignedTo" type="resource" value="<%= workflowTaskDisplayContext.getWorkflowTaskUnassignedUserName() %>" />
							</c:otherwise>
						</c:choose>

						&nbsp;

						<c:if test="<%= workflowTaskDisplayContext.hasOtherAssignees(workflowTask) %>">
							<%= StringPool.DASH %>

							<portlet:actionURL name="assignWorkflowTask" var="assignURL">
								<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
							</portlet:actionURL>

							<aui:a cssClass="icon-signin" href="<%= assignURL %>" id='<%= randomId + "taskAssignLink" %>' label="assign-to-..." />
						</c:if>
					</div>

					<aui:input name="state" type="resource" value="<%= workflowTaskDisplayContext.getState(workflowTask) %>" />
				</aui:col>

				<aui:col width="<%= 50 %>">
					<aui:input name="createDate" type="resource" value="<%= workflowTaskDisplayContext.getCreateDate(workflowTask) %>" />

					<aui:input inlineField="<%= true %>" name="dueDate" type="resource" value="<%= workflowTaskDisplayContext.getDueDateString(workflowTask) %>" />

				</aui:col>
			</aui:row>

			<c:if test="<%= Validator.isNotNull(workflowTask.getDescription()) %>">
				<div class="lfr-asset-field">
					<aui:field-wrapper label="description">
						<%= workflowTaskDisplayContext.getDescription(workflowTask) %>
					</aui:field-wrapper>
				</div>
			</c:if>

			<liferay-ui:panel-container cssClass="task-panel-container" extended="<%= true %>">
				<c:if test="<%= assetRenderer != null %>">
					<liferay-ui:panel defaultState="open" title="<%= workflowTaskDisplayContext.getPreviewOfTitle(workflowTask) %>">
						<div class="task-content-actions">
							<liferay-ui:icon-list>
								<c:if test="<%= assetRenderer.hasViewPermission(permissionChecker) %>">
									<portlet:renderURL var="viewFullContentURL">
										<portlet:param name="mvcPath" value="/view_content.jsp" />
										<portlet:param name="redirect" value="<%= currentURL %>" />

										<c:if test="<%= assetEntry != null %>">
											<portlet:param name="assetEntryId" value="<%= String.valueOf(assetEntry.getEntryId()) %>" />
											<portlet:param name="assetEntryClassPK" value="<%= String.valueOf(assetEntry.getClassPK()) %>" />
										</c:if>

										<portlet:param name="type" value="<%= assetRendererFactory.getType() %>" />
										<portlet:param name="showEditURL" value="<%= String.valueOf(workflowTaskDisplayContext.isShowEditURL(workflowTask)) %>" />
									</portlet:renderURL>

									<liferay-ui:icon iconCssClass="icon-search" message="view[action]" method="get" target='<%= assetRenderer.isPreviewInContext() ? "_blank" : StringPool.BLANK %>' url="<%= assetRenderer.isPreviewInContext() ? assetRenderer.getURLViewInContext((LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse, null) : viewFullContentURL.toString() %>" />

									<c:if test="<%= workflowTaskDisplayContext.hasViewDiffsPortletURL(workflowTask) %>">
										<liferay-ui:icon iconCssClass="icon-copy" message="diffs" url="<%= workflowTaskDisplayContext.getTaglibViewDiffsURL(workflowTask) %>" />
									</c:if>
								</c:if>

								<c:if test="<%= workflowTaskDisplayContext.hasEditPortletURL(workflowTask) %>">
									<c:choose>
										<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && workflowTaskDisplayContext.isShowEditURL(workflowTask) %>">
											<liferay-ui:icon iconCssClass="icon-edit" message="edit" url="<%= workflowTaskDisplayContext.getTaglibEditURL(workflowTask) %>" />
										</c:when>
										<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && !workflowTaskDisplayContext.isShowEditURL(workflowTask) && !workflowTask.isCompleted() %>">
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
								message="<%= workflowTaskDisplayContext.getTaskContentTitle(workflowTask) %>"
							/>
						</h3>

						<liferay-ui:asset-display
							assetRenderer="<%= assetRenderer %>"
							template="<%= AssetRenderer.TEMPLATE_ABSTRACT %>"
						/>

						<liferay-ui:asset-metadata
							className="<%= assetEntry.getClassName() %>"
							classPK="<%= assetEntry.getClassPK() %>"
							metadataFields="<%= workflowTaskDisplayContext.getMetadataFields() %>"
						/>
					</liferay-ui:panel>

					<liferay-ui:panel title="comments">
						<liferay-ui:discussion
							assetEntryVisible="<%= false %>"
							className="<%= assetRenderer.getClassName() %>"
							classPK="<%= assetRenderer.getClassPK() %>"
							formName='<%= "fm" + assetRenderer.getClassPK() %>'
							ratingsEnabled="<%= false %>"
							redirect="<%= currentURL %>"
							userId="<%= user.getUserId() %>"
						/>
					</liferay-ui:panel>
				</c:if>

				<liferay-ui:panel defaultState="closed" title="activities">

					<%
					List<WorkflowLog> workflowLogs = workflowTaskDisplayContext.getWorkflowLogs(workflowTask);
					%>

					<%@ include file="/workflow_logs.jspf" %>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</aui:col>

		<aui:col cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>" width="<%= 25 %>">

			<%
			request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
			%>

			<liferay-util:include page="/workflow_task_action.jsp" servletContext="<%= application %>" />
		</aui:col>
	</aui:row>
</div>

<aui:script use="liferay-workflow-tasks">
	var onTaskClickFn = A.rbind('onTaskClick', Liferay.WorkflowTasks, '');

	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskAssignLink', onTaskClickFn);
</aui:script>