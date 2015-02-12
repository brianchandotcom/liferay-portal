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

<%@ include file="/html/portlet/workflow_instances/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WorkflowInstance workflowInstance = (WorkflowInstance)request.getAttribute(WebKeys.WORKFLOW_INSTANCE);

Map<String, Serializable> workflowContext = workflowInstance.getWorkflowContext();

String className = (String)workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
long classPK = GetterUtil.getLong((String)workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

WorkflowHandler<?> workflowHandler = WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

AssetRenderer assetRenderer = workflowHandler.getAssetRenderer(classPK);
AssetRendererFactory assetRendererFactory = workflowHandler.getAssetRendererFactory();

AssetEntry assetEntry = null;

if (assetRenderer != null) {
	assetEntry = assetRendererFactory.getAssetEntry(assetRendererFactory.getClassName(), assetRenderer.getClassPK());
}

String headerTitle = LanguageUtil.get(request, workflowInstance.getWorkflowDefinitionName());

if (assetEntry != null) {
	headerTitle = headerTitle.concat(StringPool.COLON + StringPool.SPACE + assetRenderer.getTitle(locale));
}

String portletId = PortletProviderUtil.getPortletId(className, PortletProvider.Action.VIEW);

PortletURL viewFullContentURL = PortletURLFactoryUtil.create(request, portletId, plid, PortletRequest.RENDER_PHASE);

viewFullContentURL.setParameter("mvcPath", "/html/portlet/asset_publisher/view_content.jsp");
viewFullContentURL.setParameter("redirect", currentURL);

if (assetEntry != null) {
	viewFullContentURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
	viewFullContentURL.setParameter("assetEntryVersionId", String.valueOf(classPK));
}

viewFullContentURL.setParameter("type", assetRendererFactory.getType());
viewFullContentURL.setParameter("showEditURL", Boolean.FALSE.toString());
viewFullContentURL.setParameter("workflowAssetPreview", Boolean.TRUE.toString());

request.setAttribute(WebKeys.WORKFLOW_ASSET_PREVIEW, Boolean.TRUE);
%>

<portlet:renderURL var="backURL">
	<portlet:param name="struts_action" value="/workflow_instances/view" />
</portlet:renderURL>

<liferay-ui:header
	backURL="<%= backURL.toString() %>"
	localizeTitle="<%= false %>"
	title="<%= headerTitle %>"
/>

<aui:row>
	<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="<%= 75 %>">
		<aui:row>
			<aui:col width="<%= 60 %>">
				<aui:input name="state" type="resource" value="<%= LanguageUtil.get(request, workflowInstance.getState()) %>" />
			</aui:col>

			<aui:col width="<%= 33 %>">
				<aui:input name="endDate" type="resource" value='<%= (workflowInstance.getEndDate() == null) ? LanguageUtil.get(request, "never") : dateFormatDateTime.format(workflowInstance.getEndDate()) %>' />
			</aui:col>
		</aui:row>

		<liferay-ui:panel-container cssClass="task-panel-container" extended="<%= true %>" id="preview">

			<c:if test="<%= assetRenderer != null %>">
				<liferay-ui:panel defaultState="open" title='<%= LanguageUtil.format(request, "preview-of-x", ResourceActionsUtil.getModelResource(locale, className), false) %>'>
					<div class="task-content-actions">
						<liferay-ui:icon-list>
							<c:if test="<%= assetRenderer.hasViewPermission(permissionChecker) %>">
								<liferay-ui:icon iconCssClass="icon-search" message="view[action]" method="get" url="<%= viewFullContentURL.toString() %>" />
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
					String[] metadataFields = new String[] {"author", "categories", "tags"};
					%>

					<liferay-ui:asset-metadata
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK() %>"
						metadataFields="<%= metadataFields %>"
					/>
				</liferay-ui:panel>
			</c:if>

			<%
			List<WorkflowTask> workflowTasks = null;

			if (portletName.equals(PortletKeys.WORKFLOW_DEFINITIONS)) {
				workflowTasks = WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(company.getCompanyId(), null, workflowInstance.getWorkflowInstanceId(), null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
			}
			else {
				workflowTasks = WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(company.getCompanyId(), user.getUserId(), workflowInstance.getWorkflowInstanceId(), false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
			}
			%>

			<c:if test="<%= !workflowTasks.isEmpty() %>">
				<liferay-ui:panel defaultState="open" title="tasks">

					<%
					PortletURL portletURL = renderResponse.createRenderURL();
					%>

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
									<liferay-ui:message key="<%= HtmlUtil.escape(workflowTask.getName()) %>" />
								</span>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								name="due-date"
								value='<%= (workflowTask.getDueDate() == null) ? LanguageUtil.get(request, "never") : dateFormatDateTime.format(workflowTask.getDueDate()) %>'
							/>

							<liferay-ui:search-container-column-text
								name="completed"
								value='<%= workflowTask.isCompleted() ? LanguageUtil.get(request, "yes") : LanguageUtil.get(request, "no") %>'
							/>

							<liferay-ui:search-container-column-jsp
								align="right"
								cssClass="entry-action"
								path="/html/portlet/workflow_instances/workflow_task_action.jsp"
							/>
						</liferay-ui:search-container-row>
						<liferay-ui:search-iterator />
					</liferay-ui:search-container>
				</liferay-ui:panel>
			</c:if>

			<liferay-ui:panel defaultState="closed" title="activities">

				<%
				List<Integer> logTypes = new ArrayList<Integer>();

				logTypes.add(WorkflowLog.TASK_ASSIGN);
				logTypes.add(WorkflowLog.TASK_COMPLETION);
				logTypes.add(WorkflowLog.TASK_UPDATE);
				logTypes.add(WorkflowLog.TRANSITION);

				List<WorkflowLog> workflowLogs = WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(company.getCompanyId(), workflowInstance.getWorkflowInstanceId(), logTypes, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getLogCreateDateComparator(true));
				%>

				<%@ include file="/html/portlet/workflow_instances/workflow_logs.jspf" %>
			</liferay-ui:panel>

			<liferay-ui:panel title="comments">
				<portlet:actionURL var="discussionURL">
					<portlet:param name="struts_action" value="/workflow_instances/edit_workflow_instance_discussion" />
				</portlet:actionURL>

				<portlet:resourceURL var="discussionPaginationURL">
					<portlet:param name="struts_action" value="/workflow_instances/edit_workflow_instance_discussion" />
				</portlet:resourceURL>

				<liferay-ui:discussion
					className="<%= WorkflowInstance.class.getName() %>"
					classPK="<%= workflowInstance.getWorkflowInstanceId() %>"
					formAction="<%= discussionURL %>"
					formName="fm1"
					paginationURL="<%= discussionPaginationURL %>"
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

			<div class="lfr-asset-name">
				<%= HtmlUtil.escape(workflowInstance.getWorkflowDefinitionName()) %>
			</div>
		</div>

		<%
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		%>

		<liferay-util:include page="/html/portlet/workflow_instances/workflow_instance_action.jsp" />
	</aui:col>
</aui:row>

<%
PortalUtil.addPortletBreadcrumbEntry(request, headerTitle, currentURL);
%>