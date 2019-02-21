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

if (group != null) {
	portletDisplay.setShowBackIcon(true);

	PortletURL backURL = renderResponse.createRenderURL();

	backURL.setParameter("mvcPath", "/view.jsp");
	backURL.setParameter("groupId", String.valueOf(group.getParentGroupId()));

	portletDisplay.setURLBack(backURL.toString());

	renderResponse.setTitle(HtmlUtil.escape(group.getDescriptiveName(locale)));
}
%>

<clay:management-toolbar
	displayContext="<%= siteAdminManagementToolbarDisplayContext %>"
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
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<liferay-site-navigation:breadcrumb
				breadcrumbEntries="<%= siteAdminDisplayContext.getBreadcrumbEntries() %>"
			/>

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

<liferay-frontend:component
	componentId="<%= siteAdminManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/ManagementToolbarDefaultEventHandler.es"
/>