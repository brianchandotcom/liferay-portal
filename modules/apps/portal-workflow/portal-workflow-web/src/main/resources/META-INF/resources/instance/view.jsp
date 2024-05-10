<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/instance/init.jsp" %>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new WorkflowInstanceViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, workflowInstanceViewDisplayContext.getSearchContainer()) %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		id="workflowInstances"
		searchContainer="<%= workflowInstanceViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.workflow.WorkflowInstance"
			modelVar="workflowInstance"
			stringKey="<%= true %>"
		>
			<liferay-ui:search-container-row-parameter
				name="workflowInstance"
				value="<%= workflowInstance %>"
			/>

			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/instance/edit_workflow_instance.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="workflowInstanceId" value="<%= String.valueOf(workflowInstance.getWorkflowInstanceId()) %>" />
			</portlet:renderURL>

			<c:choose>
				<c:when test='<%= Objects.equals(workflowInstanceViewDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-icon
						cssClass="asset-icon"
						icon="<%= workflowInstanceViewDisplayContext.getAssetIconCssClass(workflowInstance) %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<div class="h5 text-default">

							<%
							DateSearchEntry dateSearchEntry = new DateSearchEntry();

							dateSearchEntry.setDate(workflowInstanceViewDisplayContext.getLastActivityDate(workflowInstance));
							%>

							<liferay-ui:message key="last-activity-date" />, <%= dateSearchEntry.getName(request) %>
						</div>

						<div class="h4">
							<clay:link
								href="<%= rowURL %>"
								label="<%= workflowInstanceViewDisplayContext.getAssetTitle(workflowInstance) %>"
							/>
						</div>

						<div class="h5 text-default">
							<span class="asset-type">
								<liferay-ui:message key="<%= workflowInstanceViewDisplayContext.getAssetType(workflowInstance) %>" />
							</span>
							<span class="status">
								<liferay-ui:message key="<%= workflowInstanceViewDisplayContext.getStatus(workflowInstance) %>" />
							</span>
							<span class="definition">
								<liferay-ui:message key="<%= workflowInstanceViewDisplayContext.getDefinition(workflowInstance) %>" />
							</span>

							<c:if test="<%= workflowInstanceViewDisplayContext.getEndDate(workflowInstance) != null %>">

								<%
								dateSearchEntry.setDate(workflowInstanceViewDisplayContext.getEndDate(workflowInstance));
								%>

								<span class="end-date">
									<liferay-ui:message key="end-date" />: <%= dateSearchEntry.getName(request) %>
								</span>
							</c:if>
						</div>
					</liferay-ui:search-container-column-text>

					<c:choose>
						<c:when test="<%= !workflowInstance.isComplete() %>">
							<liferay-ui:search-container-column-jsp
								align="right"
								path="/instance/workflow_instance_action.jsp"
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
						cssClass="table-cell-expand table-cell-minw-200 table-title"
						href="<%= rowURL %>"
						name="asset-title"
						value="<%= workflowInstanceViewDisplayContext.getAssetTitle(workflowInstance) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller table-cell-minw-150"
						href="<%= rowURL %>"
						name="asset-type"
						value="<%= workflowInstanceViewDisplayContext.getAssetType(workflowInstance) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest"
						href="<%= rowURL %>"
						name="status"
						value="<%= workflowInstanceViewDisplayContext.getStatus(workflowInstance) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller table-cell-minw-150"
						href="<%= rowURL %>"
						name="definition"
						value="<%= workflowInstanceViewDisplayContext.getDefinition(workflowInstance) %>"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-ws-nowrap"
						href="<%= rowURL %>"
						name="last-activity-date"
						value="<%= workflowInstanceViewDisplayContext.getLastActivityDate(workflowInstance) %>"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-ws-nowrap"
						href="<%= rowURL %>"
						name="end-date"
						value="<%= workflowInstanceViewDisplayContext.getEndDate(workflowInstance) %>"
					/>

					<c:choose>
						<c:when test="<%= !workflowInstance.isComplete() %>">
							<liferay-ui:search-container-column-jsp
								path="/instance/workflow_instance_action.jsp"
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
			displayStyle="<%= workflowInstanceViewDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			resultRowSplitter="<%= new WorkflowInstanceResultRowSplitter() %>"
			searchContainer="<%= workflowInstanceViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>