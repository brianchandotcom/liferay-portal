<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceDataIntegrationProcessLogDisplayContext commerceDataIntegrationProcessLogDisplayContext = (CommerceDataIntegrationProcessLogDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceDataIntegrationProcess commerceDataIntegrationProcess = commerceDataIntegrationProcessLogDisplayContext.getCommerceDataIntegrationProcess();

PortletURL portletURL = commerceDataIntegrationProcessLogDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceDataIntegrationProcessLogs");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-util:include page="/process_log_toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="commerceDataIntegrationProcessLogs" />
</liferay-util:include>

<div id="<portlet:namespace />processLogsContainer">
	<div class="closed container-fluid container-fluid-max-xl" id="<portlet:namespace />infoPanelId">
		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
			<aui:input name="deleteCDataIntegrationProcessLogIds" type="hidden" />

			<div class="process-lists-container" id="<portlet:namespace />entriesContainer">
				<liferay-ui:search-container
					id="commerceDataIntegrationProcessLogs"
					searchContainer="<%= commerceDataIntegrationProcessLogDisplayContext.getSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog"
						keyProperty="commerceDataIntegrationProcessLogId"
						modelVar="commerceDataIntegrationProcessLog"
					>

						<%
						PortletURL rowURL = renderResponse.createRenderURL();

						rowURL.setParameter("mvcRenderCommandName", "/commerce_data_integration/view_commerce_data_integration_process_log");
						rowURL.setParameter("redirect", currentURL);
						rowURL.setParameter("cDataIntegrationProcessLogId", String.valueOf(commerceDataIntegrationProcessLog.getCommerceDataIntegrationProcessLogId()));
						%>

						<liferay-ui:search-container-column-text
							cssClass="important table-cell-expand"
							href="<%= rowURL %>"
							name="start-date"
						>
							<%= commerceDataIntegrationProcessLogDisplayContext.getFormattedDate(commerceDataIntegrationProcessLog.getStartDate()) %>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="runtime"
						>
							<%= (commerceDataIntegrationProcessLog.getEndDate() == null) ? StringPool.DASH : String.valueOf(commerceDataIntegrationProcessLog.getEndDate().getTime() - commerceDataIntegrationProcessLog.getStartDate().getTime()) + " ms" %>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="process"
							value="<%= HtmlUtil.escape(commerceDataIntegrationProcess.getName()) %>"
						/>

						<liferay-ui:search-container-column-text
							name="status"
						>
							<h6 class="background-task-status-row background-task-status-<%= BackgroundTaskConstants.getStatusLabel(commerceDataIntegrationProcessLog.getStatus()) %> <%= BackgroundTaskConstants.getStatusCssClass(commerceDataIntegrationProcessLog.getStatus()) %>">
								<liferay-ui:message key="<%= BackgroundTaskConstants.getStatusLabel(commerceDataIntegrationProcessLog.getStatus()) %>" />
							</h6>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-jsp
							cssClass="entry-action-column"
							path="/process_log_action.jsp"
						/>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="list"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</div>
		</aui:form>
	</div>
</div>