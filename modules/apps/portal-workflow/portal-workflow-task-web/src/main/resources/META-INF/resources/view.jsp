<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(renderRequest, "tabs1", "assigned-to-me");

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}
else {
	portletDisplay.setShowBackIcon(false);
}
%>

<clay:navigation-bar
	inverted="<%= layout.isTypeControlPanel() %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("assigned-to-me"));
						navigationItem.setHref(renderResponse.createRenderURL(), "mvcPath", "/view.jsp", "tabs1", "assigned-to-me");
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "assigned-to-me"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("assigned-to-my-roles"));
						navigationItem.setHref(renderResponse.createRenderURL(), "mvcPath", "/view.jsp", "tabs1", "assigned-to-my-roles");
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "assigned-to-my-roles"));
					});
			}
		}
	%>'
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new WorkflowTaskManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, workflowTaskDisplayContext.getWorkflowTaskSearch()) %>"
/>

<clay:container-fluid>
	<liferay-ui:error exception="<%= WorkflowTaskDueDateException.class %>" message="please-enter-a-valid-due-date" />

	<liferay-ui:search-container
		id="workflowTasks"
		searchContainer="<%= workflowTaskDisplayContext.getWorkflowTaskSearch() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.workflow.WorkflowTask"
			modelVar="workflowTask"
			stringKey="<%= true %>"
		>
			<liferay-ui:search-container-row-parameter
				name="workflowTask"
				value="<%= workflowTask %>"
			/>

			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
			</portlet:renderURL>

			<c:choose>
				<c:when test='<%= Objects.equals(workflowTaskDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-icon
						cssClass="asset-icon"
						icon="<%= workflowTaskDisplayContext.getAssetIconCssClass(workflowTask) %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<div class="h5 text-default">

							<%
							DateSearchEntry dateSearchEntry = new DateSearchEntry();

							dateSearchEntry.setDate(workflowTaskDisplayContext.getLastActivityDate(workflowTask));
							%>

							<liferay-ui:message key="last-activity-date" />, <%= dateSearchEntry.getName(request) %>
						</div>

						<div class="h4">
							<clay:link
								href="<%= rowURL %>"
								label="<%= HtmlUtil.escape(workflowTaskDisplayContext.getAssetTitle(workflowTask)) %>"
							/>
						</div>

						<div class="h5 text-default">
							<span class="asset-type">
								<liferay-ui:message key="<%= workflowTaskDisplayContext.getAssetType(workflowTask) %>" />
							</span>
							<span class="author">
								<liferay-ui:message key="<%= HtmlUtil.escape(workflowTask.getUserName()) %>" />
							</span>
							<span class="task-name" id="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>">
								<liferay-ui:message key="<%= workflowTask.getLabel(workflowTaskDisplayContext.getTaskContentLocale()) %>" />
							</span>

							<c:if test="<%= workflowTaskDisplayContext.getDueDate(workflowTask) != null %>">

								<%
								dateSearchEntry.setDate(workflowTaskDisplayContext.getDueDate(workflowTask));
								%>

								<span class="due-date">
									<liferay-ui:message key="due-date" />: <%= dateSearchEntry.getName(request) %>
								</span>
							</c:if>
						</div>
					</liferay-ui:search-container-column-text>

					<c:choose>
						<c:when test="<%= !workflowTask.isCompleted() %>">
							<liferay-ui:search-container-column-jsp
								align="right"
								path="/workflow_task_action.jsp"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container-column-text
								value="<%= StringPool.BLANK %>"
							/>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="asset-title"
						href="<%= rowURL %>"
						name="asset-title"
						truncate="<%= true %>"
					>
						<span class="lfr-portal-tooltip text-truncate-inline" title="<%= HtmlUtil.escape(workflowTaskDisplayContext.getAssetTitle(workflowTask)) %>">
							<span class="text-truncate">
								<%= HtmlUtil.escape(workflowTaskDisplayContext.getAssetTitle(workflowTask)) %>
							</span>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="asset-type"
						value="<%= workflowTaskDisplayContext.getAssetType(workflowTask) %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="author"
						value="<%= HtmlUtil.escape(workflowTask.getUserName()) %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="task"
					>
						<span class="task-name" id="<%= workflowTask.getWorkflowTaskId() %>">
							<liferay-ui:message key="<%= workflowTask.getLabel(workflowTaskDisplayContext.getTaskContentLocale()) %>" />
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-date
						href="<%= rowURL %>"
						name="last-activity-date"
						value="<%= workflowTaskDisplayContext.getLastActivityDate(workflowTask) %>"
					/>

					<liferay-ui:search-container-column-date
						href="<%= rowURL %>"
						name="due-date"
						orderable="<%= true %>"
						value="<%= workflowTaskDisplayContext.getDueDate(workflowTask) %>"
					/>

					<c:choose>
						<c:when test="<%= !workflowTask.isCompleted() %>">
							<liferay-ui:search-container-column-jsp
								align="right"
								path="/workflow_task_action.jsp"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container-column-text
								value="<%= StringPool.BLANK %>"
							/>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= workflowTaskDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			resultRowSplitter="<%= new WorkflowTaskResultRowSplitter() %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>