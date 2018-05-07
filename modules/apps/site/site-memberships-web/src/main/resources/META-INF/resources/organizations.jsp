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
	items="<%= siteMembershipsDisplayContext.getViewNavigationItems() %>"
/>

<liferay-frontend:management-bar
	disabled="<%= organizationsDisplayContext.isDisabledManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="organizations"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= changeDisplayStyleURL %>"
			selectedDisplayStyle="<%= organizationsDisplayContext.getDisplayStyle() %>"
		/>

		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					id="selectOrganizations"
					title='<%= LanguageUtil.get(request, "assign-organizations") %>'
					url="javascript:;"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= organizationsDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= organizationsDisplayContext.getOrderByCol() %>"
			orderByType="<%= organizationsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name", "type"} %>'
			portletURL="<%= organizationsDisplayContext.getPortletURL() %>"
		/>

		<c:if test="<%= organizationsDisplayContext.isShowSearch() %>">
			<li>
				<aui:form action="<%= siteMembershipsDisplayContext.getPortletURL() %>" name="searchFm">
					<liferay-ui:input-search
						autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<liferay-frontend:management-bar-button
			href="javascript:;"
			icon="trash"
			id="deleteSelectedOrganizations"
			label="remove-membership"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/organization/info_panel" var="sidebarPanelURL">
		<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
	</liferay-portlet:resourceURL>

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="organizations"
	>
		<liferay-util:include page="/organization_info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<portlet:actionURL name="deleteGroupOrganizations" var="deleteGroupOrganizationsURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		<aui:form action="<%= deleteGroupOrganizationsURL %>" method="post" name="fm">
			<aui:input name="tabs1" type="hidden" value="organizations" />
			<aui:input name="groupId" type="hidden" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />

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
	</div>
</div>

<portlet:actionURL name="addGroupOrganizations" var="addGroupOrganizationsURL" />

<aui:form action="<%= addGroupOrganizationsURL %>" cssClass="hide" name="addGroupOrganizationsFm">
	<aui:input name="tabs1" type="hidden" value="organizations" />
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />deleteSelectedOrganizations').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm(form);
			}
		}
	);

	$('#<portlet:namespace />selectOrganizations').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectOrganizations',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								var addGroupOrganizationsFm = $(document.<portlet:namespace />addGroupOrganizationsFm);

								addGroupOrganizationsFm.append(selectedItem);

								submitForm(addGroupOrganizationsFm);
							}
						}
					},
					'strings.add': '<liferay-ui:message key="done" />',
					title: '<liferay-ui:message key="assign-organizations-to-this-site" />',
					url: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_organizations.jsp" /></portlet:renderURL>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>