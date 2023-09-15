<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/instance/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(workflowInstanceEditDisplayContext.getHeaderTitle());
%>

<clay:container-fluid>
	<clay:col
		cssClass="lfr-asset-column lfr-asset-column-details"
	>
		<clay:sheet
			size="full"
		>
			<clay:sheet-section>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<liferay-util:include page="/instance/workflow_instance_action.jsp" servletContext="<%= application %>" />

				<clay:col
					md="7"
				>
					<aui:field-wrapper label="status">
						<aui:fieldset>
							<%= workflowInstanceEditDisplayContext.getStatus() %>
						</aui:fieldset>
					</aui:field-wrapper>
				</clay:col>

				<clay:col
					md="4"
				>
					<aui:field-wrapper label="end-date">
						<aui:fieldset>
							<%= workflowInstanceEditDisplayContext.getWorkflowInstanceEndDate() %>
						</aui:fieldset>
					</aui:field-wrapper>
				</clay:col>
			</clay:sheet-section>

			<clay:panel-group>

				<%
				AssetRenderer<?> assetRenderer = workflowInstanceEditDisplayContext.getAssetRenderer();

				AssetEntry assetEntry = workflowInstanceEditDisplayContext.getAssetEntry();

				WorkflowHandler<?> workflowHandler = workflowInstanceEditDisplayContext.getWorkflowHandler();
				%>

				<c:if test="<%= assetRenderer != null %>">
					<clay:panel
						displayTitle="<%= workflowInstanceEditDisplayContext.getPanelTitle() %>"
						expanded="<%= true %>"
					>
						<div class="panel-body">
							<div class="task-content-actions">
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

									<liferay-ui:icon
										data='<%= Collections.singletonMap("title", "View") %>'
										icon="view"
										label="<%= false %>"
										linkCssClass="btn btn-monospaced btn-outline-secondary"
										markupView="lexicon"
										message="view[action]"
										toolTip="<%= true %>"
										url="<%= assetRenderer.isPreviewInContext() ? workflowHandler.getURLViewInContext(assetRenderer.getClassPK(), liferayPortletRequest, liferayPortletResponse, null) : viewFullContentURL.toString() %>"
									/>
								</c:if>
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

							<c:if test="<%= assetEntry != null %>">
								<h4 class="task-content-author">
									<liferay-ui:message key="author" />
								</h4>

								<liferay-asset:asset-metadata
									className="<%= assetEntry.getClassName() %>"
									classPK="<%= assetEntry.getClassPK() %>"
									metadataFields='<%= new String[] {"author", "categories", "tags"} %>'
								/>
							</c:if>
						</div>
					</clay:panel>

					<c:if test="<%= workflowHandler.isCommentable() %>">

						<%
						WorkflowInstance workflowInstance = (WorkflowInstance)renderRequest.getAttribute(WebKeys.WORKFLOW_INSTANCE);

						long discussionClassPK = workflowHandler.getDiscussionClassPK(workflowInstance.getWorkflowContext());
						%>

						<clay:panel
							displayTitle="comments"
						>
							<div class="panel-body">
								<liferay-comment:discussion
									className="<%= assetRenderer.getClassName() %>"
									classPK="<%= discussionClassPK %>"
									formName='<%= "fm" + discussionClassPK %>'
									ratingsEnabled="<%= false %>"
									redirect="<%= currentURL %>"
									userId="<%= user.getUserId() %>"
								/>
							</div>
						</clay:panel>
					</c:if>
				</c:if>

				<c:if test="<%= !workflowInstanceEditDisplayContext.isWorkflowTasksEmpty() %>">
					<clay:panel
						displayTitle='<%= LanguageUtil.get(request, "tasks") %>'
						expanded="<%= false %>"
					>
						<div class="panel-body">
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
											<liferay-ui:message key="<%= workflowTask.getLabel(locale) %>" />
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
						</div>
					</clay:panel>
				</c:if>

				<clay:panel
					displayTitle='<%= LanguageUtil.get(request, "activities") %>'
				>
					<div class="panel-body">
						<%@ include file="/instance/workflow_logs.jspf" %>
					</div>
				</clay:panel>
			</clay:panel-group>
		</clay:sheet>
	</clay:col>
</clay:container-fluid>