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
String redirect = ParamUtil.getString(request, "redirect");

WorkflowInstanceEditDisplayContext workflowInstanceEditDisplayContext = new WorkflowInstanceEditDisplayContext(liferayPortletRequest);

AssetRenderer assetRenderer = workflowInstanceEditDisplayContext.getAssetRenderer();

AssetEntry assetEntry = workflowInstanceEditDisplayContext.getAssetEntry();

List<WorkflowTask> workflowTasks = workflowInstanceEditDisplayContext.getWorkflowTasks();

PortletURL portletURL = null;

if (!workflowTasks.isEmpty()) {
	portletURL = renderResponse.createRenderURL();
}

String[] metadataFields = new String[] {"author", "categories", "tags"};

List<WorkflowLog> workflowLogs = workflowInstanceEditDisplayContext.getWorkflowLogs();
%>

<portlet:renderURL var="backURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:renderURL>

<liferay-ui:header
	backURL="<%= backURL.toString() %>"
	localizeTitle="<%= Boolean.FALSE %>"
	title="<%= workflowInstanceEditDisplayContext.getHeaderTitle() %>"
/>

<aui:row>
	<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="<%= 75 %>">
		<aui:row>
			<aui:col width="<%= 60 %>">
				<aui:input name="state" type="resource" value="<%= workflowInstanceEditDisplayContext.getState() %>" />
			</aui:col>

			<aui:col width="<%= 33 %>">
				<aui:input name="endDate" type="resource" value="<%= workflowInstanceEditDisplayContext.getEndDate() %>" />
			</aui:col>
		</aui:row>

		<liferay-ui:panel-container cssClass="task-panel-container" extended="<%= Boolean.TRUE %>" id="preview">

			<c:if test="<%= assetRenderer != null %>">
				<liferay-ui:panel defaultState="open" title="<%= workflowInstanceEditDisplayContext.getPanelTitle() %>">
					<div class="task-content-actions">
						<liferay-ui:icon-list>
							<c:if test="<%= assetRenderer.hasViewPermission(permissionChecker) %>">
								<portlet:renderURL var="viewFullContentURL">
									<portlet:param name="mvcPath" value="/view_content.jsp" />
									<portlet:param name="redirect" value="<%= currentURL %>" />

									<c:if test="<%= assetEntry != null %>">
										<portlet:param name="assetEntryId" value="<%= String.valueOf(assetEntry.getEntryId()) %>" />
										<portlet:param name="assetEntryVersionId" value="<%= workflowInstanceEditDisplayContext.getAssetEntryVersionId() %>" />
									</c:if>

									<portlet:param name="type" value="<%= workflowInstanceEditDisplayContext.getAssetRendererFactory().getType() %>" />
									<portlet:param name="showEditURL" value="<%= Boolean.FALSE.toString() %>" />
								</portlet:renderURL>

								<liferay-ui:icon iconCssClass="icon-search" message="view[action]" method="get" url="<%= assetRenderer.isPreviewInContext() ? assetRenderer.getURLViewInContext((LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse, null) : viewFullContentURL.toString() %>" />
							</c:if>
						</liferay-ui:icon-list>
					</div>

					<h3 class="task-content-title">
						<liferay-ui:icon
							iconCssClass="<%= workflowInstanceEditDisplayContext.getIconCssClass() %>"
							label="<%= Boolean.TRUE %>"
							message="<%= workflowInstanceEditDisplayContext.getTaskContentTitleMessage() %>"
						/>
					</h3>

					<liferay-ui:asset-display
						assetRenderer="<%= assetRenderer %>"
						template="<%= AssetRenderer.TEMPLATE_ABSTRACT %>"
					/>

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
						className="<%= assetRenderer.getClassName() %>"
						classPK="<%= assetRenderer.getClassPK() %>"
						formAction="<%= discussionURL %>"
						formName='<%= "fm" + assetRenderer.getClassPK() %>'
						paginationURL="<%= discussionPaginationURL %>"
						ratingsEnabled="<%= Boolean.FALSE %>"
						redirect="<%= currentURL %>"
						userId="<%= user.getUserId() %>" />
				</liferay-ui:panel>
			</c:if>

			<c:if test="<%= !workflowTasks.isEmpty() %>">
				<liferay-ui:panel defaultState="open" title="tasks">

					<liferay-ui:search-container
						emptyResultsMessage="there-are-no-tasks"
						iteratorURL="<%= portletURL %>"
					>
						<liferay-ui:search-container-results
							results="<%= workflowTasks %>"
						/>

						<liferay-ui:search-container-row
							className="com.liferay.portal.kernel.workflow.WorkflowTask"
							modelVar="workflowTask"
							stringKey="<%= Boolean.TRUE %>"
						>
							<liferay-ui:search-container-row-parameter
								name="workflowTask"
								value="<%= workflowTask %>"
							/>

							<liferay-ui:search-container-column-text
								name="task"
							>
								<span class="task-name" id="<%= workflowTask.getWorkflowTaskId() %>">
									<liferay-ui:message key="<%= workflowInstanceEditDisplayContext.getTaskName(workflowTask) %>" />
								</span>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								name="due-date"
								value="<%= workflowInstanceEditDisplayContext.getTaskDueDate(workflowTask) %>"
							/>

							<liferay-ui:search-container-column-text
								name="completed"
								value="<%= workflowInstanceEditDisplayContext.getTaskCompleted(workflowTask) %>"
							/>

						</liferay-ui:search-container-row>
						<liferay-ui:search-iterator />
					</liferay-ui:search-container>
				</liferay-ui:panel>
			</c:if>

			<liferay-ui:panel defaultState="closed" title="activities">
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

			<div class="lfr-asset-name">
				<%= workflowInstanceEditDisplayContext.getAssetName() %>
			</div>
		</div>

		<%
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		%>

		<liferay-util:include page="/workflow_instance_action.jsp" servletContext="<%= application %>" />
	</aui:col>
</aui:row>

<%
PortalUtil.addPortletBreadcrumbEntry(request, workflowInstanceEditDisplayContext.getHeaderTitle(), currentURL);
%>