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
OrganizationsDisplayContext organizationsDisplayContext = new OrganizationsDisplayContext(request, renderRequest, renderResponse);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= siteMembershipsDisplayContext.getViewNavigationItems() %>"
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new OrganizationsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, organizationsDisplayContext) %>"
	propsTransformer="js/OrganizationsManagementToolbarPropsTransformer"
/>

<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/site_memberships/organizations_info_panel" var="sidebarPanelURL">
		<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
	</liferay-portlet:resourceURL>

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="organizations"
	>
		<liferay-util:include page="/organization_info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<clay:container-fluid>
			<portlet:actionURL name="deleteGroupOrganizations" var="deleteGroupOrganizationsURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			<aui:form action="<%= deleteGroupOrganizationsURL %>" method="post" name="fm">
				<aui:input name="tabs1" type="hidden" value="organizations" />
				<aui:input name="groupId" type="hidden" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />

				<liferay-ui:breadcrumb
					showLayout="<%= false %>"
				/>

				<liferay-ui:search-container
					id="organizations"
					searchContainer="<%= organizationsDisplayContext.getOrganizationSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.portal.kernel.model.Organization"
						escapedModel="<%= true %>"
						keyProperty="organizationId"
						modelVar="organization"
					>

						<%
						String displayStyle = organizationsDisplayContext.getDisplayStyle();

						boolean selectOrganizations = false;
						%>

						<%@ include file="/organization_columns.jspf" %>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="<%= organizationsDisplayContext.getDisplayStyle() %>"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</aui:form>
		</clay:container-fluid>
	</div>
</div>

<portlet:actionURL name="addGroupOrganizations" var="addGroupOrganizationsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= addGroupOrganizationsURL %>" cssClass="hide" name="addGroupOrganizationsFm">
	<aui:input name="tabs1" type="hidden" value="organizations" />
</aui:form>