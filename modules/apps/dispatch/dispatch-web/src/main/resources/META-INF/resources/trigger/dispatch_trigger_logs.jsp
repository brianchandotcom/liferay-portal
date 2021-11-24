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
DispatchLogDisplayContext dispatchLogDisplayContext = (DispatchLogDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

DispatchTrigger dispatchTrigger = dispatchLogDisplayContext.getDispatchTrigger();

PortletURL portletURL = PortletURLBuilder.create(
	dispatchLogDisplayContext.getPortletURL()
).setParameter(
	"searchContainerId", "dispatchLogs"
).buildPortletURL();

request.setAttribute("view.jsp-portletURL", portletURL);

SearchContainer<DispatchLog> dispatchLogSearchContainer = DispatchLogSearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new ViewDispatchLogManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, dispatchLogSearchContainer) %>"
	propsTransformer="trigger/js/DispatchLogManagementToolbarPropsTransformer"
/>

<div id="<portlet:namespace />triggerLogsContainer">
	<div class="closed container-fluid container-fluid-max-xl" id="<portlet:namespace />infoPanelId">
		<aui:form action="<%= portletURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
			<aui:input name="deleteDispatchLogIds" type="hidden" />

			<div class="trigger-lists-container" id="<portlet:namespace />entriesContainer">
				<liferay-ui:search-container
					id="dispatchLogs"
					searchContainer="<%= dispatchLogSearchContainer %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.dispatch.model.DispatchLog"
						keyProperty="dispatchLogId"
						modelVar="dispatchLog"
					>
						<liferay-ui:search-container-column-text
							cssClass="important table-cell-expand"
							href='<%=
								PortletURLBuilder.createRenderURL(
									renderResponse
								).setMVCRenderCommandName(
									"/dispatch/view_dispatch_log"
								).setRedirect(
									currentURL
								).setParameter(
									"dispatchLogId", dispatchLog.getDispatchLogId()
								).buildPortletURL()
							%>'
							name="start-date"
						>
							<%= fastDateFormat.format(dispatchLog.getStartDate()) %>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="runtime"
						>
							<%= (dispatchLog.getEndDate() == null) ? StringPool.DASH : String.valueOf(dispatchLog.getEndDate().getTime() - dispatchLog.getStartDate().getTime()) + " ms" %>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="trigger"
							value="<%= HtmlUtil.escape(dispatchTrigger.getName()) %>"
						/>

						<liferay-ui:search-container-column-text
							name="status"
						>

							<%
							DispatchTaskStatus dispatchTaskStatus = DispatchTaskStatus.valueOf(dispatchLog.getStatus());
							%>

							<h6 class="background-task-status-row background-task-status-<%= dispatchTaskStatus.getLabel() %> <%= dispatchTaskStatus.getCssClass() %>">
								<liferay-ui:message key="<%= dispatchTaskStatus.getLabel() %>" />
							</h6>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-jsp
							cssClass="entry-action-column"
							path="/dispatch_log_action.jsp"
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