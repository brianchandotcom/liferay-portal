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

DepotAdminManagementToolbarDisplayContext depotAdminManagementToolbarDisplayContext = new DepotAdminManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, depotAdminDisplayContext);
%>

<clay:management-toolbar-v2
	displayContext="<%= depotAdminManagementToolbarDisplayContext %>"
/>

<div class="closed sidenav-container sidenav-right">
	<div class="sidenav-content">
		<clay:container-fluid>
			<portlet:actionURL name="deleteGroups" var="deleteGroupsURL" />

			<aui:form action="<%= depotAdminDisplayContext.getIteratorURL() %>" name="fm">
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

				<liferay-ui:search-container
					id="<%= depotAdminDisplayContext.getSearchContainerId() %>"
					searchContainer="<%= depotAdminDisplayContext.searchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.depot.model.DepotEntry"
						escapedModel="<%= true %>"
						keyProperty="depotEntryId"
						rowIdProperty="depotEntryId"
					>

						<%
						DepotEntry depotEntry = (DepotEntry)row.getObject();

						Group depotEntryGroup = depotEntry.getGroup();

						row.setData(depotAdminManagementToolbarDisplayContext.getRowData(depotEntry));
						%>

						<c:choose>
							<c:when test="<%= depotAdminDisplayContext.isDisplayStyleDescriptive() %>">
								<liferay-ui:search-container-column-text>
									<liferay-ui:search-container-column-icon
										icon="books"
										toggleRowChecker="<%= true %>"
									/>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text
									colspan="<%= 2 %>"
								>
									<h5>
										<aui:a cssClass="selector-button" href="<%= depotAdminDisplayContext.getViewDepotURL(depotEntry) %>">
											<%= HtmlUtil.escape(depotEntryGroup.getDescriptiveName(locale)) %>
										</aui:a>
									</h5>

									<h6>

										<%
										int depotEntryConnectedGroupsCount = depotAdminDisplayContext.getDepotEntryConnectedGroupsCount(depotEntry);
										%>

										<liferay-ui:message arguments="<%= depotEntryConnectedGroupsCount %>" key='<%= (depotEntryConnectedGroupsCount != 1) ? "x-connected-sites" : "x-connected-site" %>' />
									</h6>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text>
									<clay:dropdown-actions
										defaultEventHandler="<%= DepotAdminWebKeys.DEPOT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
										dropdownItems="<%= depotAdminDisplayContext.getActionDropdownItems(depotEntry) %>"
									/>
								</liferay-ui:search-container-column-text>
							</c:when>
							<c:when test="<%= depotAdminDisplayContext.isDisplayStyleIcon() %>">
								<liferay-ui:search-container-column-text>
									<clay:vertical-card
										verticalCard="<%= depotAdminDisplayContext.getDepotEntryVerticalCard(depotEntry) %>"
									/>
								</liferay-ui:search-container-column-text>
							</c:when>
							<c:otherwise>
								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand table-cell-minw-200 table-title"
									name="name"
									orderable="<%= true %>"
								>
									<aui:a href="<%= depotAdminDisplayContext.getViewDepotURL(depotEntry) %>" label="<%= HtmlUtil.escape(depotEntryGroup.getDescriptiveName(locale)) %>" localizeLabel="<%= false %>" />
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand table-cell-minw-200"
									name="num-of-connections"
									value="<%= String.valueOf(depotAdminDisplayContext.getDepotEntryConnectedGroupsCount(depotEntry)) %>"
								/>

								<liferay-ui:search-container-column-text>
									<clay:dropdown-actions
										defaultEventHandler="<%= DepotAdminWebKeys.DEPOT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
										dropdownItems="<%= depotAdminDisplayContext.getActionDropdownItems(depotEntry) %>"
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
		</clay:container-fluid>
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