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

String backURL = ParamUtil.getString(request, "backURL", redirect);

WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();

long classPK = workflowTaskDisplayContext.getWorkflowContextEntryClassPK(workflowTask);

WorkflowHandler<?> workflowHandler = workflowTaskDisplayContext.getWorkflowHandler(workflowTask);

AssetRenderer<?> assetRenderer = workflowHandler.getAssetRenderer(classPK);

AssetRendererFactory<?> assetRendererFactory = assetRenderer.getAssetRendererFactory();

AssetEntry assetEntry = assetRendererFactory.getAssetEntry(workflowHandler.getClassName(), assetRenderer.getClassPK());

String headerTitle = workflowTaskDisplayContext.getHeaderTitle(workflowTask);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(headerTitle);
%>

<div class="container-fluid-1280">
	<aui:col cssClass="lfr-asset-column lfr-asset-column-details">
		<liferay-ui:error exception="<%= WorkflowTaskDueDateException.class %>" message="please-enter-a-valid-due-date" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<liferay-util:include page="/workflow_task_action.jsp" servletContext="<%= application %>" />

				<aui:col width="<%= 50 %>">
					<aui:field-wrapper label="assigned-to">
						<aui:fieldset>
							<div class="card-row">
								<c:choose>
									<c:when test="<%= workflowTask.isAssignedToSingleUser() %>">
										<div class="card-col-field">
											<div class="list-group-card-icon">
												<liferay-ui:user-portrait userId="<%= workflowTask.getAssigneeUserId() %>" />
											</div>
										</div>

										<div class="card-col-content card-col-gutters">
											<div class="lfr-asset-assigned">
												<%= workflowTaskDisplayContext.getWorkflowTaskAssigneeUserName(workflowTask) %>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="card-col-content card-col-gutters lfr-asset-assigned">
											<div class="lfr-asset-assigned">
												<%= workflowTaskDisplayContext.getWorkflowTaskUnassignedUserName() %>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</aui:fieldset>
					</aui:field-wrapper>

					<aui:field-wrapper label="state">
						<aui:fieldset>
							<%= workflowTaskDisplayContext.getState(workflowTask) %>
						</aui:fieldset>
					</aui:field-wrapper>
				</aui:col>

				<aui:col width="<%= 50 %>">
					<aui:field-wrapper label="create-date">
						<aui:fieldset>
							<%= workflowTaskDisplayContext.getCreateDate(workflowTask) %>
						</aui:fieldset>
					</aui:field-wrapper>

					<aui:field-wrapper label="due-date">
						<aui:fieldset>
							<%= workflowTaskDisplayContext.getDueDateString(workflowTask) %>
						</aui:fieldset>
					</aui:field-wrapper>
				</aui:col>

				<c:if test="<%= Validator.isNotNull(workflowTask.getDescription()) %>">
					<aui:col>
						<aui:field-wrapper label="description">
							<aui:fieldset>
								<%= workflowTaskDisplayContext.getDescription(workflowTask) %>
							</aui:fieldset>
						</aui:field-wrapper>
					</aui:col>
				</c:if>
			</aui:fieldset>

			<liferay-ui:panel-container cssClass="task-panel-container" extended="<%= false %>">
				<c:if test="<%= assetRenderer != null %>">
					<liferay-ui:panel extended="<%= true %>" markupView="lexicon" title="<%= workflowTaskDisplayContext.getPreviewOfTitle(workflowTask) %>">
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
										<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
									</portlet:renderURL>

									<liferay-frontend:management-bar-button
										href="<%= assetRenderer.isPreviewInContext() ? assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, null) : viewFullContentURL.toString() %>"
										icon="view"
										label="view[action]"
									/>

									<c:if test="<%= workflowTaskDisplayContext.hasViewDiffsPortletURL(workflowTask) %>">
										<liferay-ui:icon iconCssClass="icon-copy" message="diffs" url="<%= workflowTaskDisplayContext.getTaglibViewDiffsURL(workflowTask) %>" />
									</c:if>
								</c:if>

								<c:if test="<%= workflowTaskDisplayContext.hasEditPortletURL(workflowTask) %>">
									<c:choose>
										<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && workflowTaskDisplayContext.isShowEditURL(workflowTask) %>">
											<liferay-frontend:management-bar-button
												href="<%= workflowTaskDisplayContext.getTaglibEditURL(workflowTask) %>"
												icon="pencil"
												label="edit"
											/>
										</c:when>
										<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) && !workflowTaskDisplayContext.isShowEditURL(workflowTask) && !workflowTask.isCompleted() %>">
											<liferay-frontend:management-bar-button
												href=""
												icon="question-circle-full"
												label="please-assign-the-task-to-yourself-to-be-able-to-edit-the-content"
											/>
										</c:when>
									</c:choose>
								</c:if>
							</liferay-ui:icon-list>
						</div>

						<h3 class="task-content-title">
							<liferay-ui:icon
								icon="<%= workflowHandler.getIconCssClass() %>"
								label="<%= true %>"
								markupView="lexicon"
								message="<%= workflowTaskDisplayContext.getTaskContentTitle(workflowTask) %>"
							/>
						</h3>

						<liferay-ui:asset-display
							assetRenderer="<%= assetRenderer %>"
							template="<%= AssetRenderer.TEMPLATE_ABSTRACT %>"
						/>
					</liferay-ui:panel>

					<liferay-ui:panel extended="<%= true %>" markupView="lexicon" title="comments">
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

				<liferay-ui:panel markupView="lexicon" title="activities">

					<%
					List<WorkflowLog> workflowLogs = workflowTaskDisplayContext.getWorkflowLogs(workflowTask);
					%>

					<%@ include file="/workflow_logs.jspf" %>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</aui:fieldset-group>
	</aui:col>
</div>

<aui:script use="liferay-workflow-tasks">
	var onTaskClickFn = A.rbind('onTaskClick', Liferay.WorkflowTasks, '');

	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskAssignLink', onTaskClickFn);
</aui:script>