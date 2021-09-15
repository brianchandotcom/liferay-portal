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
DispatchTriggerDisplayContext dispatchTriggerDisplayContext = (DispatchTriggerDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = PortletURLBuilder.create(
	dispatchTriggerDisplayContext.getPortletURL()
).setTabs1(
	"dispatch-trigger"
).setParameter(
	"searchContainerId", "dispatchTriggers"
).buildPortletURL();
%>

<clay:navigation-bar
	navigationItems="<%= dispatchTriggerDisplayContext.getNavigationItems() %>"
/>

<liferay-util:include page="/dispatch_trigger_toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="dispatchTriggers" />
</liferay-util:include>

<div id="<portlet:namespace />dispatchTriggerContainer">
	<div class="closed container" id="<portlet:namespace />infoPanelId">
		<aui:form action="<%= portletURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
			<aui:input name="deleteDispatchTriggerIds" type="hidden" />

			<liferay-ui:search-container
				id="dispatchTriggers"
				searchContainer="<%= dispatchTriggerDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.dispatch.model.DispatchTrigger"
					keyProperty="dispatchTriggerId"
					modelVar="dispatchTrigger"
				>
					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/dispatch_trigger_action.jsp"
					/>

					<liferay-ui:search-container-column-text
						cssClass="font-weight-bold table-cell-expand"
						href='<%=
							PortletURLBuilder.createRenderURL(
								renderResponse
							).setMVCRenderCommandName(
								"/dispatch/edit_dispatch_trigger"
							).setRedirect(
								currentURL
							).setParameter(
								"dispatchTriggerId", dispatchTrigger.getDispatchTriggerId()
							).buildPortletURL()
						%>'
						property="name"
					/>

					<liferay-ui:search-container-column-text
						name="task-executor-type"
						property="dispatchTaskExecutorType"
					/>

					<liferay-ui:search-container-column-text
						name="system"
						value='<%= dispatchTrigger.isSystem() ? LanguageUtil.get(request, "yes") : LanguageUtil.get(request, "no") %>'
					/>

					<liferay-ui:search-container-column-date
						name="create-date"
						property="createDate"
					/>

					<%
					DispatchTriggerMetadata dispatchTriggerMetadata = dispatchTriggerDisplayContext.getDispatchTriggerMetadata(dispatchTrigger.getDispatchTriggerId());

					String nextFireDateString = LanguageUtil.get(request, "not-scheduled");

					if (dispatchTriggerMetadata.isDispatchTaskExecutorReady() && (dispatchTrigger.getNextFireDate() != null)) {
						nextFireDateString = fastDateFormat.format(dispatchTrigger.getNextFireDate());
					}
					%>

					<liferay-ui:search-container-column-text
						name="next-fire-date"
						value="<%= nextFireDateString %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="font-weight-bold table-cell-ws-nowrap"
						name="status"
					>

						<%
						DispatchTaskStatus dispatchTaskStatus = dispatchTrigger.getDispatchTaskStatus();
						%>

						<h6 class="background-task-status-row background-task-status-<%= dispatchTaskStatus.getLabel() %> <%= dispatchTaskStatus.getCssClass() %>">
							<liferay-ui:message key="<%= dispatchTaskStatus.getLabel() %>" />
						</h6>
					</liferay-ui:search-container-column-text>

					<c:choose>
						<c:when test="<%= dispatchTriggerMetadata.isDispatchTaskExecutorReady() %>">
							<liferay-ui:search-container-column-jsp
								cssClass="table-cell-ws-nowrap"
								path="/trigger/buttons.jsp"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container-column-text
								cssClass="font-weight-bold table-cell-ws-nowrap"
							>
								<h6 class="background-task-status-row text-warning">
									<liferay-ui:message key="incomplete" />
								</h6>
							</liferay-ui:search-container-column-text>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="list"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</div>