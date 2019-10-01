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
ContentRepositoryAdminDisplayContext contentRepositoryAdminDisplayContext = new ContentRepositoryAdminDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

ContentRepositoryAdminManagementToolbarDisplayContext contentRepositoryAdminManagementToolbarDisplayContext = new ContentRepositoryAdminManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, contentRepositoryAdminDisplayContext);
%>

<clay:management-toolbar
	displayContext="<%= contentRepositoryAdminManagementToolbarDisplayContext %>"
/>

<div class="closed container-fluid-1280 sidenav-container sidenav-right">
	<div class="sidenav-content">
		<portlet:actionURL name="deleteGroups" var="deleteGroupsURL" />

		<aui:form action="<%= deleteGroupsURL %>" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<liferay-ui:search-container
				searchContainer="<%= contentRepositoryAdminDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Group"
					escapedModel="<%= true %>"
					keyProperty="groupId"
					modelVar="curGroup"
					rowIdProperty="groupId"
				>

					<%
					row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new ContentRepositoryEntryVerticalCard(curGroup, liferayPortletRequest, liferayPortletResponse, searchContainer.getRowChecker()) %>"
						/>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= contentRepositoryAdminDisplayContext.getDisplayStyle() %>"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</div>

<liferay-frontend:component
	componentId="<%= contentRepositoryAdminManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/ContentRepositoryAdminManagementToolbarDefaultEventHandler.es"
/>