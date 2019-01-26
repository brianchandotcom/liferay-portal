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
Group group = siteAdminDisplayContext.getGroup();

PortletURL portletURL = siteAdminDisplayContext.getPortletURL();

PortletURL mainURL = renderResponse.createRenderURL();

mainURL.setParameter("mvcPath", "/view.jsp");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "sites"), mainURL.toString());

if (group != null) {
	SitesUtil.addPortletBreadcrumbEntries(group, request, mainURL);

	portletDisplay.setShowBackIcon(true);

	PortletURL backURL = renderResponse.createRenderURL();

	backURL.setParameter("mvcPath", "/view.jsp");
	backURL.setParameter("groupId", String.valueOf(group.getParentGroupId()));

	portletDisplay.setURLBack(backURL.toString());

	renderResponse.setTitle(HtmlUtil.escape(group.getDescriptiveName(locale)));
}
%>

<clay:management-toolbar
	actionDropdownItems="<%= siteAdminDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= siteAdminDisplayContext.getClearResultsURL() %>"
	componentId="siteAdminWebManagementToolbar"
	creationMenu="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) ? siteAdminDisplayContext.getCreationMenu() : null %>"
	filterDropdownItems="<%= siteAdminDisplayContext.getFilterDropdownItems() %>"
	infoPanelId="infoPanelId"
	itemsTotal="<%= siteAdminDisplayContext.getTotalItems() %>"
	searchActionURL="<%= siteAdminDisplayContext.getSearchActionURL() %>"
	searchContainerId="sites"
	searchFormName="searchFm"
	showInfoButton="<%= true %>"
	sortingOrder="<%= siteAdminDisplayContext.getOrderByType() %>"
	sortingURL="<%= siteAdminDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= siteAdminDisplayContext.getViewTypeItems() %>"
/>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/site/info_panel" var="sidebarPanelURL" />

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="sites"
	>
		<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<portlet:actionURL name="deleteGroups" var="deleteGroupsURL" />

		<aui:form action="<%= deleteGroupsURL %>" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

			<div id="breadcrumb">
				<liferay-ui:breadcrumb
					showCurrentGroup="<%= false %>"
					showGuestGroup="<%= false %>"
					showLayout="<%= false %>"
					showPortletBreadcrumb="<%= true %>"
				/>
			</div>

			<liferay-ui:error exception="<%= NoSuchLayoutSetException.class %>">

				<%
				Group curGroup = GroupLocalServiceUtil.fetchGroup(scopeGroupId);

				NoSuchLayoutSetException nslse = (NoSuchLayoutSetException)errorException;

				String message = nslse.getMessage();

				int index = message.indexOf("{");

				if (index > 0) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(message.substring(index));

					curGroup = GroupLocalServiceUtil.fetchGroup(jsonObject.getLong("groupId"));
				}
				%>

				<c:if test="<%= curGroup != null %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>" key="site-x-does-not-have-any-private-pages" translateArguments="<%= false %>" />
				</c:if>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteCurrentGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-you-are-accessing-the-site" />
			<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteGroupThatHasChild.class %>" message="you-cannot-delete-sites-that-have-subsites" />
			<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteSystemGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-it-is-a-required-system-site" />

			<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>" />
		</aui:form>
	</div>
</div>

<aui:script sandbox="<%= true %>">
	var deleteSites = function() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			submitForm(AUI.$(document.<portlet:namespace />fm));
		}
	};

	var ACTIONS = {
		'deleteSites': deleteSites
	};

	Liferay.componentReady('siteAdminWebManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on(
				'actionItemClicked',
				function(event) {
					var itemData = event.data.item.data;

					if (itemData && itemData.action && ACTIONS[itemData.action]) {
						ACTIONS[itemData.action]();
					}
				}
			);
		}
	);
</aui:script>