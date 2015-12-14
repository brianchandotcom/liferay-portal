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

WorkflowInstanceEditDisplayContext workflowInstanceEditDisplayContext = null;

if (portletName.equals(WorkflowInstancePortletKeys.WORKFLOW_INSTANCE)) {
	workflowInstanceEditDisplayContext = new WorkflowInstanceEditDisplayContext(renderRequest, renderResponse);
}
else {
	workflowInstanceEditDisplayContext = new MyWorkflowInstanceEditDisplayContext(renderRequest, renderResponse);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(workflowInstanceEditDisplayContext.getHeaderTitle());
%>

<div class="container-fluid-1280">
	<aui:row>
		<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="<%= 75 %>">
			<aui:row>
				<aui:col width="<%= 60 %>">
					<aui:input name="state" type="resource" value="<%= workflowInstanceEditDisplayContext.getWorkflowInstanceState() %>" />
				</aui:col>

				<aui:col width="<%= 33 %>">
					<aui:input name="endDate" type="resource" value="<%= workflowInstanceEditDisplayContext.getWorkflowInstanceEndDate() %>" />
				</aui:col>
			</aui:row>

			<liferay-ui:panel-container cssClass="task-panel-container" extended="<%= true %>" id="preview">

				<%
				AssetRenderer<?> assetRenderer = workflowInstanceEditDisplayContext.getAssetRenderer();

				AssetEntry assetEntry = workflowInstanceEditDisplayContext.getAssetEntry();
				%>

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
								label="<%= true %>"
								message="<%= workflowInstanceEditDisplayContext.getTaskContentTitleMessage() %>"
							/>
						</h3>

						<liferay-ui:asset-display
							assetRenderer="<%= assetRenderer %>"
							template="<%= AssetRenderer.TEMPLATE_ABSTRACT %>"
						/>

						<c:if test="<%= assetEntry != null %>">
							<liferay-ui:asset-metadata
								className="<%= assetEntry.getClassName() %>"
								classPK="<%= assetEntry.getClassPK() %>"
								metadataFields='<%= new String[] {"author", "categories", "tags"} %>'
							/>
						</c:if>
					</liferay-ui:panel>

					<liferay-ui:panel title="comments">
						<liferay-ui:discussion
							className="<%= assetRenderer.getClassName() %>"
							classPK="<%= assetRenderer.getClassPK() %>"
							formName='<%= "fm" + assetRenderer.getClassPK() %>'
							ratingsEnabled="<%= false %>"
							redirect="<%= currentURL %>"
							userId="<%= user.getUserId() %>" />
					</liferay-ui:panel>
				</c:if>

				<c:if test="<%= !workflowInstanceEditDisplayContext.isWorkflowTasksEmpty() %>">
					<liferay-ui:panel defaultState="open" title="tasks">

						<liferay-ui:search-container
							emptyResultsMessage="there-are-no-tasks"
							iteratorURL="<%= renderResponse.createRenderURL() %>"
						>
							<liferay-ui:search-container-results
								results="<%= workflowInstanceEditDisplayContext.getWorkflowTasks() %>"
							/>

							<liferay-ui:search-container-row
								className="com.liferay.portal.kernel.workflow.WorkflowTask"
								modelVar="workflowTask"
								stringKey="<%= true %>"
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
							<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
						</liferay-ui:search-container>
					</liferay-ui:panel>
				</c:if>

				<liferay-ui:panel defaultState="closed" title="activities">
					<%@ include file="/workflow_logs.jspf" %>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</aui:col>

		<aui:col cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>" width="<%= 25 %>">

			<%
			request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
			%>

			<liferay-util:include page="/workflow_instance_action.jsp" servletContext="<%= application %>" />
		</aui:col>
	</aui:row>
</div>