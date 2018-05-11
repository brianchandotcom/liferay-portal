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

<%@ include file="/instance/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WorkflowInstanceEditDisplayContext workflowInstanceEditDisplayContext = null;

if (portletName.equals(WorkflowPortletKeys.USER_WORKFLOW)) {
	workflowInstanceEditDisplayContext = new MyWorkflowInstanceEditDisplayContext(liferayPortletRequest, liferayPortletResponse);
}
else {
	workflowInstanceEditDisplayContext = new WorkflowInstanceEditDisplayContext(liferayPortletRequest, liferayPortletResponse);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(workflowInstanceEditDisplayContext.getHeaderTitle());
%>

<div class="container-fluid-1280">
	<aui:col cssClass="lfr-asset-column lfr-asset-column-details">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<liferay-util:include page="/instance/workflow_instance_action.jsp" servletContext="<%= application %>" />

				<aui:col width="<%= 60 %>">
					<aui:field-wrapper label="state">
						<aui:fieldset>
							<%= HtmlUtil.escape(workflowInstanceEditDisplayContext.getWorkflowInstanceState()) %>
						</aui:fieldset>
					</aui:field-wrapper>
				</aui:col>

				<aui:col width="<%= 33 %>">
					<aui:field-wrapper label="end-date">
						<aui:fieldset>
							<%= workflowInstanceEditDisplayContext.getWorkflowInstanceEndDate() %>
						</aui:fieldset>
					</aui:field-wrapper>
				</aui:col>
			</aui:fieldset>

			<liferay-ui:panel-container
				cssClass="task-panel-container"
				extended="<%= false %>"
				id="preview"
			>

				<%
				AssetRenderer<?> assetRenderer = workflowInstanceEditDisplayContext.getAssetRenderer();

				AssetEntry assetEntry = workflowInstanceEditDisplayContext.getAssetEntry();
				%>

				<c:if test="<%= assetRenderer != null %>">
					<liferay-ui:panel
						extended="<%= true %>"
						markupView="lexicon"
						title="<%= workflowInstanceEditDisplayContext.getPanelTitle() %>"
					>
						<div class="task-content-actions">
							<liferay-ui:icon-list>
								<c:if test="<%= assetRenderer.hasViewPermission(permissionChecker) %>">
									<portlet:renderURL var="viewFullContentURL">
										<portlet:param name="mvcPath" value="/instance/view_content.jsp" />
										<portlet:param name="redirect" value="<%= currentURL %>" />

										<c:if test="<%= assetEntry != null %>">
											<portlet:param name="assetEntryId" value="<%= String.valueOf(assetEntry.getEntryId()) %>" />
											<portlet:param name="assetEntryVersionId" value="<%= workflowInstanceEditDisplayContext.getAssetEntryVersionId() %>" />
										</c:if>

										<portlet:param name="type" value="<%= workflowInstanceEditDisplayContext.getAssetRendererFactory().getType() %>" />
										<portlet:param name="showEditURL" value="<%= Boolean.FALSE.toString() %>" />
									</portlet:renderURL>

									<liferay-frontend:management-bar-button
										href="<%= assetRenderer.isPreviewInContext() ? assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, null) : viewFullContentURL.toString() %>"
										icon="view"
										label="view[action]"
									/>
								</c:if>
							</liferay-ui:icon-list>
						</div>

						<h3 class="task-content-title">
							<liferay-ui:icon
								icon="<%= workflowInstanceEditDisplayContext.getIconCssClass() %>"
								label="<%= true %>"
								markupView="lexicon"
								message="<%= workflowInstanceEditDisplayContext.getTaskContentTitleMessage() %>"
							/>
						</h3>

						<liferay-asset:asset-display
							assetRenderer="<%= assetRenderer %>"
							template="<%= AssetRenderer.TEMPLATE_ABSTRACT %>"
						/>
					</liferay-ui:panel>

					<liferay-ui:panel
						markupView="lexicon"
						title="comments"
					>
						<liferay-comment:discussion
							className="<%= assetRenderer.getClassName() %>"
							classPK="<%= assetRenderer.getClassPK() %>"
							formName='<%= "fm" + assetRenderer.getClassPK() %>'
							ratingsEnabled="<%= false %>"
							redirect="<%= currentURL %>"
							userId="<%= user.getUserId() %>"
						/>
					</liferay-ui:panel>
				</c:if>

				<c:if test="<%= !workflowInstanceEditDisplayContext.isWorkflowTasksEmpty() %>">
					<liferay-ui:panel
						extended="<%= false %>"
						markupView="lexicon"
						title="tasks"
					>
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

							<liferay-ui:search-iterator
								displayStyle="list"
								markupView="lexicon"
							/>
						</liferay-ui:search-container>
					</liferay-ui:panel>
				</c:if>

				<liferay-ui:panel
					markupView="lexicon"
					title="activities"
				>
					<%@ include file="/instance/workflow_logs.jspf" %>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</aui:fieldset-group>
	</aui:col>
</div>