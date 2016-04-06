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
long groupId = siteAdminDisplayContext.getGroupId();

Group group = siteAdminDisplayContext.getGroup();

String displayStyle = siteAdminDisplayContext.getDisplayStyle();
SearchContainer groupSearch = siteAdminDisplayContext.getSearchContainer();
PortletURL portletURL = siteAdminDisplayContext.getPortletURL();

request.setAttribute("view.jsp-displayStyle", displayStyle);
request.setAttribute("view.jsp-groupSearchContainer", groupSearch);

PortletURL mainURL = renderResponse.createRenderURL();

mainURL.setParameter("mvcPath", "/view.jsp");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "sites"), mainURL.toString());

if (group != null) {
	SitesUtil.addPortletBreadcrumbEntries(group, request, mainURL);

	renderResponse.setTitle(HtmlUtil.escape(group.getDescriptiveName(locale)));
}
%>

<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="sites" />
</liferay-util:include>

<div id="<portlet:namespace />sitesContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/site/info_panel" var="sidebarPanelURL">
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			<portlet:param name="displayStyle" value="<%= displayStyle %>" />
		</liferay-portlet:resourceURL>

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
					<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
				</div>

				<liferay-ui:error exception="<%= NoSuchLayoutSetException.class %>">

					<%
					Group curGroup = GroupLocalServiceUtil.getGroup(scopeGroupId);

					NoSuchLayoutSetException nslse = (NoSuchLayoutSetException)errorException;

					if (nslse.getMessage().indexOf("{") > 0) {
						String msg = nslse.getMessage();
						JSONObject jsonObject = JSONFactoryUtil.createJSONObject(msg.substring(msg.indexOf("{")));
						long errorGroupId = jsonObject.getLong("groupId");

						curGroup = GroupLocalServiceUtil.getGroup(errorGroupId);
					}
					%>

					<liferay-ui:message arguments="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>" key="site-x-does-not-have-any-private-pages" translateArguments="<%= false %>" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteCurrentGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-you-are-accessing-the-site" />
				<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteGroupThatHasChild.class %>" message="you-cannot-delete-sites-that-have-subsites" />
				<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteSystemGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-it-is-a-required-system-site" />

				<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>">
					<liferay-util:param name="searchContainerId" value="sites" />
				</liferay-util:include>
			</aui:form>
		</div>
	</div>
</div>