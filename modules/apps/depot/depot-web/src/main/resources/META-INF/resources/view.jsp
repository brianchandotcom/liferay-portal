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
DepotAdminDisplayContext depotAdminDisplayContext = new DepotAdminDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

DepotAdminManagementToolbarDisplayContext depotAdminManagementToolbarDisplayContext = new DepotAdminManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, depotAdminDisplayContext);
%>

<clay:management-toolbar
	displayContext="<%= depotAdminManagementToolbarDisplayContext %>"
/>

<div class="closed container-fluid-1280 sidenav-container sidenav-right">
	<div class="sidenav-content">
		<portlet:actionURL name="deleteGroups" var="deleteGroupsURL" />

		<aui:form action="<%= depotAdminDisplayContext.getIteratorURL() %>" cssClass="container-fluid-1280" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<liferay-ui:search-container
				id="<%= depotAdminDisplayContext.getSearchContainerId() %>"
				searchContainer="<%= depotAdminDisplayContext.getGroupSearch() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Group"
					cssClass="entry-display-style"
					escapedModel="<%= true %>"
					keyProperty="classPK"
					modelVar="curGroup"
					rowIdProperty="groupId"
				>

					<%
					row.setData(depotAdminManagementToolbarDisplayContext.getRowData(curGroup));
					%>

					<c:choose>
						<c:when test="<%= depotAdminDisplayContext.isDisplayStyleDescriptive() %>">
							<liferay-ui:search-container-column-text>
								<liferay-ui:search-container-column-icon
									icon="repository"
									toggleRowChecker="<%= true %>"
								/>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								colspan="<%= 2 %>"
							>
								<h5>
									<aui:a cssClass="selector-button" href="<%= depotAdminDisplayContext.getViewDepotURL(curGroup) %>">
										<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>
									</aui:a>
								</h5>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text>
								<clay:dropdown-actions
									defaultEventHandler="<%= DepotAdminWebKeys.DEPOT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
									dropdownItems="<%= depotAdminDisplayContext.getActionDropdownItems(curGroup) %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test="<%= depotAdminDisplayContext.isDisplayStyleIcon() %>">

							<%
							row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
							%>

							<liferay-ui:search-container-column-text>
								<clay:vertical-card
									verticalCard="<%= depotAdminDisplayContext.getDepotEntryVerticalCard(curGroup) %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 table-title"
								name="name"
								orderable="<%= true %>"
							>
								<aui:a href="<%= depotAdminDisplayContext.getViewDepotURL(curGroup) %>" label="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>" localizeLabel="<%= false %>" />
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text>
								<clay:dropdown-actions
									defaultEventHandler="<%= DepotAdminWebKeys.DEPOT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
									dropdownItems="<%= depotAdminDisplayContext.getActionDropdownItems(curGroup) %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= depotAdminDisplayContext.getDisplayStyle() %>"
					markupView="lexicon"
					searchContainer="<%= searchContainer %>"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</div>

<liferay-frontend:component
	componentId="<%= DepotAdminWebKeys.DEPOT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/DepotEntryDropdownDefaultEventHandler.es"
/>

<liferay-frontend:component
	componentId="<%= depotAdminManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	context="<%= depotAdminManagementToolbarDisplayContext.getComponentContext() %>"
	module="js/DepotAdminManagementToolbarDefaultEventHandler.es"
/>